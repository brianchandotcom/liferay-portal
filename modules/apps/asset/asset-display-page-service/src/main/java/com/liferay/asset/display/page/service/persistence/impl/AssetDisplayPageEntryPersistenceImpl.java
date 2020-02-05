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

package com.liferay.asset.display.page.service.persistence.impl;

import com.liferay.asset.display.page.exception.NoSuchDisplayPageEntryException;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryImpl;
import com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryModelImpl;
import com.liferay.asset.display.page.service.persistence.AssetDisplayPageEntryPersistence;
import com.liferay.asset.display.page.service.persistence.impl.constants.AssetPersistenceConstants;
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
import com.liferay.portal.kernel.util.GetterUtil;
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
 * The persistence implementation for the asset display page entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AssetDisplayPageEntryPersistence.class)
public class AssetDisplayPageEntryPersistenceImpl
	extends BasePersistenceImpl<AssetDisplayPageEntry>
	implements AssetDisplayPageEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetDisplayPageEntryUtil</code> to access the asset display page entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetDisplayPageEntryImpl.class.getName();

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
	 * Returns all the asset display page entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display page entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @return the range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetDisplayPageEntry.class);

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

		List<AssetDisplayPageEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetDisplayPageEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetDisplayPageEntry assetDisplayPageEntry : list) {
					if (!uuid.equals(assetDisplayPageEntry.getUuid())) {
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

			query.append(_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE);

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
				query.append(AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<AssetDisplayPageEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset display page entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByUuid_First(
			String uuid,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (assetDisplayPageEntry != null) {
			return assetDisplayPageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchDisplayPageEntryException(msg.toString());
	}

	/**
	 * Returns the first asset display page entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		List<AssetDisplayPageEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset display page entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByUuid_Last(
			String uuid,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (assetDisplayPageEntry != null) {
			return assetDisplayPageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchDisplayPageEntryException(msg.toString());
	}

	/**
	 * Returns the last asset display page entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AssetDisplayPageEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset display page entries before and after the current asset display page entry in the ordered set where uuid = &#63;.
	 *
	 * @param assetDisplayPageEntryId the primary key of the current asset display page entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry[] findByUuid_PrevAndNext(
			long assetDisplayPageEntryId, String uuid,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		uuid = Objects.toString(uuid, "");

		AssetDisplayPageEntry assetDisplayPageEntry = findByPrimaryKey(
			assetDisplayPageEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetDisplayPageEntry[] array = new AssetDisplayPageEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, assetDisplayPageEntry, uuid, orderByComparator, true);

			array[1] = assetDisplayPageEntry;

			array[2] = getByUuid_PrevAndNext(
				session, assetDisplayPageEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetDisplayPageEntry getByUuid_PrevAndNext(
		Session session, AssetDisplayPageEntry assetDisplayPageEntry,
		String uuid, OrderByComparator<AssetDisplayPageEntry> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE);

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
			query.append(AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL);
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
						assetDisplayPageEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetDisplayPageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset display page entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AssetDisplayPageEntry assetDisplayPageEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetDisplayPageEntry);
		}
	}

	/**
	 * Returns the number of asset display page entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset display page entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetDisplayPageEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETDISPLAYPAGEENTRY_WHERE);

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

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"assetDisplayPageEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(assetDisplayPageEntry.uuid IS NULL OR assetDisplayPageEntry.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the asset display page entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchDisplayPageEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = fetchByUUID_G(
			uuid, groupId);

		if (assetDisplayPageEntry == null) {
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

			throw new NoSuchDisplayPageEntryException(msg.toString());
		}

		return assetDisplayPageEntry;
	}

	/**
	 * Returns the asset display page entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the asset display page entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetDisplayPageEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof AssetDisplayPageEntry) {
			AssetDisplayPageEntry assetDisplayPageEntry =
				(AssetDisplayPageEntry)result;

			if (!Objects.equals(uuid, assetDisplayPageEntry.getUuid()) ||
				(groupId != assetDisplayPageEntry.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE);

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

				List<AssetDisplayPageEntry> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					AssetDisplayPageEntry assetDisplayPageEntry = list.get(0);

					result = assetDisplayPageEntry;

					cacheResult(assetDisplayPageEntry);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					finderCache.removeResult(
						_finderPathFetchByUUID_G, finderArgs);
				}

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
			return (AssetDisplayPageEntry)result;
		}
	}

	/**
	 * Removes the asset display page entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset display page entry that was removed
	 */
	@Override
	public AssetDisplayPageEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = findByUUID_G(
			uuid, groupId);

		return remove(assetDisplayPageEntry);
	}

	/**
	 * Returns the number of asset display page entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset display page entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetDisplayPageEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G;

			finderArgs = new Object[] {uuid, groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETDISPLAYPAGEENTRY_WHERE);

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

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"assetDisplayPageEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(assetDisplayPageEntry.uuid IS NULL OR assetDisplayPageEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"assetDisplayPageEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the asset display page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @return the range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetDisplayPageEntry.class);

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

		List<AssetDisplayPageEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetDisplayPageEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetDisplayPageEntry assetDisplayPageEntry : list) {
					if (!uuid.equals(assetDisplayPageEntry.getUuid()) ||
						(companyId != assetDisplayPageEntry.getCompanyId())) {

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

			query.append(_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE);

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
				query.append(AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<AssetDisplayPageEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset display page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (assetDisplayPageEntry != null) {
			return assetDisplayPageEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchDisplayPageEntryException(msg.toString());
	}

	/**
	 * Returns the first asset display page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		List<AssetDisplayPageEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset display page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (assetDisplayPageEntry != null) {
			return assetDisplayPageEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchDisplayPageEntryException(msg.toString());
	}

	/**
	 * Returns the last asset display page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AssetDisplayPageEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset display page entries before and after the current asset display page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetDisplayPageEntryId the primary key of the current asset display page entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry[] findByUuid_C_PrevAndNext(
			long assetDisplayPageEntryId, String uuid, long companyId,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		uuid = Objects.toString(uuid, "");

		AssetDisplayPageEntry assetDisplayPageEntry = findByPrimaryKey(
			assetDisplayPageEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetDisplayPageEntry[] array = new AssetDisplayPageEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, assetDisplayPageEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = assetDisplayPageEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, assetDisplayPageEntry, uuid, companyId,
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

	protected AssetDisplayPageEntry getByUuid_C_PrevAndNext(
		Session session, AssetDisplayPageEntry assetDisplayPageEntry,
		String uuid, long companyId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE);

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
			query.append(AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL);
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
						assetDisplayPageEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetDisplayPageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset display page entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AssetDisplayPageEntry assetDisplayPageEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetDisplayPageEntry);
		}
	}

	/**
	 * Returns the number of asset display page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset display page entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetDisplayPageEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETDISPLAYPAGEENTRY_WHERE);

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

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"assetDisplayPageEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(assetDisplayPageEntry.uuid IS NULL OR assetDisplayPageEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"assetDisplayPageEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the asset display page entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display page entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @return the range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetDisplayPageEntry.class);

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

		List<AssetDisplayPageEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetDisplayPageEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetDisplayPageEntry assetDisplayPageEntry : list) {
					if (groupId != assetDisplayPageEntry.getGroupId()) {
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

			query.append(_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<AssetDisplayPageEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset display page entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByGroupId_First(
			long groupId,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (assetDisplayPageEntry != null) {
			return assetDisplayPageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchDisplayPageEntryException(msg.toString());
	}

	/**
	 * Returns the first asset display page entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByGroupId_First(
		long groupId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		List<AssetDisplayPageEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset display page entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByGroupId_Last(
			long groupId,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (assetDisplayPageEntry != null) {
			return assetDisplayPageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchDisplayPageEntryException(msg.toString());
	}

	/**
	 * Returns the last asset display page entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByGroupId_Last(
		long groupId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<AssetDisplayPageEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset display page entries before and after the current asset display page entry in the ordered set where groupId = &#63;.
	 *
	 * @param assetDisplayPageEntryId the primary key of the current asset display page entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry[] findByGroupId_PrevAndNext(
			long assetDisplayPageEntryId, long groupId,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = findByPrimaryKey(
			assetDisplayPageEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetDisplayPageEntry[] array = new AssetDisplayPageEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, assetDisplayPageEntry, groupId, orderByComparator,
				true);

			array[1] = assetDisplayPageEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, assetDisplayPageEntry, groupId, orderByComparator,
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

	protected AssetDisplayPageEntry getByGroupId_PrevAndNext(
		Session session, AssetDisplayPageEntry assetDisplayPageEntry,
		long groupId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetDisplayPageEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetDisplayPageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset display page entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (AssetDisplayPageEntry assetDisplayPageEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetDisplayPageEntry);
		}
	}

	/**
	 * Returns the number of asset display page entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset display page entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetDisplayPageEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId;

			finderArgs = new Object[] {groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETDISPLAYPAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"assetDisplayPageEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByLayoutPageTemplateEntryId;
	private FinderPath
		_finderPathWithoutPaginationFindByLayoutPageTemplateEntryId;
	private FinderPath _finderPathCountByLayoutPageTemplateEntryId;

	/**
	 * Returns all the asset display page entries where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		return findByLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset display page entries where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @return the range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end) {

		return findByLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return findByLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetDisplayPageEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByLayoutPageTemplateEntryId;
				finderArgs = new Object[] {layoutPageTemplateEntryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath =
				_finderPathWithPaginationFindByLayoutPageTemplateEntryId;
			finderArgs = new Object[] {
				layoutPageTemplateEntryId, start, end, orderByComparator
			};
		}

		List<AssetDisplayPageEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetDisplayPageEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetDisplayPageEntry assetDisplayPageEntry : list) {
					if (layoutPageTemplateEntryId !=
							assetDisplayPageEntry.
								getLayoutPageTemplateEntryId()) {

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

			query.append(_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE);

			query.append(
				_FINDER_COLUMN_LAYOUTPAGETEMPLATEENTRYID_LAYOUTPAGETEMPLATEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutPageTemplateEntryId);

				list = (List<AssetDisplayPageEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByLayoutPageTemplateEntryId_First(
			long layoutPageTemplateEntryId,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry =
			fetchByLayoutPageTemplateEntryId_First(
				layoutPageTemplateEntryId, orderByComparator);

		if (assetDisplayPageEntry != null) {
			return assetDisplayPageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPageTemplateEntryId=");
		msg.append(layoutPageTemplateEntryId);

		msg.append("}");

		throw new NoSuchDisplayPageEntryException(msg.toString());
	}

	/**
	 * Returns the first asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByLayoutPageTemplateEntryId_First(
		long layoutPageTemplateEntryId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		List<AssetDisplayPageEntry> list = findByLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByLayoutPageTemplateEntryId_Last(
			long layoutPageTemplateEntryId,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry =
			fetchByLayoutPageTemplateEntryId_Last(
				layoutPageTemplateEntryId, orderByComparator);

		if (assetDisplayPageEntry != null) {
			return assetDisplayPageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPageTemplateEntryId=");
		msg.append(layoutPageTemplateEntryId);

		msg.append("}");

		throw new NoSuchDisplayPageEntryException(msg.toString());
	}

	/**
	 * Returns the last asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByLayoutPageTemplateEntryId_Last(
		long layoutPageTemplateEntryId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		int count = countByLayoutPageTemplateEntryId(layoutPageTemplateEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetDisplayPageEntry> list = findByLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset display page entries before and after the current asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param assetDisplayPageEntryId the primary key of the current asset display page entry
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry[] findByLayoutPageTemplateEntryId_PrevAndNext(
			long assetDisplayPageEntryId, long layoutPageTemplateEntryId,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = findByPrimaryKey(
			assetDisplayPageEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetDisplayPageEntry[] array = new AssetDisplayPageEntryImpl[3];

			array[0] = getByLayoutPageTemplateEntryId_PrevAndNext(
				session, assetDisplayPageEntry, layoutPageTemplateEntryId,
				orderByComparator, true);

			array[1] = assetDisplayPageEntry;

			array[2] = getByLayoutPageTemplateEntryId_PrevAndNext(
				session, assetDisplayPageEntry, layoutPageTemplateEntryId,
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

	protected AssetDisplayPageEntry getByLayoutPageTemplateEntryId_PrevAndNext(
		Session session, AssetDisplayPageEntry assetDisplayPageEntry,
		long layoutPageTemplateEntryId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE);

		query.append(
			_FINDER_COLUMN_LAYOUTPAGETEMPLATEENTRYID_LAYOUTPAGETEMPLATEENTRYID_2);

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
			query.append(AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutPageTemplateEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetDisplayPageEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetDisplayPageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset display page entries where layoutPageTemplateEntryId = &#63; from the database.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 */
	@Override
	public void removeByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		for (AssetDisplayPageEntry assetDisplayPageEntry :
				findByLayoutPageTemplateEntryId(
					layoutPageTemplateEntryId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(assetDisplayPageEntry);
		}
	}

	/**
	 * Returns the number of asset display page entries where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the number of matching asset display page entries
	 */
	@Override
	public int countByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetDisplayPageEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByLayoutPageTemplateEntryId;

			finderArgs = new Object[] {layoutPageTemplateEntryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETDISPLAYPAGEENTRY_WHERE);

			query.append(
				_FINDER_COLUMN_LAYOUTPAGETEMPLATEENTRYID_LAYOUTPAGETEMPLATEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutPageTemplateEntryId);

				count = (Long)q.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_LAYOUTPAGETEMPLATEENTRYID_LAYOUTPAGETEMPLATEENTRYID_2 =
			"assetDisplayPageEntry.layoutPageTemplateEntryId = ?";

	private FinderPath _finderPathFetchByG_C_C;
	private FinderPath _finderPathCountByG_C_C;

	/**
	 * Returns the asset display page entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchDisplayPageEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByG_C_C(
			long groupId, long classNameId, long classPK)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = fetchByG_C_C(
			groupId, classNameId, classPK);

		if (assetDisplayPageEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchDisplayPageEntryException(msg.toString());
		}

		return assetDisplayPageEntry;
	}

	/**
	 * Returns the asset display page entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByG_C_C(
		long groupId, long classNameId, long classPK) {

		return fetchByG_C_C(groupId, classNameId, classPK, true);
	}

	/**
	 * Returns the asset display page entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByG_C_C(
		long groupId, long classNameId, long classPK, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetDisplayPageEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, classNameId, classPK};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_C_C, finderArgs, this);
		}

		if (result instanceof AssetDisplayPageEntry) {
			AssetDisplayPageEntry assetDisplayPageEntry =
				(AssetDisplayPageEntry)result;

			if ((groupId != assetDisplayPageEntry.getGroupId()) ||
				(classNameId != assetDisplayPageEntry.getClassNameId()) ||
				(classPK != assetDisplayPageEntry.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<AssetDisplayPageEntry> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_C_C, finderArgs, list);
					}
				}
				else {
					AssetDisplayPageEntry assetDisplayPageEntry = list.get(0);

					result = assetDisplayPageEntry;

					cacheResult(assetDisplayPageEntry);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					finderCache.removeResult(
						_finderPathFetchByG_C_C, finderArgs);
				}

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
			return (AssetDisplayPageEntry)result;
		}
	}

	/**
	 * Removes the asset display page entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the asset display page entry that was removed
	 */
	@Override
	public AssetDisplayPageEntry removeByG_C_C(
			long groupId, long classNameId, long classPK)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = findByG_C_C(
			groupId, classNameId, classPK);

		return remove(assetDisplayPageEntry);
	}

	/**
	 * Returns the number of asset display page entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching asset display page entries
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetDisplayPageEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_C;

			finderArgs = new Object[] {groupId, classNameId, classPK};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_ASSETDISPLAYPAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 =
		"assetDisplayPageEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 =
		"assetDisplayPageEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 =
		"assetDisplayPageEntry.classPK = ?";

	public AssetDisplayPageEntryPersistenceImpl() {
		setModelClass(AssetDisplayPageEntry.class);

		setModelImplClass(AssetDisplayPageEntryImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the asset display page entry in the entity cache if it is enabled.
	 *
	 * @param assetDisplayPageEntry the asset display page entry
	 */
	@Override
	public void cacheResult(AssetDisplayPageEntry assetDisplayPageEntry) {
		if (assetDisplayPageEntry.getCtCollectionId() != 0) {
			assetDisplayPageEntry.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			entityCacheEnabled, AssetDisplayPageEntryImpl.class,
			assetDisplayPageEntry.getPrimaryKey(), assetDisplayPageEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				assetDisplayPageEntry.getUuid(),
				assetDisplayPageEntry.getGroupId()
			},
			assetDisplayPageEntry);

		finderCache.putResult(
			_finderPathFetchByG_C_C,
			new Object[] {
				assetDisplayPageEntry.getGroupId(),
				assetDisplayPageEntry.getClassNameId(),
				assetDisplayPageEntry.getClassPK()
			},
			assetDisplayPageEntry);

		assetDisplayPageEntry.resetOriginalValues();
	}

	/**
	 * Caches the asset display page entries in the entity cache if it is enabled.
	 *
	 * @param assetDisplayPageEntries the asset display page entries
	 */
	@Override
	public void cacheResult(
		List<AssetDisplayPageEntry> assetDisplayPageEntries) {

		for (AssetDisplayPageEntry assetDisplayPageEntry :
				assetDisplayPageEntries) {

			if (assetDisplayPageEntry.getCtCollectionId() != 0) {
				assetDisplayPageEntry.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					entityCacheEnabled, AssetDisplayPageEntryImpl.class,
					assetDisplayPageEntry.getPrimaryKey()) == null) {

				cacheResult(assetDisplayPageEntry);
			}
			else {
				assetDisplayPageEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset display page entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetDisplayPageEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset display page entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetDisplayPageEntry assetDisplayPageEntry) {
		entityCache.removeResult(
			entityCacheEnabled, AssetDisplayPageEntryImpl.class,
			assetDisplayPageEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(AssetDisplayPageEntryModelImpl)assetDisplayPageEntry, true);
	}

	@Override
	public void clearCache(
		List<AssetDisplayPageEntry> assetDisplayPageEntries) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetDisplayPageEntry assetDisplayPageEntry :
				assetDisplayPageEntries) {

			entityCache.removeResult(
				entityCacheEnabled, AssetDisplayPageEntryImpl.class,
				assetDisplayPageEntry.getPrimaryKey());

			clearUniqueFindersCache(
				(AssetDisplayPageEntryModelImpl)assetDisplayPageEntry, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, AssetDisplayPageEntryImpl.class,
				primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetDisplayPageEntryModelImpl assetDisplayPageEntryModelImpl) {

		Object[] args = new Object[] {
			assetDisplayPageEntryModelImpl.getUuid(),
			assetDisplayPageEntryModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, assetDisplayPageEntryModelImpl,
			false);

		args = new Object[] {
			assetDisplayPageEntryModelImpl.getGroupId(),
			assetDisplayPageEntryModelImpl.getClassNameId(),
			assetDisplayPageEntryModelImpl.getClassPK()
		};

		finderCache.putResult(
			_finderPathCountByG_C_C, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_C_C, args, assetDisplayPageEntryModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		AssetDisplayPageEntryModelImpl assetDisplayPageEntryModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetDisplayPageEntryModelImpl.getUuid(),
				assetDisplayPageEntryModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((assetDisplayPageEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetDisplayPageEntryModelImpl.getOriginalUuid(),
				assetDisplayPageEntryModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetDisplayPageEntryModelImpl.getGroupId(),
				assetDisplayPageEntryModelImpl.getClassNameId(),
				assetDisplayPageEntryModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByG_C_C, args);
			finderCache.removeResult(_finderPathFetchByG_C_C, args);
		}

		if ((assetDisplayPageEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_C_C.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetDisplayPageEntryModelImpl.getOriginalGroupId(),
				assetDisplayPageEntryModelImpl.getOriginalClassNameId(),
				assetDisplayPageEntryModelImpl.getOriginalClassPK()
			};

			finderCache.removeResult(_finderPathCountByG_C_C, args);
			finderCache.removeResult(_finderPathFetchByG_C_C, args);
		}
	}

	/**
	 * Creates a new asset display page entry with the primary key. Does not add the asset display page entry to the database.
	 *
	 * @param assetDisplayPageEntryId the primary key for the new asset display page entry
	 * @return the new asset display page entry
	 */
	@Override
	public AssetDisplayPageEntry create(long assetDisplayPageEntryId) {
		AssetDisplayPageEntry assetDisplayPageEntry =
			new AssetDisplayPageEntryImpl();

		assetDisplayPageEntry.setNew(true);
		assetDisplayPageEntry.setPrimaryKey(assetDisplayPageEntryId);

		String uuid = PortalUUIDUtil.generate();

		assetDisplayPageEntry.setUuid(uuid);

		assetDisplayPageEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return assetDisplayPageEntry;
	}

	/**
	 * Removes the asset display page entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetDisplayPageEntryId the primary key of the asset display page entry
	 * @return the asset display page entry that was removed
	 * @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry remove(long assetDisplayPageEntryId)
		throws NoSuchDisplayPageEntryException {

		return remove((Serializable)assetDisplayPageEntryId);
	}

	/**
	 * Removes the asset display page entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset display page entry
	 * @return the asset display page entry that was removed
	 * @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry remove(Serializable primaryKey)
		throws NoSuchDisplayPageEntryException {

		Session session = null;

		try {
			session = openSession();

			AssetDisplayPageEntry assetDisplayPageEntry =
				(AssetDisplayPageEntry)session.get(
					AssetDisplayPageEntryImpl.class, primaryKey);

			if (assetDisplayPageEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDisplayPageEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetDisplayPageEntry);
		}
		catch (NoSuchDisplayPageEntryException noSuchEntityException) {
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
	protected AssetDisplayPageEntry removeImpl(
		AssetDisplayPageEntry assetDisplayPageEntry) {

		if (!ctPersistenceHelper.isRemove(assetDisplayPageEntry)) {
			return assetDisplayPageEntry;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetDisplayPageEntry)) {
				assetDisplayPageEntry = (AssetDisplayPageEntry)session.get(
					AssetDisplayPageEntryImpl.class,
					assetDisplayPageEntry.getPrimaryKeyObj());
			}

			if (assetDisplayPageEntry != null) {
				session.delete(assetDisplayPageEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetDisplayPageEntry != null) {
			clearCache(assetDisplayPageEntry);
		}

		return assetDisplayPageEntry;
	}

	@Override
	public AssetDisplayPageEntry updateImpl(
		AssetDisplayPageEntry assetDisplayPageEntry) {

		boolean isNew = assetDisplayPageEntry.isNew();

		if (!(assetDisplayPageEntry instanceof
				AssetDisplayPageEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetDisplayPageEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					assetDisplayPageEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetDisplayPageEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetDisplayPageEntry implementation " +
					assetDisplayPageEntry.getClass());
		}

		AssetDisplayPageEntryModelImpl assetDisplayPageEntryModelImpl =
			(AssetDisplayPageEntryModelImpl)assetDisplayPageEntry;

		if (Validator.isNull(assetDisplayPageEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetDisplayPageEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (assetDisplayPageEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetDisplayPageEntry.setCreateDate(now);
			}
			else {
				assetDisplayPageEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!assetDisplayPageEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetDisplayPageEntry.setModifiedDate(now);
			}
			else {
				assetDisplayPageEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(assetDisplayPageEntry)) {
				if (!isNew) {
					AssetDisplayPageEntry oldAssetDisplayPageEntry =
						(AssetDisplayPageEntry)session.get(
							AssetDisplayPageEntryImpl.class,
							assetDisplayPageEntry.getPrimaryKeyObj());

					if (oldAssetDisplayPageEntry != null) {
						session.evict(oldAssetDisplayPageEntry);
					}
				}

				session.save(assetDisplayPageEntry);

				assetDisplayPageEntry.setNew(false);
			}
			else {
				assetDisplayPageEntry = (AssetDisplayPageEntry)session.merge(
					assetDisplayPageEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetDisplayPageEntry.getCtCollectionId() != 0) {
			assetDisplayPageEntry.resetOriginalValues();

			return assetDisplayPageEntry;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				assetDisplayPageEntryModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				assetDisplayPageEntryModelImpl.getUuid(),
				assetDisplayPageEntryModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {assetDisplayPageEntryModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				assetDisplayPageEntryModelImpl.getLayoutPageTemplateEntryId()
			};

			finderCache.removeResult(
				_finderPathCountByLayoutPageTemplateEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByLayoutPageTemplateEntryId,
				args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((assetDisplayPageEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetDisplayPageEntryModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {assetDisplayPageEntryModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((assetDisplayPageEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetDisplayPageEntryModelImpl.getOriginalUuid(),
					assetDisplayPageEntryModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					assetDisplayPageEntryModelImpl.getUuid(),
					assetDisplayPageEntryModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((assetDisplayPageEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetDisplayPageEntryModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {
					assetDisplayPageEntryModelImpl.getGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((assetDisplayPageEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByLayoutPageTemplateEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetDisplayPageEntryModelImpl.
						getOriginalLayoutPageTemplateEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByLayoutPageTemplateEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByLayoutPageTemplateEntryId,
					args);

				args = new Object[] {
					assetDisplayPageEntryModelImpl.
						getLayoutPageTemplateEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByLayoutPageTemplateEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByLayoutPageTemplateEntryId,
					args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, AssetDisplayPageEntryImpl.class,
			assetDisplayPageEntry.getPrimaryKey(), assetDisplayPageEntry,
			false);

		clearUniqueFindersCache(assetDisplayPageEntryModelImpl, false);
		cacheUniqueFindersCache(assetDisplayPageEntryModelImpl);

		assetDisplayPageEntry.resetOriginalValues();

		return assetDisplayPageEntry;
	}

	/**
	 * Returns the asset display page entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset display page entry
	 * @return the asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = fetchByPrimaryKey(
			primaryKey);

		if (assetDisplayPageEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDisplayPageEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetDisplayPageEntry;
	}

	/**
	 * Returns the asset display page entry with the primary key or throws a <code>NoSuchDisplayPageEntryException</code> if it could not be found.
	 *
	 * @param assetDisplayPageEntryId the primary key of the asset display page entry
	 * @return the asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByPrimaryKey(long assetDisplayPageEntryId)
		throws NoSuchDisplayPageEntryException {

		return findByPrimaryKey((Serializable)assetDisplayPageEntryId);
	}

	/**
	 * Returns the asset display page entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset display page entry
	 * @return the asset display page entry, or <code>null</code> if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(AssetDisplayPageEntry.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		AssetDisplayPageEntry assetDisplayPageEntry = null;

		Session session = null;

		try {
			session = openSession();

			assetDisplayPageEntry = (AssetDisplayPageEntry)session.get(
				AssetDisplayPageEntryImpl.class, primaryKey);

			if (assetDisplayPageEntry != null) {
				cacheResult(assetDisplayPageEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return assetDisplayPageEntry;
	}

	/**
	 * Returns the asset display page entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetDisplayPageEntryId the primary key of the asset display page entry
	 * @return the asset display page entry, or <code>null</code> if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByPrimaryKey(
		long assetDisplayPageEntryId) {

		return fetchByPrimaryKey((Serializable)assetDisplayPageEntryId);
	}

	@Override
	public Map<Serializable, AssetDisplayPageEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(AssetDisplayPageEntry.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetDisplayPageEntry> map =
			new HashMap<Serializable, AssetDisplayPageEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetDisplayPageEntry assetDisplayPageEntry = fetchByPrimaryKey(
				primaryKey);

			if (assetDisplayPageEntry != null) {
				map.put(primaryKey, assetDisplayPageEntry);
			}

			return map;
		}

		StringBundler query = new StringBundler(primaryKeys.size() * 2 + 1);

		query.append(getSelectSQL());
		query.append(" WHERE ");
		query.append(getPKDBName());
		query.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (AssetDisplayPageEntry assetDisplayPageEntry :
					(List<AssetDisplayPageEntry>)q.list()) {

				map.put(
					assetDisplayPageEntry.getPrimaryKeyObj(),
					assetDisplayPageEntry);

				cacheResult(assetDisplayPageEntry);
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
	 * Returns all the asset display page entries.
	 *
	 * @return the asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display page entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @return the range of asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display page entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findAll(
		int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset display page entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findAll(
		int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetDisplayPageEntry.class);

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

		List<AssetDisplayPageEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetDisplayPageEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ASSETDISPLAYPAGEENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETDISPLAYPAGEENTRY;

				sql = sql.concat(AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<AssetDisplayPageEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset display page entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetDisplayPageEntry assetDisplayPageEntry : findAll()) {
			remove(assetDisplayPageEntry);
		}
	}

	/**
	 * Returns the number of asset display page entries.
	 *
	 * @return the number of asset display page entries
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetDisplayPageEntry.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETDISPLAYPAGEENTRY);

				count = (Long)q.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					finderCache.removeResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY);
				}

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
		return "assetDisplayPageEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETDISPLAYPAGEENTRY;
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
		return AssetDisplayPageEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AssetDisplayPageEntry";
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
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("layoutPageTemplateEntryId");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("plid");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("assetDisplayPageEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "classNameId", "classPK"});
	}

	/**
	 * Initializes the asset display page entry persistence.
	 */
	@Activate
	public void activate() {
		AssetDisplayPageEntryModelImpl.setEntityCacheEnabled(
			entityCacheEnabled);
		AssetDisplayPageEntryModelImpl.setFinderCacheEnabled(
			finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetDisplayPageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetDisplayPageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetDisplayPageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetDisplayPageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			AssetDisplayPageEntryModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetDisplayPageEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			AssetDisplayPageEntryModelImpl.UUID_COLUMN_BITMASK |
			AssetDisplayPageEntryModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetDisplayPageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetDisplayPageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			AssetDisplayPageEntryModelImpl.UUID_COLUMN_BITMASK |
			AssetDisplayPageEntryModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetDisplayPageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetDisplayPageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			AssetDisplayPageEntryModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByLayoutPageTemplateEntryId =
			new FinderPath(
				entityCacheEnabled, finderCacheEnabled,
				AssetDisplayPageEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByLayoutPageTemplateEntryId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByLayoutPageTemplateEntryId =
			new FinderPath(
				entityCacheEnabled, finderCacheEnabled,
				AssetDisplayPageEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByLayoutPageTemplateEntryId",
				new String[] {Long.class.getName()},
				AssetDisplayPageEntryModelImpl.
					LAYOUTPAGETEMPLATEENTRYID_COLUMN_BITMASK);

		_finderPathCountByLayoutPageTemplateEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutPageTemplateEntryId",
			new String[] {Long.class.getName()});

		_finderPathFetchByG_C_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetDisplayPageEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			AssetDisplayPageEntryModelImpl.GROUPID_COLUMN_BITMASK |
			AssetDisplayPageEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AssetDisplayPageEntryModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByG_C_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AssetDisplayPageEntryImpl.class.getName());
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
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.asset.display.page.model.AssetDisplayPageEntry"),
			true);
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

	private boolean _columnBitmaskEnabled;

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ASSETDISPLAYPAGEENTRY =
		"SELECT assetDisplayPageEntry FROM AssetDisplayPageEntry assetDisplayPageEntry";

	private static final String _SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE =
		"SELECT assetDisplayPageEntry FROM AssetDisplayPageEntry assetDisplayPageEntry WHERE ";

	private static final String _SQL_COUNT_ASSETDISPLAYPAGEENTRY =
		"SELECT COUNT(assetDisplayPageEntry) FROM AssetDisplayPageEntry assetDisplayPageEntry";

	private static final String _SQL_COUNT_ASSETDISPLAYPAGEENTRY_WHERE =
		"SELECT COUNT(assetDisplayPageEntry) FROM AssetDisplayPageEntry assetDisplayPageEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"assetDisplayPageEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetDisplayPageEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetDisplayPageEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetDisplayPageEntryPersistenceImpl.class);

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