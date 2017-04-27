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

package com.liferay.commerce.product.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionLocalizationException;
import com.liferay.commerce.product.model.CPDefinitionLocalization;
import com.liferay.commerce.product.model.impl.CPDefinitionLocalizationImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionLocalizationModelImpl;
import com.liferay.commerce.product.service.persistence.CPDefinitionLocalizationPersistence;

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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the cp definition localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionLocalizationPersistence
 * @see com.liferay.commerce.product.service.persistence.CPDefinitionLocalizationUtil
 * @generated
 */
@ProviderType
public class CPDefinitionLocalizationPersistenceImpl extends BasePersistenceImpl<CPDefinitionLocalization>
	implements CPDefinitionLocalizationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPDefinitionLocalizationUtil} to access the cp definition localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPDefinitionLocalizationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLocalizationModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLocalizationModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLocalizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CPDEFINITIONPK =
		new FinderPath(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLocalizationModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPDefinitionPK",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONPK =
		new FinderPath(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLocalizationModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPDefinitionPK",
			new String[] { Long.class.getName() },
			CPDefinitionLocalizationModelImpl.CPDEFINITIONPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPDEFINITIONPK = new FinderPath(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLocalizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPDefinitionPK",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the cp definition localizations where cpDefinitionPK = &#63;.
	 *
	 * @param cpDefinitionPK the cp definition pk
	 * @return the matching cp definition localizations
	 */
	@Override
	public List<CPDefinitionLocalization> findByCPDefinitionPK(
		long cpDefinitionPK) {
		return findByCPDefinitionPK(cpDefinitionPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition localizations where cpDefinitionPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param cpDefinitionPK the cp definition pk
	 * @param start the lower bound of the range of cp definition localizations
	 * @param end the upper bound of the range of cp definition localizations (not inclusive)
	 * @return the range of matching cp definition localizations
	 */
	@Override
	public List<CPDefinitionLocalization> findByCPDefinitionPK(
		long cpDefinitionPK, int start, int end) {
		return findByCPDefinitionPK(cpDefinitionPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition localizations where cpDefinitionPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param cpDefinitionPK the cp definition pk
	 * @param start the lower bound of the range of cp definition localizations
	 * @param end the upper bound of the range of cp definition localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition localizations
	 */
	@Override
	public List<CPDefinitionLocalization> findByCPDefinitionPK(
		long cpDefinitionPK, int start, int end,
		OrderByComparator<CPDefinitionLocalization> orderByComparator) {
		return findByCPDefinitionPK(cpDefinitionPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition localizations where cpDefinitionPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param cpDefinitionPK the cp definition pk
	 * @param start the lower bound of the range of cp definition localizations
	 * @param end the upper bound of the range of cp definition localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition localizations
	 */
	@Override
	public List<CPDefinitionLocalization> findByCPDefinitionPK(
		long cpDefinitionPK, int start, int end,
		OrderByComparator<CPDefinitionLocalization> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONPK;
			finderArgs = new Object[] { cpDefinitionPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CPDEFINITIONPK;
			finderArgs = new Object[] {
					cpDefinitionPK,
					
					start, end, orderByComparator
				};
		}

		List<CPDefinitionLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionLocalization cpDefinitionLocalization : list) {
					if ((cpDefinitionPK != cpDefinitionLocalization.getCpDefinitionPK())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONPK_CPDEFINITIONPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPDefinitionLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(cpDefinitionPK);

				if (!pagination) {
					list = (List<CPDefinitionLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionLocalization>)QueryUtil.list(q,
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
	 * Returns the first cp definition localization in the ordered set where cpDefinitionPK = &#63;.
	 *
	 * @param cpDefinitionPK the cp definition pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition localization
	 * @throws NoSuchCPDefinitionLocalizationException if a matching cp definition localization could not be found
	 */
	@Override
	public CPDefinitionLocalization findByCPDefinitionPK_First(
		long cpDefinitionPK,
		OrderByComparator<CPDefinitionLocalization> orderByComparator)
		throws NoSuchCPDefinitionLocalizationException {
		CPDefinitionLocalization cpDefinitionLocalization = fetchByCPDefinitionPK_First(cpDefinitionPK,
				orderByComparator);

		if (cpDefinitionLocalization != null) {
			return cpDefinitionLocalization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("cpDefinitionPK=");
		msg.append(cpDefinitionPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPDefinitionLocalizationException(msg.toString());
	}

	/**
	 * Returns the first cp definition localization in the ordered set where cpDefinitionPK = &#63;.
	 *
	 * @param cpDefinitionPK the cp definition pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition localization, or <code>null</code> if a matching cp definition localization could not be found
	 */
	@Override
	public CPDefinitionLocalization fetchByCPDefinitionPK_First(
		long cpDefinitionPK,
		OrderByComparator<CPDefinitionLocalization> orderByComparator) {
		List<CPDefinitionLocalization> list = findByCPDefinitionPK(cpDefinitionPK,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition localization in the ordered set where cpDefinitionPK = &#63;.
	 *
	 * @param cpDefinitionPK the cp definition pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition localization
	 * @throws NoSuchCPDefinitionLocalizationException if a matching cp definition localization could not be found
	 */
	@Override
	public CPDefinitionLocalization findByCPDefinitionPK_Last(
		long cpDefinitionPK,
		OrderByComparator<CPDefinitionLocalization> orderByComparator)
		throws NoSuchCPDefinitionLocalizationException {
		CPDefinitionLocalization cpDefinitionLocalization = fetchByCPDefinitionPK_Last(cpDefinitionPK,
				orderByComparator);

		if (cpDefinitionLocalization != null) {
			return cpDefinitionLocalization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("cpDefinitionPK=");
		msg.append(cpDefinitionPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPDefinitionLocalizationException(msg.toString());
	}

	/**
	 * Returns the last cp definition localization in the ordered set where cpDefinitionPK = &#63;.
	 *
	 * @param cpDefinitionPK the cp definition pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition localization, or <code>null</code> if a matching cp definition localization could not be found
	 */
	@Override
	public CPDefinitionLocalization fetchByCPDefinitionPK_Last(
		long cpDefinitionPK,
		OrderByComparator<CPDefinitionLocalization> orderByComparator) {
		int count = countByCPDefinitionPK(cpDefinitionPK);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionLocalization> list = findByCPDefinitionPK(cpDefinitionPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition localizations before and after the current cp definition localization in the ordered set where cpDefinitionPK = &#63;.
	 *
	 * @param cpDefinitionLocalizationId the primary key of the current cp definition localization
	 * @param cpDefinitionPK the cp definition pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition localization
	 * @throws NoSuchCPDefinitionLocalizationException if a cp definition localization with the primary key could not be found
	 */
	@Override
	public CPDefinitionLocalization[] findByCPDefinitionPK_PrevAndNext(
		long cpDefinitionLocalizationId, long cpDefinitionPK,
		OrderByComparator<CPDefinitionLocalization> orderByComparator)
		throws NoSuchCPDefinitionLocalizationException {
		CPDefinitionLocalization cpDefinitionLocalization = findByPrimaryKey(cpDefinitionLocalizationId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionLocalization[] array = new CPDefinitionLocalizationImpl[3];

			array[0] = getByCPDefinitionPK_PrevAndNext(session,
					cpDefinitionLocalization, cpDefinitionPK,
					orderByComparator, true);

			array[1] = cpDefinitionLocalization;

			array[2] = getByCPDefinitionPK_PrevAndNext(session,
					cpDefinitionLocalization, cpDefinitionPK,
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

	protected CPDefinitionLocalization getByCPDefinitionPK_PrevAndNext(
		Session session, CPDefinitionLocalization cpDefinitionLocalization,
		long cpDefinitionPK,
		OrderByComparator<CPDefinitionLocalization> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONLOCALIZATION_WHERE);

		query.append(_FINDER_COLUMN_CPDEFINITIONPK_CPDEFINITIONPK_2);

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
			query.append(CPDefinitionLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(cpDefinitionPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionLocalization);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionLocalization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition localizations where cpDefinitionPK = &#63; from the database.
	 *
	 * @param cpDefinitionPK the cp definition pk
	 */
	@Override
	public void removeByCPDefinitionPK(long cpDefinitionPK) {
		for (CPDefinitionLocalization cpDefinitionLocalization : findByCPDefinitionPK(
				cpDefinitionPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionLocalization);
		}
	}

	/**
	 * Returns the number of cp definition localizations where cpDefinitionPK = &#63;.
	 *
	 * @param cpDefinitionPK the cp definition pk
	 * @return the number of matching cp definition localizations
	 */
	@Override
	public int countByCPDefinitionPK(long cpDefinitionPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPDEFINITIONPK;

		Object[] finderArgs = new Object[] { cpDefinitionPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONPK_CPDEFINITIONPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(cpDefinitionPK);

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

	private static final String _FINDER_COLUMN_CPDEFINITIONPK_CPDEFINITIONPK_2 = "cpDefinitionLocalization.cpDefinitionPK = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_CPD_L = new FinderPath(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLocalizationModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionLocalizationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCPD_L",
			new String[] { Long.class.getName(), String.class.getName() },
			CPDefinitionLocalizationModelImpl.CPDEFINITIONPK_COLUMN_BITMASK |
			CPDefinitionLocalizationModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPD_L = new FinderPath(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLocalizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPD_L",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the cp definition localization where cpDefinitionPK = &#63; and languageId = &#63; or throws a {@link NoSuchCPDefinitionLocalizationException} if it could not be found.
	 *
	 * @param cpDefinitionPK the cp definition pk
	 * @param languageId the language ID
	 * @return the matching cp definition localization
	 * @throws NoSuchCPDefinitionLocalizationException if a matching cp definition localization could not be found
	 */
	@Override
	public CPDefinitionLocalization findByCPD_L(long cpDefinitionPK,
		String languageId) throws NoSuchCPDefinitionLocalizationException {
		CPDefinitionLocalization cpDefinitionLocalization = fetchByCPD_L(cpDefinitionPK,
				languageId);

		if (cpDefinitionLocalization == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("cpDefinitionPK=");
			msg.append(cpDefinitionPK);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPDefinitionLocalizationException(msg.toString());
		}

		return cpDefinitionLocalization;
	}

	/**
	 * Returns the cp definition localization where cpDefinitionPK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param cpDefinitionPK the cp definition pk
	 * @param languageId the language ID
	 * @return the matching cp definition localization, or <code>null</code> if a matching cp definition localization could not be found
	 */
	@Override
	public CPDefinitionLocalization fetchByCPD_L(long cpDefinitionPK,
		String languageId) {
		return fetchByCPD_L(cpDefinitionPK, languageId, true);
	}

	/**
	 * Returns the cp definition localization where cpDefinitionPK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param cpDefinitionPK the cp definition pk
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp definition localization, or <code>null</code> if a matching cp definition localization could not be found
	 */
	@Override
	public CPDefinitionLocalization fetchByCPD_L(long cpDefinitionPK,
		String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { cpDefinitionPK, languageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_CPD_L,
					finderArgs, this);
		}

		if (result instanceof CPDefinitionLocalization) {
			CPDefinitionLocalization cpDefinitionLocalization = (CPDefinitionLocalization)result;

			if ((cpDefinitionPK != cpDefinitionLocalization.getCpDefinitionPK()) ||
					!Objects.equals(languageId,
						cpDefinitionLocalization.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPDEFINITIONLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_CPD_L_CPDEFINITIONPK_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_CPD_L_LANGUAGEID_1);
			}
			else if (languageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CPD_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_CPD_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(cpDefinitionPK);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				List<CPDefinitionLocalization> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_CPD_L,
						finderArgs, list);
				}
				else {
					CPDefinitionLocalization cpDefinitionLocalization = list.get(0);

					result = cpDefinitionLocalization;

					cacheResult(cpDefinitionLocalization);

					if ((cpDefinitionLocalization.getCpDefinitionPK() != cpDefinitionPK) ||
							(cpDefinitionLocalization.getLanguageId() == null) ||
							!cpDefinitionLocalization.getLanguageId()
														 .equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_CPD_L,
							finderArgs, cpDefinitionLocalization);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_CPD_L, finderArgs);

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
			return (CPDefinitionLocalization)result;
		}
	}

	/**
	 * Removes the cp definition localization where cpDefinitionPK = &#63; and languageId = &#63; from the database.
	 *
	 * @param cpDefinitionPK the cp definition pk
	 * @param languageId the language ID
	 * @return the cp definition localization that was removed
	 */
	@Override
	public CPDefinitionLocalization removeByCPD_L(long cpDefinitionPK,
		String languageId) throws NoSuchCPDefinitionLocalizationException {
		CPDefinitionLocalization cpDefinitionLocalization = findByCPD_L(cpDefinitionPK,
				languageId);

		return remove(cpDefinitionLocalization);
	}

	/**
	 * Returns the number of cp definition localizations where cpDefinitionPK = &#63; and languageId = &#63;.
	 *
	 * @param cpDefinitionPK the cp definition pk
	 * @param languageId the language ID
	 * @return the number of matching cp definition localizations
	 */
	@Override
	public int countByCPD_L(long cpDefinitionPK, String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPD_L;

		Object[] finderArgs = new Object[] { cpDefinitionPK, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDEFINITIONLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_CPD_L_CPDEFINITIONPK_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_CPD_L_LANGUAGEID_1);
			}
			else if (languageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CPD_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_CPD_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(cpDefinitionPK);

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

	private static final String _FINDER_COLUMN_CPD_L_CPDEFINITIONPK_2 = "cpDefinitionLocalization.cpDefinitionPK = ? AND ";
	private static final String _FINDER_COLUMN_CPD_L_LANGUAGEID_1 = "cpDefinitionLocalization.languageId IS NULL";
	private static final String _FINDER_COLUMN_CPD_L_LANGUAGEID_2 = "cpDefinitionLocalization.languageId = ?";
	private static final String _FINDER_COLUMN_CPD_L_LANGUAGEID_3 = "(cpDefinitionLocalization.languageId IS NULL OR cpDefinitionLocalization.languageId = '')";

	public CPDefinitionLocalizationPersistenceImpl() {
		setModelClass(CPDefinitionLocalization.class);
	}

	/**
	 * Caches the cp definition localization in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionLocalization the cp definition localization
	 */
	@Override
	public void cacheResult(CPDefinitionLocalization cpDefinitionLocalization) {
		entityCache.putResult(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLocalizationImpl.class,
			cpDefinitionLocalization.getPrimaryKey(), cpDefinitionLocalization);

		finderCache.putResult(FINDER_PATH_FETCH_BY_CPD_L,
			new Object[] {
				cpDefinitionLocalization.getCpDefinitionPK(),
				cpDefinitionLocalization.getLanguageId()
			}, cpDefinitionLocalization);

		cpDefinitionLocalization.resetOriginalValues();
	}

	/**
	 * Caches the cp definition localizations in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionLocalizations the cp definition localizations
	 */
	@Override
	public void cacheResult(
		List<CPDefinitionLocalization> cpDefinitionLocalizations) {
		for (CPDefinitionLocalization cpDefinitionLocalization : cpDefinitionLocalizations) {
			if (entityCache.getResult(
						CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionLocalizationImpl.class,
						cpDefinitionLocalization.getPrimaryKey()) == null) {
				cacheResult(cpDefinitionLocalization);
			}
			else {
				cpDefinitionLocalization.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp definition localizations.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionLocalizationImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp definition localization.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPDefinitionLocalization cpDefinitionLocalization) {
		entityCache.removeResult(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLocalizationImpl.class,
			cpDefinitionLocalization.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CPDefinitionLocalizationModelImpl)cpDefinitionLocalization,
			true);
	}

	@Override
	public void clearCache(
		List<CPDefinitionLocalization> cpDefinitionLocalizations) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPDefinitionLocalization cpDefinitionLocalization : cpDefinitionLocalizations) {
			entityCache.removeResult(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionLocalizationImpl.class,
				cpDefinitionLocalization.getPrimaryKey());

			clearUniqueFindersCache((CPDefinitionLocalizationModelImpl)cpDefinitionLocalization,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionLocalizationModelImpl cpDefinitionLocalizationModelImpl) {
		Object[] args = new Object[] {
				cpDefinitionLocalizationModelImpl.getCpDefinitionPK(),
				cpDefinitionLocalizationModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_CPD_L, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_CPD_L, args,
			cpDefinitionLocalizationModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CPDefinitionLocalizationModelImpl cpDefinitionLocalizationModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cpDefinitionLocalizationModelImpl.getCpDefinitionPK(),
					cpDefinitionLocalizationModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPD_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CPD_L, args);
		}

		if ((cpDefinitionLocalizationModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CPD_L.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpDefinitionLocalizationModelImpl.getOriginalCpDefinitionPK(),
					cpDefinitionLocalizationModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPD_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CPD_L, args);
		}
	}

	/**
	 * Creates a new cp definition localization with the primary key. Does not add the cp definition localization to the database.
	 *
	 * @param cpDefinitionLocalizationId the primary key for the new cp definition localization
	 * @return the new cp definition localization
	 */
	@Override
	public CPDefinitionLocalization create(long cpDefinitionLocalizationId) {
		CPDefinitionLocalization cpDefinitionLocalization = new CPDefinitionLocalizationImpl();

		cpDefinitionLocalization.setNew(true);
		cpDefinitionLocalization.setPrimaryKey(cpDefinitionLocalizationId);

		cpDefinitionLocalization.setCompanyId(companyProvider.getCompanyId());

		return cpDefinitionLocalization;
	}

	/**
	 * Removes the cp definition localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cpDefinitionLocalizationId the primary key of the cp definition localization
	 * @return the cp definition localization that was removed
	 * @throws NoSuchCPDefinitionLocalizationException if a cp definition localization with the primary key could not be found
	 */
	@Override
	public CPDefinitionLocalization remove(long cpDefinitionLocalizationId)
		throws NoSuchCPDefinitionLocalizationException {
		return remove((Serializable)cpDefinitionLocalizationId);
	}

	/**
	 * Removes the cp definition localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition localization
	 * @return the cp definition localization that was removed
	 * @throws NoSuchCPDefinitionLocalizationException if a cp definition localization with the primary key could not be found
	 */
	@Override
	public CPDefinitionLocalization remove(Serializable primaryKey)
		throws NoSuchCPDefinitionLocalizationException {
		Session session = null;

		try {
			session = openSession();

			CPDefinitionLocalization cpDefinitionLocalization = (CPDefinitionLocalization)session.get(CPDefinitionLocalizationImpl.class,
					primaryKey);

			if (cpDefinitionLocalization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpDefinitionLocalization);
		}
		catch (NoSuchCPDefinitionLocalizationException nsee) {
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
	protected CPDefinitionLocalization removeImpl(
		CPDefinitionLocalization cpDefinitionLocalization) {
		cpDefinitionLocalization = toUnwrappedModel(cpDefinitionLocalization);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionLocalization)) {
				cpDefinitionLocalization = (CPDefinitionLocalization)session.get(CPDefinitionLocalizationImpl.class,
						cpDefinitionLocalization.getPrimaryKeyObj());
			}

			if (cpDefinitionLocalization != null) {
				session.delete(cpDefinitionLocalization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionLocalization != null) {
			clearCache(cpDefinitionLocalization);
		}

		return cpDefinitionLocalization;
	}

	@Override
	public CPDefinitionLocalization updateImpl(
		CPDefinitionLocalization cpDefinitionLocalization) {
		cpDefinitionLocalization = toUnwrappedModel(cpDefinitionLocalization);

		boolean isNew = cpDefinitionLocalization.isNew();

		CPDefinitionLocalizationModelImpl cpDefinitionLocalizationModelImpl = (CPDefinitionLocalizationModelImpl)cpDefinitionLocalization;

		Session session = null;

		try {
			session = openSession();

			if (cpDefinitionLocalization.isNew()) {
				session.save(cpDefinitionLocalization);

				cpDefinitionLocalization.setNew(false);
			}
			else {
				cpDefinitionLocalization = (CPDefinitionLocalization)session.merge(cpDefinitionLocalization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPDefinitionLocalizationModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					cpDefinitionLocalizationModelImpl.getCpDefinitionPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONPK, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONPK,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpDefinitionLocalizationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONPK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionLocalizationModelImpl.getOriginalCpDefinitionPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONPK,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONPK,
					args);

				args = new Object[] {
						cpDefinitionLocalizationModelImpl.getCpDefinitionPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONPK,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONPK,
					args);
			}
		}

		entityCache.putResult(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLocalizationImpl.class,
			cpDefinitionLocalization.getPrimaryKey(), cpDefinitionLocalization,
			false);

		clearUniqueFindersCache(cpDefinitionLocalizationModelImpl, false);
		cacheUniqueFindersCache(cpDefinitionLocalizationModelImpl);

		cpDefinitionLocalization.resetOriginalValues();

		return cpDefinitionLocalization;
	}

	protected CPDefinitionLocalization toUnwrappedModel(
		CPDefinitionLocalization cpDefinitionLocalization) {
		if (cpDefinitionLocalization instanceof CPDefinitionLocalizationImpl) {
			return cpDefinitionLocalization;
		}

		CPDefinitionLocalizationImpl cpDefinitionLocalizationImpl = new CPDefinitionLocalizationImpl();

		cpDefinitionLocalizationImpl.setNew(cpDefinitionLocalization.isNew());
		cpDefinitionLocalizationImpl.setPrimaryKey(cpDefinitionLocalization.getPrimaryKey());

		cpDefinitionLocalizationImpl.setMvccVersion(cpDefinitionLocalization.getMvccVersion());
		cpDefinitionLocalizationImpl.setCpDefinitionLocalizationId(cpDefinitionLocalization.getCpDefinitionLocalizationId());
		cpDefinitionLocalizationImpl.setCompanyId(cpDefinitionLocalization.getCompanyId());
		cpDefinitionLocalizationImpl.setCpDefinitionPK(cpDefinitionLocalization.getCpDefinitionPK());
		cpDefinitionLocalizationImpl.setLanguageId(cpDefinitionLocalization.getLanguageId());
		cpDefinitionLocalizationImpl.setTitle(cpDefinitionLocalization.getTitle());
		cpDefinitionLocalizationImpl.setUrlTitle(cpDefinitionLocalization.getUrlTitle());
		cpDefinitionLocalizationImpl.setDescription(cpDefinitionLocalization.getDescription());

		return cpDefinitionLocalizationImpl;
	}

	/**
	 * Returns the cp definition localization with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition localization
	 * @return the cp definition localization
	 * @throws NoSuchCPDefinitionLocalizationException if a cp definition localization with the primary key could not be found
	 */
	@Override
	public CPDefinitionLocalization findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDefinitionLocalizationException {
		CPDefinitionLocalization cpDefinitionLocalization = fetchByPrimaryKey(primaryKey);

		if (cpDefinitionLocalization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpDefinitionLocalization;
	}

	/**
	 * Returns the cp definition localization with the primary key or throws a {@link NoSuchCPDefinitionLocalizationException} if it could not be found.
	 *
	 * @param cpDefinitionLocalizationId the primary key of the cp definition localization
	 * @return the cp definition localization
	 * @throws NoSuchCPDefinitionLocalizationException if a cp definition localization with the primary key could not be found
	 */
	@Override
	public CPDefinitionLocalization findByPrimaryKey(
		long cpDefinitionLocalizationId)
		throws NoSuchCPDefinitionLocalizationException {
		return findByPrimaryKey((Serializable)cpDefinitionLocalizationId);
	}

	/**
	 * Returns the cp definition localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition localization
	 * @return the cp definition localization, or <code>null</code> if a cp definition localization with the primary key could not be found
	 */
	@Override
	public CPDefinitionLocalization fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionLocalizationImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPDefinitionLocalization cpDefinitionLocalization = (CPDefinitionLocalization)serializable;

		if (cpDefinitionLocalization == null) {
			Session session = null;

			try {
				session = openSession();

				cpDefinitionLocalization = (CPDefinitionLocalization)session.get(CPDefinitionLocalizationImpl.class,
						primaryKey);

				if (cpDefinitionLocalization != null) {
					cacheResult(cpDefinitionLocalization);
				}
				else {
					entityCache.putResult(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionLocalizationImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionLocalizationImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpDefinitionLocalization;
	}

	/**
	 * Returns the cp definition localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cpDefinitionLocalizationId the primary key of the cp definition localization
	 * @return the cp definition localization, or <code>null</code> if a cp definition localization with the primary key could not be found
	 */
	@Override
	public CPDefinitionLocalization fetchByPrimaryKey(
		long cpDefinitionLocalizationId) {
		return fetchByPrimaryKey((Serializable)cpDefinitionLocalizationId);
	}

	@Override
	public Map<Serializable, CPDefinitionLocalization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPDefinitionLocalization> map = new HashMap<Serializable, CPDefinitionLocalization>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPDefinitionLocalization cpDefinitionLocalization = fetchByPrimaryKey(primaryKey);

			if (cpDefinitionLocalization != null) {
				map.put(primaryKey, cpDefinitionLocalization);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionLocalizationImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPDefinitionLocalization)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPDEFINITIONLOCALIZATION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CPDefinitionLocalization cpDefinitionLocalization : (List<CPDefinitionLocalization>)q.list()) {
				map.put(cpDefinitionLocalization.getPrimaryKeyObj(),
					cpDefinitionLocalization);

				cacheResult(cpDefinitionLocalization);

				uncachedPrimaryKeys.remove(cpDefinitionLocalization.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionLocalizationImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp definition localizations.
	 *
	 * @return the cp definition localizations
	 */
	@Override
	public List<CPDefinitionLocalization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition localizations
	 * @param end the upper bound of the range of cp definition localizations (not inclusive)
	 * @return the range of cp definition localizations
	 */
	@Override
	public List<CPDefinitionLocalization> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition localizations
	 * @param end the upper bound of the range of cp definition localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition localizations
	 */
	@Override
	public List<CPDefinitionLocalization> findAll(int start, int end,
		OrderByComparator<CPDefinitionLocalization> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition localizations
	 * @param end the upper bound of the range of cp definition localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp definition localizations
	 */
	@Override
	public List<CPDefinitionLocalization> findAll(int start, int end,
		OrderByComparator<CPDefinitionLocalization> orderByComparator,
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

		List<CPDefinitionLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPDEFINITIONLOCALIZATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITIONLOCALIZATION;

				if (pagination) {
					sql = sql.concat(CPDefinitionLocalizationModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPDefinitionLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionLocalization>)QueryUtil.list(q,
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
	 * Removes all the cp definition localizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionLocalization cpDefinitionLocalization : findAll()) {
			remove(cpDefinitionLocalization);
		}
	}

	/**
	 * Returns the number of cp definition localizations.
	 *
	 * @return the number of cp definition localizations
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPDEFINITIONLOCALIZATION);

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
		return CPDefinitionLocalizationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp definition localization persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPDefinitionLocalizationImpl.class.getName());
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
	private static final String _SQL_SELECT_CPDEFINITIONLOCALIZATION = "SELECT cpDefinitionLocalization FROM CPDefinitionLocalization cpDefinitionLocalization";
	private static final String _SQL_SELECT_CPDEFINITIONLOCALIZATION_WHERE_PKS_IN =
		"SELECT cpDefinitionLocalization FROM CPDefinitionLocalization cpDefinitionLocalization WHERE cpDefinitionLocalizationId IN (";
	private static final String _SQL_SELECT_CPDEFINITIONLOCALIZATION_WHERE = "SELECT cpDefinitionLocalization FROM CPDefinitionLocalization cpDefinitionLocalization WHERE ";
	private static final String _SQL_COUNT_CPDEFINITIONLOCALIZATION = "SELECT COUNT(cpDefinitionLocalization) FROM CPDefinitionLocalization cpDefinitionLocalization";
	private static final String _SQL_COUNT_CPDEFINITIONLOCALIZATION_WHERE = "SELECT COUNT(cpDefinitionLocalization) FROM CPDefinitionLocalization cpDefinitionLocalization WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpDefinitionLocalization.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPDefinitionLocalization exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPDefinitionLocalization exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPDefinitionLocalizationPersistenceImpl.class);
}