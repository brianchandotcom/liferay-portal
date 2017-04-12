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

import com.liferay.commerce.product.exception.NoSuchProductDefinitionOptionRelException;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel;
import com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionRelImpl;
import com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionRelModelImpl;
import com.liferay.commerce.product.service.persistence.CommerceProductDefinitionOptionRelPersistence;

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
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce product definition option rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionRelPersistence
 * @see com.liferay.commerce.product.service.persistence.CommerceProductDefinitionOptionRelUtil
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionRelPersistenceImpl
	extends BasePersistenceImpl<CommerceProductDefinitionOptionRel>
	implements CommerceProductDefinitionOptionRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceProductDefinitionOptionRelUtil} to access the commerce product definition option rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceProductDefinitionOptionRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceProductDefinitionOptionRelModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceProductDefinitionOptionRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product definition option rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce product definition option rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionRel> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product definition option rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product definition option rels
	 * @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	 * @return the range of matching commerce product definition option rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionRel> findByGroupId(
		long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product definition option rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product definition option rels
	 * @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product definition option rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product definition option rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product definition option rels
	 * @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product definition option rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator,
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

		List<CommerceProductDefinitionOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefinitionOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel : list) {
					if ((groupId != commerceProductDefinitionOptionRel.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceProductDefinitionOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefinitionOptionRel>)QueryUtil.list(q,
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
	 * Returns the first commerce product definition option rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition option rel
	 * @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel findByGroupId_First(
		long groupId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException {
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceProductDefinitionOptionRel != null) {
			return commerceProductDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the first commerce product definition option rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator) {
		List<CommerceProductDefinitionOptionRel> list = findByGroupId(groupId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product definition option rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition option rel
	 * @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel findByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException {
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceProductDefinitionOptionRel != null) {
			return commerceProductDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the last commerce product definition option rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductDefinitionOptionRel> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product definition option rels before and after the current commerce product definition option rel in the ordered set where groupId = &#63;.
	 *
	 * @param commerceProductDefinitionOptionRelId the primary key of the current commerce product definition option rel
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product definition option rel
	 * @throws NoSuchProductDefinitionOptionRelException if a commerce product definition option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel[] findByGroupId_PrevAndNext(
		long commerceProductDefinitionOptionRelId, long groupId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException {
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel = findByPrimaryKey(commerceProductDefinitionOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductDefinitionOptionRel[] array = new CommerceProductDefinitionOptionRelImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					commerceProductDefinitionOptionRel, groupId,
					orderByComparator, true);

			array[1] = commerceProductDefinitionOptionRel;

			array[2] = getByGroupId_PrevAndNext(session,
					commerceProductDefinitionOptionRel, groupId,
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

	protected CommerceProductDefinitionOptionRel getByGroupId_PrevAndNext(
		Session session,
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel,
		long groupId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONREL_WHERE);

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
			query.append(CommerceProductDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductDefinitionOptionRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductDefinitionOptionRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product definition option rels where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductDefinitionOptionRel);
		}
	}

	/**
	 * Returns the number of commerce product definition option rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce product definition option rels
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTDEFINITIONOPTIONREL_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceProductDefinitionOptionRel.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			CommerceProductDefinitionOptionRelModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceProductDefinitionOptionRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product definition option rels where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce product definition option rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionRel> findByCompanyId(
		long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce product definition option rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product definition option rels
	 * @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	 * @return the range of matching commerce product definition option rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionRel> findByCompanyId(
		long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product definition option rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product definition option rels
	 * @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product definition option rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionRel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product definition option rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product definition option rels
	 * @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product definition option rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionRel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator,
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

		List<CommerceProductDefinitionOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefinitionOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel : list) {
					if ((companyId != commerceProductDefinitionOptionRel.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CommerceProductDefinitionOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefinitionOptionRel>)QueryUtil.list(q,
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
	 * Returns the first commerce product definition option rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition option rel
	 * @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel findByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException {
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (commerceProductDefinitionOptionRel != null) {
			return commerceProductDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the first commerce product definition option rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator) {
		List<CommerceProductDefinitionOptionRel> list = findByCompanyId(companyId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product definition option rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition option rel
	 * @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel findByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException {
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (commerceProductDefinitionOptionRel != null) {
			return commerceProductDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the last commerce product definition option rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductDefinitionOptionRel> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product definition option rels before and after the current commerce product definition option rel in the ordered set where companyId = &#63;.
	 *
	 * @param commerceProductDefinitionOptionRelId the primary key of the current commerce product definition option rel
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product definition option rel
	 * @throws NoSuchProductDefinitionOptionRelException if a commerce product definition option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel[] findByCompanyId_PrevAndNext(
		long commerceProductDefinitionOptionRelId, long companyId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException {
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel = findByPrimaryKey(commerceProductDefinitionOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductDefinitionOptionRel[] array = new CommerceProductDefinitionOptionRelImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					commerceProductDefinitionOptionRel, companyId,
					orderByComparator, true);

			array[1] = commerceProductDefinitionOptionRel;

			array[2] = getByCompanyId_PrevAndNext(session,
					commerceProductDefinitionOptionRel, companyId,
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

	protected CommerceProductDefinitionOptionRel getByCompanyId_PrevAndNext(
		Session session,
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel,
		long companyId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONREL_WHERE);

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
			query.append(CommerceProductDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductDefinitionOptionRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductDefinitionOptionRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product definition option rels where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductDefinitionOptionRel);
		}
	}

	/**
	 * Returns the number of commerce product definition option rels where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce product definition option rels
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTDEFINITIONOPTIONREL_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "commerceProductDefinitionOptionRel.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONID =
		new FinderPath(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceProductDefinitionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONID =
		new FinderPath(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceProductDefinitionId",
			new String[] { Long.class.getName() },
			CommerceProductDefinitionOptionRelModelImpl.COMMERCEPRODUCTDEFINITIONID_COLUMN_BITMASK |
			CommerceProductDefinitionOptionRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEPRODUCTDEFINITIONID =
		new FinderPath(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceProductDefinitionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product definition option rels where commerceProductDefinitionId = &#63;.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @return the matching commerce product definition option rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionRel> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId) {
		return findByCommerceProductDefinitionId(commerceProductDefinitionId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product definition option rels where commerceProductDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param start the lower bound of the range of commerce product definition option rels
	 * @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	 * @return the range of matching commerce product definition option rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionRel> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId, int start, int end) {
		return findByCommerceProductDefinitionId(commerceProductDefinitionId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product definition option rels where commerceProductDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param start the lower bound of the range of commerce product definition option rels
	 * @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product definition option rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionRel> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator) {
		return findByCommerceProductDefinitionId(commerceProductDefinitionId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product definition option rels where commerceProductDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param start the lower bound of the range of commerce product definition option rels
	 * @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product definition option rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionRel> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONID;
			finderArgs = new Object[] { commerceProductDefinitionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONID;
			finderArgs = new Object[] {
					commerceProductDefinitionId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceProductDefinitionOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefinitionOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel : list) {
					if ((commerceProductDefinitionId != commerceProductDefinitionOptionRel.getCommerceProductDefinitionId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEPRODUCTDEFINITIONID_COMMERCEPRODUCTDEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceProductDefinitionId);

				if (!pagination) {
					list = (List<CommerceProductDefinitionOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefinitionOptionRel>)QueryUtil.list(q,
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
	 * Returns the first commerce product definition option rel in the ordered set where commerceProductDefinitionId = &#63;.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition option rel
	 * @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel findByCommerceProductDefinitionId_First(
		long commerceProductDefinitionId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException {
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel = fetchByCommerceProductDefinitionId_First(commerceProductDefinitionId,
				orderByComparator);

		if (commerceProductDefinitionOptionRel != null) {
			return commerceProductDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceProductDefinitionId=");
		msg.append(commerceProductDefinitionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the first commerce product definition option rel in the ordered set where commerceProductDefinitionId = &#63;.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel fetchByCommerceProductDefinitionId_First(
		long commerceProductDefinitionId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator) {
		List<CommerceProductDefinitionOptionRel> list = findByCommerceProductDefinitionId(commerceProductDefinitionId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product definition option rel in the ordered set where commerceProductDefinitionId = &#63;.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition option rel
	 * @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel findByCommerceProductDefinitionId_Last(
		long commerceProductDefinitionId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException {
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel = fetchByCommerceProductDefinitionId_Last(commerceProductDefinitionId,
				orderByComparator);

		if (commerceProductDefinitionOptionRel != null) {
			return commerceProductDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceProductDefinitionId=");
		msg.append(commerceProductDefinitionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the last commerce product definition option rel in the ordered set where commerceProductDefinitionId = &#63;.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel fetchByCommerceProductDefinitionId_Last(
		long commerceProductDefinitionId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator) {
		int count = countByCommerceProductDefinitionId(commerceProductDefinitionId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductDefinitionOptionRel> list = findByCommerceProductDefinitionId(commerceProductDefinitionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product definition option rels before and after the current commerce product definition option rel in the ordered set where commerceProductDefinitionId = &#63;.
	 *
	 * @param commerceProductDefinitionOptionRelId the primary key of the current commerce product definition option rel
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product definition option rel
	 * @throws NoSuchProductDefinitionOptionRelException if a commerce product definition option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel[] findByCommerceProductDefinitionId_PrevAndNext(
		long commerceProductDefinitionOptionRelId,
		long commerceProductDefinitionId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException {
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel = findByPrimaryKey(commerceProductDefinitionOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductDefinitionOptionRel[] array = new CommerceProductDefinitionOptionRelImpl[3];

			array[0] = getByCommerceProductDefinitionId_PrevAndNext(session,
					commerceProductDefinitionOptionRel,
					commerceProductDefinitionId, orderByComparator, true);

			array[1] = commerceProductDefinitionOptionRel;

			array[2] = getByCommerceProductDefinitionId_PrevAndNext(session,
					commerceProductDefinitionOptionRel,
					commerceProductDefinitionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceProductDefinitionOptionRel getByCommerceProductDefinitionId_PrevAndNext(
		Session session,
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel,
		long commerceProductDefinitionId,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONREL_WHERE);

		query.append(_FINDER_COLUMN_COMMERCEPRODUCTDEFINITIONID_COMMERCEPRODUCTDEFINITIONID_2);

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
			query.append(CommerceProductDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceProductDefinitionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductDefinitionOptionRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductDefinitionOptionRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product definition option rels where commerceProductDefinitionId = &#63; from the database.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 */
	@Override
	public void removeByCommerceProductDefinitionId(
		long commerceProductDefinitionId) {
		for (CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel : findByCommerceProductDefinitionId(
				commerceProductDefinitionId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(commerceProductDefinitionOptionRel);
		}
	}

	/**
	 * Returns the number of commerce product definition option rels where commerceProductDefinitionId = &#63;.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @return the number of matching commerce product definition option rels
	 */
	@Override
	public int countByCommerceProductDefinitionId(
		long commerceProductDefinitionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEPRODUCTDEFINITIONID;

		Object[] finderArgs = new Object[] { commerceProductDefinitionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTDEFINITIONOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEPRODUCTDEFINITIONID_COMMERCEPRODUCTDEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceProductDefinitionId);

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

	private static final String _FINDER_COLUMN_COMMERCEPRODUCTDEFINITIONID_COMMERCEPRODUCTDEFINITIONID_2 =
		"commerceProductDefinitionOptionRel.commerceProductDefinitionId = ?";

	public CommerceProductDefinitionOptionRelPersistenceImpl() {
		setModelClass(CommerceProductDefinitionOptionRel.class);

		try {
			Field field = ReflectionUtil.getDeclaredField(BasePersistenceImpl.class,
					"_dbColumnNames");

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("commerceProductDefinitionOptionRelId",
				"definitionOptionRelId");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce product definition option rel in the entity cache if it is enabled.
	 *
	 * @param commerceProductDefinitionOptionRel the commerce product definition option rel
	 */
	@Override
	public void cacheResult(
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel) {
		entityCache.putResult(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelImpl.class,
			commerceProductDefinitionOptionRel.getPrimaryKey(),
			commerceProductDefinitionOptionRel);

		commerceProductDefinitionOptionRel.resetOriginalValues();
	}

	/**
	 * Caches the commerce product definition option rels in the entity cache if it is enabled.
	 *
	 * @param commerceProductDefinitionOptionRels the commerce product definition option rels
	 */
	@Override
	public void cacheResult(
		List<CommerceProductDefinitionOptionRel> commerceProductDefinitionOptionRels) {
		for (CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel : commerceProductDefinitionOptionRels) {
			if (entityCache.getResult(
						CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductDefinitionOptionRelImpl.class,
						commerceProductDefinitionOptionRel.getPrimaryKey()) == null) {
				cacheResult(commerceProductDefinitionOptionRel);
			}
			else {
				commerceProductDefinitionOptionRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce product definition option rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceProductDefinitionOptionRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce product definition option rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel) {
		entityCache.removeResult(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelImpl.class,
			commerceProductDefinitionOptionRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<CommerceProductDefinitionOptionRel> commerceProductDefinitionOptionRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel : commerceProductDefinitionOptionRels) {
			entityCache.removeResult(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductDefinitionOptionRelImpl.class,
				commerceProductDefinitionOptionRel.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce product definition option rel with the primary key. Does not add the commerce product definition option rel to the database.
	 *
	 * @param commerceProductDefinitionOptionRelId the primary key for the new commerce product definition option rel
	 * @return the new commerce product definition option rel
	 */
	@Override
	public CommerceProductDefinitionOptionRel create(
		long commerceProductDefinitionOptionRelId) {
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel = new CommerceProductDefinitionOptionRelImpl();

		commerceProductDefinitionOptionRel.setNew(true);
		commerceProductDefinitionOptionRel.setPrimaryKey(commerceProductDefinitionOptionRelId);

		commerceProductDefinitionOptionRel.setCompanyId(companyProvider.getCompanyId());

		return commerceProductDefinitionOptionRel;
	}

	/**
	 * Removes the commerce product definition option rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceProductDefinitionOptionRelId the primary key of the commerce product definition option rel
	 * @return the commerce product definition option rel that was removed
	 * @throws NoSuchProductDefinitionOptionRelException if a commerce product definition option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel remove(
		long commerceProductDefinitionOptionRelId)
		throws NoSuchProductDefinitionOptionRelException {
		return remove((Serializable)commerceProductDefinitionOptionRelId);
	}

	/**
	 * Removes the commerce product definition option rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce product definition option rel
	 * @return the commerce product definition option rel that was removed
	 * @throws NoSuchProductDefinitionOptionRelException if a commerce product definition option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel remove(Serializable primaryKey)
		throws NoSuchProductDefinitionOptionRelException {
		Session session = null;

		try {
			session = openSession();

			CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel =
				(CommerceProductDefinitionOptionRel)session.get(CommerceProductDefinitionOptionRelImpl.class,
					primaryKey);

			if (commerceProductDefinitionOptionRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProductDefinitionOptionRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceProductDefinitionOptionRel);
		}
		catch (NoSuchProductDefinitionOptionRelException nsee) {
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
	protected CommerceProductDefinitionOptionRel removeImpl(
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel) {
		commerceProductDefinitionOptionRel = toUnwrappedModel(commerceProductDefinitionOptionRel);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceProductDefinitionOptionRel)) {
				commerceProductDefinitionOptionRel = (CommerceProductDefinitionOptionRel)session.get(CommerceProductDefinitionOptionRelImpl.class,
						commerceProductDefinitionOptionRel.getPrimaryKeyObj());
			}

			if (commerceProductDefinitionOptionRel != null) {
				session.delete(commerceProductDefinitionOptionRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceProductDefinitionOptionRel != null) {
			clearCache(commerceProductDefinitionOptionRel);
		}

		return commerceProductDefinitionOptionRel;
	}

	@Override
	public CommerceProductDefinitionOptionRel updateImpl(
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel) {
		commerceProductDefinitionOptionRel = toUnwrappedModel(commerceProductDefinitionOptionRel);

		boolean isNew = commerceProductDefinitionOptionRel.isNew();

		CommerceProductDefinitionOptionRelModelImpl commerceProductDefinitionOptionRelModelImpl =
			(CommerceProductDefinitionOptionRelModelImpl)commerceProductDefinitionOptionRel;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew &&
				(commerceProductDefinitionOptionRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceProductDefinitionOptionRel.setCreateDate(now);
			}
			else {
				commerceProductDefinitionOptionRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceProductDefinitionOptionRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceProductDefinitionOptionRel.setModifiedDate(now);
			}
			else {
				commerceProductDefinitionOptionRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceProductDefinitionOptionRel.isNew()) {
				session.save(commerceProductDefinitionOptionRel);

				commerceProductDefinitionOptionRel.setNew(false);
			}
			else {
				commerceProductDefinitionOptionRel = (CommerceProductDefinitionOptionRel)session.merge(commerceProductDefinitionOptionRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceProductDefinitionOptionRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceProductDefinitionOptionRelModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					commerceProductDefinitionOptionRelModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] {
					commerceProductDefinitionOptionRelModelImpl.getCommerceProductDefinitionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRODUCTDEFINITIONID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceProductDefinitionOptionRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductDefinitionOptionRelModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						commerceProductDefinitionOptionRelModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceProductDefinitionOptionRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductDefinitionOptionRelModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						commerceProductDefinitionOptionRelModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((commerceProductDefinitionOptionRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductDefinitionOptionRelModelImpl.getOriginalCommerceProductDefinitionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRODUCTDEFINITIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONID,
					args);

				args = new Object[] {
						commerceProductDefinitionOptionRelModelImpl.getCommerceProductDefinitionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRODUCTDEFINITIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONID,
					args);
			}
		}

		entityCache.putResult(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionRelImpl.class,
			commerceProductDefinitionOptionRel.getPrimaryKey(),
			commerceProductDefinitionOptionRel, false);

		commerceProductDefinitionOptionRel.resetOriginalValues();

		return commerceProductDefinitionOptionRel;
	}

	protected CommerceProductDefinitionOptionRel toUnwrappedModel(
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel) {
		if (commerceProductDefinitionOptionRel instanceof CommerceProductDefinitionOptionRelImpl) {
			return commerceProductDefinitionOptionRel;
		}

		CommerceProductDefinitionOptionRelImpl commerceProductDefinitionOptionRelImpl =
			new CommerceProductDefinitionOptionRelImpl();

		commerceProductDefinitionOptionRelImpl.setNew(commerceProductDefinitionOptionRel.isNew());
		commerceProductDefinitionOptionRelImpl.setPrimaryKey(commerceProductDefinitionOptionRel.getPrimaryKey());

		commerceProductDefinitionOptionRelImpl.setCommerceProductDefinitionOptionRelId(commerceProductDefinitionOptionRel.getCommerceProductDefinitionOptionRelId());
		commerceProductDefinitionOptionRelImpl.setGroupId(commerceProductDefinitionOptionRel.getGroupId());
		commerceProductDefinitionOptionRelImpl.setCompanyId(commerceProductDefinitionOptionRel.getCompanyId());
		commerceProductDefinitionOptionRelImpl.setUserId(commerceProductDefinitionOptionRel.getUserId());
		commerceProductDefinitionOptionRelImpl.setUserName(commerceProductDefinitionOptionRel.getUserName());
		commerceProductDefinitionOptionRelImpl.setCreateDate(commerceProductDefinitionOptionRel.getCreateDate());
		commerceProductDefinitionOptionRelImpl.setModifiedDate(commerceProductDefinitionOptionRel.getModifiedDate());
		commerceProductDefinitionOptionRelImpl.setCommerceProductDefinitionId(commerceProductDefinitionOptionRel.getCommerceProductDefinitionId());
		commerceProductDefinitionOptionRelImpl.setCommerceProductOptionId(commerceProductDefinitionOptionRel.getCommerceProductOptionId());
		commerceProductDefinitionOptionRelImpl.setName(commerceProductDefinitionOptionRel.getName());
		commerceProductDefinitionOptionRelImpl.setDescription(commerceProductDefinitionOptionRel.getDescription());
		commerceProductDefinitionOptionRelImpl.setDDMFormFieldTypeName(commerceProductDefinitionOptionRel.getDDMFormFieldTypeName());
		commerceProductDefinitionOptionRelImpl.setPriority(commerceProductDefinitionOptionRel.getPriority());

		return commerceProductDefinitionOptionRelImpl;
	}

	/**
	 * Returns the commerce product definition option rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product definition option rel
	 * @return the commerce product definition option rel
	 * @throws NoSuchProductDefinitionOptionRelException if a commerce product definition option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel findByPrimaryKey(
		Serializable primaryKey)
		throws NoSuchProductDefinitionOptionRelException {
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel = fetchByPrimaryKey(primaryKey);

		if (commerceProductDefinitionOptionRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchProductDefinitionOptionRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceProductDefinitionOptionRel;
	}

	/**
	 * Returns the commerce product definition option rel with the primary key or throws a {@link NoSuchProductDefinitionOptionRelException} if it could not be found.
	 *
	 * @param commerceProductDefinitionOptionRelId the primary key of the commerce product definition option rel
	 * @return the commerce product definition option rel
	 * @throws NoSuchProductDefinitionOptionRelException if a commerce product definition option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel findByPrimaryKey(
		long commerceProductDefinitionOptionRelId)
		throws NoSuchProductDefinitionOptionRelException {
		return findByPrimaryKey((Serializable)commerceProductDefinitionOptionRelId);
	}

	/**
	 * Returns the commerce product definition option rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product definition option rel
	 * @return the commerce product definition option rel, or <code>null</code> if a commerce product definition option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductDefinitionOptionRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel = (CommerceProductDefinitionOptionRel)serializable;

		if (commerceProductDefinitionOptionRel == null) {
			Session session = null;

			try {
				session = openSession();

				commerceProductDefinitionOptionRel = (CommerceProductDefinitionOptionRel)session.get(CommerceProductDefinitionOptionRelImpl.class,
						primaryKey);

				if (commerceProductDefinitionOptionRel != null) {
					cacheResult(commerceProductDefinitionOptionRel);
				}
				else {
					entityCache.putResult(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductDefinitionOptionRelImpl.class,
						primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefinitionOptionRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceProductDefinitionOptionRel;
	}

	/**
	 * Returns the commerce product definition option rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceProductDefinitionOptionRelId the primary key of the commerce product definition option rel
	 * @return the commerce product definition option rel, or <code>null</code> if a commerce product definition option rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionRel fetchByPrimaryKey(
		long commerceProductDefinitionOptionRelId) {
		return fetchByPrimaryKey((Serializable)commerceProductDefinitionOptionRelId);
	}

	@Override
	public Map<Serializable, CommerceProductDefinitionOptionRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceProductDefinitionOptionRel> map = new HashMap<Serializable, CommerceProductDefinitionOptionRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel =
				fetchByPrimaryKey(primaryKey);

			if (commerceProductDefinitionOptionRel != null) {
				map.put(primaryKey, commerceProductDefinitionOptionRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefinitionOptionRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommerceProductDefinitionOptionRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONREL_WHERE_PKS_IN);

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

			for (CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel : (List<CommerceProductDefinitionOptionRel>)q.list()) {
				map.put(commerceProductDefinitionOptionRel.getPrimaryKeyObj(),
					commerceProductDefinitionOptionRel);

				cacheResult(commerceProductDefinitionOptionRel);

				uncachedPrimaryKeys.remove(commerceProductDefinitionOptionRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceProductDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefinitionOptionRelImpl.class, primaryKey,
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
	 * Returns all the commerce product definition option rels.
	 *
	 * @return the commerce product definition option rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product definition option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product definition option rels
	 * @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	 * @return the range of commerce product definition option rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product definition option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product definition option rels
	 * @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce product definition option rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionRel> findAll(int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product definition option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product definition option rels
	 * @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce product definition option rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionRel> findAll(int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator,
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

		List<CommerceProductDefinitionOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefinitionOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONREL;

				if (pagination) {
					sql = sql.concat(CommerceProductDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceProductDefinitionOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefinitionOptionRel>)QueryUtil.list(q,
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
	 * Removes all the commerce product definition option rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel : findAll()) {
			remove(commerceProductDefinitionOptionRel);
		}
	}

	/**
	 * Returns the number of commerce product definition option rels.
	 *
	 * @return the number of commerce product definition option rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEPRODUCTDEFINITIONOPTIONREL);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceProductDefinitionOptionRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce product definition option rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceProductDefinitionOptionRelImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONREL = "SELECT commerceProductDefinitionOptionRel FROM CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel";
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONREL_WHERE_PKS_IN =
		"SELECT commerceProductDefinitionOptionRel FROM CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel WHERE definitionOptionRelId IN (";
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONREL_WHERE =
		"SELECT commerceProductDefinitionOptionRel FROM CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel WHERE ";
	private static final String _SQL_COUNT_COMMERCEPRODUCTDEFINITIONOPTIONREL = "SELECT COUNT(commerceProductDefinitionOptionRel) FROM CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel";
	private static final String _SQL_COUNT_COMMERCEPRODUCTDEFINITIONOPTIONREL_WHERE =
		"SELECT COUNT(commerceProductDefinitionOptionRel) FROM CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceProductDefinitionOptionRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceProductDefinitionOptionRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceProductDefinitionOptionRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceProductDefinitionOptionRelPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"commerceProductDefinitionOptionRelId"
			});
}