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

import com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRelTable;
import com.liferay.asset.list.model.impl.AssetListEntryAssetEntryRelImpl;
import com.liferay.asset.list.model.impl.AssetListEntryAssetEntryRelModelImpl;
import com.liferay.asset.list.service.persistence.AssetListEntryAssetEntryRelPersistence;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
 * The persistence implementation for the asset list entry asset entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AssetListEntryAssetEntryRelPersistence.class)
public class AssetListEntryAssetEntryRelPersistenceImpl
	extends BasePersistenceImpl<AssetListEntryAssetEntryRel>
	implements AssetListEntryAssetEntryRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetListEntryAssetEntryRelUtil</code> to access the asset list entry asset entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetListEntryAssetEntryRelImpl.class.getName();

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
	 * Returns all the asset list entry asset entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry asset entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @return the range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

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

		List<AssetListEntryAssetEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntryAssetEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
						list) {

					if (!uuid.equals(assetListEntryAssetEntryRel.getUuid())) {
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

			sb.append(_SQL_SELECT_ASSETLISTENTRYASSETENTRYREL_WHERE);

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
				sb.append(AssetListEntryAssetEntryRelModelImpl.ORDER_BY_JPQL);
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

				list = (List<AssetListEntryAssetEntryRel>)QueryUtil.list(
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
	 * Returns the first asset list entry asset entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel findByUuid_First(
			String uuid,
			OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			fetchByUuid_First(uuid, orderByComparator);

		if (assetListEntryAssetEntryRel != null) {
			return assetListEntryAssetEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryAssetEntryRelException(sb.toString());
	}

	/**
	 * Returns the first asset list entry asset entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel fetchByUuid_First(
		String uuid,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		List<AssetListEntryAssetEntryRel> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel findByUuid_Last(
			String uuid,
			OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			fetchByUuid_Last(uuid, orderByComparator);

		if (assetListEntryAssetEntryRel != null) {
			return assetListEntryAssetEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryAssetEntryRelException(sb.toString());
	}

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AssetListEntryAssetEntryRel> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry asset entry rels before and after the current asset list entry asset entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the current asset list entry asset entry rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel[] findByUuid_PrevAndNext(
			long assetListEntryAssetEntryRelId, String uuid,
			OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException {

		uuid = Objects.toString(uuid, "");

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			findByPrimaryKey(assetListEntryAssetEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntryAssetEntryRel[] array =
				new AssetListEntryAssetEntryRelImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, assetListEntryAssetEntryRel, uuid, orderByComparator,
				true);

			array[1] = assetListEntryAssetEntryRel;

			array[2] = getByUuid_PrevAndNext(
				session, assetListEntryAssetEntryRel, uuid, orderByComparator,
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

	protected AssetListEntryAssetEntryRel getByUuid_PrevAndNext(
		Session session,
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel, String uuid,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETLISTENTRYASSETENTRYREL_WHERE);

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
			sb.append(AssetListEntryAssetEntryRelModelImpl.ORDER_BY_JPQL);
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
						assetListEntryAssetEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntryAssetEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry asset entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetListEntryAssetEntryRel);
		}
	}

	/**
	 * Returns the number of asset list entry asset entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset list entry asset entry rels
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

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

			sb.append(_SQL_COUNT_ASSETLISTENTRYASSETENTRYREL_WHERE);

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
		"assetListEntryAssetEntryRel.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(assetListEntryAssetEntryRel.uuid IS NULL OR assetListEntryAssetEntryRel.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the asset list entry asset entry rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryAssetEntryRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel = fetchByUUID_G(
			uuid, groupId);

		if (assetListEntryAssetEntryRel == null) {
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

			throw new NoSuchEntryAssetEntryRelException(sb.toString());
		}

		return assetListEntryAssetEntryRel;
	}

	/**
	 * Returns the asset list entry asset entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel fetchByUUID_G(
		String uuid, long groupId) {

		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the asset list entry asset entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof AssetListEntryAssetEntryRel) {
			AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
				(AssetListEntryAssetEntryRel)result;

			if (!Objects.equals(uuid, assetListEntryAssetEntryRel.getUuid()) ||
				(groupId != assetListEntryAssetEntryRel.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ASSETLISTENTRYASSETENTRYREL_WHERE);

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

				List<AssetListEntryAssetEntryRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
						list.get(0);

					result = assetListEntryAssetEntryRel;

					cacheResult(assetListEntryAssetEntryRel);
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
			return (AssetListEntryAssetEntryRel)result;
		}
	}

	/**
	 * Removes the asset list entry asset entry rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset list entry asset entry rel that was removed
	 */
	@Override
	public AssetListEntryAssetEntryRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel = findByUUID_G(
			uuid, groupId);

		return remove(assetListEntryAssetEntryRel);
	}

	/**
	 * Returns the number of asset list entry asset entry rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset list entry asset entry rels
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

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

			sb.append(_SQL_COUNT_ASSETLISTENTRYASSETENTRYREL_WHERE);

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
		"assetListEntryAssetEntryRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(assetListEntryAssetEntryRel.uuid IS NULL OR assetListEntryAssetEntryRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"assetListEntryAssetEntryRel.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the asset list entry asset entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry asset entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @return the range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

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

		List<AssetListEntryAssetEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntryAssetEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
						list) {

					if (!uuid.equals(assetListEntryAssetEntryRel.getUuid()) ||
						(companyId !=
							assetListEntryAssetEntryRel.getCompanyId())) {

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

			sb.append(_SQL_SELECT_ASSETLISTENTRYASSETENTRYREL_WHERE);

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
				sb.append(AssetListEntryAssetEntryRelModelImpl.ORDER_BY_JPQL);
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

				list = (List<AssetListEntryAssetEntryRel>)QueryUtil.list(
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
	 * Returns the first asset list entry asset entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (assetListEntryAssetEntryRel != null) {
			return assetListEntryAssetEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryAssetEntryRelException(sb.toString());
	}

	/**
	 * Returns the first asset list entry asset entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		List<AssetListEntryAssetEntryRel> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (assetListEntryAssetEntryRel != null) {
			return assetListEntryAssetEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryAssetEntryRelException(sb.toString());
	}

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AssetListEntryAssetEntryRel> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry asset entry rels before and after the current asset list entry asset entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the current asset list entry asset entry rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel[] findByUuid_C_PrevAndNext(
			long assetListEntryAssetEntryRelId, String uuid, long companyId,
			OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException {

		uuid = Objects.toString(uuid, "");

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			findByPrimaryKey(assetListEntryAssetEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntryAssetEntryRel[] array =
				new AssetListEntryAssetEntryRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, assetListEntryAssetEntryRel, uuid, companyId,
				orderByComparator, true);

			array[1] = assetListEntryAssetEntryRel;

			array[2] = getByUuid_C_PrevAndNext(
				session, assetListEntryAssetEntryRel, uuid, companyId,
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

	protected AssetListEntryAssetEntryRel getByUuid_C_PrevAndNext(
		Session session,
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel, String uuid,
		long companyId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETLISTENTRYASSETENTRYREL_WHERE);

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
			sb.append(AssetListEntryAssetEntryRelModelImpl.ORDER_BY_JPQL);
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
						assetListEntryAssetEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntryAssetEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry asset entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetListEntryAssetEntryRel);
		}
	}

	/**
	 * Returns the number of asset list entry asset entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset list entry asset entry rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

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

			sb.append(_SQL_COUNT_ASSETLISTENTRYASSETENTRYREL_WHERE);

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
		"assetListEntryAssetEntryRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(assetListEntryAssetEntryRel.uuid IS NULL OR assetListEntryAssetEntryRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"assetListEntryAssetEntryRel.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByAssetListEntryId;
	private FinderPath _finderPathWithoutPaginationFindByAssetListEntryId;
	private FinderPath _finderPathCountByAssetListEntryId;

	/**
	 * Returns all the asset list entry asset entry rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByAssetListEntryId(
		long assetListEntryId) {

		return findByAssetListEntryId(
			assetListEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry asset entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @return the range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end) {

		return findByAssetListEntryId(assetListEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		return findByAssetListEntryId(
			assetListEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

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

		List<AssetListEntryAssetEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntryAssetEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
						list) {

					if (assetListEntryId !=
							assetListEntryAssetEntryRel.getAssetListEntryId()) {

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

			sb.append(_SQL_SELECT_ASSETLISTENTRYASSETENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_ASSETLISTENTRYID_ASSETLISTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryAssetEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetListEntryId);

				list = (List<AssetListEntryAssetEntryRel>)QueryUtil.list(
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
	 * Returns the first asset list entry asset entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel findByAssetListEntryId_First(
			long assetListEntryId,
			OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			fetchByAssetListEntryId_First(assetListEntryId, orderByComparator);

		if (assetListEntryAssetEntryRel != null) {
			return assetListEntryAssetEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetListEntryId=");
		sb.append(assetListEntryId);

		sb.append("}");

		throw new NoSuchEntryAssetEntryRelException(sb.toString());
	}

	/**
	 * Returns the first asset list entry asset entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel fetchByAssetListEntryId_First(
		long assetListEntryId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		List<AssetListEntryAssetEntryRel> list = findByAssetListEntryId(
			assetListEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel findByAssetListEntryId_Last(
			long assetListEntryId,
			OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			fetchByAssetListEntryId_Last(assetListEntryId, orderByComparator);

		if (assetListEntryAssetEntryRel != null) {
			return assetListEntryAssetEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetListEntryId=");
		sb.append(assetListEntryId);

		sb.append("}");

		throw new NoSuchEntryAssetEntryRelException(sb.toString());
	}

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel fetchByAssetListEntryId_Last(
		long assetListEntryId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		int count = countByAssetListEntryId(assetListEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetListEntryAssetEntryRel> list = findByAssetListEntryId(
			assetListEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry asset entry rels before and after the current asset list entry asset entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the current asset list entry asset entry rel
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel[] findByAssetListEntryId_PrevAndNext(
			long assetListEntryAssetEntryRelId, long assetListEntryId,
			OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			findByPrimaryKey(assetListEntryAssetEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntryAssetEntryRel[] array =
				new AssetListEntryAssetEntryRelImpl[3];

			array[0] = getByAssetListEntryId_PrevAndNext(
				session, assetListEntryAssetEntryRel, assetListEntryId,
				orderByComparator, true);

			array[1] = assetListEntryAssetEntryRel;

			array[2] = getByAssetListEntryId_PrevAndNext(
				session, assetListEntryAssetEntryRel, assetListEntryId,
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

	protected AssetListEntryAssetEntryRel getByAssetListEntryId_PrevAndNext(
		Session session,
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel,
		long assetListEntryId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETLISTENTRYASSETENTRYREL_WHERE);

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
			sb.append(AssetListEntryAssetEntryRelModelImpl.ORDER_BY_JPQL);
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
						assetListEntryAssetEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntryAssetEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry asset entry rels where assetListEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 */
	@Override
	public void removeByAssetListEntryId(long assetListEntryId) {
		for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
				findByAssetListEntryId(
					assetListEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetListEntryAssetEntryRel);
		}
	}

	/**
	 * Returns the number of asset list entry asset entry rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the number of matching asset list entry asset entry rels
	 */
	@Override
	public int countByAssetListEntryId(long assetListEntryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

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

			sb.append(_SQL_COUNT_ASSETLISTENTRYASSETENTRYREL_WHERE);

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
			"assetListEntryAssetEntryRel.assetListEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByA_S;
	private FinderPath _finderPathWithoutPaginationFindByA_S;
	private FinderPath _finderPathCountByA_S;
	private FinderPath _finderPathWithPaginationCountByA_S;

	/**
	 * Returns all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByA_S(
		long assetListEntryId, long segmentsEntryId) {

		return findByA_S(
			assetListEntryId, segmentsEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @return the range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByA_S(
		long assetListEntryId, long segmentsEntryId, int start, int end) {

		return findByA_S(assetListEntryId, segmentsEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByA_S(
		long assetListEntryId, long segmentsEntryId, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		return findByA_S(
			assetListEntryId, segmentsEntryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByA_S(
		long assetListEntryId, long segmentsEntryId, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByA_S;
				finderArgs = new Object[] {assetListEntryId, segmentsEntryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByA_S;
			finderArgs = new Object[] {
				assetListEntryId, segmentsEntryId, start, end, orderByComparator
			};
		}

		List<AssetListEntryAssetEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntryAssetEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
						list) {

					if ((assetListEntryId !=
							assetListEntryAssetEntryRel.
								getAssetListEntryId()) ||
						(segmentsEntryId !=
							assetListEntryAssetEntryRel.getSegmentsEntryId())) {

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

			sb.append(_SQL_SELECT_ASSETLISTENTRYASSETENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_A_S_ASSETLISTENTRYID_2);

			sb.append(_FINDER_COLUMN_A_S_SEGMENTSENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryAssetEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetListEntryId);

				queryPos.add(segmentsEntryId);

				list = (List<AssetListEntryAssetEntryRel>)QueryUtil.list(
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
	 * Returns the first asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel findByA_S_First(
			long assetListEntryId, long segmentsEntryId,
			OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			fetchByA_S_First(
				assetListEntryId, segmentsEntryId, orderByComparator);

		if (assetListEntryAssetEntryRel != null) {
			return assetListEntryAssetEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetListEntryId=");
		sb.append(assetListEntryId);

		sb.append(", segmentsEntryId=");
		sb.append(segmentsEntryId);

		sb.append("}");

		throw new NoSuchEntryAssetEntryRelException(sb.toString());
	}

	/**
	 * Returns the first asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel fetchByA_S_First(
		long assetListEntryId, long segmentsEntryId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		List<AssetListEntryAssetEntryRel> list = findByA_S(
			assetListEntryId, segmentsEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel findByA_S_Last(
			long assetListEntryId, long segmentsEntryId,
			OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			fetchByA_S_Last(
				assetListEntryId, segmentsEntryId, orderByComparator);

		if (assetListEntryAssetEntryRel != null) {
			return assetListEntryAssetEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetListEntryId=");
		sb.append(assetListEntryId);

		sb.append(", segmentsEntryId=");
		sb.append(segmentsEntryId);

		sb.append("}");

		throw new NoSuchEntryAssetEntryRelException(sb.toString());
	}

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel fetchByA_S_Last(
		long assetListEntryId, long segmentsEntryId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		int count = countByA_S(assetListEntryId, segmentsEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetListEntryAssetEntryRel> list = findByA_S(
			assetListEntryId, segmentsEntryId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry asset entry rels before and after the current asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the current asset list entry asset entry rel
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel[] findByA_S_PrevAndNext(
			long assetListEntryAssetEntryRelId, long assetListEntryId,
			long segmentsEntryId,
			OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			findByPrimaryKey(assetListEntryAssetEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntryAssetEntryRel[] array =
				new AssetListEntryAssetEntryRelImpl[3];

			array[0] = getByA_S_PrevAndNext(
				session, assetListEntryAssetEntryRel, assetListEntryId,
				segmentsEntryId, orderByComparator, true);

			array[1] = assetListEntryAssetEntryRel;

			array[2] = getByA_S_PrevAndNext(
				session, assetListEntryAssetEntryRel, assetListEntryId,
				segmentsEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntryAssetEntryRel getByA_S_PrevAndNext(
		Session session,
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel,
		long assetListEntryId, long segmentsEntryId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETLISTENTRYASSETENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_A_S_ASSETLISTENTRYID_2);

		sb.append(_FINDER_COLUMN_A_S_SEGMENTSENTRYID_2);

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
			sb.append(AssetListEntryAssetEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(assetListEntryId);

		queryPos.add(segmentsEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntryAssetEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntryAssetEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryIds the segments entry IDs
	 * @return the matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByA_S(
		long assetListEntryId, long[] segmentsEntryIds) {

		return findByA_S(
			assetListEntryId, segmentsEntryIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryIds the segments entry IDs
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @return the range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByA_S(
		long assetListEntryId, long[] segmentsEntryIds, int start, int end) {

		return findByA_S(assetListEntryId, segmentsEntryIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryIds the segments entry IDs
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByA_S(
		long assetListEntryId, long[] segmentsEntryIds, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		return findByA_S(
			assetListEntryId, segmentsEntryIds, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByA_S(
		long assetListEntryId, long[] segmentsEntryIds, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
		boolean useFinderCache) {

		if (segmentsEntryIds == null) {
			segmentsEntryIds = new long[0];
		}
		else if (segmentsEntryIds.length > 1) {
			segmentsEntryIds = ArrayUtil.sortedUnique(segmentsEntryIds);
		}

		if (segmentsEntryIds.length == 1) {
			return findByA_S(
				assetListEntryId, segmentsEntryIds[0], start, end,
				orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {
					assetListEntryId, StringUtil.merge(segmentsEntryIds)
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				assetListEntryId, StringUtil.merge(segmentsEntryIds), start,
				end, orderByComparator
			};
		}

		List<AssetListEntryAssetEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntryAssetEntryRel>)finderCache.getResult(
				_finderPathWithPaginationFindByA_S, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
						list) {

					if ((assetListEntryId !=
							assetListEntryAssetEntryRel.
								getAssetListEntryId()) ||
						!ArrayUtil.contains(
							segmentsEntryIds,
							assetListEntryAssetEntryRel.getSegmentsEntryId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_ASSETLISTENTRYASSETENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_A_S_ASSETLISTENTRYID_2);

			if (segmentsEntryIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_A_S_SEGMENTSENTRYID_7);

				sb.append(StringUtil.merge(segmentsEntryIds));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryAssetEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetListEntryId);

				list = (List<AssetListEntryAssetEntryRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByA_S, finderArgs, list);
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
	 * Removes all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 */
	@Override
	public void removeByA_S(long assetListEntryId, long segmentsEntryId) {
		for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
				findByA_S(
					assetListEntryId, segmentsEntryId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(assetListEntryAssetEntryRel);
		}
	}

	/**
	 * Returns the number of asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching asset list entry asset entry rels
	 */
	@Override
	public int countByA_S(long assetListEntryId, long segmentsEntryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByA_S;

			finderArgs = new Object[] {assetListEntryId, segmentsEntryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETLISTENTRYASSETENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_A_S_ASSETLISTENTRYID_2);

			sb.append(_FINDER_COLUMN_A_S_SEGMENTSENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetListEntryId);

				queryPos.add(segmentsEntryId);

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

	/**
	 * Returns the number of asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = any &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryIds the segments entry IDs
	 * @return the number of matching asset list entry asset entry rels
	 */
	@Override
	public int countByA_S(long assetListEntryId, long[] segmentsEntryIds) {
		if (segmentsEntryIds == null) {
			segmentsEntryIds = new long[0];
		}
		else if (segmentsEntryIds.length > 1) {
			segmentsEntryIds = ArrayUtil.sortedUnique(segmentsEntryIds);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {
				assetListEntryId, StringUtil.merge(segmentsEntryIds)
			};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByA_S, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_ASSETLISTENTRYASSETENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_A_S_ASSETLISTENTRYID_2);

			if (segmentsEntryIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_A_S_SEGMENTSENTRYID_7);

				sb.append(StringUtil.merge(segmentsEntryIds));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetListEntryId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByA_S, finderArgs, count);
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

	private static final String _FINDER_COLUMN_A_S_ASSETLISTENTRYID_2 =
		"assetListEntryAssetEntryRel.assetListEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_S_SEGMENTSENTRYID_2 =
		"assetListEntryAssetEntryRel.segmentsEntryId = ?";

	private static final String _FINDER_COLUMN_A_S_SEGMENTSENTRYID_7 =
		"assetListEntryAssetEntryRel.segmentsEntryId IN (";

	private FinderPath _finderPathFetchByA_S_P;
	private FinderPath _finderPathCountByA_S_P;

	/**
	 * Returns the asset list entry asset entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; and position = &#63; or throws a <code>NoSuchEntryAssetEntryRelException</code> if it could not be found.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @return the matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel findByA_S_P(
			long assetListEntryId, long segmentsEntryId, int position)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel = fetchByA_S_P(
			assetListEntryId, segmentsEntryId, position);

		if (assetListEntryAssetEntryRel == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("assetListEntryId=");
			sb.append(assetListEntryId);

			sb.append(", segmentsEntryId=");
			sb.append(segmentsEntryId);

			sb.append(", position=");
			sb.append(position);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryAssetEntryRelException(sb.toString());
		}

		return assetListEntryAssetEntryRel;
	}

	/**
	 * Returns the asset list entry asset entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; and position = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @return the matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel fetchByA_S_P(
		long assetListEntryId, long segmentsEntryId, int position) {

		return fetchByA_S_P(assetListEntryId, segmentsEntryId, position, true);
	}

	/**
	 * Returns the asset list entry asset entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; and position = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel fetchByA_S_P(
		long assetListEntryId, long segmentsEntryId, int position,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				assetListEntryId, segmentsEntryId, position
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByA_S_P, finderArgs, this);
		}

		if (result instanceof AssetListEntryAssetEntryRel) {
			AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
				(AssetListEntryAssetEntryRel)result;

			if ((assetListEntryId !=
					assetListEntryAssetEntryRel.getAssetListEntryId()) ||
				(segmentsEntryId !=
					assetListEntryAssetEntryRel.getSegmentsEntryId()) ||
				(position != assetListEntryAssetEntryRel.getPosition())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_ASSETLISTENTRYASSETENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_A_S_P_ASSETLISTENTRYID_2);

			sb.append(_FINDER_COLUMN_A_S_P_SEGMENTSENTRYID_2);

			sb.append(_FINDER_COLUMN_A_S_P_POSITION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetListEntryId);

				queryPos.add(segmentsEntryId);

				queryPos.add(position);

				List<AssetListEntryAssetEntryRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByA_S_P, finderArgs, list);
					}
				}
				else {
					AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
						list.get(0);

					result = assetListEntryAssetEntryRel;

					cacheResult(assetListEntryAssetEntryRel);
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
			return (AssetListEntryAssetEntryRel)result;
		}
	}

	/**
	 * Removes the asset list entry asset entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; and position = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @return the asset list entry asset entry rel that was removed
	 */
	@Override
	public AssetListEntryAssetEntryRel removeByA_S_P(
			long assetListEntryId, long segmentsEntryId, int position)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel = findByA_S_P(
			assetListEntryId, segmentsEntryId, position);

		return remove(assetListEntryAssetEntryRel);
	}

	/**
	 * Returns the number of asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63; and position = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @return the number of matching asset list entry asset entry rels
	 */
	@Override
	public int countByA_S_P(
		long assetListEntryId, long segmentsEntryId, int position) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByA_S_P;

			finderArgs = new Object[] {
				assetListEntryId, segmentsEntryId, position
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ASSETLISTENTRYASSETENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_A_S_P_ASSETLISTENTRYID_2);

			sb.append(_FINDER_COLUMN_A_S_P_SEGMENTSENTRYID_2);

			sb.append(_FINDER_COLUMN_A_S_P_POSITION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetListEntryId);

				queryPos.add(segmentsEntryId);

				queryPos.add(position);

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

	private static final String _FINDER_COLUMN_A_S_P_ASSETLISTENTRYID_2 =
		"assetListEntryAssetEntryRel.assetListEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_S_P_SEGMENTSENTRYID_2 =
		"assetListEntryAssetEntryRel.segmentsEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_S_P_POSITION_2 =
		"assetListEntryAssetEntryRel.position = ?";

	private FinderPath _finderPathWithPaginationFindByA_S_GtP;
	private FinderPath _finderPathWithPaginationCountByA_S_GtP;

	/**
	 * Returns all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @return the matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByA_S_GtP(
		long assetListEntryId, long segmentsEntryId, int position) {

		return findByA_S_GtP(
			assetListEntryId, segmentsEntryId, position, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @return the range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByA_S_GtP(
		long assetListEntryId, long segmentsEntryId, int position, int start,
		int end) {

		return findByA_S_GtP(
			assetListEntryId, segmentsEntryId, position, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByA_S_GtP(
		long assetListEntryId, long segmentsEntryId, int position, int start,
		int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		return findByA_S_GtP(
			assetListEntryId, segmentsEntryId, position, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findByA_S_GtP(
		long assetListEntryId, long segmentsEntryId, int position, int start,
		int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByA_S_GtP;
		finderArgs = new Object[] {
			assetListEntryId, segmentsEntryId, position, start, end,
			orderByComparator
		};

		List<AssetListEntryAssetEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntryAssetEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
						list) {

					if ((assetListEntryId !=
							assetListEntryAssetEntryRel.
								getAssetListEntryId()) ||
						(segmentsEntryId !=
							assetListEntryAssetEntryRel.getSegmentsEntryId()) ||
						(position >=
							assetListEntryAssetEntryRel.getPosition())) {

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

			sb.append(_SQL_SELECT_ASSETLISTENTRYASSETENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_A_S_GTP_ASSETLISTENTRYID_2);

			sb.append(_FINDER_COLUMN_A_S_GTP_SEGMENTSENTRYID_2);

			sb.append(_FINDER_COLUMN_A_S_GTP_POSITION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryAssetEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetListEntryId);

				queryPos.add(segmentsEntryId);

				queryPos.add(position);

				list = (List<AssetListEntryAssetEntryRel>)QueryUtil.list(
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
	 * Returns the first asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel findByA_S_GtP_First(
			long assetListEntryId, long segmentsEntryId, int position,
			OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			fetchByA_S_GtP_First(
				assetListEntryId, segmentsEntryId, position, orderByComparator);

		if (assetListEntryAssetEntryRel != null) {
			return assetListEntryAssetEntryRel;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetListEntryId=");
		sb.append(assetListEntryId);

		sb.append(", segmentsEntryId=");
		sb.append(segmentsEntryId);

		sb.append(", position>");
		sb.append(position);

		sb.append("}");

		throw new NoSuchEntryAssetEntryRelException(sb.toString());
	}

	/**
	 * Returns the first asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel fetchByA_S_GtP_First(
		long assetListEntryId, long segmentsEntryId, int position,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		List<AssetListEntryAssetEntryRel> list = findByA_S_GtP(
			assetListEntryId, segmentsEntryId, position, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel findByA_S_GtP_Last(
			long assetListEntryId, long segmentsEntryId, int position,
			OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			fetchByA_S_GtP_Last(
				assetListEntryId, segmentsEntryId, position, orderByComparator);

		if (assetListEntryAssetEntryRel != null) {
			return assetListEntryAssetEntryRel;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetListEntryId=");
		sb.append(assetListEntryId);

		sb.append(", segmentsEntryId=");
		sb.append(segmentsEntryId);

		sb.append(", position>");
		sb.append(position);

		sb.append("}");

		throw new NoSuchEntryAssetEntryRelException(sb.toString());
	}

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel fetchByA_S_GtP_Last(
		long assetListEntryId, long segmentsEntryId, int position,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		int count = countByA_S_GtP(assetListEntryId, segmentsEntryId, position);

		if (count == 0) {
			return null;
		}

		List<AssetListEntryAssetEntryRel> list = findByA_S_GtP(
			assetListEntryId, segmentsEntryId, position, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry asset entry rels before and after the current asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the current asset list entry asset entry rel
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel[] findByA_S_GtP_PrevAndNext(
			long assetListEntryAssetEntryRelId, long assetListEntryId,
			long segmentsEntryId, int position,
			OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			findByPrimaryKey(assetListEntryAssetEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntryAssetEntryRel[] array =
				new AssetListEntryAssetEntryRelImpl[3];

			array[0] = getByA_S_GtP_PrevAndNext(
				session, assetListEntryAssetEntryRel, assetListEntryId,
				segmentsEntryId, position, orderByComparator, true);

			array[1] = assetListEntryAssetEntryRel;

			array[2] = getByA_S_GtP_PrevAndNext(
				session, assetListEntryAssetEntryRel, assetListEntryId,
				segmentsEntryId, position, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntryAssetEntryRel getByA_S_GtP_PrevAndNext(
		Session session,
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel,
		long assetListEntryId, long segmentsEntryId, int position,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETLISTENTRYASSETENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_A_S_GTP_ASSETLISTENTRYID_2);

		sb.append(_FINDER_COLUMN_A_S_GTP_SEGMENTSENTRYID_2);

		sb.append(_FINDER_COLUMN_A_S_GTP_POSITION_2);

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
			sb.append(AssetListEntryAssetEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(assetListEntryId);

		queryPos.add(segmentsEntryId);

		queryPos.add(position);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntryAssetEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntryAssetEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 */
	@Override
	public void removeByA_S_GtP(
		long assetListEntryId, long segmentsEntryId, int position) {

		for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
				findByA_S_GtP(
					assetListEntryId, segmentsEntryId, position,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetListEntryAssetEntryRel);
		}
	}

	/**
	 * Returns the number of asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @return the number of matching asset list entry asset entry rels
	 */
	@Override
	public int countByA_S_GtP(
		long assetListEntryId, long segmentsEntryId, int position) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByA_S_GtP;

			finderArgs = new Object[] {
				assetListEntryId, segmentsEntryId, position
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ASSETLISTENTRYASSETENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_A_S_GTP_ASSETLISTENTRYID_2);

			sb.append(_FINDER_COLUMN_A_S_GTP_SEGMENTSENTRYID_2);

			sb.append(_FINDER_COLUMN_A_S_GTP_POSITION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetListEntryId);

				queryPos.add(segmentsEntryId);

				queryPos.add(position);

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

	private static final String _FINDER_COLUMN_A_S_GTP_ASSETLISTENTRYID_2 =
		"assetListEntryAssetEntryRel.assetListEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_S_GTP_SEGMENTSENTRYID_2 =
		"assetListEntryAssetEntryRel.segmentsEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_S_GTP_POSITION_2 =
		"assetListEntryAssetEntryRel.position > ?";

	public AssetListEntryAssetEntryRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AssetListEntryAssetEntryRel.class);

		setModelImplClass(AssetListEntryAssetEntryRelImpl.class);
		setModelPKClass(long.class);

		setTable(AssetListEntryAssetEntryRelTable.INSTANCE);
	}

	/**
	 * Caches the asset list entry asset entry rel in the entity cache if it is enabled.
	 *
	 * @param assetListEntryAssetEntryRel the asset list entry asset entry rel
	 */
	@Override
	public void cacheResult(
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel) {

		if (assetListEntryAssetEntryRel.getCtCollectionId() != 0) {
			assetListEntryAssetEntryRel.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			AssetListEntryAssetEntryRelImpl.class,
			assetListEntryAssetEntryRel.getPrimaryKey(),
			assetListEntryAssetEntryRel);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				assetListEntryAssetEntryRel.getUuid(),
				assetListEntryAssetEntryRel.getGroupId()
			},
			assetListEntryAssetEntryRel);

		finderCache.putResult(
			_finderPathFetchByA_S_P,
			new Object[] {
				assetListEntryAssetEntryRel.getAssetListEntryId(),
				assetListEntryAssetEntryRel.getSegmentsEntryId(),
				assetListEntryAssetEntryRel.getPosition()
			},
			assetListEntryAssetEntryRel);

		assetListEntryAssetEntryRel.resetOriginalValues();
	}

	/**
	 * Caches the asset list entry asset entry rels in the entity cache if it is enabled.
	 *
	 * @param assetListEntryAssetEntryRels the asset list entry asset entry rels
	 */
	@Override
	public void cacheResult(
		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels) {

		for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
				assetListEntryAssetEntryRels) {

			if (assetListEntryAssetEntryRel.getCtCollectionId() != 0) {
				assetListEntryAssetEntryRel.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					AssetListEntryAssetEntryRelImpl.class,
					assetListEntryAssetEntryRel.getPrimaryKey()) == null) {

				cacheResult(assetListEntryAssetEntryRel);
			}
			else {
				assetListEntryAssetEntryRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset list entry asset entry rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetListEntryAssetEntryRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset list entry asset entry rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel) {

		entityCache.removeResult(
			AssetListEntryAssetEntryRelImpl.class,
			assetListEntryAssetEntryRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(AssetListEntryAssetEntryRelModelImpl)assetListEntryAssetEntryRel,
			true);
	}

	@Override
	public void clearCache(
		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
				assetListEntryAssetEntryRels) {

			entityCache.removeResult(
				AssetListEntryAssetEntryRelImpl.class,
				assetListEntryAssetEntryRel.getPrimaryKey());

			clearUniqueFindersCache(
				(AssetListEntryAssetEntryRelModelImpl)
					assetListEntryAssetEntryRel,
				true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AssetListEntryAssetEntryRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetListEntryAssetEntryRelModelImpl
			assetListEntryAssetEntryRelModelImpl) {

		Object[] args = new Object[] {
			assetListEntryAssetEntryRelModelImpl.getUuid(),
			assetListEntryAssetEntryRelModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args,
			assetListEntryAssetEntryRelModelImpl, false);

		args = new Object[] {
			assetListEntryAssetEntryRelModelImpl.getAssetListEntryId(),
			assetListEntryAssetEntryRelModelImpl.getSegmentsEntryId(),
			assetListEntryAssetEntryRelModelImpl.getPosition()
		};

		finderCache.putResult(
			_finderPathCountByA_S_P, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByA_S_P, args, assetListEntryAssetEntryRelModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		AssetListEntryAssetEntryRelModelImpl
			assetListEntryAssetEntryRelModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetListEntryAssetEntryRelModelImpl.getUuid(),
				assetListEntryAssetEntryRelModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((assetListEntryAssetEntryRelModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetListEntryAssetEntryRelModelImpl.getOriginalUuid(),
				assetListEntryAssetEntryRelModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetListEntryAssetEntryRelModelImpl.getAssetListEntryId(),
				assetListEntryAssetEntryRelModelImpl.getSegmentsEntryId(),
				assetListEntryAssetEntryRelModelImpl.getPosition()
			};

			finderCache.removeResult(_finderPathCountByA_S_P, args);
			finderCache.removeResult(_finderPathFetchByA_S_P, args);
		}

		if ((assetListEntryAssetEntryRelModelImpl.getColumnBitmask() &
			 _finderPathFetchByA_S_P.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetListEntryAssetEntryRelModelImpl.
					getOriginalAssetListEntryId(),
				assetListEntryAssetEntryRelModelImpl.
					getOriginalSegmentsEntryId(),
				assetListEntryAssetEntryRelModelImpl.getOriginalPosition()
			};

			finderCache.removeResult(_finderPathCountByA_S_P, args);
			finderCache.removeResult(_finderPathFetchByA_S_P, args);
		}
	}

	/**
	 * Creates a new asset list entry asset entry rel with the primary key. Does not add the asset list entry asset entry rel to the database.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key for the new asset list entry asset entry rel
	 * @return the new asset list entry asset entry rel
	 */
	@Override
	public AssetListEntryAssetEntryRel create(
		long assetListEntryAssetEntryRelId) {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			new AssetListEntryAssetEntryRelImpl();

		assetListEntryAssetEntryRel.setNew(true);
		assetListEntryAssetEntryRel.setPrimaryKey(
			assetListEntryAssetEntryRelId);

		String uuid = PortalUUIDUtil.generate();

		assetListEntryAssetEntryRel.setUuid(uuid);

		assetListEntryAssetEntryRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return assetListEntryAssetEntryRel;
	}

	/**
	 * Removes the asset list entry asset entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the asset list entry asset entry rel
	 * @return the asset list entry asset entry rel that was removed
	 * @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel remove(
			long assetListEntryAssetEntryRelId)
		throws NoSuchEntryAssetEntryRelException {

		return remove((Serializable)assetListEntryAssetEntryRelId);
	}

	/**
	 * Removes the asset list entry asset entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset list entry asset entry rel
	 * @return the asset list entry asset entry rel that was removed
	 * @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel remove(Serializable primaryKey)
		throws NoSuchEntryAssetEntryRelException {

		Session session = null;

		try {
			session = openSession();

			AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
				(AssetListEntryAssetEntryRel)session.get(
					AssetListEntryAssetEntryRelImpl.class, primaryKey);

			if (assetListEntryAssetEntryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryAssetEntryRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetListEntryAssetEntryRel);
		}
		catch (NoSuchEntryAssetEntryRelException noSuchEntityException) {
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
	protected AssetListEntryAssetEntryRel removeImpl(
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel) {

		if (!ctPersistenceHelper.isRemove(assetListEntryAssetEntryRel)) {
			return assetListEntryAssetEntryRel;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetListEntryAssetEntryRel)) {
				assetListEntryAssetEntryRel =
					(AssetListEntryAssetEntryRel)session.get(
						AssetListEntryAssetEntryRelImpl.class,
						assetListEntryAssetEntryRel.getPrimaryKeyObj());
			}

			if (assetListEntryAssetEntryRel != null) {
				session.delete(assetListEntryAssetEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetListEntryAssetEntryRel != null) {
			clearCache(assetListEntryAssetEntryRel);
		}

		return assetListEntryAssetEntryRel;
	}

	@Override
	public AssetListEntryAssetEntryRel updateImpl(
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel) {

		boolean isNew = assetListEntryAssetEntryRel.isNew();

		if (!(assetListEntryAssetEntryRel instanceof
				AssetListEntryAssetEntryRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					assetListEntryAssetEntryRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					assetListEntryAssetEntryRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetListEntryAssetEntryRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetListEntryAssetEntryRel implementation " +
					assetListEntryAssetEntryRel.getClass());
		}

		AssetListEntryAssetEntryRelModelImpl
			assetListEntryAssetEntryRelModelImpl =
				(AssetListEntryAssetEntryRelModelImpl)
					assetListEntryAssetEntryRel;

		if (Validator.isNull(assetListEntryAssetEntryRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetListEntryAssetEntryRel.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (assetListEntryAssetEntryRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetListEntryAssetEntryRel.setCreateDate(now);
			}
			else {
				assetListEntryAssetEntryRel.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!assetListEntryAssetEntryRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetListEntryAssetEntryRel.setModifiedDate(now);
			}
			else {
				assetListEntryAssetEntryRel.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(assetListEntryAssetEntryRel)) {
				if (!isNew) {
					session.evict(
						AssetListEntryAssetEntryRelImpl.class,
						assetListEntryAssetEntryRel.getPrimaryKeyObj());
				}

				session.save(assetListEntryAssetEntryRel);

				assetListEntryAssetEntryRel.setNew(false);
			}
			else {
				assetListEntryAssetEntryRel =
					(AssetListEntryAssetEntryRel)session.merge(
						assetListEntryAssetEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetListEntryAssetEntryRel.getCtCollectionId() != 0) {
			assetListEntryAssetEntryRel.resetOriginalValues();

			return assetListEntryAssetEntryRel;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				assetListEntryAssetEntryRelModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				assetListEntryAssetEntryRelModelImpl.getUuid(),
				assetListEntryAssetEntryRelModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				assetListEntryAssetEntryRelModelImpl.getAssetListEntryId()
			};

			finderCache.removeResult(_finderPathCountByAssetListEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAssetListEntryId, args);

			args = new Object[] {
				assetListEntryAssetEntryRelModelImpl.getAssetListEntryId(),
				assetListEntryAssetEntryRelModelImpl.getSegmentsEntryId()
			};

			finderCache.removeResult(_finderPathCountByA_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByA_S, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((assetListEntryAssetEntryRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetListEntryAssetEntryRelModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {
					assetListEntryAssetEntryRelModelImpl.getUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((assetListEntryAssetEntryRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetListEntryAssetEntryRelModelImpl.getOriginalUuid(),
					assetListEntryAssetEntryRelModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					assetListEntryAssetEntryRelModelImpl.getUuid(),
					assetListEntryAssetEntryRelModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((assetListEntryAssetEntryRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAssetListEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetListEntryAssetEntryRelModelImpl.
						getOriginalAssetListEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAssetListEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetListEntryId, args);

				args = new Object[] {
					assetListEntryAssetEntryRelModelImpl.getAssetListEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAssetListEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetListEntryId, args);
			}

			if ((assetListEntryAssetEntryRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByA_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetListEntryAssetEntryRelModelImpl.
						getOriginalAssetListEntryId(),
					assetListEntryAssetEntryRelModelImpl.
						getOriginalSegmentsEntryId()
				};

				finderCache.removeResult(_finderPathCountByA_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByA_S, args);

				args = new Object[] {
					assetListEntryAssetEntryRelModelImpl.getAssetListEntryId(),
					assetListEntryAssetEntryRelModelImpl.getSegmentsEntryId()
				};

				finderCache.removeResult(_finderPathCountByA_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByA_S, args);
			}
		}

		entityCache.putResult(
			AssetListEntryAssetEntryRelImpl.class,
			assetListEntryAssetEntryRel.getPrimaryKey(),
			assetListEntryAssetEntryRel, false);

		clearUniqueFindersCache(assetListEntryAssetEntryRelModelImpl, false);
		cacheUniqueFindersCache(assetListEntryAssetEntryRelModelImpl);

		assetListEntryAssetEntryRel.resetOriginalValues();

		return assetListEntryAssetEntryRel;
	}

	/**
	 * Returns the asset list entry asset entry rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset list entry asset entry rel
	 * @return the asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryAssetEntryRelException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			fetchByPrimaryKey(primaryKey);

		if (assetListEntryAssetEntryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryAssetEntryRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetListEntryAssetEntryRel;
	}

	/**
	 * Returns the asset list entry asset entry rel with the primary key or throws a <code>NoSuchEntryAssetEntryRelException</code> if it could not be found.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the asset list entry asset entry rel
	 * @return the asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel findByPrimaryKey(
			long assetListEntryAssetEntryRelId)
		throws NoSuchEntryAssetEntryRelException {

		return findByPrimaryKey((Serializable)assetListEntryAssetEntryRelId);
	}

	/**
	 * Returns the asset list entry asset entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset list entry asset entry rel
	 * @return the asset list entry asset entry rel, or <code>null</code> if a asset list entry asset entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				AssetListEntryAssetEntryRel.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel = null;

		Session session = null;

		try {
			session = openSession();

			assetListEntryAssetEntryRel =
				(AssetListEntryAssetEntryRel)session.get(
					AssetListEntryAssetEntryRelImpl.class, primaryKey);

			if (assetListEntryAssetEntryRel != null) {
				cacheResult(assetListEntryAssetEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return assetListEntryAssetEntryRel;
	}

	/**
	 * Returns the asset list entry asset entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the asset list entry asset entry rel
	 * @return the asset list entry asset entry rel, or <code>null</code> if a asset list entry asset entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntryAssetEntryRel fetchByPrimaryKey(
		long assetListEntryAssetEntryRelId) {

		return fetchByPrimaryKey((Serializable)assetListEntryAssetEntryRelId);
	}

	@Override
	public Map<Serializable, AssetListEntryAssetEntryRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				AssetListEntryAssetEntryRel.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetListEntryAssetEntryRel> map =
			new HashMap<Serializable, AssetListEntryAssetEntryRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
				fetchByPrimaryKey(primaryKey);

			if (assetListEntryAssetEntryRel != null) {
				map.put(primaryKey, assetListEntryAssetEntryRel);
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

			for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
					(List<AssetListEntryAssetEntryRel>)query.list()) {

				map.put(
					assetListEntryAssetEntryRel.getPrimaryKeyObj(),
					assetListEntryAssetEntryRel);

				cacheResult(assetListEntryAssetEntryRel);
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
	 * Returns all the asset list entry asset entry rels.
	 *
	 * @return the asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry asset entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @return the range of asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findAll(
		int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset list entry asset entry rels
	 */
	@Override
	public List<AssetListEntryAssetEntryRel> findAll(
		int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

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

		List<AssetListEntryAssetEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntryAssetEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ASSETLISTENTRYASSETENTRYREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETLISTENTRYASSETENTRYREL;

				sql = sql.concat(
					AssetListEntryAssetEntryRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AssetListEntryAssetEntryRel>)QueryUtil.list(
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
	 * Removes all the asset list entry asset entry rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
				findAll()) {

			remove(assetListEntryAssetEntryRel);
		}
	}

	/**
	 * Returns the number of asset list entry asset entry rels.
	 *
	 * @return the number of asset list entry asset entry rels
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntryAssetEntryRel.class);

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
					_SQL_COUNT_ASSETLISTENTRYASSETENTRYREL);

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
		return "assetListEntryAssetEntryRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETLISTENTRYASSETENTRYREL;
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
		return AssetListEntryAssetEntryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AssetListEntryAssetEntryRel";
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
		ctStrictColumnNames.add("assetEntryId");
		ctStrictColumnNames.add("segmentsEntryId");
		ctStrictColumnNames.add("position");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("assetListEntryAssetEntryRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"assetListEntryId", "segmentsEntryId", "position"});
	}

	/**
	 * Initializes the asset list entry asset entry rel persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			AssetListEntryAssetEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AssetListEntryAssetEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			AssetListEntryAssetEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			AssetListEntryAssetEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			AssetListEntryAssetEntryRelModelImpl.UUID_COLUMN_BITMASK |
			AssetListEntryAssetEntryRelModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			AssetListEntryAssetEntryRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			AssetListEntryAssetEntryRelModelImpl.UUID_COLUMN_BITMASK |
			AssetListEntryAssetEntryRelModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			AssetListEntryAssetEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			AssetListEntryAssetEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			AssetListEntryAssetEntryRelModelImpl.UUID_COLUMN_BITMASK |
			AssetListEntryAssetEntryRelModelImpl.COMPANYID_COLUMN_BITMASK |
			AssetListEntryAssetEntryRelModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByAssetListEntryId = new FinderPath(
			AssetListEntryAssetEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetListEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAssetListEntryId = new FinderPath(
			AssetListEntryAssetEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetListEntryId",
			new String[] {Long.class.getName()},
			AssetListEntryAssetEntryRelModelImpl.
				ASSETLISTENTRYID_COLUMN_BITMASK |
			AssetListEntryAssetEntryRelModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByAssetListEntryId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetListEntryId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByA_S = new FinderPath(
			AssetListEntryAssetEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByA_S = new FinderPath(
			AssetListEntryAssetEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA_S",
			new String[] {Long.class.getName(), Long.class.getName()},
			AssetListEntryAssetEntryRelModelImpl.
				ASSETLISTENTRYID_COLUMN_BITMASK |
			AssetListEntryAssetEntryRelModelImpl.
				SEGMENTSENTRYID_COLUMN_BITMASK |
			AssetListEntryAssetEntryRelModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByA_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_S",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationCountByA_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByA_S",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByA_S_P = new FinderPath(
			AssetListEntryAssetEntryRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByA_S_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			AssetListEntryAssetEntryRelModelImpl.
				ASSETLISTENTRYID_COLUMN_BITMASK |
			AssetListEntryAssetEntryRelModelImpl.
				SEGMENTSENTRYID_COLUMN_BITMASK |
			AssetListEntryAssetEntryRelModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByA_S_P = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByA_S_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByA_S_GtP = new FinderPath(
			AssetListEntryAssetEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_S_GtP",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByA_S_GtP = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByA_S_GtP",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(
			AssetListEntryAssetEntryRelImpl.class.getName());
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

	private static final String _SQL_SELECT_ASSETLISTENTRYASSETENTRYREL =
		"SELECT assetListEntryAssetEntryRel FROM AssetListEntryAssetEntryRel assetListEntryAssetEntryRel";

	private static final String _SQL_SELECT_ASSETLISTENTRYASSETENTRYREL_WHERE =
		"SELECT assetListEntryAssetEntryRel FROM AssetListEntryAssetEntryRel assetListEntryAssetEntryRel WHERE ";

	private static final String _SQL_COUNT_ASSETLISTENTRYASSETENTRYREL =
		"SELECT COUNT(assetListEntryAssetEntryRel) FROM AssetListEntryAssetEntryRel assetListEntryAssetEntryRel";

	private static final String _SQL_COUNT_ASSETLISTENTRYASSETENTRYREL_WHERE =
		"SELECT COUNT(assetListEntryAssetEntryRel) FROM AssetListEntryAssetEntryRel assetListEntryAssetEntryRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"assetListEntryAssetEntryRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetListEntryAssetEntryRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetListEntryAssetEntryRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetListEntryAssetEntryRelPersistenceImpl.class);

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