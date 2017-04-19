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

import com.liferay.commerce.product.exception.NoSuchProductDefinitionLocalizationException;
import com.liferay.commerce.product.model.CommerceProductDefinitionLocalization;
import com.liferay.commerce.product.model.impl.CommerceProductDefinitionLocalizationImpl;
import com.liferay.commerce.product.model.impl.CommerceProductDefinitionLocalizationModelImpl;
import com.liferay.commerce.product.service.persistence.CommerceProductDefinitionLocalizationPersistence;

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
 * The persistence implementation for the commerce product definition localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionLocalizationPersistence
 * @see com.liferay.commerce.product.service.persistence.CommerceProductDefinitionLocalizationUtil
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionLocalizationPersistenceImpl
	extends BasePersistenceImpl<CommerceProductDefinitionLocalization>
	implements CommerceProductDefinitionLocalizationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceProductDefinitionLocalizationUtil} to access the commerce product definition localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceProductDefinitionLocalizationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionLocalizationModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionLocalizationModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONPK =
		new FinderPath(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionLocalizationModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceProductDefinitionPK",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONPK =
		new FinderPath(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionLocalizationModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceProductDefinitionPK",
			new String[] { Long.class.getName() },
			CommerceProductDefinitionLocalizationModelImpl.COMMERCEPRODUCTDEFINITIONPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEPRODUCTDEFINITIONPK =
		new FinderPath(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceProductDefinitionPK",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product definition localizations where commerceProductDefinitionPK = &#63;.
	 *
	 * @param commerceProductDefinitionPK the commerce product definition pk
	 * @return the matching commerce product definition localizations
	 */
	@Override
	public List<CommerceProductDefinitionLocalization> findByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK) {
		return findByCommerceProductDefinitionPK(commerceProductDefinitionPK,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product definition localizations where commerceProductDefinitionPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceProductDefinitionPK the commerce product definition pk
	 * @param start the lower bound of the range of commerce product definition localizations
	 * @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	 * @return the range of matching commerce product definition localizations
	 */
	@Override
	public List<CommerceProductDefinitionLocalization> findByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK, int start, int end) {
		return findByCommerceProductDefinitionPK(commerceProductDefinitionPK,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product definition localizations where commerceProductDefinitionPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceProductDefinitionPK the commerce product definition pk
	 * @param start the lower bound of the range of commerce product definition localizations
	 * @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product definition localizations
	 */
	@Override
	public List<CommerceProductDefinitionLocalization> findByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK, int start, int end,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator) {
		return findByCommerceProductDefinitionPK(commerceProductDefinitionPK,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product definition localizations where commerceProductDefinitionPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceProductDefinitionPK the commerce product definition pk
	 * @param start the lower bound of the range of commerce product definition localizations
	 * @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product definition localizations
	 */
	@Override
	public List<CommerceProductDefinitionLocalization> findByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK, int start, int end,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONPK;
			finderArgs = new Object[] { commerceProductDefinitionPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONPK;
			finderArgs = new Object[] {
					commerceProductDefinitionPK,
					
					start, end, orderByComparator
				};
		}

		List<CommerceProductDefinitionLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefinitionLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductDefinitionLocalization commerceProductDefinitionLocalization : list) {
					if ((commerceProductDefinitionPK != commerceProductDefinitionLocalization.getCommerceProductDefinitionPK())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEPRODUCTDEFINITIONPK_COMMERCEPRODUCTDEFINITIONPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductDefinitionLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceProductDefinitionPK);

				if (!pagination) {
					list = (List<CommerceProductDefinitionLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefinitionLocalization>)QueryUtil.list(q,
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
	 * Returns the first commerce product definition localization in the ordered set where commerceProductDefinitionPK = &#63;.
	 *
	 * @param commerceProductDefinitionPK the commerce product definition pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition localization
	 * @throws NoSuchProductDefinitionLocalizationException if a matching commerce product definition localization could not be found
	 */
	@Override
	public CommerceProductDefinitionLocalization findByCommerceProductDefinitionPK_First(
		long commerceProductDefinitionPK,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator)
		throws NoSuchProductDefinitionLocalizationException {
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization =
			fetchByCommerceProductDefinitionPK_First(commerceProductDefinitionPK,
				orderByComparator);

		if (commerceProductDefinitionLocalization != null) {
			return commerceProductDefinitionLocalization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceProductDefinitionPK=");
		msg.append(commerceProductDefinitionPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionLocalizationException(msg.toString());
	}

	/**
	 * Returns the first commerce product definition localization in the ordered set where commerceProductDefinitionPK = &#63;.
	 *
	 * @param commerceProductDefinitionPK the commerce product definition pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition localization, or <code>null</code> if a matching commerce product definition localization could not be found
	 */
	@Override
	public CommerceProductDefinitionLocalization fetchByCommerceProductDefinitionPK_First(
		long commerceProductDefinitionPK,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator) {
		List<CommerceProductDefinitionLocalization> list = findByCommerceProductDefinitionPK(commerceProductDefinitionPK,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product definition localization in the ordered set where commerceProductDefinitionPK = &#63;.
	 *
	 * @param commerceProductDefinitionPK the commerce product definition pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition localization
	 * @throws NoSuchProductDefinitionLocalizationException if a matching commerce product definition localization could not be found
	 */
	@Override
	public CommerceProductDefinitionLocalization findByCommerceProductDefinitionPK_Last(
		long commerceProductDefinitionPK,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator)
		throws NoSuchProductDefinitionLocalizationException {
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization =
			fetchByCommerceProductDefinitionPK_Last(commerceProductDefinitionPK,
				orderByComparator);

		if (commerceProductDefinitionLocalization != null) {
			return commerceProductDefinitionLocalization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceProductDefinitionPK=");
		msg.append(commerceProductDefinitionPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionLocalizationException(msg.toString());
	}

	/**
	 * Returns the last commerce product definition localization in the ordered set where commerceProductDefinitionPK = &#63;.
	 *
	 * @param commerceProductDefinitionPK the commerce product definition pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition localization, or <code>null</code> if a matching commerce product definition localization could not be found
	 */
	@Override
	public CommerceProductDefinitionLocalization fetchByCommerceProductDefinitionPK_Last(
		long commerceProductDefinitionPK,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator) {
		int count = countByCommerceProductDefinitionPK(commerceProductDefinitionPK);

		if (count == 0) {
			return null;
		}

		List<CommerceProductDefinitionLocalization> list = findByCommerceProductDefinitionPK(commerceProductDefinitionPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product definition localizations before and after the current commerce product definition localization in the ordered set where commerceProductDefinitionPK = &#63;.
	 *
	 * @param commerceProductDefinitionLocalizationId the primary key of the current commerce product definition localization
	 * @param commerceProductDefinitionPK the commerce product definition pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product definition localization
	 * @throws NoSuchProductDefinitionLocalizationException if a commerce product definition localization with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionLocalization[] findByCommerceProductDefinitionPK_PrevAndNext(
		long commerceProductDefinitionLocalizationId,
		long commerceProductDefinitionPK,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator)
		throws NoSuchProductDefinitionLocalizationException {
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization =
			findByPrimaryKey(commerceProductDefinitionLocalizationId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductDefinitionLocalization[] array = new CommerceProductDefinitionLocalizationImpl[3];

			array[0] = getByCommerceProductDefinitionPK_PrevAndNext(session,
					commerceProductDefinitionLocalization,
					commerceProductDefinitionPK, orderByComparator, true);

			array[1] = commerceProductDefinitionLocalization;

			array[2] = getByCommerceProductDefinitionPK_PrevAndNext(session,
					commerceProductDefinitionLocalization,
					commerceProductDefinitionPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceProductDefinitionLocalization getByCommerceProductDefinitionPK_PrevAndNext(
		Session session,
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization,
		long commerceProductDefinitionPK,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONLOCALIZATION_WHERE);

		query.append(_FINDER_COLUMN_COMMERCEPRODUCTDEFINITIONPK_COMMERCEPRODUCTDEFINITIONPK_2);

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
			query.append(CommerceProductDefinitionLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceProductDefinitionPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductDefinitionLocalization);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductDefinitionLocalization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product definition localizations where commerceProductDefinitionPK = &#63; from the database.
	 *
	 * @param commerceProductDefinitionPK the commerce product definition pk
	 */
	@Override
	public void removeByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK) {
		for (CommerceProductDefinitionLocalization commerceProductDefinitionLocalization : findByCommerceProductDefinitionPK(
				commerceProductDefinitionPK, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(commerceProductDefinitionLocalization);
		}
	}

	/**
	 * Returns the number of commerce product definition localizations where commerceProductDefinitionPK = &#63;.
	 *
	 * @param commerceProductDefinitionPK the commerce product definition pk
	 * @return the number of matching commerce product definition localizations
	 */
	@Override
	public int countByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEPRODUCTDEFINITIONPK;

		Object[] finderArgs = new Object[] { commerceProductDefinitionPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTDEFINITIONLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEPRODUCTDEFINITIONPK_COMMERCEPRODUCTDEFINITIONPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceProductDefinitionPK);

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

	private static final String _FINDER_COLUMN_COMMERCEPRODUCTDEFINITIONPK_COMMERCEPRODUCTDEFINITIONPK_2 =
		"commerceProductDefinitionLocalization.commerceProductDefinitionPK = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_CPD_L = new FinderPath(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionLocalizationModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionLocalizationImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByCPD_L",
			new String[] { Long.class.getName(), String.class.getName() },
			CommerceProductDefinitionLocalizationModelImpl.COMMERCEPRODUCTDEFINITIONPK_COLUMN_BITMASK |
			CommerceProductDefinitionLocalizationModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPD_L = new FinderPath(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCPD_L",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the commerce product definition localization where commerceProductDefinitionPK = &#63; and languageId = &#63; or throws a {@link NoSuchProductDefinitionLocalizationException} if it could not be found.
	 *
	 * @param commerceProductDefinitionPK the commerce product definition pk
	 * @param languageId the language ID
	 * @return the matching commerce product definition localization
	 * @throws NoSuchProductDefinitionLocalizationException if a matching commerce product definition localization could not be found
	 */
	@Override
	public CommerceProductDefinitionLocalization findByCPD_L(
		long commerceProductDefinitionPK, String languageId)
		throws NoSuchProductDefinitionLocalizationException {
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization =
			fetchByCPD_L(commerceProductDefinitionPK, languageId);

		if (commerceProductDefinitionLocalization == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("commerceProductDefinitionPK=");
			msg.append(commerceProductDefinitionPK);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchProductDefinitionLocalizationException(msg.toString());
		}

		return commerceProductDefinitionLocalization;
	}

	/**
	 * Returns the commerce product definition localization where commerceProductDefinitionPK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceProductDefinitionPK the commerce product definition pk
	 * @param languageId the language ID
	 * @return the matching commerce product definition localization, or <code>null</code> if a matching commerce product definition localization could not be found
	 */
	@Override
	public CommerceProductDefinitionLocalization fetchByCPD_L(
		long commerceProductDefinitionPK, String languageId) {
		return fetchByCPD_L(commerceProductDefinitionPK, languageId, true);
	}

	/**
	 * Returns the commerce product definition localization where commerceProductDefinitionPK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceProductDefinitionPK the commerce product definition pk
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce product definition localization, or <code>null</code> if a matching commerce product definition localization could not be found
	 */
	@Override
	public CommerceProductDefinitionLocalization fetchByCPD_L(
		long commerceProductDefinitionPK, String languageId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				commerceProductDefinitionPK, languageId
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_CPD_L,
					finderArgs, this);
		}

		if (result instanceof CommerceProductDefinitionLocalization) {
			CommerceProductDefinitionLocalization commerceProductDefinitionLocalization =
				(CommerceProductDefinitionLocalization)result;

			if ((commerceProductDefinitionPK != commerceProductDefinitionLocalization.getCommerceProductDefinitionPK()) ||
					!Objects.equals(languageId,
						commerceProductDefinitionLocalization.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_CPD_L_COMMERCEPRODUCTDEFINITIONPK_2);

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

				qPos.add(commerceProductDefinitionPK);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				List<CommerceProductDefinitionLocalization> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_CPD_L,
						finderArgs, list);
				}
				else {
					CommerceProductDefinitionLocalization commerceProductDefinitionLocalization =
						list.get(0);

					result = commerceProductDefinitionLocalization;

					cacheResult(commerceProductDefinitionLocalization);

					if ((commerceProductDefinitionLocalization.getCommerceProductDefinitionPK() != commerceProductDefinitionPK) ||
							(commerceProductDefinitionLocalization.getLanguageId() == null) ||
							!commerceProductDefinitionLocalization.getLanguageId()
																	  .equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_CPD_L,
							finderArgs, commerceProductDefinitionLocalization);
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
			return (CommerceProductDefinitionLocalization)result;
		}
	}

	/**
	 * Removes the commerce product definition localization where commerceProductDefinitionPK = &#63; and languageId = &#63; from the database.
	 *
	 * @param commerceProductDefinitionPK the commerce product definition pk
	 * @param languageId the language ID
	 * @return the commerce product definition localization that was removed
	 */
	@Override
	public CommerceProductDefinitionLocalization removeByCPD_L(
		long commerceProductDefinitionPK, String languageId)
		throws NoSuchProductDefinitionLocalizationException {
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization =
			findByCPD_L(commerceProductDefinitionPK, languageId);

		return remove(commerceProductDefinitionLocalization);
	}

	/**
	 * Returns the number of commerce product definition localizations where commerceProductDefinitionPK = &#63; and languageId = &#63;.
	 *
	 * @param commerceProductDefinitionPK the commerce product definition pk
	 * @param languageId the language ID
	 * @return the number of matching commerce product definition localizations
	 */
	@Override
	public int countByCPD_L(long commerceProductDefinitionPK, String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPD_L;

		Object[] finderArgs = new Object[] {
				commerceProductDefinitionPK, languageId
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRODUCTDEFINITIONLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_CPD_L_COMMERCEPRODUCTDEFINITIONPK_2);

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

				qPos.add(commerceProductDefinitionPK);

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

	private static final String _FINDER_COLUMN_CPD_L_COMMERCEPRODUCTDEFINITIONPK_2 =
		"commerceProductDefinitionLocalization.commerceProductDefinitionPK = ? AND ";
	private static final String _FINDER_COLUMN_CPD_L_LANGUAGEID_1 = "commerceProductDefinitionLocalization.languageId IS NULL";
	private static final String _FINDER_COLUMN_CPD_L_LANGUAGEID_2 = "commerceProductDefinitionLocalization.languageId = ?";
	private static final String _FINDER_COLUMN_CPD_L_LANGUAGEID_3 = "(commerceProductDefinitionLocalization.languageId IS NULL OR commerceProductDefinitionLocalization.languageId = '')";

	public CommerceProductDefinitionLocalizationPersistenceImpl() {
		setModelClass(CommerceProductDefinitionLocalization.class);
	}

	/**
	 * Caches the commerce product definition localization in the entity cache if it is enabled.
	 *
	 * @param commerceProductDefinitionLocalization the commerce product definition localization
	 */
	@Override
	public void cacheResult(
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization) {
		entityCache.putResult(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionLocalizationImpl.class,
			commerceProductDefinitionLocalization.getPrimaryKey(),
			commerceProductDefinitionLocalization);

		finderCache.putResult(FINDER_PATH_FETCH_BY_CPD_L,
			new Object[] {
				commerceProductDefinitionLocalization.getCommerceProductDefinitionPK(),
				commerceProductDefinitionLocalization.getLanguageId()
			}, commerceProductDefinitionLocalization);

		commerceProductDefinitionLocalization.resetOriginalValues();
	}

	/**
	 * Caches the commerce product definition localizations in the entity cache if it is enabled.
	 *
	 * @param commerceProductDefinitionLocalizations the commerce product definition localizations
	 */
	@Override
	public void cacheResult(
		List<CommerceProductDefinitionLocalization> commerceProductDefinitionLocalizations) {
		for (CommerceProductDefinitionLocalization commerceProductDefinitionLocalization : commerceProductDefinitionLocalizations) {
			if (entityCache.getResult(
						CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductDefinitionLocalizationImpl.class,
						commerceProductDefinitionLocalization.getPrimaryKey()) == null) {
				cacheResult(commerceProductDefinitionLocalization);
			}
			else {
				commerceProductDefinitionLocalization.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce product definition localizations.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceProductDefinitionLocalizationImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce product definition localization.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization) {
		entityCache.removeResult(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionLocalizationImpl.class,
			commerceProductDefinitionLocalization.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceProductDefinitionLocalizationModelImpl)commerceProductDefinitionLocalization,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceProductDefinitionLocalization> commerceProductDefinitionLocalizations) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceProductDefinitionLocalization commerceProductDefinitionLocalization : commerceProductDefinitionLocalizations) {
			entityCache.removeResult(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductDefinitionLocalizationImpl.class,
				commerceProductDefinitionLocalization.getPrimaryKey());

			clearUniqueFindersCache((CommerceProductDefinitionLocalizationModelImpl)commerceProductDefinitionLocalization,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceProductDefinitionLocalizationModelImpl commerceProductDefinitionLocalizationModelImpl) {
		Object[] args = new Object[] {
				commerceProductDefinitionLocalizationModelImpl.getCommerceProductDefinitionPK(),
				commerceProductDefinitionLocalizationModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_CPD_L, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_CPD_L, args,
			commerceProductDefinitionLocalizationModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceProductDefinitionLocalizationModelImpl commerceProductDefinitionLocalizationModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceProductDefinitionLocalizationModelImpl.getCommerceProductDefinitionPK(),
					commerceProductDefinitionLocalizationModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPD_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CPD_L, args);
		}

		if ((commerceProductDefinitionLocalizationModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CPD_L.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceProductDefinitionLocalizationModelImpl.getOriginalCommerceProductDefinitionPK(),
					commerceProductDefinitionLocalizationModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPD_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CPD_L, args);
		}
	}

	/**
	 * Creates a new commerce product definition localization with the primary key. Does not add the commerce product definition localization to the database.
	 *
	 * @param commerceProductDefinitionLocalizationId the primary key for the new commerce product definition localization
	 * @return the new commerce product definition localization
	 */
	@Override
	public CommerceProductDefinitionLocalization create(
		long commerceProductDefinitionLocalizationId) {
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization =
			new CommerceProductDefinitionLocalizationImpl();

		commerceProductDefinitionLocalization.setNew(true);
		commerceProductDefinitionLocalization.setPrimaryKey(commerceProductDefinitionLocalizationId);

		commerceProductDefinitionLocalization.setCompanyId(companyProvider.getCompanyId());

		return commerceProductDefinitionLocalization;
	}

	/**
	 * Removes the commerce product definition localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceProductDefinitionLocalizationId the primary key of the commerce product definition localization
	 * @return the commerce product definition localization that was removed
	 * @throws NoSuchProductDefinitionLocalizationException if a commerce product definition localization with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionLocalization remove(
		long commerceProductDefinitionLocalizationId)
		throws NoSuchProductDefinitionLocalizationException {
		return remove((Serializable)commerceProductDefinitionLocalizationId);
	}

	/**
	 * Removes the commerce product definition localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce product definition localization
	 * @return the commerce product definition localization that was removed
	 * @throws NoSuchProductDefinitionLocalizationException if a commerce product definition localization with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionLocalization remove(Serializable primaryKey)
		throws NoSuchProductDefinitionLocalizationException {
		Session session = null;

		try {
			session = openSession();

			CommerceProductDefinitionLocalization commerceProductDefinitionLocalization =
				(CommerceProductDefinitionLocalization)session.get(CommerceProductDefinitionLocalizationImpl.class,
					primaryKey);

			if (commerceProductDefinitionLocalization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProductDefinitionLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceProductDefinitionLocalization);
		}
		catch (NoSuchProductDefinitionLocalizationException nsee) {
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
	protected CommerceProductDefinitionLocalization removeImpl(
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization) {
		commerceProductDefinitionLocalization = toUnwrappedModel(commerceProductDefinitionLocalization);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceProductDefinitionLocalization)) {
				commerceProductDefinitionLocalization = (CommerceProductDefinitionLocalization)session.get(CommerceProductDefinitionLocalizationImpl.class,
						commerceProductDefinitionLocalization.getPrimaryKeyObj());
			}

			if (commerceProductDefinitionLocalization != null) {
				session.delete(commerceProductDefinitionLocalization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceProductDefinitionLocalization != null) {
			clearCache(commerceProductDefinitionLocalization);
		}

		return commerceProductDefinitionLocalization;
	}

	@Override
	public CommerceProductDefinitionLocalization updateImpl(
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization) {
		commerceProductDefinitionLocalization = toUnwrappedModel(commerceProductDefinitionLocalization);

		boolean isNew = commerceProductDefinitionLocalization.isNew();

		CommerceProductDefinitionLocalizationModelImpl commerceProductDefinitionLocalizationModelImpl =
			(CommerceProductDefinitionLocalizationModelImpl)commerceProductDefinitionLocalization;

		Session session = null;

		try {
			session = openSession();

			if (commerceProductDefinitionLocalization.isNew()) {
				session.save(commerceProductDefinitionLocalization);

				commerceProductDefinitionLocalization.setNew(false);
			}
			else {
				commerceProductDefinitionLocalization = (CommerceProductDefinitionLocalization)session.merge(commerceProductDefinitionLocalization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceProductDefinitionLocalizationModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceProductDefinitionLocalizationModelImpl.getCommerceProductDefinitionPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRODUCTDEFINITIONPK,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONPK,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceProductDefinitionLocalizationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONPK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductDefinitionLocalizationModelImpl.getOriginalCommerceProductDefinitionPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRODUCTDEFINITIONPK,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONPK,
					args);

				args = new Object[] {
						commerceProductDefinitionLocalizationModelImpl.getCommerceProductDefinitionPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRODUCTDEFINITIONPK,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONPK,
					args);
			}
		}

		entityCache.putResult(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionLocalizationImpl.class,
			commerceProductDefinitionLocalization.getPrimaryKey(),
			commerceProductDefinitionLocalization, false);

		clearUniqueFindersCache(commerceProductDefinitionLocalizationModelImpl,
			false);
		cacheUniqueFindersCache(commerceProductDefinitionLocalizationModelImpl);

		commerceProductDefinitionLocalization.resetOriginalValues();

		return commerceProductDefinitionLocalization;
	}

	protected CommerceProductDefinitionLocalization toUnwrappedModel(
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization) {
		if (commerceProductDefinitionLocalization instanceof CommerceProductDefinitionLocalizationImpl) {
			return commerceProductDefinitionLocalization;
		}

		CommerceProductDefinitionLocalizationImpl commerceProductDefinitionLocalizationImpl =
			new CommerceProductDefinitionLocalizationImpl();

		commerceProductDefinitionLocalizationImpl.setNew(commerceProductDefinitionLocalization.isNew());
		commerceProductDefinitionLocalizationImpl.setPrimaryKey(commerceProductDefinitionLocalization.getPrimaryKey());

		commerceProductDefinitionLocalizationImpl.setMvccVersion(commerceProductDefinitionLocalization.getMvccVersion());
		commerceProductDefinitionLocalizationImpl.setCommerceProductDefinitionLocalizationId(commerceProductDefinitionLocalization.getCommerceProductDefinitionLocalizationId());
		commerceProductDefinitionLocalizationImpl.setCompanyId(commerceProductDefinitionLocalization.getCompanyId());
		commerceProductDefinitionLocalizationImpl.setCommerceProductDefinitionPK(commerceProductDefinitionLocalization.getCommerceProductDefinitionPK());
		commerceProductDefinitionLocalizationImpl.setLanguageId(commerceProductDefinitionLocalization.getLanguageId());
		commerceProductDefinitionLocalizationImpl.setTitle(commerceProductDefinitionLocalization.getTitle());
		commerceProductDefinitionLocalizationImpl.setUrlTitle(commerceProductDefinitionLocalization.getUrlTitle());
		commerceProductDefinitionLocalizationImpl.setDescription(commerceProductDefinitionLocalization.getDescription());

		return commerceProductDefinitionLocalizationImpl;
	}

	/**
	 * Returns the commerce product definition localization with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product definition localization
	 * @return the commerce product definition localization
	 * @throws NoSuchProductDefinitionLocalizationException if a commerce product definition localization with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionLocalization findByPrimaryKey(
		Serializable primaryKey)
		throws NoSuchProductDefinitionLocalizationException {
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization =
			fetchByPrimaryKey(primaryKey);

		if (commerceProductDefinitionLocalization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchProductDefinitionLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceProductDefinitionLocalization;
	}

	/**
	 * Returns the commerce product definition localization with the primary key or throws a {@link NoSuchProductDefinitionLocalizationException} if it could not be found.
	 *
	 * @param commerceProductDefinitionLocalizationId the primary key of the commerce product definition localization
	 * @return the commerce product definition localization
	 * @throws NoSuchProductDefinitionLocalizationException if a commerce product definition localization with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionLocalization findByPrimaryKey(
		long commerceProductDefinitionLocalizationId)
		throws NoSuchProductDefinitionLocalizationException {
		return findByPrimaryKey((Serializable)commerceProductDefinitionLocalizationId);
	}

	/**
	 * Returns the commerce product definition localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product definition localization
	 * @return the commerce product definition localization, or <code>null</code> if a commerce product definition localization with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionLocalization fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductDefinitionLocalizationImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization =
			(CommerceProductDefinitionLocalization)serializable;

		if (commerceProductDefinitionLocalization == null) {
			Session session = null;

			try {
				session = openSession();

				commerceProductDefinitionLocalization = (CommerceProductDefinitionLocalization)session.get(CommerceProductDefinitionLocalizationImpl.class,
						primaryKey);

				if (commerceProductDefinitionLocalization != null) {
					cacheResult(commerceProductDefinitionLocalization);
				}
				else {
					entityCache.putResult(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductDefinitionLocalizationImpl.class,
						primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefinitionLocalizationImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceProductDefinitionLocalization;
	}

	/**
	 * Returns the commerce product definition localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceProductDefinitionLocalizationId the primary key of the commerce product definition localization
	 * @return the commerce product definition localization, or <code>null</code> if a commerce product definition localization with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionLocalization fetchByPrimaryKey(
		long commerceProductDefinitionLocalizationId) {
		return fetchByPrimaryKey((Serializable)commerceProductDefinitionLocalizationId);
	}

	@Override
	public Map<Serializable, CommerceProductDefinitionLocalization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceProductDefinitionLocalization> map = new HashMap<Serializable, CommerceProductDefinitionLocalization>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceProductDefinitionLocalization commerceProductDefinitionLocalization =
				fetchByPrimaryKey(primaryKey);

			if (commerceProductDefinitionLocalization != null) {
				map.put(primaryKey, commerceProductDefinitionLocalization);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefinitionLocalizationImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommerceProductDefinitionLocalization)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONLOCALIZATION_WHERE_PKS_IN);

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

			for (CommerceProductDefinitionLocalization commerceProductDefinitionLocalization : (List<CommerceProductDefinitionLocalization>)q.list()) {
				map.put(commerceProductDefinitionLocalization.getPrimaryKeyObj(),
					commerceProductDefinitionLocalization);

				cacheResult(commerceProductDefinitionLocalization);

				uncachedPrimaryKeys.remove(commerceProductDefinitionLocalization.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceProductDefinitionLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefinitionLocalizationImpl.class,
					primaryKey, nullModel);
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
	 * Returns all the commerce product definition localizations.
	 *
	 * @return the commerce product definition localizations
	 */
	@Override
	public List<CommerceProductDefinitionLocalization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product definition localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product definition localizations
	 * @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	 * @return the range of commerce product definition localizations
	 */
	@Override
	public List<CommerceProductDefinitionLocalization> findAll(int start,
		int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product definition localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product definition localizations
	 * @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce product definition localizations
	 */
	@Override
	public List<CommerceProductDefinitionLocalization> findAll(int start,
		int end,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product definition localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product definition localizations
	 * @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce product definition localizations
	 */
	@Override
	public List<CommerceProductDefinitionLocalization> findAll(int start,
		int end,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator,
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

		List<CommerceProductDefinitionLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefinitionLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONLOCALIZATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRODUCTDEFINITIONLOCALIZATION;

				if (pagination) {
					sql = sql.concat(CommerceProductDefinitionLocalizationModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceProductDefinitionLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefinitionLocalization>)QueryUtil.list(q,
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
	 * Removes all the commerce product definition localizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceProductDefinitionLocalization commerceProductDefinitionLocalization : findAll()) {
			remove(commerceProductDefinitionLocalization);
		}
	}

	/**
	 * Returns the number of commerce product definition localizations.
	 *
	 * @return the number of commerce product definition localizations
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEPRODUCTDEFINITIONLOCALIZATION);

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
		return CommerceProductDefinitionLocalizationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce product definition localization persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceProductDefinitionLocalizationImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINITIONLOCALIZATION =
		"SELECT commerceProductDefinitionLocalization FROM CommerceProductDefinitionLocalization commerceProductDefinitionLocalization";
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINITIONLOCALIZATION_WHERE_PKS_IN =
		"SELECT commerceProductDefinitionLocalization FROM CommerceProductDefinitionLocalization commerceProductDefinitionLocalization WHERE commerceProductDefinitionLocalizationId IN (";
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINITIONLOCALIZATION_WHERE =
		"SELECT commerceProductDefinitionLocalization FROM CommerceProductDefinitionLocalization commerceProductDefinitionLocalization WHERE ";
	private static final String _SQL_COUNT_COMMERCEPRODUCTDEFINITIONLOCALIZATION =
		"SELECT COUNT(commerceProductDefinitionLocalization) FROM CommerceProductDefinitionLocalization commerceProductDefinitionLocalization";
	private static final String _SQL_COUNT_COMMERCEPRODUCTDEFINITIONLOCALIZATION_WHERE =
		"SELECT COUNT(commerceProductDefinitionLocalization) FROM CommerceProductDefinitionLocalization commerceProductDefinitionLocalization WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceProductDefinitionLocalization.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceProductDefinitionLocalization exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceProductDefinitionLocalization exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceProductDefinitionLocalizationPersistenceImpl.class);
}