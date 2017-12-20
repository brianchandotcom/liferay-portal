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

package com.liferay.fragment.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.exception.NoSuchEntryInstanceLinkException;
import com.liferay.fragment.model.FragmentEntryInstanceLink;
import com.liferay.fragment.model.impl.FragmentEntryInstanceLinkImpl;
import com.liferay.fragment.model.impl.FragmentEntryInstanceLinkModelImpl;
import com.liferay.fragment.service.persistence.FragmentEntryInstanceLinkPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the fragment entry instance link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryInstanceLinkPersistence
 * @see com.liferay.fragment.service.persistence.FragmentEntryInstanceLinkUtil
 * @generated
 */
@ProviderType
public class FragmentEntryInstanceLinkPersistenceImpl
	extends BasePersistenceImpl<FragmentEntryInstanceLink>
	implements FragmentEntryInstanceLinkPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link FragmentEntryInstanceLinkUtil} to access the fragment entry instance link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = FragmentEntryInstanceLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			FragmentEntryInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			FragmentEntryInstanceLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			FragmentEntryInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			FragmentEntryInstanceLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			FragmentEntryInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			FragmentEntryInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			FragmentEntryInstanceLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			FragmentEntryInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			FragmentEntryInstanceLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			FragmentEntryInstanceLinkModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			FragmentEntryInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the fragment entry instance links where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching fragment entry instance links
	 */
	@Override
	public List<FragmentEntryInstanceLink> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry instance links where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry instance links
	 * @param end the upper bound of the range of fragment entry instance links (not inclusive)
	 * @return the range of matching fragment entry instance links
	 */
	@Override
	public List<FragmentEntryInstanceLink> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry instance links where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry instance links
	 * @param end the upper bound of the range of fragment entry instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry instance links
	 */
	@Override
	public List<FragmentEntryInstanceLink> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry instance links where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry instance links
	 * @param end the upper bound of the range of fragment entry instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching fragment entry instance links
	 */
	@Override
	public List<FragmentEntryInstanceLink> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator,
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

		List<FragmentEntryInstanceLink> list = null;

		if (retrieveFromCache) {
			list = (List<FragmentEntryInstanceLink>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryInstanceLink fragmentEntryInstanceLink : list) {
					if ((groupId != fragmentEntryInstanceLink.getGroupId())) {
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

			query.append(_SQL_SELECT_FRAGMENTENTRYINSTANCELINK_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(FragmentEntryInstanceLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<FragmentEntryInstanceLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FragmentEntryInstanceLink>)QueryUtil.list(q,
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
	 * Returns the first fragment entry instance link in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry instance link
	 * @throws NoSuchEntryInstanceLinkException if a matching fragment entry instance link could not be found
	 */
	@Override
	public FragmentEntryInstanceLink findByGroupId_First(long groupId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws NoSuchEntryInstanceLinkException {
		FragmentEntryInstanceLink fragmentEntryInstanceLink = fetchByGroupId_First(groupId,
				orderByComparator);

		if (fragmentEntryInstanceLink != null) {
			return fragmentEntryInstanceLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchEntryInstanceLinkException(msg.toString());
	}

	/**
	 * Returns the first fragment entry instance link in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry instance link, or <code>null</code> if a matching fragment entry instance link could not be found
	 */
	@Override
	public FragmentEntryInstanceLink fetchByGroupId_First(long groupId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		List<FragmentEntryInstanceLink> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry instance link in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry instance link
	 * @throws NoSuchEntryInstanceLinkException if a matching fragment entry instance link could not be found
	 */
	@Override
	public FragmentEntryInstanceLink findByGroupId_Last(long groupId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws NoSuchEntryInstanceLinkException {
		FragmentEntryInstanceLink fragmentEntryInstanceLink = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (fragmentEntryInstanceLink != null) {
			return fragmentEntryInstanceLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchEntryInstanceLinkException(msg.toString());
	}

	/**
	 * Returns the last fragment entry instance link in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry instance link, or <code>null</code> if a matching fragment entry instance link could not be found
	 */
	@Override
	public FragmentEntryInstanceLink fetchByGroupId_Last(long groupId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryInstanceLink> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry instance links before and after the current fragment entry instance link in the ordered set where groupId = &#63;.
	 *
	 * @param fragmentEntryInstanceLinkId the primary key of the current fragment entry instance link
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry instance link
	 * @throws NoSuchEntryInstanceLinkException if a fragment entry instance link with the primary key could not be found
	 */
	@Override
	public FragmentEntryInstanceLink[] findByGroupId_PrevAndNext(
		long fragmentEntryInstanceLinkId, long groupId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws NoSuchEntryInstanceLinkException {
		FragmentEntryInstanceLink fragmentEntryInstanceLink = findByPrimaryKey(fragmentEntryInstanceLinkId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryInstanceLink[] array = new FragmentEntryInstanceLinkImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					fragmentEntryInstanceLink, groupId, orderByComparator, true);

			array[1] = fragmentEntryInstanceLink;

			array[2] = getByGroupId_PrevAndNext(session,
					fragmentEntryInstanceLink, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryInstanceLink getByGroupId_PrevAndNext(
		Session session, FragmentEntryInstanceLink fragmentEntryInstanceLink,
		long groupId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator,
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

		query.append(_SQL_SELECT_FRAGMENTENTRYINSTANCELINK_WHERE);

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
			query.append(FragmentEntryInstanceLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(fragmentEntryInstanceLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FragmentEntryInstanceLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry instance links where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (FragmentEntryInstanceLink fragmentEntryInstanceLink : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(fragmentEntryInstanceLink);
		}
	}

	/**
	 * Returns the number of fragment entry instance links where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching fragment entry instance links
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_FRAGMENTENTRYINSTANCELINK_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "fragmentEntryInstanceLink.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F = new FinderPath(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			FragmentEntryInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			FragmentEntryInstanceLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F = new FinderPath(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			FragmentEntryInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			FragmentEntryInstanceLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F",
			new String[] { Long.class.getName(), Long.class.getName() },
			FragmentEntryInstanceLinkModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryInstanceLinkModelImpl.FRAGMENTENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			FragmentEntryInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the fragment entry instance links where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @return the matching fragment entry instance links
	 */
	@Override
	public List<FragmentEntryInstanceLink> findByG_F(long groupId,
		long fragmentEntryId) {
		return findByG_F(groupId, fragmentEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry instance links where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry instance links
	 * @param end the upper bound of the range of fragment entry instance links (not inclusive)
	 * @return the range of matching fragment entry instance links
	 */
	@Override
	public List<FragmentEntryInstanceLink> findByG_F(long groupId,
		long fragmentEntryId, int start, int end) {
		return findByG_F(groupId, fragmentEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry instance links where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry instance links
	 * @param end the upper bound of the range of fragment entry instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry instance links
	 */
	@Override
	public List<FragmentEntryInstanceLink> findByG_F(long groupId,
		long fragmentEntryId, int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		return findByG_F(groupId, fragmentEntryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry instance links where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry instance links
	 * @param end the upper bound of the range of fragment entry instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching fragment entry instance links
	 */
	@Override
	public List<FragmentEntryInstanceLink> findByG_F(long groupId,
		long fragmentEntryId, int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator,
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

		List<FragmentEntryInstanceLink> list = null;

		if (retrieveFromCache) {
			list = (List<FragmentEntryInstanceLink>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryInstanceLink fragmentEntryInstanceLink : list) {
					if ((groupId != fragmentEntryInstanceLink.getGroupId()) ||
							(fragmentEntryId != fragmentEntryInstanceLink.getFragmentEntryId())) {
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

			query.append(_SQL_SELECT_FRAGMENTENTRYINSTANCELINK_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FRAGMENTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(FragmentEntryInstanceLinkModelImpl.ORDER_BY_JPQL);
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
					list = (List<FragmentEntryInstanceLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FragmentEntryInstanceLink>)QueryUtil.list(q,
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
	 * Returns the first fragment entry instance link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry instance link
	 * @throws NoSuchEntryInstanceLinkException if a matching fragment entry instance link could not be found
	 */
	@Override
	public FragmentEntryInstanceLink findByG_F_First(long groupId,
		long fragmentEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws NoSuchEntryInstanceLinkException {
		FragmentEntryInstanceLink fragmentEntryInstanceLink = fetchByG_F_First(groupId,
				fragmentEntryId, orderByComparator);

		if (fragmentEntryInstanceLink != null) {
			return fragmentEntryInstanceLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", fragmentEntryId=");
		msg.append(fragmentEntryId);

		msg.append("}");

		throw new NoSuchEntryInstanceLinkException(msg.toString());
	}

	/**
	 * Returns the first fragment entry instance link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry instance link, or <code>null</code> if a matching fragment entry instance link could not be found
	 */
	@Override
	public FragmentEntryInstanceLink fetchByG_F_First(long groupId,
		long fragmentEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		List<FragmentEntryInstanceLink> list = findByG_F(groupId,
				fragmentEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry instance link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry instance link
	 * @throws NoSuchEntryInstanceLinkException if a matching fragment entry instance link could not be found
	 */
	@Override
	public FragmentEntryInstanceLink findByG_F_Last(long groupId,
		long fragmentEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws NoSuchEntryInstanceLinkException {
		FragmentEntryInstanceLink fragmentEntryInstanceLink = fetchByG_F_Last(groupId,
				fragmentEntryId, orderByComparator);

		if (fragmentEntryInstanceLink != null) {
			return fragmentEntryInstanceLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", fragmentEntryId=");
		msg.append(fragmentEntryId);

		msg.append("}");

		throw new NoSuchEntryInstanceLinkException(msg.toString());
	}

	/**
	 * Returns the last fragment entry instance link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry instance link, or <code>null</code> if a matching fragment entry instance link could not be found
	 */
	@Override
	public FragmentEntryInstanceLink fetchByG_F_Last(long groupId,
		long fragmentEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		int count = countByG_F(groupId, fragmentEntryId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryInstanceLink> list = findByG_F(groupId,
				fragmentEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry instance links before and after the current fragment entry instance link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryInstanceLinkId the primary key of the current fragment entry instance link
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry instance link
	 * @throws NoSuchEntryInstanceLinkException if a fragment entry instance link with the primary key could not be found
	 */
	@Override
	public FragmentEntryInstanceLink[] findByG_F_PrevAndNext(
		long fragmentEntryInstanceLinkId, long groupId, long fragmentEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws NoSuchEntryInstanceLinkException {
		FragmentEntryInstanceLink fragmentEntryInstanceLink = findByPrimaryKey(fragmentEntryInstanceLinkId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryInstanceLink[] array = new FragmentEntryInstanceLinkImpl[3];

			array[0] = getByG_F_PrevAndNext(session, fragmentEntryInstanceLink,
					groupId, fragmentEntryId, orderByComparator, true);

			array[1] = fragmentEntryInstanceLink;

			array[2] = getByG_F_PrevAndNext(session, fragmentEntryInstanceLink,
					groupId, fragmentEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryInstanceLink getByG_F_PrevAndNext(Session session,
		FragmentEntryInstanceLink fragmentEntryInstanceLink, long groupId,
		long fragmentEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator,
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

		query.append(_SQL_SELECT_FRAGMENTENTRYINSTANCELINK_WHERE);

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
			query.append(FragmentEntryInstanceLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(fragmentEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(fragmentEntryInstanceLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FragmentEntryInstanceLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry instance links where groupId = &#63; and fragmentEntryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 */
	@Override
	public void removeByG_F(long groupId, long fragmentEntryId) {
		for (FragmentEntryInstanceLink fragmentEntryInstanceLink : findByG_F(
				groupId, fragmentEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(fragmentEntryInstanceLink);
		}
	}

	/**
	 * Returns the number of fragment entry instance links where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @return the number of matching fragment entry instance links
	 */
	@Override
	public int countByG_F(long groupId, long fragmentEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F;

		Object[] finderArgs = new Object[] { groupId, fragmentEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRAGMENTENTRYINSTANCELINK_WHERE);

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

	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "fragmentEntryInstanceLink.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FRAGMENTENTRYID_2 = "fragmentEntryInstanceLink.fragmentEntryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_L = new FinderPath(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			FragmentEntryInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			FragmentEntryInstanceLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L = new FinderPath(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			FragmentEntryInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			FragmentEntryInstanceLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_L",
			new String[] { Long.class.getName(), Long.class.getName() },
			FragmentEntryInstanceLinkModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryInstanceLinkModelImpl.LAYOUTPAGETEMPLATEENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_L = new FinderPath(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			FragmentEntryInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_L",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the fragment entry instance links where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the matching fragment entry instance links
	 */
	@Override
	public List<FragmentEntryInstanceLink> findByG_L(long groupId,
		long layoutPageTemplateEntryId) {
		return findByG_L(groupId, layoutPageTemplateEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry instance links where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of fragment entry instance links
	 * @param end the upper bound of the range of fragment entry instance links (not inclusive)
	 * @return the range of matching fragment entry instance links
	 */
	@Override
	public List<FragmentEntryInstanceLink> findByG_L(long groupId,
		long layoutPageTemplateEntryId, int start, int end) {
		return findByG_L(groupId, layoutPageTemplateEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry instance links where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of fragment entry instance links
	 * @param end the upper bound of the range of fragment entry instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry instance links
	 */
	@Override
	public List<FragmentEntryInstanceLink> findByG_L(long groupId,
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		return findByG_L(groupId, layoutPageTemplateEntryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry instance links where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of fragment entry instance links
	 * @param end the upper bound of the range of fragment entry instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching fragment entry instance links
	 */
	@Override
	public List<FragmentEntryInstanceLink> findByG_L(long groupId,
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator,
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

		List<FragmentEntryInstanceLink> list = null;

		if (retrieveFromCache) {
			list = (List<FragmentEntryInstanceLink>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryInstanceLink fragmentEntryInstanceLink : list) {
					if ((groupId != fragmentEntryInstanceLink.getGroupId()) ||
							(layoutPageTemplateEntryId != fragmentEntryInstanceLink.getLayoutPageTemplateEntryId())) {
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

			query.append(_SQL_SELECT_FRAGMENTENTRYINSTANCELINK_WHERE);

			query.append(_FINDER_COLUMN_G_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(FragmentEntryInstanceLinkModelImpl.ORDER_BY_JPQL);
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
					list = (List<FragmentEntryInstanceLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FragmentEntryInstanceLink>)QueryUtil.list(q,
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
	 * Returns the first fragment entry instance link in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry instance link
	 * @throws NoSuchEntryInstanceLinkException if a matching fragment entry instance link could not be found
	 */
	@Override
	public FragmentEntryInstanceLink findByG_L_First(long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws NoSuchEntryInstanceLinkException {
		FragmentEntryInstanceLink fragmentEntryInstanceLink = fetchByG_L_First(groupId,
				layoutPageTemplateEntryId, orderByComparator);

		if (fragmentEntryInstanceLink != null) {
			return fragmentEntryInstanceLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutPageTemplateEntryId=");
		msg.append(layoutPageTemplateEntryId);

		msg.append("}");

		throw new NoSuchEntryInstanceLinkException(msg.toString());
	}

	/**
	 * Returns the first fragment entry instance link in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry instance link, or <code>null</code> if a matching fragment entry instance link could not be found
	 */
	@Override
	public FragmentEntryInstanceLink fetchByG_L_First(long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		List<FragmentEntryInstanceLink> list = findByG_L(groupId,
				layoutPageTemplateEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry instance link in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry instance link
	 * @throws NoSuchEntryInstanceLinkException if a matching fragment entry instance link could not be found
	 */
	@Override
	public FragmentEntryInstanceLink findByG_L_Last(long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws NoSuchEntryInstanceLinkException {
		FragmentEntryInstanceLink fragmentEntryInstanceLink = fetchByG_L_Last(groupId,
				layoutPageTemplateEntryId, orderByComparator);

		if (fragmentEntryInstanceLink != null) {
			return fragmentEntryInstanceLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutPageTemplateEntryId=");
		msg.append(layoutPageTemplateEntryId);

		msg.append("}");

		throw new NoSuchEntryInstanceLinkException(msg.toString());
	}

	/**
	 * Returns the last fragment entry instance link in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry instance link, or <code>null</code> if a matching fragment entry instance link could not be found
	 */
	@Override
	public FragmentEntryInstanceLink fetchByG_L_Last(long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		int count = countByG_L(groupId, layoutPageTemplateEntryId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryInstanceLink> list = findByG_L(groupId,
				layoutPageTemplateEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry instance links before and after the current fragment entry instance link in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * @param fragmentEntryInstanceLinkId the primary key of the current fragment entry instance link
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry instance link
	 * @throws NoSuchEntryInstanceLinkException if a fragment entry instance link with the primary key could not be found
	 */
	@Override
	public FragmentEntryInstanceLink[] findByG_L_PrevAndNext(
		long fragmentEntryInstanceLinkId, long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws NoSuchEntryInstanceLinkException {
		FragmentEntryInstanceLink fragmentEntryInstanceLink = findByPrimaryKey(fragmentEntryInstanceLinkId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryInstanceLink[] array = new FragmentEntryInstanceLinkImpl[3];

			array[0] = getByG_L_PrevAndNext(session, fragmentEntryInstanceLink,
					groupId, layoutPageTemplateEntryId, orderByComparator, true);

			array[1] = fragmentEntryInstanceLink;

			array[2] = getByG_L_PrevAndNext(session, fragmentEntryInstanceLink,
					groupId, layoutPageTemplateEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryInstanceLink getByG_L_PrevAndNext(Session session,
		FragmentEntryInstanceLink fragmentEntryInstanceLink, long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator,
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

		query.append(_SQL_SELECT_FRAGMENTENTRYINSTANCELINK_WHERE);

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
			query.append(FragmentEntryInstanceLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(layoutPageTemplateEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(fragmentEntryInstanceLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FragmentEntryInstanceLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry instance links where groupId = &#63; and layoutPageTemplateEntryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 */
	@Override
	public void removeByG_L(long groupId, long layoutPageTemplateEntryId) {
		for (FragmentEntryInstanceLink fragmentEntryInstanceLink : findByG_L(
				groupId, layoutPageTemplateEntryId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(fragmentEntryInstanceLink);
		}
	}

	/**
	 * Returns the number of fragment entry instance links where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the number of matching fragment entry instance links
	 */
	@Override
	public int countByG_L(long groupId, long layoutPageTemplateEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_L;

		Object[] finderArgs = new Object[] { groupId, layoutPageTemplateEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRAGMENTENTRYINSTANCELINK_WHERE);

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

	private static final String _FINDER_COLUMN_G_L_GROUPID_2 = "fragmentEntryInstanceLink.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATEENTRYID_2 = "fragmentEntryInstanceLink.layoutPageTemplateEntryId = ?";

	public FragmentEntryInstanceLinkPersistenceImpl() {
		setModelClass(FragmentEntryInstanceLink.class);
	}

	/**
	 * Caches the fragment entry instance link in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryInstanceLink the fragment entry instance link
	 */
	@Override
	public void cacheResult(FragmentEntryInstanceLink fragmentEntryInstanceLink) {
		entityCache.putResult(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			FragmentEntryInstanceLinkImpl.class,
			fragmentEntryInstanceLink.getPrimaryKey(), fragmentEntryInstanceLink);

		fragmentEntryInstanceLink.resetOriginalValues();
	}

	/**
	 * Caches the fragment entry instance links in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryInstanceLinks the fragment entry instance links
	 */
	@Override
	public void cacheResult(
		List<FragmentEntryInstanceLink> fragmentEntryInstanceLinks) {
		for (FragmentEntryInstanceLink fragmentEntryInstanceLink : fragmentEntryInstanceLinks) {
			if (entityCache.getResult(
						FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
						FragmentEntryInstanceLinkImpl.class,
						fragmentEntryInstanceLink.getPrimaryKey()) == null) {
				cacheResult(fragmentEntryInstanceLink);
			}
			else {
				fragmentEntryInstanceLink.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all fragment entry instance links.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FragmentEntryInstanceLinkImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the fragment entry instance link.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FragmentEntryInstanceLink fragmentEntryInstanceLink) {
		entityCache.removeResult(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			FragmentEntryInstanceLinkImpl.class,
			fragmentEntryInstanceLink.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<FragmentEntryInstanceLink> fragmentEntryInstanceLinks) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (FragmentEntryInstanceLink fragmentEntryInstanceLink : fragmentEntryInstanceLinks) {
			entityCache.removeResult(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
				FragmentEntryInstanceLinkImpl.class,
				fragmentEntryInstanceLink.getPrimaryKey());
		}
	}

	/**
	 * Creates a new fragment entry instance link with the primary key. Does not add the fragment entry instance link to the database.
	 *
	 * @param fragmentEntryInstanceLinkId the primary key for the new fragment entry instance link
	 * @return the new fragment entry instance link
	 */
	@Override
	public FragmentEntryInstanceLink create(long fragmentEntryInstanceLinkId) {
		FragmentEntryInstanceLink fragmentEntryInstanceLink = new FragmentEntryInstanceLinkImpl();

		fragmentEntryInstanceLink.setNew(true);
		fragmentEntryInstanceLink.setPrimaryKey(fragmentEntryInstanceLinkId);

		return fragmentEntryInstanceLink;
	}

	/**
	 * Removes the fragment entry instance link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryInstanceLinkId the primary key of the fragment entry instance link
	 * @return the fragment entry instance link that was removed
	 * @throws NoSuchEntryInstanceLinkException if a fragment entry instance link with the primary key could not be found
	 */
	@Override
	public FragmentEntryInstanceLink remove(long fragmentEntryInstanceLinkId)
		throws NoSuchEntryInstanceLinkException {
		return remove((Serializable)fragmentEntryInstanceLinkId);
	}

	/**
	 * Removes the fragment entry instance link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the fragment entry instance link
	 * @return the fragment entry instance link that was removed
	 * @throws NoSuchEntryInstanceLinkException if a fragment entry instance link with the primary key could not be found
	 */
	@Override
	public FragmentEntryInstanceLink remove(Serializable primaryKey)
		throws NoSuchEntryInstanceLinkException {
		Session session = null;

		try {
			session = openSession();

			FragmentEntryInstanceLink fragmentEntryInstanceLink = (FragmentEntryInstanceLink)session.get(FragmentEntryInstanceLinkImpl.class,
					primaryKey);

			if (fragmentEntryInstanceLink == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryInstanceLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(fragmentEntryInstanceLink);
		}
		catch (NoSuchEntryInstanceLinkException nsee) {
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
	protected FragmentEntryInstanceLink removeImpl(
		FragmentEntryInstanceLink fragmentEntryInstanceLink) {
		fragmentEntryInstanceLink = toUnwrappedModel(fragmentEntryInstanceLink);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(fragmentEntryInstanceLink)) {
				fragmentEntryInstanceLink = (FragmentEntryInstanceLink)session.get(FragmentEntryInstanceLinkImpl.class,
						fragmentEntryInstanceLink.getPrimaryKeyObj());
			}

			if (fragmentEntryInstanceLink != null) {
				session.delete(fragmentEntryInstanceLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (fragmentEntryInstanceLink != null) {
			clearCache(fragmentEntryInstanceLink);
		}

		return fragmentEntryInstanceLink;
	}

	@Override
	public FragmentEntryInstanceLink updateImpl(
		FragmentEntryInstanceLink fragmentEntryInstanceLink) {
		fragmentEntryInstanceLink = toUnwrappedModel(fragmentEntryInstanceLink);

		boolean isNew = fragmentEntryInstanceLink.isNew();

		FragmentEntryInstanceLinkModelImpl fragmentEntryInstanceLinkModelImpl = (FragmentEntryInstanceLinkModelImpl)fragmentEntryInstanceLink;

		Session session = null;

		try {
			session = openSession();

			if (fragmentEntryInstanceLink.isNew()) {
				session.save(fragmentEntryInstanceLink);

				fragmentEntryInstanceLink.setNew(false);
			}
			else {
				fragmentEntryInstanceLink = (FragmentEntryInstanceLink)session.merge(fragmentEntryInstanceLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!FragmentEntryInstanceLinkModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					fragmentEntryInstanceLinkModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					fragmentEntryInstanceLinkModelImpl.getGroupId(),
					fragmentEntryInstanceLinkModelImpl.getFragmentEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
				args);

			args = new Object[] {
					fragmentEntryInstanceLinkModelImpl.getGroupId(),
					fragmentEntryInstanceLinkModelImpl.getLayoutPageTemplateEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_L, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((fragmentEntryInstanceLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						fragmentEntryInstanceLinkModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						fragmentEntryInstanceLinkModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((fragmentEntryInstanceLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						fragmentEntryInstanceLinkModelImpl.getOriginalGroupId(),
						fragmentEntryInstanceLinkModelImpl.getOriginalFragmentEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);

				args = new Object[] {
						fragmentEntryInstanceLinkModelImpl.getGroupId(),
						fragmentEntryInstanceLinkModelImpl.getFragmentEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);
			}

			if ((fragmentEntryInstanceLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						fragmentEntryInstanceLinkModelImpl.getOriginalGroupId(),
						fragmentEntryInstanceLinkModelImpl.getOriginalLayoutPageTemplateEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_L, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L,
					args);

				args = new Object[] {
						fragmentEntryInstanceLinkModelImpl.getGroupId(),
						fragmentEntryInstanceLinkModelImpl.getLayoutPageTemplateEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_L, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L,
					args);
			}
		}

		entityCache.putResult(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			FragmentEntryInstanceLinkImpl.class,
			fragmentEntryInstanceLink.getPrimaryKey(),
			fragmentEntryInstanceLink, false);

		fragmentEntryInstanceLink.resetOriginalValues();

		return fragmentEntryInstanceLink;
	}

	protected FragmentEntryInstanceLink toUnwrappedModel(
		FragmentEntryInstanceLink fragmentEntryInstanceLink) {
		if (fragmentEntryInstanceLink instanceof FragmentEntryInstanceLinkImpl) {
			return fragmentEntryInstanceLink;
		}

		FragmentEntryInstanceLinkImpl fragmentEntryInstanceLinkImpl = new FragmentEntryInstanceLinkImpl();

		fragmentEntryInstanceLinkImpl.setNew(fragmentEntryInstanceLink.isNew());
		fragmentEntryInstanceLinkImpl.setPrimaryKey(fragmentEntryInstanceLink.getPrimaryKey());

		fragmentEntryInstanceLinkImpl.setFragmentEntryInstanceLinkId(fragmentEntryInstanceLink.getFragmentEntryInstanceLinkId());
		fragmentEntryInstanceLinkImpl.setGroupId(fragmentEntryInstanceLink.getGroupId());
		fragmentEntryInstanceLinkImpl.setFragmentEntryId(fragmentEntryInstanceLink.getFragmentEntryId());
		fragmentEntryInstanceLinkImpl.setLayoutPageTemplateEntryId(fragmentEntryInstanceLink.getLayoutPageTemplateEntryId());
		fragmentEntryInstanceLinkImpl.setPosition(fragmentEntryInstanceLink.getPosition());

		return fragmentEntryInstanceLinkImpl;
	}

	/**
	 * Returns the fragment entry instance link with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the fragment entry instance link
	 * @return the fragment entry instance link
	 * @throws NoSuchEntryInstanceLinkException if a fragment entry instance link with the primary key could not be found
	 */
	@Override
	public FragmentEntryInstanceLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryInstanceLinkException {
		FragmentEntryInstanceLink fragmentEntryInstanceLink = fetchByPrimaryKey(primaryKey);

		if (fragmentEntryInstanceLink == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryInstanceLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return fragmentEntryInstanceLink;
	}

	/**
	 * Returns the fragment entry instance link with the primary key or throws a {@link NoSuchEntryInstanceLinkException} if it could not be found.
	 *
	 * @param fragmentEntryInstanceLinkId the primary key of the fragment entry instance link
	 * @return the fragment entry instance link
	 * @throws NoSuchEntryInstanceLinkException if a fragment entry instance link with the primary key could not be found
	 */
	@Override
	public FragmentEntryInstanceLink findByPrimaryKey(
		long fragmentEntryInstanceLinkId)
		throws NoSuchEntryInstanceLinkException {
		return findByPrimaryKey((Serializable)fragmentEntryInstanceLinkId);
	}

	/**
	 * Returns the fragment entry instance link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the fragment entry instance link
	 * @return the fragment entry instance link, or <code>null</code> if a fragment entry instance link with the primary key could not be found
	 */
	@Override
	public FragmentEntryInstanceLink fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
				FragmentEntryInstanceLinkImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		FragmentEntryInstanceLink fragmentEntryInstanceLink = (FragmentEntryInstanceLink)serializable;

		if (fragmentEntryInstanceLink == null) {
			Session session = null;

			try {
				session = openSession();

				fragmentEntryInstanceLink = (FragmentEntryInstanceLink)session.get(FragmentEntryInstanceLinkImpl.class,
						primaryKey);

				if (fragmentEntryInstanceLink != null) {
					cacheResult(fragmentEntryInstanceLink);
				}
				else {
					entityCache.putResult(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
						FragmentEntryInstanceLinkImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
					FragmentEntryInstanceLinkImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return fragmentEntryInstanceLink;
	}

	/**
	 * Returns the fragment entry instance link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fragmentEntryInstanceLinkId the primary key of the fragment entry instance link
	 * @return the fragment entry instance link, or <code>null</code> if a fragment entry instance link with the primary key could not be found
	 */
	@Override
	public FragmentEntryInstanceLink fetchByPrimaryKey(
		long fragmentEntryInstanceLinkId) {
		return fetchByPrimaryKey((Serializable)fragmentEntryInstanceLinkId);
	}

	@Override
	public Map<Serializable, FragmentEntryInstanceLink> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, FragmentEntryInstanceLink> map = new HashMap<Serializable, FragmentEntryInstanceLink>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			FragmentEntryInstanceLink fragmentEntryInstanceLink = fetchByPrimaryKey(primaryKey);

			if (fragmentEntryInstanceLink != null) {
				map.put(primaryKey, fragmentEntryInstanceLink);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
					FragmentEntryInstanceLinkImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (FragmentEntryInstanceLink)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_FRAGMENTENTRYINSTANCELINK_WHERE_PKS_IN);

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

			for (FragmentEntryInstanceLink fragmentEntryInstanceLink : (List<FragmentEntryInstanceLink>)q.list()) {
				map.put(fragmentEntryInstanceLink.getPrimaryKeyObj(),
					fragmentEntryInstanceLink);

				cacheResult(fragmentEntryInstanceLink);

				uncachedPrimaryKeys.remove(fragmentEntryInstanceLink.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(FragmentEntryInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
					FragmentEntryInstanceLinkImpl.class, primaryKey, nullModel);
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
	 * Returns all the fragment entry instance links.
	 *
	 * @return the fragment entry instance links
	 */
	@Override
	public List<FragmentEntryInstanceLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry instance links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry instance links
	 * @param end the upper bound of the range of fragment entry instance links (not inclusive)
	 * @return the range of fragment entry instance links
	 */
	@Override
	public List<FragmentEntryInstanceLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry instance links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry instance links
	 * @param end the upper bound of the range of fragment entry instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fragment entry instance links
	 */
	@Override
	public List<FragmentEntryInstanceLink> findAll(int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry instance links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry instance links
	 * @param end the upper bound of the range of fragment entry instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of fragment entry instance links
	 */
	@Override
	public List<FragmentEntryInstanceLink> findAll(int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator,
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

		List<FragmentEntryInstanceLink> list = null;

		if (retrieveFromCache) {
			list = (List<FragmentEntryInstanceLink>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_FRAGMENTENTRYINSTANCELINK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_FRAGMENTENTRYINSTANCELINK;

				if (pagination) {
					sql = sql.concat(FragmentEntryInstanceLinkModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<FragmentEntryInstanceLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FragmentEntryInstanceLink>)QueryUtil.list(q,
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
	 * Removes all the fragment entry instance links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FragmentEntryInstanceLink fragmentEntryInstanceLink : findAll()) {
			remove(fragmentEntryInstanceLink);
		}
	}

	/**
	 * Returns the number of fragment entry instance links.
	 *
	 * @return the number of fragment entry instance links
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_FRAGMENTENTRYINSTANCELINK);

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
		return FragmentEntryInstanceLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the fragment entry instance link persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(FragmentEntryInstanceLinkImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_FRAGMENTENTRYINSTANCELINK = "SELECT fragmentEntryInstanceLink FROM FragmentEntryInstanceLink fragmentEntryInstanceLink";
	private static final String _SQL_SELECT_FRAGMENTENTRYINSTANCELINK_WHERE_PKS_IN =
		"SELECT fragmentEntryInstanceLink FROM FragmentEntryInstanceLink fragmentEntryInstanceLink WHERE fragmentEntryInstanceLinkId IN (";
	private static final String _SQL_SELECT_FRAGMENTENTRYINSTANCELINK_WHERE = "SELECT fragmentEntryInstanceLink FROM FragmentEntryInstanceLink fragmentEntryInstanceLink WHERE ";
	private static final String _SQL_COUNT_FRAGMENTENTRYINSTANCELINK = "SELECT COUNT(fragmentEntryInstanceLink) FROM FragmentEntryInstanceLink fragmentEntryInstanceLink";
	private static final String _SQL_COUNT_FRAGMENTENTRYINSTANCELINK_WHERE = "SELECT COUNT(fragmentEntryInstanceLink) FROM FragmentEntryInstanceLink fragmentEntryInstanceLink WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "fragmentEntryInstanceLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FragmentEntryInstanceLink exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No FragmentEntryInstanceLink exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(FragmentEntryInstanceLinkPersistenceImpl.class);
}