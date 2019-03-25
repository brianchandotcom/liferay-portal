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

import com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryVersionException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntryVersion;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateEntryVersionImpl;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateEntryVersionModelImpl;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateEntryVersionPersistence;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the layout page template entry version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LayoutPageTemplateEntryVersionPersistenceImpl
	extends BasePersistenceImpl<LayoutPageTemplateEntryVersion>
	implements LayoutPageTemplateEntryVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutPageTemplateEntryVersionUtil</code> to access the layout page template entry version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LayoutPageTemplateEntryVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindBylayoutPageTemplateEntryId;
	private FinderPath
		_finderPathWithoutPaginationFindBylayoutPageTemplateEntryId;
	private FinderPath _finderPathCountBylayoutPageTemplateEntryId;

	/**
	 * Returns all the layout page template entry versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the matching layout page template entry versions
	 */
	@Override
	public List<LayoutPageTemplateEntryVersion> findBylayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		return findBylayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout page template entry versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @return the range of matching layout page template entry versions
	 */
	@Override
	public List<LayoutPageTemplateEntryVersion> findBylayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end) {

		return findBylayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entry versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entry versions
	 */
	@Override
	public List<LayoutPageTemplateEntryVersion> findBylayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntryVersion> orderByComparator) {

		return findBylayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entry versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template entry versions
	 */
	@Override
	public List<LayoutPageTemplateEntryVersion> findBylayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntryVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath =
				_finderPathWithoutPaginationFindBylayoutPageTemplateEntryId;
			finderArgs = new Object[] {layoutPageTemplateEntryId};
		}
		else {
			finderPath =
				_finderPathWithPaginationFindBylayoutPageTemplateEntryId;
			finderArgs = new Object[] {
				layoutPageTemplateEntryId, start, end, orderByComparator
			};
		}

		List<LayoutPageTemplateEntryVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntryVersion
						layoutPageTemplateEntryVersion : list) {

					if ((layoutPageTemplateEntryId !=
							layoutPageTemplateEntryVersion.
								getLayoutPageTemplateEntryId())) {

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

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRYVERSION_WHERE);

			query.append(
				_FINDER_COLUMN_LAYOUTPAGETEMPLATEENTRYID_LAYOUTPAGETEMPLATEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(
					LayoutPageTemplateEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutPageTemplateEntryId);

				if (!pagination) {
					list = (List<LayoutPageTemplateEntryVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateEntryVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout page template entry version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry version
	 * @throws NoSuchPageTemplateEntryVersionException if a matching layout page template entry version could not be found
	 */
	@Override
	public LayoutPageTemplateEntryVersion findBylayoutPageTemplateEntryId_First(
			long layoutPageTemplateEntryId,
			OrderByComparator<LayoutPageTemplateEntryVersion> orderByComparator)
		throws NoSuchPageTemplateEntryVersionException {

		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion =
			fetchBylayoutPageTemplateEntryId_First(
				layoutPageTemplateEntryId, orderByComparator);

		if (layoutPageTemplateEntryVersion != null) {
			return layoutPageTemplateEntryVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPageTemplateEntryId=");
		msg.append(layoutPageTemplateEntryId);

		msg.append("}");

		throw new NoSuchPageTemplateEntryVersionException(msg.toString());
	}

	/**
	 * Returns the first layout page template entry version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry version, or <code>null</code> if a matching layout page template entry version could not be found
	 */
	@Override
	public LayoutPageTemplateEntryVersion
		fetchBylayoutPageTemplateEntryId_First(
			long layoutPageTemplateEntryId,
			OrderByComparator<LayoutPageTemplateEntryVersion>
				orderByComparator) {

		List<LayoutPageTemplateEntryVersion> list =
			findBylayoutPageTemplateEntryId(
				layoutPageTemplateEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry version
	 * @throws NoSuchPageTemplateEntryVersionException if a matching layout page template entry version could not be found
	 */
	@Override
	public LayoutPageTemplateEntryVersion findBylayoutPageTemplateEntryId_Last(
			long layoutPageTemplateEntryId,
			OrderByComparator<LayoutPageTemplateEntryVersion> orderByComparator)
		throws NoSuchPageTemplateEntryVersionException {

		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion =
			fetchBylayoutPageTemplateEntryId_Last(
				layoutPageTemplateEntryId, orderByComparator);

		if (layoutPageTemplateEntryVersion != null) {
			return layoutPageTemplateEntryVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPageTemplateEntryId=");
		msg.append(layoutPageTemplateEntryId);

		msg.append("}");

		throw new NoSuchPageTemplateEntryVersionException(msg.toString());
	}

	/**
	 * Returns the last layout page template entry version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry version, or <code>null</code> if a matching layout page template entry version could not be found
	 */
	@Override
	public LayoutPageTemplateEntryVersion fetchBylayoutPageTemplateEntryId_Last(
		long layoutPageTemplateEntryId,
		OrderByComparator<LayoutPageTemplateEntryVersion> orderByComparator) {

		int count = countBylayoutPageTemplateEntryId(layoutPageTemplateEntryId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntryVersion> list =
			findBylayoutPageTemplateEntryId(
				layoutPageTemplateEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entry versions before and after the current layout page template entry version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key of the current layout page template entry version
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry version
	 * @throws NoSuchPageTemplateEntryVersionException if a layout page template entry version with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntryVersion[]
			findBylayoutPageTemplateEntryId_PrevAndNext(
				long layoutPageTemplateEntryVersionId,
				long layoutPageTemplateEntryId,
				OrderByComparator<LayoutPageTemplateEntryVersion>
					orderByComparator)
		throws NoSuchPageTemplateEntryVersionException {

		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion =
			findByPrimaryKey(layoutPageTemplateEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntryVersion[] array =
				new LayoutPageTemplateEntryVersionImpl[3];

			array[0] = getBylayoutPageTemplateEntryId_PrevAndNext(
				session, layoutPageTemplateEntryVersion,
				layoutPageTemplateEntryId, orderByComparator, true);

			array[1] = layoutPageTemplateEntryVersion;

			array[2] = getBylayoutPageTemplateEntryId_PrevAndNext(
				session, layoutPageTemplateEntryVersion,
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

	protected LayoutPageTemplateEntryVersion
		getBylayoutPageTemplateEntryId_PrevAndNext(
			Session session,
			LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion,
			long layoutPageTemplateEntryId,
			OrderByComparator<LayoutPageTemplateEntryVersion> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRYVERSION_WHERE);

		query.append(
			_FINDER_COLUMN_LAYOUTPAGETEMPLATEENTRYID_LAYOUTPAGETEMPLATEENTRYID_2);

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
			query.append(LayoutPageTemplateEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutPageTemplateEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntryVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntryVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entry versions where layoutPageTemplateEntryId = &#63; from the database.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 */
	@Override
	public void removeBylayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		for (LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion :
				findBylayoutPageTemplateEntryId(
					layoutPageTemplateEntryId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntryVersion);
		}
	}

	/**
	 * Returns the number of layout page template entry versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the number of matching layout page template entry versions
	 */
	@Override
	public int countBylayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		FinderPath finderPath = _finderPathCountBylayoutPageTemplateEntryId;

		Object[] finderArgs = new Object[] {layoutPageTemplateEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRYVERSION_WHERE);

			query.append(
				_FINDER_COLUMN_LAYOUTPAGETEMPLATEENTRYID_LAYOUTPAGETEMPLATEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String
		_FINDER_COLUMN_LAYOUTPAGETEMPLATEENTRYID_LAYOUTPAGETEMPLATEENTRYID_2 =
			"layoutPageTemplateEntryVersion.layoutPageTemplateEntryId = ?";

	public LayoutPageTemplateEntryVersionPersistenceImpl() {
		setModelClass(LayoutPageTemplateEntryVersion.class);

		setModelImplClass(LayoutPageTemplateEntryVersionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(
			LayoutPageTemplateEntryVersionModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("layoutPageTemplateEntryVersionId", "lPTEVersionId");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the layout page template entry version in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateEntryVersion the layout page template entry version
	 */
	@Override
	public void cacheResult(
		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion) {

		entityCache.putResult(
			LayoutPageTemplateEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryVersionImpl.class,
			layoutPageTemplateEntryVersion.getPrimaryKey(),
			layoutPageTemplateEntryVersion);

		layoutPageTemplateEntryVersion.resetOriginalValues();
	}

	/**
	 * Caches the layout page template entry versions in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateEntryVersions the layout page template entry versions
	 */
	@Override
	public void cacheResult(
		List<LayoutPageTemplateEntryVersion> layoutPageTemplateEntryVersions) {

		for (LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion :
				layoutPageTemplateEntryVersions) {

			if (entityCache.getResult(
					LayoutPageTemplateEntryVersionModelImpl.
						ENTITY_CACHE_ENABLED,
					LayoutPageTemplateEntryVersionImpl.class,
					layoutPageTemplateEntryVersion.getPrimaryKey()) == null) {

				cacheResult(layoutPageTemplateEntryVersion);
			}
			else {
				layoutPageTemplateEntryVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout page template entry versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutPageTemplateEntryVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout page template entry version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion) {

		entityCache.removeResult(
			LayoutPageTemplateEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryVersionImpl.class,
			layoutPageTemplateEntryVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<LayoutPageTemplateEntryVersion> layoutPageTemplateEntryVersions) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion :
				layoutPageTemplateEntryVersions) {

			entityCache.removeResult(
				LayoutPageTemplateEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateEntryVersionImpl.class,
				layoutPageTemplateEntryVersion.getPrimaryKey());
		}
	}

	/**
	 * Creates a new layout page template entry version with the primary key. Does not add the layout page template entry version to the database.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key for the new layout page template entry version
	 * @return the new layout page template entry version
	 */
	@Override
	public LayoutPageTemplateEntryVersion create(
		long layoutPageTemplateEntryVersionId) {

		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion =
			new LayoutPageTemplateEntryVersionImpl();

		layoutPageTemplateEntryVersion.setNew(true);
		layoutPageTemplateEntryVersion.setPrimaryKey(
			layoutPageTemplateEntryVersionId);

		layoutPageTemplateEntryVersion.setCompanyId(
			companyProvider.getCompanyId());

		return layoutPageTemplateEntryVersion;
	}

	/**
	 * Removes the layout page template entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key of the layout page template entry version
	 * @return the layout page template entry version that was removed
	 * @throws NoSuchPageTemplateEntryVersionException if a layout page template entry version with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntryVersion remove(
			long layoutPageTemplateEntryVersionId)
		throws NoSuchPageTemplateEntryVersionException {

		return remove((Serializable)layoutPageTemplateEntryVersionId);
	}

	/**
	 * Removes the layout page template entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout page template entry version
	 * @return the layout page template entry version that was removed
	 * @throws NoSuchPageTemplateEntryVersionException if a layout page template entry version with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntryVersion remove(Serializable primaryKey)
		throws NoSuchPageTemplateEntryVersionException {

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion =
				(LayoutPageTemplateEntryVersion)session.get(
					LayoutPageTemplateEntryVersionImpl.class, primaryKey);

			if (layoutPageTemplateEntryVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPageTemplateEntryVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(layoutPageTemplateEntryVersion);
		}
		catch (NoSuchPageTemplateEntryVersionException nsee) {
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
	protected LayoutPageTemplateEntryVersion removeImpl(
		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutPageTemplateEntryVersion)) {
				layoutPageTemplateEntryVersion =
					(LayoutPageTemplateEntryVersion)session.get(
						LayoutPageTemplateEntryVersionImpl.class,
						layoutPageTemplateEntryVersion.getPrimaryKeyObj());
			}

			if (layoutPageTemplateEntryVersion != null) {
				session.delete(layoutPageTemplateEntryVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutPageTemplateEntryVersion != null) {
			clearCache(layoutPageTemplateEntryVersion);
		}

		return layoutPageTemplateEntryVersion;
	}

	@Override
	public LayoutPageTemplateEntryVersion updateImpl(
		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion) {

		boolean isNew = layoutPageTemplateEntryVersion.isNew();

		if (!(layoutPageTemplateEntryVersion instanceof
				LayoutPageTemplateEntryVersionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					layoutPageTemplateEntryVersion.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					layoutPageTemplateEntryVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutPageTemplateEntryVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutPageTemplateEntryVersion implementation " +
					layoutPageTemplateEntryVersion.getClass());
		}

		LayoutPageTemplateEntryVersionModelImpl
			layoutPageTemplateEntryVersionModelImpl =
				(LayoutPageTemplateEntryVersionModelImpl)
					layoutPageTemplateEntryVersion;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutPageTemplateEntryVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutPageTemplateEntryVersion.setCreateDate(now);
			}
			else {
				layoutPageTemplateEntryVersion.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!layoutPageTemplateEntryVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutPageTemplateEntryVersion.setModifiedDate(now);
			}
			else {
				layoutPageTemplateEntryVersion.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutPageTemplateEntryVersion.isNew()) {
				session.save(layoutPageTemplateEntryVersion);

				layoutPageTemplateEntryVersion.setNew(false);
			}
			else {
				layoutPageTemplateEntryVersion =
					(LayoutPageTemplateEntryVersion)session.merge(
						layoutPageTemplateEntryVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LayoutPageTemplateEntryVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				layoutPageTemplateEntryVersionModelImpl.
					getLayoutPageTemplateEntryId()
			};

			finderCache.removeResult(
				_finderPathCountBylayoutPageTemplateEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindBylayoutPageTemplateEntryId,
				args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((layoutPageTemplateEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindBylayoutPageTemplateEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryVersionModelImpl.
						getOriginalLayoutPageTemplateEntryId()
				};

				finderCache.removeResult(
					_finderPathCountBylayoutPageTemplateEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBylayoutPageTemplateEntryId,
					args);

				args = new Object[] {
					layoutPageTemplateEntryVersionModelImpl.
						getLayoutPageTemplateEntryId()
				};

				finderCache.removeResult(
					_finderPathCountBylayoutPageTemplateEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBylayoutPageTemplateEntryId,
					args);
			}
		}

		entityCache.putResult(
			LayoutPageTemplateEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryVersionImpl.class,
			layoutPageTemplateEntryVersion.getPrimaryKey(),
			layoutPageTemplateEntryVersion, false);

		layoutPageTemplateEntryVersion.resetOriginalValues();

		return layoutPageTemplateEntryVersion;
	}

	/**
	 * Returns the layout page template entry version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout page template entry version
	 * @return the layout page template entry version
	 * @throws NoSuchPageTemplateEntryVersionException if a layout page template entry version with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntryVersion findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchPageTemplateEntryVersionException {

		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion =
			fetchByPrimaryKey(primaryKey);

		if (layoutPageTemplateEntryVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPageTemplateEntryVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return layoutPageTemplateEntryVersion;
	}

	/**
	 * Returns the layout page template entry version with the primary key or throws a <code>NoSuchPageTemplateEntryVersionException</code> if it could not be found.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key of the layout page template entry version
	 * @return the layout page template entry version
	 * @throws NoSuchPageTemplateEntryVersionException if a layout page template entry version with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntryVersion findByPrimaryKey(
			long layoutPageTemplateEntryVersionId)
		throws NoSuchPageTemplateEntryVersionException {

		return findByPrimaryKey((Serializable)layoutPageTemplateEntryVersionId);
	}

	/**
	 * Returns the layout page template entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key of the layout page template entry version
	 * @return the layout page template entry version, or <code>null</code> if a layout page template entry version with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntryVersion fetchByPrimaryKey(
		long layoutPageTemplateEntryVersionId) {

		return fetchByPrimaryKey(
			(Serializable)layoutPageTemplateEntryVersionId);
	}

	/**
	 * Returns all the layout page template entry versions.
	 *
	 * @return the layout page template entry versions
	 */
	@Override
	public List<LayoutPageTemplateEntryVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @return the range of layout page template entry versions
	 */
	@Override
	public List<LayoutPageTemplateEntryVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout page template entry versions
	 */
	@Override
	public List<LayoutPageTemplateEntryVersion> findAll(
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntryVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout page template entry versions
	 */
	@Override
	public List<LayoutPageTemplateEntryVersion> findAll(
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntryVersion> orderByComparator,
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
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<LayoutPageTemplateEntryVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRYVERSION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTPAGETEMPLATEENTRYVERSION;

				if (pagination) {
					sql = sql.concat(
						LayoutPageTemplateEntryVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutPageTemplateEntryVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateEntryVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Removes all the layout page template entry versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion :
				findAll()) {

			remove(layoutPageTemplateEntryVersion);
		}
	}

	/**
	 * Returns the number of layout page template entry versions.
	 *
	 * @return the number of layout page template entry versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
					_SQL_COUNT_LAYOUTPAGETEMPLATEENTRYVERSION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "lPTEVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTPAGETEMPLATEENTRYVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutPageTemplateEntryVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout page template entry version persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			LayoutPageTemplateEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LayoutPageTemplateEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			LayoutPageTemplateEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindBylayoutPageTemplateEntryId =
			new FinderPath(
				LayoutPageTemplateEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateEntryVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutPageTemplateEntryVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findBylayoutPageTemplateEntryId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindBylayoutPageTemplateEntryId =
			new FinderPath(
				LayoutPageTemplateEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateEntryVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutPageTemplateEntryVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findBylayoutPageTemplateEntryId",
				new String[] {Long.class.getName()},
				LayoutPageTemplateEntryVersionModelImpl.
					LAYOUTPAGETEMPLATEENTRYID_COLUMN_BITMASK);

		_finderPathCountBylayoutPageTemplateEntryId = new FinderPath(
			LayoutPageTemplateEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBylayoutPageTemplateEntryId",
			new String[] {Long.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(
			LayoutPageTemplateEntryVersionImpl.class.getName());
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

	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATEENTRYVERSION =
		"SELECT layoutPageTemplateEntryVersion FROM LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion";

	private static final String
		_SQL_SELECT_LAYOUTPAGETEMPLATEENTRYVERSION_WHERE =
			"SELECT layoutPageTemplateEntryVersion FROM LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion WHERE ";

	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATEENTRYVERSION =
		"SELECT COUNT(layoutPageTemplateEntryVersion) FROM LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion";

	private static final String
		_SQL_COUNT_LAYOUTPAGETEMPLATEENTRYVERSION_WHERE =
			"SELECT COUNT(layoutPageTemplateEntryVersion) FROM LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"layoutPageTemplateEntryVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LayoutPageTemplateEntryVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LayoutPageTemplateEntryVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateEntryVersionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"layoutPageTemplateEntryVersionId"});

}