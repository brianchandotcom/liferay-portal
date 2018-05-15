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

package com.liferay.commerce.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchAddressRestrictionException;
import com.liferay.commerce.model.CommerceAddressRestriction;
import com.liferay.commerce.model.impl.CommerceAddressRestrictionImpl;
import com.liferay.commerce.model.impl.CommerceAddressRestrictionModelImpl;
import com.liferay.commerce.service.persistence.CommerceAddressRestrictionPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce address restriction service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressRestrictionPersistence
 * @see com.liferay.commerce.service.persistence.CommerceAddressRestrictionUtil
 * @generated
 */
@ProviderType
public class CommerceAddressRestrictionPersistenceImpl
	extends BasePersistenceImpl<CommerceAddressRestriction>
	implements CommerceAddressRestrictionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceAddressRestrictionUtil} to access the commerce address restriction persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceAddressRestrictionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressRestrictionModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressRestrictionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressRestrictionModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressRestrictionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressRestrictionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCECOUNTRYID =
		new FinderPath(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressRestrictionModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressRestrictionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceCountryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID =
		new FinderPath(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressRestrictionModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressRestrictionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceCountryId", new String[] { Long.class.getName() },
			CommerceAddressRestrictionModelImpl.COMMERCECOUNTRYID_COLUMN_BITMASK |
			CommerceAddressRestrictionModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCECOUNTRYID = new FinderPath(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressRestrictionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceCountryId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce address restrictions where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @return the matching commerce address restrictions
	 */
	@Override
	public List<CommerceAddressRestriction> findByCommerceCountryId(
		long commerceCountryId) {
		return findByCommerceCountryId(commerceCountryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce address restrictions where commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce address restrictions
	 * @param end the upper bound of the range of commerce address restrictions (not inclusive)
	 * @return the range of matching commerce address restrictions
	 */
	@Override
	public List<CommerceAddressRestriction> findByCommerceCountryId(
		long commerceCountryId, int start, int end) {
		return findByCommerceCountryId(commerceCountryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce address restrictions where commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce address restrictions
	 * @param end the upper bound of the range of commerce address restrictions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce address restrictions
	 */
	@Override
	public List<CommerceAddressRestriction> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {
		return findByCommerceCountryId(commerceCountryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce address restrictions where commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce address restrictions
	 * @param end the upper bound of the range of commerce address restrictions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce address restrictions
	 */
	@Override
	public List<CommerceAddressRestriction> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID;
			finderArgs = new Object[] { commerceCountryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCECOUNTRYID;
			finderArgs = new Object[] {
					commerceCountryId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceAddressRestriction> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAddressRestriction>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAddressRestriction commerceAddressRestriction : list) {
					if ((commerceCountryId != commerceAddressRestriction.getCommerceCountryId())) {
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

			query.append(_SQL_SELECT_COMMERCEADDRESSRESTRICTION_WHERE);

			query.append(_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceAddressRestrictionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceCountryId);

				if (!pagination) {
					list = (List<CommerceAddressRestriction>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAddressRestriction>)QueryUtil.list(q,
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
	 * Returns the first commerce address restriction in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address restriction
	 * @throws NoSuchAddressRestrictionException if a matching commerce address restriction could not be found
	 */
	@Override
	public CommerceAddressRestriction findByCommerceCountryId_First(
		long commerceCountryId,
		OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws NoSuchAddressRestrictionException {
		CommerceAddressRestriction commerceAddressRestriction = fetchByCommerceCountryId_First(commerceCountryId,
				orderByComparator);

		if (commerceAddressRestriction != null) {
			return commerceAddressRestriction;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceCountryId=");
		msg.append(commerceCountryId);

		msg.append("}");

		throw new NoSuchAddressRestrictionException(msg.toString());
	}

	/**
	 * Returns the first commerce address restriction in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address restriction, or <code>null</code> if a matching commerce address restriction could not be found
	 */
	@Override
	public CommerceAddressRestriction fetchByCommerceCountryId_First(
		long commerceCountryId,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {
		List<CommerceAddressRestriction> list = findByCommerceCountryId(commerceCountryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce address restriction in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address restriction
	 * @throws NoSuchAddressRestrictionException if a matching commerce address restriction could not be found
	 */
	@Override
	public CommerceAddressRestriction findByCommerceCountryId_Last(
		long commerceCountryId,
		OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws NoSuchAddressRestrictionException {
		CommerceAddressRestriction commerceAddressRestriction = fetchByCommerceCountryId_Last(commerceCountryId,
				orderByComparator);

		if (commerceAddressRestriction != null) {
			return commerceAddressRestriction;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceCountryId=");
		msg.append(commerceCountryId);

		msg.append("}");

		throw new NoSuchAddressRestrictionException(msg.toString());
	}

	/**
	 * Returns the last commerce address restriction in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address restriction, or <code>null</code> if a matching commerce address restriction could not be found
	 */
	@Override
	public CommerceAddressRestriction fetchByCommerceCountryId_Last(
		long commerceCountryId,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {
		int count = countByCommerceCountryId(commerceCountryId);

		if (count == 0) {
			return null;
		}

		List<CommerceAddressRestriction> list = findByCommerceCountryId(commerceCountryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce address restrictions before and after the current commerce address restriction in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceAddressRestrictionId the primary key of the current commerce address restriction
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce address restriction
	 * @throws NoSuchAddressRestrictionException if a commerce address restriction with the primary key could not be found
	 */
	@Override
	public CommerceAddressRestriction[] findByCommerceCountryId_PrevAndNext(
		long commerceAddressRestrictionId, long commerceCountryId,
		OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws NoSuchAddressRestrictionException {
		CommerceAddressRestriction commerceAddressRestriction = findByPrimaryKey(commerceAddressRestrictionId);

		Session session = null;

		try {
			session = openSession();

			CommerceAddressRestriction[] array = new CommerceAddressRestrictionImpl[3];

			array[0] = getByCommerceCountryId_PrevAndNext(session,
					commerceAddressRestriction, commerceCountryId,
					orderByComparator, true);

			array[1] = commerceAddressRestriction;

			array[2] = getByCommerceCountryId_PrevAndNext(session,
					commerceAddressRestriction, commerceCountryId,
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

	protected CommerceAddressRestriction getByCommerceCountryId_PrevAndNext(
		Session session, CommerceAddressRestriction commerceAddressRestriction,
		long commerceCountryId,
		OrderByComparator<CommerceAddressRestriction> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEADDRESSRESTRICTION_WHERE);

		query.append(_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2);

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
			query.append(CommerceAddressRestrictionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceCountryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceAddressRestriction);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceAddressRestriction> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce address restrictions where commerceCountryId = &#63; from the database.
	 *
	 * @param commerceCountryId the commerce country ID
	 */
	@Override
	public void removeByCommerceCountryId(long commerceCountryId) {
		for (CommerceAddressRestriction commerceAddressRestriction : findByCommerceCountryId(
				commerceCountryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceAddressRestriction);
		}
	}

	/**
	 * Returns the number of commerce address restrictions where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @return the number of matching commerce address restrictions
	 */
	@Override
	public int countByCommerceCountryId(long commerceCountryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCECOUNTRYID;

		Object[] finderArgs = new Object[] { commerceCountryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEADDRESSRESTRICTION_WHERE);

			query.append(_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceCountryId);

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

	private static final String _FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2 =
		"commerceAddressRestriction.commerceCountryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressRestrictionModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressRestrictionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressRestrictionModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressRestrictionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			CommerceAddressRestrictionModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CommerceAddressRestrictionModelImpl.CLASSPK_COLUMN_BITMASK |
			CommerceAddressRestrictionModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressRestrictionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce address restrictions where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce address restrictions
	 */
	@Override
	public List<CommerceAddressRestriction> findByC_C(long classNameId,
		long classPK) {
		return findByC_C(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce address restrictions where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce address restrictions
	 * @param end the upper bound of the range of commerce address restrictions (not inclusive)
	 * @return the range of matching commerce address restrictions
	 */
	@Override
	public List<CommerceAddressRestriction> findByC_C(long classNameId,
		long classPK, int start, int end) {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce address restrictions where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce address restrictions
	 * @param end the upper bound of the range of commerce address restrictions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce address restrictions
	 */
	@Override
	public List<CommerceAddressRestriction> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {
		return findByC_C(classNameId, classPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce address restrictions where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce address restrictions
	 * @param end the upper bound of the range of commerce address restrictions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce address restrictions
	 */
	@Override
	public List<CommerceAddressRestriction> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<CommerceAddressRestriction> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAddressRestriction>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAddressRestriction commerceAddressRestriction : list) {
					if ((classNameId != commerceAddressRestriction.getClassNameId()) ||
							(classPK != commerceAddressRestriction.getClassPK())) {
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

			query.append(_SQL_SELECT_COMMERCEADDRESSRESTRICTION_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceAddressRestrictionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<CommerceAddressRestriction>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAddressRestriction>)QueryUtil.list(q,
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
	 * Returns the first commerce address restriction in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address restriction
	 * @throws NoSuchAddressRestrictionException if a matching commerce address restriction could not be found
	 */
	@Override
	public CommerceAddressRestriction findByC_C_First(long classNameId,
		long classPK,
		OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws NoSuchAddressRestrictionException {
		CommerceAddressRestriction commerceAddressRestriction = fetchByC_C_First(classNameId,
				classPK, orderByComparator);

		if (commerceAddressRestriction != null) {
			return commerceAddressRestriction;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchAddressRestrictionException(msg.toString());
	}

	/**
	 * Returns the first commerce address restriction in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address restriction, or <code>null</code> if a matching commerce address restriction could not be found
	 */
	@Override
	public CommerceAddressRestriction fetchByC_C_First(long classNameId,
		long classPK,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {
		List<CommerceAddressRestriction> list = findByC_C(classNameId, classPK,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce address restriction in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address restriction
	 * @throws NoSuchAddressRestrictionException if a matching commerce address restriction could not be found
	 */
	@Override
	public CommerceAddressRestriction findByC_C_Last(long classNameId,
		long classPK,
		OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws NoSuchAddressRestrictionException {
		CommerceAddressRestriction commerceAddressRestriction = fetchByC_C_Last(classNameId,
				classPK, orderByComparator);

		if (commerceAddressRestriction != null) {
			return commerceAddressRestriction;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchAddressRestrictionException(msg.toString());
	}

	/**
	 * Returns the last commerce address restriction in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address restriction, or <code>null</code> if a matching commerce address restriction could not be found
	 */
	@Override
	public CommerceAddressRestriction fetchByC_C_Last(long classNameId,
		long classPK,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {
		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<CommerceAddressRestriction> list = findByC_C(classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce address restrictions before and after the current commerce address restriction in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commerceAddressRestrictionId the primary key of the current commerce address restriction
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce address restriction
	 * @throws NoSuchAddressRestrictionException if a commerce address restriction with the primary key could not be found
	 */
	@Override
	public CommerceAddressRestriction[] findByC_C_PrevAndNext(
		long commerceAddressRestrictionId, long classNameId, long classPK,
		OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws NoSuchAddressRestrictionException {
		CommerceAddressRestriction commerceAddressRestriction = findByPrimaryKey(commerceAddressRestrictionId);

		Session session = null;

		try {
			session = openSession();

			CommerceAddressRestriction[] array = new CommerceAddressRestrictionImpl[3];

			array[0] = getByC_C_PrevAndNext(session,
					commerceAddressRestriction, classNameId, classPK,
					orderByComparator, true);

			array[1] = commerceAddressRestriction;

			array[2] = getByC_C_PrevAndNext(session,
					commerceAddressRestriction, classNameId, classPK,
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

	protected CommerceAddressRestriction getByC_C_PrevAndNext(Session session,
		CommerceAddressRestriction commerceAddressRestriction,
		long classNameId, long classPK,
		OrderByComparator<CommerceAddressRestriction> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEADDRESSRESTRICTION_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			query.append(CommerceAddressRestrictionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceAddressRestriction);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceAddressRestriction> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce address restrictions where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (CommerceAddressRestriction commerceAddressRestriction : findByC_C(
				classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceAddressRestriction);
		}
	}

	/**
	 * Returns the number of commerce address restrictions where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce address restrictions
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEADDRESSRESTRICTION_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "commerceAddressRestriction.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "commerceAddressRestriction.classPK = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C_C = new FinderPath(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressRestrictionModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressRestrictionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			CommerceAddressRestrictionModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CommerceAddressRestrictionModelImpl.CLASSPK_COLUMN_BITMASK |
			CommerceAddressRestrictionModelImpl.COMMERCECOUNTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_C = new FinderPath(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressRestrictionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the commerce address restriction where classNameId = &#63; and classPK = &#63; and commerceCountryId = &#63; or throws a {@link NoSuchAddressRestrictionException} if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceCountryId the commerce country ID
	 * @return the matching commerce address restriction
	 * @throws NoSuchAddressRestrictionException if a matching commerce address restriction could not be found
	 */
	@Override
	public CommerceAddressRestriction findByC_C_C(long classNameId,
		long classPK, long commerceCountryId)
		throws NoSuchAddressRestrictionException {
		CommerceAddressRestriction commerceAddressRestriction = fetchByC_C_C(classNameId,
				classPK, commerceCountryId);

		if (commerceAddressRestriction == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", commerceCountryId=");
			msg.append(commerceCountryId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchAddressRestrictionException(msg.toString());
		}

		return commerceAddressRestriction;
	}

	/**
	 * Returns the commerce address restriction where classNameId = &#63; and classPK = &#63; and commerceCountryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceCountryId the commerce country ID
	 * @return the matching commerce address restriction, or <code>null</code> if a matching commerce address restriction could not be found
	 */
	@Override
	public CommerceAddressRestriction fetchByC_C_C(long classNameId,
		long classPK, long commerceCountryId) {
		return fetchByC_C_C(classNameId, classPK, commerceCountryId, true);
	}

	/**
	 * Returns the commerce address restriction where classNameId = &#63; and classPK = &#63; and commerceCountryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceCountryId the commerce country ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce address restriction, or <code>null</code> if a matching commerce address restriction could not be found
	 */
	@Override
	public CommerceAddressRestriction fetchByC_C_C(long classNameId,
		long classPK, long commerceCountryId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				classNameId, classPK, commerceCountryId
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_C_C,
					finderArgs, this);
		}

		if (result instanceof CommerceAddressRestriction) {
			CommerceAddressRestriction commerceAddressRestriction = (CommerceAddressRestriction)result;

			if ((classNameId != commerceAddressRestriction.getClassNameId()) ||
					(classPK != commerceAddressRestriction.getClassPK()) ||
					(commerceCountryId != commerceAddressRestriction.getCommerceCountryId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_COMMERCEADDRESSRESTRICTION_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_C_COMMERCECOUNTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(commerceCountryId);

				List<CommerceAddressRestriction> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_C_C,
						finderArgs, list);
				}
				else {
					CommerceAddressRestriction commerceAddressRestriction = list.get(0);

					result = commerceAddressRestriction;

					cacheResult(commerceAddressRestriction);

					if ((commerceAddressRestriction.getClassNameId() != classNameId) ||
							(commerceAddressRestriction.getClassPK() != classPK) ||
							(commerceAddressRestriction.getCommerceCountryId() != commerceCountryId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_C_C,
							finderArgs, commerceAddressRestriction);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C_C, finderArgs);

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
			return (CommerceAddressRestriction)result;
		}
	}

	/**
	 * Removes the commerce address restriction where classNameId = &#63; and classPK = &#63; and commerceCountryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceCountryId the commerce country ID
	 * @return the commerce address restriction that was removed
	 */
	@Override
	public CommerceAddressRestriction removeByC_C_C(long classNameId,
		long classPK, long commerceCountryId)
		throws NoSuchAddressRestrictionException {
		CommerceAddressRestriction commerceAddressRestriction = findByC_C_C(classNameId,
				classPK, commerceCountryId);

		return remove(commerceAddressRestriction);
	}

	/**
	 * Returns the number of commerce address restrictions where classNameId = &#63; and classPK = &#63; and commerceCountryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceCountryId the commerce country ID
	 * @return the number of matching commerce address restrictions
	 */
	@Override
	public int countByC_C_C(long classNameId, long classPK,
		long commerceCountryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C_C;

		Object[] finderArgs = new Object[] {
				classNameId, classPK, commerceCountryId
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_COMMERCEADDRESSRESTRICTION_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_C_COMMERCECOUNTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(commerceCountryId);

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

	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 = "commerceAddressRestriction.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 = "commerceAddressRestriction.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_COMMERCECOUNTRYID_2 = "commerceAddressRestriction.commerceCountryId = ?";

	public CommerceAddressRestrictionPersistenceImpl() {
		setModelClass(CommerceAddressRestriction.class);
	}

	/**
	 * Caches the commerce address restriction in the entity cache if it is enabled.
	 *
	 * @param commerceAddressRestriction the commerce address restriction
	 */
	@Override
	public void cacheResult(
		CommerceAddressRestriction commerceAddressRestriction) {
		entityCache.putResult(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressRestrictionImpl.class,
			commerceAddressRestriction.getPrimaryKey(),
			commerceAddressRestriction);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C_C,
			new Object[] {
				commerceAddressRestriction.getClassNameId(),
				commerceAddressRestriction.getClassPK(),
				commerceAddressRestriction.getCommerceCountryId()
			}, commerceAddressRestriction);

		commerceAddressRestriction.resetOriginalValues();
	}

	/**
	 * Caches the commerce address restrictions in the entity cache if it is enabled.
	 *
	 * @param commerceAddressRestrictions the commerce address restrictions
	 */
	@Override
	public void cacheResult(
		List<CommerceAddressRestriction> commerceAddressRestrictions) {
		for (CommerceAddressRestriction commerceAddressRestriction : commerceAddressRestrictions) {
			if (entityCache.getResult(
						CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
						CommerceAddressRestrictionImpl.class,
						commerceAddressRestriction.getPrimaryKey()) == null) {
				cacheResult(commerceAddressRestriction);
			}
			else {
				commerceAddressRestriction.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce address restrictions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceAddressRestrictionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce address restriction.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceAddressRestriction commerceAddressRestriction) {
		entityCache.removeResult(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressRestrictionImpl.class,
			commerceAddressRestriction.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceAddressRestrictionModelImpl)commerceAddressRestriction,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceAddressRestriction> commerceAddressRestrictions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceAddressRestriction commerceAddressRestriction : commerceAddressRestrictions) {
			entityCache.removeResult(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
				CommerceAddressRestrictionImpl.class,
				commerceAddressRestriction.getPrimaryKey());

			clearUniqueFindersCache((CommerceAddressRestrictionModelImpl)commerceAddressRestriction,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceAddressRestrictionModelImpl commerceAddressRestrictionModelImpl) {
		Object[] args = new Object[] {
				commerceAddressRestrictionModelImpl.getClassNameId(),
				commerceAddressRestrictionModelImpl.getClassPK(),
				commerceAddressRestrictionModelImpl.getCommerceCountryId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_C_C, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C_C, args,
			commerceAddressRestrictionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceAddressRestrictionModelImpl commerceAddressRestrictionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceAddressRestrictionModelImpl.getClassNameId(),
					commerceAddressRestrictionModelImpl.getClassPK(),
					commerceAddressRestrictionModelImpl.getCommerceCountryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C_C, args);
		}

		if ((commerceAddressRestrictionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_C_C.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceAddressRestrictionModelImpl.getOriginalClassNameId(),
					commerceAddressRestrictionModelImpl.getOriginalClassPK(),
					commerceAddressRestrictionModelImpl.getOriginalCommerceCountryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C_C, args);
		}
	}

	/**
	 * Creates a new commerce address restriction with the primary key. Does not add the commerce address restriction to the database.
	 *
	 * @param commerceAddressRestrictionId the primary key for the new commerce address restriction
	 * @return the new commerce address restriction
	 */
	@Override
	public CommerceAddressRestriction create(long commerceAddressRestrictionId) {
		CommerceAddressRestriction commerceAddressRestriction = new CommerceAddressRestrictionImpl();

		commerceAddressRestriction.setNew(true);
		commerceAddressRestriction.setPrimaryKey(commerceAddressRestrictionId);

		commerceAddressRestriction.setCompanyId(companyProvider.getCompanyId());

		return commerceAddressRestriction;
	}

	/**
	 * Removes the commerce address restriction with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAddressRestrictionId the primary key of the commerce address restriction
	 * @return the commerce address restriction that was removed
	 * @throws NoSuchAddressRestrictionException if a commerce address restriction with the primary key could not be found
	 */
	@Override
	public CommerceAddressRestriction remove(long commerceAddressRestrictionId)
		throws NoSuchAddressRestrictionException {
		return remove((Serializable)commerceAddressRestrictionId);
	}

	/**
	 * Removes the commerce address restriction with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce address restriction
	 * @return the commerce address restriction that was removed
	 * @throws NoSuchAddressRestrictionException if a commerce address restriction with the primary key could not be found
	 */
	@Override
	public CommerceAddressRestriction remove(Serializable primaryKey)
		throws NoSuchAddressRestrictionException {
		Session session = null;

		try {
			session = openSession();

			CommerceAddressRestriction commerceAddressRestriction = (CommerceAddressRestriction)session.get(CommerceAddressRestrictionImpl.class,
					primaryKey);

			if (commerceAddressRestriction == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAddressRestrictionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceAddressRestriction);
		}
		catch (NoSuchAddressRestrictionException nsee) {
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
	protected CommerceAddressRestriction removeImpl(
		CommerceAddressRestriction commerceAddressRestriction) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceAddressRestriction)) {
				commerceAddressRestriction = (CommerceAddressRestriction)session.get(CommerceAddressRestrictionImpl.class,
						commerceAddressRestriction.getPrimaryKeyObj());
			}

			if (commerceAddressRestriction != null) {
				session.delete(commerceAddressRestriction);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceAddressRestriction != null) {
			clearCache(commerceAddressRestriction);
		}

		return commerceAddressRestriction;
	}

	@Override
	public CommerceAddressRestriction updateImpl(
		CommerceAddressRestriction commerceAddressRestriction) {
		boolean isNew = commerceAddressRestriction.isNew();

		if (!(commerceAddressRestriction instanceof CommerceAddressRestrictionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceAddressRestriction.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceAddressRestriction);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceAddressRestriction proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceAddressRestriction implementation " +
				commerceAddressRestriction.getClass());
		}

		CommerceAddressRestrictionModelImpl commerceAddressRestrictionModelImpl = (CommerceAddressRestrictionModelImpl)commerceAddressRestriction;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceAddressRestriction.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceAddressRestriction.setCreateDate(now);
			}
			else {
				commerceAddressRestriction.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceAddressRestrictionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceAddressRestriction.setModifiedDate(now);
			}
			else {
				commerceAddressRestriction.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceAddressRestriction.isNew()) {
				session.save(commerceAddressRestriction);

				commerceAddressRestriction.setNew(false);
			}
			else {
				commerceAddressRestriction = (CommerceAddressRestriction)session.merge(commerceAddressRestriction);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceAddressRestrictionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceAddressRestrictionModelImpl.getCommerceCountryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCECOUNTRYID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID,
				args);

			args = new Object[] {
					commerceAddressRestrictionModelImpl.getClassNameId(),
					commerceAddressRestrictionModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceAddressRestrictionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceAddressRestrictionModelImpl.getOriginalCommerceCountryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCECOUNTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID,
					args);

				args = new Object[] {
						commerceAddressRestrictionModelImpl.getCommerceCountryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCECOUNTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID,
					args);
			}

			if ((commerceAddressRestrictionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceAddressRestrictionModelImpl.getOriginalClassNameId(),
						commerceAddressRestrictionModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						commerceAddressRestrictionModelImpl.getClassNameId(),
						commerceAddressRestrictionModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}
		}

		entityCache.putResult(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressRestrictionImpl.class,
			commerceAddressRestriction.getPrimaryKey(),
			commerceAddressRestriction, false);

		clearUniqueFindersCache(commerceAddressRestrictionModelImpl, false);
		cacheUniqueFindersCache(commerceAddressRestrictionModelImpl);

		commerceAddressRestriction.resetOriginalValues();

		return commerceAddressRestriction;
	}

	/**
	 * Returns the commerce address restriction with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce address restriction
	 * @return the commerce address restriction
	 * @throws NoSuchAddressRestrictionException if a commerce address restriction with the primary key could not be found
	 */
	@Override
	public CommerceAddressRestriction findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAddressRestrictionException {
		CommerceAddressRestriction commerceAddressRestriction = fetchByPrimaryKey(primaryKey);

		if (commerceAddressRestriction == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAddressRestrictionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceAddressRestriction;
	}

	/**
	 * Returns the commerce address restriction with the primary key or throws a {@link NoSuchAddressRestrictionException} if it could not be found.
	 *
	 * @param commerceAddressRestrictionId the primary key of the commerce address restriction
	 * @return the commerce address restriction
	 * @throws NoSuchAddressRestrictionException if a commerce address restriction with the primary key could not be found
	 */
	@Override
	public CommerceAddressRestriction findByPrimaryKey(
		long commerceAddressRestrictionId)
		throws NoSuchAddressRestrictionException {
		return findByPrimaryKey((Serializable)commerceAddressRestrictionId);
	}

	/**
	 * Returns the commerce address restriction with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce address restriction
	 * @return the commerce address restriction, or <code>null</code> if a commerce address restriction with the primary key could not be found
	 */
	@Override
	public CommerceAddressRestriction fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
				CommerceAddressRestrictionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceAddressRestriction commerceAddressRestriction = (CommerceAddressRestriction)serializable;

		if (commerceAddressRestriction == null) {
			Session session = null;

			try {
				session = openSession();

				commerceAddressRestriction = (CommerceAddressRestriction)session.get(CommerceAddressRestrictionImpl.class,
						primaryKey);

				if (commerceAddressRestriction != null) {
					cacheResult(commerceAddressRestriction);
				}
				else {
					entityCache.putResult(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
						CommerceAddressRestrictionImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceAddressRestrictionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceAddressRestriction;
	}

	/**
	 * Returns the commerce address restriction with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAddressRestrictionId the primary key of the commerce address restriction
	 * @return the commerce address restriction, or <code>null</code> if a commerce address restriction with the primary key could not be found
	 */
	@Override
	public CommerceAddressRestriction fetchByPrimaryKey(
		long commerceAddressRestrictionId) {
		return fetchByPrimaryKey((Serializable)commerceAddressRestrictionId);
	}

	@Override
	public Map<Serializable, CommerceAddressRestriction> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceAddressRestriction> map = new HashMap<Serializable, CommerceAddressRestriction>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceAddressRestriction commerceAddressRestriction = fetchByPrimaryKey(primaryKey);

			if (commerceAddressRestriction != null) {
				map.put(primaryKey, commerceAddressRestriction);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceAddressRestrictionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceAddressRestriction)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEADDRESSRESTRICTION_WHERE_PKS_IN);

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

			for (CommerceAddressRestriction commerceAddressRestriction : (List<CommerceAddressRestriction>)q.list()) {
				map.put(commerceAddressRestriction.getPrimaryKeyObj(),
					commerceAddressRestriction);

				cacheResult(commerceAddressRestriction);

				uncachedPrimaryKeys.remove(commerceAddressRestriction.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceAddressRestrictionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceAddressRestrictionImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce address restrictions.
	 *
	 * @return the commerce address restrictions
	 */
	@Override
	public List<CommerceAddressRestriction> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce address restrictions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce address restrictions
	 * @param end the upper bound of the range of commerce address restrictions (not inclusive)
	 * @return the range of commerce address restrictions
	 */
	@Override
	public List<CommerceAddressRestriction> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce address restrictions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce address restrictions
	 * @param end the upper bound of the range of commerce address restrictions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce address restrictions
	 */
	@Override
	public List<CommerceAddressRestriction> findAll(int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce address restrictions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce address restrictions
	 * @param end the upper bound of the range of commerce address restrictions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce address restrictions
	 */
	@Override
	public List<CommerceAddressRestriction> findAll(int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator,
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

		List<CommerceAddressRestriction> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAddressRestriction>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEADDRESSRESTRICTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEADDRESSRESTRICTION;

				if (pagination) {
					sql = sql.concat(CommerceAddressRestrictionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceAddressRestriction>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAddressRestriction>)QueryUtil.list(q,
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
	 * Removes all the commerce address restrictions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceAddressRestriction commerceAddressRestriction : findAll()) {
			remove(commerceAddressRestriction);
		}
	}

	/**
	 * Returns the number of commerce address restrictions.
	 *
	 * @return the number of commerce address restrictions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEADDRESSRESTRICTION);

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
		return CommerceAddressRestrictionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce address restriction persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceAddressRestrictionImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEADDRESSRESTRICTION = "SELECT commerceAddressRestriction FROM CommerceAddressRestriction commerceAddressRestriction";
	private static final String _SQL_SELECT_COMMERCEADDRESSRESTRICTION_WHERE_PKS_IN =
		"SELECT commerceAddressRestriction FROM CommerceAddressRestriction commerceAddressRestriction WHERE commerceAddressRestrictionId IN (";
	private static final String _SQL_SELECT_COMMERCEADDRESSRESTRICTION_WHERE = "SELECT commerceAddressRestriction FROM CommerceAddressRestriction commerceAddressRestriction WHERE ";
	private static final String _SQL_COUNT_COMMERCEADDRESSRESTRICTION = "SELECT COUNT(commerceAddressRestriction) FROM CommerceAddressRestriction commerceAddressRestriction";
	private static final String _SQL_COUNT_COMMERCEADDRESSRESTRICTION_WHERE = "SELECT COUNT(commerceAddressRestriction) FROM CommerceAddressRestriction commerceAddressRestriction WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceAddressRestriction.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceAddressRestriction exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceAddressRestriction exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceAddressRestrictionPersistenceImpl.class);
}