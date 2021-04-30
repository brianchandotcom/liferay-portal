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

package com.liferay.portlet.asset.service.persistence.impl;

import com.liferay.asset.kernel.exception.NoSuchTagException;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetTagTable;
import com.liferay.asset.kernel.service.persistence.AssetEntryPersistence;
import com.liferay.asset.kernel.service.persistence.AssetTagPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portlet.asset.model.impl.AssetTagImpl;
import com.liferay.portlet.asset.model.impl.AssetTagModelImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

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
import java.util.concurrent.ConcurrentHashMap;

/**
 * The persistence implementation for the asset tag service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetTagPersistenceImpl
	extends BasePersistenceImpl<AssetTag> implements AssetTagPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetTagUtil</code> to access the asset tag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetTagImpl.class.getName();

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
	 * Returns all the asset tags where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset tags
	 */
	@Override
	public List<AssetTag> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tags where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @return the range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tags where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetTag> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset tags where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetTag> orderByComparator, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

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

		List<AssetTag> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetTag>)FinderCacheUtil.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AssetTag assetTag : list) {
					if (!uuid.equals(assetTag.getUuid())) {
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

			sb.append(_SQL_SELECT_ASSETTAG_WHERE);

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
				sb.append(AssetTagModelImpl.ORDER_BY_JPQL);
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

				list = (List<AssetTag>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first asset tag in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag
	 * @throws NoSuchTagException if a matching asset tag could not be found
	 */
	@Override
	public AssetTag findByUuid_First(
			String uuid, OrderByComparator<AssetTag> orderByComparator)
		throws NoSuchTagException {

		AssetTag assetTag = fetchByUuid_First(uuid, orderByComparator);

		if (assetTag != null) {
			return assetTag;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchTagException(sb.toString());
	}

	/**
	 * Returns the first asset tag in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag, or <code>null</code> if a matching asset tag could not be found
	 */
	@Override
	public AssetTag fetchByUuid_First(
		String uuid, OrderByComparator<AssetTag> orderByComparator) {

		List<AssetTag> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset tag in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag
	 * @throws NoSuchTagException if a matching asset tag could not be found
	 */
	@Override
	public AssetTag findByUuid_Last(
			String uuid, OrderByComparator<AssetTag> orderByComparator)
		throws NoSuchTagException {

		AssetTag assetTag = fetchByUuid_Last(uuid, orderByComparator);

		if (assetTag != null) {
			return assetTag;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchTagException(sb.toString());
	}

	/**
	 * Returns the last asset tag in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag, or <code>null</code> if a matching asset tag could not be found
	 */
	@Override
	public AssetTag fetchByUuid_Last(
		String uuid, OrderByComparator<AssetTag> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AssetTag> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset tags before and after the current asset tag in the ordered set where uuid = &#63;.
	 *
	 * @param tagId the primary key of the current asset tag
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag
	 * @throws NoSuchTagException if a asset tag with the primary key could not be found
	 */
	@Override
	public AssetTag[] findByUuid_PrevAndNext(
			long tagId, String uuid,
			OrderByComparator<AssetTag> orderByComparator)
		throws NoSuchTagException {

		uuid = Objects.toString(uuid, "");

		AssetTag assetTag = findByPrimaryKey(tagId);

		Session session = null;

		try {
			session = openSession();

			AssetTag[] array = new AssetTagImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, assetTag, uuid, orderByComparator, true);

			array[1] = assetTag;

			array[2] = getByUuid_PrevAndNext(
				session, assetTag, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetTag getByUuid_PrevAndNext(
		Session session, AssetTag assetTag, String uuid,
		OrderByComparator<AssetTag> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ASSETTAG_WHERE);

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
			sb.append(AssetTagModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(assetTag)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetTag> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset tags where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AssetTag assetTag :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetTag);
		}
	}

	/**
	 * Returns the number of asset tags where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset tags
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETTAG_WHERE);

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
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		"assetTag.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(assetTag.uuid IS NULL OR assetTag.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the asset tag where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchTagException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset tag
	 * @throws NoSuchTagException if a matching asset tag could not be found
	 */
	@Override
	public AssetTag findByUUID_G(String uuid, long groupId)
		throws NoSuchTagException {

		AssetTag assetTag = fetchByUUID_G(uuid, groupId);

		if (assetTag == null) {
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

			throw new NoSuchTagException(sb.toString());
		}

		return assetTag;
	}

	/**
	 * Returns the asset tag where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset tag, or <code>null</code> if a matching asset tag could not be found
	 */
	@Override
	public AssetTag fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the asset tag where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset tag, or <code>null</code> if a matching asset tag could not be found
	 */
	@Override
	public AssetTag fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof AssetTag) {
			AssetTag assetTag = (AssetTag)result;

			if (!Objects.equals(uuid, assetTag.getUuid()) ||
				(groupId != assetTag.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ASSETTAG_WHERE);

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

				List<AssetTag> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					AssetTag assetTag = list.get(0);

					result = assetTag;

					cacheResult(assetTag);
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
			return (AssetTag)result;
		}
	}

	/**
	 * Removes the asset tag where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset tag that was removed
	 */
	@Override
	public AssetTag removeByUUID_G(String uuid, long groupId)
		throws NoSuchTagException {

		AssetTag assetTag = findByUUID_G(uuid, groupId);

		return remove(assetTag);
	}

	/**
	 * Returns the number of asset tags where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset tags
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G;

			finderArgs = new Object[] {uuid, groupId};

			count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETTAG_WHERE);

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
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		"assetTag.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(assetTag.uuid IS NULL OR assetTag.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"assetTag.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the asset tags where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset tags
	 */
	@Override
	public List<AssetTag> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tags where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @return the range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tags where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetTag> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset tags where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetTag> orderByComparator, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

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

		List<AssetTag> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetTag>)FinderCacheUtil.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AssetTag assetTag : list) {
					if (!uuid.equals(assetTag.getUuid()) ||
						(companyId != assetTag.getCompanyId())) {

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

			sb.append(_SQL_SELECT_ASSETTAG_WHERE);

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
				sb.append(AssetTagModelImpl.ORDER_BY_JPQL);
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

				list = (List<AssetTag>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first asset tag in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag
	 * @throws NoSuchTagException if a matching asset tag could not be found
	 */
	@Override
	public AssetTag findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetTag> orderByComparator)
		throws NoSuchTagException {

		AssetTag assetTag = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (assetTag != null) {
			return assetTag;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTagException(sb.toString());
	}

	/**
	 * Returns the first asset tag in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag, or <code>null</code> if a matching asset tag could not be found
	 */
	@Override
	public AssetTag fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetTag> orderByComparator) {

		List<AssetTag> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset tag in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag
	 * @throws NoSuchTagException if a matching asset tag could not be found
	 */
	@Override
	public AssetTag findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetTag> orderByComparator)
		throws NoSuchTagException {

		AssetTag assetTag = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (assetTag != null) {
			return assetTag;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTagException(sb.toString());
	}

	/**
	 * Returns the last asset tag in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag, or <code>null</code> if a matching asset tag could not be found
	 */
	@Override
	public AssetTag fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetTag> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AssetTag> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset tags before and after the current asset tag in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param tagId the primary key of the current asset tag
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag
	 * @throws NoSuchTagException if a asset tag with the primary key could not be found
	 */
	@Override
	public AssetTag[] findByUuid_C_PrevAndNext(
			long tagId, String uuid, long companyId,
			OrderByComparator<AssetTag> orderByComparator)
		throws NoSuchTagException {

		uuid = Objects.toString(uuid, "");

		AssetTag assetTag = findByPrimaryKey(tagId);

		Session session = null;

		try {
			session = openSession();

			AssetTag[] array = new AssetTagImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, assetTag, uuid, companyId, orderByComparator, true);

			array[1] = assetTag;

			array[2] = getByUuid_C_PrevAndNext(
				session, assetTag, uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetTag getByUuid_C_PrevAndNext(
		Session session, AssetTag assetTag, String uuid, long companyId,
		OrderByComparator<AssetTag> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ASSETTAG_WHERE);

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
			sb.append(AssetTagModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(assetTag)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetTag> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset tags where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AssetTag assetTag :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetTag);
		}
	}

	/**
	 * Returns the number of asset tags where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset tags
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETTAG_WHERE);

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
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		"assetTag.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(assetTag.uuid IS NULL OR assetTag.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"assetTag.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private FinderPath _finderPathWithPaginationCountByGroupId;

	/**
	 * Returns all the asset tags where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset tags
	 */
	@Override
	public List<AssetTag> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tags where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @return the range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tags where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetTag> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset tags where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetTag> orderByComparator, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<AssetTag> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetTag>)FinderCacheUtil.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AssetTag assetTag : list) {
					if (groupId != assetTag.getGroupId()) {
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

			sb.append(_SQL_SELECT_ASSETTAG_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetTagModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<AssetTag>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first asset tag in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag
	 * @throws NoSuchTagException if a matching asset tag could not be found
	 */
	@Override
	public AssetTag findByGroupId_First(
			long groupId, OrderByComparator<AssetTag> orderByComparator)
		throws NoSuchTagException {

		AssetTag assetTag = fetchByGroupId_First(groupId, orderByComparator);

		if (assetTag != null) {
			return assetTag;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchTagException(sb.toString());
	}

	/**
	 * Returns the first asset tag in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag, or <code>null</code> if a matching asset tag could not be found
	 */
	@Override
	public AssetTag fetchByGroupId_First(
		long groupId, OrderByComparator<AssetTag> orderByComparator) {

		List<AssetTag> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset tag in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag
	 * @throws NoSuchTagException if a matching asset tag could not be found
	 */
	@Override
	public AssetTag findByGroupId_Last(
			long groupId, OrderByComparator<AssetTag> orderByComparator)
		throws NoSuchTagException {

		AssetTag assetTag = fetchByGroupId_Last(groupId, orderByComparator);

		if (assetTag != null) {
			return assetTag;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchTagException(sb.toString());
	}

	/**
	 * Returns the last asset tag in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag, or <code>null</code> if a matching asset tag could not be found
	 */
	@Override
	public AssetTag fetchByGroupId_Last(
		long groupId, OrderByComparator<AssetTag> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<AssetTag> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset tags before and after the current asset tag in the ordered set where groupId = &#63;.
	 *
	 * @param tagId the primary key of the current asset tag
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag
	 * @throws NoSuchTagException if a asset tag with the primary key could not be found
	 */
	@Override
	public AssetTag[] findByGroupId_PrevAndNext(
			long tagId, long groupId,
			OrderByComparator<AssetTag> orderByComparator)
		throws NoSuchTagException {

		AssetTag assetTag = findByPrimaryKey(tagId);

		Session session = null;

		try {
			session = openSession();

			AssetTag[] array = new AssetTagImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, assetTag, groupId, orderByComparator, true);

			array[1] = assetTag;

			array[2] = getByGroupId_PrevAndNext(
				session, assetTag, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetTag getByGroupId_PrevAndNext(
		Session session, AssetTag assetTag, long groupId,
		OrderByComparator<AssetTag> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ASSETTAG_WHERE);

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
			sb.append(AssetTagModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(assetTag)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetTag> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset tags where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching asset tags
	 */
	@Override
	public List<AssetTag> findByGroupId(long[] groupIds) {
		return findByGroupId(
			groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tags where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @return the range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByGroupId(long[] groupIds, int start, int end) {
		return findByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tags where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<AssetTag> orderByComparator) {

		return findByGroupId(groupIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset tags where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<AssetTag> orderByComparator, boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (groupIds.length == 1) {
			return findByGroupId(groupIds[0], start, end, orderByComparator);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {StringUtil.merge(groupIds)};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), start, end, orderByComparator
			};
		}

		List<AssetTag> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetTag>)FinderCacheUtil.getResult(
				_finderPathWithPaginationFindByGroupId, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AssetTag assetTag : list) {
					if (!ArrayUtil.contains(groupIds, assetTag.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_ASSETTAG_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

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
				sb.append(AssetTagModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AssetTag>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationFindByGroupId, finderArgs,
						list);
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
	 * Removes all the asset tags where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (AssetTag assetTag :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetTag);
		}
	}

	/**
	 * Returns the number of asset tags where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset tags
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId;

			finderArgs = new Object[] {groupId};

			count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETTAG_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	 * Returns the number of asset tags where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching asset tags
	 */
	@Override
	public int countByGroupId(long[] groupIds) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {StringUtil.merge(groupIds)};

			count = (Long)FinderCacheUtil.getResult(
				_finderPathWithPaginationCountByGroupId, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_ASSETTAG_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

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

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationCountByGroupId, finderArgs,
						count);
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"assetTag.groupId = ?";

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_7 =
		"assetTag.groupId IN (";

	private FinderPath _finderPathWithPaginationFindByName;
	private FinderPath _finderPathWithoutPaginationFindByName;
	private FinderPath _finderPathCountByName;
	private FinderPath _finderPathWithPaginationCountByName;

	/**
	 * Returns all the asset tags where name = &#63;.
	 *
	 * @param name the name
	 * @return the matching asset tags
	 */
	@Override
	public List<AssetTag> findByName(String name) {
		return findByName(name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tags where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @return the range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByName(String name, int start, int end) {
		return findByName(name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tags where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByName(
		String name, int start, int end,
		OrderByComparator<AssetTag> orderByComparator) {

		return findByName(name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset tags where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByName(
		String name, int start, int end,
		OrderByComparator<AssetTag> orderByComparator, boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByName;
				finderArgs = new Object[] {name};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByName;
			finderArgs = new Object[] {name, start, end, orderByComparator};
		}

		List<AssetTag> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetTag>)FinderCacheUtil.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AssetTag assetTag : list) {
					if (!name.equals(assetTag.getName())) {
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

			sb.append(_SQL_SELECT_ASSETTAG_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetTagModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
				}

				list = (List<AssetTag>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first asset tag in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag
	 * @throws NoSuchTagException if a matching asset tag could not be found
	 */
	@Override
	public AssetTag findByName_First(
			String name, OrderByComparator<AssetTag> orderByComparator)
		throws NoSuchTagException {

		AssetTag assetTag = fetchByName_First(name, orderByComparator);

		if (assetTag != null) {
			return assetTag;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchTagException(sb.toString());
	}

	/**
	 * Returns the first asset tag in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag, or <code>null</code> if a matching asset tag could not be found
	 */
	@Override
	public AssetTag fetchByName_First(
		String name, OrderByComparator<AssetTag> orderByComparator) {

		List<AssetTag> list = findByName(name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset tag in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag
	 * @throws NoSuchTagException if a matching asset tag could not be found
	 */
	@Override
	public AssetTag findByName_Last(
			String name, OrderByComparator<AssetTag> orderByComparator)
		throws NoSuchTagException {

		AssetTag assetTag = fetchByName_Last(name, orderByComparator);

		if (assetTag != null) {
			return assetTag;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchTagException(sb.toString());
	}

	/**
	 * Returns the last asset tag in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag, or <code>null</code> if a matching asset tag could not be found
	 */
	@Override
	public AssetTag fetchByName_Last(
		String name, OrderByComparator<AssetTag> orderByComparator) {

		int count = countByName(name);

		if (count == 0) {
			return null;
		}

		List<AssetTag> list = findByName(
			name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset tags before and after the current asset tag in the ordered set where name = &#63;.
	 *
	 * @param tagId the primary key of the current asset tag
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag
	 * @throws NoSuchTagException if a asset tag with the primary key could not be found
	 */
	@Override
	public AssetTag[] findByName_PrevAndNext(
			long tagId, String name,
			OrderByComparator<AssetTag> orderByComparator)
		throws NoSuchTagException {

		name = Objects.toString(name, "");

		AssetTag assetTag = findByPrimaryKey(tagId);

		Session session = null;

		try {
			session = openSession();

			AssetTag[] array = new AssetTagImpl[3];

			array[0] = getByName_PrevAndNext(
				session, assetTag, name, orderByComparator, true);

			array[1] = assetTag;

			array[2] = getByName_PrevAndNext(
				session, assetTag, name, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetTag getByName_PrevAndNext(
		Session session, AssetTag assetTag, String name,
		OrderByComparator<AssetTag> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ASSETTAG_WHERE);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_NAME_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_NAME_NAME_2);
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
			sb.append(AssetTagModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindName) {
			queryPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(assetTag)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetTag> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset tags where name = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param names the names
	 * @return the matching asset tags
	 */
	@Override
	public List<AssetTag> findByName(String[] names) {
		return findByName(names, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tags where name = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param names the names
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @return the range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByName(String[] names, int start, int end) {
		return findByName(names, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tags where name = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param names the names
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByName(
		String[] names, int start, int end,
		OrderByComparator<AssetTag> orderByComparator) {

		return findByName(names, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset tags where name = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByName(
		String[] names, int start, int end,
		OrderByComparator<AssetTag> orderByComparator, boolean useFinderCache) {

		if (names == null) {
			names = new String[0];
		}
		else if (names.length > 1) {
			for (int i = 0; i < names.length; i++) {
				names[i] = Objects.toString(names[i], "");
			}

			names = ArrayUtil.sortedUnique(names);
		}

		if (names.length == 1) {
			return findByName(names[0], start, end, orderByComparator);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {StringUtil.merge(names)};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(names), start, end, orderByComparator
			};
		}

		List<AssetTag> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetTag>)FinderCacheUtil.getResult(
				_finderPathWithPaginationFindByName, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AssetTag assetTag : list) {
					if (!ArrayUtil.contains(names, assetTag.getName())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_ASSETTAG_WHERE);

			if (names.length > 0) {
				sb.append("(");

				for (int i = 0; i < names.length; i++) {
					String name = names[i];

					if (name.isEmpty()) {
						sb.append(_FINDER_COLUMN_NAME_NAME_3);
					}
					else {
						sb.append(_FINDER_COLUMN_NAME_NAME_2);
					}

					if ((i + 1) < names.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetTagModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				for (String name : names) {
					if ((name != null) && !name.isEmpty()) {
						queryPos.add(name);
					}
				}

				list = (List<AssetTag>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationFindByName, finderArgs, list);
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
	 * Removes all the asset tags where name = &#63; from the database.
	 *
	 * @param name the name
	 */
	@Override
	public void removeByName(String name) {
		for (AssetTag assetTag :
				findByName(name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetTag);
		}
	}

	/**
	 * Returns the number of asset tags where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching asset tags
	 */
	@Override
	public int countByName(String name) {
		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByName;

			finderArgs = new Object[] {name};

			count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETTAG_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	 * Returns the number of asset tags where name = any &#63;.
	 *
	 * @param names the names
	 * @return the number of matching asset tags
	 */
	@Override
	public int countByName(String[] names) {
		if (names == null) {
			names = new String[0];
		}
		else if (names.length > 1) {
			for (int i = 0; i < names.length; i++) {
				names[i] = Objects.toString(names[i], "");
			}

			names = ArrayUtil.sortedUnique(names);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {StringUtil.merge(names)};

			count = (Long)FinderCacheUtil.getResult(
				_finderPathWithPaginationCountByName, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_ASSETTAG_WHERE);

			if (names.length > 0) {
				sb.append("(");

				for (int i = 0; i < names.length; i++) {
					String name = names[i];

					if (name.isEmpty()) {
						sb.append(_FINDER_COLUMN_NAME_NAME_3);
					}
					else {
						sb.append(_FINDER_COLUMN_NAME_NAME_2);
					}

					if ((i + 1) < names.length) {
						sb.append(WHERE_OR);
					}
				}

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

				for (String name : names) {
					if ((name != null) && !name.isEmpty()) {
						queryPos.add(name);
					}
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationCountByName, finderArgs,
						count);
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

	private static final String _FINDER_COLUMN_NAME_NAME_2 =
		"lower(assetTag.name) = ?";

	private static final String _FINDER_COLUMN_NAME_NAME_3 =
		"(assetTag.name IS NULL OR assetTag.name = '')";

	private FinderPath _finderPathFetchByG_N;
	private FinderPath _finderPathCountByG_N;

	/**
	 * Returns the asset tag where groupId = &#63; and name = &#63; or throws a <code>NoSuchTagException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching asset tag
	 * @throws NoSuchTagException if a matching asset tag could not be found
	 */
	@Override
	public AssetTag findByG_N(long groupId, String name)
		throws NoSuchTagException {

		AssetTag assetTag = fetchByG_N(groupId, name);

		if (assetTag == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTagException(sb.toString());
		}

		return assetTag;
	}

	/**
	 * Returns the asset tag where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching asset tag, or <code>null</code> if a matching asset tag could not be found
	 */
	@Override
	public AssetTag fetchByG_N(long groupId, String name) {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Returns the asset tag where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset tag, or <code>null</code> if a matching asset tag could not be found
	 */
	@Override
	public AssetTag fetchByG_N(
		long groupId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, name};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByG_N, finderArgs);
		}

		if (result instanceof AssetTag) {
			AssetTag assetTag = (AssetTag)result;

			if ((groupId != assetTag.getGroupId()) ||
				!Objects.equals(name, assetTag.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ASSETTAG_WHERE);

			sb.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
				}

				List<AssetTag> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByG_N, finderArgs, list);
					}
				}
				else {
					AssetTag assetTag = list.get(0);

					result = assetTag;

					cacheResult(assetTag);
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
			return (AssetTag)result;
		}
	}

	/**
	 * Removes the asset tag where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the asset tag that was removed
	 */
	@Override
	public AssetTag removeByG_N(long groupId, String name)
		throws NoSuchTagException {

		AssetTag assetTag = findByG_N(groupId, name);

		return remove(assetTag);
	}

	/**
	 * Returns the number of asset tags where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching asset tags
	 */
	@Override
	public int countByG_N(long groupId, String name) {
		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_N;

			finderArgs = new Object[] {groupId, name};

			count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETTAG_WHERE);

			sb.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_N_GROUPID_2 =
		"assetTag.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_N_NAME_2 =
		"lower(assetTag.name) = ?";

	private static final String _FINDER_COLUMN_G_N_NAME_3 =
		"(assetTag.name IS NULL OR assetTag.name = '')";

	private FinderPath _finderPathWithPaginationFindByG_LikeN;
	private FinderPath _finderPathWithPaginationCountByG_LikeN;

	/**
	 * Returns all the asset tags where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching asset tags
	 */
	@Override
	public List<AssetTag> findByG_LikeN(long groupId, String name) {
		return findByG_LikeN(
			groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tags where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @return the range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByG_LikeN(
		long groupId, String name, int start, int end) {

		return findByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tags where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<AssetTag> orderByComparator) {

		return findByG_LikeN(
			groupId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset tags where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<AssetTag> orderByComparator, boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_LikeN;
		finderArgs = new Object[] {
			groupId, name, start, end, orderByComparator
		};

		List<AssetTag> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetTag>)FinderCacheUtil.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AssetTag assetTag : list) {
					if ((groupId != assetTag.getGroupId()) ||
						!StringUtil.wildcardMatches(
							assetTag.getName(), name, '_', '%', '\\', false)) {

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

			sb.append(_SQL_SELECT_ASSETTAG_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetTagModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
				}

				list = (List<AssetTag>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first asset tag in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag
	 * @throws NoSuchTagException if a matching asset tag could not be found
	 */
	@Override
	public AssetTag findByG_LikeN_First(
			long groupId, String name,
			OrderByComparator<AssetTag> orderByComparator)
		throws NoSuchTagException {

		AssetTag assetTag = fetchByG_LikeN_First(
			groupId, name, orderByComparator);

		if (assetTag != null) {
			return assetTag;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchTagException(sb.toString());
	}

	/**
	 * Returns the first asset tag in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag, or <code>null</code> if a matching asset tag could not be found
	 */
	@Override
	public AssetTag fetchByG_LikeN_First(
		long groupId, String name,
		OrderByComparator<AssetTag> orderByComparator) {

		List<AssetTag> list = findByG_LikeN(
			groupId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset tag in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag
	 * @throws NoSuchTagException if a matching asset tag could not be found
	 */
	@Override
	public AssetTag findByG_LikeN_Last(
			long groupId, String name,
			OrderByComparator<AssetTag> orderByComparator)
		throws NoSuchTagException {

		AssetTag assetTag = fetchByG_LikeN_Last(
			groupId, name, orderByComparator);

		if (assetTag != null) {
			return assetTag;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchTagException(sb.toString());
	}

	/**
	 * Returns the last asset tag in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag, or <code>null</code> if a matching asset tag could not be found
	 */
	@Override
	public AssetTag fetchByG_LikeN_Last(
		long groupId, String name,
		OrderByComparator<AssetTag> orderByComparator) {

		int count = countByG_LikeN(groupId, name);

		if (count == 0) {
			return null;
		}

		List<AssetTag> list = findByG_LikeN(
			groupId, name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset tags before and after the current asset tag in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param tagId the primary key of the current asset tag
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag
	 * @throws NoSuchTagException if a asset tag with the primary key could not be found
	 */
	@Override
	public AssetTag[] findByG_LikeN_PrevAndNext(
			long tagId, long groupId, String name,
			OrderByComparator<AssetTag> orderByComparator)
		throws NoSuchTagException {

		name = Objects.toString(name, "");

		AssetTag assetTag = findByPrimaryKey(tagId);

		Session session = null;

		try {
			session = openSession();

			AssetTag[] array = new AssetTagImpl[3];

			array[0] = getByG_LikeN_PrevAndNext(
				session, assetTag, groupId, name, orderByComparator, true);

			array[1] = assetTag;

			array[2] = getByG_LikeN_PrevAndNext(
				session, assetTag, groupId, name, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetTag getByG_LikeN_PrevAndNext(
		Session session, AssetTag assetTag, long groupId, String name,
		OrderByComparator<AssetTag> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ASSETTAG_WHERE);

		sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
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
			sb.append(AssetTagModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindName) {
			queryPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(assetTag)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetTag> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset tags where groupId = any &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @return the matching asset tags
	 */
	@Override
	public List<AssetTag> findByG_LikeN(long[] groupIds, String name) {
		return findByG_LikeN(
			groupIds, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tags where groupId = any &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @return the range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByG_LikeN(
		long[] groupIds, String name, int start, int end) {

		return findByG_LikeN(groupIds, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tags where groupId = any &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByG_LikeN(
		long[] groupIds, String name, int start, int end,
		OrderByComparator<AssetTag> orderByComparator) {

		return findByG_LikeN(
			groupIds, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset tags where groupId = &#63; and name LIKE &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tags
	 */
	@Override
	public List<AssetTag> findByG_LikeN(
		long[] groupIds, String name, int start, int end,
		OrderByComparator<AssetTag> orderByComparator, boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		name = Objects.toString(name, "");

		if (groupIds.length == 1) {
			return findByG_LikeN(
				groupIds[0], name, start, end, orderByComparator);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {StringUtil.merge(groupIds), name};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), name, start, end, orderByComparator
			};
		}

		List<AssetTag> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetTag>)FinderCacheUtil.getResult(
				_finderPathWithPaginationFindByG_LikeN, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AssetTag assetTag : list) {
					if (!ArrayUtil.contains(groupIds, assetTag.getGroupId()) ||
						!StringUtil.wildcardMatches(
							assetTag.getName(), name, '_', '%', '\\', false)) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_ASSETTAG_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetTagModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
				}

				list = (List<AssetTag>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationFindByG_LikeN, finderArgs,
						list);
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
	 * Removes all the asset tags where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	@Override
	public void removeByG_LikeN(long groupId, String name) {
		for (AssetTag assetTag :
				findByG_LikeN(
					groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetTag);
		}
	}

	/**
	 * Returns the number of asset tags where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching asset tags
	 */
	@Override
	public int countByG_LikeN(long groupId, String name) {
		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_LikeN;

			finderArgs = new Object[] {groupId, name};

			count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETTAG_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	 * Returns the number of asset tags where groupId = any &#63; and name LIKE &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @return the number of matching asset tags
	 */
	@Override
	public int countByG_LikeN(long[] groupIds, String name) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {StringUtil.merge(groupIds), name};

			count = (Long)FinderCacheUtil.getResult(
				_finderPathWithPaginationCountByG_LikeN, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_ASSETTAG_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationCountByG_LikeN, finderArgs,
						count);
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

	private static final String _FINDER_COLUMN_G_LIKEN_GROUPID_2 =
		"assetTag.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_LIKEN_GROUPID_7 =
		"assetTag.groupId IN (";

	private static final String _FINDER_COLUMN_G_LIKEN_NAME_2 =
		"lower(assetTag.name) LIKE ?";

	private static final String _FINDER_COLUMN_G_LIKEN_NAME_3 =
		"(assetTag.name IS NULL OR assetTag.name LIKE '')";

	public AssetTagPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AssetTag.class);

		setModelImplClass(AssetTagImpl.class);
		setModelPKClass(long.class);

		setTable(AssetTagTable.INSTANCE);
	}

	/**
	 * Caches the asset tag in the entity cache if it is enabled.
	 *
	 * @param assetTag the asset tag
	 */
	@Override
	public void cacheResult(AssetTag assetTag) {
		if (assetTag.getCtCollectionId() != 0) {
			return;
		}

		EntityCacheUtil.putResult(
			AssetTagImpl.class, assetTag.getPrimaryKey(), assetTag);

		FinderCacheUtil.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {assetTag.getUuid(), assetTag.getGroupId()}, assetTag);

		FinderCacheUtil.putResult(
			_finderPathFetchByG_N,
			new Object[] {assetTag.getGroupId(), assetTag.getName()}, assetTag);
	}

	/**
	 * Caches the asset tags in the entity cache if it is enabled.
	 *
	 * @param assetTags the asset tags
	 */
	@Override
	public void cacheResult(List<AssetTag> assetTags) {
		for (AssetTag assetTag : assetTags) {
			if (assetTag.getCtCollectionId() != 0) {
				continue;
			}

			if (EntityCacheUtil.getResult(
					AssetTagImpl.class, assetTag.getPrimaryKey()) == null) {

				cacheResult(assetTag);
			}
		}
	}

	/**
	 * Clears the cache for all asset tags.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(AssetTagImpl.class);

		FinderCacheUtil.clearCache(AssetTagImpl.class);
	}

	/**
	 * Clears the cache for the asset tag.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetTag assetTag) {
		EntityCacheUtil.removeResult(AssetTagImpl.class, assetTag);
	}

	@Override
	public void clearCache(List<AssetTag> assetTags) {
		for (AssetTag assetTag : assetTags) {
			EntityCacheUtil.removeResult(AssetTagImpl.class, assetTag);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(AssetTagImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(AssetTagImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetTagModelImpl assetTagModelImpl) {

		Object[] args = new Object[] {
			assetTagModelImpl.getUuid(), assetTagModelImpl.getGroupId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByUUID_G, args, assetTagModelImpl);

		args = new Object[] {
			assetTagModelImpl.getGroupId(), assetTagModelImpl.getName()
		};

		FinderCacheUtil.putResult(_finderPathCountByG_N, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByG_N, args, assetTagModelImpl);
	}

	/**
	 * Creates a new asset tag with the primary key. Does not add the asset tag to the database.
	 *
	 * @param tagId the primary key for the new asset tag
	 * @return the new asset tag
	 */
	@Override
	public AssetTag create(long tagId) {
		AssetTag assetTag = new AssetTagImpl();

		assetTag.setNew(true);
		assetTag.setPrimaryKey(tagId);

		String uuid = PortalUUIDUtil.generate();

		assetTag.setUuid(uuid);

		assetTag.setCompanyId(CompanyThreadLocal.getCompanyId());

		return assetTag;
	}

	/**
	 * Removes the asset tag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param tagId the primary key of the asset tag
	 * @return the asset tag that was removed
	 * @throws NoSuchTagException if a asset tag with the primary key could not be found
	 */
	@Override
	public AssetTag remove(long tagId) throws NoSuchTagException {
		return remove((Serializable)tagId);
	}

	/**
	 * Removes the asset tag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset tag
	 * @return the asset tag that was removed
	 * @throws NoSuchTagException if a asset tag with the primary key could not be found
	 */
	@Override
	public AssetTag remove(Serializable primaryKey) throws NoSuchTagException {
		Session session = null;

		try {
			session = openSession();

			AssetTag assetTag = (AssetTag)session.get(
				AssetTagImpl.class, primaryKey);

			if (assetTag == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTagException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetTag);
		}
		catch (NoSuchTagException noSuchEntityException) {
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
	protected AssetTag removeImpl(AssetTag assetTag) {
		assetTagToAssetEntryTableMapper.deleteLeftPrimaryKeyTableMappings(
			assetTag.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetTag)) {
				assetTag = (AssetTag)session.get(
					AssetTagImpl.class, assetTag.getPrimaryKeyObj());
			}

			if ((assetTag != null) &&
				CTPersistenceHelperUtil.isRemove(assetTag)) {

				session.delete(assetTag);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetTag != null) {
			clearCache(assetTag);
		}

		return assetTag;
	}

	@Override
	public AssetTag updateImpl(AssetTag assetTag) {
		boolean isNew = assetTag.isNew();

		if (!(assetTag instanceof AssetTagModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetTag.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(assetTag);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetTag proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetTag implementation " +
					assetTag.getClass());
		}

		AssetTagModelImpl assetTagModelImpl = (AssetTagModelImpl)assetTag;

		if (Validator.isNull(assetTag.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetTag.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (assetTag.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetTag.setCreateDate(now);
			}
			else {
				assetTag.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!assetTagModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetTag.setModifiedDate(now);
			}
			else {
				assetTag.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(assetTag)) {
				if (!isNew) {
					session.evict(
						AssetTagImpl.class, assetTag.getPrimaryKeyObj());
				}

				session.save(assetTag);
			}
			else {
				assetTag = (AssetTag)session.merge(assetTag);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetTag.getCtCollectionId() != 0) {
			if (isNew) {
				assetTag.setNew(false);
			}

			assetTag.resetOriginalValues();

			return assetTag;
		}

		EntityCacheUtil.putResult(
			AssetTagImpl.class, assetTagModelImpl, false, true);

		cacheUniqueFindersCache(assetTagModelImpl);

		if (isNew) {
			assetTag.setNew(false);
		}

		assetTag.resetOriginalValues();

		return assetTag;
	}

	/**
	 * Returns the asset tag with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset tag
	 * @return the asset tag
	 * @throws NoSuchTagException if a asset tag with the primary key could not be found
	 */
	@Override
	public AssetTag findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTagException {

		AssetTag assetTag = fetchByPrimaryKey(primaryKey);

		if (assetTag == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTagException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetTag;
	}

	/**
	 * Returns the asset tag with the primary key or throws a <code>NoSuchTagException</code> if it could not be found.
	 *
	 * @param tagId the primary key of the asset tag
	 * @return the asset tag
	 * @throws NoSuchTagException if a asset tag with the primary key could not be found
	 */
	@Override
	public AssetTag findByPrimaryKey(long tagId) throws NoSuchTagException {
		return findByPrimaryKey((Serializable)tagId);
	}

	/**
	 * Returns the asset tag with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset tag
	 * @return the asset tag, or <code>null</code> if a asset tag with the primary key could not be found
	 */
	@Override
	public AssetTag fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(AssetTag.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		AssetTag assetTag = null;

		Session session = null;

		try {
			session = openSession();

			assetTag = (AssetTag)session.get(AssetTagImpl.class, primaryKey);

			if (assetTag != null) {
				cacheResult(assetTag);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return assetTag;
	}

	/**
	 * Returns the asset tag with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param tagId the primary key of the asset tag
	 * @return the asset tag, or <code>null</code> if a asset tag with the primary key could not be found
	 */
	@Override
	public AssetTag fetchByPrimaryKey(long tagId) {
		return fetchByPrimaryKey((Serializable)tagId);
	}

	@Override
	public Map<Serializable, AssetTag> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(AssetTag.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetTag> map = new HashMap<Serializable, AssetTag>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetTag assetTag = fetchByPrimaryKey(primaryKey);

			if (assetTag != null) {
				map.put(primaryKey, assetTag);
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

			for (AssetTag assetTag : (List<AssetTag>)query.list()) {
				map.put(assetTag.getPrimaryKeyObj(), assetTag);

				cacheResult(assetTag);
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
	 * Returns all the asset tags.
	 *
	 * @return the asset tags
	 */
	@Override
	public List<AssetTag> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @return the range of asset tags
	 */
	@Override
	public List<AssetTag> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset tags
	 */
	@Override
	public List<AssetTag> findAll(
		int start, int end, OrderByComparator<AssetTag> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset tags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset tags
	 */
	@Override
	public List<AssetTag> findAll(
		int start, int end, OrderByComparator<AssetTag> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

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

		List<AssetTag> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetTag>)FinderCacheUtil.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ASSETTAG);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETTAG;

				sql = sql.concat(AssetTagModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AssetTag>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the asset tags from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetTag assetTag : findAll()) {
			remove(assetTag);
		}
	}

	/**
	 * Returns the number of asset tags.
	 *
	 * @return the number of asset tags
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetTag.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ASSETTAG);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
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

	/**
	 * Returns the primaryKeys of asset entries associated with the asset tag.
	 *
	 * @param pk the primary key of the asset tag
	 * @return long[] of the primaryKeys of asset entries associated with the asset tag
	 */
	@Override
	public long[] getAssetEntryPrimaryKeys(long pk) {
		long[] pks = assetTagToAssetEntryTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the asset entries associated with the asset tag.
	 *
	 * @param pk the primary key of the asset tag
	 * @return the asset entries associated with the asset tag
	 */
	@Override
	public List<com.liferay.asset.kernel.model.AssetEntry> getAssetEntries(
		long pk) {

		return getAssetEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the asset entries associated with the asset tag.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the asset tag
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @return the range of asset entries associated with the asset tag
	 */
	@Override
	public List<com.liferay.asset.kernel.model.AssetEntry> getAssetEntries(
		long pk, int start, int end) {

		return getAssetEntries(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entries associated with the asset tag.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the asset tag
	 * @param start the lower bound of the range of asset tags
	 * @param end the upper bound of the range of asset tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entries associated with the asset tag
	 */
	@Override
	public List<com.liferay.asset.kernel.model.AssetEntry> getAssetEntries(
		long pk, int start, int end,
		OrderByComparator<com.liferay.asset.kernel.model.AssetEntry>
			orderByComparator) {

		return assetTagToAssetEntryTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of asset entries associated with the asset tag.
	 *
	 * @param pk the primary key of the asset tag
	 * @return the number of asset entries associated with the asset tag
	 */
	@Override
	public int getAssetEntriesSize(long pk) {
		long[] pks = assetTagToAssetEntryTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the asset entry is associated with the asset tag.
	 *
	 * @param pk the primary key of the asset tag
	 * @param assetEntryPK the primary key of the asset entry
	 * @return <code>true</code> if the asset entry is associated with the asset tag; <code>false</code> otherwise
	 */
	@Override
	public boolean containsAssetEntry(long pk, long assetEntryPK) {
		return assetTagToAssetEntryTableMapper.containsTableMapping(
			pk, assetEntryPK);
	}

	/**
	 * Returns <code>true</code> if the asset tag has any asset entries associated with it.
	 *
	 * @param pk the primary key of the asset tag to check for associations with asset entries
	 * @return <code>true</code> if the asset tag has any asset entries associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsAssetEntries(long pk) {
		if (getAssetEntriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset tag
	 * @param assetEntryPK the primary key of the asset entry
	 */
	@Override
	public void addAssetEntry(long pk, long assetEntryPK) {
		AssetTag assetTag = fetchByPrimaryKey(pk);

		if (assetTag == null) {
			assetTagToAssetEntryTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, assetEntryPK);
		}
		else {
			assetTagToAssetEntryTableMapper.addTableMapping(
				assetTag.getCompanyId(), pk, assetEntryPK);
		}
	}

	/**
	 * Adds an association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset tag
	 * @param assetEntry the asset entry
	 */
	@Override
	public void addAssetEntry(
		long pk, com.liferay.asset.kernel.model.AssetEntry assetEntry) {

		AssetTag assetTag = fetchByPrimaryKey(pk);

		if (assetTag == null) {
			assetTagToAssetEntryTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				assetEntry.getPrimaryKey());
		}
		else {
			assetTagToAssetEntryTableMapper.addTableMapping(
				assetTag.getCompanyId(), pk, assetEntry.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset tag
	 * @param assetEntryPKs the primary keys of the asset entries
	 */
	@Override
	public void addAssetEntries(long pk, long[] assetEntryPKs) {
		long companyId = 0;

		AssetTag assetTag = fetchByPrimaryKey(pk);

		if (assetTag == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = assetTag.getCompanyId();
		}

		assetTagToAssetEntryTableMapper.addTableMappings(
			companyId, pk, assetEntryPKs);
	}

	/**
	 * Adds an association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset tag
	 * @param assetEntries the asset entries
	 */
	@Override
	public void addAssetEntries(
		long pk, List<com.liferay.asset.kernel.model.AssetEntry> assetEntries) {

		addAssetEntries(
			pk,
			ListUtil.toLongArray(
				assetEntries,
				com.liferay.asset.kernel.model.AssetEntry.ENTRY_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the asset tag and its asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset tag to clear the associated asset entries from
	 */
	@Override
	public void clearAssetEntries(long pk) {
		assetTagToAssetEntryTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset tag
	 * @param assetEntryPK the primary key of the asset entry
	 */
	@Override
	public void removeAssetEntry(long pk, long assetEntryPK) {
		assetTagToAssetEntryTableMapper.deleteTableMapping(pk, assetEntryPK);
	}

	/**
	 * Removes the association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset tag
	 * @param assetEntry the asset entry
	 */
	@Override
	public void removeAssetEntry(
		long pk, com.liferay.asset.kernel.model.AssetEntry assetEntry) {

		assetTagToAssetEntryTableMapper.deleteTableMapping(
			pk, assetEntry.getPrimaryKey());
	}

	/**
	 * Removes the association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset tag
	 * @param assetEntryPKs the primary keys of the asset entries
	 */
	@Override
	public void removeAssetEntries(long pk, long[] assetEntryPKs) {
		assetTagToAssetEntryTableMapper.deleteTableMappings(pk, assetEntryPKs);
	}

	/**
	 * Removes the association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset tag
	 * @param assetEntries the asset entries
	 */
	@Override
	public void removeAssetEntries(
		long pk, List<com.liferay.asset.kernel.model.AssetEntry> assetEntries) {

		removeAssetEntries(
			pk,
			ListUtil.toLongArray(
				assetEntries,
				com.liferay.asset.kernel.model.AssetEntry.ENTRY_ID_ACCESSOR));
	}

	/**
	 * Sets the asset entries associated with the asset tag, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset tag
	 * @param assetEntryPKs the primary keys of the asset entries to be associated with the asset tag
	 */
	@Override
	public void setAssetEntries(long pk, long[] assetEntryPKs) {
		Set<Long> newAssetEntryPKsSet = SetUtil.fromArray(assetEntryPKs);
		Set<Long> oldAssetEntryPKsSet = SetUtil.fromArray(
			assetTagToAssetEntryTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeAssetEntryPKsSet = new HashSet<Long>(
			oldAssetEntryPKsSet);

		removeAssetEntryPKsSet.removeAll(newAssetEntryPKsSet);

		assetTagToAssetEntryTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeAssetEntryPKsSet));

		newAssetEntryPKsSet.removeAll(oldAssetEntryPKsSet);

		long companyId = 0;

		AssetTag assetTag = fetchByPrimaryKey(pk);

		if (assetTag == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = assetTag.getCompanyId();
		}

		assetTagToAssetEntryTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newAssetEntryPKsSet));
	}

	/**
	 * Sets the asset entries associated with the asset tag, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset tag
	 * @param assetEntries the asset entries to be associated with the asset tag
	 */
	@Override
	public void setAssetEntries(
		long pk, List<com.liferay.asset.kernel.model.AssetEntry> assetEntries) {

		try {
			long[] assetEntryPKs = new long[assetEntries.size()];

			for (int i = 0; i < assetEntries.size(); i++) {
				com.liferay.asset.kernel.model.AssetEntry assetEntry =
					assetEntries.get(i);

				assetEntryPKs[i] = assetEntry.getPrimaryKey();
			}

			setAssetEntries(pk, assetEntryPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "tagId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETTAG;
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
		return AssetTagModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AssetTag";
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
		ctStrictColumnNames.add("name");
		ctMergeColumnNames.add("assetCount");
		ctStrictColumnNames.add("lastPublishDate");
		ctStrictColumnNames.add("entries");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("tagId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_mappingTableNames.add("AssetEntries_AssetTags");

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(new String[] {"groupId", "name"});
	}

	/**
	 * Initializes the asset tag persistence.
	 */
	public void afterPropertiesSet() {
		Registry registry = RegistryUtil.getRegistry();

		_argumentsResolverServiceRegistration = registry.registerService(
			ArgumentsResolver.class, new AssetTagModelArgumentsResolver());

		assetTagToAssetEntryTableMapper = TableMapperFactory.getTableMapper(
			"AssetEntries_AssetTags", "companyId", "tagId", "entryId", this,
			assetEntryPersistence);

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

		_finderPathWithPaginationCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathWithPaginationFindByName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByName",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"name"}, true);

		_finderPathWithoutPaginationFindByName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByName",
			new String[] {String.class.getName()}, new String[] {"name"}, true);

		_finderPathCountByName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByName",
			new String[] {String.class.getName()}, new String[] {"name"},
			false);

		_finderPathWithPaginationCountByName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByName",
			new String[] {String.class.getName()}, new String[] {"name"},
			false);

		_finderPathFetchByG_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "name"}, true);

		_finderPathCountByG_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "name"}, false);

		_finderPathWithPaginationFindByG_LikeN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "name"}, true);

		_finderPathWithPaginationCountByG_LikeN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_LikeN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "name"}, false);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(AssetTagImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();

		TableMapperFactory.removeTableMapper("AssetEntries_AssetTags");
	}

	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;

	protected TableMapper<AssetTag, com.liferay.asset.kernel.model.AssetEntry>
		assetTagToAssetEntryTableMapper;

	private static final String _SQL_SELECT_ASSETTAG =
		"SELECT assetTag FROM AssetTag assetTag";

	private static final String _SQL_SELECT_ASSETTAG_WHERE =
		"SELECT assetTag FROM AssetTag assetTag WHERE ";

	private static final String _SQL_COUNT_ASSETTAG =
		"SELECT COUNT(assetTag) FROM AssetTag assetTag";

	private static final String _SQL_COUNT_ASSETTAG_WHERE =
		"SELECT COUNT(assetTag) FROM AssetTag assetTag WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "assetTag.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetTag exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetTag exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetTagPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class AssetTagModelArgumentsResolver
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

			AssetTagModelImpl assetTagModelImpl = (AssetTagModelImpl)baseModel;

			long columnBitmask = assetTagModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(assetTagModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						assetTagModelImpl.getColumnBitmask(columnName);
				}

				if (finderPath.isBaseModelResult() &&
					(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION ==
						finderPath.getCacheName())) {

					finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(assetTagModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return AssetTagImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return AssetTagTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			AssetTagModelImpl assetTagModelImpl, String[] columnNames,
			boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] = assetTagModelImpl.getColumnOriginalValue(
						columnName);
				}
				else {
					arguments[i] = assetTagModelImpl.getColumnValue(columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

		private static final long _ORDER_BY_COLUMNS_BITMASK;

		static {
			long orderByColumnsBitmask = 0;

			orderByColumnsBitmask |= AssetTagModelImpl.getColumnBitmask("name");

			_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
		}

	}

}