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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
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
@Component(service = AssetListEntryUsagePersistence.class)
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
				finderPath, finderArgs, this);

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

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
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
				_finderPathFetchByUUID_G, finderArgs, this);
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

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
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
				finderPath, finderArgs, this);

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

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
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
				finderPath, finderArgs, this);

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

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
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
				finderPath, finderArgs, this);

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

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
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

	private FinderPath _finderPathFetchByC_C_P;
	private FinderPath _finderPathCountByC_C_P;

	/**
	 * Returns the asset list entry usage where classNameId = &#63; and classPK = &#63; and portletId = &#63; or throws a <code>NoSuchEntryUsageException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param portletId the portlet ID
	 * @return the matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage findByC_C_P(
			long classNameId, long classPK, String portletId)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = fetchByC_C_P(
			classNameId, classPK, portletId);

		if (assetListEntryUsage == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", portletId=");
			sb.append(portletId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryUsageException(sb.toString());
		}

		return assetListEntryUsage;
	}

	/**
	 * Returns the asset list entry usage where classNameId = &#63; and classPK = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param portletId the portlet ID
	 * @return the matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByC_C_P(
		long classNameId, long classPK, String portletId) {

		return fetchByC_C_P(classNameId, classPK, portletId, true);
	}

	/**
	 * Returns the asset list entry usage where classNameId = &#63; and classPK = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param portletId the portlet ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	@Override
	public AssetListEntryUsage fetchByC_C_P(
		long classNameId, long classPK, String portletId,
		boolean useFinderCache) {

		portletId = Objects.toString(portletId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {classNameId, classPK, portletId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByC_C_P, finderArgs, this);
		}

		if (result instanceof AssetListEntryUsage) {
			AssetListEntryUsage assetListEntryUsage =
				(AssetListEntryUsage)result;

			if ((classNameId != assetListEntryUsage.getClassNameId()) ||
				(classPK != assetListEntryUsage.getClassPK()) ||
				!Objects.equals(
					portletId, assetListEntryUsage.getPortletId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_ASSETLISTENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_P_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_P_CLASSPK_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_C_C_P_PORTLETID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindPortletId) {
					queryPos.add(portletId);
				}

				List<AssetListEntryUsage> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByC_C_P, finderArgs, list);
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
	 * Removes the asset list entry usage where classNameId = &#63; and classPK = &#63; and portletId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param portletId the portlet ID
	 * @return the asset list entry usage that was removed
	 */
	@Override
	public AssetListEntryUsage removeByC_C_P(
			long classNameId, long classPK, String portletId)
		throws NoSuchEntryUsageException {

		AssetListEntryUsage assetListEntryUsage = findByC_C_P(
			classNameId, classPK, portletId);

		return remove(assetListEntryUsage);
	}

	/**
	 * Returns the number of asset list entry usages where classNameId = &#63; and classPK = &#63; and portletId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param portletId the portlet ID
	 * @return the number of matching asset list entry usages
	 */
	@Override
	public int countByC_C_P(long classNameId, long classPK, String portletId) {
		portletId = Objects.toString(portletId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C_P;

			finderArgs = new Object[] {classNameId, classPK, portletId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ASSETLISTENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_P_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_P_CLASSPK_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_C_C_P_PORTLETID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindPortletId) {
					queryPos.add(portletId);
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

	private static final String _FINDER_COLUMN_C_C_P_CLASSNAMEID_2 =
		"assetListEntryUsage.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_P_CLASSPK_2 =
		"assetListEntryUsage.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_P_PORTLETID_2 =
		"assetListEntryUsage.portletId = ?";

	private static final String _FINDER_COLUMN_C_C_P_PORTLETID_3 =
		"(assetListEntryUsage.portletId IS NULL OR assetListEntryUsage.portletId = '')";

	public AssetListEntryUsagePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

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
			assetListEntryUsage.resetOriginalValues();

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
			_finderPathFetchByC_C_P,
			new Object[] {
				assetListEntryUsage.getClassNameId(),
				assetListEntryUsage.getClassPK(),
				assetListEntryUsage.getPortletId()
			},
			assetListEntryUsage);

		assetListEntryUsage.resetOriginalValues();
	}

	/**
	 * Caches the asset list entry usages in the entity cache if it is enabled.
	 *
	 * @param assetListEntryUsages the asset list entry usages
	 */
	@Override
	public void cacheResult(List<AssetListEntryUsage> assetListEntryUsages) {
		for (AssetListEntryUsage assetListEntryUsage : assetListEntryUsages) {
			if (assetListEntryUsage.getCtCollectionId() != 0) {
				assetListEntryUsage.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					AssetListEntryUsageImpl.class,
					assetListEntryUsage.getPrimaryKey()) == null) {

				cacheResult(assetListEntryUsage);
			}
			else {
				assetListEntryUsage.resetOriginalValues();
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

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
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
			AssetListEntryUsageImpl.class, assetListEntryUsage.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(AssetListEntryUsageModelImpl)assetListEntryUsage, true);
	}

	@Override
	public void clearCache(List<AssetListEntryUsage> assetListEntryUsages) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetListEntryUsage assetListEntryUsage : assetListEntryUsages) {
			entityCache.removeResult(
				AssetListEntryUsageImpl.class,
				assetListEntryUsage.getPrimaryKey());

			clearUniqueFindersCache(
				(AssetListEntryUsageModelImpl)assetListEntryUsage, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

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

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, assetListEntryUsageModelImpl,
			false);

		args = new Object[] {
			assetListEntryUsageModelImpl.getClassNameId(),
			assetListEntryUsageModelImpl.getClassPK(),
			assetListEntryUsageModelImpl.getPortletId()
		};

		finderCache.putResult(
			_finderPathCountByC_C_P, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_C_P, args, assetListEntryUsageModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AssetListEntryUsageModelImpl assetListEntryUsageModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetListEntryUsageModelImpl.getUuid(),
				assetListEntryUsageModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((assetListEntryUsageModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetListEntryUsageModelImpl.getOriginalUuid(),
				assetListEntryUsageModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetListEntryUsageModelImpl.getClassNameId(),
				assetListEntryUsageModelImpl.getClassPK(),
				assetListEntryUsageModelImpl.getPortletId()
			};

			finderCache.removeResult(_finderPathCountByC_C_P, args);
			finderCache.removeResult(_finderPathFetchByC_C_P, args);
		}

		if ((assetListEntryUsageModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_C_P.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetListEntryUsageModelImpl.getOriginalClassNameId(),
				assetListEntryUsageModelImpl.getOriginalClassPK(),
				assetListEntryUsageModelImpl.getOriginalPortletId()
			};

			finderCache.removeResult(_finderPathCountByC_C_P, args);
			finderCache.removeResult(_finderPathFetchByC_C_P, args);
		}
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

		if (!ctPersistenceHelper.isRemove(assetListEntryUsage)) {
			return assetListEntryUsage;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetListEntryUsage)) {
				assetListEntryUsage = (AssetListEntryUsage)session.get(
					AssetListEntryUsageImpl.class,
					assetListEntryUsage.getPrimaryKeyObj());
			}

			if (assetListEntryUsage != null) {
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

		Date now = new Date();

		if (isNew && (assetListEntryUsage.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetListEntryUsage.setCreateDate(now);
			}
			else {
				assetListEntryUsage.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!assetListEntryUsageModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetListEntryUsage.setModifiedDate(now);
			}
			else {
				assetListEntryUsage.setModifiedDate(
					serviceContext.getModifiedDate(now));
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

				assetListEntryUsage.setNew(false);
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
			assetListEntryUsage.resetOriginalValues();

			return assetListEntryUsage;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				assetListEntryUsageModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				assetListEntryUsageModelImpl.getUuid(),
				assetListEntryUsageModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				assetListEntryUsageModelImpl.getAssetListEntryId()
			};

			finderCache.removeResult(_finderPathCountByAssetListEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAssetListEntryId, args);

			args = new Object[] {
				assetListEntryUsageModelImpl.getAssetListEntryId(),
				assetListEntryUsageModelImpl.getClassNameId()
			};

			finderCache.removeResult(_finderPathCountByA_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByA_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((assetListEntryUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetListEntryUsageModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {assetListEntryUsageModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((assetListEntryUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetListEntryUsageModelImpl.getOriginalUuid(),
					assetListEntryUsageModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					assetListEntryUsageModelImpl.getUuid(),
					assetListEntryUsageModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((assetListEntryUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAssetListEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetListEntryUsageModelImpl.getOriginalAssetListEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAssetListEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetListEntryId, args);

				args = new Object[] {
					assetListEntryUsageModelImpl.getAssetListEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAssetListEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetListEntryId, args);
			}

			if ((assetListEntryUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByA_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetListEntryUsageModelImpl.getOriginalAssetListEntryId(),
					assetListEntryUsageModelImpl.getOriginalClassNameId()
				};

				finderCache.removeResult(_finderPathCountByA_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByA_C, args);

				args = new Object[] {
					assetListEntryUsageModelImpl.getAssetListEntryId(),
					assetListEntryUsageModelImpl.getClassNameId()
				};

				finderCache.removeResult(_finderPathCountByA_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByA_C, args);
			}
		}

		entityCache.putResult(
			AssetListEntryUsageImpl.class, assetListEntryUsage.getPrimaryKey(),
			assetListEntryUsage, false);

		clearUniqueFindersCache(assetListEntryUsageModelImpl, false);
		cacheUniqueFindersCache(assetListEntryUsageModelImpl);

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

		StringBundler sb = new StringBundler(primaryKeys.size() * 2 + 1);

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
				finderPath, finderArgs, this);
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
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
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

		return _ctColumnNamesMap.get(ctColumnResolutionType);
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
		Set<String> ctMergeColumnNames = new HashSet<String>();
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
		ctStrictColumnNames.add("portletId");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("assetListEntryUsageId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"classNameId", "classPK", "portletId"});
	}

	/**
	 * Initializes the asset list entry usage persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			AssetListEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AssetListEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			AssetListEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			AssetListEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			AssetListEntryUsageModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			AssetListEntryUsageImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			AssetListEntryUsageModelImpl.UUID_COLUMN_BITMASK |
			AssetListEntryUsageModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			AssetListEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			AssetListEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			AssetListEntryUsageModelImpl.UUID_COLUMN_BITMASK |
			AssetListEntryUsageModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByAssetListEntryId = new FinderPath(
			AssetListEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetListEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAssetListEntryId = new FinderPath(
			AssetListEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetListEntryId",
			new String[] {Long.class.getName()},
			AssetListEntryUsageModelImpl.ASSETLISTENTRYID_COLUMN_BITMASK);

		_finderPathCountByAssetListEntryId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetListEntryId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByA_C = new FinderPath(
			AssetListEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByA_C = new FinderPath(
			AssetListEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			AssetListEntryUsageModelImpl.ASSETLISTENTRYID_COLUMN_BITMASK |
			AssetListEntryUsageModelImpl.CLASSNAMEID_COLUMN_BITMASK);

		_finderPathCountByA_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByC_C_P = new FinderPath(
			AssetListEntryUsageImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			AssetListEntryUsageModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AssetListEntryUsageModelImpl.CLASSPK_COLUMN_BITMASK |
			AssetListEntryUsageModelImpl.PORTLETID_COLUMN_BITMASK);

		_finderPathCountByC_C_P = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AssetListEntryUsageImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
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
		new String[] {"uuid"});

	static {
		try {
			Class.forName(AssetListPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}