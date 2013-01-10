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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the journal template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalTemplatePersistence
 * @see JournalTemplateUtil
 * @generated
 */
public class JournalTemplatePersistenceImpl extends BasePersistenceImpl<JournalTemplate>
	implements JournalTemplatePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link JournalTemplateUtil} to access the journal template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = JournalTemplateImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			JournalTemplateModelImpl.UUID_COLUMN_BITMASK |
			JournalTemplateModelImpl.TEMPLATEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the journal templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @return the range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalTemplate journalTemplate : list) {
				if (!Validator.equals(uuid, journalTemplate.getUuid())) {
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

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

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
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
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
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<JournalTemplate>(list);
				}
				else {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByUuid_First(uuid,
				orderByComparator);

		if (journalTemplate != null) {
			return journalTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first journal template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalTemplate> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByUuid_Last(uuid,
				orderByComparator);

		if (journalTemplate != null) {
			return journalTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last journal template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<JournalTemplate> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal templates before and after the current journal template in the ordered set where uuid = &#63;.
	 *
	 * @param id the primary key of the current journal template
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate[] findByUuid_PrevAndNext(long id, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = getByUuid_PrevAndNext(session, journalTemplate, uuid,
					orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = getByUuid_PrevAndNext(session, journalTemplate, uuid,
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

	protected JournalTemplate getByUuid_PrevAndNext(Session session,
		JournalTemplate journalTemplate, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

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
			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal templates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (JournalTemplate journalTemplate : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalTemplate);
		}
	}

	/**
	 * Returns the number of journal templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "journalTemplate.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "journalTemplate.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(journalTemplate.uuid IS NULL OR journalTemplate.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			JournalTemplateModelImpl.UUID_COLUMN_BITMASK |
			JournalTemplateModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the journal template where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchTemplateException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByUUID_G(uuid, groupId);

		if (journalTemplate == null) {
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

		return journalTemplate;
	}

	/**
	 * Returns the journal template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the journal template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof JournalTemplate) {
			JournalTemplate journalTemplate = (JournalTemplate)result;

			if (!Validator.equals(uuid, journalTemplate.getUuid()) ||
					(groupId != journalTemplate.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

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

				List<JournalTemplate> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					JournalTemplate journalTemplate = list.get(0);

					result = journalTemplate;

					cacheResult(journalTemplate);

					if ((journalTemplate.getUuid() == null) ||
							!journalTemplate.getUuid().equals(uuid) ||
							(journalTemplate.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, journalTemplate);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
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
			return (JournalTemplate)result;
		}
	}

	/**
	 * Removes the journal template where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the journal template that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate removeByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByUUID_G(uuid, groupId);

		return remove(journalTemplate);
	}

	/**
	 * Returns the number of journal templates where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "journalTemplate.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "journalTemplate.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(journalTemplate.uuid IS NULL OR journalTemplate.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "journalTemplate.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			JournalTemplateModelImpl.UUID_COLUMN_BITMASK |
			JournalTemplateModelImpl.COMPANYID_COLUMN_BITMASK |
			JournalTemplateModelImpl.TEMPLATEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the journal templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @return the range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
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

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalTemplate journalTemplate : list) {
				if (!Validator.equals(uuid, journalTemplate.getUuid()) ||
						(companyId != journalTemplate.getCompanyId())) {
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

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

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
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
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
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<JournalTemplate>(list);
				}
				else {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (journalTemplate != null) {
			return journalTemplate;
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
	 * Returns the first journal template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalTemplate> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (journalTemplate != null) {
			return journalTemplate;
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
	 * Returns the last journal template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<JournalTemplate> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal templates before and after the current journal template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param id the primary key of the current journal template
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate[] findByUuid_C_PrevAndNext(long id, String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, journalTemplate, uuid,
					companyId, orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = getByUuid_C_PrevAndNext(session, journalTemplate, uuid,
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

	protected JournalTemplate getByUuid_C_PrevAndNext(Session session,
		JournalTemplate journalTemplate, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

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
			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal templates where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (JournalTemplate journalTemplate : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalTemplate);
		}
	}

	/**
	 * Returns the number of journal templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "journalTemplate.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "journalTemplate.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(journalTemplate.uuid IS NULL OR journalTemplate.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "journalTemplate.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			JournalTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			JournalTemplateModelImpl.TEMPLATEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the journal templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @return the range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalTemplate journalTemplate : list) {
				if ((groupId != journalTemplate.getGroupId())) {
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

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<JournalTemplate>(list);
				}
				else {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByGroupId_First(groupId,
				orderByComparator);

		if (journalTemplate != null) {
			return journalTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first journal template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalTemplate> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (journalTemplate != null) {
			return journalTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last journal template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<JournalTemplate> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal templates before and after the current journal template in the ordered set where groupId = &#63;.
	 *
	 * @param id the primary key of the current journal template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate[] findByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, journalTemplate,
					groupId, orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = getByGroupId_PrevAndNext(session, journalTemplate,
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

	protected JournalTemplate getByGroupId_PrevAndNext(Session session,
		JournalTemplate journalTemplate, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

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
			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal templates that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @return the range of matching journal templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal templates that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the journal templates before and after the current journal template in the ordered set of journal templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param id the primary key of the current journal template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate[] filterFindByGroupId_PrevAndNext(long id,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(id, groupId, orderByComparator);
		}

		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, journalTemplate,
					groupId, orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = filterGetByGroupId_PrevAndNext(session, journalTemplate,
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

	protected JournalTemplate filterGetByGroupId_PrevAndNext(Session session,
		JournalTemplate journalTemplate, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalTemplateImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalTemplateImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal templates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalTemplate journalTemplate : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalTemplate);
		}
	}

	/**
	 * Returns the number of journal templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	/**
	 * Returns the number of journal templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_JOURNALTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "journalTemplate.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TEMPLATEID =
		new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByTemplateId",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEID =
		new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTemplateId",
			new String[] { String.class.getName() },
			JournalTemplateModelImpl.TEMPLATEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TEMPLATEID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTemplateId",
			new String[] { String.class.getName() });

	/**
	 * Returns all the journal templates where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @return the matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByTemplateId(String templateId)
		throws SystemException {
		return findByTemplateId(templateId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal templates where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @return the range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByTemplateId(String templateId, int start,
		int end) throws SystemException {
		return findByTemplateId(templateId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal templates where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByTemplateId(String templateId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEID;
			finderArgs = new Object[] { templateId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_TEMPLATEID;
			finderArgs = new Object[] { templateId, start, end, orderByComparator };
		}

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalTemplate journalTemplate : list) {
				if (!Validator.equals(templateId,
							journalTemplate.getTemplateId())) {
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

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			boolean bindTemplateId = false;

			if (templateId == null) {
				query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1);
			}
			else if (templateId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3);
			}
			else {
				bindTemplateId = true;

				query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindTemplateId) {
					qPos.add(templateId);
				}

				if (!pagination) {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<JournalTemplate>(list);
				}
				else {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal template in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByTemplateId_First(String templateId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByTemplateId_First(templateId,
				orderByComparator);

		if (journalTemplate != null) {
			return journalTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("templateId=");
		msg.append(templateId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first journal template in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByTemplateId_First(String templateId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalTemplate> list = findByTemplateId(templateId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal template in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByTemplateId_Last(String templateId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByTemplateId_Last(templateId,
				orderByComparator);

		if (journalTemplate != null) {
			return journalTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("templateId=");
		msg.append(templateId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last journal template in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByTemplateId_Last(String templateId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByTemplateId(templateId);

		List<JournalTemplate> list = findByTemplateId(templateId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal templates before and after the current journal template in the ordered set where templateId = &#63;.
	 *
	 * @param id the primary key of the current journal template
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate[] findByTemplateId_PrevAndNext(long id,
		String templateId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = getByTemplateId_PrevAndNext(session, journalTemplate,
					templateId, orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = getByTemplateId_PrevAndNext(session, journalTemplate,
					templateId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalTemplate getByTemplateId_PrevAndNext(Session session,
		JournalTemplate journalTemplate, String templateId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

		boolean bindTemplateId = false;

		if (templateId == null) {
			query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1);
		}
		else if (templateId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3);
		}
		else {
			bindTemplateId = true;

			query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);
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
			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindTemplateId) {
			qPos.add(templateId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal templates where templateId = &#63; from the database.
	 *
	 * @param templateId the template ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByTemplateId(String templateId) throws SystemException {
		for (JournalTemplate journalTemplate : findByTemplateId(templateId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalTemplate);
		}
	}

	/**
	 * Returns the number of journal templates where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @return the number of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByTemplateId(String templateId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_TEMPLATEID;

		Object[] finderArgs = new Object[] { templateId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

			boolean bindTemplateId = false;

			if (templateId == null) {
				query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1);
			}
			else if (templateId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3);
			}
			else {
				bindTemplateId = true;

				query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindTemplateId) {
					qPos.add(templateId);
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

	private static final String _FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1 = "journalTemplate.templateId IS NULL";
	private static final String _FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2 = "journalTemplate.templateId = ?";
	private static final String _FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3 = "(journalTemplate.templateId IS NULL OR journalTemplate.templateId = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_STRUCTUREID =
		new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByStructureId",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID =
		new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStructureId",
			new String[] { String.class.getName() },
			JournalTemplateModelImpl.STRUCTUREID_COLUMN_BITMASK |
			JournalTemplateModelImpl.TEMPLATEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_STRUCTUREID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStructureId",
			new String[] { String.class.getName() });

	/**
	 * Returns all the journal templates where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByStructureId(String structureId)
		throws SystemException {
		return findByStructureId(structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal templates where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @return the range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByStructureId(String structureId,
		int start, int end) throws SystemException {
		return findByStructureId(structureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal templates where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByStructureId(String structureId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID;
			finderArgs = new Object[] { structureId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_STRUCTUREID;
			finderArgs = new Object[] { structureId, start, end, orderByComparator };
		}

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalTemplate journalTemplate : list) {
				if (!Validator.equals(structureId,
							journalTemplate.getStructureId())) {
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

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			boolean bindStructureId = false;

			if (structureId == null) {
				query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_1);
			}
			else if (structureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_3);
			}
			else {
				bindStructureId = true;

				query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindStructureId) {
					qPos.add(structureId);
				}

				if (!pagination) {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<JournalTemplate>(list);
				}
				else {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal template in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByStructureId_First(String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByStructureId_First(structureId,
				orderByComparator);

		if (journalTemplate != null) {
			return journalTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("structureId=");
		msg.append(structureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first journal template in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByStructureId_First(String structureId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalTemplate> list = findByStructureId(structureId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal template in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByStructureId_Last(String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByStructureId_Last(structureId,
				orderByComparator);

		if (journalTemplate != null) {
			return journalTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("structureId=");
		msg.append(structureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last journal template in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByStructureId_Last(String structureId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByStructureId(structureId);

		List<JournalTemplate> list = findByStructureId(structureId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal templates before and after the current journal template in the ordered set where structureId = &#63;.
	 *
	 * @param id the primary key of the current journal template
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate[] findByStructureId_PrevAndNext(long id,
		String structureId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = getByStructureId_PrevAndNext(session, journalTemplate,
					structureId, orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = getByStructureId_PrevAndNext(session, journalTemplate,
					structureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalTemplate getByStructureId_PrevAndNext(Session session,
		JournalTemplate journalTemplate, String structureId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

		boolean bindStructureId = false;

		if (structureId == null) {
			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_1);
		}
		else if (structureId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_3);
		}
		else {
			bindStructureId = true;

			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);
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
			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindStructureId) {
			qPos.add(structureId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal templates where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByStructureId(String structureId)
		throws SystemException {
		for (JournalTemplate journalTemplate : findByStructureId(structureId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalTemplate);
		}
	}

	/**
	 * Returns the number of journal templates where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByStructureId(String structureId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_STRUCTUREID;

		Object[] finderArgs = new Object[] { structureId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

			boolean bindStructureId = false;

			if (structureId == null) {
				query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_1);
			}
			else if (structureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_3);
			}
			else {
				bindStructureId = true;

				query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindStructureId) {
					qPos.add(structureId);
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

	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_1 = "journalTemplate.structureId IS NULL";
	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2 = "journalTemplate.structureId = ?";
	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_3 = "(journalTemplate.structureId IS NULL OR journalTemplate.structureId = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_SMALLIMAGEID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchBySmallImageId", new String[] { Long.class.getName() },
			JournalTemplateModelImpl.SMALLIMAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SMALLIMAGEID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySmallImageId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the journal template where smallImageId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchTemplateException} if it could not be found.
	 *
	 * @param smallImageId the small image ID
	 * @return the matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findBySmallImageId(long smallImageId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchBySmallImageId(smallImageId);

		if (journalTemplate == null) {
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

		return journalTemplate;
	}

	/**
	 * Returns the journal template where smallImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param smallImageId the small image ID
	 * @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchBySmallImageId(long smallImageId)
		throws SystemException {
		return fetchBySmallImageId(smallImageId, true);
	}

	/**
	 * Returns the journal template where smallImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param smallImageId the small image ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchBySmallImageId(long smallImageId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { smallImageId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
					finderArgs, this);
		}

		if (result instanceof JournalTemplate) {
			JournalTemplate journalTemplate = (JournalTemplate)result;

			if ((smallImageId != journalTemplate.getSmallImageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				List<JournalTemplate> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"JournalTemplatePersistenceImpl.fetchBySmallImageId(long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					JournalTemplate journalTemplate = list.get(0);

					result = journalTemplate;

					cacheResult(journalTemplate);

					if ((journalTemplate.getSmallImageId() != smallImageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
							finderArgs, journalTemplate);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
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
			return (JournalTemplate)result;
		}
	}

	/**
	 * Removes the journal template where smallImageId = &#63; from the database.
	 *
	 * @param smallImageId the small image ID
	 * @return the journal template that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate removeBySmallImageId(long smallImageId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findBySmallImageId(smallImageId);

		return remove(journalTemplate);
	}

	/**
	 * Returns the number of journal templates where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @return the number of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countBySmallImageId(long smallImageId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SMALLIMAGEID;

		Object[] finderArgs = new Object[] { smallImageId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

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

	private static final String _FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2 = "journalTemplate.smallImageId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_T = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_T",
			new String[] { Long.class.getName(), String.class.getName() },
			JournalTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			JournalTemplateModelImpl.TEMPLATEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the journal template where groupId = &#63; and templateId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchTemplateException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param templateId the template ID
	 * @return the matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByG_T(long groupId, String templateId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByG_T(groupId, templateId);

		if (journalTemplate == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", templateId=");
			msg.append(templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateException(msg.toString());
		}

		return journalTemplate;
	}

	/**
	 * Returns the journal template where groupId = &#63; and templateId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param templateId the template ID
	 * @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByG_T(long groupId, String templateId)
		throws SystemException {
		return fetchByG_T(groupId, templateId, true);
	}

	/**
	 * Returns the journal template where groupId = &#63; and templateId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param templateId the template ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByG_T(long groupId, String templateId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, templateId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_T,
					finderArgs, this);
		}

		if (result instanceof JournalTemplate) {
			JournalTemplate journalTemplate = (JournalTemplate)result;

			if ((groupId != journalTemplate.getGroupId()) ||
					!Validator.equals(templateId,
						journalTemplate.getTemplateId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			boolean bindTemplateId = false;

			if (templateId == null) {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_1);
			}
			else if (templateId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_3);
			}
			else {
				bindTemplateId = true;

				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindTemplateId) {
					qPos.add(templateId);
				}

				List<JournalTemplate> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
						finderArgs, list);
				}
				else {
					JournalTemplate journalTemplate = list.get(0);

					result = journalTemplate;

					cacheResult(journalTemplate);

					if ((journalTemplate.getGroupId() != groupId) ||
							(journalTemplate.getTemplateId() == null) ||
							!journalTemplate.getTemplateId().equals(templateId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
							finderArgs, journalTemplate);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_T,
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
			return (JournalTemplate)result;
		}
	}

	/**
	 * Removes the journal template where groupId = &#63; and templateId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param templateId the template ID
	 * @return the journal template that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate removeByG_T(long groupId, String templateId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByG_T(groupId, templateId);

		return remove(journalTemplate);
	}

	/**
	 * Returns the number of journal templates where groupId = &#63; and templateId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param templateId the template ID
	 * @return the number of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_T(long groupId, String templateId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_T;

		Object[] finderArgs = new Object[] { groupId, templateId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			boolean bindTemplateId = false;

			if (templateId == null) {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_1);
			}
			else if (templateId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_3);
			}
			else {
				bindTemplateId = true;

				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindTemplateId) {
					qPos.add(templateId);
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

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "journalTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEID_1 = "journalTemplate.templateId IS NULL";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEID_2 = "journalTemplate.templateId = ?";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEID_3 = "(journalTemplate.templateId IS NULL OR journalTemplate.templateId = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			JournalTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S",
			new String[] { Long.class.getName(), String.class.getName() },
			JournalTemplateModelImpl.GROUPID_COLUMN_BITMASK |
			JournalTemplateModelImpl.STRUCTUREID_COLUMN_BITMASK |
			JournalTemplateModelImpl.TEMPLATEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the journal templates where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @return the matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByG_S(long groupId, String structureId)
		throws SystemException {
		return findByG_S(groupId, structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal templates where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @return the range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByG_S(long groupId, String structureId,
		int start, int end) throws SystemException {
		return findByG_S(groupId, structureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal templates where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findByG_S(long groupId, String structureId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S;
			finderArgs = new Object[] { groupId, structureId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S;
			finderArgs = new Object[] {
					groupId, structureId,
					
					start, end, orderByComparator
				};
		}

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalTemplate journalTemplate : list) {
				if ((groupId != journalTemplate.getGroupId()) ||
						!Validator.equals(structureId,
							journalTemplate.getStructureId())) {
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

			query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			boolean bindStructureId = false;

			if (structureId == null) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
			}
			else if (structureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
			}
			else {
				bindStructureId = true;

				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindStructureId) {
					qPos.add(structureId);
				}

				if (!pagination) {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<JournalTemplate>(list);
				}
				else {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal template in the ordered set where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByG_S_First(long groupId, String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByG_S_First(groupId,
				structureId, orderByComparator);

		if (journalTemplate != null) {
			return journalTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", structureId=");
		msg.append(structureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the first journal template in the ordered set where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByG_S_First(long groupId, String structureId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalTemplate> list = findByG_S(groupId, structureId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal template in the ordered set where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByG_S_Last(long groupId, String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByG_S_Last(groupId, structureId,
				orderByComparator);

		if (journalTemplate != null) {
			return journalTemplate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", structureId=");
		msg.append(structureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateException(msg.toString());
	}

	/**
	 * Returns the last journal template in the ordered set where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal template, or <code>null</code> if a matching journal template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByG_S_Last(long groupId, String structureId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_S(groupId, structureId);

		List<JournalTemplate> list = findByG_S(groupId, structureId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal templates before and after the current journal template in the ordered set where groupId = &#63; and structureId = &#63;.
	 *
	 * @param id the primary key of the current journal template
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate[] findByG_S_PrevAndNext(long id, long groupId,
		String structureId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = getByG_S_PrevAndNext(session, journalTemplate, groupId,
					structureId, orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = getByG_S_PrevAndNext(session, journalTemplate, groupId,
					structureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalTemplate getByG_S_PrevAndNext(Session session,
		JournalTemplate journalTemplate, long groupId, String structureId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		boolean bindStructureId = false;

		if (structureId == null) {
			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
		}
		else if (structureId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
		}
		else {
			bindStructureId = true;

			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
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
			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindStructureId) {
			qPos.add(structureId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal templates that the user has permission to view where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @return the matching journal templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> filterFindByG_S(long groupId,
		String structureId) throws SystemException {
		return filterFindByG_S(groupId, structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal templates that the user has permission to view where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @return the range of matching journal templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> filterFindByG_S(long groupId,
		String structureId, int start, int end) throws SystemException {
		return filterFindByG_S(groupId, structureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal templates that the user has permissions to view where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> filterFindByG_S(long groupId,
		String structureId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S(groupId, structureId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		boolean bindStructureId = false;

		if (structureId == null) {
			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
		}
		else if (structureId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
		}
		else {
			bindStructureId = true;

			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalTemplateImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalTemplateImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindStructureId) {
				qPos.add(structureId);
			}

			return (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
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
	 * Returns the journal templates before and after the current journal template in the ordered set of journal templates that the user has permission to view where groupId = &#63; and structureId = &#63;.
	 *
	 * @param id the primary key of the current journal template
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate[] filterFindByG_S_PrevAndNext(long id, long groupId,
		String structureId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S_PrevAndNext(id, groupId, structureId,
				orderByComparator);
		}

		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = filterGetByG_S_PrevAndNext(session, journalTemplate,
					groupId, structureId, orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = filterGetByG_S_PrevAndNext(session, journalTemplate,
					groupId, structureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalTemplate filterGetByG_S_PrevAndNext(Session session,
		JournalTemplate journalTemplate, long groupId, String structureId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		boolean bindStructureId = false;

		if (structureId == null) {
			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
		}
		else if (structureId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
		}
		else {
			bindStructureId = true;

			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalTemplateImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalTemplateImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindStructureId) {
			qPos.add(structureId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal templates where groupId = &#63; and structureId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_S(long groupId, String structureId)
		throws SystemException {
		for (JournalTemplate journalTemplate : findByG_S(groupId, structureId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalTemplate);
		}
	}

	/**
	 * Returns the number of journal templates where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @return the number of matching journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_S(long groupId, String structureId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_S;

		Object[] finderArgs = new Object[] { groupId, structureId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			boolean bindStructureId = false;

			if (structureId == null) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
			}
			else if (structureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
			}
			else {
				bindStructureId = true;

				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindStructureId) {
					qPos.add(structureId);
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

	/**
	 * Returns the number of journal templates that the user has permission to view where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @return the number of matching journal templates that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_S(long groupId, String structureId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_S(groupId, structureId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		boolean bindStructureId = false;

		if (structureId == null) {
			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
		}
		else if (structureId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
		}
		else {
			bindStructureId = true;

			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalTemplate.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindStructureId) {
				qPos.add(structureId);
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

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "journalTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_1 = "journalTemplate.structureId IS NULL";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_2 = "journalTemplate.structureId = ?";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_3 = "(journalTemplate.structureId IS NULL OR journalTemplate.structureId = '')";

	/**
	 * Caches the journal template in the entity cache if it is enabled.
	 *
	 * @param journalTemplate the journal template
	 */
	public void cacheResult(JournalTemplate journalTemplate) {
		EntityCacheUtil.putResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateImpl.class, journalTemplate.getPrimaryKey(),
			journalTemplate);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { journalTemplate.getUuid(), journalTemplate.getGroupId() },
			journalTemplate);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
			new Object[] { journalTemplate.getSmallImageId() }, journalTemplate);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
			new Object[] {
				journalTemplate.getGroupId(), journalTemplate.getTemplateId()
			}, journalTemplate);

		journalTemplate.resetOriginalValues();
	}

	/**
	 * Caches the journal templates in the entity cache if it is enabled.
	 *
	 * @param journalTemplates the journal templates
	 */
	public void cacheResult(List<JournalTemplate> journalTemplates) {
		for (JournalTemplate journalTemplate : journalTemplates) {
			if (EntityCacheUtil.getResult(
						JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
						JournalTemplateImpl.class,
						journalTemplate.getPrimaryKey()) == null) {
				cacheResult(journalTemplate);
			}
			else {
				journalTemplate.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all journal templates.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(JournalTemplateImpl.class.getName());
		}

		EntityCacheUtil.clearCache(JournalTemplateImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the journal template.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(JournalTemplate journalTemplate) {
		EntityCacheUtil.removeResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateImpl.class, journalTemplate.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(journalTemplate);
	}

	@Override
	public void clearCache(List<JournalTemplate> journalTemplates) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (JournalTemplate journalTemplate : journalTemplates) {
			EntityCacheUtil.removeResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
				JournalTemplateImpl.class, journalTemplate.getPrimaryKey());

			clearUniqueFindersCache(journalTemplate);
		}
	}

	protected void cacheUniqueFindersCache(JournalTemplate journalTemplate) {
		if (journalTemplate.isNew()) {
			Object[] args = new Object[] {
					journalTemplate.getUuid(), journalTemplate.getGroupId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				journalTemplate);

			args = new Object[] { journalTemplate.getSmallImageId() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID, args,
				journalTemplate);

			args = new Object[] {
					journalTemplate.getGroupId(),
					journalTemplate.getTemplateId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_T, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T, args,
				journalTemplate);
		}
		else {
			JournalTemplateModelImpl journalTemplateModelImpl = (JournalTemplateModelImpl)journalTemplate;

			if ((journalTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalTemplate.getUuid(), journalTemplate.getGroupId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					journalTemplate);
			}

			if ((journalTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_SMALLIMAGEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { journalTemplate.getSmallImageId() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID,
					args, Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
					args, journalTemplate);
			}

			if ((journalTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalTemplate.getGroupId(),
						journalTemplate.getTemplateId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_T, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T, args,
					journalTemplate);
			}
		}
	}

	protected void clearUniqueFindersCache(JournalTemplate journalTemplate) {
		JournalTemplateModelImpl journalTemplateModelImpl = (JournalTemplateModelImpl)journalTemplate;

		Object[] args = new Object[] {
				journalTemplate.getUuid(), journalTemplate.getGroupId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((journalTemplateModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					journalTemplateModelImpl.getOriginalUuid(),
					journalTemplateModelImpl.getOriginalGroupId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		args = new Object[] { journalTemplate.getSmallImageId() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID, args);

		if ((journalTemplateModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_SMALLIMAGEID.getColumnBitmask()) != 0) {
			args = new Object[] {
					journalTemplateModelImpl.getOriginalSmallImageId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID, args);
		}

		args = new Object[] {
				journalTemplate.getGroupId(), journalTemplate.getTemplateId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_T, args);

		if ((journalTemplateModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_T.getColumnBitmask()) != 0) {
			args = new Object[] {
					journalTemplateModelImpl.getOriginalGroupId(),
					journalTemplateModelImpl.getOriginalTemplateId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_T, args);
		}
	}

	/**
	 * Creates a new journal template with the primary key. Does not add the journal template to the database.
	 *
	 * @param id the primary key for the new journal template
	 * @return the new journal template
	 */
	public JournalTemplate create(long id) {
		JournalTemplate journalTemplate = new JournalTemplateImpl();

		journalTemplate.setNew(true);
		journalTemplate.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalTemplate.setUuid(uuid);

		return journalTemplate;
	}

	/**
	 * Removes the journal template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the journal template
	 * @return the journal template that was removed
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate remove(long id)
		throws NoSuchTemplateException, SystemException {
		return remove((Serializable)id);
	}

	/**
	 * Removes the journal template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the journal template
	 * @return the journal template that was removed
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalTemplate remove(Serializable primaryKey)
		throws NoSuchTemplateException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalTemplate journalTemplate = (JournalTemplate)session.get(JournalTemplateImpl.class,
					primaryKey);

			if (journalTemplate == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(journalTemplate);
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
	protected JournalTemplate removeImpl(JournalTemplate journalTemplate)
		throws SystemException {
		journalTemplate = toUnwrappedModel(journalTemplate);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(journalTemplate)) {
				journalTemplate = (JournalTemplate)session.get(JournalTemplateImpl.class,
						journalTemplate.getPrimaryKeyObj());
			}

			if (journalTemplate != null) {
				session.delete(journalTemplate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (journalTemplate != null) {
			clearCache(journalTemplate);
		}

		return journalTemplate;
	}

	@Override
	public JournalTemplate updateImpl(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate)
		throws SystemException {
		journalTemplate = toUnwrappedModel(journalTemplate);

		boolean isNew = journalTemplate.isNew();

		JournalTemplateModelImpl journalTemplateModelImpl = (JournalTemplateModelImpl)journalTemplate;

		if (Validator.isNull(journalTemplate.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalTemplate.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (journalTemplate.isNew()) {
				session.save(journalTemplate);

				journalTemplate.setNew(false);
			}
			else {
				session.merge(journalTemplate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !JournalTemplateModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((journalTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalTemplateModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { journalTemplateModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((journalTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalTemplateModelImpl.getOriginalUuid(),
						journalTemplateModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						journalTemplateModelImpl.getUuid(),
						journalTemplateModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((journalTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalTemplateModelImpl.getOriginalGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { journalTemplateModelImpl.getGroupId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((journalTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalTemplateModelImpl.getOriginalTemplateId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_TEMPLATEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEID,
					args);

				args = new Object[] { journalTemplateModelImpl.getTemplateId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_TEMPLATEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEID,
					args);
			}

			if ((journalTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalTemplateModelImpl.getOriginalStructureId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID,
					args);

				args = new Object[] { journalTemplateModelImpl.getStructureId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID,
					args);
			}

			if ((journalTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalTemplateModelImpl.getOriginalGroupId(),
						journalTemplateModelImpl.getOriginalStructureId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
					args);

				args = new Object[] {
						journalTemplateModelImpl.getGroupId(),
						journalTemplateModelImpl.getStructureId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
					args);
			}
		}

		EntityCacheUtil.putResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateImpl.class, journalTemplate.getPrimaryKey(),
			journalTemplate);

		clearUniqueFindersCache(journalTemplate);
		cacheUniqueFindersCache(journalTemplate);

		return journalTemplate;
	}

	protected JournalTemplate toUnwrappedModel(JournalTemplate journalTemplate) {
		if (journalTemplate instanceof JournalTemplateImpl) {
			return journalTemplate;
		}

		JournalTemplateImpl journalTemplateImpl = new JournalTemplateImpl();

		journalTemplateImpl.setNew(journalTemplate.isNew());
		journalTemplateImpl.setPrimaryKey(journalTemplate.getPrimaryKey());

		journalTemplateImpl.setUuid(journalTemplate.getUuid());
		journalTemplateImpl.setId(journalTemplate.getId());
		journalTemplateImpl.setGroupId(journalTemplate.getGroupId());
		journalTemplateImpl.setCompanyId(journalTemplate.getCompanyId());
		journalTemplateImpl.setUserId(journalTemplate.getUserId());
		journalTemplateImpl.setUserName(journalTemplate.getUserName());
		journalTemplateImpl.setCreateDate(journalTemplate.getCreateDate());
		journalTemplateImpl.setModifiedDate(journalTemplate.getModifiedDate());
		journalTemplateImpl.setTemplateId(journalTemplate.getTemplateId());
		journalTemplateImpl.setStructureId(journalTemplate.getStructureId());
		journalTemplateImpl.setName(journalTemplate.getName());
		journalTemplateImpl.setDescription(journalTemplate.getDescription());
		journalTemplateImpl.setXsl(journalTemplate.getXsl());
		journalTemplateImpl.setLangType(journalTemplate.getLangType());
		journalTemplateImpl.setCacheable(journalTemplate.isCacheable());
		journalTemplateImpl.setSmallImage(journalTemplate.isSmallImage());
		journalTemplateImpl.setSmallImageId(journalTemplate.getSmallImageId());
		journalTemplateImpl.setSmallImageURL(journalTemplate.getSmallImageURL());

		return journalTemplateImpl;
	}

	/**
	 * Returns the journal template with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal template
	 * @return the journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalTemplate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByPrimaryKey(primaryKey);

		if (journalTemplate == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return journalTemplate;
	}

	/**
	 * Returns the journal template with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchTemplateException} if it could not be found.
	 *
	 * @param id the primary key of the journal template
	 * @return the journal template
	 * @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate findByPrimaryKey(long id)
		throws NoSuchTemplateException, SystemException {
		return findByPrimaryKey((Serializable)id);
	}

	/**
	 * Returns the journal template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal template
	 * @return the journal template, or <code>null</code> if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalTemplate fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		JournalTemplate journalTemplate = (JournalTemplate)EntityCacheUtil.getResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
				JournalTemplateImpl.class, primaryKey);

		if (journalTemplate == _nullJournalTemplate) {
			return null;
		}

		if (journalTemplate == null) {
			Session session = null;

			try {
				session = openSession();

				journalTemplate = (JournalTemplate)session.get(JournalTemplateImpl.class,
						primaryKey);

				if (journalTemplate != null) {
					cacheResult(journalTemplate);
				}
				else {
					EntityCacheUtil.putResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
						JournalTemplateImpl.class, primaryKey,
						_nullJournalTemplate);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
					JournalTemplateImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return journalTemplate;
	}

	/**
	 * Returns the journal template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the journal template
	 * @return the journal template, or <code>null</code> if a journal template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalTemplate fetchByPrimaryKey(long id) throws SystemException {
		return fetchByPrimaryKey((Serializable)id);
	}

	/**
	 * Returns all the journal templates.
	 *
	 * @return the journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @return the range of journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal templates
	 * @param end the upper bound of the range of journal templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalTemplate> findAll(int start, int end,
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

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_JOURNALTEMPLATE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_JOURNALTEMPLATE;

				if (pagination) {
					sql = sql.concat(JournalTemplateModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<JournalTemplate>(list);
				}
				else {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the journal templates from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (JournalTemplate journalTemplate : findAll()) {
			remove(journalTemplate);
		}
	}

	/**
	 * Returns the number of journal templates.
	 *
	 * @return the number of journal templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_JOURNALTEMPLATE);

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
	 * Initializes the journal template persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.journal.model.JournalTemplate")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalTemplate>> listenersList = new ArrayList<ModelListener<JournalTemplate>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalTemplate>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(JournalTemplateImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_JOURNALTEMPLATE = "SELECT journalTemplate FROM JournalTemplate journalTemplate";
	private static final String _SQL_SELECT_JOURNALTEMPLATE_WHERE = "SELECT journalTemplate FROM JournalTemplate journalTemplate WHERE ";
	private static final String _SQL_COUNT_JOURNALTEMPLATE = "SELECT COUNT(journalTemplate) FROM JournalTemplate journalTemplate";
	private static final String _SQL_COUNT_JOURNALTEMPLATE_WHERE = "SELECT COUNT(journalTemplate) FROM JournalTemplate journalTemplate WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "journalTemplate.id_";
	private static final String _FILTER_SQL_SELECT_JOURNALTEMPLATE_WHERE = "SELECT DISTINCT {journalTemplate.*} FROM JournalTemplate journalTemplate WHERE ";
	private static final String _FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {JournalTemplate.*} FROM (SELECT DISTINCT journalTemplate.id_ FROM JournalTemplate journalTemplate WHERE ";
	private static final String _FILTER_SQL_SELECT_JOURNALTEMPLATE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN JournalTemplate ON TEMP_TABLE.id_ = JournalTemplate.id_";
	private static final String _FILTER_SQL_COUNT_JOURNALTEMPLATE_WHERE = "SELECT COUNT(DISTINCT journalTemplate.id_) AS COUNT_VALUE FROM JournalTemplate journalTemplate WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "journalTemplate";
	private static final String _FILTER_ENTITY_TABLE = "JournalTemplate";
	private static final String _ORDER_BY_ENTITY_ALIAS = "journalTemplate.";
	private static final String _ORDER_BY_ENTITY_TABLE = "JournalTemplate.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JournalTemplate exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JournalTemplate exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(JournalTemplatePersistenceImpl.class);
	private static JournalTemplate _nullJournalTemplate = new JournalTemplateImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<JournalTemplate> toCacheModel() {
				return _nullJournalTemplateCacheModel;
			}
		};

	private static CacheModel<JournalTemplate> _nullJournalTemplateCacheModel = new CacheModel<JournalTemplate>() {
			public JournalTemplate toEntityModel() {
				return _nullJournalTemplate;
			}
		};
}