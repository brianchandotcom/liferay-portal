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

package com.liferay.asset.service.persistence.impl;

import com.liferay.asset.exception.NoSuchEntryUsageException;
import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.model.AssetEntryUsageTable;
import com.liferay.asset.model.impl.AssetEntryUsageImpl;
import com.liferay.asset.model.impl.AssetEntryUsageModelImpl;
import com.liferay.asset.service.persistence.AssetEntryUsagePersistence;
import com.liferay.asset.service.persistence.impl.constants.AssetPersistenceConstants;
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
 * The persistence implementation for the asset entry usage service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Mueller (7.2.x), replaced by {@link
 com.liferay.layout.model.impl.LayoutClassedModelUsageImpl}
 * @generated
 */
@Component(service = AssetEntryUsagePersistence.class)
@Deprecated
public class AssetEntryUsagePersistenceImpl
	extends BasePersistenceImpl<AssetEntryUsage>
	implements AssetEntryUsagePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetEntryUsageUtil</code> to access the asset entry usage persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetEntryUsageImpl.class.getName();

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
	 * Returns all the asset entry usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @return the range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

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

		List<AssetEntryUsage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetEntryUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryUsage assetEntryUsage : list) {
					if (!uuid.equals(assetEntryUsage.getUuid())) {
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

			sb.append(_SQL_SELECT_ASSETENTRYUSAGE_WHERE);

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
				sb.append(AssetEntryUsageModelImpl.ORDER_BY_JPQL);
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

				list = (List<AssetEntryUsage>)QueryUtil.list(
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
	 * Returns the first asset entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage findByUuid_First(
			String uuid, OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = fetchByUuid_First(
			uuid, orderByComparator);

		if (assetEntryUsage != null) {
			return assetEntryUsage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the first asset entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage fetchByUuid_First(
		String uuid, OrderByComparator<AssetEntryUsage> orderByComparator) {

		List<AssetEntryUsage> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage findByUuid_Last(
			String uuid, OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = fetchByUuid_Last(
			uuid, orderByComparator);

		if (assetEntryUsage != null) {
			return assetEntryUsage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the last asset entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage fetchByUuid_Last(
		String uuid, OrderByComparator<AssetEntryUsage> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AssetEntryUsage> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry usages before and after the current asset entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param assetEntryUsageId the primary key of the current asset entry usage
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry usage
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	@Override
	public AssetEntryUsage[] findByUuid_PrevAndNext(
			long assetEntryUsageId, String uuid,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		uuid = Objects.toString(uuid, "");

		AssetEntryUsage assetEntryUsage = findByPrimaryKey(assetEntryUsageId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryUsage[] array = new AssetEntryUsageImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, assetEntryUsage, uuid, orderByComparator, true);

			array[1] = assetEntryUsage;

			array[2] = getByUuid_PrevAndNext(
				session, assetEntryUsage, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntryUsage getByUuid_PrevAndNext(
		Session session, AssetEntryUsage assetEntryUsage, String uuid,
		OrderByComparator<AssetEntryUsage> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETENTRYUSAGE_WHERE);

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
			sb.append(AssetEntryUsageModelImpl.ORDER_BY_JPQL);
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
						assetEntryUsage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntryUsage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry usages where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AssetEntryUsage assetEntryUsage :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetEntryUsage);
		}
	}

	/**
	 * Returns the number of asset entry usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset entry usages
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

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

			sb.append(_SQL_COUNT_ASSETENTRYUSAGE_WHERE);

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
		"assetEntryUsage.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(assetEntryUsage.uuid IS NULL OR assetEntryUsage.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the asset entry usage where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryUsageException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = fetchByUUID_G(uuid, groupId);

		if (assetEntryUsage == null) {
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

		return assetEntryUsage;
	}

	/**
	 * Returns the asset entry usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the asset entry usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof AssetEntryUsage) {
			AssetEntryUsage assetEntryUsage = (AssetEntryUsage)result;

			if (!Objects.equals(uuid, assetEntryUsage.getUuid()) ||
				(groupId != assetEntryUsage.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ASSETENTRYUSAGE_WHERE);

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

				List<AssetEntryUsage> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					AssetEntryUsage assetEntryUsage = list.get(0);

					result = assetEntryUsage;

					cacheResult(assetEntryUsage);
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
			return (AssetEntryUsage)result;
		}
	}

	/**
	 * Removes the asset entry usage where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset entry usage that was removed
	 */
	@Override
	public AssetEntryUsage removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = findByUUID_G(uuid, groupId);

		return remove(assetEntryUsage);
	}

	/**
	 * Returns the number of asset entry usages where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset entry usages
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

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

			sb.append(_SQL_COUNT_ASSETENTRYUSAGE_WHERE);

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
		"assetEntryUsage.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(assetEntryUsage.uuid IS NULL OR assetEntryUsage.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"assetEntryUsage.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the asset entry usages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry usages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @return the range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

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

		List<AssetEntryUsage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetEntryUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryUsage assetEntryUsage : list) {
					if (!uuid.equals(assetEntryUsage.getUuid()) ||
						(companyId != assetEntryUsage.getCompanyId())) {

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

			sb.append(_SQL_SELECT_ASSETENTRYUSAGE_WHERE);

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
				sb.append(AssetEntryUsageModelImpl.ORDER_BY_JPQL);
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

				list = (List<AssetEntryUsage>)QueryUtil.list(
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
	 * Returns the first asset entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (assetEntryUsage != null) {
			return assetEntryUsage;
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
	 * Returns the first asset entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		List<AssetEntryUsage> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (assetEntryUsage != null) {
			return assetEntryUsage;
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
	 * Returns the last asset entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AssetEntryUsage> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry usages before and after the current asset entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetEntryUsageId the primary key of the current asset entry usage
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry usage
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	@Override
	public AssetEntryUsage[] findByUuid_C_PrevAndNext(
			long assetEntryUsageId, String uuid, long companyId,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		uuid = Objects.toString(uuid, "");

		AssetEntryUsage assetEntryUsage = findByPrimaryKey(assetEntryUsageId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryUsage[] array = new AssetEntryUsageImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, assetEntryUsage, uuid, companyId, orderByComparator,
				true);

			array[1] = assetEntryUsage;

			array[2] = getByUuid_C_PrevAndNext(
				session, assetEntryUsage, uuid, companyId, orderByComparator,
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

	protected AssetEntryUsage getByUuid_C_PrevAndNext(
		Session session, AssetEntryUsage assetEntryUsage, String uuid,
		long companyId, OrderByComparator<AssetEntryUsage> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETENTRYUSAGE_WHERE);

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
			sb.append(AssetEntryUsageModelImpl.ORDER_BY_JPQL);
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
						assetEntryUsage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntryUsage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry usages where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AssetEntryUsage assetEntryUsage :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetEntryUsage);
		}
	}

	/**
	 * Returns the number of asset entry usages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset entry usages
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

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

			sb.append(_SQL_COUNT_ASSETENTRYUSAGE_WHERE);

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
		"assetEntryUsage.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(assetEntryUsage.uuid IS NULL OR assetEntryUsage.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"assetEntryUsage.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByAssetEntryId;
	private FinderPath _finderPathWithoutPaginationFindByAssetEntryId;
	private FinderPath _finderPathCountByAssetEntryId;

	/**
	 * Returns all the asset entry usages where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByAssetEntryId(long assetEntryId) {
		return findByAssetEntryId(
			assetEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry usages where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @return the range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByAssetEntryId(
		long assetEntryId, int start, int end) {

		return findByAssetEntryId(assetEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return findByAssetEntryId(
			assetEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByAssetEntryId;
				finderArgs = new Object[] {assetEntryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByAssetEntryId;
			finderArgs = new Object[] {
				assetEntryId, start, end, orderByComparator
			};
		}

		List<AssetEntryUsage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetEntryUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryUsage assetEntryUsage : list) {
					if (assetEntryId != assetEntryUsage.getAssetEntryId()) {
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

			sb.append(_SQL_SELECT_ASSETENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntryUsageModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetEntryId);

				list = (List<AssetEntryUsage>)QueryUtil.list(
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
	 * Returns the first asset entry usage in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage findByAssetEntryId_First(
			long assetEntryId,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = fetchByAssetEntryId_First(
			assetEntryId, orderByComparator);

		if (assetEntryUsage != null) {
			return assetEntryUsage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetEntryId=");
		sb.append(assetEntryId);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the first asset entry usage in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage fetchByAssetEntryId_First(
		long assetEntryId,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		List<AssetEntryUsage> list = findByAssetEntryId(
			assetEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry usage in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage findByAssetEntryId_Last(
			long assetEntryId,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = fetchByAssetEntryId_Last(
			assetEntryId, orderByComparator);

		if (assetEntryUsage != null) {
			return assetEntryUsage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetEntryId=");
		sb.append(assetEntryId);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the last asset entry usage in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage fetchByAssetEntryId_Last(
		long assetEntryId,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		int count = countByAssetEntryId(assetEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetEntryUsage> list = findByAssetEntryId(
			assetEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry usages before and after the current asset entry usage in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryUsageId the primary key of the current asset entry usage
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry usage
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	@Override
	public AssetEntryUsage[] findByAssetEntryId_PrevAndNext(
			long assetEntryUsageId, long assetEntryId,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = findByPrimaryKey(assetEntryUsageId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryUsage[] array = new AssetEntryUsageImpl[3];

			array[0] = getByAssetEntryId_PrevAndNext(
				session, assetEntryUsage, assetEntryId, orderByComparator,
				true);

			array[1] = assetEntryUsage;

			array[2] = getByAssetEntryId_PrevAndNext(
				session, assetEntryUsage, assetEntryId, orderByComparator,
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

	protected AssetEntryUsage getByAssetEntryId_PrevAndNext(
		Session session, AssetEntryUsage assetEntryUsage, long assetEntryId,
		OrderByComparator<AssetEntryUsage> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETENTRYUSAGE_WHERE);

		sb.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

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
			sb.append(AssetEntryUsageModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(assetEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntryUsage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntryUsage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry usages where assetEntryId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 */
	@Override
	public void removeByAssetEntryId(long assetEntryId) {
		for (AssetEntryUsage assetEntryUsage :
				findByAssetEntryId(
					assetEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetEntryUsage);
		}
	}

	/**
	 * Returns the number of asset entry usages where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the number of matching asset entry usages
	 */
	@Override
	public int countByAssetEntryId(long assetEntryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByAssetEntryId;

			finderArgs = new Object[] {assetEntryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetEntryId);

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

	private static final String _FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2 =
		"assetEntryUsage.assetEntryId = ? AND assetEntryUsage.containerKey IS NOT NULL";

	private FinderPath _finderPathWithPaginationFindByPlid;
	private FinderPath _finderPathWithoutPaginationFindByPlid;
	private FinderPath _finderPathCountByPlid;

	/**
	 * Returns all the asset entry usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByPlid(long plid) {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @return the range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByPlid(long plid, int start, int end) {
		return findByPlid(plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByPlid(
		long plid, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return findByPlid(plid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByPlid(
		long plid, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

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

		List<AssetEntryUsage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetEntryUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryUsage assetEntryUsage : list) {
					if (plid != assetEntryUsage.getPlid()) {
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

			sb.append(_SQL_SELECT_ASSETENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_PLID_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntryUsageModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(plid);

				list = (List<AssetEntryUsage>)QueryUtil.list(
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
	 * Returns the first asset entry usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage findByPlid_First(
			long plid, OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = fetchByPlid_First(
			plid, orderByComparator);

		if (assetEntryUsage != null) {
			return assetEntryUsage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the first asset entry usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage fetchByPlid_First(
		long plid, OrderByComparator<AssetEntryUsage> orderByComparator) {

		List<AssetEntryUsage> list = findByPlid(plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage findByPlid_Last(
			long plid, OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = fetchByPlid_Last(
			plid, orderByComparator);

		if (assetEntryUsage != null) {
			return assetEntryUsage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the last asset entry usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage fetchByPlid_Last(
		long plid, OrderByComparator<AssetEntryUsage> orderByComparator) {

		int count = countByPlid(plid);

		if (count == 0) {
			return null;
		}

		List<AssetEntryUsage> list = findByPlid(
			plid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry usages before and after the current asset entry usage in the ordered set where plid = &#63;.
	 *
	 * @param assetEntryUsageId the primary key of the current asset entry usage
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry usage
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	@Override
	public AssetEntryUsage[] findByPlid_PrevAndNext(
			long assetEntryUsageId, long plid,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = findByPrimaryKey(assetEntryUsageId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryUsage[] array = new AssetEntryUsageImpl[3];

			array[0] = getByPlid_PrevAndNext(
				session, assetEntryUsage, plid, orderByComparator, true);

			array[1] = assetEntryUsage;

			array[2] = getByPlid_PrevAndNext(
				session, assetEntryUsage, plid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntryUsage getByPlid_PrevAndNext(
		Session session, AssetEntryUsage assetEntryUsage, long plid,
		OrderByComparator<AssetEntryUsage> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETENTRYUSAGE_WHERE);

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
			sb.append(AssetEntryUsageModelImpl.ORDER_BY_JPQL);
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
						assetEntryUsage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntryUsage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry usages where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	@Override
	public void removeByPlid(long plid) {
		for (AssetEntryUsage assetEntryUsage :
				findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetEntryUsage);
		}
	}

	/**
	 * Returns the number of asset entry usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching asset entry usages
	 */
	@Override
	public int countByPlid(long plid) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByPlid;

			finderArgs = new Object[] {plid};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETENTRYUSAGE_WHERE);

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
		"assetEntryUsage.plid = ? AND assetEntryUsage.containerKey IS NOT NULL";

	private FinderPath _finderPathWithPaginationFindByA_T;
	private FinderPath _finderPathWithoutPaginationFindByA_T;
	private FinderPath _finderPathCountByA_T;

	/**
	 * Returns all the asset entry usages where assetEntryId = &#63; and type = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @return the matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByA_T(long assetEntryId, int type) {
		return findByA_T(
			assetEntryId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry usages where assetEntryId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @return the range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByA_T(
		long assetEntryId, int type, int start, int end) {

		return findByA_T(assetEntryId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where assetEntryId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByA_T(
		long assetEntryId, int type, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return findByA_T(
			assetEntryId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where assetEntryId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByA_T(
		long assetEntryId, int type, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByA_T;
				finderArgs = new Object[] {assetEntryId, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByA_T;
			finderArgs = new Object[] {
				assetEntryId, type, start, end, orderByComparator
			};
		}

		List<AssetEntryUsage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetEntryUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryUsage assetEntryUsage : list) {
					if ((assetEntryId != assetEntryUsage.getAssetEntryId()) ||
						(type != assetEntryUsage.getType())) {

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

			sb.append(_SQL_SELECT_ASSETENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_A_T_ASSETENTRYID_2);

			sb.append(_FINDER_COLUMN_A_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntryUsageModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetEntryId);

				queryPos.add(type);

				list = (List<AssetEntryUsage>)QueryUtil.list(
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
	 * Returns the first asset entry usage in the ordered set where assetEntryId = &#63; and type = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage findByA_T_First(
			long assetEntryId, int type,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = fetchByA_T_First(
			assetEntryId, type, orderByComparator);

		if (assetEntryUsage != null) {
			return assetEntryUsage;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetEntryId=");
		sb.append(assetEntryId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the first asset entry usage in the ordered set where assetEntryId = &#63; and type = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage fetchByA_T_First(
		long assetEntryId, int type,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		List<AssetEntryUsage> list = findByA_T(
			assetEntryId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry usage in the ordered set where assetEntryId = &#63; and type = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage findByA_T_Last(
			long assetEntryId, int type,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = fetchByA_T_Last(
			assetEntryId, type, orderByComparator);

		if (assetEntryUsage != null) {
			return assetEntryUsage;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetEntryId=");
		sb.append(assetEntryId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the last asset entry usage in the ordered set where assetEntryId = &#63; and type = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage fetchByA_T_Last(
		long assetEntryId, int type,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		int count = countByA_T(assetEntryId, type);

		if (count == 0) {
			return null;
		}

		List<AssetEntryUsage> list = findByA_T(
			assetEntryId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry usages before and after the current asset entry usage in the ordered set where assetEntryId = &#63; and type = &#63;.
	 *
	 * @param assetEntryUsageId the primary key of the current asset entry usage
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry usage
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	@Override
	public AssetEntryUsage[] findByA_T_PrevAndNext(
			long assetEntryUsageId, long assetEntryId, int type,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = findByPrimaryKey(assetEntryUsageId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryUsage[] array = new AssetEntryUsageImpl[3];

			array[0] = getByA_T_PrevAndNext(
				session, assetEntryUsage, assetEntryId, type, orderByComparator,
				true);

			array[1] = assetEntryUsage;

			array[2] = getByA_T_PrevAndNext(
				session, assetEntryUsage, assetEntryId, type, orderByComparator,
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

	protected AssetEntryUsage getByA_T_PrevAndNext(
		Session session, AssetEntryUsage assetEntryUsage, long assetEntryId,
		int type, OrderByComparator<AssetEntryUsage> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETENTRYUSAGE_WHERE);

		sb.append(_FINDER_COLUMN_A_T_ASSETENTRYID_2);

		sb.append(_FINDER_COLUMN_A_T_TYPE_2);

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
			sb.append(AssetEntryUsageModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(assetEntryId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntryUsage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntryUsage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry usages where assetEntryId = &#63; and type = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 */
	@Override
	public void removeByA_T(long assetEntryId, int type) {
		for (AssetEntryUsage assetEntryUsage :
				findByA_T(
					assetEntryId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetEntryUsage);
		}
	}

	/**
	 * Returns the number of asset entry usages where assetEntryId = &#63; and type = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @return the number of matching asset entry usages
	 */
	@Override
	public int countByA_T(long assetEntryId, int type) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByA_T;

			finderArgs = new Object[] {assetEntryId, type};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_A_T_ASSETENTRYID_2);

			sb.append(_FINDER_COLUMN_A_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetEntryId);

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

	private static final String _FINDER_COLUMN_A_T_ASSETENTRYID_2 =
		"assetEntryUsage.assetEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_T_TYPE_2 =
		"assetEntryUsage.type = ? AND assetEntryUsage.containerKey IS NOT NULL";

	private FinderPath _finderPathWithPaginationFindByC_C_P;
	private FinderPath _finderPathWithoutPaginationFindByC_C_P;
	private FinderPath _finderPathCountByC_C_P;

	/**
	 * Returns all the asset entry usages where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @return the matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByC_C_P(
		long containerType, String containerKey, long plid) {

		return findByC_C_P(
			containerType, containerKey, plid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry usages where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @return the range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByC_C_P(
		long containerType, String containerKey, long plid, int start,
		int end) {

		return findByC_C_P(containerType, containerKey, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByC_C_P(
		long containerType, String containerKey, long plid, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return findByC_C_P(
			containerType, containerKey, plid, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findByC_C_P(
		long containerType, String containerKey, long plid, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean useFinderCache) {

		containerKey = Objects.toString(containerKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C_P;
				finderArgs = new Object[] {containerType, containerKey, plid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C_P;
			finderArgs = new Object[] {
				containerType, containerKey, plid, start, end, orderByComparator
			};
		}

		List<AssetEntryUsage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetEntryUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryUsage assetEntryUsage : list) {
					if ((containerType != assetEntryUsage.getContainerType()) ||
						!containerKey.equals(
							assetEntryUsage.getContainerKey()) ||
						(plid != assetEntryUsage.getPlid())) {

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

			sb.append(_SQL_SELECT_ASSETENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_P_CONTAINERTYPE_2);

			boolean bindContainerKey = false;

			if (containerKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_P_CONTAINERKEY_3);
			}
			else {
				bindContainerKey = true;

				sb.append(_FINDER_COLUMN_C_C_P_CONTAINERKEY_2);
			}

			sb.append(_FINDER_COLUMN_C_C_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntryUsageModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(containerType);

				if (bindContainerKey) {
					queryPos.add(containerKey);
				}

				queryPos.add(plid);

				list = (List<AssetEntryUsage>)QueryUtil.list(
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
	 * Returns the first asset entry usage in the ordered set where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage findByC_C_P_First(
			long containerType, String containerKey, long plid,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = fetchByC_C_P_First(
			containerType, containerKey, plid, orderByComparator);

		if (assetEntryUsage != null) {
			return assetEntryUsage;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("containerType=");
		sb.append(containerType);

		sb.append(", containerKey=");
		sb.append(containerKey);

		sb.append(", plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the first asset entry usage in the ordered set where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage fetchByC_C_P_First(
		long containerType, String containerKey, long plid,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		List<AssetEntryUsage> list = findByC_C_P(
			containerType, containerKey, plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry usage in the ordered set where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage findByC_C_P_Last(
			long containerType, String containerKey, long plid,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = fetchByC_C_P_Last(
			containerType, containerKey, plid, orderByComparator);

		if (assetEntryUsage != null) {
			return assetEntryUsage;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("containerType=");
		sb.append(containerType);

		sb.append(", containerKey=");
		sb.append(containerKey);

		sb.append(", plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchEntryUsageException(sb.toString());
	}

	/**
	 * Returns the last asset entry usage in the ordered set where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage fetchByC_C_P_Last(
		long containerType, String containerKey, long plid,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		int count = countByC_C_P(containerType, containerKey, plid);

		if (count == 0) {
			return null;
		}

		List<AssetEntryUsage> list = findByC_C_P(
			containerType, containerKey, plid, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry usages before and after the current asset entry usage in the ordered set where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * @param assetEntryUsageId the primary key of the current asset entry usage
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry usage
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	@Override
	public AssetEntryUsage[] findByC_C_P_PrevAndNext(
			long assetEntryUsageId, long containerType, String containerKey,
			long plid, OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException {

		containerKey = Objects.toString(containerKey, "");

		AssetEntryUsage assetEntryUsage = findByPrimaryKey(assetEntryUsageId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryUsage[] array = new AssetEntryUsageImpl[3];

			array[0] = getByC_C_P_PrevAndNext(
				session, assetEntryUsage, containerType, containerKey, plid,
				orderByComparator, true);

			array[1] = assetEntryUsage;

			array[2] = getByC_C_P_PrevAndNext(
				session, assetEntryUsage, containerType, containerKey, plid,
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

	protected AssetEntryUsage getByC_C_P_PrevAndNext(
		Session session, AssetEntryUsage assetEntryUsage, long containerType,
		String containerKey, long plid,
		OrderByComparator<AssetEntryUsage> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETENTRYUSAGE_WHERE);

		sb.append(_FINDER_COLUMN_C_C_P_CONTAINERTYPE_2);

		boolean bindContainerKey = false;

		if (containerKey.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_C_P_CONTAINERKEY_3);
		}
		else {
			bindContainerKey = true;

			sb.append(_FINDER_COLUMN_C_C_P_CONTAINERKEY_2);
		}

		sb.append(_FINDER_COLUMN_C_C_P_PLID_2);

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
			sb.append(AssetEntryUsageModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(containerType);

		if (bindContainerKey) {
			queryPos.add(containerKey);
		}

		queryPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntryUsage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntryUsage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry usages where containerType = &#63; and containerKey = &#63; and plid = &#63; from the database.
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 */
	@Override
	public void removeByC_C_P(
		long containerType, String containerKey, long plid) {

		for (AssetEntryUsage assetEntryUsage :
				findByC_C_P(
					containerType, containerKey, plid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(assetEntryUsage);
		}
	}

	/**
	 * Returns the number of asset entry usages where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @return the number of matching asset entry usages
	 */
	@Override
	public int countByC_C_P(
		long containerType, String containerKey, long plid) {

		containerKey = Objects.toString(containerKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C_P;

			finderArgs = new Object[] {containerType, containerKey, plid};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ASSETENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_P_CONTAINERTYPE_2);

			boolean bindContainerKey = false;

			if (containerKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_P_CONTAINERKEY_3);
			}
			else {
				bindContainerKey = true;

				sb.append(_FINDER_COLUMN_C_C_P_CONTAINERKEY_2);
			}

			sb.append(_FINDER_COLUMN_C_C_P_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(containerType);

				if (bindContainerKey) {
					queryPos.add(containerKey);
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

	private static final String _FINDER_COLUMN_C_C_P_CONTAINERTYPE_2 =
		"assetEntryUsage.containerType = ? AND ";

	private static final String _FINDER_COLUMN_C_C_P_CONTAINERKEY_2 =
		"assetEntryUsage.containerKey = ? AND ";

	private static final String _FINDER_COLUMN_C_C_P_CONTAINERKEY_3 =
		"(assetEntryUsage.containerKey IS NULL OR assetEntryUsage.containerKey = '') AND ";

	private static final String _FINDER_COLUMN_C_C_P_PLID_2 =
		"assetEntryUsage.plid = ? AND assetEntryUsage.containerKey IS NOT NULL";

	private FinderPath _finderPathFetchByA_C_C_P;
	private FinderPath _finderPathCountByA_C_C_P;

	/**
	 * Returns the asset entry usage where assetEntryId = &#63; and containerType = &#63; and containerKey = &#63; and plid = &#63; or throws a <code>NoSuchEntryUsageException</code> if it could not be found.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @return the matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage findByA_C_C_P(
			long assetEntryId, long containerType, String containerKey,
			long plid)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = fetchByA_C_C_P(
			assetEntryId, containerType, containerKey, plid);

		if (assetEntryUsage == null) {
			StringBundler sb = new StringBundler(10);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("assetEntryId=");
			sb.append(assetEntryId);

			sb.append(", containerType=");
			sb.append(containerType);

			sb.append(", containerKey=");
			sb.append(containerKey);

			sb.append(", plid=");
			sb.append(plid);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryUsageException(sb.toString());
		}

		return assetEntryUsage;
	}

	/**
	 * Returns the asset entry usage where assetEntryId = &#63; and containerType = &#63; and containerKey = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage fetchByA_C_C_P(
		long assetEntryId, long containerType, String containerKey, long plid) {

		return fetchByA_C_C_P(
			assetEntryId, containerType, containerKey, plid, true);
	}

	/**
	 * Returns the asset entry usage where assetEntryId = &#63; and containerType = &#63; and containerKey = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public AssetEntryUsage fetchByA_C_C_P(
		long assetEntryId, long containerType, String containerKey, long plid,
		boolean useFinderCache) {

		containerKey = Objects.toString(containerKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				assetEntryId, containerType, containerKey, plid
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByA_C_C_P, finderArgs, this);
		}

		if (result instanceof AssetEntryUsage) {
			AssetEntryUsage assetEntryUsage = (AssetEntryUsage)result;

			if ((assetEntryId != assetEntryUsage.getAssetEntryId()) ||
				(containerType != assetEntryUsage.getContainerType()) ||
				!Objects.equals(
					containerKey, assetEntryUsage.getContainerKey()) ||
				(plid != assetEntryUsage.getPlid())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_SELECT_ASSETENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_A_C_C_P_ASSETENTRYID_2);

			sb.append(_FINDER_COLUMN_A_C_C_P_CONTAINERTYPE_2);

			boolean bindContainerKey = false;

			if (containerKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_A_C_C_P_CONTAINERKEY_3);
			}
			else {
				bindContainerKey = true;

				sb.append(_FINDER_COLUMN_A_C_C_P_CONTAINERKEY_2);
			}

			sb.append(_FINDER_COLUMN_A_C_C_P_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetEntryId);

				queryPos.add(containerType);

				if (bindContainerKey) {
					queryPos.add(containerKey);
				}

				queryPos.add(plid);

				List<AssetEntryUsage> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByA_C_C_P, finderArgs, list);
					}
				}
				else {
					AssetEntryUsage assetEntryUsage = list.get(0);

					result = assetEntryUsage;

					cacheResult(assetEntryUsage);
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
			return (AssetEntryUsage)result;
		}
	}

	/**
	 * Removes the asset entry usage where assetEntryId = &#63; and containerType = &#63; and containerKey = &#63; and plid = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @return the asset entry usage that was removed
	 */
	@Override
	public AssetEntryUsage removeByA_C_C_P(
			long assetEntryId, long containerType, String containerKey,
			long plid)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = findByA_C_C_P(
			assetEntryId, containerType, containerKey, plid);

		return remove(assetEntryUsage);
	}

	/**
	 * Returns the number of asset entry usages where assetEntryId = &#63; and containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @return the number of matching asset entry usages
	 */
	@Override
	public int countByA_C_C_P(
		long assetEntryId, long containerType, String containerKey, long plid) {

		containerKey = Objects.toString(containerKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByA_C_C_P;

			finderArgs = new Object[] {
				assetEntryId, containerType, containerKey, plid
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_ASSETENTRYUSAGE_WHERE);

			sb.append(_FINDER_COLUMN_A_C_C_P_ASSETENTRYID_2);

			sb.append(_FINDER_COLUMN_A_C_C_P_CONTAINERTYPE_2);

			boolean bindContainerKey = false;

			if (containerKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_A_C_C_P_CONTAINERKEY_3);
			}
			else {
				bindContainerKey = true;

				sb.append(_FINDER_COLUMN_A_C_C_P_CONTAINERKEY_2);
			}

			sb.append(_FINDER_COLUMN_A_C_C_P_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetEntryId);

				queryPos.add(containerType);

				if (bindContainerKey) {
					queryPos.add(containerKey);
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

	private static final String _FINDER_COLUMN_A_C_C_P_ASSETENTRYID_2 =
		"assetEntryUsage.assetEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_C_C_P_CONTAINERTYPE_2 =
		"assetEntryUsage.containerType = ? AND ";

	private static final String _FINDER_COLUMN_A_C_C_P_CONTAINERKEY_2 =
		"assetEntryUsage.containerKey = ? AND ";

	private static final String _FINDER_COLUMN_A_C_C_P_CONTAINERKEY_3 =
		"(assetEntryUsage.containerKey IS NULL OR assetEntryUsage.containerKey = '') AND ";

	private static final String _FINDER_COLUMN_A_C_C_P_PLID_2 =
		"assetEntryUsage.plid = ?";

	public AssetEntryUsagePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AssetEntryUsage.class);

		setModelImplClass(AssetEntryUsageImpl.class);
		setModelPKClass(long.class);

		setTable(AssetEntryUsageTable.INSTANCE);
	}

	/**
	 * Caches the asset entry usage in the entity cache if it is enabled.
	 *
	 * @param assetEntryUsage the asset entry usage
	 */
	@Override
	public void cacheResult(AssetEntryUsage assetEntryUsage) {
		if (assetEntryUsage.getCtCollectionId() != 0) {
			assetEntryUsage.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			AssetEntryUsageImpl.class, assetEntryUsage.getPrimaryKey(),
			assetEntryUsage);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				assetEntryUsage.getUuid(), assetEntryUsage.getGroupId()
			},
			assetEntryUsage);

		finderCache.putResult(
			_finderPathFetchByA_C_C_P,
			new Object[] {
				assetEntryUsage.getAssetEntryId(),
				assetEntryUsage.getContainerType(),
				assetEntryUsage.getContainerKey(), assetEntryUsage.getPlid()
			},
			assetEntryUsage);

		assetEntryUsage.resetOriginalValues();
	}

	/**
	 * Caches the asset entry usages in the entity cache if it is enabled.
	 *
	 * @param assetEntryUsages the asset entry usages
	 */
	@Override
	public void cacheResult(List<AssetEntryUsage> assetEntryUsages) {
		for (AssetEntryUsage assetEntryUsage : assetEntryUsages) {
			if (assetEntryUsage.getCtCollectionId() != 0) {
				assetEntryUsage.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					AssetEntryUsageImpl.class,
					assetEntryUsage.getPrimaryKey()) == null) {

				cacheResult(assetEntryUsage);
			}
			else {
				assetEntryUsage.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset entry usages.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetEntryUsageImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset entry usage.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetEntryUsage assetEntryUsage) {
		entityCache.removeResult(
			AssetEntryUsageImpl.class, assetEntryUsage.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(AssetEntryUsageModelImpl)assetEntryUsage, true);
	}

	@Override
	public void clearCache(List<AssetEntryUsage> assetEntryUsages) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetEntryUsage assetEntryUsage : assetEntryUsages) {
			entityCache.removeResult(
				AssetEntryUsageImpl.class, assetEntryUsage.getPrimaryKey());

			clearUniqueFindersCache(
				(AssetEntryUsageModelImpl)assetEntryUsage, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(AssetEntryUsageImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetEntryUsageModelImpl assetEntryUsageModelImpl) {

		Object[] args = new Object[] {
			assetEntryUsageModelImpl.getUuid(),
			assetEntryUsageModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, assetEntryUsageModelImpl, false);

		args = new Object[] {
			assetEntryUsageModelImpl.getAssetEntryId(),
			assetEntryUsageModelImpl.getContainerType(),
			assetEntryUsageModelImpl.getContainerKey(),
			assetEntryUsageModelImpl.getPlid()
		};

		finderCache.putResult(
			_finderPathCountByA_C_C_P, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByA_C_C_P, args, assetEntryUsageModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AssetEntryUsageModelImpl assetEntryUsageModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetEntryUsageModelImpl.getUuid(),
				assetEntryUsageModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((assetEntryUsageModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetEntryUsageModelImpl.getOriginalUuid(),
				assetEntryUsageModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetEntryUsageModelImpl.getAssetEntryId(),
				assetEntryUsageModelImpl.getContainerType(),
				assetEntryUsageModelImpl.getContainerKey(),
				assetEntryUsageModelImpl.getPlid()
			};

			finderCache.removeResult(_finderPathCountByA_C_C_P, args);
			finderCache.removeResult(_finderPathFetchByA_C_C_P, args);
		}

		if ((assetEntryUsageModelImpl.getColumnBitmask() &
			 _finderPathFetchByA_C_C_P.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetEntryUsageModelImpl.getOriginalAssetEntryId(),
				assetEntryUsageModelImpl.getOriginalContainerType(),
				assetEntryUsageModelImpl.getOriginalContainerKey(),
				assetEntryUsageModelImpl.getOriginalPlid()
			};

			finderCache.removeResult(_finderPathCountByA_C_C_P, args);
			finderCache.removeResult(_finderPathFetchByA_C_C_P, args);
		}
	}

	/**
	 * Creates a new asset entry usage with the primary key. Does not add the asset entry usage to the database.
	 *
	 * @param assetEntryUsageId the primary key for the new asset entry usage
	 * @return the new asset entry usage
	 */
	@Override
	public AssetEntryUsage create(long assetEntryUsageId) {
		AssetEntryUsage assetEntryUsage = new AssetEntryUsageImpl();

		assetEntryUsage.setNew(true);
		assetEntryUsage.setPrimaryKey(assetEntryUsageId);

		String uuid = PortalUUIDUtil.generate();

		assetEntryUsage.setUuid(uuid);

		assetEntryUsage.setCompanyId(CompanyThreadLocal.getCompanyId());

		return assetEntryUsage;
	}

	/**
	 * Removes the asset entry usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryUsageId the primary key of the asset entry usage
	 * @return the asset entry usage that was removed
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	@Override
	public AssetEntryUsage remove(long assetEntryUsageId)
		throws NoSuchEntryUsageException {

		return remove((Serializable)assetEntryUsageId);
	}

	/**
	 * Removes the asset entry usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset entry usage
	 * @return the asset entry usage that was removed
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	@Override
	public AssetEntryUsage remove(Serializable primaryKey)
		throws NoSuchEntryUsageException {

		Session session = null;

		try {
			session = openSession();

			AssetEntryUsage assetEntryUsage = (AssetEntryUsage)session.get(
				AssetEntryUsageImpl.class, primaryKey);

			if (assetEntryUsage == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryUsageException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetEntryUsage);
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
	protected AssetEntryUsage removeImpl(AssetEntryUsage assetEntryUsage) {
		if (!ctPersistenceHelper.isRemove(assetEntryUsage)) {
			return assetEntryUsage;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetEntryUsage)) {
				assetEntryUsage = (AssetEntryUsage)session.get(
					AssetEntryUsageImpl.class,
					assetEntryUsage.getPrimaryKeyObj());
			}

			if (assetEntryUsage != null) {
				session.delete(assetEntryUsage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetEntryUsage != null) {
			clearCache(assetEntryUsage);
		}

		return assetEntryUsage;
	}

	@Override
	public AssetEntryUsage updateImpl(AssetEntryUsage assetEntryUsage) {
		boolean isNew = assetEntryUsage.isNew();

		if (!(assetEntryUsage instanceof AssetEntryUsageModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetEntryUsage.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					assetEntryUsage);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetEntryUsage proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetEntryUsage implementation " +
					assetEntryUsage.getClass());
		}

		AssetEntryUsageModelImpl assetEntryUsageModelImpl =
			(AssetEntryUsageModelImpl)assetEntryUsage;

		if (Validator.isNull(assetEntryUsage.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetEntryUsage.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (assetEntryUsage.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetEntryUsage.setCreateDate(now);
			}
			else {
				assetEntryUsage.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!assetEntryUsageModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetEntryUsage.setModifiedDate(now);
			}
			else {
				assetEntryUsage.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(assetEntryUsage)) {
				if (!isNew) {
					session.evict(
						AssetEntryUsageImpl.class,
						assetEntryUsage.getPrimaryKeyObj());
				}

				session.save(assetEntryUsage);

				assetEntryUsage.setNew(false);
			}
			else {
				assetEntryUsage = (AssetEntryUsage)session.merge(
					assetEntryUsage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetEntryUsage.getCtCollectionId() != 0) {
			assetEntryUsage.resetOriginalValues();

			return assetEntryUsage;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {assetEntryUsageModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				assetEntryUsageModelImpl.getUuid(),
				assetEntryUsageModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {assetEntryUsageModelImpl.getAssetEntryId()};

			finderCache.removeResult(_finderPathCountByAssetEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAssetEntryId, args);

			args = new Object[] {assetEntryUsageModelImpl.getPlid()};

			finderCache.removeResult(_finderPathCountByPlid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByPlid, args);

			args = new Object[] {
				assetEntryUsageModelImpl.getAssetEntryId(),
				assetEntryUsageModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByA_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByA_T, args);

			args = new Object[] {
				assetEntryUsageModelImpl.getContainerType(),
				assetEntryUsageModelImpl.getContainerKey(),
				assetEntryUsageModelImpl.getPlid()
			};

			finderCache.removeResult(_finderPathCountByC_C_P, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_C_P, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((assetEntryUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetEntryUsageModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {assetEntryUsageModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((assetEntryUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetEntryUsageModelImpl.getOriginalUuid(),
					assetEntryUsageModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					assetEntryUsageModelImpl.getUuid(),
					assetEntryUsageModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((assetEntryUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAssetEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetEntryUsageModelImpl.getOriginalAssetEntryId()
				};

				finderCache.removeResult(_finderPathCountByAssetEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetEntryId, args);

				args = new Object[] {
					assetEntryUsageModelImpl.getAssetEntryId()
				};

				finderCache.removeResult(_finderPathCountByAssetEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetEntryId, args);
			}

			if ((assetEntryUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByPlid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetEntryUsageModelImpl.getOriginalPlid()
				};

				finderCache.removeResult(_finderPathCountByPlid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByPlid, args);

				args = new Object[] {assetEntryUsageModelImpl.getPlid()};

				finderCache.removeResult(_finderPathCountByPlid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByPlid, args);
			}

			if ((assetEntryUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByA_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetEntryUsageModelImpl.getOriginalAssetEntryId(),
					assetEntryUsageModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByA_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByA_T, args);

				args = new Object[] {
					assetEntryUsageModelImpl.getAssetEntryId(),
					assetEntryUsageModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByA_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByA_T, args);
			}

			if ((assetEntryUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetEntryUsageModelImpl.getOriginalContainerType(),
					assetEntryUsageModelImpl.getOriginalContainerKey(),
					assetEntryUsageModelImpl.getOriginalPlid()
				};

				finderCache.removeResult(_finderPathCountByC_C_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C_P, args);

				args = new Object[] {
					assetEntryUsageModelImpl.getContainerType(),
					assetEntryUsageModelImpl.getContainerKey(),
					assetEntryUsageModelImpl.getPlid()
				};

				finderCache.removeResult(_finderPathCountByC_C_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C_P, args);
			}
		}

		entityCache.putResult(
			AssetEntryUsageImpl.class, assetEntryUsage.getPrimaryKey(),
			assetEntryUsage, false);

		clearUniqueFindersCache(assetEntryUsageModelImpl, false);
		cacheUniqueFindersCache(assetEntryUsageModelImpl);

		assetEntryUsage.resetOriginalValues();

		return assetEntryUsage;
	}

	/**
	 * Returns the asset entry usage with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry usage
	 * @return the asset entry usage
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	@Override
	public AssetEntryUsage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryUsageException {

		AssetEntryUsage assetEntryUsage = fetchByPrimaryKey(primaryKey);

		if (assetEntryUsage == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryUsageException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetEntryUsage;
	}

	/**
	 * Returns the asset entry usage with the primary key or throws a <code>NoSuchEntryUsageException</code> if it could not be found.
	 *
	 * @param assetEntryUsageId the primary key of the asset entry usage
	 * @return the asset entry usage
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	@Override
	public AssetEntryUsage findByPrimaryKey(long assetEntryUsageId)
		throws NoSuchEntryUsageException {

		return findByPrimaryKey((Serializable)assetEntryUsageId);
	}

	/**
	 * Returns the asset entry usage with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry usage
	 * @return the asset entry usage, or <code>null</code> if a asset entry usage with the primary key could not be found
	 */
	@Override
	public AssetEntryUsage fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(AssetEntryUsage.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		AssetEntryUsage assetEntryUsage = null;

		Session session = null;

		try {
			session = openSession();

			assetEntryUsage = (AssetEntryUsage)session.get(
				AssetEntryUsageImpl.class, primaryKey);

			if (assetEntryUsage != null) {
				cacheResult(assetEntryUsage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return assetEntryUsage;
	}

	/**
	 * Returns the asset entry usage with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetEntryUsageId the primary key of the asset entry usage
	 * @return the asset entry usage, or <code>null</code> if a asset entry usage with the primary key could not be found
	 */
	@Override
	public AssetEntryUsage fetchByPrimaryKey(long assetEntryUsageId) {
		return fetchByPrimaryKey((Serializable)assetEntryUsageId);
	}

	@Override
	public Map<Serializable, AssetEntryUsage> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(AssetEntryUsage.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetEntryUsage> map =
			new HashMap<Serializable, AssetEntryUsage>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetEntryUsage assetEntryUsage = fetchByPrimaryKey(primaryKey);

			if (assetEntryUsage != null) {
				map.put(primaryKey, assetEntryUsage);
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

			for (AssetEntryUsage assetEntryUsage :
					(List<AssetEntryUsage>)query.list()) {

				map.put(assetEntryUsage.getPrimaryKeyObj(), assetEntryUsage);

				cacheResult(assetEntryUsage);
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
	 * Returns all the asset entry usages.
	 *
	 * @return the asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @return the range of asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findAll(
		int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset entry usages
	 */
	@Override
	public List<AssetEntryUsage> findAll(
		int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

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

		List<AssetEntryUsage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetEntryUsage>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ASSETENTRYUSAGE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETENTRYUSAGE;

				sql = sql.concat(AssetEntryUsageModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AssetEntryUsage>)QueryUtil.list(
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
	 * Removes all the asset entry usages from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetEntryUsage assetEntryUsage : findAll()) {
			remove(assetEntryUsage);
		}
	}

	/**
	 * Returns the number of asset entry usages.
	 *
	 * @return the number of asset entry usages
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetEntryUsage.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ASSETENTRYUSAGE);

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
		return "assetEntryUsageId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETENTRYUSAGE;
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
		return AssetEntryUsageModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AssetEntryUsage";
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
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("assetEntryId");
		ctStrictColumnNames.add("containerType");
		ctStrictColumnNames.add("containerKey");
		ctStrictColumnNames.add("plid");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("assetEntryUsageId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {
				"assetEntryId", "containerType", "containerKey", "plid"
			});
	}

	/**
	 * Initializes the asset entry usage persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			AssetEntryUsageImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AssetEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			AssetEntryUsageImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			AssetEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			AssetEntryUsageModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			AssetEntryUsageImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			AssetEntryUsageModelImpl.UUID_COLUMN_BITMASK |
			AssetEntryUsageModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			AssetEntryUsageImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			AssetEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			AssetEntryUsageModelImpl.UUID_COLUMN_BITMASK |
			AssetEntryUsageModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByAssetEntryId = new FinderPath(
			AssetEntryUsageImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAssetEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAssetEntryId = new FinderPath(
			AssetEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetEntryId",
			new String[] {Long.class.getName()},
			AssetEntryUsageModelImpl.ASSETENTRYID_COLUMN_BITMASK);

		_finderPathCountByAssetEntryId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetEntryId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByPlid = new FinderPath(
			AssetEntryUsageImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPlid",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByPlid = new FinderPath(
			AssetEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPlid",
			new String[] {Long.class.getName()},
			AssetEntryUsageModelImpl.PLID_COLUMN_BITMASK);

		_finderPathCountByPlid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPlid", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByA_T = new FinderPath(
			AssetEntryUsageImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByA_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByA_T = new FinderPath(
			AssetEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			AssetEntryUsageModelImpl.ASSETENTRYID_COLUMN_BITMASK |
			AssetEntryUsageModelImpl.TYPE_COLUMN_BITMASK);

		_finderPathCountByA_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_T",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByC_C_P = new FinderPath(
			AssetEntryUsageImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_C_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C_P = new FinderPath(
			AssetEntryUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			},
			AssetEntryUsageModelImpl.CONTAINERTYPE_COLUMN_BITMASK |
			AssetEntryUsageModelImpl.CONTAINERKEY_COLUMN_BITMASK |
			AssetEntryUsageModelImpl.PLID_COLUMN_BITMASK);

		_finderPathCountByC_C_P = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_C_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			});

		_finderPathFetchByA_C_C_P = new FinderPath(
			AssetEntryUsageImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByA_C_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Long.class.getName()
			},
			AssetEntryUsageModelImpl.ASSETENTRYID_COLUMN_BITMASK |
			AssetEntryUsageModelImpl.CONTAINERTYPE_COLUMN_BITMASK |
			AssetEntryUsageModelImpl.CONTAINERKEY_COLUMN_BITMASK |
			AssetEntryUsageModelImpl.PLID_COLUMN_BITMASK);

		_finderPathCountByA_C_C_P = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByA_C_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Long.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AssetEntryUsageImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = AssetPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AssetPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AssetPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_ASSETENTRYUSAGE =
		"SELECT assetEntryUsage FROM AssetEntryUsage assetEntryUsage";

	private static final String _SQL_SELECT_ASSETENTRYUSAGE_WHERE =
		"SELECT assetEntryUsage FROM AssetEntryUsage assetEntryUsage WHERE ";

	private static final String _SQL_COUNT_ASSETENTRYUSAGE =
		"SELECT COUNT(assetEntryUsage) FROM AssetEntryUsage assetEntryUsage";

	private static final String _SQL_COUNT_ASSETENTRYUSAGE_WHERE =
		"SELECT COUNT(assetEntryUsage) FROM AssetEntryUsage assetEntryUsage WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "assetEntryUsage.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetEntryUsage exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetEntryUsage exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntryUsagePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	static {
		try {
			Class.forName(AssetPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}