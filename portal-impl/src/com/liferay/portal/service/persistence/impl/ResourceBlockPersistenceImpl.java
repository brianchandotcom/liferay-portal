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

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchResourceBlockException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceBlock;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.ResourceBlockPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.ResourceBlockImpl;
import com.liferay.portal.model.impl.ResourceBlockModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The persistence implementation for the resource block service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockPersistence
 * @see com.liferay.portal.kernel.service.persistence.ResourceBlockUtil
 * @deprecated As of Judson (7.1.x), with no direct replacement
 * @generated
 */
@Deprecated
@ProviderType
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
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByC_N;
	private FinderPath _finderPathWithoutPaginationFindByC_N;
	private FinderPath _finderPathCountByC_N;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		int start, int end, OrderByComparator<ResourceBlock> orderByComparator) {
		return findByC_N(companyId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource blocks where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of resource blocks
	 * @param end the upper bound of the range of resource blocks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching resource blocks
	 */
	@Override
	public List<ResourceBlock> findByC_N(long companyId, String name,
		int start, int end, OrderByComparator<ResourceBlock> orderByComparator,
		boolean retrieveFromCache) {
		name = Objects.toString(name, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByC_N;
			finderArgs = new Object[] { companyId, name };
		}
		else {
			finderPath = _finderPathWithPaginationFindByC_N;
			finderArgs = new Object[] {
					companyId, name,
					
					start, end, orderByComparator
				};
		}

		List<ResourceBlock> list = null;

		if (retrieveFromCache) {
			list = (List<ResourceBlock>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourceBlock resourceBlock : list) {
					if ((companyId != resourceBlock.getCompanyId()) ||
							!name.equals(resourceBlock.getName())) {
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

			query.append(_SQL_SELECT_RESOURCEBLOCK_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
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
	 * @throws NoSuchResourceBlockException if a matching resource block could not be found
	 */
	@Override
	public ResourceBlock findByC_N_First(long companyId, String name,
		OrderByComparator<ResourceBlock> orderByComparator)
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

		msg.append("}");

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
		OrderByComparator<ResourceBlock> orderByComparator) {
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
	 * @throws NoSuchResourceBlockException if a matching resource block could not be found
	 */
	@Override
	public ResourceBlock findByC_N_Last(long companyId, String name,
		OrderByComparator<ResourceBlock> orderByComparator)
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

		msg.append("}");

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
		OrderByComparator<ResourceBlock> orderByComparator) {
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
	 * @throws NoSuchResourceBlockException if a resource block with the primary key could not be found
	 */
	@Override
	public ResourceBlock[] findByC_N_PrevAndNext(long resourceBlockId,
		long companyId, String name,
		OrderByComparator<ResourceBlock> orderByComparator)
		throws NoSuchResourceBlockException {
		name = Objects.toString(name, "");

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
		OrderByComparator<ResourceBlock> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_RESOURCEBLOCK_WHERE);

		query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					resourceBlock)) {
				qPos.add(orderByConditionValue);
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
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_N;

		Object[] finderArgs = new Object[] { companyId, name };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_RESOURCEBLOCK_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
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
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "resourceBlock.name = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(resourceBlock.name IS NULL OR resourceBlock.name = '')";
	private FinderPath _finderPathWithPaginationFindByC_G_N;
	private FinderPath _finderPathWithoutPaginationFindByC_G_N;
	private FinderPath _finderPathCountByC_G_N;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		String name, int start, int end,
		OrderByComparator<ResourceBlock> orderByComparator) {
		return findByC_G_N(companyId, groupId, name, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of resource blocks
	 * @param end the upper bound of the range of resource blocks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching resource blocks
	 */
	@Override
	public List<ResourceBlock> findByC_G_N(long companyId, long groupId,
		String name, int start, int end,
		OrderByComparator<ResourceBlock> orderByComparator,
		boolean retrieveFromCache) {
		name = Objects.toString(name, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByC_G_N;
			finderArgs = new Object[] { companyId, groupId, name };
		}
		else {
			finderPath = _finderPathWithPaginationFindByC_G_N;
			finderArgs = new Object[] {
					companyId, groupId, name,
					
					start, end, orderByComparator
				};
		}

		List<ResourceBlock> list = null;

		if (retrieveFromCache) {
			list = (List<ResourceBlock>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourceBlock resourceBlock : list) {
					if ((companyId != resourceBlock.getCompanyId()) ||
							(groupId != resourceBlock.getGroupId()) ||
							!name.equals(resourceBlock.getName())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_RESOURCEBLOCK_WHERE);

			query.append(_FINDER_COLUMN_C_G_N_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_N_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
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
	 * @throws NoSuchResourceBlockException if a matching resource block could not be found
	 */
	@Override
	public ResourceBlock findByC_G_N_First(long companyId, long groupId,
		String name, OrderByComparator<ResourceBlock> orderByComparator)
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

		msg.append("}");

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
		String name, OrderByComparator<ResourceBlock> orderByComparator) {
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
	 * @throws NoSuchResourceBlockException if a matching resource block could not be found
	 */
	@Override
	public ResourceBlock findByC_G_N_Last(long companyId, long groupId,
		String name, OrderByComparator<ResourceBlock> orderByComparator)
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

		msg.append("}");

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
		String name, OrderByComparator<ResourceBlock> orderByComparator) {
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
	 * @throws NoSuchResourceBlockException if a resource block with the primary key could not be found
	 */
	@Override
	public ResourceBlock[] findByC_G_N_PrevAndNext(long resourceBlockId,
		long companyId, long groupId, String name,
		OrderByComparator<ResourceBlock> orderByComparator)
		throws NoSuchResourceBlockException {
		name = Objects.toString(name, "");

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
		OrderByComparator<ResourceBlock> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_RESOURCEBLOCK_WHERE);

		query.append(_FINDER_COLUMN_C_G_N_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_G_N_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					resourceBlock)) {
				qPos.add(orderByConditionValue);
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
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_G_N;

		Object[] finderArgs = new Object[] { companyId, groupId, name };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_RESOURCEBLOCK_WHERE);

			query.append(_FINDER_COLUMN_C_G_N_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_N_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
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
	private static final String _FINDER_COLUMN_C_G_N_NAME_2 = "resourceBlock.name = ?";
	private static final String _FINDER_COLUMN_C_G_N_NAME_3 = "(resourceBlock.name IS NULL OR resourceBlock.name = '')";
	private FinderPath _finderPathFetchByC_G_N_P;
	private FinderPath _finderPathCountByC_G_N_P;

	/**
	 * Returns the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; or throws a {@link NoSuchResourceBlockException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param permissionsHash the permissions hash
	 * @return the matching resource block
	 * @throws NoSuchResourceBlockException if a matching resource block could not be found
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

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
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
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching resource block, or <code>null</code> if a matching resource block could not be found
	 */
	@Override
	public ResourceBlock fetchByC_G_N_P(long companyId, long groupId,
		String name, String permissionsHash, boolean retrieveFromCache) {
		name = Objects.toString(name, "");
		permissionsHash = Objects.toString(permissionsHash, "");

		Object[] finderArgs = new Object[] {
				companyId, groupId, name, permissionsHash
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(_finderPathFetchByC_G_N_P,
					finderArgs, this);
		}

		if (result instanceof ResourceBlock) {
			ResourceBlock resourceBlock = (ResourceBlock)result;

			if ((companyId != resourceBlock.getCompanyId()) ||
					(groupId != resourceBlock.getGroupId()) ||
					!Objects.equals(name, resourceBlock.getName()) ||
					!Objects.equals(permissionsHash,
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

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_G_N_P_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_G_N_P_NAME_2);
			}

			boolean bindPermissionsHash = false;

			if (permissionsHash.isEmpty()) {
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
					FinderCacheUtil.putResult(_finderPathFetchByC_G_N_P,
						finderArgs, list);
				}
				else {
					ResourceBlock resourceBlock = list.get(0);

					result = resourceBlock;

					cacheResult(resourceBlock);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathFetchByC_G_N_P,
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
		name = Objects.toString(name, "");
		permissionsHash = Objects.toString(permissionsHash, "");

		FinderPath finderPath = _finderPathCountByC_G_N_P;

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

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_G_N_P_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_G_N_P_NAME_2);
			}

			boolean bindPermissionsHash = false;

			if (permissionsHash.isEmpty()) {
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
	private static final String _FINDER_COLUMN_C_G_N_P_NAME_2 = "resourceBlock.name = ? AND ";
	private static final String _FINDER_COLUMN_C_G_N_P_NAME_3 = "(resourceBlock.name IS NULL OR resourceBlock.name = '') AND ";
	private static final String _FINDER_COLUMN_C_G_N_P_PERMISSIONSHASH_2 = "resourceBlock.permissionsHash = ?";
	private static final String _FINDER_COLUMN_C_G_N_P_PERMISSIONSHASH_3 = "(resourceBlock.permissionsHash IS NULL OR resourceBlock.permissionsHash = '')";

	public ResourceBlockPersistenceImpl() {
		setModelClass(ResourceBlock.class);

		setModelImplClass(ResourceBlockImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED);
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

		FinderCacheUtil.putResult(_finderPathFetchByC_G_N_P,
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
	 * The {@link EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(ResourceBlockImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the resource block.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ResourceBlock resourceBlock) {
		EntityCacheUtil.removeResult(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockImpl.class, resourceBlock.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ResourceBlockModelImpl)resourceBlock, true);
	}

	@Override
	public void clearCache(List<ResourceBlock> resourceBlocks) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ResourceBlock resourceBlock : resourceBlocks) {
			EntityCacheUtil.removeResult(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockImpl.class, resourceBlock.getPrimaryKey());

			clearUniqueFindersCache((ResourceBlockModelImpl)resourceBlock, true);
		}
	}

	protected void cacheUniqueFindersCache(
		ResourceBlockModelImpl resourceBlockModelImpl) {
		Object[] args = new Object[] {
				resourceBlockModelImpl.getCompanyId(),
				resourceBlockModelImpl.getGroupId(),
				resourceBlockModelImpl.getName(),
				resourceBlockModelImpl.getPermissionsHash()
			};

		FinderCacheUtil.putResult(_finderPathCountByC_G_N_P, args,
			Long.valueOf(1), false);
		FinderCacheUtil.putResult(_finderPathFetchByC_G_N_P, args,
			resourceBlockModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ResourceBlockModelImpl resourceBlockModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					resourceBlockModelImpl.getCompanyId(),
					resourceBlockModelImpl.getGroupId(),
					resourceBlockModelImpl.getName(),
					resourceBlockModelImpl.getPermissionsHash()
				};

			FinderCacheUtil.removeResult(_finderPathCountByC_G_N_P, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_G_N_P, args);
		}

		if ((resourceBlockModelImpl.getColumnBitmask() &
				_finderPathFetchByC_G_N_P.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					resourceBlockModelImpl.getOriginalCompanyId(),
					resourceBlockModelImpl.getOriginalGroupId(),
					resourceBlockModelImpl.getOriginalName(),
					resourceBlockModelImpl.getOriginalPermissionsHash()
				};

			FinderCacheUtil.removeResult(_finderPathCountByC_G_N_P, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_G_N_P, args);
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

		resourceBlock.setCompanyId(companyProvider.getCompanyId());

		return resourceBlock;
	}

	/**
	 * Removes the resource block with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceBlockId the primary key of the resource block
	 * @return the resource block that was removed
	 * @throws NoSuchResourceBlockException if a resource block with the primary key could not be found
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
	 * @throws NoSuchResourceBlockException if a resource block with the primary key could not be found
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
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
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
	public ResourceBlock updateImpl(ResourceBlock resourceBlock) {
		boolean isNew = resourceBlock.isNew();

		if (!(resourceBlock instanceof ResourceBlockModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(resourceBlock.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(resourceBlock);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in resourceBlock proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ResourceBlock implementation " +
				resourceBlock.getClass());
		}

		ResourceBlockModelImpl resourceBlockModelImpl = (ResourceBlockModelImpl)resourceBlock;

		Session session = null;

		try {
			session = openSession();

			if (resourceBlock.isNew()) {
				session.save(resourceBlock);

				resourceBlock.setNew(false);
			}
			else {
				resourceBlock = (ResourceBlock)session.merge(resourceBlock);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ResourceBlockModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					resourceBlockModelImpl.getCompanyId(),
					resourceBlockModelImpl.getName()
				};

			FinderCacheUtil.removeResult(_finderPathCountByC_N, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByC_N,
				args);

			args = new Object[] {
					resourceBlockModelImpl.getCompanyId(),
					resourceBlockModelImpl.getGroupId(),
					resourceBlockModelImpl.getName()
				};

			FinderCacheUtil.removeResult(_finderPathCountByC_G_N, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByC_G_N,
				args);

			FinderCacheUtil.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindAll,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((resourceBlockModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByC_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceBlockModelImpl.getOriginalCompanyId(),
						resourceBlockModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(_finderPathCountByC_N, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByC_N,
					args);

				args = new Object[] {
						resourceBlockModelImpl.getCompanyId(),
						resourceBlockModelImpl.getName()
					};

				FinderCacheUtil.removeResult(_finderPathCountByC_N, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByC_N,
					args);
			}

			if ((resourceBlockModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByC_G_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceBlockModelImpl.getOriginalCompanyId(),
						resourceBlockModelImpl.getOriginalGroupId(),
						resourceBlockModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(_finderPathCountByC_G_N, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByC_G_N,
					args);

				args = new Object[] {
						resourceBlockModelImpl.getCompanyId(),
						resourceBlockModelImpl.getGroupId(),
						resourceBlockModelImpl.getName()
					};

				FinderCacheUtil.removeResult(_finderPathCountByC_G_N, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByC_G_N,
					args);
			}
		}

		EntityCacheUtil.putResult(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockImpl.class, resourceBlock.getPrimaryKey(),
			resourceBlock, false);

		clearUniqueFindersCache(resourceBlockModelImpl, false);
		cacheUniqueFindersCache(resourceBlockModelImpl);

		resourceBlock.resetOriginalValues();

		return resourceBlock;
	}

	/**
	 * Returns the resource block with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource block
	 * @return the resource block
	 * @throws NoSuchResourceBlockException if a resource block with the primary key could not be found
	 */
	@Override
	public ResourceBlock findByPrimaryKey(Serializable primaryKey)
		throws NoSuchResourceBlockException {
		ResourceBlock resourceBlock = fetchByPrimaryKey(primaryKey);

		if (resourceBlock == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchResourceBlockException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return resourceBlock;
	}

	/**
	 * Returns the resource block with the primary key or throws a {@link NoSuchResourceBlockException} if it could not be found.
	 *
	 * @param resourceBlockId the primary key of the resource block
	 * @return the resource block
	 * @throws NoSuchResourceBlockException if a resource block with the primary key could not be found
	 */
	@Override
	public ResourceBlock findByPrimaryKey(long resourceBlockId)
		throws NoSuchResourceBlockException {
		return findByPrimaryKey((Serializable)resourceBlockId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource blocks
	 * @param end the upper bound of the range of resource blocks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of resource blocks
	 */
	@Override
	public List<ResourceBlock> findAll(int start, int end,
		OrderByComparator<ResourceBlock> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource blocks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource blocks
	 * @param end the upper bound of the range of resource blocks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of resource blocks
	 */
	@Override
	public List<ResourceBlock> findAll(int start, int end,
		OrderByComparator<ResourceBlock> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<ResourceBlock> list = null;

		if (retrieveFromCache) {
			list = (List<ResourceBlock>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

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
		Long count = (Long)FinderCacheUtil.getResult(_finderPathCountAll,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RESOURCEBLOCK);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(_finderPathCountAll,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathCountAll,
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
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "resourceBlockId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_RESOURCEBLOCK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ResourceBlockModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the resource block persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockModelImpl.FINDER_CACHE_ENABLED,
				ResourceBlockImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockModelImpl.FINDER_CACHE_ENABLED,
				ResourceBlockImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
				new String[0]);

		_finderPathCountAll = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
				new String[0]);

		_finderPathWithPaginationFindByC_N = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockModelImpl.FINDER_CACHE_ENABLED,
				ResourceBlockImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_N",
				new String[] {
					Long.class.getName(), String.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByC_N = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockModelImpl.FINDER_CACHE_ENABLED,
				ResourceBlockImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_N",
				new String[] { Long.class.getName(), String.class.getName() },
				ResourceBlockModelImpl.COMPANYID_COLUMN_BITMASK |
				ResourceBlockModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByC_N = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
				new String[] { Long.class.getName(), String.class.getName() });

		_finderPathWithPaginationFindByC_G_N = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockModelImpl.FINDER_CACHE_ENABLED,
				ResourceBlockImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_G_N",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					String.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByC_G_N = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockModelImpl.FINDER_CACHE_ENABLED,
				ResourceBlockImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_G_N",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					String.class.getName()
				},
				ResourceBlockModelImpl.COMPANYID_COLUMN_BITMASK |
				ResourceBlockModelImpl.GROUPID_COLUMN_BITMASK |
				ResourceBlockModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByC_G_N = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_G_N",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					String.class.getName()
				});

		_finderPathFetchByC_G_N_P = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
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

		_finderPathCountByC_G_N_P = new FinderPath(ResourceBlockModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_G_N_P",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					String.class.getName(), String.class.getName()
				});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ResourceBlockImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	private static final String _SQL_SELECT_RESOURCEBLOCK = "SELECT resourceBlock FROM ResourceBlock resourceBlock";
	private static final String _SQL_SELECT_RESOURCEBLOCK_WHERE = "SELECT resourceBlock FROM ResourceBlock resourceBlock WHERE ";
	private static final String _SQL_COUNT_RESOURCEBLOCK = "SELECT COUNT(resourceBlock) FROM ResourceBlock resourceBlock";
	private static final String _SQL_COUNT_RESOURCEBLOCK_WHERE = "SELECT COUNT(resourceBlock) FROM ResourceBlock resourceBlock WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "resourceBlock.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ResourceBlock exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ResourceBlock exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(ResourceBlockPersistenceImpl.class);
}