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

package com.liferay.layout.page.template.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.exception.NoSuchPageTemplateFragmentException;
import com.liferay.layout.page.template.model.LayoutPageTemplateFragment;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateFragmentImpl;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateFragmentModelImpl;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateFragmentPersistence;

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
 * The persistence implementation for the layout page template fragment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateFragmentPersistence
 * @see com.liferay.layout.page.template.service.persistence.LayoutPageTemplateFragmentUtil
 * @generated
 */
@ProviderType
public class LayoutPageTemplateFragmentPersistenceImpl
	extends BasePersistenceImpl<LayoutPageTemplateFragment>
	implements LayoutPageTemplateFragmentPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LayoutPageTemplateFragmentUtil} to access the layout page template fragment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutPageTemplateFragmentImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateFragmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateFragmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateFragmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateFragmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			LayoutPageTemplateFragmentModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the layout page template fragments where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template fragments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template fragments
	 * @param end the upper bound of the range of layout page template fragments (not inclusive)
	 * @return the range of matching layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template fragments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template fragments
	 * @param end the upper bound of the range of layout page template fragments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template fragments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template fragments
	 * @param end the upper bound of the range of layout page template fragments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator,
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

		List<LayoutPageTemplateFragment> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateFragment>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateFragment layoutPageTemplateFragment : list) {
					if ((groupId != layoutPageTemplateFragment.getGroupId())) {
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

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFRAGMENT_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateFragmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<LayoutPageTemplateFragment>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateFragment>)QueryUtil.list(q,
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
	 * Returns the first layout page template fragment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template fragment
	 * @throws NoSuchPageTemplateFragmentException if a matching layout page template fragment could not be found
	 */
	@Override
	public LayoutPageTemplateFragment findByGroupId_First(long groupId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException {
		LayoutPageTemplateFragment layoutPageTemplateFragment = fetchByGroupId_First(groupId,
				orderByComparator);

		if (layoutPageTemplateFragment != null) {
			return layoutPageTemplateFragment;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchPageTemplateFragmentException(msg.toString());
	}

	/**
	 * Returns the first layout page template fragment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template fragment, or <code>null</code> if a matching layout page template fragment could not be found
	 */
	@Override
	public LayoutPageTemplateFragment fetchByGroupId_First(long groupId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator) {
		List<LayoutPageTemplateFragment> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template fragment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template fragment
	 * @throws NoSuchPageTemplateFragmentException if a matching layout page template fragment could not be found
	 */
	@Override
	public LayoutPageTemplateFragment findByGroupId_Last(long groupId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException {
		LayoutPageTemplateFragment layoutPageTemplateFragment = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (layoutPageTemplateFragment != null) {
			return layoutPageTemplateFragment;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchPageTemplateFragmentException(msg.toString());
	}

	/**
	 * Returns the last layout page template fragment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template fragment, or <code>null</code> if a matching layout page template fragment could not be found
	 */
	@Override
	public LayoutPageTemplateFragment fetchByGroupId_Last(long groupId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateFragment> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template fragments before and after the current layout page template fragment in the ordered set where groupId = &#63;.
	 *
	 * @param layoutPageTemplateFragmentId the primary key of the current layout page template fragment
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template fragment
	 * @throws NoSuchPageTemplateFragmentException if a layout page template fragment with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFragment[] findByGroupId_PrevAndNext(
		long layoutPageTemplateFragmentId, long groupId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException {
		LayoutPageTemplateFragment layoutPageTemplateFragment = findByPrimaryKey(layoutPageTemplateFragmentId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateFragment[] array = new LayoutPageTemplateFragmentImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					layoutPageTemplateFragment, groupId, orderByComparator, true);

			array[1] = layoutPageTemplateFragment;

			array[2] = getByGroupId_PrevAndNext(session,
					layoutPageTemplateFragment, groupId, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateFragment getByGroupId_PrevAndNext(
		Session session, LayoutPageTemplateFragment layoutPageTemplateFragment,
		long groupId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFRAGMENT_WHERE);

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
			query.append(LayoutPageTemplateFragmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateFragment);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateFragment> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template fragments where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (LayoutPageTemplateFragment layoutPageTemplateFragment : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateFragment);
		}
	}

	/**
	 * Returns the number of layout page template fragments where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout page template fragments
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEFRAGMENT_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "layoutPageTemplateFragment.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_L = new FinderPath(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateFragmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L = new FinderPath(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateFragmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_L",
			new String[] { Long.class.getName(), Long.class.getName() },
			LayoutPageTemplateFragmentModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateFragmentModelImpl.LAYOUTPAGETEMPLATEENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_L = new FinderPath(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_L",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the matching layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findByG_L(long groupId,
		long layoutPageTemplateEntryId) {
		return findByG_L(groupId, layoutPageTemplateEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of layout page template fragments
	 * @param end the upper bound of the range of layout page template fragments (not inclusive)
	 * @return the range of matching layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findByG_L(long groupId,
		long layoutPageTemplateEntryId, int start, int end) {
		return findByG_L(groupId, layoutPageTemplateEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of layout page template fragments
	 * @param end the upper bound of the range of layout page template fragments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findByG_L(long groupId,
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator) {
		return findByG_L(groupId, layoutPageTemplateEntryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of layout page template fragments
	 * @param end the upper bound of the range of layout page template fragments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findByG_L(long groupId,
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L;
			finderArgs = new Object[] { groupId, layoutPageTemplateEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_L;
			finderArgs = new Object[] {
					groupId, layoutPageTemplateEntryId,
					
					start, end, orderByComparator
				};
		}

		List<LayoutPageTemplateFragment> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateFragment>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateFragment layoutPageTemplateFragment : list) {
					if ((groupId != layoutPageTemplateFragment.getGroupId()) ||
							(layoutPageTemplateEntryId != layoutPageTemplateFragment.getLayoutPageTemplateEntryId())) {
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

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFRAGMENT_WHERE);

			query.append(_FINDER_COLUMN_G_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateFragmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(layoutPageTemplateEntryId);

				if (!pagination) {
					list = (List<LayoutPageTemplateFragment>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateFragment>)QueryUtil.list(q,
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
	 * Returns the first layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template fragment
	 * @throws NoSuchPageTemplateFragmentException if a matching layout page template fragment could not be found
	 */
	@Override
	public LayoutPageTemplateFragment findByG_L_First(long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException {
		LayoutPageTemplateFragment layoutPageTemplateFragment = fetchByG_L_First(groupId,
				layoutPageTemplateEntryId, orderByComparator);

		if (layoutPageTemplateFragment != null) {
			return layoutPageTemplateFragment;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutPageTemplateEntryId=");
		msg.append(layoutPageTemplateEntryId);

		msg.append("}");

		throw new NoSuchPageTemplateFragmentException(msg.toString());
	}

	/**
	 * Returns the first layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template fragment, or <code>null</code> if a matching layout page template fragment could not be found
	 */
	@Override
	public LayoutPageTemplateFragment fetchByG_L_First(long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator) {
		List<LayoutPageTemplateFragment> list = findByG_L(groupId,
				layoutPageTemplateEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template fragment
	 * @throws NoSuchPageTemplateFragmentException if a matching layout page template fragment could not be found
	 */
	@Override
	public LayoutPageTemplateFragment findByG_L_Last(long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException {
		LayoutPageTemplateFragment layoutPageTemplateFragment = fetchByG_L_Last(groupId,
				layoutPageTemplateEntryId, orderByComparator);

		if (layoutPageTemplateFragment != null) {
			return layoutPageTemplateFragment;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutPageTemplateEntryId=");
		msg.append(layoutPageTemplateEntryId);

		msg.append("}");

		throw new NoSuchPageTemplateFragmentException(msg.toString());
	}

	/**
	 * Returns the last layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template fragment, or <code>null</code> if a matching layout page template fragment could not be found
	 */
	@Override
	public LayoutPageTemplateFragment fetchByG_L_Last(long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator) {
		int count = countByG_L(groupId, layoutPageTemplateEntryId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateFragment> list = findByG_L(groupId,
				layoutPageTemplateEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template fragments before and after the current layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateFragmentId the primary key of the current layout page template fragment
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template fragment
	 * @throws NoSuchPageTemplateFragmentException if a layout page template fragment with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFragment[] findByG_L_PrevAndNext(
		long layoutPageTemplateFragmentId, long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException {
		LayoutPageTemplateFragment layoutPageTemplateFragment = findByPrimaryKey(layoutPageTemplateFragmentId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateFragment[] array = new LayoutPageTemplateFragmentImpl[3];

			array[0] = getByG_L_PrevAndNext(session,
					layoutPageTemplateFragment, groupId,
					layoutPageTemplateEntryId, orderByComparator, true);

			array[1] = layoutPageTemplateFragment;

			array[2] = getByG_L_PrevAndNext(session,
					layoutPageTemplateFragment, groupId,
					layoutPageTemplateEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateFragment getByG_L_PrevAndNext(Session session,
		LayoutPageTemplateFragment layoutPageTemplateFragment, long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFRAGMENT_WHERE);

		query.append(_FINDER_COLUMN_G_L_GROUPID_2);

		query.append(_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATEENTRYID_2);

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
			query.append(LayoutPageTemplateFragmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(layoutPageTemplateEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateFragment);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateFragment> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 */
	@Override
	public void removeByG_L(long groupId, long layoutPageTemplateEntryId) {
		for (LayoutPageTemplateFragment layoutPageTemplateFragment : findByG_L(
				groupId, layoutPageTemplateEntryId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateFragment);
		}
	}

	/**
	 * Returns the number of layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the number of matching layout page template fragments
	 */
	@Override
	public int countByG_L(long groupId, long layoutPageTemplateEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_L;

		Object[] finderArgs = new Object[] { groupId, layoutPageTemplateEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEFRAGMENT_WHERE);

			query.append(_FINDER_COLUMN_G_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(layoutPageTemplateEntryId);

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

	private static final String _FINDER_COLUMN_G_L_GROUPID_2 = "layoutPageTemplateFragment.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATEENTRYID_2 = "layoutPageTemplateFragment.layoutPageTemplateEntryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F = new FinderPath(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateFragmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F = new FinderPath(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateFragmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F",
			new String[] { Long.class.getName(), Long.class.getName() },
			LayoutPageTemplateFragmentModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateFragmentModelImpl.FRAGMENTENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the layout page template fragments where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @return the matching layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findByG_F(long groupId,
		long fragmentEntryId) {
		return findByG_F(groupId, fragmentEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template fragments where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of layout page template fragments
	 * @param end the upper bound of the range of layout page template fragments (not inclusive)
	 * @return the range of matching layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findByG_F(long groupId,
		long fragmentEntryId, int start, int end) {
		return findByG_F(groupId, fragmentEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template fragments where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of layout page template fragments
	 * @param end the upper bound of the range of layout page template fragments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findByG_F(long groupId,
		long fragmentEntryId, int start, int end,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator) {
		return findByG_F(groupId, fragmentEntryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template fragments where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of layout page template fragments
	 * @param end the upper bound of the range of layout page template fragments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findByG_F(long groupId,
		long fragmentEntryId, int start, int end,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F;
			finderArgs = new Object[] { groupId, fragmentEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F;
			finderArgs = new Object[] {
					groupId, fragmentEntryId,
					
					start, end, orderByComparator
				};
		}

		List<LayoutPageTemplateFragment> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateFragment>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateFragment layoutPageTemplateFragment : list) {
					if ((groupId != layoutPageTemplateFragment.getGroupId()) ||
							(fragmentEntryId != layoutPageTemplateFragment.getFragmentEntryId())) {
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

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFRAGMENT_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FRAGMENTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateFragmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(fragmentEntryId);

				if (!pagination) {
					list = (List<LayoutPageTemplateFragment>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateFragment>)QueryUtil.list(q,
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
	 * Returns the first layout page template fragment in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template fragment
	 * @throws NoSuchPageTemplateFragmentException if a matching layout page template fragment could not be found
	 */
	@Override
	public LayoutPageTemplateFragment findByG_F_First(long groupId,
		long fragmentEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException {
		LayoutPageTemplateFragment layoutPageTemplateFragment = fetchByG_F_First(groupId,
				fragmentEntryId, orderByComparator);

		if (layoutPageTemplateFragment != null) {
			return layoutPageTemplateFragment;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", fragmentEntryId=");
		msg.append(fragmentEntryId);

		msg.append("}");

		throw new NoSuchPageTemplateFragmentException(msg.toString());
	}

	/**
	 * Returns the first layout page template fragment in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template fragment, or <code>null</code> if a matching layout page template fragment could not be found
	 */
	@Override
	public LayoutPageTemplateFragment fetchByG_F_First(long groupId,
		long fragmentEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator) {
		List<LayoutPageTemplateFragment> list = findByG_F(groupId,
				fragmentEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template fragment in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template fragment
	 * @throws NoSuchPageTemplateFragmentException if a matching layout page template fragment could not be found
	 */
	@Override
	public LayoutPageTemplateFragment findByG_F_Last(long groupId,
		long fragmentEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException {
		LayoutPageTemplateFragment layoutPageTemplateFragment = fetchByG_F_Last(groupId,
				fragmentEntryId, orderByComparator);

		if (layoutPageTemplateFragment != null) {
			return layoutPageTemplateFragment;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", fragmentEntryId=");
		msg.append(fragmentEntryId);

		msg.append("}");

		throw new NoSuchPageTemplateFragmentException(msg.toString());
	}

	/**
	 * Returns the last layout page template fragment in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template fragment, or <code>null</code> if a matching layout page template fragment could not be found
	 */
	@Override
	public LayoutPageTemplateFragment fetchByG_F_Last(long groupId,
		long fragmentEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator) {
		int count = countByG_F(groupId, fragmentEntryId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateFragment> list = findByG_F(groupId,
				fragmentEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template fragments before and after the current layout page template fragment in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param layoutPageTemplateFragmentId the primary key of the current layout page template fragment
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template fragment
	 * @throws NoSuchPageTemplateFragmentException if a layout page template fragment with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFragment[] findByG_F_PrevAndNext(
		long layoutPageTemplateFragmentId, long groupId, long fragmentEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException {
		LayoutPageTemplateFragment layoutPageTemplateFragment = findByPrimaryKey(layoutPageTemplateFragmentId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateFragment[] array = new LayoutPageTemplateFragmentImpl[3];

			array[0] = getByG_F_PrevAndNext(session,
					layoutPageTemplateFragment, groupId, fragmentEntryId,
					orderByComparator, true);

			array[1] = layoutPageTemplateFragment;

			array[2] = getByG_F_PrevAndNext(session,
					layoutPageTemplateFragment, groupId, fragmentEntryId,
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

	protected LayoutPageTemplateFragment getByG_F_PrevAndNext(Session session,
		LayoutPageTemplateFragment layoutPageTemplateFragment, long groupId,
		long fragmentEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFRAGMENT_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FRAGMENTENTRYID_2);

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
			query.append(LayoutPageTemplateFragmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(fragmentEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateFragment);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateFragment> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template fragments where groupId = &#63; and fragmentEntryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 */
	@Override
	public void removeByG_F(long groupId, long fragmentEntryId) {
		for (LayoutPageTemplateFragment layoutPageTemplateFragment : findByG_F(
				groupId, fragmentEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(layoutPageTemplateFragment);
		}
	}

	/**
	 * Returns the number of layout page template fragments where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @return the number of matching layout page template fragments
	 */
	@Override
	public int countByG_F(long groupId, long fragmentEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F;

		Object[] finderArgs = new Object[] { groupId, fragmentEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEFRAGMENT_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FRAGMENTENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(fragmentEntryId);

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

	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "layoutPageTemplateFragment.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FRAGMENTENTRYID_2 = "layoutPageTemplateFragment.fragmentEntryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_L_F = new FinderPath(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateFragmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_L_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L_F = new FinderPath(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateFragmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_L_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			LayoutPageTemplateFragmentModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateFragmentModelImpl.LAYOUTPAGETEMPLATEENTRYID_COLUMN_BITMASK |
			LayoutPageTemplateFragmentModelImpl.FRAGMENTENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_L_F = new FinderPath(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_L_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param fragmentEntryId the fragment entry ID
	 * @return the matching layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findByG_L_F(long groupId,
		long layoutPageTemplateEntryId, long fragmentEntryId) {
		return findByG_L_F(groupId, layoutPageTemplateEntryId, fragmentEntryId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of layout page template fragments
	 * @param end the upper bound of the range of layout page template fragments (not inclusive)
	 * @return the range of matching layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findByG_L_F(long groupId,
		long layoutPageTemplateEntryId, long fragmentEntryId, int start, int end) {
		return findByG_L_F(groupId, layoutPageTemplateEntryId, fragmentEntryId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of layout page template fragments
	 * @param end the upper bound of the range of layout page template fragments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findByG_L_F(long groupId,
		long layoutPageTemplateEntryId, long fragmentEntryId, int start,
		int end, OrderByComparator<LayoutPageTemplateFragment> orderByComparator) {
		return findByG_L_F(groupId, layoutPageTemplateEntryId, fragmentEntryId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of layout page template fragments
	 * @param end the upper bound of the range of layout page template fragments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findByG_L_F(long groupId,
		long layoutPageTemplateEntryId, long fragmentEntryId, int start,
		int end,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L_F;
			finderArgs = new Object[] {
					groupId, layoutPageTemplateEntryId, fragmentEntryId
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_L_F;
			finderArgs = new Object[] {
					groupId, layoutPageTemplateEntryId, fragmentEntryId,
					
					start, end, orderByComparator
				};
		}

		List<LayoutPageTemplateFragment> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateFragment>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateFragment layoutPageTemplateFragment : list) {
					if ((groupId != layoutPageTemplateFragment.getGroupId()) ||
							(layoutPageTemplateEntryId != layoutPageTemplateFragment.getLayoutPageTemplateEntryId()) ||
							(fragmentEntryId != layoutPageTemplateFragment.getFragmentEntryId())) {
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

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFRAGMENT_WHERE);

			query.append(_FINDER_COLUMN_G_L_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_L_F_LAYOUTPAGETEMPLATEENTRYID_2);

			query.append(_FINDER_COLUMN_G_L_F_FRAGMENTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateFragmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(layoutPageTemplateEntryId);

				qPos.add(fragmentEntryId);

				if (!pagination) {
					list = (List<LayoutPageTemplateFragment>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateFragment>)QueryUtil.list(q,
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
	 * Returns the first layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template fragment
	 * @throws NoSuchPageTemplateFragmentException if a matching layout page template fragment could not be found
	 */
	@Override
	public LayoutPageTemplateFragment findByG_L_F_First(long groupId,
		long layoutPageTemplateEntryId, long fragmentEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException {
		LayoutPageTemplateFragment layoutPageTemplateFragment = fetchByG_L_F_First(groupId,
				layoutPageTemplateEntryId, fragmentEntryId, orderByComparator);

		if (layoutPageTemplateFragment != null) {
			return layoutPageTemplateFragment;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutPageTemplateEntryId=");
		msg.append(layoutPageTemplateEntryId);

		msg.append(", fragmentEntryId=");
		msg.append(fragmentEntryId);

		msg.append("}");

		throw new NoSuchPageTemplateFragmentException(msg.toString());
	}

	/**
	 * Returns the first layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template fragment, or <code>null</code> if a matching layout page template fragment could not be found
	 */
	@Override
	public LayoutPageTemplateFragment fetchByG_L_F_First(long groupId,
		long layoutPageTemplateEntryId, long fragmentEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator) {
		List<LayoutPageTemplateFragment> list = findByG_L_F(groupId,
				layoutPageTemplateEntryId, fragmentEntryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template fragment
	 * @throws NoSuchPageTemplateFragmentException if a matching layout page template fragment could not be found
	 */
	@Override
	public LayoutPageTemplateFragment findByG_L_F_Last(long groupId,
		long layoutPageTemplateEntryId, long fragmentEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException {
		LayoutPageTemplateFragment layoutPageTemplateFragment = fetchByG_L_F_Last(groupId,
				layoutPageTemplateEntryId, fragmentEntryId, orderByComparator);

		if (layoutPageTemplateFragment != null) {
			return layoutPageTemplateFragment;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutPageTemplateEntryId=");
		msg.append(layoutPageTemplateEntryId);

		msg.append(", fragmentEntryId=");
		msg.append(fragmentEntryId);

		msg.append("}");

		throw new NoSuchPageTemplateFragmentException(msg.toString());
	}

	/**
	 * Returns the last layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template fragment, or <code>null</code> if a matching layout page template fragment could not be found
	 */
	@Override
	public LayoutPageTemplateFragment fetchByG_L_F_Last(long groupId,
		long layoutPageTemplateEntryId, long fragmentEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator) {
		int count = countByG_L_F(groupId, layoutPageTemplateEntryId,
				fragmentEntryId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateFragment> list = findByG_L_F(groupId,
				layoutPageTemplateEntryId, fragmentEntryId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template fragments before and after the current layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param layoutPageTemplateFragmentId the primary key of the current layout page template fragment
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template fragment
	 * @throws NoSuchPageTemplateFragmentException if a layout page template fragment with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFragment[] findByG_L_F_PrevAndNext(
		long layoutPageTemplateFragmentId, long groupId,
		long layoutPageTemplateEntryId, long fragmentEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException {
		LayoutPageTemplateFragment layoutPageTemplateFragment = findByPrimaryKey(layoutPageTemplateFragmentId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateFragment[] array = new LayoutPageTemplateFragmentImpl[3];

			array[0] = getByG_L_F_PrevAndNext(session,
					layoutPageTemplateFragment, groupId,
					layoutPageTemplateEntryId, fragmentEntryId,
					orderByComparator, true);

			array[1] = layoutPageTemplateFragment;

			array[2] = getByG_L_F_PrevAndNext(session,
					layoutPageTemplateFragment, groupId,
					layoutPageTemplateEntryId, fragmentEntryId,
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

	protected LayoutPageTemplateFragment getByG_L_F_PrevAndNext(
		Session session, LayoutPageTemplateFragment layoutPageTemplateFragment,
		long groupId, long layoutPageTemplateEntryId, long fragmentEntryId,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFRAGMENT_WHERE);

		query.append(_FINDER_COLUMN_G_L_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_L_F_LAYOUTPAGETEMPLATEENTRYID_2);

		query.append(_FINDER_COLUMN_G_L_F_FRAGMENTENTRYID_2);

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
			query.append(LayoutPageTemplateFragmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(layoutPageTemplateEntryId);

		qPos.add(fragmentEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateFragment);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateFragment> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param fragmentEntryId the fragment entry ID
	 */
	@Override
	public void removeByG_L_F(long groupId, long layoutPageTemplateEntryId,
		long fragmentEntryId) {
		for (LayoutPageTemplateFragment layoutPageTemplateFragment : findByG_L_F(
				groupId, layoutPageTemplateEntryId, fragmentEntryId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateFragment);
		}
	}

	/**
	 * Returns the number of layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param fragmentEntryId the fragment entry ID
	 * @return the number of matching layout page template fragments
	 */
	@Override
	public int countByG_L_F(long groupId, long layoutPageTemplateEntryId,
		long fragmentEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_L_F;

		Object[] finderArgs = new Object[] {
				groupId, layoutPageTemplateEntryId, fragmentEntryId
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEFRAGMENT_WHERE);

			query.append(_FINDER_COLUMN_G_L_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_L_F_LAYOUTPAGETEMPLATEENTRYID_2);

			query.append(_FINDER_COLUMN_G_L_F_FRAGMENTENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(layoutPageTemplateEntryId);

				qPos.add(fragmentEntryId);

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

	private static final String _FINDER_COLUMN_G_L_F_GROUPID_2 = "layoutPageTemplateFragment.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_L_F_LAYOUTPAGETEMPLATEENTRYID_2 =
		"layoutPageTemplateFragment.layoutPageTemplateEntryId = ? AND ";
	private static final String _FINDER_COLUMN_G_L_F_FRAGMENTENTRYID_2 = "layoutPageTemplateFragment.fragmentEntryId = ?";

	public LayoutPageTemplateFragmentPersistenceImpl() {
		setModelClass(LayoutPageTemplateFragment.class);
	}

	/**
	 * Caches the layout page template fragment in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateFragment the layout page template fragment
	 */
	@Override
	public void cacheResult(
		LayoutPageTemplateFragment layoutPageTemplateFragment) {
		entityCache.putResult(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentImpl.class,
			layoutPageTemplateFragment.getPrimaryKey(),
			layoutPageTemplateFragment);

		layoutPageTemplateFragment.resetOriginalValues();
	}

	/**
	 * Caches the layout page template fragments in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateFragments the layout page template fragments
	 */
	@Override
	public void cacheResult(
		List<LayoutPageTemplateFragment> layoutPageTemplateFragments) {
		for (LayoutPageTemplateFragment layoutPageTemplateFragment : layoutPageTemplateFragments) {
			if (entityCache.getResult(
						LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
						LayoutPageTemplateFragmentImpl.class,
						layoutPageTemplateFragment.getPrimaryKey()) == null) {
				cacheResult(layoutPageTemplateFragment);
			}
			else {
				layoutPageTemplateFragment.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout page template fragments.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutPageTemplateFragmentImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout page template fragment.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		LayoutPageTemplateFragment layoutPageTemplateFragment) {
		entityCache.removeResult(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentImpl.class,
			layoutPageTemplateFragment.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<LayoutPageTemplateFragment> layoutPageTemplateFragments) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutPageTemplateFragment layoutPageTemplateFragment : layoutPageTemplateFragments) {
			entityCache.removeResult(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateFragmentImpl.class,
				layoutPageTemplateFragment.getPrimaryKey());
		}
	}

	/**
	 * Creates a new layout page template fragment with the primary key. Does not add the layout page template fragment to the database.
	 *
	 * @param layoutPageTemplateFragmentId the primary key for the new layout page template fragment
	 * @return the new layout page template fragment
	 */
	@Override
	public LayoutPageTemplateFragment create(long layoutPageTemplateFragmentId) {
		LayoutPageTemplateFragment layoutPageTemplateFragment = new LayoutPageTemplateFragmentImpl();

		layoutPageTemplateFragment.setNew(true);
		layoutPageTemplateFragment.setPrimaryKey(layoutPageTemplateFragmentId);

		layoutPageTemplateFragment.setCompanyId(companyProvider.getCompanyId());

		return layoutPageTemplateFragment;
	}

	/**
	 * Removes the layout page template fragment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateFragmentId the primary key of the layout page template fragment
	 * @return the layout page template fragment that was removed
	 * @throws NoSuchPageTemplateFragmentException if a layout page template fragment with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFragment remove(long layoutPageTemplateFragmentId)
		throws NoSuchPageTemplateFragmentException {
		return remove((Serializable)layoutPageTemplateFragmentId);
	}

	/**
	 * Removes the layout page template fragment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout page template fragment
	 * @return the layout page template fragment that was removed
	 * @throws NoSuchPageTemplateFragmentException if a layout page template fragment with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFragment remove(Serializable primaryKey)
		throws NoSuchPageTemplateFragmentException {
		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateFragment layoutPageTemplateFragment = (LayoutPageTemplateFragment)session.get(LayoutPageTemplateFragmentImpl.class,
					primaryKey);

			if (layoutPageTemplateFragment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPageTemplateFragmentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(layoutPageTemplateFragment);
		}
		catch (NoSuchPageTemplateFragmentException nsee) {
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
	protected LayoutPageTemplateFragment removeImpl(
		LayoutPageTemplateFragment layoutPageTemplateFragment) {
		layoutPageTemplateFragment = toUnwrappedModel(layoutPageTemplateFragment);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutPageTemplateFragment)) {
				layoutPageTemplateFragment = (LayoutPageTemplateFragment)session.get(LayoutPageTemplateFragmentImpl.class,
						layoutPageTemplateFragment.getPrimaryKeyObj());
			}

			if (layoutPageTemplateFragment != null) {
				session.delete(layoutPageTemplateFragment);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutPageTemplateFragment != null) {
			clearCache(layoutPageTemplateFragment);
		}

		return layoutPageTemplateFragment;
	}

	@Override
	public LayoutPageTemplateFragment updateImpl(
		LayoutPageTemplateFragment layoutPageTemplateFragment) {
		layoutPageTemplateFragment = toUnwrappedModel(layoutPageTemplateFragment);

		boolean isNew = layoutPageTemplateFragment.isNew();

		LayoutPageTemplateFragmentModelImpl layoutPageTemplateFragmentModelImpl = (LayoutPageTemplateFragmentModelImpl)layoutPageTemplateFragment;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutPageTemplateFragment.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutPageTemplateFragment.setCreateDate(now);
			}
			else {
				layoutPageTemplateFragment.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!layoutPageTemplateFragmentModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutPageTemplateFragment.setModifiedDate(now);
			}
			else {
				layoutPageTemplateFragment.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutPageTemplateFragment.isNew()) {
				session.save(layoutPageTemplateFragment);

				layoutPageTemplateFragment.setNew(false);
			}
			else {
				layoutPageTemplateFragment = (LayoutPageTemplateFragment)session.merge(layoutPageTemplateFragment);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LayoutPageTemplateFragmentModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					layoutPageTemplateFragmentModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					layoutPageTemplateFragmentModelImpl.getGroupId(),
					layoutPageTemplateFragmentModelImpl.getLayoutPageTemplateEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_L, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L,
				args);

			args = new Object[] {
					layoutPageTemplateFragmentModelImpl.getGroupId(),
					layoutPageTemplateFragmentModelImpl.getFragmentEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
				args);

			args = new Object[] {
					layoutPageTemplateFragmentModelImpl.getGroupId(),
					layoutPageTemplateFragmentModelImpl.getLayoutPageTemplateEntryId(),
					layoutPageTemplateFragmentModelImpl.getFragmentEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_L_F, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L_F,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((layoutPageTemplateFragmentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateFragmentModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						layoutPageTemplateFragmentModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((layoutPageTemplateFragmentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateFragmentModelImpl.getOriginalGroupId(),
						layoutPageTemplateFragmentModelImpl.getOriginalLayoutPageTemplateEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_L, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L,
					args);

				args = new Object[] {
						layoutPageTemplateFragmentModelImpl.getGroupId(),
						layoutPageTemplateFragmentModelImpl.getLayoutPageTemplateEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_L, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L,
					args);
			}

			if ((layoutPageTemplateFragmentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateFragmentModelImpl.getOriginalGroupId(),
						layoutPageTemplateFragmentModelImpl.getOriginalFragmentEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);

				args = new Object[] {
						layoutPageTemplateFragmentModelImpl.getGroupId(),
						layoutPageTemplateFragmentModelImpl.getFragmentEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);
			}

			if ((layoutPageTemplateFragmentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateFragmentModelImpl.getOriginalGroupId(),
						layoutPageTemplateFragmentModelImpl.getOriginalLayoutPageTemplateEntryId(),
						layoutPageTemplateFragmentModelImpl.getOriginalFragmentEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_L_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L_F,
					args);

				args = new Object[] {
						layoutPageTemplateFragmentModelImpl.getGroupId(),
						layoutPageTemplateFragmentModelImpl.getLayoutPageTemplateEntryId(),
						layoutPageTemplateFragmentModelImpl.getFragmentEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_L_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L_F,
					args);
			}
		}

		entityCache.putResult(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFragmentImpl.class,
			layoutPageTemplateFragment.getPrimaryKey(),
			layoutPageTemplateFragment, false);

		layoutPageTemplateFragment.resetOriginalValues();

		return layoutPageTemplateFragment;
	}

	protected LayoutPageTemplateFragment toUnwrappedModel(
		LayoutPageTemplateFragment layoutPageTemplateFragment) {
		if (layoutPageTemplateFragment instanceof LayoutPageTemplateFragmentImpl) {
			return layoutPageTemplateFragment;
		}

		LayoutPageTemplateFragmentImpl layoutPageTemplateFragmentImpl = new LayoutPageTemplateFragmentImpl();

		layoutPageTemplateFragmentImpl.setNew(layoutPageTemplateFragment.isNew());
		layoutPageTemplateFragmentImpl.setPrimaryKey(layoutPageTemplateFragment.getPrimaryKey());

		layoutPageTemplateFragmentImpl.setLayoutPageTemplateFragmentId(layoutPageTemplateFragment.getLayoutPageTemplateFragmentId());
		layoutPageTemplateFragmentImpl.setGroupId(layoutPageTemplateFragment.getGroupId());
		layoutPageTemplateFragmentImpl.setCompanyId(layoutPageTemplateFragment.getCompanyId());
		layoutPageTemplateFragmentImpl.setUserId(layoutPageTemplateFragment.getUserId());
		layoutPageTemplateFragmentImpl.setUserName(layoutPageTemplateFragment.getUserName());
		layoutPageTemplateFragmentImpl.setCreateDate(layoutPageTemplateFragment.getCreateDate());
		layoutPageTemplateFragmentImpl.setModifiedDate(layoutPageTemplateFragment.getModifiedDate());
		layoutPageTemplateFragmentImpl.setLayoutPageTemplateEntryId(layoutPageTemplateFragment.getLayoutPageTemplateEntryId());
		layoutPageTemplateFragmentImpl.setFragmentEntryId(layoutPageTemplateFragment.getFragmentEntryId());
		layoutPageTemplateFragmentImpl.setPosition(layoutPageTemplateFragment.getPosition());

		return layoutPageTemplateFragmentImpl;
	}

	/**
	 * Returns the layout page template fragment with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout page template fragment
	 * @return the layout page template fragment
	 * @throws NoSuchPageTemplateFragmentException if a layout page template fragment with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFragment findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPageTemplateFragmentException {
		LayoutPageTemplateFragment layoutPageTemplateFragment = fetchByPrimaryKey(primaryKey);

		if (layoutPageTemplateFragment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPageTemplateFragmentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return layoutPageTemplateFragment;
	}

	/**
	 * Returns the layout page template fragment with the primary key or throws a {@link NoSuchPageTemplateFragmentException} if it could not be found.
	 *
	 * @param layoutPageTemplateFragmentId the primary key of the layout page template fragment
	 * @return the layout page template fragment
	 * @throws NoSuchPageTemplateFragmentException if a layout page template fragment with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFragment findByPrimaryKey(
		long layoutPageTemplateFragmentId)
		throws NoSuchPageTemplateFragmentException {
		return findByPrimaryKey((Serializable)layoutPageTemplateFragmentId);
	}

	/**
	 * Returns the layout page template fragment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout page template fragment
	 * @return the layout page template fragment, or <code>null</code> if a layout page template fragment with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFragment fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateFragmentImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LayoutPageTemplateFragment layoutPageTemplateFragment = (LayoutPageTemplateFragment)serializable;

		if (layoutPageTemplateFragment == null) {
			Session session = null;

			try {
				session = openSession();

				layoutPageTemplateFragment = (LayoutPageTemplateFragment)session.get(LayoutPageTemplateFragmentImpl.class,
						primaryKey);

				if (layoutPageTemplateFragment != null) {
					cacheResult(layoutPageTemplateFragment);
				}
				else {
					entityCache.putResult(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
						LayoutPageTemplateFragmentImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPageTemplateFragmentImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return layoutPageTemplateFragment;
	}

	/**
	 * Returns the layout page template fragment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPageTemplateFragmentId the primary key of the layout page template fragment
	 * @return the layout page template fragment, or <code>null</code> if a layout page template fragment with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFragment fetchByPrimaryKey(
		long layoutPageTemplateFragmentId) {
		return fetchByPrimaryKey((Serializable)layoutPageTemplateFragmentId);
	}

	@Override
	public Map<Serializable, LayoutPageTemplateFragment> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LayoutPageTemplateFragment> map = new HashMap<Serializable, LayoutPageTemplateFragment>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LayoutPageTemplateFragment layoutPageTemplateFragment = fetchByPrimaryKey(primaryKey);

			if (layoutPageTemplateFragment != null) {
				map.put(primaryKey, layoutPageTemplateFragment);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPageTemplateFragmentImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (LayoutPageTemplateFragment)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFRAGMENT_WHERE_PKS_IN);

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

			for (LayoutPageTemplateFragment layoutPageTemplateFragment : (List<LayoutPageTemplateFragment>)q.list()) {
				map.put(layoutPageTemplateFragment.getPrimaryKeyObj(),
					layoutPageTemplateFragment);

				cacheResult(layoutPageTemplateFragment);

				uncachedPrimaryKeys.remove(layoutPageTemplateFragment.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(LayoutPageTemplateFragmentModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPageTemplateFragmentImpl.class, primaryKey, nullModel);
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
	 * Returns all the layout page template fragments.
	 *
	 * @return the layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template fragments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template fragments
	 * @param end the upper bound of the range of layout page template fragments (not inclusive)
	 * @return the range of layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template fragments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template fragments
	 * @param end the upper bound of the range of layout page template fragments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findAll(int start, int end,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template fragments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template fragments
	 * @param end the upper bound of the range of layout page template fragments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout page template fragments
	 */
	@Override
	public List<LayoutPageTemplateFragment> findAll(int start, int end,
		OrderByComparator<LayoutPageTemplateFragment> orderByComparator,
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

		List<LayoutPageTemplateFragment> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateFragment>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFRAGMENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTPAGETEMPLATEFRAGMENT;

				if (pagination) {
					sql = sql.concat(LayoutPageTemplateFragmentModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutPageTemplateFragment>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateFragment>)QueryUtil.list(q,
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
	 * Removes all the layout page template fragments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutPageTemplateFragment layoutPageTemplateFragment : findAll()) {
			remove(layoutPageTemplateFragment);
		}
	}

	/**
	 * Returns the number of layout page template fragments.
	 *
	 * @return the number of layout page template fragments
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTPAGETEMPLATEFRAGMENT);

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
		return LayoutPageTemplateFragmentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout page template fragment persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(LayoutPageTemplateFragmentImpl.class.getName());
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
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATEFRAGMENT = "SELECT layoutPageTemplateFragment FROM LayoutPageTemplateFragment layoutPageTemplateFragment";
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATEFRAGMENT_WHERE_PKS_IN =
		"SELECT layoutPageTemplateFragment FROM LayoutPageTemplateFragment layoutPageTemplateFragment WHERE layoutPageTemplateFragmentId IN (";
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATEFRAGMENT_WHERE = "SELECT layoutPageTemplateFragment FROM LayoutPageTemplateFragment layoutPageTemplateFragment WHERE ";
	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATEFRAGMENT = "SELECT COUNT(layoutPageTemplateFragment) FROM LayoutPageTemplateFragment layoutPageTemplateFragment";
	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATEFRAGMENT_WHERE = "SELECT COUNT(layoutPageTemplateFragment) FROM LayoutPageTemplateFragment layoutPageTemplateFragment WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutPageTemplateFragment.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutPageTemplateFragment exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutPageTemplateFragment exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LayoutPageTemplateFragmentPersistenceImpl.class);
}