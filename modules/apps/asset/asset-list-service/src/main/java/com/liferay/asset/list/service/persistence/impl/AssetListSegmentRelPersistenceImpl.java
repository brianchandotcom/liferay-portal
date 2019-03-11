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

import com.liferay.asset.list.exception.NoSuchSegmentRelException;
import com.liferay.asset.list.model.AssetListSegmentRel;
import com.liferay.asset.list.model.impl.AssetListSegmentRelImpl;
import com.liferay.asset.list.model.impl.AssetListSegmentRelModelImpl;
import com.liferay.asset.list.service.persistence.AssetListSegmentRelPersistence;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the asset list segment rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class AssetListSegmentRelPersistenceImpl
	extends BasePersistenceImpl<AssetListSegmentRel>
	implements AssetListSegmentRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetListSegmentRelUtil</code> to access the asset list segment rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetListSegmentRelImpl.class.getName();

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
	 * Returns all the asset list segment rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list segment rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @return the range of matching asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator,
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

		List<AssetListSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetListSegmentRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListSegmentRel assetListSegmentRel : list) {
					if (!uuid.equals(assetListSegmentRel.getUuid())) {
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

			query.append(_SQL_SELECT_ASSETLISTSEGMENTREL_WHERE);

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
				query.append(AssetListSegmentRelModelImpl.ORDER_BY_JPQL);
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
					list = (List<AssetListSegmentRel>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetListSegmentRel>)QueryUtil.list(
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
	 * Returns the first asset list segment rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel findByUuid_First(
			String uuid,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {

		AssetListSegmentRel assetListSegmentRel = fetchByUuid_First(
			uuid, orderByComparator);

		if (assetListSegmentRel != null) {
			return assetListSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchSegmentRelException(msg.toString());
	}

	/**
	 * Returns the first asset list segment rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel fetchByUuid_First(
		String uuid, OrderByComparator<AssetListSegmentRel> orderByComparator) {

		List<AssetListSegmentRel> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list segment rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel findByUuid_Last(
			String uuid,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {

		AssetListSegmentRel assetListSegmentRel = fetchByUuid_Last(
			uuid, orderByComparator);

		if (assetListSegmentRel != null) {
			return assetListSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchSegmentRelException(msg.toString());
	}

	/**
	 * Returns the last asset list segment rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel fetchByUuid_Last(
		String uuid, OrderByComparator<AssetListSegmentRel> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AssetListSegmentRel> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list segment rels before and after the current asset list segment rel in the ordered set where uuid = &#63;.
	 *
	 * @param assetListSegmentRelId the primary key of the current asset list segment rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list segment rel
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	@Override
	public AssetListSegmentRel[] findByUuid_PrevAndNext(
			long assetListSegmentRelId, String uuid,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {

		uuid = Objects.toString(uuid, "");

		AssetListSegmentRel assetListSegmentRel = findByPrimaryKey(
			assetListSegmentRelId);

		Session session = null;

		try {
			session = openSession();

			AssetListSegmentRel[] array = new AssetListSegmentRelImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, assetListSegmentRel, uuid, orderByComparator, true);

			array[1] = assetListSegmentRel;

			array[2] = getByUuid_PrevAndNext(
				session, assetListSegmentRel, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListSegmentRel getByUuid_PrevAndNext(
		Session session, AssetListSegmentRel assetListSegmentRel, String uuid,
		OrderByComparator<AssetListSegmentRel> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETLISTSEGMENTREL_WHERE);

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
			query.append(AssetListSegmentRelModelImpl.ORDER_BY_JPQL);
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
						assetListSegmentRel)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetListSegmentRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list segment rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AssetListSegmentRel assetListSegmentRel :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetListSegmentRel);
		}
	}

	/**
	 * Returns the number of asset list segment rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset list segment rels
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETLISTSEGMENTREL_WHERE);

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
		"assetListSegmentRel.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(assetListSegmentRel.uuid IS NULL OR assetListSegmentRel.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the asset list segment rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSegmentRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel findByUUID_G(String uuid, long groupId)
		throws NoSuchSegmentRelException {

		AssetListSegmentRel assetListSegmentRel = fetchByUUID_G(uuid, groupId);

		if (assetListSegmentRel == null) {
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

			throw new NoSuchSegmentRelException(msg.toString());
		}

		return assetListSegmentRel;
	}

	/**
	 * Returns the asset list segment rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the asset list segment rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = new Object[] {uuid, groupId};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof AssetListSegmentRel) {
			AssetListSegmentRel assetListSegmentRel =
				(AssetListSegmentRel)result;

			if (!Objects.equals(uuid, assetListSegmentRel.getUuid()) ||
				(groupId != assetListSegmentRel.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETLISTSEGMENTREL_WHERE);

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

				List<AssetListSegmentRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByUUID_G, finderArgs, list);
				}
				else {
					AssetListSegmentRel assetListSegmentRel = list.get(0);

					result = assetListSegmentRel;

					cacheResult(assetListSegmentRel);
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
			return (AssetListSegmentRel)result;
		}
	}

	/**
	 * Removes the asset list segment rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset list segment rel that was removed
	 */
	@Override
	public AssetListSegmentRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchSegmentRelException {

		AssetListSegmentRel assetListSegmentRel = findByUUID_G(uuid, groupId);

		return remove(assetListSegmentRel);
	}

	/**
	 * Returns the number of asset list segment rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset list segment rels
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLISTSEGMENTREL_WHERE);

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
		"assetListSegmentRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(assetListSegmentRel.uuid IS NULL OR assetListSegmentRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"assetListSegmentRel.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the asset list segment rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list segment rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @return the range of matching asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator,
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

		List<AssetListSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetListSegmentRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListSegmentRel assetListSegmentRel : list) {
					if (!uuid.equals(assetListSegmentRel.getUuid()) ||
						(companyId != assetListSegmentRel.getCompanyId())) {

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

			query.append(_SQL_SELECT_ASSETLISTSEGMENTREL_WHERE);

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
				query.append(AssetListSegmentRelModelImpl.ORDER_BY_JPQL);
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
					list = (List<AssetListSegmentRel>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetListSegmentRel>)QueryUtil.list(
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
	 * Returns the first asset list segment rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {

		AssetListSegmentRel assetListSegmentRel = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (assetListSegmentRel != null) {
			return assetListSegmentRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchSegmentRelException(msg.toString());
	}

	/**
	 * Returns the first asset list segment rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		List<AssetListSegmentRel> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list segment rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {

		AssetListSegmentRel assetListSegmentRel = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (assetListSegmentRel != null) {
			return assetListSegmentRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchSegmentRelException(msg.toString());
	}

	/**
	 * Returns the last asset list segment rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AssetListSegmentRel> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list segment rels before and after the current asset list segment rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetListSegmentRelId the primary key of the current asset list segment rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list segment rel
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	@Override
	public AssetListSegmentRel[] findByUuid_C_PrevAndNext(
			long assetListSegmentRelId, String uuid, long companyId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {

		uuid = Objects.toString(uuid, "");

		AssetListSegmentRel assetListSegmentRel = findByPrimaryKey(
			assetListSegmentRelId);

		Session session = null;

		try {
			session = openSession();

			AssetListSegmentRel[] array = new AssetListSegmentRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, assetListSegmentRel, uuid, companyId,
				orderByComparator, true);

			array[1] = assetListSegmentRel;

			array[2] = getByUuid_C_PrevAndNext(
				session, assetListSegmentRel, uuid, companyId,
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

	protected AssetListSegmentRel getByUuid_C_PrevAndNext(
		Session session, AssetListSegmentRel assetListSegmentRel, String uuid,
		long companyId,
		OrderByComparator<AssetListSegmentRel> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETLISTSEGMENTREL_WHERE);

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
			query.append(AssetListSegmentRelModelImpl.ORDER_BY_JPQL);
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
						assetListSegmentRel)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetListSegmentRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list segment rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AssetListSegmentRel assetListSegmentRel :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetListSegmentRel);
		}
	}

	/**
	 * Returns the number of asset list segment rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset list segment rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLISTSEGMENTREL_WHERE);

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
		"assetListSegmentRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(assetListSegmentRel.uuid IS NULL OR assetListSegmentRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"assetListSegmentRel.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByAssetListEntryId;
	private FinderPath _finderPathWithoutPaginationFindByAssetListEntryId;
	private FinderPath _finderPathCountByAssetListEntryId;

	/**
	 * Returns all the asset list segment rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the matching asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findByAssetListEntryId(
		long assetListEntryId) {

		return findByAssetListEntryId(
			assetListEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list segment rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @return the range of matching asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end) {

		return findByAssetListEntryId(assetListEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return findByAssetListEntryId(
			assetListEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator,
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

		List<AssetListSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetListSegmentRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListSegmentRel assetListSegmentRel : list) {
					if ((assetListEntryId !=
							assetListSegmentRel.getAssetListEntryId())) {

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

			query.append(_SQL_SELECT_ASSETLISTSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_ASSETLISTENTRYID_ASSETLISTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(AssetListSegmentRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetListEntryId);

				if (!pagination) {
					list = (List<AssetListSegmentRel>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetListSegmentRel>)QueryUtil.list(
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
	 * Returns the first asset list segment rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel findByAssetListEntryId_First(
			long assetListEntryId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {

		AssetListSegmentRel assetListSegmentRel = fetchByAssetListEntryId_First(
			assetListEntryId, orderByComparator);

		if (assetListSegmentRel != null) {
			return assetListSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetListEntryId=");
		msg.append(assetListEntryId);

		msg.append("}");

		throw new NoSuchSegmentRelException(msg.toString());
	}

	/**
	 * Returns the first asset list segment rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel fetchByAssetListEntryId_First(
		long assetListEntryId,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		List<AssetListSegmentRel> list = findByAssetListEntryId(
			assetListEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list segment rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel findByAssetListEntryId_Last(
			long assetListEntryId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {

		AssetListSegmentRel assetListSegmentRel = fetchByAssetListEntryId_Last(
			assetListEntryId, orderByComparator);

		if (assetListSegmentRel != null) {
			return assetListSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetListEntryId=");
		msg.append(assetListEntryId);

		msg.append("}");

		throw new NoSuchSegmentRelException(msg.toString());
	}

	/**
	 * Returns the last asset list segment rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel fetchByAssetListEntryId_Last(
		long assetListEntryId,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		int count = countByAssetListEntryId(assetListEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetListSegmentRel> list = findByAssetListEntryId(
			assetListEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list segment rels before and after the current asset list segment rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListSegmentRelId the primary key of the current asset list segment rel
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list segment rel
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	@Override
	public AssetListSegmentRel[] findByAssetListEntryId_PrevAndNext(
			long assetListSegmentRelId, long assetListEntryId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {

		AssetListSegmentRel assetListSegmentRel = findByPrimaryKey(
			assetListSegmentRelId);

		Session session = null;

		try {
			session = openSession();

			AssetListSegmentRel[] array = new AssetListSegmentRelImpl[3];

			array[0] = getByAssetListEntryId_PrevAndNext(
				session, assetListSegmentRel, assetListEntryId,
				orderByComparator, true);

			array[1] = assetListSegmentRel;

			array[2] = getByAssetListEntryId_PrevAndNext(
				session, assetListSegmentRel, assetListEntryId,
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

	protected AssetListSegmentRel getByAssetListEntryId_PrevAndNext(
		Session session, AssetListSegmentRel assetListSegmentRel,
		long assetListEntryId,
		OrderByComparator<AssetListSegmentRel> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETLISTSEGMENTREL_WHERE);

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
			query.append(AssetListSegmentRelModelImpl.ORDER_BY_JPQL);
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
						assetListSegmentRel)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetListSegmentRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list segment rels where assetListEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 */
	@Override
	public void removeByAssetListEntryId(long assetListEntryId) {
		for (AssetListSegmentRel assetListSegmentRel :
				findByAssetListEntryId(
					assetListEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetListSegmentRel);
		}
	}

	/**
	 * Returns the number of asset list segment rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the number of matching asset list segment rels
	 */
	@Override
	public int countByAssetListEntryId(long assetListEntryId) {
		FinderPath finderPath = _finderPathCountByAssetListEntryId;

		Object[] finderArgs = new Object[] {assetListEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETLISTSEGMENTREL_WHERE);

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
			"assetListSegmentRel.assetListEntryId = ?";

	private FinderPath _finderPathWithPaginationFindBySegmentsEntryId;
	private FinderPath _finderPathWithoutPaginationFindBySegmentsEntryId;
	private FinderPath _finderPathCountBySegmentsEntryId;

	/**
	 * Returns all the asset list segment rels where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findBySegmentsEntryId(
		long segmentsEntryId) {

		return findBySegmentsEntryId(
			segmentsEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list segment rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @return the range of matching asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end) {

		return findBySegmentsEntryId(segmentsEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return findBySegmentsEntryId(
			segmentsEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindBySegmentsEntryId;
			finderArgs = new Object[] {segmentsEntryId};
		}
		else {
			finderPath = _finderPathWithPaginationFindBySegmentsEntryId;
			finderArgs = new Object[] {
				segmentsEntryId, start, end, orderByComparator
			};
		}

		List<AssetListSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetListSegmentRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListSegmentRel assetListSegmentRel : list) {
					if ((segmentsEntryId !=
							assetListSegmentRel.getSegmentsEntryId())) {

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

			query.append(_SQL_SELECT_ASSETLISTSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(AssetListSegmentRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(segmentsEntryId);

				if (!pagination) {
					list = (List<AssetListSegmentRel>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetListSegmentRel>)QueryUtil.list(
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
	 * Returns the first asset list segment rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel findBySegmentsEntryId_First(
			long segmentsEntryId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {

		AssetListSegmentRel assetListSegmentRel = fetchBySegmentsEntryId_First(
			segmentsEntryId, orderByComparator);

		if (assetListSegmentRel != null) {
			return assetListSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("segmentsEntryId=");
		msg.append(segmentsEntryId);

		msg.append("}");

		throw new NoSuchSegmentRelException(msg.toString());
	}

	/**
	 * Returns the first asset list segment rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel fetchBySegmentsEntryId_First(
		long segmentsEntryId,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		List<AssetListSegmentRel> list = findBySegmentsEntryId(
			segmentsEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list segment rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel findBySegmentsEntryId_Last(
			long segmentsEntryId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {

		AssetListSegmentRel assetListSegmentRel = fetchBySegmentsEntryId_Last(
			segmentsEntryId, orderByComparator);

		if (assetListSegmentRel != null) {
			return assetListSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("segmentsEntryId=");
		msg.append(segmentsEntryId);

		msg.append("}");

		throw new NoSuchSegmentRelException(msg.toString());
	}

	/**
	 * Returns the last asset list segment rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel fetchBySegmentsEntryId_Last(
		long segmentsEntryId,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		int count = countBySegmentsEntryId(segmentsEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetListSegmentRel> list = findBySegmentsEntryId(
			segmentsEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list segment rels before and after the current asset list segment rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param assetListSegmentRelId the primary key of the current asset list segment rel
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list segment rel
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	@Override
	public AssetListSegmentRel[] findBySegmentsEntryId_PrevAndNext(
			long assetListSegmentRelId, long segmentsEntryId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {

		AssetListSegmentRel assetListSegmentRel = findByPrimaryKey(
			assetListSegmentRelId);

		Session session = null;

		try {
			session = openSession();

			AssetListSegmentRel[] array = new AssetListSegmentRelImpl[3];

			array[0] = getBySegmentsEntryId_PrevAndNext(
				session, assetListSegmentRel, segmentsEntryId,
				orderByComparator, true);

			array[1] = assetListSegmentRel;

			array[2] = getBySegmentsEntryId_PrevAndNext(
				session, assetListSegmentRel, segmentsEntryId,
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

	protected AssetListSegmentRel getBySegmentsEntryId_PrevAndNext(
		Session session, AssetListSegmentRel assetListSegmentRel,
		long segmentsEntryId,
		OrderByComparator<AssetListSegmentRel> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETLISTSEGMENTREL_WHERE);

		query.append(_FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2);

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
			query.append(AssetListSegmentRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(segmentsEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListSegmentRel)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetListSegmentRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list segment rels where segmentsEntryId = &#63; from the database.
	 *
	 * @param segmentsEntryId the segments entry ID
	 */
	@Override
	public void removeBySegmentsEntryId(long segmentsEntryId) {
		for (AssetListSegmentRel assetListSegmentRel :
				findBySegmentsEntryId(
					segmentsEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetListSegmentRel);
		}
	}

	/**
	 * Returns the number of asset list segment rels where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching asset list segment rels
	 */
	@Override
	public int countBySegmentsEntryId(long segmentsEntryId) {
		FinderPath finderPath = _finderPathCountBySegmentsEntryId;

		Object[] finderArgs = new Object[] {segmentsEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETLISTSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(segmentsEntryId);

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
		_FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2 =
			"assetListSegmentRel.segmentsEntryId = ?";

	private FinderPath _finderPathFetchByA_S;
	private FinderPath _finderPathCountByA_S;

	/**
	 * Returns the asset list segment rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or throws a <code>NoSuchSegmentRelException</code> if it could not be found.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel findByA_S(
			long assetListEntryId, long segmentsEntryId)
		throws NoSuchSegmentRelException {

		AssetListSegmentRel assetListSegmentRel = fetchByA_S(
			assetListEntryId, segmentsEntryId);

		if (assetListSegmentRel == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetListEntryId=");
			msg.append(assetListEntryId);

			msg.append(", segmentsEntryId=");
			msg.append(segmentsEntryId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchSegmentRelException(msg.toString());
		}

		return assetListSegmentRel;
	}

	/**
	 * Returns the asset list segment rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel fetchByA_S(
		long assetListEntryId, long segmentsEntryId) {

		return fetchByA_S(assetListEntryId, segmentsEntryId, true);
	}

	/**
	 * Returns the asset list segment rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	@Override
	public AssetListSegmentRel fetchByA_S(
		long assetListEntryId, long segmentsEntryId,
		boolean retrieveFromCache) {

		Object[] finderArgs = new Object[] {assetListEntryId, segmentsEntryId};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(
				_finderPathFetchByA_S, finderArgs, this);
		}

		if (result instanceof AssetListSegmentRel) {
			AssetListSegmentRel assetListSegmentRel =
				(AssetListSegmentRel)result;

			if ((assetListEntryId !=
					assetListSegmentRel.getAssetListEntryId()) ||
				(segmentsEntryId != assetListSegmentRel.getSegmentsEntryId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETLISTSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_A_S_ASSETLISTENTRYID_2);

			query.append(_FINDER_COLUMN_A_S_SEGMENTSENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetListEntryId);

				qPos.add(segmentsEntryId);

				List<AssetListSegmentRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByA_S, finderArgs, list);
				}
				else {
					AssetListSegmentRel assetListSegmentRel = list.get(0);

					result = assetListSegmentRel;

					cacheResult(assetListSegmentRel);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByA_S, finderArgs);

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
			return (AssetListSegmentRel)result;
		}
	}

	/**
	 * Removes the asset list segment rel where assetListEntryId = &#63; and segmentsEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the asset list segment rel that was removed
	 */
	@Override
	public AssetListSegmentRel removeByA_S(
			long assetListEntryId, long segmentsEntryId)
		throws NoSuchSegmentRelException {

		AssetListSegmentRel assetListSegmentRel = findByA_S(
			assetListEntryId, segmentsEntryId);

		return remove(assetListSegmentRel);
	}

	/**
	 * Returns the number of asset list segment rels where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching asset list segment rels
	 */
	@Override
	public int countByA_S(long assetListEntryId, long segmentsEntryId) {
		FinderPath finderPath = _finderPathCountByA_S;

		Object[] finderArgs = new Object[] {assetListEntryId, segmentsEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLISTSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_A_S_ASSETLISTENTRYID_2);

			query.append(_FINDER_COLUMN_A_S_SEGMENTSENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetListEntryId);

				qPos.add(segmentsEntryId);

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

	private static final String _FINDER_COLUMN_A_S_ASSETLISTENTRYID_2 =
		"assetListSegmentRel.assetListEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_S_SEGMENTSENTRYID_2 =
		"assetListSegmentRel.segmentsEntryId = ?";

	public AssetListSegmentRelPersistenceImpl() {
		setModelClass(AssetListSegmentRel.class);

		setModelImplClass(AssetListSegmentRelImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the asset list segment rel in the entity cache if it is enabled.
	 *
	 * @param assetListSegmentRel the asset list segment rel
	 */
	@Override
	public void cacheResult(AssetListSegmentRel assetListSegmentRel) {
		entityCache.putResult(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelImpl.class, assetListSegmentRel.getPrimaryKey(),
			assetListSegmentRel);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				assetListSegmentRel.getUuid(), assetListSegmentRel.getGroupId()
			},
			assetListSegmentRel);

		finderCache.putResult(
			_finderPathFetchByA_S,
			new Object[] {
				assetListSegmentRel.getAssetListEntryId(),
				assetListSegmentRel.getSegmentsEntryId()
			},
			assetListSegmentRel);

		assetListSegmentRel.resetOriginalValues();
	}

	/**
	 * Caches the asset list segment rels in the entity cache if it is enabled.
	 *
	 * @param assetListSegmentRels the asset list segment rels
	 */
	@Override
	public void cacheResult(List<AssetListSegmentRel> assetListSegmentRels) {
		for (AssetListSegmentRel assetListSegmentRel : assetListSegmentRels) {
			if (entityCache.getResult(
					AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
					AssetListSegmentRelImpl.class,
					assetListSegmentRel.getPrimaryKey()) == null) {

				cacheResult(assetListSegmentRel);
			}
			else {
				assetListSegmentRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset list segment rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetListSegmentRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset list segment rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetListSegmentRel assetListSegmentRel) {
		entityCache.removeResult(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelImpl.class, assetListSegmentRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(AssetListSegmentRelModelImpl)assetListSegmentRel, true);
	}

	@Override
	public void clearCache(List<AssetListSegmentRel> assetListSegmentRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetListSegmentRel assetListSegmentRel : assetListSegmentRels) {
			entityCache.removeResult(
				AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
				AssetListSegmentRelImpl.class,
				assetListSegmentRel.getPrimaryKey());

			clearUniqueFindersCache(
				(AssetListSegmentRelModelImpl)assetListSegmentRel, true);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetListSegmentRelModelImpl assetListSegmentRelModelImpl) {

		Object[] args = new Object[] {
			assetListSegmentRelModelImpl.getUuid(),
			assetListSegmentRelModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, assetListSegmentRelModelImpl,
			false);

		args = new Object[] {
			assetListSegmentRelModelImpl.getAssetListEntryId(),
			assetListSegmentRelModelImpl.getSegmentsEntryId()
		};

		finderCache.putResult(
			_finderPathCountByA_S, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByA_S, args, assetListSegmentRelModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AssetListSegmentRelModelImpl assetListSegmentRelModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetListSegmentRelModelImpl.getUuid(),
				assetListSegmentRelModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((assetListSegmentRelModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetListSegmentRelModelImpl.getOriginalUuid(),
				assetListSegmentRelModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetListSegmentRelModelImpl.getAssetListEntryId(),
				assetListSegmentRelModelImpl.getSegmentsEntryId()
			};

			finderCache.removeResult(_finderPathCountByA_S, args);
			finderCache.removeResult(_finderPathFetchByA_S, args);
		}

		if ((assetListSegmentRelModelImpl.getColumnBitmask() &
			 _finderPathFetchByA_S.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetListSegmentRelModelImpl.getOriginalAssetListEntryId(),
				assetListSegmentRelModelImpl.getOriginalSegmentsEntryId()
			};

			finderCache.removeResult(_finderPathCountByA_S, args);
			finderCache.removeResult(_finderPathFetchByA_S, args);
		}
	}

	/**
	 * Creates a new asset list segment rel with the primary key. Does not add the asset list segment rel to the database.
	 *
	 * @param assetListSegmentRelId the primary key for the new asset list segment rel
	 * @return the new asset list segment rel
	 */
	@Override
	public AssetListSegmentRel create(long assetListSegmentRelId) {
		AssetListSegmentRel assetListSegmentRel = new AssetListSegmentRelImpl();

		assetListSegmentRel.setNew(true);
		assetListSegmentRel.setPrimaryKey(assetListSegmentRelId);

		String uuid = PortalUUIDUtil.generate();

		assetListSegmentRel.setUuid(uuid);

		assetListSegmentRel.setCompanyId(companyProvider.getCompanyId());

		return assetListSegmentRel;
	}

	/**
	 * Removes the asset list segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListSegmentRelId the primary key of the asset list segment rel
	 * @return the asset list segment rel that was removed
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	@Override
	public AssetListSegmentRel remove(long assetListSegmentRelId)
		throws NoSuchSegmentRelException {

		return remove((Serializable)assetListSegmentRelId);
	}

	/**
	 * Removes the asset list segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset list segment rel
	 * @return the asset list segment rel that was removed
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	@Override
	public AssetListSegmentRel remove(Serializable primaryKey)
		throws NoSuchSegmentRelException {

		Session session = null;

		try {
			session = openSession();

			AssetListSegmentRel assetListSegmentRel =
				(AssetListSegmentRel)session.get(
					AssetListSegmentRelImpl.class, primaryKey);

			if (assetListSegmentRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSegmentRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetListSegmentRel);
		}
		catch (NoSuchSegmentRelException nsee) {
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
	protected AssetListSegmentRel removeImpl(
		AssetListSegmentRel assetListSegmentRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetListSegmentRel)) {
				assetListSegmentRel = (AssetListSegmentRel)session.get(
					AssetListSegmentRelImpl.class,
					assetListSegmentRel.getPrimaryKeyObj());
			}

			if (assetListSegmentRel != null) {
				session.delete(assetListSegmentRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetListSegmentRel != null) {
			clearCache(assetListSegmentRel);
		}

		return assetListSegmentRel;
	}

	@Override
	public AssetListSegmentRel updateImpl(
		AssetListSegmentRel assetListSegmentRel) {

		boolean isNew = assetListSegmentRel.isNew();

		if (!(assetListSegmentRel instanceof AssetListSegmentRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetListSegmentRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					assetListSegmentRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetListSegmentRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetListSegmentRel implementation " +
					assetListSegmentRel.getClass());
		}

		AssetListSegmentRelModelImpl assetListSegmentRelModelImpl =
			(AssetListSegmentRelModelImpl)assetListSegmentRel;

		if (Validator.isNull(assetListSegmentRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetListSegmentRel.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (assetListSegmentRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetListSegmentRel.setCreateDate(now);
			}
			else {
				assetListSegmentRel.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!assetListSegmentRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetListSegmentRel.setModifiedDate(now);
			}
			else {
				assetListSegmentRel.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (assetListSegmentRel.isNew()) {
				session.save(assetListSegmentRel);

				assetListSegmentRel.setNew(false);
			}
			else {
				assetListSegmentRel = (AssetListSegmentRel)session.merge(
					assetListSegmentRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AssetListSegmentRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				assetListSegmentRelModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				assetListSegmentRelModelImpl.getUuid(),
				assetListSegmentRelModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				assetListSegmentRelModelImpl.getAssetListEntryId()
			};

			finderCache.removeResult(_finderPathCountByAssetListEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAssetListEntryId, args);

			args = new Object[] {
				assetListSegmentRelModelImpl.getSegmentsEntryId()
			};

			finderCache.removeResult(_finderPathCountBySegmentsEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindBySegmentsEntryId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((assetListSegmentRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetListSegmentRelModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {assetListSegmentRelModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((assetListSegmentRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetListSegmentRelModelImpl.getOriginalUuid(),
					assetListSegmentRelModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					assetListSegmentRelModelImpl.getUuid(),
					assetListSegmentRelModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((assetListSegmentRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAssetListEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetListSegmentRelModelImpl.getOriginalAssetListEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAssetListEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetListEntryId, args);

				args = new Object[] {
					assetListSegmentRelModelImpl.getAssetListEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAssetListEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetListEntryId, args);
			}

			if ((assetListSegmentRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindBySegmentsEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetListSegmentRelModelImpl.getOriginalSegmentsEntryId()
				};

				finderCache.removeResult(
					_finderPathCountBySegmentsEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySegmentsEntryId, args);

				args = new Object[] {
					assetListSegmentRelModelImpl.getSegmentsEntryId()
				};

				finderCache.removeResult(
					_finderPathCountBySegmentsEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySegmentsEntryId, args);
			}
		}

		entityCache.putResult(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelImpl.class, assetListSegmentRel.getPrimaryKey(),
			assetListSegmentRel, false);

		clearUniqueFindersCache(assetListSegmentRelModelImpl, false);
		cacheUniqueFindersCache(assetListSegmentRelModelImpl);

		assetListSegmentRel.resetOriginalValues();

		return assetListSegmentRel;
	}

	/**
	 * Returns the asset list segment rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset list segment rel
	 * @return the asset list segment rel
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	@Override
	public AssetListSegmentRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSegmentRelException {

		AssetListSegmentRel assetListSegmentRel = fetchByPrimaryKey(primaryKey);

		if (assetListSegmentRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSegmentRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetListSegmentRel;
	}

	/**
	 * Returns the asset list segment rel with the primary key or throws a <code>NoSuchSegmentRelException</code> if it could not be found.
	 *
	 * @param assetListSegmentRelId the primary key of the asset list segment rel
	 * @return the asset list segment rel
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	@Override
	public AssetListSegmentRel findByPrimaryKey(long assetListSegmentRelId)
		throws NoSuchSegmentRelException {

		return findByPrimaryKey((Serializable)assetListSegmentRelId);
	}

	/**
	 * Returns the asset list segment rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetListSegmentRelId the primary key of the asset list segment rel
	 * @return the asset list segment rel, or <code>null</code> if a asset list segment rel with the primary key could not be found
	 */
	@Override
	public AssetListSegmentRel fetchByPrimaryKey(long assetListSegmentRelId) {
		return fetchByPrimaryKey((Serializable)assetListSegmentRelId);
	}

	/**
	 * Returns all the asset list segment rels.
	 *
	 * @return the asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @return the range of asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findAll(
		int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of asset list segment rels
	 */
	@Override
	public List<AssetListSegmentRel> findAll(
		int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator,
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

		List<AssetListSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetListSegmentRel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ASSETLISTSEGMENTREL);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETLISTSEGMENTREL;

				if (pagination) {
					sql = sql.concat(
						AssetListSegmentRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AssetListSegmentRel>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetListSegmentRel>)QueryUtil.list(
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
	 * Removes all the asset list segment rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetListSegmentRel assetListSegmentRel : findAll()) {
			remove(assetListSegmentRel);
		}
	}

	/**
	 * Returns the number of asset list segment rels.
	 *
	 * @return the number of asset list segment rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETLISTSEGMENTREL);

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
		return "assetListSegmentRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETLISTSEGMENTREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AssetListSegmentRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset list segment rel persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			AssetListSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			AssetListSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			AssetListSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			AssetListSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			AssetListSegmentRelModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			AssetListSegmentRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			AssetListSegmentRelModelImpl.UUID_COLUMN_BITMASK |
			AssetListSegmentRelModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			AssetListSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			AssetListSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			AssetListSegmentRelModelImpl.UUID_COLUMN_BITMASK |
			AssetListSegmentRelModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByAssetListEntryId = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			AssetListSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetListEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAssetListEntryId = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			AssetListSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetListEntryId",
			new String[] {Long.class.getName()},
			AssetListSegmentRelModelImpl.ASSETLISTENTRYID_COLUMN_BITMASK);

		_finderPathCountByAssetListEntryId = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetListEntryId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindBySegmentsEntryId = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			AssetListSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySegmentsEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindBySegmentsEntryId = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			AssetListSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySegmentsEntryId",
			new String[] {Long.class.getName()},
			AssetListSegmentRelModelImpl.SEGMENTSENTRYID_COLUMN_BITMASK);

		_finderPathCountBySegmentsEntryId = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySegmentsEntryId",
			new String[] {Long.class.getName()});

		_finderPathFetchByA_S = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			AssetListSegmentRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByA_S",
			new String[] {Long.class.getName(), Long.class.getName()},
			AssetListSegmentRelModelImpl.ASSETLISTENTRYID_COLUMN_BITMASK |
			AssetListSegmentRelModelImpl.SEGMENTSENTRYID_COLUMN_BITMASK);

		_finderPathCountByA_S = new FinderPath(
			AssetListSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetListSegmentRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_S",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(AssetListSegmentRelImpl.class.getName());
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

	private static final String _SQL_SELECT_ASSETLISTSEGMENTREL =
		"SELECT assetListSegmentRel FROM AssetListSegmentRel assetListSegmentRel";

	private static final String _SQL_SELECT_ASSETLISTSEGMENTREL_WHERE =
		"SELECT assetListSegmentRel FROM AssetListSegmentRel assetListSegmentRel WHERE ";

	private static final String _SQL_COUNT_ASSETLISTSEGMENTREL =
		"SELECT COUNT(assetListSegmentRel) FROM AssetListSegmentRel assetListSegmentRel";

	private static final String _SQL_COUNT_ASSETLISTSEGMENTREL_WHERE =
		"SELECT COUNT(assetListSegmentRel) FROM AssetListSegmentRel assetListSegmentRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "assetListSegmentRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetListSegmentRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetListSegmentRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetListSegmentRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}