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

import com.liferay.layout.page.template.exception.NoSuchPageTemplateFolderException;
import com.liferay.layout.page.template.model.LayoutPageTemplateFolder;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateFolderImpl;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateFolderModelImpl;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateFolderPersistence;

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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
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
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the layout page template folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateFolderPersistence
 * @see com.liferay.layout.page.template.service.persistence.LayoutPageTemplateFolderUtil
 * @generated
 */
@ProviderType
public class LayoutPageTemplateFolderPersistenceImpl extends BasePersistenceImpl<LayoutPageTemplateFolder>
	implements LayoutPageTemplateFolderPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LayoutPageTemplateFolderUtil} to access the layout page template folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutPageTemplateFolderImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFolderModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFolderModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFolderModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFolderModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			LayoutPageTemplateFolderModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateFolderModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the layout page template folders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout page template folders
	 */
	@Override
	public List<LayoutPageTemplateFolder> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template folders
	 * @param end the upper bound of the range of layout page template folders (not inclusive)
	 * @return the range of matching layout page template folders
	 */
	@Override
	public List<LayoutPageTemplateFolder> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template folders
	 * @param end the upper bound of the range of layout page template folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template folders
	 */
	@Override
	public List<LayoutPageTemplateFolder> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template folders
	 * @param end the upper bound of the range of layout page template folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template folders
	 */
	@Override
	public List<LayoutPageTemplateFolder> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator,
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

		List<LayoutPageTemplateFolder> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateFolder>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateFolder layoutPageTemplateFolder : list) {
					if ((groupId != layoutPageTemplateFolder.getGroupId())) {
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

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<LayoutPageTemplateFolder>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateFolder>)QueryUtil.list(q,
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
	 * Returns the first layout page template folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template folder
	 * @throws NoSuchPageTemplateFolderException if a matching layout page template folder could not be found
	 */
	@Override
	public LayoutPageTemplateFolder findByGroupId_First(long groupId,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws NoSuchPageTemplateFolderException {
		LayoutPageTemplateFolder layoutPageTemplateFolder = fetchByGroupId_First(groupId,
				orderByComparator);

		if (layoutPageTemplateFolder != null) {
			return layoutPageTemplateFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageTemplateFolderException(msg.toString());
	}

	/**
	 * Returns the first layout page template folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template folder, or <code>null</code> if a matching layout page template folder could not be found
	 */
	@Override
	public LayoutPageTemplateFolder fetchByGroupId_First(long groupId,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator) {
		List<LayoutPageTemplateFolder> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template folder
	 * @throws NoSuchPageTemplateFolderException if a matching layout page template folder could not be found
	 */
	@Override
	public LayoutPageTemplateFolder findByGroupId_Last(long groupId,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws NoSuchPageTemplateFolderException {
		LayoutPageTemplateFolder layoutPageTemplateFolder = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (layoutPageTemplateFolder != null) {
			return layoutPageTemplateFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageTemplateFolderException(msg.toString());
	}

	/**
	 * Returns the last layout page template folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template folder, or <code>null</code> if a matching layout page template folder could not be found
	 */
	@Override
	public LayoutPageTemplateFolder fetchByGroupId_Last(long groupId,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateFolder> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template folders before and after the current layout page template folder in the ordered set where groupId = &#63;.
	 *
	 * @param layoutPageTemplateFolderId the primary key of the current layout page template folder
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template folder
	 * @throws NoSuchPageTemplateFolderException if a layout page template folder with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFolder[] findByGroupId_PrevAndNext(
		long layoutPageTemplateFolderId, long groupId,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws NoSuchPageTemplateFolderException {
		LayoutPageTemplateFolder layoutPageTemplateFolder = findByPrimaryKey(layoutPageTemplateFolderId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateFolder[] array = new LayoutPageTemplateFolderImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					layoutPageTemplateFolder, groupId, orderByComparator, true);

			array[1] = layoutPageTemplateFolder;

			array[2] = getByGroupId_PrevAndNext(session,
					layoutPageTemplateFolder, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateFolder getByGroupId_PrevAndNext(
		Session session, LayoutPageTemplateFolder layoutPageTemplateFolder,
		long groupId,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_WHERE);

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
			query.append(LayoutPageTemplateFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout page template folders that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateFolder> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template folders that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template folders
	 * @param end the upper bound of the range of layout page template folders (not inclusive)
	 * @return the range of matching layout page template folders that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateFolder> filterFindByGroupId(long groupId,
		int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template folders that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template folders
	 * @param end the upper bound of the range of layout page template folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template folders that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateFolder> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPageTemplateFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					LayoutPageTemplateFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					LayoutPageTemplateFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<LayoutPageTemplateFolder>)QueryUtil.list(q,
				getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the layout page template folders before and after the current layout page template folder in the ordered set of layout page template folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param layoutPageTemplateFolderId the primary key of the current layout page template folder
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template folder
	 * @throws NoSuchPageTemplateFolderException if a layout page template folder with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFolder[] filterFindByGroupId_PrevAndNext(
		long layoutPageTemplateFolderId, long groupId,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws NoSuchPageTemplateFolderException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(layoutPageTemplateFolderId,
				groupId, orderByComparator);
		}

		LayoutPageTemplateFolder layoutPageTemplateFolder = findByPrimaryKey(layoutPageTemplateFolderId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateFolder[] array = new LayoutPageTemplateFolderImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					layoutPageTemplateFolder, groupId, orderByComparator, true);

			array[1] = layoutPageTemplateFolder;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					layoutPageTemplateFolder, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateFolder filterGetByGroupId_PrevAndNext(
		Session session, LayoutPageTemplateFolder layoutPageTemplateFolder,
		long groupId,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPageTemplateFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutPageTemplateFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutPageTemplateFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template folders where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (LayoutPageTemplateFolder layoutPageTemplateFolder : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateFolder);
		}
	}

	/**
	 * Returns the number of layout page template folders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout page template folders
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEFOLDER_WHERE);

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
	 * Returns the number of layout page template folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout page template folders that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEFOLDER_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateFolder.class.getName(),
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "layoutPageTemplateFolder.groupId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFolderModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateFolderImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() },
			LayoutPageTemplateFolderModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateFolderModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the layout page template folder where groupId = &#63; and name = &#63; or throws a {@link NoSuchPageTemplateFolderException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template folder
	 * @throws NoSuchPageTemplateFolderException if a matching layout page template folder could not be found
	 */
	@Override
	public LayoutPageTemplateFolder findByG_N(long groupId, String name)
		throws NoSuchPageTemplateFolderException {
		LayoutPageTemplateFolder layoutPageTemplateFolder = fetchByG_N(groupId,
				name);

		if (layoutPageTemplateFolder == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchPageTemplateFolderException(msg.toString());
		}

		return layoutPageTemplateFolder;
	}

	/**
	 * Returns the layout page template folder where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template folder, or <code>null</code> if a matching layout page template folder could not be found
	 */
	@Override
	public LayoutPageTemplateFolder fetchByG_N(long groupId, String name) {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Returns the layout page template folder where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout page template folder, or <code>null</code> if a matching layout page template folder could not be found
	 */
	@Override
	public LayoutPageTemplateFolder fetchByG_N(long groupId, String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_N,
					finderArgs, this);
		}

		if (result instanceof LayoutPageTemplateFolder) {
			LayoutPageTemplateFolder layoutPageTemplateFolder = (LayoutPageTemplateFolder)result;

			if ((groupId != layoutPageTemplateFolder.getGroupId()) ||
					!Objects.equals(name, layoutPageTemplateFolder.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
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

				List<LayoutPageTemplateFolder> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, finderArgs,
						list);
				}
				else {
					LayoutPageTemplateFolder layoutPageTemplateFolder = list.get(0);

					result = layoutPageTemplateFolder;

					cacheResult(layoutPageTemplateFolder);

					if ((layoutPageTemplateFolder.getGroupId() != groupId) ||
							(layoutPageTemplateFolder.getName() == null) ||
							!layoutPageTemplateFolder.getName().equals(name)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_N,
							finderArgs, layoutPageTemplateFolder);
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
			return (LayoutPageTemplateFolder)result;
		}
	}

	/**
	 * Removes the layout page template folder where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the layout page template folder that was removed
	 */
	@Override
	public LayoutPageTemplateFolder removeByG_N(long groupId, String name)
		throws NoSuchPageTemplateFolderException {
		LayoutPageTemplateFolder layoutPageTemplateFolder = findByG_N(groupId,
				name);

		return remove(layoutPageTemplateFolder);
	}

	/**
	 * Returns the number of layout page template folders where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching layout page template folders
	 */
	@Override
	public int countByG_N(long groupId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_N;

		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
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

	private static final String _FINDER_COLUMN_G_N_GROUPID_2 = "layoutPageTemplateFolder.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_NAME_1 = "layoutPageTemplateFolder.name IS NULL";
	private static final String _FINDER_COLUMN_G_N_NAME_2 = "layoutPageTemplateFolder.name = ?";
	private static final String _FINDER_COLUMN_G_N_NAME_3 = "(layoutPageTemplateFolder.name IS NULL OR layoutPageTemplateFolder.name = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKEN = new FinderPath(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFolderModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKEN = new FinderPath(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_LikeN",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the layout page template folders where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template folders
	 */
	@Override
	public List<LayoutPageTemplateFolder> findByG_LikeN(long groupId,
		String name) {
		return findByG_LikeN(groupId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template folders where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template folders
	 * @param end the upper bound of the range of layout page template folders (not inclusive)
	 * @return the range of matching layout page template folders
	 */
	@Override
	public List<LayoutPageTemplateFolder> findByG_LikeN(long groupId,
		String name, int start, int end) {
		return findByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template folders where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template folders
	 * @param end the upper bound of the range of layout page template folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template folders
	 */
	@Override
	public List<LayoutPageTemplateFolder> findByG_LikeN(long groupId,
		String name, int start, int end,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator) {
		return findByG_LikeN(groupId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template folders where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template folders
	 * @param end the upper bound of the range of layout page template folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template folders
	 */
	@Override
	public List<LayoutPageTemplateFolder> findByG_LikeN(long groupId,
		String name, int start, int end,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKEN;
		finderArgs = new Object[] { groupId, name, start, end, orderByComparator };

		List<LayoutPageTemplateFolder> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateFolder>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateFolder layoutPageTemplateFolder : list) {
					if ((groupId != layoutPageTemplateFolder.getGroupId()) ||
							!StringUtil.wildcardMatches(
								layoutPageTemplateFolder.getName(), name,
								CharPool.UNDERLINE, CharPool.PERCENT,
								CharPool.BACK_SLASH, false)) {
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

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
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
				query.append(LayoutPageTemplateFolderModelImpl.ORDER_BY_JPQL);
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
					list = (List<LayoutPageTemplateFolder>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateFolder>)QueryUtil.list(q,
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
	 * Returns the first layout page template folder in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template folder
	 * @throws NoSuchPageTemplateFolderException if a matching layout page template folder could not be found
	 */
	@Override
	public LayoutPageTemplateFolder findByG_LikeN_First(long groupId,
		String name,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws NoSuchPageTemplateFolderException {
		LayoutPageTemplateFolder layoutPageTemplateFolder = fetchByG_LikeN_First(groupId,
				name, orderByComparator);

		if (layoutPageTemplateFolder != null) {
			return layoutPageTemplateFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageTemplateFolderException(msg.toString());
	}

	/**
	 * Returns the first layout page template folder in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template folder, or <code>null</code> if a matching layout page template folder could not be found
	 */
	@Override
	public LayoutPageTemplateFolder fetchByG_LikeN_First(long groupId,
		String name,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator) {
		List<LayoutPageTemplateFolder> list = findByG_LikeN(groupId, name, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template folder in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template folder
	 * @throws NoSuchPageTemplateFolderException if a matching layout page template folder could not be found
	 */
	@Override
	public LayoutPageTemplateFolder findByG_LikeN_Last(long groupId,
		String name,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws NoSuchPageTemplateFolderException {
		LayoutPageTemplateFolder layoutPageTemplateFolder = fetchByG_LikeN_Last(groupId,
				name, orderByComparator);

		if (layoutPageTemplateFolder != null) {
			return layoutPageTemplateFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageTemplateFolderException(msg.toString());
	}

	/**
	 * Returns the last layout page template folder in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template folder, or <code>null</code> if a matching layout page template folder could not be found
	 */
	@Override
	public LayoutPageTemplateFolder fetchByG_LikeN_Last(long groupId,
		String name,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator) {
		int count = countByG_LikeN(groupId, name);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateFolder> list = findByG_LikeN(groupId, name,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template folders before and after the current layout page template folder in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param layoutPageTemplateFolderId the primary key of the current layout page template folder
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template folder
	 * @throws NoSuchPageTemplateFolderException if a layout page template folder with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFolder[] findByG_LikeN_PrevAndNext(
		long layoutPageTemplateFolderId, long groupId, String name,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws NoSuchPageTemplateFolderException {
		LayoutPageTemplateFolder layoutPageTemplateFolder = findByPrimaryKey(layoutPageTemplateFolderId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateFolder[] array = new LayoutPageTemplateFolderImpl[3];

			array[0] = getByG_LikeN_PrevAndNext(session,
					layoutPageTemplateFolder, groupId, name, orderByComparator,
					true);

			array[1] = layoutPageTemplateFolder;

			array[2] = getByG_LikeN_PrevAndNext(session,
					layoutPageTemplateFolder, groupId, name, orderByComparator,
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

	protected LayoutPageTemplateFolder getByG_LikeN_PrevAndNext(
		Session session, LayoutPageTemplateFolder layoutPageTemplateFolder,
		long groupId, String name,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
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
			query.append(LayoutPageTemplateFolderModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template folders that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template folders that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateFolder> filterFindByG_LikeN(long groupId,
		String name) {
		return filterFindByG_LikeN(groupId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template folders that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template folders
	 * @param end the upper bound of the range of layout page template folders (not inclusive)
	 * @return the range of matching layout page template folders that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateFolder> filterFindByG_LikeN(long groupId,
		String name, int start, int end) {
		return filterFindByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template folders that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template folders
	 * @param end the upper bound of the range of layout page template folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template folders that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateFolder> filterFindByG_LikeN(long groupId,
		String name, int start, int end,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPageTemplateFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					LayoutPageTemplateFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					LayoutPageTemplateFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindName) {
				qPos.add(StringUtil.toLowerCase(name));
			}

			return (List<LayoutPageTemplateFolder>)QueryUtil.list(q,
				getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the layout page template folders before and after the current layout page template folder in the ordered set of layout page template folders that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param layoutPageTemplateFolderId the primary key of the current layout page template folder
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template folder
	 * @throws NoSuchPageTemplateFolderException if a layout page template folder with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFolder[] filterFindByG_LikeN_PrevAndNext(
		long layoutPageTemplateFolderId, long groupId, String name,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws NoSuchPageTemplateFolderException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeN_PrevAndNext(layoutPageTemplateFolderId,
				groupId, name, orderByComparator);
		}

		LayoutPageTemplateFolder layoutPageTemplateFolder = findByPrimaryKey(layoutPageTemplateFolderId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateFolder[] array = new LayoutPageTemplateFolderImpl[3];

			array[0] = filterGetByG_LikeN_PrevAndNext(session,
					layoutPageTemplateFolder, groupId, name, orderByComparator,
					true);

			array[1] = layoutPageTemplateFolder;

			array[2] = filterGetByG_LikeN_PrevAndNext(session,
					layoutPageTemplateFolder, groupId, name, orderByComparator,
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

	protected LayoutPageTemplateFolder filterGetByG_LikeN_PrevAndNext(
		Session session, LayoutPageTemplateFolder layoutPageTemplateFolder,
		long groupId, String name,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPageTemplateFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutPageTemplateFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutPageTemplateFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template folders where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	@Override
	public void removeByG_LikeN(long groupId, String name) {
		for (LayoutPageTemplateFolder layoutPageTemplateFolder : findByG_LikeN(
				groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateFolder);
		}
	}

	/**
	 * Returns the number of layout page template folders where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching layout page template folders
	 */
	@Override
	public int countByG_LikeN(long groupId, String name) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKEN;

		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
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
	 * Returns the number of layout page template folders that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching layout page template folders that the user has permission to view
	 */
	@Override
	public int filterCountByG_LikeN(long groupId, String name) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_LikeN(groupId, name);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateFolder.class.getName(),
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

	private static final String _FINDER_COLUMN_G_LIKEN_GROUPID_2 = "layoutPageTemplateFolder.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_1 = "layoutPageTemplateFolder.name IS NULL";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_2 = "lower(layoutPageTemplateFolder.name) LIKE ?";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_3 = "(layoutPageTemplateFolder.name IS NULL OR layoutPageTemplateFolder.name LIKE '')";

	public LayoutPageTemplateFolderPersistenceImpl() {
		setModelClass(LayoutPageTemplateFolder.class);
	}

	/**
	 * Caches the layout page template folder in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateFolder the layout page template folder
	 */
	@Override
	public void cacheResult(LayoutPageTemplateFolder layoutPageTemplateFolder) {
		entityCache.putResult(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFolderImpl.class,
			layoutPageTemplateFolder.getPrimaryKey(), layoutPageTemplateFolder);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				layoutPageTemplateFolder.getGroupId(),
				layoutPageTemplateFolder.getName()
			}, layoutPageTemplateFolder);

		layoutPageTemplateFolder.resetOriginalValues();
	}

	/**
	 * Caches the layout page template folders in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateFolders the layout page template folders
	 */
	@Override
	public void cacheResult(
		List<LayoutPageTemplateFolder> layoutPageTemplateFolders) {
		for (LayoutPageTemplateFolder layoutPageTemplateFolder : layoutPageTemplateFolders) {
			if (entityCache.getResult(
						LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
						LayoutPageTemplateFolderImpl.class,
						layoutPageTemplateFolder.getPrimaryKey()) == null) {
				cacheResult(layoutPageTemplateFolder);
			}
			else {
				layoutPageTemplateFolder.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout page template folders.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutPageTemplateFolderImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout page template folder.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutPageTemplateFolder layoutPageTemplateFolder) {
		entityCache.removeResult(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFolderImpl.class,
			layoutPageTemplateFolder.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LayoutPageTemplateFolderModelImpl)layoutPageTemplateFolder,
			true);
	}

	@Override
	public void clearCache(
		List<LayoutPageTemplateFolder> layoutPageTemplateFolders) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutPageTemplateFolder layoutPageTemplateFolder : layoutPageTemplateFolders) {
			entityCache.removeResult(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateFolderImpl.class,
				layoutPageTemplateFolder.getPrimaryKey());

			clearUniqueFindersCache((LayoutPageTemplateFolderModelImpl)layoutPageTemplateFolder,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutPageTemplateFolderModelImpl layoutPageTemplateFolderModelImpl) {
		Object[] args = new Object[] {
				layoutPageTemplateFolderModelImpl.getGroupId(),
				layoutPageTemplateFolderModelImpl.getName()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_N, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, args,
			layoutPageTemplateFolderModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LayoutPageTemplateFolderModelImpl layoutPageTemplateFolderModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					layoutPageTemplateFolderModelImpl.getGroupId(),
					layoutPageTemplateFolderModelImpl.getName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, args);
		}

		if ((layoutPageTemplateFolderModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_N.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					layoutPageTemplateFolderModelImpl.getOriginalGroupId(),
					layoutPageTemplateFolderModelImpl.getOriginalName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, args);
		}
	}

	/**
	 * Creates a new layout page template folder with the primary key. Does not add the layout page template folder to the database.
	 *
	 * @param layoutPageTemplateFolderId the primary key for the new layout page template folder
	 * @return the new layout page template folder
	 */
	@Override
	public LayoutPageTemplateFolder create(long layoutPageTemplateFolderId) {
		LayoutPageTemplateFolder layoutPageTemplateFolder = new LayoutPageTemplateFolderImpl();

		layoutPageTemplateFolder.setNew(true);
		layoutPageTemplateFolder.setPrimaryKey(layoutPageTemplateFolderId);

		layoutPageTemplateFolder.setCompanyId(companyProvider.getCompanyId());

		return layoutPageTemplateFolder;
	}

	/**
	 * Removes the layout page template folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateFolderId the primary key of the layout page template folder
	 * @return the layout page template folder that was removed
	 * @throws NoSuchPageTemplateFolderException if a layout page template folder with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFolder remove(long layoutPageTemplateFolderId)
		throws NoSuchPageTemplateFolderException {
		return remove((Serializable)layoutPageTemplateFolderId);
	}

	/**
	 * Removes the layout page template folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout page template folder
	 * @return the layout page template folder that was removed
	 * @throws NoSuchPageTemplateFolderException if a layout page template folder with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFolder remove(Serializable primaryKey)
		throws NoSuchPageTemplateFolderException {
		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateFolder layoutPageTemplateFolder = (LayoutPageTemplateFolder)session.get(LayoutPageTemplateFolderImpl.class,
					primaryKey);

			if (layoutPageTemplateFolder == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPageTemplateFolderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(layoutPageTemplateFolder);
		}
		catch (NoSuchPageTemplateFolderException nsee) {
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
	protected LayoutPageTemplateFolder removeImpl(
		LayoutPageTemplateFolder layoutPageTemplateFolder) {
		layoutPageTemplateFolder = toUnwrappedModel(layoutPageTemplateFolder);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutPageTemplateFolder)) {
				layoutPageTemplateFolder = (LayoutPageTemplateFolder)session.get(LayoutPageTemplateFolderImpl.class,
						layoutPageTemplateFolder.getPrimaryKeyObj());
			}

			if (layoutPageTemplateFolder != null) {
				session.delete(layoutPageTemplateFolder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutPageTemplateFolder != null) {
			clearCache(layoutPageTemplateFolder);
		}

		return layoutPageTemplateFolder;
	}

	@Override
	public LayoutPageTemplateFolder updateImpl(
		LayoutPageTemplateFolder layoutPageTemplateFolder) {
		layoutPageTemplateFolder = toUnwrappedModel(layoutPageTemplateFolder);

		boolean isNew = layoutPageTemplateFolder.isNew();

		LayoutPageTemplateFolderModelImpl layoutPageTemplateFolderModelImpl = (LayoutPageTemplateFolderModelImpl)layoutPageTemplateFolder;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutPageTemplateFolder.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutPageTemplateFolder.setCreateDate(now);
			}
			else {
				layoutPageTemplateFolder.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!layoutPageTemplateFolderModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutPageTemplateFolder.setModifiedDate(now);
			}
			else {
				layoutPageTemplateFolder.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutPageTemplateFolder.isNew()) {
				session.save(layoutPageTemplateFolder);

				layoutPageTemplateFolder.setNew(false);
			}
			else {
				layoutPageTemplateFolder = (LayoutPageTemplateFolder)session.merge(layoutPageTemplateFolder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LayoutPageTemplateFolderModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					layoutPageTemplateFolderModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((layoutPageTemplateFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateFolderModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						layoutPageTemplateFolderModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		entityCache.putResult(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateFolderImpl.class,
			layoutPageTemplateFolder.getPrimaryKey(), layoutPageTemplateFolder,
			false);

		clearUniqueFindersCache(layoutPageTemplateFolderModelImpl, false);
		cacheUniqueFindersCache(layoutPageTemplateFolderModelImpl);

		layoutPageTemplateFolder.resetOriginalValues();

		return layoutPageTemplateFolder;
	}

	protected LayoutPageTemplateFolder toUnwrappedModel(
		LayoutPageTemplateFolder layoutPageTemplateFolder) {
		if (layoutPageTemplateFolder instanceof LayoutPageTemplateFolderImpl) {
			return layoutPageTemplateFolder;
		}

		LayoutPageTemplateFolderImpl layoutPageTemplateFolderImpl = new LayoutPageTemplateFolderImpl();

		layoutPageTemplateFolderImpl.setNew(layoutPageTemplateFolder.isNew());
		layoutPageTemplateFolderImpl.setPrimaryKey(layoutPageTemplateFolder.getPrimaryKey());

		layoutPageTemplateFolderImpl.setLayoutPageTemplateFolderId(layoutPageTemplateFolder.getLayoutPageTemplateFolderId());
		layoutPageTemplateFolderImpl.setGroupId(layoutPageTemplateFolder.getGroupId());
		layoutPageTemplateFolderImpl.setCompanyId(layoutPageTemplateFolder.getCompanyId());
		layoutPageTemplateFolderImpl.setUserId(layoutPageTemplateFolder.getUserId());
		layoutPageTemplateFolderImpl.setUserName(layoutPageTemplateFolder.getUserName());
		layoutPageTemplateFolderImpl.setCreateDate(layoutPageTemplateFolder.getCreateDate());
		layoutPageTemplateFolderImpl.setModifiedDate(layoutPageTemplateFolder.getModifiedDate());
		layoutPageTemplateFolderImpl.setName(layoutPageTemplateFolder.getName());
		layoutPageTemplateFolderImpl.setDescription(layoutPageTemplateFolder.getDescription());

		return layoutPageTemplateFolderImpl;
	}

	/**
	 * Returns the layout page template folder with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout page template folder
	 * @return the layout page template folder
	 * @throws NoSuchPageTemplateFolderException if a layout page template folder with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFolder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPageTemplateFolderException {
		LayoutPageTemplateFolder layoutPageTemplateFolder = fetchByPrimaryKey(primaryKey);

		if (layoutPageTemplateFolder == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPageTemplateFolderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return layoutPageTemplateFolder;
	}

	/**
	 * Returns the layout page template folder with the primary key or throws a {@link NoSuchPageTemplateFolderException} if it could not be found.
	 *
	 * @param layoutPageTemplateFolderId the primary key of the layout page template folder
	 * @return the layout page template folder
	 * @throws NoSuchPageTemplateFolderException if a layout page template folder with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFolder findByPrimaryKey(
		long layoutPageTemplateFolderId)
		throws NoSuchPageTemplateFolderException {
		return findByPrimaryKey((Serializable)layoutPageTemplateFolderId);
	}

	/**
	 * Returns the layout page template folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout page template folder
	 * @return the layout page template folder, or <code>null</code> if a layout page template folder with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFolder fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateFolderImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LayoutPageTemplateFolder layoutPageTemplateFolder = (LayoutPageTemplateFolder)serializable;

		if (layoutPageTemplateFolder == null) {
			Session session = null;

			try {
				session = openSession();

				layoutPageTemplateFolder = (LayoutPageTemplateFolder)session.get(LayoutPageTemplateFolderImpl.class,
						primaryKey);

				if (layoutPageTemplateFolder != null) {
					cacheResult(layoutPageTemplateFolder);
				}
				else {
					entityCache.putResult(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
						LayoutPageTemplateFolderImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPageTemplateFolderImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return layoutPageTemplateFolder;
	}

	/**
	 * Returns the layout page template folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPageTemplateFolderId the primary key of the layout page template folder
	 * @return the layout page template folder, or <code>null</code> if a layout page template folder with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateFolder fetchByPrimaryKey(
		long layoutPageTemplateFolderId) {
		return fetchByPrimaryKey((Serializable)layoutPageTemplateFolderId);
	}

	@Override
	public Map<Serializable, LayoutPageTemplateFolder> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LayoutPageTemplateFolder> map = new HashMap<Serializable, LayoutPageTemplateFolder>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LayoutPageTemplateFolder layoutPageTemplateFolder = fetchByPrimaryKey(primaryKey);

			if (layoutPageTemplateFolder != null) {
				map.put(primaryKey, layoutPageTemplateFolder);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPageTemplateFolderImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (LayoutPageTemplateFolder)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (LayoutPageTemplateFolder layoutPageTemplateFolder : (List<LayoutPageTemplateFolder>)q.list()) {
				map.put(layoutPageTemplateFolder.getPrimaryKeyObj(),
					layoutPageTemplateFolder);

				cacheResult(layoutPageTemplateFolder);

				uncachedPrimaryKeys.remove(layoutPageTemplateFolder.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(LayoutPageTemplateFolderModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPageTemplateFolderImpl.class, primaryKey, nullModel);
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
	 * Returns all the layout page template folders.
	 *
	 * @return the layout page template folders
	 */
	@Override
	public List<LayoutPageTemplateFolder> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template folders
	 * @param end the upper bound of the range of layout page template folders (not inclusive)
	 * @return the range of layout page template folders
	 */
	@Override
	public List<LayoutPageTemplateFolder> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template folders
	 * @param end the upper bound of the range of layout page template folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout page template folders
	 */
	@Override
	public List<LayoutPageTemplateFolder> findAll(int start, int end,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template folders
	 * @param end the upper bound of the range of layout page template folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout page template folders
	 */
	@Override
	public List<LayoutPageTemplateFolder> findAll(int start, int end,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator,
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

		List<LayoutPageTemplateFolder> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateFolder>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER;

				if (pagination) {
					sql = sql.concat(LayoutPageTemplateFolderModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutPageTemplateFolder>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateFolder>)QueryUtil.list(q,
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
	 * Removes all the layout page template folders from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutPageTemplateFolder layoutPageTemplateFolder : findAll()) {
			remove(layoutPageTemplateFolder);
		}
	}

	/**
	 * Returns the number of layout page template folders.
	 *
	 * @return the number of layout page template folders
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTPAGETEMPLATEFOLDER);

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
		return LayoutPageTemplateFolderModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout page template folder persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(LayoutPageTemplateFolderImpl.class.getName());
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
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER = "SELECT layoutPageTemplateFolder FROM LayoutPageTemplateFolder layoutPageTemplateFolder";
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_WHERE_PKS_IN =
		"SELECT layoutPageTemplateFolder FROM LayoutPageTemplateFolder layoutPageTemplateFolder WHERE layoutPageTemplateFolderId IN (";
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_WHERE = "SELECT layoutPageTemplateFolder FROM LayoutPageTemplateFolder layoutPageTemplateFolder WHERE ";
	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATEFOLDER = "SELECT COUNT(layoutPageTemplateFolder) FROM LayoutPageTemplateFolder layoutPageTemplateFolder";
	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATEFOLDER_WHERE = "SELECT COUNT(layoutPageTemplateFolder) FROM LayoutPageTemplateFolder layoutPageTemplateFolder WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "layoutPageTemplateFolder.layoutPageTemplateFolderId";
	private static final String _FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_WHERE =
		"SELECT DISTINCT {layoutPageTemplateFolder.*} FROM LayoutPageTemplateFolder layoutPageTemplateFolder WHERE ";
	private static final String _FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {LayoutPageTemplateFolder.*} FROM (SELECT DISTINCT layoutPageTemplateFolder.layoutPageTemplateFolderId FROM LayoutPageTemplateFolder layoutPageTemplateFolder WHERE ";
	private static final String _FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEFOLDER_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN LayoutPageTemplateFolder ON TEMP_TABLE.layoutPageTemplateFolderId = LayoutPageTemplateFolder.layoutPageTemplateFolderId";
	private static final String _FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEFOLDER_WHERE =
		"SELECT COUNT(DISTINCT layoutPageTemplateFolder.layoutPageTemplateFolderId) AS COUNT_VALUE FROM LayoutPageTemplateFolder layoutPageTemplateFolder WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "layoutPageTemplateFolder";
	private static final String _FILTER_ENTITY_TABLE = "LayoutPageTemplateFolder";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutPageTemplateFolder.";
	private static final String _ORDER_BY_ENTITY_TABLE = "LayoutPageTemplateFolder.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutPageTemplateFolder exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutPageTemplateFolder exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LayoutPageTemplateFolderPersistenceImpl.class);
}