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

import com.liferay.app.builder.exception.NoSuchAppVersionException;
import com.liferay.app.builder.model.AppBuilderAppVersion;
import com.liferay.app.builder.model.AppBuilderAppVersionTable;
import com.liferay.app.builder.model.impl.AppBuilderAppVersionImpl;
import com.liferay.app.builder.model.impl.AppBuilderAppVersionModelImpl;
import com.liferay.app.builder.service.persistence.AppBuilderAppVersionPersistence;
import com.liferay.app.builder.service.persistence.impl.constants.AppBuilderPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
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
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the app builder app version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {AppBuilderAppVersionPersistence.class, BasePersistence.class}
)
public class AppBuilderAppVersionPersistenceImpl
	extends BasePersistenceImpl<AppBuilderAppVersion>
	implements AppBuilderAppVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AppBuilderAppVersionUtil</code> to access the app builder app version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AppBuilderAppVersionImpl.class.getName();

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
	 * Returns all the app builder app versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder app versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator,
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

		List<AppBuilderAppVersion> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderAppVersion>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderAppVersion appBuilderAppVersion : list) {
					if (!uuid.equals(appBuilderAppVersion.getUuid())) {
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

			sb.append(_SQL_SELECT_APPBUILDERAPPVERSION_WHERE);

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
				sb.append(AppBuilderAppVersionModelImpl.ORDER_BY_JPQL);
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

				list = (List<AppBuilderAppVersion>)QueryUtil.list(
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
	 * Returns the first app builder app version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion findByUuid_First(
			String uuid,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = fetchByUuid_First(
			uuid, orderByComparator);

		if (appBuilderAppVersion != null) {
			return appBuilderAppVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchAppVersionException(sb.toString());
	}

	/**
	 * Returns the first app builder app version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion fetchByUuid_First(
		String uuid,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		List<AppBuilderAppVersion> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion findByUuid_Last(
			String uuid,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = fetchByUuid_Last(
			uuid, orderByComparator);

		if (appBuilderAppVersion != null) {
			return appBuilderAppVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchAppVersionException(sb.toString());
	}

	/**
	 * Returns the last app builder app version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion fetchByUuid_Last(
		String uuid,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AppBuilderAppVersion> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder app versions before and after the current app builder app version in the ordered set where uuid = &#63;.
	 *
	 * @param appBuilderAppVersionId the primary key of the current app builder app version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	@Override
	public AppBuilderAppVersion[] findByUuid_PrevAndNext(
			long appBuilderAppVersionId, String uuid,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException {

		uuid = Objects.toString(uuid, "");

		AppBuilderAppVersion appBuilderAppVersion = findByPrimaryKey(
			appBuilderAppVersionId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderAppVersion[] array = new AppBuilderAppVersionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, appBuilderAppVersion, uuid, orderByComparator, true);

			array[1] = appBuilderAppVersion;

			array[2] = getByUuid_PrevAndNext(
				session, appBuilderAppVersion, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AppBuilderAppVersion getByUuid_PrevAndNext(
		Session session, AppBuilderAppVersion appBuilderAppVersion, String uuid,
		OrderByComparator<AppBuilderAppVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_APPBUILDERAPPVERSION_WHERE);

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
			sb.append(AppBuilderAppVersionModelImpl.ORDER_BY_JPQL);
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
						appBuilderAppVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderAppVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder app versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AppBuilderAppVersion appBuilderAppVersion :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(appBuilderAppVersion);
		}
	}

	/**
	 * Returns the number of app builder app versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching app builder app versions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_APPBUILDERAPPVERSION_WHERE);

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
		"appBuilderAppVersion.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(appBuilderAppVersion.uuid IS NULL OR appBuilderAppVersion.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the app builder app version where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchAppVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion findByUUID_G(String uuid, long groupId)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = fetchByUUID_G(
			uuid, groupId);

		if (appBuilderAppVersion == null) {
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

			throw new NoSuchAppVersionException(sb.toString());
		}

		return appBuilderAppVersion;
	}

	/**
	 * Returns the app builder app version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the app builder app version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion fetchByUUID_G(
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

		if (result instanceof AppBuilderAppVersion) {
			AppBuilderAppVersion appBuilderAppVersion =
				(AppBuilderAppVersion)result;

			if (!Objects.equals(uuid, appBuilderAppVersion.getUuid()) ||
				(groupId != appBuilderAppVersion.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_APPBUILDERAPPVERSION_WHERE);

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

				List<AppBuilderAppVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					AppBuilderAppVersion appBuilderAppVersion = list.get(0);

					result = appBuilderAppVersion;

					cacheResult(appBuilderAppVersion);
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
			return (AppBuilderAppVersion)result;
		}
	}

	/**
	 * Removes the app builder app version where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the app builder app version that was removed
	 */
	@Override
	public AppBuilderAppVersion removeByUUID_G(String uuid, long groupId)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = findByUUID_G(uuid, groupId);

		return remove(appBuilderAppVersion);
	}

	/**
	 * Returns the number of app builder app versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching app builder app versions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_APPBUILDERAPPVERSION_WHERE);

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
		"appBuilderAppVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(appBuilderAppVersion.uuid IS NULL OR appBuilderAppVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"appBuilderAppVersion.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the app builder app versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder app versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator,
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

		List<AppBuilderAppVersion> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderAppVersion>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderAppVersion appBuilderAppVersion : list) {
					if (!uuid.equals(appBuilderAppVersion.getUuid()) ||
						(companyId != appBuilderAppVersion.getCompanyId())) {

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

			sb.append(_SQL_SELECT_APPBUILDERAPPVERSION_WHERE);

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
				sb.append(AppBuilderAppVersionModelImpl.ORDER_BY_JPQL);
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

				list = (List<AppBuilderAppVersion>)QueryUtil.list(
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
	 * Returns the first app builder app version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (appBuilderAppVersion != null) {
			return appBuilderAppVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchAppVersionException(sb.toString());
	}

	/**
	 * Returns the first app builder app version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		List<AppBuilderAppVersion> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (appBuilderAppVersion != null) {
			return appBuilderAppVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchAppVersionException(sb.toString());
	}

	/**
	 * Returns the last app builder app version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AppBuilderAppVersion> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder app versions before and after the current app builder app version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param appBuilderAppVersionId the primary key of the current app builder app version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	@Override
	public AppBuilderAppVersion[] findByUuid_C_PrevAndNext(
			long appBuilderAppVersionId, String uuid, long companyId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException {

		uuid = Objects.toString(uuid, "");

		AppBuilderAppVersion appBuilderAppVersion = findByPrimaryKey(
			appBuilderAppVersionId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderAppVersion[] array = new AppBuilderAppVersionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, appBuilderAppVersion, uuid, companyId,
				orderByComparator, true);

			array[1] = appBuilderAppVersion;

			array[2] = getByUuid_C_PrevAndNext(
				session, appBuilderAppVersion, uuid, companyId,
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

	protected AppBuilderAppVersion getByUuid_C_PrevAndNext(
		Session session, AppBuilderAppVersion appBuilderAppVersion, String uuid,
		long companyId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_APPBUILDERAPPVERSION_WHERE);

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
			sb.append(AppBuilderAppVersionModelImpl.ORDER_BY_JPQL);
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
						appBuilderAppVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderAppVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder app versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AppBuilderAppVersion appBuilderAppVersion :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(appBuilderAppVersion);
		}
	}

	/**
	 * Returns the number of app builder app versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching app builder app versions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_APPBUILDERAPPVERSION_WHERE);

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
		"appBuilderAppVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(appBuilderAppVersion.uuid IS NULL OR appBuilderAppVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"appBuilderAppVersion.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the app builder app versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder app versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator,
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

		List<AppBuilderAppVersion> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderAppVersion>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderAppVersion appBuilderAppVersion : list) {
					if (groupId != appBuilderAppVersion.getGroupId()) {
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

			sb.append(_SQL_SELECT_APPBUILDERAPPVERSION_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderAppVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<AppBuilderAppVersion>)QueryUtil.list(
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
	 * Returns the first app builder app version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion findByGroupId_First(
			long groupId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = fetchByGroupId_First(
			groupId, orderByComparator);

		if (appBuilderAppVersion != null) {
			return appBuilderAppVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchAppVersionException(sb.toString());
	}

	/**
	 * Returns the first app builder app version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion fetchByGroupId_First(
		long groupId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		List<AppBuilderAppVersion> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion findByGroupId_Last(
			long groupId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (appBuilderAppVersion != null) {
			return appBuilderAppVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchAppVersionException(sb.toString());
	}

	/**
	 * Returns the last app builder app version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion fetchByGroupId_Last(
		long groupId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<AppBuilderAppVersion> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder app versions before and after the current app builder app version in the ordered set where groupId = &#63;.
	 *
	 * @param appBuilderAppVersionId the primary key of the current app builder app version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	@Override
	public AppBuilderAppVersion[] findByGroupId_PrevAndNext(
			long appBuilderAppVersionId, long groupId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = findByPrimaryKey(
			appBuilderAppVersionId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderAppVersion[] array = new AppBuilderAppVersionImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, appBuilderAppVersion, groupId, orderByComparator,
				true);

			array[1] = appBuilderAppVersion;

			array[2] = getByGroupId_PrevAndNext(
				session, appBuilderAppVersion, groupId, orderByComparator,
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

	protected AppBuilderAppVersion getByGroupId_PrevAndNext(
		Session session, AppBuilderAppVersion appBuilderAppVersion,
		long groupId, OrderByComparator<AppBuilderAppVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_APPBUILDERAPPVERSION_WHERE);

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
			sb.append(AppBuilderAppVersionModelImpl.ORDER_BY_JPQL);
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
						appBuilderAppVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderAppVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder app versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (AppBuilderAppVersion appBuilderAppVersion :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(appBuilderAppVersion);
		}
	}

	/**
	 * Returns the number of app builder app versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching app builder app versions
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_APPBUILDERAPPVERSION_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"appBuilderAppVersion.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the app builder app versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder app versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator,
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

		List<AppBuilderAppVersion> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderAppVersion>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderAppVersion appBuilderAppVersion : list) {
					if (companyId != appBuilderAppVersion.getCompanyId()) {
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

			sb.append(_SQL_SELECT_APPBUILDERAPPVERSION_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderAppVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<AppBuilderAppVersion>)QueryUtil.list(
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
	 * Returns the first app builder app version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion findByCompanyId_First(
			long companyId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (appBuilderAppVersion != null) {
			return appBuilderAppVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchAppVersionException(sb.toString());
	}

	/**
	 * Returns the first app builder app version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion fetchByCompanyId_First(
		long companyId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		List<AppBuilderAppVersion> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion findByCompanyId_Last(
			long companyId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (appBuilderAppVersion != null) {
			return appBuilderAppVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchAppVersionException(sb.toString());
	}

	/**
	 * Returns the last app builder app version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<AppBuilderAppVersion> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder app versions before and after the current app builder app version in the ordered set where companyId = &#63;.
	 *
	 * @param appBuilderAppVersionId the primary key of the current app builder app version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	@Override
	public AppBuilderAppVersion[] findByCompanyId_PrevAndNext(
			long appBuilderAppVersionId, long companyId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = findByPrimaryKey(
			appBuilderAppVersionId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderAppVersion[] array = new AppBuilderAppVersionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, appBuilderAppVersion, companyId, orderByComparator,
				true);

			array[1] = appBuilderAppVersion;

			array[2] = getByCompanyId_PrevAndNext(
				session, appBuilderAppVersion, companyId, orderByComparator,
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

	protected AppBuilderAppVersion getByCompanyId_PrevAndNext(
		Session session, AppBuilderAppVersion appBuilderAppVersion,
		long companyId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_APPBUILDERAPPVERSION_WHERE);

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
			sb.append(AppBuilderAppVersionModelImpl.ORDER_BY_JPQL);
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
						appBuilderAppVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderAppVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder app versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (AppBuilderAppVersion appBuilderAppVersion :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(appBuilderAppVersion);
		}
	}

	/**
	 * Returns the number of app builder app versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching app builder app versions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_APPBUILDERAPPVERSION_WHERE);

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
		"appBuilderAppVersion.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByAppBuilderAppId;
	private FinderPath _finderPathWithoutPaginationFindByAppBuilderAppId;
	private FinderPath _finderPathCountByAppBuilderAppId;

	/**
	 * Returns all the app builder app versions where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByAppBuilderAppId(
		long appBuilderAppId) {

		return findByAppBuilderAppId(
			appBuilderAppId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder app versions where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end) {

		return findByAppBuilderAppId(appBuilderAppId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return findByAppBuilderAppId(
			appBuilderAppId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByAppBuilderAppId;
				finderArgs = new Object[] {appBuilderAppId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAppBuilderAppId;
			finderArgs = new Object[] {
				appBuilderAppId, start, end, orderByComparator
			};
		}

		List<AppBuilderAppVersion> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderAppVersion>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderAppVersion appBuilderAppVersion : list) {
					if (appBuilderAppId !=
							appBuilderAppVersion.getAppBuilderAppId()) {

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

			sb.append(_SQL_SELECT_APPBUILDERAPPVERSION_WHERE);

			sb.append(_FINDER_COLUMN_APPBUILDERAPPID_APPBUILDERAPPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderAppVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				list = (List<AppBuilderAppVersion>)QueryUtil.list(
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
	 * Returns the first app builder app version in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion findByAppBuilderAppId_First(
			long appBuilderAppId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion =
			fetchByAppBuilderAppId_First(appBuilderAppId, orderByComparator);

		if (appBuilderAppVersion != null) {
			return appBuilderAppVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("appBuilderAppId=");
		sb.append(appBuilderAppId);

		sb.append("}");

		throw new NoSuchAppVersionException(sb.toString());
	}

	/**
	 * Returns the first app builder app version in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion fetchByAppBuilderAppId_First(
		long appBuilderAppId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		List<AppBuilderAppVersion> list = findByAppBuilderAppId(
			appBuilderAppId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app version in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion findByAppBuilderAppId_Last(
			long appBuilderAppId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = fetchByAppBuilderAppId_Last(
			appBuilderAppId, orderByComparator);

		if (appBuilderAppVersion != null) {
			return appBuilderAppVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("appBuilderAppId=");
		sb.append(appBuilderAppId);

		sb.append("}");

		throw new NoSuchAppVersionException(sb.toString());
	}

	/**
	 * Returns the last app builder app version in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion fetchByAppBuilderAppId_Last(
		long appBuilderAppId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		int count = countByAppBuilderAppId(appBuilderAppId);

		if (count == 0) {
			return null;
		}

		List<AppBuilderAppVersion> list = findByAppBuilderAppId(
			appBuilderAppId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder app versions before and after the current app builder app version in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppVersionId the primary key of the current app builder app version
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	@Override
	public AppBuilderAppVersion[] findByAppBuilderAppId_PrevAndNext(
			long appBuilderAppVersionId, long appBuilderAppId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = findByPrimaryKey(
			appBuilderAppVersionId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderAppVersion[] array = new AppBuilderAppVersionImpl[3];

			array[0] = getByAppBuilderAppId_PrevAndNext(
				session, appBuilderAppVersion, appBuilderAppId,
				orderByComparator, true);

			array[1] = appBuilderAppVersion;

			array[2] = getByAppBuilderAppId_PrevAndNext(
				session, appBuilderAppVersion, appBuilderAppId,
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

	protected AppBuilderAppVersion getByAppBuilderAppId_PrevAndNext(
		Session session, AppBuilderAppVersion appBuilderAppVersion,
		long appBuilderAppId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_APPBUILDERAPPVERSION_WHERE);

		sb.append(_FINDER_COLUMN_APPBUILDERAPPID_APPBUILDERAPPID_2);

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
			sb.append(AppBuilderAppVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(appBuilderAppId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						appBuilderAppVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderAppVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder app versions where appBuilderAppId = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 */
	@Override
	public void removeByAppBuilderAppId(long appBuilderAppId) {
		for (AppBuilderAppVersion appBuilderAppVersion :
				findByAppBuilderAppId(
					appBuilderAppId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(appBuilderAppVersion);
		}
	}

	/**
	 * Returns the number of app builder app versions where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the number of matching app builder app versions
	 */
	@Override
	public int countByAppBuilderAppId(long appBuilderAppId) {
		FinderPath finderPath = _finderPathCountByAppBuilderAppId;

		Object[] finderArgs = new Object[] {appBuilderAppId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_APPBUILDERAPPVERSION_WHERE);

			sb.append(_FINDER_COLUMN_APPBUILDERAPPID_APPBUILDERAPPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

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

	private static final String
		_FINDER_COLUMN_APPBUILDERAPPID_APPBUILDERAPPID_2 =
			"appBuilderAppVersion.appBuilderAppId = ?";

	private FinderPath _finderPathFetchByA_V;
	private FinderPath _finderPathCountByA_V;

	/**
	 * Returns the app builder app version where appBuilderAppId = &#63; and version = &#63; or throws a <code>NoSuchAppVersionException</code> if it could not be found.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param version the version
	 * @return the matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion findByA_V(long appBuilderAppId, String version)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = fetchByA_V(
			appBuilderAppId, version);

		if (appBuilderAppVersion == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("appBuilderAppId=");
			sb.append(appBuilderAppId);

			sb.append(", version=");
			sb.append(version);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAppVersionException(sb.toString());
		}

		return appBuilderAppVersion;
	}

	/**
	 * Returns the app builder app version where appBuilderAppId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param version the version
	 * @return the matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion fetchByA_V(
		long appBuilderAppId, String version) {

		return fetchByA_V(appBuilderAppId, version, true);
	}

	/**
	 * Returns the app builder app version where appBuilderAppId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	@Override
	public AppBuilderAppVersion fetchByA_V(
		long appBuilderAppId, String version, boolean useFinderCache) {

		version = Objects.toString(version, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {appBuilderAppId, version};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByA_V, finderArgs);
		}

		if (result instanceof AppBuilderAppVersion) {
			AppBuilderAppVersion appBuilderAppVersion =
				(AppBuilderAppVersion)result;

			if ((appBuilderAppId !=
					appBuilderAppVersion.getAppBuilderAppId()) ||
				!Objects.equals(version, appBuilderAppVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_APPBUILDERAPPVERSION_WHERE);

			sb.append(_FINDER_COLUMN_A_V_APPBUILDERAPPID_2);

			boolean bindVersion = false;

			if (version.isEmpty()) {
				sb.append(_FINDER_COLUMN_A_V_VERSION_3);
			}
			else {
				bindVersion = true;

				sb.append(_FINDER_COLUMN_A_V_VERSION_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				if (bindVersion) {
					queryPos.add(version);
				}

				List<AppBuilderAppVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByA_V, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									appBuilderAppId, version
								};
							}

							_log.warn(
								"AppBuilderAppVersionPersistenceImpl.fetchByA_V(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AppBuilderAppVersion appBuilderAppVersion = list.get(0);

					result = appBuilderAppVersion;

					cacheResult(appBuilderAppVersion);
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
			return (AppBuilderAppVersion)result;
		}
	}

	/**
	 * Removes the app builder app version where appBuilderAppId = &#63; and version = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param version the version
	 * @return the app builder app version that was removed
	 */
	@Override
	public AppBuilderAppVersion removeByA_V(
			long appBuilderAppId, String version)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = findByA_V(
			appBuilderAppId, version);

		return remove(appBuilderAppVersion);
	}

	/**
	 * Returns the number of app builder app versions where appBuilderAppId = &#63; and version = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param version the version
	 * @return the number of matching app builder app versions
	 */
	@Override
	public int countByA_V(long appBuilderAppId, String version) {
		version = Objects.toString(version, "");

		FinderPath finderPath = _finderPathCountByA_V;

		Object[] finderArgs = new Object[] {appBuilderAppId, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_APPBUILDERAPPVERSION_WHERE);

			sb.append(_FINDER_COLUMN_A_V_APPBUILDERAPPID_2);

			boolean bindVersion = false;

			if (version.isEmpty()) {
				sb.append(_FINDER_COLUMN_A_V_VERSION_3);
			}
			else {
				bindVersion = true;

				sb.append(_FINDER_COLUMN_A_V_VERSION_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				if (bindVersion) {
					queryPos.add(version);
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

	private static final String _FINDER_COLUMN_A_V_APPBUILDERAPPID_2 =
		"appBuilderAppVersion.appBuilderAppId = ? AND ";

	private static final String _FINDER_COLUMN_A_V_VERSION_2 =
		"appBuilderAppVersion.version = ?";

	private static final String _FINDER_COLUMN_A_V_VERSION_3 =
		"(appBuilderAppVersion.version IS NULL OR appBuilderAppVersion.version = '')";

	public AppBuilderAppVersionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AppBuilderAppVersion.class);

		setModelImplClass(AppBuilderAppVersionImpl.class);
		setModelPKClass(long.class);

		setTable(AppBuilderAppVersionTable.INSTANCE);
	}

	/**
	 * Caches the app builder app version in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppVersion the app builder app version
	 */
	@Override
	public void cacheResult(AppBuilderAppVersion appBuilderAppVersion) {
		entityCache.putResult(
			AppBuilderAppVersionImpl.class,
			appBuilderAppVersion.getPrimaryKey(), appBuilderAppVersion);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				appBuilderAppVersion.getUuid(),
				appBuilderAppVersion.getGroupId()
			},
			appBuilderAppVersion);

		finderCache.putResult(
			_finderPathFetchByA_V,
			new Object[] {
				appBuilderAppVersion.getAppBuilderAppId(),
				appBuilderAppVersion.getVersion()
			},
			appBuilderAppVersion);
	}

	/**
	 * Caches the app builder app versions in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppVersions the app builder app versions
	 */
	@Override
	public void cacheResult(List<AppBuilderAppVersion> appBuilderAppVersions) {
		for (AppBuilderAppVersion appBuilderAppVersion :
				appBuilderAppVersions) {

			if (entityCache.getResult(
					AppBuilderAppVersionImpl.class,
					appBuilderAppVersion.getPrimaryKey()) == null) {

				cacheResult(appBuilderAppVersion);
			}
		}
	}

	/**
	 * Clears the cache for all app builder app versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AppBuilderAppVersionImpl.class);

		finderCache.clearCache(AppBuilderAppVersionImpl.class);
	}

	/**
	 * Clears the cache for the app builder app version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AppBuilderAppVersion appBuilderAppVersion) {
		entityCache.removeResult(
			AppBuilderAppVersionImpl.class, appBuilderAppVersion);
	}

	@Override
	public void clearCache(List<AppBuilderAppVersion> appBuilderAppVersions) {
		for (AppBuilderAppVersion appBuilderAppVersion :
				appBuilderAppVersions) {

			entityCache.removeResult(
				AppBuilderAppVersionImpl.class, appBuilderAppVersion);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AppBuilderAppVersionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AppBuilderAppVersionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AppBuilderAppVersionModelImpl appBuilderAppVersionModelImpl) {

		Object[] args = new Object[] {
			appBuilderAppVersionModelImpl.getUuid(),
			appBuilderAppVersionModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, appBuilderAppVersionModelImpl);

		args = new Object[] {
			appBuilderAppVersionModelImpl.getAppBuilderAppId(),
			appBuilderAppVersionModelImpl.getVersion()
		};

		finderCache.putResult(_finderPathCountByA_V, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByA_V, args, appBuilderAppVersionModelImpl);
	}

	/**
	 * Creates a new app builder app version with the primary key. Does not add the app builder app version to the database.
	 *
	 * @param appBuilderAppVersionId the primary key for the new app builder app version
	 * @return the new app builder app version
	 */
	@Override
	public AppBuilderAppVersion create(long appBuilderAppVersionId) {
		AppBuilderAppVersion appBuilderAppVersion =
			new AppBuilderAppVersionImpl();

		appBuilderAppVersion.setNew(true);
		appBuilderAppVersion.setPrimaryKey(appBuilderAppVersionId);

		String uuid = PortalUUIDUtil.generate();

		appBuilderAppVersion.setUuid(uuid);

		appBuilderAppVersion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return appBuilderAppVersion;
	}

	/**
	 * Removes the app builder app version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppVersionId the primary key of the app builder app version
	 * @return the app builder app version that was removed
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	@Override
	public AppBuilderAppVersion remove(long appBuilderAppVersionId)
		throws NoSuchAppVersionException {

		return remove((Serializable)appBuilderAppVersionId);
	}

	/**
	 * Removes the app builder app version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the app builder app version
	 * @return the app builder app version that was removed
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	@Override
	public AppBuilderAppVersion remove(Serializable primaryKey)
		throws NoSuchAppVersionException {

		Session session = null;

		try {
			session = openSession();

			AppBuilderAppVersion appBuilderAppVersion =
				(AppBuilderAppVersion)session.get(
					AppBuilderAppVersionImpl.class, primaryKey);

			if (appBuilderAppVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAppVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(appBuilderAppVersion);
		}
		catch (NoSuchAppVersionException noSuchEntityException) {
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
	protected AppBuilderAppVersion removeImpl(
		AppBuilderAppVersion appBuilderAppVersion) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(appBuilderAppVersion)) {
				appBuilderAppVersion = (AppBuilderAppVersion)session.get(
					AppBuilderAppVersionImpl.class,
					appBuilderAppVersion.getPrimaryKeyObj());
			}

			if (appBuilderAppVersion != null) {
				session.delete(appBuilderAppVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (appBuilderAppVersion != null) {
			clearCache(appBuilderAppVersion);
		}

		return appBuilderAppVersion;
	}

	@Override
	public AppBuilderAppVersion updateImpl(
		AppBuilderAppVersion appBuilderAppVersion) {

		boolean isNew = appBuilderAppVersion.isNew();

		if (!(appBuilderAppVersion instanceof AppBuilderAppVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(appBuilderAppVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					appBuilderAppVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in appBuilderAppVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AppBuilderAppVersion implementation " +
					appBuilderAppVersion.getClass());
		}

		AppBuilderAppVersionModelImpl appBuilderAppVersionModelImpl =
			(AppBuilderAppVersionModelImpl)appBuilderAppVersion;

		if (Validator.isNull(appBuilderAppVersion.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			appBuilderAppVersion.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (appBuilderAppVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				appBuilderAppVersion.setCreateDate(now);
			}
			else {
				appBuilderAppVersion.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!appBuilderAppVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				appBuilderAppVersion.setModifiedDate(now);
			}
			else {
				appBuilderAppVersion.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(appBuilderAppVersion);
			}
			else {
				appBuilderAppVersion = (AppBuilderAppVersion)session.merge(
					appBuilderAppVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			AppBuilderAppVersionImpl.class, appBuilderAppVersionModelImpl,
			false, true);

		cacheUniqueFindersCache(appBuilderAppVersionModelImpl);

		if (isNew) {
			appBuilderAppVersion.setNew(false);
		}

		appBuilderAppVersion.resetOriginalValues();

		return appBuilderAppVersion;
	}

	/**
	 * Returns the app builder app version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the app builder app version
	 * @return the app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	@Override
	public AppBuilderAppVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAppVersionException {

		AppBuilderAppVersion appBuilderAppVersion = fetchByPrimaryKey(
			primaryKey);

		if (appBuilderAppVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAppVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return appBuilderAppVersion;
	}

	/**
	 * Returns the app builder app version with the primary key or throws a <code>NoSuchAppVersionException</code> if it could not be found.
	 *
	 * @param appBuilderAppVersionId the primary key of the app builder app version
	 * @return the app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	@Override
	public AppBuilderAppVersion findByPrimaryKey(long appBuilderAppVersionId)
		throws NoSuchAppVersionException {

		return findByPrimaryKey((Serializable)appBuilderAppVersionId);
	}

	/**
	 * Returns the app builder app version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param appBuilderAppVersionId the primary key of the app builder app version
	 * @return the app builder app version, or <code>null</code> if a app builder app version with the primary key could not be found
	 */
	@Override
	public AppBuilderAppVersion fetchByPrimaryKey(long appBuilderAppVersionId) {
		return fetchByPrimaryKey((Serializable)appBuilderAppVersionId);
	}

	/**
	 * Returns all the app builder app versions.
	 *
	 * @return the app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder app versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder app versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findAll(
		int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder app versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of app builder app versions
	 */
	@Override
	public List<AppBuilderAppVersion> findAll(
		int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator,
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

		List<AppBuilderAppVersion> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderAppVersion>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_APPBUILDERAPPVERSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_APPBUILDERAPPVERSION;

				sql = sql.concat(AppBuilderAppVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AppBuilderAppVersion>)QueryUtil.list(
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
	 * Removes all the app builder app versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AppBuilderAppVersion appBuilderAppVersion : findAll()) {
			remove(appBuilderAppVersion);
		}
	}

	/**
	 * Returns the number of app builder app versions.
	 *
	 * @return the number of app builder app versions
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
					_SQL_COUNT_APPBUILDERAPPVERSION);

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
		return "appBuilderAppVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_APPBUILDERAPPVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AppBuilderAppVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the app builder app version persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new AppBuilderAppVersionModelArgumentsResolver(),
			new HashMapDictionary<>());

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

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindByAppBuilderAppId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAppBuilderAppId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"appBuilderAppId"}, true);

		_finderPathWithoutPaginationFindByAppBuilderAppId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAppBuilderAppId",
			new String[] {Long.class.getName()},
			new String[] {"appBuilderAppId"}, true);

		_finderPathCountByAppBuilderAppId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAppBuilderAppId",
			new String[] {Long.class.getName()},
			new String[] {"appBuilderAppId"}, false);

		_finderPathFetchByA_V = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByA_V",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"appBuilderAppId", "version"}, true);

		_finderPathCountByA_V = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_V",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"appBuilderAppId", "version"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AppBuilderAppVersionImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
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

	private BundleContext _bundleContext;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_APPBUILDERAPPVERSION =
		"SELECT appBuilderAppVersion FROM AppBuilderAppVersion appBuilderAppVersion";

	private static final String _SQL_SELECT_APPBUILDERAPPVERSION_WHERE =
		"SELECT appBuilderAppVersion FROM AppBuilderAppVersion appBuilderAppVersion WHERE ";

	private static final String _SQL_COUNT_APPBUILDERAPPVERSION =
		"SELECT COUNT(appBuilderAppVersion) FROM AppBuilderAppVersion appBuilderAppVersion";

	private static final String _SQL_COUNT_APPBUILDERAPPVERSION_WHERE =
		"SELECT COUNT(appBuilderAppVersion) FROM AppBuilderAppVersion appBuilderAppVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"appBuilderAppVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AppBuilderAppVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AppBuilderAppVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AppBuilderAppVersionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class AppBuilderAppVersionModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			AppBuilderAppVersionModelImpl appBuilderAppVersionModelImpl =
				(AppBuilderAppVersionModelImpl)baseModel;

			long columnBitmask =
				appBuilderAppVersionModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					appBuilderAppVersionModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						appBuilderAppVersionModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					appBuilderAppVersionModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return AppBuilderAppVersionImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return AppBuilderAppVersionTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			AppBuilderAppVersionModelImpl appBuilderAppVersionModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						appBuilderAppVersionModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = appBuilderAppVersionModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}