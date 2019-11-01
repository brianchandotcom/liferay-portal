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

package com.liferay.asset.auto.tagger.service.persistence.impl;

import com.liferay.asset.auto.tagger.exception.NoSuchEntryException;
import com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry;
import com.liferay.asset.auto.tagger.model.impl.AssetAutoTaggerEntryImpl;
import com.liferay.asset.auto.tagger.model.impl.AssetAutoTaggerEntryModelImpl;
import com.liferay.asset.auto.tagger.service.persistence.AssetAutoTaggerEntryPersistence;
import com.liferay.asset.auto.tagger.service.persistence.impl.constants.AssetAutoTaggerPersistenceConstants;
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

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the asset auto tagger entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AssetAutoTaggerEntryPersistence.class)
public class AssetAutoTaggerEntryPersistenceImpl
	extends BasePersistenceImpl<AssetAutoTaggerEntry>
	implements AssetAutoTaggerEntryPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetAutoTaggerEntryUtil</code> to access the asset auto tagger entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetAutoTaggerEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByAssetEntryId;
	private FinderPath _finderPathWithoutPaginationFindByAssetEntryId;
	private FinderPath _finderPathCountByAssetEntryId;

	/**
	 * Returns all the asset auto tagger entries where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the matching asset auto tagger entries
	 */
	@Override
	public List<AssetAutoTaggerEntry> findByAssetEntryId(long assetEntryId) {
		return findByAssetEntryId(
			assetEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset auto tagger entries where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @return the range of matching asset auto tagger entries
	 */
	@Override
	public List<AssetAutoTaggerEntry> findByAssetEntryId(
		long assetEntryId, int start, int end) {

		return findByAssetEntryId(assetEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset auto tagger entries where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset auto tagger entries
	 */
	@Override
	public List<AssetAutoTaggerEntry> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {

		return findByAssetEntryId(
			assetEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset auto tagger entries where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset auto tagger entries
	 */
	@Override
	public List<AssetAutoTaggerEntry> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByAssetEntryId;
				finderArgs = new Object[] {assetEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAssetEntryId;
			finderArgs = new Object[] {
				assetEntryId, start, end, orderByComparator
			};
		}

		List<AssetAutoTaggerEntry> list = null;

		if (useFinderCache) {
			list = (List<AssetAutoTaggerEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetAutoTaggerEntry assetAutoTaggerEntry : list) {
					if (assetEntryId !=
							assetAutoTaggerEntry.getAssetEntryId()) {

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

			query.append(_SQL_SELECT_ASSETAUTOTAGGERENTRY_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(AssetAutoTaggerEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				list = (List<AssetAutoTaggerEntry>)QueryUtil.list(
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
	 * Returns the first asset auto tagger entry in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset auto tagger entry
	 * @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	 */
	@Override
	public AssetAutoTaggerEntry findByAssetEntryId_First(
			long assetEntryId,
			OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetAutoTaggerEntry assetAutoTaggerEntry = fetchByAssetEntryId_First(
			assetEntryId, orderByComparator);

		if (assetAutoTaggerEntry != null) {
			return assetAutoTaggerEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetEntryId=");
		msg.append(assetEntryId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first asset auto tagger entry in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	@Override
	public AssetAutoTaggerEntry fetchByAssetEntryId_First(
		long assetEntryId,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {

		List<AssetAutoTaggerEntry> list = findByAssetEntryId(
			assetEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset auto tagger entry in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset auto tagger entry
	 * @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	 */
	@Override
	public AssetAutoTaggerEntry findByAssetEntryId_Last(
			long assetEntryId,
			OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetAutoTaggerEntry assetAutoTaggerEntry = fetchByAssetEntryId_Last(
			assetEntryId, orderByComparator);

		if (assetAutoTaggerEntry != null) {
			return assetAutoTaggerEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetEntryId=");
		msg.append(assetEntryId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last asset auto tagger entry in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	@Override
	public AssetAutoTaggerEntry fetchByAssetEntryId_Last(
		long assetEntryId,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {

		int count = countByAssetEntryId(assetEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetAutoTaggerEntry> list = findByAssetEntryId(
			assetEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset auto tagger entries before and after the current asset auto tagger entry in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the current asset auto tagger entry
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset auto tagger entry
	 * @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	 */
	@Override
	public AssetAutoTaggerEntry[] findByAssetEntryId_PrevAndNext(
			long assetAutoTaggerEntryId, long assetEntryId,
			OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetAutoTaggerEntry assetAutoTaggerEntry = findByPrimaryKey(
			assetAutoTaggerEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetAutoTaggerEntry[] array = new AssetAutoTaggerEntryImpl[3];

			array[0] = getByAssetEntryId_PrevAndNext(
				session, assetAutoTaggerEntry, assetEntryId, orderByComparator,
				true);

			array[1] = assetAutoTaggerEntry;

			array[2] = getByAssetEntryId_PrevAndNext(
				session, assetAutoTaggerEntry, assetEntryId, orderByComparator,
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

	protected AssetAutoTaggerEntry getByAssetEntryId_PrevAndNext(
		Session session, AssetAutoTaggerEntry assetAutoTaggerEntry,
		long assetEntryId,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETAUTOTAGGERENTRY_WHERE);

		query.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

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
			query.append(AssetAutoTaggerEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(assetEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetAutoTaggerEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetAutoTaggerEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset auto tagger entries where assetEntryId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 */
	@Override
	public void removeByAssetEntryId(long assetEntryId) {
		for (AssetAutoTaggerEntry assetAutoTaggerEntry :
				findByAssetEntryId(
					assetEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetAutoTaggerEntry);
		}
	}

	/**
	 * Returns the number of asset auto tagger entries where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the number of matching asset auto tagger entries
	 */
	@Override
	public int countByAssetEntryId(long assetEntryId) {
		FinderPath finderPath = _finderPathCountByAssetEntryId;

		Object[] finderArgs = new Object[] {assetEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETAUTOTAGGERENTRY_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

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

	private static final String _FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2 =
		"assetAutoTaggerEntry.assetEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByAssetTagId;
	private FinderPath _finderPathWithoutPaginationFindByAssetTagId;
	private FinderPath _finderPathCountByAssetTagId;

	/**
	 * Returns all the asset auto tagger entries where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @return the matching asset auto tagger entries
	 */
	@Override
	public List<AssetAutoTaggerEntry> findByAssetTagId(long assetTagId) {
		return findByAssetTagId(
			assetTagId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset auto tagger entries where assetTagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param assetTagId the asset tag ID
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @return the range of matching asset auto tagger entries
	 */
	@Override
	public List<AssetAutoTaggerEntry> findByAssetTagId(
		long assetTagId, int start, int end) {

		return findByAssetTagId(assetTagId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset auto tagger entries where assetTagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param assetTagId the asset tag ID
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset auto tagger entries
	 */
	@Override
	public List<AssetAutoTaggerEntry> findByAssetTagId(
		long assetTagId, int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {

		return findByAssetTagId(
			assetTagId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset auto tagger entries where assetTagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param assetTagId the asset tag ID
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset auto tagger entries
	 */
	@Override
	public List<AssetAutoTaggerEntry> findByAssetTagId(
		long assetTagId, int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByAssetTagId;
				finderArgs = new Object[] {assetTagId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAssetTagId;
			finderArgs = new Object[] {
				assetTagId, start, end, orderByComparator
			};
		}

		List<AssetAutoTaggerEntry> list = null;

		if (useFinderCache) {
			list = (List<AssetAutoTaggerEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetAutoTaggerEntry assetAutoTaggerEntry : list) {
					if (assetTagId != assetAutoTaggerEntry.getAssetTagId()) {
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

			query.append(_SQL_SELECT_ASSETAUTOTAGGERENTRY_WHERE);

			query.append(_FINDER_COLUMN_ASSETTAGID_ASSETTAGID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(AssetAutoTaggerEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetTagId);

				list = (List<AssetAutoTaggerEntry>)QueryUtil.list(
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
	 * Returns the first asset auto tagger entry in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset auto tagger entry
	 * @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	 */
	@Override
	public AssetAutoTaggerEntry findByAssetTagId_First(
			long assetTagId,
			OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetAutoTaggerEntry assetAutoTaggerEntry = fetchByAssetTagId_First(
			assetTagId, orderByComparator);

		if (assetAutoTaggerEntry != null) {
			return assetAutoTaggerEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetTagId=");
		msg.append(assetTagId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first asset auto tagger entry in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	@Override
	public AssetAutoTaggerEntry fetchByAssetTagId_First(
		long assetTagId,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {

		List<AssetAutoTaggerEntry> list = findByAssetTagId(
			assetTagId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset auto tagger entry in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset auto tagger entry
	 * @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	 */
	@Override
	public AssetAutoTaggerEntry findByAssetTagId_Last(
			long assetTagId,
			OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetAutoTaggerEntry assetAutoTaggerEntry = fetchByAssetTagId_Last(
			assetTagId, orderByComparator);

		if (assetAutoTaggerEntry != null) {
			return assetAutoTaggerEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetTagId=");
		msg.append(assetTagId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last asset auto tagger entry in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	@Override
	public AssetAutoTaggerEntry fetchByAssetTagId_Last(
		long assetTagId,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {

		int count = countByAssetTagId(assetTagId);

		if (count == 0) {
			return null;
		}

		List<AssetAutoTaggerEntry> list = findByAssetTagId(
			assetTagId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset auto tagger entries before and after the current asset auto tagger entry in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the current asset auto tagger entry
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset auto tagger entry
	 * @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	 */
	@Override
	public AssetAutoTaggerEntry[] findByAssetTagId_PrevAndNext(
			long assetAutoTaggerEntryId, long assetTagId,
			OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetAutoTaggerEntry assetAutoTaggerEntry = findByPrimaryKey(
			assetAutoTaggerEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetAutoTaggerEntry[] array = new AssetAutoTaggerEntryImpl[3];

			array[0] = getByAssetTagId_PrevAndNext(
				session, assetAutoTaggerEntry, assetTagId, orderByComparator,
				true);

			array[1] = assetAutoTaggerEntry;

			array[2] = getByAssetTagId_PrevAndNext(
				session, assetAutoTaggerEntry, assetTagId, orderByComparator,
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

	protected AssetAutoTaggerEntry getByAssetTagId_PrevAndNext(
		Session session, AssetAutoTaggerEntry assetAutoTaggerEntry,
		long assetTagId,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETAUTOTAGGERENTRY_WHERE);

		query.append(_FINDER_COLUMN_ASSETTAGID_ASSETTAGID_2);

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
			query.append(AssetAutoTaggerEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(assetTagId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetAutoTaggerEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AssetAutoTaggerEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset auto tagger entries where assetTagId = &#63; from the database.
	 *
	 * @param assetTagId the asset tag ID
	 */
	@Override
	public void removeByAssetTagId(long assetTagId) {
		for (AssetAutoTaggerEntry assetAutoTaggerEntry :
				findByAssetTagId(
					assetTagId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetAutoTaggerEntry);
		}
	}

	/**
	 * Returns the number of asset auto tagger entries where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @return the number of matching asset auto tagger entries
	 */
	@Override
	public int countByAssetTagId(long assetTagId) {
		FinderPath finderPath = _finderPathCountByAssetTagId;

		Object[] finderArgs = new Object[] {assetTagId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETAUTOTAGGERENTRY_WHERE);

			query.append(_FINDER_COLUMN_ASSETTAGID_ASSETTAGID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetTagId);

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

	private static final String _FINDER_COLUMN_ASSETTAGID_ASSETTAGID_2 =
		"assetAutoTaggerEntry.assetTagId = ?";

	private FinderPath _finderPathFetchByA_A;
	private FinderPath _finderPathCountByA_A;

	/**
	 * Returns the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetTagId the asset tag ID
	 * @return the matching asset auto tagger entry
	 * @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	 */
	@Override
	public AssetAutoTaggerEntry findByA_A(long assetEntryId, long assetTagId)
		throws NoSuchEntryException {

		AssetAutoTaggerEntry assetAutoTaggerEntry = fetchByA_A(
			assetEntryId, assetTagId);

		if (assetAutoTaggerEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", assetTagId=");
			msg.append(assetTagId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return assetAutoTaggerEntry;
	}

	/**
	 * Returns the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetTagId the asset tag ID
	 * @return the matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	@Override
	public AssetAutoTaggerEntry fetchByA_A(long assetEntryId, long assetTagId) {
		return fetchByA_A(assetEntryId, assetTagId, true);
	}

	/**
	 * Returns the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetTagId the asset tag ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	@Override
	public AssetAutoTaggerEntry fetchByA_A(
		long assetEntryId, long assetTagId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {assetEntryId, assetTagId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByA_A, finderArgs, this);
		}

		if (result instanceof AssetAutoTaggerEntry) {
			AssetAutoTaggerEntry assetAutoTaggerEntry =
				(AssetAutoTaggerEntry)result;

			if ((assetEntryId != assetAutoTaggerEntry.getAssetEntryId()) ||
				(assetTagId != assetAutoTaggerEntry.getAssetTagId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETAUTOTAGGERENTRY_WHERE);

			query.append(_FINDER_COLUMN_A_A_ASSETENTRYID_2);

			query.append(_FINDER_COLUMN_A_A_ASSETTAGID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				qPos.add(assetTagId);

				List<AssetAutoTaggerEntry> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByA_A, finderArgs, list);
					}
				}
				else {
					AssetAutoTaggerEntry assetAutoTaggerEntry = list.get(0);

					result = assetAutoTaggerEntry;

					cacheResult(assetAutoTaggerEntry);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByA_A, finderArgs);
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
			return (AssetAutoTaggerEntry)result;
		}
	}

	/**
	 * Removes the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetTagId the asset tag ID
	 * @return the asset auto tagger entry that was removed
	 */
	@Override
	public AssetAutoTaggerEntry removeByA_A(long assetEntryId, long assetTagId)
		throws NoSuchEntryException {

		AssetAutoTaggerEntry assetAutoTaggerEntry = findByA_A(
			assetEntryId, assetTagId);

		return remove(assetAutoTaggerEntry);
	}

	/**
	 * Returns the number of asset auto tagger entries where assetEntryId = &#63; and assetTagId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetTagId the asset tag ID
	 * @return the number of matching asset auto tagger entries
	 */
	@Override
	public int countByA_A(long assetEntryId, long assetTagId) {
		FinderPath finderPath = _finderPathCountByA_A;

		Object[] finderArgs = new Object[] {assetEntryId, assetTagId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETAUTOTAGGERENTRY_WHERE);

			query.append(_FINDER_COLUMN_A_A_ASSETENTRYID_2);

			query.append(_FINDER_COLUMN_A_A_ASSETTAGID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				qPos.add(assetTagId);

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

	private static final String _FINDER_COLUMN_A_A_ASSETENTRYID_2 =
		"assetAutoTaggerEntry.assetEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_A_ASSETTAGID_2 =
		"assetAutoTaggerEntry.assetTagId = ?";

	public AssetAutoTaggerEntryPersistenceImpl() {
		setModelClass(AssetAutoTaggerEntry.class);

		setModelImplClass(AssetAutoTaggerEntryImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the asset auto tagger entry in the entity cache if it is enabled.
	 *
	 * @param assetAutoTaggerEntry the asset auto tagger entry
	 */
	@Override
	public void cacheResult(AssetAutoTaggerEntry assetAutoTaggerEntry) {
		entityCache.putResult(
			entityCacheEnabled, AssetAutoTaggerEntryImpl.class,
			assetAutoTaggerEntry.getPrimaryKey(), assetAutoTaggerEntry);

		finderCache.putResult(
			_finderPathFetchByA_A,
			new Object[] {
				assetAutoTaggerEntry.getAssetEntryId(),
				assetAutoTaggerEntry.getAssetTagId()
			},
			assetAutoTaggerEntry);

		assetAutoTaggerEntry.resetOriginalValues();
	}

	/**
	 * Caches the asset auto tagger entries in the entity cache if it is enabled.
	 *
	 * @param assetAutoTaggerEntries the asset auto tagger entries
	 */
	@Override
	public void cacheResult(List<AssetAutoTaggerEntry> assetAutoTaggerEntries) {
		for (AssetAutoTaggerEntry assetAutoTaggerEntry :
				assetAutoTaggerEntries) {

			if (entityCache.getResult(
					entityCacheEnabled, AssetAutoTaggerEntryImpl.class,
					assetAutoTaggerEntry.getPrimaryKey()) == null) {

				cacheResult(assetAutoTaggerEntry);
			}
			else {
				assetAutoTaggerEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset auto tagger entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetAutoTaggerEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset auto tagger entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetAutoTaggerEntry assetAutoTaggerEntry) {
		entityCache.removeResult(
			entityCacheEnabled, AssetAutoTaggerEntryImpl.class,
			assetAutoTaggerEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(AssetAutoTaggerEntryModelImpl)assetAutoTaggerEntry, true);
	}

	@Override
	public void clearCache(List<AssetAutoTaggerEntry> assetAutoTaggerEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetAutoTaggerEntry assetAutoTaggerEntry :
				assetAutoTaggerEntries) {

			entityCache.removeResult(
				entityCacheEnabled, AssetAutoTaggerEntryImpl.class,
				assetAutoTaggerEntry.getPrimaryKey());

			clearUniqueFindersCache(
				(AssetAutoTaggerEntryModelImpl)assetAutoTaggerEntry, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, AssetAutoTaggerEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetAutoTaggerEntryModelImpl assetAutoTaggerEntryModelImpl) {

		Object[] args = new Object[] {
			assetAutoTaggerEntryModelImpl.getAssetEntryId(),
			assetAutoTaggerEntryModelImpl.getAssetTagId()
		};

		finderCache.putResult(
			_finderPathCountByA_A, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByA_A, args, assetAutoTaggerEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AssetAutoTaggerEntryModelImpl assetAutoTaggerEntryModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetAutoTaggerEntryModelImpl.getAssetEntryId(),
				assetAutoTaggerEntryModelImpl.getAssetTagId()
			};

			finderCache.removeResult(_finderPathCountByA_A, args);
			finderCache.removeResult(_finderPathFetchByA_A, args);
		}

		if ((assetAutoTaggerEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByA_A.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetAutoTaggerEntryModelImpl.getOriginalAssetEntryId(),
				assetAutoTaggerEntryModelImpl.getOriginalAssetTagId()
			};

			finderCache.removeResult(_finderPathCountByA_A, args);
			finderCache.removeResult(_finderPathFetchByA_A, args);
		}
	}

	/**
	 * Creates a new asset auto tagger entry with the primary key. Does not add the asset auto tagger entry to the database.
	 *
	 * @param assetAutoTaggerEntryId the primary key for the new asset auto tagger entry
	 * @return the new asset auto tagger entry
	 */
	@Override
	public AssetAutoTaggerEntry create(long assetAutoTaggerEntryId) {
		AssetAutoTaggerEntry assetAutoTaggerEntry =
			new AssetAutoTaggerEntryImpl();

		assetAutoTaggerEntry.setNew(true);
		assetAutoTaggerEntry.setPrimaryKey(assetAutoTaggerEntryId);

		assetAutoTaggerEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return assetAutoTaggerEntry;
	}

	/**
	 * Removes the asset auto tagger entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	 * @return the asset auto tagger entry that was removed
	 * @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	 */
	@Override
	public AssetAutoTaggerEntry remove(long assetAutoTaggerEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)assetAutoTaggerEntryId);
	}

	/**
	 * Removes the asset auto tagger entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset auto tagger entry
	 * @return the asset auto tagger entry that was removed
	 * @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	 */
	@Override
	public AssetAutoTaggerEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			AssetAutoTaggerEntry assetAutoTaggerEntry =
				(AssetAutoTaggerEntry)session.get(
					AssetAutoTaggerEntryImpl.class, primaryKey);

			if (assetAutoTaggerEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetAutoTaggerEntry);
		}
		catch (NoSuchEntryException nsee) {
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
	protected AssetAutoTaggerEntry removeImpl(
		AssetAutoTaggerEntry assetAutoTaggerEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetAutoTaggerEntry)) {
				assetAutoTaggerEntry = (AssetAutoTaggerEntry)session.get(
					AssetAutoTaggerEntryImpl.class,
					assetAutoTaggerEntry.getPrimaryKeyObj());
			}

			if (assetAutoTaggerEntry != null) {
				session.delete(assetAutoTaggerEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetAutoTaggerEntry != null) {
			clearCache(assetAutoTaggerEntry);
		}

		return assetAutoTaggerEntry;
	}

	@Override
	public AssetAutoTaggerEntry updateImpl(
		AssetAutoTaggerEntry assetAutoTaggerEntry) {

		boolean isNew = assetAutoTaggerEntry.isNew();

		if (!(assetAutoTaggerEntry instanceof AssetAutoTaggerEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetAutoTaggerEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					assetAutoTaggerEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetAutoTaggerEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetAutoTaggerEntry implementation " +
					assetAutoTaggerEntry.getClass());
		}

		AssetAutoTaggerEntryModelImpl assetAutoTaggerEntryModelImpl =
			(AssetAutoTaggerEntryModelImpl)assetAutoTaggerEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (assetAutoTaggerEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetAutoTaggerEntry.setCreateDate(now);
			}
			else {
				assetAutoTaggerEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!assetAutoTaggerEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetAutoTaggerEntry.setModifiedDate(now);
			}
			else {
				assetAutoTaggerEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (assetAutoTaggerEntry.isNew()) {
				session.save(assetAutoTaggerEntry);

				assetAutoTaggerEntry.setNew(false);
			}
			else {
				assetAutoTaggerEntry = (AssetAutoTaggerEntry)session.merge(
					assetAutoTaggerEntry);
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
				assetAutoTaggerEntryModelImpl.getAssetEntryId()
			};

			finderCache.removeResult(_finderPathCountByAssetEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAssetEntryId, args);

			args = new Object[] {assetAutoTaggerEntryModelImpl.getAssetTagId()};

			finderCache.removeResult(_finderPathCountByAssetTagId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAssetTagId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((assetAutoTaggerEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAssetEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetAutoTaggerEntryModelImpl.getOriginalAssetEntryId()
				};

				finderCache.removeResult(_finderPathCountByAssetEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetEntryId, args);

				args = new Object[] {
					assetAutoTaggerEntryModelImpl.getAssetEntryId()
				};

				finderCache.removeResult(_finderPathCountByAssetEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetEntryId, args);
			}

			if ((assetAutoTaggerEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAssetTagId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetAutoTaggerEntryModelImpl.getOriginalAssetTagId()
				};

				finderCache.removeResult(_finderPathCountByAssetTagId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetTagId, args);

				args = new Object[] {
					assetAutoTaggerEntryModelImpl.getAssetTagId()
				};

				finderCache.removeResult(_finderPathCountByAssetTagId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetTagId, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, AssetAutoTaggerEntryImpl.class,
			assetAutoTaggerEntry.getPrimaryKey(), assetAutoTaggerEntry, false);

		clearUniqueFindersCache(assetAutoTaggerEntryModelImpl, false);
		cacheUniqueFindersCache(assetAutoTaggerEntryModelImpl);

		assetAutoTaggerEntry.resetOriginalValues();

		return assetAutoTaggerEntry;
	}

	/**
	 * Returns the asset auto tagger entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset auto tagger entry
	 * @return the asset auto tagger entry
	 * @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	 */
	@Override
	public AssetAutoTaggerEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		AssetAutoTaggerEntry assetAutoTaggerEntry = fetchByPrimaryKey(
			primaryKey);

		if (assetAutoTaggerEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetAutoTaggerEntry;
	}

	/**
	 * Returns the asset auto tagger entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	 * @return the asset auto tagger entry
	 * @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	 */
	@Override
	public AssetAutoTaggerEntry findByPrimaryKey(long assetAutoTaggerEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)assetAutoTaggerEntryId);
	}

	/**
	 * Returns the asset auto tagger entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	 * @return the asset auto tagger entry, or <code>null</code> if a asset auto tagger entry with the primary key could not be found
	 */
	@Override
	public AssetAutoTaggerEntry fetchByPrimaryKey(long assetAutoTaggerEntryId) {
		return fetchByPrimaryKey((Serializable)assetAutoTaggerEntryId);
	}

	/**
	 * Returns all the asset auto tagger entries.
	 *
	 * @return the asset auto tagger entries
	 */
	@Override
	public List<AssetAutoTaggerEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset auto tagger entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @return the range of asset auto tagger entries
	 */
	@Override
	public List<AssetAutoTaggerEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset auto tagger entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset auto tagger entries
	 */
	@Override
	public List<AssetAutoTaggerEntry> findAll(
		int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset auto tagger entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset auto tagger entries
	 */
	@Override
	public List<AssetAutoTaggerEntry> findAll(
		int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator,
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

		List<AssetAutoTaggerEntry> list = null;

		if (useFinderCache) {
			list = (List<AssetAutoTaggerEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ASSETAUTOTAGGERENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETAUTOTAGGERENTRY;

				sql = sql.concat(AssetAutoTaggerEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<AssetAutoTaggerEntry>)QueryUtil.list(
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
	 * Removes all the asset auto tagger entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetAutoTaggerEntry assetAutoTaggerEntry : findAll()) {
			remove(assetAutoTaggerEntry);
		}
	}

	/**
	 * Returns the number of asset auto tagger entries.
	 *
	 * @return the number of asset auto tagger entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETAUTOTAGGERENTRY);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "assetAutoTaggerEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETAUTOTAGGERENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AssetAutoTaggerEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset auto tagger entry persistence.
	 */
	@Activate
	public void activate() {
		AssetAutoTaggerEntryModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		AssetAutoTaggerEntryModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetAutoTaggerEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetAutoTaggerEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByAssetEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetAutoTaggerEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAssetEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetAutoTaggerEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetEntryId",
			new String[] {Long.class.getName()},
			AssetAutoTaggerEntryModelImpl.ASSETENTRYID_COLUMN_BITMASK |
			AssetAutoTaggerEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByAssetEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAssetEntryId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByAssetTagId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetAutoTaggerEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetTagId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAssetTagId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetAutoTaggerEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetTagId",
			new String[] {Long.class.getName()},
			AssetAutoTaggerEntryModelImpl.ASSETTAGID_COLUMN_BITMASK |
			AssetAutoTaggerEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByAssetTagId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAssetTagId",
			new String[] {Long.class.getName()});

		_finderPathFetchByA_A = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AssetAutoTaggerEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByA_A",
			new String[] {Long.class.getName(), Long.class.getName()},
			AssetAutoTaggerEntryModelImpl.ASSETENTRYID_COLUMN_BITMASK |
			AssetAutoTaggerEntryModelImpl.ASSETTAGID_COLUMN_BITMASK);

		_finderPathCountByA_A = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_A",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AssetAutoTaggerEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = AssetAutoTaggerPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry"),
			true);
	}

	@Override
	@Reference(
		target = AssetAutoTaggerPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AssetAutoTaggerPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_ASSETAUTOTAGGERENTRY =
		"SELECT assetAutoTaggerEntry FROM AssetAutoTaggerEntry assetAutoTaggerEntry";

	private static final String _SQL_SELECT_ASSETAUTOTAGGERENTRY_WHERE =
		"SELECT assetAutoTaggerEntry FROM AssetAutoTaggerEntry assetAutoTaggerEntry WHERE ";

	private static final String _SQL_COUNT_ASSETAUTOTAGGERENTRY =
		"SELECT COUNT(assetAutoTaggerEntry) FROM AssetAutoTaggerEntry assetAutoTaggerEntry";

	private static final String _SQL_COUNT_ASSETAUTOTAGGERENTRY_WHERE =
		"SELECT COUNT(assetAutoTaggerEntry) FROM AssetAutoTaggerEntry assetAutoTaggerEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"assetAutoTaggerEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetAutoTaggerEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetAutoTaggerEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetAutoTaggerEntryPersistenceImpl.class);

	static {
		try {
			Class.forName(AssetAutoTaggerPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}