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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryLocalizationException;
import com.liferay.portal.tools.service.builder.test.model.LVEntryLocalization;
import com.liferay.portal.tools.service.builder.test.model.impl.LVEntryLocalizationImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.LVEntryLocalizationModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.LVEntryLocalizationPersistence;

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
 * The persistence implementation for the lv entry localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryLocalizationPersistence
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.LVEntryLocalizationUtil
 * @generated
 */
@ProviderType
public class LVEntryLocalizationPersistenceImpl extends BasePersistenceImpl<LVEntryLocalization>
	implements LVEntryLocalizationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LVEntryLocalizationUtil} to access the lv entry localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LVEntryLocalizationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ENTRYID = new FinderPath(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID =
		new FinderPath(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByEntryId",
			new String[] { Long.class.getName() },
			LVEntryLocalizationModelImpl.ENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ENTRYID = new FinderPath(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the lv entry localizations where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @return the matching lv entry localizations
	 */
	@Override
	public List<LVEntryLocalization> findByEntryId(long entryId) {
		return findByEntryId(entryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry localizations where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @return the range of matching lv entry localizations
	 */
	@Override
	public List<LVEntryLocalization> findByEntryId(long entryId, int start,
		int end) {
		return findByEntryId(entryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry localizations where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localizations
	 */
	@Override
	public List<LVEntryLocalization> findByEntryId(long entryId, int start,
		int end, OrderByComparator<LVEntryLocalization> orderByComparator) {
		return findByEntryId(entryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry localizations where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching lv entry localizations
	 */
	@Override
	public List<LVEntryLocalization> findByEntryId(long entryId, int start,
		int end, OrderByComparator<LVEntryLocalization> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID;
			finderArgs = new Object[] { entryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ENTRYID;
			finderArgs = new Object[] { entryId, start, end, orderByComparator };
		}

		List<LVEntryLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<LVEntryLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryLocalization lvEntryLocalization : list) {
					if ((entryId != lvEntryLocalization.getEntryId())) {
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

			query.append(_SQL_SELECT_LVENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LVEntryLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				if (!pagination) {
					list = (List<LVEntryLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LVEntryLocalization>)QueryUtil.list(q,
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
	 * Returns the first lv entry localization in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization findByEntryId_First(long entryId,
		OrderByComparator<LVEntryLocalization> orderByComparator)
		throws NoSuchLVEntryLocalizationException {
		LVEntryLocalization lvEntryLocalization = fetchByEntryId_First(entryId,
				orderByComparator);

		if (lvEntryLocalization != null) {
			return lvEntryLocalization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationException(msg.toString());
	}

	/**
	 * Returns the first lv entry localization in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization fetchByEntryId_First(long entryId,
		OrderByComparator<LVEntryLocalization> orderByComparator) {
		List<LVEntryLocalization> list = findByEntryId(entryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry localization in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization findByEntryId_Last(long entryId,
		OrderByComparator<LVEntryLocalization> orderByComparator)
		throws NoSuchLVEntryLocalizationException {
		LVEntryLocalization lvEntryLocalization = fetchByEntryId_Last(entryId,
				orderByComparator);

		if (lvEntryLocalization != null) {
			return lvEntryLocalization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationException(msg.toString());
	}

	/**
	 * Returns the last lv entry localization in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization fetchByEntryId_Last(long entryId,
		OrderByComparator<LVEntryLocalization> orderByComparator) {
		int count = countByEntryId(entryId);

		if (count == 0) {
			return null;
		}

		List<LVEntryLocalization> list = findByEntryId(entryId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry localizations before and after the current lv entry localization in the ordered set where entryId = &#63;.
	 *
	 * @param lvEntryLocalizationId the primary key of the current lv entry localization
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a lv entry localization with the primary key could not be found
	 */
	@Override
	public LVEntryLocalization[] findByEntryId_PrevAndNext(
		long lvEntryLocalizationId, long entryId,
		OrderByComparator<LVEntryLocalization> orderByComparator)
		throws NoSuchLVEntryLocalizationException {
		LVEntryLocalization lvEntryLocalization = findByPrimaryKey(lvEntryLocalizationId);

		Session session = null;

		try {
			session = openSession();

			LVEntryLocalization[] array = new LVEntryLocalizationImpl[3];

			array[0] = getByEntryId_PrevAndNext(session, lvEntryLocalization,
					entryId, orderByComparator, true);

			array[1] = lvEntryLocalization;

			array[2] = getByEntryId_PrevAndNext(session, lvEntryLocalization,
					entryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LVEntryLocalization getByEntryId_PrevAndNext(Session session,
		LVEntryLocalization lvEntryLocalization, long entryId,
		OrderByComparator<LVEntryLocalization> orderByComparator,
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

		query.append(_SQL_SELECT_LVENTRYLOCALIZATION_WHERE);

		query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

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
			query.append(LVEntryLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(lvEntryLocalization);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LVEntryLocalization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry localizations where entryId = &#63; from the database.
	 *
	 * @param entryId the entry ID
	 */
	@Override
	public void removeByEntryId(long entryId) {
		for (LVEntryLocalization lvEntryLocalization : findByEntryId(entryId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(lvEntryLocalization);
		}
	}

	/**
	 * Returns the number of lv entry localizations where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @return the number of matching lv entry localizations
	 */
	@Override
	public int countByEntryId(long entryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ENTRYID;

		Object[] finderArgs = new Object[] { entryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LVENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

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

	private static final String _FINDER_COLUMN_ENTRYID_ENTRYID_2 = "lvEntryLocalization.entryId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID = new FinderPath(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByEntryId_LanguageId",
			new String[] { Long.class.getName(), String.class.getName() },
			LVEntryLocalizationModelImpl.ENTRYID_COLUMN_BITMASK |
			LVEntryLocalizationModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ENTRYID_LANGUAGEID = new FinderPath(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByEntryId_LanguageId",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the lv entry localization where entryId = &#63; and languageId = &#63; or throws a {@link NoSuchLVEntryLocalizationException} if it could not be found.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @return the matching lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization findByEntryId_LanguageId(long entryId,
		String languageId) throws NoSuchLVEntryLocalizationException {
		LVEntryLocalization lvEntryLocalization = fetchByEntryId_LanguageId(entryId,
				languageId);

		if (lvEntryLocalization == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId=");
			msg.append(entryId);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLVEntryLocalizationException(msg.toString());
		}

		return lvEntryLocalization;
	}

	/**
	 * Returns the lv entry localization where entryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization fetchByEntryId_LanguageId(long entryId,
		String languageId) {
		return fetchByEntryId_LanguageId(entryId, languageId, true);
	}

	/**
	 * Returns the lv entry localization where entryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization fetchByEntryId_LanguageId(long entryId,
		String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { entryId, languageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID,
					finderArgs, this);
		}

		if (result instanceof LVEntryLocalization) {
			LVEntryLocalization lvEntryLocalization = (LVEntryLocalization)result;

			if ((entryId != lvEntryLocalization.getEntryId()) ||
					!Objects.equals(languageId,
						lvEntryLocalization.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LVENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_ENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				List<LVEntryLocalization> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID,
						finderArgs, list);
				}
				else {
					LVEntryLocalization lvEntryLocalization = list.get(0);

					result = lvEntryLocalization;

					cacheResult(lvEntryLocalization);

					if ((lvEntryLocalization.getEntryId() != entryId) ||
							(lvEntryLocalization.getLanguageId() == null) ||
							!lvEntryLocalization.getLanguageId()
													.equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID,
							finderArgs, lvEntryLocalization);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID,
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
			return (LVEntryLocalization)result;
		}
	}

	/**
	 * Removes the lv entry localization where entryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @return the lv entry localization that was removed
	 */
	@Override
	public LVEntryLocalization removeByEntryId_LanguageId(long entryId,
		String languageId) throws NoSuchLVEntryLocalizationException {
		LVEntryLocalization lvEntryLocalization = findByEntryId_LanguageId(entryId,
				languageId);

		return remove(lvEntryLocalization);
	}

	/**
	 * Returns the number of lv entry localizations where entryId = &#63; and languageId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @return the number of matching lv entry localizations
	 */
	@Override
	public int countByEntryId_LanguageId(long entryId, String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ENTRYID_LANGUAGEID;

		Object[] finderArgs = new Object[] { entryId, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LVENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_ENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

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

	private static final String _FINDER_COLUMN_ENTRYID_LANGUAGEID_ENTRYID_2 = "lvEntryLocalization.entryId = ? AND ";
	private static final String _FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_1 = "lvEntryLocalization.languageId IS NULL";
	private static final String _FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_2 = "lvEntryLocalization.languageId = ?";
	private static final String _FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_3 = "(lvEntryLocalization.languageId IS NULL OR lvEntryLocalization.languageId = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_HEADID = new FinderPath(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByHeadId", new String[] { Long.class.getName() },
			LVEntryLocalizationModelImpl.HEADID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_HEADID = new FinderPath(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByHeadId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the lv entry localization where headId = &#63; or throws a {@link NoSuchLVEntryLocalizationException} if it could not be found.
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
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("headId=");
			msg.append(headId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLVEntryLocalizationException(msg.toString());
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
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	@Override
	public LVEntryLocalization fetchByHeadId(long headId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { headId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_HEADID,
					finderArgs, this);
		}

		if (result instanceof LVEntryLocalization) {
			LVEntryLocalization lvEntryLocalization = (LVEntryLocalization)result;

			if ((headId != lvEntryLocalization.getHeadId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_LVENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_HEADID_HEADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(headId);

				List<LVEntryLocalization> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_HEADID,
						finderArgs, list);
				}
				else {
					LVEntryLocalization lvEntryLocalization = list.get(0);

					result = lvEntryLocalization;

					cacheResult(lvEntryLocalization);

					if ((lvEntryLocalization.getHeadId() != headId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_HEADID,
							finderArgs, lvEntryLocalization);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_HEADID, finderArgs);

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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_HEADID;

		Object[] finderArgs = new Object[] { headId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LVENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_HEADID_HEADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(headId);

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

	private static final String _FINDER_COLUMN_HEADID_HEADID_2 = "lvEntryLocalization.headId = ?";

	public LVEntryLocalizationPersistenceImpl() {
		setModelClass(LVEntryLocalization.class);
	}

	/**
	 * Caches the lv entry localization in the entity cache if it is enabled.
	 *
	 * @param lvEntryLocalization the lv entry localization
	 */
	@Override
	public void cacheResult(LVEntryLocalization lvEntryLocalization) {
		entityCache.putResult(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationImpl.class, lvEntryLocalization.getPrimaryKey(),
			lvEntryLocalization);

		finderCache.putResult(FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID,
			new Object[] {
				lvEntryLocalization.getEntryId(),
				lvEntryLocalization.getLanguageId()
			}, lvEntryLocalization);

		finderCache.putResult(FINDER_PATH_FETCH_BY_HEADID,
			new Object[] { lvEntryLocalization.getHeadId() },
			lvEntryLocalization);

		lvEntryLocalization.resetOriginalValues();
	}

	/**
	 * Caches the lv entry localizations in the entity cache if it is enabled.
	 *
	 * @param lvEntryLocalizations the lv entry localizations
	 */
	@Override
	public void cacheResult(List<LVEntryLocalization> lvEntryLocalizations) {
		for (LVEntryLocalization lvEntryLocalization : lvEntryLocalizations) {
			if (entityCache.getResult(
						LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
						LVEntryLocalizationImpl.class,
						lvEntryLocalization.getPrimaryKey()) == null) {
				cacheResult(lvEntryLocalization);
			}
			else {
				lvEntryLocalization.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all lv entry localizations.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LVEntryLocalizationImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the lv entry localization.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LVEntryLocalization lvEntryLocalization) {
		entityCache.removeResult(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationImpl.class, lvEntryLocalization.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LVEntryLocalizationModelImpl)lvEntryLocalization,
			true);
	}

	@Override
	public void clearCache(List<LVEntryLocalization> lvEntryLocalizations) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LVEntryLocalization lvEntryLocalization : lvEntryLocalizations) {
			entityCache.removeResult(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryLocalizationImpl.class,
				lvEntryLocalization.getPrimaryKey());

			clearUniqueFindersCache((LVEntryLocalizationModelImpl)lvEntryLocalization,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		LVEntryLocalizationModelImpl lvEntryLocalizationModelImpl) {
		Object[] args = new Object[] {
				lvEntryLocalizationModelImpl.getEntryId(),
				lvEntryLocalizationModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_ENTRYID_LANGUAGEID, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID, args,
			lvEntryLocalizationModelImpl, false);

		args = new Object[] { lvEntryLocalizationModelImpl.getHeadId() };

		finderCache.putResult(FINDER_PATH_COUNT_BY_HEADID, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_HEADID, args,
			lvEntryLocalizationModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LVEntryLocalizationModelImpl lvEntryLocalizationModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					lvEntryLocalizationModelImpl.getEntryId(),
					lvEntryLocalizationModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID_LANGUAGEID,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID,
				args);
		}

		if ((lvEntryLocalizationModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					lvEntryLocalizationModelImpl.getOriginalEntryId(),
					lvEntryLocalizationModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID_LANGUAGEID,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID,
				args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					lvEntryLocalizationModelImpl.getHeadId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_HEADID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_HEADID, args);
		}

		if ((lvEntryLocalizationModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_HEADID.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					lvEntryLocalizationModelImpl.getOriginalHeadId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_HEADID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_HEADID, args);
		}
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

			LVEntryLocalization lvEntryLocalization = (LVEntryLocalization)session.get(LVEntryLocalizationImpl.class,
					primaryKey);

			if (lvEntryLocalization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLVEntryLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(lvEntryLocalization);
		}
		catch (NoSuchLVEntryLocalizationException nsee) {
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
	protected LVEntryLocalization removeImpl(
		LVEntryLocalization lvEntryLocalization) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(lvEntryLocalization)) {
				lvEntryLocalization = (LVEntryLocalization)session.get(LVEntryLocalizationImpl.class,
						lvEntryLocalization.getPrimaryKeyObj());
			}

			if (lvEntryLocalization != null) {
				session.delete(lvEntryLocalization);
			}
		}
		catch (Exception e) {
			throw processException(e);
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
				invocationHandler = ProxyUtil.getInvocationHandler(lvEntryLocalization);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in lvEntryLocalization proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LVEntryLocalization implementation " +
				lvEntryLocalization.getClass());
		}

		LVEntryLocalizationModelImpl lvEntryLocalizationModelImpl = (LVEntryLocalizationModelImpl)lvEntryLocalization;

		Session session = null;

		try {
			session = openSession();

			if (lvEntryLocalization.isNew()) {
				session.save(lvEntryLocalization);

				lvEntryLocalization.setNew(false);
			}
			else {
				lvEntryLocalization = (LVEntryLocalization)session.merge(lvEntryLocalization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LVEntryLocalizationModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					lvEntryLocalizationModelImpl.getEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((lvEntryLocalizationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						lvEntryLocalizationModelImpl.getOriginalEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID,
					args);

				args = new Object[] { lvEntryLocalizationModelImpl.getEntryId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID,
					args);
			}
		}

		entityCache.putResult(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationImpl.class, lvEntryLocalization.getPrimaryKey(),
			lvEntryLocalization, false);

		clearUniqueFindersCache(lvEntryLocalizationModelImpl, false);
		cacheUniqueFindersCache(lvEntryLocalizationModelImpl);

		lvEntryLocalization.resetOriginalValues();

		return lvEntryLocalization;
	}

	/**
	 * Returns the lv entry localization with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
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

			throw new NoSuchLVEntryLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return lvEntryLocalization;
	}

	/**
	 * Returns the lv entry localization with the primary key or throws a {@link NoSuchLVEntryLocalizationException} if it could not be found.
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
	 * @param primaryKey the primary key of the lv entry localization
	 * @return the lv entry localization, or <code>null</code> if a lv entry localization with the primary key could not be found
	 */
	@Override
	public LVEntryLocalization fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryLocalizationImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LVEntryLocalization lvEntryLocalization = (LVEntryLocalization)serializable;

		if (lvEntryLocalization == null) {
			Session session = null;

			try {
				session = openSession();

				lvEntryLocalization = (LVEntryLocalization)session.get(LVEntryLocalizationImpl.class,
						primaryKey);

				if (lvEntryLocalization != null) {
					cacheResult(lvEntryLocalization);
				}
				else {
					entityCache.putResult(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
						LVEntryLocalizationImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					LVEntryLocalizationImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return lvEntryLocalization;
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

	@Override
	public Map<Serializable, LVEntryLocalization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LVEntryLocalization> map = new HashMap<Serializable, LVEntryLocalization>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LVEntryLocalization lvEntryLocalization = fetchByPrimaryKey(primaryKey);

			if (lvEntryLocalization != null) {
				map.put(primaryKey, lvEntryLocalization);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					LVEntryLocalizationImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (LVEntryLocalization)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_LVENTRYLOCALIZATION_WHERE_PKS_IN);

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

			for (LVEntryLocalization lvEntryLocalization : (List<LVEntryLocalization>)q.list()) {
				map.put(lvEntryLocalization.getPrimaryKeyObj(),
					lvEntryLocalization);

				cacheResult(lvEntryLocalization);

				uncachedPrimaryKeys.remove(lvEntryLocalization.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(LVEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					LVEntryLocalizationImpl.class, primaryKey, nullModel);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lv entry localizations
	 */
	@Override
	public List<LVEntryLocalization> findAll(int start, int end,
		OrderByComparator<LVEntryLocalization> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of lv entry localizations
	 */
	@Override
	public List<LVEntryLocalization> findAll(int start, int end,
		OrderByComparator<LVEntryLocalization> orderByComparator,
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

		List<LVEntryLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<LVEntryLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LVENTRYLOCALIZATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LVENTRYLOCALIZATION;

				if (pagination) {
					sql = sql.concat(LVEntryLocalizationModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LVEntryLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LVEntryLocalization>)QueryUtil.list(q,
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
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LVENTRYLOCALIZATION);

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
		return LVEntryLocalizationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the lv entry localization persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(LVEntryLocalizationImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_LVENTRYLOCALIZATION = "SELECT lvEntryLocalization FROM LVEntryLocalization lvEntryLocalization";
	private static final String _SQL_SELECT_LVENTRYLOCALIZATION_WHERE_PKS_IN = "SELECT lvEntryLocalization FROM LVEntryLocalization lvEntryLocalization WHERE lvEntryLocalizationId IN (";
	private static final String _SQL_SELECT_LVENTRYLOCALIZATION_WHERE = "SELECT lvEntryLocalization FROM LVEntryLocalization lvEntryLocalization WHERE ";
	private static final String _SQL_COUNT_LVENTRYLOCALIZATION = "SELECT COUNT(lvEntryLocalization) FROM LVEntryLocalization lvEntryLocalization";
	private static final String _SQL_COUNT_LVENTRYLOCALIZATION_WHERE = "SELECT COUNT(lvEntryLocalization) FROM LVEntryLocalization lvEntryLocalization WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "lvEntryLocalization.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LVEntryLocalization exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LVEntryLocalization exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LVEntryLocalizationPersistenceImpl.class);
}