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

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchRegionLocalizationException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.RegionLocalization;
import com.liferay.portal.kernel.model.RegionLocalizationTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.RegionLocalizationPersistence;
import com.liferay.portal.kernel.service.persistence.RegionLocalizationUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.RegionLocalizationImpl;
import com.liferay.portal.model.impl.RegionLocalizationModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the region localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RegionLocalizationPersistenceImpl
	extends BasePersistenceImpl<RegionLocalization>
	implements RegionLocalizationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RegionLocalizationUtil</code> to access the region localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RegionLocalizationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByRegionId;
	private FinderPath _finderPathWithoutPaginationFindByRegionId;
	private FinderPath _finderPathCountByRegionId;

	/**
	 * Returns all the region localizations where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @return the matching region localizations
	 */
	@Override
	public List<RegionLocalization> findByRegionId(long regionId) {
		return findByRegionId(
			regionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the region localizations where regionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegionLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param regionId the region ID
	 * @param start the lower bound of the range of region localizations
	 * @param end the upper bound of the range of region localizations (not inclusive)
	 * @return the range of matching region localizations
	 */
	@Override
	public List<RegionLocalization> findByRegionId(
		long regionId, int start, int end) {

		return findByRegionId(regionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the region localizations where regionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegionLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param regionId the region ID
	 * @param start the lower bound of the range of region localizations
	 * @param end the upper bound of the range of region localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching region localizations
	 */
	@Override
	public List<RegionLocalization> findByRegionId(
		long regionId, int start, int end,
		OrderByComparator<RegionLocalization> orderByComparator) {

		return findByRegionId(regionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the region localizations where regionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegionLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param regionId the region ID
	 * @param start the lower bound of the range of region localizations
	 * @param end the upper bound of the range of region localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching region localizations
	 */
	@Override
	public List<RegionLocalization> findByRegionId(
		long regionId, int start, int end,
		OrderByComparator<RegionLocalization> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByRegionId;
				finderArgs = new Object[] {regionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByRegionId;
			finderArgs = new Object[] {regionId, start, end, orderByComparator};
		}

		List<RegionLocalization> list = null;

		if (useFinderCache) {
			list = (List<RegionLocalization>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RegionLocalization regionLocalization : list) {
					if (regionId != regionLocalization.getRegionId()) {
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

			sb.append(_SQL_SELECT_REGIONLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_REGIONID_REGIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RegionLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(regionId);

				list = (List<RegionLocalization>)QueryUtil.list(
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

	/**
	 * Returns the first region localization in the ordered set where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching region localization
	 * @throws NoSuchRegionLocalizationException if a matching region localization could not be found
	 */
	@Override
	public RegionLocalization findByRegionId_First(
			long regionId,
			OrderByComparator<RegionLocalization> orderByComparator)
		throws NoSuchRegionLocalizationException {

		RegionLocalization regionLocalization = fetchByRegionId_First(
			regionId, orderByComparator);

		if (regionLocalization != null) {
			return regionLocalization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("regionId=");
		sb.append(regionId);

		sb.append("}");

		throw new NoSuchRegionLocalizationException(sb.toString());
	}

	/**
	 * Returns the first region localization in the ordered set where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching region localization, or <code>null</code> if a matching region localization could not be found
	 */
	@Override
	public RegionLocalization fetchByRegionId_First(
		long regionId,
		OrderByComparator<RegionLocalization> orderByComparator) {

		List<RegionLocalization> list = findByRegionId(
			regionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last region localization in the ordered set where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching region localization
	 * @throws NoSuchRegionLocalizationException if a matching region localization could not be found
	 */
	@Override
	public RegionLocalization findByRegionId_Last(
			long regionId,
			OrderByComparator<RegionLocalization> orderByComparator)
		throws NoSuchRegionLocalizationException {

		RegionLocalization regionLocalization = fetchByRegionId_Last(
			regionId, orderByComparator);

		if (regionLocalization != null) {
			return regionLocalization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("regionId=");
		sb.append(regionId);

		sb.append("}");

		throw new NoSuchRegionLocalizationException(sb.toString());
	}

	/**
	 * Returns the last region localization in the ordered set where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching region localization, or <code>null</code> if a matching region localization could not be found
	 */
	@Override
	public RegionLocalization fetchByRegionId_Last(
		long regionId,
		OrderByComparator<RegionLocalization> orderByComparator) {

		int count = countByRegionId(regionId);

		if (count == 0) {
			return null;
		}

		List<RegionLocalization> list = findByRegionId(
			regionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the region localizations before and after the current region localization in the ordered set where regionId = &#63;.
	 *
	 * @param regionLocalizationId the primary key of the current region localization
	 * @param regionId the region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next region localization
	 * @throws NoSuchRegionLocalizationException if a region localization with the primary key could not be found
	 */
	@Override
	public RegionLocalization[] findByRegionId_PrevAndNext(
			long regionLocalizationId, long regionId,
			OrderByComparator<RegionLocalization> orderByComparator)
		throws NoSuchRegionLocalizationException {

		RegionLocalization regionLocalization = findByPrimaryKey(
			regionLocalizationId);

		Session session = null;

		try {
			session = openSession();

			RegionLocalization[] array = new RegionLocalizationImpl[3];

			array[0] = getByRegionId_PrevAndNext(
				session, regionLocalization, regionId, orderByComparator, true);

			array[1] = regionLocalization;

			array[2] = getByRegionId_PrevAndNext(
				session, regionLocalization, regionId, orderByComparator,
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

	protected RegionLocalization getByRegionId_PrevAndNext(
		Session session, RegionLocalization regionLocalization, long regionId,
		OrderByComparator<RegionLocalization> orderByComparator,
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

		sb.append(_SQL_SELECT_REGIONLOCALIZATION_WHERE);

		sb.append(_FINDER_COLUMN_REGIONID_REGIONID_2);

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
			sb.append(RegionLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(regionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						regionLocalization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RegionLocalization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the region localizations where regionId = &#63; from the database.
	 *
	 * @param regionId the region ID
	 */
	@Override
	public void removeByRegionId(long regionId) {
		for (RegionLocalization regionLocalization :
				findByRegionId(
					regionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(regionLocalization);
		}
	}

	/**
	 * Returns the number of region localizations where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @return the number of matching region localizations
	 */
	@Override
	public int countByRegionId(long regionId) {
		FinderPath finderPath = _finderPathCountByRegionId;

		Object[] finderArgs = new Object[] {regionId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_REGIONLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_REGIONID_REGIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(regionId);

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

	private static final String _FINDER_COLUMN_REGIONID_REGIONID_2 =
		"regionLocalization.regionId = ?";

	private FinderPath _finderPathFetchByRegionId_LanguageId;
	private FinderPath _finderPathCountByRegionId_LanguageId;

	/**
	 * Returns the region localization where regionId = &#63; and languageId = &#63; or throws a <code>NoSuchRegionLocalizationException</code> if it could not be found.
	 *
	 * @param regionId the region ID
	 * @param languageId the language ID
	 * @return the matching region localization
	 * @throws NoSuchRegionLocalizationException if a matching region localization could not be found
	 */
	@Override
	public RegionLocalization findByRegionId_LanguageId(
			long regionId, String languageId)
		throws NoSuchRegionLocalizationException {

		RegionLocalization regionLocalization = fetchByRegionId_LanguageId(
			regionId, languageId);

		if (regionLocalization == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("regionId=");
			sb.append(regionId);

			sb.append(", languageId=");
			sb.append(languageId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchRegionLocalizationException(sb.toString());
		}

		return regionLocalization;
	}

	/**
	 * Returns the region localization where regionId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param regionId the region ID
	 * @param languageId the language ID
	 * @return the matching region localization, or <code>null</code> if a matching region localization could not be found
	 */
	@Override
	public RegionLocalization fetchByRegionId_LanguageId(
		long regionId, String languageId) {

		return fetchByRegionId_LanguageId(regionId, languageId, true);
	}

	/**
	 * Returns the region localization where regionId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param regionId the region ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching region localization, or <code>null</code> if a matching region localization could not be found
	 */
	@Override
	public RegionLocalization fetchByRegionId_LanguageId(
		long regionId, String languageId, boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {regionId, languageId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByRegionId_LanguageId, finderArgs, this);
		}

		if (result instanceof RegionLocalization) {
			RegionLocalization regionLocalization = (RegionLocalization)result;

			if ((regionId != regionLocalization.getRegionId()) ||
				!Objects.equals(
					languageId, regionLocalization.getLanguageId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_REGIONLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_REGIONID_LANGUAGEID_REGIONID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_REGIONID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_REGIONID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(regionId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				List<RegionLocalization> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByRegionId_LanguageId, finderArgs,
							list);
					}
				}
				else {
					RegionLocalization regionLocalization = list.get(0);

					result = regionLocalization;

					cacheResult(regionLocalization);
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
			return (RegionLocalization)result;
		}
	}

	/**
	 * Removes the region localization where regionId = &#63; and languageId = &#63; from the database.
	 *
	 * @param regionId the region ID
	 * @param languageId the language ID
	 * @return the region localization that was removed
	 */
	@Override
	public RegionLocalization removeByRegionId_LanguageId(
			long regionId, String languageId)
		throws NoSuchRegionLocalizationException {

		RegionLocalization regionLocalization = findByRegionId_LanguageId(
			regionId, languageId);

		return remove(regionLocalization);
	}

	/**
	 * Returns the number of region localizations where regionId = &#63; and languageId = &#63;.
	 *
	 * @param regionId the region ID
	 * @param languageId the language ID
	 * @return the number of matching region localizations
	 */
	@Override
	public int countByRegionId_LanguageId(long regionId, String languageId) {
		languageId = Objects.toString(languageId, "");

		FinderPath finderPath = _finderPathCountByRegionId_LanguageId;

		Object[] finderArgs = new Object[] {regionId, languageId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REGIONLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_REGIONID_LANGUAGEID_REGIONID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_REGIONID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_REGIONID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(regionId);

				if (bindLanguageId) {
					queryPos.add(languageId);
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

	private static final String _FINDER_COLUMN_REGIONID_LANGUAGEID_REGIONID_2 =
		"regionLocalization.regionId = ? AND ";

	private static final String
		_FINDER_COLUMN_REGIONID_LANGUAGEID_LANGUAGEID_2 =
			"regionLocalization.languageId = ?";

	private static final String
		_FINDER_COLUMN_REGIONID_LANGUAGEID_LANGUAGEID_3 =
			"(regionLocalization.languageId IS NULL OR regionLocalization.languageId = '')";

	public RegionLocalizationPersistenceImpl() {
		setModelClass(RegionLocalization.class);

		setModelImplClass(RegionLocalizationImpl.class);
		setModelPKClass(long.class);

		setTable(RegionLocalizationTable.INSTANCE);
	}

	/**
	 * Caches the region localization in the entity cache if it is enabled.
	 *
	 * @param regionLocalization the region localization
	 */
	@Override
	public void cacheResult(RegionLocalization regionLocalization) {
		EntityCacheUtil.putResult(
			RegionLocalizationImpl.class, regionLocalization.getPrimaryKey(),
			regionLocalization);

		FinderCacheUtil.putResult(
			_finderPathFetchByRegionId_LanguageId,
			new Object[] {
				regionLocalization.getRegionId(),
				regionLocalization.getLanguageId()
			},
			regionLocalization);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the region localizations in the entity cache if it is enabled.
	 *
	 * @param regionLocalizations the region localizations
	 */
	@Override
	public void cacheResult(List<RegionLocalization> regionLocalizations) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (regionLocalizations.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (RegionLocalization regionLocalization : regionLocalizations) {
			if (EntityCacheUtil.getResult(
					RegionLocalizationImpl.class,
					regionLocalization.getPrimaryKey()) == null) {

				cacheResult(regionLocalization);
			}
		}
	}

	/**
	 * Clears the cache for all region localizations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(RegionLocalizationImpl.class);

		FinderCacheUtil.clearCache(RegionLocalizationImpl.class);
	}

	/**
	 * Clears the cache for the region localization.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RegionLocalization regionLocalization) {
		EntityCacheUtil.removeResult(
			RegionLocalizationImpl.class, regionLocalization);
	}

	@Override
	public void clearCache(List<RegionLocalization> regionLocalizations) {
		for (RegionLocalization regionLocalization : regionLocalizations) {
			EntityCacheUtil.removeResult(
				RegionLocalizationImpl.class, regionLocalization);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(RegionLocalizationImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				RegionLocalizationImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		RegionLocalizationModelImpl regionLocalizationModelImpl) {

		Object[] args = new Object[] {
			regionLocalizationModelImpl.getRegionId(),
			regionLocalizationModelImpl.getLanguageId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByRegionId_LanguageId, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByRegionId_LanguageId, args,
			regionLocalizationModelImpl);
	}

	/**
	 * Creates a new region localization with the primary key. Does not add the region localization to the database.
	 *
	 * @param regionLocalizationId the primary key for the new region localization
	 * @return the new region localization
	 */
	@Override
	public RegionLocalization create(long regionLocalizationId) {
		RegionLocalization regionLocalization = new RegionLocalizationImpl();

		regionLocalization.setNew(true);
		regionLocalization.setPrimaryKey(regionLocalizationId);

		regionLocalization.setCompanyId(CompanyThreadLocal.getCompanyId());

		return regionLocalization;
	}

	/**
	 * Removes the region localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param regionLocalizationId the primary key of the region localization
	 * @return the region localization that was removed
	 * @throws NoSuchRegionLocalizationException if a region localization with the primary key could not be found
	 */
	@Override
	public RegionLocalization remove(long regionLocalizationId)
		throws NoSuchRegionLocalizationException {

		return remove((Serializable)regionLocalizationId);
	}

	/**
	 * Removes the region localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the region localization
	 * @return the region localization that was removed
	 * @throws NoSuchRegionLocalizationException if a region localization with the primary key could not be found
	 */
	@Override
	public RegionLocalization remove(Serializable primaryKey)
		throws NoSuchRegionLocalizationException {

		Session session = null;

		try {
			session = openSession();

			RegionLocalization regionLocalization =
				(RegionLocalization)session.get(
					RegionLocalizationImpl.class, primaryKey);

			if (regionLocalization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRegionLocalizationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(regionLocalization);
		}
		catch (NoSuchRegionLocalizationException noSuchEntityException) {
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
	protected RegionLocalization removeImpl(
		RegionLocalization regionLocalization) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(regionLocalization)) {
				regionLocalization = (RegionLocalization)session.get(
					RegionLocalizationImpl.class,
					regionLocalization.getPrimaryKeyObj());
			}

			if (regionLocalization != null) {
				session.delete(regionLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (regionLocalization != null) {
			clearCache(regionLocalization);
		}

		return regionLocalization;
	}

	@Override
	public RegionLocalization updateImpl(
		RegionLocalization regionLocalization) {

		boolean isNew = regionLocalization.isNew();

		if (!(regionLocalization instanceof RegionLocalizationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(regionLocalization.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					regionLocalization);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in regionLocalization proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RegionLocalization implementation " +
					regionLocalization.getClass());
		}

		RegionLocalizationModelImpl regionLocalizationModelImpl =
			(RegionLocalizationModelImpl)regionLocalization;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(regionLocalization);
			}
			else {
				regionLocalization = (RegionLocalization)session.merge(
					regionLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			RegionLocalizationImpl.class, regionLocalizationModelImpl, false,
			true);

		cacheUniqueFindersCache(regionLocalizationModelImpl);

		if (isNew) {
			regionLocalization.setNew(false);
		}

		regionLocalization.resetOriginalValues();

		return regionLocalization;
	}

	/**
	 * Returns the region localization with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the region localization
	 * @return the region localization
	 * @throws NoSuchRegionLocalizationException if a region localization with the primary key could not be found
	 */
	@Override
	public RegionLocalization findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRegionLocalizationException {

		RegionLocalization regionLocalization = fetchByPrimaryKey(primaryKey);

		if (regionLocalization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRegionLocalizationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return regionLocalization;
	}

	/**
	 * Returns the region localization with the primary key or throws a <code>NoSuchRegionLocalizationException</code> if it could not be found.
	 *
	 * @param regionLocalizationId the primary key of the region localization
	 * @return the region localization
	 * @throws NoSuchRegionLocalizationException if a region localization with the primary key could not be found
	 */
	@Override
	public RegionLocalization findByPrimaryKey(long regionLocalizationId)
		throws NoSuchRegionLocalizationException {

		return findByPrimaryKey((Serializable)regionLocalizationId);
	}

	/**
	 * Returns the region localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param regionLocalizationId the primary key of the region localization
	 * @return the region localization, or <code>null</code> if a region localization with the primary key could not be found
	 */
	@Override
	public RegionLocalization fetchByPrimaryKey(long regionLocalizationId) {
		return fetchByPrimaryKey((Serializable)regionLocalizationId);
	}

	/**
	 * Returns all the region localizations.
	 *
	 * @return the region localizations
	 */
	@Override
	public List<RegionLocalization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the region localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegionLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of region localizations
	 * @param end the upper bound of the range of region localizations (not inclusive)
	 * @return the range of region localizations
	 */
	@Override
	public List<RegionLocalization> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the region localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegionLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of region localizations
	 * @param end the upper bound of the range of region localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of region localizations
	 */
	@Override
	public List<RegionLocalization> findAll(
		int start, int end,
		OrderByComparator<RegionLocalization> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the region localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegionLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of region localizations
	 * @param end the upper bound of the range of region localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of region localizations
	 */
	@Override
	public List<RegionLocalization> findAll(
		int start, int end,
		OrderByComparator<RegionLocalization> orderByComparator,
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

		List<RegionLocalization> list = null;

		if (useFinderCache) {
			list = (List<RegionLocalization>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_REGIONLOCALIZATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_REGIONLOCALIZATION;

				sql = sql.concat(RegionLocalizationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RegionLocalization>)QueryUtil.list(
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

	/**
	 * Removes all the region localizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RegionLocalization regionLocalization : findAll()) {
			remove(regionLocalization);
		}
	}

	/**
	 * Returns the number of region localizations.
	 *
	 * @return the number of region localizations
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_REGIONLOCALIZATION);

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

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "regionLocalizationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_REGIONLOCALIZATION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RegionLocalizationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the region localization persistence.
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

		_finderPathWithPaginationFindByRegionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRegionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"regionId"}, true);

		_finderPathWithoutPaginationFindByRegionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRegionId",
			new String[] {Long.class.getName()}, new String[] {"regionId"},
			true);

		_finderPathCountByRegionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRegionId",
			new String[] {Long.class.getName()}, new String[] {"regionId"},
			false);

		_finderPathFetchByRegionId_LanguageId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByRegionId_LanguageId",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"regionId", "languageId"}, true);

		_finderPathCountByRegionId_LanguageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByRegionId_LanguageId",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"regionId", "languageId"}, false);

		RegionLocalizationUtil.setPersistence(this);
	}

	public void destroy() {
		RegionLocalizationUtil.setPersistence(null);

		EntityCacheUtil.removeCache(RegionLocalizationImpl.class.getName());
	}

	private static final String _SQL_SELECT_REGIONLOCALIZATION =
		"SELECT regionLocalization FROM RegionLocalization regionLocalization";

	private static final String _SQL_SELECT_REGIONLOCALIZATION_WHERE =
		"SELECT regionLocalization FROM RegionLocalization regionLocalization WHERE ";

	private static final String _SQL_COUNT_REGIONLOCALIZATION =
		"SELECT COUNT(regionLocalization) FROM RegionLocalization regionLocalization";

	private static final String _SQL_COUNT_REGIONLOCALIZATION_WHERE =
		"SELECT COUNT(regionLocalization) FROM RegionLocalization regionLocalization WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "regionLocalization.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RegionLocalization exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RegionLocalization exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RegionLocalizationPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}