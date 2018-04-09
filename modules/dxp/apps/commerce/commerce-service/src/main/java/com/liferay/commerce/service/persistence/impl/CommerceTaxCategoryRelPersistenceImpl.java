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

import com.liferay.commerce.exception.NoSuchTaxCategoryRelException;
import com.liferay.commerce.model.CommerceTaxCategoryRel;
import com.liferay.commerce.model.impl.CommerceTaxCategoryRelImpl;
import com.liferay.commerce.model.impl.CommerceTaxCategoryRelModelImpl;
import com.liferay.commerce.service.persistence.CommerceTaxCategoryRelPersistence;

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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce tax category rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryRelPersistence
 * @see com.liferay.commerce.service.persistence.CommerceTaxCategoryRelUtil
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryRelPersistenceImpl extends BasePersistenceImpl<CommerceTaxCategoryRel>
	implements CommerceTaxCategoryRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceTaxCategoryRelUtil} to access the commerce tax category rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceTaxCategoryRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxCategoryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxCategoryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCETAXCATEGORYID =
		new FinderPath(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxCategoryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceTaxCategoryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXCATEGORYID =
		new FinderPath(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxCategoryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceTaxCategoryId",
			new String[] { Long.class.getName() },
			CommerceTaxCategoryRelModelImpl.COMMERCETAXCATEGORYID_COLUMN_BITMASK |
			CommerceTaxCategoryRelModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCETAXCATEGORYID = new FinderPath(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceTaxCategoryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce tax category rels where commerceTaxCategoryId = &#63;.
	 *
	 * @param commerceTaxCategoryId the commerce tax category ID
	 * @return the matching commerce tax category rels
	 */
	@Override
	public List<CommerceTaxCategoryRel> findByCommerceTaxCategoryId(
		long commerceTaxCategoryId) {
		return findByCommerceTaxCategoryId(commerceTaxCategoryId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax category rels where commerceTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceTaxCategoryId the commerce tax category ID
	 * @param start the lower bound of the range of commerce tax category rels
	 * @param end the upper bound of the range of commerce tax category rels (not inclusive)
	 * @return the range of matching commerce tax category rels
	 */
	@Override
	public List<CommerceTaxCategoryRel> findByCommerceTaxCategoryId(
		long commerceTaxCategoryId, int start, int end) {
		return findByCommerceTaxCategoryId(commerceTaxCategoryId, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the commerce tax category rels where commerceTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceTaxCategoryId the commerce tax category ID
	 * @param start the lower bound of the range of commerce tax category rels
	 * @param end the upper bound of the range of commerce tax category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax category rels
	 */
	@Override
	public List<CommerceTaxCategoryRel> findByCommerceTaxCategoryId(
		long commerceTaxCategoryId, int start, int end,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator) {
		return findByCommerceTaxCategoryId(commerceTaxCategoryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax category rels where commerceTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceTaxCategoryId the commerce tax category ID
	 * @param start the lower bound of the range of commerce tax category rels
	 * @param end the upper bound of the range of commerce tax category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tax category rels
	 */
	@Override
	public List<CommerceTaxCategoryRel> findByCommerceTaxCategoryId(
		long commerceTaxCategoryId, int start, int end,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXCATEGORYID;
			finderArgs = new Object[] { commerceTaxCategoryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCETAXCATEGORYID;
			finderArgs = new Object[] {
					commerceTaxCategoryId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceTaxCategoryRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTaxCategoryRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTaxCategoryRel commerceTaxCategoryRel : list) {
					if ((commerceTaxCategoryId != commerceTaxCategoryRel.getCommerceTaxCategoryId())) {
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

			query.append(_SQL_SELECT_COMMERCETAXCATEGORYREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCETAXCATEGORYID_COMMERCETAXCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceTaxCategoryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceTaxCategoryId);

				if (!pagination) {
					list = (List<CommerceTaxCategoryRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTaxCategoryRel>)QueryUtil.list(q,
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
	 * Returns the first commerce tax category rel in the ordered set where commerceTaxCategoryId = &#63;.
	 *
	 * @param commerceTaxCategoryId the commerce tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category rel
	 * @throws NoSuchTaxCategoryRelException if a matching commerce tax category rel could not be found
	 */
	@Override
	public CommerceTaxCategoryRel findByCommerceTaxCategoryId_First(
		long commerceTaxCategoryId,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator)
		throws NoSuchTaxCategoryRelException {
		CommerceTaxCategoryRel commerceTaxCategoryRel = fetchByCommerceTaxCategoryId_First(commerceTaxCategoryId,
				orderByComparator);

		if (commerceTaxCategoryRel != null) {
			return commerceTaxCategoryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceTaxCategoryId=");
		msg.append(commerceTaxCategoryId);

		msg.append("}");

		throw new NoSuchTaxCategoryRelException(msg.toString());
	}

	/**
	 * Returns the first commerce tax category rel in the ordered set where commerceTaxCategoryId = &#63;.
	 *
	 * @param commerceTaxCategoryId the commerce tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category rel, or <code>null</code> if a matching commerce tax category rel could not be found
	 */
	@Override
	public CommerceTaxCategoryRel fetchByCommerceTaxCategoryId_First(
		long commerceTaxCategoryId,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator) {
		List<CommerceTaxCategoryRel> list = findByCommerceTaxCategoryId(commerceTaxCategoryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tax category rel in the ordered set where commerceTaxCategoryId = &#63;.
	 *
	 * @param commerceTaxCategoryId the commerce tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category rel
	 * @throws NoSuchTaxCategoryRelException if a matching commerce tax category rel could not be found
	 */
	@Override
	public CommerceTaxCategoryRel findByCommerceTaxCategoryId_Last(
		long commerceTaxCategoryId,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator)
		throws NoSuchTaxCategoryRelException {
		CommerceTaxCategoryRel commerceTaxCategoryRel = fetchByCommerceTaxCategoryId_Last(commerceTaxCategoryId,
				orderByComparator);

		if (commerceTaxCategoryRel != null) {
			return commerceTaxCategoryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceTaxCategoryId=");
		msg.append(commerceTaxCategoryId);

		msg.append("}");

		throw new NoSuchTaxCategoryRelException(msg.toString());
	}

	/**
	 * Returns the last commerce tax category rel in the ordered set where commerceTaxCategoryId = &#63;.
	 *
	 * @param commerceTaxCategoryId the commerce tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category rel, or <code>null</code> if a matching commerce tax category rel could not be found
	 */
	@Override
	public CommerceTaxCategoryRel fetchByCommerceTaxCategoryId_Last(
		long commerceTaxCategoryId,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator) {
		int count = countByCommerceTaxCategoryId(commerceTaxCategoryId);

		if (count == 0) {
			return null;
		}

		List<CommerceTaxCategoryRel> list = findByCommerceTaxCategoryId(commerceTaxCategoryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tax category rels before and after the current commerce tax category rel in the ordered set where commerceTaxCategoryId = &#63;.
	 *
	 * @param commerceTaxCategoryRelId the primary key of the current commerce tax category rel
	 * @param commerceTaxCategoryId the commerce tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tax category rel
	 * @throws NoSuchTaxCategoryRelException if a commerce tax category rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategoryRel[] findByCommerceTaxCategoryId_PrevAndNext(
		long commerceTaxCategoryRelId, long commerceTaxCategoryId,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator)
		throws NoSuchTaxCategoryRelException {
		CommerceTaxCategoryRel commerceTaxCategoryRel = findByPrimaryKey(commerceTaxCategoryRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceTaxCategoryRel[] array = new CommerceTaxCategoryRelImpl[3];

			array[0] = getByCommerceTaxCategoryId_PrevAndNext(session,
					commerceTaxCategoryRel, commerceTaxCategoryId,
					orderByComparator, true);

			array[1] = commerceTaxCategoryRel;

			array[2] = getByCommerceTaxCategoryId_PrevAndNext(session,
					commerceTaxCategoryRel, commerceTaxCategoryId,
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

	protected CommerceTaxCategoryRel getByCommerceTaxCategoryId_PrevAndNext(
		Session session, CommerceTaxCategoryRel commerceTaxCategoryRel,
		long commerceTaxCategoryId,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCETAXCATEGORYREL_WHERE);

		query.append(_FINDER_COLUMN_COMMERCETAXCATEGORYID_COMMERCETAXCATEGORYID_2);

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
			query.append(CommerceTaxCategoryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceTaxCategoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTaxCategoryRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTaxCategoryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tax category rels where commerceTaxCategoryId = &#63; from the database.
	 *
	 * @param commerceTaxCategoryId the commerce tax category ID
	 */
	@Override
	public void removeByCommerceTaxCategoryId(long commerceTaxCategoryId) {
		for (CommerceTaxCategoryRel commerceTaxCategoryRel : findByCommerceTaxCategoryId(
				commerceTaxCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(commerceTaxCategoryRel);
		}
	}

	/**
	 * Returns the number of commerce tax category rels where commerceTaxCategoryId = &#63;.
	 *
	 * @param commerceTaxCategoryId the commerce tax category ID
	 * @return the number of matching commerce tax category rels
	 */
	@Override
	public int countByCommerceTaxCategoryId(long commerceTaxCategoryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCETAXCATEGORYID;

		Object[] finderArgs = new Object[] { commerceTaxCategoryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCETAXCATEGORYREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCETAXCATEGORYID_COMMERCETAXCATEGORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceTaxCategoryId);

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

	private static final String _FINDER_COLUMN_COMMERCETAXCATEGORYID_COMMERCETAXCATEGORYID_2 =
		"commerceTaxCategoryRel.commerceTaxCategoryId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxCategoryRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			CommerceTaxCategoryRelModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CommerceTaxCategoryRelModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce tax category rel where classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchTaxCategoryRelException} if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce tax category rel
	 * @throws NoSuchTaxCategoryRelException if a matching commerce tax category rel could not be found
	 */
	@Override
	public CommerceTaxCategoryRel findByC_C(long classNameId, long classPK)
		throws NoSuchTaxCategoryRelException {
		CommerceTaxCategoryRel commerceTaxCategoryRel = fetchByC_C(classNameId,
				classPK);

		if (commerceTaxCategoryRel == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchTaxCategoryRelException(msg.toString());
		}

		return commerceTaxCategoryRel;
	}

	/**
	 * Returns the commerce tax category rel where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce tax category rel, or <code>null</code> if a matching commerce tax category rel could not be found
	 */
	@Override
	public CommerceTaxCategoryRel fetchByC_C(long classNameId, long classPK) {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the commerce tax category rel where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce tax category rel, or <code>null</code> if a matching commerce tax category rel could not be found
	 */
	@Override
	public CommerceTaxCategoryRel fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result instanceof CommerceTaxCategoryRel) {
			CommerceTaxCategoryRel commerceTaxCategoryRel = (CommerceTaxCategoryRel)result;

			if ((classNameId != commerceTaxCategoryRel.getClassNameId()) ||
					(classPK != commerceTaxCategoryRel.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCETAXCATEGORYREL_WHERE);

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

				List<CommerceTaxCategoryRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, finderArgs,
						list);
				}
				else {
					CommerceTaxCategoryRel commerceTaxCategoryRel = list.get(0);

					result = commerceTaxCategoryRel;

					cacheResult(commerceTaxCategoryRel);

					if ((commerceTaxCategoryRel.getClassNameId() != classNameId) ||
							(commerceTaxCategoryRel.getClassPK() != classPK)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, commerceTaxCategoryRel);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, finderArgs);

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
			return (CommerceTaxCategoryRel)result;
		}
	}

	/**
	 * Removes the commerce tax category rel where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the commerce tax category rel that was removed
	 */
	@Override
	public CommerceTaxCategoryRel removeByC_C(long classNameId, long classPK)
		throws NoSuchTaxCategoryRelException {
		CommerceTaxCategoryRel commerceTaxCategoryRel = findByC_C(classNameId,
				classPK);

		return remove(commerceTaxCategoryRel);
	}

	/**
	 * Returns the number of commerce tax category rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce tax category rels
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCETAXCATEGORYREL_WHERE);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "commerceTaxCategoryRel.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "commerceTaxCategoryRel.classPK = ?";

	public CommerceTaxCategoryRelPersistenceImpl() {
		setModelClass(CommerceTaxCategoryRel.class);
	}

	/**
	 * Caches the commerce tax category rel in the entity cache if it is enabled.
	 *
	 * @param commerceTaxCategoryRel the commerce tax category rel
	 */
	@Override
	public void cacheResult(CommerceTaxCategoryRel commerceTaxCategoryRel) {
		entityCache.putResult(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryRelImpl.class,
			commerceTaxCategoryRel.getPrimaryKey(), commerceTaxCategoryRel);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				commerceTaxCategoryRel.getClassNameId(),
				commerceTaxCategoryRel.getClassPK()
			}, commerceTaxCategoryRel);

		commerceTaxCategoryRel.resetOriginalValues();
	}

	/**
	 * Caches the commerce tax category rels in the entity cache if it is enabled.
	 *
	 * @param commerceTaxCategoryRels the commerce tax category rels
	 */
	@Override
	public void cacheResult(
		List<CommerceTaxCategoryRel> commerceTaxCategoryRels) {
		for (CommerceTaxCategoryRel commerceTaxCategoryRel : commerceTaxCategoryRels) {
			if (entityCache.getResult(
						CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceTaxCategoryRelImpl.class,
						commerceTaxCategoryRel.getPrimaryKey()) == null) {
				cacheResult(commerceTaxCategoryRel);
			}
			else {
				commerceTaxCategoryRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce tax category rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceTaxCategoryRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce tax category rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceTaxCategoryRel commerceTaxCategoryRel) {
		entityCache.removeResult(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryRelImpl.class,
			commerceTaxCategoryRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceTaxCategoryRelModelImpl)commerceTaxCategoryRel,
			true);
	}

	@Override
	public void clearCache(List<CommerceTaxCategoryRel> commerceTaxCategoryRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceTaxCategoryRel commerceTaxCategoryRel : commerceTaxCategoryRels) {
			entityCache.removeResult(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceTaxCategoryRelImpl.class,
				commerceTaxCategoryRel.getPrimaryKey());

			clearUniqueFindersCache((CommerceTaxCategoryRelModelImpl)commerceTaxCategoryRel,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceTaxCategoryRelModelImpl commerceTaxCategoryRelModelImpl) {
		Object[] args = new Object[] {
				commerceTaxCategoryRelModelImpl.getClassNameId(),
				commerceTaxCategoryRelModelImpl.getClassPK()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_C, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, args,
			commerceTaxCategoryRelModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceTaxCategoryRelModelImpl commerceTaxCategoryRelModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceTaxCategoryRelModelImpl.getClassNameId(),
					commerceTaxCategoryRelModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, args);
		}

		if ((commerceTaxCategoryRelModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_C.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceTaxCategoryRelModelImpl.getOriginalClassNameId(),
					commerceTaxCategoryRelModelImpl.getOriginalClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, args);
		}
	}

	/**
	 * Creates a new commerce tax category rel with the primary key. Does not add the commerce tax category rel to the database.
	 *
	 * @param commerceTaxCategoryRelId the primary key for the new commerce tax category rel
	 * @return the new commerce tax category rel
	 */
	@Override
	public CommerceTaxCategoryRel create(long commerceTaxCategoryRelId) {
		CommerceTaxCategoryRel commerceTaxCategoryRel = new CommerceTaxCategoryRelImpl();

		commerceTaxCategoryRel.setNew(true);
		commerceTaxCategoryRel.setPrimaryKey(commerceTaxCategoryRelId);

		commerceTaxCategoryRel.setCompanyId(companyProvider.getCompanyId());

		return commerceTaxCategoryRel;
	}

	/**
	 * Removes the commerce tax category rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTaxCategoryRelId the primary key of the commerce tax category rel
	 * @return the commerce tax category rel that was removed
	 * @throws NoSuchTaxCategoryRelException if a commerce tax category rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategoryRel remove(long commerceTaxCategoryRelId)
		throws NoSuchTaxCategoryRelException {
		return remove((Serializable)commerceTaxCategoryRelId);
	}

	/**
	 * Removes the commerce tax category rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce tax category rel
	 * @return the commerce tax category rel that was removed
	 * @throws NoSuchTaxCategoryRelException if a commerce tax category rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategoryRel remove(Serializable primaryKey)
		throws NoSuchTaxCategoryRelException {
		Session session = null;

		try {
			session = openSession();

			CommerceTaxCategoryRel commerceTaxCategoryRel = (CommerceTaxCategoryRel)session.get(CommerceTaxCategoryRelImpl.class,
					primaryKey);

			if (commerceTaxCategoryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTaxCategoryRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceTaxCategoryRel);
		}
		catch (NoSuchTaxCategoryRelException nsee) {
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
	protected CommerceTaxCategoryRel removeImpl(
		CommerceTaxCategoryRel commerceTaxCategoryRel) {
		commerceTaxCategoryRel = toUnwrappedModel(commerceTaxCategoryRel);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceTaxCategoryRel)) {
				commerceTaxCategoryRel = (CommerceTaxCategoryRel)session.get(CommerceTaxCategoryRelImpl.class,
						commerceTaxCategoryRel.getPrimaryKeyObj());
			}

			if (commerceTaxCategoryRel != null) {
				session.delete(commerceTaxCategoryRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceTaxCategoryRel != null) {
			clearCache(commerceTaxCategoryRel);
		}

		return commerceTaxCategoryRel;
	}

	@Override
	public CommerceTaxCategoryRel updateImpl(
		CommerceTaxCategoryRel commerceTaxCategoryRel) {
		commerceTaxCategoryRel = toUnwrappedModel(commerceTaxCategoryRel);

		boolean isNew = commerceTaxCategoryRel.isNew();

		CommerceTaxCategoryRelModelImpl commerceTaxCategoryRelModelImpl = (CommerceTaxCategoryRelModelImpl)commerceTaxCategoryRel;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceTaxCategoryRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceTaxCategoryRel.setCreateDate(now);
			}
			else {
				commerceTaxCategoryRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceTaxCategoryRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceTaxCategoryRel.setModifiedDate(now);
			}
			else {
				commerceTaxCategoryRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceTaxCategoryRel.isNew()) {
				session.save(commerceTaxCategoryRel);

				commerceTaxCategoryRel.setNew(false);
			}
			else {
				commerceTaxCategoryRel = (CommerceTaxCategoryRel)session.merge(commerceTaxCategoryRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceTaxCategoryRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceTaxCategoryRelModelImpl.getCommerceTaxCategoryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCETAXCATEGORYID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXCATEGORYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceTaxCategoryRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXCATEGORYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTaxCategoryRelModelImpl.getOriginalCommerceTaxCategoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCETAXCATEGORYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXCATEGORYID,
					args);

				args = new Object[] {
						commerceTaxCategoryRelModelImpl.getCommerceTaxCategoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCETAXCATEGORYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXCATEGORYID,
					args);
			}
		}

		entityCache.putResult(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryRelImpl.class,
			commerceTaxCategoryRel.getPrimaryKey(), commerceTaxCategoryRel,
			false);

		clearUniqueFindersCache(commerceTaxCategoryRelModelImpl, false);
		cacheUniqueFindersCache(commerceTaxCategoryRelModelImpl);

		commerceTaxCategoryRel.resetOriginalValues();

		return commerceTaxCategoryRel;
	}

	protected CommerceTaxCategoryRel toUnwrappedModel(
		CommerceTaxCategoryRel commerceTaxCategoryRel) {
		if (commerceTaxCategoryRel instanceof CommerceTaxCategoryRelImpl) {
			return commerceTaxCategoryRel;
		}

		CommerceTaxCategoryRelImpl commerceTaxCategoryRelImpl = new CommerceTaxCategoryRelImpl();

		commerceTaxCategoryRelImpl.setNew(commerceTaxCategoryRel.isNew());
		commerceTaxCategoryRelImpl.setPrimaryKey(commerceTaxCategoryRel.getPrimaryKey());

		commerceTaxCategoryRelImpl.setCommerceTaxCategoryRelId(commerceTaxCategoryRel.getCommerceTaxCategoryRelId());
		commerceTaxCategoryRelImpl.setGroupId(commerceTaxCategoryRel.getGroupId());
		commerceTaxCategoryRelImpl.setCompanyId(commerceTaxCategoryRel.getCompanyId());
		commerceTaxCategoryRelImpl.setUserId(commerceTaxCategoryRel.getUserId());
		commerceTaxCategoryRelImpl.setUserName(commerceTaxCategoryRel.getUserName());
		commerceTaxCategoryRelImpl.setCreateDate(commerceTaxCategoryRel.getCreateDate());
		commerceTaxCategoryRelImpl.setModifiedDate(commerceTaxCategoryRel.getModifiedDate());
		commerceTaxCategoryRelImpl.setCommerceTaxCategoryId(commerceTaxCategoryRel.getCommerceTaxCategoryId());
		commerceTaxCategoryRelImpl.setClassNameId(commerceTaxCategoryRel.getClassNameId());
		commerceTaxCategoryRelImpl.setClassPK(commerceTaxCategoryRel.getClassPK());

		return commerceTaxCategoryRelImpl;
	}

	/**
	 * Returns the commerce tax category rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tax category rel
	 * @return the commerce tax category rel
	 * @throws NoSuchTaxCategoryRelException if a commerce tax category rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategoryRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTaxCategoryRelException {
		CommerceTaxCategoryRel commerceTaxCategoryRel = fetchByPrimaryKey(primaryKey);

		if (commerceTaxCategoryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTaxCategoryRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceTaxCategoryRel;
	}

	/**
	 * Returns the commerce tax category rel with the primary key or throws a {@link NoSuchTaxCategoryRelException} if it could not be found.
	 *
	 * @param commerceTaxCategoryRelId the primary key of the commerce tax category rel
	 * @return the commerce tax category rel
	 * @throws NoSuchTaxCategoryRelException if a commerce tax category rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategoryRel findByPrimaryKey(
		long commerceTaxCategoryRelId) throws NoSuchTaxCategoryRelException {
		return findByPrimaryKey((Serializable)commerceTaxCategoryRelId);
	}

	/**
	 * Returns the commerce tax category rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tax category rel
	 * @return the commerce tax category rel, or <code>null</code> if a commerce tax category rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategoryRel fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceTaxCategoryRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceTaxCategoryRel commerceTaxCategoryRel = (CommerceTaxCategoryRel)serializable;

		if (commerceTaxCategoryRel == null) {
			Session session = null;

			try {
				session = openSession();

				commerceTaxCategoryRel = (CommerceTaxCategoryRel)session.get(CommerceTaxCategoryRelImpl.class,
						primaryKey);

				if (commerceTaxCategoryRel != null) {
					cacheResult(commerceTaxCategoryRel);
				}
				else {
					entityCache.putResult(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceTaxCategoryRelImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTaxCategoryRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceTaxCategoryRel;
	}

	/**
	 * Returns the commerce tax category rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTaxCategoryRelId the primary key of the commerce tax category rel
	 * @return the commerce tax category rel, or <code>null</code> if a commerce tax category rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategoryRel fetchByPrimaryKey(
		long commerceTaxCategoryRelId) {
		return fetchByPrimaryKey((Serializable)commerceTaxCategoryRelId);
	}

	@Override
	public Map<Serializable, CommerceTaxCategoryRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceTaxCategoryRel> map = new HashMap<Serializable, CommerceTaxCategoryRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceTaxCategoryRel commerceTaxCategoryRel = fetchByPrimaryKey(primaryKey);

			if (commerceTaxCategoryRel != null) {
				map.put(primaryKey, commerceTaxCategoryRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTaxCategoryRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceTaxCategoryRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCETAXCATEGORYREL_WHERE_PKS_IN);

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

			for (CommerceTaxCategoryRel commerceTaxCategoryRel : (List<CommerceTaxCategoryRel>)q.list()) {
				map.put(commerceTaxCategoryRel.getPrimaryKeyObj(),
					commerceTaxCategoryRel);

				cacheResult(commerceTaxCategoryRel);

				uncachedPrimaryKeys.remove(commerceTaxCategoryRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceTaxCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTaxCategoryRelImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce tax category rels.
	 *
	 * @return the commerce tax category rels
	 */
	@Override
	public List<CommerceTaxCategoryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax category rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax category rels
	 * @param end the upper bound of the range of commerce tax category rels (not inclusive)
	 * @return the range of commerce tax category rels
	 */
	@Override
	public List<CommerceTaxCategoryRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax category rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax category rels
	 * @param end the upper bound of the range of commerce tax category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce tax category rels
	 */
	@Override
	public List<CommerceTaxCategoryRel> findAll(int start, int end,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax category rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax category rels
	 * @param end the upper bound of the range of commerce tax category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce tax category rels
	 */
	@Override
	public List<CommerceTaxCategoryRel> findAll(int start, int end,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator,
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

		List<CommerceTaxCategoryRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTaxCategoryRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCETAXCATEGORYREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCETAXCATEGORYREL;

				if (pagination) {
					sql = sql.concat(CommerceTaxCategoryRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceTaxCategoryRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTaxCategoryRel>)QueryUtil.list(q,
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
	 * Removes all the commerce tax category rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceTaxCategoryRel commerceTaxCategoryRel : findAll()) {
			remove(commerceTaxCategoryRel);
		}
	}

	/**
	 * Returns the number of commerce tax category rels.
	 *
	 * @return the number of commerce tax category rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCETAXCATEGORYREL);

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
		return CommerceTaxCategoryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce tax category rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceTaxCategoryRelImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCETAXCATEGORYREL = "SELECT commerceTaxCategoryRel FROM CommerceTaxCategoryRel commerceTaxCategoryRel";
	private static final String _SQL_SELECT_COMMERCETAXCATEGORYREL_WHERE_PKS_IN = "SELECT commerceTaxCategoryRel FROM CommerceTaxCategoryRel commerceTaxCategoryRel WHERE commerceTaxCategoryRelId IN (";
	private static final String _SQL_SELECT_COMMERCETAXCATEGORYREL_WHERE = "SELECT commerceTaxCategoryRel FROM CommerceTaxCategoryRel commerceTaxCategoryRel WHERE ";
	private static final String _SQL_COUNT_COMMERCETAXCATEGORYREL = "SELECT COUNT(commerceTaxCategoryRel) FROM CommerceTaxCategoryRel commerceTaxCategoryRel";
	private static final String _SQL_COUNT_COMMERCETAXCATEGORYREL_WHERE = "SELECT COUNT(commerceTaxCategoryRel) FROM CommerceTaxCategoryRel commerceTaxCategoryRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceTaxCategoryRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceTaxCategoryRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceTaxCategoryRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceTaxCategoryRelPersistenceImpl.class);
}