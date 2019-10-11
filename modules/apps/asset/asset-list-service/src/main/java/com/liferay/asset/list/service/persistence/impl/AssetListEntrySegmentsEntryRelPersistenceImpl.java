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

import com.liferay.asset.list.exception.NoSuchEntrySegmentsEntryRelException;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.model.impl.AssetListEntrySegmentsEntryRelImpl;
import com.liferay.asset.list.model.impl.AssetListEntrySegmentsEntryRelModelImpl;
import com.liferay.asset.list.service.persistence.AssetListEntrySegmentsEntryRelPersistence;
import com.liferay.asset.list.service.persistence.impl.constants.AssetListPersistenceConstants;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
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
 * The persistence implementation for the asset list entry segments entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AssetListEntrySegmentsEntryRelPersistence.class)
public class AssetListEntrySegmentsEntryRelPersistenceImpl
	extends BasePersistenceImpl<AssetListEntrySegmentsEntryRel>
	implements AssetListEntrySegmentsEntryRelPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetListEntrySegmentsEntryRelUtil</code> to access the asset list entry segments entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetListEntrySegmentsEntryRelImpl.class.getName();

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
	 * Returns all the asset list entry segments entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry segments entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @return the range of matching asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator,
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

		List<AssetListEntrySegmentsEntryRel> list = null;

		if (useFinderCache) {
			list = (List<AssetListEntrySegmentsEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntrySegmentsEntryRel
						assetListEntrySegmentsEntryRel : list) {

					if (!uuid.equals(
							assetListEntrySegmentsEntryRel.getUuid())) {

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

			query.append(_SQL_SELECT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE);

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
			else {
				query.append(
					AssetListEntrySegmentsEntryRelModelImpl.ORDER_BY_JPQL);
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

				list = (List<AssetListEntrySegmentsEntryRel>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel findByUuid_First(
			String uuid,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			fetchByUuid_First(uuid, orderByComparator);

		if (assetListEntrySegmentsEntryRel != null) {
			return assetListEntrySegmentsEntryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchEntrySegmentsEntryRelException(msg.toString());
	}

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel fetchByUuid_First(
		String uuid,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		List<AssetListEntrySegmentsEntryRel> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel findByUuid_Last(
			String uuid,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			fetchByUuid_Last(uuid, orderByComparator);

		if (assetListEntrySegmentsEntryRel != null) {
			return assetListEntrySegmentsEntryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchEntrySegmentsEntryRelException(msg.toString());
	}

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AssetListEntrySegmentsEntryRel> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry segments entry rels before and after the current asset list entry segments entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the current asset list entry segments entry rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel[] findByUuid_PrevAndNext(
			long assetListEntrySegmentsEntryRelId, String uuid,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException {

		uuid = Objects.toString(uuid, "");

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			findByPrimaryKey(assetListEntrySegmentsEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntrySegmentsEntryRel[] array =
				new AssetListEntrySegmentsEntryRelImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, assetListEntrySegmentsEntryRel, uuid,
				orderByComparator, true);

			array[1] = assetListEntrySegmentsEntryRel;

			array[2] = getByUuid_PrevAndNext(
				session, assetListEntrySegmentsEntryRel, uuid,
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

	protected AssetListEntrySegmentsEntryRel getByUuid_PrevAndNext(
		Session session,
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel,
		String uuid,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE);

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
			query.append(AssetListEntrySegmentsEntryRelModelImpl.ORDER_BY_JPQL);
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
						assetListEntrySegmentsEntryRel)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntrySegmentsEntryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry segments entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetListEntrySegmentsEntryRel);
		}
	}

	/**
	 * Returns the number of asset list entry segments entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset list entry segments entry rels
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE);

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
		"assetListEntrySegmentsEntryRel.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(assetListEntrySegmentsEntryRel.uuid IS NULL OR assetListEntrySegmentsEntryRel.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the asset list entry segments entry rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntrySegmentsEntryRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel findByUUID_G(
			String uuid, long groupId)
		throws NoSuchEntrySegmentsEntryRelException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			fetchByUUID_G(uuid, groupId);

		if (assetListEntrySegmentsEntryRel == null) {
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

			throw new NoSuchEntrySegmentsEntryRelException(msg.toString());
		}

		return assetListEntrySegmentsEntryRel;
	}

	/**
	 * Returns the asset list entry segments entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel fetchByUUID_G(
		String uuid, long groupId) {

		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the asset list entry segments entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel fetchByUUID_G(
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

		if (result instanceof AssetListEntrySegmentsEntryRel) {
			AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
				(AssetListEntrySegmentsEntryRel)result;

			if (!Objects.equals(
					uuid, assetListEntrySegmentsEntryRel.getUuid()) ||
				(groupId != assetListEntrySegmentsEntryRel.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE);

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

				List<AssetListEntrySegmentsEntryRel> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					AssetListEntrySegmentsEntryRel
						assetListEntrySegmentsEntryRel = list.get(0);

					result = assetListEntrySegmentsEntryRel;

					cacheResult(assetListEntrySegmentsEntryRel);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByUUID_G, finderArgs);
				}

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
			return (AssetListEntrySegmentsEntryRel)result;
		}
	}

	/**
	 * Removes the asset list entry segments entry rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset list entry segments entry rel that was removed
	 */
	@Override
	public AssetListEntrySegmentsEntryRel removeByUUID_G(
			String uuid, long groupId)
		throws NoSuchEntrySegmentsEntryRelException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			findByUUID_G(uuid, groupId);

		return remove(assetListEntrySegmentsEntryRel);
	}

	/**
	 * Returns the number of asset list entry segments entry rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset list entry segments entry rels
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE);

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
		"assetListEntrySegmentsEntryRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(assetListEntrySegmentsEntryRel.uuid IS NULL OR assetListEntrySegmentsEntryRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"assetListEntrySegmentsEntryRel.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the asset list entry segments entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry segments entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @return the range of matching asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator,
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

		List<AssetListEntrySegmentsEntryRel> list = null;

		if (useFinderCache) {
			list = (List<AssetListEntrySegmentsEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntrySegmentsEntryRel
						assetListEntrySegmentsEntryRel : list) {

					if (!uuid.equals(
							assetListEntrySegmentsEntryRel.getUuid()) ||
						(companyId !=
							assetListEntrySegmentsEntryRel.getCompanyId())) {

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

			query.append(_SQL_SELECT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE);

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
			else {
				query.append(
					AssetListEntrySegmentsEntryRelModelImpl.ORDER_BY_JPQL);
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

				list = (List<AssetListEntrySegmentsEntryRel>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (assetListEntrySegmentsEntryRel != null) {
			return assetListEntrySegmentsEntryRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchEntrySegmentsEntryRelException(msg.toString());
	}

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		List<AssetListEntrySegmentsEntryRel> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (assetListEntrySegmentsEntryRel != null) {
			return assetListEntrySegmentsEntryRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchEntrySegmentsEntryRelException(msg.toString());
	}

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AssetListEntrySegmentsEntryRel> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry segments entry rels before and after the current asset list entry segments entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the current asset list entry segments entry rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel[] findByUuid_C_PrevAndNext(
			long assetListEntrySegmentsEntryRelId, String uuid, long companyId,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException {

		uuid = Objects.toString(uuid, "");

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			findByPrimaryKey(assetListEntrySegmentsEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntrySegmentsEntryRel[] array =
				new AssetListEntrySegmentsEntryRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, assetListEntrySegmentsEntryRel, uuid, companyId,
				orderByComparator, true);

			array[1] = assetListEntrySegmentsEntryRel;

			array[2] = getByUuid_C_PrevAndNext(
				session, assetListEntrySegmentsEntryRel, uuid, companyId,
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

	protected AssetListEntrySegmentsEntryRel getByUuid_C_PrevAndNext(
		Session session,
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel,
		String uuid, long companyId,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE);

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
			query.append(AssetListEntrySegmentsEntryRelModelImpl.ORDER_BY_JPQL);
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
						assetListEntrySegmentsEntryRel)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntrySegmentsEntryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry segments entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetListEntrySegmentsEntryRel);
		}
	}

	/**
	 * Returns the number of asset list entry segments entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset list entry segments entry rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE);

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
		"assetListEntrySegmentsEntryRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(assetListEntrySegmentsEntryRel.uuid IS NULL OR assetListEntrySegmentsEntryRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"assetListEntrySegmentsEntryRel.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByAssetListEntryId;
	private FinderPath _finderPathWithoutPaginationFindByAssetListEntryId;
	private FinderPath _finderPathCountByAssetListEntryId;

	/**
	 * Returns all the asset list entry segments entry rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the matching asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findByAssetListEntryId(
		long assetListEntryId) {

		return findByAssetListEntryId(
			assetListEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry segments entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @return the range of matching asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end) {

		return findByAssetListEntryId(assetListEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return findByAssetListEntryId(
			assetListEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByAssetListEntryId;
				finderArgs = new Object[] {assetListEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAssetListEntryId;
			finderArgs = new Object[] {
				assetListEntryId, start, end, orderByComparator
			};
		}

		List<AssetListEntrySegmentsEntryRel> list = null;

		if (useFinderCache) {
			list = (List<AssetListEntrySegmentsEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntrySegmentsEntryRel
						assetListEntrySegmentsEntryRel : list) {

					if (assetListEntryId !=
							assetListEntrySegmentsEntryRel.
								getAssetListEntryId()) {

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

			query.append(_SQL_SELECT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_ASSETLISTENTRYID_ASSETLISTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(
					AssetListEntrySegmentsEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetListEntryId);

				list = (List<AssetListEntrySegmentsEntryRel>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel findByAssetListEntryId_First(
			long assetListEntryId,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			fetchByAssetListEntryId_First(assetListEntryId, orderByComparator);

		if (assetListEntrySegmentsEntryRel != null) {
			return assetListEntrySegmentsEntryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetListEntryId=");
		msg.append(assetListEntryId);

		msg.append("}");

		throw new NoSuchEntrySegmentsEntryRelException(msg.toString());
	}

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel fetchByAssetListEntryId_First(
		long assetListEntryId,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		List<AssetListEntrySegmentsEntryRel> list = findByAssetListEntryId(
			assetListEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel findByAssetListEntryId_Last(
			long assetListEntryId,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			fetchByAssetListEntryId_Last(assetListEntryId, orderByComparator);

		if (assetListEntrySegmentsEntryRel != null) {
			return assetListEntrySegmentsEntryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetListEntryId=");
		msg.append(assetListEntryId);

		msg.append("}");

		throw new NoSuchEntrySegmentsEntryRelException(msg.toString());
	}

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel fetchByAssetListEntryId_Last(
		long assetListEntryId,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		int count = countByAssetListEntryId(assetListEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetListEntrySegmentsEntryRel> list = findByAssetListEntryId(
			assetListEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry segments entry rels before and after the current asset list entry segments entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the current asset list entry segments entry rel
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel[] findByAssetListEntryId_PrevAndNext(
			long assetListEntrySegmentsEntryRelId, long assetListEntryId,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			findByPrimaryKey(assetListEntrySegmentsEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntrySegmentsEntryRel[] array =
				new AssetListEntrySegmentsEntryRelImpl[3];

			array[0] = getByAssetListEntryId_PrevAndNext(
				session, assetListEntrySegmentsEntryRel, assetListEntryId,
				orderByComparator, true);

			array[1] = assetListEntrySegmentsEntryRel;

			array[2] = getByAssetListEntryId_PrevAndNext(
				session, assetListEntrySegmentsEntryRel, assetListEntryId,
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

	protected AssetListEntrySegmentsEntryRel getByAssetListEntryId_PrevAndNext(
		Session session,
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel,
		long assetListEntryId,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE);

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
			query.append(AssetListEntrySegmentsEntryRelModelImpl.ORDER_BY_JPQL);
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
						assetListEntrySegmentsEntryRel)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntrySegmentsEntryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry segments entry rels where assetListEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 */
	@Override
	public void removeByAssetListEntryId(long assetListEntryId) {
		for (AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel :
				findByAssetListEntryId(
					assetListEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetListEntrySegmentsEntryRel);
		}
	}

	/**
	 * Returns the number of asset list entry segments entry rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the number of matching asset list entry segments entry rels
	 */
	@Override
	public int countByAssetListEntryId(long assetListEntryId) {
		FinderPath finderPath = _finderPathCountByAssetListEntryId;

		Object[] finderArgs = new Object[] {assetListEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE);

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
			"assetListEntrySegmentsEntryRel.assetListEntryId = ?";

	private FinderPath _finderPathWithPaginationFindBySegmentsEntryId;
	private FinderPath _finderPathWithoutPaginationFindBySegmentsEntryId;
	private FinderPath _finderPathCountBySegmentsEntryId;

	/**
	 * Returns all the asset list entry segments entry rels where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findBySegmentsEntryId(
		long segmentsEntryId) {

		return findBySegmentsEntryId(
			segmentsEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry segments entry rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @return the range of matching asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end) {

		return findBySegmentsEntryId(segmentsEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return findBySegmentsEntryId(
			segmentsEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindBySegmentsEntryId;
				finderArgs = new Object[] {segmentsEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindBySegmentsEntryId;
			finderArgs = new Object[] {
				segmentsEntryId, start, end, orderByComparator
			};
		}

		List<AssetListEntrySegmentsEntryRel> list = null;

		if (useFinderCache) {
			list = (List<AssetListEntrySegmentsEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntrySegmentsEntryRel
						assetListEntrySegmentsEntryRel : list) {

					if (segmentsEntryId !=
							assetListEntrySegmentsEntryRel.
								getSegmentsEntryId()) {

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

			query.append(_SQL_SELECT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(
					AssetListEntrySegmentsEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(segmentsEntryId);

				list = (List<AssetListEntrySegmentsEntryRel>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel findBySegmentsEntryId_First(
			long segmentsEntryId,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			fetchBySegmentsEntryId_First(segmentsEntryId, orderByComparator);

		if (assetListEntrySegmentsEntryRel != null) {
			return assetListEntrySegmentsEntryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("segmentsEntryId=");
		msg.append(segmentsEntryId);

		msg.append("}");

		throw new NoSuchEntrySegmentsEntryRelException(msg.toString());
	}

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel fetchBySegmentsEntryId_First(
		long segmentsEntryId,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		List<AssetListEntrySegmentsEntryRel> list = findBySegmentsEntryId(
			segmentsEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel findBySegmentsEntryId_Last(
			long segmentsEntryId,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			fetchBySegmentsEntryId_Last(segmentsEntryId, orderByComparator);

		if (assetListEntrySegmentsEntryRel != null) {
			return assetListEntrySegmentsEntryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("segmentsEntryId=");
		msg.append(segmentsEntryId);

		msg.append("}");

		throw new NoSuchEntrySegmentsEntryRelException(msg.toString());
	}

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel fetchBySegmentsEntryId_Last(
		long segmentsEntryId,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		int count = countBySegmentsEntryId(segmentsEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetListEntrySegmentsEntryRel> list = findBySegmentsEntryId(
			segmentsEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry segments entry rels before and after the current asset list entry segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the current asset list entry segments entry rel
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel[] findBySegmentsEntryId_PrevAndNext(
			long assetListEntrySegmentsEntryRelId, long segmentsEntryId,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			findByPrimaryKey(assetListEntrySegmentsEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntrySegmentsEntryRel[] array =
				new AssetListEntrySegmentsEntryRelImpl[3];

			array[0] = getBySegmentsEntryId_PrevAndNext(
				session, assetListEntrySegmentsEntryRel, segmentsEntryId,
				orderByComparator, true);

			array[1] = assetListEntrySegmentsEntryRel;

			array[2] = getBySegmentsEntryId_PrevAndNext(
				session, assetListEntrySegmentsEntryRel, segmentsEntryId,
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

	protected AssetListEntrySegmentsEntryRel getBySegmentsEntryId_PrevAndNext(
		Session session,
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel,
		long segmentsEntryId,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE);

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
			query.append(AssetListEntrySegmentsEntryRelModelImpl.ORDER_BY_JPQL);
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
						assetListEntrySegmentsEntryRel)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntrySegmentsEntryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry segments entry rels where segmentsEntryId = &#63; from the database.
	 *
	 * @param segmentsEntryId the segments entry ID
	 */
	@Override
	public void removeBySegmentsEntryId(long segmentsEntryId) {
		for (AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel :
				findBySegmentsEntryId(
					segmentsEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetListEntrySegmentsEntryRel);
		}
	}

	/**
	 * Returns the number of asset list entry segments entry rels where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching asset list entry segments entry rels
	 */
	@Override
	public int countBySegmentsEntryId(long segmentsEntryId) {
		FinderPath finderPath = _finderPathCountBySegmentsEntryId;

		Object[] finderArgs = new Object[] {segmentsEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE);

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
			"assetListEntrySegmentsEntryRel.segmentsEntryId = ?";

	private FinderPath _finderPathFetchByA_S;
	private FinderPath _finderPathCountByA_S;

	/**
	 * Returns the asset list entry segments entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or throws a <code>NoSuchEntrySegmentsEntryRelException</code> if it could not be found.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel findByA_S(
			long assetListEntryId, long segmentsEntryId)
		throws NoSuchEntrySegmentsEntryRelException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			fetchByA_S(assetListEntryId, segmentsEntryId);

		if (assetListEntrySegmentsEntryRel == null) {
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

			throw new NoSuchEntrySegmentsEntryRelException(msg.toString());
		}

		return assetListEntrySegmentsEntryRel;
	}

	/**
	 * Returns the asset list entry segments entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel fetchByA_S(
		long assetListEntryId, long segmentsEntryId) {

		return fetchByA_S(assetListEntryId, segmentsEntryId, true);
	}

	/**
	 * Returns the asset list entry segments entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel fetchByA_S(
		long assetListEntryId, long segmentsEntryId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {assetListEntryId, segmentsEntryId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByA_S, finderArgs, this);
		}

		if (result instanceof AssetListEntrySegmentsEntryRel) {
			AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
				(AssetListEntrySegmentsEntryRel)result;

			if ((assetListEntryId !=
					assetListEntrySegmentsEntryRel.getAssetListEntryId()) ||
				(segmentsEntryId !=
					assetListEntrySegmentsEntryRel.getSegmentsEntryId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE);

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

				List<AssetListEntrySegmentsEntryRel> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByA_S, finderArgs, list);
					}
				}
				else {
					AssetListEntrySegmentsEntryRel
						assetListEntrySegmentsEntryRel = list.get(0);

					result = assetListEntrySegmentsEntryRel;

					cacheResult(assetListEntrySegmentsEntryRel);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByA_S, finderArgs);
				}

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
			return (AssetListEntrySegmentsEntryRel)result;
		}
	}

	/**
	 * Removes the asset list entry segments entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the asset list entry segments entry rel that was removed
	 */
	@Override
	public AssetListEntrySegmentsEntryRel removeByA_S(
			long assetListEntryId, long segmentsEntryId)
		throws NoSuchEntrySegmentsEntryRelException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			findByA_S(assetListEntryId, segmentsEntryId);

		return remove(assetListEntrySegmentsEntryRel);
	}

	/**
	 * Returns the number of asset list entry segments entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching asset list entry segments entry rels
	 */
	@Override
	public int countByA_S(long assetListEntryId, long segmentsEntryId) {
		FinderPath finderPath = _finderPathCountByA_S;

		Object[] finderArgs = new Object[] {assetListEntryId, segmentsEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE);

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
		"assetListEntrySegmentsEntryRel.assetListEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_S_SEGMENTSENTRYID_2 =
		"assetListEntrySegmentsEntryRel.segmentsEntryId = ?";

	public AssetListEntrySegmentsEntryRelPersistenceImpl() {
		setModelClass(AssetListEntrySegmentsEntryRel.class);

		setModelImplClass(AssetListEntrySegmentsEntryRelImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"assetListEntrySegmentsEntryRelId", "alEntrySegmentsEntryRelId");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the asset list entry segments entry rel in the entity cache if it is enabled.
	 *
	 * @param assetListEntrySegmentsEntryRel the asset list entry segments entry rel
	 */
	@Override
	public void cacheResult(
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel) {

		entityCache.putResult(
			entityCacheEnabled, AssetListEntrySegmentsEntryRelImpl.class,
			assetListEntrySegmentsEntryRel.getPrimaryKey(),
			assetListEntrySegmentsEntryRel);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				assetListEntrySegmentsEntryRel.getUuid(),
				assetListEntrySegmentsEntryRel.getGroupId()
			},
			assetListEntrySegmentsEntryRel);

		finderCache.putResult(
			_finderPathFetchByA_S,
			new Object[] {
				assetListEntrySegmentsEntryRel.getAssetListEntryId(),
				assetListEntrySegmentsEntryRel.getSegmentsEntryId()
			},
			assetListEntrySegmentsEntryRel);

		assetListEntrySegmentsEntryRel.resetOriginalValues();
	}

	/**
	 * Caches the asset list entry segments entry rels in the entity cache if it is enabled.
	 *
	 * @param assetListEntrySegmentsEntryRels the asset list entry segments entry rels
	 */
	@Override
	public void cacheResult(
		List<AssetListEntrySegmentsEntryRel> assetListEntrySegmentsEntryRels) {

		for (AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel :
				assetListEntrySegmentsEntryRels) {

			if (entityCache.getResult(
					entityCacheEnabled,
					AssetListEntrySegmentsEntryRelImpl.class,
					assetListEntrySegmentsEntryRel.getPrimaryKey()) == null) {

				cacheResult(assetListEntrySegmentsEntryRel);
			}
			else {
				assetListEntrySegmentsEntryRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset list entry segments entry rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetListEntrySegmentsEntryRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset list entry segments entry rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel) {

		entityCache.removeResult(
			entityCacheEnabled, AssetListEntrySegmentsEntryRelImpl.class,
			assetListEntrySegmentsEntryRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(AssetListEntrySegmentsEntryRelModelImpl)
				assetListEntrySegmentsEntryRel,
			true);
	}

	@Override
	public void clearCache(
		List<AssetListEntrySegmentsEntryRel> assetListEntrySegmentsEntryRels) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel :
				assetListEntrySegmentsEntryRels) {

			entityCache.removeResult(
				entityCacheEnabled, AssetListEntrySegmentsEntryRelImpl.class,
				assetListEntrySegmentsEntryRel.getPrimaryKey());

			clearUniqueFindersCache(
				(AssetListEntrySegmentsEntryRelModelImpl)
					assetListEntrySegmentsEntryRel,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetListEntrySegmentsEntryRelModelImpl
			assetListEntrySegmentsEntryRelModelImpl) {

		Object[] args = new Object[] {
			assetListEntrySegmentsEntryRelModelImpl.getUuid(),
			assetListEntrySegmentsEntryRelModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args,
			assetListEntrySegmentsEntryRelModelImpl, false);

		args = new Object[] {
			assetListEntrySegmentsEntryRelModelImpl.getAssetListEntryId(),
			assetListEntrySegmentsEntryRelModelImpl.getSegmentsEntryId()
		};

		finderCache.putResult(
			_finderPathCountByA_S, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByA_S, args,
			assetListEntrySegmentsEntryRelModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AssetListEntrySegmentsEntryRelModelImpl
			assetListEntrySegmentsEntryRelModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetListEntrySegmentsEntryRelModelImpl.getUuid(),
				assetListEntrySegmentsEntryRelModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((assetListEntrySegmentsEntryRelModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetListEntrySegmentsEntryRelModelImpl.getOriginalUuid(),
				assetListEntrySegmentsEntryRelModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetListEntrySegmentsEntryRelModelImpl.getAssetListEntryId(),
				assetListEntrySegmentsEntryRelModelImpl.getSegmentsEntryId()
			};

			finderCache.removeResult(_finderPathCountByA_S, args);
			finderCache.removeResult(_finderPathFetchByA_S, args);
		}

		if ((assetListEntrySegmentsEntryRelModelImpl.getColumnBitmask() &
			 _finderPathFetchByA_S.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetListEntrySegmentsEntryRelModelImpl.
					getOriginalAssetListEntryId(),
				assetListEntrySegmentsEntryRelModelImpl.
					getOriginalSegmentsEntryId()
			};

			finderCache.removeResult(_finderPathCountByA_S, args);
			finderCache.removeResult(_finderPathFetchByA_S, args);
		}
	}

	/**
	 * Creates a new asset list entry segments entry rel with the primary key. Does not add the asset list entry segments entry rel to the database.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key for the new asset list entry segments entry rel
	 * @return the new asset list entry segments entry rel
	 */
	@Override
	public AssetListEntrySegmentsEntryRel create(
		long assetListEntrySegmentsEntryRelId) {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			new AssetListEntrySegmentsEntryRelImpl();

		assetListEntrySegmentsEntryRel.setNew(true);
		assetListEntrySegmentsEntryRel.setPrimaryKey(
			assetListEntrySegmentsEntryRelId);

		String uuid = PortalUUIDUtil.generate();

		assetListEntrySegmentsEntryRel.setUuid(uuid);

		assetListEntrySegmentsEntryRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return assetListEntrySegmentsEntryRel;
	}

	/**
	 * Removes the asset list entry segments entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the asset list entry segments entry rel
	 * @return the asset list entry segments entry rel that was removed
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel remove(
			long assetListEntrySegmentsEntryRelId)
		throws NoSuchEntrySegmentsEntryRelException {

		return remove((Serializable)assetListEntrySegmentsEntryRelId);
	}

	/**
	 * Removes the asset list entry segments entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset list entry segments entry rel
	 * @return the asset list entry segments entry rel that was removed
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel remove(Serializable primaryKey)
		throws NoSuchEntrySegmentsEntryRelException {

		Session session = null;

		try {
			session = openSession();

			AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
				(AssetListEntrySegmentsEntryRel)session.get(
					AssetListEntrySegmentsEntryRelImpl.class, primaryKey);

			if (assetListEntrySegmentsEntryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntrySegmentsEntryRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetListEntrySegmentsEntryRel);
		}
		catch (NoSuchEntrySegmentsEntryRelException nsee) {
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
	protected AssetListEntrySegmentsEntryRel removeImpl(
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetListEntrySegmentsEntryRel)) {
				assetListEntrySegmentsEntryRel =
					(AssetListEntrySegmentsEntryRel)session.get(
						AssetListEntrySegmentsEntryRelImpl.class,
						assetListEntrySegmentsEntryRel.getPrimaryKeyObj());
			}

			if (assetListEntrySegmentsEntryRel != null) {
				session.delete(assetListEntrySegmentsEntryRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetListEntrySegmentsEntryRel != null) {
			clearCache(assetListEntrySegmentsEntryRel);
		}

		return assetListEntrySegmentsEntryRel;
	}

	@Override
	public AssetListEntrySegmentsEntryRel updateImpl(
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel) {

		boolean isNew = assetListEntrySegmentsEntryRel.isNew();

		if (!(assetListEntrySegmentsEntryRel instanceof
				AssetListEntrySegmentsEntryRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					assetListEntrySegmentsEntryRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					assetListEntrySegmentsEntryRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetListEntrySegmentsEntryRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetListEntrySegmentsEntryRel implementation " +
					assetListEntrySegmentsEntryRel.getClass());
		}

		AssetListEntrySegmentsEntryRelModelImpl
			assetListEntrySegmentsEntryRelModelImpl =
				(AssetListEntrySegmentsEntryRelModelImpl)
					assetListEntrySegmentsEntryRel;

		if (Validator.isNull(assetListEntrySegmentsEntryRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetListEntrySegmentsEntryRel.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (assetListEntrySegmentsEntryRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetListEntrySegmentsEntryRel.setCreateDate(now);
			}
			else {
				assetListEntrySegmentsEntryRel.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!assetListEntrySegmentsEntryRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetListEntrySegmentsEntryRel.setModifiedDate(now);
			}
			else {
				assetListEntrySegmentsEntryRel.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (assetListEntrySegmentsEntryRel.isNew()) {
				session.save(assetListEntrySegmentsEntryRel);

				assetListEntrySegmentsEntryRel.setNew(false);
			}
			else {
				assetListEntrySegmentsEntryRel =
					(AssetListEntrySegmentsEntryRel)session.merge(
						assetListEntrySegmentsEntryRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				assetListEntrySegmentsEntryRelModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				assetListEntrySegmentsEntryRelModelImpl.getUuid(),
				assetListEntrySegmentsEntryRelModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				assetListEntrySegmentsEntryRelModelImpl.getAssetListEntryId()
			};

			finderCache.removeResult(_finderPathCountByAssetListEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAssetListEntryId, args);

			args = new Object[] {
				assetListEntrySegmentsEntryRelModelImpl.getSegmentsEntryId()
			};

			finderCache.removeResult(_finderPathCountBySegmentsEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindBySegmentsEntryId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((assetListEntrySegmentsEntryRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetListEntrySegmentsEntryRelModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {
					assetListEntrySegmentsEntryRelModelImpl.getUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((assetListEntrySegmentsEntryRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetListEntrySegmentsEntryRelModelImpl.getOriginalUuid(),
					assetListEntrySegmentsEntryRelModelImpl.
						getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					assetListEntrySegmentsEntryRelModelImpl.getUuid(),
					assetListEntrySegmentsEntryRelModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((assetListEntrySegmentsEntryRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAssetListEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetListEntrySegmentsEntryRelModelImpl.
						getOriginalAssetListEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAssetListEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetListEntryId, args);

				args = new Object[] {
					assetListEntrySegmentsEntryRelModelImpl.
						getAssetListEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAssetListEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetListEntryId, args);
			}

			if ((assetListEntrySegmentsEntryRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindBySegmentsEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetListEntrySegmentsEntryRelModelImpl.
						getOriginalSegmentsEntryId()
				};

				finderCache.removeResult(
					_finderPathCountBySegmentsEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySegmentsEntryId, args);

				args = new Object[] {
					assetListEntrySegmentsEntryRelModelImpl.getSegmentsEntryId()
				};

				finderCache.removeResult(
					_finderPathCountBySegmentsEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySegmentsEntryId, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, AssetListEntrySegmentsEntryRelImpl.class,
			assetListEntrySegmentsEntryRel.getPrimaryKey(),
			assetListEntrySegmentsEntryRel, false);

		clearUniqueFindersCache(assetListEntrySegmentsEntryRelModelImpl, false);
		cacheUniqueFindersCache(assetListEntrySegmentsEntryRelModelImpl);

		assetListEntrySegmentsEntryRel.resetOriginalValues();

		return assetListEntrySegmentsEntryRel;
	}

	/**
	 * Returns the asset list entry segments entry rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset list entry segments entry rel
	 * @return the asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchEntrySegmentsEntryRelException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			fetchByPrimaryKey(primaryKey);

		if (assetListEntrySegmentsEntryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntrySegmentsEntryRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetListEntrySegmentsEntryRel;
	}

	/**
	 * Returns the asset list entry segments entry rel with the primary key or throws a <code>NoSuchEntrySegmentsEntryRelException</code> if it could not be found.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the asset list entry segments entry rel
	 * @return the asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel findByPrimaryKey(
			long assetListEntrySegmentsEntryRelId)
		throws NoSuchEntrySegmentsEntryRelException {

		return findByPrimaryKey((Serializable)assetListEntrySegmentsEntryRelId);
	}

	/**
	 * Returns the asset list entry segments entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the asset list entry segments entry rel
	 * @return the asset list entry segments entry rel, or <code>null</code> if a asset list entry segments entry rel with the primary key could not be found
	 */
	@Override
	public AssetListEntrySegmentsEntryRel fetchByPrimaryKey(
		long assetListEntrySegmentsEntryRelId) {

		return fetchByPrimaryKey(
			(Serializable)assetListEntrySegmentsEntryRelId);
	}

	/**
	 * Returns all the asset list entry segments entry rels.
	 *
	 * @return the asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry segments entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @return the range of asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findAll(
		int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset list entry segments entry rels
	 */
	@Override
	public List<AssetListEntrySegmentsEntryRel> findAll(
		int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator,
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

		List<AssetListEntrySegmentsEntryRel> list = null;

		if (useFinderCache) {
			list = (List<AssetListEntrySegmentsEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ASSETLISTENTRYSEGMENTSENTRYREL);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETLISTENTRYSEGMENTSENTRYREL;

				sql = sql.concat(
					AssetListEntrySegmentsEntryRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<AssetListEntrySegmentsEntryRel>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset list entry segments entry rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel :
				findAll()) {

			remove(assetListEntrySegmentsEntryRel);
		}
	}

	/**
	 * Returns the number of asset list entry segments entry rels.
	 *
	 * @return the number of asset list entry segments entry rels
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
					_SQL_COUNT_ASSETLISTENTRYSEGMENTSENTRYREL);

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
		return "alEntrySegmentsEntryRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETLISTENTRYSEGMENTSENTRYREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AssetListEntrySegmentsEntryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset list entry segments entry rel persistence.
	 */
	@Activate
	public void activate() {
		AssetListEntrySegmentsEntryRelModelImpl.setEntityCacheEnabled(
			entityCacheEnabled);
		AssetListEntrySegmentsEntryRelModelImpl.setFinderCacheEnabled(
			finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetListEntrySegmentsEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetListEntrySegmentsEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetListEntrySegmentsEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetListEntrySegmentsEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			AssetListEntrySegmentsEntryRelModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetListEntrySegmentsEntryRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			AssetListEntrySegmentsEntryRelModelImpl.UUID_COLUMN_BITMASK |
			AssetListEntrySegmentsEntryRelModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetListEntrySegmentsEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetListEntrySegmentsEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			AssetListEntrySegmentsEntryRelModelImpl.UUID_COLUMN_BITMASK |
			AssetListEntrySegmentsEntryRelModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByAssetListEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetListEntrySegmentsEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetListEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAssetListEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetListEntrySegmentsEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetListEntryId",
			new String[] {Long.class.getName()},
			AssetListEntrySegmentsEntryRelModelImpl.
				ASSETLISTENTRYID_COLUMN_BITMASK);

		_finderPathCountByAssetListEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetListEntryId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindBySegmentsEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetListEntrySegmentsEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySegmentsEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindBySegmentsEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetListEntrySegmentsEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySegmentsEntryId",
			new String[] {Long.class.getName()},
			AssetListEntrySegmentsEntryRelModelImpl.
				SEGMENTSENTRYID_COLUMN_BITMASK);

		_finderPathCountBySegmentsEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySegmentsEntryId",
			new String[] {Long.class.getName()});

		_finderPathFetchByA_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetListEntrySegmentsEntryRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByA_S",
			new String[] {Long.class.getName(), Long.class.getName()},
			AssetListEntrySegmentsEntryRelModelImpl.
				ASSETLISTENTRYID_COLUMN_BITMASK |
			AssetListEntrySegmentsEntryRelModelImpl.
				SEGMENTSENTRYID_COLUMN_BITMASK);

		_finderPathCountByA_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_S",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(
			AssetListEntrySegmentsEntryRelImpl.class.getName());
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
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel"),
			true);
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

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ASSETLISTENTRYSEGMENTSENTRYREL =
		"SELECT assetListEntrySegmentsEntryRel FROM AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel";

	private static final String
		_SQL_SELECT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE =
			"SELECT assetListEntrySegmentsEntryRel FROM AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel WHERE ";

	private static final String _SQL_COUNT_ASSETLISTENTRYSEGMENTSENTRYREL =
		"SELECT COUNT(assetListEntrySegmentsEntryRel) FROM AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel";

	private static final String
		_SQL_COUNT_ASSETLISTENTRYSEGMENTSENTRYREL_WHERE =
			"SELECT COUNT(assetListEntrySegmentsEntryRel) FROM AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"assetListEntrySegmentsEntryRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetListEntrySegmentsEntryRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetListEntrySegmentsEntryRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetListEntrySegmentsEntryRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "assetListEntrySegmentsEntryRelId"});

	static {
		try {
			Class.forName(AssetListPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}