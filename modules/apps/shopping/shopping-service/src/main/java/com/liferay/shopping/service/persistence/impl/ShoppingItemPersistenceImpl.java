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

package com.liferay.shopping.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.shopping.exception.NoSuchItemException;
import com.liferay.shopping.model.ShoppingItem;
import com.liferay.shopping.model.impl.ShoppingItemImpl;
import com.liferay.shopping.model.impl.ShoppingItemModelImpl;
import com.liferay.shopping.service.persistence.ShoppingItemPersistence;

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
 * The persistence implementation for the shopping item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemPersistence
 * @see com.liferay.shopping.service.persistence.ShoppingItemUtil
 * @generated
 */
@ProviderType
public class ShoppingItemPersistenceImpl extends BasePersistenceImpl<ShoppingItem>
	implements ShoppingItemPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ShoppingItemUtil} to access the shopping item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ShoppingItemImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemModelImpl.FINDER_CACHE_ENABLED, ShoppingItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemModelImpl.FINDER_CACHE_ENABLED, ShoppingItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_SMALLIMAGEID = new FinderPath(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemModelImpl.FINDER_CACHE_ENABLED, ShoppingItemImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchBySmallImageId",
			new String[] { Long.class.getName() },
			ShoppingItemModelImpl.SMALLIMAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SMALLIMAGEID = new FinderPath(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySmallImageId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the shopping item where smallImageId = &#63; or throws a {@link NoSuchItemException} if it could not be found.
	 *
	 * @param smallImageId the small image ID
	 * @return the matching shopping item
	 * @throws NoSuchItemException if a matching shopping item could not be found
	 */
	@Override
	public ShoppingItem findBySmallImageId(long smallImageId)
		throws NoSuchItemException {
		ShoppingItem shoppingItem = fetchBySmallImageId(smallImageId);

		if (shoppingItem == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("smallImageId=");
			msg.append(smallImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchItemException(msg.toString());
		}

		return shoppingItem;
	}

	/**
	 * Returns the shopping item where smallImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param smallImageId the small image ID
	 * @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	 */
	@Override
	public ShoppingItem fetchBySmallImageId(long smallImageId) {
		return fetchBySmallImageId(smallImageId, true);
	}

	/**
	 * Returns the shopping item where smallImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param smallImageId the small image ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	 */
	@Override
	public ShoppingItem fetchBySmallImageId(long smallImageId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { smallImageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
					finderArgs, this);
		}

		if (result instanceof ShoppingItem) {
			ShoppingItem shoppingItem = (ShoppingItem)result;

			if ((smallImageId != shoppingItem.getSmallImageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SHOPPINGITEM_WHERE);

			query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				List<ShoppingItem> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"ShoppingItemPersistenceImpl.fetchBySmallImageId(long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					ShoppingItem shoppingItem = list.get(0);

					result = shoppingItem;

					cacheResult(shoppingItem);

					if ((shoppingItem.getSmallImageId() != smallImageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
							finderArgs, shoppingItem);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
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
			return (ShoppingItem)result;
		}
	}

	/**
	 * Removes the shopping item where smallImageId = &#63; from the database.
	 *
	 * @param smallImageId the small image ID
	 * @return the shopping item that was removed
	 */
	@Override
	public ShoppingItem removeBySmallImageId(long smallImageId)
		throws NoSuchItemException {
		ShoppingItem shoppingItem = findBySmallImageId(smallImageId);

		return remove(shoppingItem);
	}

	/**
	 * Returns the number of shopping items where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @return the number of matching shopping items
	 */
	@Override
	public int countBySmallImageId(long smallImageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SMALLIMAGEID;

		Object[] finderArgs = new Object[] { smallImageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHOPPINGITEM_WHERE);

			query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

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

	private static final String _FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2 = "shoppingItem.smallImageId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_MEDIUMIMAGEID = new FinderPath(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemModelImpl.FINDER_CACHE_ENABLED, ShoppingItemImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByMediumImageId",
			new String[] { Long.class.getName() },
			ShoppingItemModelImpl.MEDIUMIMAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_MEDIUMIMAGEID = new FinderPath(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByMediumImageId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the shopping item where mediumImageId = &#63; or throws a {@link NoSuchItemException} if it could not be found.
	 *
	 * @param mediumImageId the medium image ID
	 * @return the matching shopping item
	 * @throws NoSuchItemException if a matching shopping item could not be found
	 */
	@Override
	public ShoppingItem findByMediumImageId(long mediumImageId)
		throws NoSuchItemException {
		ShoppingItem shoppingItem = fetchByMediumImageId(mediumImageId);

		if (shoppingItem == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("mediumImageId=");
			msg.append(mediumImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchItemException(msg.toString());
		}

		return shoppingItem;
	}

	/**
	 * Returns the shopping item where mediumImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param mediumImageId the medium image ID
	 * @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	 */
	@Override
	public ShoppingItem fetchByMediumImageId(long mediumImageId) {
		return fetchByMediumImageId(mediumImageId, true);
	}

	/**
	 * Returns the shopping item where mediumImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param mediumImageId the medium image ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	 */
	@Override
	public ShoppingItem fetchByMediumImageId(long mediumImageId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { mediumImageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_MEDIUMIMAGEID,
					finderArgs, this);
		}

		if (result instanceof ShoppingItem) {
			ShoppingItem shoppingItem = (ShoppingItem)result;

			if ((mediumImageId != shoppingItem.getMediumImageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SHOPPINGITEM_WHERE);

			query.append(_FINDER_COLUMN_MEDIUMIMAGEID_MEDIUMIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(mediumImageId);

				List<ShoppingItem> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_MEDIUMIMAGEID,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"ShoppingItemPersistenceImpl.fetchByMediumImageId(long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					ShoppingItem shoppingItem = list.get(0);

					result = shoppingItem;

					cacheResult(shoppingItem);

					if ((shoppingItem.getMediumImageId() != mediumImageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_MEDIUMIMAGEID,
							finderArgs, shoppingItem);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_MEDIUMIMAGEID,
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
			return (ShoppingItem)result;
		}
	}

	/**
	 * Removes the shopping item where mediumImageId = &#63; from the database.
	 *
	 * @param mediumImageId the medium image ID
	 * @return the shopping item that was removed
	 */
	@Override
	public ShoppingItem removeByMediumImageId(long mediumImageId)
		throws NoSuchItemException {
		ShoppingItem shoppingItem = findByMediumImageId(mediumImageId);

		return remove(shoppingItem);
	}

	/**
	 * Returns the number of shopping items where mediumImageId = &#63;.
	 *
	 * @param mediumImageId the medium image ID
	 * @return the number of matching shopping items
	 */
	@Override
	public int countByMediumImageId(long mediumImageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_MEDIUMIMAGEID;

		Object[] finderArgs = new Object[] { mediumImageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHOPPINGITEM_WHERE);

			query.append(_FINDER_COLUMN_MEDIUMIMAGEID_MEDIUMIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(mediumImageId);

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

	private static final String _FINDER_COLUMN_MEDIUMIMAGEID_MEDIUMIMAGEID_2 = "shoppingItem.mediumImageId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_LARGEIMAGEID = new FinderPath(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemModelImpl.FINDER_CACHE_ENABLED, ShoppingItemImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByLargeImageId",
			new String[] { Long.class.getName() },
			ShoppingItemModelImpl.LARGEIMAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LARGEIMAGEID = new FinderPath(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLargeImageId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the shopping item where largeImageId = &#63; or throws a {@link NoSuchItemException} if it could not be found.
	 *
	 * @param largeImageId the large image ID
	 * @return the matching shopping item
	 * @throws NoSuchItemException if a matching shopping item could not be found
	 */
	@Override
	public ShoppingItem findByLargeImageId(long largeImageId)
		throws NoSuchItemException {
		ShoppingItem shoppingItem = fetchByLargeImageId(largeImageId);

		if (shoppingItem == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("largeImageId=");
			msg.append(largeImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchItemException(msg.toString());
		}

		return shoppingItem;
	}

	/**
	 * Returns the shopping item where largeImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param largeImageId the large image ID
	 * @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	 */
	@Override
	public ShoppingItem fetchByLargeImageId(long largeImageId) {
		return fetchByLargeImageId(largeImageId, true);
	}

	/**
	 * Returns the shopping item where largeImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param largeImageId the large image ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	 */
	@Override
	public ShoppingItem fetchByLargeImageId(long largeImageId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { largeImageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
					finderArgs, this);
		}

		if (result instanceof ShoppingItem) {
			ShoppingItem shoppingItem = (ShoppingItem)result;

			if ((largeImageId != shoppingItem.getLargeImageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SHOPPINGITEM_WHERE);

			query.append(_FINDER_COLUMN_LARGEIMAGEID_LARGEIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(largeImageId);

				List<ShoppingItem> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"ShoppingItemPersistenceImpl.fetchByLargeImageId(long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					ShoppingItem shoppingItem = list.get(0);

					result = shoppingItem;

					cacheResult(shoppingItem);

					if ((shoppingItem.getLargeImageId() != largeImageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
							finderArgs, shoppingItem);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
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
			return (ShoppingItem)result;
		}
	}

	/**
	 * Removes the shopping item where largeImageId = &#63; from the database.
	 *
	 * @param largeImageId the large image ID
	 * @return the shopping item that was removed
	 */
	@Override
	public ShoppingItem removeByLargeImageId(long largeImageId)
		throws NoSuchItemException {
		ShoppingItem shoppingItem = findByLargeImageId(largeImageId);

		return remove(shoppingItem);
	}

	/**
	 * Returns the number of shopping items where largeImageId = &#63;.
	 *
	 * @param largeImageId the large image ID
	 * @return the number of matching shopping items
	 */
	@Override
	public int countByLargeImageId(long largeImageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_LARGEIMAGEID;

		Object[] finderArgs = new Object[] { largeImageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHOPPINGITEM_WHERE);

			query.append(_FINDER_COLUMN_LARGEIMAGEID_LARGEIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(largeImageId);

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

	private static final String _FINDER_COLUMN_LARGEIMAGEID_LARGEIMAGEID_2 = "shoppingItem.largeImageId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C = new FinderPath(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemModelImpl.FINDER_CACHE_ENABLED, ShoppingItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C = new FinderPath(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemModelImpl.FINDER_CACHE_ENABLED, ShoppingItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			ShoppingItemModelImpl.GROUPID_COLUMN_BITMASK |
			ShoppingItemModelImpl.CATEGORYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the shopping items where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the matching shopping items
	 */
	@Override
	public List<ShoppingItem> findByG_C(long groupId, long categoryId) {
		return findByG_C(groupId, categoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping items where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of shopping items
	 * @param end the upper bound of the range of shopping items (not inclusive)
	 * @return the range of matching shopping items
	 */
	@Override
	public List<ShoppingItem> findByG_C(long groupId, long categoryId,
		int start, int end) {
		return findByG_C(groupId, categoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping items where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of shopping items
	 * @param end the upper bound of the range of shopping items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping items
	 */
	@Override
	public List<ShoppingItem> findByG_C(long groupId, long categoryId,
		int start, int end, OrderByComparator<ShoppingItem> orderByComparator) {
		return findByG_C(groupId, categoryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the shopping items where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of shopping items
	 * @param end the upper bound of the range of shopping items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching shopping items
	 */
	@Override
	public List<ShoppingItem> findByG_C(long groupId, long categoryId,
		int start, int end, OrderByComparator<ShoppingItem> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] { groupId, categoryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] {
					groupId, categoryId,
					
					start, end, orderByComparator
				};
		}

		List<ShoppingItem> list = null;

		if (retrieveFromCache) {
			list = (List<ShoppingItem>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ShoppingItem shoppingItem : list) {
					if ((groupId != shoppingItem.getGroupId()) ||
							(categoryId != shoppingItem.getCategoryId())) {
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

			query.append(_SQL_SELECT_SHOPPINGITEM_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ShoppingItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				if (!pagination) {
					list = (List<ShoppingItem>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ShoppingItem>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first shopping item in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping item
	 * @throws NoSuchItemException if a matching shopping item could not be found
	 */
	@Override
	public ShoppingItem findByG_C_First(long groupId, long categoryId,
		OrderByComparator<ShoppingItem> orderByComparator)
		throws NoSuchItemException {
		ShoppingItem shoppingItem = fetchByG_C_First(groupId, categoryId,
				orderByComparator);

		if (shoppingItem != null) {
			return shoppingItem;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchItemException(msg.toString());
	}

	/**
	 * Returns the first shopping item in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping item, or <code>null</code> if a matching shopping item could not be found
	 */
	@Override
	public ShoppingItem fetchByG_C_First(long groupId, long categoryId,
		OrderByComparator<ShoppingItem> orderByComparator) {
		List<ShoppingItem> list = findByG_C(groupId, categoryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last shopping item in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping item
	 * @throws NoSuchItemException if a matching shopping item could not be found
	 */
	@Override
	public ShoppingItem findByG_C_Last(long groupId, long categoryId,
		OrderByComparator<ShoppingItem> orderByComparator)
		throws NoSuchItemException {
		ShoppingItem shoppingItem = fetchByG_C_Last(groupId, categoryId,
				orderByComparator);

		if (shoppingItem != null) {
			return shoppingItem;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchItemException(msg.toString());
	}

	/**
	 * Returns the last shopping item in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping item, or <code>null</code> if a matching shopping item could not be found
	 */
	@Override
	public ShoppingItem fetchByG_C_Last(long groupId, long categoryId,
		OrderByComparator<ShoppingItem> orderByComparator) {
		int count = countByG_C(groupId, categoryId);

		if (count == 0) {
			return null;
		}

		List<ShoppingItem> list = findByG_C(groupId, categoryId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the shopping items before and after the current shopping item in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param itemId the primary key of the current shopping item
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping item
	 * @throws NoSuchItemException if a shopping item with the primary key could not be found
	 */
	@Override
	public ShoppingItem[] findByG_C_PrevAndNext(long itemId, long groupId,
		long categoryId, OrderByComparator<ShoppingItem> orderByComparator)
		throws NoSuchItemException {
		ShoppingItem shoppingItem = findByPrimaryKey(itemId);

		Session session = null;

		try {
			session = openSession();

			ShoppingItem[] array = new ShoppingItemImpl[3];

			array[0] = getByG_C_PrevAndNext(session, shoppingItem, groupId,
					categoryId, orderByComparator, true);

			array[1] = shoppingItem;

			array[2] = getByG_C_PrevAndNext(session, shoppingItem, groupId,
					categoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ShoppingItem getByG_C_PrevAndNext(Session session,
		ShoppingItem shoppingItem, long groupId, long categoryId,
		OrderByComparator<ShoppingItem> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SHOPPINGITEM_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

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
			query.append(ShoppingItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(shoppingItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the shopping items that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the matching shopping items that the user has permission to view
	 */
	@Override
	public List<ShoppingItem> filterFindByG_C(long groupId, long categoryId) {
		return filterFindByG_C(groupId, categoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping items that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of shopping items
	 * @param end the upper bound of the range of shopping items (not inclusive)
	 * @return the range of matching shopping items that the user has permission to view
	 */
	@Override
	public List<ShoppingItem> filterFindByG_C(long groupId, long categoryId,
		int start, int end) {
		return filterFindByG_C(groupId, categoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping items that the user has permissions to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of shopping items
	 * @param end the upper bound of the range of shopping items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping items that the user has permission to view
	 */
	@Override
	public List<ShoppingItem> filterFindByG_C(long groupId, long categoryId,
		int start, int end, OrderByComparator<ShoppingItem> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C(groupId, categoryId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGITEM_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SHOPPINGITEM_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGITEM_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(ShoppingItemModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ShoppingItemModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ShoppingItem.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, ShoppingItemImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, ShoppingItemImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			return (List<ShoppingItem>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the shopping items before and after the current shopping item in the ordered set of shopping items that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param itemId the primary key of the current shopping item
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping item
	 * @throws NoSuchItemException if a shopping item with the primary key could not be found
	 */
	@Override
	public ShoppingItem[] filterFindByG_C_PrevAndNext(long itemId,
		long groupId, long categoryId,
		OrderByComparator<ShoppingItem> orderByComparator)
		throws NoSuchItemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_PrevAndNext(itemId, groupId, categoryId,
				orderByComparator);
		}

		ShoppingItem shoppingItem = findByPrimaryKey(itemId);

		Session session = null;

		try {
			session = openSession();

			ShoppingItem[] array = new ShoppingItemImpl[3];

			array[0] = filterGetByG_C_PrevAndNext(session, shoppingItem,
					groupId, categoryId, orderByComparator, true);

			array[1] = shoppingItem;

			array[2] = filterGetByG_C_PrevAndNext(session, shoppingItem,
					groupId, categoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ShoppingItem filterGetByG_C_PrevAndNext(Session session,
		ShoppingItem shoppingItem, long groupId, long categoryId,
		OrderByComparator<ShoppingItem> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGITEM_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SHOPPINGITEM_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGITEM_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(ShoppingItemModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ShoppingItemModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ShoppingItem.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, ShoppingItemImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, ShoppingItemImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(shoppingItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the shopping items where groupId = &#63; and categoryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 */
	@Override
	public void removeByG_C(long groupId, long categoryId) {
		for (ShoppingItem shoppingItem : findByG_C(groupId, categoryId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(shoppingItem);
		}
	}

	/**
	 * Returns the number of shopping items where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the number of matching shopping items
	 */
	@Override
	public int countByG_C(long groupId, long categoryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C;

		Object[] finderArgs = new Object[] { groupId, categoryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SHOPPINGITEM_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

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

	/**
	 * Returns the number of shopping items that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the number of matching shopping items that the user has permission to view
	 */
	@Override
	public int filterCountByG_C(long groupId, long categoryId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C(groupId, categoryId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_SHOPPINGITEM_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ShoppingItem.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "shoppingItem.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CATEGORYID_2 = "shoppingItem.categoryId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_S = new FinderPath(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemModelImpl.FINDER_CACHE_ENABLED, ShoppingItemImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_S",
			new String[] { Long.class.getName(), String.class.getName() },
			ShoppingItemModelImpl.COMPANYID_COLUMN_BITMASK |
			ShoppingItemModelImpl.SKU_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_S = new FinderPath(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the shopping item where companyId = &#63; and sku = &#63; or throws a {@link NoSuchItemException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the matching shopping item
	 * @throws NoSuchItemException if a matching shopping item could not be found
	 */
	@Override
	public ShoppingItem findByC_S(long companyId, String sku)
		throws NoSuchItemException {
		ShoppingItem shoppingItem = fetchByC_S(companyId, sku);

		if (shoppingItem == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", sku=");
			msg.append(sku);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchItemException(msg.toString());
		}

		return shoppingItem;
	}

	/**
	 * Returns the shopping item where companyId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	 */
	@Override
	public ShoppingItem fetchByC_S(long companyId, String sku) {
		return fetchByC_S(companyId, sku, true);
	}

	/**
	 * Returns the shopping item where companyId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	 */
	@Override
	public ShoppingItem fetchByC_S(long companyId, String sku,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, sku };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_S,
					finderArgs, this);
		}

		if (result instanceof ShoppingItem) {
			ShoppingItem shoppingItem = (ShoppingItem)result;

			if ((companyId != shoppingItem.getCompanyId()) ||
					!Objects.equals(sku, shoppingItem.getSku())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SHOPPINGITEM_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			boolean bindSku = false;

			if (sku == null) {
				query.append(_FINDER_COLUMN_C_S_SKU_1);
			}
			else if (sku.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_S_SKU_3);
			}
			else {
				bindSku = true;

				query.append(_FINDER_COLUMN_C_S_SKU_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindSku) {
					qPos.add(sku);
				}

				List<ShoppingItem> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_S, finderArgs,
						list);
				}
				else {
					ShoppingItem shoppingItem = list.get(0);

					result = shoppingItem;

					cacheResult(shoppingItem);

					if ((shoppingItem.getCompanyId() != companyId) ||
							(shoppingItem.getSku() == null) ||
							!shoppingItem.getSku().equals(sku)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_S,
							finderArgs, shoppingItem);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_S, finderArgs);

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
			return (ShoppingItem)result;
		}
	}

	/**
	 * Removes the shopping item where companyId = &#63; and sku = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the shopping item that was removed
	 */
	@Override
	public ShoppingItem removeByC_S(long companyId, String sku)
		throws NoSuchItemException {
		ShoppingItem shoppingItem = findByC_S(companyId, sku);

		return remove(shoppingItem);
	}

	/**
	 * Returns the number of shopping items where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the number of matching shopping items
	 */
	@Override
	public int countByC_S(long companyId, String sku) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_S;

		Object[] finderArgs = new Object[] { companyId, sku };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SHOPPINGITEM_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			boolean bindSku = false;

			if (sku == null) {
				query.append(_FINDER_COLUMN_C_S_SKU_1);
			}
			else if (sku.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_S_SKU_3);
			}
			else {
				bindSku = true;

				query.append(_FINDER_COLUMN_C_S_SKU_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindSku) {
					qPos.add(sku);
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

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 = "shoppingItem.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_S_SKU_1 = "shoppingItem.sku IS NULL";
	private static final String _FINDER_COLUMN_C_S_SKU_2 = "shoppingItem.sku = ?";
	private static final String _FINDER_COLUMN_C_S_SKU_3 = "(shoppingItem.sku IS NULL OR shoppingItem.sku = '')";

	public ShoppingItemPersistenceImpl() {
		setModelClass(ShoppingItem.class);
	}

	/**
	 * Caches the shopping item in the entity cache if it is enabled.
	 *
	 * @param shoppingItem the shopping item
	 */
	@Override
	public void cacheResult(ShoppingItem shoppingItem) {
		entityCache.putResult(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemImpl.class, shoppingItem.getPrimaryKey(), shoppingItem);

		finderCache.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
			new Object[] { shoppingItem.getSmallImageId() }, shoppingItem);

		finderCache.putResult(FINDER_PATH_FETCH_BY_MEDIUMIMAGEID,
			new Object[] { shoppingItem.getMediumImageId() }, shoppingItem);

		finderCache.putResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
			new Object[] { shoppingItem.getLargeImageId() }, shoppingItem);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_S,
			new Object[] { shoppingItem.getCompanyId(), shoppingItem.getSku() },
			shoppingItem);

		shoppingItem.resetOriginalValues();
	}

	/**
	 * Caches the shopping items in the entity cache if it is enabled.
	 *
	 * @param shoppingItems the shopping items
	 */
	@Override
	public void cacheResult(List<ShoppingItem> shoppingItems) {
		for (ShoppingItem shoppingItem : shoppingItems) {
			if (entityCache.getResult(
						ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingItemImpl.class, shoppingItem.getPrimaryKey()) == null) {
				cacheResult(shoppingItem);
			}
			else {
				shoppingItem.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all shopping items.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ShoppingItemImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the shopping item.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ShoppingItem shoppingItem) {
		entityCache.removeResult(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemImpl.class, shoppingItem.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ShoppingItemModelImpl)shoppingItem);
	}

	@Override
	public void clearCache(List<ShoppingItem> shoppingItems) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ShoppingItem shoppingItem : shoppingItems) {
			entityCache.removeResult(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingItemImpl.class, shoppingItem.getPrimaryKey());

			clearUniqueFindersCache((ShoppingItemModelImpl)shoppingItem);
		}
	}

	protected void cacheUniqueFindersCache(
		ShoppingItemModelImpl shoppingItemModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] { shoppingItemModelImpl.getSmallImageId() };

			finderCache.putResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID, args,
				shoppingItemModelImpl);

			args = new Object[] { shoppingItemModelImpl.getMediumImageId() };

			finderCache.putResult(FINDER_PATH_COUNT_BY_MEDIUMIMAGEID, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_MEDIUMIMAGEID, args,
				shoppingItemModelImpl);

			args = new Object[] { shoppingItemModelImpl.getLargeImageId() };

			finderCache.putResult(FINDER_PATH_COUNT_BY_LARGEIMAGEID, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID, args,
				shoppingItemModelImpl);

			args = new Object[] {
					shoppingItemModelImpl.getCompanyId(),
					shoppingItemModelImpl.getSku()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_C_S, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_C_S, args,
				shoppingItemModelImpl);
		}
		else {
			if ((shoppingItemModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_SMALLIMAGEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						shoppingItemModelImpl.getSmallImageId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID, args,
					shoppingItemModelImpl);
			}

			if ((shoppingItemModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_MEDIUMIMAGEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						shoppingItemModelImpl.getMediumImageId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_MEDIUMIMAGEID, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_MEDIUMIMAGEID, args,
					shoppingItemModelImpl);
			}

			if ((shoppingItemModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_LARGEIMAGEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						shoppingItemModelImpl.getLargeImageId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_LARGEIMAGEID, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID, args,
					shoppingItemModelImpl);
			}

			if ((shoppingItemModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						shoppingItemModelImpl.getCompanyId(),
						shoppingItemModelImpl.getSku()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_C_S, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_C_S, args,
					shoppingItemModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		ShoppingItemModelImpl shoppingItemModelImpl) {
		Object[] args = new Object[] { shoppingItemModelImpl.getSmallImageId() };

		finderCache.removeResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID, args);

		if ((shoppingItemModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_SMALLIMAGEID.getColumnBitmask()) != 0) {
			args = new Object[] { shoppingItemModelImpl.getOriginalSmallImageId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID, args);
		}

		args = new Object[] { shoppingItemModelImpl.getMediumImageId() };

		finderCache.removeResult(FINDER_PATH_COUNT_BY_MEDIUMIMAGEID, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_MEDIUMIMAGEID, args);

		if ((shoppingItemModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_MEDIUMIMAGEID.getColumnBitmask()) != 0) {
			args = new Object[] { shoppingItemModelImpl.getOriginalMediumImageId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_MEDIUMIMAGEID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_MEDIUMIMAGEID, args);
		}

		args = new Object[] { shoppingItemModelImpl.getLargeImageId() };

		finderCache.removeResult(FINDER_PATH_COUNT_BY_LARGEIMAGEID, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID, args);

		if ((shoppingItemModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_LARGEIMAGEID.getColumnBitmask()) != 0) {
			args = new Object[] { shoppingItemModelImpl.getOriginalLargeImageId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_LARGEIMAGEID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID, args);
		}

		args = new Object[] {
				shoppingItemModelImpl.getCompanyId(),
				shoppingItemModelImpl.getSku()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_C_S, args);

		if ((shoppingItemModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_S.getColumnBitmask()) != 0) {
			args = new Object[] {
					shoppingItemModelImpl.getOriginalCompanyId(),
					shoppingItemModelImpl.getOriginalSku()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_S, args);
		}
	}

	/**
	 * Creates a new shopping item with the primary key. Does not add the shopping item to the database.
	 *
	 * @param itemId the primary key for the new shopping item
	 * @return the new shopping item
	 */
	@Override
	public ShoppingItem create(long itemId) {
		ShoppingItem shoppingItem = new ShoppingItemImpl();

		shoppingItem.setNew(true);
		shoppingItem.setPrimaryKey(itemId);

		shoppingItem.setCompanyId(companyProvider.getCompanyId());

		return shoppingItem;
	}

	/**
	 * Removes the shopping item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param itemId the primary key of the shopping item
	 * @return the shopping item that was removed
	 * @throws NoSuchItemException if a shopping item with the primary key could not be found
	 */
	@Override
	public ShoppingItem remove(long itemId) throws NoSuchItemException {
		return remove((Serializable)itemId);
	}

	/**
	 * Removes the shopping item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the shopping item
	 * @return the shopping item that was removed
	 * @throws NoSuchItemException if a shopping item with the primary key could not be found
	 */
	@Override
	public ShoppingItem remove(Serializable primaryKey)
		throws NoSuchItemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItem shoppingItem = (ShoppingItem)session.get(ShoppingItemImpl.class,
					primaryKey);

			if (shoppingItem == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(shoppingItem);
		}
		catch (NoSuchItemException nsee) {
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
	protected ShoppingItem removeImpl(ShoppingItem shoppingItem) {
		shoppingItem = toUnwrappedModel(shoppingItem);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(shoppingItem)) {
				shoppingItem = (ShoppingItem)session.get(ShoppingItemImpl.class,
						shoppingItem.getPrimaryKeyObj());
			}

			if (shoppingItem != null) {
				session.delete(shoppingItem);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (shoppingItem != null) {
			clearCache(shoppingItem);
		}

		return shoppingItem;
	}

	@Override
	public ShoppingItem updateImpl(ShoppingItem shoppingItem) {
		shoppingItem = toUnwrappedModel(shoppingItem);

		boolean isNew = shoppingItem.isNew();

		ShoppingItemModelImpl shoppingItemModelImpl = (ShoppingItemModelImpl)shoppingItem;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (shoppingItem.getCreateDate() == null)) {
			if (serviceContext == null) {
				shoppingItem.setCreateDate(now);
			}
			else {
				shoppingItem.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!shoppingItemModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				shoppingItem.setModifiedDate(now);
			}
			else {
				shoppingItem.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (shoppingItem.isNew()) {
				session.save(shoppingItem);

				shoppingItem.setNew(false);
			}
			else {
				shoppingItem = (ShoppingItem)session.merge(shoppingItem);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ShoppingItemModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((shoppingItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						shoppingItemModelImpl.getOriginalGroupId(),
						shoppingItemModelImpl.getOriginalCategoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);

				args = new Object[] {
						shoppingItemModelImpl.getGroupId(),
						shoppingItemModelImpl.getCategoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);
			}
		}

		entityCache.putResult(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemImpl.class, shoppingItem.getPrimaryKey(), shoppingItem,
			false);

		clearUniqueFindersCache(shoppingItemModelImpl);
		cacheUniqueFindersCache(shoppingItemModelImpl, isNew);

		shoppingItem.resetOriginalValues();

		return shoppingItem;
	}

	protected ShoppingItem toUnwrappedModel(ShoppingItem shoppingItem) {
		if (shoppingItem instanceof ShoppingItemImpl) {
			return shoppingItem;
		}

		ShoppingItemImpl shoppingItemImpl = new ShoppingItemImpl();

		shoppingItemImpl.setNew(shoppingItem.isNew());
		shoppingItemImpl.setPrimaryKey(shoppingItem.getPrimaryKey());

		shoppingItemImpl.setItemId(shoppingItem.getItemId());
		shoppingItemImpl.setGroupId(shoppingItem.getGroupId());
		shoppingItemImpl.setCompanyId(shoppingItem.getCompanyId());
		shoppingItemImpl.setUserId(shoppingItem.getUserId());
		shoppingItemImpl.setUserName(shoppingItem.getUserName());
		shoppingItemImpl.setCreateDate(shoppingItem.getCreateDate());
		shoppingItemImpl.setModifiedDate(shoppingItem.getModifiedDate());
		shoppingItemImpl.setCategoryId(shoppingItem.getCategoryId());
		shoppingItemImpl.setSku(shoppingItem.getSku());
		shoppingItemImpl.setName(shoppingItem.getName());
		shoppingItemImpl.setDescription(shoppingItem.getDescription());
		shoppingItemImpl.setProperties(shoppingItem.getProperties());
		shoppingItemImpl.setFields(shoppingItem.isFields());
		shoppingItemImpl.setFieldsQuantities(shoppingItem.getFieldsQuantities());
		shoppingItemImpl.setMinQuantity(shoppingItem.getMinQuantity());
		shoppingItemImpl.setMaxQuantity(shoppingItem.getMaxQuantity());
		shoppingItemImpl.setPrice(shoppingItem.getPrice());
		shoppingItemImpl.setDiscount(shoppingItem.getDiscount());
		shoppingItemImpl.setTaxable(shoppingItem.isTaxable());
		shoppingItemImpl.setShipping(shoppingItem.getShipping());
		shoppingItemImpl.setUseShippingFormula(shoppingItem.isUseShippingFormula());
		shoppingItemImpl.setRequiresShipping(shoppingItem.isRequiresShipping());
		shoppingItemImpl.setStockQuantity(shoppingItem.getStockQuantity());
		shoppingItemImpl.setFeatured(shoppingItem.isFeatured());
		shoppingItemImpl.setSale(shoppingItem.isSale());
		shoppingItemImpl.setSmallImage(shoppingItem.isSmallImage());
		shoppingItemImpl.setSmallImageId(shoppingItem.getSmallImageId());
		shoppingItemImpl.setSmallImageURL(shoppingItem.getSmallImageURL());
		shoppingItemImpl.setMediumImage(shoppingItem.isMediumImage());
		shoppingItemImpl.setMediumImageId(shoppingItem.getMediumImageId());
		shoppingItemImpl.setMediumImageURL(shoppingItem.getMediumImageURL());
		shoppingItemImpl.setLargeImage(shoppingItem.isLargeImage());
		shoppingItemImpl.setLargeImageId(shoppingItem.getLargeImageId());
		shoppingItemImpl.setLargeImageURL(shoppingItem.getLargeImageURL());

		return shoppingItemImpl;
	}

	/**
	 * Returns the shopping item with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping item
	 * @return the shopping item
	 * @throws NoSuchItemException if a shopping item with the primary key could not be found
	 */
	@Override
	public ShoppingItem findByPrimaryKey(Serializable primaryKey)
		throws NoSuchItemException {
		ShoppingItem shoppingItem = fetchByPrimaryKey(primaryKey);

		if (shoppingItem == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return shoppingItem;
	}

	/**
	 * Returns the shopping item with the primary key or throws a {@link NoSuchItemException} if it could not be found.
	 *
	 * @param itemId the primary key of the shopping item
	 * @return the shopping item
	 * @throws NoSuchItemException if a shopping item with the primary key could not be found
	 */
	@Override
	public ShoppingItem findByPrimaryKey(long itemId)
		throws NoSuchItemException {
		return findByPrimaryKey((Serializable)itemId);
	}

	/**
	 * Returns the shopping item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping item
	 * @return the shopping item, or <code>null</code> if a shopping item with the primary key could not be found
	 */
	@Override
	public ShoppingItem fetchByPrimaryKey(Serializable primaryKey) {
		ShoppingItem shoppingItem = (ShoppingItem)entityCache.getResult(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingItemImpl.class, primaryKey);

		if (shoppingItem == _nullShoppingItem) {
			return null;
		}

		if (shoppingItem == null) {
			Session session = null;

			try {
				session = openSession();

				shoppingItem = (ShoppingItem)session.get(ShoppingItemImpl.class,
						primaryKey);

				if (shoppingItem != null) {
					cacheResult(shoppingItem);
				}
				else {
					entityCache.putResult(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingItemImpl.class, primaryKey, _nullShoppingItem);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingItemImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return shoppingItem;
	}

	/**
	 * Returns the shopping item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param itemId the primary key of the shopping item
	 * @return the shopping item, or <code>null</code> if a shopping item with the primary key could not be found
	 */
	@Override
	public ShoppingItem fetchByPrimaryKey(long itemId) {
		return fetchByPrimaryKey((Serializable)itemId);
	}

	@Override
	public Map<Serializable, ShoppingItem> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ShoppingItem> map = new HashMap<Serializable, ShoppingItem>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ShoppingItem shoppingItem = fetchByPrimaryKey(primaryKey);

			if (shoppingItem != null) {
				map.put(primaryKey, shoppingItem);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			ShoppingItem shoppingItem = (ShoppingItem)entityCache.getResult(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingItemImpl.class, primaryKey);

			if (shoppingItem == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, shoppingItem);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SHOPPINGITEM_WHERE_PKS_IN);

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

			for (ShoppingItem shoppingItem : (List<ShoppingItem>)q.list()) {
				map.put(shoppingItem.getPrimaryKeyObj(), shoppingItem);

				cacheResult(shoppingItem);

				uncachedPrimaryKeys.remove(shoppingItem.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(ShoppingItemModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingItemImpl.class, primaryKey, _nullShoppingItem);
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
	 * Returns all the shopping items.
	 *
	 * @return the shopping items
	 */
	@Override
	public List<ShoppingItem> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping items
	 * @param end the upper bound of the range of shopping items (not inclusive)
	 * @return the range of shopping items
	 */
	@Override
	public List<ShoppingItem> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping items
	 * @param end the upper bound of the range of shopping items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of shopping items
	 */
	@Override
	public List<ShoppingItem> findAll(int start, int end,
		OrderByComparator<ShoppingItem> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the shopping items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping items
	 * @param end the upper bound of the range of shopping items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of shopping items
	 */
	@Override
	public List<ShoppingItem> findAll(int start, int end,
		OrderByComparator<ShoppingItem> orderByComparator,
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

		List<ShoppingItem> list = null;

		if (retrieveFromCache) {
			list = (List<ShoppingItem>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SHOPPINGITEM);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SHOPPINGITEM;

				if (pagination) {
					sql = sql.concat(ShoppingItemModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ShoppingItem>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ShoppingItem>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the shopping items from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ShoppingItem shoppingItem : findAll()) {
			remove(shoppingItem);
		}
	}

	/**
	 * Returns the number of shopping items.
	 *
	 * @return the number of shopping items
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SHOPPINGITEM);

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
		return ShoppingItemModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the shopping item persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(ShoppingItemImpl.class.getName());
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
	private static final String _SQL_SELECT_SHOPPINGITEM = "SELECT shoppingItem FROM ShoppingItem shoppingItem";
	private static final String _SQL_SELECT_SHOPPINGITEM_WHERE_PKS_IN = "SELECT shoppingItem FROM ShoppingItem shoppingItem WHERE itemId IN (";
	private static final String _SQL_SELECT_SHOPPINGITEM_WHERE = "SELECT shoppingItem FROM ShoppingItem shoppingItem WHERE ";
	private static final String _SQL_COUNT_SHOPPINGITEM = "SELECT COUNT(shoppingItem) FROM ShoppingItem shoppingItem";
	private static final String _SQL_COUNT_SHOPPINGITEM_WHERE = "SELECT COUNT(shoppingItem) FROM ShoppingItem shoppingItem WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "shoppingItem.itemId";
	private static final String _FILTER_SQL_SELECT_SHOPPINGITEM_WHERE = "SELECT DISTINCT {shoppingItem.*} FROM ShoppingItem shoppingItem WHERE ";
	private static final String _FILTER_SQL_SELECT_SHOPPINGITEM_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {ShoppingItem.*} FROM (SELECT DISTINCT shoppingItem.itemId FROM ShoppingItem shoppingItem WHERE ";
	private static final String _FILTER_SQL_SELECT_SHOPPINGITEM_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN ShoppingItem ON TEMP_TABLE.itemId = ShoppingItem.itemId";
	private static final String _FILTER_SQL_COUNT_SHOPPINGITEM_WHERE = "SELECT COUNT(DISTINCT shoppingItem.itemId) AS COUNT_VALUE FROM ShoppingItem shoppingItem WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "shoppingItem";
	private static final String _FILTER_ENTITY_TABLE = "ShoppingItem";
	private static final String _ORDER_BY_ENTITY_ALIAS = "shoppingItem.";
	private static final String _ORDER_BY_ENTITY_TABLE = "ShoppingItem.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ShoppingItem exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ShoppingItem exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(ShoppingItemPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"fields", "featured", "sale"
			});
	private static final ShoppingItem _nullShoppingItem = new ShoppingItemImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ShoppingItem> toCacheModel() {
				return _nullShoppingItemCacheModel;
			}
		};

	private static final CacheModel<ShoppingItem> _nullShoppingItemCacheModel = new CacheModel<ShoppingItem>() {
			@Override
			public ShoppingItem toEntityModel() {
				return _nullShoppingItem;
			}
		};
}