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

import com.liferay.commerce.product.exception.NoSuchProductDefinitionOptionValueRelException;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel;
import com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionValueRelImpl;
import com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionValueRelModelImpl;
import com.liferay.commerce.product.service.persistence.CommerceProductDefinitionOptionValueRelPersistence;

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
 * The persistence implementation for the commerce product definition option value rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionValueRelPersistence
 * @see com.liferay.commerce.product.service.persistence.CommerceProductDefinitionOptionValueRelUtil
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionValueRelPersistenceImpl
	extends BasePersistenceImpl<CommerceProductDefinitionOptionValueRel>
	implements CommerceProductDefinitionOptionValueRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceProductDefinitionOptionValueRelUtil} to access the commerce product definition option value rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceProductDefinitionOptionValueRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceProductDefinitionOptionValueRelModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceProductDefinitionOptionValueRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product definition option value rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce product definition option value rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionValueRel> findByGroupId(
		long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product definition option value rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product definition option value rels
	 * @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	 * @return the range of matching commerce product definition option value rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionValueRel> findByGroupId(
		long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product definition option value rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product definition option value rels
	 * @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product definition option value rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionValueRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product definition option value rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product definition option value rels
	 * @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product definition option value rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionValueRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator,
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

		List<CommerceProductDefinitionOptionValueRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefinitionOptionValueRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel : list) {
					if ((groupId != commerceProductDefinitionOptionValueRel.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONVALUEREL_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceProductDefinitionOptionValueRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefinitionOptionValueRel>)QueryUtil.list(q,
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
	 * Returns the first commerce product definition option value rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition option value rel
	 * @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel findByGroupId_First(
		long groupId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException {
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel =
			fetchByGroupId_First(groupId, orderByComparator);

		if (commerceProductDefinitionOptionValueRel != null) {
			return commerceProductDefinitionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the first commerce product definition option value rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator) {
		List<CommerceProductDefinitionOptionValueRel> list = findByGroupId(groupId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product definition option value rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition option value rel
	 * @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel findByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException {
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel =
			fetchByGroupId_Last(groupId, orderByComparator);

		if (commerceProductDefinitionOptionValueRel != null) {
			return commerceProductDefinitionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the last commerce product definition option value rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductDefinitionOptionValueRel> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product definition option value rels before and after the current commerce product definition option value rel in the ordered set where groupId = &#63;.
	 *
	 * @param commerceProductDefinitionOptionValueRelId the primary key of the current commerce product definition option value rel
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product definition option value rel
	 * @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel[] findByGroupId_PrevAndNext(
		long commerceProductDefinitionOptionValueRelId, long groupId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException {
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel =
			findByPrimaryKey(commerceProductDefinitionOptionValueRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductDefinitionOptionValueRel[] array = new CommerceProductDefinitionOptionValueRelImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					commerceProductDefinitionOptionValueRel, groupId,
					orderByComparator, true);

			array[1] = commerceProductDefinitionOptionValueRel;

			array[2] = getByGroupId_PrevAndNext(session,
					commerceProductDefinitionOptionValueRel, groupId,
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

	protected CommerceProductDefinitionOptionValueRel getByGroupId_PrevAndNext(
		Session session,
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel,
		long groupId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONVALUEREL_WHERE);

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
			query.append(CommerceProductDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductDefinitionOptionValueRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductDefinitionOptionValueRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product definition option value rels where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductDefinitionOptionValueRel);
		}
	}

	/**
	 * Returns the number of commerce product definition option value rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce product definition option value rels
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTDEFINITIONOPTIONVALUEREL_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceProductDefinitionOptionValueRel.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			CommerceProductDefinitionOptionValueRelModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceProductDefinitionOptionValueRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product definition option value rels where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce product definition option value rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionValueRel> findByCompanyId(
		long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce product definition option value rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product definition option value rels
	 * @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	 * @return the range of matching commerce product definition option value rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionValueRel> findByCompanyId(
		long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product definition option value rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product definition option value rels
	 * @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product definition option value rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionValueRel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product definition option value rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product definition option value rels
	 * @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product definition option value rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionValueRel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator,
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

		List<CommerceProductDefinitionOptionValueRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefinitionOptionValueRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel : list) {
					if ((companyId != commerceProductDefinitionOptionValueRel.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONVALUEREL_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CommerceProductDefinitionOptionValueRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefinitionOptionValueRel>)QueryUtil.list(q,
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
	 * Returns the first commerce product definition option value rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition option value rel
	 * @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel findByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException {
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel =
			fetchByCompanyId_First(companyId, orderByComparator);

		if (commerceProductDefinitionOptionValueRel != null) {
			return commerceProductDefinitionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the first commerce product definition option value rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator) {
		List<CommerceProductDefinitionOptionValueRel> list = findByCompanyId(companyId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product definition option value rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition option value rel
	 * @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel findByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException {
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel =
			fetchByCompanyId_Last(companyId, orderByComparator);

		if (commerceProductDefinitionOptionValueRel != null) {
			return commerceProductDefinitionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the last commerce product definition option value rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductDefinitionOptionValueRel> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product definition option value rels before and after the current commerce product definition option value rel in the ordered set where companyId = &#63;.
	 *
	 * @param commerceProductDefinitionOptionValueRelId the primary key of the current commerce product definition option value rel
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product definition option value rel
	 * @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel[] findByCompanyId_PrevAndNext(
		long commerceProductDefinitionOptionValueRelId, long companyId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException {
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel =
			findByPrimaryKey(commerceProductDefinitionOptionValueRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductDefinitionOptionValueRel[] array = new CommerceProductDefinitionOptionValueRelImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					commerceProductDefinitionOptionValueRel, companyId,
					orderByComparator, true);

			array[1] = commerceProductDefinitionOptionValueRel;

			array[2] = getByCompanyId_PrevAndNext(session,
					commerceProductDefinitionOptionValueRel, companyId,
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

	protected CommerceProductDefinitionOptionValueRel getByCompanyId_PrevAndNext(
		Session session,
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel,
		long companyId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONVALUEREL_WHERE);

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
			query.append(CommerceProductDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductDefinitionOptionValueRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductDefinitionOptionValueRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product definition option value rels where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductDefinitionOptionValueRel);
		}
	}

	/**
	 * Returns the number of commerce product definition option value rels where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce product definition option value rels
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTDEFINITIONOPTIONVALUEREL_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "commerceProductDefinitionOptionValueRel.companyId = ?";

	public CommerceProductDefinitionOptionValueRelPersistenceImpl() {
		setModelClass(CommerceProductDefinitionOptionValueRel.class);
	}

	/**
	 * Caches the commerce product definition option value rel in the entity cache if it is enabled.
	 *
	 * @param commerceProductDefinitionOptionValueRel the commerce product definition option value rel
	 */
	@Override
	public void cacheResult(
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel) {
		entityCache.putResult(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelImpl.class,
			commerceProductDefinitionOptionValueRel.getPrimaryKey(),
			commerceProductDefinitionOptionValueRel);

		commerceProductDefinitionOptionValueRel.resetOriginalValues();
	}

	/**
	 * Caches the commerce product definition option value rels in the entity cache if it is enabled.
	 *
	 * @param commerceProductDefinitionOptionValueRels the commerce product definition option value rels
	 */
	@Override
	public void cacheResult(
		List<CommerceProductDefinitionOptionValueRel> commerceProductDefinitionOptionValueRels) {
		for (CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel : commerceProductDefinitionOptionValueRels) {
			if (entityCache.getResult(
						CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductDefinitionOptionValueRelImpl.class,
						commerceProductDefinitionOptionValueRel.getPrimaryKey()) == null) {
				cacheResult(commerceProductDefinitionOptionValueRel);
			}
			else {
				commerceProductDefinitionOptionValueRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce product definition option value rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceProductDefinitionOptionValueRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce product definition option value rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel) {
		entityCache.removeResult(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelImpl.class,
			commerceProductDefinitionOptionValueRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<CommerceProductDefinitionOptionValueRel> commerceProductDefinitionOptionValueRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel : commerceProductDefinitionOptionValueRels) {
			entityCache.removeResult(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductDefinitionOptionValueRelImpl.class,
				commerceProductDefinitionOptionValueRel.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce product definition option value rel with the primary key. Does not add the commerce product definition option value rel to the database.
	 *
	 * @param commerceProductDefinitionOptionValueRelId the primary key for the new commerce product definition option value rel
	 * @return the new commerce product definition option value rel
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel create(
		long commerceProductDefinitionOptionValueRelId) {
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel =
			new CommerceProductDefinitionOptionValueRelImpl();

		commerceProductDefinitionOptionValueRel.setNew(true);
		commerceProductDefinitionOptionValueRel.setPrimaryKey(commerceProductDefinitionOptionValueRelId);

		commerceProductDefinitionOptionValueRel.setCompanyId(companyProvider.getCompanyId());

		return commerceProductDefinitionOptionValueRel;
	}

	/**
	 * Removes the commerce product definition option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceProductDefinitionOptionValueRelId the primary key of the commerce product definition option value rel
	 * @return the commerce product definition option value rel that was removed
	 * @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel remove(
		long commerceProductDefinitionOptionValueRelId)
		throws NoSuchProductDefinitionOptionValueRelException {
		return remove((Serializable)commerceProductDefinitionOptionValueRelId);
	}

	/**
	 * Removes the commerce product definition option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce product definition option value rel
	 * @return the commerce product definition option value rel that was removed
	 * @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel remove(
		Serializable primaryKey)
		throws NoSuchProductDefinitionOptionValueRelException {
		Session session = null;

		try {
			session = openSession();

			CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel =
				(CommerceProductDefinitionOptionValueRel)session.get(CommerceProductDefinitionOptionValueRelImpl.class,
					primaryKey);

			if (commerceProductDefinitionOptionValueRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProductDefinitionOptionValueRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceProductDefinitionOptionValueRel);
		}
		catch (NoSuchProductDefinitionOptionValueRelException nsee) {
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
	protected CommerceProductDefinitionOptionValueRel removeImpl(
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel) {
		commerceProductDefinitionOptionValueRel = toUnwrappedModel(commerceProductDefinitionOptionValueRel);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceProductDefinitionOptionValueRel)) {
				commerceProductDefinitionOptionValueRel = (CommerceProductDefinitionOptionValueRel)session.get(CommerceProductDefinitionOptionValueRelImpl.class,
						commerceProductDefinitionOptionValueRel.getPrimaryKeyObj());
			}

			if (commerceProductDefinitionOptionValueRel != null) {
				session.delete(commerceProductDefinitionOptionValueRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceProductDefinitionOptionValueRel != null) {
			clearCache(commerceProductDefinitionOptionValueRel);
		}

		return commerceProductDefinitionOptionValueRel;
	}

	@Override
	public CommerceProductDefinitionOptionValueRel updateImpl(
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel) {
		commerceProductDefinitionOptionValueRel = toUnwrappedModel(commerceProductDefinitionOptionValueRel);

		boolean isNew = commerceProductDefinitionOptionValueRel.isNew();

		CommerceProductDefinitionOptionValueRelModelImpl commerceProductDefinitionOptionValueRelModelImpl =
			(CommerceProductDefinitionOptionValueRelModelImpl)commerceProductDefinitionOptionValueRel;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew &&
				(commerceProductDefinitionOptionValueRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceProductDefinitionOptionValueRel.setCreateDate(now);
			}
			else {
				commerceProductDefinitionOptionValueRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceProductDefinitionOptionValueRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceProductDefinitionOptionValueRel.setModifiedDate(now);
			}
			else {
				commerceProductDefinitionOptionValueRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceProductDefinitionOptionValueRel.isNew()) {
				session.save(commerceProductDefinitionOptionValueRel);

				commerceProductDefinitionOptionValueRel.setNew(false);
			}
			else {
				commerceProductDefinitionOptionValueRel = (CommerceProductDefinitionOptionValueRel)session.merge(commerceProductDefinitionOptionValueRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceProductDefinitionOptionValueRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceProductDefinitionOptionValueRelModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					commerceProductDefinitionOptionValueRelModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceProductDefinitionOptionValueRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductDefinitionOptionValueRelModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						commerceProductDefinitionOptionValueRelModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceProductDefinitionOptionValueRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductDefinitionOptionValueRelModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						commerceProductDefinitionOptionValueRelModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		entityCache.putResult(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionOptionValueRelImpl.class,
			commerceProductDefinitionOptionValueRel.getPrimaryKey(),
			commerceProductDefinitionOptionValueRel, false);

		commerceProductDefinitionOptionValueRel.resetOriginalValues();

		return commerceProductDefinitionOptionValueRel;
	}

	protected CommerceProductDefinitionOptionValueRel toUnwrappedModel(
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel) {
		if (commerceProductDefinitionOptionValueRel instanceof CommerceProductDefinitionOptionValueRelImpl) {
			return commerceProductDefinitionOptionValueRel;
		}

		CommerceProductDefinitionOptionValueRelImpl commerceProductDefinitionOptionValueRelImpl =
			new CommerceProductDefinitionOptionValueRelImpl();

		commerceProductDefinitionOptionValueRelImpl.setNew(commerceProductDefinitionOptionValueRel.isNew());
		commerceProductDefinitionOptionValueRelImpl.setPrimaryKey(commerceProductDefinitionOptionValueRel.getPrimaryKey());

		commerceProductDefinitionOptionValueRelImpl.setCommerceProductDefinitionOptionValueRelId(commerceProductDefinitionOptionValueRel.getCommerceProductDefinitionOptionValueRelId());
		commerceProductDefinitionOptionValueRelImpl.setGroupId(commerceProductDefinitionOptionValueRel.getGroupId());
		commerceProductDefinitionOptionValueRelImpl.setCompanyId(commerceProductDefinitionOptionValueRel.getCompanyId());
		commerceProductDefinitionOptionValueRelImpl.setUserId(commerceProductDefinitionOptionValueRel.getUserId());
		commerceProductDefinitionOptionValueRelImpl.setUserName(commerceProductDefinitionOptionValueRel.getUserName());
		commerceProductDefinitionOptionValueRelImpl.setCreateDate(commerceProductDefinitionOptionValueRel.getCreateDate());
		commerceProductDefinitionOptionValueRelImpl.setModifiedDate(commerceProductDefinitionOptionValueRel.getModifiedDate());
		commerceProductDefinitionOptionValueRelImpl.setCommerceProductDefinitionOptionRelId(commerceProductDefinitionOptionValueRel.getCommerceProductDefinitionOptionRelId());
		commerceProductDefinitionOptionValueRelImpl.setTitle(commerceProductDefinitionOptionValueRel.getTitle());
		commerceProductDefinitionOptionValueRelImpl.setPriority(commerceProductDefinitionOptionValueRel.getPriority());

		return commerceProductDefinitionOptionValueRelImpl;
	}

	/**
	 * Returns the commerce product definition option value rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product definition option value rel
	 * @return the commerce product definition option value rel
	 * @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel findByPrimaryKey(
		Serializable primaryKey)
		throws NoSuchProductDefinitionOptionValueRelException {
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel =
			fetchByPrimaryKey(primaryKey);

		if (commerceProductDefinitionOptionValueRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchProductDefinitionOptionValueRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceProductDefinitionOptionValueRel;
	}

	/**
	 * Returns the commerce product definition option value rel with the primary key or throws a {@link NoSuchProductDefinitionOptionValueRelException} if it could not be found.
	 *
	 * @param commerceProductDefinitionOptionValueRelId the primary key of the commerce product definition option value rel
	 * @return the commerce product definition option value rel
	 * @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel findByPrimaryKey(
		long commerceProductDefinitionOptionValueRelId)
		throws NoSuchProductDefinitionOptionValueRelException {
		return findByPrimaryKey((Serializable)commerceProductDefinitionOptionValueRelId);
	}

	/**
	 * Returns the commerce product definition option value rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product definition option value rel
	 * @return the commerce product definition option value rel, or <code>null</code> if a commerce product definition option value rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductDefinitionOptionValueRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel =
			(CommerceProductDefinitionOptionValueRel)serializable;

		if (commerceProductDefinitionOptionValueRel == null) {
			Session session = null;

			try {
				session = openSession();

				commerceProductDefinitionOptionValueRel = (CommerceProductDefinitionOptionValueRel)session.get(CommerceProductDefinitionOptionValueRelImpl.class,
						primaryKey);

				if (commerceProductDefinitionOptionValueRel != null) {
					cacheResult(commerceProductDefinitionOptionValueRel);
				}
				else {
					entityCache.putResult(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductDefinitionOptionValueRelImpl.class,
						primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefinitionOptionValueRelImpl.class,
					primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceProductDefinitionOptionValueRel;
	}

	/**
	 * Returns the commerce product definition option value rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceProductDefinitionOptionValueRelId the primary key of the commerce product definition option value rel
	 * @return the commerce product definition option value rel, or <code>null</code> if a commerce product definition option value rel with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinitionOptionValueRel fetchByPrimaryKey(
		long commerceProductDefinitionOptionValueRelId) {
		return fetchByPrimaryKey((Serializable)commerceProductDefinitionOptionValueRelId);
	}

	@Override
	public Map<Serializable, CommerceProductDefinitionOptionValueRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceProductDefinitionOptionValueRel> map = new HashMap<Serializable, CommerceProductDefinitionOptionValueRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel =
				fetchByPrimaryKey(primaryKey);

			if (commerceProductDefinitionOptionValueRel != null) {
				map.put(primaryKey, commerceProductDefinitionOptionValueRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefinitionOptionValueRelImpl.class,
					primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommerceProductDefinitionOptionValueRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONVALUEREL_WHERE_PKS_IN);

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

			for (CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel : (List<CommerceProductDefinitionOptionValueRel>)q.list()) {
				map.put(commerceProductDefinitionOptionValueRel.getPrimaryKeyObj(),
					commerceProductDefinitionOptionValueRel);

				cacheResult(commerceProductDefinitionOptionValueRel);

				uncachedPrimaryKeys.remove(commerceProductDefinitionOptionValueRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceProductDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefinitionOptionValueRelImpl.class,
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
	 * Returns all the commerce product definition option value rels.
	 *
	 * @return the commerce product definition option value rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionValueRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product definition option value rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product definition option value rels
	 * @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	 * @return the range of commerce product definition option value rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionValueRel> findAll(int start,
		int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product definition option value rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product definition option value rels
	 * @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce product definition option value rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionValueRel> findAll(int start,
		int end,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product definition option value rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product definition option value rels
	 * @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce product definition option value rels
	 */
	@Override
	public List<CommerceProductDefinitionOptionValueRel> findAll(int start,
		int end,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator,
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

		List<CommerceProductDefinitionOptionValueRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefinitionOptionValueRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONVALUEREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONVALUEREL;

				if (pagination) {
					sql = sql.concat(CommerceProductDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceProductDefinitionOptionValueRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefinitionOptionValueRel>)QueryUtil.list(q,
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
	 * Removes all the commerce product definition option value rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel : findAll()) {
			remove(commerceProductDefinitionOptionValueRel);
		}
	}

	/**
	 * Returns the number of commerce product definition option value rels.
	 *
	 * @return the number of commerce product definition option value rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEPRODUCTDEFINITIONOPTIONVALUEREL);

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
		return CommerceProductDefinitionOptionValueRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce product definition option value rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceProductDefinitionOptionValueRelImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONVALUEREL =
		"SELECT commerceProductDefinitionOptionValueRel FROM CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel";
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONVALUEREL_WHERE_PKS_IN =
		"SELECT commerceProductDefinitionOptionValueRel FROM CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel WHERE commerceProductDefinitionOptionValueRelId IN (";
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINITIONOPTIONVALUEREL_WHERE =
		"SELECT commerceProductDefinitionOptionValueRel FROM CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel WHERE ";
	private static final String _SQL_COUNT_COMMERCEPRODUCTDEFINITIONOPTIONVALUEREL =
		"SELECT COUNT(commerceProductDefinitionOptionValueRel) FROM CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel";
	private static final String _SQL_COUNT_COMMERCEPRODUCTDEFINITIONOPTIONVALUEREL_WHERE =
		"SELECT COUNT(commerceProductDefinitionOptionValueRel) FROM CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceProductDefinitionOptionValueRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceProductDefinitionOptionValueRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceProductDefinitionOptionValueRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceProductDefinitionOptionValueRelPersistenceImpl.class);
}