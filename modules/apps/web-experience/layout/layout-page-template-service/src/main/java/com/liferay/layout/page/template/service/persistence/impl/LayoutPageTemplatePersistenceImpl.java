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

import com.liferay.layout.page.template.exception.NoSuchPageTemplateException;
import com.liferay.layout.page.template.model.LayoutPageTemplate;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateImpl;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateModelImpl;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplatePersistence;

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
 * The persistence implementation for the layout page template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplatePersistence
 * @see com.liferay.layout.page.template.service.persistence.LayoutPageTemplateUtil
 * @generated
 */
@ProviderType
public class LayoutPageTemplatePersistenceImpl extends BasePersistenceImpl<LayoutPageTemplate>
	implements LayoutPageTemplatePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LayoutPageTemplateUtil} to access the layout page template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutPageTemplateImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			LayoutPageTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the layout page templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page templates
	 * @param end the upper bound of the range of layout page templates (not inclusive)
	 * @return the range of matching layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page templates
	 * @param end the upper bound of the range of layout page templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findByGroupId(long groupId, int start,
		int end, OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page templates
	 * @param end the upper bound of the range of layout page templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findByGroupId(long groupId, int start,
		int end, OrderByComparator<LayoutPageTemplate> orderByComparator,
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

		List<LayoutPageTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplate layoutPageTemplate : list) {
					if ((groupId != layoutPageTemplate.getGroupId())) {
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

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<LayoutPageTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplate>)QueryUtil.list(q,
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
	 * Returns the first layout page template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template
	 * @throws NoSuchPageTemplateException if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate findByGroupId_First(long groupId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException {
		LayoutPageTemplate layoutPageTemplate = fetchByGroupId_First(groupId,
				orderByComparator);

		if (layoutPageTemplate != null) {
			return layoutPageTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageTemplateException(msg.toString());
	}

	/**
	 * Returns the first layout page template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template, or <code>null</code> if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate fetchByGroupId_First(long groupId,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		List<LayoutPageTemplate> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template
	 * @throws NoSuchPageTemplateException if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate findByGroupId_Last(long groupId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException {
		LayoutPageTemplate layoutPageTemplate = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (layoutPageTemplate != null) {
			return layoutPageTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageTemplateException(msg.toString());
	}

	/**
	 * Returns the last layout page template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template, or <code>null</code> if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate fetchByGroupId_Last(long groupId,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplate> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page templates before and after the current layout page template in the ordered set where groupId = &#63;.
	 *
	 * @param layoutPageTemplateId the primary key of the current layout page template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template
	 * @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplate[] findByGroupId_PrevAndNext(
		long layoutPageTemplateId, long groupId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException {
		LayoutPageTemplate layoutPageTemplate = findByPrimaryKey(layoutPageTemplateId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplate[] array = new LayoutPageTemplateImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, layoutPageTemplate,
					groupId, orderByComparator, true);

			array[1] = layoutPageTemplate;

			array[2] = getByGroupId_PrevAndNext(session, layoutPageTemplate,
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

	protected LayoutPageTemplate getByGroupId_PrevAndNext(Session session,
		LayoutPageTemplate layoutPageTemplate, long groupId,
		OrderByComparator<LayoutPageTemplate> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATE_WHERE);

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
			query.append(LayoutPageTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page templates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (LayoutPageTemplate layoutPageTemplate : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplate);
		}
	}

	/**
	 * Returns the number of layout page templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout page templates
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATE_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "layoutPageTemplate.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTPAGETEMPLATEFOLDERID =
		new FinderPath(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLayoutPageTemplateFolderId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPAGETEMPLATEFOLDERID =
		new FinderPath(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLayoutPageTemplateFolderId",
			new String[] { Long.class.getName() },
			LayoutPageTemplateModelImpl.LAYOUTPAGETEMPLATEFOLDERID_COLUMN_BITMASK |
			LayoutPageTemplateModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LAYOUTPAGETEMPLATEFOLDERID =
		new FinderPath(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutPageTemplateFolderId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the layout page templates where layoutPageTemplateFolderId = &#63;.
	 *
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @return the matching layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId) {
		return findByLayoutPageTemplateFolderId(layoutPageTemplateFolderId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page templates where layoutPageTemplateFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param start the lower bound of the range of layout page templates
	 * @param end the upper bound of the range of layout page templates (not inclusive)
	 * @return the range of matching layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId, int start, int end) {
		return findByLayoutPageTemplateFolderId(layoutPageTemplateFolderId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page templates where layoutPageTemplateFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param start the lower bound of the range of layout page templates
	 * @param end the upper bound of the range of layout page templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId, int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return findByLayoutPageTemplateFolderId(layoutPageTemplateFolderId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page templates where layoutPageTemplateFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param start the lower bound of the range of layout page templates
	 * @param end the upper bound of the range of layout page templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId, int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPAGETEMPLATEFOLDERID;
			finderArgs = new Object[] { layoutPageTemplateFolderId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTPAGETEMPLATEFOLDERID;
			finderArgs = new Object[] {
					layoutPageTemplateFolderId,
					
					start, end, orderByComparator
				};
		}

		List<LayoutPageTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplate layoutPageTemplate : list) {
					if ((layoutPageTemplateFolderId != layoutPageTemplate.getLayoutPageTemplateFolderId())) {
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

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTPAGETEMPLATEFOLDERID_LAYOUTPAGETEMPLATEFOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutPageTemplateFolderId);

				if (!pagination) {
					list = (List<LayoutPageTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplate>)QueryUtil.list(q,
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
	 * Returns the first layout page template in the ordered set where layoutPageTemplateFolderId = &#63;.
	 *
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template
	 * @throws NoSuchPageTemplateException if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate findByLayoutPageTemplateFolderId_First(
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException {
		LayoutPageTemplate layoutPageTemplate = fetchByLayoutPageTemplateFolderId_First(layoutPageTemplateFolderId,
				orderByComparator);

		if (layoutPageTemplate != null) {
			return layoutPageTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPageTemplateFolderId=");
		msg.append(layoutPageTemplateFolderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageTemplateException(msg.toString());
	}

	/**
	 * Returns the first layout page template in the ordered set where layoutPageTemplateFolderId = &#63;.
	 *
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template, or <code>null</code> if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate fetchByLayoutPageTemplateFolderId_First(
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		List<LayoutPageTemplate> list = findByLayoutPageTemplateFolderId(layoutPageTemplateFolderId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template in the ordered set where layoutPageTemplateFolderId = &#63;.
	 *
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template
	 * @throws NoSuchPageTemplateException if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate findByLayoutPageTemplateFolderId_Last(
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException {
		LayoutPageTemplate layoutPageTemplate = fetchByLayoutPageTemplateFolderId_Last(layoutPageTemplateFolderId,
				orderByComparator);

		if (layoutPageTemplate != null) {
			return layoutPageTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPageTemplateFolderId=");
		msg.append(layoutPageTemplateFolderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageTemplateException(msg.toString());
	}

	/**
	 * Returns the last layout page template in the ordered set where layoutPageTemplateFolderId = &#63;.
	 *
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template, or <code>null</code> if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate fetchByLayoutPageTemplateFolderId_Last(
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		int count = countByLayoutPageTemplateFolderId(layoutPageTemplateFolderId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplate> list = findByLayoutPageTemplateFolderId(layoutPageTemplateFolderId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page templates before and after the current layout page template in the ordered set where layoutPageTemplateFolderId = &#63;.
	 *
	 * @param layoutPageTemplateId the primary key of the current layout page template
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template
	 * @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplate[] findByLayoutPageTemplateFolderId_PrevAndNext(
		long layoutPageTemplateId, long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException {
		LayoutPageTemplate layoutPageTemplate = findByPrimaryKey(layoutPageTemplateId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplate[] array = new LayoutPageTemplateImpl[3];

			array[0] = getByLayoutPageTemplateFolderId_PrevAndNext(session,
					layoutPageTemplate, layoutPageTemplateFolderId,
					orderByComparator, true);

			array[1] = layoutPageTemplate;

			array[2] = getByLayoutPageTemplateFolderId_PrevAndNext(session,
					layoutPageTemplate, layoutPageTemplateFolderId,
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

	protected LayoutPageTemplate getByLayoutPageTemplateFolderId_PrevAndNext(
		Session session, LayoutPageTemplate layoutPageTemplate,
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_LAYOUTPAGETEMPLATEFOLDERID_LAYOUTPAGETEMPLATEFOLDERID_2);

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
			query.append(LayoutPageTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutPageTemplateFolderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page templates where layoutPageTemplateFolderId = &#63; from the database.
	 *
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 */
	@Override
	public void removeByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId) {
		for (LayoutPageTemplate layoutPageTemplate : findByLayoutPageTemplateFolderId(
				layoutPageTemplateFolderId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplate);
		}
	}

	/**
	 * Returns the number of layout page templates where layoutPageTemplateFolderId = &#63;.
	 *
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @return the number of matching layout page templates
	 */
	@Override
	public int countByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_LAYOUTPAGETEMPLATEFOLDERID;

		Object[] finderArgs = new Object[] { layoutPageTemplateFolderId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTPAGETEMPLATEFOLDERID_LAYOUTPAGETEMPLATEFOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutPageTemplateFolderId);

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

	private static final String _FINDER_COLUMN_LAYOUTPAGETEMPLATEFOLDERID_LAYOUTPAGETEMPLATEFOLDERID_2 =
		"layoutPageTemplate.layoutPageTemplateFolderId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LPTFI = new FinderPath(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LPTFI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_LPTFI =
		new FinderPath(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_LPTFI",
			new String[] { Long.class.getName(), Long.class.getName() },
			LayoutPageTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateModelImpl.LAYOUTPAGETEMPLATEFOLDERID_COLUMN_BITMASK |
			LayoutPageTemplateModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_LPTFI = new FinderPath(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_LPTFI",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @return the matching layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findByG_LPTFI(long groupId,
		long layoutPageTemplateFolderId) {
		return findByG_LPTFI(groupId, layoutPageTemplateFolderId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param start the lower bound of the range of layout page templates
	 * @param end the upper bound of the range of layout page templates (not inclusive)
	 * @return the range of matching layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findByG_LPTFI(long groupId,
		long layoutPageTemplateFolderId, int start, int end) {
		return findByG_LPTFI(groupId, layoutPageTemplateFolderId, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param start the lower bound of the range of layout page templates
	 * @param end the upper bound of the range of layout page templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findByG_LPTFI(long groupId,
		long layoutPageTemplateFolderId, int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return findByG_LPTFI(groupId, layoutPageTemplateFolderId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param start the lower bound of the range of layout page templates
	 * @param end the upper bound of the range of layout page templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findByG_LPTFI(long groupId,
		long layoutPageTemplateFolderId, int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_LPTFI;
			finderArgs = new Object[] { groupId, layoutPageTemplateFolderId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LPTFI;
			finderArgs = new Object[] {
					groupId, layoutPageTemplateFolderId,
					
					start, end, orderByComparator
				};
		}

		List<LayoutPageTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplate layoutPageTemplate : list) {
					if ((groupId != layoutPageTemplate.getGroupId()) ||
							(layoutPageTemplateFolderId != layoutPageTemplate.getLayoutPageTemplateFolderId())) {
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

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_LPTFI_GROUPID_2);

			query.append(_FINDER_COLUMN_G_LPTFI_LAYOUTPAGETEMPLATEFOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(layoutPageTemplateFolderId);

				if (!pagination) {
					list = (List<LayoutPageTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplate>)QueryUtil.list(q,
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
	 * Returns the first layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template
	 * @throws NoSuchPageTemplateException if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate findByG_LPTFI_First(long groupId,
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException {
		LayoutPageTemplate layoutPageTemplate = fetchByG_LPTFI_First(groupId,
				layoutPageTemplateFolderId, orderByComparator);

		if (layoutPageTemplate != null) {
			return layoutPageTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutPageTemplateFolderId=");
		msg.append(layoutPageTemplateFolderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageTemplateException(msg.toString());
	}

	/**
	 * Returns the first layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template, or <code>null</code> if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate fetchByG_LPTFI_First(long groupId,
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		List<LayoutPageTemplate> list = findByG_LPTFI(groupId,
				layoutPageTemplateFolderId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template
	 * @throws NoSuchPageTemplateException if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate findByG_LPTFI_Last(long groupId,
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException {
		LayoutPageTemplate layoutPageTemplate = fetchByG_LPTFI_Last(groupId,
				layoutPageTemplateFolderId, orderByComparator);

		if (layoutPageTemplate != null) {
			return layoutPageTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutPageTemplateFolderId=");
		msg.append(layoutPageTemplateFolderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageTemplateException(msg.toString());
	}

	/**
	 * Returns the last layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template, or <code>null</code> if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate fetchByG_LPTFI_Last(long groupId,
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		int count = countByG_LPTFI(groupId, layoutPageTemplateFolderId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplate> list = findByG_LPTFI(groupId,
				layoutPageTemplateFolderId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page templates before and after the current layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	 *
	 * @param layoutPageTemplateId the primary key of the current layout page template
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template
	 * @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplate[] findByG_LPTFI_PrevAndNext(
		long layoutPageTemplateId, long groupId,
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException {
		LayoutPageTemplate layoutPageTemplate = findByPrimaryKey(layoutPageTemplateId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplate[] array = new LayoutPageTemplateImpl[3];

			array[0] = getByG_LPTFI_PrevAndNext(session, layoutPageTemplate,
					groupId, layoutPageTemplateFolderId, orderByComparator, true);

			array[1] = layoutPageTemplate;

			array[2] = getByG_LPTFI_PrevAndNext(session, layoutPageTemplate,
					groupId, layoutPageTemplateFolderId, orderByComparator,
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

	protected LayoutPageTemplate getByG_LPTFI_PrevAndNext(Session session,
		LayoutPageTemplate layoutPageTemplate, long groupId,
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_LPTFI_GROUPID_2);

		query.append(_FINDER_COLUMN_G_LPTFI_LAYOUTPAGETEMPLATEFOLDERID_2);

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
			query.append(LayoutPageTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(layoutPageTemplateFolderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 */
	@Override
	public void removeByG_LPTFI(long groupId, long layoutPageTemplateFolderId) {
		for (LayoutPageTemplate layoutPageTemplate : findByG_LPTFI(groupId,
				layoutPageTemplateFolderId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplate);
		}
	}

	/**
	 * Returns the number of layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @return the number of matching layout page templates
	 */
	@Override
	public int countByG_LPTFI(long groupId, long layoutPageTemplateFolderId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_LPTFI;

		Object[] finderArgs = new Object[] { groupId, layoutPageTemplateFolderId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_LPTFI_GROUPID_2);

			query.append(_FINDER_COLUMN_G_LPTFI_LAYOUTPAGETEMPLATEFOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(layoutPageTemplateFolderId);

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

	private static final String _FINDER_COLUMN_G_LPTFI_GROUPID_2 = "layoutPageTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_LPTFI_LAYOUTPAGETEMPLATEFOLDERID_2 =
		"layoutPageTemplate.layoutPageTemplateFolderId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() },
			LayoutPageTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the layout page template where groupId = &#63; and name = &#63; or throws a {@link NoSuchPageTemplateException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template
	 * @throws NoSuchPageTemplateException if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate findByG_N(long groupId, String name)
		throws NoSuchPageTemplateException {
		LayoutPageTemplate layoutPageTemplate = fetchByG_N(groupId, name);

		if (layoutPageTemplate == null) {
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

			throw new NoSuchPageTemplateException(msg.toString());
		}

		return layoutPageTemplate;
	}

	/**
	 * Returns the layout page template where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template, or <code>null</code> if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate fetchByG_N(long groupId, String name) {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Returns the layout page template where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout page template, or <code>null</code> if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate fetchByG_N(long groupId, String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_N,
					finderArgs, this);
		}

		if (result instanceof LayoutPageTemplate) {
			LayoutPageTemplate layoutPageTemplate = (LayoutPageTemplate)result;

			if ((groupId != layoutPageTemplate.getGroupId()) ||
					!Objects.equals(name, layoutPageTemplate.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATE_WHERE);

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

				List<LayoutPageTemplate> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, finderArgs,
						list);
				}
				else {
					LayoutPageTemplate layoutPageTemplate = list.get(0);

					result = layoutPageTemplate;

					cacheResult(layoutPageTemplate);

					if ((layoutPageTemplate.getGroupId() != groupId) ||
							(layoutPageTemplate.getName() == null) ||
							!layoutPageTemplate.getName().equals(name)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_N,
							finderArgs, layoutPageTemplate);
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
			return (LayoutPageTemplate)result;
		}
	}

	/**
	 * Removes the layout page template where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the layout page template that was removed
	 */
	@Override
	public LayoutPageTemplate removeByG_N(long groupId, String name)
		throws NoSuchPageTemplateException {
		LayoutPageTemplate layoutPageTemplate = findByG_N(groupId, name);

		return remove(layoutPageTemplate);
	}

	/**
	 * Returns the number of layout page templates where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching layout page templates
	 */
	@Override
	public int countByG_N(long groupId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_N;

		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATE_WHERE);

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

	private static final String _FINDER_COLUMN_G_N_GROUPID_2 = "layoutPageTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_NAME_1 = "layoutPageTemplate.name IS NULL";
	private static final String _FINDER_COLUMN_G_N_NAME_2 = "layoutPageTemplate.name = ?";
	private static final String _FINDER_COLUMN_G_N_NAME_3 = "(layoutPageTemplate.name IS NULL OR layoutPageTemplate.name = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LPTFI_LIKEN =
		new FinderPath(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LPTFI_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LPTFI_LIKEN =
		new FinderPath(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_LPTFI_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param name the name
	 * @return the matching layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findByG_LPTFI_LikeN(long groupId,
		long layoutPageTemplateFolderId, String name) {
		return findByG_LPTFI_LikeN(groupId, layoutPageTemplateFolderId, name,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page templates
	 * @param end the upper bound of the range of layout page templates (not inclusive)
	 * @return the range of matching layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findByG_LPTFI_LikeN(long groupId,
		long layoutPageTemplateFolderId, String name, int start, int end) {
		return findByG_LPTFI_LikeN(groupId, layoutPageTemplateFolderId, name,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page templates
	 * @param end the upper bound of the range of layout page templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findByG_LPTFI_LikeN(long groupId,
		long layoutPageTemplateFolderId, String name, int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return findByG_LPTFI_LikeN(groupId, layoutPageTemplateFolderId, name,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page templates
	 * @param end the upper bound of the range of layout page templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findByG_LPTFI_LikeN(long groupId,
		long layoutPageTemplateFolderId, String name, int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LPTFI_LIKEN;
		finderArgs = new Object[] {
				groupId, layoutPageTemplateFolderId, name,
				
				start, end, orderByComparator
			};

		List<LayoutPageTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplate layoutPageTemplate : list) {
					if ((groupId != layoutPageTemplate.getGroupId()) ||
							(layoutPageTemplateFolderId != layoutPageTemplate.getLayoutPageTemplateFolderId()) ||
							!StringUtil.wildcardMatches(
								layoutPageTemplate.getName(), name,
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
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_LPTFI_LIKEN_GROUPID_2);

			query.append(_FINDER_COLUMN_G_LPTFI_LIKEN_LAYOUTPAGETEMPLATEFOLDERID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_LPTFI_LIKEN_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_LPTFI_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_LPTFI_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(layoutPageTemplateFolderId);

				if (bindName) {
					qPos.add(StringUtil.toLowerCase(name));
				}

				if (!pagination) {
					list = (List<LayoutPageTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplate>)QueryUtil.list(q,
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
	 * Returns the first layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template
	 * @throws NoSuchPageTemplateException if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate findByG_LPTFI_LikeN_First(long groupId,
		long layoutPageTemplateFolderId, String name,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException {
		LayoutPageTemplate layoutPageTemplate = fetchByG_LPTFI_LikeN_First(groupId,
				layoutPageTemplateFolderId, name, orderByComparator);

		if (layoutPageTemplate != null) {
			return layoutPageTemplate;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutPageTemplateFolderId=");
		msg.append(layoutPageTemplateFolderId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageTemplateException(msg.toString());
	}

	/**
	 * Returns the first layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template, or <code>null</code> if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate fetchByG_LPTFI_LikeN_First(long groupId,
		long layoutPageTemplateFolderId, String name,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		List<LayoutPageTemplate> list = findByG_LPTFI_LikeN(groupId,
				layoutPageTemplateFolderId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template
	 * @throws NoSuchPageTemplateException if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate findByG_LPTFI_LikeN_Last(long groupId,
		long layoutPageTemplateFolderId, String name,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException {
		LayoutPageTemplate layoutPageTemplate = fetchByG_LPTFI_LikeN_Last(groupId,
				layoutPageTemplateFolderId, name, orderByComparator);

		if (layoutPageTemplate != null) {
			return layoutPageTemplate;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutPageTemplateFolderId=");
		msg.append(layoutPageTemplateFolderId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageTemplateException(msg.toString());
	}

	/**
	 * Returns the last layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template, or <code>null</code> if a matching layout page template could not be found
	 */
	@Override
	public LayoutPageTemplate fetchByG_LPTFI_LikeN_Last(long groupId,
		long layoutPageTemplateFolderId, String name,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		int count = countByG_LPTFI_LikeN(groupId, layoutPageTemplateFolderId,
				name);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplate> list = findByG_LPTFI_LikeN(groupId,
				layoutPageTemplateFolderId, name, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page templates before and after the current layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	 *
	 * @param layoutPageTemplateId the primary key of the current layout page template
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template
	 * @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplate[] findByG_LPTFI_LikeN_PrevAndNext(
		long layoutPageTemplateId, long groupId,
		long layoutPageTemplateFolderId, String name,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException {
		LayoutPageTemplate layoutPageTemplate = findByPrimaryKey(layoutPageTemplateId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplate[] array = new LayoutPageTemplateImpl[3];

			array[0] = getByG_LPTFI_LikeN_PrevAndNext(session,
					layoutPageTemplate, groupId, layoutPageTemplateFolderId,
					name, orderByComparator, true);

			array[1] = layoutPageTemplate;

			array[2] = getByG_LPTFI_LikeN_PrevAndNext(session,
					layoutPageTemplate, groupId, layoutPageTemplateFolderId,
					name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplate getByG_LPTFI_LikeN_PrevAndNext(
		Session session, LayoutPageTemplate layoutPageTemplate, long groupId,
		long layoutPageTemplateFolderId, String name,
		OrderByComparator<LayoutPageTemplate> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_LPTFI_LIKEN_GROUPID_2);

		query.append(_FINDER_COLUMN_G_LPTFI_LIKEN_LAYOUTPAGETEMPLATEFOLDERID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LPTFI_LIKEN_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_LPTFI_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LPTFI_LIKEN_NAME_2);
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
			query.append(LayoutPageTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(layoutPageTemplateFolderId);

		if (bindName) {
			qPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param name the name
	 */
	@Override
	public void removeByG_LPTFI_LikeN(long groupId,
		long layoutPageTemplateFolderId, String name) {
		for (LayoutPageTemplate layoutPageTemplate : findByG_LPTFI_LikeN(
				groupId, layoutPageTemplateFolderId, name, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplate);
		}
	}

	/**
	 * Returns the number of layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateFolderId the layout page template folder ID
	 * @param name the name
	 * @return the number of matching layout page templates
	 */
	@Override
	public int countByG_LPTFI_LikeN(long groupId,
		long layoutPageTemplateFolderId, String name) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LPTFI_LIKEN;

		Object[] finderArgs = new Object[] {
				groupId, layoutPageTemplateFolderId, name
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_LPTFI_LIKEN_GROUPID_2);

			query.append(_FINDER_COLUMN_G_LPTFI_LIKEN_LAYOUTPAGETEMPLATEFOLDERID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_LPTFI_LIKEN_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_LPTFI_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_LPTFI_LIKEN_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(layoutPageTemplateFolderId);

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

	private static final String _FINDER_COLUMN_G_LPTFI_LIKEN_GROUPID_2 = "layoutPageTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_LPTFI_LIKEN_LAYOUTPAGETEMPLATEFOLDERID_2 =
		"layoutPageTemplate.layoutPageTemplateFolderId = ? AND ";
	private static final String _FINDER_COLUMN_G_LPTFI_LIKEN_NAME_1 = "layoutPageTemplate.name IS NULL";
	private static final String _FINDER_COLUMN_G_LPTFI_LIKEN_NAME_2 = "lower(layoutPageTemplate.name) LIKE ?";
	private static final String _FINDER_COLUMN_G_LPTFI_LIKEN_NAME_3 = "(layoutPageTemplate.name IS NULL OR layoutPageTemplate.name LIKE '')";

	public LayoutPageTemplatePersistenceImpl() {
		setModelClass(LayoutPageTemplate.class);
	}

	/**
	 * Caches the layout page template in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplate the layout page template
	 */
	@Override
	public void cacheResult(LayoutPageTemplate layoutPageTemplate) {
		entityCache.putResult(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateImpl.class, layoutPageTemplate.getPrimaryKey(),
			layoutPageTemplate);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				layoutPageTemplate.getGroupId(), layoutPageTemplate.getName()
			}, layoutPageTemplate);

		layoutPageTemplate.resetOriginalValues();
	}

	/**
	 * Caches the layout page templates in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplates the layout page templates
	 */
	@Override
	public void cacheResult(List<LayoutPageTemplate> layoutPageTemplates) {
		for (LayoutPageTemplate layoutPageTemplate : layoutPageTemplates) {
			if (entityCache.getResult(
						LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
						LayoutPageTemplateImpl.class,
						layoutPageTemplate.getPrimaryKey()) == null) {
				cacheResult(layoutPageTemplate);
			}
			else {
				layoutPageTemplate.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout page templates.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutPageTemplateImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout page template.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutPageTemplate layoutPageTemplate) {
		entityCache.removeResult(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateImpl.class, layoutPageTemplate.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LayoutPageTemplateModelImpl)layoutPageTemplate,
			true);
	}

	@Override
	public void clearCache(List<LayoutPageTemplate> layoutPageTemplates) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutPageTemplate layoutPageTemplate : layoutPageTemplates) {
			entityCache.removeResult(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateImpl.class, layoutPageTemplate.getPrimaryKey());

			clearUniqueFindersCache((LayoutPageTemplateModelImpl)layoutPageTemplate,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutPageTemplateModelImpl layoutPageTemplateModelImpl) {
		Object[] args = new Object[] {
				layoutPageTemplateModelImpl.getGroupId(),
				layoutPageTemplateModelImpl.getName()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_N, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, args,
			layoutPageTemplateModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LayoutPageTemplateModelImpl layoutPageTemplateModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					layoutPageTemplateModelImpl.getGroupId(),
					layoutPageTemplateModelImpl.getName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, args);
		}

		if ((layoutPageTemplateModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_N.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					layoutPageTemplateModelImpl.getOriginalGroupId(),
					layoutPageTemplateModelImpl.getOriginalName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, args);
		}
	}

	/**
	 * Creates a new layout page template with the primary key. Does not add the layout page template to the database.
	 *
	 * @param layoutPageTemplateId the primary key for the new layout page template
	 * @return the new layout page template
	 */
	@Override
	public LayoutPageTemplate create(long layoutPageTemplateId) {
		LayoutPageTemplate layoutPageTemplate = new LayoutPageTemplateImpl();

		layoutPageTemplate.setNew(true);
		layoutPageTemplate.setPrimaryKey(layoutPageTemplateId);

		layoutPageTemplate.setCompanyId(companyProvider.getCompanyId());

		return layoutPageTemplate;
	}

	/**
	 * Removes the layout page template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateId the primary key of the layout page template
	 * @return the layout page template that was removed
	 * @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplate remove(long layoutPageTemplateId)
		throws NoSuchPageTemplateException {
		return remove((Serializable)layoutPageTemplateId);
	}

	/**
	 * Removes the layout page template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout page template
	 * @return the layout page template that was removed
	 * @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplate remove(Serializable primaryKey)
		throws NoSuchPageTemplateException {
		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplate layoutPageTemplate = (LayoutPageTemplate)session.get(LayoutPageTemplateImpl.class,
					primaryKey);

			if (layoutPageTemplate == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPageTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(layoutPageTemplate);
		}
		catch (NoSuchPageTemplateException nsee) {
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
	protected LayoutPageTemplate removeImpl(
		LayoutPageTemplate layoutPageTemplate) {
		layoutPageTemplate = toUnwrappedModel(layoutPageTemplate);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutPageTemplate)) {
				layoutPageTemplate = (LayoutPageTemplate)session.get(LayoutPageTemplateImpl.class,
						layoutPageTemplate.getPrimaryKeyObj());
			}

			if (layoutPageTemplate != null) {
				session.delete(layoutPageTemplate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutPageTemplate != null) {
			clearCache(layoutPageTemplate);
		}

		return layoutPageTemplate;
	}

	@Override
	public LayoutPageTemplate updateImpl(LayoutPageTemplate layoutPageTemplate) {
		layoutPageTemplate = toUnwrappedModel(layoutPageTemplate);

		boolean isNew = layoutPageTemplate.isNew();

		LayoutPageTemplateModelImpl layoutPageTemplateModelImpl = (LayoutPageTemplateModelImpl)layoutPageTemplate;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutPageTemplate.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutPageTemplate.setCreateDate(now);
			}
			else {
				layoutPageTemplate.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!layoutPageTemplateModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutPageTemplate.setModifiedDate(now);
			}
			else {
				layoutPageTemplate.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutPageTemplate.isNew()) {
				session.save(layoutPageTemplate);

				layoutPageTemplate.setNew(false);
			}
			else {
				layoutPageTemplate = (LayoutPageTemplate)session.merge(layoutPageTemplate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LayoutPageTemplateModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					layoutPageTemplateModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					layoutPageTemplateModelImpl.getLayoutPageTemplateFolderId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_LAYOUTPAGETEMPLATEFOLDERID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPAGETEMPLATEFOLDERID,
				args);

			args = new Object[] {
					layoutPageTemplateModelImpl.getGroupId(),
					layoutPageTemplateModelImpl.getLayoutPageTemplateFolderId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_LPTFI, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_LPTFI,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((layoutPageTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { layoutPageTemplateModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((layoutPageTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPAGETEMPLATEFOLDERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateModelImpl.getOriginalLayoutPageTemplateFolderId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LAYOUTPAGETEMPLATEFOLDERID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPAGETEMPLATEFOLDERID,
					args);

				args = new Object[] {
						layoutPageTemplateModelImpl.getLayoutPageTemplateFolderId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LAYOUTPAGETEMPLATEFOLDERID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPAGETEMPLATEFOLDERID,
					args);
			}

			if ((layoutPageTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_LPTFI.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateModelImpl.getOriginalGroupId(),
						layoutPageTemplateModelImpl.getOriginalLayoutPageTemplateFolderId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_LPTFI, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_LPTFI,
					args);

				args = new Object[] {
						layoutPageTemplateModelImpl.getGroupId(),
						layoutPageTemplateModelImpl.getLayoutPageTemplateFolderId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_LPTFI, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_LPTFI,
					args);
			}
		}

		entityCache.putResult(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateImpl.class, layoutPageTemplate.getPrimaryKey(),
			layoutPageTemplate, false);

		clearUniqueFindersCache(layoutPageTemplateModelImpl, false);
		cacheUniqueFindersCache(layoutPageTemplateModelImpl);

		layoutPageTemplate.resetOriginalValues();

		return layoutPageTemplate;
	}

	protected LayoutPageTemplate toUnwrappedModel(
		LayoutPageTemplate layoutPageTemplate) {
		if (layoutPageTemplate instanceof LayoutPageTemplateImpl) {
			return layoutPageTemplate;
		}

		LayoutPageTemplateImpl layoutPageTemplateImpl = new LayoutPageTemplateImpl();

		layoutPageTemplateImpl.setNew(layoutPageTemplate.isNew());
		layoutPageTemplateImpl.setPrimaryKey(layoutPageTemplate.getPrimaryKey());

		layoutPageTemplateImpl.setLayoutPageTemplateId(layoutPageTemplate.getLayoutPageTemplateId());
		layoutPageTemplateImpl.setGroupId(layoutPageTemplate.getGroupId());
		layoutPageTemplateImpl.setCompanyId(layoutPageTemplate.getCompanyId());
		layoutPageTemplateImpl.setUserId(layoutPageTemplate.getUserId());
		layoutPageTemplateImpl.setUserName(layoutPageTemplate.getUserName());
		layoutPageTemplateImpl.setCreateDate(layoutPageTemplate.getCreateDate());
		layoutPageTemplateImpl.setModifiedDate(layoutPageTemplate.getModifiedDate());
		layoutPageTemplateImpl.setLayoutPageTemplateFolderId(layoutPageTemplate.getLayoutPageTemplateFolderId());
		layoutPageTemplateImpl.setName(layoutPageTemplate.getName());

		return layoutPageTemplateImpl;
	}

	/**
	 * Returns the layout page template with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout page template
	 * @return the layout page template
	 * @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPageTemplateException {
		LayoutPageTemplate layoutPageTemplate = fetchByPrimaryKey(primaryKey);

		if (layoutPageTemplate == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPageTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return layoutPageTemplate;
	}

	/**
	 * Returns the layout page template with the primary key or throws a {@link NoSuchPageTemplateException} if it could not be found.
	 *
	 * @param layoutPageTemplateId the primary key of the layout page template
	 * @return the layout page template
	 * @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplate findByPrimaryKey(long layoutPageTemplateId)
		throws NoSuchPageTemplateException {
		return findByPrimaryKey((Serializable)layoutPageTemplateId);
	}

	/**
	 * Returns the layout page template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout page template
	 * @return the layout page template, or <code>null</code> if a layout page template with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplate fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LayoutPageTemplate layoutPageTemplate = (LayoutPageTemplate)serializable;

		if (layoutPageTemplate == null) {
			Session session = null;

			try {
				session = openSession();

				layoutPageTemplate = (LayoutPageTemplate)session.get(LayoutPageTemplateImpl.class,
						primaryKey);

				if (layoutPageTemplate != null) {
					cacheResult(layoutPageTemplate);
				}
				else {
					entityCache.putResult(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
						LayoutPageTemplateImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPageTemplateImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return layoutPageTemplate;
	}

	/**
	 * Returns the layout page template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPageTemplateId the primary key of the layout page template
	 * @return the layout page template, or <code>null</code> if a layout page template with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplate fetchByPrimaryKey(long layoutPageTemplateId) {
		return fetchByPrimaryKey((Serializable)layoutPageTemplateId);
	}

	@Override
	public Map<Serializable, LayoutPageTemplate> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LayoutPageTemplate> map = new HashMap<Serializable, LayoutPageTemplate>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LayoutPageTemplate layoutPageTemplate = fetchByPrimaryKey(primaryKey);

			if (layoutPageTemplate != null) {
				map.put(primaryKey, layoutPageTemplate);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPageTemplateImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (LayoutPageTemplate)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATE_WHERE_PKS_IN);

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

			for (LayoutPageTemplate layoutPageTemplate : (List<LayoutPageTemplate>)q.list()) {
				map.put(layoutPageTemplate.getPrimaryKeyObj(),
					layoutPageTemplate);

				cacheResult(layoutPageTemplate);

				uncachedPrimaryKeys.remove(layoutPageTemplate.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(LayoutPageTemplateModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPageTemplateImpl.class, primaryKey, nullModel);
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
	 * Returns all the layout page templates.
	 *
	 * @return the layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page templates
	 * @param end the upper bound of the range of layout page templates (not inclusive)
	 * @return the range of layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page templates
	 * @param end the upper bound of the range of layout page templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findAll(int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page templates
	 * @param end the upper bound of the range of layout page templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout page templates
	 */
	@Override
	public List<LayoutPageTemplate> findAll(int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator,
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

		List<LayoutPageTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTPAGETEMPLATE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTPAGETEMPLATE;

				if (pagination) {
					sql = sql.concat(LayoutPageTemplateModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutPageTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplate>)QueryUtil.list(q,
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
	 * Removes all the layout page templates from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutPageTemplate layoutPageTemplate : findAll()) {
			remove(layoutPageTemplate);
		}
	}

	/**
	 * Returns the number of layout page templates.
	 *
	 * @return the number of layout page templates
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTPAGETEMPLATE);

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
		return LayoutPageTemplateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout page template persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(LayoutPageTemplateImpl.class.getName());
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
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATE = "SELECT layoutPageTemplate FROM LayoutPageTemplate layoutPageTemplate";
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATE_WHERE_PKS_IN = "SELECT layoutPageTemplate FROM LayoutPageTemplate layoutPageTemplate WHERE layoutPageTemplateId IN (";
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATE_WHERE = "SELECT layoutPageTemplate FROM LayoutPageTemplate layoutPageTemplate WHERE ";
	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATE = "SELECT COUNT(layoutPageTemplate) FROM LayoutPageTemplate layoutPageTemplate";
	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATE_WHERE = "SELECT COUNT(layoutPageTemplate) FROM LayoutPageTemplate layoutPageTemplate WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutPageTemplate.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutPageTemplate exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutPageTemplate exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LayoutPageTemplatePersistenceImpl.class);
}