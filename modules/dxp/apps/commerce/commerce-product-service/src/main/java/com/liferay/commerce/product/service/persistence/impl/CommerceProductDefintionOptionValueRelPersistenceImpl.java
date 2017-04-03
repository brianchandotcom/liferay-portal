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

import com.liferay.commerce.product.exception.NoSuchProductDefintionOptionValueRelException;
import com.liferay.commerce.product.model.CommerceProductDefintionOptionValueRel;
import com.liferay.commerce.product.model.impl.CommerceProductDefintionOptionValueRelImpl;
import com.liferay.commerce.product.model.impl.CommerceProductDefintionOptionValueRelModelImpl;
import com.liferay.commerce.product.service.persistence.CommerceProductDefintionOptionValueRelPersistence;

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
 * The persistence implementation for the commerce product defintion option value rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductDefintionOptionValueRelPersistence
 * @see com.liferay.commerce.product.service.persistence.CommerceProductDefintionOptionValueRelUtil
 * @generated
 */
@ProviderType
public class CommerceProductDefintionOptionValueRelPersistenceImpl
	extends BasePersistenceImpl<CommerceProductDefintionOptionValueRel>
	implements CommerceProductDefintionOptionValueRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceProductDefintionOptionValueRelUtil} to access the commerce product defintion option value rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceProductDefintionOptionValueRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceProductDefintionOptionValueRelModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceProductDefintionOptionValueRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product defintion option value rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce product defintion option value rels
	 */
	@Override
	public List<CommerceProductDefintionOptionValueRel> findByGroupId(
		long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product defintion option value rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product defintion option value rels
	 * @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	 * @return the range of matching commerce product defintion option value rels
	 */
	@Override
	public List<CommerceProductDefintionOptionValueRel> findByGroupId(
		long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product defintion option value rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product defintion option value rels
	 * @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product defintion option value rels
	 */
	@Override
	public List<CommerceProductDefintionOptionValueRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product defintion option value rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product defintion option value rels
	 * @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product defintion option value rels
	 */
	@Override
	public List<CommerceProductDefintionOptionValueRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator,
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

		List<CommerceProductDefintionOptionValueRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefintionOptionValueRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel : list) {
					if ((groupId != commerceProductDefintionOptionValueRel.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONVALUEREL_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductDefintionOptionValueRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceProductDefintionOptionValueRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefintionOptionValueRel>)QueryUtil.list(q,
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
	 * Returns the first commerce product defintion option value rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product defintion option value rel
	 * @throws NoSuchProductDefintionOptionValueRelException if a matching commerce product defintion option value rel could not be found
	 */
	@Override
	public CommerceProductDefintionOptionValueRel findByGroupId_First(
		long groupId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator)
		throws NoSuchProductDefintionOptionValueRelException {
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel =
			fetchByGroupId_First(groupId, orderByComparator);

		if (commerceProductDefintionOptionValueRel != null) {
			return commerceProductDefintionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefintionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the first commerce product defintion option value rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product defintion option value rel, or <code>null</code> if a matching commerce product defintion option value rel could not be found
	 */
	@Override
	public CommerceProductDefintionOptionValueRel fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator) {
		List<CommerceProductDefintionOptionValueRel> list = findByGroupId(groupId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product defintion option value rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product defintion option value rel
	 * @throws NoSuchProductDefintionOptionValueRelException if a matching commerce product defintion option value rel could not be found
	 */
	@Override
	public CommerceProductDefintionOptionValueRel findByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator)
		throws NoSuchProductDefintionOptionValueRelException {
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel =
			fetchByGroupId_Last(groupId, orderByComparator);

		if (commerceProductDefintionOptionValueRel != null) {
			return commerceProductDefintionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefintionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the last commerce product defintion option value rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product defintion option value rel, or <code>null</code> if a matching commerce product defintion option value rel could not be found
	 */
	@Override
	public CommerceProductDefintionOptionValueRel fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductDefintionOptionValueRel> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product defintion option value rels before and after the current commerce product defintion option value rel in the ordered set where groupId = &#63;.
	 *
	 * @param commerceProductDefintionOptionValueRelId the primary key of the current commerce product defintion option value rel
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product defintion option value rel
	 * @throws NoSuchProductDefintionOptionValueRelException if a commerce product defintion option value rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefintionOptionValueRel[] findByGroupId_PrevAndNext(
		long commerceProductDefintionOptionValueRelId, long groupId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator)
		throws NoSuchProductDefintionOptionValueRelException {
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel =
			findByPrimaryKey(commerceProductDefintionOptionValueRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductDefintionOptionValueRel[] array = new CommerceProductDefintionOptionValueRelImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					commerceProductDefintionOptionValueRel, groupId,
					orderByComparator, true);

			array[1] = commerceProductDefintionOptionValueRel;

			array[2] = getByGroupId_PrevAndNext(session,
					commerceProductDefintionOptionValueRel, groupId,
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

	protected CommerceProductDefintionOptionValueRel getByGroupId_PrevAndNext(
		Session session,
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel,
		long groupId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONVALUEREL_WHERE);

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
			query.append(CommerceProductDefintionOptionValueRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductDefintionOptionValueRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductDefintionOptionValueRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product defintion option value rels where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductDefintionOptionValueRel);
		}
	}

	/**
	 * Returns the number of commerce product defintion option value rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce product defintion option value rels
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTDEFINTIONOPTIONVALUEREL_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceProductDefintionOptionValueRel.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			CommerceProductDefintionOptionValueRelModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceProductDefintionOptionValueRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product defintion option value rels where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce product defintion option value rels
	 */
	@Override
	public List<CommerceProductDefintionOptionValueRel> findByCompanyId(
		long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce product defintion option value rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product defintion option value rels
	 * @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	 * @return the range of matching commerce product defintion option value rels
	 */
	@Override
	public List<CommerceProductDefintionOptionValueRel> findByCompanyId(
		long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product defintion option value rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product defintion option value rels
	 * @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product defintion option value rels
	 */
	@Override
	public List<CommerceProductDefintionOptionValueRel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product defintion option value rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product defintion option value rels
	 * @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product defintion option value rels
	 */
	@Override
	public List<CommerceProductDefintionOptionValueRel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator,
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

		List<CommerceProductDefintionOptionValueRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefintionOptionValueRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel : list) {
					if ((companyId != commerceProductDefintionOptionValueRel.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONVALUEREL_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductDefintionOptionValueRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CommerceProductDefintionOptionValueRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefintionOptionValueRel>)QueryUtil.list(q,
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
	 * Returns the first commerce product defintion option value rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product defintion option value rel
	 * @throws NoSuchProductDefintionOptionValueRelException if a matching commerce product defintion option value rel could not be found
	 */
	@Override
	public CommerceProductDefintionOptionValueRel findByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator)
		throws NoSuchProductDefintionOptionValueRelException {
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel =
			fetchByCompanyId_First(companyId, orderByComparator);

		if (commerceProductDefintionOptionValueRel != null) {
			return commerceProductDefintionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefintionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the first commerce product defintion option value rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product defintion option value rel, or <code>null</code> if a matching commerce product defintion option value rel could not be found
	 */
	@Override
	public CommerceProductDefintionOptionValueRel fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator) {
		List<CommerceProductDefintionOptionValueRel> list = findByCompanyId(companyId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product defintion option value rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product defintion option value rel
	 * @throws NoSuchProductDefintionOptionValueRelException if a matching commerce product defintion option value rel could not be found
	 */
	@Override
	public CommerceProductDefintionOptionValueRel findByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator)
		throws NoSuchProductDefintionOptionValueRelException {
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel =
			fetchByCompanyId_Last(companyId, orderByComparator);

		if (commerceProductDefintionOptionValueRel != null) {
			return commerceProductDefintionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefintionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the last commerce product defintion option value rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product defintion option value rel, or <code>null</code> if a matching commerce product defintion option value rel could not be found
	 */
	@Override
	public CommerceProductDefintionOptionValueRel fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductDefintionOptionValueRel> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product defintion option value rels before and after the current commerce product defintion option value rel in the ordered set where companyId = &#63;.
	 *
	 * @param commerceProductDefintionOptionValueRelId the primary key of the current commerce product defintion option value rel
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product defintion option value rel
	 * @throws NoSuchProductDefintionOptionValueRelException if a commerce product defintion option value rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefintionOptionValueRel[] findByCompanyId_PrevAndNext(
		long commerceProductDefintionOptionValueRelId, long companyId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator)
		throws NoSuchProductDefintionOptionValueRelException {
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel =
			findByPrimaryKey(commerceProductDefintionOptionValueRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductDefintionOptionValueRel[] array = new CommerceProductDefintionOptionValueRelImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					commerceProductDefintionOptionValueRel, companyId,
					orderByComparator, true);

			array[1] = commerceProductDefintionOptionValueRel;

			array[2] = getByCompanyId_PrevAndNext(session,
					commerceProductDefintionOptionValueRel, companyId,
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

	protected CommerceProductDefintionOptionValueRel getByCompanyId_PrevAndNext(
		Session session,
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel,
		long companyId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONVALUEREL_WHERE);

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
			query.append(CommerceProductDefintionOptionValueRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductDefintionOptionValueRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductDefintionOptionValueRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product defintion option value rels where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductDefintionOptionValueRel);
		}
	}

	/**
	 * Returns the number of commerce product defintion option value rels where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce product defintion option value rels
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTDEFINTIONOPTIONVALUEREL_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "commerceProductDefintionOptionValueRel.companyId = ?";

	public CommerceProductDefintionOptionValueRelPersistenceImpl() {
		setModelClass(CommerceProductDefintionOptionValueRel.class);
	}

	/**
	 * Caches the commerce product defintion option value rel in the entity cache if it is enabled.
	 *
	 * @param commerceProductDefintionOptionValueRel the commerce product defintion option value rel
	 */
	@Override
	public void cacheResult(
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel) {
		entityCache.putResult(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelImpl.class,
			commerceProductDefintionOptionValueRel.getPrimaryKey(),
			commerceProductDefintionOptionValueRel);

		commerceProductDefintionOptionValueRel.resetOriginalValues();
	}

	/**
	 * Caches the commerce product defintion option value rels in the entity cache if it is enabled.
	 *
	 * @param commerceProductDefintionOptionValueRels the commerce product defintion option value rels
	 */
	@Override
	public void cacheResult(
		List<CommerceProductDefintionOptionValueRel> commerceProductDefintionOptionValueRels) {
		for (CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel : commerceProductDefintionOptionValueRels) {
			if (entityCache.getResult(
						CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductDefintionOptionValueRelImpl.class,
						commerceProductDefintionOptionValueRel.getPrimaryKey()) == null) {
				cacheResult(commerceProductDefintionOptionValueRel);
			}
			else {
				commerceProductDefintionOptionValueRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce product defintion option value rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceProductDefintionOptionValueRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce product defintion option value rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel) {
		entityCache.removeResult(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelImpl.class,
			commerceProductDefintionOptionValueRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<CommerceProductDefintionOptionValueRel> commerceProductDefintionOptionValueRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel : commerceProductDefintionOptionValueRels) {
			entityCache.removeResult(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductDefintionOptionValueRelImpl.class,
				commerceProductDefintionOptionValueRel.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce product defintion option value rel with the primary key. Does not add the commerce product defintion option value rel to the database.
	 *
	 * @param commerceProductDefintionOptionValueRelId the primary key for the new commerce product defintion option value rel
	 * @return the new commerce product defintion option value rel
	 */
	@Override
	public CommerceProductDefintionOptionValueRel create(
		long commerceProductDefintionOptionValueRelId) {
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel =
			new CommerceProductDefintionOptionValueRelImpl();

		commerceProductDefintionOptionValueRel.setNew(true);
		commerceProductDefintionOptionValueRel.setPrimaryKey(commerceProductDefintionOptionValueRelId);

		commerceProductDefintionOptionValueRel.setCompanyId(companyProvider.getCompanyId());

		return commerceProductDefintionOptionValueRel;
	}

	/**
	 * Removes the commerce product defintion option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceProductDefintionOptionValueRelId the primary key of the commerce product defintion option value rel
	 * @return the commerce product defintion option value rel that was removed
	 * @throws NoSuchProductDefintionOptionValueRelException if a commerce product defintion option value rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefintionOptionValueRel remove(
		long commerceProductDefintionOptionValueRelId)
		throws NoSuchProductDefintionOptionValueRelException {
		return remove((Serializable)commerceProductDefintionOptionValueRelId);
	}

	/**
	 * Removes the commerce product defintion option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce product defintion option value rel
	 * @return the commerce product defintion option value rel that was removed
	 * @throws NoSuchProductDefintionOptionValueRelException if a commerce product defintion option value rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefintionOptionValueRel remove(
		Serializable primaryKey)
		throws NoSuchProductDefintionOptionValueRelException {
		Session session = null;

		try {
			session = openSession();

			CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel =
				(CommerceProductDefintionOptionValueRel)session.get(CommerceProductDefintionOptionValueRelImpl.class,
					primaryKey);

			if (commerceProductDefintionOptionValueRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProductDefintionOptionValueRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceProductDefintionOptionValueRel);
		}
		catch (NoSuchProductDefintionOptionValueRelException nsee) {
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
	protected CommerceProductDefintionOptionValueRel removeImpl(
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel) {
		commerceProductDefintionOptionValueRel = toUnwrappedModel(commerceProductDefintionOptionValueRel);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceProductDefintionOptionValueRel)) {
				commerceProductDefintionOptionValueRel = (CommerceProductDefintionOptionValueRel)session.get(CommerceProductDefintionOptionValueRelImpl.class,
						commerceProductDefintionOptionValueRel.getPrimaryKeyObj());
			}

			if (commerceProductDefintionOptionValueRel != null) {
				session.delete(commerceProductDefintionOptionValueRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceProductDefintionOptionValueRel != null) {
			clearCache(commerceProductDefintionOptionValueRel);
		}

		return commerceProductDefintionOptionValueRel;
	}

	@Override
	public CommerceProductDefintionOptionValueRel updateImpl(
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel) {
		commerceProductDefintionOptionValueRel = toUnwrappedModel(commerceProductDefintionOptionValueRel);

		boolean isNew = commerceProductDefintionOptionValueRel.isNew();

		CommerceProductDefintionOptionValueRelModelImpl commerceProductDefintionOptionValueRelModelImpl =
			(CommerceProductDefintionOptionValueRelModelImpl)commerceProductDefintionOptionValueRel;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew &&
				(commerceProductDefintionOptionValueRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceProductDefintionOptionValueRel.setCreateDate(now);
			}
			else {
				commerceProductDefintionOptionValueRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceProductDefintionOptionValueRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceProductDefintionOptionValueRel.setModifiedDate(now);
			}
			else {
				commerceProductDefintionOptionValueRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceProductDefintionOptionValueRel.isNew()) {
				session.save(commerceProductDefintionOptionValueRel);

				commerceProductDefintionOptionValueRel.setNew(false);
			}
			else {
				commerceProductDefintionOptionValueRel = (CommerceProductDefintionOptionValueRel)session.merge(commerceProductDefintionOptionValueRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceProductDefintionOptionValueRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceProductDefintionOptionValueRelModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					commerceProductDefintionOptionValueRelModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceProductDefintionOptionValueRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductDefintionOptionValueRelModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						commerceProductDefintionOptionValueRelModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceProductDefintionOptionValueRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductDefintionOptionValueRelModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						commerceProductDefintionOptionValueRelModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		entityCache.putResult(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefintionOptionValueRelImpl.class,
			commerceProductDefintionOptionValueRel.getPrimaryKey(),
			commerceProductDefintionOptionValueRel, false);

		commerceProductDefintionOptionValueRel.resetOriginalValues();

		return commerceProductDefintionOptionValueRel;
	}

	protected CommerceProductDefintionOptionValueRel toUnwrappedModel(
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel) {
		if (commerceProductDefintionOptionValueRel instanceof CommerceProductDefintionOptionValueRelImpl) {
			return commerceProductDefintionOptionValueRel;
		}

		CommerceProductDefintionOptionValueRelImpl commerceProductDefintionOptionValueRelImpl =
			new CommerceProductDefintionOptionValueRelImpl();

		commerceProductDefintionOptionValueRelImpl.setNew(commerceProductDefintionOptionValueRel.isNew());
		commerceProductDefintionOptionValueRelImpl.setPrimaryKey(commerceProductDefintionOptionValueRel.getPrimaryKey());

		commerceProductDefintionOptionValueRelImpl.setCommerceProductDefintionOptionValueRelId(commerceProductDefintionOptionValueRel.getCommerceProductDefintionOptionValueRelId());
		commerceProductDefintionOptionValueRelImpl.setGroupId(commerceProductDefintionOptionValueRel.getGroupId());
		commerceProductDefintionOptionValueRelImpl.setCompanyId(commerceProductDefintionOptionValueRel.getCompanyId());
		commerceProductDefintionOptionValueRelImpl.setUserId(commerceProductDefintionOptionValueRel.getUserId());
		commerceProductDefintionOptionValueRelImpl.setUserName(commerceProductDefintionOptionValueRel.getUserName());
		commerceProductDefintionOptionValueRelImpl.setCreateDate(commerceProductDefintionOptionValueRel.getCreateDate());
		commerceProductDefintionOptionValueRelImpl.setModifiedDate(commerceProductDefintionOptionValueRel.getModifiedDate());
		commerceProductDefintionOptionValueRelImpl.setCommerceProductDefintionOptionRelId(commerceProductDefintionOptionValueRel.getCommerceProductDefintionOptionRelId());
		commerceProductDefintionOptionValueRelImpl.setTitle(commerceProductDefintionOptionValueRel.getTitle());
		commerceProductDefintionOptionValueRelImpl.setPriority(commerceProductDefintionOptionValueRel.getPriority());

		return commerceProductDefintionOptionValueRelImpl;
	}

	/**
	 * Returns the commerce product defintion option value rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product defintion option value rel
	 * @return the commerce product defintion option value rel
	 * @throws NoSuchProductDefintionOptionValueRelException if a commerce product defintion option value rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefintionOptionValueRel findByPrimaryKey(
		Serializable primaryKey)
		throws NoSuchProductDefintionOptionValueRelException {
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel =
			fetchByPrimaryKey(primaryKey);

		if (commerceProductDefintionOptionValueRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchProductDefintionOptionValueRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceProductDefintionOptionValueRel;
	}

	/**
	 * Returns the commerce product defintion option value rel with the primary key or throws a {@link NoSuchProductDefintionOptionValueRelException} if it could not be found.
	 *
	 * @param commerceProductDefintionOptionValueRelId the primary key of the commerce product defintion option value rel
	 * @return the commerce product defintion option value rel
	 * @throws NoSuchProductDefintionOptionValueRelException if a commerce product defintion option value rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefintionOptionValueRel findByPrimaryKey(
		long commerceProductDefintionOptionValueRelId)
		throws NoSuchProductDefintionOptionValueRelException {
		return findByPrimaryKey((Serializable)commerceProductDefintionOptionValueRelId);
	}

	/**
	 * Returns the commerce product defintion option value rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product defintion option value rel
	 * @return the commerce product defintion option value rel, or <code>null</code> if a commerce product defintion option value rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefintionOptionValueRel fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductDefintionOptionValueRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel =
			(CommerceProductDefintionOptionValueRel)serializable;

		if (commerceProductDefintionOptionValueRel == null) {
			Session session = null;

			try {
				session = openSession();

				commerceProductDefintionOptionValueRel = (CommerceProductDefintionOptionValueRel)session.get(CommerceProductDefintionOptionValueRelImpl.class,
						primaryKey);

				if (commerceProductDefintionOptionValueRel != null) {
					cacheResult(commerceProductDefintionOptionValueRel);
				}
				else {
					entityCache.putResult(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductDefintionOptionValueRelImpl.class,
						primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefintionOptionValueRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceProductDefintionOptionValueRel;
	}

	/**
	 * Returns the commerce product defintion option value rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceProductDefintionOptionValueRelId the primary key of the commerce product defintion option value rel
	 * @return the commerce product defintion option value rel, or <code>null</code> if a commerce product defintion option value rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefintionOptionValueRel fetchByPrimaryKey(
		long commerceProductDefintionOptionValueRelId) {
		return fetchByPrimaryKey((Serializable)commerceProductDefintionOptionValueRelId);
	}

	@Override
	public Map<Serializable, CommerceProductDefintionOptionValueRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceProductDefintionOptionValueRel> map = new HashMap<Serializable, CommerceProductDefintionOptionValueRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel =
				fetchByPrimaryKey(primaryKey);

			if (commerceProductDefintionOptionValueRel != null) {
				map.put(primaryKey, commerceProductDefintionOptionValueRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefintionOptionValueRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommerceProductDefintionOptionValueRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONVALUEREL_WHERE_PKS_IN);

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

			for (CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel : (List<CommerceProductDefintionOptionValueRel>)q.list()) {
				map.put(commerceProductDefintionOptionValueRel.getPrimaryKeyObj(),
					commerceProductDefintionOptionValueRel);

				cacheResult(commerceProductDefintionOptionValueRel);

				uncachedPrimaryKeys.remove(commerceProductDefintionOptionValueRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceProductDefintionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefintionOptionValueRelImpl.class,
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
	 * Returns all the commerce product defintion option value rels.
	 *
	 * @return the commerce product defintion option value rels
	 */
	@Override
	public List<CommerceProductDefintionOptionValueRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product defintion option value rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product defintion option value rels
	 * @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	 * @return the range of commerce product defintion option value rels
	 */
	@Override
	public List<CommerceProductDefintionOptionValueRel> findAll(int start,
		int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product defintion option value rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product defintion option value rels
	 * @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce product defintion option value rels
	 */
	@Override
	public List<CommerceProductDefintionOptionValueRel> findAll(int start,
		int end,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product defintion option value rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product defintion option value rels
	 * @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce product defintion option value rels
	 */
	@Override
	public List<CommerceProductDefintionOptionValueRel> findAll(int start,
		int end,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator,
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

		List<CommerceProductDefintionOptionValueRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefintionOptionValueRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONVALUEREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONVALUEREL;

				if (pagination) {
					sql = sql.concat(CommerceProductDefintionOptionValueRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceProductDefintionOptionValueRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefintionOptionValueRel>)QueryUtil.list(q,
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
	 * Removes all the commerce product defintion option value rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel : findAll()) {
			remove(commerceProductDefintionOptionValueRel);
		}
	}

	/**
	 * Returns the number of commerce product defintion option value rels.
	 *
	 * @return the number of commerce product defintion option value rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEPRODUCTDEFINTIONOPTIONVALUEREL);

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
		return CommerceProductDefintionOptionValueRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce product defintion option value rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceProductDefintionOptionValueRelImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONVALUEREL =
		"SELECT commerceProductDefintionOptionValueRel FROM CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel";
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONVALUEREL_WHERE_PKS_IN =
		"SELECT commerceProductDefintionOptionValueRel FROM CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel WHERE commerceProductDefintionOptionValueRelId IN (";
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINTIONOPTIONVALUEREL_WHERE =
		"SELECT commerceProductDefintionOptionValueRel FROM CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel WHERE ";
	private static final String _SQL_COUNT_COMMERCEPRODUCTDEFINTIONOPTIONVALUEREL =
		"SELECT COUNT(commerceProductDefintionOptionValueRel) FROM CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel";
	private static final String _SQL_COUNT_COMMERCEPRODUCTDEFINTIONOPTIONVALUEREL_WHERE =
		"SELECT COUNT(commerceProductDefintionOptionValueRel) FROM CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceProductDefintionOptionValueRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceProductDefintionOptionValueRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceProductDefintionOptionValueRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceProductDefintionOptionValueRelPersistenceImpl.class);
}