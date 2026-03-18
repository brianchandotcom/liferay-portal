/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.service.persistence.impl;

import com.liferay.asset.kernel.exception.NoSuchTagGroupRelException;
import com.liferay.asset.kernel.model.AssetTagGroupRel;
import com.liferay.asset.kernel.model.AssetTagGroupRelTable;
import com.liferay.asset.kernel.service.persistence.AssetTagGroupRelPersistence;
import com.liferay.asset.kernel.service.persistence.AssetTagGroupRelUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portlet.asset.model.impl.AssetTagGroupRelImpl;
import com.liferay.portlet.asset.model.impl.AssetTagGroupRelModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the asset tag group rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetTagGroupRelPersistenceImpl
	extends BasePersistenceImpl<AssetTagGroupRel>
	implements AssetTagGroupRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetTagGroupRelUtil</code> to access the asset tag group rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetTagGroupRelImpl.class.getName();

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
	 * Returns all the asset tag group rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tag group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @return the range of matching asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetTagGroupRel.class)) {

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

			List<AssetTagGroupRel> list = null;

			if (useFinderCache) {
				list = (List<AssetTagGroupRel>)FinderCacheUtil.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (AssetTagGroupRel assetTagGroupRel : list) {
						if (!uuid.equals(assetTagGroupRel.getUuid())) {
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

				sb.append(_SQL_SELECT_ASSETTAGGROUPREL_WHERE);

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
					sb.append(AssetTagGroupRelModelImpl.ORDER_BY_JPQL);
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

					list = (List<AssetTagGroupRel>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
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
	}

	/**
	 * Returns the first asset tag group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel findByUuid_First(
			String uuid, OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws NoSuchTagGroupRelException {

		AssetTagGroupRel assetTagGroupRel = fetchByUuid_First(
			uuid, orderByComparator);

		if (assetTagGroupRel != null) {
			return assetTagGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchTagGroupRelException(sb.toString());
	}

	/**
	 * Returns the first asset tag group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel fetchByUuid_First(
		String uuid, OrderByComparator<AssetTagGroupRel> orderByComparator) {

		List<AssetTagGroupRel> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset tag group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel findByUuid_Last(
			String uuid, OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws NoSuchTagGroupRelException {

		AssetTagGroupRel assetTagGroupRel = fetchByUuid_Last(
			uuid, orderByComparator);

		if (assetTagGroupRel != null) {
			return assetTagGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchTagGroupRelException(sb.toString());
	}

	/**
	 * Returns the last asset tag group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel fetchByUuid_Last(
		String uuid, OrderByComparator<AssetTagGroupRel> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AssetTagGroupRel> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset tag group rels before and after the current asset tag group rel in the ordered set where uuid = &#63;.
	 *
	 * @param assetTagGroupRelId the primary key of the current asset tag group rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag group rel
	 * @throws NoSuchTagGroupRelException if a asset tag group rel with the primary key could not be found
	 */
	@Override
	public AssetTagGroupRel[] findByUuid_PrevAndNext(
			long assetTagGroupRelId, String uuid,
			OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws NoSuchTagGroupRelException {

		uuid = Objects.toString(uuid, "");

		AssetTagGroupRel assetTagGroupRel = findByPrimaryKey(
			assetTagGroupRelId);

		Session session = null;

		try {
			session = openSession();

			AssetTagGroupRel[] array = new AssetTagGroupRelImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, assetTagGroupRel, uuid, orderByComparator, true);

			array[1] = assetTagGroupRel;

			array[2] = getByUuid_PrevAndNext(
				session, assetTagGroupRel, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetTagGroupRel getByUuid_PrevAndNext(
		Session session, AssetTagGroupRel assetTagGroupRel, String uuid,
		OrderByComparator<AssetTagGroupRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETTAGGROUPREL_WHERE);

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
			sb.append(AssetTagGroupRelModelImpl.ORDER_BY_JPQL);
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
						assetTagGroupRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetTagGroupRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset tag group rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AssetTagGroupRel assetTagGroupRel :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetTagGroupRel);
		}
	}

	/**
	 * Returns the number of asset tag group rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset tag group rels
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetTagGroupRel.class)) {

			uuid = Objects.toString(uuid, "");

			FinderPath finderPath = _finderPathCountByUuid;

			Object[] finderArgs = new Object[] {uuid};

			Long count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_ASSETTAGGROUPREL_WHERE);

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

					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"assetTagGroupRel.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(assetTagGroupRel.uuid IS NULL OR assetTagGroupRel.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;

	/**
	 * Returns the asset tag group rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchTagGroupRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel findByUUID_G(String uuid, long groupId)
		throws NoSuchTagGroupRelException {

		AssetTagGroupRel assetTagGroupRel = fetchByUUID_G(uuid, groupId);

		if (assetTagGroupRel == null) {
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

			throw new NoSuchTagGroupRelException(sb.toString());
		}

		return assetTagGroupRel;
	}

	/**
	 * Returns the asset tag group rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the asset tag group rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetTagGroupRel.class)) {

			uuid = Objects.toString(uuid, "");

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {uuid, groupId};
			}

			Object result = null;

			if (useFinderCache) {
				result = FinderCacheUtil.getResult(
					_finderPathFetchByUUID_G, finderArgs, this);
			}

			if (result instanceof AssetTagGroupRel) {
				AssetTagGroupRel assetTagGroupRel = (AssetTagGroupRel)result;

				if (!Objects.equals(uuid, assetTagGroupRel.getUuid()) ||
					(groupId != assetTagGroupRel.getGroupId())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_ASSETTAGGROUPREL_WHERE);

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

					List<AssetTagGroupRel> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							FinderCacheUtil.putResult(
								_finderPathFetchByUUID_G, finderArgs, list);
						}
					}
					else {
						AssetTagGroupRel assetTagGroupRel = list.get(0);

						result = assetTagGroupRel;

						cacheResult(assetTagGroupRel);
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
				return (AssetTagGroupRel)result;
			}
		}
	}

	/**
	 * Removes the asset tag group rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset tag group rel that was removed
	 */
	@Override
	public AssetTagGroupRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchTagGroupRelException {

		AssetTagGroupRel assetTagGroupRel = findByUUID_G(uuid, groupId);

		return remove(assetTagGroupRel);
	}

	/**
	 * Returns the number of asset tag group rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset tag group rels
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		AssetTagGroupRel assetTagGroupRel = fetchByUUID_G(uuid, groupId);

		if (assetTagGroupRel == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"assetTagGroupRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(assetTagGroupRel.uuid IS NULL OR assetTagGroupRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"assetTagGroupRel.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the asset tag group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tag group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @return the range of matching asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetTagGroupRel.class)) {

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

			List<AssetTagGroupRel> list = null;

			if (useFinderCache) {
				list = (List<AssetTagGroupRel>)FinderCacheUtil.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (AssetTagGroupRel assetTagGroupRel : list) {
						if (!uuid.equals(assetTagGroupRel.getUuid()) ||
							(companyId != assetTagGroupRel.getCompanyId())) {

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

				sb.append(_SQL_SELECT_ASSETTAGGROUPREL_WHERE);

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
					sb.append(AssetTagGroupRelModelImpl.ORDER_BY_JPQL);
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

					list = (List<AssetTagGroupRel>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
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
	}

	/**
	 * Returns the first asset tag group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws NoSuchTagGroupRelException {

		AssetTagGroupRel assetTagGroupRel = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (assetTagGroupRel != null) {
			return assetTagGroupRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTagGroupRelException(sb.toString());
	}

	/**
	 * Returns the first asset tag group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetTagGroupRel> orderByComparator) {

		List<AssetTagGroupRel> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset tag group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws NoSuchTagGroupRelException {

		AssetTagGroupRel assetTagGroupRel = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (assetTagGroupRel != null) {
			return assetTagGroupRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTagGroupRelException(sb.toString());
	}

	/**
	 * Returns the last asset tag group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetTagGroupRel> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AssetTagGroupRel> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset tag group rels before and after the current asset tag group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetTagGroupRelId the primary key of the current asset tag group rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag group rel
	 * @throws NoSuchTagGroupRelException if a asset tag group rel with the primary key could not be found
	 */
	@Override
	public AssetTagGroupRel[] findByUuid_C_PrevAndNext(
			long assetTagGroupRelId, String uuid, long companyId,
			OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws NoSuchTagGroupRelException {

		uuid = Objects.toString(uuid, "");

		AssetTagGroupRel assetTagGroupRel = findByPrimaryKey(
			assetTagGroupRelId);

		Session session = null;

		try {
			session = openSession();

			AssetTagGroupRel[] array = new AssetTagGroupRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, assetTagGroupRel, uuid, companyId, orderByComparator,
				true);

			array[1] = assetTagGroupRel;

			array[2] = getByUuid_C_PrevAndNext(
				session, assetTagGroupRel, uuid, companyId, orderByComparator,
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

	protected AssetTagGroupRel getByUuid_C_PrevAndNext(
		Session session, AssetTagGroupRel assetTagGroupRel, String uuid,
		long companyId, OrderByComparator<AssetTagGroupRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETTAGGROUPREL_WHERE);

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
			sb.append(AssetTagGroupRelModelImpl.ORDER_BY_JPQL);
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
						assetTagGroupRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetTagGroupRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset tag group rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AssetTagGroupRel assetTagGroupRel :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetTagGroupRel);
		}
	}

	/**
	 * Returns the number of asset tag group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset tag group rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetTagGroupRel.class)) {

			uuid = Objects.toString(uuid, "");

			FinderPath finderPath = _finderPathCountByUuid_C;

			Object[] finderArgs = new Object[] {uuid, companyId};

			Long count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_ASSETTAGGROUPREL_WHERE);

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

					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"assetTagGroupRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(assetTagGroupRel.uuid IS NULL OR assetTagGroupRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"assetTagGroupRel.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the asset tag group rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tag group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @return the range of matching asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetTagGroupRel.class)) {

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
				finderArgs = new Object[] {
					groupId, start, end, orderByComparator
				};
			}

			List<AssetTagGroupRel> list = null;

			if (useFinderCache) {
				list = (List<AssetTagGroupRel>)FinderCacheUtil.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (AssetTagGroupRel assetTagGroupRel : list) {
						if (groupId != assetTagGroupRel.getGroupId()) {
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

				sb.append(_SQL_SELECT_ASSETTAGGROUPREL_WHERE);

				sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(AssetTagGroupRelModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					list = (List<AssetTagGroupRel>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
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
	}

	/**
	 * Returns the first asset tag group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel findByGroupId_First(
			long groupId, OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws NoSuchTagGroupRelException {

		AssetTagGroupRel assetTagGroupRel = fetchByGroupId_First(
			groupId, orderByComparator);

		if (assetTagGroupRel != null) {
			return assetTagGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchTagGroupRelException(sb.toString());
	}

	/**
	 * Returns the first asset tag group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel fetchByGroupId_First(
		long groupId, OrderByComparator<AssetTagGroupRel> orderByComparator) {

		List<AssetTagGroupRel> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset tag group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel findByGroupId_Last(
			long groupId, OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws NoSuchTagGroupRelException {

		AssetTagGroupRel assetTagGroupRel = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (assetTagGroupRel != null) {
			return assetTagGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchTagGroupRelException(sb.toString());
	}

	/**
	 * Returns the last asset tag group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel fetchByGroupId_Last(
		long groupId, OrderByComparator<AssetTagGroupRel> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<AssetTagGroupRel> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset tag group rels before and after the current asset tag group rel in the ordered set where groupId = &#63;.
	 *
	 * @param assetTagGroupRelId the primary key of the current asset tag group rel
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag group rel
	 * @throws NoSuchTagGroupRelException if a asset tag group rel with the primary key could not be found
	 */
	@Override
	public AssetTagGroupRel[] findByGroupId_PrevAndNext(
			long assetTagGroupRelId, long groupId,
			OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws NoSuchTagGroupRelException {

		AssetTagGroupRel assetTagGroupRel = findByPrimaryKey(
			assetTagGroupRelId);

		Session session = null;

		try {
			session = openSession();

			AssetTagGroupRel[] array = new AssetTagGroupRelImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, assetTagGroupRel, groupId, orderByComparator, true);

			array[1] = assetTagGroupRel;

			array[2] = getByGroupId_PrevAndNext(
				session, assetTagGroupRel, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetTagGroupRel getByGroupId_PrevAndNext(
		Session session, AssetTagGroupRel assetTagGroupRel, long groupId,
		OrderByComparator<AssetTagGroupRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETTAGGROUPREL_WHERE);

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
			sb.append(AssetTagGroupRelModelImpl.ORDER_BY_JPQL);
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
						assetTagGroupRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetTagGroupRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset tag group rels where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (AssetTagGroupRel assetTagGroupRel :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetTagGroupRel);
		}
	}

	/**
	 * Returns the number of asset tag group rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset tag group rels
	 */
	@Override
	public int countByGroupId(long groupId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetTagGroupRel.class)) {

			FinderPath finderPath = _finderPathCountByGroupId;

			Object[] finderArgs = new Object[] {groupId};

			Long count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_ASSETTAGGROUPREL_WHERE);

				sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					count = (Long)query.uniqueResult();

					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"assetTagGroupRel.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByTagId;
	private FinderPath _finderPathWithoutPaginationFindByTagId;
	private FinderPath _finderPathCountByTagId;

	/**
	 * Returns all the asset tag group rels where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @return the matching asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findByTagId(long tagId) {
		return findByTagId(tagId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tag group rels where tagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param tagId the tag ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @return the range of matching asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findByTagId(long tagId, int start, int end) {
		return findByTagId(tagId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels where tagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param tagId the tag ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findByTagId(
		long tagId, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return findByTagId(tagId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels where tagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param tagId the tag ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findByTagId(
		long tagId, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetTagGroupRel.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByTagId;
					finderArgs = new Object[] {tagId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByTagId;
				finderArgs = new Object[] {
					tagId, start, end, orderByComparator
				};
			}

			List<AssetTagGroupRel> list = null;

			if (useFinderCache) {
				list = (List<AssetTagGroupRel>)FinderCacheUtil.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (AssetTagGroupRel assetTagGroupRel : list) {
						if (tagId != assetTagGroupRel.getTagId()) {
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

				sb.append(_SQL_SELECT_ASSETTAGGROUPREL_WHERE);

				sb.append(_FINDER_COLUMN_TAGID_TAGID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(AssetTagGroupRelModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(tagId);

					list = (List<AssetTagGroupRel>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
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
	}

	/**
	 * Returns the first asset tag group rel in the ordered set where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel findByTagId_First(
			long tagId, OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws NoSuchTagGroupRelException {

		AssetTagGroupRel assetTagGroupRel = fetchByTagId_First(
			tagId, orderByComparator);

		if (assetTagGroupRel != null) {
			return assetTagGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("tagId=");
		sb.append(tagId);

		sb.append("}");

		throw new NoSuchTagGroupRelException(sb.toString());
	}

	/**
	 * Returns the first asset tag group rel in the ordered set where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel fetchByTagId_First(
		long tagId, OrderByComparator<AssetTagGroupRel> orderByComparator) {

		List<AssetTagGroupRel> list = findByTagId(
			tagId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset tag group rel in the ordered set where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel findByTagId_Last(
			long tagId, OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws NoSuchTagGroupRelException {

		AssetTagGroupRel assetTagGroupRel = fetchByTagId_Last(
			tagId, orderByComparator);

		if (assetTagGroupRel != null) {
			return assetTagGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("tagId=");
		sb.append(tagId);

		sb.append("}");

		throw new NoSuchTagGroupRelException(sb.toString());
	}

	/**
	 * Returns the last asset tag group rel in the ordered set where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel fetchByTagId_Last(
		long tagId, OrderByComparator<AssetTagGroupRel> orderByComparator) {

		int count = countByTagId(tagId);

		if (count == 0) {
			return null;
		}

		List<AssetTagGroupRel> list = findByTagId(
			tagId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset tag group rels before and after the current asset tag group rel in the ordered set where tagId = &#63;.
	 *
	 * @param assetTagGroupRelId the primary key of the current asset tag group rel
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag group rel
	 * @throws NoSuchTagGroupRelException if a asset tag group rel with the primary key could not be found
	 */
	@Override
	public AssetTagGroupRel[] findByTagId_PrevAndNext(
			long assetTagGroupRelId, long tagId,
			OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws NoSuchTagGroupRelException {

		AssetTagGroupRel assetTagGroupRel = findByPrimaryKey(
			assetTagGroupRelId);

		Session session = null;

		try {
			session = openSession();

			AssetTagGroupRel[] array = new AssetTagGroupRelImpl[3];

			array[0] = getByTagId_PrevAndNext(
				session, assetTagGroupRel, tagId, orderByComparator, true);

			array[1] = assetTagGroupRel;

			array[2] = getByTagId_PrevAndNext(
				session, assetTagGroupRel, tagId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetTagGroupRel getByTagId_PrevAndNext(
		Session session, AssetTagGroupRel assetTagGroupRel, long tagId,
		OrderByComparator<AssetTagGroupRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETTAGGROUPREL_WHERE);

		sb.append(_FINDER_COLUMN_TAGID_TAGID_2);

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
			sb.append(AssetTagGroupRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(tagId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetTagGroupRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetTagGroupRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset tag group rels where tagId = &#63; from the database.
	 *
	 * @param tagId the tag ID
	 */
	@Override
	public void removeByTagId(long tagId) {
		for (AssetTagGroupRel assetTagGroupRel :
				findByTagId(
					tagId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetTagGroupRel);
		}
	}

	/**
	 * Returns the number of asset tag group rels where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @return the number of matching asset tag group rels
	 */
	@Override
	public int countByTagId(long tagId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetTagGroupRel.class)) {

			FinderPath finderPath = _finderPathCountByTagId;

			Object[] finderArgs = new Object[] {tagId};

			Long count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_ASSETTAGGROUPREL_WHERE);

				sb.append(_FINDER_COLUMN_TAGID_TAGID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(tagId);

					count = (Long)query.uniqueResult();

					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	}

	private static final String _FINDER_COLUMN_TAGID_TAGID_2 =
		"assetTagGroupRel.tagId = ?";

	private FinderPath _finderPathFetchByG_T;

	/**
	 * Returns the asset tag group rel where groupId = &#63; and tagId = &#63; or throws a <code>NoSuchTagGroupRelException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param tagId the tag ID
	 * @return the matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel findByG_T(long groupId, long tagId)
		throws NoSuchTagGroupRelException {

		AssetTagGroupRel assetTagGroupRel = fetchByG_T(groupId, tagId);

		if (assetTagGroupRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", tagId=");
			sb.append(tagId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTagGroupRelException(sb.toString());
		}

		return assetTagGroupRel;
	}

	/**
	 * Returns the asset tag group rel where groupId = &#63; and tagId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param tagId the tag ID
	 * @return the matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel fetchByG_T(long groupId, long tagId) {
		return fetchByG_T(groupId, tagId, true);
	}

	/**
	 * Returns the asset tag group rel where groupId = &#63; and tagId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param tagId the tag ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	@Override
	public AssetTagGroupRel fetchByG_T(
		long groupId, long tagId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetTagGroupRel.class)) {

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {groupId, tagId};
			}

			Object result = null;

			if (useFinderCache) {
				result = FinderCacheUtil.getResult(
					_finderPathFetchByG_T, finderArgs, this);
			}

			if (result instanceof AssetTagGroupRel) {
				AssetTagGroupRel assetTagGroupRel = (AssetTagGroupRel)result;

				if ((groupId != assetTagGroupRel.getGroupId()) ||
					(tagId != assetTagGroupRel.getTagId())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_ASSETTAGGROUPREL_WHERE);

				sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_T_TAGID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(tagId);

					List<AssetTagGroupRel> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							FinderCacheUtil.putResult(
								_finderPathFetchByG_T, finderArgs, list);
						}
					}
					else {
						if (list.size() > 1) {
							Collections.sort(list, Collections.reverseOrder());

							if (_log.isWarnEnabled()) {
								if (!useFinderCache) {
									finderArgs = new Object[] {groupId, tagId};
								}

								_log.warn(
									"AssetTagGroupRelPersistenceImpl.fetchByG_T(long, long, boolean) with parameters (" +
										StringUtil.merge(finderArgs) +
											") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
							}
						}

						AssetTagGroupRel assetTagGroupRel = list.get(0);

						result = assetTagGroupRel;

						cacheResult(assetTagGroupRel);
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
				return (AssetTagGroupRel)result;
			}
		}
	}

	/**
	 * Removes the asset tag group rel where groupId = &#63; and tagId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param tagId the tag ID
	 * @return the asset tag group rel that was removed
	 */
	@Override
	public AssetTagGroupRel removeByG_T(long groupId, long tagId)
		throws NoSuchTagGroupRelException {

		AssetTagGroupRel assetTagGroupRel = findByG_T(groupId, tagId);

		return remove(assetTagGroupRel);
	}

	/**
	 * Returns the number of asset tag group rels where groupId = &#63; and tagId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param tagId the tag ID
	 * @return the number of matching asset tag group rels
	 */
	@Override
	public int countByG_T(long groupId, long tagId) {
		AssetTagGroupRel assetTagGroupRel = fetchByG_T(groupId, tagId);

		if (assetTagGroupRel == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 =
		"assetTagGroupRel.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_TAGID_2 =
		"assetTagGroupRel.tagId = ?";

	public AssetTagGroupRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AssetTagGroupRel.class);

		setModelImplClass(AssetTagGroupRelImpl.class);
		setModelPKClass(long.class);

		setTable(AssetTagGroupRelTable.INSTANCE);
	}

	/**
	 * Caches the asset tag group rel in the entity cache if it is enabled.
	 *
	 * @param assetTagGroupRel the asset tag group rel
	 */
	@Override
	public void cacheResult(AssetTagGroupRel assetTagGroupRel) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					assetTagGroupRel.getCtCollectionId())) {

			EntityCacheUtil.putResult(
				AssetTagGroupRelImpl.class, assetTagGroupRel.getPrimaryKey(),
				assetTagGroupRel);

			FinderCacheUtil.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {
					assetTagGroupRel.getUuid(), assetTagGroupRel.getGroupId()
				},
				assetTagGroupRel);

			FinderCacheUtil.putResult(
				_finderPathFetchByG_T,
				new Object[] {
					assetTagGroupRel.getGroupId(), assetTagGroupRel.getTagId()
				},
				assetTagGroupRel);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the asset tag group rels in the entity cache if it is enabled.
	 *
	 * @param assetTagGroupRels the asset tag group rels
	 */
	@Override
	public void cacheResult(List<AssetTagGroupRel> assetTagGroupRels) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (assetTagGroupRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AssetTagGroupRel assetTagGroupRel : assetTagGroupRels) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						assetTagGroupRel.getCtCollectionId())) {

				if (EntityCacheUtil.getResult(
						AssetTagGroupRelImpl.class,
						assetTagGroupRel.getPrimaryKey()) == null) {

					cacheResult(assetTagGroupRel);
				}
			}
		}
	}

	/**
	 * Clears the cache for all asset tag group rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(AssetTagGroupRelImpl.class);

		FinderCacheUtil.clearCache(AssetTagGroupRelImpl.class);
	}

	/**
	 * Clears the cache for the asset tag group rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetTagGroupRel assetTagGroupRel) {
		EntityCacheUtil.removeResult(
			AssetTagGroupRelImpl.class, assetTagGroupRel);
	}

	@Override
	public void clearCache(List<AssetTagGroupRel> assetTagGroupRels) {
		for (AssetTagGroupRel assetTagGroupRel : assetTagGroupRels) {
			EntityCacheUtil.removeResult(
				AssetTagGroupRelImpl.class, assetTagGroupRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(AssetTagGroupRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				AssetTagGroupRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetTagGroupRelModelImpl assetTagGroupRelModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					assetTagGroupRelModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				assetTagGroupRelModelImpl.getUuid(),
				assetTagGroupRelModelImpl.getGroupId()
			};

			FinderCacheUtil.putResult(
				_finderPathFetchByUUID_G, args, assetTagGroupRelModelImpl);

			args = new Object[] {
				assetTagGroupRelModelImpl.getGroupId(),
				assetTagGroupRelModelImpl.getTagId()
			};

			FinderCacheUtil.putResult(
				_finderPathFetchByG_T, args, assetTagGroupRelModelImpl);
		}
	}

	/**
	 * Creates a new asset tag group rel with the primary key. Does not add the asset tag group rel to the database.
	 *
	 * @param assetTagGroupRelId the primary key for the new asset tag group rel
	 * @return the new asset tag group rel
	 */
	@Override
	public AssetTagGroupRel create(long assetTagGroupRelId) {
		AssetTagGroupRel assetTagGroupRel = new AssetTagGroupRelImpl();

		assetTagGroupRel.setNew(true);
		assetTagGroupRel.setPrimaryKey(assetTagGroupRelId);

		String uuid = PortalUUIDUtil.generate();

		assetTagGroupRel.setUuid(uuid);

		assetTagGroupRel.setCompanyId(CompanyThreadLocal.getCompanyId());

		return assetTagGroupRel;
	}

	/**
	 * Removes the asset tag group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetTagGroupRelId the primary key of the asset tag group rel
	 * @return the asset tag group rel that was removed
	 * @throws NoSuchTagGroupRelException if a asset tag group rel with the primary key could not be found
	 */
	@Override
	public AssetTagGroupRel remove(long assetTagGroupRelId)
		throws NoSuchTagGroupRelException {

		return remove((Serializable)assetTagGroupRelId);
	}

	/**
	 * Removes the asset tag group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset tag group rel
	 * @return the asset tag group rel that was removed
	 * @throws NoSuchTagGroupRelException if a asset tag group rel with the primary key could not be found
	 */
	@Override
	public AssetTagGroupRel remove(Serializable primaryKey)
		throws NoSuchTagGroupRelException {

		Session session = null;

		try {
			session = openSession();

			AssetTagGroupRel assetTagGroupRel = (AssetTagGroupRel)session.get(
				AssetTagGroupRelImpl.class, primaryKey);

			if (assetTagGroupRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTagGroupRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetTagGroupRel);
		}
		catch (NoSuchTagGroupRelException noSuchEntityException) {
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
	protected AssetTagGroupRel removeImpl(AssetTagGroupRel assetTagGroupRel) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetTagGroupRel)) {
				assetTagGroupRel = (AssetTagGroupRel)session.get(
					AssetTagGroupRelImpl.class,
					assetTagGroupRel.getPrimaryKeyObj());
			}

			if ((assetTagGroupRel != null) &&
				CTPersistenceHelperUtil.isRemove(assetTagGroupRel)) {

				session.delete(assetTagGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetTagGroupRel != null) {
			clearCache(assetTagGroupRel);
		}

		return assetTagGroupRel;
	}

	@Override
	public AssetTagGroupRel updateImpl(AssetTagGroupRel assetTagGroupRel) {
		boolean isNew = assetTagGroupRel.isNew();

		if (!(assetTagGroupRel instanceof AssetTagGroupRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetTagGroupRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					assetTagGroupRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetTagGroupRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetTagGroupRel implementation " +
					assetTagGroupRel.getClass());
		}

		AssetTagGroupRelModelImpl assetTagGroupRelModelImpl =
			(AssetTagGroupRelModelImpl)assetTagGroupRel;

		if (Validator.isNull(assetTagGroupRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetTagGroupRel.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(assetTagGroupRel)) {
				if (!isNew) {
					session.evict(
						AssetTagGroupRelImpl.class,
						assetTagGroupRel.getPrimaryKeyObj());
				}

				session.save(assetTagGroupRel);
			}
			else {
				assetTagGroupRel = (AssetTagGroupRel)session.merge(
					assetTagGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			AssetTagGroupRelImpl.class, assetTagGroupRelModelImpl, false, true);

		cacheUniqueFindersCache(assetTagGroupRelModelImpl);

		if (isNew) {
			assetTagGroupRel.setNew(false);
		}

		assetTagGroupRel.resetOriginalValues();

		return assetTagGroupRel;
	}

	/**
	 * Returns the asset tag group rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset tag group rel
	 * @return the asset tag group rel
	 * @throws NoSuchTagGroupRelException if a asset tag group rel with the primary key could not be found
	 */
	@Override
	public AssetTagGroupRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTagGroupRelException {

		AssetTagGroupRel assetTagGroupRel = fetchByPrimaryKey(primaryKey);

		if (assetTagGroupRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTagGroupRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetTagGroupRel;
	}

	/**
	 * Returns the asset tag group rel with the primary key or throws a <code>NoSuchTagGroupRelException</code> if it could not be found.
	 *
	 * @param assetTagGroupRelId the primary key of the asset tag group rel
	 * @return the asset tag group rel
	 * @throws NoSuchTagGroupRelException if a asset tag group rel with the primary key could not be found
	 */
	@Override
	public AssetTagGroupRel findByPrimaryKey(long assetTagGroupRelId)
		throws NoSuchTagGroupRelException {

		return findByPrimaryKey((Serializable)assetTagGroupRelId);
	}

	/**
	 * Returns the asset tag group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset tag group rel
	 * @return the asset tag group rel, or <code>null</code> if a asset tag group rel with the primary key could not be found
	 */
	@Override
	public AssetTagGroupRel fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(
				AssetTagGroupRel.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		AssetTagGroupRel assetTagGroupRel =
			(AssetTagGroupRel)EntityCacheUtil.getResult(
				AssetTagGroupRelImpl.class, primaryKey);

		if (assetTagGroupRel != null) {
			return assetTagGroupRel;
		}

		Session session = null;

		try {
			session = openSession();

			assetTagGroupRel = (AssetTagGroupRel)session.get(
				AssetTagGroupRelImpl.class, primaryKey);

			if (assetTagGroupRel != null) {
				cacheResult(assetTagGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return assetTagGroupRel;
	}

	/**
	 * Returns the asset tag group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetTagGroupRelId the primary key of the asset tag group rel
	 * @return the asset tag group rel, or <code>null</code> if a asset tag group rel with the primary key could not be found
	 */
	@Override
	public AssetTagGroupRel fetchByPrimaryKey(long assetTagGroupRelId) {
		return fetchByPrimaryKey((Serializable)assetTagGroupRelId);
	}

	@Override
	public Map<Serializable, AssetTagGroupRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(AssetTagGroupRel.class)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetTagGroupRel> map =
			new HashMap<Serializable, AssetTagGroupRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetTagGroupRel assetTagGroupRel = fetchByPrimaryKey(primaryKey);

			if (assetTagGroupRel != null) {
				map.put(primaryKey, assetTagGroupRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
						AssetTagGroupRel.class, primaryKey)) {

				AssetTagGroupRel assetTagGroupRel =
					(AssetTagGroupRel)EntityCacheUtil.getResult(
						AssetTagGroupRelImpl.class, primaryKey);

				if (assetTagGroupRel == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, assetTagGroupRel);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
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

			for (AssetTagGroupRel assetTagGroupRel :
					(List<AssetTagGroupRel>)query.list()) {

				map.put(assetTagGroupRel.getPrimaryKeyObj(), assetTagGroupRel);

				cacheResult(assetTagGroupRel);
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
	 * Returns all the asset tag group rels.
	 *
	 * @return the asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tag group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @return the range of asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findAll(
		int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset tag group rels
	 */
	@Override
	public List<AssetTagGroupRel> findAll(
		int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetTagGroupRel.class)) {

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

			List<AssetTagGroupRel> list = null;

			if (useFinderCache) {
				list = (List<AssetTagGroupRel>)FinderCacheUtil.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_ASSETTAGGROUPREL);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_ASSETTAGGROUPREL;

					sql = sql.concat(AssetTagGroupRelModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<AssetTagGroupRel>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
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
	}

	/**
	 * Removes all the asset tag group rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetTagGroupRel assetTagGroupRel : findAll()) {
			remove(assetTagGroupRel);
		}
	}

	/**
	 * Returns the number of asset tag group rels.
	 *
	 * @return the number of asset tag group rels
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetTagGroupRel.class)) {

			Long count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_ASSETTAGGROUPREL);

					count = (Long)query.uniqueResult();

					FinderCacheUtil.putResult(
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
		return "assetTagGroupRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETTAGGROUPREL;
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
		return AssetTagGroupRelModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AssetTagGroupRel";
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
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctMergeColumnNames.add("tagId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("assetTagGroupRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});
	}

	/**
	 * Initializes the asset tag group rel persistence.
	 */
	public void afterPropertiesSet() {
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

		_finderPathWithPaginationFindByTagId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByTagId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"tagId"}, true);

		_finderPathWithoutPaginationFindByTagId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTagId",
			new String[] {Long.class.getName()}, new String[] {"tagId"}, true);

		_finderPathCountByTagId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTagId",
			new String[] {Long.class.getName()}, new String[] {"tagId"}, false);

		_finderPathFetchByG_T = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_T",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "tagId"}, true);

		AssetTagGroupRelUtil.setPersistence(this);
	}

	public void destroy() {
		AssetTagGroupRelUtil.setPersistence(null);

		EntityCacheUtil.removeCache(AssetTagGroupRelImpl.class.getName());
	}

	private static final String _SQL_SELECT_ASSETTAGGROUPREL =
		"SELECT assetTagGroupRel FROM AssetTagGroupRel assetTagGroupRel";

	private static final String _SQL_SELECT_ASSETTAGGROUPREL_WHERE =
		"SELECT assetTagGroupRel FROM AssetTagGroupRel assetTagGroupRel WHERE ";

	private static final String _SQL_COUNT_ASSETTAGGROUPREL =
		"SELECT COUNT(assetTagGroupRel) FROM AssetTagGroupRel assetTagGroupRel";

	private static final String _SQL_COUNT_ASSETTAGGROUPREL_WHERE =
		"SELECT COUNT(assetTagGroupRel) FROM AssetTagGroupRel assetTagGroupRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "assetTagGroupRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetTagGroupRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetTagGroupRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetTagGroupRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1651693865