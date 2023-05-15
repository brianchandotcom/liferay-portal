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
import com.liferay.portal.kernel.exception.NoSuchCountryLocalizationException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CountryLocalization;
import com.liferay.portal.kernel.model.CountryLocalizationTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.CountryLocalizationPersistence;
import com.liferay.portal.kernel.service.persistence.CountryLocalizationUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.CountryLocalizationImpl;
import com.liferay.portal.model.impl.CountryLocalizationModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the country localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CountryLocalizationPersistenceImpl
	extends BasePersistenceImpl<CountryLocalization>
	implements CountryLocalizationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CountryLocalizationUtil</code> to access the country localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CountryLocalizationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCountryId;
	private FinderPath _finderPathWithoutPaginationFindByCountryId;
	private FinderPath _finderPathCountByCountryId;

	/**
	 * Returns all the country localizations where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @return the matching country localizations
	 */
	@Override
	public List<CountryLocalization> findByCountryId(long countryId) {
		return findByCountryId(
			countryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the country localizations where countryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param start the lower bound of the range of country localizations
	 * @param end the upper bound of the range of country localizations (not inclusive)
	 * @return the range of matching country localizations
	 */
	@Override
	public List<CountryLocalization> findByCountryId(
		long countryId, int start, int end) {

		return findByCountryId(countryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the country localizations where countryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param start the lower bound of the range of country localizations
	 * @param end the upper bound of the range of country localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching country localizations
	 */
	@Override
	public List<CountryLocalization> findByCountryId(
		long countryId, int start, int end,
		OrderByComparator<CountryLocalization> orderByComparator) {

		return findByCountryId(countryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the country localizations where countryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param start the lower bound of the range of country localizations
	 * @param end the upper bound of the range of country localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching country localizations
	 */
	@Override
	public List<CountryLocalization> findByCountryId(
		long countryId, int start, int end,
		OrderByComparator<CountryLocalization> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCountryId;
				finderArgs = new Object[] {countryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCountryId;
			finderArgs = new Object[] {
				countryId, start, end, orderByComparator
			};
		}

		List<CountryLocalization> list = null;

		if (useFinderCache) {
			list = (List<CountryLocalization>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CountryLocalization countryLocalization : list) {
					if (countryId != countryLocalization.getCountryId()) {
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

			sb.append(_SQL_SELECT_COUNTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_COUNTRYID_COUNTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CountryLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(countryId);

				list = (List<CountryLocalization>)QueryUtil.list(
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
	 * Returns the first country localization in the ordered set where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country localization
	 * @throws NoSuchCountryLocalizationException if a matching country localization could not be found
	 */
	@Override
	public CountryLocalization findByCountryId_First(
			long countryId,
			OrderByComparator<CountryLocalization> orderByComparator)
		throws NoSuchCountryLocalizationException {

		CountryLocalization countryLocalization = fetchByCountryId_First(
			countryId, orderByComparator);

		if (countryLocalization != null) {
			return countryLocalization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("countryId=");
		sb.append(countryId);

		sb.append("}");

		throw new NoSuchCountryLocalizationException(sb.toString());
	}

	/**
	 * Returns the first country localization in the ordered set where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country localization, or <code>null</code> if a matching country localization could not be found
	 */
	@Override
	public CountryLocalization fetchByCountryId_First(
		long countryId,
		OrderByComparator<CountryLocalization> orderByComparator) {

		List<CountryLocalization> list = findByCountryId(
			countryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last country localization in the ordered set where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country localization
	 * @throws NoSuchCountryLocalizationException if a matching country localization could not be found
	 */
	@Override
	public CountryLocalization findByCountryId_Last(
			long countryId,
			OrderByComparator<CountryLocalization> orderByComparator)
		throws NoSuchCountryLocalizationException {

		CountryLocalization countryLocalization = fetchByCountryId_Last(
			countryId, orderByComparator);

		if (countryLocalization != null) {
			return countryLocalization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("countryId=");
		sb.append(countryId);

		sb.append("}");

		throw new NoSuchCountryLocalizationException(sb.toString());
	}

	/**
	 * Returns the last country localization in the ordered set where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country localization, or <code>null</code> if a matching country localization could not be found
	 */
	@Override
	public CountryLocalization fetchByCountryId_Last(
		long countryId,
		OrderByComparator<CountryLocalization> orderByComparator) {

		int count = countByCountryId(countryId);

		if (count == 0) {
			return null;
		}

		List<CountryLocalization> list = findByCountryId(
			countryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the country localizations before and after the current country localization in the ordered set where countryId = &#63;.
	 *
	 * @param countryLocalizationId the primary key of the current country localization
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next country localization
	 * @throws NoSuchCountryLocalizationException if a country localization with the primary key could not be found
	 */
	@Override
	public CountryLocalization[] findByCountryId_PrevAndNext(
			long countryLocalizationId, long countryId,
			OrderByComparator<CountryLocalization> orderByComparator)
		throws NoSuchCountryLocalizationException {

		CountryLocalization countryLocalization = findByPrimaryKey(
			countryLocalizationId);

		Session session = null;

		try {
			session = openSession();

			CountryLocalization[] array = new CountryLocalizationImpl[3];

			array[0] = getByCountryId_PrevAndNext(
				session, countryLocalization, countryId, orderByComparator,
				true);

			array[1] = countryLocalization;

			array[2] = getByCountryId_PrevAndNext(
				session, countryLocalization, countryId, orderByComparator,
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

	protected CountryLocalization getByCountryId_PrevAndNext(
		Session session, CountryLocalization countryLocalization,
		long countryId,
		OrderByComparator<CountryLocalization> orderByComparator,
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

		sb.append(_SQL_SELECT_COUNTRYLOCALIZATION_WHERE);

		sb.append(_FINDER_COLUMN_COUNTRYID_COUNTRYID_2);

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
			sb.append(CountryLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(countryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						countryLocalization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CountryLocalization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the country localizations where countryId = &#63; from the database.
	 *
	 * @param countryId the country ID
	 */
	@Override
	public void removeByCountryId(long countryId) {
		for (CountryLocalization countryLocalization :
				findByCountryId(
					countryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(countryLocalization);
		}
	}

	/**
	 * Returns the number of country localizations where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @return the number of matching country localizations
	 */
	@Override
	public int countByCountryId(long countryId) {
		FinderPath finderPath = _finderPathCountByCountryId;

		Object[] finderArgs = new Object[] {countryId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COUNTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_COUNTRYID_COUNTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(countryId);

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

	private static final String _FINDER_COLUMN_COUNTRYID_COUNTRYID_2 =
		"countryLocalization.countryId = ?";

	private FinderPath _finderPathFetchByCountryId_LanguageId;
	private FinderPath _finderPathCountByCountryId_LanguageId;

	/**
	 * Returns the country localization where countryId = &#63; and languageId = &#63; or throws a <code>NoSuchCountryLocalizationException</code> if it could not be found.
	 *
	 * @param countryId the country ID
	 * @param languageId the language ID
	 * @return the matching country localization
	 * @throws NoSuchCountryLocalizationException if a matching country localization could not be found
	 */
	@Override
	public CountryLocalization findByCountryId_LanguageId(
			long countryId, String languageId)
		throws NoSuchCountryLocalizationException {

		CountryLocalization countryLocalization = fetchByCountryId_LanguageId(
			countryId, languageId);

		if (countryLocalization == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("countryId=");
			sb.append(countryId);

			sb.append(", languageId=");
			sb.append(languageId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCountryLocalizationException(sb.toString());
		}

		return countryLocalization;
	}

	/**
	 * Returns the country localization where countryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param countryId the country ID
	 * @param languageId the language ID
	 * @return the matching country localization, or <code>null</code> if a matching country localization could not be found
	 */
	@Override
	public CountryLocalization fetchByCountryId_LanguageId(
		long countryId, String languageId) {

		return fetchByCountryId_LanguageId(countryId, languageId, true);
	}

	/**
	 * Returns the country localization where countryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param countryId the country ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching country localization, or <code>null</code> if a matching country localization could not be found
	 */
	@Override
	public CountryLocalization fetchByCountryId_LanguageId(
		long countryId, String languageId, boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {countryId, languageId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByCountryId_LanguageId, finderArgs, this);
		}

		if (result instanceof CountryLocalization) {
			CountryLocalization countryLocalization =
				(CountryLocalization)result;

			if ((countryId != countryLocalization.getCountryId()) ||
				!Objects.equals(
					languageId, countryLocalization.getLanguageId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COUNTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_COUNTRYID_LANGUAGEID_COUNTRYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_COUNTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_COUNTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(countryId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				List<CountryLocalization> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByCountryId_LanguageId, finderArgs,
							list);
					}
				}
				else {
					CountryLocalization countryLocalization = list.get(0);

					result = countryLocalization;

					cacheResult(countryLocalization);
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
			return (CountryLocalization)result;
		}
	}

	/**
	 * Removes the country localization where countryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param countryId the country ID
	 * @param languageId the language ID
	 * @return the country localization that was removed
	 */
	@Override
	public CountryLocalization removeByCountryId_LanguageId(
			long countryId, String languageId)
		throws NoSuchCountryLocalizationException {

		CountryLocalization countryLocalization = findByCountryId_LanguageId(
			countryId, languageId);

		return remove(countryLocalization);
	}

	/**
	 * Returns the number of country localizations where countryId = &#63; and languageId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param languageId the language ID
	 * @return the number of matching country localizations
	 */
	@Override
	public int countByCountryId_LanguageId(long countryId, String languageId) {
		languageId = Objects.toString(languageId, "");

		FinderPath finderPath = _finderPathCountByCountryId_LanguageId;

		Object[] finderArgs = new Object[] {countryId, languageId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COUNTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_COUNTRYID_LANGUAGEID_COUNTRYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_COUNTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_COUNTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(countryId);

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

	private static final String
		_FINDER_COLUMN_COUNTRYID_LANGUAGEID_COUNTRYID_2 =
			"countryLocalization.countryId = ? AND ";

	private static final String
		_FINDER_COLUMN_COUNTRYID_LANGUAGEID_LANGUAGEID_2 =
			"countryLocalization.languageId = ?";

	private static final String
		_FINDER_COLUMN_COUNTRYID_LANGUAGEID_LANGUAGEID_3 =
			"(countryLocalization.languageId IS NULL OR countryLocalization.languageId = '')";

	public CountryLocalizationPersistenceImpl() {
		setModelClass(CountryLocalization.class);

		setModelImplClass(CountryLocalizationImpl.class);
		setModelPKClass(long.class);

		setTable(CountryLocalizationTable.INSTANCE);
	}

	/**
	 * Caches the country localization in the entity cache if it is enabled.
	 *
	 * @param countryLocalization the country localization
	 */
	@Override
	public void cacheResult(CountryLocalization countryLocalization) {
		EntityCacheUtil.putResult(
			CountryLocalizationImpl.class, countryLocalization.getPrimaryKey(),
			countryLocalization);

		FinderCacheUtil.putResult(
			_finderPathFetchByCountryId_LanguageId,
			new Object[] {
				countryLocalization.getCountryId(),
				countryLocalization.getLanguageId()
			},
			countryLocalization);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the country localizations in the entity cache if it is enabled.
	 *
	 * @param countryLocalizations the country localizations
	 */
	@Override
	public void cacheResult(List<CountryLocalization> countryLocalizations) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (countryLocalizations.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CountryLocalization countryLocalization : countryLocalizations) {
			if (EntityCacheUtil.getResult(
					CountryLocalizationImpl.class,
					countryLocalization.getPrimaryKey()) == null) {

				cacheResult(countryLocalization);
			}
		}
	}

	/**
	 * Clears the cache for all country localizations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(CountryLocalizationImpl.class);

		FinderCacheUtil.clearCache(CountryLocalizationImpl.class);
	}

	/**
	 * Clears the cache for the country localization.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CountryLocalization countryLocalization) {
		EntityCacheUtil.removeResult(
			CountryLocalizationImpl.class, countryLocalization);
	}

	@Override
	public void clearCache(List<CountryLocalization> countryLocalizations) {
		for (CountryLocalization countryLocalization : countryLocalizations) {
			EntityCacheUtil.removeResult(
				CountryLocalizationImpl.class, countryLocalization);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(CountryLocalizationImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				CountryLocalizationImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CountryLocalizationModelImpl countryLocalizationModelImpl) {

		Object[] args = new Object[] {
			countryLocalizationModelImpl.getCountryId(),
			countryLocalizationModelImpl.getLanguageId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByCountryId_LanguageId, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByCountryId_LanguageId, args,
			countryLocalizationModelImpl);
	}

	/**
	 * Creates a new country localization with the primary key. Does not add the country localization to the database.
	 *
	 * @param countryLocalizationId the primary key for the new country localization
	 * @return the new country localization
	 */
	@Override
	public CountryLocalization create(long countryLocalizationId) {
		CountryLocalization countryLocalization = new CountryLocalizationImpl();

		countryLocalization.setNew(true);
		countryLocalization.setPrimaryKey(countryLocalizationId);

		countryLocalization.setCompanyId(CompanyThreadLocal.getCompanyId());

		return countryLocalization;
	}

	/**
	 * Removes the country localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param countryLocalizationId the primary key of the country localization
	 * @return the country localization that was removed
	 * @throws NoSuchCountryLocalizationException if a country localization with the primary key could not be found
	 */
	@Override
	public CountryLocalization remove(long countryLocalizationId)
		throws NoSuchCountryLocalizationException {

		return remove((Serializable)countryLocalizationId);
	}

	/**
	 * Removes the country localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the country localization
	 * @return the country localization that was removed
	 * @throws NoSuchCountryLocalizationException if a country localization with the primary key could not be found
	 */
	@Override
	public CountryLocalization remove(Serializable primaryKey)
		throws NoSuchCountryLocalizationException {

		Session session = null;

		try {
			session = openSession();

			CountryLocalization countryLocalization =
				(CountryLocalization)session.get(
					CountryLocalizationImpl.class, primaryKey);

			if (countryLocalization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCountryLocalizationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(countryLocalization);
		}
		catch (NoSuchCountryLocalizationException noSuchEntityException) {
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
	protected CountryLocalization removeImpl(
		CountryLocalization countryLocalization) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(countryLocalization)) {
				countryLocalization = (CountryLocalization)session.get(
					CountryLocalizationImpl.class,
					countryLocalization.getPrimaryKeyObj());
			}

			if (countryLocalization != null) {
				session.delete(countryLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (countryLocalization != null) {
			clearCache(countryLocalization);
		}

		return countryLocalization;
	}

	@Override
	public CountryLocalization updateImpl(
		CountryLocalization countryLocalization) {

		boolean isNew = countryLocalization.isNew();

		if (!(countryLocalization instanceof CountryLocalizationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(countryLocalization.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					countryLocalization);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in countryLocalization proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CountryLocalization implementation " +
					countryLocalization.getClass());
		}

		CountryLocalizationModelImpl countryLocalizationModelImpl =
			(CountryLocalizationModelImpl)countryLocalization;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(countryLocalization);
			}
			else {
				countryLocalization = (CountryLocalization)session.merge(
					countryLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			CountryLocalizationImpl.class, countryLocalizationModelImpl, false,
			true);

		cacheUniqueFindersCache(countryLocalizationModelImpl);

		if (isNew) {
			countryLocalization.setNew(false);
		}

		countryLocalization.resetOriginalValues();

		return countryLocalization;
	}

	/**
	 * Returns the country localization with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the country localization
	 * @return the country localization
	 * @throws NoSuchCountryLocalizationException if a country localization with the primary key could not be found
	 */
	@Override
	public CountryLocalization findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCountryLocalizationException {

		CountryLocalization countryLocalization = fetchByPrimaryKey(primaryKey);

		if (countryLocalization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCountryLocalizationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return countryLocalization;
	}

	/**
	 * Returns the country localization with the primary key or throws a <code>NoSuchCountryLocalizationException</code> if it could not be found.
	 *
	 * @param countryLocalizationId the primary key of the country localization
	 * @return the country localization
	 * @throws NoSuchCountryLocalizationException if a country localization with the primary key could not be found
	 */
	@Override
	public CountryLocalization findByPrimaryKey(long countryLocalizationId)
		throws NoSuchCountryLocalizationException {

		return findByPrimaryKey((Serializable)countryLocalizationId);
	}

	/**
	 * Returns the country localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param countryLocalizationId the primary key of the country localization
	 * @return the country localization, or <code>null</code> if a country localization with the primary key could not be found
	 */
	@Override
	public CountryLocalization fetchByPrimaryKey(long countryLocalizationId) {
		return fetchByPrimaryKey((Serializable)countryLocalizationId);
	}

	/**
	 * Returns all the country localizations.
	 *
	 * @return the country localizations
	 */
	@Override
	public List<CountryLocalization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the country localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of country localizations
	 * @param end the upper bound of the range of country localizations (not inclusive)
	 * @return the range of country localizations
	 */
	@Override
	public List<CountryLocalization> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the country localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of country localizations
	 * @param end the upper bound of the range of country localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of country localizations
	 */
	@Override
	public List<CountryLocalization> findAll(
		int start, int end,
		OrderByComparator<CountryLocalization> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the country localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of country localizations
	 * @param end the upper bound of the range of country localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of country localizations
	 */
	@Override
	public List<CountryLocalization> findAll(
		int start, int end,
		OrderByComparator<CountryLocalization> orderByComparator,
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

		List<CountryLocalization> list = null;

		if (useFinderCache) {
			list = (List<CountryLocalization>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COUNTRYLOCALIZATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COUNTRYLOCALIZATION;

				sql = sql.concat(CountryLocalizationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CountryLocalization>)QueryUtil.list(
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
	 * Removes all the country localizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CountryLocalization countryLocalization : findAll()) {
			remove(countryLocalization);
		}
	}

	/**
	 * Returns the number of country localizations.
	 *
	 * @return the number of country localizations
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
					_SQL_COUNT_COUNTRYLOCALIZATION);

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
		return "countryLocalizationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COUNTRYLOCALIZATION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CountryLocalizationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the country localization persistence.
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

		_finderPathWithPaginationFindByCountryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCountryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"countryId"}, true);

		_finderPathWithoutPaginationFindByCountryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCountryId",
			new String[] {Long.class.getName()}, new String[] {"countryId"},
			true);

		_finderPathCountByCountryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCountryId",
			new String[] {Long.class.getName()}, new String[] {"countryId"},
			false);

		_finderPathFetchByCountryId_LanguageId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCountryId_LanguageId",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"countryId", "languageId"}, true);

		_finderPathCountByCountryId_LanguageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCountryId_LanguageId",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"countryId", "languageId"}, false);

		CountryLocalizationUtil.setPersistence(this);
	}

	public void destroy() {
		CountryLocalizationUtil.setPersistence(null);

		EntityCacheUtil.removeCache(CountryLocalizationImpl.class.getName());
	}

	private static final String _SQL_SELECT_COUNTRYLOCALIZATION =
		"SELECT countryLocalization FROM CountryLocalization countryLocalization";

	private static final String _SQL_SELECT_COUNTRYLOCALIZATION_WHERE =
		"SELECT countryLocalization FROM CountryLocalization countryLocalization WHERE ";

	private static final String _SQL_COUNT_COUNTRYLOCALIZATION =
		"SELECT COUNT(countryLocalization) FROM CountryLocalization countryLocalization";

	private static final String _SQL_COUNT_COUNTRYLOCALIZATION_WHERE =
		"SELECT COUNT(countryLocalization) FROM CountryLocalization countryLocalization WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "countryLocalization.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CountryLocalization exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CountryLocalization exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CountryLocalizationPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}