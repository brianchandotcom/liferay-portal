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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.NoSuchResourceBlockException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.model.impl.ResourceBlockImpl;
import com.liferay.portal.model.impl.ResourceBlockModelImpl;
import com.liferay.portal.service.persistence.ResourceBlockPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the resource block service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockPersistence
 * @see ResourceBlockUtil
 * @generated
 */
public class ResourceBlockPersistenceImpl extends BasePersistenceImpl<ResourceBlock>
	implements ResourceBlockPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ResourceBlockUtil} to access the resource block persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ResourceBlockImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockModelImpl.FINDER_CACHE_ENABLED,
			ResourceBlockImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockModelImpl.FINDER_CACHE_ENABLED,
			ResourceBlockImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_N = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockModelImpl.FINDER_CACHE_ENABLED,
			ResourceBlockImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockModelImpl.FINDER_CACHE_ENABLED,
			ResourceBlockImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_N",
			new String[] { Long.class.getName(), String.class.getName() },
			ResourceBlockModelImpl.COMPANYID_COLUMN_BITMASK |
			ResourceBlockModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the resource blocks where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching resource blocks
	 */
	@Override
	public List<ResourceBlock> findByC_N(long companyId, String name) {
		return findByC_N(companyId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the resource blocks where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of resource blocks
	 * @param end the upper bound of the range of resource blocks (not inclusive)
	 * @return the range of matching resource blocks
	 */
	@Override
	public List<ResourceBlock> findByC_N(long companyId, String name,
		int start, int end) {
		return findByC_N(companyId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource blocks where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of resource blocks
	 * @param end the upper bound of the range of resource blocks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource blocks
	 */
	@Override
	public List<ResourceBlock> findByC_N(long companyId, String name,
		int start, int end, OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N;
			finderArgs = new Object[] { companyId, name };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_N;
			finderArgs = new Object[] {
					companyId, name,
					
					start, end, orderByComparator
				};
		}

		List<ResourceBlock> list = (List<ResourceBlock>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ResourceBlock resourceBlock : list) {
				if ((companyId != resourceBlock.getCompanyId()) ||
						!Validator.equals(name, resourceBlock.getName())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_RESOURCEBLOCK_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ResourceBlockModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				if (!pagination) {
					list = (List<ResourceBlock>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ResourceBlock>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first resource block in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource block
	 * @throws com.liferay.portal.NoSuchResourceBlockException if a matching resource block could not be found
	 */
	@Override
	public ResourceBlock findByC_N_First(long companyId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchResourceBlockException {
		ResourceBlock resourceBlock = fetchByC_N_First(companyId, name,
				orderByComparator);

		if (resourceBlock != null) {
			return resourceBlock;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchResourceBlockException(msg.toString());
	}

	/**
	 * Returns the first resource block in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource block, or <code>null</code> if a matching resource block could not be found
	 */
	@Override
	public ResourceBlock fetchByC_N_First(long companyId, String name,
		OrderByComparator orderByComparator) {
		List<ResourceBlock> list = findByC_N(companyId, name, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last resource block in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource block
	 * @throws com.liferay.portal.NoSuchResourceBlockException if a matching resource block could not be found
	 */
	@Override
	public ResourceBlock findByC_N_Last(long companyId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchResourceBlockException {
		ResourceBlock resourceBlock = fetchByC_N_Last(companyId, name,
				orderByComparator);

		if (resourceBlock != null) {
			return resourceBlock;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchResourceBlockException(msg.toString());
	}

	/**
	 * Returns the last resource block in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource block, or <code>null</code> if a matching resource block could not be found
	 */
	@Override
	public ResourceBlock fetchByC_N_Last(long companyId, String name,
		OrderByComparator orderByComparator) {
		int count = countByC_N(companyId, name);

		if (count == 0) {
			return null;
		}

		List<ResourceBlock> list = findByC_N(companyId, name, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the resource blocks before and after the current resource block in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param resourceBlockId the primary key of the current resource block
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource block
	 * @throws com.liferay.portal.NoSuchResourceBlockException if a resource block with the primary key could not be found
	 */
	@Override
	public ResourceBlock[] findByC_N_PrevAndNext(long resourceBlockId,
		long companyId, String name, OrderByComparator orderByComparator)
		throws NoSuchResourceBlockException {
		ResourceBlock resourceBlock = findByPrimaryKey(resourceBlockId);

		Session session = null;

		try {
			session = openSession();

			ResourceBlock[] array = new ResourceBlockImpl[3];

			array[0] = getByC_N_PrevAndNext(session, resourceBlock, companyId,
					name, orderByComparator, true);

			array[1] = resourceBlock;

			array[2] = getByC_N_PrevAndNext(session, resourceBlock, companyId,
					name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourceBlock getByC_N_PrevAndNext(Session session,
		ResourceBlock resourceBlock, long companyId, String name,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RESOURCEBLOCK_WHERE);

		query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_C_N_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_C_N_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_C_N_NAME_2);
		}

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
			query.append(ResourceBlockModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindName) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(resourceBlock);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ResourceBlock> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the resource blocks where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	@Override
	public void removeByC_N(long companyId, String name) {
		for (ResourceBlock resourceBlock : findByC_N(companyId, name,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(resourceBlock);
		}
	}

	/**
	 * Returns the number of resource blocks where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching resource blocks
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_N;

		Object[] finderArgs = new Object[] { companyId, name };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_RESOURCEBLOCK_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "resourceBlock.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "resourceBlock.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "resourceBlock.name = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(resourceBlock.name IS NULL OR resourceBlock.name = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_G_N = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockModelImpl.FINDER_CACHE_ENABLED,
			ResourceBlockImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_G_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_G_N = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockModelImpl.FINDER_CACHE_ENABLED,
			ResourceBlockImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_G_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			ResourceBlockModelImpl.COMPANYID_COLUMN_BITMASK |
			ResourceBlockModelImpl.GROUPID_COLUMN_BITMASK |
			ResourceBlockModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_G_N = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_G_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching resource blocks
	 */
	@Override
	public List<ResourceBlock> findByC_G_N(long companyId, long groupId,
		String name) {
		return findByC_G_N(companyId, groupId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of resource blocks
	 * @param end the upper bound of the range of resource blocks (not inclusive)
	 * @return the range of matching resource blocks
	 */
	@Override
	public List<ResourceBlock> findByC_G_N(long companyId, long groupId,
		String name, int start, int end) {
		return findByC_G_N(companyId, groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of resource blocks
	 * @param end the upper bound of the range of resource blocks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource blocks
	 */
	@Override
	public List<ResourceBlock> findByC_G_N(long companyId, long groupId,
		String name, int start, int end, OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_G_N;
			finderArgs = new Object[] { companyId, groupId, name };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_G_N;
			finderArgs = new Object[] {
					companyId, groupId, name,
					
					start, end, orderByComparator
				};
		}

		List<ResourceBlock> list = (List<ResourceBlock>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ResourceBlock resourceBlock : list) {
				if ((companyId != resourceBlock.getCompanyId()) ||
						(groupId != resourceBlock.getGroupId()) ||
						!Validator.equals(name, resourceBlock.getName())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_RESOURCEBLOCK_WHERE);

			query.append(_FINDER_COLUMN_C_G_N_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_N_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_G_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_G_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_G_N_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ResourceBlockModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name);
				}

				if (!pagination) {
					list = (List<ResourceBlock>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ResourceBlock>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource block
	 * @throws com.liferay.portal.NoSuchResourceBlockException if a matching resource block could not be found
	 */
	@Override
	public ResourceBlock findByC_G_N_First(long companyId, long groupId,
		String name, OrderByComparator orderByComparator)
		throws NoSuchResourceBlockException {
		ResourceBlock resourceBlock = fetchByC_G_N_First(companyId, groupId,
				name, orderByComparator);

		if (resourceBlock != null) {
			return resourceBlock;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchResourceBlockException(msg.toString());
	}

	/**
	 * Returns the first resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource block, or <code>null</code> if a matching resource block could not be found
	 */
	@Override
	public ResourceBlock fetchByC_G_N_First(long companyId, long groupId,
		String name, OrderByComparator orderByComparator) {
		List<ResourceBlock> list = findByC_G_N(companyId, groupId, name, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource block
	 * @throws com.liferay.portal.NoSuchResourceBlockException if a matching resource block could not be found
	 */
	@Override
	public ResourceBlock findByC_G_N_Last(long companyId, long groupId,
		String name, OrderByComparator orderByComparator)
		throws NoSuchResourceBlockException {
		ResourceBlock resourceBlock = fetchByC_G_N_Last(companyId, groupId,
				name, orderByComparator);

		if (resourceBlock != null) {
			return resourceBlock;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchResourceBlockException(msg.toString());
	}

	/**
	 * Returns the last resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource block, or <code>null</code> if a matching resource block could not be found
	 */
	@Override
	public ResourceBlock fetchByC_G_N_Last(long companyId, long groupId,
		String name, OrderByComparator orderByComparator) {
		int count = countByC_G_N(companyId, groupId, name);

		if (count == 0) {
			return null;
		}

		List<ResourceBlock> list = findByC_G_N(companyId, groupId, name,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the resource blocks before and after the current resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	 *
	 * @param resourceBlockId the primary key of the current resource block
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource block
	 * @throws com.liferay.portal.NoSuchResourceBlockException if a resource block with the primary key could not be found
	 */
	@Override
	public ResourceBlock[] findByC_G_N_PrevAndNext(long resourceBlockId,
		long companyId, long groupId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchResourceBlockException {
		ResourceBlock resourceBlock = findByPrimaryKey(resourceBlockId);

		Session session = null;

		try {
			session = openSession();

			ResourceBlock[] array = new ResourceBlockImpl[3];

			array[0] = getByC_G_N_PrevAndNext(session, resourceBlock,
					companyId, groupId, name, orderByComparator, true);

			array[1] = resourceBlock;

			array[2] = getByC_G_N_PrevAndNext(session, resourceBlock,
					companyId, groupId, name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourceBlock getByC_G_N_PrevAndNext(Session session,
		ResourceBlock resourceBlock, long companyId, long groupId, String name,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RESOURCEBLOCK_WHERE);

		query.append(_FINDER_COLUMN_C_G_N_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_G_N_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_C_G_N_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_C_G_N_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_C_G_N_NAME_2);
		}

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
			query.append(ResourceBlockModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(resourceBlock);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ResourceBlock> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 */
	@Override
	public void removeByC_G_N(long companyId, long groupId, String name) {
		for (ResourceBlock resourceBlock : findByC_G_N(companyId, groupId,
				name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(resourceBlock);
		}
	}

	/**
	 * Returns the number of resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching resource blocks
	 */
	@Override
	public int countByC_G_N(long companyId, long groupId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_G_N;

		Object[] finderArgs = new Object[] { companyId, groupId, name };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_RESOURCEBLOCK_WHERE);

			query.append(_FINDER_COLUMN_C_G_N_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_N_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_G_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_G_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_G_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_G_N_COMPANYID_2 = "resourceBlock.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_G_N_GROUPID_2 = "resourceBlock.groupId = ? AND ";
	private static final String _FINDER_COLUMN_C_G_N_NAME_1 = "resourceBlock.name IS NULL";
	private static final String _FINDER_COLUMN_C_G_N_NAME_2 = "resourceBlock.name = ?";
	private static final String _FINDER_COLUMN_C_G_N_NAME_3 = "(resourceBlock.name IS NULL OR resourceBlock.name = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_G_N_P = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockModelImpl.FINDER_CACHE_ENABLED,
			ResourceBlockImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_G_N_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			ResourceBlockModelImpl.COMPANYID_COLUMN_BITMASK |
			ResourceBlockModelImpl.GROUPID_COLUMN_BITMASK |
			ResourceBlockModelImpl.NAME_COLUMN_BITMASK |
			ResourceBlockModelImpl.PERMISSIONSHASH_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_G_N_P = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_G_N_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			});

	/**
	 * Returns the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; or throws a {@link com.liferay.portal.NoSuchResourceBlockException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param permissionsHash the permissions hash
	 * @return the matching resource block
	 * @throws com.liferay.portal.NoSuchResourceBlockException if a matching resource block could not be found
	 */
	@Override
	public ResourceBlock findByC_G_N_P(long companyId, long groupId,
		String name, String permissionsHash)
		throws NoSuchResourceBlockException {
		ResourceBlock resourceBlock = fetchByC_G_N_P(companyId, groupId, name,
				permissionsHash);

		if (resourceBlock == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", permissionsHash=");
			msg.append(permissionsHash);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchResourceBlockException(msg.toString());
		}

		return resourceBlock;
	}

	/**
	 * Returns the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param permissionsHash the permissions hash
	 * @return the matching resource block, or <code>null</code> if a matching resource block could not be found
	 */
	@Override
	public ResourceBlock fetchByC_G_N_P(long companyId, long groupId,
		String name, String permissionsHash) {
		return fetchByC_G_N_P(companyId, groupId, name, permissionsHash, true);
	}

	/**
	 * Returns the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param permissionsHash the permissions hash
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching resource block, or <code>null</code> if a matching resource block could not be found
	 */
	@Override
	public ResourceBlock fetchByC_G_N_P(long companyId, long groupId,
		String name, String permissionsHash, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				companyId, groupId, name, permissionsHash
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_G_N_P,
					finderArgs, this);
		}

		if (result instanceof ResourceBlock) {
			ResourceBlock resourceBlock = (ResourceBlock)result;

			if ((companyId != resourceBlock.getCompanyId()) ||
					(groupId != resourceBlock.getGroupId()) ||
					!Validator.equals(name, resourceBlock.getName()) ||
					!Validator.equals(permissionsHash,
						resourceBlock.getPermissionsHash())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_RESOURCEBLOCK_WHERE);

			query.append(_FINDER_COLUMN_C_G_N_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_N_P_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_G_N_P_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_G_N_P_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_G_N_P_NAME_2);
			}

			boolean bindPermissionsHash = false;

			if (permissionsHash == null) {
				query.append(_FINDER_COLUMN_C_G_N_P_PERMISSIONSHASH_1);
			}
			else if (permissionsHash.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_G_N_P_PERMISSIONSHASH_3);
			}
			else {
				bindPermissionsHash = true;

				query.append(_FINDER_COLUMN_C_G_N_P_PERMISSIONSHASH_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name);
				}

				if (bindPermissionsHash) {
					qPos.add(permissionsHash);
				}

				List<ResourceBlock> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_N_P,
						finderArgs, list);
				}
				else {
					ResourceBlock resourceBlock = list.get(0);

					result = resourceBlock;

					cacheResult(resourceBlock);

					if ((resourceBlock.getCompanyId() != companyId) ||
							(resourceBlock.getGroupId() != groupId) ||
							(resourceBlock.getName() == null) ||
							!resourceBlock.getName().equals(name) ||
							(resourceBlock.getPermissionsHash() == null) ||
							!resourceBlock.getPermissionsHash()
											  .equals(permissionsHash)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_N_P,
							finderArgs, resourceBlock);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_G_N_P,
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
			return (ResourceBlock)result;
		}
	}

	/**
	 * Removes the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param permissionsHash the permissions hash
	 * @return the resource block that was removed
	 */
	@Override
	public ResourceBlock removeByC_G_N_P(long companyId, long groupId,
		String name, String permissionsHash)
		throws NoSuchResourceBlockException {
		ResourceBlock resourceBlock = findByC_G_N_P(companyId, groupId, name,
				permissionsHash);

		return remove(resourceBlock);
	}

	/**
	 * Returns the number of resource blocks where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param permissionsHash the permissions hash
	 * @return the number of matching resource blocks
	 */
	@Override
	public int countByC_G_N_P(long companyId, long groupId, String name,
		String permissionsHash) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_G_N_P;

		Object[] finderArgs = new Object[] {
				companyId, groupId, name, permissionsHash
			};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_RESOURCEBLOCK_WHERE);

			query.append(_FINDER_COLUMN_C_G_N_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_N_P_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_G_N_P_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_G_N_P_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_G_N_P_NAME_2);
			}

			boolean bindPermissionsHash = false;

			if (permissionsHash == null) {
				query.append(_FINDER_COLUMN_C_G_N_P_PERMISSIONSHASH_1);
			}
			else if (permissionsHash.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_G_N_P_PERMISSIONSHASH_3);
			}
			else {
				bindPermissionsHash = true;

				query.append(_FINDER_COLUMN_C_G_N_P_PERMISSIONSHASH_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name);
				}

				if (bindPermissionsHash) {
					qPos.add(permissionsHash);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_G_N_P_COMPANYID_2 = "resourceBlock.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_G_N_P_GROUPID_2 = "resourceBlock.groupId = ? AND ";
	private static final String _FINDER_COLUMN_C_G_N_P_NAME_1 = "resourceBlock.name IS NULL AND ";
	private static final String _FINDER_COLUMN_C_G_N_P_NAME_2 = "resourceBlock.name = ? AND ";
	private static final String _FINDER_COLUMN_C_G_N_P_NAME_3 = "(resourceBlock.name IS NULL OR resourceBlock.name = '') AND ";
	private static final String _FINDER_COLUMN_C_G_N_P_PERMISSIONSHASH_1 = "resourceBlock.permissionsHash IS NULL";
	private static final String _FINDER_COLUMN_C_G_N_P_PERMISSIONSHASH_2 = "resourceBlock.permissionsHash = ?";
	private static final String _FINDER_COLUMN_C_G_N_P_PERMISSIONSHASH_3 = "(resourceBlock.permissionsHash IS NULL OR resourceBlock.permissionsHash = '')";

	public ResourceBlockPersistenceImpl() {
		setModelClass(ResourceBlock.class);
	}

	/**
	 * Caches the resource block in the entity cache if it is enabled.
	 *
	 * @param resourceBlock the resource block
	 */
	@Override
	public void cacheResult(ResourceBlock resourceBlock) {
		EntityCacheUtil.putResult(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockImpl.class, resourceBlock.getPrimaryKey(),
			resourceBlock);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_N_P,
			new Object[] {
				resourceBlock.getCompanyId(), resourceBlock.getGroupId(),
				resourceBlock.getName(), resourceBlock.getPermissionsHash()
			}, resourceBlock);

		resourceBlock.resetOriginalValues();
	}

	/**
	 * Caches the resource blocks in the entity cache if it is enabled.
	 *
	 * @param resourceBlocks the resource blocks
	 */
	@Override
	public void cacheResult(List<ResourceBlock> resourceBlocks) {
		for (ResourceBlock resourceBlock : resourceBlocks) {
			if (EntityCacheUtil.getResult(
						ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
						ResourceBlockImpl.class, resourceBlock.getPrimaryKey()) == null) {
				cacheResult(resourceBlock);
			}
			else {
				resourceBlock.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all resource blocks.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ResourceBlockImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ResourceBlockImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the resource block.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ResourceBlock resourceBlock) {
		EntityCacheUtil.removeResult(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockImpl.class, resourceBlock.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(resourceBlock);
	}

	@Override
	public void clearCache(List<ResourceBlock> resourceBlocks) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ResourceBlock resourceBlock : resourceBlocks) {
			EntityCacheUtil.removeResult(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockImpl.class, resourceBlock.getPrimaryKey());

			clearUniqueFindersCache(resourceBlock);
		}
	}

	protected void cacheUniqueFindersCache(ResourceBlock resourceBlock) {
		if (resourceBlock.isNew()) {
			Object[] args = new Object[] {
					resourceBlock.getCompanyId(), resourceBlock.getGroupId(),
					resourceBlock.getName(), resourceBlock.getPermissionsHash()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_G_N_P, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_N_P, args,
				resourceBlock);
		}
		else {
			ResourceBlockModelImpl resourceBlockModelImpl = (ResourceBlockModelImpl)resourceBlock;

			if ((resourceBlockModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_G_N_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceBlock.getCompanyId(), resourceBlock.getGroupId(),
						resourceBlock.getName(),
						resourceBlock.getPermissionsHash()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_G_N_P, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_N_P, args,
					resourceBlock);
			}
		}
	}

	protected void clearUniqueFindersCache(ResourceBlock resourceBlock) {
		ResourceBlockModelImpl resourceBlockModelImpl = (ResourceBlockModelImpl)resourceBlock;

		Object[] args = new Object[] {
				resourceBlock.getCompanyId(), resourceBlock.getGroupId(),
				resourceBlock.getName(), resourceBlock.getPermissionsHash()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_G_N_P, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_G_N_P, args);

		if ((resourceBlockModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_G_N_P.getColumnBitmask()) != 0) {
			args = new Object[] {
					resourceBlockModelImpl.getOriginalCompanyId(),
					resourceBlockModelImpl.getOriginalGroupId(),
					resourceBlockModelImpl.getOriginalName(),
					resourceBlockModelImpl.getOriginalPermissionsHash()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_G_N_P, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_G_N_P, args);
		}
	}

	/**
	 * Creates a new resource block with the primary key. Does not add the resource block to the database.
	 *
	 * @param resourceBlockId the primary key for the new resource block
	 * @return the new resource block
	 */
	@Override
	public ResourceBlock create(long resourceBlockId) {
		ResourceBlock resourceBlock = new ResourceBlockImpl();

		resourceBlock.setNew(true);
		resourceBlock.setPrimaryKey(resourceBlockId);

		return resourceBlock;
	}

	/**
	 * Removes the resource block with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceBlockId the primary key of the resource block
	 * @return the resource block that was removed
	 * @throws com.liferay.portal.NoSuchResourceBlockException if a resource block with the primary key could not be found
	 */
	@Override
	public ResourceBlock remove(long resourceBlockId)
		throws NoSuchResourceBlockException {
		return remove((Serializable)resourceBlockId);
	}

	/**
	 * Removes the resource block with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the resource block
	 * @return the resource block that was removed
	 * @throws com.liferay.portal.NoSuchResourceBlockException if a resource block with the primary key could not be found
	 */
	@Override
	public ResourceBlock remove(Serializable primaryKey)
		throws NoSuchResourceBlockException {
		Session session = null;

		try {
			session = openSession();

			ResourceBlock resourceBlock = (ResourceBlock)session.get(ResourceBlockImpl.class,
					primaryKey);

			if (resourceBlock == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchResourceBlockException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(resourceBlock);
		}
		catch (NoSuchResourceBlockException nsee) {
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
	protected ResourceBlock removeImpl(ResourceBlock resourceBlock) {
		resourceBlock = toUnwrappedModel(resourceBlock);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(resourceBlock)) {
				resourceBlock = (ResourceBlock)session.get(ResourceBlockImpl.class,
						resourceBlock.getPrimaryKeyObj());
			}

			if (resourceBlock != null) {
				session.delete(resourceBlock);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (resourceBlock != null) {
			clearCache(resourceBlock);
		}

		return resourceBlock;
	}

	@Override
	public ResourceBlock updateImpl(
		com.liferay.portal.model.ResourceBlock resourceBlock) {
		resourceBlock = toUnwrappedModel(resourceBlock);

		boolean isNew = resourceBlock.isNew();

		ResourceBlockModelImpl resourceBlockModelImpl = (ResourceBlockModelImpl)resourceBlock;

		Session session = null;

		try {
			session = openSession();

			if (resourceBlock.isNew()) {
				session.save(resourceBlock);

				resourceBlock.setNew(false);
			}
			else {
				session.merge(resourceBlock);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ResourceBlockModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((resourceBlockModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceBlockModelImpl.getOriginalCompanyId(),
						resourceBlockModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N,
					args);

				args = new Object[] {
						resourceBlockModelImpl.getCompanyId(),
						resourceBlockModelImpl.getName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N,
					args);
			}

			if ((resourceBlockModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_G_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceBlockModelImpl.getOriginalCompanyId(),
						resourceBlockModelImpl.getOriginalGroupId(),
						resourceBlockModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_G_N, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_G_N,
					args);

				args = new Object[] {
						resourceBlockModelImpl.getCompanyId(),
						resourceBlockModelImpl.getGroupId(),
						resourceBlockModelImpl.getName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_G_N, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_G_N,
					args);
			}
		}

		EntityCacheUtil.putResult(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockImpl.class, resourceBlock.getPrimaryKey(),
			resourceBlock, false);

		clearUniqueFindersCache(resourceBlock);
		cacheUniqueFindersCache(resourceBlock);

		resourceBlock.resetOriginalValues();

		return resourceBlock;
	}

	protected ResourceBlock toUnwrappedModel(ResourceBlock resourceBlock) {
		if (resourceBlock instanceof ResourceBlockImpl) {
			return resourceBlock;
		}

		ResourceBlockImpl resourceBlockImpl = new ResourceBlockImpl();

		resourceBlockImpl.setNew(resourceBlock.isNew());
		resourceBlockImpl.setPrimaryKey(resourceBlock.getPrimaryKey());

		resourceBlockImpl.setMvccVersion(resourceBlock.getMvccVersion());
		resourceBlockImpl.setResourceBlockId(resourceBlock.getResourceBlockId());
		resourceBlockImpl.setCompanyId(resourceBlock.getCompanyId());
		resourceBlockImpl.setGroupId(resourceBlock.getGroupId());
		resourceBlockImpl.setName(resourceBlock.getName());
		resourceBlockImpl.setPermissionsHash(resourceBlock.getPermissionsHash());
		resourceBlockImpl.setReferenceCount(resourceBlock.getReferenceCount());

		return resourceBlockImpl;
	}

	/**
	 * Returns the resource block with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource block
	 * @return the resource block
	 * @throws com.liferay.portal.NoSuchResourceBlockException if a resource block with the primary key could not be found
	 */
	@Override
	public ResourceBlock findByPrimaryKey(Serializable primaryKey)
		throws NoSuchResourceBlockException {
		ResourceBlock resourceBlock = fetchByPrimaryKey(primaryKey);

		if (resourceBlock == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchResourceBlockException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return resourceBlock;
	}

	/**
	 * Returns the resource block with the primary key or throws a {@link com.liferay.portal.NoSuchResourceBlockException} if it could not be found.
	 *
	 * @param resourceBlockId the primary key of the resource block
	 * @return the resource block
	 * @throws com.liferay.portal.NoSuchResourceBlockException if a resource block with the primary key could not be found
	 */
	@Override
	public ResourceBlock findByPrimaryKey(long resourceBlockId)
		throws NoSuchResourceBlockException {
		return findByPrimaryKey((Serializable)resourceBlockId);
	}

	/**
	 * Returns the resource block with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource block
	 * @return the resource block, or <code>null</code> if a resource block with the primary key could not be found
	 */
	@Override
	public ResourceBlock fetchByPrimaryKey(Serializable primaryKey) {
		ResourceBlock resourceBlock = (ResourceBlock)EntityCacheUtil.getResult(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockImpl.class, primaryKey);

		if (resourceBlock == _nullResourceBlock) {
			return null;
		}

		if (resourceBlock == null) {
			Session session = null;

			try {
				session = openSession();

				resourceBlock = (ResourceBlock)session.get(ResourceBlockImpl.class,
						primaryKey);

				if (resourceBlock != null) {
					cacheResult(resourceBlock);
				}
				else {
					EntityCacheUtil.putResult(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
						ResourceBlockImpl.class, primaryKey, _nullResourceBlock);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
					ResourceBlockImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return resourceBlock;
	}

	/**
	 * Returns the resource block with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param resourceBlockId the primary key of the resource block
	 * @return the resource block, or <code>null</code> if a resource block with the primary key could not be found
	 */
	@Override
	public ResourceBlock fetchByPrimaryKey(long resourceBlockId) {
		return fetchByPrimaryKey((Serializable)resourceBlockId);
	}

	@Override
	public Map<Serializable, ResourceBlock> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ResourceBlock> map = new HashMap<Serializable, ResourceBlock>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ResourceBlock resourceBlock = fetchByPrimaryKey(primaryKey);

			if (resourceBlock != null) {
				map.put(primaryKey, resourceBlock);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			ResourceBlock resourceBlock = (ResourceBlock)EntityCacheUtil.getResult(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
					ResourceBlockImpl.class, primaryKey);

			if (resourceBlock == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, resourceBlock);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_RESOURCEBLOCK_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (ResourceBlock resourceBlock : (List<ResourceBlock>)q.list()) {
				map.put(resourceBlock.getPrimaryKeyObj(), resourceBlock);

				cacheResult(resourceBlock);

				uncachedPrimaryKeys.remove(resourceBlock.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
					ResourceBlockImpl.class, primaryKey, _nullResourceBlock);
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
	 * Returns all the resource blocks.
	 *
	 * @return the resource blocks
	 */
	@Override
	public List<ResourceBlock> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource blocks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource blocks
	 * @param end the upper bound of the range of resource blocks (not inclusive)
	 * @return the range of resource blocks
	 */
	@Override
	public List<ResourceBlock> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource blocks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource blocks
	 * @param end the upper bound of the range of resource blocks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of resource blocks
	 */
	@Override
	public List<ResourceBlock> findAll(int start, int end,
		OrderByComparator orderByComparator) {
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

		List<ResourceBlock> list = (List<ResourceBlock>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_RESOURCEBLOCK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_RESOURCEBLOCK;

				if (pagination) {
					sql = sql.concat(ResourceBlockModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ResourceBlock>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ResourceBlock>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the resource blocks from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ResourceBlock resourceBlock : findAll()) {
			remove(resourceBlock);
		}
	}

	/**
	 * Returns the number of resource blocks.
	 *
	 * @return the number of resource blocks
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RESOURCEBLOCK);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the resource block persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.ResourceBlock")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ResourceBlock>> listenersList = new ArrayList<ModelListener<ResourceBlock>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ResourceBlock>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ResourceBlockImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_RESOURCEBLOCK = "SELECT resourceBlock FROM ResourceBlock resourceBlock";
	private static final String _SQL_SELECT_RESOURCEBLOCK_WHERE_PKS_IN = "SELECT resourceBlock FROM ResourceBlock resourceBlock WHERE resourceBlockId IN (";
	private static final String _SQL_SELECT_RESOURCEBLOCK_WHERE = "SELECT resourceBlock FROM ResourceBlock resourceBlock WHERE ";
	private static final String _SQL_COUNT_RESOURCEBLOCK = "SELECT COUNT(resourceBlock) FROM ResourceBlock resourceBlock";
	private static final String _SQL_COUNT_RESOURCEBLOCK_WHERE = "SELECT COUNT(resourceBlock) FROM ResourceBlock resourceBlock WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "resourceBlock.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ResourceBlock exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ResourceBlock exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(ResourceBlockPersistenceImpl.class);
	private static ResourceBlock _nullResourceBlock = new ResourceBlockImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ResourceBlock> toCacheModel() {
				return _nullResourceBlockCacheModel;
			}
		};

	private static CacheModel<ResourceBlock> _nullResourceBlockCacheModel = new NullCacheModel();

	private static class NullCacheModel implements CacheModel<ResourceBlock>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return 0;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public ResourceBlock toEntityModel() {
			return _nullResourceBlock;
		}
	}
}