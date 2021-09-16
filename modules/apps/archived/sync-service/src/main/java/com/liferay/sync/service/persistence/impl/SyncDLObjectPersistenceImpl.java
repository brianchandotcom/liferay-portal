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

package com.liferay.sync.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.sync.exception.NoSuchDLObjectException;
import com.liferay.sync.model.SyncDLObject;
import com.liferay.sync.model.SyncDLObjectTable;
import com.liferay.sync.model.impl.SyncDLObjectImpl;
import com.liferay.sync.model.impl.SyncDLObjectModelImpl;
import com.liferay.sync.service.persistence.SyncDLObjectPersistence;
import com.liferay.sync.service.persistence.impl.constants.SyncPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the sync dl object service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {SyncDLObjectPersistence.class, BasePersistence.class})
public class SyncDLObjectPersistenceImpl
	extends BasePersistenceImpl<SyncDLObject>
	implements SyncDLObjectPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SyncDLObjectUtil</code> to access the sync dl object persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SyncDLObjectImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByLikeTreePath;
	private FinderPath _finderPathWithPaginationCountByLikeTreePath;

	/**
	 * Returns all the sync dl objects where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @return the matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByLikeTreePath(String treePath) {
		return findByLikeTreePath(
			treePath, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sync dl objects where treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param treePath the tree path
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByLikeTreePath(
		String treePath, int start, int end) {

		return findByLikeTreePath(treePath, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param treePath the tree path
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByLikeTreePath(
		String treePath, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return findByLikeTreePath(
			treePath, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param treePath the tree path
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByLikeTreePath(
		String treePath, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		treePath = Objects.toString(treePath, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLikeTreePath;
		finderArgs = new Object[] {treePath, start, end, orderByComparator};

		List<SyncDLObject> list = null;

		if (useFinderCache) {
			list = (List<SyncDLObject>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SyncDLObject syncDLObject : list) {
					if (!StringUtil.wildcardMatches(
							syncDLObject.getTreePath(), treePath, '_', '%',
							'\\', true)) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

			boolean bindTreePath = false;

			if (treePath.isEmpty()) {
				sb.append(_FINDER_COLUMN_LIKETREEPATH_TREEPATH_3);
			}
			else {
				bindTreePath = true;

				sb.append(_FINDER_COLUMN_LIKETREEPATH_TREEPATH_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindTreePath) {
					queryPos.add(treePath);
				}

				list = (List<SyncDLObject>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sync dl object in the ordered set where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByLikeTreePath_First(
			String treePath, OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByLikeTreePath_First(
			treePath, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("treePathLIKE");
		sb.append(treePath);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the first sync dl object in the ordered set where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByLikeTreePath_First(
		String treePath, OrderByComparator<SyncDLObject> orderByComparator) {

		List<SyncDLObject> list = findByLikeTreePath(
			treePath, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sync dl object in the ordered set where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByLikeTreePath_Last(
			String treePath, OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByLikeTreePath_Last(
			treePath, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("treePathLIKE");
		sb.append(treePath);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the last sync dl object in the ordered set where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByLikeTreePath_Last(
		String treePath, OrderByComparator<SyncDLObject> orderByComparator) {

		int count = countByLikeTreePath(treePath);

		if (count == 0) {
			return null;
		}

		List<SyncDLObject> list = findByLikeTreePath(
			treePath, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where treePath LIKE &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	@Override
	public SyncDLObject[] findByLikeTreePath_PrevAndNext(
			long syncDLObjectId, String treePath,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		treePath = Objects.toString(treePath, "");

		SyncDLObject syncDLObject = findByPrimaryKey(syncDLObjectId);

		Session session = null;

		try {
			session = openSession();

			SyncDLObject[] array = new SyncDLObjectImpl[3];

			array[0] = getByLikeTreePath_PrevAndNext(
				session, syncDLObject, treePath, orderByComparator, true);

			array[1] = syncDLObject;

			array[2] = getByLikeTreePath_PrevAndNext(
				session, syncDLObject, treePath, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SyncDLObject getByLikeTreePath_PrevAndNext(
		Session session, SyncDLObject syncDLObject, String treePath,
		OrderByComparator<SyncDLObject> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

		boolean bindTreePath = false;

		if (treePath.isEmpty()) {
			sb.append(_FINDER_COLUMN_LIKETREEPATH_TREEPATH_3);
		}
		else {
			bindTreePath = true;

			sb.append(_FINDER_COLUMN_LIKETREEPATH_TREEPATH_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindTreePath) {
			queryPos.add(treePath);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(syncDLObject)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SyncDLObject> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sync dl objects where treePath LIKE &#63; from the database.
	 *
	 * @param treePath the tree path
	 */
	@Override
	public void removeByLikeTreePath(String treePath) {
		for (SyncDLObject syncDLObject :
				findByLikeTreePath(
					treePath, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(syncDLObject);
		}
	}

	/**
	 * Returns the number of sync dl objects where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @return the number of matching sync dl objects
	 */
	@Override
	public int countByLikeTreePath(String treePath) {
		treePath = Objects.toString(treePath, "");

		FinderPath finderPath = _finderPathWithPaginationCountByLikeTreePath;

		Object[] finderArgs = new Object[] {treePath};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SYNCDLOBJECT_WHERE);

			boolean bindTreePath = false;

			if (treePath.isEmpty()) {
				sb.append(_FINDER_COLUMN_LIKETREEPATH_TREEPATH_3);
			}
			else {
				bindTreePath = true;

				sb.append(_FINDER_COLUMN_LIKETREEPATH_TREEPATH_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindTreePath) {
					queryPos.add(treePath);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_LIKETREEPATH_TREEPATH_2 =
		"syncDLObject.treePath LIKE ?";

	private static final String _FINDER_COLUMN_LIKETREEPATH_TREEPATH_3 =
		"(syncDLObject.treePath IS NULL OR syncDLObject.treePath LIKE '')";

	private FinderPath _finderPathWithPaginationFindByGtM_R;
	private FinderPath _finderPathWithPaginationCountByGtM_R;

	/**
	 * Returns all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @return the matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByGtM_R(
		long modifiedTime, long repositoryId) {

		return findByGtM_R(
			modifiedTime, repositoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByGtM_R(
		long modifiedTime, long repositoryId, int start, int end) {

		return findByGtM_R(modifiedTime, repositoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByGtM_R(
		long modifiedTime, long repositoryId, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return findByGtM_R(
			modifiedTime, repositoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByGtM_R(
		long modifiedTime, long repositoryId, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByGtM_R;
		finderArgs = new Object[] {
			modifiedTime, repositoryId, start, end, orderByComparator
		};

		List<SyncDLObject> list = null;

		if (useFinderCache) {
			list = (List<SyncDLObject>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SyncDLObject syncDLObject : list) {
					if ((modifiedTime >= syncDLObject.getModifiedTime()) ||
						(repositoryId != syncDLObject.getRepositoryId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

			sb.append(_FINDER_COLUMN_GTM_R_MODIFIEDTIME_2);

			sb.append(_FINDER_COLUMN_GTM_R_REPOSITORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(modifiedTime);

				queryPos.add(repositoryId);

				list = (List<SyncDLObject>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByGtM_R_First(
			long modifiedTime, long repositoryId,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByGtM_R_First(
			modifiedTime, repositoryId, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("modifiedTime>");
		sb.append(modifiedTime);

		sb.append(", repositoryId=");
		sb.append(repositoryId);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the first sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByGtM_R_First(
		long modifiedTime, long repositoryId,
		OrderByComparator<SyncDLObject> orderByComparator) {

		List<SyncDLObject> list = findByGtM_R(
			modifiedTime, repositoryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByGtM_R_Last(
			long modifiedTime, long repositoryId,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByGtM_R_Last(
			modifiedTime, repositoryId, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("modifiedTime>");
		sb.append(modifiedTime);

		sb.append(", repositoryId=");
		sb.append(repositoryId);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the last sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByGtM_R_Last(
		long modifiedTime, long repositoryId,
		OrderByComparator<SyncDLObject> orderByComparator) {

		int count = countByGtM_R(modifiedTime, repositoryId);

		if (count == 0) {
			return null;
		}

		List<SyncDLObject> list = findByGtM_R(
			modifiedTime, repositoryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	@Override
	public SyncDLObject[] findByGtM_R_PrevAndNext(
			long syncDLObjectId, long modifiedTime, long repositoryId,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = findByPrimaryKey(syncDLObjectId);

		Session session = null;

		try {
			session = openSession();

			SyncDLObject[] array = new SyncDLObjectImpl[3];

			array[0] = getByGtM_R_PrevAndNext(
				session, syncDLObject, modifiedTime, repositoryId,
				orderByComparator, true);

			array[1] = syncDLObject;

			array[2] = getByGtM_R_PrevAndNext(
				session, syncDLObject, modifiedTime, repositoryId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SyncDLObject getByGtM_R_PrevAndNext(
		Session session, SyncDLObject syncDLObject, long modifiedTime,
		long repositoryId, OrderByComparator<SyncDLObject> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

		sb.append(_FINDER_COLUMN_GTM_R_MODIFIEDTIME_2);

		sb.append(_FINDER_COLUMN_GTM_R_REPOSITORYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(modifiedTime);

		queryPos.add(repositoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(syncDLObject)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SyncDLObject> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; from the database.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 */
	@Override
	public void removeByGtM_R(long modifiedTime, long repositoryId) {
		for (SyncDLObject syncDLObject :
				findByGtM_R(
					modifiedTime, repositoryId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(syncDLObject);
		}
	}

	/**
	 * Returns the number of sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @return the number of matching sync dl objects
	 */
	@Override
	public int countByGtM_R(long modifiedTime, long repositoryId) {
		FinderPath finderPath = _finderPathWithPaginationCountByGtM_R;

		Object[] finderArgs = new Object[] {modifiedTime, repositoryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SYNCDLOBJECT_WHERE);

			sb.append(_FINDER_COLUMN_GTM_R_MODIFIEDTIME_2);

			sb.append(_FINDER_COLUMN_GTM_R_REPOSITORYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(modifiedTime);

				queryPos.add(repositoryId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GTM_R_MODIFIEDTIME_2 =
		"syncDLObject.modifiedTime > ? AND ";

	private static final String _FINDER_COLUMN_GTM_R_REPOSITORYID_2 =
		"syncDLObject.repositoryId = ?";

	private FinderPath _finderPathWithPaginationFindByR_P;
	private FinderPath _finderPathWithoutPaginationFindByR_P;
	private FinderPath _finderPathCountByR_P;

	/**
	 * Returns all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @return the matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_P(
		long repositoryId, long parentFolderId) {

		return findByR_P(
			repositoryId, parentFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_P(
		long repositoryId, long parentFolderId, int start, int end) {

		return findByR_P(repositoryId, parentFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_P(
		long repositoryId, long parentFolderId, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return findByR_P(
			repositoryId, parentFolderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_P(
		long repositoryId, long parentFolderId, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByR_P;
				finderArgs = new Object[] {repositoryId, parentFolderId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByR_P;
			finderArgs = new Object[] {
				repositoryId, parentFolderId, start, end, orderByComparator
			};
		}

		List<SyncDLObject> list = null;

		if (useFinderCache) {
			list = (List<SyncDLObject>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SyncDLObject syncDLObject : list) {
					if ((repositoryId != syncDLObject.getRepositoryId()) ||
						(parentFolderId != syncDLObject.getParentFolderId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

			sb.append(_FINDER_COLUMN_R_P_REPOSITORYID_2);

			sb.append(_FINDER_COLUMN_R_P_PARENTFOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(repositoryId);

				queryPos.add(parentFolderId);

				list = (List<SyncDLObject>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByR_P_First(
			long repositoryId, long parentFolderId,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByR_P_First(
			repositoryId, parentFolderId, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("repositoryId=");
		sb.append(repositoryId);

		sb.append(", parentFolderId=");
		sb.append(parentFolderId);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByR_P_First(
		long repositoryId, long parentFolderId,
		OrderByComparator<SyncDLObject> orderByComparator) {

		List<SyncDLObject> list = findByR_P(
			repositoryId, parentFolderId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByR_P_Last(
			long repositoryId, long parentFolderId,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByR_P_Last(
			repositoryId, parentFolderId, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("repositoryId=");
		sb.append(repositoryId);

		sb.append(", parentFolderId=");
		sb.append(parentFolderId);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByR_P_Last(
		long repositoryId, long parentFolderId,
		OrderByComparator<SyncDLObject> orderByComparator) {

		int count = countByR_P(repositoryId, parentFolderId);

		if (count == 0) {
			return null;
		}

		List<SyncDLObject> list = findByR_P(
			repositoryId, parentFolderId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	@Override
	public SyncDLObject[] findByR_P_PrevAndNext(
			long syncDLObjectId, long repositoryId, long parentFolderId,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = findByPrimaryKey(syncDLObjectId);

		Session session = null;

		try {
			session = openSession();

			SyncDLObject[] array = new SyncDLObjectImpl[3];

			array[0] = getByR_P_PrevAndNext(
				session, syncDLObject, repositoryId, parentFolderId,
				orderByComparator, true);

			array[1] = syncDLObject;

			array[2] = getByR_P_PrevAndNext(
				session, syncDLObject, repositoryId, parentFolderId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SyncDLObject getByR_P_PrevAndNext(
		Session session, SyncDLObject syncDLObject, long repositoryId,
		long parentFolderId, OrderByComparator<SyncDLObject> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

		sb.append(_FINDER_COLUMN_R_P_REPOSITORYID_2);

		sb.append(_FINDER_COLUMN_R_P_PARENTFOLDERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(repositoryId);

		queryPos.add(parentFolderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(syncDLObject)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SyncDLObject> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 */
	@Override
	public void removeByR_P(long repositoryId, long parentFolderId) {
		for (SyncDLObject syncDLObject :
				findByR_P(
					repositoryId, parentFolderId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(syncDLObject);
		}
	}

	/**
	 * Returns the number of sync dl objects where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching sync dl objects
	 */
	@Override
	public int countByR_P(long repositoryId, long parentFolderId) {
		FinderPath finderPath = _finderPathCountByR_P;

		Object[] finderArgs = new Object[] {repositoryId, parentFolderId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SYNCDLOBJECT_WHERE);

			sb.append(_FINDER_COLUMN_R_P_REPOSITORYID_2);

			sb.append(_FINDER_COLUMN_R_P_PARENTFOLDERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(repositoryId);

				queryPos.add(parentFolderId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_R_P_REPOSITORYID_2 =
		"syncDLObject.repositoryId = ? AND ";

	private static final String _FINDER_COLUMN_R_P_PARENTFOLDERID_2 =
		"syncDLObject.parentFolderId = ?";

	private FinderPath _finderPathWithPaginationFindByR_NotE;
	private FinderPath _finderPathWithPaginationCountByR_NotE;

	/**
	 * Returns all the sync dl objects where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @return the matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_NotE(long repositoryId, String event) {
		return findByR_NotE(
			repositoryId, event, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sync dl objects where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_NotE(
		long repositoryId, String event, int start, int end) {

		return findByR_NotE(repositoryId, event, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_NotE(
		long repositoryId, String event, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return findByR_NotE(
			repositoryId, event, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_NotE(
		long repositoryId, String event, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		event = Objects.toString(event, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByR_NotE;
		finderArgs = new Object[] {
			repositoryId, event, start, end, orderByComparator
		};

		List<SyncDLObject> list = null;

		if (useFinderCache) {
			list = (List<SyncDLObject>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SyncDLObject syncDLObject : list) {
					if ((repositoryId != syncDLObject.getRepositoryId()) ||
						event.equals(syncDLObject.getEvent())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

			sb.append(_FINDER_COLUMN_R_NOTE_REPOSITORYID_2);

			boolean bindEvent = false;

			if (event.isEmpty()) {
				sb.append(_FINDER_COLUMN_R_NOTE_EVENT_3);
			}
			else {
				bindEvent = true;

				sb.append(_FINDER_COLUMN_R_NOTE_EVENT_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(repositoryId);

				if (bindEvent) {
					queryPos.add(event);
				}

				list = (List<SyncDLObject>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByR_NotE_First(
			long repositoryId, String event,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByR_NotE_First(
			repositoryId, event, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("repositoryId=");
		sb.append(repositoryId);

		sb.append(", event!=");
		sb.append(event);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByR_NotE_First(
		long repositoryId, String event,
		OrderByComparator<SyncDLObject> orderByComparator) {

		List<SyncDLObject> list = findByR_NotE(
			repositoryId, event, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByR_NotE_Last(
			long repositoryId, String event,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByR_NotE_Last(
			repositoryId, event, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("repositoryId=");
		sb.append(repositoryId);

		sb.append(", event!=");
		sb.append(event);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByR_NotE_Last(
		long repositoryId, String event,
		OrderByComparator<SyncDLObject> orderByComparator) {

		int count = countByR_NotE(repositoryId, event);

		if (count == 0) {
			return null;
		}

		List<SyncDLObject> list = findByR_NotE(
			repositoryId, event, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	@Override
	public SyncDLObject[] findByR_NotE_PrevAndNext(
			long syncDLObjectId, long repositoryId, String event,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		event = Objects.toString(event, "");

		SyncDLObject syncDLObject = findByPrimaryKey(syncDLObjectId);

		Session session = null;

		try {
			session = openSession();

			SyncDLObject[] array = new SyncDLObjectImpl[3];

			array[0] = getByR_NotE_PrevAndNext(
				session, syncDLObject, repositoryId, event, orderByComparator,
				true);

			array[1] = syncDLObject;

			array[2] = getByR_NotE_PrevAndNext(
				session, syncDLObject, repositoryId, event, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SyncDLObject getByR_NotE_PrevAndNext(
		Session session, SyncDLObject syncDLObject, long repositoryId,
		String event, OrderByComparator<SyncDLObject> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

		sb.append(_FINDER_COLUMN_R_NOTE_REPOSITORYID_2);

		boolean bindEvent = false;

		if (event.isEmpty()) {
			sb.append(_FINDER_COLUMN_R_NOTE_EVENT_3);
		}
		else {
			bindEvent = true;

			sb.append(_FINDER_COLUMN_R_NOTE_EVENT_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(repositoryId);

		if (bindEvent) {
			queryPos.add(event);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(syncDLObject)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SyncDLObject> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sync dl objects where repositoryId = &#63; and event &ne; &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 */
	@Override
	public void removeByR_NotE(long repositoryId, String event) {
		for (SyncDLObject syncDLObject :
				findByR_NotE(
					repositoryId, event, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(syncDLObject);
		}
	}

	/**
	 * Returns the number of sync dl objects where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @return the number of matching sync dl objects
	 */
	@Override
	public int countByR_NotE(long repositoryId, String event) {
		event = Objects.toString(event, "");

		FinderPath finderPath = _finderPathWithPaginationCountByR_NotE;

		Object[] finderArgs = new Object[] {repositoryId, event};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SYNCDLOBJECT_WHERE);

			sb.append(_FINDER_COLUMN_R_NOTE_REPOSITORYID_2);

			boolean bindEvent = false;

			if (event.isEmpty()) {
				sb.append(_FINDER_COLUMN_R_NOTE_EVENT_3);
			}
			else {
				bindEvent = true;

				sb.append(_FINDER_COLUMN_R_NOTE_EVENT_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(repositoryId);

				if (bindEvent) {
					queryPos.add(event);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_R_NOTE_REPOSITORYID_2 =
		"syncDLObject.repositoryId = ? AND ";

	private static final String _FINDER_COLUMN_R_NOTE_EVENT_2 =
		"syncDLObject.event != ?";

	private static final String _FINDER_COLUMN_R_NOTE_EVENT_3 =
		"(syncDLObject.event IS NULL OR syncDLObject.event != '')";

	private FinderPath _finderPathWithPaginationFindByR_T;
	private FinderPath _finderPathWithoutPaginationFindByR_T;
	private FinderPath _finderPathCountByR_T;

	/**
	 * Returns all the sync dl objects where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @return the matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_T(long repositoryId, String type) {
		return findByR_T(
			repositoryId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sync dl objects where repositoryId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_T(
		long repositoryId, String type, int start, int end) {

		return findByR_T(repositoryId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_T(
		long repositoryId, String type, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return findByR_T(
			repositoryId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_T(
		long repositoryId, String type, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByR_T;
				finderArgs = new Object[] {repositoryId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByR_T;
			finderArgs = new Object[] {
				repositoryId, type, start, end, orderByComparator
			};
		}

		List<SyncDLObject> list = null;

		if (useFinderCache) {
			list = (List<SyncDLObject>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SyncDLObject syncDLObject : list) {
					if ((repositoryId != syncDLObject.getRepositoryId()) ||
						!type.equals(syncDLObject.getType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

			sb.append(_FINDER_COLUMN_R_T_REPOSITORYID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_R_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_R_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(repositoryId);

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<SyncDLObject>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByR_T_First(
			long repositoryId, String type,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByR_T_First(
			repositoryId, type, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("repositoryId=");
		sb.append(repositoryId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByR_T_First(
		long repositoryId, String type,
		OrderByComparator<SyncDLObject> orderByComparator) {

		List<SyncDLObject> list = findByR_T(
			repositoryId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByR_T_Last(
			long repositoryId, String type,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByR_T_Last(
			repositoryId, type, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("repositoryId=");
		sb.append(repositoryId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByR_T_Last(
		long repositoryId, String type,
		OrderByComparator<SyncDLObject> orderByComparator) {

		int count = countByR_T(repositoryId, type);

		if (count == 0) {
			return null;
		}

		List<SyncDLObject> list = findByR_T(
			repositoryId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where repositoryId = &#63; and type = &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	@Override
	public SyncDLObject[] findByR_T_PrevAndNext(
			long syncDLObjectId, long repositoryId, String type,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		type = Objects.toString(type, "");

		SyncDLObject syncDLObject = findByPrimaryKey(syncDLObjectId);

		Session session = null;

		try {
			session = openSession();

			SyncDLObject[] array = new SyncDLObjectImpl[3];

			array[0] = getByR_T_PrevAndNext(
				session, syncDLObject, repositoryId, type, orderByComparator,
				true);

			array[1] = syncDLObject;

			array[2] = getByR_T_PrevAndNext(
				session, syncDLObject, repositoryId, type, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SyncDLObject getByR_T_PrevAndNext(
		Session session, SyncDLObject syncDLObject, long repositoryId,
		String type, OrderByComparator<SyncDLObject> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

		sb.append(_FINDER_COLUMN_R_T_REPOSITORYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_R_T_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_R_T_TYPE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(repositoryId);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(syncDLObject)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SyncDLObject> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sync dl objects where repositoryId = &#63; and type = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 */
	@Override
	public void removeByR_T(long repositoryId, String type) {
		for (SyncDLObject syncDLObject :
				findByR_T(
					repositoryId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(syncDLObject);
		}
	}

	/**
	 * Returns the number of sync dl objects where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @return the number of matching sync dl objects
	 */
	@Override
	public int countByR_T(long repositoryId, String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByR_T;

		Object[] finderArgs = new Object[] {repositoryId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SYNCDLOBJECT_WHERE);

			sb.append(_FINDER_COLUMN_R_T_REPOSITORYID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_R_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_R_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(repositoryId);

				if (bindType) {
					queryPos.add(type);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_R_T_REPOSITORYID_2 =
		"syncDLObject.repositoryId = ? AND ";

	private static final String _FINDER_COLUMN_R_T_TYPE_2 =
		"syncDLObject.type = ?";

	private static final String _FINDER_COLUMN_R_T_TYPE_3 =
		"(syncDLObject.type IS NULL OR syncDLObject.type = '')";

	private FinderPath _finderPathWithPaginationFindByLikeT_NotE;
	private FinderPath _finderPathWithPaginationCountByLikeT_NotE;

	/**
	 * Returns all the sync dl objects where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @return the matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByLikeT_NotE(String treePath, String event) {
		return findByLikeT_NotE(
			treePath, event, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sync dl objects where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByLikeT_NotE(
		String treePath, String event, int start, int end) {

		return findByLikeT_NotE(treePath, event, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByLikeT_NotE(
		String treePath, String event, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return findByLikeT_NotE(
			treePath, event, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByLikeT_NotE(
		String treePath, String event, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		treePath = Objects.toString(treePath, "");
		event = Objects.toString(event, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLikeT_NotE;
		finderArgs = new Object[] {
			treePath, event, start, end, orderByComparator
		};

		List<SyncDLObject> list = null;

		if (useFinderCache) {
			list = (List<SyncDLObject>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SyncDLObject syncDLObject : list) {
					if (!StringUtil.wildcardMatches(
							syncDLObject.getTreePath(), treePath, '_', '%',
							'\\', true) ||
						event.equals(syncDLObject.getEvent())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

			boolean bindTreePath = false;

			if (treePath.isEmpty()) {
				sb.append(_FINDER_COLUMN_LIKET_NOTE_TREEPATH_3);
			}
			else {
				bindTreePath = true;

				sb.append(_FINDER_COLUMN_LIKET_NOTE_TREEPATH_2);
			}

			boolean bindEvent = false;

			if (event.isEmpty()) {
				sb.append(_FINDER_COLUMN_LIKET_NOTE_EVENT_3);
			}
			else {
				bindEvent = true;

				sb.append(_FINDER_COLUMN_LIKET_NOTE_EVENT_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindTreePath) {
					queryPos.add(treePath);
				}

				if (bindEvent) {
					queryPos.add(event);
				}

				list = (List<SyncDLObject>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sync dl object in the ordered set where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByLikeT_NotE_First(
			String treePath, String event,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByLikeT_NotE_First(
			treePath, event, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("treePathLIKE");
		sb.append(treePath);

		sb.append(", event!=");
		sb.append(event);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the first sync dl object in the ordered set where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByLikeT_NotE_First(
		String treePath, String event,
		OrderByComparator<SyncDLObject> orderByComparator) {

		List<SyncDLObject> list = findByLikeT_NotE(
			treePath, event, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sync dl object in the ordered set where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByLikeT_NotE_Last(
			String treePath, String event,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByLikeT_NotE_Last(
			treePath, event, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("treePathLIKE");
		sb.append(treePath);

		sb.append(", event!=");
		sb.append(event);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the last sync dl object in the ordered set where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByLikeT_NotE_Last(
		String treePath, String event,
		OrderByComparator<SyncDLObject> orderByComparator) {

		int count = countByLikeT_NotE(treePath, event);

		if (count == 0) {
			return null;
		}

		List<SyncDLObject> list = findByLikeT_NotE(
			treePath, event, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param treePath the tree path
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	@Override
	public SyncDLObject[] findByLikeT_NotE_PrevAndNext(
			long syncDLObjectId, String treePath, String event,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		treePath = Objects.toString(treePath, "");
		event = Objects.toString(event, "");

		SyncDLObject syncDLObject = findByPrimaryKey(syncDLObjectId);

		Session session = null;

		try {
			session = openSession();

			SyncDLObject[] array = new SyncDLObjectImpl[3];

			array[0] = getByLikeT_NotE_PrevAndNext(
				session, syncDLObject, treePath, event, orderByComparator,
				true);

			array[1] = syncDLObject;

			array[2] = getByLikeT_NotE_PrevAndNext(
				session, syncDLObject, treePath, event, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SyncDLObject getByLikeT_NotE_PrevAndNext(
		Session session, SyncDLObject syncDLObject, String treePath,
		String event, OrderByComparator<SyncDLObject> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

		boolean bindTreePath = false;

		if (treePath.isEmpty()) {
			sb.append(_FINDER_COLUMN_LIKET_NOTE_TREEPATH_3);
		}
		else {
			bindTreePath = true;

			sb.append(_FINDER_COLUMN_LIKET_NOTE_TREEPATH_2);
		}

		boolean bindEvent = false;

		if (event.isEmpty()) {
			sb.append(_FINDER_COLUMN_LIKET_NOTE_EVENT_3);
		}
		else {
			bindEvent = true;

			sb.append(_FINDER_COLUMN_LIKET_NOTE_EVENT_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindTreePath) {
			queryPos.add(treePath);
		}

		if (bindEvent) {
			queryPos.add(event);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(syncDLObject)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SyncDLObject> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sync dl objects where treePath LIKE &#63; and event &ne; &#63; from the database.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 */
	@Override
	public void removeByLikeT_NotE(String treePath, String event) {
		for (SyncDLObject syncDLObject :
				findByLikeT_NotE(
					treePath, event, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(syncDLObject);
		}
	}

	/**
	 * Returns the number of sync dl objects where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @return the number of matching sync dl objects
	 */
	@Override
	public int countByLikeT_NotE(String treePath, String event) {
		treePath = Objects.toString(treePath, "");
		event = Objects.toString(event, "");

		FinderPath finderPath = _finderPathWithPaginationCountByLikeT_NotE;

		Object[] finderArgs = new Object[] {treePath, event};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SYNCDLOBJECT_WHERE);

			boolean bindTreePath = false;

			if (treePath.isEmpty()) {
				sb.append(_FINDER_COLUMN_LIKET_NOTE_TREEPATH_3);
			}
			else {
				bindTreePath = true;

				sb.append(_FINDER_COLUMN_LIKET_NOTE_TREEPATH_2);
			}

			boolean bindEvent = false;

			if (event.isEmpty()) {
				sb.append(_FINDER_COLUMN_LIKET_NOTE_EVENT_3);
			}
			else {
				bindEvent = true;

				sb.append(_FINDER_COLUMN_LIKET_NOTE_EVENT_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindTreePath) {
					queryPos.add(treePath);
				}

				if (bindEvent) {
					queryPos.add(event);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_LIKET_NOTE_TREEPATH_2 =
		"syncDLObject.treePath LIKE ? AND ";

	private static final String _FINDER_COLUMN_LIKET_NOTE_TREEPATH_3 =
		"(syncDLObject.treePath IS NULL OR syncDLObject.treePath LIKE '') AND ";

	private static final String _FINDER_COLUMN_LIKET_NOTE_EVENT_2 =
		"syncDLObject.event != ?";

	private static final String _FINDER_COLUMN_LIKET_NOTE_EVENT_3 =
		"(syncDLObject.event IS NULL OR syncDLObject.event != '')";

	private FinderPath _finderPathWithPaginationFindByV_T;
	private FinderPath _finderPathWithoutPaginationFindByV_T;
	private FinderPath _finderPathCountByV_T;

	/**
	 * Returns all the sync dl objects where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @return the matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByV_T(String version, String type) {
		return findByV_T(
			version, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sync dl objects where version = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param version the version
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByV_T(
		String version, String type, int start, int end) {

		return findByV_T(version, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where version = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param version the version
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByV_T(
		String version, String type, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return findByV_T(version, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where version = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param version the version
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByV_T(
		String version, String type, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		version = Objects.toString(version, "");
		type = Objects.toString(type, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByV_T;
				finderArgs = new Object[] {version, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByV_T;
			finderArgs = new Object[] {
				version, type, start, end, orderByComparator
			};
		}

		List<SyncDLObject> list = null;

		if (useFinderCache) {
			list = (List<SyncDLObject>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SyncDLObject syncDLObject : list) {
					if (!version.equals(syncDLObject.getVersion()) ||
						!type.equals(syncDLObject.getType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

			boolean bindVersion = false;

			if (version.isEmpty()) {
				sb.append(_FINDER_COLUMN_V_T_VERSION_3);
			}
			else {
				bindVersion = true;

				sb.append(_FINDER_COLUMN_V_T_VERSION_2);
			}

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_V_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_V_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindVersion) {
					queryPos.add(version);
				}

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<SyncDLObject>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sync dl object in the ordered set where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByV_T_First(
			String version, String type,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByV_T_First(
			version, type, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("version=");
		sb.append(version);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the first sync dl object in the ordered set where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByV_T_First(
		String version, String type,
		OrderByComparator<SyncDLObject> orderByComparator) {

		List<SyncDLObject> list = findByV_T(
			version, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sync dl object in the ordered set where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByV_T_Last(
			String version, String type,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByV_T_Last(
			version, type, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("version=");
		sb.append(version);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the last sync dl object in the ordered set where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByV_T_Last(
		String version, String type,
		OrderByComparator<SyncDLObject> orderByComparator) {

		int count = countByV_T(version, type);

		if (count == 0) {
			return null;
		}

		List<SyncDLObject> list = findByV_T(
			version, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where version = &#63; and type = &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param version the version
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	@Override
	public SyncDLObject[] findByV_T_PrevAndNext(
			long syncDLObjectId, String version, String type,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		version = Objects.toString(version, "");
		type = Objects.toString(type, "");

		SyncDLObject syncDLObject = findByPrimaryKey(syncDLObjectId);

		Session session = null;

		try {
			session = openSession();

			SyncDLObject[] array = new SyncDLObjectImpl[3];

			array[0] = getByV_T_PrevAndNext(
				session, syncDLObject, version, type, orderByComparator, true);

			array[1] = syncDLObject;

			array[2] = getByV_T_PrevAndNext(
				session, syncDLObject, version, type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SyncDLObject getByV_T_PrevAndNext(
		Session session, SyncDLObject syncDLObject, String version, String type,
		OrderByComparator<SyncDLObject> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

		boolean bindVersion = false;

		if (version.isEmpty()) {
			sb.append(_FINDER_COLUMN_V_T_VERSION_3);
		}
		else {
			bindVersion = true;

			sb.append(_FINDER_COLUMN_V_T_VERSION_2);
		}

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_V_T_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_V_T_TYPE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindVersion) {
			queryPos.add(version);
		}

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(syncDLObject)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SyncDLObject> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sync dl objects where version = &#63; and type = &#63; from the database.
	 *
	 * @param version the version
	 * @param type the type
	 */
	@Override
	public void removeByV_T(String version, String type) {
		for (SyncDLObject syncDLObject :
				findByV_T(
					version, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(syncDLObject);
		}
	}

	/**
	 * Returns the number of sync dl objects where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @return the number of matching sync dl objects
	 */
	@Override
	public int countByV_T(String version, String type) {
		version = Objects.toString(version, "");
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByV_T;

		Object[] finderArgs = new Object[] {version, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SYNCDLOBJECT_WHERE);

			boolean bindVersion = false;

			if (version.isEmpty()) {
				sb.append(_FINDER_COLUMN_V_T_VERSION_3);
			}
			else {
				bindVersion = true;

				sb.append(_FINDER_COLUMN_V_T_VERSION_2);
			}

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_V_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_V_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindVersion) {
					queryPos.add(version);
				}

				if (bindType) {
					queryPos.add(type);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_V_T_VERSION_2 =
		"syncDLObject.version = ? AND ";

	private static final String _FINDER_COLUMN_V_T_VERSION_3 =
		"(syncDLObject.version IS NULL OR syncDLObject.version = '') AND ";

	private static final String _FINDER_COLUMN_V_T_TYPE_2 =
		"syncDLObject.type = ?";

	private static final String _FINDER_COLUMN_V_T_TYPE_3 =
		"(syncDLObject.type IS NULL OR syncDLObject.type = '')";

	private FinderPath _finderPathFetchByT_T;
	private FinderPath _finderPathCountByT_T;

	/**
	 * Returns the sync dl object where type = &#63; and typePK = &#63; or throws a <code>NoSuchDLObjectException</code> if it could not be found.
	 *
	 * @param type the type
	 * @param typePK the type pk
	 * @return the matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByT_T(String type, long typePK)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByT_T(type, typePK);

		if (syncDLObject == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("type=");
			sb.append(type);

			sb.append(", typePK=");
			sb.append(typePK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDLObjectException(sb.toString());
		}

		return syncDLObject;
	}

	/**
	 * Returns the sync dl object where type = &#63; and typePK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param type the type
	 * @param typePK the type pk
	 * @return the matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByT_T(String type, long typePK) {
		return fetchByT_T(type, typePK, true);
	}

	/**
	 * Returns the sync dl object where type = &#63; and typePK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param type the type
	 * @param typePK the type pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByT_T(
		String type, long typePK, boolean useFinderCache) {

		type = Objects.toString(type, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {type, typePK};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByT_T, finderArgs);
		}

		if (result instanceof SyncDLObject) {
			SyncDLObject syncDLObject = (SyncDLObject)result;

			if (!Objects.equals(type, syncDLObject.getType()) ||
				(typePK != syncDLObject.getTypePK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_T_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_T_T_TYPE_2);
			}

			sb.append(_FINDER_COLUMN_T_T_TYPEPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindType) {
					queryPos.add(type);
				}

				queryPos.add(typePK);

				List<SyncDLObject> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByT_T, finderArgs, list);
					}
				}
				else {
					SyncDLObject syncDLObject = list.get(0);

					result = syncDLObject;

					cacheResult(syncDLObject);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (SyncDLObject)result;
		}
	}

	/**
	 * Removes the sync dl object where type = &#63; and typePK = &#63; from the database.
	 *
	 * @param type the type
	 * @param typePK the type pk
	 * @return the sync dl object that was removed
	 */
	@Override
	public SyncDLObject removeByT_T(String type, long typePK)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = findByT_T(type, typePK);

		return remove(syncDLObject);
	}

	/**
	 * Returns the number of sync dl objects where type = &#63; and typePK = &#63;.
	 *
	 * @param type the type
	 * @param typePK the type pk
	 * @return the number of matching sync dl objects
	 */
	@Override
	public int countByT_T(String type, long typePK) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByT_T;

		Object[] finderArgs = new Object[] {type, typePK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SYNCDLOBJECT_WHERE);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_T_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_T_T_TYPE_2);
			}

			sb.append(_FINDER_COLUMN_T_T_TYPEPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindType) {
					queryPos.add(type);
				}

				queryPos.add(typePK);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_T_T_TYPE_2 =
		"syncDLObject.type = ? AND ";

	private static final String _FINDER_COLUMN_T_T_TYPE_3 =
		"(syncDLObject.type IS NULL OR syncDLObject.type = '') AND ";

	private static final String _FINDER_COLUMN_T_T_TYPEPK_2 =
		"syncDLObject.typePK = ?";

	private FinderPath _finderPathWithPaginationFindByGtM_R_NotE;
	private FinderPath _finderPathWithPaginationCountByGtM_R_NotE;

	/**
	 * Returns all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @return the matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByGtM_R_NotE(
		long modifiedTime, long repositoryId, String event) {

		return findByGtM_R_NotE(
			modifiedTime, repositoryId, event, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByGtM_R_NotE(
		long modifiedTime, long repositoryId, String event, int start,
		int end) {

		return findByGtM_R_NotE(
			modifiedTime, repositoryId, event, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByGtM_R_NotE(
		long modifiedTime, long repositoryId, String event, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return findByGtM_R_NotE(
			modifiedTime, repositoryId, event, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByGtM_R_NotE(
		long modifiedTime, long repositoryId, String event, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		event = Objects.toString(event, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByGtM_R_NotE;
		finderArgs = new Object[] {
			modifiedTime, repositoryId, event, start, end, orderByComparator
		};

		List<SyncDLObject> list = null;

		if (useFinderCache) {
			list = (List<SyncDLObject>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SyncDLObject syncDLObject : list) {
					if ((modifiedTime >= syncDLObject.getModifiedTime()) ||
						(repositoryId != syncDLObject.getRepositoryId()) ||
						event.equals(syncDLObject.getEvent())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

			sb.append(_FINDER_COLUMN_GTM_R_NOTE_MODIFIEDTIME_2);

			sb.append(_FINDER_COLUMN_GTM_R_NOTE_REPOSITORYID_2);

			boolean bindEvent = false;

			if (event.isEmpty()) {
				sb.append(_FINDER_COLUMN_GTM_R_NOTE_EVENT_3);
			}
			else {
				bindEvent = true;

				sb.append(_FINDER_COLUMN_GTM_R_NOTE_EVENT_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(modifiedTime);

				queryPos.add(repositoryId);

				if (bindEvent) {
					queryPos.add(event);
				}

				list = (List<SyncDLObject>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByGtM_R_NotE_First(
			long modifiedTime, long repositoryId, String event,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByGtM_R_NotE_First(
			modifiedTime, repositoryId, event, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("modifiedTime>");
		sb.append(modifiedTime);

		sb.append(", repositoryId=");
		sb.append(repositoryId);

		sb.append(", event!=");
		sb.append(event);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the first sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByGtM_R_NotE_First(
		long modifiedTime, long repositoryId, String event,
		OrderByComparator<SyncDLObject> orderByComparator) {

		List<SyncDLObject> list = findByGtM_R_NotE(
			modifiedTime, repositoryId, event, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByGtM_R_NotE_Last(
			long modifiedTime, long repositoryId, String event,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByGtM_R_NotE_Last(
			modifiedTime, repositoryId, event, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("modifiedTime>");
		sb.append(modifiedTime);

		sb.append(", repositoryId=");
		sb.append(repositoryId);

		sb.append(", event!=");
		sb.append(event);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the last sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByGtM_R_NotE_Last(
		long modifiedTime, long repositoryId, String event,
		OrderByComparator<SyncDLObject> orderByComparator) {

		int count = countByGtM_R_NotE(modifiedTime, repositoryId, event);

		if (count == 0) {
			return null;
		}

		List<SyncDLObject> list = findByGtM_R_NotE(
			modifiedTime, repositoryId, event, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	@Override
	public SyncDLObject[] findByGtM_R_NotE_PrevAndNext(
			long syncDLObjectId, long modifiedTime, long repositoryId,
			String event, OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		event = Objects.toString(event, "");

		SyncDLObject syncDLObject = findByPrimaryKey(syncDLObjectId);

		Session session = null;

		try {
			session = openSession();

			SyncDLObject[] array = new SyncDLObjectImpl[3];

			array[0] = getByGtM_R_NotE_PrevAndNext(
				session, syncDLObject, modifiedTime, repositoryId, event,
				orderByComparator, true);

			array[1] = syncDLObject;

			array[2] = getByGtM_R_NotE_PrevAndNext(
				session, syncDLObject, modifiedTime, repositoryId, event,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SyncDLObject getByGtM_R_NotE_PrevAndNext(
		Session session, SyncDLObject syncDLObject, long modifiedTime,
		long repositoryId, String event,
		OrderByComparator<SyncDLObject> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

		sb.append(_FINDER_COLUMN_GTM_R_NOTE_MODIFIEDTIME_2);

		sb.append(_FINDER_COLUMN_GTM_R_NOTE_REPOSITORYID_2);

		boolean bindEvent = false;

		if (event.isEmpty()) {
			sb.append(_FINDER_COLUMN_GTM_R_NOTE_EVENT_3);
		}
		else {
			bindEvent = true;

			sb.append(_FINDER_COLUMN_GTM_R_NOTE_EVENT_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(modifiedTime);

		queryPos.add(repositoryId);

		if (bindEvent) {
			queryPos.add(event);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(syncDLObject)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SyncDLObject> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; all &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param events the events
	 * @return the matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByGtM_R_NotE(
		long modifiedTime, long repositoryId, String[] events) {

		return findByGtM_R_NotE(
			modifiedTime, repositoryId, events, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; all &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param events the events
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByGtM_R_NotE(
		long modifiedTime, long repositoryId, String[] events, int start,
		int end) {

		return findByGtM_R_NotE(
			modifiedTime, repositoryId, events, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; all &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param events the events
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByGtM_R_NotE(
		long modifiedTime, long repositoryId, String[] events, int start,
		int end, OrderByComparator<SyncDLObject> orderByComparator) {

		return findByGtM_R_NotE(
			modifiedTime, repositoryId, events, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByGtM_R_NotE(
		long modifiedTime, long repositoryId, String[] events, int start,
		int end, OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		if (events == null) {
			events = new String[0];
		}
		else if (events.length > 1) {
			for (int i = 0; i < events.length; i++) {
				events[i] = Objects.toString(events[i], "");
			}

			events = ArrayUtil.sortedUnique(events);
		}

		if (events.length == 1) {
			return findByGtM_R_NotE(
				modifiedTime, repositoryId, events[0], start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					modifiedTime, repositoryId, StringUtil.merge(events)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				modifiedTime, repositoryId, StringUtil.merge(events), start,
				end, orderByComparator
			};
		}

		List<SyncDLObject> list = null;

		if (useFinderCache) {
			list = (List<SyncDLObject>)finderCache.getResult(
				_finderPathWithPaginationFindByGtM_R_NotE, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SyncDLObject syncDLObject : list) {
					if ((modifiedTime >= syncDLObject.getModifiedTime()) ||
						(repositoryId != syncDLObject.getRepositoryId()) ||
						!ArrayUtil.contains(events, syncDLObject.getEvent())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

			sb.append(_FINDER_COLUMN_GTM_R_NOTE_MODIFIEDTIME_2);

			sb.append(_FINDER_COLUMN_GTM_R_NOTE_REPOSITORYID_2);

			if (events.length > 0) {
				sb.append("(");

				for (int i = 0; i < events.length; i++) {
					String event = events[i];

					if (event.isEmpty()) {
						sb.append(_FINDER_COLUMN_GTM_R_NOTE_EVENT_3);
					}
					else {
						sb.append(_FINDER_COLUMN_GTM_R_NOTE_EVENT_2);
					}

					if ((i + 1) < events.length) {
						sb.append(WHERE_AND);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(modifiedTime);

				queryPos.add(repositoryId);

				for (String event : events) {
					if ((event != null) && !event.isEmpty()) {
						queryPos.add(event);
					}
				}

				list = (List<SyncDLObject>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByGtM_R_NotE, finderArgs,
						list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63; from the database.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 */
	@Override
	public void removeByGtM_R_NotE(
		long modifiedTime, long repositoryId, String event) {

		for (SyncDLObject syncDLObject :
				findByGtM_R_NotE(
					modifiedTime, repositoryId, event, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(syncDLObject);
		}
	}

	/**
	 * Returns the number of sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @return the number of matching sync dl objects
	 */
	@Override
	public int countByGtM_R_NotE(
		long modifiedTime, long repositoryId, String event) {

		event = Objects.toString(event, "");

		FinderPath finderPath = _finderPathWithPaginationCountByGtM_R_NotE;

		Object[] finderArgs = new Object[] {modifiedTime, repositoryId, event};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_SYNCDLOBJECT_WHERE);

			sb.append(_FINDER_COLUMN_GTM_R_NOTE_MODIFIEDTIME_2);

			sb.append(_FINDER_COLUMN_GTM_R_NOTE_REPOSITORYID_2);

			boolean bindEvent = false;

			if (event.isEmpty()) {
				sb.append(_FINDER_COLUMN_GTM_R_NOTE_EVENT_3);
			}
			else {
				bindEvent = true;

				sb.append(_FINDER_COLUMN_GTM_R_NOTE_EVENT_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(modifiedTime);

				queryPos.add(repositoryId);

				if (bindEvent) {
					queryPos.add(event);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; all &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param events the events
	 * @return the number of matching sync dl objects
	 */
	@Override
	public int countByGtM_R_NotE(
		long modifiedTime, long repositoryId, String[] events) {

		if (events == null) {
			events = new String[0];
		}
		else if (events.length > 1) {
			for (int i = 0; i < events.length; i++) {
				events[i] = Objects.toString(events[i], "");
			}

			events = ArrayUtil.sortedUnique(events);
		}

		Object[] finderArgs = new Object[] {
			modifiedTime, repositoryId, StringUtil.merge(events)
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByGtM_R_NotE, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_SYNCDLOBJECT_WHERE);

			sb.append(_FINDER_COLUMN_GTM_R_NOTE_MODIFIEDTIME_2);

			sb.append(_FINDER_COLUMN_GTM_R_NOTE_REPOSITORYID_2);

			if (events.length > 0) {
				sb.append("(");

				for (int i = 0; i < events.length; i++) {
					String event = events[i];

					if (event.isEmpty()) {
						sb.append(_FINDER_COLUMN_GTM_R_NOTE_EVENT_3);
					}
					else {
						sb.append(_FINDER_COLUMN_GTM_R_NOTE_EVENT_2);
					}

					if ((i + 1) < events.length) {
						sb.append(WHERE_AND);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(modifiedTime);

				queryPos.add(repositoryId);

				for (String event : events) {
					if ((event != null) && !event.isEmpty()) {
						queryPos.add(event);
					}
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByGtM_R_NotE, finderArgs,
					count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GTM_R_NOTE_MODIFIEDTIME_2 =
		"syncDLObject.modifiedTime > ? AND ";

	private static final String _FINDER_COLUMN_GTM_R_NOTE_REPOSITORYID_2 =
		"syncDLObject.repositoryId = ? AND ";

	private static final String _FINDER_COLUMN_GTM_R_NOTE_EVENT_2 =
		"syncDLObject.event != ?";

	private static final String _FINDER_COLUMN_GTM_R_NOTE_EVENT_3 =
		"(syncDLObject.event IS NULL OR syncDLObject.event != '')";

	private FinderPath _finderPathWithPaginationFindByR_P_T;
	private FinderPath _finderPathWithoutPaginationFindByR_P_T;
	private FinderPath _finderPathCountByR_P_T;
	private FinderPath _finderPathWithPaginationCountByR_P_T;

	/**
	 * Returns all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @return the matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String type) {

		return findByR_P_T(
			repositoryId, parentFolderId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String type, int start,
		int end) {

		return findByR_P_T(
			repositoryId, parentFolderId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String type, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return findByR_P_T(
			repositoryId, parentFolderId, type, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String type, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByR_P_T;
				finderArgs = new Object[] {repositoryId, parentFolderId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByR_P_T;
			finderArgs = new Object[] {
				repositoryId, parentFolderId, type, start, end,
				orderByComparator
			};
		}

		List<SyncDLObject> list = null;

		if (useFinderCache) {
			list = (List<SyncDLObject>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SyncDLObject syncDLObject : list) {
					if ((repositoryId != syncDLObject.getRepositoryId()) ||
						(parentFolderId != syncDLObject.getParentFolderId()) ||
						!type.equals(syncDLObject.getType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

			sb.append(_FINDER_COLUMN_R_P_T_REPOSITORYID_2);

			sb.append(_FINDER_COLUMN_R_P_T_PARENTFOLDERID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_R_P_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_R_P_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(repositoryId);

				queryPos.add(parentFolderId);

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<SyncDLObject>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByR_P_T_First(
			long repositoryId, long parentFolderId, String type,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByR_P_T_First(
			repositoryId, parentFolderId, type, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("repositoryId=");
		sb.append(repositoryId);

		sb.append(", parentFolderId=");
		sb.append(parentFolderId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByR_P_T_First(
		long repositoryId, long parentFolderId, String type,
		OrderByComparator<SyncDLObject> orderByComparator) {

		List<SyncDLObject> list = findByR_P_T(
			repositoryId, parentFolderId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject findByR_P_T_Last(
			long repositoryId, long parentFolderId, String type,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByR_P_T_Last(
			repositoryId, parentFolderId, type, orderByComparator);

		if (syncDLObject != null) {
			return syncDLObject;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("repositoryId=");
		sb.append(repositoryId);

		sb.append(", parentFolderId=");
		sb.append(parentFolderId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchDLObjectException(sb.toString());
	}

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	@Override
	public SyncDLObject fetchByR_P_T_Last(
		long repositoryId, long parentFolderId, String type,
		OrderByComparator<SyncDLObject> orderByComparator) {

		int count = countByR_P_T(repositoryId, parentFolderId, type);

		if (count == 0) {
			return null;
		}

		List<SyncDLObject> list = findByR_P_T(
			repositoryId, parentFolderId, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	@Override
	public SyncDLObject[] findByR_P_T_PrevAndNext(
			long syncDLObjectId, long repositoryId, long parentFolderId,
			String type, OrderByComparator<SyncDLObject> orderByComparator)
		throws NoSuchDLObjectException {

		type = Objects.toString(type, "");

		SyncDLObject syncDLObject = findByPrimaryKey(syncDLObjectId);

		Session session = null;

		try {
			session = openSession();

			SyncDLObject[] array = new SyncDLObjectImpl[3];

			array[0] = getByR_P_T_PrevAndNext(
				session, syncDLObject, repositoryId, parentFolderId, type,
				orderByComparator, true);

			array[1] = syncDLObject;

			array[2] = getByR_P_T_PrevAndNext(
				session, syncDLObject, repositoryId, parentFolderId, type,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SyncDLObject getByR_P_T_PrevAndNext(
		Session session, SyncDLObject syncDLObject, long repositoryId,
		long parentFolderId, String type,
		OrderByComparator<SyncDLObject> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

		sb.append(_FINDER_COLUMN_R_P_T_REPOSITORYID_2);

		sb.append(_FINDER_COLUMN_R_P_T_PARENTFOLDERID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_R_P_T_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_R_P_T_TYPE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(repositoryId);

		queryPos.add(parentFolderId);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(syncDLObject)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SyncDLObject> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param types the types
	 * @return the matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String[] types) {

		return findByR_P_T(
			repositoryId, parentFolderId, types, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param types the types
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String[] types, int start,
		int end) {

		return findByR_P_T(
			repositoryId, parentFolderId, types, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param types the types
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String[] types, int start,
		int end, OrderByComparator<SyncDLObject> orderByComparator) {

		return findByR_P_T(
			repositoryId, parentFolderId, types, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	@Override
	public List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String[] types, int start,
		int end, OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		if (types == null) {
			types = new String[0];
		}
		else if (types.length > 1) {
			for (int i = 0; i < types.length; i++) {
				types[i] = Objects.toString(types[i], "");
			}

			types = ArrayUtil.sortedUnique(types);
		}

		if (types.length == 1) {
			return findByR_P_T(
				repositoryId, parentFolderId, types[0], start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					repositoryId, parentFolderId, StringUtil.merge(types)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				repositoryId, parentFolderId, StringUtil.merge(types), start,
				end, orderByComparator
			};
		}

		List<SyncDLObject> list = null;

		if (useFinderCache) {
			list = (List<SyncDLObject>)finderCache.getResult(
				_finderPathWithPaginationFindByR_P_T, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SyncDLObject syncDLObject : list) {
					if ((repositoryId != syncDLObject.getRepositoryId()) ||
						(parentFolderId != syncDLObject.getParentFolderId()) ||
						!ArrayUtil.contains(types, syncDLObject.getType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_SYNCDLOBJECT_WHERE);

			sb.append(_FINDER_COLUMN_R_P_T_REPOSITORYID_2);

			sb.append(_FINDER_COLUMN_R_P_T_PARENTFOLDERID_2);

			if (types.length > 0) {
				sb.append("(");

				for (int i = 0; i < types.length; i++) {
					String type = types[i];

					if (type.isEmpty()) {
						sb.append(_FINDER_COLUMN_R_P_T_TYPE_3);
					}
					else {
						sb.append(_FINDER_COLUMN_R_P_T_TYPE_2);
					}

					if ((i + 1) < types.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SyncDLObjectModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(repositoryId);

				queryPos.add(parentFolderId);

				for (String type : types) {
					if ((type != null) && !type.isEmpty()) {
						queryPos.add(type);
					}
				}

				list = (List<SyncDLObject>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByR_P_T, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 */
	@Override
	public void removeByR_P_T(
		long repositoryId, long parentFolderId, String type) {

		for (SyncDLObject syncDLObject :
				findByR_P_T(
					repositoryId, parentFolderId, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(syncDLObject);
		}
	}

	/**
	 * Returns the number of sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @return the number of matching sync dl objects
	 */
	@Override
	public int countByR_P_T(
		long repositoryId, long parentFolderId, String type) {

		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByR_P_T;

		Object[] finderArgs = new Object[] {repositoryId, parentFolderId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_SYNCDLOBJECT_WHERE);

			sb.append(_FINDER_COLUMN_R_P_T_REPOSITORYID_2);

			sb.append(_FINDER_COLUMN_R_P_T_PARENTFOLDERID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_R_P_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_R_P_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(repositoryId);

				queryPos.add(parentFolderId);

				if (bindType) {
					queryPos.add(type);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = any &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param types the types
	 * @return the number of matching sync dl objects
	 */
	@Override
	public int countByR_P_T(
		long repositoryId, long parentFolderId, String[] types) {

		if (types == null) {
			types = new String[0];
		}
		else if (types.length > 1) {
			for (int i = 0; i < types.length; i++) {
				types[i] = Objects.toString(types[i], "");
			}

			types = ArrayUtil.sortedUnique(types);
		}

		Object[] finderArgs = new Object[] {
			repositoryId, parentFolderId, StringUtil.merge(types)
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByR_P_T, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_SYNCDLOBJECT_WHERE);

			sb.append(_FINDER_COLUMN_R_P_T_REPOSITORYID_2);

			sb.append(_FINDER_COLUMN_R_P_T_PARENTFOLDERID_2);

			if (types.length > 0) {
				sb.append("(");

				for (int i = 0; i < types.length; i++) {
					String type = types[i];

					if (type.isEmpty()) {
						sb.append(_FINDER_COLUMN_R_P_T_TYPE_3);
					}
					else {
						sb.append(_FINDER_COLUMN_R_P_T_TYPE_2);
					}

					if ((i + 1) < types.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(repositoryId);

				queryPos.add(parentFolderId);

				for (String type : types) {
					if ((type != null) && !type.isEmpty()) {
						queryPos.add(type);
					}
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByR_P_T, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_R_P_T_REPOSITORYID_2 =
		"syncDLObject.repositoryId = ? AND ";

	private static final String _FINDER_COLUMN_R_P_T_PARENTFOLDERID_2 =
		"syncDLObject.parentFolderId = ? AND ";

	private static final String _FINDER_COLUMN_R_P_T_TYPE_2 =
		"syncDLObject.type = ?";

	private static final String _FINDER_COLUMN_R_P_T_TYPE_3 =
		"(syncDLObject.type IS NULL OR syncDLObject.type = '')";

	public SyncDLObjectPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("size", "size_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(SyncDLObject.class);

		setModelImplClass(SyncDLObjectImpl.class);
		setModelPKClass(long.class);

		setTable(SyncDLObjectTable.INSTANCE);
	}

	/**
	 * Caches the sync dl object in the entity cache if it is enabled.
	 *
	 * @param syncDLObject the sync dl object
	 */
	@Override
	public void cacheResult(SyncDLObject syncDLObject) {
		entityCache.putResult(
			SyncDLObjectImpl.class, syncDLObject.getPrimaryKey(), syncDLObject);

		finderCache.putResult(
			_finderPathFetchByT_T,
			new Object[] {syncDLObject.getType(), syncDLObject.getTypePK()},
			syncDLObject);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the sync dl objects in the entity cache if it is enabled.
	 *
	 * @param syncDLObjects the sync dl objects
	 */
	@Override
	public void cacheResult(List<SyncDLObject> syncDLObjects) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (syncDLObjects.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (SyncDLObject syncDLObject : syncDLObjects) {
			if (entityCache.getResult(
					SyncDLObjectImpl.class, syncDLObject.getPrimaryKey()) ==
						null) {

				cacheResult(syncDLObject);
			}
		}
	}

	/**
	 * Clears the cache for all sync dl objects.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SyncDLObjectImpl.class);

		finderCache.clearCache(SyncDLObjectImpl.class);
	}

	/**
	 * Clears the cache for the sync dl object.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SyncDLObject syncDLObject) {
		entityCache.removeResult(SyncDLObjectImpl.class, syncDLObject);
	}

	@Override
	public void clearCache(List<SyncDLObject> syncDLObjects) {
		for (SyncDLObject syncDLObject : syncDLObjects) {
			entityCache.removeResult(SyncDLObjectImpl.class, syncDLObject);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(SyncDLObjectImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(SyncDLObjectImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SyncDLObjectModelImpl syncDLObjectModelImpl) {

		Object[] args = new Object[] {
			syncDLObjectModelImpl.getType(), syncDLObjectModelImpl.getTypePK()
		};

		finderCache.putResult(_finderPathCountByT_T, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByT_T, args, syncDLObjectModelImpl);
	}

	/**
	 * Creates a new sync dl object with the primary key. Does not add the sync dl object to the database.
	 *
	 * @param syncDLObjectId the primary key for the new sync dl object
	 * @return the new sync dl object
	 */
	@Override
	public SyncDLObject create(long syncDLObjectId) {
		SyncDLObject syncDLObject = new SyncDLObjectImpl();

		syncDLObject.setNew(true);
		syncDLObject.setPrimaryKey(syncDLObjectId);

		syncDLObject.setCompanyId(CompanyThreadLocal.getCompanyId());

		return syncDLObject;
	}

	/**
	 * Removes the sync dl object with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLObjectId the primary key of the sync dl object
	 * @return the sync dl object that was removed
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	@Override
	public SyncDLObject remove(long syncDLObjectId)
		throws NoSuchDLObjectException {

		return remove((Serializable)syncDLObjectId);
	}

	/**
	 * Removes the sync dl object with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the sync dl object
	 * @return the sync dl object that was removed
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	@Override
	public SyncDLObject remove(Serializable primaryKey)
		throws NoSuchDLObjectException {

		Session session = null;

		try {
			session = openSession();

			SyncDLObject syncDLObject = (SyncDLObject)session.get(
				SyncDLObjectImpl.class, primaryKey);

			if (syncDLObject == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDLObjectException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(syncDLObject);
		}
		catch (NoSuchDLObjectException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected SyncDLObject removeImpl(SyncDLObject syncDLObject) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(syncDLObject)) {
				syncDLObject = (SyncDLObject)session.get(
					SyncDLObjectImpl.class, syncDLObject.getPrimaryKeyObj());
			}

			if (syncDLObject != null) {
				session.delete(syncDLObject);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (syncDLObject != null) {
			clearCache(syncDLObject);
		}

		return syncDLObject;
	}

	@Override
	public SyncDLObject updateImpl(SyncDLObject syncDLObject) {
		boolean isNew = syncDLObject.isNew();

		if (!(syncDLObject instanceof SyncDLObjectModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(syncDLObject.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					syncDLObject);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in syncDLObject proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SyncDLObject implementation " +
					syncDLObject.getClass());
		}

		SyncDLObjectModelImpl syncDLObjectModelImpl =
			(SyncDLObjectModelImpl)syncDLObject;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(syncDLObject);
			}
			else {
				syncDLObject = (SyncDLObject)session.merge(syncDLObject);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			SyncDLObjectImpl.class, syncDLObjectModelImpl, false, true);

		cacheUniqueFindersCache(syncDLObjectModelImpl);

		if (isNew) {
			syncDLObject.setNew(false);
		}

		syncDLObject.resetOriginalValues();

		return syncDLObject;
	}

	/**
	 * Returns the sync dl object with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the sync dl object
	 * @return the sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	@Override
	public SyncDLObject findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDLObjectException {

		SyncDLObject syncDLObject = fetchByPrimaryKey(primaryKey);

		if (syncDLObject == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDLObjectException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return syncDLObject;
	}

	/**
	 * Returns the sync dl object with the primary key or throws a <code>NoSuchDLObjectException</code> if it could not be found.
	 *
	 * @param syncDLObjectId the primary key of the sync dl object
	 * @return the sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	@Override
	public SyncDLObject findByPrimaryKey(long syncDLObjectId)
		throws NoSuchDLObjectException {

		return findByPrimaryKey((Serializable)syncDLObjectId);
	}

	/**
	 * Returns the sync dl object with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param syncDLObjectId the primary key of the sync dl object
	 * @return the sync dl object, or <code>null</code> if a sync dl object with the primary key could not be found
	 */
	@Override
	public SyncDLObject fetchByPrimaryKey(long syncDLObjectId) {
		return fetchByPrimaryKey((Serializable)syncDLObjectId);
	}

	/**
	 * Returns all the sync dl objects.
	 *
	 * @return the sync dl objects
	 */
	@Override
	public List<SyncDLObject> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sync dl objects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of sync dl objects
	 */
	@Override
	public List<SyncDLObject> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the sync dl objects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sync dl objects
	 */
	@Override
	public List<SyncDLObject> findAll(
		int start, int end, OrderByComparator<SyncDLObject> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sync dl objects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of sync dl objects
	 */
	@Override
	public List<SyncDLObject> findAll(
		int start, int end, OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<SyncDLObject> list = null;

		if (useFinderCache) {
			list = (List<SyncDLObject>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SYNCDLOBJECT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SYNCDLOBJECT;

				sql = sql.concat(SyncDLObjectModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SyncDLObject>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the sync dl objects from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SyncDLObject syncDLObject : findAll()) {
			remove(syncDLObject);
		}
	}

	/**
	 * Returns the number of sync dl objects.
	 *
	 * @return the number of sync dl objects
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_SYNCDLOBJECT);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "syncDLObjectId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SYNCDLOBJECT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SyncDLObjectModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the sync dl object persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByLikeTreePath = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLikeTreePath",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"treePath"}, true);

		_finderPathWithPaginationCountByLikeTreePath = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLikeTreePath",
			new String[] {String.class.getName()}, new String[] {"treePath"},
			false);

		_finderPathWithPaginationFindByGtM_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGtM_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"modifiedTime", "repositoryId"}, true);

		_finderPathWithPaginationCountByGtM_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGtM_R",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"modifiedTime", "repositoryId"}, false);

		_finderPathWithPaginationFindByR_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"repositoryId", "parentFolderId"}, true);

		_finderPathWithoutPaginationFindByR_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"repositoryId", "parentFolderId"}, true);

		_finderPathCountByR_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"repositoryId", "parentFolderId"}, false);

		_finderPathWithPaginationFindByR_NotE = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_NotE",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"repositoryId", "event"}, true);

		_finderPathWithPaginationCountByR_NotE = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByR_NotE",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"repositoryId", "event"}, false);

		_finderPathWithPaginationFindByR_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"repositoryId", "type_"}, true);

		_finderPathWithoutPaginationFindByR_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_T",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"repositoryId", "type_"}, true);

		_finderPathCountByR_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_T",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"repositoryId", "type_"}, false);

		_finderPathWithPaginationFindByLikeT_NotE = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLikeT_NotE",
			new String[] {
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"treePath", "event"}, true);

		_finderPathWithPaginationCountByLikeT_NotE = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLikeT_NotE",
			new String[] {String.class.getName(), String.class.getName()},
			new String[] {"treePath", "event"}, false);

		_finderPathWithPaginationFindByV_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByV_T",
			new String[] {
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"version", "type_"}, true);

		_finderPathWithoutPaginationFindByV_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByV_T",
			new String[] {String.class.getName(), String.class.getName()},
			new String[] {"version", "type_"}, true);

		_finderPathCountByV_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByV_T",
			new String[] {String.class.getName(), String.class.getName()},
			new String[] {"version", "type_"}, false);

		_finderPathFetchByT_T = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByT_T",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"type_", "typePK"}, true);

		_finderPathCountByT_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_T",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"type_", "typePK"}, false);

		_finderPathWithPaginationFindByGtM_R_NotE = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGtM_R_NotE",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"modifiedTime", "repositoryId", "event"}, true);

		_finderPathWithPaginationCountByGtM_R_NotE = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGtM_R_NotE",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"modifiedTime", "repositoryId", "event"}, false);

		_finderPathWithPaginationFindByR_P_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_P_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"repositoryId", "parentFolderId", "type_"}, true);

		_finderPathWithoutPaginationFindByR_P_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_P_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"repositoryId", "parentFolderId", "type_"}, true);

		_finderPathCountByR_P_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_P_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"repositoryId", "parentFolderId", "type_"}, false);

		_finderPathWithPaginationCountByR_P_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByR_P_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"repositoryId", "parentFolderId", "type_"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SyncDLObjectImpl.class.getName());
	}

	@Override
	@Reference(
		target = SyncPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SyncPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SyncPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_SYNCDLOBJECT =
		"SELECT syncDLObject FROM SyncDLObject syncDLObject";

	private static final String _SQL_SELECT_SYNCDLOBJECT_WHERE =
		"SELECT syncDLObject FROM SyncDLObject syncDLObject WHERE ";

	private static final String _SQL_COUNT_SYNCDLOBJECT =
		"SELECT COUNT(syncDLObject) FROM SyncDLObject syncDLObject";

	private static final String _SQL_COUNT_SYNCDLOBJECT_WHERE =
		"SELECT COUNT(syncDLObject) FROM SyncDLObject syncDLObject WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "syncDLObject.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SyncDLObject exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SyncDLObject exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SyncDLObjectPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"size", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private SyncDLObjectModelArgumentsResolver
		_syncDLObjectModelArgumentsResolver;

}