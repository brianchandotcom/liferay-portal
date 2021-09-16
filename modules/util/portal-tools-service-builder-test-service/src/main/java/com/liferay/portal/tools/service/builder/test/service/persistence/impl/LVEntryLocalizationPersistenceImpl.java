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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryLocalizationException;
import com.liferay.portal.tools.service.builder.test.model.LVEntryLocalization;
import com.liferay.portal.tools.service.builder.test.model.LVEntryLocalizationTable;
import com.liferay.portal.tools.service.builder.test.model.impl.LVEntryLocalizationImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.LVEntryLocalizationModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.LVEntryLocalizationPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the lv entry localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LVEntryLocalizationPersistenceImpl
	extends BasePersistenceImpl<LVEntryLocalization>
	implements LVEntryLocalizationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LVEntryLocalizationUtil</code> to access the lv entry localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LVEntryLocalizationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByLvEntryId;
	private FinderPath _finderPathWithoutPaginationFindByLvEntryId;
	private FinderPath _finderPathCountByLvEntryId;

	/**
	 * Returns all the lv entry localizations where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the matching lv entry localizations
	 */
	@Override
	public List<LVEntryLocalization> findByLvEntryId(long lvEntryId) {
		return findByLvEntryId(
			lvEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry localizations where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @return the range of matching lv entry localizations
	 */
	@Override
	public List<LVEntryLocalization> findByLvEntryId(
		long lvEntryId, int start, int end) {

		return findByLvEntryId(lvEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry localizations where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localizations
	 */
	@Override
	public List<LVEntryLocalization> findByLvEntryId(
		long lvEntryId, int start, int end,
		OrderByComparator<LVEntryLocalization> orderByComparator) {

		return findByLvEntryId(lvEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry localizations where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry localizations
	 */
	@Override
	public List<LVEntryLocalization> findByLvEntryId(
		long lvEntryId, int start, int end,
		OrderByComparator<LVEntryLocalization> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByLvEntryId;
				finderArgs = new Object[] {lvEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByLvEntryId;
			finderArgs = new Object[] {
				lvEntryId, start, end, orderByComparator
			};
		}

		List<LVEntryLocalization> list = null;

		if (useFinderCache) {
			list = (List<LVEntryLocalization>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryLocalization lvEntryLocalization : list) {
					if (lvEntryId != lvEntryLocalization.getLvEntryId()) {
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

			sb.append(_SQL_SELECT_LVENTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_LVENTRYID_LVENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LVEntryLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(lvEntryId);

				list = (List<LVEntryLocalization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first lv entry localization in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization findByLvEntryId_First(
			long lvEntryId,
			OrderByComparator<LVEntryLocalization> orderByComparator)
		throws NoSuchLVEntryLocalizationException {

		LVEntryLocalization lvEntryLocalization = fetchByLvEntryId_First(
			lvEntryId, orderByComparator);

		if (lvEntryLocalization != null) {
			return lvEntryLocalization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("lvEntryId=");
		sb.append(lvEntryId);

		sb.append("}");

		throw new NoSuchLVEntryLocalizationException(sb.toString());
	}

	/**
	 * Returns the first lv entry localization in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization fetchByLvEntryId_First(
		long lvEntryId,
		OrderByComparator<LVEntryLocalization> orderByComparator) {

		List<LVEntryLocalization> list = findByLvEntryId(
			lvEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry localization in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization findByLvEntryId_Last(
			long lvEntryId,
			OrderByComparator<LVEntryLocalization> orderByComparator)
		throws NoSuchLVEntryLocalizationException {

		LVEntryLocalization lvEntryLocalization = fetchByLvEntryId_Last(
			lvEntryId, orderByComparator);

		if (lvEntryLocalization != null) {
			return lvEntryLocalization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("lvEntryId=");
		sb.append(lvEntryId);

		sb.append("}");

		throw new NoSuchLVEntryLocalizationException(sb.toString());
	}

	/**
	 * Returns the last lv entry localization in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization fetchByLvEntryId_Last(
		long lvEntryId,
		OrderByComparator<LVEntryLocalization> orderByComparator) {

		int count = countByLvEntryId(lvEntryId);

		if (count == 0) {
			return null;
		}

		List<LVEntryLocalization> list = findByLvEntryId(
			lvEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry localizations before and after the current lv entry localization in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryLocalizationId the primary key of the current lv entry localization
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a lv entry localization with the primary key could not be found
	 */
	@Override
	public LVEntryLocalization[] findByLvEntryId_PrevAndNext(
			long lvEntryLocalizationId, long lvEntryId,
			OrderByComparator<LVEntryLocalization> orderByComparator)
		throws NoSuchLVEntryLocalizationException {

		LVEntryLocalization lvEntryLocalization = findByPrimaryKey(
			lvEntryLocalizationId);

		Session session = null;

		try {
			session = openSession();

			LVEntryLocalization[] array = new LVEntryLocalizationImpl[3];

			array[0] = getByLvEntryId_PrevAndNext(
				session, lvEntryLocalization, lvEntryId, orderByComparator,
				true);

			array[1] = lvEntryLocalization;

			array[2] = getByLvEntryId_PrevAndNext(
				session, lvEntryLocalization, lvEntryId, orderByComparator,
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

	protected LVEntryLocalization getByLvEntryId_PrevAndNext(
		Session session, LVEntryLocalization lvEntryLocalization,
		long lvEntryId,
		OrderByComparator<LVEntryLocalization> orderByComparator,
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

		sb.append(_SQL_SELECT_LVENTRYLOCALIZATION_WHERE);

		sb.append(_FINDER_COLUMN_LVENTRYID_LVENTRYID_2);

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
			sb.append(LVEntryLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(lvEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						lvEntryLocalization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LVEntryLocalization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry localizations where lvEntryId = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 */
	@Override
	public void removeByLvEntryId(long lvEntryId) {
		for (LVEntryLocalization lvEntryLocalization :
				findByLvEntryId(
					lvEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(lvEntryLocalization);
		}
	}

	/**
	 * Returns the number of lv entry localizations where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the number of matching lv entry localizations
	 */
	@Override
	public int countByLvEntryId(long lvEntryId) {
		FinderPath finderPath = _finderPathCountByLvEntryId;

		Object[] finderArgs = new Object[] {lvEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LVENTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_LVENTRYID_LVENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(lvEntryId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_LVENTRYID_LVENTRYID_2 =
		"lvEntryLocalization.lvEntryId = ?";

	private FinderPath _finderPathFetchByLvEntryId_LanguageId;
	private FinderPath _finderPathCountByLvEntryId_LanguageId;

	/**
	 * Returns the lv entry localization where lvEntryId = &#63; and languageId = &#63; or throws a <code>NoSuchLVEntryLocalizationException</code> if it could not be found.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the matching lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization findByLvEntryId_LanguageId(
			long lvEntryId, String languageId)
		throws NoSuchLVEntryLocalizationException {

		LVEntryLocalization lvEntryLocalization = fetchByLvEntryId_LanguageId(
			lvEntryId, languageId);

		if (lvEntryLocalization == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("lvEntryId=");
			sb.append(lvEntryId);

			sb.append(", languageId=");
			sb.append(languageId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLVEntryLocalizationException(sb.toString());
		}

		return lvEntryLocalization;
	}

	/**
	 * Returns the lv entry localization where lvEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization fetchByLvEntryId_LanguageId(
		long lvEntryId, String languageId) {

		return fetchByLvEntryId_LanguageId(lvEntryId, languageId, true);
	}

	/**
	 * Returns the lv entry localization where lvEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization fetchByLvEntryId_LanguageId(
		long lvEntryId, String languageId, boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {lvEntryId, languageId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByLvEntryId_LanguageId, finderArgs);
		}

		if (result instanceof LVEntryLocalization) {
			LVEntryLocalization lvEntryLocalization =
				(LVEntryLocalization)result;

			if ((lvEntryId != lvEntryLocalization.getLvEntryId()) ||
				!Objects.equals(
					languageId, lvEntryLocalization.getLanguageId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LVENTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LVENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(lvEntryId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				List<LVEntryLocalization> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByLvEntryId_LanguageId, finderArgs,
							list);
					}
				}
				else {
					LVEntryLocalization lvEntryLocalization = list.get(0);

					result = lvEntryLocalization;

					cacheResult(lvEntryLocalization);
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
			return (LVEntryLocalization)result;
		}
	}

	/**
	 * Removes the lv entry localization where lvEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the lv entry localization that was removed
	 */
	@Override
	public LVEntryLocalization removeByLvEntryId_LanguageId(
			long lvEntryId, String languageId)
		throws NoSuchLVEntryLocalizationException {

		LVEntryLocalization lvEntryLocalization = findByLvEntryId_LanguageId(
			lvEntryId, languageId);

		return remove(lvEntryLocalization);
	}

	/**
	 * Returns the number of lv entry localizations where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the number of matching lv entry localizations
	 */
	@Override
	public int countByLvEntryId_LanguageId(long lvEntryId, String languageId) {
		languageId = Objects.toString(languageId, "");

		FinderPath finderPath = _finderPathCountByLvEntryId_LanguageId;

		Object[] finderArgs = new Object[] {lvEntryId, languageId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LVENTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LVENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(lvEntryId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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
		_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LVENTRYID_2 =
			"lvEntryLocalization.lvEntryId = ? AND ";

	private static final String
		_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LANGUAGEID_2 =
			"lvEntryLocalization.languageId = ?";

	private static final String
		_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LANGUAGEID_3 =
			"(lvEntryLocalization.languageId IS NULL OR lvEntryLocalization.languageId = '')";

	private FinderPath _finderPathFetchByHeadId;
	private FinderPath _finderPathCountByHeadId;

	/**
	 * Returns the lv entry localization where headId = &#63; or throws a <code>NoSuchLVEntryLocalizationException</code> if it could not be found.
	 *
	 * @param headId the head ID
	 * @return the matching lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization findByHeadId(long headId)
		throws NoSuchLVEntryLocalizationException {

		LVEntryLocalization lvEntryLocalization = fetchByHeadId(headId);

		if (lvEntryLocalization == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("headId=");
			sb.append(headId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLVEntryLocalizationException(sb.toString());
		}

		return lvEntryLocalization;
	}

	/**
	 * Returns the lv entry localization where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param headId the head ID
	 * @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization fetchByHeadId(long headId) {
		return fetchByHeadId(headId, true);
	}

	/**
	 * Returns the lv entry localization where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param headId the head ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization fetchByHeadId(
		long headId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {headId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByHeadId, finderArgs);
		}

		if (result instanceof LVEntryLocalization) {
			LVEntryLocalization lvEntryLocalization =
				(LVEntryLocalization)result;

			if (headId != lvEntryLocalization.getHeadId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_LVENTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_HEADID_HEADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(headId);

				List<LVEntryLocalization> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByHeadId, finderArgs, list);
					}
				}
				else {
					LVEntryLocalization lvEntryLocalization = list.get(0);

					result = lvEntryLocalization;

					cacheResult(lvEntryLocalization);
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
			return (LVEntryLocalization)result;
		}
	}

	/**
	 * Removes the lv entry localization where headId = &#63; from the database.
	 *
	 * @param headId the head ID
	 * @return the lv entry localization that was removed
	 */
	@Override
	public LVEntryLocalization removeByHeadId(long headId)
		throws NoSuchLVEntryLocalizationException {

		LVEntryLocalization lvEntryLocalization = findByHeadId(headId);

		return remove(lvEntryLocalization);
	}

	/**
	 * Returns the number of lv entry localizations where headId = &#63;.
	 *
	 * @param headId the head ID
	 * @return the number of matching lv entry localizations
	 */
	@Override
	public int countByHeadId(long headId) {
		FinderPath finderPath = _finderPathCountByHeadId;

		Object[] finderArgs = new Object[] {headId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LVENTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_HEADID_HEADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(headId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_HEADID_HEADID_2 =
		"lvEntryLocalization.headId = ?";

	public LVEntryLocalizationPersistenceImpl() {
		setModelClass(LVEntryLocalization.class);

		setModelImplClass(LVEntryLocalizationImpl.class);
		setModelPKClass(long.class);

		setTable(LVEntryLocalizationTable.INSTANCE);
	}

	/**
	 * Caches the lv entry localization in the entity cache if it is enabled.
	 *
	 * @param lvEntryLocalization the lv entry localization
	 */
	@Override
	public void cacheResult(LVEntryLocalization lvEntryLocalization) {
		entityCache.putResult(
			LVEntryLocalizationImpl.class, lvEntryLocalization.getPrimaryKey(),
			lvEntryLocalization);

		finderCache.putResult(
			_finderPathFetchByLvEntryId_LanguageId,
			new Object[] {
				lvEntryLocalization.getLvEntryId(),
				lvEntryLocalization.getLanguageId()
			},
			lvEntryLocalization);

		finderCache.putResult(
			_finderPathFetchByHeadId,
			new Object[] {lvEntryLocalization.getHeadId()},
			lvEntryLocalization);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the lv entry localizations in the entity cache if it is enabled.
	 *
	 * @param lvEntryLocalizations the lv entry localizations
	 */
	@Override
	public void cacheResult(List<LVEntryLocalization> lvEntryLocalizations) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (lvEntryLocalizations.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LVEntryLocalization lvEntryLocalization : lvEntryLocalizations) {
			if (entityCache.getResult(
					LVEntryLocalizationImpl.class,
					lvEntryLocalization.getPrimaryKey()) == null) {

				cacheResult(lvEntryLocalization);
			}
		}
	}

	/**
	 * Clears the cache for all lv entry localizations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LVEntryLocalizationImpl.class);

		finderCache.clearCache(LVEntryLocalizationImpl.class);
	}

	/**
	 * Clears the cache for the lv entry localization.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LVEntryLocalization lvEntryLocalization) {
		entityCache.removeResult(
			LVEntryLocalizationImpl.class, lvEntryLocalization);
	}

	@Override
	public void clearCache(List<LVEntryLocalization> lvEntryLocalizations) {
		for (LVEntryLocalization lvEntryLocalization : lvEntryLocalizations) {
			entityCache.removeResult(
				LVEntryLocalizationImpl.class, lvEntryLocalization);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(LVEntryLocalizationImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(LVEntryLocalizationImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LVEntryLocalizationModelImpl lvEntryLocalizationModelImpl) {

		Object[] args = new Object[] {
			lvEntryLocalizationModelImpl.getLvEntryId(),
			lvEntryLocalizationModelImpl.getLanguageId()
		};

		finderCache.putResult(
			_finderPathCountByLvEntryId_LanguageId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByLvEntryId_LanguageId, args,
			lvEntryLocalizationModelImpl);

		args = new Object[] {lvEntryLocalizationModelImpl.getHeadId()};

		finderCache.putResult(_finderPathCountByHeadId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByHeadId, args, lvEntryLocalizationModelImpl);
	}

	/**
	 * Creates a new lv entry localization with the primary key. Does not add the lv entry localization to the database.
	 *
	 * @param lvEntryLocalizationId the primary key for the new lv entry localization
	 * @return the new lv entry localization
	 */
	@Override
	public LVEntryLocalization create(long lvEntryLocalizationId) {
		LVEntryLocalization lvEntryLocalization = new LVEntryLocalizationImpl();

		lvEntryLocalization.setNew(true);
		lvEntryLocalization.setPrimaryKey(lvEntryLocalizationId);

		lvEntryLocalization.setCompanyId(CompanyThreadLocal.getCompanyId());

		return lvEntryLocalization;
	}

	/**
	 * Removes the lv entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lvEntryLocalizationId the primary key of the lv entry localization
	 * @return the lv entry localization that was removed
	 * @throws NoSuchLVEntryLocalizationException if a lv entry localization with the primary key could not be found
	 */
	@Override
	public LVEntryLocalization remove(long lvEntryLocalizationId)
		throws NoSuchLVEntryLocalizationException {

		return remove((Serializable)lvEntryLocalizationId);
	}

	/**
	 * Removes the lv entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the lv entry localization
	 * @return the lv entry localization that was removed
	 * @throws NoSuchLVEntryLocalizationException if a lv entry localization with the primary key could not be found
	 */
	@Override
	public LVEntryLocalization remove(Serializable primaryKey)
		throws NoSuchLVEntryLocalizationException {

		Session session = null;

		try {
			session = openSession();

			LVEntryLocalization lvEntryLocalization =
				(LVEntryLocalization)session.get(
					LVEntryLocalizationImpl.class, primaryKey);

			if (lvEntryLocalization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLVEntryLocalizationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(lvEntryLocalization);
		}
		catch (NoSuchLVEntryLocalizationException noSuchEntityException) {
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
	protected LVEntryLocalization removeImpl(
		LVEntryLocalization lvEntryLocalization) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(lvEntryLocalization)) {
				lvEntryLocalization = (LVEntryLocalization)session.get(
					LVEntryLocalizationImpl.class,
					lvEntryLocalization.getPrimaryKeyObj());
			}

			if (lvEntryLocalization != null) {
				session.delete(lvEntryLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (lvEntryLocalization != null) {
			clearCache(lvEntryLocalization);
		}

		return lvEntryLocalization;
	}

	@Override
	public LVEntryLocalization updateImpl(
		LVEntryLocalization lvEntryLocalization) {

		boolean isNew = lvEntryLocalization.isNew();

		if (!(lvEntryLocalization instanceof LVEntryLocalizationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(lvEntryLocalization.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					lvEntryLocalization);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in lvEntryLocalization proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LVEntryLocalization implementation " +
					lvEntryLocalization.getClass());
		}

		LVEntryLocalizationModelImpl lvEntryLocalizationModelImpl =
			(LVEntryLocalizationModelImpl)lvEntryLocalization;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(lvEntryLocalization);
			}
			else {
				lvEntryLocalization = (LVEntryLocalization)session.merge(
					lvEntryLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			LVEntryLocalizationImpl.class, lvEntryLocalizationModelImpl, false,
			true);

		cacheUniqueFindersCache(lvEntryLocalizationModelImpl);

		if (isNew) {
			lvEntryLocalization.setNew(false);
		}

		lvEntryLocalization.resetOriginalValues();

		return lvEntryLocalization;
	}

	/**
	 * Returns the lv entry localization with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the lv entry localization
	 * @return the lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a lv entry localization with the primary key could not be found
	 */
	@Override
	public LVEntryLocalization findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLVEntryLocalizationException {

		LVEntryLocalization lvEntryLocalization = fetchByPrimaryKey(primaryKey);

		if (lvEntryLocalization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLVEntryLocalizationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return lvEntryLocalization;
	}

	/**
	 * Returns the lv entry localization with the primary key or throws a <code>NoSuchLVEntryLocalizationException</code> if it could not be found.
	 *
	 * @param lvEntryLocalizationId the primary key of the lv entry localization
	 * @return the lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a lv entry localization with the primary key could not be found
	 */
	@Override
	public LVEntryLocalization findByPrimaryKey(long lvEntryLocalizationId)
		throws NoSuchLVEntryLocalizationException {

		return findByPrimaryKey((Serializable)lvEntryLocalizationId);
	}

	/**
	 * Returns the lv entry localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lvEntryLocalizationId the primary key of the lv entry localization
	 * @return the lv entry localization, or <code>null</code> if a lv entry localization with the primary key could not be found
	 */
	@Override
	public LVEntryLocalization fetchByPrimaryKey(long lvEntryLocalizationId) {
		return fetchByPrimaryKey((Serializable)lvEntryLocalizationId);
	}

	/**
	 * Returns all the lv entry localizations.
	 *
	 * @return the lv entry localizations
	 */
	@Override
	public List<LVEntryLocalization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @return the range of lv entry localizations
	 */
	@Override
	public List<LVEntryLocalization> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lv entry localizations
	 */
	@Override
	public List<LVEntryLocalization> findAll(
		int start, int end,
		OrderByComparator<LVEntryLocalization> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of lv entry localizations
	 */
	@Override
	public List<LVEntryLocalization> findAll(
		int start, int end,
		OrderByComparator<LVEntryLocalization> orderByComparator,
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

		List<LVEntryLocalization> list = null;

		if (useFinderCache) {
			list = (List<LVEntryLocalization>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LVENTRYLOCALIZATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LVENTRYLOCALIZATION;

				sql = sql.concat(LVEntryLocalizationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LVEntryLocalization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Removes all the lv entry localizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LVEntryLocalization lvEntryLocalization : findAll()) {
			remove(lvEntryLocalization);
		}
	}

	/**
	 * Returns the number of lv entry localizations.
	 *
	 * @return the number of lv entry localizations
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_LVENTRYLOCALIZATION);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
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
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "lvEntryLocalizationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LVENTRYLOCALIZATION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LVEntryLocalizationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the lv entry localization persistence.
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

		_finderPathWithPaginationFindByLvEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLvEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"lvEntryId"}, true);

		_finderPathWithoutPaginationFindByLvEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLvEntryId",
			new String[] {Long.class.getName()}, new String[] {"lvEntryId"},
			true);

		_finderPathCountByLvEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLvEntryId",
			new String[] {Long.class.getName()}, new String[] {"lvEntryId"},
			false);

		_finderPathFetchByLvEntryId_LanguageId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByLvEntryId_LanguageId",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"lvEntryId", "languageId"}, true);

		_finderPathCountByLvEntryId_LanguageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLvEntryId_LanguageId",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"lvEntryId", "languageId"}, false);

		_finderPathFetchByHeadId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByHeadId",
			new String[] {Long.class.getName()}, new String[] {"headId"}, true);

		_finderPathCountByHeadId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByHeadId",
			new String[] {Long.class.getName()}, new String[] {"headId"},
			false);
	}

	public void destroy() {
		entityCache.removeCache(LVEntryLocalizationImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_LVENTRYLOCALIZATION =
		"SELECT lvEntryLocalization FROM LVEntryLocalization lvEntryLocalization";

	private static final String _SQL_SELECT_LVENTRYLOCALIZATION_WHERE =
		"SELECT lvEntryLocalization FROM LVEntryLocalization lvEntryLocalization WHERE ";

	private static final String _SQL_COUNT_LVENTRYLOCALIZATION =
		"SELECT COUNT(lvEntryLocalization) FROM LVEntryLocalization lvEntryLocalization";

	private static final String _SQL_COUNT_LVENTRYLOCALIZATION_WHERE =
		"SELECT COUNT(lvEntryLocalization) FROM LVEntryLocalization lvEntryLocalization WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "lvEntryLocalization.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LVEntryLocalization exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LVEntryLocalization exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LVEntryLocalizationPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}