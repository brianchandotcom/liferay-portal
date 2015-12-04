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

import com.liferay.portal.NoSuchLayoutBranchException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.impl.LayoutBranchImpl;
import com.liferay.portal.model.impl.LayoutBranchModelImpl;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.LayoutBranchPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the layout branch service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutBranchPersistence
 * @see com.liferay.portal.service.persistence.LayoutBranchUtil
 * @generated
 */
@ProviderType
public class LayoutBranchPersistenceImpl extends BasePersistenceImpl<LayoutBranch>
	implements LayoutBranchPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LayoutBranchUtil} to access the layout branch persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutBranchImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, LayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, LayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTSETBRANCHID =
		new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, LayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLayoutSetBranchId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETBRANCHID =
		new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, LayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLayoutSetBranchId", new String[] { Long.class.getName() },
			LayoutBranchModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LAYOUTSETBRANCHID = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutSetBranchId", new String[] { Long.class.getName() });

	/**
	 * Returns all the layout branchs where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @return the matching layout branchs
	 */
	@Override
	public List<LayoutBranch> findByLayoutSetBranchId(long layoutSetBranchId) {
		return findByLayoutSetBranchId(layoutSetBranchId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout branchs where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @return the range of matching layout branchs
	 */
	@Override
	public List<LayoutBranch> findByLayoutSetBranchId(long layoutSetBranchId,
		int start, int end) {
		return findByLayoutSetBranchId(layoutSetBranchId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout branchs where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout branchs
	 */
	@Override
	public List<LayoutBranch> findByLayoutSetBranchId(long layoutSetBranchId,
		int start, int end, OrderByComparator<LayoutBranch> orderByComparator) {
		return findByLayoutSetBranchId(layoutSetBranchId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout branchs where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout branchs
	 */
	@Override
	public List<LayoutBranch> findByLayoutSetBranchId(long layoutSetBranchId,
		int start, int end, OrderByComparator<LayoutBranch> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETBRANCHID;
			finderArgs = new Object[] { layoutSetBranchId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTSETBRANCHID;
			finderArgs = new Object[] {
					layoutSetBranchId,
					
					start, end, orderByComparator
				};
		}

		List<LayoutBranch> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutBranch>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutBranch layoutBranch : list) {
					if ((layoutSetBranchId != layoutBranch.getLayoutSetBranchId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutBranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				if (!pagination) {
					list = (List<LayoutBranch>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutBranch>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first layout branch in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout branch
	 * @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch findByLayoutSetBranchId_First(long layoutSetBranchId,
		OrderByComparator<LayoutBranch> orderByComparator)
		throws NoSuchLayoutBranchException {
		LayoutBranch layoutBranch = fetchByLayoutSetBranchId_First(layoutSetBranchId,
				orderByComparator);

		if (layoutBranch != null) {
			return layoutBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutBranchException(msg.toString());
	}

	/**
	 * Returns the first layout branch in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch fetchByLayoutSetBranchId_First(long layoutSetBranchId,
		OrderByComparator<LayoutBranch> orderByComparator) {
		List<LayoutBranch> list = findByLayoutSetBranchId(layoutSetBranchId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout branch in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout branch
	 * @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch findByLayoutSetBranchId_Last(long layoutSetBranchId,
		OrderByComparator<LayoutBranch> orderByComparator)
		throws NoSuchLayoutBranchException {
		LayoutBranch layoutBranch = fetchByLayoutSetBranchId_Last(layoutSetBranchId,
				orderByComparator);

		if (layoutBranch != null) {
			return layoutBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutBranchException(msg.toString());
	}

	/**
	 * Returns the last layout branch in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch fetchByLayoutSetBranchId_Last(long layoutSetBranchId,
		OrderByComparator<LayoutBranch> orderByComparator) {
		int count = countByLayoutSetBranchId(layoutSetBranchId);

		if (count == 0) {
			return null;
		}

		List<LayoutBranch> list = findByLayoutSetBranchId(layoutSetBranchId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout branchs before and after the current layout branch in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutBranchId the primary key of the current layout branch
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout branch
	 * @throws NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 */
	@Override
	public LayoutBranch[] findByLayoutSetBranchId_PrevAndNext(
		long layoutBranchId, long layoutSetBranchId,
		OrderByComparator<LayoutBranch> orderByComparator)
		throws NoSuchLayoutBranchException {
		LayoutBranch layoutBranch = findByPrimaryKey(layoutBranchId);

		Session session = null;

		try {
			session = openSession();

			LayoutBranch[] array = new LayoutBranchImpl[3];

			array[0] = getByLayoutSetBranchId_PrevAndNext(session,
					layoutBranch, layoutSetBranchId, orderByComparator, true);

			array[1] = layoutBranch;

			array[2] = getByLayoutSetBranchId_PrevAndNext(session,
					layoutBranch, layoutSetBranchId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutBranch getByLayoutSetBranchId_PrevAndNext(Session session,
		LayoutBranch layoutBranch, long layoutSetBranchId,
		OrderByComparator<LayoutBranch> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTBRANCH_WHERE);

		query.append(_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2);

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
			query.append(LayoutBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutBranch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout branchs where layoutSetBranchId = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 */
	@Override
	public void removeByLayoutSetBranchId(long layoutSetBranchId) {
		for (LayoutBranch layoutBranch : findByLayoutSetBranchId(
				layoutSetBranchId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutBranch);
		}
	}

	/**
	 * Returns the number of layout branchs where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @return the number of matching layout branchs
	 */
	@Override
	public int countByLayoutSetBranchId(long layoutSetBranchId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_LAYOUTSETBRANCHID;

		Object[] finderArgs = new Object[] { layoutSetBranchId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

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

	private static final String _FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2 =
		"layoutBranch.layoutSetBranchId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_L_P = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, LayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByL_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, LayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByL_P",
			new String[] { Long.class.getName(), Long.class.getName() },
			LayoutBranchModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutBranchModelImpl.PLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_L_P = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_P",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the layout branchs where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the matching layout branchs
	 */
	@Override
	public List<LayoutBranch> findByL_P(long layoutSetBranchId, long plid) {
		return findByL_P(layoutSetBranchId, plid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout branchs where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @return the range of matching layout branchs
	 */
	@Override
	public List<LayoutBranch> findByL_P(long layoutSetBranchId, long plid,
		int start, int end) {
		return findByL_P(layoutSetBranchId, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout branchs where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout branchs
	 */
	@Override
	public List<LayoutBranch> findByL_P(long layoutSetBranchId, long plid,
		int start, int end, OrderByComparator<LayoutBranch> orderByComparator) {
		return findByL_P(layoutSetBranchId, plid, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout branchs where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout branchs
	 */
	@Override
	public List<LayoutBranch> findByL_P(long layoutSetBranchId, long plid,
		int start, int end, OrderByComparator<LayoutBranch> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P;
			finderArgs = new Object[] { layoutSetBranchId, plid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_L_P;
			finderArgs = new Object[] {
					layoutSetBranchId, plid,
					
					start, end, orderByComparator
				};
		}

		List<LayoutBranch> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutBranch>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutBranch layoutBranch : list) {
					if ((layoutSetBranchId != layoutBranch.getLayoutSetBranchId()) ||
							(plid != layoutBranch.getPlid())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_L_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutBranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

				if (!pagination) {
					list = (List<LayoutBranch>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutBranch>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout branch
	 * @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch findByL_P_First(long layoutSetBranchId, long plid,
		OrderByComparator<LayoutBranch> orderByComparator)
		throws NoSuchLayoutBranchException {
		LayoutBranch layoutBranch = fetchByL_P_First(layoutSetBranchId, plid,
				orderByComparator);

		if (layoutBranch != null) {
			return layoutBranch;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutBranchException(msg.toString());
	}

	/**
	 * Returns the first layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch fetchByL_P_First(long layoutSetBranchId, long plid,
		OrderByComparator<LayoutBranch> orderByComparator) {
		List<LayoutBranch> list = findByL_P(layoutSetBranchId, plid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout branch
	 * @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch findByL_P_Last(long layoutSetBranchId, long plid,
		OrderByComparator<LayoutBranch> orderByComparator)
		throws NoSuchLayoutBranchException {
		LayoutBranch layoutBranch = fetchByL_P_Last(layoutSetBranchId, plid,
				orderByComparator);

		if (layoutBranch != null) {
			return layoutBranch;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutBranchException(msg.toString());
	}

	/**
	 * Returns the last layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch fetchByL_P_Last(long layoutSetBranchId, long plid,
		OrderByComparator<LayoutBranch> orderByComparator) {
		int count = countByL_P(layoutSetBranchId, plid);

		if (count == 0) {
			return null;
		}

		List<LayoutBranch> list = findByL_P(layoutSetBranchId, plid, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout branchs before and after the current layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutBranchId the primary key of the current layout branch
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout branch
	 * @throws NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 */
	@Override
	public LayoutBranch[] findByL_P_PrevAndNext(long layoutBranchId,
		long layoutSetBranchId, long plid,
		OrderByComparator<LayoutBranch> orderByComparator)
		throws NoSuchLayoutBranchException {
		LayoutBranch layoutBranch = findByPrimaryKey(layoutBranchId);

		Session session = null;

		try {
			session = openSession();

			LayoutBranch[] array = new LayoutBranchImpl[3];

			array[0] = getByL_P_PrevAndNext(session, layoutBranch,
					layoutSetBranchId, plid, orderByComparator, true);

			array[1] = layoutBranch;

			array[2] = getByL_P_PrevAndNext(session, layoutBranch,
					layoutSetBranchId, plid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutBranch getByL_P_PrevAndNext(Session session,
		LayoutBranch layoutBranch, long layoutSetBranchId, long plid,
		OrderByComparator<LayoutBranch> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTBRANCH_WHERE);

		query.append(_FINDER_COLUMN_L_P_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_P_PLID_2);

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
			query.append(LayoutBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutBranch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout branchs where layoutSetBranchId = &#63; and plid = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 */
	@Override
	public void removeByL_P(long layoutSetBranchId, long plid) {
		for (LayoutBranch layoutBranch : findByL_P(layoutSetBranchId, plid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutBranch);
		}
	}

	/**
	 * Returns the number of layout branchs where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the number of matching layout branchs
	 */
	@Override
	public int countByL_P(long layoutSetBranchId, long plid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_L_P;

		Object[] finderArgs = new Object[] { layoutSetBranchId, plid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_L_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

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

	private static final String _FINDER_COLUMN_L_P_LAYOUTSETBRANCHID_2 = "layoutBranch.layoutSetBranchId = ? AND ";
	private static final String _FINDER_COLUMN_L_P_PLID_2 = "layoutBranch.plid = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_L_P_N = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, LayoutBranchImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByL_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			LayoutBranchModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutBranchModelImpl.PLID_COLUMN_BITMASK |
			LayoutBranchModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_L_P_N = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the layout branch where layoutSetBranchId = &#63; and plid = &#63; and name = &#63; or throws a {@link NoSuchLayoutBranchException} if it could not be found.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param name the name
	 * @return the matching layout branch
	 * @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch findByL_P_N(long layoutSetBranchId, long plid,
		String name) throws NoSuchLayoutBranchException {
		LayoutBranch layoutBranch = fetchByL_P_N(layoutSetBranchId, plid, name);

		if (layoutBranch == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("layoutSetBranchId=");
			msg.append(layoutSetBranchId);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutBranchException(msg.toString());
		}

		return layoutBranch;
	}

	/**
	 * Returns the layout branch where layoutSetBranchId = &#63; and plid = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param name the name
	 * @return the matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch fetchByL_P_N(long layoutSetBranchId, long plid,
		String name) {
		return fetchByL_P_N(layoutSetBranchId, plid, name, true);
	}

	/**
	 * Returns the layout branch where layoutSetBranchId = &#63; and plid = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch fetchByL_P_N(long layoutSetBranchId, long plid,
		String name, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { layoutSetBranchId, plid, name };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_L_P_N,
					finderArgs, this);
		}

		if (result instanceof LayoutBranch) {
			LayoutBranch layoutBranch = (LayoutBranch)result;

			if ((layoutSetBranchId != layoutBranch.getLayoutSetBranchId()) ||
					(plid != layoutBranch.getPlid()) ||
					!Validator.equals(name, layoutBranch.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_L_P_N_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_N_PLID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_L_P_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_L_P_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_L_P_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

				if (bindName) {
					qPos.add(name);
				}

				List<LayoutBranch> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_L_P_N,
						finderArgs, list);
				}
				else {
					LayoutBranch layoutBranch = list.get(0);

					result = layoutBranch;

					cacheResult(layoutBranch);

					if ((layoutBranch.getLayoutSetBranchId() != layoutSetBranchId) ||
							(layoutBranch.getPlid() != plid) ||
							(layoutBranch.getName() == null) ||
							!layoutBranch.getName().equals(name)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_L_P_N,
							finderArgs, layoutBranch);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_L_P_N, finderArgs);

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
			return (LayoutBranch)result;
		}
	}

	/**
	 * Removes the layout branch where layoutSetBranchId = &#63; and plid = &#63; and name = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param name the name
	 * @return the layout branch that was removed
	 */
	@Override
	public LayoutBranch removeByL_P_N(long layoutSetBranchId, long plid,
		String name) throws NoSuchLayoutBranchException {
		LayoutBranch layoutBranch = findByL_P_N(layoutSetBranchId, plid, name);

		return remove(layoutBranch);
	}

	/**
	 * Returns the number of layout branchs where layoutSetBranchId = &#63; and plid = &#63; and name = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param name the name
	 * @return the number of matching layout branchs
	 */
	@Override
	public int countByL_P_N(long layoutSetBranchId, long plid, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_L_P_N;

		Object[] finderArgs = new Object[] { layoutSetBranchId, plid, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_L_P_N_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_N_PLID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_L_P_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_L_P_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_L_P_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

				if (bindName) {
					qPos.add(name);
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

	private static final String _FINDER_COLUMN_L_P_N_LAYOUTSETBRANCHID_2 = "layoutBranch.layoutSetBranchId = ? AND ";
	private static final String _FINDER_COLUMN_L_P_N_PLID_2 = "layoutBranch.plid = ? AND ";
	private static final String _FINDER_COLUMN_L_P_N_NAME_1 = "layoutBranch.name IS NULL";
	private static final String _FINDER_COLUMN_L_P_N_NAME_2 = "layoutBranch.name = ?";
	private static final String _FINDER_COLUMN_L_P_N_NAME_3 = "(layoutBranch.name IS NULL OR layoutBranch.name = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_L_P_M = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, LayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByL_P_M",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P_M = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, LayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByL_P_M",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			LayoutBranchModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutBranchModelImpl.PLID_COLUMN_BITMASK |
			LayoutBranchModelImpl.MASTER_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_L_P_M = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_P_M",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the layout branchs where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @return the matching layout branchs
	 */
	@Override
	public List<LayoutBranch> findByL_P_M(long layoutSetBranchId, long plid,
		boolean master) {
		return findByL_P_M(layoutSetBranchId, plid, master, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout branchs where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @return the range of matching layout branchs
	 */
	@Override
	public List<LayoutBranch> findByL_P_M(long layoutSetBranchId, long plid,
		boolean master, int start, int end) {
		return findByL_P_M(layoutSetBranchId, plid, master, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout branchs where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout branchs
	 */
	@Override
	public List<LayoutBranch> findByL_P_M(long layoutSetBranchId, long plid,
		boolean master, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator) {
		return findByL_P_M(layoutSetBranchId, plid, master, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout branchs where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout branchs
	 */
	@Override
	public List<LayoutBranch> findByL_P_M(long layoutSetBranchId, long plid,
		boolean master, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P_M;
			finderArgs = new Object[] { layoutSetBranchId, plid, master };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_L_P_M;
			finderArgs = new Object[] {
					layoutSetBranchId, plid, master,
					
					start, end, orderByComparator
				};
		}

		List<LayoutBranch> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutBranch>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutBranch layoutBranch : list) {
					if ((layoutSetBranchId != layoutBranch.getLayoutSetBranchId()) ||
							(plid != layoutBranch.getPlid()) ||
							(master != layoutBranch.getMaster())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_L_P_M_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_M_PLID_2);

			query.append(_FINDER_COLUMN_L_P_M_MASTER_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutBranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

				qPos.add(master);

				if (!pagination) {
					list = (List<LayoutBranch>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutBranch>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout branch
	 * @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch findByL_P_M_First(long layoutSetBranchId, long plid,
		boolean master, OrderByComparator<LayoutBranch> orderByComparator)
		throws NoSuchLayoutBranchException {
		LayoutBranch layoutBranch = fetchByL_P_M_First(layoutSetBranchId, plid,
				master, orderByComparator);

		if (layoutBranch != null) {
			return layoutBranch;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(", master=");
		msg.append(master);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutBranchException(msg.toString());
	}

	/**
	 * Returns the first layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch fetchByL_P_M_First(long layoutSetBranchId, long plid,
		boolean master, OrderByComparator<LayoutBranch> orderByComparator) {
		List<LayoutBranch> list = findByL_P_M(layoutSetBranchId, plid, master,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout branch
	 * @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch findByL_P_M_Last(long layoutSetBranchId, long plid,
		boolean master, OrderByComparator<LayoutBranch> orderByComparator)
		throws NoSuchLayoutBranchException {
		LayoutBranch layoutBranch = fetchByL_P_M_Last(layoutSetBranchId, plid,
				master, orderByComparator);

		if (layoutBranch != null) {
			return layoutBranch;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(", master=");
		msg.append(master);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutBranchException(msg.toString());
	}

	/**
	 * Returns the last layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 */
	@Override
	public LayoutBranch fetchByL_P_M_Last(long layoutSetBranchId, long plid,
		boolean master, OrderByComparator<LayoutBranch> orderByComparator) {
		int count = countByL_P_M(layoutSetBranchId, plid, master);

		if (count == 0) {
			return null;
		}

		List<LayoutBranch> list = findByL_P_M(layoutSetBranchId, plid, master,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout branchs before and after the current layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * @param layoutBranchId the primary key of the current layout branch
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout branch
	 * @throws NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 */
	@Override
	public LayoutBranch[] findByL_P_M_PrevAndNext(long layoutBranchId,
		long layoutSetBranchId, long plid, boolean master,
		OrderByComparator<LayoutBranch> orderByComparator)
		throws NoSuchLayoutBranchException {
		LayoutBranch layoutBranch = findByPrimaryKey(layoutBranchId);

		Session session = null;

		try {
			session = openSession();

			LayoutBranch[] array = new LayoutBranchImpl[3];

			array[0] = getByL_P_M_PrevAndNext(session, layoutBranch,
					layoutSetBranchId, plid, master, orderByComparator, true);

			array[1] = layoutBranch;

			array[2] = getByL_P_M_PrevAndNext(session, layoutBranch,
					layoutSetBranchId, plid, master, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutBranch getByL_P_M_PrevAndNext(Session session,
		LayoutBranch layoutBranch, long layoutSetBranchId, long plid,
		boolean master, OrderByComparator<LayoutBranch> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTBRANCH_WHERE);

		query.append(_FINDER_COLUMN_L_P_M_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_P_M_PLID_2);

		query.append(_FINDER_COLUMN_L_P_M_MASTER_2);

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
			query.append(LayoutBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(plid);

		qPos.add(master);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutBranch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout branchs where layoutSetBranchId = &#63; and plid = &#63; and master = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 */
	@Override
	public void removeByL_P_M(long layoutSetBranchId, long plid, boolean master) {
		for (LayoutBranch layoutBranch : findByL_P_M(layoutSetBranchId, plid,
				master, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutBranch);
		}
	}

	/**
	 * Returns the number of layout branchs where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param master the master
	 * @return the number of matching layout branchs
	 */
	@Override
	public int countByL_P_M(long layoutSetBranchId, long plid, boolean master) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_L_P_M;

		Object[] finderArgs = new Object[] { layoutSetBranchId, plid, master };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_L_P_M_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_M_PLID_2);

			query.append(_FINDER_COLUMN_L_P_M_MASTER_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

				qPos.add(master);

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

	private static final String _FINDER_COLUMN_L_P_M_LAYOUTSETBRANCHID_2 = "layoutBranch.layoutSetBranchId = ? AND ";
	private static final String _FINDER_COLUMN_L_P_M_PLID_2 = "layoutBranch.plid = ? AND ";
	private static final String _FINDER_COLUMN_L_P_M_MASTER_2 = "layoutBranch.master = ?";

	public LayoutBranchPersistenceImpl() {
		setModelClass(LayoutBranch.class);
	}

	/**
	 * Caches the layout branch in the entity cache if it is enabled.
	 *
	 * @param layoutBranch the layout branch
	 */
	@Override
	public void cacheResult(LayoutBranch layoutBranch) {
		entityCache.putResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchImpl.class, layoutBranch.getPrimaryKey(), layoutBranch);

		finderCache.putResult(FINDER_PATH_FETCH_BY_L_P_N,
			new Object[] {
				layoutBranch.getLayoutSetBranchId(), layoutBranch.getPlid(),
				layoutBranch.getName()
			}, layoutBranch);

		layoutBranch.resetOriginalValues();
	}

	/**
	 * Caches the layout branchs in the entity cache if it is enabled.
	 *
	 * @param layoutBranchs the layout branchs
	 */
	@Override
	public void cacheResult(List<LayoutBranch> layoutBranchs) {
		for (LayoutBranch layoutBranch : layoutBranchs) {
			if (entityCache.getResult(
						LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
						LayoutBranchImpl.class, layoutBranch.getPrimaryKey()) == null) {
				cacheResult(layoutBranch);
			}
			else {
				layoutBranch.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout branchs.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutBranchImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout branch.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutBranch layoutBranch) {
		entityCache.removeResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchImpl.class, layoutBranch.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LayoutBranchModelImpl)layoutBranch);
	}

	@Override
	public void clearCache(List<LayoutBranch> layoutBranchs) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutBranch layoutBranch : layoutBranchs) {
			entityCache.removeResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutBranchImpl.class, layoutBranch.getPrimaryKey());

			clearUniqueFindersCache((LayoutBranchModelImpl)layoutBranch);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutBranchModelImpl layoutBranchModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					layoutBranchModelImpl.getLayoutSetBranchId(),
					layoutBranchModelImpl.getPlid(),
					layoutBranchModelImpl.getName()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_L_P_N, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_L_P_N, args,
				layoutBranchModelImpl);
		}
		else {
			if ((layoutBranchModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_L_P_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutBranchModelImpl.getLayoutSetBranchId(),
						layoutBranchModelImpl.getPlid(),
						layoutBranchModelImpl.getName()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_L_P_N, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_L_P_N, args,
					layoutBranchModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		LayoutBranchModelImpl layoutBranchModelImpl) {
		Object[] args = new Object[] {
				layoutBranchModelImpl.getLayoutSetBranchId(),
				layoutBranchModelImpl.getPlid(), layoutBranchModelImpl.getName()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_L_P_N, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_L_P_N, args);

		if ((layoutBranchModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_L_P_N.getColumnBitmask()) != 0) {
			args = new Object[] {
					layoutBranchModelImpl.getOriginalLayoutSetBranchId(),
					layoutBranchModelImpl.getOriginalPlid(),
					layoutBranchModelImpl.getOriginalName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_L_P_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_L_P_N, args);
		}
	}

	/**
	 * Creates a new layout branch with the primary key. Does not add the layout branch to the database.
	 *
	 * @param layoutBranchId the primary key for the new layout branch
	 * @return the new layout branch
	 */
	@Override
	public LayoutBranch create(long layoutBranchId) {
		LayoutBranch layoutBranch = new LayoutBranchImpl();

		layoutBranch.setNew(true);
		layoutBranch.setPrimaryKey(layoutBranchId);

		return layoutBranch;
	}

	/**
	 * Removes the layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutBranchId the primary key of the layout branch
	 * @return the layout branch that was removed
	 * @throws NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 */
	@Override
	public LayoutBranch remove(long layoutBranchId)
		throws NoSuchLayoutBranchException {
		return remove((Serializable)layoutBranchId);
	}

	/**
	 * Removes the layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout branch
	 * @return the layout branch that was removed
	 * @throws NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 */
	@Override
	public LayoutBranch remove(Serializable primaryKey)
		throws NoSuchLayoutBranchException {
		Session session = null;

		try {
			session = openSession();

			LayoutBranch layoutBranch = (LayoutBranch)session.get(LayoutBranchImpl.class,
					primaryKey);

			if (layoutBranch == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLayoutBranchException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(layoutBranch);
		}
		catch (NoSuchLayoutBranchException nsee) {
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
	protected LayoutBranch removeImpl(LayoutBranch layoutBranch) {
		layoutBranch = toUnwrappedModel(layoutBranch);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutBranch)) {
				layoutBranch = (LayoutBranch)session.get(LayoutBranchImpl.class,
						layoutBranch.getPrimaryKeyObj());
			}

			if (layoutBranch != null) {
				session.delete(layoutBranch);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutBranch != null) {
			clearCache(layoutBranch);
		}

		return layoutBranch;
	}

	@Override
	public LayoutBranch updateImpl(LayoutBranch layoutBranch) {
		layoutBranch = toUnwrappedModel(layoutBranch);

		boolean isNew = layoutBranch.isNew();

		LayoutBranchModelImpl layoutBranchModelImpl = (LayoutBranchModelImpl)layoutBranch;

		Session session = null;

		try {
			session = openSession();

			if (layoutBranch.isNew()) {
				session.save(layoutBranch);

				layoutBranch.setNew(false);
			}
			else {
				layoutBranch = (LayoutBranch)session.merge(layoutBranch);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !LayoutBranchModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((layoutBranchModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETBRANCHID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutBranchModelImpl.getOriginalLayoutSetBranchId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LAYOUTSETBRANCHID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETBRANCHID,
					args);

				args = new Object[] { layoutBranchModelImpl.getLayoutSetBranchId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LAYOUTSETBRANCHID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETBRANCHID,
					args);
			}

			if ((layoutBranchModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutBranchModelImpl.getOriginalLayoutSetBranchId(),
						layoutBranchModelImpl.getOriginalPlid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_L_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P,
					args);

				args = new Object[] {
						layoutBranchModelImpl.getLayoutSetBranchId(),
						layoutBranchModelImpl.getPlid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_L_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P,
					args);
			}

			if ((layoutBranchModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P_M.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutBranchModelImpl.getOriginalLayoutSetBranchId(),
						layoutBranchModelImpl.getOriginalPlid(),
						layoutBranchModelImpl.getOriginalMaster()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_L_P_M, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P_M,
					args);

				args = new Object[] {
						layoutBranchModelImpl.getLayoutSetBranchId(),
						layoutBranchModelImpl.getPlid(),
						layoutBranchModelImpl.getMaster()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_L_P_M, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P_M,
					args);
			}
		}

		entityCache.putResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchImpl.class, layoutBranch.getPrimaryKey(), layoutBranch,
			false);

		clearUniqueFindersCache(layoutBranchModelImpl);
		cacheUniqueFindersCache(layoutBranchModelImpl, isNew);

		layoutBranch.resetOriginalValues();

		return layoutBranch;
	}

	protected LayoutBranch toUnwrappedModel(LayoutBranch layoutBranch) {
		if (layoutBranch instanceof LayoutBranchImpl) {
			return layoutBranch;
		}

		LayoutBranchImpl layoutBranchImpl = new LayoutBranchImpl();

		layoutBranchImpl.setNew(layoutBranch.isNew());
		layoutBranchImpl.setPrimaryKey(layoutBranch.getPrimaryKey());

		layoutBranchImpl.setMvccVersion(layoutBranch.getMvccVersion());
		layoutBranchImpl.setLayoutBranchId(layoutBranch.getLayoutBranchId());
		layoutBranchImpl.setGroupId(layoutBranch.getGroupId());
		layoutBranchImpl.setCompanyId(layoutBranch.getCompanyId());
		layoutBranchImpl.setUserId(layoutBranch.getUserId());
		layoutBranchImpl.setUserName(layoutBranch.getUserName());
		layoutBranchImpl.setLayoutSetBranchId(layoutBranch.getLayoutSetBranchId());
		layoutBranchImpl.setPlid(layoutBranch.getPlid());
		layoutBranchImpl.setName(layoutBranch.getName());
		layoutBranchImpl.setDescription(layoutBranch.getDescription());
		layoutBranchImpl.setMaster(layoutBranch.isMaster());

		return layoutBranchImpl;
	}

	/**
	 * Returns the layout branch with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout branch
	 * @return the layout branch
	 * @throws NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 */
	@Override
	public LayoutBranch findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLayoutBranchException {
		LayoutBranch layoutBranch = fetchByPrimaryKey(primaryKey);

		if (layoutBranch == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLayoutBranchException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return layoutBranch;
	}

	/**
	 * Returns the layout branch with the primary key or throws a {@link NoSuchLayoutBranchException} if it could not be found.
	 *
	 * @param layoutBranchId the primary key of the layout branch
	 * @return the layout branch
	 * @throws NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 */
	@Override
	public LayoutBranch findByPrimaryKey(long layoutBranchId)
		throws NoSuchLayoutBranchException {
		return findByPrimaryKey((Serializable)layoutBranchId);
	}

	/**
	 * Returns the layout branch with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout branch
	 * @return the layout branch, or <code>null</code> if a layout branch with the primary key could not be found
	 */
	@Override
	public LayoutBranch fetchByPrimaryKey(Serializable primaryKey) {
		LayoutBranch layoutBranch = (LayoutBranch)entityCache.getResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutBranchImpl.class, primaryKey);

		if (layoutBranch == _nullLayoutBranch) {
			return null;
		}

		if (layoutBranch == null) {
			Session session = null;

			try {
				session = openSession();

				layoutBranch = (LayoutBranch)session.get(LayoutBranchImpl.class,
						primaryKey);

				if (layoutBranch != null) {
					cacheResult(layoutBranch);
				}
				else {
					entityCache.putResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
						LayoutBranchImpl.class, primaryKey, _nullLayoutBranch);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
					LayoutBranchImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return layoutBranch;
	}

	/**
	 * Returns the layout branch with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutBranchId the primary key of the layout branch
	 * @return the layout branch, or <code>null</code> if a layout branch with the primary key could not be found
	 */
	@Override
	public LayoutBranch fetchByPrimaryKey(long layoutBranchId) {
		return fetchByPrimaryKey((Serializable)layoutBranchId);
	}

	@Override
	public Map<Serializable, LayoutBranch> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LayoutBranch> map = new HashMap<Serializable, LayoutBranch>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LayoutBranch layoutBranch = fetchByPrimaryKey(primaryKey);

			if (layoutBranch != null) {
				map.put(primaryKey, layoutBranch);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			LayoutBranch layoutBranch = (LayoutBranch)entityCache.getResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
					LayoutBranchImpl.class, primaryKey);

			if (layoutBranch == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, layoutBranch);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_LAYOUTBRANCH_WHERE_PKS_IN);

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

			for (LayoutBranch layoutBranch : (List<LayoutBranch>)q.list()) {
				map.put(layoutBranch.getPrimaryKeyObj(), layoutBranch);

				cacheResult(layoutBranch);

				uncachedPrimaryKeys.remove(layoutBranch.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
					LayoutBranchImpl.class, primaryKey, _nullLayoutBranch);
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
	 * Returns all the layout branchs.
	 *
	 * @return the layout branchs
	 */
	@Override
	public List<LayoutBranch> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @return the range of layout branchs
	 */
	@Override
	public List<LayoutBranch> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout branchs
	 */
	@Override
	public List<LayoutBranch> findAll(int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout branchs
	 * @param end the upper bound of the range of layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout branchs
	 */
	@Override
	public List<LayoutBranch> findAll(int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator,
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

		List<LayoutBranch> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutBranch>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LAYOUTBRANCH);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTBRANCH;

				if (pagination) {
					sql = sql.concat(LayoutBranchModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutBranch>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutBranch>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the layout branchs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutBranch layoutBranch : findAll()) {
			remove(layoutBranch);
		}
	}

	/**
	 * Returns the number of layout branchs.
	 *
	 * @return the number of layout branchs
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTBRANCH);

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
		return LayoutBranchModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout branch persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(LayoutBranchImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_LAYOUTBRANCH = "SELECT layoutBranch FROM LayoutBranch layoutBranch";
	private static final String _SQL_SELECT_LAYOUTBRANCH_WHERE_PKS_IN = "SELECT layoutBranch FROM LayoutBranch layoutBranch WHERE layoutBranchId IN (";
	private static final String _SQL_SELECT_LAYOUTBRANCH_WHERE = "SELECT layoutBranch FROM LayoutBranch layoutBranch WHERE ";
	private static final String _SQL_COUNT_LAYOUTBRANCH = "SELECT COUNT(layoutBranch) FROM LayoutBranch layoutBranch";
	private static final String _SQL_COUNT_LAYOUTBRANCH_WHERE = "SELECT COUNT(layoutBranch) FROM LayoutBranch layoutBranch WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutBranch.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutBranch exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutBranch exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LayoutBranchPersistenceImpl.class);
	private static final LayoutBranch _nullLayoutBranch = new LayoutBranchImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<LayoutBranch> toCacheModel() {
				return _nullLayoutBranchCacheModel;
			}
		};

	private static final CacheModel<LayoutBranch> _nullLayoutBranchCacheModel = new NullCacheModel();

	private static class NullCacheModel implements CacheModel<LayoutBranch>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public LayoutBranch toEntityModel() {
			return _nullLayoutBranch;
		}
	}
}