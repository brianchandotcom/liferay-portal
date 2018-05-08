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

package com.liferay.changeset.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.changeset.exception.NoSuchCollectionException;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.impl.ChangesetCollectionImpl;
import com.liferay.changeset.model.impl.ChangesetCollectionModelImpl;
import com.liferay.changeset.service.persistence.ChangesetCollectionPersistence;

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
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the changeset collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetCollectionPersistence
 * @see com.liferay.changeset.service.persistence.ChangesetCollectionUtil
 * @generated
 */
@ProviderType
public class ChangesetCollectionPersistenceImpl extends BasePersistenceImpl<ChangesetCollection>
	implements ChangesetCollectionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ChangesetCollectionUtil} to access the changeset collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ChangesetCollectionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangesetCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangesetCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangesetCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangesetCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			ChangesetCollectionModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the changeset collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the changeset collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByGroupId(long groupId, int start,
		int end, OrderByComparator<ChangesetCollection> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByGroupId(long groupId, int start,
		int end, OrderByComparator<ChangesetCollection> orderByComparator,
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

		List<ChangesetCollection> list = null;

		if (retrieveFromCache) {
			list = (List<ChangesetCollection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ChangesetCollection changesetCollection : list) {
					if ((groupId != changesetCollection.getGroupId())) {
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

			query.append(_SQL_SELECT_CHANGESETCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ChangesetCollectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<ChangesetCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ChangesetCollection>)QueryUtil.list(q,
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
	 * Returns the first changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection findByGroupId_First(long groupId,
		OrderByComparator<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException {
		ChangesetCollection changesetCollection = fetchByGroupId_First(groupId,
				orderByComparator);

		if (changesetCollection != null) {
			return changesetCollection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the first changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection fetchByGroupId_First(long groupId,
		OrderByComparator<ChangesetCollection> orderByComparator) {
		List<ChangesetCollection> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection findByGroupId_Last(long groupId,
		OrderByComparator<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException {
		ChangesetCollection changesetCollection = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (changesetCollection != null) {
			return changesetCollection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the last changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection fetchByGroupId_Last(long groupId,
		OrderByComparator<ChangesetCollection> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<ChangesetCollection> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the changeset collections before and after the current changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param changesetCollectionId the primary key of the current changeset collection
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	@Override
	public ChangesetCollection[] findByGroupId_PrevAndNext(
		long changesetCollectionId, long groupId,
		OrderByComparator<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException {
		ChangesetCollection changesetCollection = findByPrimaryKey(changesetCollectionId);

		Session session = null;

		try {
			session = openSession();

			ChangesetCollection[] array = new ChangesetCollectionImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, changesetCollection,
					groupId, orderByComparator, true);

			array[1] = changesetCollection;

			array[2] = getByGroupId_PrevAndNext(session, changesetCollection,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ChangesetCollection getByGroupId_PrevAndNext(Session session,
		ChangesetCollection changesetCollection, long groupId,
		OrderByComparator<ChangesetCollection> orderByComparator,
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

		query.append(_SQL_SELECT_CHANGESETCOLLECTION_WHERE);

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
			query.append(ChangesetCollectionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(changesetCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ChangesetCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the changeset collections where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (ChangesetCollection changesetCollection : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(changesetCollection);
		}
	}

	/**
	 * Returns the number of changeset collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching changeset collections
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CHANGESETCOLLECTION_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "changesetCollection.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangesetCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangesetCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			ChangesetCollectionModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the changeset collections where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the changeset collections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByCompanyId(long companyId, int start,
		int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset collections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<ChangesetCollection> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset collections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<ChangesetCollection> orderByComparator,
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

		List<ChangesetCollection> list = null;

		if (retrieveFromCache) {
			list = (List<ChangesetCollection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ChangesetCollection changesetCollection : list) {
					if ((companyId != changesetCollection.getCompanyId())) {
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

			query.append(_SQL_SELECT_CHANGESETCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ChangesetCollectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<ChangesetCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ChangesetCollection>)QueryUtil.list(q,
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
	 * Returns the first changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection findByCompanyId_First(long companyId,
		OrderByComparator<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException {
		ChangesetCollection changesetCollection = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (changesetCollection != null) {
			return changesetCollection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the first changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection fetchByCompanyId_First(long companyId,
		OrderByComparator<ChangesetCollection> orderByComparator) {
		List<ChangesetCollection> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection findByCompanyId_Last(long companyId,
		OrderByComparator<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException {
		ChangesetCollection changesetCollection = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (changesetCollection != null) {
			return changesetCollection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the last changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection fetchByCompanyId_Last(long companyId,
		OrderByComparator<ChangesetCollection> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<ChangesetCollection> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the changeset collections before and after the current changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param changesetCollectionId the primary key of the current changeset collection
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	@Override
	public ChangesetCollection[] findByCompanyId_PrevAndNext(
		long changesetCollectionId, long companyId,
		OrderByComparator<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException {
		ChangesetCollection changesetCollection = findByPrimaryKey(changesetCollectionId);

		Session session = null;

		try {
			session = openSession();

			ChangesetCollection[] array = new ChangesetCollectionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, changesetCollection,
					companyId, orderByComparator, true);

			array[1] = changesetCollection;

			array[2] = getByCompanyId_PrevAndNext(session, changesetCollection,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ChangesetCollection getByCompanyId_PrevAndNext(Session session,
		ChangesetCollection changesetCollection, long companyId,
		OrderByComparator<ChangesetCollection> orderByComparator,
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

		query.append(_SQL_SELECT_CHANGESETCOLLECTION_WHERE);

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
			query.append(ChangesetCollectionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(changesetCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ChangesetCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the changeset collections where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (ChangesetCollection changesetCollection : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(changesetCollection);
		}
	}

	/**
	 * Returns the number of changeset collections where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching changeset collections
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CHANGESETCOLLECTION_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "changesetCollection.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U = new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangesetCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U = new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangesetCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			ChangesetCollectionModelImpl.GROUPID_COLUMN_BITMASK |
			ChangesetCollectionModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByG_U(long groupId, long userId) {
		return findByG_U(groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByG_U(long groupId, long userId,
		int start, int end) {
		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByG_U(long groupId, long userId,
		int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator) {
		return findByG_U(groupId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByG_U(long groupId, long userId,
		int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U;
			finderArgs = new Object[] { groupId, userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U;
			finderArgs = new Object[] {
					groupId, userId,
					
					start, end, orderByComparator
				};
		}

		List<ChangesetCollection> list = null;

		if (retrieveFromCache) {
			list = (List<ChangesetCollection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ChangesetCollection changesetCollection : list) {
					if ((groupId != changesetCollection.getGroupId()) ||
							(userId != changesetCollection.getUserId())) {
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

			query.append(_SQL_SELECT_CHANGESETCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ChangesetCollectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (!pagination) {
					list = (List<ChangesetCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ChangesetCollection>)QueryUtil.list(q,
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
	 * Returns the first changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection findByG_U_First(long groupId, long userId,
		OrderByComparator<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException {
		ChangesetCollection changesetCollection = fetchByG_U_First(groupId,
				userId, orderByComparator);

		if (changesetCollection != null) {
			return changesetCollection;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the first changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection fetchByG_U_First(long groupId, long userId,
		OrderByComparator<ChangesetCollection> orderByComparator) {
		List<ChangesetCollection> list = findByG_U(groupId, userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection findByG_U_Last(long groupId, long userId,
		OrderByComparator<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException {
		ChangesetCollection changesetCollection = fetchByG_U_Last(groupId,
				userId, orderByComparator);

		if (changesetCollection != null) {
			return changesetCollection;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the last changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection fetchByG_U_Last(long groupId, long userId,
		OrderByComparator<ChangesetCollection> orderByComparator) {
		int count = countByG_U(groupId, userId);

		if (count == 0) {
			return null;
		}

		List<ChangesetCollection> list = findByG_U(groupId, userId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the changeset collections before and after the current changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param changesetCollectionId the primary key of the current changeset collection
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	@Override
	public ChangesetCollection[] findByG_U_PrevAndNext(
		long changesetCollectionId, long groupId, long userId,
		OrderByComparator<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException {
		ChangesetCollection changesetCollection = findByPrimaryKey(changesetCollectionId);

		Session session = null;

		try {
			session = openSession();

			ChangesetCollection[] array = new ChangesetCollectionImpl[3];

			array[0] = getByG_U_PrevAndNext(session, changesetCollection,
					groupId, userId, orderByComparator, true);

			array[1] = changesetCollection;

			array[2] = getByG_U_PrevAndNext(session, changesetCollection,
					groupId, userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ChangesetCollection getByG_U_PrevAndNext(Session session,
		ChangesetCollection changesetCollection, long groupId, long userId,
		OrderByComparator<ChangesetCollection> orderByComparator,
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

		query.append(_SQL_SELECT_CHANGESETCOLLECTION_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

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
			query.append(ChangesetCollectionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(changesetCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ChangesetCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the changeset collections where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByG_U(long groupId, long userId) {
		for (ChangesetCollection changesetCollection : findByG_U(groupId,
				userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(changesetCollection);
		}
	}

	/**
	 * Returns the number of changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching changeset collections
	 */
	@Override
	public int countByG_U(long groupId, long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U;

		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CHANGESETCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

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

	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "changesetCollection.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "changesetCollection.userId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangesetCollectionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() },
			ChangesetCollectionModelImpl.GROUPID_COLUMN_BITMASK |
			ChangesetCollectionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the changeset collection where groupId = &#63; and name = &#63; or throws a {@link NoSuchCollectionException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection findByG_N(long groupId, String name)
		throws NoSuchCollectionException {
		ChangesetCollection changesetCollection = fetchByG_N(groupId, name);

		if (changesetCollection == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCollectionException(msg.toString());
		}

		return changesetCollection;
	}

	/**
	 * Returns the changeset collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection fetchByG_N(long groupId, String name) {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Returns the changeset collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection fetchByG_N(long groupId, String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_N,
					finderArgs, this);
		}

		if (result instanceof ChangesetCollection) {
			ChangesetCollection changesetCollection = (ChangesetCollection)result;

			if ((groupId != changesetCollection.getGroupId()) ||
					!Objects.equals(name, changesetCollection.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CHANGESETCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else if (name.equals("")) {
				query.append(_FINDER_COLUMN_G_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name);
				}

				List<ChangesetCollection> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, finderArgs,
						list);
				}
				else {
					ChangesetCollection changesetCollection = list.get(0);

					result = changesetCollection;

					cacheResult(changesetCollection);

					if ((changesetCollection.getGroupId() != groupId) ||
							(changesetCollection.getName() == null) ||
							!changesetCollection.getName().equals(name)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_N,
							finderArgs, changesetCollection);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, finderArgs);

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
			return (ChangesetCollection)result;
		}
	}

	/**
	 * Removes the changeset collection where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the changeset collection that was removed
	 */
	@Override
	public ChangesetCollection removeByG_N(long groupId, String name)
		throws NoSuchCollectionException {
		ChangesetCollection changesetCollection = findByG_N(groupId, name);

		return remove(changesetCollection);
	}

	/**
	 * Returns the number of changeset collections where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching changeset collections
	 */
	@Override
	public int countByG_N(long groupId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_N;

		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CHANGESETCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else if (name.equals("")) {
				query.append(_FINDER_COLUMN_G_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_G_N_GROUPID_2 = "changesetCollection.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_NAME_1 = "changesetCollection.name IS NULL";
	private static final String _FINDER_COLUMN_G_N_NAME_2 = "changesetCollection.name = ?";
	private static final String _FINDER_COLUMN_G_N_NAME_3 = "(changesetCollection.name IS NULL OR changesetCollection.name = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_N = new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangesetCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N = new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangesetCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_N",
			new String[] { Long.class.getName(), String.class.getName() },
			ChangesetCollectionModelImpl.COMPANYID_COLUMN_BITMASK |
			ChangesetCollectionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByC_N(long companyId, String name) {
		return findByC_N(companyId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByC_N(long companyId, String name,
		int start, int end) {
		return findByC_N(companyId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByC_N(long companyId, String name,
		int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator) {
		return findByC_N(companyId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByC_N(long companyId, String name,
		int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator,
		boolean retrieveFromCache) {
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

		List<ChangesetCollection> list = null;

		if (retrieveFromCache) {
			list = (List<ChangesetCollection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ChangesetCollection changesetCollection : list) {
					if ((companyId != changesetCollection.getCompanyId()) ||
							!Objects.equals(name, changesetCollection.getName())) {
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

			query.append(_SQL_SELECT_CHANGESETCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else if (name.equals("")) {
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
				query.append(ChangesetCollectionModelImpl.ORDER_BY_JPQL);
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
					list = (List<ChangesetCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ChangesetCollection>)QueryUtil.list(q,
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
	 * Returns the first changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection findByC_N_First(long companyId, String name,
		OrderByComparator<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException {
		ChangesetCollection changesetCollection = fetchByC_N_First(companyId,
				name, orderByComparator);

		if (changesetCollection != null) {
			return changesetCollection;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the first changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection fetchByC_N_First(long companyId, String name,
		OrderByComparator<ChangesetCollection> orderByComparator) {
		List<ChangesetCollection> list = findByC_N(companyId, name, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection findByC_N_Last(long companyId, String name,
		OrderByComparator<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException {
		ChangesetCollection changesetCollection = fetchByC_N_Last(companyId,
				name, orderByComparator);

		if (changesetCollection != null) {
			return changesetCollection;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the last changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection fetchByC_N_Last(long companyId, String name,
		OrderByComparator<ChangesetCollection> orderByComparator) {
		int count = countByC_N(companyId, name);

		if (count == 0) {
			return null;
		}

		List<ChangesetCollection> list = findByC_N(companyId, name, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the changeset collections before and after the current changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param changesetCollectionId the primary key of the current changeset collection
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	@Override
	public ChangesetCollection[] findByC_N_PrevAndNext(
		long changesetCollectionId, long companyId, String name,
		OrderByComparator<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException {
		ChangesetCollection changesetCollection = findByPrimaryKey(changesetCollectionId);

		Session session = null;

		try {
			session = openSession();

			ChangesetCollection[] array = new ChangesetCollectionImpl[3];

			array[0] = getByC_N_PrevAndNext(session, changesetCollection,
					companyId, name, orderByComparator, true);

			array[1] = changesetCollection;

			array[2] = getByC_N_PrevAndNext(session, changesetCollection,
					companyId, name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ChangesetCollection getByC_N_PrevAndNext(Session session,
		ChangesetCollection changesetCollection, long companyId, String name,
		OrderByComparator<ChangesetCollection> orderByComparator,
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

		query.append(_SQL_SELECT_CHANGESETCOLLECTION_WHERE);

		query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_C_N_NAME_1);
		}
		else if (name.equals("")) {
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
			query.append(ChangesetCollectionModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(changesetCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ChangesetCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the changeset collections where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	@Override
	public void removeByC_N(long companyId, String name) {
		for (ChangesetCollection changesetCollection : findByC_N(companyId,
				name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(changesetCollection);
		}
	}

	/**
	 * Returns the number of changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching changeset collections
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_N;

		Object[] finderArgs = new Object[] { companyId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CHANGESETCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else if (name.equals("")) {
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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "changesetCollection.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "changesetCollection.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "changesetCollection.name = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(changesetCollection.name IS NULL OR changesetCollection.name = '')";

	public ChangesetCollectionPersistenceImpl() {
		setModelClass(ChangesetCollection.class);
	}

	/**
	 * Caches the changeset collection in the entity cache if it is enabled.
	 *
	 * @param changesetCollection the changeset collection
	 */
	@Override
	public void cacheResult(ChangesetCollection changesetCollection) {
		entityCache.putResult(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionImpl.class, changesetCollection.getPrimaryKey(),
			changesetCollection);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				changesetCollection.getGroupId(), changesetCollection.getName()
			}, changesetCollection);

		changesetCollection.resetOriginalValues();
	}

	/**
	 * Caches the changeset collections in the entity cache if it is enabled.
	 *
	 * @param changesetCollections the changeset collections
	 */
	@Override
	public void cacheResult(List<ChangesetCollection> changesetCollections) {
		for (ChangesetCollection changesetCollection : changesetCollections) {
			if (entityCache.getResult(
						ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
						ChangesetCollectionImpl.class,
						changesetCollection.getPrimaryKey()) == null) {
				cacheResult(changesetCollection);
			}
			else {
				changesetCollection.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all changeset collections.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ChangesetCollectionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the changeset collection.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ChangesetCollection changesetCollection) {
		entityCache.removeResult(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionImpl.class, changesetCollection.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ChangesetCollectionModelImpl)changesetCollection,
			true);
	}

	@Override
	public void clearCache(List<ChangesetCollection> changesetCollections) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ChangesetCollection changesetCollection : changesetCollections) {
			entityCache.removeResult(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
				ChangesetCollectionImpl.class,
				changesetCollection.getPrimaryKey());

			clearUniqueFindersCache((ChangesetCollectionModelImpl)changesetCollection,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		ChangesetCollectionModelImpl changesetCollectionModelImpl) {
		Object[] args = new Object[] {
				changesetCollectionModelImpl.getGroupId(),
				changesetCollectionModelImpl.getName()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_N, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, args,
			changesetCollectionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ChangesetCollectionModelImpl changesetCollectionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					changesetCollectionModelImpl.getGroupId(),
					changesetCollectionModelImpl.getName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, args);
		}

		if ((changesetCollectionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_N.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					changesetCollectionModelImpl.getOriginalGroupId(),
					changesetCollectionModelImpl.getOriginalName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, args);
		}
	}

	/**
	 * Creates a new changeset collection with the primary key. Does not add the changeset collection to the database.
	 *
	 * @param changesetCollectionId the primary key for the new changeset collection
	 * @return the new changeset collection
	 */
	@Override
	public ChangesetCollection create(long changesetCollectionId) {
		ChangesetCollection changesetCollection = new ChangesetCollectionImpl();

		changesetCollection.setNew(true);
		changesetCollection.setPrimaryKey(changesetCollectionId);

		changesetCollection.setCompanyId(companyProvider.getCompanyId());

		return changesetCollection;
	}

	/**
	 * Removes the changeset collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param changesetCollectionId the primary key of the changeset collection
	 * @return the changeset collection that was removed
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	@Override
	public ChangesetCollection remove(long changesetCollectionId)
		throws NoSuchCollectionException {
		return remove((Serializable)changesetCollectionId);
	}

	/**
	 * Removes the changeset collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the changeset collection
	 * @return the changeset collection that was removed
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	@Override
	public ChangesetCollection remove(Serializable primaryKey)
		throws NoSuchCollectionException {
		Session session = null;

		try {
			session = openSession();

			ChangesetCollection changesetCollection = (ChangesetCollection)session.get(ChangesetCollectionImpl.class,
					primaryKey);

			if (changesetCollection == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(changesetCollection);
		}
		catch (NoSuchCollectionException nsee) {
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
	protected ChangesetCollection removeImpl(
		ChangesetCollection changesetCollection) {
		changesetCollection = toUnwrappedModel(changesetCollection);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(changesetCollection)) {
				changesetCollection = (ChangesetCollection)session.get(ChangesetCollectionImpl.class,
						changesetCollection.getPrimaryKeyObj());
			}

			if (changesetCollection != null) {
				session.delete(changesetCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (changesetCollection != null) {
			clearCache(changesetCollection);
		}

		return changesetCollection;
	}

	@Override
	public ChangesetCollection updateImpl(
		ChangesetCollection changesetCollection) {
		changesetCollection = toUnwrappedModel(changesetCollection);

		boolean isNew = changesetCollection.isNew();

		ChangesetCollectionModelImpl changesetCollectionModelImpl = (ChangesetCollectionModelImpl)changesetCollection;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (changesetCollection.getCreateDate() == null)) {
			if (serviceContext == null) {
				changesetCollection.setCreateDate(now);
			}
			else {
				changesetCollection.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!changesetCollectionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				changesetCollection.setModifiedDate(now);
			}
			else {
				changesetCollection.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (changesetCollection.isNew()) {
				session.save(changesetCollection);

				changesetCollection.setNew(false);
			}
			else {
				changesetCollection = (ChangesetCollection)session.merge(changesetCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ChangesetCollectionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					changesetCollectionModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] { changesetCollectionModelImpl.getCompanyId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] {
					changesetCollectionModelImpl.getGroupId(),
					changesetCollectionModelImpl.getUserId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
				args);

			args = new Object[] {
					changesetCollectionModelImpl.getCompanyId(),
					changesetCollectionModelImpl.getName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((changesetCollectionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						changesetCollectionModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { changesetCollectionModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((changesetCollectionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						changesetCollectionModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { changesetCollectionModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((changesetCollectionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						changesetCollectionModelImpl.getOriginalGroupId(),
						changesetCollectionModelImpl.getOriginalUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
					args);

				args = new Object[] {
						changesetCollectionModelImpl.getGroupId(),
						changesetCollectionModelImpl.getUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
					args);
			}

			if ((changesetCollectionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						changesetCollectionModelImpl.getOriginalCompanyId(),
						changesetCollectionModelImpl.getOriginalName()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N,
					args);

				args = new Object[] {
						changesetCollectionModelImpl.getCompanyId(),
						changesetCollectionModelImpl.getName()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N,
					args);
			}
		}

		entityCache.putResult(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetCollectionImpl.class, changesetCollection.getPrimaryKey(),
			changesetCollection, false);

		clearUniqueFindersCache(changesetCollectionModelImpl, false);
		cacheUniqueFindersCache(changesetCollectionModelImpl);

		changesetCollection.resetOriginalValues();

		return changesetCollection;
	}

	protected ChangesetCollection toUnwrappedModel(
		ChangesetCollection changesetCollection) {
		if (changesetCollection instanceof ChangesetCollectionImpl) {
			return changesetCollection;
		}

		ChangesetCollectionImpl changesetCollectionImpl = new ChangesetCollectionImpl();

		changesetCollectionImpl.setNew(changesetCollection.isNew());
		changesetCollectionImpl.setPrimaryKey(changesetCollection.getPrimaryKey());

		changesetCollectionImpl.setChangesetCollectionId(changesetCollection.getChangesetCollectionId());
		changesetCollectionImpl.setGroupId(changesetCollection.getGroupId());
		changesetCollectionImpl.setCompanyId(changesetCollection.getCompanyId());
		changesetCollectionImpl.setUserId(changesetCollection.getUserId());
		changesetCollectionImpl.setUserName(changesetCollection.getUserName());
		changesetCollectionImpl.setCreateDate(changesetCollection.getCreateDate());
		changesetCollectionImpl.setModifiedDate(changesetCollection.getModifiedDate());
		changesetCollectionImpl.setName(changesetCollection.getName());
		changesetCollectionImpl.setDescription(changesetCollection.getDescription());

		return changesetCollectionImpl;
	}

	/**
	 * Returns the changeset collection with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the changeset collection
	 * @return the changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	@Override
	public ChangesetCollection findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCollectionException {
		ChangesetCollection changesetCollection = fetchByPrimaryKey(primaryKey);

		if (changesetCollection == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return changesetCollection;
	}

	/**
	 * Returns the changeset collection with the primary key or throws a {@link NoSuchCollectionException} if it could not be found.
	 *
	 * @param changesetCollectionId the primary key of the changeset collection
	 * @return the changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	@Override
	public ChangesetCollection findByPrimaryKey(long changesetCollectionId)
		throws NoSuchCollectionException {
		return findByPrimaryKey((Serializable)changesetCollectionId);
	}

	/**
	 * Returns the changeset collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the changeset collection
	 * @return the changeset collection, or <code>null</code> if a changeset collection with the primary key could not be found
	 */
	@Override
	public ChangesetCollection fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
				ChangesetCollectionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		ChangesetCollection changesetCollection = (ChangesetCollection)serializable;

		if (changesetCollection == null) {
			Session session = null;

			try {
				session = openSession();

				changesetCollection = (ChangesetCollection)session.get(ChangesetCollectionImpl.class,
						primaryKey);

				if (changesetCollection != null) {
					cacheResult(changesetCollection);
				}
				else {
					entityCache.putResult(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
						ChangesetCollectionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
					ChangesetCollectionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return changesetCollection;
	}

	/**
	 * Returns the changeset collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param changesetCollectionId the primary key of the changeset collection
	 * @return the changeset collection, or <code>null</code> if a changeset collection with the primary key could not be found
	 */
	@Override
	public ChangesetCollection fetchByPrimaryKey(long changesetCollectionId) {
		return fetchByPrimaryKey((Serializable)changesetCollectionId);
	}

	@Override
	public Map<Serializable, ChangesetCollection> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ChangesetCollection> map = new HashMap<Serializable, ChangesetCollection>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ChangesetCollection changesetCollection = fetchByPrimaryKey(primaryKey);

			if (changesetCollection != null) {
				map.put(primaryKey, changesetCollection);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
					ChangesetCollectionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ChangesetCollection)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CHANGESETCOLLECTION_WHERE_PKS_IN);

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

			for (ChangesetCollection changesetCollection : (List<ChangesetCollection>)q.list()) {
				map.put(changesetCollection.getPrimaryKeyObj(),
					changesetCollection);

				cacheResult(changesetCollection);

				uncachedPrimaryKeys.remove(changesetCollection.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(ChangesetCollectionModelImpl.ENTITY_CACHE_ENABLED,
					ChangesetCollectionImpl.class, primaryKey, nullModel);
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
	 * Returns all the changeset collections.
	 *
	 * @return the changeset collections
	 */
	@Override
	public List<ChangesetCollection> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the changeset collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of changeset collections
	 */
	@Override
	public List<ChangesetCollection> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of changeset collections
	 */
	@Override
	public List<ChangesetCollection> findAll(int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of changeset collections
	 */
	@Override
	public List<ChangesetCollection> findAll(int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator,
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

		List<ChangesetCollection> list = null;

		if (retrieveFromCache) {
			list = (List<ChangesetCollection>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CHANGESETCOLLECTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CHANGESETCOLLECTION;

				if (pagination) {
					sql = sql.concat(ChangesetCollectionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ChangesetCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ChangesetCollection>)QueryUtil.list(q,
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
	 * Removes all the changeset collections from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ChangesetCollection changesetCollection : findAll()) {
			remove(changesetCollection);
		}
	}

	/**
	 * Returns the number of changeset collections.
	 *
	 * @return the number of changeset collections
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CHANGESETCOLLECTION);

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
		return ChangesetCollectionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the changeset collection persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(ChangesetCollectionImpl.class.getName());
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
	private static final String _SQL_SELECT_CHANGESETCOLLECTION = "SELECT changesetCollection FROM ChangesetCollection changesetCollection";
	private static final String _SQL_SELECT_CHANGESETCOLLECTION_WHERE_PKS_IN = "SELECT changesetCollection FROM ChangesetCollection changesetCollection WHERE changesetCollectionId IN (";
	private static final String _SQL_SELECT_CHANGESETCOLLECTION_WHERE = "SELECT changesetCollection FROM ChangesetCollection changesetCollection WHERE ";
	private static final String _SQL_COUNT_CHANGESETCOLLECTION = "SELECT COUNT(changesetCollection) FROM ChangesetCollection changesetCollection";
	private static final String _SQL_COUNT_CHANGESETCOLLECTION_WHERE = "SELECT COUNT(changesetCollection) FROM ChangesetCollection changesetCollection WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "changesetCollection.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ChangesetCollection exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ChangesetCollection exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(ChangesetCollectionPersistenceImpl.class);
}