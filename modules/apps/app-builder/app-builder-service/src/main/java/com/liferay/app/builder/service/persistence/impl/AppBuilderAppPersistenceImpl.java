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

package com.liferay.app.builder.service.persistence.impl;

import com.liferay.app.builder.exception.NoSuchAppException;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppTable;
import com.liferay.app.builder.model.impl.AppBuilderAppImpl;
import com.liferay.app.builder.model.impl.AppBuilderAppModelImpl;
import com.liferay.app.builder.service.persistence.AppBuilderAppPersistence;
import com.liferay.app.builder.service.persistence.impl.constants.AppBuilderPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

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
 * The persistence implementation for the app builder app service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AppBuilderAppPersistence.class)
public class AppBuilderAppPersistenceImpl
	extends BasePersistenceImpl<AppBuilderApp>
	implements AppBuilderAppPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AppBuilderAppUtil</code> to access the app builder app persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AppBuilderAppImpl.class.getName();

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
	 * Returns all the app builder apps where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder apps where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder apps where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder apps where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator,
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

		List<AppBuilderApp> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderApp>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderApp appBuilderApp : list) {
					if (!uuid.equals(appBuilderApp.getUuid())) {
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

			sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

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
				sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
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

				list = (List<AppBuilderApp>)QueryUtil.list(
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
	 * Returns the first app builder app in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByUuid_First(
			String uuid, OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByUuid_First(
			uuid, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the first app builder app in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByUuid_First(
		String uuid, OrderByComparator<AppBuilderApp> orderByComparator) {

		List<AppBuilderApp> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByUuid_Last(
			String uuid, OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByUuid_Last(uuid, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the last app builder app in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByUuid_Last(
		String uuid, OrderByComparator<AppBuilderApp> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AppBuilderApp> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where uuid = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp[] findByUuid_PrevAndNext(
			long appBuilderAppId, String uuid,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		uuid = Objects.toString(uuid, "");

		AppBuilderApp appBuilderApp = findByPrimaryKey(appBuilderAppId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderApp[] array = new AppBuilderAppImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, appBuilderApp, uuid, orderByComparator, true);

			array[1] = appBuilderApp;

			array[2] = getByUuid_PrevAndNext(
				session, appBuilderApp, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AppBuilderApp getByUuid_PrevAndNext(
		Session session, AppBuilderApp appBuilderApp, String uuid,
		OrderByComparator<AppBuilderApp> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

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
			sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
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
						appBuilderApp)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderApp> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder apps where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AppBuilderApp appBuilderApp :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(appBuilderApp);
		}
	}

	/**
	 * Returns the number of app builder apps where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching app builder apps
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_APPBUILDERAPP_WHERE);

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
		"appBuilderApp.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(appBuilderApp.uuid IS NULL OR appBuilderApp.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the app builder app where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchAppException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByUUID_G(String uuid, long groupId)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByUUID_G(uuid, groupId);

		if (appBuilderApp == null) {
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

			throw new NoSuchAppException(sb.toString());
		}

		return appBuilderApp;
	}

	/**
	 * Returns the app builder app where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the app builder app where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof AppBuilderApp) {
			AppBuilderApp appBuilderApp = (AppBuilderApp)result;

			if (!Objects.equals(uuid, appBuilderApp.getUuid()) ||
				(groupId != appBuilderApp.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

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

				List<AppBuilderApp> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					AppBuilderApp appBuilderApp = list.get(0);

					result = appBuilderApp;

					cacheResult(appBuilderApp);
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
			return (AppBuilderApp)result;
		}
	}

	/**
	 * Removes the app builder app where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the app builder app that was removed
	 */
	@Override
	public AppBuilderApp removeByUUID_G(String uuid, long groupId)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = findByUUID_G(uuid, groupId);

		return remove(appBuilderApp);
	}

	/**
	 * Returns the number of app builder apps where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching app builder apps
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_APPBUILDERAPP_WHERE);

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
		"appBuilderApp.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(appBuilderApp.uuid IS NULL OR appBuilderApp.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"appBuilderApp.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the app builder apps where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder apps where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder apps where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder apps where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator,
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

		List<AppBuilderApp> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderApp>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderApp appBuilderApp : list) {
					if (!uuid.equals(appBuilderApp.getUuid()) ||
						(companyId != appBuilderApp.getCompanyId())) {

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

			sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

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
				sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
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

				list = (List<AppBuilderApp>)QueryUtil.list(
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
	 * Returns the first app builder app in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the first app builder app in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		List<AppBuilderApp> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the last app builder app in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AppBuilderApp> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp[] findByUuid_C_PrevAndNext(
			long appBuilderAppId, String uuid, long companyId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		uuid = Objects.toString(uuid, "");

		AppBuilderApp appBuilderApp = findByPrimaryKey(appBuilderAppId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderApp[] array = new AppBuilderAppImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, appBuilderApp, uuid, companyId, orderByComparator,
				true);

			array[1] = appBuilderApp;

			array[2] = getByUuid_C_PrevAndNext(
				session, appBuilderApp, uuid, companyId, orderByComparator,
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

	protected AppBuilderApp getByUuid_C_PrevAndNext(
		Session session, AppBuilderApp appBuilderApp, String uuid,
		long companyId, OrderByComparator<AppBuilderApp> orderByComparator,
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

		sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

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
			sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
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
						appBuilderApp)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderApp> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder apps where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AppBuilderApp appBuilderApp :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(appBuilderApp);
		}
	}

	/**
	 * Returns the number of app builder apps where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching app builder apps
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_APPBUILDERAPP_WHERE);

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
		"appBuilderApp.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(appBuilderApp.uuid IS NULL OR appBuilderApp.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"appBuilderApp.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the app builder apps where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder apps where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder apps where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder apps where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<AppBuilderApp> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderApp>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderApp appBuilderApp : list) {
					if (groupId != appBuilderApp.getGroupId()) {
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

			sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<AppBuilderApp>)QueryUtil.list(
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
	 * Returns the first app builder app in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByGroupId_First(
			long groupId, OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByGroupId_First(
			groupId, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the first app builder app in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByGroupId_First(
		long groupId, OrderByComparator<AppBuilderApp> orderByComparator) {

		List<AppBuilderApp> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByGroupId_Last(
			long groupId, OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the last app builder app in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByGroupId_Last(
		long groupId, OrderByComparator<AppBuilderApp> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<AppBuilderApp> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where groupId = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp[] findByGroupId_PrevAndNext(
			long appBuilderAppId, long groupId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = findByPrimaryKey(appBuilderAppId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderApp[] array = new AppBuilderAppImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, appBuilderApp, groupId, orderByComparator, true);

			array[1] = appBuilderApp;

			array[2] = getByGroupId_PrevAndNext(
				session, appBuilderApp, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AppBuilderApp getByGroupId_PrevAndNext(
		Session session, AppBuilderApp appBuilderApp, long groupId,
		OrderByComparator<AppBuilderApp> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						appBuilderApp)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderApp> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the app builder apps that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching app builder apps that the user has permission to view
	 */
	@Override
	public List<AppBuilderApp> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder apps that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps that the user has permission to view
	 */
	@Override
	public List<AppBuilderApp> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder apps that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps that the user has permission to view
	 */
	@Override
	public List<AppBuilderApp> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_APPBUILDERAPP_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_APPBUILDERAPP_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_APPBUILDERAPP_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AppBuilderAppModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AppBuilderApp.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AppBuilderAppImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AppBuilderAppImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<AppBuilderApp>)QueryUtil.list(
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
	 * Returns the app builder apps before and after the current app builder app in the ordered set of app builder apps that the user has permission to view where groupId = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp[] filterFindByGroupId_PrevAndNext(
			long appBuilderAppId, long groupId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(
				appBuilderAppId, groupId, orderByComparator);
		}

		AppBuilderApp appBuilderApp = findByPrimaryKey(appBuilderAppId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderApp[] array = new AppBuilderAppImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(
				session, appBuilderApp, groupId, orderByComparator, true);

			array[1] = appBuilderApp;

			array[2] = filterGetByGroupId_PrevAndNext(
				session, appBuilderApp, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AppBuilderApp filterGetByGroupId_PrevAndNext(
		Session session, AppBuilderApp appBuilderApp, long groupId,
		OrderByComparator<AppBuilderApp> orderByComparator, boolean previous) {

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
			sb.append(_FILTER_SQL_SELECT_APPBUILDERAPP_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_APPBUILDERAPP_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_APPBUILDERAPP_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AppBuilderAppModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AppBuilderApp.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, AppBuilderAppImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, AppBuilderAppImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						appBuilderApp)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderApp> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder apps where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (AppBuilderApp appBuilderApp :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(appBuilderApp);
		}
	}

	/**
	 * Returns the number of app builder apps where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching app builder apps
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_APPBUILDERAPP_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	/**
	 * Returns the number of app builder apps that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching app builder apps that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_APPBUILDERAPP_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AppBuilderApp.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"appBuilderApp.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the app builder apps where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder apps where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder apps where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder apps where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<AppBuilderApp> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderApp>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderApp appBuilderApp : list) {
					if (companyId != appBuilderApp.getCompanyId()) {
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

			sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<AppBuilderApp>)QueryUtil.list(
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
	 * Returns the first app builder app in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByCompanyId_First(
			long companyId, OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the first app builder app in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByCompanyId_First(
		long companyId, OrderByComparator<AppBuilderApp> orderByComparator) {

		List<AppBuilderApp> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByCompanyId_Last(
			long companyId, OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the last app builder app in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByCompanyId_Last(
		long companyId, OrderByComparator<AppBuilderApp> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<AppBuilderApp> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where companyId = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp[] findByCompanyId_PrevAndNext(
			long appBuilderAppId, long companyId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = findByPrimaryKey(appBuilderAppId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderApp[] array = new AppBuilderAppImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, appBuilderApp, companyId, orderByComparator, true);

			array[1] = appBuilderApp;

			array[2] = getByCompanyId_PrevAndNext(
				session, appBuilderApp, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AppBuilderApp getByCompanyId_PrevAndNext(
		Session session, AppBuilderApp appBuilderApp, long companyId,
		OrderByComparator<AppBuilderApp> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						appBuilderApp)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderApp> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder apps where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (AppBuilderApp appBuilderApp :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(appBuilderApp);
		}
	}

	/**
	 * Returns the number of app builder apps where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching app builder apps
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_APPBUILDERAPP_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"appBuilderApp.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByDDMStructureId;
	private FinderPath _finderPathWithoutPaginationFindByDDMStructureId;
	private FinderPath _finderPathCountByDDMStructureId;

	/**
	 * Returns all the app builder apps where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @return the matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByDDMStructureId(long ddmStructureId) {
		return findByDDMStructureId(
			ddmStructureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder apps where ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByDDMStructureId(
		long ddmStructureId, int start, int end) {

		return findByDDMStructureId(ddmStructureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder apps where ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByDDMStructureId(
		long ddmStructureId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return findByDDMStructureId(
			ddmStructureId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder apps where ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByDDMStructureId(
		long ddmStructureId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByDDMStructureId;
				finderArgs = new Object[] {ddmStructureId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByDDMStructureId;
			finderArgs = new Object[] {
				ddmStructureId, start, end, orderByComparator
			};
		}

		List<AppBuilderApp> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderApp>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderApp appBuilderApp : list) {
					if (ddmStructureId != appBuilderApp.getDdmStructureId()) {
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

			sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

			sb.append(_FINDER_COLUMN_DDMSTRUCTUREID_DDMSTRUCTUREID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ddmStructureId);

				list = (List<AppBuilderApp>)QueryUtil.list(
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
	 * Returns the first app builder app in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByDDMStructureId_First(
			long ddmStructureId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByDDMStructureId_First(
			ddmStructureId, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ddmStructureId=");
		sb.append(ddmStructureId);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the first app builder app in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByDDMStructureId_First(
		long ddmStructureId,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		List<AppBuilderApp> list = findByDDMStructureId(
			ddmStructureId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByDDMStructureId_Last(
			long ddmStructureId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByDDMStructureId_Last(
			ddmStructureId, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ddmStructureId=");
		sb.append(ddmStructureId);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the last app builder app in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByDDMStructureId_Last(
		long ddmStructureId,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		int count = countByDDMStructureId(ddmStructureId);

		if (count == 0) {
			return null;
		}

		List<AppBuilderApp> list = findByDDMStructureId(
			ddmStructureId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp[] findByDDMStructureId_PrevAndNext(
			long appBuilderAppId, long ddmStructureId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = findByPrimaryKey(appBuilderAppId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderApp[] array = new AppBuilderAppImpl[3];

			array[0] = getByDDMStructureId_PrevAndNext(
				session, appBuilderApp, ddmStructureId, orderByComparator,
				true);

			array[1] = appBuilderApp;

			array[2] = getByDDMStructureId_PrevAndNext(
				session, appBuilderApp, ddmStructureId, orderByComparator,
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

	protected AppBuilderApp getByDDMStructureId_PrevAndNext(
		Session session, AppBuilderApp appBuilderApp, long ddmStructureId,
		OrderByComparator<AppBuilderApp> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

		sb.append(_FINDER_COLUMN_DDMSTRUCTUREID_DDMSTRUCTUREID_2);

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
			sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ddmStructureId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						appBuilderApp)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderApp> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder apps where ddmStructureId = &#63; from the database.
	 *
	 * @param ddmStructureId the ddm structure ID
	 */
	@Override
	public void removeByDDMStructureId(long ddmStructureId) {
		for (AppBuilderApp appBuilderApp :
				findByDDMStructureId(
					ddmStructureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(appBuilderApp);
		}
	}

	/**
	 * Returns the number of app builder apps where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @return the number of matching app builder apps
	 */
	@Override
	public int countByDDMStructureId(long ddmStructureId) {
		FinderPath finderPath = _finderPathCountByDDMStructureId;

		Object[] finderArgs = new Object[] {ddmStructureId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_APPBUILDERAPP_WHERE);

			sb.append(_FINDER_COLUMN_DDMSTRUCTUREID_DDMSTRUCTUREID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ddmStructureId);

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

	private static final String _FINDER_COLUMN_DDMSTRUCTUREID_DDMSTRUCTUREID_2 =
		"appBuilderApp.ddmStructureId = ?";

	private FinderPath _finderPathWithPaginationFindByG_S;
	private FinderPath _finderPathWithoutPaginationFindByG_S;
	private FinderPath _finderPathCountByG_S;

	/**
	 * Returns all the app builder apps where groupId = &#63; and scope = &#63;.
	 *
	 * @param groupId the group ID
	 * @param scope the scope
	 * @return the matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByG_S(long groupId, String scope) {
		return findByG_S(
			groupId, scope, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder apps where groupId = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param scope the scope
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByG_S(
		long groupId, String scope, int start, int end) {

		return findByG_S(groupId, scope, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder apps where groupId = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param scope the scope
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByG_S(
		long groupId, String scope, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return findByG_S(groupId, scope, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder apps where groupId = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param scope the scope
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByG_S(
		long groupId, String scope, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator,
		boolean useFinderCache) {

		scope = Objects.toString(scope, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_S;
				finderArgs = new Object[] {groupId, scope};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_S;
			finderArgs = new Object[] {
				groupId, scope, start, end, orderByComparator
			};
		}

		List<AppBuilderApp> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderApp>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderApp appBuilderApp : list) {
					if ((groupId != appBuilderApp.getGroupId()) ||
						!scope.equals(appBuilderApp.getScope())) {

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

			sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			boolean bindScope = false;

			if (scope.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_S_SCOPE_3);
			}
			else {
				bindScope = true;

				sb.append(_FINDER_COLUMN_G_S_SCOPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindScope) {
					queryPos.add(scope);
				}

				list = (List<AppBuilderApp>)QueryUtil.list(
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
	 * Returns the first app builder app in the ordered set where groupId = &#63; and scope = &#63;.
	 *
	 * @param groupId the group ID
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByG_S_First(
			long groupId, String scope,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByG_S_First(
			groupId, scope, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", scope=");
		sb.append(scope);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the first app builder app in the ordered set where groupId = &#63; and scope = &#63;.
	 *
	 * @param groupId the group ID
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByG_S_First(
		long groupId, String scope,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		List<AppBuilderApp> list = findByG_S(
			groupId, scope, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app in the ordered set where groupId = &#63; and scope = &#63;.
	 *
	 * @param groupId the group ID
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByG_S_Last(
			long groupId, String scope,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByG_S_Last(
			groupId, scope, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", scope=");
		sb.append(scope);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the last app builder app in the ordered set where groupId = &#63; and scope = &#63;.
	 *
	 * @param groupId the group ID
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByG_S_Last(
		long groupId, String scope,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		int count = countByG_S(groupId, scope);

		if (count == 0) {
			return null;
		}

		List<AppBuilderApp> list = findByG_S(
			groupId, scope, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where groupId = &#63; and scope = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param groupId the group ID
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp[] findByG_S_PrevAndNext(
			long appBuilderAppId, long groupId, String scope,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		scope = Objects.toString(scope, "");

		AppBuilderApp appBuilderApp = findByPrimaryKey(appBuilderAppId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderApp[] array = new AppBuilderAppImpl[3];

			array[0] = getByG_S_PrevAndNext(
				session, appBuilderApp, groupId, scope, orderByComparator,
				true);

			array[1] = appBuilderApp;

			array[2] = getByG_S_PrevAndNext(
				session, appBuilderApp, groupId, scope, orderByComparator,
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

	protected AppBuilderApp getByG_S_PrevAndNext(
		Session session, AppBuilderApp appBuilderApp, long groupId,
		String scope, OrderByComparator<AppBuilderApp> orderByComparator,
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

		sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		boolean bindScope = false;

		if (scope.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_S_SCOPE_3);
		}
		else {
			bindScope = true;

			sb.append(_FINDER_COLUMN_G_S_SCOPE_2);
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
			sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindScope) {
			queryPos.add(scope);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						appBuilderApp)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderApp> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the app builder apps that the user has permission to view where groupId = &#63; and scope = &#63;.
	 *
	 * @param groupId the group ID
	 * @param scope the scope
	 * @return the matching app builder apps that the user has permission to view
	 */
	@Override
	public List<AppBuilderApp> filterFindByG_S(long groupId, String scope) {
		return filterFindByG_S(
			groupId, scope, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder apps that the user has permission to view where groupId = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param scope the scope
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps that the user has permission to view
	 */
	@Override
	public List<AppBuilderApp> filterFindByG_S(
		long groupId, String scope, int start, int end) {

		return filterFindByG_S(groupId, scope, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder apps that the user has permissions to view where groupId = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param scope the scope
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps that the user has permission to view
	 */
	@Override
	public List<AppBuilderApp> filterFindByG_S(
		long groupId, String scope, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S(groupId, scope, start, end, orderByComparator);
		}

		scope = Objects.toString(scope, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_APPBUILDERAPP_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_APPBUILDERAPP_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		boolean bindScope = false;

		if (scope.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_S_SCOPE_3);
		}
		else {
			bindScope = true;

			sb.append(_FINDER_COLUMN_G_S_SCOPE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_APPBUILDERAPP_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AppBuilderAppModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AppBuilderApp.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AppBuilderAppImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AppBuilderAppImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindScope) {
				queryPos.add(scope);
			}

			return (List<AppBuilderApp>)QueryUtil.list(
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
	 * Returns the app builder apps before and after the current app builder app in the ordered set of app builder apps that the user has permission to view where groupId = &#63; and scope = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param groupId the group ID
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp[] filterFindByG_S_PrevAndNext(
			long appBuilderAppId, long groupId, String scope,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S_PrevAndNext(
				appBuilderAppId, groupId, scope, orderByComparator);
		}

		scope = Objects.toString(scope, "");

		AppBuilderApp appBuilderApp = findByPrimaryKey(appBuilderAppId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderApp[] array = new AppBuilderAppImpl[3];

			array[0] = filterGetByG_S_PrevAndNext(
				session, appBuilderApp, groupId, scope, orderByComparator,
				true);

			array[1] = appBuilderApp;

			array[2] = filterGetByG_S_PrevAndNext(
				session, appBuilderApp, groupId, scope, orderByComparator,
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

	protected AppBuilderApp filterGetByG_S_PrevAndNext(
		Session session, AppBuilderApp appBuilderApp, long groupId,
		String scope, OrderByComparator<AppBuilderApp> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_APPBUILDERAPP_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_APPBUILDERAPP_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		boolean bindScope = false;

		if (scope.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_S_SCOPE_3);
		}
		else {
			bindScope = true;

			sb.append(_FINDER_COLUMN_G_S_SCOPE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_APPBUILDERAPP_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AppBuilderAppModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AppBuilderApp.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, AppBuilderAppImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, AppBuilderAppImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (bindScope) {
			queryPos.add(scope);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						appBuilderApp)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderApp> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder apps where groupId = &#63; and scope = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param scope the scope
	 */
	@Override
	public void removeByG_S(long groupId, String scope) {
		for (AppBuilderApp appBuilderApp :
				findByG_S(
					groupId, scope, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(appBuilderApp);
		}
	}

	/**
	 * Returns the number of app builder apps where groupId = &#63; and scope = &#63;.
	 *
	 * @param groupId the group ID
	 * @param scope the scope
	 * @return the number of matching app builder apps
	 */
	@Override
	public int countByG_S(long groupId, String scope) {
		scope = Objects.toString(scope, "");

		FinderPath finderPath = _finderPathCountByG_S;

		Object[] finderArgs = new Object[] {groupId, scope};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_APPBUILDERAPP_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			boolean bindScope = false;

			if (scope.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_S_SCOPE_3);
			}
			else {
				bindScope = true;

				sb.append(_FINDER_COLUMN_G_S_SCOPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindScope) {
					queryPos.add(scope);
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
	 * Returns the number of app builder apps that the user has permission to view where groupId = &#63; and scope = &#63;.
	 *
	 * @param groupId the group ID
	 * @param scope the scope
	 * @return the number of matching app builder apps that the user has permission to view
	 */
	@Override
	public int filterCountByG_S(long groupId, String scope) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_S(groupId, scope);
		}

		scope = Objects.toString(scope, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_APPBUILDERAPP_WHERE);

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		boolean bindScope = false;

		if (scope.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_S_SCOPE_3);
		}
		else {
			bindScope = true;

			sb.append(_FINDER_COLUMN_G_S_SCOPE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AppBuilderApp.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindScope) {
				queryPos.add(scope);
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

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 =
		"appBuilderApp.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_SCOPE_2 =
		"appBuilderApp.scope = ?";

	private static final String _FINDER_COLUMN_G_S_SCOPE_3 =
		"(appBuilderApp.scope IS NULL OR appBuilderApp.scope = '')";

	private FinderPath _finderPathWithPaginationFindByC_A;
	private FinderPath _finderPathWithoutPaginationFindByC_A;
	private FinderPath _finderPathCountByC_A;

	/**
	 * Returns all the app builder apps where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByC_A(long companyId, boolean active) {
		return findByC_A(
			companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder apps where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByC_A(
		long companyId, boolean active, int start, int end) {

		return findByC_A(companyId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder apps where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return findByC_A(
			companyId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder apps where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_A;
				finderArgs = new Object[] {companyId, active};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_A;
			finderArgs = new Object[] {
				companyId, active, start, end, orderByComparator
			};
		}

		List<AppBuilderApp> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderApp>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderApp appBuilderApp : list) {
					if ((companyId != appBuilderApp.getCompanyId()) ||
						(active != appBuilderApp.isActive())) {

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

			sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

			sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				list = (List<AppBuilderApp>)QueryUtil.list(
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
	 * Returns the first app builder app in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByC_A_First(
			long companyId, boolean active,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByC_A_First(
			companyId, active, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the first app builder app in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByC_A_First(
		long companyId, boolean active,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		List<AppBuilderApp> list = findByC_A(
			companyId, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByC_A_Last(
			long companyId, boolean active,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByC_A_Last(
			companyId, active, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the last app builder app in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByC_A_Last(
		long companyId, boolean active,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		int count = countByC_A(companyId, active);

		if (count == 0) {
			return null;
		}

		List<AppBuilderApp> list = findByC_A(
			companyId, active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp[] findByC_A_PrevAndNext(
			long appBuilderAppId, long companyId, boolean active,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = findByPrimaryKey(appBuilderAppId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderApp[] array = new AppBuilderAppImpl[3];

			array[0] = getByC_A_PrevAndNext(
				session, appBuilderApp, companyId, active, orderByComparator,
				true);

			array[1] = appBuilderApp;

			array[2] = getByC_A_PrevAndNext(
				session, appBuilderApp, companyId, active, orderByComparator,
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

	protected AppBuilderApp getByC_A_PrevAndNext(
		Session session, AppBuilderApp appBuilderApp, long companyId,
		boolean active, OrderByComparator<AppBuilderApp> orderByComparator,
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

		sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

		sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

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
			sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						appBuilderApp)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderApp> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder apps where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	@Override
	public void removeByC_A(long companyId, boolean active) {
		for (AppBuilderApp appBuilderApp :
				findByC_A(
					companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(appBuilderApp);
		}
	}

	/**
	 * Returns the number of app builder apps where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching app builder apps
	 */
	@Override
	public int countByC_A(long companyId, boolean active) {
		FinderPath finderPath = _finderPathCountByC_A;

		Object[] finderArgs = new Object[] {companyId, active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_APPBUILDERAPP_WHERE);

			sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

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

	private static final String _FINDER_COLUMN_C_A_COMPANYID_2 =
		"appBuilderApp.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_ACTIVE_2 =
		"appBuilderApp.active = ?";

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;

	/**
	 * Returns all the app builder apps where companyId = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @return the matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByC_S(long companyId, String scope) {
		return findByC_S(
			companyId, scope, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder apps where companyId = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByC_S(
		long companyId, String scope, int start, int end) {

		return findByC_S(companyId, scope, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder apps where companyId = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByC_S(
		long companyId, String scope, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return findByC_S(companyId, scope, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder apps where companyId = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByC_S(
		long companyId, String scope, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator,
		boolean useFinderCache) {

		scope = Objects.toString(scope, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_S;
				finderArgs = new Object[] {companyId, scope};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_S;
			finderArgs = new Object[] {
				companyId, scope, start, end, orderByComparator
			};
		}

		List<AppBuilderApp> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderApp>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderApp appBuilderApp : list) {
					if ((companyId != appBuilderApp.getCompanyId()) ||
						!scope.equals(appBuilderApp.getScope())) {

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

			sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			boolean bindScope = false;

			if (scope.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_S_SCOPE_3);
			}
			else {
				bindScope = true;

				sb.append(_FINDER_COLUMN_C_S_SCOPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindScope) {
					queryPos.add(scope);
				}

				list = (List<AppBuilderApp>)QueryUtil.list(
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
	 * Returns the first app builder app in the ordered set where companyId = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByC_S_First(
			long companyId, String scope,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByC_S_First(
			companyId, scope, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", scope=");
		sb.append(scope);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the first app builder app in the ordered set where companyId = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByC_S_First(
		long companyId, String scope,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		List<AppBuilderApp> list = findByC_S(
			companyId, scope, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app in the ordered set where companyId = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByC_S_Last(
			long companyId, String scope,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByC_S_Last(
			companyId, scope, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", scope=");
		sb.append(scope);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the last app builder app in the ordered set where companyId = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByC_S_Last(
		long companyId, String scope,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		int count = countByC_S(companyId, scope);

		if (count == 0) {
			return null;
		}

		List<AppBuilderApp> list = findByC_S(
			companyId, scope, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where companyId = &#63; and scope = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp[] findByC_S_PrevAndNext(
			long appBuilderAppId, long companyId, String scope,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		scope = Objects.toString(scope, "");

		AppBuilderApp appBuilderApp = findByPrimaryKey(appBuilderAppId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderApp[] array = new AppBuilderAppImpl[3];

			array[0] = getByC_S_PrevAndNext(
				session, appBuilderApp, companyId, scope, orderByComparator,
				true);

			array[1] = appBuilderApp;

			array[2] = getByC_S_PrevAndNext(
				session, appBuilderApp, companyId, scope, orderByComparator,
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

	protected AppBuilderApp getByC_S_PrevAndNext(
		Session session, AppBuilderApp appBuilderApp, long companyId,
		String scope, OrderByComparator<AppBuilderApp> orderByComparator,
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

		sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

		sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		boolean bindScope = false;

		if (scope.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_S_SCOPE_3);
		}
		else {
			bindScope = true;

			sb.append(_FINDER_COLUMN_C_S_SCOPE_2);
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
			sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindScope) {
			queryPos.add(scope);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						appBuilderApp)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderApp> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder apps where companyId = &#63; and scope = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 */
	@Override
	public void removeByC_S(long companyId, String scope) {
		for (AppBuilderApp appBuilderApp :
				findByC_S(
					companyId, scope, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(appBuilderApp);
		}
	}

	/**
	 * Returns the number of app builder apps where companyId = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @return the number of matching app builder apps
	 */
	@Override
	public int countByC_S(long companyId, String scope) {
		scope = Objects.toString(scope, "");

		FinderPath finderPath = _finderPathCountByC_S;

		Object[] finderArgs = new Object[] {companyId, scope};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_APPBUILDERAPP_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			boolean bindScope = false;

			if (scope.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_S_SCOPE_3);
			}
			else {
				bindScope = true;

				sb.append(_FINDER_COLUMN_C_S_SCOPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindScope) {
					queryPos.add(scope);
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

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 =
		"appBuilderApp.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_SCOPE_2 =
		"appBuilderApp.scope = ?";

	private static final String _FINDER_COLUMN_C_S_SCOPE_3 =
		"(appBuilderApp.scope IS NULL OR appBuilderApp.scope = '')";

	private FinderPath _finderPathWithPaginationFindByG_C_D;
	private FinderPath _finderPathWithoutPaginationFindByG_C_D;
	private FinderPath _finderPathCountByG_C_D;

	/**
	 * Returns all the app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @return the matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByG_C_D(
		long groupId, long companyId, long ddmStructureId) {

		return findByG_C_D(
			groupId, companyId, ddmStructureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end) {

		return findByG_C_D(
			groupId, companyId, ddmStructureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return findByG_C_D(
			groupId, companyId, ddmStructureId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C_D;
				finderArgs = new Object[] {groupId, companyId, ddmStructureId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C_D;
			finderArgs = new Object[] {
				groupId, companyId, ddmStructureId, start, end,
				orderByComparator
			};
		}

		List<AppBuilderApp> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderApp>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderApp appBuilderApp : list) {
					if ((groupId != appBuilderApp.getGroupId()) ||
						(companyId != appBuilderApp.getCompanyId()) ||
						(ddmStructureId != appBuilderApp.getDdmStructureId())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

			sb.append(_FINDER_COLUMN_G_C_D_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_D_COMPANYID_2);

			sb.append(_FINDER_COLUMN_G_C_D_DDMSTRUCTUREID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(companyId);

				queryPos.add(ddmStructureId);

				list = (List<AppBuilderApp>)QueryUtil.list(
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
	 * Returns the first app builder app in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByG_C_D_First(
			long groupId, long companyId, long ddmStructureId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByG_C_D_First(
			groupId, companyId, ddmStructureId, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", ddmStructureId=");
		sb.append(ddmStructureId);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the first app builder app in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByG_C_D_First(
		long groupId, long companyId, long ddmStructureId,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		List<AppBuilderApp> list = findByG_C_D(
			groupId, companyId, ddmStructureId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByG_C_D_Last(
			long groupId, long companyId, long ddmStructureId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByG_C_D_Last(
			groupId, companyId, ddmStructureId, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", ddmStructureId=");
		sb.append(ddmStructureId);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the last app builder app in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByG_C_D_Last(
		long groupId, long companyId, long ddmStructureId,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		int count = countByG_C_D(groupId, companyId, ddmStructureId);

		if (count == 0) {
			return null;
		}

		List<AppBuilderApp> list = findByG_C_D(
			groupId, companyId, ddmStructureId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp[] findByG_C_D_PrevAndNext(
			long appBuilderAppId, long groupId, long companyId,
			long ddmStructureId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = findByPrimaryKey(appBuilderAppId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderApp[] array = new AppBuilderAppImpl[3];

			array[0] = getByG_C_D_PrevAndNext(
				session, appBuilderApp, groupId, companyId, ddmStructureId,
				orderByComparator, true);

			array[1] = appBuilderApp;

			array[2] = getByG_C_D_PrevAndNext(
				session, appBuilderApp, groupId, companyId, ddmStructureId,
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

	protected AppBuilderApp getByG_C_D_PrevAndNext(
		Session session, AppBuilderApp appBuilderApp, long groupId,
		long companyId, long ddmStructureId,
		OrderByComparator<AppBuilderApp> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

		sb.append(_FINDER_COLUMN_G_C_D_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_D_COMPANYID_2);

		sb.append(_FINDER_COLUMN_G_C_D_DDMSTRUCTUREID_2);

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
			sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(companyId);

		queryPos.add(ddmStructureId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						appBuilderApp)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderApp> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the app builder apps that the user has permission to view where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @return the matching app builder apps that the user has permission to view
	 */
	@Override
	public List<AppBuilderApp> filterFindByG_C_D(
		long groupId, long companyId, long ddmStructureId) {

		return filterFindByG_C_D(
			groupId, companyId, ddmStructureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder apps that the user has permission to view where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps that the user has permission to view
	 */
	@Override
	public List<AppBuilderApp> filterFindByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end) {

		return filterFindByG_C_D(
			groupId, companyId, ddmStructureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder apps that the user has permissions to view where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps that the user has permission to view
	 */
	@Override
	public List<AppBuilderApp> filterFindByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_D(
				groupId, companyId, ddmStructureId, start, end,
				orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_APPBUILDERAPP_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_APPBUILDERAPP_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_D_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_D_COMPANYID_2);

		sb.append(_FINDER_COLUMN_G_C_D_DDMSTRUCTUREID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_APPBUILDERAPP_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AppBuilderAppModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AppBuilderApp.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AppBuilderAppImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AppBuilderAppImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(companyId);

			queryPos.add(ddmStructureId);

			return (List<AppBuilderApp>)QueryUtil.list(
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
	 * Returns the app builder apps before and after the current app builder app in the ordered set of app builder apps that the user has permission to view where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp[] filterFindByG_C_D_PrevAndNext(
			long appBuilderAppId, long groupId, long companyId,
			long ddmStructureId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_D_PrevAndNext(
				appBuilderAppId, groupId, companyId, ddmStructureId,
				orderByComparator);
		}

		AppBuilderApp appBuilderApp = findByPrimaryKey(appBuilderAppId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderApp[] array = new AppBuilderAppImpl[3];

			array[0] = filterGetByG_C_D_PrevAndNext(
				session, appBuilderApp, groupId, companyId, ddmStructureId,
				orderByComparator, true);

			array[1] = appBuilderApp;

			array[2] = filterGetByG_C_D_PrevAndNext(
				session, appBuilderApp, groupId, companyId, ddmStructureId,
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

	protected AppBuilderApp filterGetByG_C_D_PrevAndNext(
		Session session, AppBuilderApp appBuilderApp, long groupId,
		long companyId, long ddmStructureId,
		OrderByComparator<AppBuilderApp> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_APPBUILDERAPP_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_APPBUILDERAPP_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_D_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_D_COMPANYID_2);

		sb.append(_FINDER_COLUMN_G_C_D_DDMSTRUCTUREID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_APPBUILDERAPP_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AppBuilderAppModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AppBuilderApp.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, AppBuilderAppImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, AppBuilderAppImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(companyId);

		queryPos.add(ddmStructureId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						appBuilderApp)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderApp> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 */
	@Override
	public void removeByG_C_D(
		long groupId, long companyId, long ddmStructureId) {

		for (AppBuilderApp appBuilderApp :
				findByG_C_D(
					groupId, companyId, ddmStructureId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(appBuilderApp);
		}
	}

	/**
	 * Returns the number of app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @return the number of matching app builder apps
	 */
	@Override
	public int countByG_C_D(long groupId, long companyId, long ddmStructureId) {
		FinderPath finderPath = _finderPathCountByG_C_D;

		Object[] finderArgs = new Object[] {groupId, companyId, ddmStructureId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_APPBUILDERAPP_WHERE);

			sb.append(_FINDER_COLUMN_G_C_D_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_D_COMPANYID_2);

			sb.append(_FINDER_COLUMN_G_C_D_DDMSTRUCTUREID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(companyId);

				queryPos.add(ddmStructureId);

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
	 * Returns the number of app builder apps that the user has permission to view where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @return the number of matching app builder apps that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_D(
		long groupId, long companyId, long ddmStructureId) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_D(groupId, companyId, ddmStructureId);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_APPBUILDERAPP_WHERE);

		sb.append(_FINDER_COLUMN_G_C_D_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_D_COMPANYID_2);

		sb.append(_FINDER_COLUMN_G_C_D_DDMSTRUCTUREID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AppBuilderApp.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(companyId);

			queryPos.add(ddmStructureId);

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

	private static final String _FINDER_COLUMN_G_C_D_GROUPID_2 =
		"appBuilderApp.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_D_COMPANYID_2 =
		"appBuilderApp.companyId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_D_DDMSTRUCTUREID_2 =
		"appBuilderApp.ddmStructureId = ?";

	private FinderPath _finderPathWithPaginationFindByC_A_S;
	private FinderPath _finderPathWithoutPaginationFindByC_A_S;
	private FinderPath _finderPathCountByC_A_S;

	/**
	 * Returns all the app builder apps where companyId = &#63; and active = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param scope the scope
	 * @return the matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByC_A_S(
		long companyId, boolean active, String scope) {

		return findByC_A_S(
			companyId, active, scope, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the app builder apps where companyId = &#63; and active = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param scope the scope
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByC_A_S(
		long companyId, boolean active, String scope, int start, int end) {

		return findByC_A_S(companyId, active, scope, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder apps where companyId = &#63; and active = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param scope the scope
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByC_A_S(
		long companyId, boolean active, String scope, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return findByC_A_S(
			companyId, active, scope, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder apps where companyId = &#63; and active = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param scope the scope
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	@Override
	public List<AppBuilderApp> findByC_A_S(
		long companyId, boolean active, String scope, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator,
		boolean useFinderCache) {

		scope = Objects.toString(scope, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_A_S;
				finderArgs = new Object[] {companyId, active, scope};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_A_S;
			finderArgs = new Object[] {
				companyId, active, scope, start, end, orderByComparator
			};
		}

		List<AppBuilderApp> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderApp>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderApp appBuilderApp : list) {
					if ((companyId != appBuilderApp.getCompanyId()) ||
						(active != appBuilderApp.isActive()) ||
						!scope.equals(appBuilderApp.getScope())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

			sb.append(_FINDER_COLUMN_C_A_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_S_ACTIVE_2);

			boolean bindScope = false;

			if (scope.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_A_S_SCOPE_3);
			}
			else {
				bindScope = true;

				sb.append(_FINDER_COLUMN_C_A_S_SCOPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				if (bindScope) {
					queryPos.add(scope);
				}

				list = (List<AppBuilderApp>)QueryUtil.list(
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
	 * Returns the first app builder app in the ordered set where companyId = &#63; and active = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByC_A_S_First(
			long companyId, boolean active, String scope,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByC_A_S_First(
			companyId, active, scope, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", scope=");
		sb.append(scope);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the first app builder app in the ordered set where companyId = &#63; and active = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByC_A_S_First(
		long companyId, boolean active, String scope,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		List<AppBuilderApp> list = findByC_A_S(
			companyId, active, scope, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app in the ordered set where companyId = &#63; and active = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp findByC_A_S_Last(
			long companyId, boolean active, String scope,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByC_A_S_Last(
			companyId, active, scope, orderByComparator);

		if (appBuilderApp != null) {
			return appBuilderApp;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", scope=");
		sb.append(scope);

		sb.append("}");

		throw new NoSuchAppException(sb.toString());
	}

	/**
	 * Returns the last app builder app in the ordered set where companyId = &#63; and active = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public AppBuilderApp fetchByC_A_S_Last(
		long companyId, boolean active, String scope,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		int count = countByC_A_S(companyId, active, scope);

		if (count == 0) {
			return null;
		}

		List<AppBuilderApp> list = findByC_A_S(
			companyId, active, scope, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where companyId = &#63; and active = &#63; and scope = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param companyId the company ID
	 * @param active the active
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp[] findByC_A_S_PrevAndNext(
			long appBuilderAppId, long companyId, boolean active, String scope,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws NoSuchAppException {

		scope = Objects.toString(scope, "");

		AppBuilderApp appBuilderApp = findByPrimaryKey(appBuilderAppId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderApp[] array = new AppBuilderAppImpl[3];

			array[0] = getByC_A_S_PrevAndNext(
				session, appBuilderApp, companyId, active, scope,
				orderByComparator, true);

			array[1] = appBuilderApp;

			array[2] = getByC_A_S_PrevAndNext(
				session, appBuilderApp, companyId, active, scope,
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

	protected AppBuilderApp getByC_A_S_PrevAndNext(
		Session session, AppBuilderApp appBuilderApp, long companyId,
		boolean active, String scope,
		OrderByComparator<AppBuilderApp> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_APPBUILDERAPP_WHERE);

		sb.append(_FINDER_COLUMN_C_A_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_S_ACTIVE_2);

		boolean bindScope = false;

		if (scope.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_A_S_SCOPE_3);
		}
		else {
			bindScope = true;

			sb.append(_FINDER_COLUMN_C_A_S_SCOPE_2);
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
			sb.append(AppBuilderAppModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(active);

		if (bindScope) {
			queryPos.add(scope);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						appBuilderApp)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderApp> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder apps where companyId = &#63; and active = &#63; and scope = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param scope the scope
	 */
	@Override
	public void removeByC_A_S(long companyId, boolean active, String scope) {
		for (AppBuilderApp appBuilderApp :
				findByC_A_S(
					companyId, active, scope, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(appBuilderApp);
		}
	}

	/**
	 * Returns the number of app builder apps where companyId = &#63; and active = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param scope the scope
	 * @return the number of matching app builder apps
	 */
	@Override
	public int countByC_A_S(long companyId, boolean active, String scope) {
		scope = Objects.toString(scope, "");

		FinderPath finderPath = _finderPathCountByC_A_S;

		Object[] finderArgs = new Object[] {companyId, active, scope};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_APPBUILDERAPP_WHERE);

			sb.append(_FINDER_COLUMN_C_A_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_S_ACTIVE_2);

			boolean bindScope = false;

			if (scope.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_A_S_SCOPE_3);
			}
			else {
				bindScope = true;

				sb.append(_FINDER_COLUMN_C_A_S_SCOPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				if (bindScope) {
					queryPos.add(scope);
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

	private static final String _FINDER_COLUMN_C_A_S_COMPANYID_2 =
		"appBuilderApp.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_S_ACTIVE_2 =
		"appBuilderApp.active = ? AND ";

	private static final String _FINDER_COLUMN_C_A_S_SCOPE_2 =
		"appBuilderApp.scope = ?";

	private static final String _FINDER_COLUMN_C_A_S_SCOPE_3 =
		"(appBuilderApp.scope IS NULL OR appBuilderApp.scope = '')";

	public AppBuilderAppPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AppBuilderApp.class);

		setModelImplClass(AppBuilderAppImpl.class);
		setModelPKClass(long.class);

		setTable(AppBuilderAppTable.INSTANCE);
	}

	/**
	 * Caches the app builder app in the entity cache if it is enabled.
	 *
	 * @param appBuilderApp the app builder app
	 */
	@Override
	public void cacheResult(AppBuilderApp appBuilderApp) {
		entityCache.putResult(
			AppBuilderAppImpl.class, appBuilderApp.getPrimaryKey(),
			appBuilderApp);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {appBuilderApp.getUuid(), appBuilderApp.getGroupId()},
			appBuilderApp);

		appBuilderApp.resetOriginalValues();
	}

	/**
	 * Caches the app builder apps in the entity cache if it is enabled.
	 *
	 * @param appBuilderApps the app builder apps
	 */
	@Override
	public void cacheResult(List<AppBuilderApp> appBuilderApps) {
		for (AppBuilderApp appBuilderApp : appBuilderApps) {
			if (entityCache.getResult(
					AppBuilderAppImpl.class, appBuilderApp.getPrimaryKey()) ==
						null) {

				cacheResult(appBuilderApp);
			}
			else {
				appBuilderApp.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all app builder apps.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AppBuilderAppImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the app builder app.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AppBuilderApp appBuilderApp) {
		entityCache.removeResult(
			AppBuilderAppImpl.class, appBuilderApp.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AppBuilderAppModelImpl)appBuilderApp, true);
	}

	@Override
	public void clearCache(List<AppBuilderApp> appBuilderApps) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AppBuilderApp appBuilderApp : appBuilderApps) {
			entityCache.removeResult(
				AppBuilderAppImpl.class, appBuilderApp.getPrimaryKey());

			clearUniqueFindersCache(
				(AppBuilderAppModelImpl)appBuilderApp, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(AppBuilderAppImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AppBuilderAppModelImpl appBuilderAppModelImpl) {

		Object[] args = new Object[] {
			appBuilderAppModelImpl.getUuid(),
			appBuilderAppModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, appBuilderAppModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AppBuilderAppModelImpl appBuilderAppModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				appBuilderAppModelImpl.getUuid(),
				appBuilderAppModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((appBuilderAppModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				appBuilderAppModelImpl.getOriginalUuid(),
				appBuilderAppModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}
	}

	/**
	 * Creates a new app builder app with the primary key. Does not add the app builder app to the database.
	 *
	 * @param appBuilderAppId the primary key for the new app builder app
	 * @return the new app builder app
	 */
	@Override
	public AppBuilderApp create(long appBuilderAppId) {
		AppBuilderApp appBuilderApp = new AppBuilderAppImpl();

		appBuilderApp.setNew(true);
		appBuilderApp.setPrimaryKey(appBuilderAppId);

		String uuid = PortalUUIDUtil.generate();

		appBuilderApp.setUuid(uuid);

		appBuilderApp.setCompanyId(CompanyThreadLocal.getCompanyId());

		return appBuilderApp;
	}

	/**
	 * Removes the app builder app with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppId the primary key of the app builder app
	 * @return the app builder app that was removed
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp remove(long appBuilderAppId)
		throws NoSuchAppException {

		return remove((Serializable)appBuilderAppId);
	}

	/**
	 * Removes the app builder app with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the app builder app
	 * @return the app builder app that was removed
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp remove(Serializable primaryKey)
		throws NoSuchAppException {

		Session session = null;

		try {
			session = openSession();

			AppBuilderApp appBuilderApp = (AppBuilderApp)session.get(
				AppBuilderAppImpl.class, primaryKey);

			if (appBuilderApp == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAppException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(appBuilderApp);
		}
		catch (NoSuchAppException noSuchEntityException) {
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
	protected AppBuilderApp removeImpl(AppBuilderApp appBuilderApp) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(appBuilderApp)) {
				appBuilderApp = (AppBuilderApp)session.get(
					AppBuilderAppImpl.class, appBuilderApp.getPrimaryKeyObj());
			}

			if (appBuilderApp != null) {
				session.delete(appBuilderApp);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (appBuilderApp != null) {
			clearCache(appBuilderApp);
		}

		return appBuilderApp;
	}

	@Override
	public AppBuilderApp updateImpl(AppBuilderApp appBuilderApp) {
		boolean isNew = appBuilderApp.isNew();

		if (!(appBuilderApp instanceof AppBuilderAppModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(appBuilderApp.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					appBuilderApp);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in appBuilderApp proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AppBuilderApp implementation " +
					appBuilderApp.getClass());
		}

		AppBuilderAppModelImpl appBuilderAppModelImpl =
			(AppBuilderAppModelImpl)appBuilderApp;

		if (Validator.isNull(appBuilderApp.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			appBuilderApp.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (appBuilderApp.getCreateDate() == null)) {
			if (serviceContext == null) {
				appBuilderApp.setCreateDate(now);
			}
			else {
				appBuilderApp.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!appBuilderAppModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				appBuilderApp.setModifiedDate(now);
			}
			else {
				appBuilderApp.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (appBuilderApp.isNew()) {
				session.save(appBuilderApp);

				appBuilderApp.setNew(false);
			}
			else {
				appBuilderApp = (AppBuilderApp)session.merge(appBuilderApp);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {appBuilderAppModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				appBuilderAppModelImpl.getUuid(),
				appBuilderAppModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {appBuilderAppModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {appBuilderAppModelImpl.getCompanyId()};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {appBuilderAppModelImpl.getDdmStructureId()};

			finderCache.removeResult(_finderPathCountByDDMStructureId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByDDMStructureId, args);

			args = new Object[] {
				appBuilderAppModelImpl.getGroupId(),
				appBuilderAppModelImpl.getScope()
			};

			finderCache.removeResult(_finderPathCountByG_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_S, args);

			args = new Object[] {
				appBuilderAppModelImpl.getCompanyId(),
				appBuilderAppModelImpl.isActive()
			};

			finderCache.removeResult(_finderPathCountByC_A, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_A, args);

			args = new Object[] {
				appBuilderAppModelImpl.getCompanyId(),
				appBuilderAppModelImpl.getScope()
			};

			finderCache.removeResult(_finderPathCountByC_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_S, args);

			args = new Object[] {
				appBuilderAppModelImpl.getGroupId(),
				appBuilderAppModelImpl.getCompanyId(),
				appBuilderAppModelImpl.getDdmStructureId()
			};

			finderCache.removeResult(_finderPathCountByG_C_D, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C_D, args);

			args = new Object[] {
				appBuilderAppModelImpl.getCompanyId(),
				appBuilderAppModelImpl.isActive(),
				appBuilderAppModelImpl.getScope()
			};

			finderCache.removeResult(_finderPathCountByC_A_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_A_S, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((appBuilderAppModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					appBuilderAppModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {appBuilderAppModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((appBuilderAppModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					appBuilderAppModelImpl.getOriginalUuid(),
					appBuilderAppModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					appBuilderAppModelImpl.getUuid(),
					appBuilderAppModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((appBuilderAppModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					appBuilderAppModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {appBuilderAppModelImpl.getGroupId()};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((appBuilderAppModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					appBuilderAppModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {appBuilderAppModelImpl.getCompanyId()};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((appBuilderAppModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByDDMStructureId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					appBuilderAppModelImpl.getOriginalDdmStructureId()
				};

				finderCache.removeResult(
					_finderPathCountByDDMStructureId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByDDMStructureId, args);

				args = new Object[] {
					appBuilderAppModelImpl.getDdmStructureId()
				};

				finderCache.removeResult(
					_finderPathCountByDDMStructureId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByDDMStructureId, args);
			}

			if ((appBuilderAppModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					appBuilderAppModelImpl.getOriginalGroupId(),
					appBuilderAppModelImpl.getOriginalScope()
				};

				finderCache.removeResult(_finderPathCountByG_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_S, args);

				args = new Object[] {
					appBuilderAppModelImpl.getGroupId(),
					appBuilderAppModelImpl.getScope()
				};

				finderCache.removeResult(_finderPathCountByG_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_S, args);
			}

			if ((appBuilderAppModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_A.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					appBuilderAppModelImpl.getOriginalCompanyId(),
					appBuilderAppModelImpl.getOriginalActive()
				};

				finderCache.removeResult(_finderPathCountByC_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_A, args);

				args = new Object[] {
					appBuilderAppModelImpl.getCompanyId(),
					appBuilderAppModelImpl.isActive()
				};

				finderCache.removeResult(_finderPathCountByC_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_A, args);
			}

			if ((appBuilderAppModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					appBuilderAppModelImpl.getOriginalCompanyId(),
					appBuilderAppModelImpl.getOriginalScope()
				};

				finderCache.removeResult(_finderPathCountByC_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_S, args);

				args = new Object[] {
					appBuilderAppModelImpl.getCompanyId(),
					appBuilderAppModelImpl.getScope()
				};

				finderCache.removeResult(_finderPathCountByC_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_S, args);
			}

			if ((appBuilderAppModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_D.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					appBuilderAppModelImpl.getOriginalGroupId(),
					appBuilderAppModelImpl.getOriginalCompanyId(),
					appBuilderAppModelImpl.getOriginalDdmStructureId()
				};

				finderCache.removeResult(_finderPathCountByG_C_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_D, args);

				args = new Object[] {
					appBuilderAppModelImpl.getGroupId(),
					appBuilderAppModelImpl.getCompanyId(),
					appBuilderAppModelImpl.getDdmStructureId()
				};

				finderCache.removeResult(_finderPathCountByG_C_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_D, args);
			}

			if ((appBuilderAppModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_A_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					appBuilderAppModelImpl.getOriginalCompanyId(),
					appBuilderAppModelImpl.getOriginalActive(),
					appBuilderAppModelImpl.getOriginalScope()
				};

				finderCache.removeResult(_finderPathCountByC_A_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_A_S, args);

				args = new Object[] {
					appBuilderAppModelImpl.getCompanyId(),
					appBuilderAppModelImpl.isActive(),
					appBuilderAppModelImpl.getScope()
				};

				finderCache.removeResult(_finderPathCountByC_A_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_A_S, args);
			}
		}

		entityCache.putResult(
			AppBuilderAppImpl.class, appBuilderApp.getPrimaryKey(),
			appBuilderApp, false);

		clearUniqueFindersCache(appBuilderAppModelImpl, false);
		cacheUniqueFindersCache(appBuilderAppModelImpl);

		appBuilderApp.resetOriginalValues();

		return appBuilderApp;
	}

	/**
	 * Returns the app builder app with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the app builder app
	 * @return the app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAppException {

		AppBuilderApp appBuilderApp = fetchByPrimaryKey(primaryKey);

		if (appBuilderApp == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAppException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return appBuilderApp;
	}

	/**
	 * Returns the app builder app with the primary key or throws a <code>NoSuchAppException</code> if it could not be found.
	 *
	 * @param appBuilderAppId the primary key of the app builder app
	 * @return the app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp findByPrimaryKey(long appBuilderAppId)
		throws NoSuchAppException {

		return findByPrimaryKey((Serializable)appBuilderAppId);
	}

	/**
	 * Returns the app builder app with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param appBuilderAppId the primary key of the app builder app
	 * @return the app builder app, or <code>null</code> if a app builder app with the primary key could not be found
	 */
	@Override
	public AppBuilderApp fetchByPrimaryKey(long appBuilderAppId) {
		return fetchByPrimaryKey((Serializable)appBuilderAppId);
	}

	/**
	 * Returns all the app builder apps.
	 *
	 * @return the app builder apps
	 */
	@Override
	public List<AppBuilderApp> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder apps.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of app builder apps
	 */
	@Override
	public List<AppBuilderApp> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder apps.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of app builder apps
	 */
	@Override
	public List<AppBuilderApp> findAll(
		int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder apps.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of app builder apps
	 */
	@Override
	public List<AppBuilderApp> findAll(
		int start, int end, OrderByComparator<AppBuilderApp> orderByComparator,
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

		List<AppBuilderApp> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderApp>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_APPBUILDERAPP);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_APPBUILDERAPP;

				sql = sql.concat(AppBuilderAppModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AppBuilderApp>)QueryUtil.list(
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
	 * Removes all the app builder apps from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AppBuilderApp appBuilderApp : findAll()) {
			remove(appBuilderApp);
		}
	}

	/**
	 * Returns the number of app builder apps.
	 *
	 * @return the number of app builder apps
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_APPBUILDERAPP);

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
		return "appBuilderAppId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_APPBUILDERAPP;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AppBuilderAppModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the app builder app persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			AppBuilderAppModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			AppBuilderAppModelImpl.UUID_COLUMN_BITMASK |
			AppBuilderAppModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			AppBuilderAppModelImpl.UUID_COLUMN_BITMASK |
			AppBuilderAppModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] {Long.class.getName()},
			AppBuilderAppModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] {Long.class.getName()},
			AppBuilderAppModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByDDMStructureId = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByDDMStructureId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByDDMStructureId = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByDDMStructureId", new String[] {Long.class.getName()},
			AppBuilderAppModelImpl.DDMSTRUCTUREID_COLUMN_BITMASK);

		_finderPathCountByDDMStructureId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByDDMStructureId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByG_S = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_S = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_S",
			new String[] {Long.class.getName(), String.class.getName()},
			AppBuilderAppModelImpl.GROUPID_COLUMN_BITMASK |
			AppBuilderAppModelImpl.SCOPE_COLUMN_BITMASK);

		_finderPathCountByG_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByC_A = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_A = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			AppBuilderAppModelImpl.COMPANYID_COLUMN_BITMASK |
			AppBuilderAppModelImpl.ACTIVE_COLUMN_BITMASK);

		_finderPathCountByC_A = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByC_S = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_S",
			new String[] {Long.class.getName(), String.class.getName()},
			AppBuilderAppModelImpl.COMPANYID_COLUMN_BITMASK |
			AppBuilderAppModelImpl.SCOPE_COLUMN_BITMASK);

		_finderPathCountByC_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_C_D = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_D = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			AppBuilderAppModelImpl.GROUPID_COLUMN_BITMASK |
			AppBuilderAppModelImpl.COMPANYID_COLUMN_BITMASK |
			AppBuilderAppModelImpl.DDMSTRUCTUREID_COLUMN_BITMASK);

		_finderPathCountByG_C_D = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByC_A_S = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_A_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_A_S = new FinderPath(
			AppBuilderAppImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_A_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			AppBuilderAppModelImpl.COMPANYID_COLUMN_BITMASK |
			AppBuilderAppModelImpl.ACTIVE_COLUMN_BITMASK |
			AppBuilderAppModelImpl.SCOPE_COLUMN_BITMASK);

		_finderPathCountByC_A_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_A_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AppBuilderAppImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = AppBuilderPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AppBuilderPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AppBuilderPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_APPBUILDERAPP =
		"SELECT appBuilderApp FROM AppBuilderApp appBuilderApp";

	private static final String _SQL_SELECT_APPBUILDERAPP_WHERE =
		"SELECT appBuilderApp FROM AppBuilderApp appBuilderApp WHERE ";

	private static final String _SQL_COUNT_APPBUILDERAPP =
		"SELECT COUNT(appBuilderApp) FROM AppBuilderApp appBuilderApp";

	private static final String _SQL_COUNT_APPBUILDERAPP_WHERE =
		"SELECT COUNT(appBuilderApp) FROM AppBuilderApp appBuilderApp WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"appBuilderApp.appBuilderAppId";

	private static final String _FILTER_SQL_SELECT_APPBUILDERAPP_WHERE =
		"SELECT DISTINCT {appBuilderApp.*} FROM AppBuilderApp appBuilderApp WHERE ";

	private static final String
		_FILTER_SQL_SELECT_APPBUILDERAPP_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {AppBuilderApp.*} FROM (SELECT DISTINCT appBuilderApp.appBuilderAppId FROM AppBuilderApp appBuilderApp WHERE ";

	private static final String
		_FILTER_SQL_SELECT_APPBUILDERAPP_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN AppBuilderApp ON TEMP_TABLE.appBuilderAppId = AppBuilderApp.appBuilderAppId";

	private static final String _FILTER_SQL_COUNT_APPBUILDERAPP_WHERE =
		"SELECT COUNT(DISTINCT appBuilderApp.appBuilderAppId) AS COUNT_VALUE FROM AppBuilderApp appBuilderApp WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "appBuilderApp";

	private static final String _FILTER_ENTITY_TABLE = "AppBuilderApp";

	private static final String _ORDER_BY_ENTITY_ALIAS = "appBuilderApp.";

	private static final String _ORDER_BY_ENTITY_TABLE = "AppBuilderApp.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AppBuilderApp exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AppBuilderApp exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AppBuilderAppPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "active"});

	static {
		try {
			Class.forName(AppBuilderPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}