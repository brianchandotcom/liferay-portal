/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchShardException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Shard;
import com.liferay.portal.model.impl.ShardImpl;
import com.liferay.portal.model.impl.ShardModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the shard service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShardPersistence
 * @see ShardUtil
 * @generated
 */
public class ShardPersistenceImpl extends BasePersistenceImpl<Shard>
	implements ShardPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ShardUtil} to access the shard persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ShardImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardModelImpl.FINDER_CACHE_ENABLED, ShardImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardModelImpl.FINDER_CACHE_ENABLED, ShardImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_NAME = new FinderPath(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardModelImpl.FINDER_CACHE_ENABLED, ShardImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByName",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME = new FinderPath(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardModelImpl.FINDER_CACHE_ENABLED, ShardImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByName",
			new String[] { String.class.getName() },
			ShardModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_NAME = new FinderPath(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByName",
			new String[] { String.class.getName() });

	/**
	 * Returns an ordered range of all the shards where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from ShardModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted in portal side by PK ASC order.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of shards
	 * @param end the upper bound of the range of shards (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shards
	 * @throws SystemException if a system exception occurred
	 */
	protected List<Shard> findByName(String name, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME;
			finderArgs = new Object[] { name };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_NAME;
			finderArgs = new Object[] { name, start, end, orderByComparator };
		}

		List<Shard> list = (List<Shard>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Shard shard : list) {
				if (!Validator.equals(name, shard.getName())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_SHARD_WHERE);

			if (name == null) {
				query.append(_FINDER_COLUMN_NAME_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_NAME_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_NAME_NAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else if (pagination) {
				query.append(ShardModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				if (!pagination) {
					list = (List<Shard>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList(list);
				}
				else {
					list = (List<Shard>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first shard in the default ordered set defined by {@link ShardModelImpl#ORDER_BY_JPQL} where name = &#63;.
	 *
	 * @param name the name
	 * @return the first matching shard
	 * @throws com.liferay.portal.NoSuchShardException if a matching shard could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard findByName_First(String name)
		throws NoSuchShardException, SystemException {
		return findByName_First(name, null);
	}

	/**
	 * Returns the first shard in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shard
	 * @throws com.liferay.portal.NoSuchShardException if a matching shard could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard findByName_First(String name,
		OrderByComparator orderByComparator)
		throws NoSuchShardException, SystemException {
		Shard shard = fetchByName_First(name, orderByComparator);

		if (shard != null) {
			return shard;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchShardException(msg.toString());
	}

	/**
	 * Returns the first shard in the default ordered set defined by {@link ShardModelImpl#ORDER_BY_JPQL} where name = &#63;.
	 *
	 * @param name the name
	 * @return the first matching shard, or <code>null</code> if a matching shard could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard fetchByName_First(String name) throws SystemException {
		return fetchByName_First(name, null);
	}

	/**
	 * Returns the first shard in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shard, or <code>null</code> if a matching shard could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard fetchByName_First(String name,
		OrderByComparator orderByComparator) throws SystemException {
		List<Shard> list = findByName(name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last shard in the default ordered set defined by {@link ShardModelImpl#ORDER_BY_JPQL} where name = &#63;.
	 *
	 * @param name the name
	 * @return the last matching shard
	 * @throws com.liferay.portal.NoSuchShardException if a matching shard could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard findByName_Last(String name)
		throws NoSuchShardException, SystemException {
		return findByName_Last(name, null);
	}

	/**
	 * Returns the last shard in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shard
	 * @throws com.liferay.portal.NoSuchShardException if a matching shard could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard findByName_Last(String name,
		OrderByComparator orderByComparator)
		throws NoSuchShardException, SystemException {
		Shard shard = fetchByName_Last(name, orderByComparator);

		if (shard != null) {
			return shard;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchShardException(msg.toString());
	}

	/**
	 * Returns the last shard in the default ordered set defined by {@link ShardModelImpl#ORDER_BY_JPQL} where name = &#63;.
	 *
	 * @param name the name
	 * @return the last matching shard, or <code>null</code> if a matching shard could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard fetchByName_Last(String name) throws SystemException {
		return fetchByName_Last(name, null);
	}

	/**
	 * Returns the last shard in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shard, or <code>null</code> if a matching shard could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard fetchByName_Last(String name,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByName(name);

		List<Shard> list = findByName(name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the shards where name = &#63; from the database.
	 *
	 * @param name the name
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByName(String name) throws SystemException {
		for (Shard shard : findByName(name, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(shard);
		}
	}

	/**
	 * Returns the number of shards where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching shards
	 * @throws SystemException if a system exception occurred
	 */
	public int countByName(String name) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_NAME;

		Object[] finderArgs = new Object[] { name };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHARD_WHERE);

			if (name == null) {
				query.append(_FINDER_COLUMN_NAME_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_NAME_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_NAME_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
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

	private static final String _FINDER_COLUMN_NAME_NAME_1 = "shard.name IS NULL";
	private static final String _FINDER_COLUMN_NAME_NAME_2 = "shard.name = ?";
	private static final String _FINDER_COLUMN_NAME_NAME_3 = "(shard.name IS NULL OR shard.name = ?)";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardModelImpl.FINDER_CACHE_ENABLED, ShardImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardModelImpl.FINDER_CACHE_ENABLED, ShardImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			ShardModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			ShardModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns an ordered range of all the shards where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from ShardModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted in portal side by PK ASC order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of shards
	 * @param end the upper bound of the range of shards (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shards
	 * @throws SystemException if a system exception occurred
	 */
	protected List<Shard> findByC_C(long classNameId, long classPK, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<Shard> list = (List<Shard>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Shard shard : list) {
				if ((classNameId != shard.getClassNameId()) ||
						(classPK != shard.getClassPK())) {
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

			query.append(_SQL_SELECT_SHARD_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else if (pagination) {
				query.append(ShardModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<Shard>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList(list);
				}
				else {
					list = (List<Shard>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first shard in the default ordered set defined by {@link ShardModelImpl#ORDER_BY_JPQL} where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the first matching shard
	 * @throws com.liferay.portal.NoSuchShardException if a matching shard could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard findByC_C_First(long classNameId, long classPK)
		throws NoSuchShardException, SystemException {
		return findByC_C_First(classNameId, classPK, null);
	}

	/**
	 * Returns the first shard in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shard
	 * @throws com.liferay.portal.NoSuchShardException if a matching shard could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard findByC_C_First(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchShardException, SystemException {
		Shard shard = fetchByC_C_First(classNameId, classPK, orderByComparator);

		if (shard != null) {
			return shard;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchShardException(msg.toString());
	}

	/**
	 * Returns the first shard in the default ordered set defined by {@link ShardModelImpl#ORDER_BY_JPQL} where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the first matching shard, or <code>null</code> if a matching shard could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard fetchByC_C_First(long classNameId, long classPK)
		throws SystemException {
		return fetchByC_C_First(classNameId, classPK, null);
	}

	/**
	 * Returns the first shard in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shard, or <code>null</code> if a matching shard could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard fetchByC_C_First(long classNameId, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		List<Shard> list = findByC_C(classNameId, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last shard in the default ordered set defined by {@link ShardModelImpl#ORDER_BY_JPQL} where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the last matching shard
	 * @throws com.liferay.portal.NoSuchShardException if a matching shard could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard findByC_C_Last(long classNameId, long classPK)
		throws NoSuchShardException, SystemException {
		return findByC_C_Last(classNameId, classPK, null);
	}

	/**
	 * Returns the last shard in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shard
	 * @throws com.liferay.portal.NoSuchShardException if a matching shard could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard findByC_C_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchShardException, SystemException {
		Shard shard = fetchByC_C_Last(classNameId, classPK, orderByComparator);

		if (shard != null) {
			return shard;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchShardException(msg.toString());
	}

	/**
	 * Returns the last shard in the default ordered set defined by {@link ShardModelImpl#ORDER_BY_JPQL} where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the last matching shard, or <code>null</code> if a matching shard could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard fetchByC_C_Last(long classNameId, long classPK)
		throws SystemException {
		return fetchByC_C_Last(classNameId, classPK, null);
	}

	/**
	 * Returns the last shard in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shard, or <code>null</code> if a matching shard could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard fetchByC_C_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_C(classNameId, classPK);

		List<Shard> list = findByC_C(classNameId, classPK, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the shards where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (Shard shard : findByC_C(classNameId, classPK, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(shard);
		}
	}

	/**
	 * Returns the number of shards where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching shards
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SHARD_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "shard.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "shard.classPK = ?";

	/**
	 * Caches the shard in the entity cache if it is enabled.
	 *
	 * @param shard the shard
	 */
	public void cacheResult(Shard shard) {
		EntityCacheUtil.putResult(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardImpl.class, shard.getPrimaryKey(), shard);

		shard.resetOriginalValues();
	}

	/**
	 * Caches the shards in the entity cache if it is enabled.
	 *
	 * @param shards the shards
	 */
	public void cacheResult(List<Shard> shards) {
		for (Shard shard : shards) {
			if (EntityCacheUtil.getResult(ShardModelImpl.ENTITY_CACHE_ENABLED,
						ShardImpl.class, shard.getPrimaryKey()) == null) {
				cacheResult(shard);
			}
			else {
				shard.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all shards.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ShardImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ShardImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the shard.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Shard shard) {
		EntityCacheUtil.removeResult(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardImpl.class, shard.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Shard> shards) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Shard shard : shards) {
			EntityCacheUtil.removeResult(ShardModelImpl.ENTITY_CACHE_ENABLED,
				ShardImpl.class, shard.getPrimaryKey());
		}
	}

	/**
	 * Creates a new shard with the primary key. Does not add the shard to the database.
	 *
	 * @param shardId the primary key for the new shard
	 * @return the new shard
	 */
	public Shard create(long shardId) {
		Shard shard = new ShardImpl();

		shard.setNew(true);
		shard.setPrimaryKey(shardId);

		return shard;
	}

	/**
	 * Removes the shard with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param shardId the primary key of the shard
	 * @return the shard that was removed
	 * @throws com.liferay.portal.NoSuchShardException if a shard with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard remove(long shardId)
		throws NoSuchShardException, SystemException {
		return remove(Long.valueOf(shardId));
	}

	/**
	 * Removes the shard with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the shard
	 * @return the shard that was removed
	 * @throws com.liferay.portal.NoSuchShardException if a shard with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Shard remove(Serializable primaryKey)
		throws NoSuchShardException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Shard shard = (Shard)session.get(ShardImpl.class, primaryKey);

			if (shard == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchShardException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(shard);
		}
		catch (NoSuchShardException nsee) {
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
	protected Shard removeImpl(Shard shard) throws SystemException {
		shard = toUnwrappedModel(shard);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(shard)) {
				shard = (Shard)session.get(ShardImpl.class,
						shard.getPrimaryKeyObj());
			}

			if (shard != null) {
				session.delete(shard);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (shard != null) {
			clearCache(shard);
		}

		return shard;
	}

	@Override
	public Shard updateImpl(com.liferay.portal.model.Shard shard)
		throws SystemException {
		shard = toUnwrappedModel(shard);

		boolean isNew = shard.isNew();

		Session session = null;

		try {
			session = openSession();

			if (shard.isNew()) {
				session.save(shard);

				shard.setNew(false);
			}
			else {
				session.merge(shard);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ShardModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(ShardModelImpl.ENTITY_CACHE_ENABLED,
			ShardImpl.class, shard.getPrimaryKey(), shard);

		return shard;
	}

	protected Shard toUnwrappedModel(Shard shard) {
		if (shard instanceof ShardImpl) {
			return shard;
		}

		ShardImpl shardImpl = new ShardImpl();

		shardImpl.setNew(shard.isNew());
		shardImpl.setPrimaryKey(shard.getPrimaryKey());

		shardImpl.setShardId(shard.getShardId());
		shardImpl.setClassNameId(shard.getClassNameId());
		shardImpl.setClassPK(shard.getClassPK());
		shardImpl.setName(shard.getName());

		return shardImpl;
	}

	/**
	 * Returns the shard with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the shard
	 * @return the shard
	 * @throws com.liferay.portal.NoSuchModelException if a shard with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Shard findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the shard with the primary key or throws a {@link com.liferay.portal.NoSuchShardException} if it could not be found.
	 *
	 * @param shardId the primary key of the shard
	 * @return the shard
	 * @throws com.liferay.portal.NoSuchShardException if a shard with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard findByPrimaryKey(long shardId)
		throws NoSuchShardException, SystemException {
		Shard shard = fetchByPrimaryKey(shardId);

		if (shard == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + shardId);
			}

			throw new NoSuchShardException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				shardId);
		}

		return shard;
	}

	/**
	 * Returns the shard with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the shard
	 * @return the shard, or <code>null</code> if a shard with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Shard fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the shard with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param shardId the primary key of the shard
	 * @return the shard, or <code>null</code> if a shard with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Shard fetchByPrimaryKey(long shardId) throws SystemException {
		Shard shard = (Shard)EntityCacheUtil.getResult(ShardModelImpl.ENTITY_CACHE_ENABLED,
				ShardImpl.class, shardId);

		if (shard == _nullShard) {
			return null;
		}

		if (shard == null) {
			Session session = null;

			try {
				session = openSession();

				shard = (Shard)session.get(ShardImpl.class,
						Long.valueOf(shardId));

				if (shard != null) {
					cacheResult(shard);
				}
				else {
					EntityCacheUtil.putResult(ShardModelImpl.ENTITY_CACHE_ENABLED,
						ShardImpl.class, shardId, _nullShard);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(ShardModelImpl.ENTITY_CACHE_ENABLED,
					ShardImpl.class, shardId);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return shard;
	}

	/**
	 * Returns all the shards.
	 *
	 * @return the shards
	 * @throws SystemException if a system exception occurred
	 */
	public List<Shard> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shards.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from ShardModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted in portal side by PK ASC order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shards
	 * @param end the upper bound of the range of shards (not inclusive)
	 * @return the range of shards
	 * @throws SystemException if a system exception occurred
	 */
	public List<Shard> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the shards.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from ShardModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted in portal side by PK ASC order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shards
	 * @param end the upper bound of the range of shards (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of shards
	 * @throws SystemException if a system exception occurred
	 */
	public List<Shard> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<Shard> list = (List<Shard>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SHARD);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SHARD;

				if (pagination) {
					sql = sql.concat(ShardModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Shard>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList(list);
				}
				else {
					list = (List<Shard>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Removes all the shards from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Shard shard : findAll()) {
			remove(shard);
		}
	}

	/**
	 * Returns the number of shards.
	 *
	 * @return the number of shards
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SHARD);

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
	 * Initializes the shard persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Shard")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Shard>> listenersList = new ArrayList<ModelListener<Shard>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Shard>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ShardImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortalPreferencesPersistence.class)
	protected PortalPreferencesPersistence portalPreferencesPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = RepositoryEntryPersistence.class)
	protected RepositoryEntryPersistence repositoryEntryPersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = ResourceTypePermissionPersistence.class)
	protected ResourceTypePermissionPersistence resourceTypePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserNotificationEventPersistence.class)
	protected UserNotificationEventPersistence userNotificationEventPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = VirtualHostPersistence.class)
	protected VirtualHostPersistence virtualHostPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_SHARD = "SELECT shard FROM Shard shard";
	private static final String _SQL_SELECT_SHARD_WHERE = "SELECT shard FROM Shard shard WHERE ";
	private static final String _SQL_COUNT_SHARD = "SELECT COUNT(shard) FROM Shard shard";
	private static final String _SQL_COUNT_SHARD_WHERE = "SELECT COUNT(shard) FROM Shard shard WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "shard.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Shard exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Shard exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(ShardPersistenceImpl.class);
	private static Shard _nullShard = new ShardImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Shard> toCacheModel() {
				return _nullShardCacheModel;
			}
		};

	private static CacheModel<Shard> _nullShardCacheModel = new CacheModel<Shard>() {
			public Shard toEntityModel() {
				return _nullShard;
			}
		};
}