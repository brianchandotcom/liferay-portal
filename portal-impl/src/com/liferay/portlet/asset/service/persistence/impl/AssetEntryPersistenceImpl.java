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

import com.liferay.asset.kernel.exception.NoSuchEntryException;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.asset.kernel.service.persistence.AssetCategoryPersistence;
import com.liferay.asset.kernel.service.persistence.AssetEntryPersistence;
import com.liferay.asset.kernel.service.persistence.AssetTagPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import com.liferay.portlet.asset.model.impl.AssetEntryImpl;
import com.liferay.portlet.asset.model.impl.AssetEntryModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

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

/**
 * The persistence implementation for the asset entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetEntryPersistenceImpl
	extends BasePersistenceImpl<AssetEntry> implements AssetEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetEntryUtil</code> to access the asset entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the asset entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset entries
	 */
	@Override
	public List<AssetEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

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

		List<AssetEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntry assetEntry : list) {
					if (groupId != assetEntry.getGroupId()) {
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

			sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<AssetEntry>)QueryUtil.list(
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
	 * Returns the first asset entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByGroupId_First(
			long groupId, OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByGroupId_First(
		long groupId, OrderByComparator<AssetEntry> orderByComparator) {

		List<AssetEntry> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByGroupId_Last(
			long groupId, OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByGroupId_Last(groupId, orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<AssetEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<AssetEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entries before and after the current asset entry in the ordered set where groupId = &#63;.
	 *
	 * @param entryId the primary key of the current asset entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry
	 * @throws NoSuchEntryException if a asset entry with the primary key could not be found
	 */
	@Override
	public AssetEntry[] findByGroupId_PrevAndNext(
			long entryId, long groupId,
			OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AssetEntry[] array = new AssetEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, assetEntry, groupId, orderByComparator, true);

			array[1] = assetEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, assetEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntry getByGroupId_PrevAndNext(
		Session session, AssetEntry assetEntry, long groupId,
		OrderByComparator<AssetEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

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
			sb.append(AssetEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(assetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (AssetEntry assetEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetEntry);
		}
	}

	/**
	 * Returns the number of asset entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId;

			finderArgs = new Object[] {groupId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"assetEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the asset entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching asset entries
	 */
	@Override
	public List<AssetEntry> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<AssetEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntry assetEntry : list) {
					if (companyId != assetEntry.getCompanyId()) {
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

			sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<AssetEntry>)QueryUtil.list(
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
	 * Returns the first asset entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByCompanyId_First(
			long companyId, OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<AssetEntry> orderByComparator) {

		List<AssetEntry> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByCompanyId_Last(
			long companyId, OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByCompanyId_Last(
		long companyId, OrderByComparator<AssetEntry> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<AssetEntry> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entries before and after the current asset entry in the ordered set where companyId = &#63;.
	 *
	 * @param entryId the primary key of the current asset entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry
	 * @throws NoSuchEntryException if a asset entry with the primary key could not be found
	 */
	@Override
	public AssetEntry[] findByCompanyId_PrevAndNext(
			long entryId, long companyId,
			OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AssetEntry[] array = new AssetEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, assetEntry, companyId, orderByComparator, true);

			array[1] = assetEntry;

			array[2] = getByCompanyId_PrevAndNext(
				session, assetEntry, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntry getByCompanyId_PrevAndNext(
		Session session, AssetEntry assetEntry, long companyId,
		OrderByComparator<AssetEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			sb.append(AssetEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(assetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (AssetEntry assetEntry :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetEntry);
		}
	}

	/**
	 * Returns the number of asset entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching asset entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCompanyId;

			finderArgs = new Object[] {companyId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"assetEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByVisible;
	private FinderPath _finderPathWithoutPaginationFindByVisible;
	private FinderPath _finderPathCountByVisible;

	/**
	 * Returns all the asset entries where visible = &#63;.
	 *
	 * @param visible the visible
	 * @return the matching asset entries
	 */
	@Override
	public List<AssetEntry> findByVisible(boolean visible) {
		return findByVisible(
			visible, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entries where visible = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param visible the visible
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByVisible(boolean visible, int start, int end) {
		return findByVisible(visible, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entries where visible = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param visible the visible
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByVisible(
		boolean visible, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {

		return findByVisible(visible, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entries where visible = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param visible the visible
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByVisible(
		boolean visible, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByVisible;
				finderArgs = new Object[] {visible};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByVisible;
			finderArgs = new Object[] {visible, start, end, orderByComparator};
		}

		List<AssetEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntry assetEntry : list) {
					if (visible != assetEntry.isVisible()) {
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

			sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_VISIBLE_VISIBLE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(visible);

				list = (List<AssetEntry>)QueryUtil.list(
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
	 * Returns the first asset entry in the ordered set where visible = &#63;.
	 *
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByVisible_First(
			boolean visible, OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByVisible_First(
			visible, orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("visible=");
		sb.append(visible);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset entry in the ordered set where visible = &#63;.
	 *
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByVisible_First(
		boolean visible, OrderByComparator<AssetEntry> orderByComparator) {

		List<AssetEntry> list = findByVisible(visible, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry in the ordered set where visible = &#63;.
	 *
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByVisible_Last(
			boolean visible, OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByVisible_Last(visible, orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("visible=");
		sb.append(visible);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset entry in the ordered set where visible = &#63;.
	 *
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByVisible_Last(
		boolean visible, OrderByComparator<AssetEntry> orderByComparator) {

		int count = countByVisible(visible);

		if (count == 0) {
			return null;
		}

		List<AssetEntry> list = findByVisible(
			visible, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entries before and after the current asset entry in the ordered set where visible = &#63;.
	 *
	 * @param entryId the primary key of the current asset entry
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry
	 * @throws NoSuchEntryException if a asset entry with the primary key could not be found
	 */
	@Override
	public AssetEntry[] findByVisible_PrevAndNext(
			long entryId, boolean visible,
			OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AssetEntry[] array = new AssetEntryImpl[3];

			array[0] = getByVisible_PrevAndNext(
				session, assetEntry, visible, orderByComparator, true);

			array[1] = assetEntry;

			array[2] = getByVisible_PrevAndNext(
				session, assetEntry, visible, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntry getByVisible_PrevAndNext(
		Session session, AssetEntry assetEntry, boolean visible,
		OrderByComparator<AssetEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

		sb.append(_FINDER_COLUMN_VISIBLE_VISIBLE_2);

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
			sb.append(AssetEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(visible);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(assetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entries where visible = &#63; from the database.
	 *
	 * @param visible the visible
	 */
	@Override
	public void removeByVisible(boolean visible) {
		for (AssetEntry assetEntry :
				findByVisible(
					visible, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetEntry);
		}
	}

	/**
	 * Returns the number of asset entries where visible = &#63;.
	 *
	 * @param visible the visible
	 * @return the number of matching asset entries
	 */
	@Override
	public int countByVisible(boolean visible) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByVisible;

			finderArgs = new Object[] {visible};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_VISIBLE_VISIBLE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(visible);

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

	private static final String _FINDER_COLUMN_VISIBLE_VISIBLE_2 =
		"assetEntry.visible = ?";

	private FinderPath _finderPathWithPaginationFindByPublishDate;
	private FinderPath _finderPathWithoutPaginationFindByPublishDate;
	private FinderPath _finderPathCountByPublishDate;

	/**
	 * Returns all the asset entries where publishDate = &#63;.
	 *
	 * @param publishDate the publish date
	 * @return the matching asset entries
	 */
	@Override
	public List<AssetEntry> findByPublishDate(Date publishDate) {
		return findByPublishDate(
			publishDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entries where publishDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param publishDate the publish date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByPublishDate(
		Date publishDate, int start, int end) {

		return findByPublishDate(publishDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entries where publishDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param publishDate the publish date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByPublishDate(
		Date publishDate, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {

		return findByPublishDate(
			publishDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entries where publishDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param publishDate the publish date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByPublishDate(
		Date publishDate, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByPublishDate;
				finderArgs = new Object[] {_getTime(publishDate)};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByPublishDate;
			finderArgs = new Object[] {
				_getTime(publishDate), start, end, orderByComparator
			};
		}

		List<AssetEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntry assetEntry : list) {
					if (!Objects.equals(
							publishDate, assetEntry.getPublishDate())) {

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

			sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

			boolean bindPublishDate = false;

			if (publishDate == null) {
				sb.append(_FINDER_COLUMN_PUBLISHDATE_PUBLISHDATE_1);
			}
			else {
				bindPublishDate = true;

				sb.append(_FINDER_COLUMN_PUBLISHDATE_PUBLISHDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindPublishDate) {
					queryPos.add(new Timestamp(publishDate.getTime()));
				}

				list = (List<AssetEntry>)QueryUtil.list(
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
	 * Returns the first asset entry in the ordered set where publishDate = &#63;.
	 *
	 * @param publishDate the publish date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByPublishDate_First(
			Date publishDate, OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByPublishDate_First(
			publishDate, orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("publishDate=");
		sb.append(publishDate);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset entry in the ordered set where publishDate = &#63;.
	 *
	 * @param publishDate the publish date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByPublishDate_First(
		Date publishDate, OrderByComparator<AssetEntry> orderByComparator) {

		List<AssetEntry> list = findByPublishDate(
			publishDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry in the ordered set where publishDate = &#63;.
	 *
	 * @param publishDate the publish date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByPublishDate_Last(
			Date publishDate, OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByPublishDate_Last(
			publishDate, orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("publishDate=");
		sb.append(publishDate);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset entry in the ordered set where publishDate = &#63;.
	 *
	 * @param publishDate the publish date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByPublishDate_Last(
		Date publishDate, OrderByComparator<AssetEntry> orderByComparator) {

		int count = countByPublishDate(publishDate);

		if (count == 0) {
			return null;
		}

		List<AssetEntry> list = findByPublishDate(
			publishDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entries before and after the current asset entry in the ordered set where publishDate = &#63;.
	 *
	 * @param entryId the primary key of the current asset entry
	 * @param publishDate the publish date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry
	 * @throws NoSuchEntryException if a asset entry with the primary key could not be found
	 */
	@Override
	public AssetEntry[] findByPublishDate_PrevAndNext(
			long entryId, Date publishDate,
			OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AssetEntry[] array = new AssetEntryImpl[3];

			array[0] = getByPublishDate_PrevAndNext(
				session, assetEntry, publishDate, orderByComparator, true);

			array[1] = assetEntry;

			array[2] = getByPublishDate_PrevAndNext(
				session, assetEntry, publishDate, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntry getByPublishDate_PrevAndNext(
		Session session, AssetEntry assetEntry, Date publishDate,
		OrderByComparator<AssetEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

		boolean bindPublishDate = false;

		if (publishDate == null) {
			sb.append(_FINDER_COLUMN_PUBLISHDATE_PUBLISHDATE_1);
		}
		else {
			bindPublishDate = true;

			sb.append(_FINDER_COLUMN_PUBLISHDATE_PUBLISHDATE_2);
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
			sb.append(AssetEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindPublishDate) {
			queryPos.add(new Timestamp(publishDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(assetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entries where publishDate = &#63; from the database.
	 *
	 * @param publishDate the publish date
	 */
	@Override
	public void removeByPublishDate(Date publishDate) {
		for (AssetEntry assetEntry :
				findByPublishDate(
					publishDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetEntry);
		}
	}

	/**
	 * Returns the number of asset entries where publishDate = &#63;.
	 *
	 * @param publishDate the publish date
	 * @return the number of matching asset entries
	 */
	@Override
	public int countByPublishDate(Date publishDate) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByPublishDate;

			finderArgs = new Object[] {_getTime(publishDate)};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETENTRY_WHERE);

			boolean bindPublishDate = false;

			if (publishDate == null) {
				sb.append(_FINDER_COLUMN_PUBLISHDATE_PUBLISHDATE_1);
			}
			else {
				bindPublishDate = true;

				sb.append(_FINDER_COLUMN_PUBLISHDATE_PUBLISHDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindPublishDate) {
					queryPos.add(new Timestamp(publishDate.getTime()));
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

	private static final String _FINDER_COLUMN_PUBLISHDATE_PUBLISHDATE_1 =
		"assetEntry.publishDate IS NULL";

	private static final String _FINDER_COLUMN_PUBLISHDATE_PUBLISHDATE_2 =
		"assetEntry.publishDate = ?";

	private FinderPath _finderPathWithPaginationFindByExpirationDate;
	private FinderPath _finderPathWithoutPaginationFindByExpirationDate;
	private FinderPath _finderPathCountByExpirationDate;

	/**
	 * Returns all the asset entries where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the matching asset entries
	 */
	@Override
	public List<AssetEntry> findByExpirationDate(Date expirationDate) {
		return findByExpirationDate(
			expirationDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entries where expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByExpirationDate(
		Date expirationDate, int start, int end) {

		return findByExpirationDate(expirationDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entries where expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {

		return findByExpirationDate(
			expirationDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entries where expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByExpirationDate;
				finderArgs = new Object[] {_getTime(expirationDate)};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByExpirationDate;
			finderArgs = new Object[] {
				_getTime(expirationDate), start, end, orderByComparator
			};
		}

		List<AssetEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntry assetEntry : list) {
					if (!Objects.equals(
							expirationDate, assetEntry.getExpirationDate())) {

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

			sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
				}

				list = (List<AssetEntry>)QueryUtil.list(
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
	 * Returns the first asset entry in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByExpirationDate_First(
			Date expirationDate,
			OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByExpirationDate_First(
			expirationDate, orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate=");
		sb.append(expirationDate);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset entry in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByExpirationDate_First(
		Date expirationDate, OrderByComparator<AssetEntry> orderByComparator) {

		List<AssetEntry> list = findByExpirationDate(
			expirationDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByExpirationDate_Last(
			Date expirationDate,
			OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByExpirationDate_Last(
			expirationDate, orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate=");
		sb.append(expirationDate);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset entry in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByExpirationDate_Last(
		Date expirationDate, OrderByComparator<AssetEntry> orderByComparator) {

		int count = countByExpirationDate(expirationDate);

		if (count == 0) {
			return null;
		}

		List<AssetEntry> list = findByExpirationDate(
			expirationDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entries before and after the current asset entry in the ordered set where expirationDate = &#63;.
	 *
	 * @param entryId the primary key of the current asset entry
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry
	 * @throws NoSuchEntryException if a asset entry with the primary key could not be found
	 */
	@Override
	public AssetEntry[] findByExpirationDate_PrevAndNext(
			long entryId, Date expirationDate,
			OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AssetEntry[] array = new AssetEntryImpl[3];

			array[0] = getByExpirationDate_PrevAndNext(
				session, assetEntry, expirationDate, orderByComparator, true);

			array[1] = assetEntry;

			array[2] = getByExpirationDate_PrevAndNext(
				session, assetEntry, expirationDate, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntry getByExpirationDate_PrevAndNext(
		Session session, AssetEntry assetEntry, Date expirationDate,
		OrderByComparator<AssetEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

		boolean bindExpirationDate = false;

		if (expirationDate == null) {
			sb.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1);
		}
		else {
			bindExpirationDate = true;

			sb.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2);
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
			sb.append(AssetEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindExpirationDate) {
			queryPos.add(new Timestamp(expirationDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(assetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entries where expirationDate = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 */
	@Override
	public void removeByExpirationDate(Date expirationDate) {
		for (AssetEntry assetEntry :
				findByExpirationDate(
					expirationDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetEntry);
		}
	}

	/**
	 * Returns the number of asset entries where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the number of matching asset entries
	 */
	@Override
	public int countByExpirationDate(Date expirationDate) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByExpirationDate;

			finderArgs = new Object[] {_getTime(expirationDate)};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETENTRY_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
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

	private static final String _FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1 =
		"assetEntry.expirationDate IS NULL";

	private static final String _FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2 =
		"assetEntry.expirationDate = ?";

	private FinderPath _finderPathWithPaginationFindByLayoutUuid;
	private FinderPath _finderPathWithoutPaginationFindByLayoutUuid;
	private FinderPath _finderPathCountByLayoutUuid;

	/**
	 * Returns all the asset entries where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @return the matching asset entries
	 */
	@Override
	public List<AssetEntry> findByLayoutUuid(String layoutUuid) {
		return findByLayoutUuid(
			layoutUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entries where layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByLayoutUuid(
		String layoutUuid, int start, int end) {

		return findByLayoutUuid(layoutUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entries where layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByLayoutUuid(
		String layoutUuid, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {

		return findByLayoutUuid(
			layoutUuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entries where layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByLayoutUuid(
		String layoutUuid, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		layoutUuid = Objects.toString(layoutUuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByLayoutUuid;
				finderArgs = new Object[] {layoutUuid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByLayoutUuid;
			finderArgs = new Object[] {
				layoutUuid, start, end, orderByComparator
			};
		}

		List<AssetEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntry assetEntry : list) {
					if (!layoutUuid.equals(assetEntry.getLayoutUuid())) {
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

			sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

			boolean bindLayoutUuid = false;

			if (layoutUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_3);
			}
			else {
				bindLayoutUuid = true;

				sb.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindLayoutUuid) {
					queryPos.add(layoutUuid);
				}

				list = (List<AssetEntry>)QueryUtil.list(
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
	 * Returns the first asset entry in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByLayoutUuid_First(
			String layoutUuid, OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByLayoutUuid_First(
			layoutUuid, orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("layoutUuid=");
		sb.append(layoutUuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset entry in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByLayoutUuid_First(
		String layoutUuid, OrderByComparator<AssetEntry> orderByComparator) {

		List<AssetEntry> list = findByLayoutUuid(
			layoutUuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByLayoutUuid_Last(
			String layoutUuid, OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByLayoutUuid_Last(
			layoutUuid, orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("layoutUuid=");
		sb.append(layoutUuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset entry in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByLayoutUuid_Last(
		String layoutUuid, OrderByComparator<AssetEntry> orderByComparator) {

		int count = countByLayoutUuid(layoutUuid);

		if (count == 0) {
			return null;
		}

		List<AssetEntry> list = findByLayoutUuid(
			layoutUuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entries before and after the current asset entry in the ordered set where layoutUuid = &#63;.
	 *
	 * @param entryId the primary key of the current asset entry
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry
	 * @throws NoSuchEntryException if a asset entry with the primary key could not be found
	 */
	@Override
	public AssetEntry[] findByLayoutUuid_PrevAndNext(
			long entryId, String layoutUuid,
			OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		layoutUuid = Objects.toString(layoutUuid, "");

		AssetEntry assetEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AssetEntry[] array = new AssetEntryImpl[3];

			array[0] = getByLayoutUuid_PrevAndNext(
				session, assetEntry, layoutUuid, orderByComparator, true);

			array[1] = assetEntry;

			array[2] = getByLayoutUuid_PrevAndNext(
				session, assetEntry, layoutUuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntry getByLayoutUuid_PrevAndNext(
		Session session, AssetEntry assetEntry, String layoutUuid,
		OrderByComparator<AssetEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

		boolean bindLayoutUuid = false;

		if (layoutUuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			sb.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_2);
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
			sb.append(AssetEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindLayoutUuid) {
			queryPos.add(layoutUuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(assetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entries where layoutUuid = &#63; from the database.
	 *
	 * @param layoutUuid the layout uuid
	 */
	@Override
	public void removeByLayoutUuid(String layoutUuid) {
		for (AssetEntry assetEntry :
				findByLayoutUuid(
					layoutUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetEntry);
		}
	}

	/**
	 * Returns the number of asset entries where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @return the number of matching asset entries
	 */
	@Override
	public int countByLayoutUuid(String layoutUuid) {
		layoutUuid = Objects.toString(layoutUuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByLayoutUuid;

			finderArgs = new Object[] {layoutUuid};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETENTRY_WHERE);

			boolean bindLayoutUuid = false;

			if (layoutUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_3);
			}
			else {
				bindLayoutUuid = true;

				sb.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindLayoutUuid) {
					queryPos.add(layoutUuid);
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

	private static final String _FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_2 =
		"assetEntry.layoutUuid = ?";

	private static final String _FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_3 =
		"(assetEntry.layoutUuid IS NULL OR assetEntry.layoutUuid = '')";

	private FinderPath _finderPathFetchByG_CU;
	private FinderPath _finderPathCountByG_CU;

	/**
	 * Returns the asset entry where groupId = &#63; and classUuid = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classUuid the class uuid
	 * @return the matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByG_CU(long groupId, String classUuid)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByG_CU(groupId, classUuid);

		if (assetEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", classUuid=");
			sb.append(classUuid);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return assetEntry;
	}

	/**
	 * Returns the asset entry where groupId = &#63; and classUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classUuid the class uuid
	 * @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByG_CU(long groupId, String classUuid) {
		return fetchByG_CU(groupId, classUuid, true);
	}

	/**
	 * Returns the asset entry where groupId = &#63; and classUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classUuid the class uuid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByG_CU(
		long groupId, String classUuid, boolean useFinderCache) {

		classUuid = Objects.toString(classUuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, classUuid};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByG_CU, finderArgs, this);
		}

		if (result instanceof AssetEntry) {
			AssetEntry assetEntry = (AssetEntry)result;

			if ((groupId != assetEntry.getGroupId()) ||
				!Objects.equals(classUuid, assetEntry.getClassUuid())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_CU_GROUPID_2);

			boolean bindClassUuid = false;

			if (classUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_CU_CLASSUUID_3);
			}
			else {
				bindClassUuid = true;

				sb.append(_FINDER_COLUMN_G_CU_CLASSUUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindClassUuid) {
					queryPos.add(classUuid);
				}

				List<AssetEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByG_CU, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!productionMode || !useFinderCache) {
								finderArgs = new Object[] {groupId, classUuid};
							}

							_log.warn(
								"AssetEntryPersistenceImpl.fetchByG_CU(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AssetEntry assetEntry = list.get(0);

					result = assetEntry;

					cacheResult(assetEntry);
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
			return (AssetEntry)result;
		}
	}

	/**
	 * Removes the asset entry where groupId = &#63; and classUuid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classUuid the class uuid
	 * @return the asset entry that was removed
	 */
	@Override
	public AssetEntry removeByG_CU(long groupId, String classUuid)
		throws NoSuchEntryException {

		AssetEntry assetEntry = findByG_CU(groupId, classUuid);

		return remove(assetEntry);
	}

	/**
	 * Returns the number of asset entries where groupId = &#63; and classUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classUuid the class uuid
	 * @return the number of matching asset entries
	 */
	@Override
	public int countByG_CU(long groupId, String classUuid) {
		classUuid = Objects.toString(classUuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_CU;

			finderArgs = new Object[] {groupId, classUuid};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_CU_GROUPID_2);

			boolean bindClassUuid = false;

			if (classUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_CU_CLASSUUID_3);
			}
			else {
				bindClassUuid = true;

				sb.append(_FINDER_COLUMN_G_CU_CLASSUUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindClassUuid) {
					queryPos.add(classUuid);
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

	private static final String _FINDER_COLUMN_G_CU_GROUPID_2 =
		"assetEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_CU_CLASSUUID_2 =
		"assetEntry.classUuid = ?";

	private static final String _FINDER_COLUMN_G_CU_CLASSUUID_3 =
		"(assetEntry.classUuid IS NULL OR assetEntry.classUuid = '')";

	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the asset entry where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByC_C(long classNameId, long classPK)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByC_C(classNameId, classPK);

		if (assetEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return assetEntry;
	}

	/**
	 * Returns the asset entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByC_C(long classNameId, long classPK) {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the asset entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {classNameId, classPK};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_C, finderArgs, this);
		}

		if (result instanceof AssetEntry) {
			AssetEntry assetEntry = (AssetEntry)result;

			if ((classNameId != assetEntry.getClassNameId()) ||
				(classPK != assetEntry.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				List<AssetEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					AssetEntry assetEntry = list.get(0);

					result = assetEntry;

					cacheResult(assetEntry);
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
			return (AssetEntry)result;
		}
	}

	/**
	 * Removes the asset entry where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the asset entry that was removed
	 */
	@Override
	public AssetEntry removeByC_C(long classNameId, long classPK)
		throws NoSuchEntryException {

		AssetEntry assetEntry = findByC_C(classNameId, classPK);

		return remove(assetEntry);
	}

	/**
	 * Returns the number of asset entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching asset entries
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C;

			finderArgs = new Object[] {classNameId, classPK};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"assetEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"assetEntry.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_V;
	private FinderPath _finderPathWithoutPaginationFindByG_C_V;
	private FinderPath _finderPathCountByG_C_V;

	/**
	 * Returns all the asset entries where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @return the matching asset entries
	 */
	@Override
	public List<AssetEntry> findByG_C_V(
		long groupId, long classNameId, boolean visible) {

		return findByG_C_V(
			groupId, classNameId, visible, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset entries where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByG_C_V(
		long groupId, long classNameId, boolean visible, int start, int end) {

		return findByG_C_V(groupId, classNameId, visible, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entries where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByG_C_V(
		long groupId, long classNameId, boolean visible, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {

		return findByG_C_V(
			groupId, classNameId, visible, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entries where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByG_C_V(
		long groupId, long classNameId, boolean visible, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_V;
				finderArgs = new Object[] {groupId, classNameId, visible};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_V;
			finderArgs = new Object[] {
				groupId, classNameId, visible, start, end, orderByComparator
			};
		}

		List<AssetEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntry assetEntry : list) {
					if ((groupId != assetEntry.getGroupId()) ||
						(classNameId != assetEntry.getClassNameId()) ||
						(visible != assetEntry.isVisible())) {

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

			sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_V_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_V_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_V_VISIBLE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(visible);

				list = (List<AssetEntry>)QueryUtil.list(
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
	 * Returns the first asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByG_C_V_First(
			long groupId, long classNameId, boolean visible,
			OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByG_C_V_First(
			groupId, classNameId, visible, orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", visible=");
		sb.append(visible);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByG_C_V_First(
		long groupId, long classNameId, boolean visible,
		OrderByComparator<AssetEntry> orderByComparator) {

		List<AssetEntry> list = findByG_C_V(
			groupId, classNameId, visible, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByG_C_V_Last(
			long groupId, long classNameId, boolean visible,
			OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByG_C_V_Last(
			groupId, classNameId, visible, orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", visible=");
		sb.append(visible);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByG_C_V_Last(
		long groupId, long classNameId, boolean visible,
		OrderByComparator<AssetEntry> orderByComparator) {

		int count = countByG_C_V(groupId, classNameId, visible);

		if (count == 0) {
			return null;
		}

		List<AssetEntry> list = findByG_C_V(
			groupId, classNameId, visible, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entries before and after the current asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * @param entryId the primary key of the current asset entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry
	 * @throws NoSuchEntryException if a asset entry with the primary key could not be found
	 */
	@Override
	public AssetEntry[] findByG_C_V_PrevAndNext(
			long entryId, long groupId, long classNameId, boolean visible,
			OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AssetEntry[] array = new AssetEntryImpl[3];

			array[0] = getByG_C_V_PrevAndNext(
				session, assetEntry, groupId, classNameId, visible,
				orderByComparator, true);

			array[1] = assetEntry;

			array[2] = getByG_C_V_PrevAndNext(
				session, assetEntry, groupId, classNameId, visible,
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

	protected AssetEntry getByG_C_V_PrevAndNext(
		Session session, AssetEntry assetEntry, long groupId, long classNameId,
		boolean visible, OrderByComparator<AssetEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_V_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_V_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_V_VISIBLE_2);

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
			sb.append(AssetEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(visible);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(assetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entries where groupId = &#63; and classNameId = &#63; and visible = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 */
	@Override
	public void removeByG_C_V(long groupId, long classNameId, boolean visible) {
		for (AssetEntry assetEntry :
				findByG_C_V(
					groupId, classNameId, visible, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(assetEntry);
		}
	}

	/**
	 * Returns the number of asset entries where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @return the number of matching asset entries
	 */
	@Override
	public int countByG_C_V(long groupId, long classNameId, boolean visible) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_V;

			finderArgs = new Object[] {groupId, classNameId, visible};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ASSETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_V_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_V_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_V_VISIBLE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(visible);

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

	private static final String _FINDER_COLUMN_G_C_V_GROUPID_2 =
		"assetEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_V_CLASSNAMEID_2 =
		"assetEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_V_VISIBLE_2 =
		"assetEntry.visible = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_P_E;
	private FinderPath _finderPathWithoutPaginationFindByG_C_P_E;
	private FinderPath _finderPathCountByG_C_P_E;

	/**
	 * Returns all the asset entries where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @return the matching asset entries
	 */
	@Override
	public List<AssetEntry> findByG_C_P_E(
		long groupId, long classNameId, Date publishDate, Date expirationDate) {

		return findByG_C_P_E(
			groupId, classNameId, publishDate, expirationDate,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entries where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByG_C_P_E(
		long groupId, long classNameId, Date publishDate, Date expirationDate,
		int start, int end) {

		return findByG_C_P_E(
			groupId, classNameId, publishDate, expirationDate, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the asset entries where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByG_C_P_E(
		long groupId, long classNameId, Date publishDate, Date expirationDate,
		int start, int end, OrderByComparator<AssetEntry> orderByComparator) {

		return findByG_C_P_E(
			groupId, classNameId, publishDate, expirationDate, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entries where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	@Override
	public List<AssetEntry> findByG_C_P_E(
		long groupId, long classNameId, Date publishDate, Date expirationDate,
		int start, int end, OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_P_E;
				finderArgs = new Object[] {
					groupId, classNameId, _getTime(publishDate),
					_getTime(expirationDate)
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_P_E;
			finderArgs = new Object[] {
				groupId, classNameId, _getTime(publishDate),
				_getTime(expirationDate), start, end, orderByComparator
			};
		}

		List<AssetEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntry assetEntry : list) {
					if ((groupId != assetEntry.getGroupId()) ||
						(classNameId != assetEntry.getClassNameId()) ||
						!Objects.equals(
							publishDate, assetEntry.getPublishDate()) ||
						!Objects.equals(
							expirationDate, assetEntry.getExpirationDate())) {

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

			sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_P_E_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_P_E_CLASSNAMEID_2);

			boolean bindPublishDate = false;

			if (publishDate == null) {
				sb.append(_FINDER_COLUMN_G_C_P_E_PUBLISHDATE_1);
			}
			else {
				bindPublishDate = true;

				sb.append(_FINDER_COLUMN_G_C_P_E_PUBLISHDATE_2);
			}

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_G_C_P_E_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_G_C_P_E_EXPIRATIONDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				if (bindPublishDate) {
					queryPos.add(new Timestamp(publishDate.getTime()));
				}

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
				}

				list = (List<AssetEntry>)QueryUtil.list(
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
	 * Returns the first asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByG_C_P_E_First(
			long groupId, long classNameId, Date publishDate,
			Date expirationDate,
			OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByG_C_P_E_First(
			groupId, classNameId, publishDate, expirationDate,
			orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", publishDate=");
		sb.append(publishDate);

		sb.append(", expirationDate=");
		sb.append(expirationDate);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByG_C_P_E_First(
		long groupId, long classNameId, Date publishDate, Date expirationDate,
		OrderByComparator<AssetEntry> orderByComparator) {

		List<AssetEntry> list = findByG_C_P_E(
			groupId, classNameId, publishDate, expirationDate, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry findByG_C_P_E_Last(
			long groupId, long classNameId, Date publishDate,
			Date expirationDate,
			OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByG_C_P_E_Last(
			groupId, classNameId, publishDate, expirationDate,
			orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", publishDate=");
		sb.append(publishDate);

		sb.append(", expirationDate=");
		sb.append(expirationDate);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	@Override
	public AssetEntry fetchByG_C_P_E_Last(
		long groupId, long classNameId, Date publishDate, Date expirationDate,
		OrderByComparator<AssetEntry> orderByComparator) {

		int count = countByG_C_P_E(
			groupId, classNameId, publishDate, expirationDate);

		if (count == 0) {
			return null;
		}

		List<AssetEntry> list = findByG_C_P_E(
			groupId, classNameId, publishDate, expirationDate, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entries before and after the current asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * @param entryId the primary key of the current asset entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry
	 * @throws NoSuchEntryException if a asset entry with the primary key could not be found
	 */
	@Override
	public AssetEntry[] findByG_C_P_E_PrevAndNext(
			long entryId, long groupId, long classNameId, Date publishDate,
			Date expirationDate,
			OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetEntry assetEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AssetEntry[] array = new AssetEntryImpl[3];

			array[0] = getByG_C_P_E_PrevAndNext(
				session, assetEntry, groupId, classNameId, publishDate,
				expirationDate, orderByComparator, true);

			array[1] = assetEntry;

			array[2] = getByG_C_P_E_PrevAndNext(
				session, assetEntry, groupId, classNameId, publishDate,
				expirationDate, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntry getByG_C_P_E_PrevAndNext(
		Session session, AssetEntry assetEntry, long groupId, long classNameId,
		Date publishDate, Date expirationDate,
		OrderByComparator<AssetEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_ASSETENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_P_E_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_P_E_CLASSNAMEID_2);

		boolean bindPublishDate = false;

		if (publishDate == null) {
			sb.append(_FINDER_COLUMN_G_C_P_E_PUBLISHDATE_1);
		}
		else {
			bindPublishDate = true;

			sb.append(_FINDER_COLUMN_G_C_P_E_PUBLISHDATE_2);
		}

		boolean bindExpirationDate = false;

		if (expirationDate == null) {
			sb.append(_FINDER_COLUMN_G_C_P_E_EXPIRATIONDATE_1);
		}
		else {
			bindExpirationDate = true;

			sb.append(_FINDER_COLUMN_G_C_P_E_EXPIRATIONDATE_2);
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
			sb.append(AssetEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		if (bindPublishDate) {
			queryPos.add(new Timestamp(publishDate.getTime()));
		}

		if (bindExpirationDate) {
			queryPos.add(new Timestamp(expirationDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(assetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entries where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 */
	@Override
	public void removeByG_C_P_E(
		long groupId, long classNameId, Date publishDate, Date expirationDate) {

		for (AssetEntry assetEntry :
				findByG_C_P_E(
					groupId, classNameId, publishDate, expirationDate,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetEntry);
		}
	}

	/**
	 * Returns the number of asset entries where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @return the number of matching asset entries
	 */
	@Override
	public int countByG_C_P_E(
		long groupId, long classNameId, Date publishDate, Date expirationDate) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_P_E;

			finderArgs = new Object[] {
				groupId, classNameId, _getTime(publishDate),
				_getTime(expirationDate)
			};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_ASSETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_P_E_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_P_E_CLASSNAMEID_2);

			boolean bindPublishDate = false;

			if (publishDate == null) {
				sb.append(_FINDER_COLUMN_G_C_P_E_PUBLISHDATE_1);
			}
			else {
				bindPublishDate = true;

				sb.append(_FINDER_COLUMN_G_C_P_E_PUBLISHDATE_2);
			}

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_G_C_P_E_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_G_C_P_E_EXPIRATIONDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				if (bindPublishDate) {
					queryPos.add(new Timestamp(publishDate.getTime()));
				}

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
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

	private static final String _FINDER_COLUMN_G_C_P_E_GROUPID_2 =
		"assetEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_P_E_CLASSNAMEID_2 =
		"assetEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_P_E_PUBLISHDATE_1 =
		"assetEntry.publishDate IS NULL AND ";

	private static final String _FINDER_COLUMN_G_C_P_E_PUBLISHDATE_2 =
		"assetEntry.publishDate = ? AND ";

	private static final String _FINDER_COLUMN_G_C_P_E_EXPIRATIONDATE_1 =
		"assetEntry.expirationDate IS NULL";

	private static final String _FINDER_COLUMN_G_C_P_E_EXPIRATIONDATE_2 =
		"assetEntry.expirationDate = ?";

	public AssetEntryPersistenceImpl() {
		setModelClass(AssetEntry.class);

		setModelImplClass(AssetEntryImpl.class);
		setModelPKClass(long.class);

		setTable(AssetEntryTable.INSTANCE);
	}

	/**
	 * Caches the asset entry in the entity cache if it is enabled.
	 *
	 * @param assetEntry the asset entry
	 */
	@Override
	public void cacheResult(AssetEntry assetEntry) {
		if (assetEntry.getCtCollectionId() != 0) {
			assetEntry.resetOriginalValues();

			return;
		}

		EntityCacheUtil.putResult(
			AssetEntryImpl.class, assetEntry.getPrimaryKey(), assetEntry);

		FinderCacheUtil.putResult(
			_finderPathFetchByG_CU,
			new Object[] {assetEntry.getGroupId(), assetEntry.getClassUuid()},
			assetEntry);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_C,
			new Object[] {assetEntry.getClassNameId(), assetEntry.getClassPK()},
			assetEntry);

		assetEntry.resetOriginalValues();
	}

	/**
	 * Caches the asset entries in the entity cache if it is enabled.
	 *
	 * @param assetEntries the asset entries
	 */
	@Override
	public void cacheResult(List<AssetEntry> assetEntries) {
		for (AssetEntry assetEntry : assetEntries) {
			if (assetEntry.getCtCollectionId() != 0) {
				assetEntry.resetOriginalValues();

				continue;
			}

			if (EntityCacheUtil.getResult(
					AssetEntryImpl.class, assetEntry.getPrimaryKey()) == null) {

				cacheResult(assetEntry);
			}
			else {
				assetEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(AssetEntryImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetEntry assetEntry) {
		EntityCacheUtil.removeResult(
			AssetEntryImpl.class, assetEntry.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AssetEntryModelImpl)assetEntry, true);
	}

	@Override
	public void clearCache(List<AssetEntry> assetEntries) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetEntry assetEntry : assetEntries) {
			EntityCacheUtil.removeResult(
				AssetEntryImpl.class, assetEntry.getPrimaryKey());

			clearUniqueFindersCache((AssetEntryModelImpl)assetEntry, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(AssetEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetEntryModelImpl assetEntryModelImpl) {

		Object[] args = new Object[] {
			assetEntryModelImpl.getGroupId(), assetEntryModelImpl.getClassUuid()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByG_CU, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByG_CU, args, assetEntryModelImpl, false);

		args = new Object[] {
			assetEntryModelImpl.getClassNameId(),
			assetEntryModelImpl.getClassPK()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByC_C, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByC_C, args, assetEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AssetEntryModelImpl assetEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetEntryModelImpl.getGroupId(),
				assetEntryModelImpl.getClassUuid()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_CU, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_CU, args);
		}

		if ((assetEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_CU.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetEntryModelImpl.getOriginalGroupId(),
				assetEntryModelImpl.getOriginalClassUuid()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_CU, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_CU, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetEntryModelImpl.getClassNameId(),
				assetEntryModelImpl.getClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_C, args);
		}

		if ((assetEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_C.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetEntryModelImpl.getOriginalClassNameId(),
				assetEntryModelImpl.getOriginalClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_C, args);
		}
	}

	/**
	 * Creates a new asset entry with the primary key. Does not add the asset entry to the database.
	 *
	 * @param entryId the primary key for the new asset entry
	 * @return the new asset entry
	 */
	@Override
	public AssetEntry create(long entryId) {
		AssetEntry assetEntry = new AssetEntryImpl();

		assetEntry.setNew(true);
		assetEntry.setPrimaryKey(entryId);

		assetEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return assetEntry;
	}

	/**
	 * Removes the asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the asset entry
	 * @return the asset entry that was removed
	 * @throws NoSuchEntryException if a asset entry with the primary key could not be found
	 */
	@Override
	public AssetEntry remove(long entryId) throws NoSuchEntryException {
		return remove((Serializable)entryId);
	}

	/**
	 * Removes the asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset entry
	 * @return the asset entry that was removed
	 * @throws NoSuchEntryException if a asset entry with the primary key could not be found
	 */
	@Override
	public AssetEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			AssetEntry assetEntry = (AssetEntry)session.get(
				AssetEntryImpl.class, primaryKey);

			if (assetEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetEntry);
		}
		catch (NoSuchEntryException noSuchEntityException) {
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
	protected AssetEntry removeImpl(AssetEntry assetEntry) {
		assetEntryToAssetCategoryTableMapper.deleteLeftPrimaryKeyTableMappings(
			assetEntry.getPrimaryKey());

		assetEntryToAssetTagTableMapper.deleteLeftPrimaryKeyTableMappings(
			assetEntry.getPrimaryKey());

		if (!CTPersistenceHelperUtil.isRemove(assetEntry)) {
			return assetEntry;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetEntry)) {
				assetEntry = (AssetEntry)session.get(
					AssetEntryImpl.class, assetEntry.getPrimaryKeyObj());
			}

			if (assetEntry != null) {
				session.delete(assetEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetEntry != null) {
			clearCache(assetEntry);
		}

		return assetEntry;
	}

	@Override
	public AssetEntry updateImpl(AssetEntry assetEntry) {
		boolean isNew = assetEntry.isNew();

		if (!(assetEntry instanceof AssetEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(assetEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetEntry implementation " +
					assetEntry.getClass());
		}

		AssetEntryModelImpl assetEntryModelImpl =
			(AssetEntryModelImpl)assetEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (assetEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetEntry.setCreateDate(now);
			}
			else {
				assetEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!assetEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetEntry.setModifiedDate(now);
			}
			else {
				assetEntry.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(assetEntry)) {
				if (!isNew) {
					session.evict(
						AssetEntryImpl.class, assetEntry.getPrimaryKeyObj());
				}

				session.save(assetEntry);

				assetEntry.setNew(false);
			}
			else {
				assetEntry = (AssetEntry)session.merge(assetEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetEntry.getCtCollectionId() != 0) {
			assetEntry.resetOriginalValues();

			return assetEntry;
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {assetEntryModelImpl.getGroupId()};

			FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {assetEntryModelImpl.getCompanyId()};

			FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {assetEntryModelImpl.isVisible()};

			FinderCacheUtil.removeResult(_finderPathCountByVisible, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByVisible, args);

			args = new Object[] {assetEntryModelImpl.getPublishDate()};

			FinderCacheUtil.removeResult(_finderPathCountByPublishDate, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByPublishDate, args);

			args = new Object[] {assetEntryModelImpl.getExpirationDate()};

			FinderCacheUtil.removeResult(
				_finderPathCountByExpirationDate, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByExpirationDate, args);

			args = new Object[] {assetEntryModelImpl.getLayoutUuid()};

			FinderCacheUtil.removeResult(_finderPathCountByLayoutUuid, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByLayoutUuid, args);

			args = new Object[] {
				assetEntryModelImpl.getGroupId(),
				assetEntryModelImpl.getClassNameId(),
				assetEntryModelImpl.isVisible()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_C_V, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_C_V, args);

			args = new Object[] {
				assetEntryModelImpl.getGroupId(),
				assetEntryModelImpl.getClassNameId(),
				assetEntryModelImpl.getPublishDate(),
				assetEntryModelImpl.getExpirationDate()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_C_P_E, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_C_P_E, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((assetEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetEntryModelImpl.getOriginalGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {assetEntryModelImpl.getGroupId()};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((assetEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetEntryModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {assetEntryModelImpl.getCompanyId()};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((assetEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByVisible.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetEntryModelImpl.getOriginalVisible()
				};

				FinderCacheUtil.removeResult(_finderPathCountByVisible, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByVisible, args);

				args = new Object[] {assetEntryModelImpl.isVisible()};

				FinderCacheUtil.removeResult(_finderPathCountByVisible, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByVisible, args);
			}

			if ((assetEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByPublishDate.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetEntryModelImpl.getOriginalPublishDate()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByPublishDate, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByPublishDate, args);

				args = new Object[] {assetEntryModelImpl.getPublishDate()};

				FinderCacheUtil.removeResult(
					_finderPathCountByPublishDate, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByPublishDate, args);
			}

			if ((assetEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByExpirationDate.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetEntryModelImpl.getOriginalExpirationDate()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByExpirationDate, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByExpirationDate, args);

				args = new Object[] {assetEntryModelImpl.getExpirationDate()};

				FinderCacheUtil.removeResult(
					_finderPathCountByExpirationDate, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByExpirationDate, args);
			}

			if ((assetEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByLayoutUuid.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetEntryModelImpl.getOriginalLayoutUuid()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByLayoutUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByLayoutUuid, args);

				args = new Object[] {assetEntryModelImpl.getLayoutUuid()};

				FinderCacheUtil.removeResult(
					_finderPathCountByLayoutUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByLayoutUuid, args);
			}

			if ((assetEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_V.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetEntryModelImpl.getOriginalGroupId(),
					assetEntryModelImpl.getOriginalClassNameId(),
					assetEntryModelImpl.getOriginalVisible()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_C_V, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_C_V, args);

				args = new Object[] {
					assetEntryModelImpl.getGroupId(),
					assetEntryModelImpl.getClassNameId(),
					assetEntryModelImpl.isVisible()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_C_V, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_C_V, args);
			}

			if ((assetEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_P_E.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetEntryModelImpl.getOriginalGroupId(),
					assetEntryModelImpl.getOriginalClassNameId(),
					assetEntryModelImpl.getOriginalPublishDate(),
					assetEntryModelImpl.getOriginalExpirationDate()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_C_P_E, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_C_P_E, args);

				args = new Object[] {
					assetEntryModelImpl.getGroupId(),
					assetEntryModelImpl.getClassNameId(),
					assetEntryModelImpl.getPublishDate(),
					assetEntryModelImpl.getExpirationDate()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_C_P_E, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_C_P_E, args);
			}
		}

		EntityCacheUtil.putResult(
			AssetEntryImpl.class, assetEntry.getPrimaryKey(), assetEntry,
			false);

		clearUniqueFindersCache(assetEntryModelImpl, false);
		cacheUniqueFindersCache(assetEntryModelImpl);

		assetEntry.resetOriginalValues();

		return assetEntry;
	}

	/**
	 * Returns the asset entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry
	 * @return the asset entry
	 * @throws NoSuchEntryException if a asset entry with the primary key could not be found
	 */
	@Override
	public AssetEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		AssetEntry assetEntry = fetchByPrimaryKey(primaryKey);

		if (assetEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetEntry;
	}

	/**
	 * Returns the asset entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param entryId the primary key of the asset entry
	 * @return the asset entry
	 * @throws NoSuchEntryException if a asset entry with the primary key could not be found
	 */
	@Override
	public AssetEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)entryId);
	}

	/**
	 * Returns the asset entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry
	 * @return the asset entry, or <code>null</code> if a asset entry with the primary key could not be found
	 */
	@Override
	public AssetEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(AssetEntry.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		AssetEntry assetEntry = null;

		Session session = null;

		try {
			session = openSession();

			assetEntry = (AssetEntry)session.get(
				AssetEntryImpl.class, primaryKey);

			if (assetEntry != null) {
				cacheResult(assetEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return assetEntry;
	}

	/**
	 * Returns the asset entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the asset entry
	 * @return the asset entry, or <code>null</code> if a asset entry with the primary key could not be found
	 */
	@Override
	public AssetEntry fetchByPrimaryKey(long entryId) {
		return fetchByPrimaryKey((Serializable)entryId);
	}

	@Override
	public Map<Serializable, AssetEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(AssetEntry.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetEntry> map =
			new HashMap<Serializable, AssetEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetEntry assetEntry = fetchByPrimaryKey(primaryKey);

			if (assetEntry != null) {
				map.put(primaryKey, assetEntry);
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

			for (AssetEntry assetEntry : (List<AssetEntry>)query.list()) {
				map.put(assetEntry.getPrimaryKeyObj(), assetEntry);

				cacheResult(assetEntry);
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
	 * Returns all the asset entries.
	 *
	 * @return the asset entries
	 */
	@Override
	public List<AssetEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of asset entries
	 */
	@Override
	public List<AssetEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entries
	 */
	@Override
	public List<AssetEntry> findAll(
		int start, int end, OrderByComparator<AssetEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset entries
	 */
	@Override
	public List<AssetEntry> findAll(
		int start, int end, OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

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

		List<AssetEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ASSETENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETENTRY;

				sql = sql.concat(AssetEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AssetEntry>)QueryUtil.list(
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
	 * Removes all the asset entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetEntry assetEntry : findAll()) {
			remove(assetEntry);
		}
	}

	/**
	 * Returns the number of asset entries.
	 *
	 * @return the number of asset entries
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			AssetEntry.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ASSETENTRY);

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
	 * Returns the primaryKeys of asset categories associated with the asset entry.
	 *
	 * @param pk the primary key of the asset entry
	 * @return long[] of the primaryKeys of asset categories associated with the asset entry
	 */
	@Override
	public long[] getAssetCategoryPrimaryKeys(long pk) {
		long[] pks = assetEntryToAssetCategoryTableMapper.getRightPrimaryKeys(
			pk);

		return pks.clone();
	}

	/**
	 * Returns all the asset categories associated with the asset entry.
	 *
	 * @param pk the primary key of the asset entry
	 * @return the asset categories associated with the asset entry
	 */
	@Override
	public List<com.liferay.asset.kernel.model.AssetCategory>
		getAssetCategories(long pk) {

		return getAssetCategories(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the asset categories associated with the asset entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the asset entry
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of asset categories associated with the asset entry
	 */
	@Override
	public List<com.liferay.asset.kernel.model.AssetCategory>
		getAssetCategories(long pk, int start, int end) {

		return getAssetCategories(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories associated with the asset entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the asset entry
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset categories associated with the asset entry
	 */
	@Override
	public List<com.liferay.asset.kernel.model.AssetCategory>
		getAssetCategories(
			long pk, int start, int end,
			OrderByComparator<com.liferay.asset.kernel.model.AssetCategory>
				orderByComparator) {

		return assetEntryToAssetCategoryTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of asset categories associated with the asset entry.
	 *
	 * @param pk the primary key of the asset entry
	 * @return the number of asset categories associated with the asset entry
	 */
	@Override
	public int getAssetCategoriesSize(long pk) {
		long[] pks = assetEntryToAssetCategoryTableMapper.getRightPrimaryKeys(
			pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the asset category is associated with the asset entry.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategoryPK the primary key of the asset category
	 * @return <code>true</code> if the asset category is associated with the asset entry; <code>false</code> otherwise
	 */
	@Override
	public boolean containsAssetCategory(long pk, long assetCategoryPK) {
		return assetEntryToAssetCategoryTableMapper.containsTableMapping(
			pk, assetCategoryPK);
	}

	/**
	 * Returns <code>true</code> if the asset entry has any asset categories associated with it.
	 *
	 * @param pk the primary key of the asset entry to check for associations with asset categories
	 * @return <code>true</code> if the asset entry has any asset categories associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsAssetCategories(long pk) {
		if (getAssetCategoriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategoryPK the primary key of the asset category
	 */
	@Override
	public void addAssetCategory(long pk, long assetCategoryPK) {
		AssetEntry assetEntry = fetchByPrimaryKey(pk);

		if (assetEntry == null) {
			assetEntryToAssetCategoryTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, assetCategoryPK);
		}
		else {
			assetEntryToAssetCategoryTableMapper.addTableMapping(
				assetEntry.getCompanyId(), pk, assetCategoryPK);
		}
	}

	/**
	 * Adds an association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategory the asset category
	 */
	@Override
	public void addAssetCategory(
		long pk, com.liferay.asset.kernel.model.AssetCategory assetCategory) {

		AssetEntry assetEntry = fetchByPrimaryKey(pk);

		if (assetEntry == null) {
			assetEntryToAssetCategoryTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				assetCategory.getPrimaryKey());
		}
		else {
			assetEntryToAssetCategoryTableMapper.addTableMapping(
				assetEntry.getCompanyId(), pk, assetCategory.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategoryPKs the primary keys of the asset categories
	 */
	@Override
	public void addAssetCategories(long pk, long[] assetCategoryPKs) {
		long companyId = 0;

		AssetEntry assetEntry = fetchByPrimaryKey(pk);

		if (assetEntry == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = assetEntry.getCompanyId();
		}

		assetEntryToAssetCategoryTableMapper.addTableMappings(
			companyId, pk, assetCategoryPKs);
	}

	/**
	 * Adds an association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategories the asset categories
	 */
	@Override
	public void addAssetCategories(
		long pk,
		List<com.liferay.asset.kernel.model.AssetCategory> assetCategories) {

		addAssetCategories(
			pk,
			ListUtil.toLongArray(
				assetCategories,
				com.liferay.asset.kernel.model.AssetCategory.
					CATEGORY_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the asset entry and its asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry to clear the associated asset categories from
	 */
	@Override
	public void clearAssetCategories(long pk) {
		assetEntryToAssetCategoryTableMapper.deleteLeftPrimaryKeyTableMappings(
			pk);
	}

	/**
	 * Removes the association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategoryPK the primary key of the asset category
	 */
	@Override
	public void removeAssetCategory(long pk, long assetCategoryPK) {
		assetEntryToAssetCategoryTableMapper.deleteTableMapping(
			pk, assetCategoryPK);
	}

	/**
	 * Removes the association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategory the asset category
	 */
	@Override
	public void removeAssetCategory(
		long pk, com.liferay.asset.kernel.model.AssetCategory assetCategory) {

		assetEntryToAssetCategoryTableMapper.deleteTableMapping(
			pk, assetCategory.getPrimaryKey());
	}

	/**
	 * Removes the association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategoryPKs the primary keys of the asset categories
	 */
	@Override
	public void removeAssetCategories(long pk, long[] assetCategoryPKs) {
		assetEntryToAssetCategoryTableMapper.deleteTableMappings(
			pk, assetCategoryPKs);
	}

	/**
	 * Removes the association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategories the asset categories
	 */
	@Override
	public void removeAssetCategories(
		long pk,
		List<com.liferay.asset.kernel.model.AssetCategory> assetCategories) {

		removeAssetCategories(
			pk,
			ListUtil.toLongArray(
				assetCategories,
				com.liferay.asset.kernel.model.AssetCategory.
					CATEGORY_ID_ACCESSOR));
	}

	/**
	 * Sets the asset categories associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategoryPKs the primary keys of the asset categories to be associated with the asset entry
	 */
	@Override
	public void setAssetCategories(long pk, long[] assetCategoryPKs) {
		Set<Long> newAssetCategoryPKsSet = SetUtil.fromArray(assetCategoryPKs);
		Set<Long> oldAssetCategoryPKsSet = SetUtil.fromArray(
			assetEntryToAssetCategoryTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeAssetCategoryPKsSet = new HashSet<Long>(
			oldAssetCategoryPKsSet);

		removeAssetCategoryPKsSet.removeAll(newAssetCategoryPKsSet);

		assetEntryToAssetCategoryTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeAssetCategoryPKsSet));

		newAssetCategoryPKsSet.removeAll(oldAssetCategoryPKsSet);

		long companyId = 0;

		AssetEntry assetEntry = fetchByPrimaryKey(pk);

		if (assetEntry == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = assetEntry.getCompanyId();
		}

		assetEntryToAssetCategoryTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newAssetCategoryPKsSet));
	}

	/**
	 * Sets the asset categories associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategories the asset categories to be associated with the asset entry
	 */
	@Override
	public void setAssetCategories(
		long pk,
		List<com.liferay.asset.kernel.model.AssetCategory> assetCategories) {

		try {
			long[] assetCategoryPKs = new long[assetCategories.size()];

			for (int i = 0; i < assetCategories.size(); i++) {
				com.liferay.asset.kernel.model.AssetCategory assetCategory =
					assetCategories.get(i);

				assetCategoryPKs[i] = assetCategory.getPrimaryKey();
			}

			setAssetCategories(pk, assetCategoryPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	/**
	 * Returns the primaryKeys of asset tags associated with the asset entry.
	 *
	 * @param pk the primary key of the asset entry
	 * @return long[] of the primaryKeys of asset tags associated with the asset entry
	 */
	@Override
	public long[] getAssetTagPrimaryKeys(long pk) {
		long[] pks = assetEntryToAssetTagTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the asset tags associated with the asset entry.
	 *
	 * @param pk the primary key of the asset entry
	 * @return the asset tags associated with the asset entry
	 */
	@Override
	public List<com.liferay.asset.kernel.model.AssetTag> getAssetTags(long pk) {
		return getAssetTags(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the asset tags associated with the asset entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the asset entry
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of asset tags associated with the asset entry
	 */
	@Override
	public List<com.liferay.asset.kernel.model.AssetTag> getAssetTags(
		long pk, int start, int end) {

		return getAssetTags(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tags associated with the asset entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the asset entry
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset tags associated with the asset entry
	 */
	@Override
	public List<com.liferay.asset.kernel.model.AssetTag> getAssetTags(
		long pk, int start, int end,
		OrderByComparator<com.liferay.asset.kernel.model.AssetTag>
			orderByComparator) {

		return assetEntryToAssetTagTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of asset tags associated with the asset entry.
	 *
	 * @param pk the primary key of the asset entry
	 * @return the number of asset tags associated with the asset entry
	 */
	@Override
	public int getAssetTagsSize(long pk) {
		long[] pks = assetEntryToAssetTagTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the asset tag is associated with the asset entry.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTagPK the primary key of the asset tag
	 * @return <code>true</code> if the asset tag is associated with the asset entry; <code>false</code> otherwise
	 */
	@Override
	public boolean containsAssetTag(long pk, long assetTagPK) {
		return assetEntryToAssetTagTableMapper.containsTableMapping(
			pk, assetTagPK);
	}

	/**
	 * Returns <code>true</code> if the asset entry has any asset tags associated with it.
	 *
	 * @param pk the primary key of the asset entry to check for associations with asset tags
	 * @return <code>true</code> if the asset entry has any asset tags associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsAssetTags(long pk) {
		if (getAssetTagsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTagPK the primary key of the asset tag
	 */
	@Override
	public void addAssetTag(long pk, long assetTagPK) {
		AssetEntry assetEntry = fetchByPrimaryKey(pk);

		if (assetEntry == null) {
			assetEntryToAssetTagTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, assetTagPK);
		}
		else {
			assetEntryToAssetTagTableMapper.addTableMapping(
				assetEntry.getCompanyId(), pk, assetTagPK);
		}
	}

	/**
	 * Adds an association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTag the asset tag
	 */
	@Override
	public void addAssetTag(
		long pk, com.liferay.asset.kernel.model.AssetTag assetTag) {

		AssetEntry assetEntry = fetchByPrimaryKey(pk);

		if (assetEntry == null) {
			assetEntryToAssetTagTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				assetTag.getPrimaryKey());
		}
		else {
			assetEntryToAssetTagTableMapper.addTableMapping(
				assetEntry.getCompanyId(), pk, assetTag.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTagPKs the primary keys of the asset tags
	 */
	@Override
	public void addAssetTags(long pk, long[] assetTagPKs) {
		long companyId = 0;

		AssetEntry assetEntry = fetchByPrimaryKey(pk);

		if (assetEntry == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = assetEntry.getCompanyId();
		}

		assetEntryToAssetTagTableMapper.addTableMappings(
			companyId, pk, assetTagPKs);
	}

	/**
	 * Adds an association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTags the asset tags
	 */
	@Override
	public void addAssetTags(
		long pk, List<com.liferay.asset.kernel.model.AssetTag> assetTags) {

		addAssetTags(
			pk,
			ListUtil.toLongArray(
				assetTags,
				com.liferay.asset.kernel.model.AssetTag.TAG_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the asset entry and its asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry to clear the associated asset tags from
	 */
	@Override
	public void clearAssetTags(long pk) {
		assetEntryToAssetTagTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTagPK the primary key of the asset tag
	 */
	@Override
	public void removeAssetTag(long pk, long assetTagPK) {
		assetEntryToAssetTagTableMapper.deleteTableMapping(pk, assetTagPK);
	}

	/**
	 * Removes the association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTag the asset tag
	 */
	@Override
	public void removeAssetTag(
		long pk, com.liferay.asset.kernel.model.AssetTag assetTag) {

		assetEntryToAssetTagTableMapper.deleteTableMapping(
			pk, assetTag.getPrimaryKey());
	}

	/**
	 * Removes the association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTagPKs the primary keys of the asset tags
	 */
	@Override
	public void removeAssetTags(long pk, long[] assetTagPKs) {
		assetEntryToAssetTagTableMapper.deleteTableMappings(pk, assetTagPKs);
	}

	/**
	 * Removes the association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTags the asset tags
	 */
	@Override
	public void removeAssetTags(
		long pk, List<com.liferay.asset.kernel.model.AssetTag> assetTags) {

		removeAssetTags(
			pk,
			ListUtil.toLongArray(
				assetTags,
				com.liferay.asset.kernel.model.AssetTag.TAG_ID_ACCESSOR));
	}

	/**
	 * Sets the asset tags associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTagPKs the primary keys of the asset tags to be associated with the asset entry
	 */
	@Override
	public void setAssetTags(long pk, long[] assetTagPKs) {
		Set<Long> newAssetTagPKsSet = SetUtil.fromArray(assetTagPKs);
		Set<Long> oldAssetTagPKsSet = SetUtil.fromArray(
			assetEntryToAssetTagTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeAssetTagPKsSet = new HashSet<Long>(oldAssetTagPKsSet);

		removeAssetTagPKsSet.removeAll(newAssetTagPKsSet);

		assetEntryToAssetTagTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeAssetTagPKsSet));

		newAssetTagPKsSet.removeAll(oldAssetTagPKsSet);

		long companyId = 0;

		AssetEntry assetEntry = fetchByPrimaryKey(pk);

		if (assetEntry == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = assetEntry.getCompanyId();
		}

		assetEntryToAssetTagTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newAssetTagPKsSet));
	}

	/**
	 * Sets the asset tags associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTags the asset tags to be associated with the asset entry
	 */
	@Override
	public void setAssetTags(
		long pk, List<com.liferay.asset.kernel.model.AssetTag> assetTags) {

		try {
			long[] assetTagPKs = new long[assetTags.size()];

			for (int i = 0; i < assetTags.size(); i++) {
				com.liferay.asset.kernel.model.AssetTag assetTag =
					assetTags.get(i);

				assetTagPKs[i] = assetTag.getPrimaryKey();
			}

			setAssetTags(pk, assetTagPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "entryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETENTRY;
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
		return AssetEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AssetEntry";
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
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("classUuid");
		ctStrictColumnNames.add("classTypeId");
		ctStrictColumnNames.add("listable");
		ctStrictColumnNames.add("visible");
		ctStrictColumnNames.add("startDate");
		ctStrictColumnNames.add("endDate");
		ctStrictColumnNames.add("publishDate");
		ctStrictColumnNames.add("expirationDate");
		ctStrictColumnNames.add("mimeType");
		ctStrictColumnNames.add("title");
		ctStrictColumnNames.add("description");
		ctStrictColumnNames.add("summary");
		ctStrictColumnNames.add("url");
		ctStrictColumnNames.add("layoutUuid");
		ctStrictColumnNames.add("height");
		ctStrictColumnNames.add("width");
		ctStrictColumnNames.add("priority");
		ctStrictColumnNames.add("categories");
		ctStrictColumnNames.add("tags");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("entryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_mappingTableNames.add("AssetEntries_AssetCategories");
		_mappingTableNames.add("AssetEntries_AssetTags");

		_uniqueIndexColumnNames.add(new String[] {"classNameId", "classPK"});
	}

	/**
	 * Initializes the asset entry persistence.
	 */
	public void afterPropertiesSet() {
		assetEntryToAssetCategoryTableMapper =
			TableMapperFactory.getTableMapper(
				"AssetEntries_AssetCategories", "companyId", "entryId",
				"categoryId", this, assetCategoryPersistence);

		assetEntryToAssetTagTableMapper = TableMapperFactory.getTableMapper(
			"AssetEntries_AssetTags", "companyId", "entryId", "tagId", this,
			assetTagPersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] {Long.class.getName()},
			AssetEntryModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] {Long.class.getName()},
			AssetEntryModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByVisible = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByVisible",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByVisible = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByVisible", new String[] {Boolean.class.getName()},
			AssetEntryModelImpl.VISIBLE_COLUMN_BITMASK);

		_finderPathCountByVisible = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByVisible", new String[] {Boolean.class.getName()});

		_finderPathWithPaginationFindByPublishDate = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPublishDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByPublishDate = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByPublishDate", new String[] {Date.class.getName()},
			AssetEntryModelImpl.PUBLISHDATE_COLUMN_BITMASK);

		_finderPathCountByPublishDate = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPublishDate", new String[] {Date.class.getName()});

		_finderPathWithPaginationFindByExpirationDate = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByExpirationDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByExpirationDate = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByExpirationDate", new String[] {Date.class.getName()},
			AssetEntryModelImpl.EXPIRATIONDATE_COLUMN_BITMASK);

		_finderPathCountByExpirationDate = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByExpirationDate", new String[] {Date.class.getName()});

		_finderPathWithPaginationFindByLayoutUuid = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLayoutUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByLayoutUuid = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLayoutUuid", new String[] {String.class.getName()},
			AssetEntryModelImpl.LAYOUTUUID_COLUMN_BITMASK);

		_finderPathCountByLayoutUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutUuid", new String[] {String.class.getName()});

		_finderPathFetchByG_CU = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_CU",
			new String[] {Long.class.getName(), String.class.getName()},
			AssetEntryModelImpl.GROUPID_COLUMN_BITMASK |
			AssetEntryModelImpl.CLASSUUID_COLUMN_BITMASK);

		_finderPathCountByG_CU = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_CU",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByC_C = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			AssetEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AssetEntryModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByG_C_V = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_V = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_C_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			AssetEntryModelImpl.GROUPID_COLUMN_BITMASK |
			AssetEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AssetEntryModelImpl.VISIBLE_COLUMN_BITMASK);

		_finderPathCountByG_C_V = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationFindByG_C_P_E = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C_P_E",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Date.class.getName(), Date.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_P_E = new FinderPath(
			AssetEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_C_P_E",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Date.class.getName(), Date.class.getName()
			},
			AssetEntryModelImpl.GROUPID_COLUMN_BITMASK |
			AssetEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AssetEntryModelImpl.PUBLISHDATE_COLUMN_BITMASK |
			AssetEntryModelImpl.EXPIRATIONDATE_COLUMN_BITMASK);

		_finderPathCountByG_C_P_E = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_P_E",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Date.class.getName(), Date.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(AssetEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("AssetEntries_AssetCategories");
		TableMapperFactory.removeTableMapper("AssetEntries_AssetTags");
	}

	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;

	protected TableMapper
		<AssetEntry, com.liferay.asset.kernel.model.AssetCategory>
			assetEntryToAssetCategoryTableMapper;

	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;

	protected TableMapper<AssetEntry, com.liferay.asset.kernel.model.AssetTag>
		assetEntryToAssetTagTableMapper;

	private Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_ASSETENTRY =
		"SELECT assetEntry FROM AssetEntry assetEntry";

	private static final String _SQL_SELECT_ASSETENTRY_WHERE =
		"SELECT assetEntry FROM AssetEntry assetEntry WHERE ";

	private static final String _SQL_COUNT_ASSETENTRY =
		"SELECT COUNT(assetEntry) FROM AssetEntry assetEntry";

	private static final String _SQL_COUNT_ASSETENTRY_WHERE =
		"SELECT COUNT(assetEntry) FROM AssetEntry assetEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "assetEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntryPersistenceImpl.class);

}