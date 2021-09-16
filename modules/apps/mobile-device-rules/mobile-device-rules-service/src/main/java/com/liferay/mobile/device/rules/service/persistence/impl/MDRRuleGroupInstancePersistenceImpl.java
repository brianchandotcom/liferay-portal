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

package com.liferay.mobile.device.rules.service.persistence.impl;

import com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException;
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstanceTable;
import com.liferay.mobile.device.rules.model.impl.MDRRuleGroupInstanceImpl;
import com.liferay.mobile.device.rules.model.impl.MDRRuleGroupInstanceModelImpl;
import com.liferay.mobile.device.rules.service.persistence.MDRRuleGroupInstancePersistence;
import com.liferay.mobile.device.rules.service.persistence.impl.constants.MDRPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
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
 * The persistence implementation for the mdr rule group instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @generated
 */
@Component(
	service = {MDRRuleGroupInstancePersistence.class, BasePersistence.class}
)
public class MDRRuleGroupInstancePersistenceImpl
	extends BasePersistenceImpl<MDRRuleGroupInstance>
	implements MDRRuleGroupInstancePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MDRRuleGroupInstanceUtil</code> to access the mdr rule group instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MDRRuleGroupInstanceImpl.class.getName();

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
	 * Returns all the mdr rule group instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mdr rule group instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the mdr rule group instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mdr rule group instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<MDRRuleGroupInstance> list = null;

		if (useFinderCache) {
			list = (List<MDRRuleGroupInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (MDRRuleGroupInstance mdrRuleGroupInstance : list) {
					if (!uuid.equals(mdrRuleGroupInstance.getUuid())) {
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

			sb.append(_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);

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
				sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
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

				list = (List<MDRRuleGroupInstance>)QueryUtil.list(
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
	 * Returns the first mdr rule group instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance findByUuid_First(
			String uuid,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = fetchByUuid_First(
			uuid, orderByComparator);

		if (mdrRuleGroupInstance != null) {
			return mdrRuleGroupInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchRuleGroupInstanceException(sb.toString());
	}

	/**
	 * Returns the first mdr rule group instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByUuid_First(
		String uuid,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		List<MDRRuleGroupInstance> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last mdr rule group instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance findByUuid_Last(
			String uuid,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = fetchByUuid_Last(
			uuid, orderByComparator);

		if (mdrRuleGroupInstance != null) {
			return mdrRuleGroupInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchRuleGroupInstanceException(sb.toString());
	}

	/**
	 * Returns the last mdr rule group instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByUuid_Last(
		String uuid,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<MDRRuleGroupInstance> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the mdr rule group instances before and after the current mdr rule group instance in the ordered set where uuid = &#63;.
	 *
	 * @param ruleGroupInstanceId the primary key of the current mdr rule group instance
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	@Override
	public MDRRuleGroupInstance[] findByUuid_PrevAndNext(
			long ruleGroupInstanceId, String uuid,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		uuid = Objects.toString(uuid, "");

		MDRRuleGroupInstance mdrRuleGroupInstance = findByPrimaryKey(
			ruleGroupInstanceId);

		Session session = null;

		try {
			session = openSession();

			MDRRuleGroupInstance[] array = new MDRRuleGroupInstanceImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, mdrRuleGroupInstance, uuid, orderByComparator, true);

			array[1] = mdrRuleGroupInstance;

			array[2] = getByUuid_PrevAndNext(
				session, mdrRuleGroupInstance, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MDRRuleGroupInstance getByUuid_PrevAndNext(
		Session session, MDRRuleGroupInstance mdrRuleGroupInstance, String uuid,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);

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
			sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
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
						mdrRuleGroupInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MDRRuleGroupInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the mdr rule group instances where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (MDRRuleGroupInstance mdrRuleGroupInstance :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mdrRuleGroupInstance);
		}
	}

	/**
	 * Returns the number of mdr rule group instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching mdr rule group instances
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MDRRULEGROUPINSTANCE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"mdrRuleGroupInstance.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(mdrRuleGroupInstance.uuid IS NULL OR mdrRuleGroupInstance.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the mdr rule group instance where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchRuleGroupInstanceException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance findByUUID_G(String uuid, long groupId)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = fetchByUUID_G(
			uuid, groupId);

		if (mdrRuleGroupInstance == null) {
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

			throw new NoSuchRuleGroupInstanceException(sb.toString());
		}

		return mdrRuleGroupInstance;
	}

	/**
	 * Returns the mdr rule group instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the mdr rule group instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof MDRRuleGroupInstance) {
			MDRRuleGroupInstance mdrRuleGroupInstance =
				(MDRRuleGroupInstance)result;

			if (!Objects.equals(uuid, mdrRuleGroupInstance.getUuid()) ||
				(groupId != mdrRuleGroupInstance.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);

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

				List<MDRRuleGroupInstance> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					MDRRuleGroupInstance mdrRuleGroupInstance = list.get(0);

					result = mdrRuleGroupInstance;

					cacheResult(mdrRuleGroupInstance);
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
			return (MDRRuleGroupInstance)result;
		}
	}

	/**
	 * Removes the mdr rule group instance where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the mdr rule group instance that was removed
	 */
	@Override
	public MDRRuleGroupInstance removeByUUID_G(String uuid, long groupId)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = findByUUID_G(uuid, groupId);

		return remove(mdrRuleGroupInstance);
	}

	/**
	 * Returns the number of mdr rule group instances where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching mdr rule group instances
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MDRRULEGROUPINSTANCE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"mdrRuleGroupInstance.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(mdrRuleGroupInstance.uuid IS NULL OR mdrRuleGroupInstance.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"mdrRuleGroupInstance.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the mdr rule group instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mdr rule group instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the mdr rule group instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mdr rule group instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<MDRRuleGroupInstance> list = null;

		if (useFinderCache) {
			list = (List<MDRRuleGroupInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (MDRRuleGroupInstance mdrRuleGroupInstance : list) {
					if (!uuid.equals(mdrRuleGroupInstance.getUuid()) ||
						(companyId != mdrRuleGroupInstance.getCompanyId())) {

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

			sb.append(_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);

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
				sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
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

				list = (List<MDRRuleGroupInstance>)QueryUtil.list(
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
	 * Returns the first mdr rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (mdrRuleGroupInstance != null) {
			return mdrRuleGroupInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchRuleGroupInstanceException(sb.toString());
	}

	/**
	 * Returns the first mdr rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		List<MDRRuleGroupInstance> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last mdr rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (mdrRuleGroupInstance != null) {
			return mdrRuleGroupInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchRuleGroupInstanceException(sb.toString());
	}

	/**
	 * Returns the last mdr rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<MDRRuleGroupInstance> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the mdr rule group instances before and after the current mdr rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param ruleGroupInstanceId the primary key of the current mdr rule group instance
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	@Override
	public MDRRuleGroupInstance[] findByUuid_C_PrevAndNext(
			long ruleGroupInstanceId, String uuid, long companyId,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		uuid = Objects.toString(uuid, "");

		MDRRuleGroupInstance mdrRuleGroupInstance = findByPrimaryKey(
			ruleGroupInstanceId);

		Session session = null;

		try {
			session = openSession();

			MDRRuleGroupInstance[] array = new MDRRuleGroupInstanceImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, mdrRuleGroupInstance, uuid, companyId,
				orderByComparator, true);

			array[1] = mdrRuleGroupInstance;

			array[2] = getByUuid_C_PrevAndNext(
				session, mdrRuleGroupInstance, uuid, companyId,
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

	protected MDRRuleGroupInstance getByUuid_C_PrevAndNext(
		Session session, MDRRuleGroupInstance mdrRuleGroupInstance, String uuid,
		long companyId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
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

		sb.append(_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);

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
			sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
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
						mdrRuleGroupInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MDRRuleGroupInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the mdr rule group instances where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (MDRRuleGroupInstance mdrRuleGroupInstance :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(mdrRuleGroupInstance);
		}
	}

	/**
	 * Returns the number of mdr rule group instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching mdr rule group instances
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MDRRULEGROUPINSTANCE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"mdrRuleGroupInstance.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(mdrRuleGroupInstance.uuid IS NULL OR mdrRuleGroupInstance.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"mdrRuleGroupInstance.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the mdr rule group instances where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mdr rule group instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the mdr rule group instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mdr rule group instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<MDRRuleGroupInstance> list = null;

		if (useFinderCache) {
			list = (List<MDRRuleGroupInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (MDRRuleGroupInstance mdrRuleGroupInstance : list) {
					if (groupId != mdrRuleGroupInstance.getGroupId()) {
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

			sb.append(_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<MDRRuleGroupInstance>)QueryUtil.list(
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
	 * Returns the first mdr rule group instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance findByGroupId_First(
			long groupId,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = fetchByGroupId_First(
			groupId, orderByComparator);

		if (mdrRuleGroupInstance != null) {
			return mdrRuleGroupInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchRuleGroupInstanceException(sb.toString());
	}

	/**
	 * Returns the first mdr rule group instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByGroupId_First(
		long groupId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		List<MDRRuleGroupInstance> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last mdr rule group instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance findByGroupId_Last(
			long groupId,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (mdrRuleGroupInstance != null) {
			return mdrRuleGroupInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchRuleGroupInstanceException(sb.toString());
	}

	/**
	 * Returns the last mdr rule group instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByGroupId_Last(
		long groupId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<MDRRuleGroupInstance> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the mdr rule group instances before and after the current mdr rule group instance in the ordered set where groupId = &#63;.
	 *
	 * @param ruleGroupInstanceId the primary key of the current mdr rule group instance
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	@Override
	public MDRRuleGroupInstance[] findByGroupId_PrevAndNext(
			long ruleGroupInstanceId, long groupId,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = findByPrimaryKey(
			ruleGroupInstanceId);

		Session session = null;

		try {
			session = openSession();

			MDRRuleGroupInstance[] array = new MDRRuleGroupInstanceImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, mdrRuleGroupInstance, groupId, orderByComparator,
				true);

			array[1] = mdrRuleGroupInstance;

			array[2] = getByGroupId_PrevAndNext(
				session, mdrRuleGroupInstance, groupId, orderByComparator,
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

	protected MDRRuleGroupInstance getByGroupId_PrevAndNext(
		Session session, MDRRuleGroupInstance mdrRuleGroupInstance,
		long groupId, OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);

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
			sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
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
						mdrRuleGroupInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MDRRuleGroupInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the mdr rule group instances that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching mdr rule group instances that the user has permission to view
	 */
	@Override
	public List<MDRRuleGroupInstance> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mdr rule group instances that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of matching mdr rule group instances that the user has permission to view
	 */
	@Override
	public List<MDRRuleGroupInstance> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the mdr rule group instances that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule group instances that the user has permission to view
	 */
	@Override
	public List<MDRRuleGroupInstance> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_MDRRULEGROUPINSTANCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_MDRRULEGROUPINSTANCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MDRRuleGroupInstance.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, MDRRuleGroupInstanceImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, MDRRuleGroupInstanceImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<MDRRuleGroupInstance>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the mdr rule group instances before and after the current mdr rule group instance in the ordered set of mdr rule group instances that the user has permission to view where groupId = &#63;.
	 *
	 * @param ruleGroupInstanceId the primary key of the current mdr rule group instance
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	@Override
	public MDRRuleGroupInstance[] filterFindByGroupId_PrevAndNext(
			long ruleGroupInstanceId, long groupId,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(
				ruleGroupInstanceId, groupId, orderByComparator);
		}

		MDRRuleGroupInstance mdrRuleGroupInstance = findByPrimaryKey(
			ruleGroupInstanceId);

		Session session = null;

		try {
			session = openSession();

			MDRRuleGroupInstance[] array = new MDRRuleGroupInstanceImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(
				session, mdrRuleGroupInstance, groupId, orderByComparator,
				true);

			array[1] = mdrRuleGroupInstance;

			array[2] = filterGetByGroupId_PrevAndNext(
				session, mdrRuleGroupInstance, groupId, orderByComparator,
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

	protected MDRRuleGroupInstance filterGetByGroupId_PrevAndNext(
		Session session, MDRRuleGroupInstance mdrRuleGroupInstance,
		long groupId, OrderByComparator<MDRRuleGroupInstance> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_MDRRULEGROUPINSTANCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_MDRRULEGROUPINSTANCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MDRRuleGroupInstance.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, MDRRuleGroupInstanceImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, MDRRuleGroupInstanceImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						mdrRuleGroupInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MDRRuleGroupInstance> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the mdr rule group instances where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (MDRRuleGroupInstance mdrRuleGroupInstance :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mdrRuleGroupInstance);
		}
	}

	/**
	 * Returns the number of mdr rule group instances where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching mdr rule group instances
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MDRRULEGROUPINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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
	 * Returns the number of mdr rule group instances that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching mdr rule group instances that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_MDRRULEGROUPINSTANCE_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MDRRuleGroupInstance.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"mdrRuleGroupInstance.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByRuleGroupId;
	private FinderPath _finderPathWithoutPaginationFindByRuleGroupId;
	private FinderPath _finderPathCountByRuleGroupId;

	/**
	 * Returns all the mdr rule group instances where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @return the matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByRuleGroupId(long ruleGroupId) {
		return findByRuleGroupId(
			ruleGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mdr rule group instances where ruleGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param ruleGroupId the rule group ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId, int start, int end) {

		return findByRuleGroupId(ruleGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the mdr rule group instances where ruleGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param ruleGroupId the rule group ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		return findByRuleGroupId(
			ruleGroupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mdr rule group instances where ruleGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param ruleGroupId the rule group ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByRuleGroupId;
				finderArgs = new Object[] {ruleGroupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByRuleGroupId;
			finderArgs = new Object[] {
				ruleGroupId, start, end, orderByComparator
			};
		}

		List<MDRRuleGroupInstance> list = null;

		if (useFinderCache) {
			list = (List<MDRRuleGroupInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (MDRRuleGroupInstance mdrRuleGroupInstance : list) {
					if (ruleGroupId != mdrRuleGroupInstance.getRuleGroupId()) {
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

			sb.append(_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_RULEGROUPID_RULEGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ruleGroupId);

				list = (List<MDRRuleGroupInstance>)QueryUtil.list(
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
	 * Returns the first mdr rule group instance in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance findByRuleGroupId_First(
			long ruleGroupId,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = fetchByRuleGroupId_First(
			ruleGroupId, orderByComparator);

		if (mdrRuleGroupInstance != null) {
			return mdrRuleGroupInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ruleGroupId=");
		sb.append(ruleGroupId);

		sb.append("}");

		throw new NoSuchRuleGroupInstanceException(sb.toString());
	}

	/**
	 * Returns the first mdr rule group instance in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByRuleGroupId_First(
		long ruleGroupId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		List<MDRRuleGroupInstance> list = findByRuleGroupId(
			ruleGroupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last mdr rule group instance in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance findByRuleGroupId_Last(
			long ruleGroupId,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = fetchByRuleGroupId_Last(
			ruleGroupId, orderByComparator);

		if (mdrRuleGroupInstance != null) {
			return mdrRuleGroupInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ruleGroupId=");
		sb.append(ruleGroupId);

		sb.append("}");

		throw new NoSuchRuleGroupInstanceException(sb.toString());
	}

	/**
	 * Returns the last mdr rule group instance in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByRuleGroupId_Last(
		long ruleGroupId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		int count = countByRuleGroupId(ruleGroupId);

		if (count == 0) {
			return null;
		}

		List<MDRRuleGroupInstance> list = findByRuleGroupId(
			ruleGroupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the mdr rule group instances before and after the current mdr rule group instance in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupInstanceId the primary key of the current mdr rule group instance
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	@Override
	public MDRRuleGroupInstance[] findByRuleGroupId_PrevAndNext(
			long ruleGroupInstanceId, long ruleGroupId,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = findByPrimaryKey(
			ruleGroupInstanceId);

		Session session = null;

		try {
			session = openSession();

			MDRRuleGroupInstance[] array = new MDRRuleGroupInstanceImpl[3];

			array[0] = getByRuleGroupId_PrevAndNext(
				session, mdrRuleGroupInstance, ruleGroupId, orderByComparator,
				true);

			array[1] = mdrRuleGroupInstance;

			array[2] = getByRuleGroupId_PrevAndNext(
				session, mdrRuleGroupInstance, ruleGroupId, orderByComparator,
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

	protected MDRRuleGroupInstance getByRuleGroupId_PrevAndNext(
		Session session, MDRRuleGroupInstance mdrRuleGroupInstance,
		long ruleGroupId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);

		sb.append(_FINDER_COLUMN_RULEGROUPID_RULEGROUPID_2);

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
			sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ruleGroupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						mdrRuleGroupInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MDRRuleGroupInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the mdr rule group instances where ruleGroupId = &#63; from the database.
	 *
	 * @param ruleGroupId the rule group ID
	 */
	@Override
	public void removeByRuleGroupId(long ruleGroupId) {
		for (MDRRuleGroupInstance mdrRuleGroupInstance :
				findByRuleGroupId(
					ruleGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mdrRuleGroupInstance);
		}
	}

	/**
	 * Returns the number of mdr rule group instances where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @return the number of matching mdr rule group instances
	 */
	@Override
	public int countByRuleGroupId(long ruleGroupId) {
		FinderPath finderPath = _finderPathCountByRuleGroupId;

		Object[] finderArgs = new Object[] {ruleGroupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MDRRULEGROUPINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_RULEGROUPID_RULEGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ruleGroupId);

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

	private static final String _FINDER_COLUMN_RULEGROUPID_RULEGROUPID_2 =
		"mdrRuleGroupInstance.ruleGroupId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the mdr rule group instances where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByC_C(
		long classNameId, long classPK) {

		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mdr rule group instances where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the mdr rule group instances where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mdr rule group instances where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<MDRRuleGroupInstance> list = null;

		if (useFinderCache) {
			list = (List<MDRRuleGroupInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (MDRRuleGroupInstance mdrRuleGroupInstance : list) {
					if ((classNameId !=
							mdrRuleGroupInstance.getClassNameId()) ||
						(classPK != mdrRuleGroupInstance.getClassPK())) {

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

			sb.append(_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<MDRRuleGroupInstance>)QueryUtil.list(
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
	 * Returns the first mdr rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (mdrRuleGroupInstance != null) {
			return mdrRuleGroupInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchRuleGroupInstanceException(sb.toString());
	}

	/**
	 * Returns the first mdr rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		List<MDRRuleGroupInstance> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last mdr rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (mdrRuleGroupInstance != null) {
			return mdrRuleGroupInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchRuleGroupInstanceException(sb.toString());
	}

	/**
	 * Returns the last mdr rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<MDRRuleGroupInstance> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the mdr rule group instances before and after the current mdr rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param ruleGroupInstanceId the primary key of the current mdr rule group instance
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	@Override
	public MDRRuleGroupInstance[] findByC_C_PrevAndNext(
			long ruleGroupInstanceId, long classNameId, long classPK,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = findByPrimaryKey(
			ruleGroupInstanceId);

		Session session = null;

		try {
			session = openSession();

			MDRRuleGroupInstance[] array = new MDRRuleGroupInstanceImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, mdrRuleGroupInstance, classNameId, classPK,
				orderByComparator, true);

			array[1] = mdrRuleGroupInstance;

			array[2] = getByC_C_PrevAndNext(
				session, mdrRuleGroupInstance, classNameId, classPK,
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

	protected MDRRuleGroupInstance getByC_C_PrevAndNext(
		Session session, MDRRuleGroupInstance mdrRuleGroupInstance,
		long classNameId, long classPK,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
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

		sb.append(_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						mdrRuleGroupInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MDRRuleGroupInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the mdr rule group instances where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (MDRRuleGroupInstance mdrRuleGroupInstance :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(mdrRuleGroupInstance);
		}
	}

	/**
	 * Returns the number of mdr rule group instances where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching mdr rule group instances
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MDRRULEGROUPINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"mdrRuleGroupInstance.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"mdrRuleGroupInstance.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C;
	private FinderPath _finderPathCountByG_C_C;

	/**
	 * Returns all the mdr rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByG_C_C(
		long groupId, long classNameId, long classPK) {

		return findByG_C_C(
			groupId, classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the mdr rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end) {

		return findByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the mdr rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		return findByG_C_C(
			groupId, classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mdr rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C;
				finderArgs = new Object[] {groupId, classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C_C;
			finderArgs = new Object[] {
				groupId, classNameId, classPK, start, end, orderByComparator
			};
		}

		List<MDRRuleGroupInstance> list = null;

		if (useFinderCache) {
			list = (List<MDRRuleGroupInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (MDRRuleGroupInstance mdrRuleGroupInstance : list) {
					if ((groupId != mdrRuleGroupInstance.getGroupId()) ||
						(classNameId !=
							mdrRuleGroupInstance.getClassNameId()) ||
						(classPK != mdrRuleGroupInstance.getClassPK())) {

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

			sb.append(_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<MDRRuleGroupInstance>)QueryUtil.list(
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
	 * Returns the first mdr rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance findByG_C_C_First(
			long groupId, long classNameId, long classPK,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = fetchByG_C_C_First(
			groupId, classNameId, classPK, orderByComparator);

		if (mdrRuleGroupInstance != null) {
			return mdrRuleGroupInstance;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchRuleGroupInstanceException(sb.toString());
	}

	/**
	 * Returns the first mdr rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByG_C_C_First(
		long groupId, long classNameId, long classPK,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		List<MDRRuleGroupInstance> list = findByG_C_C(
			groupId, classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last mdr rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance findByG_C_C_Last(
			long groupId, long classNameId, long classPK,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = fetchByG_C_C_Last(
			groupId, classNameId, classPK, orderByComparator);

		if (mdrRuleGroupInstance != null) {
			return mdrRuleGroupInstance;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchRuleGroupInstanceException(sb.toString());
	}

	/**
	 * Returns the last mdr rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByG_C_C_Last(
		long groupId, long classNameId, long classPK,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		int count = countByG_C_C(groupId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<MDRRuleGroupInstance> list = findByG_C_C(
			groupId, classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the mdr rule group instances before and after the current mdr rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param ruleGroupInstanceId the primary key of the current mdr rule group instance
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	@Override
	public MDRRuleGroupInstance[] findByG_C_C_PrevAndNext(
			long ruleGroupInstanceId, long groupId, long classNameId,
			long classPK,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = findByPrimaryKey(
			ruleGroupInstanceId);

		Session session = null;

		try {
			session = openSession();

			MDRRuleGroupInstance[] array = new MDRRuleGroupInstanceImpl[3];

			array[0] = getByG_C_C_PrevAndNext(
				session, mdrRuleGroupInstance, groupId, classNameId, classPK,
				orderByComparator, true);

			array[1] = mdrRuleGroupInstance;

			array[2] = getByG_C_C_PrevAndNext(
				session, mdrRuleGroupInstance, groupId, classNameId, classPK,
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

	protected MDRRuleGroupInstance getByG_C_C_PrevAndNext(
		Session session, MDRRuleGroupInstance mdrRuleGroupInstance,
		long groupId, long classNameId, long classPK,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

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
			sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						mdrRuleGroupInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MDRRuleGroupInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the mdr rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching mdr rule group instances that the user has permission to view
	 */
	@Override
	public List<MDRRuleGroupInstance> filterFindByG_C_C(
		long groupId, long classNameId, long classPK) {

		return filterFindByG_C_C(
			groupId, classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the mdr rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of matching mdr rule group instances that the user has permission to view
	 */
	@Override
	public List<MDRRuleGroupInstance> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end) {

		return filterFindByG_C_C(
			groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the mdr rule group instances that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule group instances that the user has permission to view
	 */
	@Override
	public List<MDRRuleGroupInstance> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C(
				groupId, classNameId, classPK, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_MDRRULEGROUPINSTANCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_MDRRULEGROUPINSTANCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MDRRuleGroupInstance.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, MDRRuleGroupInstanceImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, MDRRuleGroupInstanceImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			queryPos.add(classPK);

			return (List<MDRRuleGroupInstance>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the mdr rule group instances before and after the current mdr rule group instance in the ordered set of mdr rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param ruleGroupInstanceId the primary key of the current mdr rule group instance
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	@Override
	public MDRRuleGroupInstance[] filterFindByG_C_C_PrevAndNext(
			long ruleGroupInstanceId, long groupId, long classNameId,
			long classPK,
			OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_PrevAndNext(
				ruleGroupInstanceId, groupId, classNameId, classPK,
				orderByComparator);
		}

		MDRRuleGroupInstance mdrRuleGroupInstance = findByPrimaryKey(
			ruleGroupInstanceId);

		Session session = null;

		try {
			session = openSession();

			MDRRuleGroupInstance[] array = new MDRRuleGroupInstanceImpl[3];

			array[0] = filterGetByG_C_C_PrevAndNext(
				session, mdrRuleGroupInstance, groupId, classNameId, classPK,
				orderByComparator, true);

			array[1] = mdrRuleGroupInstance;

			array[2] = filterGetByG_C_C_PrevAndNext(
				session, mdrRuleGroupInstance, groupId, classNameId, classPK,
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

	protected MDRRuleGroupInstance filterGetByG_C_C_PrevAndNext(
		Session session, MDRRuleGroupInstance mdrRuleGroupInstance,
		long groupId, long classNameId, long classPK,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_MDRRULEGROUPINSTANCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_MDRRULEGROUPINSTANCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MDRRuleGroupInstanceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MDRRuleGroupInstance.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, MDRRuleGroupInstanceImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, MDRRuleGroupInstanceImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						mdrRuleGroupInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MDRRuleGroupInstance> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the mdr rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_C_C(long groupId, long classNameId, long classPK) {
		for (MDRRuleGroupInstance mdrRuleGroupInstance :
				findByG_C_C(
					groupId, classNameId, classPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(mdrRuleGroupInstance);
		}
	}

	/**
	 * Returns the number of mdr rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching mdr rule group instances
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByG_C_C;

		Object[] finderArgs = new Object[] {groupId, classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_MDRRULEGROUPINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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
	 * Returns the number of mdr rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching mdr rule group instances that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C(
		long groupId, long classNameId, long classPK) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C(groupId, classNameId, classPK);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_MDRRULEGROUPINSTANCE_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MDRRuleGroupInstance.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			queryPos.add(classPK);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 =
		"mdrRuleGroupInstance.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 =
		"mdrRuleGroupInstance.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 =
		"mdrRuleGroupInstance.classPK = ?";

	private FinderPath _finderPathFetchByC_C_R;
	private FinderPath _finderPathCountByC_C_R;

	/**
	 * Returns the mdr rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; or throws a <code>NoSuchRuleGroupInstanceException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ruleGroupId the rule group ID
	 * @return the matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance findByC_C_R(
			long classNameId, long classPK, long ruleGroupId)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = fetchByC_C_R(
			classNameId, classPK, ruleGroupId);

		if (mdrRuleGroupInstance == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", ruleGroupId=");
			sb.append(ruleGroupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchRuleGroupInstanceException(sb.toString());
		}

		return mdrRuleGroupInstance;
	}

	/**
	 * Returns the mdr rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ruleGroupId the rule group ID
	 * @return the matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByC_C_R(
		long classNameId, long classPK, long ruleGroupId) {

		return fetchByC_C_R(classNameId, classPK, ruleGroupId, true);
	}

	/**
	 * Returns the mdr rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ruleGroupId the rule group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByC_C_R(
		long classNameId, long classPK, long ruleGroupId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {classNameId, classPK, ruleGroupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_C_R, finderArgs);
		}

		if (result instanceof MDRRuleGroupInstance) {
			MDRRuleGroupInstance mdrRuleGroupInstance =
				(MDRRuleGroupInstance)result;

			if ((classNameId != mdrRuleGroupInstance.getClassNameId()) ||
				(classPK != mdrRuleGroupInstance.getClassPK()) ||
				(ruleGroupId != mdrRuleGroupInstance.getRuleGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_R_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_R_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_R_RULEGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(ruleGroupId);

				List<MDRRuleGroupInstance> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_R, finderArgs, list);
					}
				}
				else {
					MDRRuleGroupInstance mdrRuleGroupInstance = list.get(0);

					result = mdrRuleGroupInstance;

					cacheResult(mdrRuleGroupInstance);
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
			return (MDRRuleGroupInstance)result;
		}
	}

	/**
	 * Removes the mdr rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ruleGroupId the rule group ID
	 * @return the mdr rule group instance that was removed
	 */
	@Override
	public MDRRuleGroupInstance removeByC_C_R(
			long classNameId, long classPK, long ruleGroupId)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = findByC_C_R(
			classNameId, classPK, ruleGroupId);

		return remove(mdrRuleGroupInstance);
	}

	/**
	 * Returns the number of mdr rule group instances where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ruleGroupId the rule group ID
	 * @return the number of matching mdr rule group instances
	 */
	@Override
	public int countByC_C_R(long classNameId, long classPK, long ruleGroupId) {
		FinderPath finderPath = _finderPathCountByC_C_R;

		Object[] finderArgs = new Object[] {classNameId, classPK, ruleGroupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_MDRRULEGROUPINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_R_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_R_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_R_RULEGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(ruleGroupId);

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

	private static final String _FINDER_COLUMN_C_C_R_CLASSNAMEID_2 =
		"mdrRuleGroupInstance.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_R_CLASSPK_2 =
		"mdrRuleGroupInstance.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_R_RULEGROUPID_2 =
		"mdrRuleGroupInstance.ruleGroupId = ?";

	public MDRRuleGroupInstancePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(MDRRuleGroupInstance.class);

		setModelImplClass(MDRRuleGroupInstanceImpl.class);
		setModelPKClass(long.class);

		setTable(MDRRuleGroupInstanceTable.INSTANCE);
	}

	/**
	 * Caches the mdr rule group instance in the entity cache if it is enabled.
	 *
	 * @param mdrRuleGroupInstance the mdr rule group instance
	 */
	@Override
	public void cacheResult(MDRRuleGroupInstance mdrRuleGroupInstance) {
		entityCache.putResult(
			MDRRuleGroupInstanceImpl.class,
			mdrRuleGroupInstance.getPrimaryKey(), mdrRuleGroupInstance);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				mdrRuleGroupInstance.getUuid(),
				mdrRuleGroupInstance.getGroupId()
			},
			mdrRuleGroupInstance);

		finderCache.putResult(
			_finderPathFetchByC_C_R,
			new Object[] {
				mdrRuleGroupInstance.getClassNameId(),
				mdrRuleGroupInstance.getClassPK(),
				mdrRuleGroupInstance.getRuleGroupId()
			},
			mdrRuleGroupInstance);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the mdr rule group instances in the entity cache if it is enabled.
	 *
	 * @param mdrRuleGroupInstances the mdr rule group instances
	 */
	@Override
	public void cacheResult(List<MDRRuleGroupInstance> mdrRuleGroupInstances) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (mdrRuleGroupInstances.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (MDRRuleGroupInstance mdrRuleGroupInstance :
				mdrRuleGroupInstances) {

			if (entityCache.getResult(
					MDRRuleGroupInstanceImpl.class,
					mdrRuleGroupInstance.getPrimaryKey()) == null) {

				cacheResult(mdrRuleGroupInstance);
			}
		}
	}

	/**
	 * Clears the cache for all mdr rule group instances.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MDRRuleGroupInstanceImpl.class);

		finderCache.clearCache(MDRRuleGroupInstanceImpl.class);
	}

	/**
	 * Clears the cache for the mdr rule group instance.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MDRRuleGroupInstance mdrRuleGroupInstance) {
		entityCache.removeResult(
			MDRRuleGroupInstanceImpl.class, mdrRuleGroupInstance);
	}

	@Override
	public void clearCache(List<MDRRuleGroupInstance> mdrRuleGroupInstances) {
		for (MDRRuleGroupInstance mdrRuleGroupInstance :
				mdrRuleGroupInstances) {

			entityCache.removeResult(
				MDRRuleGroupInstanceImpl.class, mdrRuleGroupInstance);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(MDRRuleGroupInstanceImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				MDRRuleGroupInstanceImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		MDRRuleGroupInstanceModelImpl mdrRuleGroupInstanceModelImpl) {

		Object[] args = new Object[] {
			mdrRuleGroupInstanceModelImpl.getUuid(),
			mdrRuleGroupInstanceModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, mdrRuleGroupInstanceModelImpl);

		args = new Object[] {
			mdrRuleGroupInstanceModelImpl.getClassNameId(),
			mdrRuleGroupInstanceModelImpl.getClassPK(),
			mdrRuleGroupInstanceModelImpl.getRuleGroupId()
		};

		finderCache.putResult(_finderPathCountByC_C_R, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C_R, args, mdrRuleGroupInstanceModelImpl);
	}

	/**
	 * Creates a new mdr rule group instance with the primary key. Does not add the mdr rule group instance to the database.
	 *
	 * @param ruleGroupInstanceId the primary key for the new mdr rule group instance
	 * @return the new mdr rule group instance
	 */
	@Override
	public MDRRuleGroupInstance create(long ruleGroupInstanceId) {
		MDRRuleGroupInstance mdrRuleGroupInstance =
			new MDRRuleGroupInstanceImpl();

		mdrRuleGroupInstance.setNew(true);
		mdrRuleGroupInstance.setPrimaryKey(ruleGroupInstanceId);

		String uuid = PortalUUIDUtil.generate();

		mdrRuleGroupInstance.setUuid(uuid);

		mdrRuleGroupInstance.setCompanyId(CompanyThreadLocal.getCompanyId());

		return mdrRuleGroupInstance;
	}

	/**
	 * Removes the mdr rule group instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ruleGroupInstanceId the primary key of the mdr rule group instance
	 * @return the mdr rule group instance that was removed
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	@Override
	public MDRRuleGroupInstance remove(long ruleGroupInstanceId)
		throws NoSuchRuleGroupInstanceException {

		return remove((Serializable)ruleGroupInstanceId);
	}

	/**
	 * Removes the mdr rule group instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the mdr rule group instance
	 * @return the mdr rule group instance that was removed
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	@Override
	public MDRRuleGroupInstance remove(Serializable primaryKey)
		throws NoSuchRuleGroupInstanceException {

		Session session = null;

		try {
			session = openSession();

			MDRRuleGroupInstance mdrRuleGroupInstance =
				(MDRRuleGroupInstance)session.get(
					MDRRuleGroupInstanceImpl.class, primaryKey);

			if (mdrRuleGroupInstance == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRuleGroupInstanceException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(mdrRuleGroupInstance);
		}
		catch (NoSuchRuleGroupInstanceException noSuchEntityException) {
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
	protected MDRRuleGroupInstance removeImpl(
		MDRRuleGroupInstance mdrRuleGroupInstance) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mdrRuleGroupInstance)) {
				mdrRuleGroupInstance = (MDRRuleGroupInstance)session.get(
					MDRRuleGroupInstanceImpl.class,
					mdrRuleGroupInstance.getPrimaryKeyObj());
			}

			if (mdrRuleGroupInstance != null) {
				session.delete(mdrRuleGroupInstance);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mdrRuleGroupInstance != null) {
			clearCache(mdrRuleGroupInstance);
		}

		return mdrRuleGroupInstance;
	}

	@Override
	public MDRRuleGroupInstance updateImpl(
		MDRRuleGroupInstance mdrRuleGroupInstance) {

		boolean isNew = mdrRuleGroupInstance.isNew();

		if (!(mdrRuleGroupInstance instanceof MDRRuleGroupInstanceModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(mdrRuleGroupInstance.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					mdrRuleGroupInstance);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in mdrRuleGroupInstance proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MDRRuleGroupInstance implementation " +
					mdrRuleGroupInstance.getClass());
		}

		MDRRuleGroupInstanceModelImpl mdrRuleGroupInstanceModelImpl =
			(MDRRuleGroupInstanceModelImpl)mdrRuleGroupInstance;

		if (Validator.isNull(mdrRuleGroupInstance.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mdrRuleGroupInstance.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (mdrRuleGroupInstance.getCreateDate() == null)) {
			if (serviceContext == null) {
				mdrRuleGroupInstance.setCreateDate(date);
			}
			else {
				mdrRuleGroupInstance.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!mdrRuleGroupInstanceModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				mdrRuleGroupInstance.setModifiedDate(date);
			}
			else {
				mdrRuleGroupInstance.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(mdrRuleGroupInstance);
			}
			else {
				mdrRuleGroupInstance = (MDRRuleGroupInstance)session.merge(
					mdrRuleGroupInstance);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			MDRRuleGroupInstanceImpl.class, mdrRuleGroupInstanceModelImpl,
			false, true);

		cacheUniqueFindersCache(mdrRuleGroupInstanceModelImpl);

		if (isNew) {
			mdrRuleGroupInstance.setNew(false);
		}

		mdrRuleGroupInstance.resetOriginalValues();

		return mdrRuleGroupInstance;
	}

	/**
	 * Returns the mdr rule group instance with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the mdr rule group instance
	 * @return the mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	@Override
	public MDRRuleGroupInstance findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRuleGroupInstanceException {

		MDRRuleGroupInstance mdrRuleGroupInstance = fetchByPrimaryKey(
			primaryKey);

		if (mdrRuleGroupInstance == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRuleGroupInstanceException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return mdrRuleGroupInstance;
	}

	/**
	 * Returns the mdr rule group instance with the primary key or throws a <code>NoSuchRuleGroupInstanceException</code> if it could not be found.
	 *
	 * @param ruleGroupInstanceId the primary key of the mdr rule group instance
	 * @return the mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	@Override
	public MDRRuleGroupInstance findByPrimaryKey(long ruleGroupInstanceId)
		throws NoSuchRuleGroupInstanceException {

		return findByPrimaryKey((Serializable)ruleGroupInstanceId);
	}

	/**
	 * Returns the mdr rule group instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ruleGroupInstanceId the primary key of the mdr rule group instance
	 * @return the mdr rule group instance, or <code>null</code> if a mdr rule group instance with the primary key could not be found
	 */
	@Override
	public MDRRuleGroupInstance fetchByPrimaryKey(long ruleGroupInstanceId) {
		return fetchByPrimaryKey((Serializable)ruleGroupInstanceId);
	}

	/**
	 * Returns all the mdr rule group instances.
	 *
	 * @return the mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mdr rule group instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the mdr rule group instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findAll(
		int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mdr rule group instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mdr rule group instances
	 */
	@Override
	public List<MDRRuleGroupInstance> findAll(
		int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
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

		List<MDRRuleGroupInstance> list = null;

		if (useFinderCache) {
			list = (List<MDRRuleGroupInstance>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MDRRULEGROUPINSTANCE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MDRRULEGROUPINSTANCE;

				sql = sql.concat(MDRRuleGroupInstanceModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<MDRRuleGroupInstance>)QueryUtil.list(
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
	 * Removes all the mdr rule group instances from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MDRRuleGroupInstance mdrRuleGroupInstance : findAll()) {
			remove(mdrRuleGroupInstance);
		}
	}

	/**
	 * Returns the number of mdr rule group instances.
	 *
	 * @return the number of mdr rule group instances
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_MDRRULEGROUPINSTANCE);

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
		return "ruleGroupInstanceId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MDRRULEGROUPINSTANCE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return MDRRuleGroupInstanceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the mdr rule group instance persistence.
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

		_finderPathWithPaginationFindByRuleGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRuleGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"ruleGroupId"}, true);

		_finderPathWithoutPaginationFindByRuleGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRuleGroupId",
			new String[] {Long.class.getName()}, new String[] {"ruleGroupId"},
			true);

		_finderPathCountByRuleGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRuleGroupId",
			new String[] {Long.class.getName()}, new String[] {"ruleGroupId"},
			false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);

		_finderPathWithPaginationFindByG_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByG_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, true);

		_finderPathCountByG_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, false);

		_finderPathFetchByC_C_R = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_R",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "ruleGroupId"}, true);

		_finderPathCountByC_C_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_R",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "ruleGroupId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(MDRRuleGroupInstanceImpl.class.getName());
	}

	@Override
	@Reference(
		target = MDRPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = MDRPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = MDRPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_MDRRULEGROUPINSTANCE =
		"SELECT mdrRuleGroupInstance FROM MDRRuleGroupInstance mdrRuleGroupInstance";

	private static final String _SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE =
		"SELECT mdrRuleGroupInstance FROM MDRRuleGroupInstance mdrRuleGroupInstance WHERE ";

	private static final String _SQL_COUNT_MDRRULEGROUPINSTANCE =
		"SELECT COUNT(mdrRuleGroupInstance) FROM MDRRuleGroupInstance mdrRuleGroupInstance";

	private static final String _SQL_COUNT_MDRRULEGROUPINSTANCE_WHERE =
		"SELECT COUNT(mdrRuleGroupInstance) FROM MDRRuleGroupInstance mdrRuleGroupInstance WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"mdrRuleGroupInstance.ruleGroupInstanceId";

	private static final String _FILTER_SQL_SELECT_MDRRULEGROUPINSTANCE_WHERE =
		"SELECT DISTINCT {mdrRuleGroupInstance.*} FROM MDRRuleGroupInstance mdrRuleGroupInstance WHERE ";

	private static final String
		_FILTER_SQL_SELECT_MDRRULEGROUPINSTANCE_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {MDRRuleGroupInstance.*} FROM (SELECT DISTINCT mdrRuleGroupInstance.ruleGroupInstanceId FROM MDRRuleGroupInstance mdrRuleGroupInstance WHERE ";

	private static final String
		_FILTER_SQL_SELECT_MDRRULEGROUPINSTANCE_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN MDRRuleGroupInstance ON TEMP_TABLE.ruleGroupInstanceId = MDRRuleGroupInstance.ruleGroupInstanceId";

	private static final String _FILTER_SQL_COUNT_MDRRULEGROUPINSTANCE_WHERE =
		"SELECT COUNT(DISTINCT mdrRuleGroupInstance.ruleGroupInstanceId) AS COUNT_VALUE FROM MDRRuleGroupInstance mdrRuleGroupInstance WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "mdrRuleGroupInstance";

	private static final String _FILTER_ENTITY_TABLE = "MDRRuleGroupInstance";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"mdrRuleGroupInstance.";

	private static final String _ORDER_BY_ENTITY_TABLE =
		"MDRRuleGroupInstance.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No MDRRuleGroupInstance exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No MDRRuleGroupInstance exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MDRRuleGroupInstancePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private MDRRuleGroupInstanceModelArgumentsResolver
		_mdrRuleGroupInstanceModelArgumentsResolver;

}