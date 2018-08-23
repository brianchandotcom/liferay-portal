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

import com.liferay.asset.list.exception.NoSuchEntryLocalizationException;
import com.liferay.asset.list.model.AssetListEntryLocalization;
import com.liferay.asset.list.model.impl.AssetListEntryLocalizationImpl;
import com.liferay.asset.list.model.impl.AssetListEntryLocalizationModelImpl;
import com.liferay.asset.list.service.persistence.AssetListEntryLocalizationPersistence;

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
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the asset list entry localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryLocalizationPersistence
 * @see com.liferay.asset.list.service.persistence.AssetListEntryLocalizationUtil
 * @generated
 */
@ProviderType
public class AssetListEntryLocalizationPersistenceImpl
	extends BasePersistenceImpl<AssetListEntryLocalization>
	implements AssetListEntryLocalizationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetListEntryLocalizationUtil} to access the asset list entry localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetListEntryLocalizationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			AssetListEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			AssetListEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			AssetListEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			AssetListEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			AssetListEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ASSETLISTENTRYID =
		new FinderPath(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			AssetListEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			AssetListEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetListEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETLISTENTRYID =
		new FinderPath(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			AssetListEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			AssetListEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByAssetListEntryId", new String[] { Long.class.getName() },
			AssetListEntryLocalizationModelImpl.ASSETLISTENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ASSETLISTENTRYID = new FinderPath(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			AssetListEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetListEntryId", new String[] { Long.class.getName() });

	/**
	 * Returns all the asset list entry localizations where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the matching asset list entry localizations
	 */
	@Override
	public List<AssetListEntryLocalization> findByAssetListEntryId(
		long assetListEntryId) {
		return findByAssetListEntryId(assetListEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry localizations where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry localizations
	 * @param end the upper bound of the range of asset list entry localizations (not inclusive)
	 * @return the range of matching asset list entry localizations
	 */
	@Override
	public List<AssetListEntryLocalization> findByAssetListEntryId(
		long assetListEntryId, int start, int end) {
		return findByAssetListEntryId(assetListEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry localizations where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry localizations
	 * @param end the upper bound of the range of asset list entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry localizations
	 */
	@Override
	public List<AssetListEntryLocalization> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListEntryLocalization> orderByComparator) {
		return findByAssetListEntryId(assetListEntryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry localizations where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry localizations
	 * @param end the upper bound of the range of asset list entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset list entry localizations
	 */
	@Override
	public List<AssetListEntryLocalization> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListEntryLocalization> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETLISTENTRYID;
			finderArgs = new Object[] { assetListEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ASSETLISTENTRYID;
			finderArgs = new Object[] {
					assetListEntryId,
					
					start, end, orderByComparator
				};
		}

		List<AssetListEntryLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<AssetListEntryLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntryLocalization assetListEntryLocalization : list) {
					if ((assetListEntryId != assetListEntryLocalization.getAssetListEntryId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ASSETLISTENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_ASSETLISTENTRYID_ASSETLISTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetListEntryLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetListEntryId);

				if (!pagination) {
					list = (List<AssetListEntryLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetListEntryLocalization>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first asset list entry localization in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry localization
	 * @throws NoSuchEntryLocalizationException if a matching asset list entry localization could not be found
	 */
	@Override
	public AssetListEntryLocalization findByAssetListEntryId_First(
		long assetListEntryId,
		OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws NoSuchEntryLocalizationException {
		AssetListEntryLocalization assetListEntryLocalization = fetchByAssetListEntryId_First(assetListEntryId,
				orderByComparator);

		if (assetListEntryLocalization != null) {
			return assetListEntryLocalization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetListEntryId=");
		msg.append(assetListEntryId);

		msg.append("}");

		throw new NoSuchEntryLocalizationException(msg.toString());
	}

	/**
	 * Returns the first asset list entry localization in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	 */
	@Override
	public AssetListEntryLocalization fetchByAssetListEntryId_First(
		long assetListEntryId,
		OrderByComparator<AssetListEntryLocalization> orderByComparator) {
		List<AssetListEntryLocalization> list = findByAssetListEntryId(assetListEntryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry localization in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry localization
	 * @throws NoSuchEntryLocalizationException if a matching asset list entry localization could not be found
	 */
	@Override
	public AssetListEntryLocalization findByAssetListEntryId_Last(
		long assetListEntryId,
		OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws NoSuchEntryLocalizationException {
		AssetListEntryLocalization assetListEntryLocalization = fetchByAssetListEntryId_Last(assetListEntryId,
				orderByComparator);

		if (assetListEntryLocalization != null) {
			return assetListEntryLocalization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetListEntryId=");
		msg.append(assetListEntryId);

		msg.append("}");

		throw new NoSuchEntryLocalizationException(msg.toString());
	}

	/**
	 * Returns the last asset list entry localization in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	 */
	@Override
	public AssetListEntryLocalization fetchByAssetListEntryId_Last(
		long assetListEntryId,
		OrderByComparator<AssetListEntryLocalization> orderByComparator) {
		int count = countByAssetListEntryId(assetListEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetListEntryLocalization> list = findByAssetListEntryId(assetListEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry localizations before and after the current asset list entry localization in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryLocalizationId the primary key of the current asset list entry localization
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry localization
	 * @throws NoSuchEntryLocalizationException if a asset list entry localization with the primary key could not be found
	 */
	@Override
	public AssetListEntryLocalization[] findByAssetListEntryId_PrevAndNext(
		long assetListEntryLocalizationId, long assetListEntryId,
		OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws NoSuchEntryLocalizationException {
		AssetListEntryLocalization assetListEntryLocalization = findByPrimaryKey(assetListEntryLocalizationId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntryLocalization[] array = new AssetListEntryLocalizationImpl[3];

			array[0] = getByAssetListEntryId_PrevAndNext(session,
					assetListEntryLocalization, assetListEntryId,
					orderByComparator, true);

			array[1] = assetListEntryLocalization;

			array[2] = getByAssetListEntryId_PrevAndNext(session,
					assetListEntryLocalization, assetListEntryId,
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

	protected AssetListEntryLocalization getByAssetListEntryId_PrevAndNext(
		Session session, AssetListEntryLocalization assetListEntryLocalization,
		long assetListEntryId,
		OrderByComparator<AssetListEntryLocalization> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETLISTENTRYLOCALIZATION_WHERE);

		query.append(_FINDER_COLUMN_ASSETLISTENTRYID_ASSETLISTENTRYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(AssetListEntryLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(assetListEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetListEntryLocalization);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetListEntryLocalization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry localizations where assetListEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 */
	@Override
	public void removeByAssetListEntryId(long assetListEntryId) {
		for (AssetListEntryLocalization assetListEntryLocalization : findByAssetListEntryId(
				assetListEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetListEntryLocalization);
		}
	}

	/**
	 * Returns the number of asset list entry localizations where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the number of matching asset list entry localizations
	 */
	@Override
	public int countByAssetListEntryId(long assetListEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ASSETLISTENTRYID;

		Object[] finderArgs = new Object[] { assetListEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETLISTENTRYLOCALIZATION_WHERE);

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

	private static final String _FINDER_COLUMN_ASSETLISTENTRYID_ASSETLISTENTRYID_2 =
		"assetListEntryLocalization.assetListEntryId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_ASSETLISTENTRYID_LANGUAGEID =
		new FinderPath(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			AssetListEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			AssetListEntryLocalizationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByAssetListEntryId_LanguageId",
			new String[] { Long.class.getName(), String.class.getName() },
			AssetListEntryLocalizationModelImpl.ASSETLISTENTRYID_COLUMN_BITMASK |
			AssetListEntryLocalizationModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ASSETLISTENTRYID_LANGUAGEID =
		new FinderPath(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			AssetListEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetListEntryId_LanguageId",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the asset list entry localization where assetListEntryId = &#63; and languageId = &#63; or throws a {@link NoSuchEntryLocalizationException} if it could not be found.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param languageId the language ID
	 * @return the matching asset list entry localization
	 * @throws NoSuchEntryLocalizationException if a matching asset list entry localization could not be found
	 */
	@Override
	public AssetListEntryLocalization findByAssetListEntryId_LanguageId(
		long assetListEntryId, String languageId)
		throws NoSuchEntryLocalizationException {
		AssetListEntryLocalization assetListEntryLocalization = fetchByAssetListEntryId_LanguageId(assetListEntryId,
				languageId);

		if (assetListEntryLocalization == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetListEntryId=");
			msg.append(assetListEntryId);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEntryLocalizationException(msg.toString());
		}

		return assetListEntryLocalization;
	}

	/**
	 * Returns the asset list entry localization where assetListEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param languageId the language ID
	 * @return the matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	 */
	@Override
	public AssetListEntryLocalization fetchByAssetListEntryId_LanguageId(
		long assetListEntryId, String languageId) {
		return fetchByAssetListEntryId_LanguageId(assetListEntryId, languageId,
			true);
	}

	/**
	 * Returns the asset list entry localization where assetListEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	 */
	@Override
	public AssetListEntryLocalization fetchByAssetListEntryId_LanguageId(
		long assetListEntryId, String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { assetListEntryId, languageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_ASSETLISTENTRYID_LANGUAGEID,
					finderArgs, this);
		}

		if (result instanceof AssetListEntryLocalization) {
			AssetListEntryLocalization assetListEntryLocalization = (AssetListEntryLocalization)result;

			if ((assetListEntryId != assetListEntryLocalization.getAssetListEntryId()) ||
					!Objects.equals(languageId,
						assetListEntryLocalization.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETLISTENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_ASSETLISTENTRYID_LANGUAGEID_ASSETLISTENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_ASSETLISTENTRYID_LANGUAGEID_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_ASSETLISTENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_ASSETLISTENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetListEntryId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				List<AssetListEntryLocalization> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_ASSETLISTENTRYID_LANGUAGEID,
						finderArgs, list);
				}
				else {
					AssetListEntryLocalization assetListEntryLocalization = list.get(0);

					result = assetListEntryLocalization;

					cacheResult(assetListEntryLocalization);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_ASSETLISTENTRYID_LANGUAGEID,
					finderArgs);

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
			return (AssetListEntryLocalization)result;
		}
	}

	/**
	 * Removes the asset list entry localization where assetListEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param languageId the language ID
	 * @return the asset list entry localization that was removed
	 */
	@Override
	public AssetListEntryLocalization removeByAssetListEntryId_LanguageId(
		long assetListEntryId, String languageId)
		throws NoSuchEntryLocalizationException {
		AssetListEntryLocalization assetListEntryLocalization = findByAssetListEntryId_LanguageId(assetListEntryId,
				languageId);

		return remove(assetListEntryLocalization);
	}

	/**
	 * Returns the number of asset list entry localizations where assetListEntryId = &#63; and languageId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param languageId the language ID
	 * @return the number of matching asset list entry localizations
	 */
	@Override
	public int countByAssetListEntryId_LanguageId(long assetListEntryId,
		String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ASSETLISTENTRYID_LANGUAGEID;

		Object[] finderArgs = new Object[] { assetListEntryId, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLISTENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_ASSETLISTENTRYID_LANGUAGEID_ASSETLISTENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_ASSETLISTENTRYID_LANGUAGEID_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_ASSETLISTENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_ASSETLISTENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetListEntryId);

				if (bindLanguageId) {
					qPos.add(languageId);
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

	private static final String _FINDER_COLUMN_ASSETLISTENTRYID_LANGUAGEID_ASSETLISTENTRYID_2 =
		"assetListEntryLocalization.assetListEntryId = ? AND ";
	private static final String _FINDER_COLUMN_ASSETLISTENTRYID_LANGUAGEID_LANGUAGEID_1 =
		"assetListEntryLocalization.languageId IS NULL";
	private static final String _FINDER_COLUMN_ASSETLISTENTRYID_LANGUAGEID_LANGUAGEID_2 =
		"assetListEntryLocalization.languageId = ?";
	private static final String _FINDER_COLUMN_ASSETLISTENTRYID_LANGUAGEID_LANGUAGEID_3 =
		"(assetListEntryLocalization.languageId IS NULL OR assetListEntryLocalization.languageId = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKET = new FinderPath(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			AssetListEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			AssetListEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LikeT",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKET = new FinderPath(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			AssetListEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_LikeT",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the asset list entry localizations where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the matching asset list entry localizations
	 */
	@Override
	public List<AssetListEntryLocalization> findByG_LikeT(long groupId,
		String title) {
		return findByG_LikeT(groupId, title, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry localizations where groupId = &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param start the lower bound of the range of asset list entry localizations
	 * @param end the upper bound of the range of asset list entry localizations (not inclusive)
	 * @return the range of matching asset list entry localizations
	 */
	@Override
	public List<AssetListEntryLocalization> findByG_LikeT(long groupId,
		String title, int start, int end) {
		return findByG_LikeT(groupId, title, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry localizations where groupId = &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param start the lower bound of the range of asset list entry localizations
	 * @param end the upper bound of the range of asset list entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry localizations
	 */
	@Override
	public List<AssetListEntryLocalization> findByG_LikeT(long groupId,
		String title, int start, int end,
		OrderByComparator<AssetListEntryLocalization> orderByComparator) {
		return findByG_LikeT(groupId, title, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry localizations where groupId = &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param start the lower bound of the range of asset list entry localizations
	 * @param end the upper bound of the range of asset list entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset list entry localizations
	 */
	@Override
	public List<AssetListEntryLocalization> findByG_LikeT(long groupId,
		String title, int start, int end,
		OrderByComparator<AssetListEntryLocalization> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKET;
		finderArgs = new Object[] { groupId, title, start, end, orderByComparator };

		List<AssetListEntryLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<AssetListEntryLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntryLocalization assetListEntryLocalization : list) {
					if ((groupId != assetListEntryLocalization.getGroupId()) ||
							!StringUtil.wildcardMatches(
								assetListEntryLocalization.getTitle(), title,
								'_', '%', '\\', false)) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ASSETLISTENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_G_LIKET_GROUPID_2);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_G_LIKET_TITLE_1);
			}
			else if (title.equals("")) {
				query.append(_FINDER_COLUMN_G_LIKET_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_G_LIKET_TITLE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetListEntryLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindTitle) {
					qPos.add(StringUtil.toLowerCase(title));
				}

				if (!pagination) {
					list = (List<AssetListEntryLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetListEntryLocalization>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first asset list entry localization in the ordered set where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry localization
	 * @throws NoSuchEntryLocalizationException if a matching asset list entry localization could not be found
	 */
	@Override
	public AssetListEntryLocalization findByG_LikeT_First(long groupId,
		String title,
		OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws NoSuchEntryLocalizationException {
		AssetListEntryLocalization assetListEntryLocalization = fetchByG_LikeT_First(groupId,
				title, orderByComparator);

		if (assetListEntryLocalization != null) {
			return assetListEntryLocalization;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", title=");
		msg.append(title);

		msg.append("}");

		throw new NoSuchEntryLocalizationException(msg.toString());
	}

	/**
	 * Returns the first asset list entry localization in the ordered set where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	 */
	@Override
	public AssetListEntryLocalization fetchByG_LikeT_First(long groupId,
		String title,
		OrderByComparator<AssetListEntryLocalization> orderByComparator) {
		List<AssetListEntryLocalization> list = findByG_LikeT(groupId, title,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry localization in the ordered set where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry localization
	 * @throws NoSuchEntryLocalizationException if a matching asset list entry localization could not be found
	 */
	@Override
	public AssetListEntryLocalization findByG_LikeT_Last(long groupId,
		String title,
		OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws NoSuchEntryLocalizationException {
		AssetListEntryLocalization assetListEntryLocalization = fetchByG_LikeT_Last(groupId,
				title, orderByComparator);

		if (assetListEntryLocalization != null) {
			return assetListEntryLocalization;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", title=");
		msg.append(title);

		msg.append("}");

		throw new NoSuchEntryLocalizationException(msg.toString());
	}

	/**
	 * Returns the last asset list entry localization in the ordered set where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	 */
	@Override
	public AssetListEntryLocalization fetchByG_LikeT_Last(long groupId,
		String title,
		OrderByComparator<AssetListEntryLocalization> orderByComparator) {
		int count = countByG_LikeT(groupId, title);

		if (count == 0) {
			return null;
		}

		List<AssetListEntryLocalization> list = findByG_LikeT(groupId, title,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entry localizations before and after the current asset list entry localization in the ordered set where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param assetListEntryLocalizationId the primary key of the current asset list entry localization
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry localization
	 * @throws NoSuchEntryLocalizationException if a asset list entry localization with the primary key could not be found
	 */
	@Override
	public AssetListEntryLocalization[] findByG_LikeT_PrevAndNext(
		long assetListEntryLocalizationId, long groupId, String title,
		OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws NoSuchEntryLocalizationException {
		AssetListEntryLocalization assetListEntryLocalization = findByPrimaryKey(assetListEntryLocalizationId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntryLocalization[] array = new AssetListEntryLocalizationImpl[3];

			array[0] = getByG_LikeT_PrevAndNext(session,
					assetListEntryLocalization, groupId, title,
					orderByComparator, true);

			array[1] = assetListEntryLocalization;

			array[2] = getByG_LikeT_PrevAndNext(session,
					assetListEntryLocalization, groupId, title,
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

	protected AssetListEntryLocalization getByG_LikeT_PrevAndNext(
		Session session, AssetListEntryLocalization assetListEntryLocalization,
		long groupId, String title,
		OrderByComparator<AssetListEntryLocalization> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_ASSETLISTENTRYLOCALIZATION_WHERE);

		query.append(_FINDER_COLUMN_G_LIKET_GROUPID_2);

		boolean bindTitle = false;

		if (title == null) {
			query.append(_FINDER_COLUMN_G_LIKET_TITLE_1);
		}
		else if (title.equals("")) {
			query.append(_FINDER_COLUMN_G_LIKET_TITLE_3);
		}
		else {
			bindTitle = true;

			query.append(_FINDER_COLUMN_G_LIKET_TITLE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(AssetListEntryLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindTitle) {
			qPos.add(StringUtil.toLowerCase(title));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetListEntryLocalization);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetListEntryLocalization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entry localizations where groupId = &#63; and title LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 */
	@Override
	public void removeByG_LikeT(long groupId, String title) {
		for (AssetListEntryLocalization assetListEntryLocalization : findByG_LikeT(
				groupId, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetListEntryLocalization);
		}
	}

	/**
	 * Returns the number of asset list entry localizations where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the number of matching asset list entry localizations
	 */
	@Override
	public int countByG_LikeT(long groupId, String title) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKET;

		Object[] finderArgs = new Object[] { groupId, title };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLISTENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_G_LIKET_GROUPID_2);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_G_LIKET_TITLE_1);
			}
			else if (title.equals("")) {
				query.append(_FINDER_COLUMN_G_LIKET_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_G_LIKET_TITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindTitle) {
					qPos.add(StringUtil.toLowerCase(title));
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

	private static final String _FINDER_COLUMN_G_LIKET_GROUPID_2 = "assetListEntryLocalization.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_LIKET_TITLE_1 = "assetListEntryLocalization.title IS NULL";
	private static final String _FINDER_COLUMN_G_LIKET_TITLE_2 = "lower(assetListEntryLocalization.title) LIKE ?";
	private static final String _FINDER_COLUMN_G_LIKET_TITLE_3 = "(assetListEntryLocalization.title IS NULL OR assetListEntryLocalization.title LIKE '')";

	public AssetListEntryLocalizationPersistenceImpl() {
		setModelClass(AssetListEntryLocalization.class);
	}

	/**
	 * Caches the asset list entry localization in the entity cache if it is enabled.
	 *
	 * @param assetListEntryLocalization the asset list entry localization
	 */
	@Override
	public void cacheResult(
		AssetListEntryLocalization assetListEntryLocalization) {
		entityCache.putResult(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			AssetListEntryLocalizationImpl.class,
			assetListEntryLocalization.getPrimaryKey(),
			assetListEntryLocalization);

		finderCache.putResult(FINDER_PATH_FETCH_BY_ASSETLISTENTRYID_LANGUAGEID,
			new Object[] {
				assetListEntryLocalization.getAssetListEntryId(),
				assetListEntryLocalization.getLanguageId()
			}, assetListEntryLocalization);

		assetListEntryLocalization.resetOriginalValues();
	}

	/**
	 * Caches the asset list entry localizations in the entity cache if it is enabled.
	 *
	 * @param assetListEntryLocalizations the asset list entry localizations
	 */
	@Override
	public void cacheResult(
		List<AssetListEntryLocalization> assetListEntryLocalizations) {
		for (AssetListEntryLocalization assetListEntryLocalization : assetListEntryLocalizations) {
			if (entityCache.getResult(
						AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
						AssetListEntryLocalizationImpl.class,
						assetListEntryLocalization.getPrimaryKey()) == null) {
				cacheResult(assetListEntryLocalization);
			}
			else {
				assetListEntryLocalization.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset list entry localizations.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetListEntryLocalizationImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset list entry localization.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		AssetListEntryLocalization assetListEntryLocalization) {
		entityCache.removeResult(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			AssetListEntryLocalizationImpl.class,
			assetListEntryLocalization.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AssetListEntryLocalizationModelImpl)assetListEntryLocalization,
			true);
	}

	@Override
	public void clearCache(
		List<AssetListEntryLocalization> assetListEntryLocalizations) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetListEntryLocalization assetListEntryLocalization : assetListEntryLocalizations) {
			entityCache.removeResult(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
				AssetListEntryLocalizationImpl.class,
				assetListEntryLocalization.getPrimaryKey());

			clearUniqueFindersCache((AssetListEntryLocalizationModelImpl)assetListEntryLocalization,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetListEntryLocalizationModelImpl assetListEntryLocalizationModelImpl) {
		Object[] args = new Object[] {
				assetListEntryLocalizationModelImpl.getAssetListEntryId(),
				assetListEntryLocalizationModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_ASSETLISTENTRYID_LANGUAGEID,
			args, Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_ASSETLISTENTRYID_LANGUAGEID,
			args, assetListEntryLocalizationModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AssetListEntryLocalizationModelImpl assetListEntryLocalizationModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					assetListEntryLocalizationModelImpl.getAssetListEntryId(),
					assetListEntryLocalizationModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETLISTENTRYID_LANGUAGEID,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_ASSETLISTENTRYID_LANGUAGEID,
				args);
		}

		if ((assetListEntryLocalizationModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_ASSETLISTENTRYID_LANGUAGEID.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					assetListEntryLocalizationModelImpl.getOriginalAssetListEntryId(),
					assetListEntryLocalizationModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETLISTENTRYID_LANGUAGEID,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_ASSETLISTENTRYID_LANGUAGEID,
				args);
		}
	}

	/**
	 * Creates a new asset list entry localization with the primary key. Does not add the asset list entry localization to the database.
	 *
	 * @param assetListEntryLocalizationId the primary key for the new asset list entry localization
	 * @return the new asset list entry localization
	 */
	@Override
	public AssetListEntryLocalization create(long assetListEntryLocalizationId) {
		AssetListEntryLocalization assetListEntryLocalization = new AssetListEntryLocalizationImpl();

		assetListEntryLocalization.setNew(true);
		assetListEntryLocalization.setPrimaryKey(assetListEntryLocalizationId);

		assetListEntryLocalization.setCompanyId(companyProvider.getCompanyId());

		return assetListEntryLocalization;
	}

	/**
	 * Removes the asset list entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryLocalizationId the primary key of the asset list entry localization
	 * @return the asset list entry localization that was removed
	 * @throws NoSuchEntryLocalizationException if a asset list entry localization with the primary key could not be found
	 */
	@Override
	public AssetListEntryLocalization remove(long assetListEntryLocalizationId)
		throws NoSuchEntryLocalizationException {
		return remove((Serializable)assetListEntryLocalizationId);
	}

	/**
	 * Removes the asset list entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset list entry localization
	 * @return the asset list entry localization that was removed
	 * @throws NoSuchEntryLocalizationException if a asset list entry localization with the primary key could not be found
	 */
	@Override
	public AssetListEntryLocalization remove(Serializable primaryKey)
		throws NoSuchEntryLocalizationException {
		Session session = null;

		try {
			session = openSession();

			AssetListEntryLocalization assetListEntryLocalization = (AssetListEntryLocalization)session.get(AssetListEntryLocalizationImpl.class,
					primaryKey);

			if (assetListEntryLocalization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetListEntryLocalization);
		}
		catch (NoSuchEntryLocalizationException nsee) {
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
	protected AssetListEntryLocalization removeImpl(
		AssetListEntryLocalization assetListEntryLocalization) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetListEntryLocalization)) {
				assetListEntryLocalization = (AssetListEntryLocalization)session.get(AssetListEntryLocalizationImpl.class,
						assetListEntryLocalization.getPrimaryKeyObj());
			}

			if (assetListEntryLocalization != null) {
				session.delete(assetListEntryLocalization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetListEntryLocalization != null) {
			clearCache(assetListEntryLocalization);
		}

		return assetListEntryLocalization;
	}

	@Override
	public AssetListEntryLocalization updateImpl(
		AssetListEntryLocalization assetListEntryLocalization) {
		boolean isNew = assetListEntryLocalization.isNew();

		if (!(assetListEntryLocalization instanceof AssetListEntryLocalizationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetListEntryLocalization.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(assetListEntryLocalization);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetListEntryLocalization proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetListEntryLocalization implementation " +
				assetListEntryLocalization.getClass());
		}

		AssetListEntryLocalizationModelImpl assetListEntryLocalizationModelImpl = (AssetListEntryLocalizationModelImpl)assetListEntryLocalization;

		Session session = null;

		try {
			session = openSession();

			if (assetListEntryLocalization.isNew()) {
				session.save(assetListEntryLocalization);

				assetListEntryLocalization.setNew(false);
			}
			else {
				assetListEntryLocalization = (AssetListEntryLocalization)session.merge(assetListEntryLocalization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AssetListEntryLocalizationModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					assetListEntryLocalizationModelImpl.getAssetListEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETLISTENTRYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETLISTENTRYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((assetListEntryLocalizationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETLISTENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetListEntryLocalizationModelImpl.getOriginalAssetListEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETLISTENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETLISTENTRYID,
					args);

				args = new Object[] {
						assetListEntryLocalizationModelImpl.getAssetListEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETLISTENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETLISTENTRYID,
					args);
			}
		}

		entityCache.putResult(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			AssetListEntryLocalizationImpl.class,
			assetListEntryLocalization.getPrimaryKey(),
			assetListEntryLocalization, false);

		clearUniqueFindersCache(assetListEntryLocalizationModelImpl, false);
		cacheUniqueFindersCache(assetListEntryLocalizationModelImpl);

		assetListEntryLocalization.resetOriginalValues();

		return assetListEntryLocalization;
	}

	/**
	 * Returns the asset list entry localization with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset list entry localization
	 * @return the asset list entry localization
	 * @throws NoSuchEntryLocalizationException if a asset list entry localization with the primary key could not be found
	 */
	@Override
	public AssetListEntryLocalization findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryLocalizationException {
		AssetListEntryLocalization assetListEntryLocalization = fetchByPrimaryKey(primaryKey);

		if (assetListEntryLocalization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return assetListEntryLocalization;
	}

	/**
	 * Returns the asset list entry localization with the primary key or throws a {@link NoSuchEntryLocalizationException} if it could not be found.
	 *
	 * @param assetListEntryLocalizationId the primary key of the asset list entry localization
	 * @return the asset list entry localization
	 * @throws NoSuchEntryLocalizationException if a asset list entry localization with the primary key could not be found
	 */
	@Override
	public AssetListEntryLocalization findByPrimaryKey(
		long assetListEntryLocalizationId)
		throws NoSuchEntryLocalizationException {
		return findByPrimaryKey((Serializable)assetListEntryLocalizationId);
	}

	/**
	 * Returns the asset list entry localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset list entry localization
	 * @return the asset list entry localization, or <code>null</code> if a asset list entry localization with the primary key could not be found
	 */
	@Override
	public AssetListEntryLocalization fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
				AssetListEntryLocalizationImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AssetListEntryLocalization assetListEntryLocalization = (AssetListEntryLocalization)serializable;

		if (assetListEntryLocalization == null) {
			Session session = null;

			try {
				session = openSession();

				assetListEntryLocalization = (AssetListEntryLocalization)session.get(AssetListEntryLocalizationImpl.class,
						primaryKey);

				if (assetListEntryLocalization != null) {
					cacheResult(assetListEntryLocalization);
				}
				else {
					entityCache.putResult(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
						AssetListEntryLocalizationImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					AssetListEntryLocalizationImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return assetListEntryLocalization;
	}

	/**
	 * Returns the asset list entry localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetListEntryLocalizationId the primary key of the asset list entry localization
	 * @return the asset list entry localization, or <code>null</code> if a asset list entry localization with the primary key could not be found
	 */
	@Override
	public AssetListEntryLocalization fetchByPrimaryKey(
		long assetListEntryLocalizationId) {
		return fetchByPrimaryKey((Serializable)assetListEntryLocalizationId);
	}

	@Override
	public Map<Serializable, AssetListEntryLocalization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetListEntryLocalization> map = new HashMap<Serializable, AssetListEntryLocalization>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetListEntryLocalization assetListEntryLocalization = fetchByPrimaryKey(primaryKey);

			if (assetListEntryLocalization != null) {
				map.put(primaryKey, assetListEntryLocalization);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					AssetListEntryLocalizationImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (AssetListEntryLocalization)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ASSETLISTENTRYLOCALIZATION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
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

			for (AssetListEntryLocalization assetListEntryLocalization : (List<AssetListEntryLocalization>)q.list()) {
				map.put(assetListEntryLocalization.getPrimaryKeyObj(),
					assetListEntryLocalization);

				cacheResult(assetListEntryLocalization);

				uncachedPrimaryKeys.remove(assetListEntryLocalization.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(AssetListEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					AssetListEntryLocalizationImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the asset list entry localizations.
	 *
	 * @return the asset list entry localizations
	 */
	@Override
	public List<AssetListEntryLocalization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry localizations
	 * @param end the upper bound of the range of asset list entry localizations (not inclusive)
	 * @return the range of asset list entry localizations
	 */
	@Override
	public List<AssetListEntryLocalization> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry localizations
	 * @param end the upper bound of the range of asset list entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset list entry localizations
	 */
	@Override
	public List<AssetListEntryLocalization> findAll(int start, int end,
		OrderByComparator<AssetListEntryLocalization> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry localizations
	 * @param end the upper bound of the range of asset list entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of asset list entry localizations
	 */
	@Override
	public List<AssetListEntryLocalization> findAll(int start, int end,
		OrderByComparator<AssetListEntryLocalization> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<AssetListEntryLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<AssetListEntryLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ASSETLISTENTRYLOCALIZATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETLISTENTRYLOCALIZATION;

				if (pagination) {
					sql = sql.concat(AssetListEntryLocalizationModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AssetListEntryLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetListEntryLocalization>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the asset list entry localizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetListEntryLocalization assetListEntryLocalization : findAll()) {
			remove(assetListEntryLocalization);
		}
	}

	/**
	 * Returns the number of asset list entry localizations.
	 *
	 * @return the number of asset list entry localizations
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETLISTENTRYLOCALIZATION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AssetListEntryLocalizationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset list entry localization persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(AssetListEntryLocalizationImpl.class.getName());
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
	private static final String _SQL_SELECT_ASSETLISTENTRYLOCALIZATION = "SELECT assetListEntryLocalization FROM AssetListEntryLocalization assetListEntryLocalization";
	private static final String _SQL_SELECT_ASSETLISTENTRYLOCALIZATION_WHERE_PKS_IN =
		"SELECT assetListEntryLocalization FROM AssetListEntryLocalization assetListEntryLocalization WHERE assetListEntryLocalizationId IN (";
	private static final String _SQL_SELECT_ASSETLISTENTRYLOCALIZATION_WHERE = "SELECT assetListEntryLocalization FROM AssetListEntryLocalization assetListEntryLocalization WHERE ";
	private static final String _SQL_COUNT_ASSETLISTENTRYLOCALIZATION = "SELECT COUNT(assetListEntryLocalization) FROM AssetListEntryLocalization assetListEntryLocalization";
	private static final String _SQL_COUNT_ASSETLISTENTRYLOCALIZATION_WHERE = "SELECT COUNT(assetListEntryLocalization) FROM AssetListEntryLocalization assetListEntryLocalization WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetListEntryLocalization.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetListEntryLocalization exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetListEntryLocalization exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(AssetListEntryLocalizationPersistenceImpl.class);
}