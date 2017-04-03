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

import com.liferay.commerce.product.exception.NoSuchProductDefintionOptionRelException;
import com.liferay.commerce.product.model.CommerceProductDefintionOptionRel;
import com.liferay.commerce.product.model.impl.CommerceProductDefintionOptionRelImpl;
import com.liferay.commerce.product.model.impl.CommerceProductDefintionOptionRelModelImpl;
import com.liferay.commerce.product.service.persistence.CommerceProductDefintionOptionRelPersistence;

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
import com.liferay.portal.kernel.util.StringPool;
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
 * The persistence implementation for the commerce product defintion option rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductDefintionOptionRelPersistence
 * @see com.liferay.commerce.product.service.persistence.CommerceProductDefintionOptionRelUtil
 * @generated
 */
@ProviderType
public class CommerceProductDefintionOptionRelPersistenceImpl
	extends BasePersistenceImpl<CommerceProductDefintionOptionRel>
	implements CommerceProductDefintionOptionRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceProductDefintionOptionRelUtil} to access the commerce product defintion option rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceProductDefintionOptionRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefintionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefintionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefintionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefintionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceProductDefintionOptionRelModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceProductDefintionOptionRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product defintion option rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce product defintion option rels
	 */
	@Override
	public List<CommerceProductDefintionOptionRel> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product defintion option rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product defintion option rels
	 * @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	 * @return the range of matching commerce product defintion option rels
	 */
	@Override
	public List<CommerceProductDefintionOptionRel> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product defintion option rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product defintion option rels
	 * @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product defintion option rels
	 */
	@Override
	public List<CommerceProductDefintionOptionRel> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product defintion option rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product defintion option rels
	 * @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product defintion option rels
	 */
	@Override
	public List<CommerceProductDefintionOptionRel> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<CommerceProductDefintionOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefintionOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductDefintionOptionRel commerceProductDefintionOptionRel : list) {
					if ((groupId != commerceProductDefintionOptionRel.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductDefintionOptionRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceProductDefintionOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefintionOptionRel>)QueryUtil.list(q,
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
	 * Returns the first commerce product defintion option rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product defintion option rel
	 * @throws NoSuchProductDefintionOptionRelException if a matching commerce product defintion option rel could not be found
	 */
	@Override
	public CommerceProductDefintionOptionRel findByGroupId_First(long groupId,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator)
		throws NoSuchProductDefintionOptionRelException {
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceProductDefintionOptionRel != null) {
			return commerceProductDefintionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefintionOptionRelException(msg.toString());
	}

	/**
	 * Returns the first commerce product defintion option rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product defintion option rel, or <code>null</code> if a matching commerce product defintion option rel could not be found
	 */
	@Override
	public CommerceProductDefintionOptionRel fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator) {
		List<CommerceProductDefintionOptionRel> list = findByGroupId(groupId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product defintion option rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product defintion option rel
	 * @throws NoSuchProductDefintionOptionRelException if a matching commerce product defintion option rel could not be found
	 */
	@Override
	public CommerceProductDefintionOptionRel findByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator)
		throws NoSuchProductDefintionOptionRelException {
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceProductDefintionOptionRel != null) {
			return commerceProductDefintionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefintionOptionRelException(msg.toString());
	}

	/**
	 * Returns the last commerce product defintion option rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product defintion option rel, or <code>null</code> if a matching commerce product defintion option rel could not be found
	 */
	@Override
	public CommerceProductDefintionOptionRel fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductDefintionOptionRel> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product defintion option rels before and after the current commerce product defintion option rel in the ordered set where groupId = &#63;.
	 *
	 * @param commerceProductDefintionOptionRelId the primary key of the current commerce product defintion option rel
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product defintion option rel
	 * @throws NoSuchProductDefintionOptionRelException if a commerce product defintion option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefintionOptionRel[] findByGroupId_PrevAndNext(
		long commerceProductDefintionOptionRelId, long groupId,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator)
		throws NoSuchProductDefintionOptionRelException {
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel = findByPrimaryKey(commerceProductDefintionOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductDefintionOptionRel[] array = new CommerceProductDefintionOptionRelImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					commerceProductDefintionOptionRel, groupId,
					orderByComparator, true);

			array[1] = commerceProductDefintionOptionRel;

			array[2] = getByGroupId_PrevAndNext(session,
					commerceProductDefintionOptionRel, groupId,
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

	protected CommerceProductDefintionOptionRel getByGroupId_PrevAndNext(
		Session session,
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel,
		long groupId,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONREL_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(CommerceProductDefintionOptionRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductDefintionOptionRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductDefintionOptionRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product defintion option rels where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceProductDefintionOptionRel commerceProductDefintionOptionRel : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductDefintionOptionRel);
		}
	}

	/**
	 * Returns the number of commerce product defintion option rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce product defintion option rels
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTDEFINTIONOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceProductDefintionOptionRel.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefintionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefintionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			CommerceProductDefintionOptionRelModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceProductDefintionOptionRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product defintion option rels where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce product defintion option rels
	 */
	@Override
	public List<CommerceProductDefintionOptionRel> findByCompanyId(
		long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce product defintion option rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product defintion option rels
	 * @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	 * @return the range of matching commerce product defintion option rels
	 */
	@Override
	public List<CommerceProductDefintionOptionRel> findByCompanyId(
		long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product defintion option rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product defintion option rels
	 * @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product defintion option rels
	 */
	@Override
	public List<CommerceProductDefintionOptionRel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product defintion option rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product defintion option rels
	 * @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product defintion option rels
	 */
	@Override
	public List<CommerceProductDefintionOptionRel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<CommerceProductDefintionOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefintionOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductDefintionOptionRel commerceProductDefintionOptionRel : list) {
					if ((companyId != commerceProductDefintionOptionRel.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductDefintionOptionRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CommerceProductDefintionOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefintionOptionRel>)QueryUtil.list(q,
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
	 * Returns the first commerce product defintion option rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product defintion option rel
	 * @throws NoSuchProductDefintionOptionRelException if a matching commerce product defintion option rel could not be found
	 */
	@Override
	public CommerceProductDefintionOptionRel findByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator)
		throws NoSuchProductDefintionOptionRelException {
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (commerceProductDefintionOptionRel != null) {
			return commerceProductDefintionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefintionOptionRelException(msg.toString());
	}

	/**
	 * Returns the first commerce product defintion option rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product defintion option rel, or <code>null</code> if a matching commerce product defintion option rel could not be found
	 */
	@Override
	public CommerceProductDefintionOptionRel fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator) {
		List<CommerceProductDefintionOptionRel> list = findByCompanyId(companyId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product defintion option rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product defintion option rel
	 * @throws NoSuchProductDefintionOptionRelException if a matching commerce product defintion option rel could not be found
	 */
	@Override
	public CommerceProductDefintionOptionRel findByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator)
		throws NoSuchProductDefintionOptionRelException {
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (commerceProductDefintionOptionRel != null) {
			return commerceProductDefintionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefintionOptionRelException(msg.toString());
	}

	/**
	 * Returns the last commerce product defintion option rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product defintion option rel, or <code>null</code> if a matching commerce product defintion option rel could not be found
	 */
	@Override
	public CommerceProductDefintionOptionRel fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductDefintionOptionRel> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product defintion option rels before and after the current commerce product defintion option rel in the ordered set where companyId = &#63;.
	 *
	 * @param commerceProductDefintionOptionRelId the primary key of the current commerce product defintion option rel
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product defintion option rel
	 * @throws NoSuchProductDefintionOptionRelException if a commerce product defintion option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefintionOptionRel[] findByCompanyId_PrevAndNext(
		long commerceProductDefintionOptionRelId, long companyId,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator)
		throws NoSuchProductDefintionOptionRelException {
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel = findByPrimaryKey(commerceProductDefintionOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductDefintionOptionRel[] array = new CommerceProductDefintionOptionRelImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					commerceProductDefintionOptionRel, companyId,
					orderByComparator, true);

			array[1] = commerceProductDefintionOptionRel;

			array[2] = getByCompanyId_PrevAndNext(session,
					commerceProductDefintionOptionRel, companyId,
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

	protected CommerceProductDefintionOptionRel getByCompanyId_PrevAndNext(
		Session session,
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel,
		long companyId,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONREL_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(CommerceProductDefintionOptionRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductDefintionOptionRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductDefintionOptionRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product defintion option rels where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceProductDefintionOptionRel commerceProductDefintionOptionRel : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductDefintionOptionRel);
		}
	}

	/**
	 * Returns the number of commerce product defintion option rels where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce product defintion option rels
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTDEFINTIONOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "commerceProductDefintionOptionRel.companyId = ?";

	public CommerceProductDefintionOptionRelPersistenceImpl() {
		setModelClass(CommerceProductDefintionOptionRel.class);
	}

	/**
	 * Caches the commerce product defintion option rel in the entity cache if it is enabled.
	 *
	 * @param commerceProductDefintionOptionRel the commerce product defintion option rel
	 */
	@Override
	public void cacheResult(
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel) {
		entityCache.putResult(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionRelImpl.class,
			commerceProductDefintionOptionRel.getPrimaryKey(),
			commerceProductDefintionOptionRel);

		commerceProductDefintionOptionRel.resetOriginalValues();
	}

	/**
	 * Caches the commerce product defintion option rels in the entity cache if it is enabled.
	 *
	 * @param commerceProductDefintionOptionRels the commerce product defintion option rels
	 */
	@Override
	public void cacheResult(
		List<CommerceProductDefintionOptionRel> commerceProductDefintionOptionRels) {
		for (CommerceProductDefintionOptionRel commerceProductDefintionOptionRel : commerceProductDefintionOptionRels) {
			if (entityCache.getResult(
						CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductDefintionOptionRelImpl.class,
						commerceProductDefintionOptionRel.getPrimaryKey()) == null) {
				cacheResult(commerceProductDefintionOptionRel);
			}
			else {
				commerceProductDefintionOptionRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce product defintion option rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceProductDefintionOptionRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce product defintion option rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel) {
		entityCache.removeResult(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionRelImpl.class,
			commerceProductDefintionOptionRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<CommerceProductDefintionOptionRel> commerceProductDefintionOptionRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceProductDefintionOptionRel commerceProductDefintionOptionRel : commerceProductDefintionOptionRels) {
			entityCache.removeResult(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductDefintionOptionRelImpl.class,
				commerceProductDefintionOptionRel.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce product defintion option rel with the primary key. Does not add the commerce product defintion option rel to the database.
	 *
	 * @param commerceProductDefintionOptionRelId the primary key for the new commerce product defintion option rel
	 * @return the new commerce product defintion option rel
	 */
	@Override
	public CommerceProductDefintionOptionRel create(
		long commerceProductDefintionOptionRelId) {
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel = new CommerceProductDefintionOptionRelImpl();

		commerceProductDefintionOptionRel.setNew(true);
		commerceProductDefintionOptionRel.setPrimaryKey(commerceProductDefintionOptionRelId);

		commerceProductDefintionOptionRel.setCompanyId(companyProvider.getCompanyId());

		return commerceProductDefintionOptionRel;
	}

	/**
	 * Removes the commerce product defintion option rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceProductDefintionOptionRelId the primary key of the commerce product defintion option rel
	 * @return the commerce product defintion option rel that was removed
	 * @throws NoSuchProductDefintionOptionRelException if a commerce product defintion option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefintionOptionRel remove(
		long commerceProductDefintionOptionRelId)
		throws NoSuchProductDefintionOptionRelException {
		return remove((Serializable)commerceProductDefintionOptionRelId);
	}

	/**
	 * Removes the commerce product defintion option rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce product defintion option rel
	 * @return the commerce product defintion option rel that was removed
	 * @throws NoSuchProductDefintionOptionRelException if a commerce product defintion option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefintionOptionRel remove(Serializable primaryKey)
		throws NoSuchProductDefintionOptionRelException {
		Session session = null;

		try {
			session = openSession();

			CommerceProductDefintionOptionRel commerceProductDefintionOptionRel = (CommerceProductDefintionOptionRel)session.get(CommerceProductDefintionOptionRelImpl.class,
					primaryKey);

			if (commerceProductDefintionOptionRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProductDefintionOptionRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceProductDefintionOptionRel);
		}
		catch (NoSuchProductDefintionOptionRelException nsee) {
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
	protected CommerceProductDefintionOptionRel removeImpl(
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel) {
		commerceProductDefintionOptionRel = toUnwrappedModel(commerceProductDefintionOptionRel);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceProductDefintionOptionRel)) {
				commerceProductDefintionOptionRel = (CommerceProductDefintionOptionRel)session.get(CommerceProductDefintionOptionRelImpl.class,
						commerceProductDefintionOptionRel.getPrimaryKeyObj());
			}

			if (commerceProductDefintionOptionRel != null) {
				session.delete(commerceProductDefintionOptionRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceProductDefintionOptionRel != null) {
			clearCache(commerceProductDefintionOptionRel);
		}

		return commerceProductDefintionOptionRel;
	}

	@Override
	public CommerceProductDefintionOptionRel updateImpl(
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel) {
		commerceProductDefintionOptionRel = toUnwrappedModel(commerceProductDefintionOptionRel);

		boolean isNew = commerceProductDefintionOptionRel.isNew();

		CommerceProductDefintionOptionRelModelImpl commerceProductDefintionOptionRelModelImpl =
			(CommerceProductDefintionOptionRelModelImpl)commerceProductDefintionOptionRel;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew &&
				(commerceProductDefintionOptionRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceProductDefintionOptionRel.setCreateDate(now);
			}
			else {
				commerceProductDefintionOptionRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceProductDefintionOptionRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceProductDefintionOptionRel.setModifiedDate(now);
			}
			else {
				commerceProductDefintionOptionRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceProductDefintionOptionRel.isNew()) {
				session.save(commerceProductDefintionOptionRel);

				commerceProductDefintionOptionRel.setNew(false);
			}
			else {
				commerceProductDefintionOptionRel = (CommerceProductDefintionOptionRel)session.merge(commerceProductDefintionOptionRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceProductDefintionOptionRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceProductDefintionOptionRelModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					commerceProductDefintionOptionRelModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceProductDefintionOptionRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductDefintionOptionRelModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						commerceProductDefintionOptionRelModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceProductDefintionOptionRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductDefintionOptionRelModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						commerceProductDefintionOptionRelModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		entityCache.putResult(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionRelImpl.class,
			commerceProductDefintionOptionRel.getPrimaryKey(),
			commerceProductDefintionOptionRel, false);

		commerceProductDefintionOptionRel.resetOriginalValues();

		return commerceProductDefintionOptionRel;
	}

	protected CommerceProductDefintionOptionRel toUnwrappedModel(
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel) {
		if (commerceProductDefintionOptionRel instanceof CommerceProductDefintionOptionRelImpl) {
			return commerceProductDefintionOptionRel;
		}

		CommerceProductDefintionOptionRelImpl commerceProductDefintionOptionRelImpl =
			new CommerceProductDefintionOptionRelImpl();

		commerceProductDefintionOptionRelImpl.setNew(commerceProductDefintionOptionRel.isNew());
		commerceProductDefintionOptionRelImpl.setPrimaryKey(commerceProductDefintionOptionRel.getPrimaryKey());

		commerceProductDefintionOptionRelImpl.setCommerceProductDefintionOptionRelId(commerceProductDefintionOptionRel.getCommerceProductDefintionOptionRelId());
		commerceProductDefintionOptionRelImpl.setGroupId(commerceProductDefintionOptionRel.getGroupId());
		commerceProductDefintionOptionRelImpl.setCompanyId(commerceProductDefintionOptionRel.getCompanyId());
		commerceProductDefintionOptionRelImpl.setUserId(commerceProductDefintionOptionRel.getUserId());
		commerceProductDefintionOptionRelImpl.setUserName(commerceProductDefintionOptionRel.getUserName());
		commerceProductDefintionOptionRelImpl.setCreateDate(commerceProductDefintionOptionRel.getCreateDate());
		commerceProductDefintionOptionRelImpl.setModifiedDate(commerceProductDefintionOptionRel.getModifiedDate());
		commerceProductDefintionOptionRelImpl.setCommerceProductOptionId(commerceProductDefintionOptionRel.getCommerceProductOptionId());
		commerceProductDefintionOptionRelImpl.setCommerceProductDefinitionId(commerceProductDefintionOptionRel.getCommerceProductDefinitionId());
		commerceProductDefintionOptionRelImpl.setName(commerceProductDefintionOptionRel.getName());
		commerceProductDefintionOptionRelImpl.setDescription(commerceProductDefintionOptionRel.getDescription());
		commerceProductDefintionOptionRelImpl.setDDMFormFieldTypeName(commerceProductDefintionOptionRel.getDDMFormFieldTypeName());
		commerceProductDefintionOptionRelImpl.setPriority(commerceProductDefintionOptionRel.getPriority());

		return commerceProductDefintionOptionRelImpl;
	}

	/**
	 * Returns the commerce product defintion option rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product defintion option rel
	 * @return the commerce product defintion option rel
	 * @throws NoSuchProductDefintionOptionRelException if a commerce product defintion option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefintionOptionRel findByPrimaryKey(
		Serializable primaryKey)
		throws NoSuchProductDefintionOptionRelException {
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel = fetchByPrimaryKey(primaryKey);

		if (commerceProductDefintionOptionRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchProductDefintionOptionRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceProductDefintionOptionRel;
	}

	/**
	 * Returns the commerce product defintion option rel with the primary key or throws a {@link NoSuchProductDefintionOptionRelException} if it could not be found.
	 *
	 * @param commerceProductDefintionOptionRelId the primary key of the commerce product defintion option rel
	 * @return the commerce product defintion option rel
	 * @throws NoSuchProductDefintionOptionRelException if a commerce product defintion option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefintionOptionRel findByPrimaryKey(
		long commerceProductDefintionOptionRelId)
		throws NoSuchProductDefintionOptionRelException {
		return findByPrimaryKey((Serializable)commerceProductDefintionOptionRelId);
	}

	/**
	 * Returns the commerce product defintion option rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product defintion option rel
	 * @return the commerce product defintion option rel, or <code>null</code> if a commerce product defintion option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefintionOptionRel fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductDefintionOptionRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel = (CommerceProductDefintionOptionRel)serializable;

		if (commerceProductDefintionOptionRel == null) {
			Session session = null;

			try {
				session = openSession();

				commerceProductDefintionOptionRel = (CommerceProductDefintionOptionRel)session.get(CommerceProductDefintionOptionRelImpl.class,
						primaryKey);

				if (commerceProductDefintionOptionRel != null) {
					cacheResult(commerceProductDefintionOptionRel);
				}
				else {
					entityCache.putResult(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductDefintionOptionRelImpl.class,
						primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefintionOptionRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceProductDefintionOptionRel;
	}

	/**
	 * Returns the commerce product defintion option rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceProductDefintionOptionRelId the primary key of the commerce product defintion option rel
	 * @return the commerce product defintion option rel, or <code>null</code> if a commerce product defintion option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefintionOptionRel fetchByPrimaryKey(
		long commerceProductDefintionOptionRelId) {
		return fetchByPrimaryKey((Serializable)commerceProductDefintionOptionRelId);
	}

	@Override
	public Map<Serializable, CommerceProductDefintionOptionRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceProductDefintionOptionRel> map = new HashMap<Serializable, CommerceProductDefintionOptionRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceProductDefintionOptionRel commerceProductDefintionOptionRel = fetchByPrimaryKey(primaryKey);

			if (commerceProductDefintionOptionRel != null) {
				map.put(primaryKey, commerceProductDefintionOptionRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefintionOptionRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommerceProductDefintionOptionRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONREL_WHERE_PKS_IN);

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

			for (CommerceProductDefintionOptionRel commerceProductDefintionOptionRel : (List<CommerceProductDefintionOptionRel>)q.list()) {
				map.put(commerceProductDefintionOptionRel.getPrimaryKeyObj(),
					commerceProductDefintionOptionRel);

				cacheResult(commerceProductDefintionOptionRel);

				uncachedPrimaryKeys.remove(commerceProductDefintionOptionRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceProductDefintionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefintionOptionRelImpl.class, primaryKey,
					nullModel);
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
	 * Returns all the commerce product defintion option rels.
	 *
	 * @return the commerce product defintion option rels
	 */
	@Override
	public List<CommerceProductDefintionOptionRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product defintion option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product defintion option rels
	 * @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	 * @return the range of commerce product defintion option rels
	 */
	@Override
	public List<CommerceProductDefintionOptionRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product defintion option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product defintion option rels
	 * @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce product defintion option rels
	 */
	@Override
	public List<CommerceProductDefintionOptionRel> findAll(int start, int end,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product defintion option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product defintion option rels
	 * @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce product defintion option rels
	 */
	@Override
	public List<CommerceProductDefintionOptionRel> findAll(int start, int end,
		OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator,
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

		List<CommerceProductDefintionOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefintionOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONREL;

				if (pagination) {
					sql = sql.concat(CommerceProductDefintionOptionRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceProductDefintionOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefintionOptionRel>)QueryUtil.list(q,
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
	 * Removes all the commerce product defintion option rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceProductDefintionOptionRel commerceProductDefintionOptionRel : findAll()) {
			remove(commerceProductDefintionOptionRel);
		}
	}

	/**
	 * Returns the number of commerce product defintion option rels.
	 *
	 * @return the number of commerce product defintion option rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEPRODUCTDEFINTIONOPTIONREL);

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
		return CommerceProductDefintionOptionRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce product defintion option rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceProductDefintionOptionRelImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONREL = "SELECT commerceProductDefintionOptionRel FROM CommerceProductDefintionOptionRel commerceProductDefintionOptionRel";
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONREL_WHERE_PKS_IN =
		"SELECT commerceProductDefintionOptionRel FROM CommerceProductDefintionOptionRel commerceProductDefintionOptionRel WHERE commerceProductDefintionOptionRelId IN (";
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONREL_WHERE =
		"SELECT commerceProductDefintionOptionRel FROM CommerceProductDefintionOptionRel commerceProductDefintionOptionRel WHERE ";
	private static final String _SQL_COUNT_COMMERCEPRODUCTDEFINTIONOPTIONREL = "SELECT COUNT(commerceProductDefintionOptionRel) FROM CommerceProductDefintionOptionRel commerceProductDefintionOptionRel";
	private static final String _SQL_COUNT_COMMERCEPRODUCTDEFINTIONOPTIONREL_WHERE =
		"SELECT COUNT(commerceProductDefintionOptionRel) FROM CommerceProductDefintionOptionRel commerceProductDefintionOptionRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceProductDefintionOptionRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceProductDefintionOptionRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceProductDefintionOptionRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceProductDefintionOptionRelPersistenceImpl.class);
}