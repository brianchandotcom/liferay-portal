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

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.exception.NoSuchAutoResolutionInfoException;
import com.liferay.change.tracking.model.CTAutoResolutionInfo;
import com.liferay.change.tracking.model.impl.CTAutoResolutionInfoImpl;
import com.liferay.change.tracking.model.impl.CTAutoResolutionInfoModelImpl;
import com.liferay.change.tracking.service.persistence.CTAutoResolutionInfoPersistence;
import com.liferay.change.tracking.service.persistence.impl.constants.CTPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the ct auto resolution info service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = CTAutoResolutionInfoPersistence.class)
public class CTAutoResolutionInfoPersistenceImpl
	extends BasePersistenceImpl<CTAutoResolutionInfo>
	implements CTAutoResolutionInfoPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTAutoResolutionInfoUtil</code> to access the ct auto resolution info persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTAutoResolutionInfoImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCTCollectionId;
	private FinderPath _finderPathWithoutPaginationFindByCTCollectionId;
	private FinderPath _finderPathCountByCTCollectionId;

	/**
	 * Returns all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByCTCollectionId(
		long ctCollectionId) {

		return findByCTCollectionId(
			ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @return the range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByCTCollectionId(
		long ctCollectionId, int start, int end) {

		return findByCTCollectionId(ctCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		return findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCTCollectionId;
				finderArgs = new Object[] {ctCollectionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCTCollectionId;
			finderArgs = new Object[] {
				ctCollectionId, start, end, orderByComparator
			};
		}

		List<CTAutoResolutionInfo> list = null;

		if (useFinderCache) {
			list = (List<CTAutoResolutionInfo>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTAutoResolutionInfo ctAutoResolutionInfo : list) {
					if (ctCollectionId !=
							ctAutoResolutionInfo.getCtCollectionId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CTAUTORESOLUTIONINFO_WHERE);

			query.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(CTAutoResolutionInfoModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				list = (List<CTAutoResolutionInfo>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a matching ct auto resolution info could not be found
	 */
	@Override
	public CTAutoResolutionInfo findByCTCollectionId_First(
			long ctCollectionId,
			OrderByComparator<CTAutoResolutionInfo> orderByComparator)
		throws NoSuchAutoResolutionInfoException {

		CTAutoResolutionInfo ctAutoResolutionInfo = fetchByCTCollectionId_First(
			ctCollectionId, orderByComparator);

		if (ctAutoResolutionInfo != null) {
			return ctAutoResolutionInfo;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append("}");

		throw new NoSuchAutoResolutionInfoException(msg.toString());
	}

	/**
	 * Returns the first ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct auto resolution info, or <code>null</code> if a matching ct auto resolution info could not be found
	 */
	@Override
	public CTAutoResolutionInfo fetchByCTCollectionId_First(
		long ctCollectionId,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		List<CTAutoResolutionInfo> list = findByCTCollectionId(
			ctCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a matching ct auto resolution info could not be found
	 */
	@Override
	public CTAutoResolutionInfo findByCTCollectionId_Last(
			long ctCollectionId,
			OrderByComparator<CTAutoResolutionInfo> orderByComparator)
		throws NoSuchAutoResolutionInfoException {

		CTAutoResolutionInfo ctAutoResolutionInfo = fetchByCTCollectionId_Last(
			ctCollectionId, orderByComparator);

		if (ctAutoResolutionInfo != null) {
			return ctAutoResolutionInfo;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append("}");

		throw new NoSuchAutoResolutionInfoException(msg.toString());
	}

	/**
	 * Returns the last ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct auto resolution info, or <code>null</code> if a matching ct auto resolution info could not be found
	 */
	@Override
	public CTAutoResolutionInfo fetchByCTCollectionId_Last(
		long ctCollectionId,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		int count = countByCTCollectionId(ctCollectionId);

		if (count == 0) {
			return null;
		}

		List<CTAutoResolutionInfo> list = findByCTCollectionId(
			ctCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct auto resolution infos before and after the current ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the current ct auto resolution info
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	@Override
	public CTAutoResolutionInfo[] findByCTCollectionId_PrevAndNext(
			long ctAutoResolutionInfoId, long ctCollectionId,
			OrderByComparator<CTAutoResolutionInfo> orderByComparator)
		throws NoSuchAutoResolutionInfoException {

		CTAutoResolutionInfo ctAutoResolutionInfo = findByPrimaryKey(
			ctAutoResolutionInfoId);

		Session session = null;

		try {
			session = openSession();

			CTAutoResolutionInfo[] array = new CTAutoResolutionInfoImpl[3];

			array[0] = getByCTCollectionId_PrevAndNext(
				session, ctAutoResolutionInfo, ctCollectionId,
				orderByComparator, true);

			array[1] = ctAutoResolutionInfo;

			array[2] = getByCTCollectionId_PrevAndNext(
				session, ctAutoResolutionInfo, ctCollectionId,
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

	protected CTAutoResolutionInfo getByCTCollectionId_PrevAndNext(
		Session session, CTAutoResolutionInfo ctAutoResolutionInfo,
		long ctCollectionId,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CTAUTORESOLUTIONINFO_WHERE);

		query.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CTAutoResolutionInfoModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ctCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ctAutoResolutionInfo)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CTAutoResolutionInfo> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct auto resolution infos where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	@Override
	public void removeByCTCollectionId(long ctCollectionId) {
		for (CTAutoResolutionInfo ctAutoResolutionInfo :
				findByCTCollectionId(
					ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ctAutoResolutionInfo);
		}
	}

	/**
	 * Returns the number of ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct auto resolution infos
	 */
	@Override
	public int countByCTCollectionId(long ctCollectionId) {
		FinderPath finderPath = _finderPathCountByCTCollectionId;

		Object[] finderArgs = new Object[] {ctCollectionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CTAUTORESOLUTIONINFO_WHERE);

			query.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2 =
		"ctAutoResolutionInfo.ctCollectionId = ?";

	public CTAutoResolutionInfoPersistenceImpl() {
		setModelClass(CTAutoResolutionInfo.class);

		setModelImplClass(CTAutoResolutionInfoImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the ct auto resolution info in the entity cache if it is enabled.
	 *
	 * @param ctAutoResolutionInfo the ct auto resolution info
	 */
	@Override
	public void cacheResult(CTAutoResolutionInfo ctAutoResolutionInfo) {
		entityCache.putResult(
			entityCacheEnabled, CTAutoResolutionInfoImpl.class,
			ctAutoResolutionInfo.getPrimaryKey(), ctAutoResolutionInfo);

		ctAutoResolutionInfo.resetOriginalValues();
	}

	/**
	 * Caches the ct auto resolution infos in the entity cache if it is enabled.
	 *
	 * @param ctAutoResolutionInfos the ct auto resolution infos
	 */
	@Override
	public void cacheResult(List<CTAutoResolutionInfo> ctAutoResolutionInfos) {
		for (CTAutoResolutionInfo ctAutoResolutionInfo :
				ctAutoResolutionInfos) {

			if (entityCache.getResult(
					entityCacheEnabled, CTAutoResolutionInfoImpl.class,
					ctAutoResolutionInfo.getPrimaryKey()) == null) {

				cacheResult(ctAutoResolutionInfo);
			}
			else {
				ctAutoResolutionInfo.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ct auto resolution infos.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTAutoResolutionInfoImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ct auto resolution info.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTAutoResolutionInfo ctAutoResolutionInfo) {
		entityCache.removeResult(
			entityCacheEnabled, CTAutoResolutionInfoImpl.class,
			ctAutoResolutionInfo.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CTAutoResolutionInfo> ctAutoResolutionInfos) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CTAutoResolutionInfo ctAutoResolutionInfo :
				ctAutoResolutionInfos) {

			entityCache.removeResult(
				entityCacheEnabled, CTAutoResolutionInfoImpl.class,
				ctAutoResolutionInfo.getPrimaryKey());
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, CTAutoResolutionInfoImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new ct auto resolution info with the primary key. Does not add the ct auto resolution info to the database.
	 *
	 * @param ctAutoResolutionInfoId the primary key for the new ct auto resolution info
	 * @return the new ct auto resolution info
	 */
	@Override
	public CTAutoResolutionInfo create(long ctAutoResolutionInfoId) {
		CTAutoResolutionInfo ctAutoResolutionInfo =
			new CTAutoResolutionInfoImpl();

		ctAutoResolutionInfo.setNew(true);
		ctAutoResolutionInfo.setPrimaryKey(ctAutoResolutionInfoId);

		ctAutoResolutionInfo.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ctAutoResolutionInfo;
	}

	/**
	 * Removes the ct auto resolution info with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the ct auto resolution info
	 * @return the ct auto resolution info that was removed
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	@Override
	public CTAutoResolutionInfo remove(long ctAutoResolutionInfoId)
		throws NoSuchAutoResolutionInfoException {

		return remove((Serializable)ctAutoResolutionInfoId);
	}

	/**
	 * Removes the ct auto resolution info with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ct auto resolution info
	 * @return the ct auto resolution info that was removed
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	@Override
	public CTAutoResolutionInfo remove(Serializable primaryKey)
		throws NoSuchAutoResolutionInfoException {

		Session session = null;

		try {
			session = openSession();

			CTAutoResolutionInfo ctAutoResolutionInfo =
				(CTAutoResolutionInfo)session.get(
					CTAutoResolutionInfoImpl.class, primaryKey);

			if (ctAutoResolutionInfo == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAutoResolutionInfoException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ctAutoResolutionInfo);
		}
		catch (NoSuchAutoResolutionInfoException noSuchEntityException) {
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
	protected CTAutoResolutionInfo removeImpl(
		CTAutoResolutionInfo ctAutoResolutionInfo) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctAutoResolutionInfo)) {
				ctAutoResolutionInfo = (CTAutoResolutionInfo)session.get(
					CTAutoResolutionInfoImpl.class,
					ctAutoResolutionInfo.getPrimaryKeyObj());
			}

			if (ctAutoResolutionInfo != null) {
				session.delete(ctAutoResolutionInfo);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ctAutoResolutionInfo != null) {
			clearCache(ctAutoResolutionInfo);
		}

		return ctAutoResolutionInfo;
	}

	@Override
	public CTAutoResolutionInfo updateImpl(
		CTAutoResolutionInfo ctAutoResolutionInfo) {

		boolean isNew = ctAutoResolutionInfo.isNew();

		if (!(ctAutoResolutionInfo instanceof CTAutoResolutionInfoModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctAutoResolutionInfo.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ctAutoResolutionInfo);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctAutoResolutionInfo proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTAutoResolutionInfo implementation " +
					ctAutoResolutionInfo.getClass());
		}

		CTAutoResolutionInfoModelImpl ctAutoResolutionInfoModelImpl =
			(CTAutoResolutionInfoModelImpl)ctAutoResolutionInfo;

		Session session = null;

		try {
			session = openSession();

			if (ctAutoResolutionInfo.isNew()) {
				session.save(ctAutoResolutionInfo);

				ctAutoResolutionInfo.setNew(false);
			}
			else {
				ctAutoResolutionInfo = (CTAutoResolutionInfo)session.merge(
					ctAutoResolutionInfo);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				ctAutoResolutionInfoModelImpl.getCtCollectionId()
			};

			finderCache.removeResult(_finderPathCountByCTCollectionId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCTCollectionId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ctAutoResolutionInfoModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCTCollectionId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ctAutoResolutionInfoModelImpl.getOriginalCtCollectionId()
				};

				finderCache.removeResult(
					_finderPathCountByCTCollectionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCTCollectionId, args);

				args = new Object[] {
					ctAutoResolutionInfoModelImpl.getCtCollectionId()
				};

				finderCache.removeResult(
					_finderPathCountByCTCollectionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCTCollectionId, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, CTAutoResolutionInfoImpl.class,
			ctAutoResolutionInfo.getPrimaryKey(), ctAutoResolutionInfo, false);

		ctAutoResolutionInfo.resetOriginalValues();

		return ctAutoResolutionInfo;
	}

	/**
	 * Returns the ct auto resolution info with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ct auto resolution info
	 * @return the ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	@Override
	public CTAutoResolutionInfo findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAutoResolutionInfoException {

		CTAutoResolutionInfo ctAutoResolutionInfo = fetchByPrimaryKey(
			primaryKey);

		if (ctAutoResolutionInfo == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAutoResolutionInfoException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ctAutoResolutionInfo;
	}

	/**
	 * Returns the ct auto resolution info with the primary key or throws a <code>NoSuchAutoResolutionInfoException</code> if it could not be found.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the ct auto resolution info
	 * @return the ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	@Override
	public CTAutoResolutionInfo findByPrimaryKey(long ctAutoResolutionInfoId)
		throws NoSuchAutoResolutionInfoException {

		return findByPrimaryKey((Serializable)ctAutoResolutionInfoId);
	}

	/**
	 * Returns the ct auto resolution info with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the ct auto resolution info
	 * @return the ct auto resolution info, or <code>null</code> if a ct auto resolution info with the primary key could not be found
	 */
	@Override
	public CTAutoResolutionInfo fetchByPrimaryKey(long ctAutoResolutionInfoId) {
		return fetchByPrimaryKey((Serializable)ctAutoResolutionInfoId);
	}

	/**
	 * Returns all the ct auto resolution infos.
	 *
	 * @return the ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct auto resolution infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @return the range of ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findAll(
		int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findAll(
		int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator,
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

		List<CTAutoResolutionInfo> list = null;

		if (useFinderCache) {
			list = (List<CTAutoResolutionInfo>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CTAUTORESOLUTIONINFO);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CTAUTORESOLUTIONINFO;

				sql = sql.concat(CTAutoResolutionInfoModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<CTAutoResolutionInfo>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the ct auto resolution infos from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTAutoResolutionInfo ctAutoResolutionInfo : findAll()) {
			remove(ctAutoResolutionInfo);
		}
	}

	/**
	 * Returns the number of ct auto resolution infos.
	 *
	 * @return the number of ct auto resolution infos
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CTAUTORESOLUTIONINFO);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "ctAutoResolutionInfoId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTAUTORESOLUTIONINFO;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTAutoResolutionInfoModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ct auto resolution info persistence.
	 */
	@Activate
	public void activate() {
		CTAutoResolutionInfoModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		CTAutoResolutionInfoModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			CTAutoResolutionInfoImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			CTAutoResolutionInfoImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCTCollectionId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			CTAutoResolutionInfoImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCTCollectionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCTCollectionId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			CTAutoResolutionInfoImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCTCollectionId",
			new String[] {Long.class.getName()},
			CTAutoResolutionInfoModelImpl.CTCOLLECTIONID_COLUMN_BITMASK |
			CTAutoResolutionInfoModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByCTCollectionId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCTCollectionId",
			new String[] {Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CTAutoResolutionInfoImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.change.tracking.model.CTAutoResolutionInfo"),
			true);
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CTAUTORESOLUTIONINFO =
		"SELECT ctAutoResolutionInfo FROM CTAutoResolutionInfo ctAutoResolutionInfo";

	private static final String _SQL_SELECT_CTAUTORESOLUTIONINFO_WHERE =
		"SELECT ctAutoResolutionInfo FROM CTAutoResolutionInfo ctAutoResolutionInfo WHERE ";

	private static final String _SQL_COUNT_CTAUTORESOLUTIONINFO =
		"SELECT COUNT(ctAutoResolutionInfo) FROM CTAutoResolutionInfo ctAutoResolutionInfo";

	private static final String _SQL_COUNT_CTAUTORESOLUTIONINFO_WHERE =
		"SELECT COUNT(ctAutoResolutionInfo) FROM CTAutoResolutionInfo ctAutoResolutionInfo WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"ctAutoResolutionInfo.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CTAutoResolutionInfo exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTAutoResolutionInfo exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTAutoResolutionInfoPersistenceImpl.class);

	static {
		try {
			Class.forName(CTPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}