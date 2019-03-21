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

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.list.exception.NoSuchAssetEntryAssetListEntryRelException;
import com.liferay.asset.list.model.AssetEntryAssetListEntryRel;
import com.liferay.asset.list.model.impl.AssetEntryAssetListEntryRelImpl;
import com.liferay.asset.list.model.impl.AssetEntryAssetListEntryRelModelImpl;
import com.liferay.asset.list.service.persistence.AssetEntryAssetListEntryRelPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the asset entry asset list entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class AssetEntryAssetListEntryRelPersistenceImpl
	extends BasePersistenceImpl<AssetEntryAssetListEntryRel>
	implements AssetEntryAssetListEntryRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetEntryAssetListEntryRelUtil</code> to access the asset entry asset list entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetEntryAssetListEntryRelImpl.class.getName();

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
	 * Returns all the asset entry asset list entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry asset list entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @return the range of matching asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid;
			finderArgs = new Object[] {uuid};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<AssetEntryAssetListEntryRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetEntryAssetListEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryAssetListEntryRel assetEntryAssetListEntryRel :
						list) {

					if (!uuid.equals(assetEntryAssetListEntryRel.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ASSETENTRYASSETLISTENTRYREL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(
					AssetEntryAssetListEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<AssetEntryAssetListEntryRel>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetEntryAssetListEntryRel>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset entry asset list entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel findByUuid_First(
			String uuid,
			OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			fetchByUuid_First(uuid, orderByComparator);

		if (assetEntryAssetListEntryRel != null) {
			return assetEntryAssetListEntryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchAssetEntryAssetListEntryRelException(msg.toString());
	}

	/**
	 * Returns the first asset entry asset list entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel fetchByUuid_First(
		String uuid,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator) {

		List<AssetEntryAssetListEntryRel> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry asset list entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel findByUuid_Last(
			String uuid,
			OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			fetchByUuid_Last(uuid, orderByComparator);

		if (assetEntryAssetListEntryRel != null) {
			return assetEntryAssetListEntryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchAssetEntryAssetListEntryRelException(msg.toString());
	}

	/**
	 * Returns the last asset entry asset list entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AssetEntryAssetListEntryRel> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry asset list entry rels before and after the current asset entry asset list entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the current asset entry asset list entry rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a asset entry asset list entry rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel[] findByUuid_PrevAndNext(
			long assetEntryAssetListEntryRelId, String uuid,
			OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException {

		uuid = Objects.toString(uuid, "");

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			findByPrimaryKey(assetEntryAssetListEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryAssetListEntryRel[] array =
				new AssetEntryAssetListEntryRelImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, assetEntryAssetListEntryRel, uuid, orderByComparator,
				true);

			array[1] = assetEntryAssetListEntryRel;

			array[2] = getByUuid_PrevAndNext(
				session, assetEntryAssetListEntryRel, uuid, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntryAssetListEntryRel getByUuid_PrevAndNext(
		Session session,
		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel, String uuid,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETENTRYASSETLISTENTRYREL_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AssetEntryAssetListEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntryAssetListEntryRel)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetEntryAssetListEntryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry asset list entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AssetEntryAssetListEntryRel assetEntryAssetListEntryRel :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetEntryAssetListEntryRel);
		}
	}

	/**
	 * Returns the number of asset entry asset list entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset entry asset list entry rels
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETENTRYASSETLISTENTRYREL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"assetEntryAssetListEntryRel.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(assetEntryAssetListEntryRel.uuid IS NULL OR assetEntryAssetListEntryRel.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the asset entry asset list entry rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchAssetEntryAssetListEntryRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel findByUUID_G(String uuid, long groupId)
		throws NoSuchAssetEntryAssetListEntryRelException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel = fetchByUUID_G(
			uuid, groupId);

		if (assetEntryAssetListEntryRel == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchAssetEntryAssetListEntryRelException(
				msg.toString());
		}

		return assetEntryAssetListEntryRel;
	}

	/**
	 * Returns the asset entry asset list entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel fetchByUUID_G(
		String uuid, long groupId) {

		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the asset entry asset list entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = new Object[] {uuid, groupId};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof AssetEntryAssetListEntryRel) {
			AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
				(AssetEntryAssetListEntryRel)result;

			if (!Objects.equals(uuid, assetEntryAssetListEntryRel.getUuid()) ||
				(groupId != assetEntryAssetListEntryRel.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETENTRYASSETLISTENTRYREL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<AssetEntryAssetListEntryRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByUUID_G, finderArgs, list);
				}
				else {
					AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
						list.get(0);

					result = assetEntryAssetListEntryRel;

					cacheResult(assetEntryAssetListEntryRel);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByUUID_G, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (AssetEntryAssetListEntryRel)result;
		}
	}

	/**
	 * Removes the asset entry asset list entry rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset entry asset list entry rel that was removed
	 */
	@Override
	public AssetEntryAssetListEntryRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchAssetEntryAssetListEntryRelException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel = findByUUID_G(
			uuid, groupId);

		return remove(assetEntryAssetListEntryRel);
	}

	/**
	 * Returns the number of asset entry asset list entry rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset entry asset list entry rels
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETENTRYASSETLISTENTRYREL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"assetEntryAssetListEntryRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(assetEntryAssetListEntryRel.uuid IS NULL OR assetEntryAssetListEntryRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"assetEntryAssetListEntryRel.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the asset entry asset list entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry asset list entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @return the range of matching asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid_C;
			finderArgs = new Object[] {uuid, companyId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<AssetEntryAssetListEntryRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetEntryAssetListEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryAssetListEntryRel assetEntryAssetListEntryRel :
						list) {

					if (!uuid.equals(assetEntryAssetListEntryRel.getUuid()) ||
						(companyId !=
							assetEntryAssetListEntryRel.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ASSETENTRYASSETLISTENTRYREL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(
					AssetEntryAssetListEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<AssetEntryAssetListEntryRel>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetEntryAssetListEntryRel>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset entry asset list entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (assetEntryAssetListEntryRel != null) {
			return assetEntryAssetListEntryRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchAssetEntryAssetListEntryRelException(msg.toString());
	}

	/**
	 * Returns the first asset entry asset list entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator) {

		List<AssetEntryAssetListEntryRel> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry asset list entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (assetEntryAssetListEntryRel != null) {
			return assetEntryAssetListEntryRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchAssetEntryAssetListEntryRelException(msg.toString());
	}

	/**
	 * Returns the last asset entry asset list entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AssetEntryAssetListEntryRel> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry asset list entry rels before and after the current asset entry asset list entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the current asset entry asset list entry rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a asset entry asset list entry rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel[] findByUuid_C_PrevAndNext(
			long assetEntryAssetListEntryRelId, String uuid, long companyId,
			OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException {

		uuid = Objects.toString(uuid, "");

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			findByPrimaryKey(assetEntryAssetListEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryAssetListEntryRel[] array =
				new AssetEntryAssetListEntryRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, assetEntryAssetListEntryRel, uuid, companyId,
				orderByComparator, true);

			array[1] = assetEntryAssetListEntryRel;

			array[2] = getByUuid_C_PrevAndNext(
				session, assetEntryAssetListEntryRel, uuid, companyId,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntryAssetListEntryRel getByUuid_C_PrevAndNext(
		Session session,
		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel, String uuid,
		long companyId,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_ASSETENTRYASSETLISTENTRYREL_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AssetEntryAssetListEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntryAssetListEntryRel)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetEntryAssetListEntryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry asset list entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AssetEntryAssetListEntryRel assetEntryAssetListEntryRel :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetEntryAssetListEntryRel);
		}
	}

	/**
	 * Returns the number of asset entry asset list entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset entry asset list entry rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETENTRYASSETLISTENTRYREL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"assetEntryAssetListEntryRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(assetEntryAssetListEntryRel.uuid IS NULL OR assetEntryAssetListEntryRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"assetEntryAssetListEntryRel.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByAssetListEntryId;
	private FinderPath _finderPathWithoutPaginationFindByAssetListEntryId;
	private FinderPath _finderPathCountByAssetListEntryId;

	/**
	 * Returns all the asset entry asset list entry rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the matching asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findByAssetListEntryId(
		long assetListEntryId) {

		return findByAssetListEntryId(
			assetListEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry asset list entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @return the range of matching asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end) {

		return findByAssetListEntryId(assetListEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator) {

		return findByAssetListEntryId(
			assetListEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByAssetListEntryId;
			finderArgs = new Object[] {assetListEntryId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByAssetListEntryId;
			finderArgs = new Object[] {
				assetListEntryId, start, end, orderByComparator
			};
		}

		List<AssetEntryAssetListEntryRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetEntryAssetListEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryAssetListEntryRel assetEntryAssetListEntryRel :
						list) {

					if ((assetListEntryId !=
							assetEntryAssetListEntryRel.
								getAssetListEntryId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ASSETENTRYASSETLISTENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_ASSETLISTENTRYID_ASSETLISTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(
					AssetEntryAssetListEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetListEntryId);

				if (!pagination) {
					list = (List<AssetEntryAssetListEntryRel>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetEntryAssetListEntryRel>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset entry asset list entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel findByAssetListEntryId_First(
			long assetListEntryId,
			OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			fetchByAssetListEntryId_First(assetListEntryId, orderByComparator);

		if (assetEntryAssetListEntryRel != null) {
			return assetEntryAssetListEntryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetListEntryId=");
		msg.append(assetListEntryId);

		msg.append("}");

		throw new NoSuchAssetEntryAssetListEntryRelException(msg.toString());
	}

	/**
	 * Returns the first asset entry asset list entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel fetchByAssetListEntryId_First(
		long assetListEntryId,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator) {

		List<AssetEntryAssetListEntryRel> list = findByAssetListEntryId(
			assetListEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry asset list entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel findByAssetListEntryId_Last(
			long assetListEntryId,
			OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			fetchByAssetListEntryId_Last(assetListEntryId, orderByComparator);

		if (assetEntryAssetListEntryRel != null) {
			return assetEntryAssetListEntryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetListEntryId=");
		msg.append(assetListEntryId);

		msg.append("}");

		throw new NoSuchAssetEntryAssetListEntryRelException(msg.toString());
	}

	/**
	 * Returns the last asset entry asset list entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel fetchByAssetListEntryId_Last(
		long assetListEntryId,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator) {

		int count = countByAssetListEntryId(assetListEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetEntryAssetListEntryRel> list = findByAssetListEntryId(
			assetListEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry asset list entry rels before and after the current asset entry asset list entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the current asset entry asset list entry rel
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a asset entry asset list entry rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel[] findByAssetListEntryId_PrevAndNext(
			long assetEntryAssetListEntryRelId, long assetListEntryId,
			OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			findByPrimaryKey(assetEntryAssetListEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryAssetListEntryRel[] array =
				new AssetEntryAssetListEntryRelImpl[3];

			array[0] = getByAssetListEntryId_PrevAndNext(
				session, assetEntryAssetListEntryRel, assetListEntryId,
				orderByComparator, true);

			array[1] = assetEntryAssetListEntryRel;

			array[2] = getByAssetListEntryId_PrevAndNext(
				session, assetEntryAssetListEntryRel, assetListEntryId,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntryAssetListEntryRel getByAssetListEntryId_PrevAndNext(
		Session session,
		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel,
		long assetListEntryId,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETENTRYASSETLISTENTRYREL_WHERE);

		query.append(_FINDER_COLUMN_ASSETLISTENTRYID_ASSETLISTENTRYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AssetEntryAssetListEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(assetListEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntryAssetListEntryRel)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetEntryAssetListEntryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry asset list entry rels where assetListEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 */
	@Override
	public void removeByAssetListEntryId(long assetListEntryId) {
		for (AssetEntryAssetListEntryRel assetEntryAssetListEntryRel :
				findByAssetListEntryId(
					assetListEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetEntryAssetListEntryRel);
		}
	}

	/**
	 * Returns the number of asset entry asset list entry rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the number of matching asset entry asset list entry rels
	 */
	@Override
	public int countByAssetListEntryId(long assetListEntryId) {
		FinderPath finderPath = _finderPathCountByAssetListEntryId;

		Object[] finderArgs = new Object[] {assetListEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETENTRYASSETLISTENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_ASSETLISTENTRYID_ASSETLISTENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetListEntryId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_ASSETLISTENTRYID_ASSETLISTENTRYID_2 =
			"assetEntryAssetListEntryRel.assetListEntryId = ?";

	private FinderPath _finderPathFetchByA_P;
	private FinderPath _finderPathCountByA_P;

	/**
	 * Returns the asset entry asset list entry rel where assetListEntryId = &#63; and position = &#63; or throws a <code>NoSuchAssetEntryAssetListEntryRelException</code> if it could not be found.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @return the matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel findByA_P(
			long assetListEntryId, int position)
		throws NoSuchAssetEntryAssetListEntryRelException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel = fetchByA_P(
			assetListEntryId, position);

		if (assetEntryAssetListEntryRel == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetListEntryId=");
			msg.append(assetListEntryId);

			msg.append(", position=");
			msg.append(position);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchAssetEntryAssetListEntryRelException(
				msg.toString());
		}

		return assetEntryAssetListEntryRel;
	}

	/**
	 * Returns the asset entry asset list entry rel where assetListEntryId = &#63; and position = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @return the matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel fetchByA_P(
		long assetListEntryId, int position) {

		return fetchByA_P(assetListEntryId, position, true);
	}

	/**
	 * Returns the asset entry asset list entry rel where assetListEntryId = &#63; and position = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel fetchByA_P(
		long assetListEntryId, int position, boolean retrieveFromCache) {

		Object[] finderArgs = new Object[] {assetListEntryId, position};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(
				_finderPathFetchByA_P, finderArgs, this);
		}

		if (result instanceof AssetEntryAssetListEntryRel) {
			AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
				(AssetEntryAssetListEntryRel)result;

			if ((assetListEntryId !=
					assetEntryAssetListEntryRel.getAssetListEntryId()) ||
				(position != assetEntryAssetListEntryRel.getPosition())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETENTRYASSETLISTENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_A_P_ASSETLISTENTRYID_2);

			query.append(_FINDER_COLUMN_A_P_POSITION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetListEntryId);

				qPos.add(position);

				List<AssetEntryAssetListEntryRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByA_P, finderArgs, list);
				}
				else {
					AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
						list.get(0);

					result = assetEntryAssetListEntryRel;

					cacheResult(assetEntryAssetListEntryRel);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByA_P, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (AssetEntryAssetListEntryRel)result;
		}
	}

	/**
	 * Removes the asset entry asset list entry rel where assetListEntryId = &#63; and position = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @return the asset entry asset list entry rel that was removed
	 */
	@Override
	public AssetEntryAssetListEntryRel removeByA_P(
			long assetListEntryId, int position)
		throws NoSuchAssetEntryAssetListEntryRelException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel = findByA_P(
			assetListEntryId, position);

		return remove(assetEntryAssetListEntryRel);
	}

	/**
	 * Returns the number of asset entry asset list entry rels where assetListEntryId = &#63; and position = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @return the number of matching asset entry asset list entry rels
	 */
	@Override
	public int countByA_P(long assetListEntryId, int position) {
		FinderPath finderPath = _finderPathCountByA_P;

		Object[] finderArgs = new Object[] {assetListEntryId, position};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETENTRYASSETLISTENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_A_P_ASSETLISTENTRYID_2);

			query.append(_FINDER_COLUMN_A_P_POSITION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetListEntryId);

				qPos.add(position);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_A_P_ASSETLISTENTRYID_2 =
		"assetEntryAssetListEntryRel.assetListEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_P_POSITION_2 =
		"assetEntryAssetListEntryRel.position = ?";

	private FinderPath _finderPathWithPaginationFindByA_GtP;
	private FinderPath _finderPathWithPaginationCountByA_GtP;

	/**
	 * Returns all the asset entry asset list entry rels where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @return the matching asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findByA_GtP(
		long assetListEntryId, int position) {

		return findByA_GtP(
			assetListEntryId, position, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset entry asset list entry rels where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @return the range of matching asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findByA_GtP(
		long assetListEntryId, int position, int start, int end) {

		return findByA_GtP(assetListEntryId, position, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findByA_GtP(
		long assetListEntryId, int position, int start, int end,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator) {

		return findByA_GtP(
			assetListEntryId, position, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findByA_GtP(
		long assetListEntryId, int position, int start, int end,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByA_GtP;
		finderArgs = new Object[] {
			assetListEntryId, position, start, end, orderByComparator
		};

		List<AssetEntryAssetListEntryRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetEntryAssetListEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryAssetListEntryRel assetEntryAssetListEntryRel :
						list) {

					if ((assetListEntryId !=
							assetEntryAssetListEntryRel.
								getAssetListEntryId()) ||
						(position >=
							assetEntryAssetListEntryRel.getPosition())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ASSETENTRYASSETLISTENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_A_GTP_ASSETLISTENTRYID_2);

			query.append(_FINDER_COLUMN_A_GTP_POSITION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(
					AssetEntryAssetListEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetListEntryId);

				qPos.add(position);

				if (!pagination) {
					list = (List<AssetEntryAssetListEntryRel>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetEntryAssetListEntryRel>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset entry asset list entry rel in the ordered set where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel findByA_GtP_First(
			long assetListEntryId, int position,
			OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			fetchByA_GtP_First(assetListEntryId, position, orderByComparator);

		if (assetEntryAssetListEntryRel != null) {
			return assetEntryAssetListEntryRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetListEntryId=");
		msg.append(assetListEntryId);

		msg.append(", position=");
		msg.append(position);

		msg.append("}");

		throw new NoSuchAssetEntryAssetListEntryRelException(msg.toString());
	}

	/**
	 * Returns the first asset entry asset list entry rel in the ordered set where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel fetchByA_GtP_First(
		long assetListEntryId, int position,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator) {

		List<AssetEntryAssetListEntryRel> list = findByA_GtP(
			assetListEntryId, position, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry asset list entry rel in the ordered set where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel findByA_GtP_Last(
			long assetListEntryId, int position,
			OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			fetchByA_GtP_Last(assetListEntryId, position, orderByComparator);

		if (assetEntryAssetListEntryRel != null) {
			return assetEntryAssetListEntryRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetListEntryId=");
		msg.append(assetListEntryId);

		msg.append(", position=");
		msg.append(position);

		msg.append("}");

		throw new NoSuchAssetEntryAssetListEntryRelException(msg.toString());
	}

	/**
	 * Returns the last asset entry asset list entry rel in the ordered set where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel fetchByA_GtP_Last(
		long assetListEntryId, int position,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator) {

		int count = countByA_GtP(assetListEntryId, position);

		if (count == 0) {
			return null;
		}

		List<AssetEntryAssetListEntryRel> list = findByA_GtP(
			assetListEntryId, position, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry asset list entry rels before and after the current asset entry asset list entry rel in the ordered set where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the current asset entry asset list entry rel
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a asset entry asset list entry rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel[] findByA_GtP_PrevAndNext(
			long assetEntryAssetListEntryRelId, long assetListEntryId,
			int position,
			OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			findByPrimaryKey(assetEntryAssetListEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryAssetListEntryRel[] array =
				new AssetEntryAssetListEntryRelImpl[3];

			array[0] = getByA_GtP_PrevAndNext(
				session, assetEntryAssetListEntryRel, assetListEntryId,
				position, orderByComparator, true);

			array[1] = assetEntryAssetListEntryRel;

			array[2] = getByA_GtP_PrevAndNext(
				session, assetEntryAssetListEntryRel, assetListEntryId,
				position, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntryAssetListEntryRel getByA_GtP_PrevAndNext(
		Session session,
		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel,
		long assetListEntryId, int position,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_ASSETENTRYASSETLISTENTRYREL_WHERE);

		query.append(_FINDER_COLUMN_A_GTP_ASSETLISTENTRYID_2);

		query.append(_FINDER_COLUMN_A_GTP_POSITION_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AssetEntryAssetListEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(assetListEntryId);

		qPos.add(position);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntryAssetListEntryRel)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetEntryAssetListEntryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry asset list entry rels where assetListEntryId = &#63; and position &gt; &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 */
	@Override
	public void removeByA_GtP(long assetListEntryId, int position) {
		for (AssetEntryAssetListEntryRel assetEntryAssetListEntryRel :
				findByA_GtP(
					assetListEntryId, position, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(assetEntryAssetListEntryRel);
		}
	}

	/**
	 * Returns the number of asset entry asset list entry rels where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @return the number of matching asset entry asset list entry rels
	 */
	@Override
	public int countByA_GtP(long assetListEntryId, int position) {
		FinderPath finderPath = _finderPathWithPaginationCountByA_GtP;

		Object[] finderArgs = new Object[] {assetListEntryId, position};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETENTRYASSETLISTENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_A_GTP_ASSETLISTENTRYID_2);

			query.append(_FINDER_COLUMN_A_GTP_POSITION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetListEntryId);

				qPos.add(position);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_A_GTP_ASSETLISTENTRYID_2 =
		"assetEntryAssetListEntryRel.assetListEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_GTP_POSITION_2 =
		"assetEntryAssetListEntryRel.position > ?";

	public AssetEntryAssetListEntryRelPersistenceImpl() {
		setModelClass(AssetEntryAssetListEntryRel.class);

		setModelImplClass(AssetEntryAssetListEntryRelImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the asset entry asset list entry rel in the entity cache if it is enabled.
	 *
	 * @param assetEntryAssetListEntryRel the asset entry asset list entry rel
	 */
	@Override
	public void cacheResult(
		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel) {

		entityCache.putResult(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelImpl.class,
			assetEntryAssetListEntryRel.getPrimaryKey(),
			assetEntryAssetListEntryRel);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				assetEntryAssetListEntryRel.getUuid(),
				assetEntryAssetListEntryRel.getGroupId()
			},
			assetEntryAssetListEntryRel);

		finderCache.putResult(
			_finderPathFetchByA_P,
			new Object[] {
				assetEntryAssetListEntryRel.getAssetListEntryId(),
				assetEntryAssetListEntryRel.getPosition()
			},
			assetEntryAssetListEntryRel);

		assetEntryAssetListEntryRel.resetOriginalValues();
	}

	/**
	 * Caches the asset entry asset list entry rels in the entity cache if it is enabled.
	 *
	 * @param assetEntryAssetListEntryRels the asset entry asset list entry rels
	 */
	@Override
	public void cacheResult(
		List<AssetEntryAssetListEntryRel> assetEntryAssetListEntryRels) {

		for (AssetEntryAssetListEntryRel assetEntryAssetListEntryRel :
				assetEntryAssetListEntryRels) {

			if (entityCache.getResult(
					AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntryAssetListEntryRelImpl.class,
					assetEntryAssetListEntryRel.getPrimaryKey()) == null) {

				cacheResult(assetEntryAssetListEntryRel);
			}
			else {
				assetEntryAssetListEntryRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset entry asset list entry rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetEntryAssetListEntryRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset entry asset list entry rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel) {

		entityCache.removeResult(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelImpl.class,
			assetEntryAssetListEntryRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(AssetEntryAssetListEntryRelModelImpl)assetEntryAssetListEntryRel,
			true);
	}

	@Override
	public void clearCache(
		List<AssetEntryAssetListEntryRel> assetEntryAssetListEntryRels) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetEntryAssetListEntryRel assetEntryAssetListEntryRel :
				assetEntryAssetListEntryRels) {

			entityCache.removeResult(
				AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntryAssetListEntryRelImpl.class,
				assetEntryAssetListEntryRel.getPrimaryKey());

			clearUniqueFindersCache(
				(AssetEntryAssetListEntryRelModelImpl)
					assetEntryAssetListEntryRel,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetEntryAssetListEntryRelModelImpl
			assetEntryAssetListEntryRelModelImpl) {

		Object[] args = new Object[] {
			assetEntryAssetListEntryRelModelImpl.getUuid(),
			assetEntryAssetListEntryRelModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args,
			assetEntryAssetListEntryRelModelImpl, false);

		args = new Object[] {
			assetEntryAssetListEntryRelModelImpl.getAssetListEntryId(),
			assetEntryAssetListEntryRelModelImpl.getPosition()
		};

		finderCache.putResult(
			_finderPathCountByA_P, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByA_P, args, assetEntryAssetListEntryRelModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		AssetEntryAssetListEntryRelModelImpl
			assetEntryAssetListEntryRelModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetEntryAssetListEntryRelModelImpl.getUuid(),
				assetEntryAssetListEntryRelModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((assetEntryAssetListEntryRelModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetEntryAssetListEntryRelModelImpl.getOriginalUuid(),
				assetEntryAssetListEntryRelModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetEntryAssetListEntryRelModelImpl.getAssetListEntryId(),
				assetEntryAssetListEntryRelModelImpl.getPosition()
			};

			finderCache.removeResult(_finderPathCountByA_P, args);
			finderCache.removeResult(_finderPathFetchByA_P, args);
		}

		if ((assetEntryAssetListEntryRelModelImpl.getColumnBitmask() &
			 _finderPathFetchByA_P.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetEntryAssetListEntryRelModelImpl.
					getOriginalAssetListEntryId(),
				assetEntryAssetListEntryRelModelImpl.getOriginalPosition()
			};

			finderCache.removeResult(_finderPathCountByA_P, args);
			finderCache.removeResult(_finderPathFetchByA_P, args);
		}
	}

	/**
	 * Creates a new asset entry asset list entry rel with the primary key. Does not add the asset entry asset list entry rel to the database.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key for the new asset entry asset list entry rel
	 * @return the new asset entry asset list entry rel
	 */
	@Override
	public AssetEntryAssetListEntryRel create(
		long assetEntryAssetListEntryRelId) {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			new AssetEntryAssetListEntryRelImpl();

		assetEntryAssetListEntryRel.setNew(true);
		assetEntryAssetListEntryRel.setPrimaryKey(
			assetEntryAssetListEntryRelId);

		String uuid = PortalUUIDUtil.generate();

		assetEntryAssetListEntryRel.setUuid(uuid);

		assetEntryAssetListEntryRel.setCompanyId(
			companyProvider.getCompanyId());

		return assetEntryAssetListEntryRel;
	}

	/**
	 * Removes the asset entry asset list entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel that was removed
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a asset entry asset list entry rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel remove(
			long assetEntryAssetListEntryRelId)
		throws NoSuchAssetEntryAssetListEntryRelException {

		return remove((Serializable)assetEntryAssetListEntryRelId);
	}

	/**
	 * Removes the asset entry asset list entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel that was removed
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a asset entry asset list entry rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel remove(Serializable primaryKey)
		throws NoSuchAssetEntryAssetListEntryRelException {

		Session session = null;

		try {
			session = openSession();

			AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
				(AssetEntryAssetListEntryRel)session.get(
					AssetEntryAssetListEntryRelImpl.class, primaryKey);

			if (assetEntryAssetListEntryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAssetEntryAssetListEntryRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetEntryAssetListEntryRel);
		}
		catch (NoSuchAssetEntryAssetListEntryRelException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected AssetEntryAssetListEntryRel removeImpl(
		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetEntryAssetListEntryRel)) {
				assetEntryAssetListEntryRel =
					(AssetEntryAssetListEntryRel)session.get(
						AssetEntryAssetListEntryRelImpl.class,
						assetEntryAssetListEntryRel.getPrimaryKeyObj());
			}

			if (assetEntryAssetListEntryRel != null) {
				session.delete(assetEntryAssetListEntryRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetEntryAssetListEntryRel != null) {
			clearCache(assetEntryAssetListEntryRel);
		}

		return assetEntryAssetListEntryRel;
	}

	@Override
	public AssetEntryAssetListEntryRel updateImpl(
		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel) {

		boolean isNew = assetEntryAssetListEntryRel.isNew();

		if (!(assetEntryAssetListEntryRel instanceof
				AssetEntryAssetListEntryRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					assetEntryAssetListEntryRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					assetEntryAssetListEntryRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetEntryAssetListEntryRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetEntryAssetListEntryRel implementation " +
					assetEntryAssetListEntryRel.getClass());
		}

		AssetEntryAssetListEntryRelModelImpl
			assetEntryAssetListEntryRelModelImpl =
				(AssetEntryAssetListEntryRelModelImpl)
					assetEntryAssetListEntryRel;

		if (Validator.isNull(assetEntryAssetListEntryRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetEntryAssetListEntryRel.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (assetEntryAssetListEntryRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetEntryAssetListEntryRel.setCreateDate(now);
			}
			else {
				assetEntryAssetListEntryRel.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!assetEntryAssetListEntryRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetEntryAssetListEntryRel.setModifiedDate(now);
			}
			else {
				assetEntryAssetListEntryRel.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (assetEntryAssetListEntryRel.isNew()) {
				session.save(assetEntryAssetListEntryRel);

				assetEntryAssetListEntryRel.setNew(false);
			}
			else {
				assetEntryAssetListEntryRel =
					(AssetEntryAssetListEntryRel)session.merge(
						assetEntryAssetListEntryRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AssetEntryAssetListEntryRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				assetEntryAssetListEntryRelModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				assetEntryAssetListEntryRelModelImpl.getUuid(),
				assetEntryAssetListEntryRelModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				assetEntryAssetListEntryRelModelImpl.getAssetListEntryId()
			};

			finderCache.removeResult(_finderPathCountByAssetListEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAssetListEntryId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((assetEntryAssetListEntryRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetEntryAssetListEntryRelModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {
					assetEntryAssetListEntryRelModelImpl.getUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((assetEntryAssetListEntryRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetEntryAssetListEntryRelModelImpl.getOriginalUuid(),
					assetEntryAssetListEntryRelModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					assetEntryAssetListEntryRelModelImpl.getUuid(),
					assetEntryAssetListEntryRelModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((assetEntryAssetListEntryRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAssetListEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetEntryAssetListEntryRelModelImpl.
						getOriginalAssetListEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAssetListEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetListEntryId, args);

				args = new Object[] {
					assetEntryAssetListEntryRelModelImpl.getAssetListEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAssetListEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetListEntryId, args);
			}
		}

		entityCache.putResult(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelImpl.class,
			assetEntryAssetListEntryRel.getPrimaryKey(),
			assetEntryAssetListEntryRel, false);

		clearUniqueFindersCache(assetEntryAssetListEntryRelModelImpl, false);
		cacheUniqueFindersCache(assetEntryAssetListEntryRelModelImpl);

		assetEntryAssetListEntryRel.resetOriginalValues();

		return assetEntryAssetListEntryRel;
	}

	/**
	 * Returns the asset entry asset list entry rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a asset entry asset list entry rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAssetEntryAssetListEntryRelException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			fetchByPrimaryKey(primaryKey);

		if (assetEntryAssetListEntryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAssetEntryAssetListEntryRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetEntryAssetListEntryRel;
	}

	/**
	 * Returns the asset entry asset list entry rel with the primary key or throws a <code>NoSuchAssetEntryAssetListEntryRelException</code> if it could not be found.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a asset entry asset list entry rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel findByPrimaryKey(
			long assetEntryAssetListEntryRelId)
		throws NoSuchAssetEntryAssetListEntryRelException {

		return findByPrimaryKey((Serializable)assetEntryAssetListEntryRelId);
	}

	/**
	 * Returns the asset entry asset list entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel, or <code>null</code> if a asset entry asset list entry rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetListEntryRel fetchByPrimaryKey(
		long assetEntryAssetListEntryRelId) {

		return fetchByPrimaryKey((Serializable)assetEntryAssetListEntryRelId);
	}

	/**
	 * Returns all the asset entry asset list entry rels.
	 *
	 * @return the asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry asset list entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @return the range of asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findAll(
		int start, int end,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of asset entry asset list entry rels
	 */
	@Override
	public List<AssetEntryAssetListEntryRel> findAll(
		int start, int end,
		OrderByComparator<AssetEntryAssetListEntryRel> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<AssetEntryAssetListEntryRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetEntryAssetListEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ASSETENTRYASSETLISTENTRYREL);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETENTRYASSETLISTENTRYREL;

				if (pagination) {
					sql = sql.concat(
						AssetEntryAssetListEntryRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AssetEntryAssetListEntryRel>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetEntryAssetListEntryRel>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset entry asset list entry rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetEntryAssetListEntryRel assetEntryAssetListEntryRel :
				findAll()) {

			remove(assetEntryAssetListEntryRel);
		}
	}

	/**
	 * Returns the number of asset entry asset list entry rels.
	 *
	 * @return the number of asset entry asset list entry rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
					_SQL_COUNT_ASSETENTRYASSETLISTENTRYREL);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
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
		return "assetEntryAssetListEntryRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETENTRYASSETLISTENTRYREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AssetEntryAssetListEntryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset entry asset list entry rel persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetListEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetListEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetListEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetListEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			AssetEntryAssetListEntryRelModelImpl.UUID_COLUMN_BITMASK |
			AssetEntryAssetListEntryRelModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetListEntryRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			AssetEntryAssetListEntryRelModelImpl.UUID_COLUMN_BITMASK |
			AssetEntryAssetListEntryRelModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetListEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetListEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			AssetEntryAssetListEntryRelModelImpl.UUID_COLUMN_BITMASK |
			AssetEntryAssetListEntryRelModelImpl.COMPANYID_COLUMN_BITMASK |
			AssetEntryAssetListEntryRelModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByAssetListEntryId = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetListEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetListEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAssetListEntryId = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetListEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetListEntryId",
			new String[] {Long.class.getName()},
			AssetEntryAssetListEntryRelModelImpl.
				ASSETLISTENTRYID_COLUMN_BITMASK |
			AssetEntryAssetListEntryRelModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByAssetListEntryId = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetListEntryId", new String[] {Long.class.getName()});

		_finderPathFetchByA_P = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetListEntryRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByA_P",
			new String[] {Long.class.getName(), Integer.class.getName()},
			AssetEntryAssetListEntryRelModelImpl.
				ASSETLISTENTRYID_COLUMN_BITMASK |
			AssetEntryAssetListEntryRelModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByA_P = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_P",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByA_GtP = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetListEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_GtP",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByA_GtP = new FinderPath(
			AssetEntryAssetListEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetListEntryRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByA_GtP",
			new String[] {Long.class.getName(), Integer.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(
			AssetEntryAssetListEntryRelImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ASSETENTRYASSETLISTENTRYREL =
		"SELECT assetEntryAssetListEntryRel FROM AssetEntryAssetListEntryRel assetEntryAssetListEntryRel";

	private static final String _SQL_SELECT_ASSETENTRYASSETLISTENTRYREL_WHERE =
		"SELECT assetEntryAssetListEntryRel FROM AssetEntryAssetListEntryRel assetEntryAssetListEntryRel WHERE ";

	private static final String _SQL_COUNT_ASSETENTRYASSETLISTENTRYREL =
		"SELECT COUNT(assetEntryAssetListEntryRel) FROM AssetEntryAssetListEntryRel assetEntryAssetListEntryRel";

	private static final String _SQL_COUNT_ASSETENTRYASSETLISTENTRYREL_WHERE =
		"SELECT COUNT(assetEntryAssetListEntryRel) FROM AssetEntryAssetListEntryRel assetEntryAssetListEntryRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"assetEntryAssetListEntryRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetEntryAssetListEntryRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetEntryAssetListEntryRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntryAssetListEntryRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}