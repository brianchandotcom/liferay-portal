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

package com.liferay.asset.display.template.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException;
import com.liferay.asset.display.template.model.AssetDisplayTemplate;
import com.liferay.asset.display.template.model.impl.AssetDisplayTemplateImpl;
import com.liferay.asset.display.template.model.impl.AssetDisplayTemplateModelImpl;
import com.liferay.asset.display.template.service.persistence.AssetDisplayTemplatePersistence;

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
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
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
 * The persistence implementation for the asset display template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayTemplatePersistence
 * @see com.liferay.asset.display.template.service.persistence.AssetDisplayTemplateUtil
 * @generated
 */
@ProviderType
public class AssetDisplayTemplatePersistenceImpl extends BasePersistenceImpl<AssetDisplayTemplate>
	implements AssetDisplayTemplatePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetDisplayTemplateUtil} to access the asset display template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetDisplayTemplateImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayTemplateModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayTemplateModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayTemplateModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayTemplateModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			AssetDisplayTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			AssetDisplayTemplateModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the asset display templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset display templates
	 */
	@Override
	public List<AssetDisplayTemplate> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @return the range of matching asset display templates
	 */
	@Override
	public List<AssetDisplayTemplate> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display templates
	 */
	@Override
	public List<AssetDisplayTemplate> findByGroupId(long groupId, int start,
		int end, OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset display templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset display templates
	 */
	@Override
	public List<AssetDisplayTemplate> findByGroupId(long groupId, int start,
		int end, OrderByComparator<AssetDisplayTemplate> orderByComparator,
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

		List<AssetDisplayTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<AssetDisplayTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetDisplayTemplate assetDisplayTemplate : list) {
					if ((groupId != assetDisplayTemplate.getGroupId())) {
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

			query.append(_SQL_SELECT_ASSETDISPLAYTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetDisplayTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<AssetDisplayTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetDisplayTemplate>)QueryUtil.list(q,
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
	 * Returns the first asset display template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display template
	 * @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	 */
	@Override
	public AssetDisplayTemplate findByGroupId_First(long groupId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException {
		AssetDisplayTemplate assetDisplayTemplate = fetchByGroupId_First(groupId,
				orderByComparator);

		if (assetDisplayTemplate != null) {
			return assetDisplayTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchDisplayTemplateException(msg.toString());
	}

	/**
	 * Returns the first asset display template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display template, or <code>null</code> if a matching asset display template could not be found
	 */
	@Override
	public AssetDisplayTemplate fetchByGroupId_First(long groupId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		List<AssetDisplayTemplate> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset display template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display template
	 * @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	 */
	@Override
	public AssetDisplayTemplate findByGroupId_Last(long groupId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException {
		AssetDisplayTemplate assetDisplayTemplate = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (assetDisplayTemplate != null) {
			return assetDisplayTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchDisplayTemplateException(msg.toString());
	}

	/**
	 * Returns the last asset display template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display template, or <code>null</code> if a matching asset display template could not be found
	 */
	@Override
	public AssetDisplayTemplate fetchByGroupId_Last(long groupId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<AssetDisplayTemplate> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset display templates before and after the current asset display template in the ordered set where groupId = &#63;.
	 *
	 * @param assetDisplayTemplateId the primary key of the current asset display template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset display template
	 * @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	 */
	@Override
	public AssetDisplayTemplate[] findByGroupId_PrevAndNext(
		long assetDisplayTemplateId, long groupId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException {
		AssetDisplayTemplate assetDisplayTemplate = findByPrimaryKey(assetDisplayTemplateId);

		Session session = null;

		try {
			session = openSession();

			AssetDisplayTemplate[] array = new AssetDisplayTemplateImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, assetDisplayTemplate,
					groupId, orderByComparator, true);

			array[1] = assetDisplayTemplate;

			array[2] = getByGroupId_PrevAndNext(session, assetDisplayTemplate,
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

	protected AssetDisplayTemplate getByGroupId_PrevAndNext(Session session,
		AssetDisplayTemplate assetDisplayTemplate, long groupId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETDISPLAYTEMPLATE_WHERE);

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
			query.append(AssetDisplayTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetDisplayTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetDisplayTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset display templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset display templates that the user has permission to view
	 */
	@Override
	public List<AssetDisplayTemplate> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display templates that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @return the range of matching asset display templates that the user has permission to view
	 */
	@Override
	public List<AssetDisplayTemplate> filterFindByGroupId(long groupId,
		int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display templates that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display templates that the user has permission to view
	 */
	@Override
	public List<AssetDisplayTemplate> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AssetDisplayTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetDisplayTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetDisplayTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AssetDisplayTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AssetDisplayTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<AssetDisplayTemplate>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset display templates before and after the current asset display template in the ordered set of asset display templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param assetDisplayTemplateId the primary key of the current asset display template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset display template
	 * @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	 */
	@Override
	public AssetDisplayTemplate[] filterFindByGroupId_PrevAndNext(
		long assetDisplayTemplateId, long groupId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(assetDisplayTemplateId, groupId,
				orderByComparator);
		}

		AssetDisplayTemplate assetDisplayTemplate = findByPrimaryKey(assetDisplayTemplateId);

		Session session = null;

		try {
			session = openSession();

			AssetDisplayTemplate[] array = new AssetDisplayTemplateImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					assetDisplayTemplate, groupId, orderByComparator, true);

			array[1] = assetDisplayTemplate;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					assetDisplayTemplate, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetDisplayTemplate filterGetByGroupId_PrevAndNext(
		Session session, AssetDisplayTemplate assetDisplayTemplate,
		long groupId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AssetDisplayTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetDisplayTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetDisplayTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AssetDisplayTemplateImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AssetDisplayTemplateImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetDisplayTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetDisplayTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset display templates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (AssetDisplayTemplate assetDisplayTemplate : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetDisplayTemplate);
		}
	}

	/**
	 * Returns the number of asset display templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset display templates
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETDISPLAYTEMPLATE_WHERE);

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

	/**
	 * Returns the number of asset display templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset display templates that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_ASSETDISPLAYTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetDisplayTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "assetDisplayTemplate.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKEN = new FinderPath(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayTemplateModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKEN = new FinderPath(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_LikeN",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the asset display templates where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching asset display templates
	 */
	@Override
	public List<AssetDisplayTemplate> findByG_LikeN(long groupId, String name) {
		return findByG_LikeN(groupId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display templates where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @return the range of matching asset display templates
	 */
	@Override
	public List<AssetDisplayTemplate> findByG_LikeN(long groupId, String name,
		int start, int end) {
		return findByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display templates where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display templates
	 */
	@Override
	public List<AssetDisplayTemplate> findByG_LikeN(long groupId, String name,
		int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return findByG_LikeN(groupId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset display templates where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset display templates
	 */
	@Override
	public List<AssetDisplayTemplate> findByG_LikeN(long groupId, String name,
		int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKEN;
		finderArgs = new Object[] { groupId, name, start, end, orderByComparator };

		List<AssetDisplayTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<AssetDisplayTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetDisplayTemplate assetDisplayTemplate : list) {
					if ((groupId != assetDisplayTemplate.getGroupId()) ||
							!StringUtil.wildcardMatches(
								assetDisplayTemplate.getName(), name, '_', '%',
								'\\', false)) {
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

			query.append(_SQL_SELECT_ASSETDISPLAYTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
			}
			else if (name.equals("")) {
				query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetDisplayTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(StringUtil.toLowerCase(name));
				}

				if (!pagination) {
					list = (List<AssetDisplayTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetDisplayTemplate>)QueryUtil.list(q,
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
	 * Returns the first asset display template in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display template
	 * @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	 */
	@Override
	public AssetDisplayTemplate findByG_LikeN_First(long groupId, String name,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException {
		AssetDisplayTemplate assetDisplayTemplate = fetchByG_LikeN_First(groupId,
				name, orderByComparator);

		if (assetDisplayTemplate != null) {
			return assetDisplayTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchDisplayTemplateException(msg.toString());
	}

	/**
	 * Returns the first asset display template in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display template, or <code>null</code> if a matching asset display template could not be found
	 */
	@Override
	public AssetDisplayTemplate fetchByG_LikeN_First(long groupId, String name,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		List<AssetDisplayTemplate> list = findByG_LikeN(groupId, name, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset display template in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display template
	 * @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	 */
	@Override
	public AssetDisplayTemplate findByG_LikeN_Last(long groupId, String name,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException {
		AssetDisplayTemplate assetDisplayTemplate = fetchByG_LikeN_Last(groupId,
				name, orderByComparator);

		if (assetDisplayTemplate != null) {
			return assetDisplayTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchDisplayTemplateException(msg.toString());
	}

	/**
	 * Returns the last asset display template in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display template, or <code>null</code> if a matching asset display template could not be found
	 */
	@Override
	public AssetDisplayTemplate fetchByG_LikeN_Last(long groupId, String name,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		int count = countByG_LikeN(groupId, name);

		if (count == 0) {
			return null;
		}

		List<AssetDisplayTemplate> list = findByG_LikeN(groupId, name,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset display templates before and after the current asset display template in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param assetDisplayTemplateId the primary key of the current asset display template
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset display template
	 * @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	 */
	@Override
	public AssetDisplayTemplate[] findByG_LikeN_PrevAndNext(
		long assetDisplayTemplateId, long groupId, String name,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException {
		AssetDisplayTemplate assetDisplayTemplate = findByPrimaryKey(assetDisplayTemplateId);

		Session session = null;

		try {
			session = openSession();

			AssetDisplayTemplate[] array = new AssetDisplayTemplateImpl[3];

			array[0] = getByG_LikeN_PrevAndNext(session, assetDisplayTemplate,
					groupId, name, orderByComparator, true);

			array[1] = assetDisplayTemplate;

			array[2] = getByG_LikeN_PrevAndNext(session, assetDisplayTemplate,
					groupId, name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetDisplayTemplate getByG_LikeN_PrevAndNext(Session session,
		AssetDisplayTemplate assetDisplayTemplate, long groupId, String name,
		OrderByComparator<AssetDisplayTemplate> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETDISPLAYTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
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
			query.append(AssetDisplayTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetDisplayTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetDisplayTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset display templates that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching asset display templates that the user has permission to view
	 */
	@Override
	public List<AssetDisplayTemplate> filterFindByG_LikeN(long groupId,
		String name) {
		return filterFindByG_LikeN(groupId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display templates that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @return the range of matching asset display templates that the user has permission to view
	 */
	@Override
	public List<AssetDisplayTemplate> filterFindByG_LikeN(long groupId,
		String name, int start, int end) {
		return filterFindByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display templates that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display templates that the user has permission to view
	 */
	@Override
	public List<AssetDisplayTemplate> filterFindByG_LikeN(long groupId,
		String name, int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeN(groupId, name, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AssetDisplayTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetDisplayTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetDisplayTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AssetDisplayTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AssetDisplayTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindName) {
				qPos.add(StringUtil.toLowerCase(name));
			}

			return (List<AssetDisplayTemplate>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset display templates before and after the current asset display template in the ordered set of asset display templates that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param assetDisplayTemplateId the primary key of the current asset display template
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset display template
	 * @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	 */
	@Override
	public AssetDisplayTemplate[] filterFindByG_LikeN_PrevAndNext(
		long assetDisplayTemplateId, long groupId, String name,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeN_PrevAndNext(assetDisplayTemplateId, groupId,
				name, orderByComparator);
		}

		AssetDisplayTemplate assetDisplayTemplate = findByPrimaryKey(assetDisplayTemplateId);

		Session session = null;

		try {
			session = openSession();

			AssetDisplayTemplate[] array = new AssetDisplayTemplateImpl[3];

			array[0] = filterGetByG_LikeN_PrevAndNext(session,
					assetDisplayTemplate, groupId, name, orderByComparator, true);

			array[1] = assetDisplayTemplate;

			array[2] = filterGetByG_LikeN_PrevAndNext(session,
					assetDisplayTemplate, groupId, name, orderByComparator,
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

	protected AssetDisplayTemplate filterGetByG_LikeN_PrevAndNext(
		Session session, AssetDisplayTemplate assetDisplayTemplate,
		long groupId, String name,
		OrderByComparator<AssetDisplayTemplate> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AssetDisplayTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetDisplayTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetDisplayTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AssetDisplayTemplateImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AssetDisplayTemplateImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetDisplayTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetDisplayTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset display templates where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	@Override
	public void removeByG_LikeN(long groupId, String name) {
		for (AssetDisplayTemplate assetDisplayTemplate : findByG_LikeN(
				groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetDisplayTemplate);
		}
	}

	/**
	 * Returns the number of asset display templates where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching asset display templates
	 */
	@Override
	public int countByG_LikeN(long groupId, String name) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKEN;

		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETDISPLAYTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
			}
			else if (name.equals("")) {
				query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(StringUtil.toLowerCase(name));
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

	/**
	 * Returns the number of asset display templates that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching asset display templates that the user has permission to view
	 */
	@Override
	public int filterCountByG_LikeN(long groupId, String name) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_LikeN(groupId, name);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_ASSETDISPLAYTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetDisplayTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindName) {
				qPos.add(StringUtil.toLowerCase(name));
			}

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

	private static final String _FINDER_COLUMN_G_LIKEN_GROUPID_2 = "assetDisplayTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_1 = "assetDisplayTemplate.name IS NULL";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_2 = "lower(assetDisplayTemplate.name) LIKE ?";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_3 = "(assetDisplayTemplate.name IS NULL OR assetDisplayTemplate.name LIKE '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C = new FinderPath(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayTemplateModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C = new FinderPath(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayTemplateModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			AssetDisplayTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			AssetDisplayTemplateModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AssetDisplayTemplateModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the asset display templates where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching asset display templates
	 */
	@Override
	public List<AssetDisplayTemplate> findByG_C(long groupId, long classNameId) {
		return findByG_C(groupId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display templates where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @return the range of matching asset display templates
	 */
	@Override
	public List<AssetDisplayTemplate> findByG_C(long groupId, long classNameId,
		int start, int end) {
		return findByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display templates where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display templates
	 */
	@Override
	public List<AssetDisplayTemplate> findByG_C(long groupId, long classNameId,
		int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return findByG_C(groupId, classNameId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the asset display templates where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset display templates
	 */
	@Override
	public List<AssetDisplayTemplate> findByG_C(long groupId, long classNameId,
		int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] { groupId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] {
					groupId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<AssetDisplayTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<AssetDisplayTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetDisplayTemplate assetDisplayTemplate : list) {
					if ((groupId != assetDisplayTemplate.getGroupId()) ||
							(classNameId != assetDisplayTemplate.getClassNameId())) {
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

			query.append(_SQL_SELECT_ASSETDISPLAYTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetDisplayTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<AssetDisplayTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetDisplayTemplate>)QueryUtil.list(q,
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
	 * Returns the first asset display template in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display template
	 * @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	 */
	@Override
	public AssetDisplayTemplate findByG_C_First(long groupId, long classNameId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException {
		AssetDisplayTemplate assetDisplayTemplate = fetchByG_C_First(groupId,
				classNameId, orderByComparator);

		if (assetDisplayTemplate != null) {
			return assetDisplayTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append("}");

		throw new NoSuchDisplayTemplateException(msg.toString());
	}

	/**
	 * Returns the first asset display template in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display template, or <code>null</code> if a matching asset display template could not be found
	 */
	@Override
	public AssetDisplayTemplate fetchByG_C_First(long groupId,
		long classNameId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		List<AssetDisplayTemplate> list = findByG_C(groupId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset display template in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display template
	 * @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	 */
	@Override
	public AssetDisplayTemplate findByG_C_Last(long groupId, long classNameId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException {
		AssetDisplayTemplate assetDisplayTemplate = fetchByG_C_Last(groupId,
				classNameId, orderByComparator);

		if (assetDisplayTemplate != null) {
			return assetDisplayTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append("}");

		throw new NoSuchDisplayTemplateException(msg.toString());
	}

	/**
	 * Returns the last asset display template in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display template, or <code>null</code> if a matching asset display template could not be found
	 */
	@Override
	public AssetDisplayTemplate fetchByG_C_Last(long groupId, long classNameId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		int count = countByG_C(groupId, classNameId);

		if (count == 0) {
			return null;
		}

		List<AssetDisplayTemplate> list = findByG_C(groupId, classNameId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset display templates before and after the current asset display template in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param assetDisplayTemplateId the primary key of the current asset display template
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset display template
	 * @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	 */
	@Override
	public AssetDisplayTemplate[] findByG_C_PrevAndNext(
		long assetDisplayTemplateId, long groupId, long classNameId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException {
		AssetDisplayTemplate assetDisplayTemplate = findByPrimaryKey(assetDisplayTemplateId);

		Session session = null;

		try {
			session = openSession();

			AssetDisplayTemplate[] array = new AssetDisplayTemplateImpl[3];

			array[0] = getByG_C_PrevAndNext(session, assetDisplayTemplate,
					groupId, classNameId, orderByComparator, true);

			array[1] = assetDisplayTemplate;

			array[2] = getByG_C_PrevAndNext(session, assetDisplayTemplate,
					groupId, classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetDisplayTemplate getByG_C_PrevAndNext(Session session,
		AssetDisplayTemplate assetDisplayTemplate, long groupId,
		long classNameId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETDISPLAYTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

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
			query.append(AssetDisplayTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetDisplayTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetDisplayTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset display templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching asset display templates that the user has permission to view
	 */
	@Override
	public List<AssetDisplayTemplate> filterFindByG_C(long groupId,
		long classNameId) {
		return filterFindByG_C(groupId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @return the range of matching asset display templates that the user has permission to view
	 */
	@Override
	public List<AssetDisplayTemplate> filterFindByG_C(long groupId,
		long classNameId, int start, int end) {
		return filterFindByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display templates that the user has permissions to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display templates that the user has permission to view
	 */
	@Override
	public List<AssetDisplayTemplate> filterFindByG_C(long groupId,
		long classNameId, int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C(groupId, classNameId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AssetDisplayTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetDisplayTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetDisplayTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AssetDisplayTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AssetDisplayTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			return (List<AssetDisplayTemplate>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset display templates before and after the current asset display template in the ordered set of asset display templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param assetDisplayTemplateId the primary key of the current asset display template
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset display template
	 * @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	 */
	@Override
	public AssetDisplayTemplate[] filterFindByG_C_PrevAndNext(
		long assetDisplayTemplateId, long groupId, long classNameId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_PrevAndNext(assetDisplayTemplateId, groupId,
				classNameId, orderByComparator);
		}

		AssetDisplayTemplate assetDisplayTemplate = findByPrimaryKey(assetDisplayTemplateId);

		Session session = null;

		try {
			session = openSession();

			AssetDisplayTemplate[] array = new AssetDisplayTemplateImpl[3];

			array[0] = filterGetByG_C_PrevAndNext(session,
					assetDisplayTemplate, groupId, classNameId,
					orderByComparator, true);

			array[1] = assetDisplayTemplate;

			array[2] = filterGetByG_C_PrevAndNext(session,
					assetDisplayTemplate, groupId, classNameId,
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

	protected AssetDisplayTemplate filterGetByG_C_PrevAndNext(Session session,
		AssetDisplayTemplate assetDisplayTemplate, long groupId,
		long classNameId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AssetDisplayTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetDisplayTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetDisplayTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AssetDisplayTemplateImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AssetDisplayTemplateImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetDisplayTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetDisplayTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset display templates where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByG_C(long groupId, long classNameId) {
		for (AssetDisplayTemplate assetDisplayTemplate : findByG_C(groupId,
				classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetDisplayTemplate);
		}
	}

	/**
	 * Returns the number of asset display templates where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching asset display templates
	 */
	@Override
	public int countByG_C(long groupId, long classNameId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C;

		Object[] finderArgs = new Object[] { groupId, classNameId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETDISPLAYTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

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
	 * Returns the number of asset display templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching asset display templates that the user has permission to view
	 */
	@Override
	public int filterCountByG_C(long groupId, long classNameId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C(groupId, classNameId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_ASSETDISPLAYTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetDisplayTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "assetDisplayTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CLASSNAMEID_2 = "assetDisplayTemplate.classNameId = ?";

	public AssetDisplayTemplatePersistenceImpl() {
		setModelClass(AssetDisplayTemplate.class);
	}

	/**
	 * Caches the asset display template in the entity cache if it is enabled.
	 *
	 * @param assetDisplayTemplate the asset display template
	 */
	@Override
	public void cacheResult(AssetDisplayTemplate assetDisplayTemplate) {
		entityCache.putResult(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayTemplateImpl.class,
			assetDisplayTemplate.getPrimaryKey(), assetDisplayTemplate);

		assetDisplayTemplate.resetOriginalValues();
	}

	/**
	 * Caches the asset display templates in the entity cache if it is enabled.
	 *
	 * @param assetDisplayTemplates the asset display templates
	 */
	@Override
	public void cacheResult(List<AssetDisplayTemplate> assetDisplayTemplates) {
		for (AssetDisplayTemplate assetDisplayTemplate : assetDisplayTemplates) {
			if (entityCache.getResult(
						AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
						AssetDisplayTemplateImpl.class,
						assetDisplayTemplate.getPrimaryKey()) == null) {
				cacheResult(assetDisplayTemplate);
			}
			else {
				assetDisplayTemplate.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset display templates.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetDisplayTemplateImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset display template.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetDisplayTemplate assetDisplayTemplate) {
		entityCache.removeResult(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayTemplateImpl.class, assetDisplayTemplate.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<AssetDisplayTemplate> assetDisplayTemplates) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetDisplayTemplate assetDisplayTemplate : assetDisplayTemplates) {
			entityCache.removeResult(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
				AssetDisplayTemplateImpl.class,
				assetDisplayTemplate.getPrimaryKey());
		}
	}

	/**
	 * Creates a new asset display template with the primary key. Does not add the asset display template to the database.
	 *
	 * @param assetDisplayTemplateId the primary key for the new asset display template
	 * @return the new asset display template
	 */
	@Override
	public AssetDisplayTemplate create(long assetDisplayTemplateId) {
		AssetDisplayTemplate assetDisplayTemplate = new AssetDisplayTemplateImpl();

		assetDisplayTemplate.setNew(true);
		assetDisplayTemplate.setPrimaryKey(assetDisplayTemplateId);

		assetDisplayTemplate.setCompanyId(companyProvider.getCompanyId());

		return assetDisplayTemplate;
	}

	/**
	 * Removes the asset display template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetDisplayTemplateId the primary key of the asset display template
	 * @return the asset display template that was removed
	 * @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	 */
	@Override
	public AssetDisplayTemplate remove(long assetDisplayTemplateId)
		throws NoSuchDisplayTemplateException {
		return remove((Serializable)assetDisplayTemplateId);
	}

	/**
	 * Removes the asset display template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset display template
	 * @return the asset display template that was removed
	 * @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	 */
	@Override
	public AssetDisplayTemplate remove(Serializable primaryKey)
		throws NoSuchDisplayTemplateException {
		Session session = null;

		try {
			session = openSession();

			AssetDisplayTemplate assetDisplayTemplate = (AssetDisplayTemplate)session.get(AssetDisplayTemplateImpl.class,
					primaryKey);

			if (assetDisplayTemplate == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDisplayTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetDisplayTemplate);
		}
		catch (NoSuchDisplayTemplateException nsee) {
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
	protected AssetDisplayTemplate removeImpl(
		AssetDisplayTemplate assetDisplayTemplate) {
		assetDisplayTemplate = toUnwrappedModel(assetDisplayTemplate);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetDisplayTemplate)) {
				assetDisplayTemplate = (AssetDisplayTemplate)session.get(AssetDisplayTemplateImpl.class,
						assetDisplayTemplate.getPrimaryKeyObj());
			}

			if (assetDisplayTemplate != null) {
				session.delete(assetDisplayTemplate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetDisplayTemplate != null) {
			clearCache(assetDisplayTemplate);
		}

		return assetDisplayTemplate;
	}

	@Override
	public AssetDisplayTemplate updateImpl(
		AssetDisplayTemplate assetDisplayTemplate) {
		assetDisplayTemplate = toUnwrappedModel(assetDisplayTemplate);

		boolean isNew = assetDisplayTemplate.isNew();

		AssetDisplayTemplateModelImpl assetDisplayTemplateModelImpl = (AssetDisplayTemplateModelImpl)assetDisplayTemplate;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (assetDisplayTemplate.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetDisplayTemplate.setCreateDate(now);
			}
			else {
				assetDisplayTemplate.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!assetDisplayTemplateModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetDisplayTemplate.setModifiedDate(now);
			}
			else {
				assetDisplayTemplate.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (assetDisplayTemplate.isNew()) {
				session.save(assetDisplayTemplate);

				assetDisplayTemplate.setNew(false);
			}
			else {
				assetDisplayTemplate = (AssetDisplayTemplate)session.merge(assetDisplayTemplate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AssetDisplayTemplateModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					assetDisplayTemplateModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					assetDisplayTemplateModelImpl.getGroupId(),
					assetDisplayTemplateModelImpl.getClassNameId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((assetDisplayTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetDisplayTemplateModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { assetDisplayTemplateModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((assetDisplayTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetDisplayTemplateModelImpl.getOriginalGroupId(),
						assetDisplayTemplateModelImpl.getOriginalClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);

				args = new Object[] {
						assetDisplayTemplateModelImpl.getGroupId(),
						assetDisplayTemplateModelImpl.getClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);
			}
		}

		entityCache.putResult(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayTemplateImpl.class,
			assetDisplayTemplate.getPrimaryKey(), assetDisplayTemplate, false);

		assetDisplayTemplate.resetOriginalValues();

		return assetDisplayTemplate;
	}

	protected AssetDisplayTemplate toUnwrappedModel(
		AssetDisplayTemplate assetDisplayTemplate) {
		if (assetDisplayTemplate instanceof AssetDisplayTemplateImpl) {
			return assetDisplayTemplate;
		}

		AssetDisplayTemplateImpl assetDisplayTemplateImpl = new AssetDisplayTemplateImpl();

		assetDisplayTemplateImpl.setNew(assetDisplayTemplate.isNew());
		assetDisplayTemplateImpl.setPrimaryKey(assetDisplayTemplate.getPrimaryKey());

		assetDisplayTemplateImpl.setAssetDisplayTemplateId(assetDisplayTemplate.getAssetDisplayTemplateId());
		assetDisplayTemplateImpl.setGroupId(assetDisplayTemplate.getGroupId());
		assetDisplayTemplateImpl.setCompanyId(assetDisplayTemplate.getCompanyId());
		assetDisplayTemplateImpl.setUserId(assetDisplayTemplate.getUserId());
		assetDisplayTemplateImpl.setUserName(assetDisplayTemplate.getUserName());
		assetDisplayTemplateImpl.setCreateDate(assetDisplayTemplate.getCreateDate());
		assetDisplayTemplateImpl.setModifiedDate(assetDisplayTemplate.getModifiedDate());
		assetDisplayTemplateImpl.setName(assetDisplayTemplate.getName());
		assetDisplayTemplateImpl.setClassNameId(assetDisplayTemplate.getClassNameId());
		assetDisplayTemplateImpl.setDDMTemplateId(assetDisplayTemplate.getDDMTemplateId());
		assetDisplayTemplateImpl.setMain(assetDisplayTemplate.isMain());

		return assetDisplayTemplateImpl;
	}

	/**
	 * Returns the asset display template with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset display template
	 * @return the asset display template
	 * @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	 */
	@Override
	public AssetDisplayTemplate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDisplayTemplateException {
		AssetDisplayTemplate assetDisplayTemplate = fetchByPrimaryKey(primaryKey);

		if (assetDisplayTemplate == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDisplayTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return assetDisplayTemplate;
	}

	/**
	 * Returns the asset display template with the primary key or throws a {@link NoSuchDisplayTemplateException} if it could not be found.
	 *
	 * @param assetDisplayTemplateId the primary key of the asset display template
	 * @return the asset display template
	 * @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	 */
	@Override
	public AssetDisplayTemplate findByPrimaryKey(long assetDisplayTemplateId)
		throws NoSuchDisplayTemplateException {
		return findByPrimaryKey((Serializable)assetDisplayTemplateId);
	}

	/**
	 * Returns the asset display template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset display template
	 * @return the asset display template, or <code>null</code> if a asset display template with the primary key could not be found
	 */
	@Override
	public AssetDisplayTemplate fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
				AssetDisplayTemplateImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AssetDisplayTemplate assetDisplayTemplate = (AssetDisplayTemplate)serializable;

		if (assetDisplayTemplate == null) {
			Session session = null;

			try {
				session = openSession();

				assetDisplayTemplate = (AssetDisplayTemplate)session.get(AssetDisplayTemplateImpl.class,
						primaryKey);

				if (assetDisplayTemplate != null) {
					cacheResult(assetDisplayTemplate);
				}
				else {
					entityCache.putResult(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
						AssetDisplayTemplateImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
					AssetDisplayTemplateImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return assetDisplayTemplate;
	}

	/**
	 * Returns the asset display template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetDisplayTemplateId the primary key of the asset display template
	 * @return the asset display template, or <code>null</code> if a asset display template with the primary key could not be found
	 */
	@Override
	public AssetDisplayTemplate fetchByPrimaryKey(long assetDisplayTemplateId) {
		return fetchByPrimaryKey((Serializable)assetDisplayTemplateId);
	}

	@Override
	public Map<Serializable, AssetDisplayTemplate> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetDisplayTemplate> map = new HashMap<Serializable, AssetDisplayTemplate>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetDisplayTemplate assetDisplayTemplate = fetchByPrimaryKey(primaryKey);

			if (assetDisplayTemplate != null) {
				map.put(primaryKey, assetDisplayTemplate);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
					AssetDisplayTemplateImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (AssetDisplayTemplate)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ASSETDISPLAYTEMPLATE_WHERE_PKS_IN);

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

			for (AssetDisplayTemplate assetDisplayTemplate : (List<AssetDisplayTemplate>)q.list()) {
				map.put(assetDisplayTemplate.getPrimaryKeyObj(),
					assetDisplayTemplate);

				cacheResult(assetDisplayTemplate);

				uncachedPrimaryKeys.remove(assetDisplayTemplate.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(AssetDisplayTemplateModelImpl.ENTITY_CACHE_ENABLED,
					AssetDisplayTemplateImpl.class, primaryKey, nullModel);
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
	 * Returns all the asset display templates.
	 *
	 * @return the asset display templates
	 */
	@Override
	public List<AssetDisplayTemplate> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @return the range of asset display templates
	 */
	@Override
	public List<AssetDisplayTemplate> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset display templates
	 */
	@Override
	public List<AssetDisplayTemplate> findAll(int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset display templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset display templates
	 * @param end the upper bound of the range of asset display templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of asset display templates
	 */
	@Override
	public List<AssetDisplayTemplate> findAll(int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator,
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

		List<AssetDisplayTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<AssetDisplayTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ASSETDISPLAYTEMPLATE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETDISPLAYTEMPLATE;

				if (pagination) {
					sql = sql.concat(AssetDisplayTemplateModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AssetDisplayTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetDisplayTemplate>)QueryUtil.list(q,
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
	 * Removes all the asset display templates from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetDisplayTemplate assetDisplayTemplate : findAll()) {
			remove(assetDisplayTemplate);
		}
	}

	/**
	 * Returns the number of asset display templates.
	 *
	 * @return the number of asset display templates
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETDISPLAYTEMPLATE);

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
		return AssetDisplayTemplateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset display template persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(AssetDisplayTemplateImpl.class.getName());
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
	private static final String _SQL_SELECT_ASSETDISPLAYTEMPLATE = "SELECT assetDisplayTemplate FROM AssetDisplayTemplate assetDisplayTemplate";
	private static final String _SQL_SELECT_ASSETDISPLAYTEMPLATE_WHERE_PKS_IN = "SELECT assetDisplayTemplate FROM AssetDisplayTemplate assetDisplayTemplate WHERE assetDisplayTemplateId IN (";
	private static final String _SQL_SELECT_ASSETDISPLAYTEMPLATE_WHERE = "SELECT assetDisplayTemplate FROM AssetDisplayTemplate assetDisplayTemplate WHERE ";
	private static final String _SQL_COUNT_ASSETDISPLAYTEMPLATE = "SELECT COUNT(assetDisplayTemplate) FROM AssetDisplayTemplate assetDisplayTemplate";
	private static final String _SQL_COUNT_ASSETDISPLAYTEMPLATE_WHERE = "SELECT COUNT(assetDisplayTemplate) FROM AssetDisplayTemplate assetDisplayTemplate WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "assetDisplayTemplate.assetDisplayTemplateId";
	private static final String _FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_WHERE = "SELECT DISTINCT {assetDisplayTemplate.*} FROM AssetDisplayTemplate assetDisplayTemplate WHERE ";
	private static final String _FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {AssetDisplayTemplate.*} FROM (SELECT DISTINCT assetDisplayTemplate.assetDisplayTemplateId FROM AssetDisplayTemplate assetDisplayTemplate WHERE ";
	private static final String _FILTER_SQL_SELECT_ASSETDISPLAYTEMPLATE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN AssetDisplayTemplate ON TEMP_TABLE.assetDisplayTemplateId = AssetDisplayTemplate.assetDisplayTemplateId";
	private static final String _FILTER_SQL_COUNT_ASSETDISPLAYTEMPLATE_WHERE = "SELECT COUNT(DISTINCT assetDisplayTemplate.assetDisplayTemplateId) AS COUNT_VALUE FROM AssetDisplayTemplate assetDisplayTemplate WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "assetDisplayTemplate";
	private static final String _FILTER_ENTITY_TABLE = "AssetDisplayTemplate";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetDisplayTemplate.";
	private static final String _ORDER_BY_ENTITY_TABLE = "AssetDisplayTemplate.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetDisplayTemplate exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetDisplayTemplate exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(AssetDisplayTemplatePersistenceImpl.class);
}