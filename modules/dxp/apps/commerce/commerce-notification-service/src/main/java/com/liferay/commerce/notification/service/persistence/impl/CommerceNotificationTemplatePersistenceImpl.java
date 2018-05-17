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

package com.liferay.commerce.notification.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.exception.NoSuchNotificationTemplateException;
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateImpl;
import com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateModelImpl;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationTemplatePersistence;

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
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the commerce notification template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplatePersistence
 * @see com.liferay.commerce.notification.service.persistence.CommerceNotificationTemplateUtil
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplatePersistenceImpl
	extends BasePersistenceImpl<CommerceNotificationTemplate>
	implements CommerceNotificationTemplatePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceNotificationTemplateUtil} to access the commerce notification template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceNotificationTemplateImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CommerceNotificationTemplateModelImpl.UUID_COLUMN_BITMASK |
			CommerceNotificationTemplateModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] { String.class.getName() });

	/**
	 * Returns all the commerce notification templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce notification templates
	 */
	@Override
	public List<CommerceNotificationTemplate> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of matching commerce notification templates
	 */
	@Override
	public List<CommerceNotificationTemplate> findByUuid(String uuid,
		int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification templates
	 */
	@Override
	public List<CommerceNotificationTemplate> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce notification templates
	 */
	@Override
	public List<CommerceNotificationTemplate> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator,
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

		List<CommerceNotificationTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceNotificationTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationTemplate commerceNotificationTemplate : list) {
					if (!Objects.equals(uuid,
								commerceNotificationTemplate.getUuid())) {
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

			query.append(_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
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
				query.append(CommerceNotificationTemplateModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceNotificationTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceNotificationTemplate>)QueryUtil.list(q,
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
	 * Returns the first commerce notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	@Override
	public CommerceNotificationTemplate findByUuid_First(String uuid,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {
		CommerceNotificationTemplate commerceNotificationTemplate = fetchByUuid_First(uuid,
				orderByComparator);

		if (commerceNotificationTemplate != null) {
			return commerceNotificationTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchNotificationTemplateException(msg.toString());
	}

	/**
	 * Returns the first commerce notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	@Override
	public CommerceNotificationTemplate fetchByUuid_First(String uuid,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {
		List<CommerceNotificationTemplate> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	@Override
	public CommerceNotificationTemplate findByUuid_Last(String uuid,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {
		CommerceNotificationTemplate commerceNotificationTemplate = fetchByUuid_Last(uuid,
				orderByComparator);

		if (commerceNotificationTemplate != null) {
			return commerceNotificationTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchNotificationTemplateException(msg.toString());
	}

	/**
	 * Returns the last commerce notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	@Override
	public CommerceNotificationTemplate fetchByUuid_Last(String uuid,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationTemplate> list = findByUuid(uuid, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification templates before and after the current commerce notification template in the ordered set where uuid = &#63;.
	 *
	 * @param commerceNotificationTemplateId the primary key of the current commerce notification template
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplate[] findByUuid_PrevAndNext(
		long commerceNotificationTemplateId, String uuid,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {
		CommerceNotificationTemplate commerceNotificationTemplate = findByPrimaryKey(commerceNotificationTemplateId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationTemplate[] array = new CommerceNotificationTemplateImpl[3];

			array[0] = getByUuid_PrevAndNext(session,
					commerceNotificationTemplate, uuid, orderByComparator, true);

			array[1] = commerceNotificationTemplate;

			array[2] = getByUuid_PrevAndNext(session,
					commerceNotificationTemplate, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceNotificationTemplate getByUuid_PrevAndNext(
		Session session,
		CommerceNotificationTemplate commerceNotificationTemplate, String uuid,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals("")) {
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
			query.append(CommerceNotificationTemplateModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceNotificationTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceNotificationTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification templates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceNotificationTemplate commerceNotificationTemplate : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceNotificationTemplate);
		}
	}

	/**
	 * Returns the number of commerce notification templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce notification templates
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "commerceNotificationTemplate.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "commerceNotificationTemplate.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(commerceNotificationTemplate.uuid IS NULL OR commerceNotificationTemplate.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceNotificationTemplateModelImpl.UUID_COLUMN_BITMASK |
			CommerceNotificationTemplateModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce notification template where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchNotificationTemplateException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	@Override
	public CommerceNotificationTemplate findByUUID_G(String uuid, long groupId)
		throws NoSuchNotificationTemplateException {
		CommerceNotificationTemplate commerceNotificationTemplate = fetchByUUID_G(uuid,
				groupId);

		if (commerceNotificationTemplate == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchNotificationTemplateException(msg.toString());
		}

		return commerceNotificationTemplate;
	}

	/**
	 * Returns the commerce notification template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	@Override
	public CommerceNotificationTemplate fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce notification template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	@Override
	public CommerceNotificationTemplate fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CommerceNotificationTemplate) {
			CommerceNotificationTemplate commerceNotificationTemplate = (CommerceNotificationTemplate)result;

			if (!Objects.equals(uuid, commerceNotificationTemplate.getUuid()) ||
					(groupId != commerceNotificationTemplate.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
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

				List<CommerceNotificationTemplate> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CommerceNotificationTemplate commerceNotificationTemplate = list.get(0);

					result = commerceNotificationTemplate;

					cacheResult(commerceNotificationTemplate);

					if ((commerceNotificationTemplate.getUuid() == null) ||
							!commerceNotificationTemplate.getUuid().equals(uuid) ||
							(commerceNotificationTemplate.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, commerceNotificationTemplate);
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
			return (CommerceNotificationTemplate)result;
		}
	}

	/**
	 * Removes the commerce notification template where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce notification template that was removed
	 */
	@Override
	public CommerceNotificationTemplate removeByUUID_G(String uuid, long groupId)
		throws NoSuchNotificationTemplateException {
		CommerceNotificationTemplate commerceNotificationTemplate = findByUUID_G(uuid,
				groupId);

		return remove(commerceNotificationTemplate);
	}

	/**
	 * Returns the number of commerce notification templates where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce notification templates
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "commerceNotificationTemplate.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "commerceNotificationTemplate.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(commerceNotificationTemplate.uuid IS NULL OR commerceNotificationTemplate.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "commerceNotificationTemplate.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceNotificationTemplateModelImpl.UUID_COLUMN_BITMASK |
			CommerceNotificationTemplateModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceNotificationTemplateModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce notification templates
	 */
	@Override
	public List<CommerceNotificationTemplate> findByUuid_C(String uuid,
		long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of matching commerce notification templates
	 */
	@Override
	public List<CommerceNotificationTemplate> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification templates
	 */
	@Override
	public List<CommerceNotificationTemplate> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce notification templates
	 */
	@Override
	public List<CommerceNotificationTemplate> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator,
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

		List<CommerceNotificationTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceNotificationTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationTemplate commerceNotificationTemplate : list) {
					if (!Objects.equals(uuid,
								commerceNotificationTemplate.getUuid()) ||
							(companyId != commerceNotificationTemplate.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
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
				query.append(CommerceNotificationTemplateModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceNotificationTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceNotificationTemplate>)QueryUtil.list(q,
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
	 * Returns the first commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	@Override
	public CommerceNotificationTemplate findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {
		CommerceNotificationTemplate commerceNotificationTemplate = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (commerceNotificationTemplate != null) {
			return commerceNotificationTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchNotificationTemplateException(msg.toString());
	}

	/**
	 * Returns the first commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	@Override
	public CommerceNotificationTemplate fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {
		List<CommerceNotificationTemplate> list = findByUuid_C(uuid, companyId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	@Override
	public CommerceNotificationTemplate findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {
		CommerceNotificationTemplate commerceNotificationTemplate = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (commerceNotificationTemplate != null) {
			return commerceNotificationTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchNotificationTemplateException(msg.toString());
	}

	/**
	 * Returns the last commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	@Override
	public CommerceNotificationTemplate fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationTemplate> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification templates before and after the current commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the primary key of the current commerce notification template
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplate[] findByUuid_C_PrevAndNext(
		long commerceNotificationTemplateId, String uuid, long companyId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {
		CommerceNotificationTemplate commerceNotificationTemplate = findByPrimaryKey(commerceNotificationTemplateId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationTemplate[] array = new CommerceNotificationTemplateImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					commerceNotificationTemplate, uuid, companyId,
					orderByComparator, true);

			array[1] = commerceNotificationTemplate;

			array[2] = getByUuid_C_PrevAndNext(session,
					commerceNotificationTemplate, uuid, companyId,
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

	protected CommerceNotificationTemplate getByUuid_C_PrevAndNext(
		Session session,
		CommerceNotificationTemplate commerceNotificationTemplate, String uuid,
		long companyId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals("")) {
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
			query.append(CommerceNotificationTemplateModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceNotificationTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceNotificationTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification templates where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceNotificationTemplate commerceNotificationTemplate : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceNotificationTemplate);
		}
	}

	/**
	 * Returns the number of commerce notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce notification templates
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "commerceNotificationTemplate.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "commerceNotificationTemplate.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(commerceNotificationTemplate.uuid IS NULL OR commerceNotificationTemplate.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "commerceNotificationTemplate.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceNotificationTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceNotificationTemplateModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce notification templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce notification templates
	 */
	@Override
	public List<CommerceNotificationTemplate> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of matching commerce notification templates
	 */
	@Override
	public List<CommerceNotificationTemplate> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification templates
	 */
	@Override
	public List<CommerceNotificationTemplate> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce notification templates
	 */
	@Override
	public List<CommerceNotificationTemplate> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator,
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

		List<CommerceNotificationTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceNotificationTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationTemplate commerceNotificationTemplate : list) {
					if ((groupId != commerceNotificationTemplate.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceNotificationTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceNotificationTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceNotificationTemplate>)QueryUtil.list(q,
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
	 * Returns the first commerce notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	@Override
	public CommerceNotificationTemplate findByGroupId_First(long groupId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {
		CommerceNotificationTemplate commerceNotificationTemplate = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceNotificationTemplate != null) {
			return commerceNotificationTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchNotificationTemplateException(msg.toString());
	}

	/**
	 * Returns the first commerce notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	@Override
	public CommerceNotificationTemplate fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {
		List<CommerceNotificationTemplate> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	@Override
	public CommerceNotificationTemplate findByGroupId_Last(long groupId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {
		CommerceNotificationTemplate commerceNotificationTemplate = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceNotificationTemplate != null) {
			return commerceNotificationTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchNotificationTemplateException(msg.toString());
	}

	/**
	 * Returns the last commerce notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	@Override
	public CommerceNotificationTemplate fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationTemplate> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification templates before and after the current commerce notification template in the ordered set where groupId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the primary key of the current commerce notification template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplate[] findByGroupId_PrevAndNext(
		long commerceNotificationTemplateId, long groupId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {
		CommerceNotificationTemplate commerceNotificationTemplate = findByPrimaryKey(commerceNotificationTemplateId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationTemplate[] array = new CommerceNotificationTemplateImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					commerceNotificationTemplate, groupId, orderByComparator,
					true);

			array[1] = commerceNotificationTemplate;

			array[2] = getByGroupId_PrevAndNext(session,
					commerceNotificationTemplate, groupId, orderByComparator,
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

	protected CommerceNotificationTemplate getByGroupId_PrevAndNext(
		Session session,
		CommerceNotificationTemplate commerceNotificationTemplate,
		long groupId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_WHERE);

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
			query.append(CommerceNotificationTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceNotificationTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceNotificationTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce notification templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce notification templates that the user has permission to view
	 */
	@Override
	public List<CommerceNotificationTemplate> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification templates that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of matching commerce notification templates that the user has permission to view
	 */
	@Override
	public List<CommerceNotificationTemplate> filterFindByGroupId(
		long groupId, int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification templates that the user has permission to view
	 */
	@Override
	public List<CommerceNotificationTemplate> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(CommerceNotificationTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CommerceNotificationTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceNotificationTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					CommerceNotificationTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					CommerceNotificationTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<CommerceNotificationTemplate>)QueryUtil.list(q,
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
	 * Returns the commerce notification templates before and after the current commerce notification template in the ordered set of commerce notification templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the primary key of the current commerce notification template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplate[] filterFindByGroupId_PrevAndNext(
		long commerceNotificationTemplateId, long groupId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(commerceNotificationTemplateId,
				groupId, orderByComparator);
		}

		CommerceNotificationTemplate commerceNotificationTemplate = findByPrimaryKey(commerceNotificationTemplateId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationTemplate[] array = new CommerceNotificationTemplateImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					commerceNotificationTemplate, groupId, orderByComparator,
					true);

			array[1] = commerceNotificationTemplate;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					commerceNotificationTemplate, groupId, orderByComparator,
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

	protected CommerceNotificationTemplate filterGetByGroupId_PrevAndNext(
		Session session,
		CommerceNotificationTemplate commerceNotificationTemplate,
		long groupId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(CommerceNotificationTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CommerceNotificationTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceNotificationTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS,
				CommerceNotificationTemplateImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE,
				CommerceNotificationTemplateImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceNotificationTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceNotificationTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification templates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceNotificationTemplate commerceNotificationTemplate : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceNotificationTemplate);
		}
	}

	/**
	 * Returns the number of commerce notification templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce notification templates
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATE_WHERE);

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
	 * Returns the number of commerce notification templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce notification templates that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceNotificationTemplate.class.getName(),
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceNotificationTemplate.groupId = ?";

	public CommerceNotificationTemplatePersistenceImpl() {
		setModelClass(CommerceNotificationTemplate.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce notification template in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationTemplate the commerce notification template
	 */
	@Override
	public void cacheResult(
		CommerceNotificationTemplate commerceNotificationTemplate) {
		entityCache.putResult(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateImpl.class,
			commerceNotificationTemplate.getPrimaryKey(),
			commerceNotificationTemplate);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				commerceNotificationTemplate.getUuid(),
				commerceNotificationTemplate.getGroupId()
			}, commerceNotificationTemplate);

		commerceNotificationTemplate.resetOriginalValues();
	}

	/**
	 * Caches the commerce notification templates in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationTemplates the commerce notification templates
	 */
	@Override
	public void cacheResult(
		List<CommerceNotificationTemplate> commerceNotificationTemplates) {
		for (CommerceNotificationTemplate commerceNotificationTemplate : commerceNotificationTemplates) {
			if (entityCache.getResult(
						CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
						CommerceNotificationTemplateImpl.class,
						commerceNotificationTemplate.getPrimaryKey()) == null) {
				cacheResult(commerceNotificationTemplate);
			}
			else {
				commerceNotificationTemplate.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce notification templates.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceNotificationTemplateImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce notification template.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceNotificationTemplate commerceNotificationTemplate) {
		entityCache.removeResult(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateImpl.class,
			commerceNotificationTemplate.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceNotificationTemplateModelImpl)commerceNotificationTemplate,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceNotificationTemplate> commerceNotificationTemplates) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceNotificationTemplate commerceNotificationTemplate : commerceNotificationTemplates) {
			entityCache.removeResult(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
				CommerceNotificationTemplateImpl.class,
				commerceNotificationTemplate.getPrimaryKey());

			clearUniqueFindersCache((CommerceNotificationTemplateModelImpl)commerceNotificationTemplate,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceNotificationTemplateModelImpl commerceNotificationTemplateModelImpl) {
		Object[] args = new Object[] {
				commerceNotificationTemplateModelImpl.getUuid(),
				commerceNotificationTemplateModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			commerceNotificationTemplateModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceNotificationTemplateModelImpl commerceNotificationTemplateModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceNotificationTemplateModelImpl.getUuid(),
					commerceNotificationTemplateModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((commerceNotificationTemplateModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceNotificationTemplateModelImpl.getOriginalUuid(),
					commerceNotificationTemplateModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new commerce notification template with the primary key. Does not add the commerce notification template to the database.
	 *
	 * @param commerceNotificationTemplateId the primary key for the new commerce notification template
	 * @return the new commerce notification template
	 */
	@Override
	public CommerceNotificationTemplate create(
		long commerceNotificationTemplateId) {
		CommerceNotificationTemplate commerceNotificationTemplate = new CommerceNotificationTemplateImpl();

		commerceNotificationTemplate.setNew(true);
		commerceNotificationTemplate.setPrimaryKey(commerceNotificationTemplateId);

		String uuid = PortalUUIDUtil.generate();

		commerceNotificationTemplate.setUuid(uuid);

		commerceNotificationTemplate.setCompanyId(companyProvider.getCompanyId());

		return commerceNotificationTemplate;
	}

	/**
	 * Removes the commerce notification template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceNotificationTemplateId the primary key of the commerce notification template
	 * @return the commerce notification template that was removed
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplate remove(
		long commerceNotificationTemplateId)
		throws NoSuchNotificationTemplateException {
		return remove((Serializable)commerceNotificationTemplateId);
	}

	/**
	 * Removes the commerce notification template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce notification template
	 * @return the commerce notification template that was removed
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplate remove(Serializable primaryKey)
		throws NoSuchNotificationTemplateException {
		Session session = null;

		try {
			session = openSession();

			CommerceNotificationTemplate commerceNotificationTemplate = (CommerceNotificationTemplate)session.get(CommerceNotificationTemplateImpl.class,
					primaryKey);

			if (commerceNotificationTemplate == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceNotificationTemplate);
		}
		catch (NoSuchNotificationTemplateException nsee) {
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
	protected CommerceNotificationTemplate removeImpl(
		CommerceNotificationTemplate commerceNotificationTemplate) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceNotificationTemplate)) {
				commerceNotificationTemplate = (CommerceNotificationTemplate)session.get(CommerceNotificationTemplateImpl.class,
						commerceNotificationTemplate.getPrimaryKeyObj());
			}

			if (commerceNotificationTemplate != null) {
				session.delete(commerceNotificationTemplate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceNotificationTemplate != null) {
			clearCache(commerceNotificationTemplate);
		}

		return commerceNotificationTemplate;
	}

	@Override
	public CommerceNotificationTemplate updateImpl(
		CommerceNotificationTemplate commerceNotificationTemplate) {
		boolean isNew = commerceNotificationTemplate.isNew();

		if (!(commerceNotificationTemplate instanceof CommerceNotificationTemplateModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceNotificationTemplate.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceNotificationTemplate);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceNotificationTemplate proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceNotificationTemplate implementation " +
				commerceNotificationTemplate.getClass());
		}

		CommerceNotificationTemplateModelImpl commerceNotificationTemplateModelImpl =
			(CommerceNotificationTemplateModelImpl)commerceNotificationTemplate;

		if (Validator.isNull(commerceNotificationTemplate.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceNotificationTemplate.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceNotificationTemplate.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceNotificationTemplate.setCreateDate(now);
			}
			else {
				commerceNotificationTemplate.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceNotificationTemplateModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceNotificationTemplate.setModifiedDate(now);
			}
			else {
				commerceNotificationTemplate.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceNotificationTemplate.isNew()) {
				session.save(commerceNotificationTemplate);

				commerceNotificationTemplate.setNew(false);
			}
			else {
				commerceNotificationTemplate = (CommerceNotificationTemplate)session.merge(commerceNotificationTemplate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceNotificationTemplateModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceNotificationTemplateModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					commerceNotificationTemplateModelImpl.getUuid(),
					commerceNotificationTemplateModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] {
					commerceNotificationTemplateModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceNotificationTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceNotificationTemplateModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] {
						commerceNotificationTemplateModelImpl.getUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((commerceNotificationTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceNotificationTemplateModelImpl.getOriginalUuid(),
						commerceNotificationTemplateModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						commerceNotificationTemplateModelImpl.getUuid(),
						commerceNotificationTemplateModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((commerceNotificationTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceNotificationTemplateModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						commerceNotificationTemplateModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		entityCache.putResult(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateImpl.class,
			commerceNotificationTemplate.getPrimaryKey(),
			commerceNotificationTemplate, false);

		clearUniqueFindersCache(commerceNotificationTemplateModelImpl, false);
		cacheUniqueFindersCache(commerceNotificationTemplateModelImpl);

		commerceNotificationTemplate.resetOriginalValues();

		return commerceNotificationTemplate;
	}

	/**
	 * Returns the commerce notification template with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce notification template
	 * @return the commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplate findByPrimaryKey(
		Serializable primaryKey) throws NoSuchNotificationTemplateException {
		CommerceNotificationTemplate commerceNotificationTemplate = fetchByPrimaryKey(primaryKey);

		if (commerceNotificationTemplate == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceNotificationTemplate;
	}

	/**
	 * Returns the commerce notification template with the primary key or throws a {@link NoSuchNotificationTemplateException} if it could not be found.
	 *
	 * @param commerceNotificationTemplateId the primary key of the commerce notification template
	 * @return the commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplate findByPrimaryKey(
		long commerceNotificationTemplateId)
		throws NoSuchNotificationTemplateException {
		return findByPrimaryKey((Serializable)commerceNotificationTemplateId);
	}

	/**
	 * Returns the commerce notification template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce notification template
	 * @return the commerce notification template, or <code>null</code> if a commerce notification template with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplate fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
				CommerceNotificationTemplateImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceNotificationTemplate commerceNotificationTemplate = (CommerceNotificationTemplate)serializable;

		if (commerceNotificationTemplate == null) {
			Session session = null;

			try {
				session = openSession();

				commerceNotificationTemplate = (CommerceNotificationTemplate)session.get(CommerceNotificationTemplateImpl.class,
						primaryKey);

				if (commerceNotificationTemplate != null) {
					cacheResult(commerceNotificationTemplate);
				}
				else {
					entityCache.putResult(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
						CommerceNotificationTemplateImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
					CommerceNotificationTemplateImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceNotificationTemplate;
	}

	/**
	 * Returns the commerce notification template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceNotificationTemplateId the primary key of the commerce notification template
	 * @return the commerce notification template, or <code>null</code> if a commerce notification template with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplate fetchByPrimaryKey(
		long commerceNotificationTemplateId) {
		return fetchByPrimaryKey((Serializable)commerceNotificationTemplateId);
	}

	@Override
	public Map<Serializable, CommerceNotificationTemplate> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceNotificationTemplate> map = new HashMap<Serializable, CommerceNotificationTemplate>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceNotificationTemplate commerceNotificationTemplate = fetchByPrimaryKey(primaryKey);

			if (commerceNotificationTemplate != null) {
				map.put(primaryKey, commerceNotificationTemplate);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
					CommerceNotificationTemplateImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommerceNotificationTemplate)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_WHERE_PKS_IN);

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

			for (CommerceNotificationTemplate commerceNotificationTemplate : (List<CommerceNotificationTemplate>)q.list()) {
				map.put(commerceNotificationTemplate.getPrimaryKeyObj(),
					commerceNotificationTemplate);

				cacheResult(commerceNotificationTemplate);

				uncachedPrimaryKeys.remove(commerceNotificationTemplate.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceNotificationTemplateModelImpl.ENTITY_CACHE_ENABLED,
					CommerceNotificationTemplateImpl.class, primaryKey,
					nullModel);
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
	 * Returns all the commerce notification templates.
	 *
	 * @return the commerce notification templates
	 */
	@Override
	public List<CommerceNotificationTemplate> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of commerce notification templates
	 */
	@Override
	public List<CommerceNotificationTemplate> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce notification templates
	 */
	@Override
	public List<CommerceNotificationTemplate> findAll(int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce notification templates
	 */
	@Override
	public List<CommerceNotificationTemplate> findAll(int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator,
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

		List<CommerceNotificationTemplate> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceNotificationTemplate>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE;

				if (pagination) {
					sql = sql.concat(CommerceNotificationTemplateModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceNotificationTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceNotificationTemplate>)QueryUtil.list(q,
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
	 * Removes all the commerce notification templates from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceNotificationTemplate commerceNotificationTemplate : findAll()) {
			remove(commerceNotificationTemplate);
		}
	}

	/**
	 * Returns the number of commerce notification templates.
	 *
	 * @return the number of commerce notification templates
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATE);

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
		return CommerceNotificationTemplateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce notification template persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceNotificationTemplateImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE = "SELECT commerceNotificationTemplate FROM CommerceNotificationTemplate commerceNotificationTemplate";
	private static final String _SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_WHERE_PKS_IN =
		"SELECT commerceNotificationTemplate FROM CommerceNotificationTemplate commerceNotificationTemplate WHERE commerceNotificationTemplateId IN (";
	private static final String _SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_WHERE = "SELECT commerceNotificationTemplate FROM CommerceNotificationTemplate commerceNotificationTemplate WHERE ";
	private static final String _SQL_COUNT_COMMERCENOTIFICATIONTEMPLATE = "SELECT COUNT(commerceNotificationTemplate) FROM CommerceNotificationTemplate commerceNotificationTemplate";
	private static final String _SQL_COUNT_COMMERCENOTIFICATIONTEMPLATE_WHERE = "SELECT COUNT(commerceNotificationTemplate) FROM CommerceNotificationTemplate commerceNotificationTemplate WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "commerceNotificationTemplate.commerceNotificationTemplateId";
	private static final String _FILTER_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_WHERE =
		"SELECT DISTINCT {commerceNotificationTemplate.*} FROM CommerceNotificationTemplate commerceNotificationTemplate WHERE ";
	private static final String _FILTER_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {CommerceNotificationTemplate.*} FROM (SELECT DISTINCT commerceNotificationTemplate.commerceNotificationTemplateId FROM CommerceNotificationTemplate commerceNotificationTemplate WHERE ";
	private static final String _FILTER_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN CommerceNotificationTemplate ON TEMP_TABLE.commerceNotificationTemplateId = CommerceNotificationTemplate.commerceNotificationTemplateId";
	private static final String _FILTER_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATE_WHERE =
		"SELECT COUNT(DISTINCT commerceNotificationTemplate.commerceNotificationTemplateId) AS COUNT_VALUE FROM CommerceNotificationTemplate commerceNotificationTemplate WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "commerceNotificationTemplate";
	private static final String _FILTER_ENTITY_TABLE = "CommerceNotificationTemplate";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceNotificationTemplate.";
	private static final String _ORDER_BY_ENTITY_TABLE = "CommerceNotificationTemplate.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceNotificationTemplate exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceNotificationTemplate exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceNotificationTemplatePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "type"
			});
}