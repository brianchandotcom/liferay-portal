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

package com.liferay.template.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.template.exception.NoSuchTemplateEntryException;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.model.TemplateEntryTable;
import com.liferay.template.model.impl.TemplateEntryImpl;
import com.liferay.template.model.impl.TemplateEntryModelImpl;
import com.liferay.template.service.persistence.TemplateEntryPersistence;
import com.liferay.template.service.persistence.TemplateEntryUtil;
import com.liferay.template.service.persistence.impl.constants.TemplatePersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
 * The persistence implementation for the template entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {TemplateEntryPersistence.class, BasePersistence.class})
public class TemplateEntryPersistenceImpl
	extends BasePersistenceImpl<TemplateEntry>
	implements TemplateEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TemplateEntryUtil</code> to access the template entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TemplateEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the template entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching template entries
	 */
	@Override
	public List<TemplateEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the template entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @return the range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the template entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the template entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<TemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<TemplateEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (TemplateEntry templateEntry : list) {
					if (!uuid.equals(templateEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_TEMPLATEENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<TemplateEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first template entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry findByUuid_First(
			String uuid, OrderByComparator<TemplateEntry> orderByComparator)
		throws NoSuchTemplateEntryException {

		TemplateEntry templateEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (templateEntry != null) {
			return templateEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first template entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry fetchByUuid_First(
		String uuid, OrderByComparator<TemplateEntry> orderByComparator) {

		List<TemplateEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last template entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry findByUuid_Last(
			String uuid, OrderByComparator<TemplateEntry> orderByComparator)
		throws NoSuchTemplateEntryException {

		TemplateEntry templateEntry = fetchByUuid_Last(uuid, orderByComparator);

		if (templateEntry != null) {
			return templateEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last template entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry fetchByUuid_Last(
		String uuid, OrderByComparator<TemplateEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<TemplateEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the template entries before and after the current template entry in the ordered set where uuid = &#63;.
	 *
	 * @param templateEntryId the primary key of the current template entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next template entry
	 * @throws NoSuchTemplateEntryException if a template entry with the primary key could not be found
	 */
	@Override
	public TemplateEntry[] findByUuid_PrevAndNext(
			long templateEntryId, String uuid,
			OrderByComparator<TemplateEntry> orderByComparator)
		throws NoSuchTemplateEntryException {

		uuid = Objects.toString(uuid, "");

		TemplateEntry templateEntry = findByPrimaryKey(templateEntryId);

		Session session = null;

		try {
			session = openSession();

			TemplateEntry[] array = new TemplateEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, templateEntry, uuid, orderByComparator, true);

			array[1] = templateEntry;

			array[2] = getByUuid_PrevAndNext(
				session, templateEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected TemplateEntry getByUuid_PrevAndNext(
		Session session, TemplateEntry templateEntry, String uuid,
		OrderByComparator<TemplateEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_TEMPLATEENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
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
			sb.append(TemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						templateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the template entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (TemplateEntry templateEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(templateEntry);
		}
	}

	/**
	 * Returns the number of template entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching template entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_TEMPLATEENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"templateEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(templateEntry.uuid IS NULL OR templateEntry.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the template entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchTemplateEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateEntryException {

		TemplateEntry templateEntry = fetchByUUID_G(uuid, groupId);

		if (templateEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTemplateEntryException(sb.toString());
		}

		return templateEntry;
	}

	/**
	 * Returns the template entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the template entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof TemplateEntry) {
			TemplateEntry templateEntry = (TemplateEntry)result;

			if (!Objects.equals(uuid, templateEntry.getUuid()) ||
				(groupId != templateEntry.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_TEMPLATEENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				List<TemplateEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					TemplateEntry templateEntry = list.get(0);

					result = templateEntry;

					cacheResult(templateEntry);
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
			return (TemplateEntry)result;
		}
	}

	/**
	 * Removes the template entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the template entry that was removed
	 */
	@Override
	public TemplateEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateEntryException {

		TemplateEntry templateEntry = findByUUID_G(uuid, groupId);

		return remove(templateEntry);
	}

	/**
	 * Returns the number of template entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching template entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G;

			finderArgs = new Object[] {uuid, groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TEMPLATEENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"templateEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(templateEntry.uuid IS NULL OR templateEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"templateEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the template entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching template entries
	 */
	@Override
	public List<TemplateEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the template entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @return the range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the template entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the template entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<TemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<TemplateEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (TemplateEntry templateEntry : list) {
					if (!uuid.equals(templateEntry.getUuid()) ||
						(companyId != templateEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_TEMPLATEENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<TemplateEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first template entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<TemplateEntry> orderByComparator)
		throws NoSuchTemplateEntryException {

		TemplateEntry templateEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (templateEntry != null) {
			return templateEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first template entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<TemplateEntry> orderByComparator) {

		List<TemplateEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last template entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<TemplateEntry> orderByComparator)
		throws NoSuchTemplateEntryException {

		TemplateEntry templateEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (templateEntry != null) {
			return templateEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last template entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<TemplateEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<TemplateEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the template entries before and after the current template entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param templateEntryId the primary key of the current template entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next template entry
	 * @throws NoSuchTemplateEntryException if a template entry with the primary key could not be found
	 */
	@Override
	public TemplateEntry[] findByUuid_C_PrevAndNext(
			long templateEntryId, String uuid, long companyId,
			OrderByComparator<TemplateEntry> orderByComparator)
		throws NoSuchTemplateEntryException {

		uuid = Objects.toString(uuid, "");

		TemplateEntry templateEntry = findByPrimaryKey(templateEntryId);

		Session session = null;

		try {
			session = openSession();

			TemplateEntry[] array = new TemplateEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, templateEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = templateEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, templateEntry, uuid, companyId, orderByComparator,
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

	protected TemplateEntry getByUuid_C_PrevAndNext(
		Session session, TemplateEntry templateEntry, String uuid,
		long companyId, OrderByComparator<TemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_TEMPLATEENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			sb.append(TemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						templateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the template entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (TemplateEntry templateEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(templateEntry);
		}
	}

	/**
	 * Returns the number of template entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching template entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TEMPLATEENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"templateEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(templateEntry.uuid IS NULL OR templateEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"templateEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private FinderPath _finderPathWithPaginationCountByGroupId;

	/**
	 * Returns all the template entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching template entries
	 */
	@Override
	public List<TemplateEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the template entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @return the range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the template entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the template entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<TemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<TemplateEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (TemplateEntry templateEntry : list) {
					if (groupId != templateEntry.getGroupId()) {
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

			sb.append(_SQL_SELECT_TEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<TemplateEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first template entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry findByGroupId_First(
			long groupId, OrderByComparator<TemplateEntry> orderByComparator)
		throws NoSuchTemplateEntryException {

		TemplateEntry templateEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (templateEntry != null) {
			return templateEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first template entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry fetchByGroupId_First(
		long groupId, OrderByComparator<TemplateEntry> orderByComparator) {

		List<TemplateEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last template entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry findByGroupId_Last(
			long groupId, OrderByComparator<TemplateEntry> orderByComparator)
		throws NoSuchTemplateEntryException {

		TemplateEntry templateEntry = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (templateEntry != null) {
			return templateEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last template entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<TemplateEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<TemplateEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the template entries before and after the current template entry in the ordered set where groupId = &#63;.
	 *
	 * @param templateEntryId the primary key of the current template entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next template entry
	 * @throws NoSuchTemplateEntryException if a template entry with the primary key could not be found
	 */
	@Override
	public TemplateEntry[] findByGroupId_PrevAndNext(
			long templateEntryId, long groupId,
			OrderByComparator<TemplateEntry> orderByComparator)
		throws NoSuchTemplateEntryException {

		TemplateEntry templateEntry = findByPrimaryKey(templateEntryId);

		Session session = null;

		try {
			session = openSession();

			TemplateEntry[] array = new TemplateEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, templateEntry, groupId, orderByComparator, true);

			array[1] = templateEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, templateEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected TemplateEntry getByGroupId_PrevAndNext(
		Session session, TemplateEntry templateEntry, long groupId,
		OrderByComparator<TemplateEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_TEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			sb.append(TemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						templateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the template entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching template entries
	 */
	@Override
	public List<TemplateEntry> findByGroupId(long[] groupIds) {
		return findByGroupId(
			groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the template entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @return the range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByGroupId(
		long[] groupIds, int start, int end) {

		return findByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the template entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator) {

		return findByGroupId(groupIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the template entries where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (groupIds.length == 1) {
			return findByGroupId(groupIds[0], start, end, orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {StringUtil.merge(groupIds)};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), start, end, orderByComparator
			};
		}

		List<TemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<TemplateEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByGroupId, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (TemplateEntry templateEntry : list) {
					if (!ArrayUtil.contains(
							groupIds, templateEntry.getGroupId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_TEMPLATEENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TemplateEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByGroupId, finderArgs,
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
	 * Removes all the template entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (TemplateEntry templateEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(templateEntry);
		}
	}

	/**
	 * Returns the number of template entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching template entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId;

			finderArgs = new Object[] {groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_TEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
	 * Returns the number of template entries where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching template entries
	 */
	@Override
	public int countByGroupId(long[] groupIds) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {StringUtil.merge(groupIds)};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByGroupId, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_TEMPLATEENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByGroupId, finderArgs,
						count);
				}
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"templateEntry.groupId = ?";

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_7 =
		"templateEntry.groupId IN (";

	private FinderPath _finderPathFetchByDDMTemplateId;
	private FinderPath _finderPathCountByDDMTemplateId;

	/**
	 * Returns the template entry where ddmTemplateId = &#63; or throws a <code>NoSuchTemplateEntryException</code> if it could not be found.
	 *
	 * @param ddmTemplateId the ddm template ID
	 * @return the matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry findByDDMTemplateId(long ddmTemplateId)
		throws NoSuchTemplateEntryException {

		TemplateEntry templateEntry = fetchByDDMTemplateId(ddmTemplateId);

		if (templateEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("ddmTemplateId=");
			sb.append(ddmTemplateId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTemplateEntryException(sb.toString());
		}

		return templateEntry;
	}

	/**
	 * Returns the template entry where ddmTemplateId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ddmTemplateId the ddm template ID
	 * @return the matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry fetchByDDMTemplateId(long ddmTemplateId) {
		return fetchByDDMTemplateId(ddmTemplateId, true);
	}

	/**
	 * Returns the template entry where ddmTemplateId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ddmTemplateId the ddm template ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry fetchByDDMTemplateId(
		long ddmTemplateId, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {ddmTemplateId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByDDMTemplateId, finderArgs);
		}

		if (result instanceof TemplateEntry) {
			TemplateEntry templateEntry = (TemplateEntry)result;

			if (ddmTemplateId != templateEntry.getDDMTemplateId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_TEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_DDMTEMPLATEID_DDMTEMPLATEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ddmTemplateId);

				List<TemplateEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByDDMTemplateId, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!productionMode || !useFinderCache) {
								finderArgs = new Object[] {ddmTemplateId};
							}

							_log.warn(
								"TemplateEntryPersistenceImpl.fetchByDDMTemplateId(long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					TemplateEntry templateEntry = list.get(0);

					result = templateEntry;

					cacheResult(templateEntry);
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
			return (TemplateEntry)result;
		}
	}

	/**
	 * Removes the template entry where ddmTemplateId = &#63; from the database.
	 *
	 * @param ddmTemplateId the ddm template ID
	 * @return the template entry that was removed
	 */
	@Override
	public TemplateEntry removeByDDMTemplateId(long ddmTemplateId)
		throws NoSuchTemplateEntryException {

		TemplateEntry templateEntry = findByDDMTemplateId(ddmTemplateId);

		return remove(templateEntry);
	}

	/**
	 * Returns the number of template entries where ddmTemplateId = &#63;.
	 *
	 * @param ddmTemplateId the ddm template ID
	 * @return the number of matching template entries
	 */
	@Override
	public int countByDDMTemplateId(long ddmTemplateId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByDDMTemplateId;

			finderArgs = new Object[] {ddmTemplateId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_TEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_DDMTEMPLATEID_DDMTEMPLATEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ddmTemplateId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_DDMTEMPLATEID_DDMTEMPLATEID_2 =
		"templateEntry.ddmTemplateId = ?";

	private FinderPath _finderPathWithPaginationFindByG_IICN;
	private FinderPath _finderPathWithoutPaginationFindByG_IICN;
	private FinderPath _finderPathCountByG_IICN;

	/**
	 * Returns all the template entries where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @return the matching template entries
	 */
	@Override
	public List<TemplateEntry> findByG_IICN(
		long groupId, String infoItemClassName) {

		return findByG_IICN(
			groupId, infoItemClassName, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the template entries where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @return the range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByG_IICN(
		long groupId, String infoItemClassName, int start, int end) {

		return findByG_IICN(groupId, infoItemClassName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the template entries where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByG_IICN(
		long groupId, String infoItemClassName, int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator) {

		return findByG_IICN(
			groupId, infoItemClassName, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the template entries where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByG_IICN(
		long groupId, String infoItemClassName, int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator,
		boolean useFinderCache) {

		infoItemClassName = Objects.toString(infoItemClassName, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_IICN;
				finderArgs = new Object[] {groupId, infoItemClassName};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_IICN;
			finderArgs = new Object[] {
				groupId, infoItemClassName, start, end, orderByComparator
			};
		}

		List<TemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<TemplateEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (TemplateEntry templateEntry : list) {
					if ((groupId != templateEntry.getGroupId()) ||
						!infoItemClassName.equals(
							templateEntry.getInfoItemClassName())) {

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

			sb.append(_SQL_SELECT_TEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_IICN_GROUPID_2);

			boolean bindInfoItemClassName = false;

			if (infoItemClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_IICN_INFOITEMCLASSNAME_3);
			}
			else {
				bindInfoItemClassName = true;

				sb.append(_FINDER_COLUMN_G_IICN_INFOITEMCLASSNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindInfoItemClassName) {
					queryPos.add(infoItemClassName);
				}

				list = (List<TemplateEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry findByG_IICN_First(
			long groupId, String infoItemClassName,
			OrderByComparator<TemplateEntry> orderByComparator)
		throws NoSuchTemplateEntryException {

		TemplateEntry templateEntry = fetchByG_IICN_First(
			groupId, infoItemClassName, orderByComparator);

		if (templateEntry != null) {
			return templateEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", infoItemClassName=");
		sb.append(infoItemClassName);

		sb.append("}");

		throw new NoSuchTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry fetchByG_IICN_First(
		long groupId, String infoItemClassName,
		OrderByComparator<TemplateEntry> orderByComparator) {

		List<TemplateEntry> list = findByG_IICN(
			groupId, infoItemClassName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry findByG_IICN_Last(
			long groupId, String infoItemClassName,
			OrderByComparator<TemplateEntry> orderByComparator)
		throws NoSuchTemplateEntryException {

		TemplateEntry templateEntry = fetchByG_IICN_Last(
			groupId, infoItemClassName, orderByComparator);

		if (templateEntry != null) {
			return templateEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", infoItemClassName=");
		sb.append(infoItemClassName);

		sb.append("}");

		throw new NoSuchTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry fetchByG_IICN_Last(
		long groupId, String infoItemClassName,
		OrderByComparator<TemplateEntry> orderByComparator) {

		int count = countByG_IICN(groupId, infoItemClassName);

		if (count == 0) {
			return null;
		}

		List<TemplateEntry> list = findByG_IICN(
			groupId, infoItemClassName, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the template entries before and after the current template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * @param templateEntryId the primary key of the current template entry
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next template entry
	 * @throws NoSuchTemplateEntryException if a template entry with the primary key could not be found
	 */
	@Override
	public TemplateEntry[] findByG_IICN_PrevAndNext(
			long templateEntryId, long groupId, String infoItemClassName,
			OrderByComparator<TemplateEntry> orderByComparator)
		throws NoSuchTemplateEntryException {

		infoItemClassName = Objects.toString(infoItemClassName, "");

		TemplateEntry templateEntry = findByPrimaryKey(templateEntryId);

		Session session = null;

		try {
			session = openSession();

			TemplateEntry[] array = new TemplateEntryImpl[3];

			array[0] = getByG_IICN_PrevAndNext(
				session, templateEntry, groupId, infoItemClassName,
				orderByComparator, true);

			array[1] = templateEntry;

			array[2] = getByG_IICN_PrevAndNext(
				session, templateEntry, groupId, infoItemClassName,
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

	protected TemplateEntry getByG_IICN_PrevAndNext(
		Session session, TemplateEntry templateEntry, long groupId,
		String infoItemClassName,
		OrderByComparator<TemplateEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_TEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_IICN_GROUPID_2);

		boolean bindInfoItemClassName = false;

		if (infoItemClassName.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_IICN_INFOITEMCLASSNAME_3);
		}
		else {
			bindInfoItemClassName = true;

			sb.append(_FINDER_COLUMN_G_IICN_INFOITEMCLASSNAME_2);
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
			sb.append(TemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindInfoItemClassName) {
			queryPos.add(infoItemClassName);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						templateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the template entries where groupId = &#63; and infoItemClassName = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 */
	@Override
	public void removeByG_IICN(long groupId, String infoItemClassName) {
		for (TemplateEntry templateEntry :
				findByG_IICN(
					groupId, infoItemClassName, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(templateEntry);
		}
	}

	/**
	 * Returns the number of template entries where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @return the number of matching template entries
	 */
	@Override
	public int countByG_IICN(long groupId, String infoItemClassName) {
		infoItemClassName = Objects.toString(infoItemClassName, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_IICN;

			finderArgs = new Object[] {groupId, infoItemClassName};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_IICN_GROUPID_2);

			boolean bindInfoItemClassName = false;

			if (infoItemClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_IICN_INFOITEMCLASSNAME_3);
			}
			else {
				bindInfoItemClassName = true;

				sb.append(_FINDER_COLUMN_G_IICN_INFOITEMCLASSNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindInfoItemClassName) {
					queryPos.add(infoItemClassName);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_IICN_GROUPID_2 =
		"templateEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_IICN_INFOITEMCLASSNAME_2 =
		"templateEntry.infoItemClassName = ?";

	private static final String _FINDER_COLUMN_G_IICN_INFOITEMCLASSNAME_3 =
		"(templateEntry.infoItemClassName IS NULL OR templateEntry.infoItemClassName = '')";

	private FinderPath _finderPathWithPaginationFindByG_IICN_IIFVK;
	private FinderPath _finderPathWithoutPaginationFindByG_IICN_IIFVK;
	private FinderPath _finderPathCountByG_IICN_IIFVK;
	private FinderPath _finderPathWithPaginationCountByG_IICN_IIFVK;

	/**
	 * Returns all the template entries where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @return the matching template entries
	 */
	@Override
	public List<TemplateEntry> findByG_IICN_IIFVK(
		long groupId, String infoItemClassName,
		String infoItemFormVariationKey) {

		return findByG_IICN_IIFVK(
			groupId, infoItemClassName, infoItemFormVariationKey,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the template entries where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @return the range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByG_IICN_IIFVK(
		long groupId, String infoItemClassName, String infoItemFormVariationKey,
		int start, int end) {

		return findByG_IICN_IIFVK(
			groupId, infoItemClassName, infoItemFormVariationKey, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the template entries where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByG_IICN_IIFVK(
		long groupId, String infoItemClassName, String infoItemFormVariationKey,
		int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator) {

		return findByG_IICN_IIFVK(
			groupId, infoItemClassName, infoItemFormVariationKey, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the template entries where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByG_IICN_IIFVK(
		long groupId, String infoItemClassName, String infoItemFormVariationKey,
		int start, int end, OrderByComparator<TemplateEntry> orderByComparator,
		boolean useFinderCache) {

		infoItemClassName = Objects.toString(infoItemClassName, "");
		infoItemFormVariationKey = Objects.toString(
			infoItemFormVariationKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_IICN_IIFVK;
				finderArgs = new Object[] {
					groupId, infoItemClassName, infoItemFormVariationKey
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_IICN_IIFVK;
			finderArgs = new Object[] {
				groupId, infoItemClassName, infoItemFormVariationKey, start,
				end, orderByComparator
			};
		}

		List<TemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<TemplateEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (TemplateEntry templateEntry : list) {
					if ((groupId != templateEntry.getGroupId()) ||
						!infoItemClassName.equals(
							templateEntry.getInfoItemClassName()) ||
						!infoItemFormVariationKey.equals(
							templateEntry.getInfoItemFormVariationKey())) {

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

			sb.append(_SQL_SELECT_TEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_IICN_IIFVK_GROUPID_2);

			boolean bindInfoItemClassName = false;

			if (infoItemClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMCLASSNAME_3);
			}
			else {
				bindInfoItemClassName = true;

				sb.append(_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMCLASSNAME_2);
			}

			boolean bindInfoItemFormVariationKey = false;

			if (infoItemFormVariationKey.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMFORMVARIATIONKEY_3);
			}
			else {
				bindInfoItemFormVariationKey = true;

				sb.append(
					_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMFORMVARIATIONKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindInfoItemClassName) {
					queryPos.add(infoItemClassName);
				}

				if (bindInfoItemFormVariationKey) {
					queryPos.add(infoItemFormVariationKey);
				}

				list = (List<TemplateEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry findByG_IICN_IIFVK_First(
			long groupId, String infoItemClassName,
			String infoItemFormVariationKey,
			OrderByComparator<TemplateEntry> orderByComparator)
		throws NoSuchTemplateEntryException {

		TemplateEntry templateEntry = fetchByG_IICN_IIFVK_First(
			groupId, infoItemClassName, infoItemFormVariationKey,
			orderByComparator);

		if (templateEntry != null) {
			return templateEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", infoItemClassName=");
		sb.append(infoItemClassName);

		sb.append(", infoItemFormVariationKey=");
		sb.append(infoItemFormVariationKey);

		sb.append("}");

		throw new NoSuchTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry fetchByG_IICN_IIFVK_First(
		long groupId, String infoItemClassName, String infoItemFormVariationKey,
		OrderByComparator<TemplateEntry> orderByComparator) {

		List<TemplateEntry> list = findByG_IICN_IIFVK(
			groupId, infoItemClassName, infoItemFormVariationKey, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry findByG_IICN_IIFVK_Last(
			long groupId, String infoItemClassName,
			String infoItemFormVariationKey,
			OrderByComparator<TemplateEntry> orderByComparator)
		throws NoSuchTemplateEntryException {

		TemplateEntry templateEntry = fetchByG_IICN_IIFVK_Last(
			groupId, infoItemClassName, infoItemFormVariationKey,
			orderByComparator);

		if (templateEntry != null) {
			return templateEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", infoItemClassName=");
		sb.append(infoItemClassName);

		sb.append(", infoItemFormVariationKey=");
		sb.append(infoItemFormVariationKey);

		sb.append("}");

		throw new NoSuchTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	@Override
	public TemplateEntry fetchByG_IICN_IIFVK_Last(
		long groupId, String infoItemClassName, String infoItemFormVariationKey,
		OrderByComparator<TemplateEntry> orderByComparator) {

		int count = countByG_IICN_IIFVK(
			groupId, infoItemClassName, infoItemFormVariationKey);

		if (count == 0) {
			return null;
		}

		List<TemplateEntry> list = findByG_IICN_IIFVK(
			groupId, infoItemClassName, infoItemFormVariationKey, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the template entries before and after the current template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * @param templateEntryId the primary key of the current template entry
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next template entry
	 * @throws NoSuchTemplateEntryException if a template entry with the primary key could not be found
	 */
	@Override
	public TemplateEntry[] findByG_IICN_IIFVK_PrevAndNext(
			long templateEntryId, long groupId, String infoItemClassName,
			String infoItemFormVariationKey,
			OrderByComparator<TemplateEntry> orderByComparator)
		throws NoSuchTemplateEntryException {

		infoItemClassName = Objects.toString(infoItemClassName, "");
		infoItemFormVariationKey = Objects.toString(
			infoItemFormVariationKey, "");

		TemplateEntry templateEntry = findByPrimaryKey(templateEntryId);

		Session session = null;

		try {
			session = openSession();

			TemplateEntry[] array = new TemplateEntryImpl[3];

			array[0] = getByG_IICN_IIFVK_PrevAndNext(
				session, templateEntry, groupId, infoItemClassName,
				infoItemFormVariationKey, orderByComparator, true);

			array[1] = templateEntry;

			array[2] = getByG_IICN_IIFVK_PrevAndNext(
				session, templateEntry, groupId, infoItemClassName,
				infoItemFormVariationKey, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected TemplateEntry getByG_IICN_IIFVK_PrevAndNext(
		Session session, TemplateEntry templateEntry, long groupId,
		String infoItemClassName, String infoItemFormVariationKey,
		OrderByComparator<TemplateEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_TEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_IICN_IIFVK_GROUPID_2);

		boolean bindInfoItemClassName = false;

		if (infoItemClassName.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMCLASSNAME_3);
		}
		else {
			bindInfoItemClassName = true;

			sb.append(_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMCLASSNAME_2);
		}

		boolean bindInfoItemFormVariationKey = false;

		if (infoItemFormVariationKey.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMFORMVARIATIONKEY_3);
		}
		else {
			bindInfoItemFormVariationKey = true;

			sb.append(_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMFORMVARIATIONKEY_2);
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
			sb.append(TemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindInfoItemClassName) {
			queryPos.add(infoItemClassName);
		}

		if (bindInfoItemFormVariationKey) {
			queryPos.add(infoItemFormVariationKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						templateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the template entries where groupId = any &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @return the matching template entries
	 */
	@Override
	public List<TemplateEntry> findByG_IICN_IIFVK(
		long[] groupIds, String infoItemClassName,
		String infoItemFormVariationKey) {

		return findByG_IICN_IIFVK(
			groupIds, infoItemClassName, infoItemFormVariationKey,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the template entries where groupId = any &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @return the range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByG_IICN_IIFVK(
		long[] groupIds, String infoItemClassName,
		String infoItemFormVariationKey, int start, int end) {

		return findByG_IICN_IIFVK(
			groupIds, infoItemClassName, infoItemFormVariationKey, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the template entries where groupId = any &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByG_IICN_IIFVK(
		long[] groupIds, String infoItemClassName,
		String infoItemFormVariationKey, int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator) {

		return findByG_IICN_IIFVK(
			groupIds, infoItemClassName, infoItemFormVariationKey, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the template entries where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching template entries
	 */
	@Override
	public List<TemplateEntry> findByG_IICN_IIFVK(
		long[] groupIds, String infoItemClassName,
		String infoItemFormVariationKey, int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		infoItemClassName = Objects.toString(infoItemClassName, "");
		infoItemFormVariationKey = Objects.toString(
			infoItemFormVariationKey, "");

		if (groupIds.length == 1) {
			return findByG_IICN_IIFVK(
				groupIds[0], infoItemClassName, infoItemFormVariationKey, start,
				end, orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds), infoItemClassName,
					infoItemFormVariationKey
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), infoItemClassName,
				infoItemFormVariationKey, start, end, orderByComparator
			};
		}

		List<TemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<TemplateEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByG_IICN_IIFVK, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (TemplateEntry templateEntry : list) {
					if (!ArrayUtil.contains(
							groupIds, templateEntry.getGroupId()) ||
						!infoItemClassName.equals(
							templateEntry.getInfoItemClassName()) ||
						!infoItemFormVariationKey.equals(
							templateEntry.getInfoItemFormVariationKey())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_TEMPLATEENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_IICN_IIFVK_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindInfoItemClassName = false;

			if (infoItemClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMCLASSNAME_3);
			}
			else {
				bindInfoItemClassName = true;

				sb.append(_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMCLASSNAME_2);
			}

			boolean bindInfoItemFormVariationKey = false;

			if (infoItemFormVariationKey.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMFORMVARIATIONKEY_3);
			}
			else {
				bindInfoItemFormVariationKey = true;

				sb.append(
					_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMFORMVARIATIONKEY_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindInfoItemClassName) {
					queryPos.add(infoItemClassName);
				}

				if (bindInfoItemFormVariationKey) {
					queryPos.add(infoItemFormVariationKey);
				}

				list = (List<TemplateEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_IICN_IIFVK, finderArgs,
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
	 * Removes all the template entries where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 */
	@Override
	public void removeByG_IICN_IIFVK(
		long groupId, String infoItemClassName,
		String infoItemFormVariationKey) {

		for (TemplateEntry templateEntry :
				findByG_IICN_IIFVK(
					groupId, infoItemClassName, infoItemFormVariationKey,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(templateEntry);
		}
	}

	/**
	 * Returns the number of template entries where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @return the number of matching template entries
	 */
	@Override
	public int countByG_IICN_IIFVK(
		long groupId, String infoItemClassName,
		String infoItemFormVariationKey) {

		infoItemClassName = Objects.toString(infoItemClassName, "");
		infoItemFormVariationKey = Objects.toString(
			infoItemFormVariationKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_IICN_IIFVK;

			finderArgs = new Object[] {
				groupId, infoItemClassName, infoItemFormVariationKey
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_TEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_IICN_IIFVK_GROUPID_2);

			boolean bindInfoItemClassName = false;

			if (infoItemClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMCLASSNAME_3);
			}
			else {
				bindInfoItemClassName = true;

				sb.append(_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMCLASSNAME_2);
			}

			boolean bindInfoItemFormVariationKey = false;

			if (infoItemFormVariationKey.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMFORMVARIATIONKEY_3);
			}
			else {
				bindInfoItemFormVariationKey = true;

				sb.append(
					_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMFORMVARIATIONKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindInfoItemClassName) {
					queryPos.add(infoItemClassName);
				}

				if (bindInfoItemFormVariationKey) {
					queryPos.add(infoItemFormVariationKey);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
	 * Returns the number of template entries where groupId = any &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @return the number of matching template entries
	 */
	@Override
	public int countByG_IICN_IIFVK(
		long[] groupIds, String infoItemClassName,
		String infoItemFormVariationKey) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		infoItemClassName = Objects.toString(infoItemClassName, "");
		infoItemFormVariationKey = Objects.toString(
			infoItemFormVariationKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), infoItemClassName,
				infoItemFormVariationKey
			};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_IICN_IIFVK, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_TEMPLATEENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_IICN_IIFVK_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindInfoItemClassName = false;

			if (infoItemClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMCLASSNAME_3);
			}
			else {
				bindInfoItemClassName = true;

				sb.append(_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMCLASSNAME_2);
			}

			boolean bindInfoItemFormVariationKey = false;

			if (infoItemFormVariationKey.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMFORMVARIATIONKEY_3);
			}
			else {
				bindInfoItemFormVariationKey = true;

				sb.append(
					_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMFORMVARIATIONKEY_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindInfoItemClassName) {
					queryPos.add(infoItemClassName);
				}

				if (bindInfoItemFormVariationKey) {
					queryPos.add(infoItemFormVariationKey);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByG_IICN_IIFVK,
						finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_IICN_IIFVK_GROUPID_2 =
		"templateEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_IICN_IIFVK_GROUPID_7 =
		"templateEntry.groupId IN (";

	private static final String
		_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMCLASSNAME_2 =
			"templateEntry.infoItemClassName = ? AND ";

	private static final String
		_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMCLASSNAME_3 =
			"(templateEntry.infoItemClassName IS NULL OR templateEntry.infoItemClassName = '') AND ";

	private static final String
		_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMFORMVARIATIONKEY_2 =
			"templateEntry.infoItemFormVariationKey = ?";

	private static final String
		_FINDER_COLUMN_G_IICN_IIFVK_INFOITEMFORMVARIATIONKEY_3 =
			"(templateEntry.infoItemFormVariationKey IS NULL OR templateEntry.infoItemFormVariationKey = '')";

	public TemplateEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(TemplateEntry.class);

		setModelImplClass(TemplateEntryImpl.class);
		setModelPKClass(long.class);

		setTable(TemplateEntryTable.INSTANCE);
	}

	/**
	 * Caches the template entry in the entity cache if it is enabled.
	 *
	 * @param templateEntry the template entry
	 */
	@Override
	public void cacheResult(TemplateEntry templateEntry) {
		if (templateEntry.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			TemplateEntryImpl.class, templateEntry.getPrimaryKey(),
			templateEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {templateEntry.getUuid(), templateEntry.getGroupId()},
			templateEntry);

		finderCache.putResult(
			_finderPathFetchByDDMTemplateId,
			new Object[] {templateEntry.getDDMTemplateId()}, templateEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the template entries in the entity cache if it is enabled.
	 *
	 * @param templateEntries the template entries
	 */
	@Override
	public void cacheResult(List<TemplateEntry> templateEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (templateEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TemplateEntry templateEntry : templateEntries) {
			if (templateEntry.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					TemplateEntryImpl.class, templateEntry.getPrimaryKey()) ==
						null) {

				cacheResult(templateEntry);
			}
		}
	}

	/**
	 * Clears the cache for all template entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TemplateEntryImpl.class);

		finderCache.clearCache(TemplateEntryImpl.class);
	}

	/**
	 * Clears the cache for the template entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TemplateEntry templateEntry) {
		entityCache.removeResult(TemplateEntryImpl.class, templateEntry);
	}

	@Override
	public void clearCache(List<TemplateEntry> templateEntries) {
		for (TemplateEntry templateEntry : templateEntries) {
			entityCache.removeResult(TemplateEntryImpl.class, templateEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(TemplateEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(TemplateEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		TemplateEntryModelImpl templateEntryModelImpl) {

		Object[] args = new Object[] {
			templateEntryModelImpl.getUuid(),
			templateEntryModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, templateEntryModelImpl);

		args = new Object[] {templateEntryModelImpl.getDDMTemplateId()};

		finderCache.putResult(
			_finderPathCountByDDMTemplateId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByDDMTemplateId, args, templateEntryModelImpl);
	}

	/**
	 * Creates a new template entry with the primary key. Does not add the template entry to the database.
	 *
	 * @param templateEntryId the primary key for the new template entry
	 * @return the new template entry
	 */
	@Override
	public TemplateEntry create(long templateEntryId) {
		TemplateEntry templateEntry = new TemplateEntryImpl();

		templateEntry.setNew(true);
		templateEntry.setPrimaryKey(templateEntryId);

		String uuid = PortalUUIDUtil.generate();

		templateEntry.setUuid(uuid);

		templateEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return templateEntry;
	}

	/**
	 * Removes the template entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param templateEntryId the primary key of the template entry
	 * @return the template entry that was removed
	 * @throws NoSuchTemplateEntryException if a template entry with the primary key could not be found
	 */
	@Override
	public TemplateEntry remove(long templateEntryId)
		throws NoSuchTemplateEntryException {

		return remove((Serializable)templateEntryId);
	}

	/**
	 * Removes the template entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the template entry
	 * @return the template entry that was removed
	 * @throws NoSuchTemplateEntryException if a template entry with the primary key could not be found
	 */
	@Override
	public TemplateEntry remove(Serializable primaryKey)
		throws NoSuchTemplateEntryException {

		Session session = null;

		try {
			session = openSession();

			TemplateEntry templateEntry = (TemplateEntry)session.get(
				TemplateEntryImpl.class, primaryKey);

			if (templateEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTemplateEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(templateEntry);
		}
		catch (NoSuchTemplateEntryException noSuchEntityException) {
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
	protected TemplateEntry removeImpl(TemplateEntry templateEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(templateEntry)) {
				templateEntry = (TemplateEntry)session.get(
					TemplateEntryImpl.class, templateEntry.getPrimaryKeyObj());
			}

			if ((templateEntry != null) &&
				ctPersistenceHelper.isRemove(templateEntry)) {

				session.delete(templateEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (templateEntry != null) {
			clearCache(templateEntry);
		}

		return templateEntry;
	}

	@Override
	public TemplateEntry updateImpl(TemplateEntry templateEntry) {
		boolean isNew = templateEntry.isNew();

		if (!(templateEntry instanceof TemplateEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(templateEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					templateEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in templateEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TemplateEntry implementation " +
					templateEntry.getClass());
		}

		TemplateEntryModelImpl templateEntryModelImpl =
			(TemplateEntryModelImpl)templateEntry;

		if (Validator.isNull(templateEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			templateEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (templateEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				templateEntry.setCreateDate(date);
			}
			else {
				templateEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!templateEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				templateEntry.setModifiedDate(date);
			}
			else {
				templateEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(templateEntry)) {
				if (!isNew) {
					session.evict(
						TemplateEntryImpl.class,
						templateEntry.getPrimaryKeyObj());
				}

				session.save(templateEntry);
			}
			else {
				templateEntry = (TemplateEntry)session.merge(templateEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (templateEntry.getCtCollectionId() != 0) {
			if (isNew) {
				templateEntry.setNew(false);
			}

			templateEntry.resetOriginalValues();

			return templateEntry;
		}

		entityCache.putResult(
			TemplateEntryImpl.class, templateEntryModelImpl, false, true);

		cacheUniqueFindersCache(templateEntryModelImpl);

		if (isNew) {
			templateEntry.setNew(false);
		}

		templateEntry.resetOriginalValues();

		return templateEntry;
	}

	/**
	 * Returns the template entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the template entry
	 * @return the template entry
	 * @throws NoSuchTemplateEntryException if a template entry with the primary key could not be found
	 */
	@Override
	public TemplateEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTemplateEntryException {

		TemplateEntry templateEntry = fetchByPrimaryKey(primaryKey);

		if (templateEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTemplateEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return templateEntry;
	}

	/**
	 * Returns the template entry with the primary key or throws a <code>NoSuchTemplateEntryException</code> if it could not be found.
	 *
	 * @param templateEntryId the primary key of the template entry
	 * @return the template entry
	 * @throws NoSuchTemplateEntryException if a template entry with the primary key could not be found
	 */
	@Override
	public TemplateEntry findByPrimaryKey(long templateEntryId)
		throws NoSuchTemplateEntryException {

		return findByPrimaryKey((Serializable)templateEntryId);
	}

	/**
	 * Returns the template entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the template entry
	 * @return the template entry, or <code>null</code> if a template entry with the primary key could not be found
	 */
	@Override
	public TemplateEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(TemplateEntry.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		TemplateEntry templateEntry = null;

		Session session = null;

		try {
			session = openSession();

			templateEntry = (TemplateEntry)session.get(
				TemplateEntryImpl.class, primaryKey);

			if (templateEntry != null) {
				cacheResult(templateEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return templateEntry;
	}

	/**
	 * Returns the template entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param templateEntryId the primary key of the template entry
	 * @return the template entry, or <code>null</code> if a template entry with the primary key could not be found
	 */
	@Override
	public TemplateEntry fetchByPrimaryKey(long templateEntryId) {
		return fetchByPrimaryKey((Serializable)templateEntryId);
	}

	@Override
	public Map<Serializable, TemplateEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(TemplateEntry.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TemplateEntry> map =
			new HashMap<Serializable, TemplateEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TemplateEntry templateEntry = fetchByPrimaryKey(primaryKey);

			if (templateEntry != null) {
				map.put(primaryKey, templateEntry);
			}

			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (TemplateEntry templateEntry :
					(List<TemplateEntry>)query.list()) {

				map.put(templateEntry.getPrimaryKeyObj(), templateEntry);

				cacheResult(templateEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the template entries.
	 *
	 * @return the template entries
	 */
	@Override
	public List<TemplateEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the template entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @return the range of template entries
	 */
	@Override
	public List<TemplateEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the template entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of template entries
	 */
	@Override
	public List<TemplateEntry> findAll(
		int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the template entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of template entries
	 */
	@Override
	public List<TemplateEntry> findAll(
		int start, int end, OrderByComparator<TemplateEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<TemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<TemplateEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TEMPLATEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TEMPLATEENTRY;

				sql = sql.concat(TemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TemplateEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Removes all the template entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TemplateEntry templateEntry : findAll()) {
			remove(templateEntry);
		}
	}

	/**
	 * Returns the number of template entries.
	 *
	 * @return the number of template entries
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TemplateEntry.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TEMPLATEENTRY);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
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
		return "templateEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_TEMPLATEENTRY;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return TemplateEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "TemplateEntry";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("ddmTemplateId");
		ctStrictColumnNames.add("infoItemClassName");
		ctStrictColumnNames.add("infoItemFormVariationKey");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("templateEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});
	}

	/**
	 * Initializes the template entry persistence.
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

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathWithPaginationCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathFetchByDDMTemplateId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByDDMTemplateId",
			new String[] {Long.class.getName()}, new String[] {"ddmTemplateId"},
			true);

		_finderPathCountByDDMTemplateId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByDDMTemplateId",
			new String[] {Long.class.getName()}, new String[] {"ddmTemplateId"},
			false);

		_finderPathWithPaginationFindByG_IICN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_IICN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "infoItemClassName"}, true);

		_finderPathWithoutPaginationFindByG_IICN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_IICN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "infoItemClassName"}, true);

		_finderPathCountByG_IICN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_IICN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "infoItemClassName"}, false);

		_finderPathWithPaginationFindByG_IICN_IIFVK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_IICN_IIFVK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "infoItemClassName", "infoItemFormVariationKey"
			},
			true);

		_finderPathWithoutPaginationFindByG_IICN_IIFVK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_IICN_IIFVK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {
				"groupId", "infoItemClassName", "infoItemFormVariationKey"
			},
			true);

		_finderPathCountByG_IICN_IIFVK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_IICN_IIFVK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {
				"groupId", "infoItemClassName", "infoItemFormVariationKey"
			},
			false);

		_finderPathWithPaginationCountByG_IICN_IIFVK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_IICN_IIFVK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {
				"groupId", "infoItemClassName", "infoItemFormVariationKey"
			},
			false);

		_setTemplateEntryUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setTemplateEntryUtilPersistence(null);

		entityCache.removeCache(TemplateEntryImpl.class.getName());
	}

	private void _setTemplateEntryUtilPersistence(
		TemplateEntryPersistence templateEntryPersistence) {

		try {
			Field field = TemplateEntryUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, templateEntryPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = TemplatePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = TemplatePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = TemplatePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_TEMPLATEENTRY =
		"SELECT templateEntry FROM TemplateEntry templateEntry";

	private static final String _SQL_SELECT_TEMPLATEENTRY_WHERE =
		"SELECT templateEntry FROM TemplateEntry templateEntry WHERE ";

	private static final String _SQL_COUNT_TEMPLATEENTRY =
		"SELECT COUNT(templateEntry) FROM TemplateEntry templateEntry";

	private static final String _SQL_COUNT_TEMPLATEENTRY_WHERE =
		"SELECT COUNT(templateEntry) FROM TemplateEntry templateEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "templateEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TemplateEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TemplateEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TemplateEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private TemplateEntryModelArgumentsResolver
		_templateEntryModelArgumentsResolver;

}