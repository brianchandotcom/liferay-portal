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

package com.liferay.dynamic.data.mapping.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMTemplatePersistence;

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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the d d m template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplatePersistence
 * @see com.liferay.dynamic.data.mapping.service.persistence.DDMTemplateUtil
 * @generated
 */
@ProviderType
public class DDMTemplatePersistenceImpl extends BasePersistenceImpl<DDMTemplate>
	implements DDMTemplatePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMTemplateUtil} to access the d d m template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMTemplateImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DDMTemplateModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the d d m templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByUuid(String uuid, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByUuid(String uuid, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<DDMTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplate ddmTemplate : list) {
					if (!Validator.equals(uuid, ddmTemplate.getUuid())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByUuid_First(String uuid,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByUuid_First(uuid, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByUuid_First(String uuid,
		OrderByComparator<DDMTemplate> orderByComparator) {
		List<DDMTemplate> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByUuid_Last(String uuid,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByUuid_Last(uuid, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByUuid_Last(String uuid,
		OrderByComparator<DDMTemplate> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DDMTemplate> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where uuid = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] findByUuid_PrevAndNext(long templateId, String uuid,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByUuid_PrevAndNext(session, ddmTemplate, uuid,
					orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByUuid_PrevAndNext(session, ddmTemplate, uuid,
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

	protected DDMTemplate getByUuid_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, String uuid,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
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
			query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m templates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DDMTemplate ddmTemplate : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Returns the number of d d m templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "ddmTemplate.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "ddmTemplate.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(ddmTemplate.uuid IS NULL OR ddmTemplate.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMTemplateModelImpl.UUID_COLUMN_BITMASK |
			DDMTemplateModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the d d m template where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchTemplateException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByUUID_G(uuid, groupId);

		if (ddmTemplate == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateException(msg.toString());
		}

		return ddmTemplate;
	}

	/**
	 * Returns the d d m template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the d d m template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof DDMTemplate) {
			DDMTemplate ddmTemplate = (DDMTemplate)result;

			if (!Validator.equals(uuid, ddmTemplate.getUuid()) ||
					(groupId != ddmTemplate.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<DDMTemplate> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					DDMTemplate ddmTemplate = list.get(0);

					result = ddmTemplate;

					cacheResult(ddmTemplate);

					if ((ddmTemplate.getUuid() == null) ||
							!ddmTemplate.getUuid().equals(uuid) ||
							(ddmTemplate.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, ddmTemplate);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, finderArgs);

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
			return (DDMTemplate)result;
		}
	}

	/**
	 * Removes the d d m template where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the d d m template that was removed
	 */
	@Override
	public DDMTemplate removeByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = findByUUID_G(uuid, groupId);

		return remove(ddmTemplate);
	}

	/**
	 * Returns the number of d d m templates where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "ddmTemplate.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "ddmTemplate.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(ddmTemplate.uuid IS NULL OR ddmTemplate.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "ddmTemplate.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMTemplateModelImpl.UUID_COLUMN_BITMASK |
			DDMTemplateModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the d d m templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<DDMTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplate ddmTemplate : list) {
					if (!Validator.equals(uuid, ddmTemplate.getUuid()) ||
							(companyId != ddmTemplate.getCompanyId())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DDMTemplate> orderByComparator) {
		List<DDMTemplate> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DDMTemplate> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DDMTemplate> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] findByUuid_C_PrevAndNext(long templateId, String uuid,
		long companyId, OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, ddmTemplate, uuid,
					companyId, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByUuid_C_PrevAndNext(session, ddmTemplate, uuid,
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

	protected DDMTemplate getByUuid_C_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, String uuid, long companyId,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m templates where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DDMTemplate ddmTemplate : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Returns the number of d d m templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "ddmTemplate.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "ddmTemplate.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(ddmTemplate.uuid IS NULL OR ddmTemplate.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "ddmTemplate.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			DDMTemplateModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d d m templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByGroupId(long groupId, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByGroupId(long groupId, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
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

		List<DDMTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplate ddmTemplate : list) {
					if ((groupId != ddmTemplate.getGroupId())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByGroupId_First(long groupId,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByGroupId_First(groupId,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByGroupId_First(long groupId,
		OrderByComparator<DDMTemplate> orderByComparator) {
		List<DDMTemplate> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByGroupId_Last(long groupId,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByGroupId_Last(groupId, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByGroupId_Last(long groupId,
		OrderByComparator<DDMTemplate> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<DDMTemplate> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] findByGroupId_PrevAndNext(long templateId,
		long groupId, OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, ddmTemplate, groupId,
					orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByGroupId_PrevAndNext(session, ddmTemplate, groupId,
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

	protected DDMTemplate getByGroupId_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

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
			query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByGroupId(long groupId, int start,
		int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<DDMTemplate>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] filterFindByGroupId_PrevAndNext(long templateId,
		long groupId, OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(templateId, groupId,
				orderByComparator);
		}

		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, ddmTemplate,
					groupId, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = filterGetByGroupId_PrevAndNext(session, ddmTemplate,
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

	protected DDMTemplate filterGetByGroupId_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DDMTemplateImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DDMTemplateImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m templates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (DDMTemplate ddmTemplate : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Returns the number of d d m templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

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
	 * Returns the number of d d m templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching d d m templates that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "ddmTemplate.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CLASSPK = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByClassPK",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSPK =
		new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByClassPK",
			new String[] { Long.class.getName() },
			DDMTemplateModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CLASSPK = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByClassPK",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d d m templates where classPK = &#63;.
	 *
	 * @param classPK the class p k
	 * @return the matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByClassPK(long classPK) {
		return findByClassPK(classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByClassPK(long classPK, int start, int end) {
		return findByClassPK(classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByClassPK(long classPK, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return findByClassPK(classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m templates where classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByClassPK(long classPK, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSPK;
			finderArgs = new Object[] { classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CLASSPK;
			finderArgs = new Object[] { classPK, start, end, orderByComparator };
		}

		List<DDMTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplate ddmTemplate : list) {
					if ((classPK != ddmTemplate.getClassPK())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByClassPK_First(long classPK,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByClassPK_First(classPK,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByClassPK_First(long classPK,
		OrderByComparator<DDMTemplate> orderByComparator) {
		List<DDMTemplate> list = findByClassPK(classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByClassPK_Last(long classPK,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByClassPK_Last(classPK, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByClassPK_Last(long classPK,
		OrderByComparator<DDMTemplate> orderByComparator) {
		int count = countByClassPK(classPK);

		if (count == 0) {
			return null;
		}

		List<DDMTemplate> list = findByClassPK(classPK, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where classPK = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] findByClassPK_PrevAndNext(long templateId,
		long classPK, OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByClassPK_PrevAndNext(session, ddmTemplate, classPK,
					orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByClassPK_PrevAndNext(session, ddmTemplate, classPK,
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

	protected DDMTemplate getByClassPK_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

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
			query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m templates where classPK = &#63; from the database.
	 *
	 * @param classPK the class p k
	 */
	@Override
	public void removeByClassPK(long classPK) {
		for (DDMTemplate ddmTemplate : findByClassPK(classPK,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Returns the number of d d m templates where classPK = &#63;.
	 *
	 * @param classPK the class p k
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByClassPK(long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CLASSPK;

		Object[] finderArgs = new Object[] { classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_CLASSPK_CLASSPK_2 = "ddmTemplate.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TEMPLATEKEY =
		new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByTemplateKey",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEKEY =
		new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTemplateKey",
			new String[] { String.class.getName() },
			DDMTemplateModelImpl.TEMPLATEKEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TEMPLATEKEY = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTemplateKey",
			new String[] { String.class.getName() });

	/**
	 * Returns all the d d m templates where templateKey = &#63;.
	 *
	 * @param templateKey the template key
	 * @return the matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByTemplateKey(String templateKey) {
		return findByTemplateKey(templateKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where templateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateKey the template key
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByTemplateKey(String templateKey, int start,
		int end) {
		return findByTemplateKey(templateKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where templateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateKey the template key
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByTemplateKey(String templateKey, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return findByTemplateKey(templateKey, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the d d m templates where templateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateKey the template key
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByTemplateKey(String templateKey, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEKEY;
			finderArgs = new Object[] { templateKey };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_TEMPLATEKEY;
			finderArgs = new Object[] { templateKey, start, end, orderByComparator };
		}

		List<DDMTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplate ddmTemplate : list) {
					if (!Validator.equals(templateKey,
								ddmTemplate.getTemplateKey())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			boolean bindTemplateKey = false;

			if (templateKey == null) {
				query.append(_FINDER_COLUMN_TEMPLATEKEY_TEMPLATEKEY_1);
			}
			else if (templateKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_TEMPLATEKEY_TEMPLATEKEY_3);
			}
			else {
				bindTemplateKey = true;

				query.append(_FINDER_COLUMN_TEMPLATEKEY_TEMPLATEKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindTemplateKey) {
					qPos.add(templateKey);
				}

				if (!pagination) {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where templateKey = &#63;.
	 *
	 * @param templateKey the template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByTemplateKey_First(String templateKey,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByTemplateKey_First(templateKey,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("templateKey=");
		msg.append(templateKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where templateKey = &#63;.
	 *
	 * @param templateKey the template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByTemplateKey_First(String templateKey,
		OrderByComparator<DDMTemplate> orderByComparator) {
		List<DDMTemplate> list = findByTemplateKey(templateKey, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where templateKey = &#63;.
	 *
	 * @param templateKey the template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByTemplateKey_Last(String templateKey,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByTemplateKey_Last(templateKey,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("templateKey=");
		msg.append(templateKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where templateKey = &#63;.
	 *
	 * @param templateKey the template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByTemplateKey_Last(String templateKey,
		OrderByComparator<DDMTemplate> orderByComparator) {
		int count = countByTemplateKey(templateKey);

		if (count == 0) {
			return null;
		}

		List<DDMTemplate> list = findByTemplateKey(templateKey, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where templateKey = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param templateKey the template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] findByTemplateKey_PrevAndNext(long templateId,
		String templateKey, OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByTemplateKey_PrevAndNext(session, ddmTemplate,
					templateKey, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByTemplateKey_PrevAndNext(session, ddmTemplate,
					templateKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate getByTemplateKey_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, String templateKey,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		boolean bindTemplateKey = false;

		if (templateKey == null) {
			query.append(_FINDER_COLUMN_TEMPLATEKEY_TEMPLATEKEY_1);
		}
		else if (templateKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_TEMPLATEKEY_TEMPLATEKEY_3);
		}
		else {
			bindTemplateKey = true;

			query.append(_FINDER_COLUMN_TEMPLATEKEY_TEMPLATEKEY_2);
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
			query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindTemplateKey) {
			qPos.add(templateKey);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m templates where templateKey = &#63; from the database.
	 *
	 * @param templateKey the template key
	 */
	@Override
	public void removeByTemplateKey(String templateKey) {
		for (DDMTemplate ddmTemplate : findByTemplateKey(templateKey,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Returns the number of d d m templates where templateKey = &#63;.
	 *
	 * @param templateKey the template key
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByTemplateKey(String templateKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_TEMPLATEKEY;

		Object[] finderArgs = new Object[] { templateKey };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			boolean bindTemplateKey = false;

			if (templateKey == null) {
				query.append(_FINDER_COLUMN_TEMPLATEKEY_TEMPLATEKEY_1);
			}
			else if (templateKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_TEMPLATEKEY_TEMPLATEKEY_3);
			}
			else {
				bindTemplateKey = true;

				query.append(_FINDER_COLUMN_TEMPLATEKEY_TEMPLATEKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindTemplateKey) {
					qPos.add(templateKey);
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

	private static final String _FINDER_COLUMN_TEMPLATEKEY_TEMPLATEKEY_1 = "ddmTemplate.templateKey IS NULL";
	private static final String _FINDER_COLUMN_TEMPLATEKEY_TEMPLATEKEY_2 = "ddmTemplate.templateKey = ?";
	private static final String _FINDER_COLUMN_TEMPLATEKEY_TEMPLATEKEY_3 = "(ddmTemplate.templateKey IS NULL OR ddmTemplate.templateKey = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TYPE = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByType",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByType",
			new String[] { String.class.getName() },
			DDMTemplateModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TYPE = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByType",
			new String[] { String.class.getName() });

	/**
	 * Returns all the d d m templates where type = &#63;.
	 *
	 * @param type the type
	 * @return the matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByType(String type) {
		return findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByType(String type, int start, int end) {
		return findByType(type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByType(String type, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return findByType(type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m templates where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByType(String type, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE;
			finderArgs = new Object[] { type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_TYPE;
			finderArgs = new Object[] { type, start, end, orderByComparator };
		}

		List<DDMTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplate ddmTemplate : list) {
					if (!Validator.equals(type, ddmTemplate.getType())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			boolean bindType = false;

			if (type == null) {
				query.append(_FINDER_COLUMN_TYPE_TYPE_1);
			}
			else if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_TYPE_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_TYPE_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindType) {
					qPos.add(type);
				}

				if (!pagination) {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByType_First(String type,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByType_First(type, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByType_First(String type,
		OrderByComparator<DDMTemplate> orderByComparator) {
		List<DDMTemplate> list = findByType(type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByType_Last(String type,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByType_Last(type, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByType_Last(String type,
		OrderByComparator<DDMTemplate> orderByComparator) {
		int count = countByType(type);

		if (count == 0) {
			return null;
		}

		List<DDMTemplate> list = findByType(type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where type = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] findByType_PrevAndNext(long templateId, String type,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByType_PrevAndNext(session, ddmTemplate, type,
					orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByType_PrevAndNext(session, ddmTemplate, type,
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

	protected DDMTemplate getByType_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, String type,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_TYPE_TYPE_1);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_TYPE_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_TYPE_TYPE_2);
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
			query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m templates where type = &#63; from the database.
	 *
	 * @param type the type
	 */
	@Override
	public void removeByType(String type) {
		for (DDMTemplate ddmTemplate : findByType(type, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Returns the number of d d m templates where type = &#63;.
	 *
	 * @param type the type
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByType(String type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_TYPE;

		Object[] finderArgs = new Object[] { type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			boolean bindType = false;

			if (type == null) {
				query.append(_FINDER_COLUMN_TYPE_TYPE_1);
			}
			else if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_TYPE_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_TYPE_TYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindType) {
					qPos.add(type);
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

	private static final String _FINDER_COLUMN_TYPE_TYPE_1 = "ddmTemplate.type IS NULL";
	private static final String _FINDER_COLUMN_TYPE_TYPE_2 = "ddmTemplate.type = ?";
	private static final String _FINDER_COLUMN_TYPE_TYPE_3 = "(ddmTemplate.type IS NULL OR ddmTemplate.type = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LANGUAGE = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLanguage",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LANGUAGE =
		new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLanguage",
			new String[] { String.class.getName() },
			DDMTemplateModelImpl.LANGUAGE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LANGUAGE = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLanguage",
			new String[] { String.class.getName() });

	/**
	 * Returns all the d d m templates where language = &#63;.
	 *
	 * @param language the language
	 * @return the matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByLanguage(String language) {
		return findByLanguage(language, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the d d m templates where language = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param language the language
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByLanguage(String language, int start, int end) {
		return findByLanguage(language, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where language = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param language the language
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByLanguage(String language, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return findByLanguage(language, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m templates where language = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param language the language
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByLanguage(String language, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LANGUAGE;
			finderArgs = new Object[] { language };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LANGUAGE;
			finderArgs = new Object[] { language, start, end, orderByComparator };
		}

		List<DDMTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplate ddmTemplate : list) {
					if (!Validator.equals(language, ddmTemplate.getLanguage())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			boolean bindLanguage = false;

			if (language == null) {
				query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_1);
			}
			else if (language.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_3);
			}
			else {
				bindLanguage = true;

				query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLanguage) {
					qPos.add(language);
				}

				if (!pagination) {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where language = &#63;.
	 *
	 * @param language the language
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByLanguage_First(String language,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByLanguage_First(language,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("language=");
		msg.append(language);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where language = &#63;.
	 *
	 * @param language the language
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByLanguage_First(String language,
		OrderByComparator<DDMTemplate> orderByComparator) {
		List<DDMTemplate> list = findByLanguage(language, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where language = &#63;.
	 *
	 * @param language the language
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByLanguage_Last(String language,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByLanguage_Last(language,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("language=");
		msg.append(language);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where language = &#63;.
	 *
	 * @param language the language
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByLanguage_Last(String language,
		OrderByComparator<DDMTemplate> orderByComparator) {
		int count = countByLanguage(language);

		if (count == 0) {
			return null;
		}

		List<DDMTemplate> list = findByLanguage(language, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where language = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param language the language
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] findByLanguage_PrevAndNext(long templateId,
		String language, OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByLanguage_PrevAndNext(session, ddmTemplate,
					language, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByLanguage_PrevAndNext(session, ddmTemplate,
					language, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate getByLanguage_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, String language,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		boolean bindLanguage = false;

		if (language == null) {
			query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_1);
		}
		else if (language.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_3);
		}
		else {
			bindLanguage = true;

			query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_2);
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
			query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindLanguage) {
			qPos.add(language);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m templates where language = &#63; from the database.
	 *
	 * @param language the language
	 */
	@Override
	public void removeByLanguage(String language) {
		for (DDMTemplate ddmTemplate : findByLanguage(language,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Returns the number of d d m templates where language = &#63;.
	 *
	 * @param language the language
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByLanguage(String language) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_LANGUAGE;

		Object[] finderArgs = new Object[] { language };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			boolean bindLanguage = false;

			if (language == null) {
				query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_1);
			}
			else if (language.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_3);
			}
			else {
				bindLanguage = true;

				query.append(_FINDER_COLUMN_LANGUAGE_LANGUAGE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLanguage) {
					qPos.add(language);
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

	private static final String _FINDER_COLUMN_LANGUAGE_LANGUAGE_1 = "ddmTemplate.language IS NULL";
	private static final String _FINDER_COLUMN_LANGUAGE_LANGUAGE_2 = "ddmTemplate.language = ?";
	private static final String _FINDER_COLUMN_LANGUAGE_LANGUAGE_3 = "(ddmTemplate.language IS NULL OR ddmTemplate.language = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_SMALLIMAGEID = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchBySmallImageId",
			new String[] { Long.class.getName() },
			DDMTemplateModelImpl.SMALLIMAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SMALLIMAGEID = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySmallImageId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the d d m template where smallImageId = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchTemplateException} if it could not be found.
	 *
	 * @param smallImageId the small image ID
	 * @return the matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findBySmallImageId(long smallImageId)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchBySmallImageId(smallImageId);

		if (ddmTemplate == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("smallImageId=");
			msg.append(smallImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateException(msg.toString());
		}

		return ddmTemplate;
	}

	/**
	 * Returns the d d m template where smallImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param smallImageId the small image ID
	 * @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchBySmallImageId(long smallImageId) {
		return fetchBySmallImageId(smallImageId, true);
	}

	/**
	 * Returns the d d m template where smallImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param smallImageId the small image ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchBySmallImageId(long smallImageId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { smallImageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
					finderArgs, this);
		}

		if (result instanceof DDMTemplate) {
			DDMTemplate ddmTemplate = (DDMTemplate)result;

			if ((smallImageId != ddmTemplate.getSmallImageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				List<DDMTemplate> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"DDMTemplatePersistenceImpl.fetchBySmallImageId(long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					DDMTemplate ddmTemplate = list.get(0);

					result = ddmTemplate;

					cacheResult(ddmTemplate);

					if ((ddmTemplate.getSmallImageId() != smallImageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
							finderArgs, ddmTemplate);
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
			return (DDMTemplate)result;
		}
	}

	/**
	 * Removes the d d m template where smallImageId = &#63; from the database.
	 *
	 * @param smallImageId the small image ID
	 * @return the d d m template that was removed
	 */
	@Override
	public DDMTemplate removeBySmallImageId(long smallImageId)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = findBySmallImageId(smallImageId);

		return remove(ddmTemplate);
	}

	/**
	 * Returns the number of d d m templates where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countBySmallImageId(long smallImageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SMALLIMAGEID;

		Object[] finderArgs = new Object[] { smallImageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

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

	private static final String _FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2 = "ddmTemplate.smallImageId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			DDMTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			DDMTemplateModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the d d m templates where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C(long groupId, long classNameId) {
		return findByG_C(groupId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C(long groupId, long classNameId,
		int start, int end) {
		return findByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return findByG_C(groupId, classNameId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator,
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

		List<DDMTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplate ddmTemplate : list) {
					if ((groupId != ddmTemplate.getGroupId()) ||
							(classNameId != ddmTemplate.getClassNameId())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
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
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByG_C_First(long groupId, long classNameId,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByG_C_First(groupId, classNameId,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByG_C_First(long groupId, long classNameId,
		OrderByComparator<DDMTemplate> orderByComparator) {
		List<DDMTemplate> list = findByG_C(groupId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByG_C_Last(long groupId, long classNameId,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByG_C_Last(groupId, classNameId,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByG_C_Last(long groupId, long classNameId,
		OrderByComparator<DDMTemplate> orderByComparator) {
		int count = countByG_C(groupId, classNameId);

		if (count == 0) {
			return null;
		}

		List<DDMTemplate> list = findByG_C(groupId, classNameId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] findByG_C_PrevAndNext(long templateId, long groupId,
		long classNameId, OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByG_C_PrevAndNext(session, ddmTemplate, groupId,
					classNameId, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByG_C_PrevAndNext(session, ddmTemplate, groupId,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate getByG_C_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId, long classNameId,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

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
			query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_C(long groupId, long classNameId) {
		return filterFindByG_C(groupId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_C(long groupId, long classNameId,
		int start, int end) {
		return filterFindByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C(groupId, classNameId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			return (List<DDMTemplate>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] filterFindByG_C_PrevAndNext(long templateId,
		long groupId, long classNameId,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_PrevAndNext(templateId, groupId, classNameId,
				orderByComparator);
		}

		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = filterGetByG_C_PrevAndNext(session, ddmTemplate,
					groupId, classNameId, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = filterGetByG_C_PrevAndNext(session, ddmTemplate,
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

	protected DDMTemplate filterGetByG_C_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId, long classNameId,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DDMTemplateImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DDMTemplateImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m templates where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByG_C(long groupId, long classNameId) {
		for (DDMTemplate ddmTemplate : findByG_C(groupId, classNameId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Returns the number of d d m templates where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByG_C(long groupId, long classNameId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C;

		Object[] finderArgs = new Object[] { groupId, classNameId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

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
	 * Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching d d m templates that the user has permission to view
	 */
	@Override
	public int filterCountByG_C(long groupId, long classNameId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C(groupId, classNameId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
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

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "ddmTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CLASSNAMEID_2 = "ddmTemplate.classNameId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_CPK = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_CPK = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_CPK",
			new String[] { Long.class.getName(), Long.class.getName() },
			DDMTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			DDMTemplateModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_CPK = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_CPK",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_CPK = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_CPK",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the d d m templates where groupId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classPK the class p k
	 * @return the matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_CPK(long groupId, long classPK) {
		return findByG_CPK(groupId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where groupId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_CPK(long groupId, long classPK, int start,
		int end) {
		return findByG_CPK(groupId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_CPK(long groupId, long classPK, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return findByG_CPK(groupId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_CPK(long groupId, long classPK, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_CPK;
			finderArgs = new Object[] { groupId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_CPK;
			finderArgs = new Object[] {
					groupId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<DDMTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplate ddmTemplate : list) {
					if ((groupId != ddmTemplate.getGroupId()) ||
							(classPK != ddmTemplate.getClassPK())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_CPK_GROUPID_2);

			query.append(_FINDER_COLUMN_G_CPK_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where groupId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByG_CPK_First(long groupId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByG_CPK_First(groupId, classPK,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where groupId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByG_CPK_First(long groupId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator) {
		List<DDMTemplate> list = findByG_CPK(groupId, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByG_CPK_Last(long groupId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByG_CPK_Last(groupId, classPK,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByG_CPK_Last(long groupId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator) {
		int count = countByG_CPK(groupId, classPK);

		if (count == 0) {
			return null;
		}

		List<DDMTemplate> list = findByG_CPK(groupId, classPK, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classPK = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] findByG_CPK_PrevAndNext(long templateId, long groupId,
		long classPK, OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByG_CPK_PrevAndNext(session, ddmTemplate, groupId,
					classPK, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByG_CPK_PrevAndNext(session, ddmTemplate, groupId,
					classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate getByG_CPK_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_CPK_GROUPID_2);

		query.append(_FINDER_COLUMN_G_CPK_CLASSPK_2);

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
			query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m templates that the user has permission to view where groupId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classPK the class p k
	 * @return the matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_CPK(long groupId, long classPK) {
		return filterFindByG_CPK(groupId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_CPK(long groupId, long classPK,
		int start, int end) {
		return filterFindByG_CPK(groupId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_CPK(long groupId, long classPK,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_CPK(groupId, classPK, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_CPK_GROUPID_2);

		query.append(_FINDER_COLUMN_G_CPK_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classPK);

			return (List<DDMTemplate>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classPK = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] filterFindByG_CPK_PrevAndNext(long templateId,
		long groupId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_CPK_PrevAndNext(templateId, groupId, classPK,
				orderByComparator);
		}

		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = filterGetByG_CPK_PrevAndNext(session, ddmTemplate,
					groupId, classPK, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = filterGetByG_CPK_PrevAndNext(session, ddmTemplate,
					groupId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate filterGetByG_CPK_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_CPK_GROUPID_2);

		query.append(_FINDER_COLUMN_G_CPK_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DDMTemplateImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DDMTemplateImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m templates that the user has permission to view where groupId = any &#63; and classPK = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param classPK the class p k
	 * @return the matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_CPK(long[] groupIds, long classPK) {
		return filterFindByG_CPK(groupIds, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates that the user has permission to view where groupId = any &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_CPK(long[] groupIds, long classPK,
		int start, int end) {
		return filterFindByG_CPK(groupIds, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates that the user has permission to view where groupId = any &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_CPK(long[] groupIds, long classPK,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByG_CPK(groupIds, classPK, start, end, orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.unique(groupIds);

			Arrays.sort(groupIds);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_CPK_GROUPID_7);

			query.append(StringUtil.merge(groupIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_CPK_CLASSPK_2);

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classPK);

			return (List<DDMTemplate>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the d d m templates where groupId = any &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param classPK the class p k
	 * @return the matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_CPK(long[] groupIds, long classPK) {
		return findByG_CPK(groupIds, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where groupId = any &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_CPK(long[] groupIds, long classPK,
		int start, int end) {
		return findByG_CPK(groupIds, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = any &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_CPK(long[] groupIds, long classPK,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return findByG_CPK(groupIds, classPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63; and classPK = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_CPK(long[] groupIds, long classPK,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.unique(groupIds);

			Arrays.sort(groupIds);
		}

		if (groupIds.length == 1) {
			return findByG_CPK(groupIds[0], classPK, start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { StringUtil.merge(groupIds), classPK };
		}
		else {
			finderArgs = new Object[] {
					StringUtil.merge(groupIds), classPK,
					
					start, end, orderByComparator
				};
		}

		List<DDMTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplate>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_CPK,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplate ddmTemplate : list) {
					if (!ArrayUtil.contains(groupIds, ddmTemplate.getGroupId()) ||
							(classPK != ddmTemplate.getClassPK())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			if (groupIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_CPK_GROUPID_7);

				query.append(StringUtil.merge(groupIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_CPK_CLASSPK_2);

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_CPK,
					finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_CPK,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the d d m templates where groupId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classPK the class p k
	 */
	@Override
	public void removeByG_CPK(long groupId, long classPK) {
		for (DDMTemplate ddmTemplate : findByG_CPK(groupId, classPK,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Returns the number of d d m templates where groupId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classPK the class p k
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByG_CPK(long groupId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_CPK;

		Object[] finderArgs = new Object[] { groupId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_CPK_GROUPID_2);

			query.append(_FINDER_COLUMN_G_CPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classPK);

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
	 * Returns the number of d d m templates where groupId = any &#63; and classPK = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param classPK the class p k
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByG_CPK(long[] groupIds, long classPK) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.unique(groupIds);

			Arrays.sort(groupIds);
		}

		Object[] finderArgs = new Object[] { StringUtil.merge(groupIds), classPK };

		Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_CPK,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			if (groupIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_CPK_GROUPID_7);

				query.append(StringUtil.merge(groupIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_CPK_CLASSPK_2);

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_CPK,
					finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_CPK,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classPK the class p k
	 * @return the number of matching d d m templates that the user has permission to view
	 */
	@Override
	public int filterCountByG_CPK(long groupId, long classPK) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_CPK(groupId, classPK);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_CPK_GROUPID_2);

		query.append(_FINDER_COLUMN_G_CPK_CLASSPK_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classPK);

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

	/**
	 * Returns the number of d d m templates that the user has permission to view where groupId = any &#63; and classPK = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param classPK the class p k
	 * @return the number of matching d d m templates that the user has permission to view
	 */
	@Override
	public int filterCountByG_CPK(long[] groupIds, long classPK) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByG_CPK(groupIds, classPK);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.unique(groupIds);

			Arrays.sort(groupIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_DDMTEMPLATE_WHERE);

		if (groupIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_CPK_GROUPID_7);

			query.append(StringUtil.merge(groupIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_CPK_CLASSPK_2);

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_CPK_GROUPID_2 = "ddmTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_CPK_GROUPID_7 = "ddmTemplate.groupId IN (";
	private static final String _FINDER_COLUMN_G_CPK_CLASSPK_2 = "ddmTemplate.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			DDMTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			DDMTemplateModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DDMTemplateModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C_C = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C_C(long groupId, long classNameId,
		long classPK) {
		return findByG_C_C(groupId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end) {
		return findByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return findByG_C_C(groupId, classNameId, classPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C;
			finderArgs = new Object[] { groupId, classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C;
			finderArgs = new Object[] {
					groupId, classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<DDMTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplate ddmTemplate : list) {
					if ((groupId != ddmTemplate.getGroupId()) ||
							(classNameId != ddmTemplate.getClassNameId()) ||
							(classPK != ddmTemplate.getClassPK())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByG_C_C_First(long groupId, long classNameId,
		long classPK, OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByG_C_C_First(groupId, classNameId,
				classPK, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByG_C_C_First(long groupId, long classNameId,
		long classPK, OrderByComparator<DDMTemplate> orderByComparator) {
		List<DDMTemplate> list = findByG_C_C(groupId, classNameId, classPK, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByG_C_C_Last(long groupId, long classNameId,
		long classPK, OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByG_C_C_Last(groupId, classNameId,
				classPK, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByG_C_C_Last(long groupId, long classNameId,
		long classPK, OrderByComparator<DDMTemplate> orderByComparator) {
		int count = countByG_C_C(groupId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<DDMTemplate> list = findByG_C_C(groupId, classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] findByG_C_C_PrevAndNext(long templateId, long groupId,
		long classNameId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByG_C_C_PrevAndNext(session, ddmTemplate, groupId,
					classNameId, classPK, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByG_C_C_PrevAndNext(session, ddmTemplate, groupId,
					classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate getByG_C_C_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId, long classNameId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

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
			query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_C_C(long groupId, long classNameId,
		long classPK) {
		return filterFindByG_C_C(groupId, classNameId, classPK,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end) {
		return filterFindByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C(groupId, classNameId, classPK, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			qPos.add(classPK);

			return (List<DDMTemplate>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] filterFindByG_C_C_PrevAndNext(long templateId,
		long groupId, long classNameId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_PrevAndNext(templateId, groupId, classNameId,
				classPK, orderByComparator);
		}

		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = filterGetByG_C_C_PrevAndNext(session, ddmTemplate,
					groupId, classNameId, classPK, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = filterGetByG_C_C_PrevAndNext(session, ddmTemplate,
					groupId, classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate filterGetByG_C_C_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId, long classNameId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DDMTemplateImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DDMTemplateImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m templates that the user has permission to view where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_C_C(long[] groupIds,
		long classNameId, long classPK) {
		return filterFindByG_C_C(groupIds, classNameId, classPK,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates that the user has permission to view where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_C_C(long[] groupIds,
		long classNameId, long classPK, int start, int end) {
		return filterFindByG_C_C(groupIds, classNameId, classPK, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the d d m templates that the user has permission to view where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_C_C(long[] groupIds,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByG_C_C(groupIds, classNameId, classPK, start, end,
				orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.unique(groupIds);

			Arrays.sort(groupIds);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_7);

			query.append(StringUtil.merge(groupIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			return (List<DDMTemplate>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the d d m templates where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C_C(long[] groupIds, long classNameId,
		long classPK) {
		return findByG_C_C(groupIds, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C_C(long[] groupIds, long classNameId,
		long classPK, int start, int end) {
		return findByG_C_C(groupIds, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C_C(long[] groupIds, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return findByG_C_C(groupIds, classNameId, classPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C_C(long[] groupIds, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.unique(groupIds);

			Arrays.sort(groupIds);
		}

		if (groupIds.length == 1) {
			return findByG_C_C(groupIds[0], classNameId, classPK, start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] {
					StringUtil.merge(groupIds), classNameId, classPK
				};
		}
		else {
			finderArgs = new Object[] {
					StringUtil.merge(groupIds), classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<DDMTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplate>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplate ddmTemplate : list) {
					if (!ArrayUtil.contains(groupIds, ddmTemplate.getGroupId()) ||
							(classNameId != ddmTemplate.getClassNameId()) ||
							(classPK != ddmTemplate.getClassPK())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			if (groupIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_C_C_GROUPID_7);

				query.append(StringUtil.merge(groupIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
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
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C,
					finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 */
	@Override
	public void removeByG_C_C(long groupId, long classNameId, long classPK) {
		for (DDMTemplate ddmTemplate : findByG_C_C(groupId, classNameId,
				classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Returns the number of d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C;

		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

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
	 * Returns the number of d d m templates where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByG_C_C(long[] groupIds, long classNameId, long classPK) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.unique(groupIds);

			Arrays.sort(groupIds);
		}

		Object[] finderArgs = new Object[] {
				StringUtil.merge(groupIds), classNameId, classPK
			};

		Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			if (groupIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_C_C_GROUPID_7);

				query.append(StringUtil.merge(groupIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C_C,
					finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C_C,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching d d m templates that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C(long groupId, long classNameId, long classPK) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C(groupId, classNameId, classPK);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
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

			qPos.add(classPK);

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

	/**
	 * Returns the number of d d m templates that the user has permission to view where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching d d m templates that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C(long[] groupIds, long classNameId,
		long classPK) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByG_C_C(groupIds, classNameId, classPK);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.unique(groupIds);

			Arrays.sort(groupIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_DDMTEMPLATE_WHERE);

		if (groupIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_7);

			query.append(StringUtil.merge(groupIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 = "ddmTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_GROUPID_7 = "ddmTemplate.groupId IN (";
	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 = "ddmTemplate.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 = "ddmTemplate.classPK = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_T = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			DDMTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			DDMTemplateModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DDMTemplateModelImpl.TEMPLATEKEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_T = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the d d m template where groupId = &#63; and classNameId = &#63; and templateKey = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchTemplateException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param templateKey the template key
	 * @return the matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByG_C_T(long groupId, long classNameId,
		String templateKey) throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByG_C_T(groupId, classNameId, templateKey);

		if (ddmTemplate == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", templateKey=");
			msg.append(templateKey);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateException(msg.toString());
		}

		return ddmTemplate;
	}

	/**
	 * Returns the d d m template where groupId = &#63; and classNameId = &#63; and templateKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param templateKey the template key
	 * @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByG_C_T(long groupId, long classNameId,
		String templateKey) {
		return fetchByG_C_T(groupId, classNameId, templateKey, true);
	}

	/**
	 * Returns the d d m template where groupId = &#63; and classNameId = &#63; and templateKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param templateKey the template key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByG_C_T(long groupId, long classNameId,
		String templateKey, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, classNameId, templateKey };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_T,
					finderArgs, this);
		}

		if (result instanceof DDMTemplate) {
			DDMTemplate ddmTemplate = (DDMTemplate)result;

			if ((groupId != ddmTemplate.getGroupId()) ||
					(classNameId != ddmTemplate.getClassNameId()) ||
					!Validator.equals(templateKey, ddmTemplate.getTemplateKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_C_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_T_CLASSNAMEID_2);

			boolean bindTemplateKey = false;

			if (templateKey == null) {
				query.append(_FINDER_COLUMN_G_C_T_TEMPLATEKEY_1);
			}
			else if (templateKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_T_TEMPLATEKEY_3);
			}
			else {
				bindTemplateKey = true;

				query.append(_FINDER_COLUMN_G_C_T_TEMPLATEKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (bindTemplateKey) {
					qPos.add(templateKey);
				}

				List<DDMTemplate> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_T,
						finderArgs, list);
				}
				else {
					DDMTemplate ddmTemplate = list.get(0);

					result = ddmTemplate;

					cacheResult(ddmTemplate);

					if ((ddmTemplate.getGroupId() != groupId) ||
							(ddmTemplate.getClassNameId() != classNameId) ||
							(ddmTemplate.getTemplateKey() == null) ||
							!ddmTemplate.getTemplateKey().equals(templateKey)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_T,
							finderArgs, ddmTemplate);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_T, finderArgs);

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
			return (DDMTemplate)result;
		}
	}

	/**
	 * Removes the d d m template where groupId = &#63; and classNameId = &#63; and templateKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param templateKey the template key
	 * @return the d d m template that was removed
	 */
	@Override
	public DDMTemplate removeByG_C_T(long groupId, long classNameId,
		String templateKey) throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = findByG_C_T(groupId, classNameId, templateKey);

		return remove(ddmTemplate);
	}

	/**
	 * Returns the number of d d m templates where groupId = &#63; and classNameId = &#63; and templateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param templateKey the template key
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByG_C_T(long groupId, long classNameId, String templateKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_T;

		Object[] finderArgs = new Object[] { groupId, classNameId, templateKey };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_C_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_T_CLASSNAMEID_2);

			boolean bindTemplateKey = false;

			if (templateKey == null) {
				query.append(_FINDER_COLUMN_G_C_T_TEMPLATEKEY_1);
			}
			else if (templateKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_T_TEMPLATEKEY_3);
			}
			else {
				bindTemplateKey = true;

				query.append(_FINDER_COLUMN_G_C_T_TEMPLATEKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (bindTemplateKey) {
					qPos.add(templateKey);
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

	private static final String _FINDER_COLUMN_G_C_T_GROUPID_2 = "ddmTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_T_CLASSNAMEID_2 = "ddmTemplate.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_T_TEMPLATEKEY_1 = "ddmTemplate.templateKey IS NULL";
	private static final String _FINDER_COLUMN_G_C_T_TEMPLATEKEY_2 = "ddmTemplate.templateKey = ?";
	private static final String _FINDER_COLUMN_G_C_T_TEMPLATEKEY_3 = "(ddmTemplate.templateKey IS NULL OR ddmTemplate.templateKey = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_T = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			DDMTemplateModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DDMTemplateModelImpl.CLASSPK_COLUMN_BITMASK |
			DDMTemplateModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_T = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @return the matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByC_C_T(long classNameId, long classPK,
		String type) {
		return findByC_C_T(classNameId, classPK, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByC_C_T(long classNameId, long classPK,
		String type, int start, int end) {
		return findByC_C_T(classNameId, classPK, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByC_C_T(long classNameId, long classPK,
		String type, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return findByC_C_T(classNameId, classPK, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByC_C_T(long classNameId, long classPK,
		String type, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T;
			finderArgs = new Object[] { classNameId, classPK, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_T;
			finderArgs = new Object[] {
					classNameId, classPK, type,
					
					start, end, orderByComparator
				};
		}

		List<DDMTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplate ddmTemplate : list) {
					if ((classNameId != ddmTemplate.getClassNameId()) ||
							(classPK != ddmTemplate.getClassPK()) ||
							!Validator.equals(type, ddmTemplate.getType())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

			boolean bindType = false;

			if (type == null) {
				query.append(_FINDER_COLUMN_C_C_T_TYPE_1);
			}
			else if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_C_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_C_C_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindType) {
					qPos.add(type);
				}

				if (!pagination) {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByC_C_T_First(long classNameId, long classPK,
		String type, OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByC_C_T_First(classNameId, classPK,
				type, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByC_C_T_First(long classNameId, long classPK,
		String type, OrderByComparator<DDMTemplate> orderByComparator) {
		List<DDMTemplate> list = findByC_C_T(classNameId, classPK, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByC_C_T_Last(long classNameId, long classPK,
		String type, OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByC_C_T_Last(classNameId, classPK, type,
				orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByC_C_T_Last(long classNameId, long classPK,
		String type, OrderByComparator<DDMTemplate> orderByComparator) {
		int count = countByC_C_T(classNameId, classPK, type);

		if (count == 0) {
			return null;
		}

		List<DDMTemplate> list = findByC_C_T(classNameId, classPK, type,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] findByC_C_T_PrevAndNext(long templateId,
		long classNameId, long classPK, String type,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByC_C_T_PrevAndNext(session, ddmTemplate,
					classNameId, classPK, type, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByC_C_T_PrevAndNext(session, ddmTemplate,
					classNameId, classPK, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate getByC_C_T_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long classNameId, long classPK, String type,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_C_C_T_TYPE_1);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_C_C_T_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_C_C_T_TYPE_2);
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
			query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 */
	@Override
	public void removeByC_C_T(long classNameId, long classPK, String type) {
		for (DDMTemplate ddmTemplate : findByC_C_T(classNameId, classPK, type,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Returns the number of d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByC_C_T(long classNameId, long classPK, String type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C_T;

		Object[] finderArgs = new Object[] { classNameId, classPK, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

			boolean bindType = false;

			if (type == null) {
				query.append(_FINDER_COLUMN_C_C_T_TYPE_1);
			}
			else if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_C_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_C_C_T_TYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindType) {
					qPos.add(type);
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

	private static final String _FINDER_COLUMN_C_C_T_CLASSNAMEID_2 = "ddmTemplate.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_CLASSPK_2 = "ddmTemplate.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_TYPE_1 = "ddmTemplate.type IS NULL";
	private static final String _FINDER_COLUMN_C_C_T_TYPE_2 = "ddmTemplate.type = ?";
	private static final String _FINDER_COLUMN_C_C_T_TYPE_3 = "(ddmTemplate.type IS NULL OR ddmTemplate.type = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C_T = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_T =
		new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			DDMTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			DDMTemplateModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DDMTemplateModelImpl.CLASSPK_COLUMN_BITMASK |
			DDMTemplateModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_T = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @return the matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C_C_T(long groupId, long classNameId,
		long classPK, String type) {
		return findByG_C_C_T(groupId, classNameId, classPK, type,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C_C_T(long groupId, long classNameId,
		long classPK, String type, int start, int end) {
		return findByG_C_C_T(groupId, classNameId, classPK, type, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C_C_T(long groupId, long classNameId,
		long classPK, String type, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return findByG_C_C_T(groupId, classNameId, classPK, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C_C_T(long groupId, long classNameId,
		long classPK, String type, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_T;
			finderArgs = new Object[] { groupId, classNameId, classPK, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C_T;
			finderArgs = new Object[] {
					groupId, classNameId, classPK, type,
					
					start, end, orderByComparator
				};
		}

		List<DDMTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplate ddmTemplate : list) {
					if ((groupId != ddmTemplate.getGroupId()) ||
							(classNameId != ddmTemplate.getClassNameId()) ||
							(classPK != ddmTemplate.getClassPK()) ||
							!Validator.equals(type, ddmTemplate.getType())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_T_CLASSPK_2);

			boolean bindType = false;

			if (type == null) {
				query.append(_FINDER_COLUMN_G_C_C_T_TYPE_1);
			}
			else if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_C_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_C_C_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindType) {
					qPos.add(type);
				}

				if (!pagination) {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByG_C_C_T_First(long groupId, long classNameId,
		long classPK, String type,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByG_C_C_T_First(groupId, classNameId,
				classPK, type, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByG_C_C_T_First(long groupId, long classNameId,
		long classPK, String type,
		OrderByComparator<DDMTemplate> orderByComparator) {
		List<DDMTemplate> list = findByG_C_C_T(groupId, classNameId, classPK,
				type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByG_C_C_T_Last(long groupId, long classNameId,
		long classPK, String type,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByG_C_C_T_Last(groupId, classNameId,
				classPK, type, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByG_C_C_T_Last(long groupId, long classNameId,
		long classPK, String type,
		OrderByComparator<DDMTemplate> orderByComparator) {
		int count = countByG_C_C_T(groupId, classNameId, classPK, type);

		if (count == 0) {
			return null;
		}

		List<DDMTemplate> list = findByG_C_C_T(groupId, classNameId, classPK,
				type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] findByG_C_C_T_PrevAndNext(long templateId,
		long groupId, long classNameId, long classPK, String type,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByG_C_C_T_PrevAndNext(session, ddmTemplate, groupId,
					classNameId, classPK, type, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByG_C_C_T_PrevAndNext(session, ddmTemplate, groupId,
					classNameId, classPK, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplate getByG_C_C_T_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId, long classNameId, long classPK,
		String type, OrderByComparator<DDMTemplate> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_CLASSPK_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_C_C_T_TYPE_1);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_C_T_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_C_C_T_TYPE_2);
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
			query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @return the matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_C_C_T(long groupId,
		long classNameId, long classPK, String type) {
		return filterFindByG_C_C_T(groupId, classNameId, classPK, type,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_C_C_T(long groupId,
		long classNameId, long classPK, String type, int start, int end) {
		return filterFindByG_C_C_T(groupId, classNameId, classPK, type, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_C_C_T(long groupId,
		long classNameId, long classPK, String type, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_T(groupId, classNameId, classPK, type, start,
				end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_CLASSPK_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_C_C_T_TYPE_1_SQL);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_C_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_C_C_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			qPos.add(classPK);

			if (bindType) {
				qPos.add(type);
			}

			return (List<DDMTemplate>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] filterFindByG_C_C_T_PrevAndNext(long templateId,
		long groupId, long classNameId, long classPK, String type,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_T_PrevAndNext(templateId, groupId, classNameId,
				classPK, type, orderByComparator);
		}

		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = filterGetByG_C_C_T_PrevAndNext(session, ddmTemplate,
					groupId, classNameId, classPK, type, orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = filterGetByG_C_C_T_PrevAndNext(session, ddmTemplate,
					groupId, classNameId, classPK, type, orderByComparator,
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

	protected DDMTemplate filterGetByG_C_C_T_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId, long classNameId, long classPK,
		String type, OrderByComparator<DDMTemplate> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_CLASSPK_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_C_C_T_TYPE_1_SQL);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_C_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_C_C_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DDMTemplateImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DDMTemplateImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 */
	@Override
	public void removeByG_C_C_T(long groupId, long classNameId, long classPK,
		String type) {
		for (DDMTemplate ddmTemplate : findByG_C_C_T(groupId, classNameId,
				classPK, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Returns the number of d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByG_C_C_T(long groupId, long classNameId, long classPK,
		String type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C_T;

		Object[] finderArgs = new Object[] { groupId, classNameId, classPK, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_T_CLASSPK_2);

			boolean bindType = false;

			if (type == null) {
				query.append(_FINDER_COLUMN_G_C_C_T_TYPE_1);
			}
			else if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_C_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_C_C_T_TYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindType) {
					qPos.add(type);
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
	 * Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @return the number of matching d d m templates that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C_T(long groupId, long classNameId,
		long classPK, String type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C_T(groupId, classNameId, classPK, type);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_CLASSPK_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_C_C_T_TYPE_1_SQL);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_C_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_C_C_T_TYPE_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
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

			qPos.add(classPK);

			if (bindType) {
				qPos.add(type);
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

	private static final String _FINDER_COLUMN_G_C_C_T_GROUPID_2 = "ddmTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2 = "ddmTemplate.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_T_CLASSPK_2 = "ddmTemplate.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_T_TYPE_1 = "ddmTemplate.type IS NULL";
	private static final String _FINDER_COLUMN_G_C_C_T_TYPE_2 = "ddmTemplate.type = ?";
	private static final String _FINDER_COLUMN_G_C_C_T_TYPE_3 = "(ddmTemplate.type IS NULL OR ddmTemplate.type = '')";
	private static final String _FINDER_COLUMN_G_C_C_T_TYPE_1_SQL = "ddmTemplate.type_ IS NULL";
	private static final String _FINDER_COLUMN_G_C_C_T_TYPE_2_SQL = "ddmTemplate.type_ = ?";
	private static final String _FINDER_COLUMN_G_C_C_T_TYPE_3_SQL = "(ddmTemplate.type_ IS NULL OR ddmTemplate.type_ = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C_T_M =
		new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_T_M",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_T_M =
		new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, DDMTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_T_M",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			DDMTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			DDMTemplateModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DDMTemplateModelImpl.CLASSPK_COLUMN_BITMASK |
			DDMTemplateModelImpl.TYPE_COLUMN_BITMASK |
			DDMTemplateModelImpl.MODE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_T_M = new FinderPath(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_T_M",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			});

	/**
	 * Returns all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @return the matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C_C_T_M(long groupId, long classNameId,
		long classPK, String type, String mode) {
		return findByG_C_C_T_M(groupId, classNameId, classPK, type, mode,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C_C_T_M(long groupId, long classNameId,
		long classPK, String type, String mode, int start, int end) {
		return findByG_C_C_T_M(groupId, classNameId, classPK, type, mode,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C_C_T_M(long groupId, long classNameId,
		long classPK, String type, String mode, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return findByG_C_C_T_M(groupId, classNameId, classPK, type, mode,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m templates
	 */
	@Override
	public List<DDMTemplate> findByG_C_C_T_M(long groupId, long classNameId,
		long classPK, String type, String mode, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_T_M;
			finderArgs = new Object[] { groupId, classNameId, classPK, type, mode };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C_T_M;
			finderArgs = new Object[] {
					groupId, classNameId, classPK, type, mode,
					
					start, end, orderByComparator
				};
		}

		List<DDMTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplate ddmTemplate : list) {
					if ((groupId != ddmTemplate.getGroupId()) ||
							(classNameId != ddmTemplate.getClassNameId()) ||
							(classPK != ddmTemplate.getClassPK()) ||
							!Validator.equals(type, ddmTemplate.getType()) ||
							!Validator.equals(mode, ddmTemplate.getMode())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(7 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(7);
			}

			query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_T_M_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_T_M_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_T_M_CLASSPK_2);

			boolean bindType = false;

			if (type == null) {
				query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_1);
			}
			else if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_2);
			}

			boolean bindMode = false;

			if (mode == null) {
				query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_1);
			}
			else if (mode.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_3);
			}
			else {
				bindMode = true;

				query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindType) {
					qPos.add(type);
				}

				if (bindMode) {
					qPos.add(mode);
				}

				if (!pagination) {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByG_C_C_T_M_First(long groupId, long classNameId,
		long classPK, String type, String mode,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByG_C_C_T_M_First(groupId, classNameId,
				classPK, type, mode, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(", mode=");
		msg.append(mode);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByG_C_C_T_M_First(long groupId, long classNameId,
		long classPK, String type, String mode,
		OrderByComparator<DDMTemplate> orderByComparator) {
		List<DDMTemplate> list = findByG_C_C_T_M(groupId, classNameId, classPK,
				type, mode, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate findByG_C_C_T_M_Last(long groupId, long classNameId,
		long classPK, String type, String mode,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByG_C_C_T_M_Last(groupId, classNameId,
				classPK, type, mode, orderByComparator);

		if (ddmTemplate != null) {
			return ddmTemplate;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(", mode=");
		msg.append(mode);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	 */
	@Override
	public DDMTemplate fetchByG_C_C_T_M_Last(long groupId, long classNameId,
		long classPK, String type, String mode,
		OrderByComparator<DDMTemplate> orderByComparator) {
		int count = countByG_C_C_T_M(groupId, classNameId, classPK, type, mode);

		if (count == 0) {
			return null;
		}

		List<DDMTemplate> list = findByG_C_C_T_M(groupId, classNameId, classPK,
				type, mode, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] findByG_C_C_T_M_PrevAndNext(long templateId,
		long groupId, long classNameId, long classPK, String type, String mode,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = getByG_C_C_T_M_PrevAndNext(session, ddmTemplate,
					groupId, classNameId, classPK, type, mode,
					orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = getByG_C_C_T_M_PrevAndNext(session, ddmTemplate,
					groupId, classNameId, classPK, type, mode,
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

	protected DDMTemplate getByG_C_C_T_M_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId, long classNameId, long classPK,
		String type, String mode,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_T_M_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_M_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_M_CLASSPK_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_1);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_2);
		}

		boolean bindMode = false;

		if (mode == null) {
			query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_1);
		}
		else if (mode.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_3);
		}
		else {
			bindMode = true;

			query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_2);
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
			query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (bindType) {
			qPos.add(type);
		}

		if (bindMode) {
			qPos.add(mode);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @return the matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_C_C_T_M(long groupId,
		long classNameId, long classPK, String type, String mode) {
		return filterFindByG_C_C_T_M(groupId, classNameId, classPK, type, mode,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_C_C_T_M(long groupId,
		long classNameId, long classPK, String type, String mode, int start,
		int end) {
		return filterFindByG_C_C_T_M(groupId, classNameId, classPK, type, mode,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m templates that the user has permission to view
	 */
	@Override
	public List<DDMTemplate> filterFindByG_C_C_T_M(long groupId,
		long classNameId, long classPK, String type, String mode, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_T_M(groupId, classNameId, classPK, type, mode,
				start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_T_M_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_M_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_M_CLASSPK_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_1_SQL);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_2_SQL);
		}

		boolean bindMode = false;

		if (mode == null) {
			query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_1_SQL);
		}
		else if (mode.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_3_SQL);
		}
		else {
			bindMode = true;

			query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			qPos.add(classPK);

			if (bindType) {
				qPos.add(type);
			}

			if (bindMode) {
				qPos.add(mode);
			}

			return (List<DDMTemplate>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param templateId the primary key of the current d d m template
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate[] filterFindByG_C_C_T_M_PrevAndNext(long templateId,
		long groupId, long classNameId, long classPK, String type, String mode,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_T_M_PrevAndNext(templateId, groupId,
				classNameId, classPK, type, mode, orderByComparator);
		}

		DDMTemplate ddmTemplate = findByPrimaryKey(templateId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplate[] array = new DDMTemplateImpl[3];

			array[0] = filterGetByG_C_C_T_M_PrevAndNext(session, ddmTemplate,
					groupId, classNameId, classPK, type, mode,
					orderByComparator, true);

			array[1] = ddmTemplate;

			array[2] = filterGetByG_C_C_T_M_PrevAndNext(session, ddmTemplate,
					groupId, classNameId, classPK, type, mode,
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

	protected DDMTemplate filterGetByG_C_C_T_M_PrevAndNext(Session session,
		DDMTemplate ddmTemplate, long groupId, long classNameId, long classPK,
		String type, String mode,
		OrderByComparator<DDMTemplate> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_T_M_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_M_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_M_CLASSPK_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_1_SQL);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_2_SQL);
		}

		boolean bindMode = false;

		if (mode == null) {
			query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_1_SQL);
		}
		else if (mode.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_3_SQL);
		}
		else {
			bindMode = true;

			query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DDMTemplateImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DDMTemplateImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (bindType) {
			qPos.add(type);
		}

		if (bindMode) {
			qPos.add(mode);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 */
	@Override
	public void removeByG_C_C_T_M(long groupId, long classNameId, long classPK,
		String type, String mode) {
		for (DDMTemplate ddmTemplate : findByG_C_C_T_M(groupId, classNameId,
				classPK, type, mode, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Returns the number of d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @return the number of matching d d m templates
	 */
	@Override
	public int countByG_C_C_T_M(long groupId, long classNameId, long classPK,
		String type, String mode) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C_T_M;

		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, type, mode
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_DDMTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_T_M_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_T_M_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_T_M_CLASSPK_2);

			boolean bindType = false;

			if (type == null) {
				query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_1);
			}
			else if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_2);
			}

			boolean bindMode = false;

			if (mode == null) {
				query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_1);
			}
			else if (mode.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_3);
			}
			else {
				bindMode = true;

				query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindType) {
					qPos.add(type);
				}

				if (bindMode) {
					qPos.add(mode);
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
	 * Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param mode the mode
	 * @return the number of matching d d m templates that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C_T_M(long groupId, long classNameId,
		long classPK, String type, String mode) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C_T_M(groupId, classNameId, classPK, type, mode);
		}

		StringBundler query = new StringBundler(6);

		query.append(_FILTER_SQL_COUNT_DDMTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_T_M_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_M_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_M_CLASSPK_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_1_SQL);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_C_C_T_M_TYPE_2_SQL);
		}

		boolean bindMode = false;

		if (mode == null) {
			query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_1_SQL);
		}
		else if (mode.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_3_SQL);
		}
		else {
			bindMode = true;

			query.append(_FINDER_COLUMN_G_C_C_T_M_MODE_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMTemplate.class.getName(),
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

			qPos.add(classPK);

			if (bindType) {
				qPos.add(type);
			}

			if (bindMode) {
				qPos.add(mode);
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

	private static final String _FINDER_COLUMN_G_C_C_T_M_GROUPID_2 = "ddmTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_T_M_CLASSNAMEID_2 = "ddmTemplate.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_T_M_CLASSPK_2 = "ddmTemplate.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_T_M_TYPE_1 = "ddmTemplate.type IS NULL AND ";
	private static final String _FINDER_COLUMN_G_C_C_T_M_TYPE_2 = "ddmTemplate.type = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_T_M_TYPE_3 = "(ddmTemplate.type IS NULL OR ddmTemplate.type = '') AND ";
	private static final String _FINDER_COLUMN_G_C_C_T_M_TYPE_1_SQL = "ddmTemplate.type_ IS NULL AND ";
	private static final String _FINDER_COLUMN_G_C_C_T_M_TYPE_2_SQL = "ddmTemplate.type_ = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_T_M_TYPE_3_SQL = "(ddmTemplate.type_ IS NULL OR ddmTemplate.type_ = '') AND ";
	private static final String _FINDER_COLUMN_G_C_C_T_M_MODE_1 = "ddmTemplate.mode IS NULL";
	private static final String _FINDER_COLUMN_G_C_C_T_M_MODE_2 = "ddmTemplate.mode = ?";
	private static final String _FINDER_COLUMN_G_C_C_T_M_MODE_3 = "(ddmTemplate.mode IS NULL OR ddmTemplate.mode = '')";
	private static final String _FINDER_COLUMN_G_C_C_T_M_MODE_1_SQL = "ddmTemplate.mode_ IS NULL";
	private static final String _FINDER_COLUMN_G_C_C_T_M_MODE_2_SQL = "ddmTemplate.mode_ = ?";
	private static final String _FINDER_COLUMN_G_C_C_T_M_MODE_3_SQL = "(ddmTemplate.mode_ IS NULL OR ddmTemplate.mode_ = '')";

	public DDMTemplatePersistenceImpl() {
		setModelClass(DDMTemplate.class);
	}

	/**
	 * Caches the d d m template in the entity cache if it is enabled.
	 *
	 * @param ddmTemplate the d d m template
	 */
	@Override
	public void cacheResult(DDMTemplate ddmTemplate) {
		entityCache.putResult(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateImpl.class, ddmTemplate.getPrimaryKey(), ddmTemplate);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { ddmTemplate.getUuid(), ddmTemplate.getGroupId() },
			ddmTemplate);

		finderCache.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
			new Object[] { ddmTemplate.getSmallImageId() }, ddmTemplate);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_T,
			new Object[] {
				ddmTemplate.getGroupId(), ddmTemplate.getClassNameId(),
				ddmTemplate.getTemplateKey()
			}, ddmTemplate);

		ddmTemplate.resetOriginalValues();
	}

	/**
	 * Caches the d d m templates in the entity cache if it is enabled.
	 *
	 * @param ddmTemplates the d d m templates
	 */
	@Override
	public void cacheResult(List<DDMTemplate> ddmTemplates) {
		for (DDMTemplate ddmTemplate : ddmTemplates) {
			if (entityCache.getResult(
						DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
						DDMTemplateImpl.class, ddmTemplate.getPrimaryKey()) == null) {
				cacheResult(ddmTemplate);
			}
			else {
				ddmTemplate.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d d m templates.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDMTemplateImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d d m template.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMTemplate ddmTemplate) {
		entityCache.removeResult(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateImpl.class, ddmTemplate.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DDMTemplateModelImpl)ddmTemplate);
	}

	@Override
	public void clearCache(List<DDMTemplate> ddmTemplates) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMTemplate ddmTemplate : ddmTemplates) {
			entityCache.removeResult(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateImpl.class, ddmTemplate.getPrimaryKey());

			clearUniqueFindersCache((DDMTemplateModelImpl)ddmTemplate);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMTemplateModelImpl ddmTemplateModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					ddmTemplateModelImpl.getUuid(),
					ddmTemplateModelImpl.getGroupId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				ddmTemplateModelImpl);

			args = new Object[] { ddmTemplateModelImpl.getSmallImageId() };

			finderCache.putResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID, args,
				ddmTemplateModelImpl);

			args = new Object[] {
					ddmTemplateModelImpl.getGroupId(),
					ddmTemplateModelImpl.getClassNameId(),
					ddmTemplateModelImpl.getTemplateKey()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_T, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_T, args,
				ddmTemplateModelImpl);
		}
		else {
			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getUuid(),
						ddmTemplateModelImpl.getGroupId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					ddmTemplateModelImpl);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_SMALLIMAGEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getSmallImageId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID, args,
					ddmTemplateModelImpl);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_C_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getGroupId(),
						ddmTemplateModelImpl.getClassNameId(),
						ddmTemplateModelImpl.getTemplateKey()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_T, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_T, args,
					ddmTemplateModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		DDMTemplateModelImpl ddmTemplateModelImpl) {
		Object[] args = new Object[] {
				ddmTemplateModelImpl.getUuid(),
				ddmTemplateModelImpl.getGroupId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((ddmTemplateModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					ddmTemplateModelImpl.getOriginalUuid(),
					ddmTemplateModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		args = new Object[] { ddmTemplateModelImpl.getSmallImageId() };

		finderCache.removeResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID, args);

		if ((ddmTemplateModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_SMALLIMAGEID.getColumnBitmask()) != 0) {
			args = new Object[] { ddmTemplateModelImpl.getOriginalSmallImageId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID, args);
		}

		args = new Object[] {
				ddmTemplateModelImpl.getGroupId(),
				ddmTemplateModelImpl.getClassNameId(),
				ddmTemplateModelImpl.getTemplateKey()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_T, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_T, args);

		if ((ddmTemplateModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_T.getColumnBitmask()) != 0) {
			args = new Object[] {
					ddmTemplateModelImpl.getOriginalGroupId(),
					ddmTemplateModelImpl.getOriginalClassNameId(),
					ddmTemplateModelImpl.getOriginalTemplateKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_T, args);
		}
	}

	/**
	 * Creates a new d d m template with the primary key. Does not add the d d m template to the database.
	 *
	 * @param templateId the primary key for the new d d m template
	 * @return the new d d m template
	 */
	@Override
	public DDMTemplate create(long templateId) {
		DDMTemplate ddmTemplate = new DDMTemplateImpl();

		ddmTemplate.setNew(true);
		ddmTemplate.setPrimaryKey(templateId);

		String uuid = PortalUUIDUtil.generate();

		ddmTemplate.setUuid(uuid);

		return ddmTemplate;
	}

	/**
	 * Removes the d d m template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param templateId the primary key of the d d m template
	 * @return the d d m template that was removed
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate remove(long templateId) throws NoSuchTemplateException {
		return remove((Serializable)templateId);
	}

	/**
	 * Removes the d d m template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m template
	 * @return the d d m template that was removed
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate remove(Serializable primaryKey)
		throws NoSuchTemplateException {
		Session session = null;

		try {
			session = openSession();

			DDMTemplate ddmTemplate = (DDMTemplate)session.get(DDMTemplateImpl.class,
					primaryKey);

			if (ddmTemplate == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddmTemplate);
		}
		catch (NoSuchTemplateException nsee) {
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
	protected DDMTemplate removeImpl(DDMTemplate ddmTemplate) {
		ddmTemplate = toUnwrappedModel(ddmTemplate);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmTemplate)) {
				ddmTemplate = (DDMTemplate)session.get(DDMTemplateImpl.class,
						ddmTemplate.getPrimaryKeyObj());
			}

			if (ddmTemplate != null) {
				session.delete(ddmTemplate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddmTemplate != null) {
			clearCache(ddmTemplate);
		}

		return ddmTemplate;
	}

	@Override
	public DDMTemplate updateImpl(DDMTemplate ddmTemplate) {
		ddmTemplate = toUnwrappedModel(ddmTemplate);

		boolean isNew = ddmTemplate.isNew();

		DDMTemplateModelImpl ddmTemplateModelImpl = (DDMTemplateModelImpl)ddmTemplate;

		if (Validator.isNull(ddmTemplate.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddmTemplate.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (ddmTemplate.getCreateDate() == null)) {
			if (serviceContext == null) {
				ddmTemplate.setCreateDate(now);
			}
			else {
				ddmTemplate.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!ddmTemplateModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ddmTemplate.setModifiedDate(now);
			}
			else {
				ddmTemplate.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ddmTemplate.isNew()) {
				session.save(ddmTemplate);

				ddmTemplate.setNew(false);
			}
			else {
				ddmTemplate = (DDMTemplate)session.merge(ddmTemplate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DDMTemplateModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { ddmTemplateModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalUuid(),
						ddmTemplateModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						ddmTemplateModelImpl.getUuid(),
						ddmTemplateModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { ddmTemplateModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSPK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CLASSPK, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSPK,
					args);

				args = new Object[] { ddmTemplateModelImpl.getClassPK() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CLASSPK, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSPK,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEKEY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalTemplateKey()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TEMPLATEKEY, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEKEY,
					args);

				args = new Object[] { ddmTemplateModelImpl.getTemplateKey() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TEMPLATEKEY, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEKEY,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TYPE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE,
					args);

				args = new Object[] { ddmTemplateModelImpl.getType() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TYPE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LANGUAGE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalLanguage()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LANGUAGE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LANGUAGE,
					args);

				args = new Object[] { ddmTemplateModelImpl.getLanguage() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LANGUAGE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LANGUAGE,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalGroupId(),
						ddmTemplateModelImpl.getOriginalClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);

				args = new Object[] {
						ddmTemplateModelImpl.getGroupId(),
						ddmTemplateModelImpl.getClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_CPK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalGroupId(),
						ddmTemplateModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_CPK, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_CPK,
					args);

				args = new Object[] {
						ddmTemplateModelImpl.getGroupId(),
						ddmTemplateModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_CPK, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_CPK,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalGroupId(),
						ddmTemplateModelImpl.getOriginalClassNameId(),
						ddmTemplateModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C,
					args);

				args = new Object[] {
						ddmTemplateModelImpl.getGroupId(),
						ddmTemplateModelImpl.getClassNameId(),
						ddmTemplateModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalClassNameId(),
						ddmTemplateModelImpl.getOriginalClassPK(),
						ddmTemplateModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T,
					args);

				args = new Object[] {
						ddmTemplateModelImpl.getClassNameId(),
						ddmTemplateModelImpl.getClassPK(),
						ddmTemplateModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalGroupId(),
						ddmTemplateModelImpl.getOriginalClassNameId(),
						ddmTemplateModelImpl.getOriginalClassPK(),
						ddmTemplateModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_T,
					args);

				args = new Object[] {
						ddmTemplateModelImpl.getGroupId(),
						ddmTemplateModelImpl.getClassNameId(),
						ddmTemplateModelImpl.getClassPK(),
						ddmTemplateModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_T,
					args);
			}

			if ((ddmTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_T_M.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateModelImpl.getOriginalGroupId(),
						ddmTemplateModelImpl.getOriginalClassNameId(),
						ddmTemplateModelImpl.getOriginalClassPK(),
						ddmTemplateModelImpl.getOriginalType(),
						ddmTemplateModelImpl.getOriginalMode()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_T_M, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_T_M,
					args);

				args = new Object[] {
						ddmTemplateModelImpl.getGroupId(),
						ddmTemplateModelImpl.getClassNameId(),
						ddmTemplateModelImpl.getClassPK(),
						ddmTemplateModelImpl.getType(),
						ddmTemplateModelImpl.getMode()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_T_M, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_T_M,
					args);
			}
		}

		entityCache.putResult(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateImpl.class, ddmTemplate.getPrimaryKey(), ddmTemplate,
			false);

		clearUniqueFindersCache(ddmTemplateModelImpl);
		cacheUniqueFindersCache(ddmTemplateModelImpl, isNew);

		ddmTemplate.resetOriginalValues();

		return ddmTemplate;
	}

	protected DDMTemplate toUnwrappedModel(DDMTemplate ddmTemplate) {
		if (ddmTemplate instanceof DDMTemplateImpl) {
			return ddmTemplate;
		}

		DDMTemplateImpl ddmTemplateImpl = new DDMTemplateImpl();

		ddmTemplateImpl.setNew(ddmTemplate.isNew());
		ddmTemplateImpl.setPrimaryKey(ddmTemplate.getPrimaryKey());

		ddmTemplateImpl.setUuid(ddmTemplate.getUuid());
		ddmTemplateImpl.setTemplateId(ddmTemplate.getTemplateId());
		ddmTemplateImpl.setGroupId(ddmTemplate.getGroupId());
		ddmTemplateImpl.setCompanyId(ddmTemplate.getCompanyId());
		ddmTemplateImpl.setUserId(ddmTemplate.getUserId());
		ddmTemplateImpl.setUserName(ddmTemplate.getUserName());
		ddmTemplateImpl.setVersionUserId(ddmTemplate.getVersionUserId());
		ddmTemplateImpl.setVersionUserName(ddmTemplate.getVersionUserName());
		ddmTemplateImpl.setCreateDate(ddmTemplate.getCreateDate());
		ddmTemplateImpl.setModifiedDate(ddmTemplate.getModifiedDate());
		ddmTemplateImpl.setClassNameId(ddmTemplate.getClassNameId());
		ddmTemplateImpl.setClassPK(ddmTemplate.getClassPK());
		ddmTemplateImpl.setResourceClassNameId(ddmTemplate.getResourceClassNameId());
		ddmTemplateImpl.setTemplateKey(ddmTemplate.getTemplateKey());
		ddmTemplateImpl.setVersion(ddmTemplate.getVersion());
		ddmTemplateImpl.setName(ddmTemplate.getName());
		ddmTemplateImpl.setDescription(ddmTemplate.getDescription());
		ddmTemplateImpl.setType(ddmTemplate.getType());
		ddmTemplateImpl.setMode(ddmTemplate.getMode());
		ddmTemplateImpl.setLanguage(ddmTemplate.getLanguage());
		ddmTemplateImpl.setScript(ddmTemplate.getScript());
		ddmTemplateImpl.setCacheable(ddmTemplate.isCacheable());
		ddmTemplateImpl.setSmallImage(ddmTemplate.isSmallImage());
		ddmTemplateImpl.setSmallImageId(ddmTemplate.getSmallImageId());
		ddmTemplateImpl.setSmallImageURL(ddmTemplate.getSmallImageURL());
		ddmTemplateImpl.setLastPublishDate(ddmTemplate.getLastPublishDate());

		return ddmTemplateImpl;
	}

	/**
	 * Returns the d d m template with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m template
	 * @return the d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTemplateException {
		DDMTemplate ddmTemplate = fetchByPrimaryKey(primaryKey);

		if (ddmTemplate == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ddmTemplate;
	}

	/**
	 * Returns the d d m template with the primary key or throws a {@link com.liferay.dynamic.data.mapping.NoSuchTemplateException} if it could not be found.
	 *
	 * @param templateId the primary key of the d d m template
	 * @return the d d m template
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate findByPrimaryKey(long templateId)
		throws NoSuchTemplateException {
		return findByPrimaryKey((Serializable)templateId);
	}

	/**
	 * Returns the d d m template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m template
	 * @return the d d m template, or <code>null</code> if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate fetchByPrimaryKey(Serializable primaryKey) {
		DDMTemplate ddmTemplate = (DDMTemplate)entityCache.getResult(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateImpl.class, primaryKey);

		if (ddmTemplate == _nullDDMTemplate) {
			return null;
		}

		if (ddmTemplate == null) {
			Session session = null;

			try {
				session = openSession();

				ddmTemplate = (DDMTemplate)session.get(DDMTemplateImpl.class,
						primaryKey);

				if (ddmTemplate != null) {
					cacheResult(ddmTemplate);
				}
				else {
					entityCache.putResult(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
						DDMTemplateImpl.class, primaryKey, _nullDDMTemplate);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
					DDMTemplateImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ddmTemplate;
	}

	/**
	 * Returns the d d m template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param templateId the primary key of the d d m template
	 * @return the d d m template, or <code>null</code> if a d d m template with the primary key could not be found
	 */
	@Override
	public DDMTemplate fetchByPrimaryKey(long templateId) {
		return fetchByPrimaryKey((Serializable)templateId);
	}

	@Override
	public Map<Serializable, DDMTemplate> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMTemplate> map = new HashMap<Serializable, DDMTemplate>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMTemplate ddmTemplate = fetchByPrimaryKey(primaryKey);

			if (ddmTemplate != null) {
				map.put(primaryKey, ddmTemplate);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DDMTemplate ddmTemplate = (DDMTemplate)entityCache.getResult(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
					DDMTemplateImpl.class, primaryKey);

			if (ddmTemplate == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, ddmTemplate);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DDMTEMPLATE_WHERE_PKS_IN);

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

			for (DDMTemplate ddmTemplate : (List<DDMTemplate>)q.list()) {
				map.put(ddmTemplate.getPrimaryKeyObj(), ddmTemplate);

				cacheResult(ddmTemplate);

				uncachedPrimaryKeys.remove(ddmTemplate.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DDMTemplateModelImpl.ENTITY_CACHE_ENABLED,
					DDMTemplateImpl.class, primaryKey, _nullDDMTemplate);
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
	 * Returns all the d d m templates.
	 *
	 * @return the d d m templates
	 */
	@Override
	public List<DDMTemplate> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @return the range of d d m templates
	 */
	@Override
	public List<DDMTemplate> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m templates
	 */
	@Override
	public List<DDMTemplate> findAll(int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m templates
	 * @param end the upper bound of the range of d d m templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of d d m templates
	 */
	@Override
	public List<DDMTemplate> findAll(int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
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

		List<DDMTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMTEMPLATE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMTEMPLATE;

				if (pagination) {
					sql = sql.concat(DDMTemplateModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the d d m templates from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMTemplate ddmTemplate : findAll()) {
			remove(ddmTemplate);
		}
	}

	/**
	 * Returns the number of d d m templates.
	 *
	 * @return the number of d d m templates
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMTEMPLATE);

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
		return DDMTemplateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the d d m template persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DDMTemplateImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_DDMTEMPLATE = "SELECT ddmTemplate FROM DDMTemplate ddmTemplate";
	private static final String _SQL_SELECT_DDMTEMPLATE_WHERE_PKS_IN = "SELECT ddmTemplate FROM DDMTemplate ddmTemplate WHERE templateId IN (";
	private static final String _SQL_SELECT_DDMTEMPLATE_WHERE = "SELECT ddmTemplate FROM DDMTemplate ddmTemplate WHERE ";
	private static final String _SQL_COUNT_DDMTEMPLATE = "SELECT COUNT(ddmTemplate) FROM DDMTemplate ddmTemplate";
	private static final String _SQL_COUNT_DDMTEMPLATE_WHERE = "SELECT COUNT(ddmTemplate) FROM DDMTemplate ddmTemplate WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "ddmTemplate.templateId";
	private static final String _FILTER_SQL_SELECT_DDMTEMPLATE_WHERE = "SELECT DISTINCT {ddmTemplate.*} FROM DDMTemplate ddmTemplate WHERE ";
	private static final String _FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {DDMTemplate.*} FROM (SELECT DISTINCT ddmTemplate.templateId FROM DDMTemplate ddmTemplate WHERE ";
	private static final String _FILTER_SQL_SELECT_DDMTEMPLATE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN DDMTemplate ON TEMP_TABLE.templateId = DDMTemplate.templateId";
	private static final String _FILTER_SQL_COUNT_DDMTEMPLATE_WHERE = "SELECT COUNT(DISTINCT ddmTemplate.templateId) AS COUNT_VALUE FROM DDMTemplate ddmTemplate WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "ddmTemplate";
	private static final String _FILTER_ENTITY_TABLE = "DDMTemplate";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmTemplate.";
	private static final String _ORDER_BY_ENTITY_TABLE = "DDMTemplate.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMTemplate exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMTemplate exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DDMTemplatePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "type", "mode"
			});
	private static final DDMTemplate _nullDDMTemplate = new DDMTemplateImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DDMTemplate> toCacheModel() {
				return _nullDDMTemplateCacheModel;
			}
		};

	private static final CacheModel<DDMTemplate> _nullDDMTemplateCacheModel = new CacheModel<DDMTemplate>() {
			@Override
			public DDMTemplate toEntityModel() {
				return _nullDDMTemplate;
			}
		};
}