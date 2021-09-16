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

package com.liferay.asset.list.service.persistence.impl;

import com.liferay.asset.list.exception.NoSuchEntryUsageException;
import com.liferay.asset.list.model.AssetListEntryUsage;
import com.liferay.asset.list.model.AssetListEntryUsageTable;
import com.liferay.asset.list.model.impl.AssetListEntryUsageImpl;
import com.liferay.asset.list.model.impl.AssetListEntryUsageModelImpl;
import com.liferay.asset.list.service.persistence.AssetListEntryUsagePersistence;
import com.liferay.asset.list.service.persistence.impl.constants.AssetListPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
 * The persistence implementation for the asset list entry usage service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {AssetListEntryUsagePersistence.class, BasePersistence.class}
)
public class AssetListEntryUsagePersistenceImpl
	extends BasePersistenceImpl<AssetListEntryUsage>
	implements AssetListEntryUsagePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetListEntryUsageUtil</code> to access the asset list entry usage persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetListEntryUsageImpl.class.getName();

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
	 * Returns all the asset list entry usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetListEntryUsage> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<AssetListEntryUsage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntryUsage>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntryUsage assetListEntryUsage : list) {
					if (!uuid.equals(assetListEntryUsage.getUuid())) {
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

			sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

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
				sb.append(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
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

				list = (List<AssetListEntryUsage>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first asset list entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByUuid_First(
			String uuid,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByUuid_First(
			uuid, orderByComparator);

		if (assetListEntryUsage != null) {
			return assetListEntryUsage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the first asset list entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByUuid_First(
		String uuid, OrderByComparator<AssetListEntryUsage> orderByComparator) {

		List<AssetListEntryUsage> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByUuid_Last(
			String uuid,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByUuid_Last(
			uuid, orderByComparator);

		if (assetListEntryUsage != null) {
			return assetListEntryUsage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the last asset list entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByUuid_Last(
		String uuid, OrderByComparator<AssetListEntryUsage> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AssetListEntryUsage> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry usages before and after the current asset list entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param assetListEntryUsageId the primary key of the current asset list entry usage
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	@Override
	public AssetListEntryUsage[] findByUuid_PrevAndNext(
			long assetListEntryUsageId, String uuid,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		uuid = Objects.toString(uuid, "");

		AssetListEntryUsage assetListEntryUsage = findByPrimaryKey(
			assetListEntryUsageId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntryUsage[] array = new AssetListEntryUsageImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, assetListEntryUsage, uuid, orderByComparator, true);

			array[1] = assetListEntryUsage;

			array[2] = getByUuid_PrevAndNext(
				session, assetListEntryUsage, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntryUsage getByUuid_PrevAndNext(
		Session session, AssetListEntryUsage assetListEntryUsage, String uuid,
		OrderByComparator<AssetListEntryUsage> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

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
			sb.append(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
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
						assetListEntryUsage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntryUsage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry usages where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AssetListEntryUsage assetListEntryUsage :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetListEntryUsage);
		}
	}

	/**
	 * Returns the number of asset list entry usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset list entry usages
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETLISTENTRYUSAGE_WHERE);

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

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
		"assetListEntryUsage.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(assetListEntryUsage.uuid IS NULL OR assetListEntryUsage.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the asset list entry usage where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryUsageException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByUUID_G(uuid, groupId);

		if (assetListEntryUsage == null) {
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

			throw new NoSuchEntryUsageException(sb.toString());
		}

		return assetListEntryUsage;
	}

	/**
	 * Returns the asset list entry usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the asset list entry usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof AssetListEntryUsage) {
			AssetListEntryUsage assetListEntryUsage =
				(AssetListEntryUsage)result;

			if (!Objects.equals(uuid, assetListEntryUsage.getUuid()) ||
				(groupId != assetListEntryUsage.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

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

				List<AssetListEntryUsage> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					AssetListEntryUsage assetListEntryUsage = list.get(0);

					result = assetListEntryUsage;

					cacheResult(assetListEntryUsage);
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
			return (AssetListEntryUsage)result;
		}
	}

	/**
	 * Removes the asset list entry usage where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset list entry usage that was removed
	 */
	@Override
	public AssetListEntryUsage removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = findByUUID_G(uuid, groupId);

		return remove(assetListEntryUsage);
	}

	/**
	 * Returns the number of asset list entry usages where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset list entry usages
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G;

			finderArgs = new Object[] {uuid, groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETLISTENTRYUSAGE_WHERE);

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

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
		"assetListEntryUsage.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(assetListEntryUsage.uuid IS NULL OR assetListEntryUsage.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"assetListEntryUsage.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the asset list entry usages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry usages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetListEntryUsage> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<AssetListEntryUsage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntryUsage>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntryUsage assetListEntryUsage : list) {
					if (!uuid.equals(assetListEntryUsage.getUuid()) ||
						(companyId != assetListEntryUsage.getCompanyId())) {

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

			sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

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
				sb.append(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
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

				list = (List<AssetListEntryUsage>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first asset list entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (assetListEntryUsage != null) {
			return assetListEntryUsage;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the first asset list entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		List<AssetListEntryUsage> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (assetListEntryUsage != null) {
			return assetListEntryUsage;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the last asset list entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AssetListEntryUsage> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry usages before and after the current asset list entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetListEntryUsageId the primary key of the current asset list entry usage
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	@Override
	public AssetListEntryUsage[] findByUuid_C_PrevAndNext(
			long assetListEntryUsageId, String uuid, long companyId,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		uuid = Objects.toString(uuid, "");

		AssetListEntryUsage assetListEntryUsage = findByPrimaryKey(
			assetListEntryUsageId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntryUsage[] array = new AssetListEntryUsageImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, assetListEntryUsage, uuid, companyId,
				orderByComparator, true);

			array[1] = assetListEntryUsage;

			array[2] = getByUuid_C_PrevAndNext(
				session, assetListEntryUsage, uuid, companyId,
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

	protected AssetListEntryUsage getByUuid_C_PrevAndNext(
		Session session, AssetListEntryUsage assetListEntryUsage, String uuid,
		long companyId,
		OrderByComparator<AssetListEntryUsage> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

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
			sb.append(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
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
						assetListEntryUsage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntryUsage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry usages where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AssetListEntryUsage assetListEntryUsage :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetListEntryUsage);
		}
	}

	/**
	 * Returns the number of asset list entry usages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset list entry usages
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETLISTENTRYUSAGE_WHERE);

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

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
		"assetListEntryUsage.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(assetListEntryUsage.uuid IS NULL OR assetListEntryUsage.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"assetListEntryUsage.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByAssetListEntryId;
	private FinderPath _finderPathWithoutPaginationFindByAssetListEntryId;
	private FinderPath _finderPathCountByAssetListEntryId;

	/**
	 * Returns all the asset list entry usages where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByAssetListEntryId(
		long assetListEntryId) {

		return findByAssetListEntryId(
			assetListEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry usages where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByAssetListEntryId(
		long assetListEntryId, int start, int end) {

		return findByAssetListEntryId(assetListEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		return findByAssetListEntryId(
			assetListEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListEntryUsage> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByAssetListEntryId;
				finderArgs = new Object[] {assetListEntryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByAssetListEntryId;
			finderArgs = new Object[] {
				assetListEntryId, start, end, orderByComparator
			};
		}

		List<AssetListEntryUsage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntryUsage>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntryUsage assetListEntryUsage : list) {
					if (assetListEntryId !=
							assetListEntryUsage.getAssetListEntryId()) {

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

			sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_ASSETLISTENTRYID_ASSETLISTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetListEntryId);

				list = (List<AssetListEntryUsage>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first asset list entry usage in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByAssetListEntryId_First(
			long assetListEntryId,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByAssetListEntryId_First(
			assetListEntryId, orderByComparator);

		if (assetListEntryUsage != null) {
			return assetListEntryUsage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetListEntryId=");
		sb.append(assetListEntryId);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the first asset list entry usage in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByAssetListEntryId_First(
		long assetListEntryId,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		List<AssetListEntryUsage> list = findByAssetListEntryId(
			assetListEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry usage in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByAssetListEntryId_Last(
			long assetListEntryId,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByAssetListEntryId_Last(
			assetListEntryId, orderByComparator);

		if (assetListEntryUsage != null) {
			return assetListEntryUsage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetListEntryId=");
		sb.append(assetListEntryId);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the last asset list entry usage in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByAssetListEntryId_Last(
		long assetListEntryId,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		int count = countByAssetListEntryId(assetListEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetListEntryUsage> list = findByAssetListEntryId(
			assetListEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry usages before and after the current asset list entry usage in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryUsageId the primary key of the current asset list entry usage
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	@Override
	public AssetListEntryUsage[] findByAssetListEntryId_PrevAndNext(
			long assetListEntryUsageId, long assetListEntryId,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = findByPrimaryKey(
			assetListEntryUsageId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntryUsage[] array = new AssetListEntryUsageImpl[3];

			array[0] = getByAssetListEntryId_PrevAndNext(
				session, assetListEntryUsage, assetListEntryId,
				orderByComparator, true);

			array[1] = assetListEntryUsage;

			array[2] = getByAssetListEntryId_PrevAndNext(
				session, assetListEntryUsage, assetListEntryId,
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

	protected AssetListEntryUsage getByAssetListEntryId_PrevAndNext(
		Session session, AssetListEntryUsage assetListEntryUsage,
		long assetListEntryId,
		OrderByComparator<AssetListEntryUsage> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

		sb.append(_FINDER_COLUMN_ASSETLISTENTRYID_ASSETLISTENTRYID_2);

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
			sb.append(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(assetListEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntryUsage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntryUsage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry usages where assetListEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 */
	@Override
	public void removeByAssetListEntryId(long assetListEntryId) {
		for (AssetListEntryUsage assetListEntryUsage :
				findByAssetListEntryId(
					assetListEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetListEntryUsage);
		}
	}

	/**
	 * Returns the number of asset list entry usages where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the number of matching asset list entry usages
	 */
	@Override
	public int countByAssetListEntryId(long assetListEntryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByAssetListEntryId;

			finderArgs = new Object[] {assetListEntryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETLISTENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_ASSETLISTENTRYID_ASSETLISTENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetListEntryId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
		_FINDER_COLUMN_ASSETLISTENTRYID_ASSETLISTENTRYID_2 =
			"assetListEntryUsage.assetListEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByPlid;
	private FinderPath _finderPathWithoutPaginationFindByPlid;
	private FinderPath _finderPathCountByPlid;

	/**
	 * Returns all the asset list entry usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByPlid(long plid) {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByPlid(long plid, int start, int end) {
		return findByPlid(plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByPlid(
		long plid, int start, int end,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		return findByPlid(plid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByPlid(
		long plid, int start, int end,
		OrderByComparator<AssetListEntryUsage> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByPlid;
				finderArgs = new Object[] {plid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByPlid;
			finderArgs = new Object[] {plid, start, end, orderByComparator};
		}

		List<AssetListEntryUsage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntryUsage>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntryUsage assetListEntryUsage : list) {
					if (plid != assetListEntryUsage.getPlid()) {
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

			sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_PLID_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(plid);

				list = (List<AssetListEntryUsage>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first asset list entry usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByPlid_First(
			long plid, OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByPlid_First(
			plid, orderByComparator);

		if (assetListEntryUsage != null) {
			return assetListEntryUsage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the first asset list entry usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByPlid_First(
		long plid, OrderByComparator<AssetListEntryUsage> orderByComparator) {

		List<AssetListEntryUsage> list = findByPlid(
			plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByPlid_Last(
			long plid, OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByPlid_Last(
			plid, orderByComparator);

		if (assetListEntryUsage != null) {
			return assetListEntryUsage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the last asset list entry usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByPlid_Last(
		long plid, OrderByComparator<AssetListEntryUsage> orderByComparator) {

		int count = countByPlid(plid);

		if (count == 0) {
			return null;
		}

		List<AssetListEntryUsage> list = findByPlid(
			plid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry usages before and after the current asset list entry usage in the ordered set where plid = &#63;.
	 *
	 * @param assetListEntryUsageId the primary key of the current asset list entry usage
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	@Override
	public AssetListEntryUsage[] findByPlid_PrevAndNext(
			long assetListEntryUsageId, long plid,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = findByPrimaryKey(
			assetListEntryUsageId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntryUsage[] array = new AssetListEntryUsageImpl[3];

			array[0] = getByPlid_PrevAndNext(
				session, assetListEntryUsage, plid, orderByComparator, true);

			array[1] = assetListEntryUsage;

			array[2] = getByPlid_PrevAndNext(
				session, assetListEntryUsage, plid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntryUsage getByPlid_PrevAndNext(
		Session session, AssetListEntryUsage assetListEntryUsage, long plid,
		OrderByComparator<AssetListEntryUsage> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

		sb.append(_FINDER_COLUMN_PLID_PLID_2);

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
			sb.append(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntryUsage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntryUsage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry usages where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	@Override
	public void removeByPlid(long plid) {
		for (AssetListEntryUsage assetListEntryUsage :
				findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetListEntryUsage);
		}
	}

	/**
	 * Returns the number of asset list entry usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching asset list entry usages
	 */
	@Override
	public int countByPlid(long plid) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByPlid;

			finderArgs = new Object[] {plid};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETLISTENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_PLID_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(plid);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_PLID_PLID_2 =
		"assetListEntryUsage.plid = ?";

	private FinderPath _finderPathWithPaginationFindByA_C;
	private FinderPath _finderPathWithoutPaginationFindByA_C;
	private FinderPath _finderPathCountByA_C;

	/**
	 * Returns all the asset list entry usages where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @return the matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByA_C(
		long assetListEntryId, long classNameId) {

		return findByA_C(
			assetListEntryId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset list entry usages where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByA_C(
		long assetListEntryId, long classNameId, int start, int end) {

		return findByA_C(assetListEntryId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByA_C(
		long assetListEntryId, long classNameId, int start, int end,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		return findByA_C(
			assetListEntryId, classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByA_C(
		long assetListEntryId, long classNameId, int start, int end,
		OrderByComparator<AssetListEntryUsage> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByA_C;
				finderArgs = new Object[] {assetListEntryId, classNameId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByA_C;
			finderArgs = new Object[] {
				assetListEntryId, classNameId, start, end, orderByComparator
			};
		}

		List<AssetListEntryUsage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntryUsage>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntryUsage assetListEntryUsage : list) {
					if ((assetListEntryId !=
							assetListEntryUsage.getAssetListEntryId()) ||
						(classNameId != assetListEntryUsage.getClassNameId())) {

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

			sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_A_C_ASSETLISTENTRYID_2);

			sb.append(_FINDER_COLUMN_A_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetListEntryId);

				queryPos.add(classNameId);

				list = (List<AssetListEntryUsage>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first asset list entry usage in the ordered set where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByA_C_First(
			long assetListEntryId, long classNameId,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByA_C_First(
			assetListEntryId, classNameId, orderByComparator);

		if (assetListEntryUsage != null) {
			return assetListEntryUsage;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetListEntryId=");
		sb.append(assetListEntryId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the first asset list entry usage in the ordered set where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByA_C_First(
		long assetListEntryId, long classNameId,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		List<AssetListEntryUsage> list = findByA_C(
			assetListEntryId, classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry usage in the ordered set where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByA_C_Last(
			long assetListEntryId, long classNameId,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByA_C_Last(
			assetListEntryId, classNameId, orderByComparator);

		if (assetListEntryUsage != null) {
			return assetListEntryUsage;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetListEntryId=");
		sb.append(assetListEntryId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the last asset list entry usage in the ordered set where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByA_C_Last(
		long assetListEntryId, long classNameId,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		int count = countByA_C(assetListEntryId, classNameId);

		if (count == 0) {
			return null;
		}

		List<AssetListEntryUsage> list = findByA_C(
			assetListEntryId, classNameId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry usages before and after the current asset list entry usage in the ordered set where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param assetListEntryUsageId the primary key of the current asset list entry usage
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	@Override
	public AssetListEntryUsage[] findByA_C_PrevAndNext(
			long assetListEntryUsageId, long assetListEntryId, long classNameId,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = findByPrimaryKey(
			assetListEntryUsageId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntryUsage[] array = new AssetListEntryUsageImpl[3];

			array[0] = getByA_C_PrevAndNext(
				session, assetListEntryUsage, assetListEntryId, classNameId,
				orderByComparator, true);

			array[1] = assetListEntryUsage;

			array[2] = getByA_C_PrevAndNext(
				session, assetListEntryUsage, assetListEntryId, classNameId,
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

	protected AssetListEntryUsage getByA_C_PrevAndNext(
		Session session, AssetListEntryUsage assetListEntryUsage,
		long assetListEntryId, long classNameId,
		OrderByComparator<AssetListEntryUsage> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

		sb.append(_FINDER_COLUMN_A_C_ASSETLISTENTRYID_2);

		sb.append(_FINDER_COLUMN_A_C_CLASSNAMEID_2);

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
			sb.append(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(assetListEntryId);

		queryPos.add(classNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntryUsage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntryUsage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry usages where assetListEntryId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByA_C(long assetListEntryId, long classNameId) {
		for (AssetListEntryUsage assetListEntryUsage :
				findByA_C(
					assetListEntryId, classNameId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(assetListEntryUsage);
		}
	}

	/**
	 * Returns the number of asset list entry usages where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @return the number of matching asset list entry usages
	 */
	@Override
	public int countByA_C(long assetListEntryId, long classNameId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByA_C;

			finderArgs = new Object[] {assetListEntryId, classNameId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETLISTENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_A_C_ASSETLISTENTRYID_2);

			sb.append(_FINDER_COLUMN_A_C_CLASSNAMEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetListEntryId);

				queryPos.add(classNameId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_A_C_ASSETLISTENTRYID_2 =
		"assetListEntryUsage.assetListEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_C_CLASSNAMEID_2 =
		"assetListEntryUsage.classNameId = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_K;
	private FinderPath _finderPathWithoutPaginationFindByG_C_K;
	private FinderPath _finderPathCountByG_C_K;

	/**
	 * Returns all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @return the matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByG_C_K(
		long groupId, long classNameId, String key) {

		return findByG_C_K(
			groupId, classNameId, key, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByG_C_K(
		long groupId, long classNameId, String key, int start, int end) {

		return findByG_C_K(groupId, classNameId, key, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByG_C_K(
		long groupId, long classNameId, String key, int start, int end,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		return findByG_C_K(
			groupId, classNameId, key, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByG_C_K(
		long groupId, long classNameId, String key, int start, int end,
		OrderByComparator<AssetListEntryUsage> orderByComparator,
		boolean useFinderCache) {

		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_K;
				finderArgs = new Object[] {groupId, classNameId, key};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_K;
			finderArgs = new Object[] {
				groupId, classNameId, key, start, end, orderByComparator
			};
		}

		List<AssetListEntryUsage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntryUsage>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntryUsage assetListEntryUsage : list) {
					if ((groupId != assetListEntryUsage.getGroupId()) ||
						(classNameId != assetListEntryUsage.getClassNameId()) ||
						!key.equals(assetListEntryUsage.getKey())) {

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

			sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_G_C_K_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_K_CLASSNAMEID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_G_C_K_KEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				if (bindKey) {
					queryPos.add(key);
				}

				list = (List<AssetListEntryUsage>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByG_C_K_First(
			long groupId, long classNameId, String key,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByG_C_K_First(
			groupId, classNameId, key, orderByComparator);

		if (assetListEntryUsage != null) {
			return assetListEntryUsage;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", key=");
		sb.append(key);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the first asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByG_C_K_First(
		long groupId, long classNameId, String key,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		List<AssetListEntryUsage> list = findByG_C_K(
			groupId, classNameId, key, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByG_C_K_Last(
			long groupId, long classNameId, String key,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByG_C_K_Last(
			groupId, classNameId, key, orderByComparator);

		if (assetListEntryUsage != null) {
			return assetListEntryUsage;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", key=");
		sb.append(key);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the last asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByG_C_K_Last(
		long groupId, long classNameId, String key,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		int count = countByG_C_K(groupId, classNameId, key);

		if (count == 0) {
			return null;
		}

		List<AssetListEntryUsage> list = findByG_C_K(
			groupId, classNameId, key, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry usages before and after the current asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * @param assetListEntryUsageId the primary key of the current asset list entry usage
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	@Override
	public AssetListEntryUsage[] findByG_C_K_PrevAndNext(
			long assetListEntryUsageId, long groupId, long classNameId,
			String key,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		key = Objects.toString(key, "");

		AssetListEntryUsage assetListEntryUsage = findByPrimaryKey(
			assetListEntryUsageId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntryUsage[] array = new AssetListEntryUsageImpl[3];

			array[0] = getByG_C_K_PrevAndNext(
				session, assetListEntryUsage, groupId, classNameId, key,
				orderByComparator, true);

			array[1] = assetListEntryUsage;

			array[2] = getByG_C_K_PrevAndNext(
				session, assetListEntryUsage, groupId, classNameId, key,
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

	protected AssetListEntryUsage getByG_C_K_PrevAndNext(
		Session session, AssetListEntryUsage assetListEntryUsage, long groupId,
		long classNameId, String key,
		OrderByComparator<AssetListEntryUsage> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

		sb.append(_FINDER_COLUMN_G_C_K_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_K_CLASSNAMEID_2);

		boolean bindKey = false;

		if (key.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_K_KEY_3);
		}
		else {
			bindKey = true;

			sb.append(_FINDER_COLUMN_G_C_K_KEY_2);
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
			sb.append(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		if (bindKey) {
			queryPos.add(key);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntryUsage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntryUsage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 */
	@Override
	public void removeByG_C_K(long groupId, long classNameId, String key) {
		for (AssetListEntryUsage assetListEntryUsage :
				findByG_C_K(
					groupId, classNameId, key, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(assetListEntryUsage);
		}
	}

	/**
	 * Returns the number of asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @return the number of matching asset list entry usages
	 */
	@Override
	public int countByG_C_K(long groupId, long classNameId, String key) {
		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_K;

			finderArgs = new Object[] {groupId, classNameId, key};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ASSETLISTENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_G_C_K_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_K_CLASSNAMEID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_G_C_K_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				if (bindKey) {
					queryPos.add(key);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_C_K_GROUPID_2 =
		"assetListEntryUsage.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_K_CLASSNAMEID_2 =
		"assetListEntryUsage.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_K_KEY_2 =
		"assetListEntryUsage.key = ?";

	private static final String _FINDER_COLUMN_G_C_K_KEY_3 =
		"(assetListEntryUsage.key IS NULL OR assetListEntryUsage.key = '')";

	private FinderPath _finderPathWithPaginationFindByCK_CT_P;
	private FinderPath _finderPathWithoutPaginationFindByCK_CT_P;
	private FinderPath _finderPathCountByCK_CT_P;

	/**
	 * Returns all the asset list entry usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid) {

		return findByCK_CT_P(
			containerKey, containerType, plid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start,
		int end) {

		return findByCK_CT_P(
			containerKey, containerType, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		return findByCK_CT_P(
			containerKey, containerType, plid, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end,
		OrderByComparator<AssetListEntryUsage> orderByComparator,
		boolean useFinderCache) {

		containerKey = Objects.toString(containerKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCK_CT_P;
				finderArgs = new Object[] {containerKey, containerType, plid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCK_CT_P;
			finderArgs = new Object[] {
				containerKey, containerType, plid, start, end, orderByComparator
			};
		}

		List<AssetListEntryUsage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntryUsage>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntryUsage assetListEntryUsage : list) {
					if (!containerKey.equals(
							assetListEntryUsage.getContainerKey()) ||
						(containerType !=
							assetListEntryUsage.getContainerType()) ||
						(plid != assetListEntryUsage.getPlid())) {

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

			sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

			boolean bindContainerKey = false;

			if (containerKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_CK_CT_P_CONTAINERKEY_3);
			}
			else {
				bindContainerKey = true;

				sb.append(_FINDER_COLUMN_CK_CT_P_CONTAINERKEY_2);
			}

			sb.append(_FINDER_COLUMN_CK_CT_P_CONTAINERTYPE_2);

			sb.append(_FINDER_COLUMN_CK_CT_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindContainerKey) {
					queryPos.add(containerKey);
				}

				queryPos.add(containerType);

				queryPos.add(plid);

				list = (List<AssetListEntryUsage>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first asset list entry usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByCK_CT_P_First(
			String containerKey, long containerType, long plid,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByCK_CT_P_First(
			containerKey, containerType, plid, orderByComparator);

		if (assetListEntryUsage != null) {
			return assetListEntryUsage;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("containerKey=");
		sb.append(containerKey);

		sb.append(", containerType=");
		sb.append(containerType);

		sb.append(", plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the first asset list entry usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByCK_CT_P_First(
		String containerKey, long containerType, long plid,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		List<AssetListEntryUsage> list = findByCK_CT_P(
			containerKey, containerType, plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByCK_CT_P_Last(
			String containerKey, long containerType, long plid,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByCK_CT_P_Last(
			containerKey, containerType, plid, orderByComparator);

		if (assetListEntryUsage != null) {
			return assetListEntryUsage;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("containerKey=");
		sb.append(containerKey);

		sb.append(", containerType=");
		sb.append(containerType);

		sb.append(", plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the last asset list entry usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByCK_CT_P_Last(
		String containerKey, long containerType, long plid,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		int count = countByCK_CT_P(containerKey, containerType, plid);

		if (count == 0) {
			return null;
		}

		List<AssetListEntryUsage> list = findByCK_CT_P(
			containerKey, containerType, plid, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry usages before and after the current asset list entry usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param assetListEntryUsageId the primary key of the current asset list entry usage
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	@Override
	public AssetListEntryUsage[] findByCK_CT_P_PrevAndNext(
			long assetListEntryUsageId, String containerKey, long containerType,
			long plid, OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		containerKey = Objects.toString(containerKey, "");

		AssetListEntryUsage assetListEntryUsage = findByPrimaryKey(
			assetListEntryUsageId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntryUsage[] array = new AssetListEntryUsageImpl[3];

			array[0] = getByCK_CT_P_PrevAndNext(
				session, assetListEntryUsage, containerKey, containerType, plid,
				orderByComparator, true);

			array[1] = assetListEntryUsage;

			array[2] = getByCK_CT_P_PrevAndNext(
				session, assetListEntryUsage, containerKey, containerType, plid,
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

	protected AssetListEntryUsage getByCK_CT_P_PrevAndNext(
		Session session, AssetListEntryUsage assetListEntryUsage,
		String containerKey, long containerType, long plid,
		OrderByComparator<AssetListEntryUsage> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

		boolean bindContainerKey = false;

		if (containerKey.isEmpty()) {
			sb.append(_FINDER_COLUMN_CK_CT_P_CONTAINERKEY_3);
		}
		else {
			bindContainerKey = true;

			sb.append(_FINDER_COLUMN_CK_CT_P_CONTAINERKEY_2);
		}

		sb.append(_FINDER_COLUMN_CK_CT_P_CONTAINERTYPE_2);

		sb.append(_FINDER_COLUMN_CK_CT_P_PLID_2);

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
			sb.append(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindContainerKey) {
			queryPos.add(containerKey);
		}

		queryPos.add(containerType);

		queryPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntryUsage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntryUsage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry usages where containerKey = &#63; and containerType = &#63; and plid = &#63; from the database.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 */
	@Override
	public void removeByCK_CT_P(
		String containerKey, long containerType, long plid) {

		for (AssetListEntryUsage assetListEntryUsage :
				findByCK_CT_P(
					containerKey, containerType, plid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(assetListEntryUsage);
		}
	}

	/**
	 * Returns the number of asset list entry usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the number of matching asset list entry usages
	 */
	@Override
	public int countByCK_CT_P(
		String containerKey, long containerType, long plid) {

		containerKey = Objects.toString(containerKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCK_CT_P;

			finderArgs = new Object[] {containerKey, containerType, plid};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ASSETLISTENTRYUSAGE_WHERE);

			boolean bindContainerKey = false;

			if (containerKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_CK_CT_P_CONTAINERKEY_3);
			}
			else {
				bindContainerKey = true;

				sb.append(_FINDER_COLUMN_CK_CT_P_CONTAINERKEY_2);
			}

			sb.append(_FINDER_COLUMN_CK_CT_P_CONTAINERTYPE_2);

			sb.append(_FINDER_COLUMN_CK_CT_P_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindContainerKey) {
					queryPos.add(containerKey);
				}

				queryPos.add(containerType);

				queryPos.add(plid);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_CK_CT_P_CONTAINERKEY_2 =
		"assetListEntryUsage.containerKey = ? AND ";

	private static final String _FINDER_COLUMN_CK_CT_P_CONTAINERKEY_3 =
		"(assetListEntryUsage.containerKey IS NULL OR assetListEntryUsage.containerKey = '') AND ";

	private static final String _FINDER_COLUMN_CK_CT_P_CONTAINERTYPE_2 =
		"assetListEntryUsage.containerType = ? AND ";

	private static final String _FINDER_COLUMN_CK_CT_P_PLID_2 =
		"assetListEntryUsage.plid = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_K_T;
	private FinderPath _finderPathWithoutPaginationFindByG_C_K_T;
	private FinderPath _finderPathCountByG_C_K_T;

	/**
	 * Returns all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @return the matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByG_C_K_T(
		long groupId, long classNameId, String key, int type) {

		return findByG_C_K_T(
			groupId, classNameId, key, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByG_C_K_T(
		long groupId, long classNameId, String key, int type, int start,
		int end) {

		return findByG_C_K_T(groupId, classNameId, key, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByG_C_K_T(
		long groupId, long classNameId, String key, int type, int start,
		int end, OrderByComparator<AssetListEntryUsage> orderByComparator) {

		return findByG_C_K_T(
			groupId, classNameId, key, type, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findByG_C_K_T(
		long groupId, long classNameId, String key, int type, int start,
		int end, OrderByComparator<AssetListEntryUsage> orderByComparator,
		boolean useFinderCache) {

		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_K_T;
				finderArgs = new Object[] {groupId, classNameId, key, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_K_T;
			finderArgs = new Object[] {
				groupId, classNameId, key, type, start, end, orderByComparator
			};
		}

		List<AssetListEntryUsage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntryUsage>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntryUsage assetListEntryUsage : list) {
					if ((groupId != assetListEntryUsage.getGroupId()) ||
						(classNameId != assetListEntryUsage.getClassNameId()) ||
						!key.equals(assetListEntryUsage.getKey()) ||
						(type != assetListEntryUsage.getType())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_G_C_K_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_K_T_CLASSNAMEID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_K_T_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_G_C_K_T_KEY_2);
			}

			sb.append(_FINDER_COLUMN_G_C_K_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				if (bindKey) {
					queryPos.add(key);
				}

				queryPos.add(type);

				list = (List<AssetListEntryUsage>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByG_C_K_T_First(
			long groupId, long classNameId, String key, int type,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByG_C_K_T_First(
			groupId, classNameId, key, type, orderByComparator);

		if (assetListEntryUsage != null) {
			return assetListEntryUsage;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", key=");
		sb.append(key);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the first asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByG_C_K_T_First(
		long groupId, long classNameId, String key, int type,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		List<AssetListEntryUsage> list = findByG_C_K_T(
			groupId, classNameId, key, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByG_C_K_T_Last(
			long groupId, long classNameId, String key, int type,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByG_C_K_T_Last(
			groupId, classNameId, key, type, orderByComparator);

		if (assetListEntryUsage != null) {
			return assetListEntryUsage;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", key=");
		sb.append(key);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the last asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByG_C_K_T_Last(
		long groupId, long classNameId, String key, int type,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		int count = countByG_C_K_T(groupId, classNameId, key, type);

		if (count == 0) {
			return null;
		}

		List<AssetListEntryUsage> list = findByG_C_K_T(
			groupId, classNameId, key, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry usages before and after the current asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param assetListEntryUsageId the primary key of the current asset list entry usage
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	@Override
	public AssetListEntryUsage[] findByG_C_K_T_PrevAndNext(
			long assetListEntryUsageId, long groupId, long classNameId,
			String key, int type,
			OrderByComparator<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		key = Objects.toString(key, "");

		AssetListEntryUsage assetListEntryUsage = findByPrimaryKey(
			assetListEntryUsageId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntryUsage[] array = new AssetListEntryUsageImpl[3];

			array[0] = getByG_C_K_T_PrevAndNext(
				session, assetListEntryUsage, groupId, classNameId, key, type,
				orderByComparator, true);

			array[1] = assetListEntryUsage;

			array[2] = getByG_C_K_T_PrevAndNext(
				session, assetListEntryUsage, groupId, classNameId, key, type,
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

	protected AssetListEntryUsage getByG_C_K_T_PrevAndNext(
		Session session, AssetListEntryUsage assetListEntryUsage, long groupId,
		long classNameId, String key, int type,
		OrderByComparator<AssetListEntryUsage> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

		sb.append(_FINDER_COLUMN_G_C_K_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_K_T_CLASSNAMEID_2);

		boolean bindKey = false;

		if (key.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_K_T_KEY_3);
		}
		else {
			bindKey = true;

			sb.append(_FINDER_COLUMN_G_C_K_T_KEY_2);
		}

		sb.append(_FINDER_COLUMN_G_C_K_T_TYPE_2);

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
			sb.append(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		if (bindKey) {
			queryPos.add(key);
		}

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntryUsage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntryUsage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 */
	@Override
	public void removeByG_C_K_T(
		long groupId, long classNameId, String key, int type) {

		for (AssetListEntryUsage assetListEntryUsage :
				findByG_C_K_T(
					groupId, classNameId, key, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(assetListEntryUsage);
		}
	}

	/**
	 * Returns the number of asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @return the number of matching asset list entry usages
	 */
	@Override
	public int countByG_C_K_T(
		long groupId, long classNameId, String key, int type) {

		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_K_T;

			finderArgs = new Object[] {groupId, classNameId, key, type};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_ASSETLISTENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_G_C_K_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_K_T_CLASSNAMEID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_K_T_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_G_C_K_T_KEY_2);
			}

			sb.append(_FINDER_COLUMN_G_C_K_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				if (bindKey) {
					queryPos.add(key);
				}

				queryPos.add(type);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_C_K_T_GROUPID_2 =
		"assetListEntryUsage.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_K_T_CLASSNAMEID_2 =
		"assetListEntryUsage.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_K_T_KEY_2 =
		"assetListEntryUsage.key = ? AND ";

	private static final String _FINDER_COLUMN_G_C_K_T_KEY_3 =
		"(assetListEntryUsage.key IS NULL OR assetListEntryUsage.key = '') AND ";

	private static final String _FINDER_COLUMN_G_C_K_T_TYPE_2 =
		"assetListEntryUsage.type = ?";

	private FinderPath _finderPathFetchByG_C_CK_CT_K_P;
	private FinderPath _finderPathCountByG_C_CK_CT_K_P;

	/**
	 * Returns the asset list entry usage where groupId = &#63; and classNameId = &#63; and containerKey = &#63; and containerType = &#63; and key = &#63; and plid = &#63; or throws a <code>NoSuchEntryUsageException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param key the key
	 * @param plid the plid
	 * @return the matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByG_C_CK_CT_K_P(
			long groupId, long classNameId, String containerKey,
			long containerType, String key, long plid)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByG_C_CK_CT_K_P(
			groupId, classNameId, containerKey, containerType, key, plid);

		if (assetListEntryUsage == null) {
			StringBundler sb = new StringBundler(14);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", containerKey=");
			sb.append(containerKey);

			sb.append(", containerType=");
			sb.append(containerType);

			sb.append(", key=");
			sb.append(key);

			sb.append(", plid=");
			sb.append(plid);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryUsageException(sb.toString());
		}

		return assetListEntryUsage;
	}

	/**
	 * Returns the asset list entry usage where groupId = &#63; and classNameId = &#63; and containerKey = &#63; and containerType = &#63; and key = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param key the key
	 * @param plid the plid
	 * @return the matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByG_C_CK_CT_K_P(
		long groupId, long classNameId, String containerKey, long containerType,
		String key, long plid) {

		return fetchByG_C_CK_CT_K_P(
			groupId, classNameId, containerKey, containerType, key, plid, true);
	}

	/**
	 * Returns the asset list entry usage where groupId = &#63; and classNameId = &#63; and containerKey = &#63; and containerType = &#63; and key = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param key the key
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByG_C_CK_CT_K_P(
		long groupId, long classNameId, String containerKey, long containerType,
		String key, long plid, boolean useFinderCache) {

		containerKey = Objects.toString(containerKey, "");
		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				groupId, classNameId, containerKey, containerType, key, plid
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_C_CK_CT_K_P, finderArgs);
		}

		if (result instanceof AssetListEntryUsage) {
			AssetListEntryUsage assetListEntryUsage =
				(AssetListEntryUsage)result;

			if ((groupId != assetListEntryUsage.getGroupId()) ||
				(classNameId != assetListEntryUsage.getClassNameId()) ||
				!Objects.equals(
					containerKey, assetListEntryUsage.getContainerKey()) ||
				(containerType != assetListEntryUsage.getContainerType()) ||
				!Objects.equals(key, assetListEntryUsage.getKey()) ||
				(plid != assetListEntryUsage.getPlid())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_G_C_CK_CT_K_P_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_CK_CT_K_P_CLASSNAMEID_2);

			boolean bindContainerKey = false;

			if (containerKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_CK_CT_K_P_CONTAINERKEY_3);
			}
			else {
				bindContainerKey = true;

				sb.append(_FINDER_COLUMN_G_C_CK_CT_K_P_CONTAINERKEY_2);
			}

			sb.append(_FINDER_COLUMN_G_C_CK_CT_K_P_CONTAINERTYPE_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_CK_CT_K_P_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_G_C_CK_CT_K_P_KEY_2);
			}

			sb.append(_FINDER_COLUMN_G_C_CK_CT_K_P_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				if (bindContainerKey) {
					queryPos.add(containerKey);
				}

				queryPos.add(containerType);

				if (bindKey) {
					queryPos.add(key);
				}

				queryPos.add(plid);

				List<AssetListEntryUsage> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_C_CK_CT_K_P, finderArgs, list);
					}
				}
				else {
					AssetListEntryUsage assetListEntryUsage = list.get(0);

					result = assetListEntryUsage;

					cacheResult(assetListEntryUsage);
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
			return (AssetListEntryUsage)result;
		}
	}

	/**
	 * Removes the asset list entry usage where groupId = &#63; and classNameId = &#63; and containerKey = &#63; and containerType = &#63; and key = &#63; and plid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param key the key
	 * @param plid the plid
	 * @return the asset list entry usage that was removed
	 */
	@Override
	public AssetListEntryUsage removeByG_C_CK_CT_K_P(
			long groupId, long classNameId, String containerKey,
			long containerType, String key, long plid)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = findByG_C_CK_CT_K_P(
			groupId, classNameId, containerKey, containerType, key, plid);

		return remove(assetListEntryUsage);
	}

	/**
	 * Returns the number of asset list entry usages where groupId = &#63; and classNameId = &#63; and containerKey = &#63; and containerType = &#63; and key = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param key the key
	 * @param plid the plid
	 * @return the number of matching asset list entry usages
	 */
	@Override
	public int countByG_C_CK_CT_K_P(
		long groupId, long classNameId, String containerKey, long containerType,
		String key, long plid) {

		containerKey = Objects.toString(containerKey, "");
		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_CK_CT_K_P;

			finderArgs = new Object[] {
				groupId, classNameId, containerKey, containerType, key, plid
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(7);

			sb.append(_SQL_COUNT_ASSETLISTENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_G_C_CK_CT_K_P_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_CK_CT_K_P_CLASSNAMEID_2);

			boolean bindContainerKey = false;

			if (containerKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_CK_CT_K_P_CONTAINERKEY_3);
			}
			else {
				bindContainerKey = true;

				sb.append(_FINDER_COLUMN_G_C_CK_CT_K_P_CONTAINERKEY_2);
			}

			sb.append(_FINDER_COLUMN_G_C_CK_CT_K_P_CONTAINERTYPE_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_CK_CT_K_P_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_G_C_CK_CT_K_P_KEY_2);
			}

			sb.append(_FINDER_COLUMN_G_C_CK_CT_K_P_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				if (bindContainerKey) {
					queryPos.add(containerKey);
				}

				queryPos.add(containerType);

				if (bindKey) {
					queryPos.add(key);
				}

				queryPos.add(plid);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_C_CK_CT_K_P_GROUPID_2 =
		"assetListEntryUsage.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_CK_CT_K_P_CLASSNAMEID_2 =
		"assetListEntryUsage.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_CK_CT_K_P_CONTAINERKEY_2 =
		"assetListEntryUsage.containerKey = ? AND ";

	private static final String _FINDER_COLUMN_G_C_CK_CT_K_P_CONTAINERKEY_3 =
		"(assetListEntryUsage.containerKey IS NULL OR assetListEntryUsage.containerKey = '') AND ";

	private static final String _FINDER_COLUMN_G_C_CK_CT_K_P_CONTAINERTYPE_2 =
		"assetListEntryUsage.containerType = ? AND ";

	private static final String _FINDER_COLUMN_G_C_CK_CT_K_P_KEY_2 =
		"assetListEntryUsage.key = ? AND ";

	private static final String _FINDER_COLUMN_G_C_CK_CT_K_P_KEY_3 =
		"(assetListEntryUsage.key IS NULL OR assetListEntryUsage.key = '') AND ";

	private static final String _FINDER_COLUMN_G_C_CK_CT_K_P_PLID_2 =
		"assetListEntryUsage.plid = ?";

	public AssetListEntryUsagePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("key", "key_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AssetListEntryUsage.class);

		setModelImplClass(AssetListEntryUsageImpl.class);
		setModelPKClass(long.class);

		setTable(AssetListEntryUsageTable.INSTANCE);
	}

	/**
	 * Caches the asset list entry usage in the entity cache if it is enabled.
	 *
	 * @param assetListEntryUsage the asset list entry usage
	 */
	@Override
	public void cacheResult(AssetListEntryUsage assetListEntryUsage) {
		if (assetListEntryUsage.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			AssetListEntryUsageImpl.class, assetListEntryUsage.getPrimaryKey(),
			assetListEntryUsage);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				assetListEntryUsage.getUuid(), assetListEntryUsage.getGroupId()
			},
			assetListEntryUsage);

		finderCache.putResult(
			_finderPathFetchByG_C_CK_CT_K_P,
			new Object[] {
				assetListEntryUsage.getGroupId(),
				assetListEntryUsage.getClassNameId(),
				assetListEntryUsage.getContainerKey(),
				assetListEntryUsage.getContainerType(),
				assetListEntryUsage.getKey(), assetListEntryUsage.getPlid()
			},
			assetListEntryUsage);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the asset list entry usages in the entity cache if it is enabled.
	 *
	 * @param assetListEntryUsages the asset list entry usages
	 */
	@Override
	public void cacheResult(List<AssetListEntryUsage> assetListEntryUsages) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (assetListEntryUsages.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AssetListEntryUsage assetListEntryUsage : assetListEntryUsages) {
			if (assetListEntryUsage.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					AssetListEntryUsageImpl.class,
					assetListEntryUsage.getPrimaryKey()) == null) {

				cacheResult(assetListEntryUsage);
			}
		}
	}

	/**
	 * Clears the cache for all asset list entry usages.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetListEntryUsageImpl.class);

		finderCache.clearCache(AssetListEntryUsageImpl.class);
	}

	/**
	 * Clears the cache for the asset list entry usage.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetListEntryUsage assetListEntryUsage) {
		entityCache.removeResult(
			AssetListEntryUsageImpl.class, assetListEntryUsage);
	}

	@Override
	public void clearCache(List<AssetListEntryUsage> assetListEntryUsages) {
		for (AssetListEntryUsage assetListEntryUsage : assetListEntryUsages) {
			entityCache.removeResult(
				AssetListEntryUsageImpl.class, assetListEntryUsage);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AssetListEntryUsageImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(AssetListEntryUsageImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetListEntryUsageModelImpl assetListEntryUsageModelImpl) {

		Object[] args = new Object[] {
			assetListEntryUsageModelImpl.getUuid(),
			assetListEntryUsageModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, assetListEntryUsageModelImpl);

		args = new Object[] {
			assetListEntryUsageModelImpl.getGroupId(),
			assetListEntryUsageModelImpl.getClassNameId(),
			assetListEntryUsageModelImpl.getContainerKey(),
			assetListEntryUsageModelImpl.getContainerType(),
			assetListEntryUsageModelImpl.getKey(),
			assetListEntryUsageModelImpl.getPlid()
		};

		finderCache.putResult(
			_finderPathCountByG_C_CK_CT_K_P, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByG_C_CK_CT_K_P, args,
			assetListEntryUsageModelImpl);
	}

	/**
	 * Creates a new asset list entry usage with the primary key. Does not add the asset list entry usage to the database.
	 *
	 * @param assetListEntryUsageId the primary key for the new asset list entry usage
	 * @return the new asset list entry usage
	 */
	@Override
	public AssetListEntryUsage create(long assetListEntryUsageId) {
		AssetListEntryUsage assetListEntryUsage = new AssetListEntryUsageImpl();

		assetListEntryUsage.setNew(true);
		assetListEntryUsage.setPrimaryKey(assetListEntryUsageId);

		String uuid = PortalUUIDUtil.generate();

		assetListEntryUsage.setUuid(uuid);

		assetListEntryUsage.setCompanyId(CompanyThreadLocal.getCompanyId());

		return assetListEntryUsage;
	}

	/**
	 * Removes the asset list entry usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryUsageId the primary key of the asset list entry usage
	 * @return the asset list entry usage that was removed
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	@Override
	public AssetListEntryUsage remove(long assetListEntryUsageId)
		throws NoSuchEntryUsageException {

		return remove((Serializable)assetListEntryUsageId);
	}

	/**
	 * Removes the asset list entry usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset list entry usage
	 * @return the asset list entry usage that was removed
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	@Override
	public AssetListEntryUsage remove(Serializable primaryKey)
		throws NoSuchEntryUsageException {

		Session session = null;

		try {
			session = openSession();

			AssetListEntryUsage assetListEntryUsage =
				(AssetListEntryUsage)session.get(
					AssetListEntryUsageImpl.class, primaryKey);

			if (assetListEntryUsage == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryUsageException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetListEntryUsage);
		}
		catch (NoSuchEntryUsageException noSuchEntityException) {
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
	protected AssetListEntryUsage removeImpl(
		AssetListEntryUsage assetListEntryUsage) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetListEntryUsage)) {
				assetListEntryUsage = (AssetListEntryUsage)session.get(
					AssetListEntryUsageImpl.class,
					assetListEntryUsage.getPrimaryKeyObj());
			}

			if ((assetListEntryUsage != null) &&
				ctPersistenceHelper.isRemove(assetListEntryUsage)) {

				session.delete(assetListEntryUsage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetListEntryUsage != null) {
			clearCache(assetListEntryUsage);
		}

		return assetListEntryUsage;
	}

	@Override
	public AssetListEntryUsage updateImpl(
		AssetListEntryUsage assetListEntryUsage) {

		boolean isNew = assetListEntryUsage.isNew();

		if (!(assetListEntryUsage instanceof AssetListEntryUsageModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetListEntryUsage.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					assetListEntryUsage);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetListEntryUsage proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetListEntryUsage implementation " +
					assetListEntryUsage.getClass());
		}

		AssetListEntryUsageModelImpl assetListEntryUsageModelImpl =
			(AssetListEntryUsageModelImpl)assetListEntryUsage;

		if (Validator.isNull(assetListEntryUsage.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetListEntryUsage.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (assetListEntryUsage.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetListEntryUsage.setCreateDate(date);
			}
			else {
				assetListEntryUsage.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!assetListEntryUsageModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetListEntryUsage.setModifiedDate(date);
			}
			else {
				assetListEntryUsage.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(assetListEntryUsage)) {
				if (!isNew) {
					session.evict(
						AssetListEntryUsageImpl.class,
						assetListEntryUsage.getPrimaryKeyObj());
				}

				session.save(assetListEntryUsage);
			}
			else {
				assetListEntryUsage = (AssetListEntryUsage)session.merge(
					assetListEntryUsage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetListEntryUsage.getCtCollectionId() != 0) {
			if (isNew) {
				assetListEntryUsage.setNew(false);
			}

			assetListEntryUsage.resetOriginalValues();

			return assetListEntryUsage;
		}

		entityCache.putResult(
			AssetListEntryUsageImpl.class, assetListEntryUsageModelImpl, false,
			true);

		cacheUniqueFindersCache(assetListEntryUsageModelImpl);

		if (isNew) {
			assetListEntryUsage.setNew(false);
		}

		assetListEntryUsage.resetOriginalValues();

		return assetListEntryUsage;
	}

	/**
	 * Returns the asset list entry usage with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset list entry usage
	 * @return the asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	@Override
	public AssetListEntryUsage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByPrimaryKey(primaryKey);

		if (assetListEntryUsage == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryUsageException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetListEntryUsage;
	}

	/**
	 * Returns the asset list entry usage with the primary key or throws a <code>NoSuchEntryUsageException</code> if it could not be found.
	 *
	 * @param assetListEntryUsageId the primary key of the asset list entry usage
	 * @return the asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	@Override
	public AssetListEntryUsage findByPrimaryKey(long assetListEntryUsageId)
		throws NoSuchEntryUsageException {

		return findByPrimaryKey((Serializable)assetListEntryUsageId);
	}

	/**
	 * Returns the asset list entry usage with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset list entry usage
	 * @return the asset list entry usage, or <code>null</code> if a asset list entry usage with the primary key could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(AssetListEntryUsage.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		AssetListEntryUsage assetListEntryUsage = null;

		Session session = null;

		try {
			session = openSession();

			assetListEntryUsage = (AssetListEntryUsage)session.get(
				AssetListEntryUsageImpl.class, primaryKey);

			if (assetListEntryUsage != null) {
				cacheResult(assetListEntryUsage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return assetListEntryUsage;
	}

	/**
	 * Returns the asset list entry usage with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetListEntryUsageId the primary key of the asset list entry usage
	 * @return the asset list entry usage, or <code>null</code> if a asset list entry usage with the primary key could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByPrimaryKey(long assetListEntryUsageId) {
		return fetchByPrimaryKey((Serializable)assetListEntryUsageId);
	}

	@Override
	public Map<Serializable, AssetListEntryUsage> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(AssetListEntryUsage.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetListEntryUsage> map =
			new HashMap<Serializable, AssetListEntryUsage>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetListEntryUsage assetListEntryUsage = fetchByPrimaryKey(
				primaryKey);

			if (assetListEntryUsage != null) {
				map.put(primaryKey, assetListEntryUsage);
			}

			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (AssetListEntryUsage assetListEntryUsage :
					(List<AssetListEntryUsage>)query.list()) {

				map.put(
					assetListEntryUsage.getPrimaryKeyObj(),
					assetListEntryUsage);

				cacheResult(assetListEntryUsage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the asset list entry usages.
	 *
	 * @return the asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findAll(
		int start, int end,
		OrderByComparator<AssetListEntryUsage> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset list entry usages
	 */
	@Override
	public List<AssetListEntryUsage> findAll(
		int start, int end,
		OrderByComparator<AssetListEntryUsage> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<AssetListEntryUsage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntryUsage>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETLISTENTRYUSAGE;

				sql = sql.concat(AssetListEntryUsageModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AssetListEntryUsage>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Removes all the asset list entry usages from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetListEntryUsage assetListEntryUsage : findAll()) {
			remove(assetListEntryUsage);
		}
	}

	/**
	 * Returns the number of asset list entry usages.
	 *
	 * @return the number of asset list entry usages
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_ASSETLISTENTRYUSAGE);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
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
		return "assetListEntryUsageId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETLISTENTRYUSAGE;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return AssetListEntryUsageModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AssetListEntryUsage";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("assetListEntryId");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("containerKey");
		ctStrictColumnNames.add("containerType");
		ctStrictColumnNames.add("key_");
		ctStrictColumnNames.add("plid");
		ctStrictColumnNames.add("portletId");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("assetListEntryUsageId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {
				"groupId", "classNameId", "containerKey", "containerType",
				"key_", "plid"
			});
	}

	/**
	 * Initializes the asset list entry usage persistence.
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

		_finderPathWithPaginationFindByAssetListEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetListEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"assetListEntryId"}, true);

		_finderPathWithoutPaginationFindByAssetListEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetListEntryId",
			new String[] {Long.class.getName()},
			new String[] {"assetListEntryId"}, true);

		_finderPathCountByAssetListEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetListEntryId", new String[] {Long.class.getName()},
			new String[] {"assetListEntryId"}, false);

		_finderPathWithPaginationFindByPlid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPlid",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"plid"}, true);

		_finderPathWithoutPaginationFindByPlid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPlid",
			new String[] {Long.class.getName()}, new String[] {"plid"}, true);

		_finderPathCountByPlid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPlid",
			new String[] {Long.class.getName()}, new String[] {"plid"}, false);

		_finderPathWithPaginationFindByA_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"assetListEntryId", "classNameId"}, true);

		_finderPathWithoutPaginationFindByA_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"assetListEntryId", "classNameId"}, true);

		_finderPathCountByA_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"assetListEntryId", "classNameId"}, false);

		_finderPathWithPaginationFindByG_C_K = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_K",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "classNameId", "key_"}, true);

		_finderPathWithoutPaginationFindByG_C_K = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_K",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "classNameId", "key_"}, true);

		_finderPathCountByG_C_K = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_K",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "classNameId", "key_"}, false);

		_finderPathWithPaginationFindByCK_CT_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCK_CT_P",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"containerKey", "containerType", "plid"}, true);

		_finderPathWithoutPaginationFindByCK_CT_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCK_CT_P",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			new String[] {"containerKey", "containerType", "plid"}, true);

		_finderPathCountByCK_CT_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCK_CT_P",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			new String[] {"containerKey", "containerType", "plid"}, false);

		_finderPathWithPaginationFindByG_C_K_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_K_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "classNameId", "key_", "type_"}, true);

		_finderPathWithoutPaginationFindByG_C_K_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_K_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			new String[] {"groupId", "classNameId", "key_", "type_"}, true);

		_finderPathCountByG_C_K_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_K_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			new String[] {"groupId", "classNameId", "key_", "type_"}, false);

		_finderPathFetchByG_C_CK_CT_K_P = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_CK_CT_K_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Long.class.getName(),
				String.class.getName(), Long.class.getName()
			},
			new String[] {
				"groupId", "classNameId", "containerKey", "containerType",
				"key_", "plid"
			},
			true);

		_finderPathCountByG_C_CK_CT_K_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_CK_CT_K_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Long.class.getName(),
				String.class.getName(), Long.class.getName()
			},
			new String[] {
				"groupId", "classNameId", "containerKey", "containerType",
				"key_", "plid"
			},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AssetListEntryUsageImpl.class.getName());
	}

	@Override
	@Reference(
		target = AssetListPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AssetListPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AssetListPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ASSETLISTENTRYUSAGE =
		"SELECT assetListEntryUsage FROM AssetListEntryUsage assetListEntryUsage";

	private static final String _SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE =
		"SELECT assetListEntryUsage FROM AssetListEntryUsage assetListEntryUsage WHERE ";

	private static final String _SQL_COUNT_ASSETLISTENTRYUSAGE =
		"SELECT COUNT(assetListEntryUsage) FROM AssetListEntryUsage assetListEntryUsage";

	private static final String _SQL_COUNT_ASSETLISTENTRYUSAGE_WHERE =
		"SELECT COUNT(assetListEntryUsage) FROM AssetListEntryUsage assetListEntryUsage WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "assetListEntryUsage.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetListEntryUsage exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetListEntryUsage exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetListEntryUsagePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "key", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private AssetListEntryUsageModelArgumentsResolver
		_assetListEntryUsageModelArgumentsResolver;

}