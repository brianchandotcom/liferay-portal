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

package com.liferay.portlet.documentlibrary.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchFileRankException;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankModelImpl;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the document library file rank service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileRankPersistence
 * @see com.liferay.portlet.documentlibrary.service.persistence.DLFileRankUtil
 * @generated
 */
@ProviderType
public class DLFileRankPersistenceImpl extends BasePersistenceImpl<DLFileRank>
	implements DLFileRankPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLFileRankUtil} to access the document library file rank persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileRankImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, DLFileRankImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, DLFileRankImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, DLFileRankImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, DLFileRankImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			DLFileRankModelImpl.USERID_COLUMN_BITMASK |
			DLFileRankModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the document library file ranks where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching document library file ranks
	 */
	@Override
	public List<DLFileRank> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file ranks where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @return the range of matching document library file ranks
	 */
	@Override
	public List<DLFileRank> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file ranks where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file ranks
	 */
	@Override
	public List<DLFileRank> findByUserId(long userId, int start, int end,
		OrderByComparator<DLFileRank> orderByComparator) {
		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file ranks where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file ranks
	 */
	@Override
	public List<DLFileRank> findByUserId(long userId, int start, int end,
		OrderByComparator<DLFileRank> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<DLFileRank> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileRank>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileRank dlFileRank : list) {
					if ((userId != dlFileRank.getUserId())) {
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

			query.append(_SQL_SELECT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<DLFileRank>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileRank>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file rank in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank findByUserId_First(long userId,
		OrderByComparator<DLFileRank> orderByComparator)
		throws NoSuchFileRankException {
		DLFileRank dlFileRank = fetchByUserId_First(userId, orderByComparator);

		if (dlFileRank != null) {
			return dlFileRank;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileRankException(msg.toString());
	}

	/**
	 * Returns the first document library file rank in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank fetchByUserId_First(long userId,
		OrderByComparator<DLFileRank> orderByComparator) {
		List<DLFileRank> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file rank in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank findByUserId_Last(long userId,
		OrderByComparator<DLFileRank> orderByComparator)
		throws NoSuchFileRankException {
		DLFileRank dlFileRank = fetchByUserId_Last(userId, orderByComparator);

		if (dlFileRank != null) {
			return dlFileRank;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileRankException(msg.toString());
	}

	/**
	 * Returns the last document library file rank in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank fetchByUserId_Last(long userId,
		OrderByComparator<DLFileRank> orderByComparator) {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<DLFileRank> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file ranks before and after the current document library file rank in the ordered set where userId = &#63;.
	 *
	 * @param fileRankId the primary key of the current document library file rank
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file rank
	 * @throws NoSuchFileRankException if a document library file rank with the primary key could not be found
	 */
	@Override
	public DLFileRank[] findByUserId_PrevAndNext(long fileRankId, long userId,
		OrderByComparator<DLFileRank> orderByComparator)
		throws NoSuchFileRankException {
		DLFileRank dlFileRank = findByPrimaryKey(fileRankId);

		Session session = null;

		try {
			session = openSession();

			DLFileRank[] array = new DLFileRankImpl[3];

			array[0] = getByUserId_PrevAndNext(session, dlFileRank, userId,
					orderByComparator, true);

			array[1] = dlFileRank;

			array[2] = getByUserId_PrevAndNext(session, dlFileRank, userId,
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

	protected DLFileRank getByUserId_PrevAndNext(Session session,
		DLFileRank dlFileRank, long userId,
		OrderByComparator<DLFileRank> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILERANK_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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
			query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileRank);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileRank> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file ranks where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (DLFileRank dlFileRank : findByUserId(userId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(dlFileRank);
		}
	}

	/**
	 * Returns the number of document library file ranks where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching document library file ranks
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "dlFileRank.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEENTRYID =
		new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, DLFileRankImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID =
		new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, DLFileRankImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileEntryId",
			new String[] { Long.class.getName() },
			DLFileRankModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileRankModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEENTRYID = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the document library file ranks where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching document library file ranks
	 */
	@Override
	public List<DLFileRank> findByFileEntryId(long fileEntryId) {
		return findByFileEntryId(fileEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file ranks where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @return the range of matching document library file ranks
	 */
	@Override
	public List<DLFileRank> findByFileEntryId(long fileEntryId, int start,
		int end) {
		return findByFileEntryId(fileEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file ranks where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file ranks
	 */
	@Override
	public List<DLFileRank> findByFileEntryId(long fileEntryId, int start,
		int end, OrderByComparator<DLFileRank> orderByComparator) {
		return findByFileEntryId(fileEntryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file ranks where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file ranks
	 */
	@Override
	public List<DLFileRank> findByFileEntryId(long fileEntryId, int start,
		int end, OrderByComparator<DLFileRank> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID;
			finderArgs = new Object[] { fileEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEENTRYID;
			finderArgs = new Object[] { fileEntryId, start, end, orderByComparator };
		}

		List<DLFileRank> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileRank>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileRank dlFileRank : list) {
					if ((fileEntryId != dlFileRank.getFileEntryId())) {
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

			query.append(_SQL_SELECT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				if (!pagination) {
					list = (List<DLFileRank>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileRank>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file rank in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank findByFileEntryId_First(long fileEntryId,
		OrderByComparator<DLFileRank> orderByComparator)
		throws NoSuchFileRankException {
		DLFileRank dlFileRank = fetchByFileEntryId_First(fileEntryId,
				orderByComparator);

		if (dlFileRank != null) {
			return dlFileRank;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileRankException(msg.toString());
	}

	/**
	 * Returns the first document library file rank in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank fetchByFileEntryId_First(long fileEntryId,
		OrderByComparator<DLFileRank> orderByComparator) {
		List<DLFileRank> list = findByFileEntryId(fileEntryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file rank in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank findByFileEntryId_Last(long fileEntryId,
		OrderByComparator<DLFileRank> orderByComparator)
		throws NoSuchFileRankException {
		DLFileRank dlFileRank = fetchByFileEntryId_Last(fileEntryId,
				orderByComparator);

		if (dlFileRank != null) {
			return dlFileRank;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileRankException(msg.toString());
	}

	/**
	 * Returns the last document library file rank in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank fetchByFileEntryId_Last(long fileEntryId,
		OrderByComparator<DLFileRank> orderByComparator) {
		int count = countByFileEntryId(fileEntryId);

		if (count == 0) {
			return null;
		}

		List<DLFileRank> list = findByFileEntryId(fileEntryId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file ranks before and after the current document library file rank in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileRankId the primary key of the current document library file rank
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file rank
	 * @throws NoSuchFileRankException if a document library file rank with the primary key could not be found
	 */
	@Override
	public DLFileRank[] findByFileEntryId_PrevAndNext(long fileRankId,
		long fileEntryId, OrderByComparator<DLFileRank> orderByComparator)
		throws NoSuchFileRankException {
		DLFileRank dlFileRank = findByPrimaryKey(fileRankId);

		Session session = null;

		try {
			session = openSession();

			DLFileRank[] array = new DLFileRankImpl[3];

			array[0] = getByFileEntryId_PrevAndNext(session, dlFileRank,
					fileEntryId, orderByComparator, true);

			array[1] = dlFileRank;

			array[2] = getByFileEntryId_PrevAndNext(session, dlFileRank,
					fileEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileRank getByFileEntryId_PrevAndNext(Session session,
		DLFileRank dlFileRank, long fileEntryId,
		OrderByComparator<DLFileRank> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILERANK_WHERE);

		query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

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
			query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileRank);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileRank> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file ranks where fileEntryId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 */
	@Override
	public void removeByFileEntryId(long fileEntryId) {
		for (DLFileRank dlFileRank : findByFileEntryId(fileEntryId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileRank);
		}
	}

	/**
	 * Returns the number of document library file ranks where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the number of matching document library file ranks
	 */
	@Override
	public int countByFileEntryId(long fileEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_FILEENTRYID;

		Object[] finderArgs = new Object[] { fileEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

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

	private static final String _FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2 = "dlFileRank.fileEntryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, DLFileRankImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, DLFileRankImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			DLFileRankModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileRankModelImpl.USERID_COLUMN_BITMASK |
			DLFileRankModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the document library file ranks where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching document library file ranks
	 */
	@Override
	public List<DLFileRank> findByG_U(long groupId, long userId) {
		return findByG_U(groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file ranks where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @return the range of matching document library file ranks
	 */
	@Override
	public List<DLFileRank> findByG_U(long groupId, long userId, int start,
		int end) {
		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file ranks where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file ranks
	 */
	@Override
	public List<DLFileRank> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator<DLFileRank> orderByComparator) {
		return findByG_U(groupId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file ranks where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file ranks
	 */
	@Override
	public List<DLFileRank> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator<DLFileRank> orderByComparator,
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

		List<DLFileRank> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileRank>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileRank dlFileRank : list) {
					if ((groupId != dlFileRank.getGroupId()) ||
							(userId != dlFileRank.getUserId())) {
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

			query.append(_SQL_SELECT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
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
					list = (List<DLFileRank>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileRank>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file rank in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank findByG_U_First(long groupId, long userId,
		OrderByComparator<DLFileRank> orderByComparator)
		throws NoSuchFileRankException {
		DLFileRank dlFileRank = fetchByG_U_First(groupId, userId,
				orderByComparator);

		if (dlFileRank != null) {
			return dlFileRank;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileRankException(msg.toString());
	}

	/**
	 * Returns the first document library file rank in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank fetchByG_U_First(long groupId, long userId,
		OrderByComparator<DLFileRank> orderByComparator) {
		List<DLFileRank> list = findByG_U(groupId, userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file rank in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank findByG_U_Last(long groupId, long userId,
		OrderByComparator<DLFileRank> orderByComparator)
		throws NoSuchFileRankException {
		DLFileRank dlFileRank = fetchByG_U_Last(groupId, userId,
				orderByComparator);

		if (dlFileRank != null) {
			return dlFileRank;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileRankException(msg.toString());
	}

	/**
	 * Returns the last document library file rank in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank fetchByG_U_Last(long groupId, long userId,
		OrderByComparator<DLFileRank> orderByComparator) {
		int count = countByG_U(groupId, userId);

		if (count == 0) {
			return null;
		}

		List<DLFileRank> list = findByG_U(groupId, userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file ranks before and after the current document library file rank in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param fileRankId the primary key of the current document library file rank
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file rank
	 * @throws NoSuchFileRankException if a document library file rank with the primary key could not be found
	 */
	@Override
	public DLFileRank[] findByG_U_PrevAndNext(long fileRankId, long groupId,
		long userId, OrderByComparator<DLFileRank> orderByComparator)
		throws NoSuchFileRankException {
		DLFileRank dlFileRank = findByPrimaryKey(fileRankId);

		Session session = null;

		try {
			session = openSession();

			DLFileRank[] array = new DLFileRankImpl[3];

			array[0] = getByG_U_PrevAndNext(session, dlFileRank, groupId,
					userId, orderByComparator, true);

			array[1] = dlFileRank;

			array[2] = getByG_U_PrevAndNext(session, dlFileRank, groupId,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileRank getByG_U_PrevAndNext(Session session,
		DLFileRank dlFileRank, long groupId, long userId,
		OrderByComparator<DLFileRank> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILERANK_WHERE);

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
			query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileRank);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileRank> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file ranks where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByG_U(long groupId, long userId) {
		for (DLFileRank dlFileRank : findByG_U(groupId, userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileRank);
		}
	}

	/**
	 * Returns the number of document library file ranks where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching document library file ranks
	 */
	@Override
	public int countByG_U(long groupId, long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U;

		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILERANK_WHERE);

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

	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "dlFileRank.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "dlFileRank.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_A = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, DLFileRankImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_A = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, DLFileRankImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			DLFileRankModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileRankModelImpl.USERID_COLUMN_BITMASK |
			DLFileRankModelImpl.ACTIVE_COLUMN_BITMASK |
			DLFileRankModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_A = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the document library file ranks where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @return the matching document library file ranks
	 */
	@Override
	public List<DLFileRank> findByG_U_A(long groupId, long userId,
		boolean active) {
		return findByG_U_A(groupId, userId, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file ranks where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @return the range of matching document library file ranks
	 */
	@Override
	public List<DLFileRank> findByG_U_A(long groupId, long userId,
		boolean active, int start, int end) {
		return findByG_U_A(groupId, userId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file ranks where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file ranks
	 */
	@Override
	public List<DLFileRank> findByG_U_A(long groupId, long userId,
		boolean active, int start, int end,
		OrderByComparator<DLFileRank> orderByComparator) {
		return findByG_U_A(groupId, userId, active, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file ranks where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file ranks
	 */
	@Override
	public List<DLFileRank> findByG_U_A(long groupId, long userId,
		boolean active, int start, int end,
		OrderByComparator<DLFileRank> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_A;
			finderArgs = new Object[] { groupId, userId, active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_A;
			finderArgs = new Object[] {
					groupId, userId, active,
					
					start, end, orderByComparator
				};
		}

		List<DLFileRank> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileRank>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileRank dlFileRank : list) {
					if ((groupId != dlFileRank.getGroupId()) ||
							(userId != dlFileRank.getUserId()) ||
							(active != dlFileRank.getActive())) {
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

			query.append(_SQL_SELECT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_G_U_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_A_USERID_2);

			query.append(_FINDER_COLUMN_G_U_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(active);

				if (!pagination) {
					list = (List<DLFileRank>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileRank>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file rank in the ordered set where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank findByG_U_A_First(long groupId, long userId,
		boolean active, OrderByComparator<DLFileRank> orderByComparator)
		throws NoSuchFileRankException {
		DLFileRank dlFileRank = fetchByG_U_A_First(groupId, userId, active,
				orderByComparator);

		if (dlFileRank != null) {
			return dlFileRank;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileRankException(msg.toString());
	}

	/**
	 * Returns the first document library file rank in the ordered set where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank fetchByG_U_A_First(long groupId, long userId,
		boolean active, OrderByComparator<DLFileRank> orderByComparator) {
		List<DLFileRank> list = findByG_U_A(groupId, userId, active, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file rank in the ordered set where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank findByG_U_A_Last(long groupId, long userId,
		boolean active, OrderByComparator<DLFileRank> orderByComparator)
		throws NoSuchFileRankException {
		DLFileRank dlFileRank = fetchByG_U_A_Last(groupId, userId, active,
				orderByComparator);

		if (dlFileRank != null) {
			return dlFileRank;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileRankException(msg.toString());
	}

	/**
	 * Returns the last document library file rank in the ordered set where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank fetchByG_U_A_Last(long groupId, long userId,
		boolean active, OrderByComparator<DLFileRank> orderByComparator) {
		int count = countByG_U_A(groupId, userId, active);

		if (count == 0) {
			return null;
		}

		List<DLFileRank> list = findByG_U_A(groupId, userId, active, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file ranks before and after the current document library file rank in the ordered set where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * @param fileRankId the primary key of the current document library file rank
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file rank
	 * @throws NoSuchFileRankException if a document library file rank with the primary key could not be found
	 */
	@Override
	public DLFileRank[] findByG_U_A_PrevAndNext(long fileRankId, long groupId,
		long userId, boolean active,
		OrderByComparator<DLFileRank> orderByComparator)
		throws NoSuchFileRankException {
		DLFileRank dlFileRank = findByPrimaryKey(fileRankId);

		Session session = null;

		try {
			session = openSession();

			DLFileRank[] array = new DLFileRankImpl[3];

			array[0] = getByG_U_A_PrevAndNext(session, dlFileRank, groupId,
					userId, active, orderByComparator, true);

			array[1] = dlFileRank;

			array[2] = getByG_U_A_PrevAndNext(session, dlFileRank, groupId,
					userId, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileRank getByG_U_A_PrevAndNext(Session session,
		DLFileRank dlFileRank, long groupId, long userId, boolean active,
		OrderByComparator<DLFileRank> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILERANK_WHERE);

		query.append(_FINDER_COLUMN_G_U_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_A_USERID_2);

		query.append(_FINDER_COLUMN_G_U_A_ACTIVE_2);

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
			query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileRank);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileRank> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file ranks where groupId = &#63; and userId = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 */
	@Override
	public void removeByG_U_A(long groupId, long userId, boolean active) {
		for (DLFileRank dlFileRank : findByG_U_A(groupId, userId, active,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileRank);
		}
	}

	/**
	 * Returns the number of document library file ranks where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @return the number of matching document library file ranks
	 */
	@Override
	public int countByG_U_A(long groupId, long userId, boolean active) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_A;

		Object[] finderArgs = new Object[] { groupId, userId, active };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_G_U_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_A_USERID_2);

			query.append(_FINDER_COLUMN_G_U_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(active);

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

	private static final String _FINDER_COLUMN_G_U_A_GROUPID_2 = "dlFileRank.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_A_USERID_2 = "dlFileRank.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_A_ACTIVE_2 = "dlFileRank.active = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_U_F = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, DLFileRankImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_U_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			DLFileRankModelImpl.COMPANYID_COLUMN_BITMASK |
			DLFileRankModelImpl.USERID_COLUMN_BITMASK |
			DLFileRankModelImpl.FILEENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_U_F = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the document library file rank where companyId = &#63; and userId = &#63; and fileEntryId = &#63; or throws a {@link NoSuchFileRankException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param fileEntryId the file entry ID
	 * @return the matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank findByC_U_F(long companyId, long userId, long fileEntryId)
		throws NoSuchFileRankException {
		DLFileRank dlFileRank = fetchByC_U_F(companyId, userId, fileEntryId);

		if (dlFileRank == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", fileEntryId=");
			msg.append(fileEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileRankException(msg.toString());
		}

		return dlFileRank;
	}

	/**
	 * Returns the document library file rank where companyId = &#63; and userId = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param fileEntryId the file entry ID
	 * @return the matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank fetchByC_U_F(long companyId, long userId, long fileEntryId) {
		return fetchByC_U_F(companyId, userId, fileEntryId, true);
	}

	/**
	 * Returns the document library file rank where companyId = &#63; and userId = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param fileEntryId the file entry ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	@Override
	public DLFileRank fetchByC_U_F(long companyId, long userId,
		long fileEntryId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, userId, fileEntryId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_U_F,
					finderArgs, this);
		}

		if (result instanceof DLFileRank) {
			DLFileRank dlFileRank = (DLFileRank)result;

			if ((companyId != dlFileRank.getCompanyId()) ||
					(userId != dlFileRank.getUserId()) ||
					(fileEntryId != dlFileRank.getFileEntryId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_C_U_F_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_F_USERID_2);

			query.append(_FINDER_COLUMN_C_U_F_FILEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(fileEntryId);

				List<DLFileRank> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_U_F,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"DLFileRankPersistenceImpl.fetchByC_U_F(long, long, long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					DLFileRank dlFileRank = list.get(0);

					result = dlFileRank;

					cacheResult(dlFileRank);

					if ((dlFileRank.getCompanyId() != companyId) ||
							(dlFileRank.getUserId() != userId) ||
							(dlFileRank.getFileEntryId() != fileEntryId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_U_F,
							finderArgs, dlFileRank);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_U_F, finderArgs);

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
			return (DLFileRank)result;
		}
	}

	/**
	 * Removes the document library file rank where companyId = &#63; and userId = &#63; and fileEntryId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param fileEntryId the file entry ID
	 * @return the document library file rank that was removed
	 */
	@Override
	public DLFileRank removeByC_U_F(long companyId, long userId,
		long fileEntryId) throws NoSuchFileRankException {
		DLFileRank dlFileRank = findByC_U_F(companyId, userId, fileEntryId);

		return remove(dlFileRank);
	}

	/**
	 * Returns the number of document library file ranks where companyId = &#63; and userId = &#63; and fileEntryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param fileEntryId the file entry ID
	 * @return the number of matching document library file ranks
	 */
	@Override
	public int countByC_U_F(long companyId, long userId, long fileEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_U_F;

		Object[] finderArgs = new Object[] { companyId, userId, fileEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_C_U_F_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_F_USERID_2);

			query.append(_FINDER_COLUMN_C_U_F_FILEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(fileEntryId);

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

	private static final String _FINDER_COLUMN_C_U_F_COMPANYID_2 = "dlFileRank.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_F_USERID_2 = "dlFileRank.userId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_F_FILEENTRYID_2 = "dlFileRank.fileEntryId = ?";

	public DLFileRankPersistenceImpl() {
		setModelClass(DLFileRank.class);
	}

	/**
	 * Caches the document library file rank in the entity cache if it is enabled.
	 *
	 * @param dlFileRank the document library file rank
	 */
	@Override
	public void cacheResult(DLFileRank dlFileRank) {
		entityCache.putResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankImpl.class, dlFileRank.getPrimaryKey(), dlFileRank);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_U_F,
			new Object[] {
				dlFileRank.getCompanyId(), dlFileRank.getUserId(),
				dlFileRank.getFileEntryId()
			}, dlFileRank);

		dlFileRank.resetOriginalValues();
	}

	/**
	 * Caches the document library file ranks in the entity cache if it is enabled.
	 *
	 * @param dlFileRanks the document library file ranks
	 */
	@Override
	public void cacheResult(List<DLFileRank> dlFileRanks) {
		for (DLFileRank dlFileRank : dlFileRanks) {
			if (entityCache.getResult(
						DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
						DLFileRankImpl.class, dlFileRank.getPrimaryKey()) == null) {
				cacheResult(dlFileRank);
			}
			else {
				dlFileRank.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all document library file ranks.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DLFileRankImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the document library file rank.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFileRank dlFileRank) {
		entityCache.removeResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankImpl.class, dlFileRank.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DLFileRankModelImpl)dlFileRank);
	}

	@Override
	public void clearCache(List<DLFileRank> dlFileRanks) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLFileRank dlFileRank : dlFileRanks) {
			entityCache.removeResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
				DLFileRankImpl.class, dlFileRank.getPrimaryKey());

			clearUniqueFindersCache((DLFileRankModelImpl)dlFileRank);
		}
	}

	protected void cacheUniqueFindersCache(
		DLFileRankModelImpl dlFileRankModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					dlFileRankModelImpl.getCompanyId(),
					dlFileRankModelImpl.getUserId(),
					dlFileRankModelImpl.getFileEntryId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_C_U_F, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_C_U_F, args,
				dlFileRankModelImpl);
		}
		else {
			if ((dlFileRankModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_U_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileRankModelImpl.getCompanyId(),
						dlFileRankModelImpl.getUserId(),
						dlFileRankModelImpl.getFileEntryId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_C_U_F, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_C_U_F, args,
					dlFileRankModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		DLFileRankModelImpl dlFileRankModelImpl) {
		Object[] args = new Object[] {
				dlFileRankModelImpl.getCompanyId(),
				dlFileRankModelImpl.getUserId(),
				dlFileRankModelImpl.getFileEntryId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_C_U_F, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_C_U_F, args);

		if ((dlFileRankModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_U_F.getColumnBitmask()) != 0) {
			args = new Object[] {
					dlFileRankModelImpl.getOriginalCompanyId(),
					dlFileRankModelImpl.getOriginalUserId(),
					dlFileRankModelImpl.getOriginalFileEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_U_F, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_U_F, args);
		}
	}

	/**
	 * Creates a new document library file rank with the primary key. Does not add the document library file rank to the database.
	 *
	 * @param fileRankId the primary key for the new document library file rank
	 * @return the new document library file rank
	 */
	@Override
	public DLFileRank create(long fileRankId) {
		DLFileRank dlFileRank = new DLFileRankImpl();

		dlFileRank.setNew(true);
		dlFileRank.setPrimaryKey(fileRankId);

		return dlFileRank;
	}

	/**
	 * Removes the document library file rank with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileRankId the primary key of the document library file rank
	 * @return the document library file rank that was removed
	 * @throws NoSuchFileRankException if a document library file rank with the primary key could not be found
	 */
	@Override
	public DLFileRank remove(long fileRankId) throws NoSuchFileRankException {
		return remove((Serializable)fileRankId);
	}

	/**
	 * Removes the document library file rank with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the document library file rank
	 * @return the document library file rank that was removed
	 * @throws NoSuchFileRankException if a document library file rank with the primary key could not be found
	 */
	@Override
	public DLFileRank remove(Serializable primaryKey)
		throws NoSuchFileRankException {
		Session session = null;

		try {
			session = openSession();

			DLFileRank dlFileRank = (DLFileRank)session.get(DLFileRankImpl.class,
					primaryKey);

			if (dlFileRank == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFileRankException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(dlFileRank);
		}
		catch (NoSuchFileRankException nsee) {
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
	protected DLFileRank removeImpl(DLFileRank dlFileRank) {
		dlFileRank = toUnwrappedModel(dlFileRank);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlFileRank)) {
				dlFileRank = (DLFileRank)session.get(DLFileRankImpl.class,
						dlFileRank.getPrimaryKeyObj());
			}

			if (dlFileRank != null) {
				session.delete(dlFileRank);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlFileRank != null) {
			clearCache(dlFileRank);
		}

		return dlFileRank;
	}

	@Override
	public DLFileRank updateImpl(DLFileRank dlFileRank) {
		dlFileRank = toUnwrappedModel(dlFileRank);

		boolean isNew = dlFileRank.isNew();

		DLFileRankModelImpl dlFileRankModelImpl = (DLFileRankModelImpl)dlFileRank;

		Session session = null;

		try {
			session = openSession();

			if (dlFileRank.isNew()) {
				session.save(dlFileRank);

				dlFileRank.setNew(false);
			}
			else {
				dlFileRank = (DLFileRank)session.merge(dlFileRank);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DLFileRankModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((dlFileRankModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileRankModelImpl.getOriginalUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { dlFileRankModelImpl.getUserId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((dlFileRankModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileRankModelImpl.getOriginalFileEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID,
					args);

				args = new Object[] { dlFileRankModelImpl.getFileEntryId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID,
					args);
			}

			if ((dlFileRankModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileRankModelImpl.getOriginalGroupId(),
						dlFileRankModelImpl.getOriginalUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
					args);

				args = new Object[] {
						dlFileRankModelImpl.getGroupId(),
						dlFileRankModelImpl.getUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
					args);
			}

			if ((dlFileRankModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileRankModelImpl.getOriginalGroupId(),
						dlFileRankModelImpl.getOriginalUserId(),
						dlFileRankModelImpl.getOriginalActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_A,
					args);

				args = new Object[] {
						dlFileRankModelImpl.getGroupId(),
						dlFileRankModelImpl.getUserId(),
						dlFileRankModelImpl.getActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_A,
					args);
			}
		}

		entityCache.putResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankImpl.class, dlFileRank.getPrimaryKey(), dlFileRank, false);

		clearUniqueFindersCache(dlFileRankModelImpl);
		cacheUniqueFindersCache(dlFileRankModelImpl, isNew);

		dlFileRank.resetOriginalValues();

		return dlFileRank;
	}

	protected DLFileRank toUnwrappedModel(DLFileRank dlFileRank) {
		if (dlFileRank instanceof DLFileRankImpl) {
			return dlFileRank;
		}

		DLFileRankImpl dlFileRankImpl = new DLFileRankImpl();

		dlFileRankImpl.setNew(dlFileRank.isNew());
		dlFileRankImpl.setPrimaryKey(dlFileRank.getPrimaryKey());

		dlFileRankImpl.setFileRankId(dlFileRank.getFileRankId());
		dlFileRankImpl.setGroupId(dlFileRank.getGroupId());
		dlFileRankImpl.setCompanyId(dlFileRank.getCompanyId());
		dlFileRankImpl.setUserId(dlFileRank.getUserId());
		dlFileRankImpl.setCreateDate(dlFileRank.getCreateDate());
		dlFileRankImpl.setFileEntryId(dlFileRank.getFileEntryId());
		dlFileRankImpl.setActive(dlFileRank.isActive());

		return dlFileRankImpl;
	}

	/**
	 * Returns the document library file rank with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file rank
	 * @return the document library file rank
	 * @throws NoSuchFileRankException if a document library file rank with the primary key could not be found
	 */
	@Override
	public DLFileRank findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFileRankException {
		DLFileRank dlFileRank = fetchByPrimaryKey(primaryKey);

		if (dlFileRank == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFileRankException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return dlFileRank;
	}

	/**
	 * Returns the document library file rank with the primary key or throws a {@link NoSuchFileRankException} if it could not be found.
	 *
	 * @param fileRankId the primary key of the document library file rank
	 * @return the document library file rank
	 * @throws NoSuchFileRankException if a document library file rank with the primary key could not be found
	 */
	@Override
	public DLFileRank findByPrimaryKey(long fileRankId)
		throws NoSuchFileRankException {
		return findByPrimaryKey((Serializable)fileRankId);
	}

	/**
	 * Returns the document library file rank with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file rank
	 * @return the document library file rank, or <code>null</code> if a document library file rank with the primary key could not be found
	 */
	@Override
	public DLFileRank fetchByPrimaryKey(Serializable primaryKey) {
		DLFileRank dlFileRank = (DLFileRank)entityCache.getResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
				DLFileRankImpl.class, primaryKey);

		if (dlFileRank == _nullDLFileRank) {
			return null;
		}

		if (dlFileRank == null) {
			Session session = null;

			try {
				session = openSession();

				dlFileRank = (DLFileRank)session.get(DLFileRankImpl.class,
						primaryKey);

				if (dlFileRank != null) {
					cacheResult(dlFileRank);
				}
				else {
					entityCache.putResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
						DLFileRankImpl.class, primaryKey, _nullDLFileRank);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
					DLFileRankImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return dlFileRank;
	}

	/**
	 * Returns the document library file rank with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileRankId the primary key of the document library file rank
	 * @return the document library file rank, or <code>null</code> if a document library file rank with the primary key could not be found
	 */
	@Override
	public DLFileRank fetchByPrimaryKey(long fileRankId) {
		return fetchByPrimaryKey((Serializable)fileRankId);
	}

	@Override
	public Map<Serializable, DLFileRank> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DLFileRank> map = new HashMap<Serializable, DLFileRank>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DLFileRank dlFileRank = fetchByPrimaryKey(primaryKey);

			if (dlFileRank != null) {
				map.put(primaryKey, dlFileRank);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DLFileRank dlFileRank = (DLFileRank)entityCache.getResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
					DLFileRankImpl.class, primaryKey);

			if (dlFileRank == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, dlFileRank);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DLFILERANK_WHERE_PKS_IN);

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

			for (DLFileRank dlFileRank : (List<DLFileRank>)q.list()) {
				map.put(dlFileRank.getPrimaryKeyObj(), dlFileRank);

				cacheResult(dlFileRank);

				uncachedPrimaryKeys.remove(dlFileRank.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
					DLFileRankImpl.class, primaryKey, _nullDLFileRank);
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
	 * Returns all the document library file ranks.
	 *
	 * @return the document library file ranks
	 */
	@Override
	public List<DLFileRank> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file ranks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @return the range of document library file ranks
	 */
	@Override
	public List<DLFileRank> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file ranks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file ranks
	 */
	@Override
	public List<DLFileRank> findAll(int start, int end,
		OrderByComparator<DLFileRank> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file ranks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of document library file ranks
	 */
	@Override
	public List<DLFileRank> findAll(int start, int end,
		OrderByComparator<DLFileRank> orderByComparator,
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

		List<DLFileRank> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileRank>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLFILERANK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILERANK;

				if (pagination) {
					sql = sql.concat(DLFileRankModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DLFileRank>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileRank>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the document library file ranks from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DLFileRank dlFileRank : findAll()) {
			remove(dlFileRank);
		}
	}

	/**
	 * Returns the number of document library file ranks.
	 *
	 * @return the number of document library file ranks
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLFILERANK);

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
		return DLFileRankModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the document library file rank persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DLFileRankImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_DLFILERANK = "SELECT dlFileRank FROM DLFileRank dlFileRank";
	private static final String _SQL_SELECT_DLFILERANK_WHERE_PKS_IN = "SELECT dlFileRank FROM DLFileRank dlFileRank WHERE fileRankId IN (";
	private static final String _SQL_SELECT_DLFILERANK_WHERE = "SELECT dlFileRank FROM DLFileRank dlFileRank WHERE ";
	private static final String _SQL_COUNT_DLFILERANK = "SELECT COUNT(dlFileRank) FROM DLFileRank dlFileRank";
	private static final String _SQL_COUNT_DLFILERANK_WHERE = "SELECT COUNT(dlFileRank) FROM DLFileRank dlFileRank WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileRank.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileRank exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileRank exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DLFileRankPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"active"
			});
	private static final DLFileRank _nullDLFileRank = new DLFileRankImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DLFileRank> toCacheModel() {
				return _nullDLFileRankCacheModel;
			}
		};

	private static final CacheModel<DLFileRank> _nullDLFileRankCacheModel = new CacheModel<DLFileRank>() {
			@Override
			public DLFileRank toEntityModel() {
				return _nullDLFileRank;
			}
		};
}