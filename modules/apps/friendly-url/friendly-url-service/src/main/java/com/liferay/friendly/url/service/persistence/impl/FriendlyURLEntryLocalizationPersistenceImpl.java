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

package com.liferay.friendly.url.service.persistence.impl;

import com.liferay.friendly.url.exception.NoSuchFriendlyURLEntryLocalizationException;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalizationTable;
import com.liferay.friendly.url.model.impl.FriendlyURLEntryLocalizationImpl;
import com.liferay.friendly.url.model.impl.FriendlyURLEntryLocalizationModelImpl;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationPersistence;
import com.liferay.friendly.url.service.persistence.impl.constants.FURLPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;

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

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the friendly url entry localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = FriendlyURLEntryLocalizationPersistence.class)
public class FriendlyURLEntryLocalizationPersistenceImpl
	extends BasePersistenceImpl<FriendlyURLEntryLocalization>
	implements FriendlyURLEntryLocalizationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FriendlyURLEntryLocalizationUtil</code> to access the friendly url entry localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FriendlyURLEntryLocalizationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByFriendlyURLEntryId;
	private FinderPath _finderPathWithoutPaginationFindByFriendlyURLEntryId;
	private FinderPath _finderPathCountByFriendlyURLEntryId;

	/**
	 * Returns all the friendly url entry localizations where friendlyURLEntryId = &#63;.
	 *
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @return the matching friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findByFriendlyURLEntryId(
		long friendlyURLEntryId) {

		return findByFriendlyURLEntryId(
			friendlyURLEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly url entry localizations where friendlyURLEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FriendlyURLEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param start the lower bound of the range of friendly url entry localizations
	 * @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	 * @return the range of matching friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findByFriendlyURLEntryId(
		long friendlyURLEntryId, int start, int end) {

		return findByFriendlyURLEntryId(friendlyURLEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly url entry localizations where friendlyURLEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FriendlyURLEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param start the lower bound of the range of friendly url entry localizations
	 * @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findByFriendlyURLEntryId(
		long friendlyURLEntryId, int start, int end,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator) {

		return findByFriendlyURLEntryId(
			friendlyURLEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly url entry localizations where friendlyURLEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FriendlyURLEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param start the lower bound of the range of friendly url entry localizations
	 * @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findByFriendlyURLEntryId(
		long friendlyURLEntryId, int start, int end,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FriendlyURLEntryLocalization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByFriendlyURLEntryId;
				finderArgs = new Object[] {friendlyURLEntryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByFriendlyURLEntryId;
			finderArgs = new Object[] {
				friendlyURLEntryId, start, end, orderByComparator
			};
		}

		List<FriendlyURLEntryLocalization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FriendlyURLEntryLocalization>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FriendlyURLEntryLocalization friendlyURLEntryLocalization :
						list) {

					if (friendlyURLEntryId !=
							friendlyURLEntryLocalization.
								getFriendlyURLEntryId()) {

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

			sb.append(_SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_FRIENDLYURLENTRYID_FRIENDLYURLENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FriendlyURLEntryLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(friendlyURLEntryId);

				list = (List<FriendlyURLEntryLocalization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first friendly url entry localization in the ordered set where friendlyURLEntryId = &#63;.
	 *
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization findByFriendlyURLEntryId_First(
			long friendlyURLEntryId,
			OrderByComparator<FriendlyURLEntryLocalization> orderByComparator)
		throws NoSuchFriendlyURLEntryLocalizationException {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			fetchByFriendlyURLEntryId_First(
				friendlyURLEntryId, orderByComparator);

		if (friendlyURLEntryLocalization != null) {
			return friendlyURLEntryLocalization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("friendlyURLEntryId=");
		sb.append(friendlyURLEntryId);

		sb.append("}");

		throw new NoSuchFriendlyURLEntryLocalizationException(sb.toString());
	}

	/**
	 * Returns the first friendly url entry localization in the ordered set where friendlyURLEntryId = &#63;.
	 *
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByFriendlyURLEntryId_First(
		long friendlyURLEntryId,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator) {

		List<FriendlyURLEntryLocalization> list = findByFriendlyURLEntryId(
			friendlyURLEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last friendly url entry localization in the ordered set where friendlyURLEntryId = &#63;.
	 *
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization findByFriendlyURLEntryId_Last(
			long friendlyURLEntryId,
			OrderByComparator<FriendlyURLEntryLocalization> orderByComparator)
		throws NoSuchFriendlyURLEntryLocalizationException {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			fetchByFriendlyURLEntryId_Last(
				friendlyURLEntryId, orderByComparator);

		if (friendlyURLEntryLocalization != null) {
			return friendlyURLEntryLocalization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("friendlyURLEntryId=");
		sb.append(friendlyURLEntryId);

		sb.append("}");

		throw new NoSuchFriendlyURLEntryLocalizationException(sb.toString());
	}

	/**
	 * Returns the last friendly url entry localization in the ordered set where friendlyURLEntryId = &#63;.
	 *
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByFriendlyURLEntryId_Last(
		long friendlyURLEntryId,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator) {

		int count = countByFriendlyURLEntryId(friendlyURLEntryId);

		if (count == 0) {
			return null;
		}

		List<FriendlyURLEntryLocalization> list = findByFriendlyURLEntryId(
			friendlyURLEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the friendly url entry localizations before and after the current friendly url entry localization in the ordered set where friendlyURLEntryId = &#63;.
	 *
	 * @param friendlyURLEntryLocalizationId the primary key of the current friendly url entry localization
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a friendly url entry localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization[] findByFriendlyURLEntryId_PrevAndNext(
			long friendlyURLEntryLocalizationId, long friendlyURLEntryId,
			OrderByComparator<FriendlyURLEntryLocalization> orderByComparator)
		throws NoSuchFriendlyURLEntryLocalizationException {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			findByPrimaryKey(friendlyURLEntryLocalizationId);

		Session session = null;

		try {
			session = openSession();

			FriendlyURLEntryLocalization[] array =
				new FriendlyURLEntryLocalizationImpl[3];

			array[0] = getByFriendlyURLEntryId_PrevAndNext(
				session, friendlyURLEntryLocalization, friendlyURLEntryId,
				orderByComparator, true);

			array[1] = friendlyURLEntryLocalization;

			array[2] = getByFriendlyURLEntryId_PrevAndNext(
				session, friendlyURLEntryLocalization, friendlyURLEntryId,
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

	protected FriendlyURLEntryLocalization getByFriendlyURLEntryId_PrevAndNext(
		Session session,
		FriendlyURLEntryLocalization friendlyURLEntryLocalization,
		long friendlyURLEntryId,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator,
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

		sb.append(_SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

		sb.append(_FINDER_COLUMN_FRIENDLYURLENTRYID_FRIENDLYURLENTRYID_2);

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
			sb.append(FriendlyURLEntryLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(friendlyURLEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						friendlyURLEntryLocalization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FriendlyURLEntryLocalization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the friendly url entry localizations where friendlyURLEntryId = &#63; from the database.
	 *
	 * @param friendlyURLEntryId the friendly url entry ID
	 */
	@Override
	public void removeByFriendlyURLEntryId(long friendlyURLEntryId) {
		for (FriendlyURLEntryLocalization friendlyURLEntryLocalization :
				findByFriendlyURLEntryId(
					friendlyURLEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(friendlyURLEntryLocalization);
		}
	}

	/**
	 * Returns the number of friendly url entry localizations where friendlyURLEntryId = &#63;.
	 *
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @return the number of matching friendly url entry localizations
	 */
	@Override
	public int countByFriendlyURLEntryId(long friendlyURLEntryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FriendlyURLEntryLocalization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFriendlyURLEntryId;

			finderArgs = new Object[] {friendlyURLEntryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_FRIENDLYURLENTRYID_FRIENDLYURLENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(friendlyURLEntryId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String
		_FINDER_COLUMN_FRIENDLYURLENTRYID_FRIENDLYURLENTRYID_2 =
			"friendlyURLEntryLocalization.friendlyURLEntryId = ?";

	private FinderPath _finderPathFetchByFriendlyURLEntryId_LanguageId;
	private FinderPath _finderPathCountByFriendlyURLEntryId_LanguageId;

	/**
	 * Returns the friendly url entry localization where friendlyURLEntryId = &#63; and languageId = &#63; or throws a <code>NoSuchFriendlyURLEntryLocalizationException</code> if it could not be found.
	 *
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param languageId the language ID
	 * @return the matching friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization findByFriendlyURLEntryId_LanguageId(
			long friendlyURLEntryId, String languageId)
		throws NoSuchFriendlyURLEntryLocalizationException {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			fetchByFriendlyURLEntryId_LanguageId(
				friendlyURLEntryId, languageId);

		if (friendlyURLEntryLocalization == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("friendlyURLEntryId=");
			sb.append(friendlyURLEntryId);

			sb.append(", languageId=");
			sb.append(languageId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFriendlyURLEntryLocalizationException(
				sb.toString());
		}

		return friendlyURLEntryLocalization;
	}

	/**
	 * Returns the friendly url entry localization where friendlyURLEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param languageId the language ID
	 * @return the matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByFriendlyURLEntryId_LanguageId(
		long friendlyURLEntryId, String languageId) {

		return fetchByFriendlyURLEntryId_LanguageId(
			friendlyURLEntryId, languageId, true);
	}

	/**
	 * Returns the friendly url entry localization where friendlyURLEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByFriendlyURLEntryId_LanguageId(
		long friendlyURLEntryId, String languageId, boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FriendlyURLEntryLocalization.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {friendlyURLEntryId, languageId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByFriendlyURLEntryId_LanguageId, finderArgs,
				this);
		}

		if (result instanceof FriendlyURLEntryLocalization) {
			FriendlyURLEntryLocalization friendlyURLEntryLocalization =
				(FriendlyURLEntryLocalization)result;

			if ((friendlyURLEntryId !=
					friendlyURLEntryLocalization.getFriendlyURLEntryId()) ||
				!Objects.equals(
					languageId, friendlyURLEntryLocalization.getLanguageId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

			sb.append(
				_FINDER_COLUMN_FRIENDLYURLENTRYID_LANGUAGEID_FRIENDLYURLENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_FRIENDLYURLENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(
					_FINDER_COLUMN_FRIENDLYURLENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(friendlyURLEntryId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				List<FriendlyURLEntryLocalization> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByFriendlyURLEntryId_LanguageId,
							finderArgs, list);
					}
				}
				else {
					FriendlyURLEntryLocalization friendlyURLEntryLocalization =
						list.get(0);

					result = friendlyURLEntryLocalization;

					cacheResult(friendlyURLEntryLocalization);
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
			return (FriendlyURLEntryLocalization)result;
		}
	}

	/**
	 * Removes the friendly url entry localization where friendlyURLEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param languageId the language ID
	 * @return the friendly url entry localization that was removed
	 */
	@Override
	public FriendlyURLEntryLocalization removeByFriendlyURLEntryId_LanguageId(
			long friendlyURLEntryId, String languageId)
		throws NoSuchFriendlyURLEntryLocalizationException {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			findByFriendlyURLEntryId_LanguageId(friendlyURLEntryId, languageId);

		return remove(friendlyURLEntryLocalization);
	}

	/**
	 * Returns the number of friendly url entry localizations where friendlyURLEntryId = &#63; and languageId = &#63;.
	 *
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param languageId the language ID
	 * @return the number of matching friendly url entry localizations
	 */
	@Override
	public int countByFriendlyURLEntryId_LanguageId(
		long friendlyURLEntryId, String languageId) {

		languageId = Objects.toString(languageId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FriendlyURLEntryLocalization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFriendlyURLEntryId_LanguageId;

			finderArgs = new Object[] {friendlyURLEntryId, languageId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

			sb.append(
				_FINDER_COLUMN_FRIENDLYURLENTRYID_LANGUAGEID_FRIENDLYURLENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_FRIENDLYURLENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(
					_FINDER_COLUMN_FRIENDLYURLENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(friendlyURLEntryId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String
		_FINDER_COLUMN_FRIENDLYURLENTRYID_LANGUAGEID_FRIENDLYURLENTRYID_2 =
			"friendlyURLEntryLocalization.friendlyURLEntryId = ? AND ";

	private static final String
		_FINDER_COLUMN_FRIENDLYURLENTRYID_LANGUAGEID_LANGUAGEID_2 =
			"friendlyURLEntryLocalization.languageId = ?";

	private static final String
		_FINDER_COLUMN_FRIENDLYURLENTRYID_LANGUAGEID_LANGUAGEID_3 =
			"(friendlyURLEntryLocalization.languageId IS NULL OR friendlyURLEntryLocalization.languageId = '')";

	private FinderPath _finderPathFetchByG_C_U;
	private FinderPath _finderPathCountByG_C_U;

	/**
	 * Returns the friendly url entry localization where groupId = &#63; and classNameId = &#63; and urlTitle = &#63; or throws a <code>NoSuchFriendlyURLEntryLocalizationException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @return the matching friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization findByG_C_U(
			long groupId, long classNameId, String urlTitle)
		throws NoSuchFriendlyURLEntryLocalizationException {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			fetchByG_C_U(groupId, classNameId, urlTitle);

		if (friendlyURLEntryLocalization == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", urlTitle=");
			sb.append(urlTitle);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFriendlyURLEntryLocalizationException(
				sb.toString());
		}

		return friendlyURLEntryLocalization;
	}

	/**
	 * Returns the friendly url entry localization where groupId = &#63; and classNameId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @return the matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByG_C_U(
		long groupId, long classNameId, String urlTitle) {

		return fetchByG_C_U(groupId, classNameId, urlTitle, true);
	}

	/**
	 * Returns the friendly url entry localization where groupId = &#63; and classNameId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByG_C_U(
		long groupId, long classNameId, String urlTitle,
		boolean useFinderCache) {

		urlTitle = Objects.toString(urlTitle, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FriendlyURLEntryLocalization.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, classNameId, urlTitle};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_C_U, finderArgs, this);
		}

		if (result instanceof FriendlyURLEntryLocalization) {
			FriendlyURLEntryLocalization friendlyURLEntryLocalization =
				(FriendlyURLEntryLocalization)result;

			if ((groupId != friendlyURLEntryLocalization.getGroupId()) ||
				(classNameId !=
					friendlyURLEntryLocalization.getClassNameId()) ||
				!Objects.equals(
					urlTitle, friendlyURLEntryLocalization.getUrlTitle())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_G_C_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_U_CLASSNAMEID_2);

			boolean bindUrlTitle = false;

			if (urlTitle.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				sb.append(_FINDER_COLUMN_G_C_U_URLTITLE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				if (bindUrlTitle) {
					queryPos.add(urlTitle);
				}

				List<FriendlyURLEntryLocalization> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_C_U, finderArgs, list);
					}
				}
				else {
					FriendlyURLEntryLocalization friendlyURLEntryLocalization =
						list.get(0);

					result = friendlyURLEntryLocalization;

					cacheResult(friendlyURLEntryLocalization);
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
			return (FriendlyURLEntryLocalization)result;
		}
	}

	/**
	 * Removes the friendly url entry localization where groupId = &#63; and classNameId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @return the friendly url entry localization that was removed
	 */
	@Override
	public FriendlyURLEntryLocalization removeByG_C_U(
			long groupId, long classNameId, String urlTitle)
		throws NoSuchFriendlyURLEntryLocalizationException {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization = findByG_C_U(
			groupId, classNameId, urlTitle);

		return remove(friendlyURLEntryLocalization);
	}

	/**
	 * Returns the number of friendly url entry localizations where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @return the number of matching friendly url entry localizations
	 */
	@Override
	public int countByG_C_U(long groupId, long classNameId, String urlTitle) {
		urlTitle = Objects.toString(urlTitle, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FriendlyURLEntryLocalization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_U;

			finderArgs = new Object[] {groupId, classNameId, urlTitle};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_G_C_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_U_CLASSNAMEID_2);

			boolean bindUrlTitle = false;

			if (urlTitle.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				sb.append(_FINDER_COLUMN_G_C_U_URLTITLE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				if (bindUrlTitle) {
					queryPos.add(urlTitle);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_C_U_GROUPID_2 =
		"friendlyURLEntryLocalization.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_U_CLASSNAMEID_2 =
		"friendlyURLEntryLocalization.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_U_URLTITLE_2 =
		"friendlyURLEntryLocalization.urlTitle = ?";

	private static final String _FINDER_COLUMN_G_C_U_URLTITLE_3 =
		"(friendlyURLEntryLocalization.urlTitle IS NULL OR friendlyURLEntryLocalization.urlTitle = '')";

	private FinderPath _finderPathWithPaginationFindByG_C_C_L;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C_L;
	private FinderPath _finderPathCountByG_C_C_L;

	/**
	 * Returns all the friendly url entry localizations where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @return the matching friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findByG_C_C_L(
		long groupId, long classNameId, long classPK, String languageId) {

		return findByG_C_C_L(
			groupId, classNameId, classPK, languageId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly url entry localizations where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FriendlyURLEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param start the lower bound of the range of friendly url entry localizations
	 * @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	 * @return the range of matching friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findByG_C_C_L(
		long groupId, long classNameId, long classPK, String languageId,
		int start, int end) {

		return findByG_C_C_L(
			groupId, classNameId, classPK, languageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly url entry localizations where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FriendlyURLEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param start the lower bound of the range of friendly url entry localizations
	 * @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findByG_C_C_L(
		long groupId, long classNameId, long classPK, String languageId,
		int start, int end,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator) {

		return findByG_C_C_L(
			groupId, classNameId, classPK, languageId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly url entry localizations where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FriendlyURLEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param start the lower bound of the range of friendly url entry localizations
	 * @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findByG_C_C_L(
		long groupId, long classNameId, long classPK, String languageId,
		int start, int end,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator,
		boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FriendlyURLEntryLocalization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C_L;
				finderArgs = new Object[] {
					groupId, classNameId, classPK, languageId
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_C_L;
			finderArgs = new Object[] {
				groupId, classNameId, classPK, languageId, start, end,
				orderByComparator
			};
		}

		List<FriendlyURLEntryLocalization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FriendlyURLEntryLocalization>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FriendlyURLEntryLocalization friendlyURLEntryLocalization :
						list) {

					if ((groupId !=
							friendlyURLEntryLocalization.getGroupId()) ||
						(classNameId !=
							friendlyURLEntryLocalization.getClassNameId()) ||
						(classPK !=
							friendlyURLEntryLocalization.getClassPK()) ||
						!languageId.equals(
							friendlyURLEntryLocalization.getLanguageId())) {

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

			sb.append(_SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_L_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_L_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_L_CLASSPK_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_C_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_G_C_C_L_LANGUAGEID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FriendlyURLEntryLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				list = (List<FriendlyURLEntryLocalization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first friendly url entry localization in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization findByG_C_C_L_First(
			long groupId, long classNameId, long classPK, String languageId,
			OrderByComparator<FriendlyURLEntryLocalization> orderByComparator)
		throws NoSuchFriendlyURLEntryLocalizationException {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			fetchByG_C_C_L_First(
				groupId, classNameId, classPK, languageId, orderByComparator);

		if (friendlyURLEntryLocalization != null) {
			return friendlyURLEntryLocalization;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", languageId=");
		sb.append(languageId);

		sb.append("}");

		throw new NoSuchFriendlyURLEntryLocalizationException(sb.toString());
	}

	/**
	 * Returns the first friendly url entry localization in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByG_C_C_L_First(
		long groupId, long classNameId, long classPK, String languageId,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator) {

		List<FriendlyURLEntryLocalization> list = findByG_C_C_L(
			groupId, classNameId, classPK, languageId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last friendly url entry localization in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization findByG_C_C_L_Last(
			long groupId, long classNameId, long classPK, String languageId,
			OrderByComparator<FriendlyURLEntryLocalization> orderByComparator)
		throws NoSuchFriendlyURLEntryLocalizationException {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			fetchByG_C_C_L_Last(
				groupId, classNameId, classPK, languageId, orderByComparator);

		if (friendlyURLEntryLocalization != null) {
			return friendlyURLEntryLocalization;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", languageId=");
		sb.append(languageId);

		sb.append("}");

		throw new NoSuchFriendlyURLEntryLocalizationException(sb.toString());
	}

	/**
	 * Returns the last friendly url entry localization in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByG_C_C_L_Last(
		long groupId, long classNameId, long classPK, String languageId,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator) {

		int count = countByG_C_C_L(groupId, classNameId, classPK, languageId);

		if (count == 0) {
			return null;
		}

		List<FriendlyURLEntryLocalization> list = findByG_C_C_L(
			groupId, classNameId, classPK, languageId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the friendly url entry localizations before and after the current friendly url entry localization in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63;.
	 *
	 * @param friendlyURLEntryLocalizationId the primary key of the current friendly url entry localization
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a friendly url entry localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization[] findByG_C_C_L_PrevAndNext(
			long friendlyURLEntryLocalizationId, long groupId, long classNameId,
			long classPK, String languageId,
			OrderByComparator<FriendlyURLEntryLocalization> orderByComparator)
		throws NoSuchFriendlyURLEntryLocalizationException {

		languageId = Objects.toString(languageId, "");

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			findByPrimaryKey(friendlyURLEntryLocalizationId);

		Session session = null;

		try {
			session = openSession();

			FriendlyURLEntryLocalization[] array =
				new FriendlyURLEntryLocalizationImpl[3];

			array[0] = getByG_C_C_L_PrevAndNext(
				session, friendlyURLEntryLocalization, groupId, classNameId,
				classPK, languageId, orderByComparator, true);

			array[1] = friendlyURLEntryLocalization;

			array[2] = getByG_C_C_L_PrevAndNext(
				session, friendlyURLEntryLocalization, groupId, classNameId,
				classPK, languageId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FriendlyURLEntryLocalization getByG_C_C_L_PrevAndNext(
		Session session,
		FriendlyURLEntryLocalization friendlyURLEntryLocalization, long groupId,
		long classNameId, long classPK, String languageId,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_L_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_L_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_L_CLASSPK_2);

		boolean bindLanguageId = false;

		if (languageId.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_C_L_LANGUAGEID_3);
		}
		else {
			bindLanguageId = true;

			sb.append(_FINDER_COLUMN_G_C_C_L_LANGUAGEID_2);
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
			sb.append(FriendlyURLEntryLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (bindLanguageId) {
			queryPos.add(languageId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						friendlyURLEntryLocalization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FriendlyURLEntryLocalization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the friendly url entry localizations where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 */
	@Override
	public void removeByG_C_C_L(
		long groupId, long classNameId, long classPK, String languageId) {

		for (FriendlyURLEntryLocalization friendlyURLEntryLocalization :
				findByG_C_C_L(
					groupId, classNameId, classPK, languageId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(friendlyURLEntryLocalization);
		}
	}

	/**
	 * Returns the number of friendly url entry localizations where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @return the number of matching friendly url entry localizations
	 */
	@Override
	public int countByG_C_C_L(
		long groupId, long classNameId, long classPK, String languageId) {

		languageId = Objects.toString(languageId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FriendlyURLEntryLocalization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_C_L;

			finderArgs = new Object[] {
				groupId, classNameId, classPK, languageId
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_L_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_L_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_L_CLASSPK_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_C_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_G_C_C_L_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_C_C_L_GROUPID_2 =
		"friendlyURLEntryLocalization.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_L_CLASSNAMEID_2 =
		"friendlyURLEntryLocalization.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_L_CLASSPK_2 =
		"friendlyURLEntryLocalization.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_L_LANGUAGEID_2 =
		"friendlyURLEntryLocalization.languageId = ?";

	private static final String _FINDER_COLUMN_G_C_C_L_LANGUAGEID_3 =
		"(friendlyURLEntryLocalization.languageId IS NULL OR friendlyURLEntryLocalization.languageId = '')";

	public FriendlyURLEntryLocalizationPersistenceImpl() {
		setModelClass(FriendlyURLEntryLocalization.class);

		setModelImplClass(FriendlyURLEntryLocalizationImpl.class);
		setModelPKClass(long.class);

		setTable(FriendlyURLEntryLocalizationTable.INSTANCE);
	}

	/**
	 * Caches the friendly url entry localization in the entity cache if it is enabled.
	 *
	 * @param friendlyURLEntryLocalization the friendly url entry localization
	 */
	@Override
	public void cacheResult(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {

		if (friendlyURLEntryLocalization.getCtCollectionId() != 0) {
			friendlyURLEntryLocalization.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			FriendlyURLEntryLocalizationImpl.class,
			friendlyURLEntryLocalization.getPrimaryKey(),
			friendlyURLEntryLocalization);

		finderCache.putResult(
			_finderPathFetchByFriendlyURLEntryId_LanguageId,
			new Object[] {
				friendlyURLEntryLocalization.getFriendlyURLEntryId(),
				friendlyURLEntryLocalization.getLanguageId()
			},
			friendlyURLEntryLocalization);

		finderCache.putResult(
			_finderPathFetchByG_C_U,
			new Object[] {
				friendlyURLEntryLocalization.getGroupId(),
				friendlyURLEntryLocalization.getClassNameId(),
				friendlyURLEntryLocalization.getUrlTitle()
			},
			friendlyURLEntryLocalization);

		friendlyURLEntryLocalization.resetOriginalValues();
	}

	/**
	 * Caches the friendly url entry localizations in the entity cache if it is enabled.
	 *
	 * @param friendlyURLEntryLocalizations the friendly url entry localizations
	 */
	@Override
	public void cacheResult(
		List<FriendlyURLEntryLocalization> friendlyURLEntryLocalizations) {

		for (FriendlyURLEntryLocalization friendlyURLEntryLocalization :
				friendlyURLEntryLocalizations) {

			if (friendlyURLEntryLocalization.getCtCollectionId() != 0) {
				friendlyURLEntryLocalization.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					FriendlyURLEntryLocalizationImpl.class,
					friendlyURLEntryLocalization.getPrimaryKey()) == null) {

				cacheResult(friendlyURLEntryLocalization);
			}
			else {
				friendlyURLEntryLocalization.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all friendly url entry localizations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FriendlyURLEntryLocalizationImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the friendly url entry localization.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {

		entityCache.removeResult(
			FriendlyURLEntryLocalizationImpl.class,
			friendlyURLEntryLocalization.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(FriendlyURLEntryLocalizationModelImpl)friendlyURLEntryLocalization,
			true);
	}

	@Override
	public void clearCache(
		List<FriendlyURLEntryLocalization> friendlyURLEntryLocalizations) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (FriendlyURLEntryLocalization friendlyURLEntryLocalization :
				friendlyURLEntryLocalizations) {

			entityCache.removeResult(
				FriendlyURLEntryLocalizationImpl.class,
				friendlyURLEntryLocalization.getPrimaryKey());

			clearUniqueFindersCache(
				(FriendlyURLEntryLocalizationModelImpl)
					friendlyURLEntryLocalization,
				true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				FriendlyURLEntryLocalizationImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		FriendlyURLEntryLocalizationModelImpl
			friendlyURLEntryLocalizationModelImpl) {

		Object[] args = new Object[] {
			friendlyURLEntryLocalizationModelImpl.getFriendlyURLEntryId(),
			friendlyURLEntryLocalizationModelImpl.getLanguageId()
		};

		finderCache.putResult(
			_finderPathCountByFriendlyURLEntryId_LanguageId, args,
			Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByFriendlyURLEntryId_LanguageId, args,
			friendlyURLEntryLocalizationModelImpl, false);

		args = new Object[] {
			friendlyURLEntryLocalizationModelImpl.getGroupId(),
			friendlyURLEntryLocalizationModelImpl.getClassNameId(),
			friendlyURLEntryLocalizationModelImpl.getUrlTitle()
		};

		finderCache.putResult(
			_finderPathCountByG_C_U, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_C_U, args,
			friendlyURLEntryLocalizationModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		FriendlyURLEntryLocalizationModelImpl
			friendlyURLEntryLocalizationModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				friendlyURLEntryLocalizationModelImpl.getFriendlyURLEntryId(),
				friendlyURLEntryLocalizationModelImpl.getLanguageId()
			};

			finderCache.removeResult(
				_finderPathCountByFriendlyURLEntryId_LanguageId, args);
			finderCache.removeResult(
				_finderPathFetchByFriendlyURLEntryId_LanguageId, args);
		}

		if ((friendlyURLEntryLocalizationModelImpl.getColumnBitmask() &
			 _finderPathFetchByFriendlyURLEntryId_LanguageId.
				 getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				friendlyURLEntryLocalizationModelImpl.
					getOriginalFriendlyURLEntryId(),
				friendlyURLEntryLocalizationModelImpl.getOriginalLanguageId()
			};

			finderCache.removeResult(
				_finderPathCountByFriendlyURLEntryId_LanguageId, args);
			finderCache.removeResult(
				_finderPathFetchByFriendlyURLEntryId_LanguageId, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				friendlyURLEntryLocalizationModelImpl.getGroupId(),
				friendlyURLEntryLocalizationModelImpl.getClassNameId(),
				friendlyURLEntryLocalizationModelImpl.getUrlTitle()
			};

			finderCache.removeResult(_finderPathCountByG_C_U, args);
			finderCache.removeResult(_finderPathFetchByG_C_U, args);
		}

		if ((friendlyURLEntryLocalizationModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_C_U.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				friendlyURLEntryLocalizationModelImpl.getOriginalGroupId(),
				friendlyURLEntryLocalizationModelImpl.getOriginalClassNameId(),
				friendlyURLEntryLocalizationModelImpl.getOriginalUrlTitle()
			};

			finderCache.removeResult(_finderPathCountByG_C_U, args);
			finderCache.removeResult(_finderPathFetchByG_C_U, args);
		}
	}

	/**
	 * Creates a new friendly url entry localization with the primary key. Does not add the friendly url entry localization to the database.
	 *
	 * @param friendlyURLEntryLocalizationId the primary key for the new friendly url entry localization
	 * @return the new friendly url entry localization
	 */
	@Override
	public FriendlyURLEntryLocalization create(
		long friendlyURLEntryLocalizationId) {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			new FriendlyURLEntryLocalizationImpl();

		friendlyURLEntryLocalization.setNew(true);
		friendlyURLEntryLocalization.setPrimaryKey(
			friendlyURLEntryLocalizationId);

		friendlyURLEntryLocalization.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return friendlyURLEntryLocalization;
	}

	/**
	 * Removes the friendly url entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param friendlyURLEntryLocalizationId the primary key of the friendly url entry localization
	 * @return the friendly url entry localization that was removed
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a friendly url entry localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization remove(
			long friendlyURLEntryLocalizationId)
		throws NoSuchFriendlyURLEntryLocalizationException {

		return remove((Serializable)friendlyURLEntryLocalizationId);
	}

	/**
	 * Removes the friendly url entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the friendly url entry localization
	 * @return the friendly url entry localization that was removed
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a friendly url entry localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization remove(Serializable primaryKey)
		throws NoSuchFriendlyURLEntryLocalizationException {

		Session session = null;

		try {
			session = openSession();

			FriendlyURLEntryLocalization friendlyURLEntryLocalization =
				(FriendlyURLEntryLocalization)session.get(
					FriendlyURLEntryLocalizationImpl.class, primaryKey);

			if (friendlyURLEntryLocalization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFriendlyURLEntryLocalizationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(friendlyURLEntryLocalization);
		}
		catch (NoSuchFriendlyURLEntryLocalizationException
					noSuchEntityException) {

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
	protected FriendlyURLEntryLocalization removeImpl(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {

		if (!ctPersistenceHelper.isRemove(friendlyURLEntryLocalization)) {
			return friendlyURLEntryLocalization;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(friendlyURLEntryLocalization)) {
				friendlyURLEntryLocalization =
					(FriendlyURLEntryLocalization)session.get(
						FriendlyURLEntryLocalizationImpl.class,
						friendlyURLEntryLocalization.getPrimaryKeyObj());
			}

			if (friendlyURLEntryLocalization != null) {
				session.delete(friendlyURLEntryLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (friendlyURLEntryLocalization != null) {
			clearCache(friendlyURLEntryLocalization);
		}

		return friendlyURLEntryLocalization;
	}

	@Override
	public FriendlyURLEntryLocalization updateImpl(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {

		boolean isNew = friendlyURLEntryLocalization.isNew();

		if (!(friendlyURLEntryLocalization instanceof
				FriendlyURLEntryLocalizationModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					friendlyURLEntryLocalization.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					friendlyURLEntryLocalization);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in friendlyURLEntryLocalization proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FriendlyURLEntryLocalization implementation " +
					friendlyURLEntryLocalization.getClass());
		}

		FriendlyURLEntryLocalizationModelImpl
			friendlyURLEntryLocalizationModelImpl =
				(FriendlyURLEntryLocalizationModelImpl)
					friendlyURLEntryLocalization;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(friendlyURLEntryLocalization)) {
				if (!isNew) {
					session.evict(
						FriendlyURLEntryLocalizationImpl.class,
						friendlyURLEntryLocalization.getPrimaryKeyObj());
				}

				session.save(friendlyURLEntryLocalization);

				friendlyURLEntryLocalization.setNew(false);
			}
			else {
				friendlyURLEntryLocalization =
					(FriendlyURLEntryLocalization)session.merge(
						friendlyURLEntryLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (friendlyURLEntryLocalization.getCtCollectionId() != 0) {
			friendlyURLEntryLocalization.resetOriginalValues();

			return friendlyURLEntryLocalization;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				friendlyURLEntryLocalizationModelImpl.getFriendlyURLEntryId()
			};

			finderCache.removeResult(
				_finderPathCountByFriendlyURLEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByFriendlyURLEntryId, args);

			args = new Object[] {
				friendlyURLEntryLocalizationModelImpl.getGroupId(),
				friendlyURLEntryLocalizationModelImpl.getClassNameId(),
				friendlyURLEntryLocalizationModelImpl.getClassPK(),
				friendlyURLEntryLocalizationModelImpl.getLanguageId()
			};

			finderCache.removeResult(_finderPathCountByG_C_C_L, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C_C_L, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((friendlyURLEntryLocalizationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFriendlyURLEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					friendlyURLEntryLocalizationModelImpl.
						getOriginalFriendlyURLEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByFriendlyURLEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFriendlyURLEntryId, args);

				args = new Object[] {
					friendlyURLEntryLocalizationModelImpl.
						getFriendlyURLEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByFriendlyURLEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFriendlyURLEntryId, args);
			}

			if ((friendlyURLEntryLocalizationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_C_L.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					friendlyURLEntryLocalizationModelImpl.getOriginalGroupId(),
					friendlyURLEntryLocalizationModelImpl.
						getOriginalClassNameId(),
					friendlyURLEntryLocalizationModelImpl.getOriginalClassPK(),
					friendlyURLEntryLocalizationModelImpl.
						getOriginalLanguageId()
				};

				finderCache.removeResult(_finderPathCountByG_C_C_L, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C_L, args);

				args = new Object[] {
					friendlyURLEntryLocalizationModelImpl.getGroupId(),
					friendlyURLEntryLocalizationModelImpl.getClassNameId(),
					friendlyURLEntryLocalizationModelImpl.getClassPK(),
					friendlyURLEntryLocalizationModelImpl.getLanguageId()
				};

				finderCache.removeResult(_finderPathCountByG_C_C_L, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C_L, args);
			}
		}

		entityCache.putResult(
			FriendlyURLEntryLocalizationImpl.class,
			friendlyURLEntryLocalization.getPrimaryKey(),
			friendlyURLEntryLocalization, false);

		clearUniqueFindersCache(friendlyURLEntryLocalizationModelImpl, false);
		cacheUniqueFindersCache(friendlyURLEntryLocalizationModelImpl);

		friendlyURLEntryLocalization.resetOriginalValues();

		return friendlyURLEntryLocalization;
	}

	/**
	 * Returns the friendly url entry localization with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the friendly url entry localization
	 * @return the friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a friendly url entry localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchFriendlyURLEntryLocalizationException {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			fetchByPrimaryKey(primaryKey);

		if (friendlyURLEntryLocalization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFriendlyURLEntryLocalizationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return friendlyURLEntryLocalization;
	}

	/**
	 * Returns the friendly url entry localization with the primary key or throws a <code>NoSuchFriendlyURLEntryLocalizationException</code> if it could not be found.
	 *
	 * @param friendlyURLEntryLocalizationId the primary key of the friendly url entry localization
	 * @return the friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a friendly url entry localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization findByPrimaryKey(
			long friendlyURLEntryLocalizationId)
		throws NoSuchFriendlyURLEntryLocalizationException {

		return findByPrimaryKey((Serializable)friendlyURLEntryLocalizationId);
	}

	/**
	 * Returns the friendly url entry localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the friendly url entry localization
	 * @return the friendly url entry localization, or <code>null</code> if a friendly url entry localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				FriendlyURLEntryLocalization.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		FriendlyURLEntryLocalization friendlyURLEntryLocalization = null;

		Session session = null;

		try {
			session = openSession();

			friendlyURLEntryLocalization =
				(FriendlyURLEntryLocalization)session.get(
					FriendlyURLEntryLocalizationImpl.class, primaryKey);

			if (friendlyURLEntryLocalization != null) {
				cacheResult(friendlyURLEntryLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return friendlyURLEntryLocalization;
	}

	/**
	 * Returns the friendly url entry localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param friendlyURLEntryLocalizationId the primary key of the friendly url entry localization
	 * @return the friendly url entry localization, or <code>null</code> if a friendly url entry localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByPrimaryKey(
		long friendlyURLEntryLocalizationId) {

		return fetchByPrimaryKey((Serializable)friendlyURLEntryLocalizationId);
	}

	@Override
	public Map<Serializable, FriendlyURLEntryLocalization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				FriendlyURLEntryLocalization.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, FriendlyURLEntryLocalization> map =
			new HashMap<Serializable, FriendlyURLEntryLocalization>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			FriendlyURLEntryLocalization friendlyURLEntryLocalization =
				fetchByPrimaryKey(primaryKey);

			if (friendlyURLEntryLocalization != null) {
				map.put(primaryKey, friendlyURLEntryLocalization);
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

			for (FriendlyURLEntryLocalization friendlyURLEntryLocalization :
					(List<FriendlyURLEntryLocalization>)query.list()) {

				map.put(
					friendlyURLEntryLocalization.getPrimaryKeyObj(),
					friendlyURLEntryLocalization);

				cacheResult(friendlyURLEntryLocalization);
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
	 * Returns all the friendly url entry localizations.
	 *
	 * @return the friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly url entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FriendlyURLEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url entry localizations
	 * @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	 * @return the range of friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly url entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FriendlyURLEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url entry localizations
	 * @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findAll(
		int start, int end,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly url entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FriendlyURLEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url entry localizations
	 * @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findAll(
		int start, int end,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FriendlyURLEntryLocalization.class);

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

		List<FriendlyURLEntryLocalization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FriendlyURLEntryLocalization>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION;

				sql = sql.concat(
					FriendlyURLEntryLocalizationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FriendlyURLEntryLocalization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Removes all the friendly url entry localizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FriendlyURLEntryLocalization friendlyURLEntryLocalization :
				findAll()) {

			remove(friendlyURLEntryLocalization);
		}
	}

	/**
	 * Returns the number of friendly url entry localizations.
	 *
	 * @return the number of friendly url entry localizations
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FriendlyURLEntryLocalization.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_FRIENDLYURLENTRYLOCALIZATION);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
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

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "friendlyURLEntryLocalizationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION;
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
		return FriendlyURLEntryLocalizationModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "FriendlyURLEntryLocalization";
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
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("friendlyURLEntryId");
		ctStrictColumnNames.add("languageId");
		ctStrictColumnNames.add("urlTitle");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("friendlyURLEntryLocalizationId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"friendlyURLEntryId", "languageId"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "classNameId", "urlTitle"});
	}

	/**
	 * Initializes the friendly url entry localization persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			FriendlyURLEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FriendlyURLEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByFriendlyURLEntryId = new FinderPath(
			FriendlyURLEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFriendlyURLEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByFriendlyURLEntryId = new FinderPath(
			FriendlyURLEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByFriendlyURLEntryId", new String[] {Long.class.getName()},
			FriendlyURLEntryLocalizationModelImpl.
				FRIENDLYURLENTRYID_COLUMN_BITMASK);

		_finderPathCountByFriendlyURLEntryId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFriendlyURLEntryId", new String[] {Long.class.getName()});

		_finderPathFetchByFriendlyURLEntryId_LanguageId = new FinderPath(
			FriendlyURLEntryLocalizationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByFriendlyURLEntryId_LanguageId",
			new String[] {Long.class.getName(), String.class.getName()},
			FriendlyURLEntryLocalizationModelImpl.
				FRIENDLYURLENTRYID_COLUMN_BITMASK |
			FriendlyURLEntryLocalizationModelImpl.LANGUAGEID_COLUMN_BITMASK);

		_finderPathCountByFriendlyURLEntryId_LanguageId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFriendlyURLEntryId_LanguageId",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByG_C_U = new FinderPath(
			FriendlyURLEntryLocalizationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			FriendlyURLEntryLocalizationModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLEntryLocalizationModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FriendlyURLEntryLocalizationModelImpl.URLTITLE_COLUMN_BITMASK);

		_finderPathCountByG_C_U = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationFindByG_C_C_L = new FinderPath(
			FriendlyURLEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_C_L = new FinderPath(
			FriendlyURLEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			FriendlyURLEntryLocalizationModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLEntryLocalizationModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FriendlyURLEntryLocalizationModelImpl.CLASSPK_COLUMN_BITMASK |
			FriendlyURLEntryLocalizationModelImpl.LANGUAGEID_COLUMN_BITMASK);

		_finderPathCountByG_C_C_L = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(
			FriendlyURLEntryLocalizationImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = FURLPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = FURLPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = FURLPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION =
		"SELECT friendlyURLEntryLocalization FROM FriendlyURLEntryLocalization friendlyURLEntryLocalization";

	private static final String _SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION_WHERE =
		"SELECT friendlyURLEntryLocalization FROM FriendlyURLEntryLocalization friendlyURLEntryLocalization WHERE ";

	private static final String _SQL_COUNT_FRIENDLYURLENTRYLOCALIZATION =
		"SELECT COUNT(friendlyURLEntryLocalization) FROM FriendlyURLEntryLocalization friendlyURLEntryLocalization";

	private static final String _SQL_COUNT_FRIENDLYURLENTRYLOCALIZATION_WHERE =
		"SELECT COUNT(friendlyURLEntryLocalization) FROM FriendlyURLEntryLocalization friendlyURLEntryLocalization WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"friendlyURLEntryLocalization.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FriendlyURLEntryLocalization exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FriendlyURLEntryLocalization exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FriendlyURLEntryLocalizationPersistenceImpl.class);

	static {
		try {
			Class.forName(FURLPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}