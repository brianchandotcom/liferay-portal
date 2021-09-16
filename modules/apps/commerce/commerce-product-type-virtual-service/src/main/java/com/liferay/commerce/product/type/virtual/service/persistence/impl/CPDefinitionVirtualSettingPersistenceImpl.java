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

package com.liferay.commerce.product.type.virtual.service.persistence.impl;

import com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSettingTable;
import com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingImpl;
import com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingModelImpl;
import com.liferay.commerce.product.type.virtual.service.persistence.CPDefinitionVirtualSettingPersistence;
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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the cp definition virtual setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
public class CPDefinitionVirtualSettingPersistenceImpl
	extends BasePersistenceImpl<CPDefinitionVirtualSetting>
	implements CPDefinitionVirtualSettingPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPDefinitionVirtualSettingUtil</code> to access the cp definition virtual setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPDefinitionVirtualSettingImpl.class.getName();

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
	 * Returns all the cp definition virtual settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definition virtual settings
	 */
	@Override
	public List<CPDefinitionVirtualSetting> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition virtual settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionVirtualSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition virtual settings
	 * @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	 * @return the range of matching cp definition virtual settings
	 */
	@Override
	public List<CPDefinitionVirtualSetting> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition virtual settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionVirtualSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition virtual settings
	 * @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition virtual settings
	 */
	@Override
	public List<CPDefinitionVirtualSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition virtual settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionVirtualSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition virtual settings
	 * @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition virtual settings
	 */
	@Override
	public List<CPDefinitionVirtualSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator,
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

		List<CPDefinitionVirtualSetting> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionVirtualSetting>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionVirtualSetting cpDefinitionVirtualSetting :
						list) {

					if (!uuid.equals(cpDefinitionVirtualSetting.getUuid())) {
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

			sb.append(_SQL_SELECT_CPDEFINITIONVIRTUALSETTING_WHERE);

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
				sb.append(CPDefinitionVirtualSettingModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPDefinitionVirtualSetting>)QueryUtil.list(
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
	 * Returns the first cp definition virtual setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition virtual setting
	 * @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting findByUuid_First(
			String uuid,
			OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws NoSuchCPDefinitionVirtualSettingException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			fetchByUuid_First(uuid, orderByComparator);

		if (cpDefinitionVirtualSetting != null) {
			return cpDefinitionVirtualSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPDefinitionVirtualSettingException(sb.toString());
	}

	/**
	 * Returns the first cp definition virtual setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting fetchByUuid_First(
		String uuid,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator) {

		List<CPDefinitionVirtualSetting> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition virtual setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition virtual setting
	 * @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting findByUuid_Last(
			String uuid,
			OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws NoSuchCPDefinitionVirtualSettingException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			fetchByUuid_Last(uuid, orderByComparator);

		if (cpDefinitionVirtualSetting != null) {
			return cpDefinitionVirtualSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPDefinitionVirtualSettingException(sb.toString());
	}

	/**
	 * Returns the last cp definition virtual setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting fetchByUuid_Last(
		String uuid,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionVirtualSetting> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition virtual settings before and after the current cp definition virtual setting in the ordered set where uuid = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingId the primary key of the current cp definition virtual setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition virtual setting
	 * @throws NoSuchCPDefinitionVirtualSettingException if a cp definition virtual setting with the primary key could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting[] findByUuid_PrevAndNext(
			long CPDefinitionVirtualSettingId, String uuid,
			OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws NoSuchCPDefinitionVirtualSettingException {

		uuid = Objects.toString(uuid, "");

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			findByPrimaryKey(CPDefinitionVirtualSettingId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionVirtualSetting[] array =
				new CPDefinitionVirtualSettingImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, cpDefinitionVirtualSetting, uuid, orderByComparator,
				true);

			array[1] = cpDefinitionVirtualSetting;

			array[2] = getByUuid_PrevAndNext(
				session, cpDefinitionVirtualSetting, uuid, orderByComparator,
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

	protected CPDefinitionVirtualSetting getByUuid_PrevAndNext(
		Session session, CPDefinitionVirtualSetting cpDefinitionVirtualSetting,
		String uuid,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator,
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

		sb.append(_SQL_SELECT_CPDEFINITIONVIRTUALSETTING_WHERE);

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
			sb.append(CPDefinitionVirtualSettingModelImpl.ORDER_BY_JPQL);
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
						cpDefinitionVirtualSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionVirtualSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition virtual settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPDefinitionVirtualSetting cpDefinitionVirtualSetting :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpDefinitionVirtualSetting);
		}
	}

	/**
	 * Returns the number of cp definition virtual settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definition virtual settings
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITIONVIRTUALSETTING_WHERE);

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
		"cpDefinitionVirtualSetting.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(cpDefinitionVirtualSetting.uuid IS NULL OR cpDefinitionVirtualSetting.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the cp definition virtual setting where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPDefinitionVirtualSettingException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition virtual setting
	 * @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionVirtualSettingException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting = fetchByUUID_G(
			uuid, groupId);

		if (cpDefinitionVirtualSetting == null) {
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

			throw new NoSuchCPDefinitionVirtualSettingException(sb.toString());
		}

		return cpDefinitionVirtualSetting;
	}

	/**
	 * Returns the cp definition virtual setting where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp definition virtual setting where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting fetchByUUID_G(
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

		if (result instanceof CPDefinitionVirtualSetting) {
			CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
				(CPDefinitionVirtualSetting)result;

			if (!Objects.equals(uuid, cpDefinitionVirtualSetting.getUuid()) ||
				(groupId != cpDefinitionVirtualSetting.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPDEFINITIONVIRTUALSETTING_WHERE);

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

				List<CPDefinitionVirtualSetting> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
						list.get(0);

					result = cpDefinitionVirtualSetting;

					cacheResult(cpDefinitionVirtualSetting);
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
			return (CPDefinitionVirtualSetting)result;
		}
	}

	/**
	 * Removes the cp definition virtual setting where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp definition virtual setting that was removed
	 */
	@Override
	public CPDefinitionVirtualSetting removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionVirtualSettingException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting = findByUUID_G(
			uuid, groupId);

		return remove(cpDefinitionVirtualSetting);
	}

	/**
	 * Returns the number of cp definition virtual settings where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp definition virtual settings
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITIONVIRTUALSETTING_WHERE);

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
		"cpDefinitionVirtualSetting.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(cpDefinitionVirtualSetting.uuid IS NULL OR cpDefinitionVirtualSetting.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"cpDefinitionVirtualSetting.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the cp definition virtual settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definition virtual settings
	 */
	@Override
	public List<CPDefinitionVirtualSetting> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition virtual settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionVirtualSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition virtual settings
	 * @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	 * @return the range of matching cp definition virtual settings
	 */
	@Override
	public List<CPDefinitionVirtualSetting> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition virtual settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionVirtualSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition virtual settings
	 * @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition virtual settings
	 */
	@Override
	public List<CPDefinitionVirtualSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition virtual settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionVirtualSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition virtual settings
	 * @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition virtual settings
	 */
	@Override
	public List<CPDefinitionVirtualSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator,
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

		List<CPDefinitionVirtualSetting> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionVirtualSetting>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionVirtualSetting cpDefinitionVirtualSetting :
						list) {

					if (!uuid.equals(cpDefinitionVirtualSetting.getUuid()) ||
						(companyId !=
							cpDefinitionVirtualSetting.getCompanyId())) {

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

			sb.append(_SQL_SELECT_CPDEFINITIONVIRTUALSETTING_WHERE);

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
				sb.append(CPDefinitionVirtualSettingModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPDefinitionVirtualSetting>)QueryUtil.list(
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
	 * Returns the first cp definition virtual setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition virtual setting
	 * @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws NoSuchCPDefinitionVirtualSettingException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (cpDefinitionVirtualSetting != null) {
			return cpDefinitionVirtualSetting;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPDefinitionVirtualSettingException(sb.toString());
	}

	/**
	 * Returns the first cp definition virtual setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator) {

		List<CPDefinitionVirtualSetting> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition virtual setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition virtual setting
	 * @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws NoSuchCPDefinitionVirtualSettingException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (cpDefinitionVirtualSetting != null) {
			return cpDefinitionVirtualSetting;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPDefinitionVirtualSettingException(sb.toString());
	}

	/**
	 * Returns the last cp definition virtual setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionVirtualSetting> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition virtual settings before and after the current cp definition virtual setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingId the primary key of the current cp definition virtual setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition virtual setting
	 * @throws NoSuchCPDefinitionVirtualSettingException if a cp definition virtual setting with the primary key could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting[] findByUuid_C_PrevAndNext(
			long CPDefinitionVirtualSettingId, String uuid, long companyId,
			OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws NoSuchCPDefinitionVirtualSettingException {

		uuid = Objects.toString(uuid, "");

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			findByPrimaryKey(CPDefinitionVirtualSettingId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionVirtualSetting[] array =
				new CPDefinitionVirtualSettingImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, cpDefinitionVirtualSetting, uuid, companyId,
				orderByComparator, true);

			array[1] = cpDefinitionVirtualSetting;

			array[2] = getByUuid_C_PrevAndNext(
				session, cpDefinitionVirtualSetting, uuid, companyId,
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

	protected CPDefinitionVirtualSetting getByUuid_C_PrevAndNext(
		Session session, CPDefinitionVirtualSetting cpDefinitionVirtualSetting,
		String uuid, long companyId,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator,
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

		sb.append(_SQL_SELECT_CPDEFINITIONVIRTUALSETTING_WHERE);

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
			sb.append(CPDefinitionVirtualSettingModelImpl.ORDER_BY_JPQL);
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
						cpDefinitionVirtualSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionVirtualSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition virtual settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPDefinitionVirtualSetting cpDefinitionVirtualSetting :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpDefinitionVirtualSetting);
		}
	}

	/**
	 * Returns the number of cp definition virtual settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definition virtual settings
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITIONVIRTUALSETTING_WHERE);

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
		"cpDefinitionVirtualSetting.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(cpDefinitionVirtualSetting.uuid IS NULL OR cpDefinitionVirtualSetting.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"cpDefinitionVirtualSetting.companyId = ?";

	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the cp definition virtual setting where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchCPDefinitionVirtualSettingException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp definition virtual setting
	 * @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting findByC_C(long classNameId, long classPK)
		throws NoSuchCPDefinitionVirtualSettingException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting = fetchByC_C(
			classNameId, classPK);

		if (cpDefinitionVirtualSetting == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPDefinitionVirtualSettingException(sb.toString());
		}

		return cpDefinitionVirtualSetting;
	}

	/**
	 * Returns the cp definition virtual setting where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting fetchByC_C(
		long classNameId, long classPK) {

		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the cp definition virtual setting where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {classNameId, classPK};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_C, finderArgs);
		}

		if (result instanceof CPDefinitionVirtualSetting) {
			CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
				(CPDefinitionVirtualSetting)result;

			if ((classNameId != cpDefinitionVirtualSetting.getClassNameId()) ||
				(classPK != cpDefinitionVirtualSetting.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPDEFINITIONVIRTUALSETTING_WHERE);

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

				List<CPDefinitionVirtualSetting> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
						list.get(0);

					result = cpDefinitionVirtualSetting;

					cacheResult(cpDefinitionVirtualSetting);
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
			return (CPDefinitionVirtualSetting)result;
		}
	}

	/**
	 * Removes the cp definition virtual setting where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the cp definition virtual setting that was removed
	 */
	@Override
	public CPDefinitionVirtualSetting removeByC_C(
			long classNameId, long classPK)
		throws NoSuchCPDefinitionVirtualSettingException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting = findByC_C(
			classNameId, classPK);

		return remove(cpDefinitionVirtualSetting);
	}

	/**
	 * Returns the number of cp definition virtual settings where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching cp definition virtual settings
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITIONVIRTUALSETTING_WHERE);

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
		"cpDefinitionVirtualSetting.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"cpDefinitionVirtualSetting.classPK = ?";

	public CPDefinitionVirtualSettingPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"termsOfUseJournalArticleResourcePrimKey",
			"termsOfUseArticleResourcePK");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPDefinitionVirtualSetting.class);

		setModelImplClass(CPDefinitionVirtualSettingImpl.class);
		setModelPKClass(long.class);

		setTable(CPDefinitionVirtualSettingTable.INSTANCE);
	}

	/**
	 * Caches the cp definition virtual setting in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionVirtualSetting the cp definition virtual setting
	 */
	@Override
	public void cacheResult(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {

		entityCache.putResult(
			CPDefinitionVirtualSettingImpl.class,
			cpDefinitionVirtualSetting.getPrimaryKey(),
			cpDefinitionVirtualSetting);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				cpDefinitionVirtualSetting.getUuid(),
				cpDefinitionVirtualSetting.getGroupId()
			},
			cpDefinitionVirtualSetting);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				cpDefinitionVirtualSetting.getClassNameId(),
				cpDefinitionVirtualSetting.getClassPK()
			},
			cpDefinitionVirtualSetting);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cp definition virtual settings in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionVirtualSettings the cp definition virtual settings
	 */
	@Override
	public void cacheResult(
		List<CPDefinitionVirtualSetting> cpDefinitionVirtualSettings) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpDefinitionVirtualSettings.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPDefinitionVirtualSetting cpDefinitionVirtualSetting :
				cpDefinitionVirtualSettings) {

			if (entityCache.getResult(
					CPDefinitionVirtualSettingImpl.class,
					cpDefinitionVirtualSetting.getPrimaryKey()) == null) {

				cacheResult(cpDefinitionVirtualSetting);
			}
		}
	}

	/**
	 * Clears the cache for all cp definition virtual settings.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionVirtualSettingImpl.class);

		finderCache.clearCache(CPDefinitionVirtualSettingImpl.class);
	}

	/**
	 * Clears the cache for the cp definition virtual setting.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {

		entityCache.removeResult(
			CPDefinitionVirtualSettingImpl.class, cpDefinitionVirtualSetting);
	}

	@Override
	public void clearCache(
		List<CPDefinitionVirtualSetting> cpDefinitionVirtualSettings) {

		for (CPDefinitionVirtualSetting cpDefinitionVirtualSetting :
				cpDefinitionVirtualSettings) {

			entityCache.removeResult(
				CPDefinitionVirtualSettingImpl.class,
				cpDefinitionVirtualSetting);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPDefinitionVirtualSettingImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPDefinitionVirtualSettingImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionVirtualSettingModelImpl
			cpDefinitionVirtualSettingModelImpl) {

		Object[] args = new Object[] {
			cpDefinitionVirtualSettingModelImpl.getUuid(),
			cpDefinitionVirtualSettingModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args,
			cpDefinitionVirtualSettingModelImpl);

		args = new Object[] {
			cpDefinitionVirtualSettingModelImpl.getClassNameId(),
			cpDefinitionVirtualSettingModelImpl.getClassPK()
		};

		finderCache.putResult(_finderPathCountByC_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C, args, cpDefinitionVirtualSettingModelImpl);
	}

	/**
	 * Creates a new cp definition virtual setting with the primary key. Does not add the cp definition virtual setting to the database.
	 *
	 * @param CPDefinitionVirtualSettingId the primary key for the new cp definition virtual setting
	 * @return the new cp definition virtual setting
	 */
	@Override
	public CPDefinitionVirtualSetting create(
		long CPDefinitionVirtualSettingId) {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			new CPDefinitionVirtualSettingImpl();

		cpDefinitionVirtualSetting.setNew(true);
		cpDefinitionVirtualSetting.setPrimaryKey(CPDefinitionVirtualSettingId);

		String uuid = PortalUUIDUtil.generate();

		cpDefinitionVirtualSetting.setUuid(uuid);

		cpDefinitionVirtualSetting.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return cpDefinitionVirtualSetting;
	}

	/**
	 * Removes the cp definition virtual setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionVirtualSettingId the primary key of the cp definition virtual setting
	 * @return the cp definition virtual setting that was removed
	 * @throws NoSuchCPDefinitionVirtualSettingException if a cp definition virtual setting with the primary key could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting remove(long CPDefinitionVirtualSettingId)
		throws NoSuchCPDefinitionVirtualSettingException {

		return remove((Serializable)CPDefinitionVirtualSettingId);
	}

	/**
	 * Removes the cp definition virtual setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition virtual setting
	 * @return the cp definition virtual setting that was removed
	 * @throws NoSuchCPDefinitionVirtualSettingException if a cp definition virtual setting with the primary key could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting remove(Serializable primaryKey)
		throws NoSuchCPDefinitionVirtualSettingException {

		Session session = null;

		try {
			session = openSession();

			CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
				(CPDefinitionVirtualSetting)session.get(
					CPDefinitionVirtualSettingImpl.class, primaryKey);

			if (cpDefinitionVirtualSetting == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionVirtualSettingException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpDefinitionVirtualSetting);
		}
		catch (NoSuchCPDefinitionVirtualSettingException
					noSuchEntityException) {

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
	protected CPDefinitionVirtualSetting removeImpl(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionVirtualSetting)) {
				cpDefinitionVirtualSetting =
					(CPDefinitionVirtualSetting)session.get(
						CPDefinitionVirtualSettingImpl.class,
						cpDefinitionVirtualSetting.getPrimaryKeyObj());
			}

			if (cpDefinitionVirtualSetting != null) {
				session.delete(cpDefinitionVirtualSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionVirtualSetting != null) {
			clearCache(cpDefinitionVirtualSetting);
		}

		return cpDefinitionVirtualSetting;
	}

	@Override
	public CPDefinitionVirtualSetting updateImpl(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {

		boolean isNew = cpDefinitionVirtualSetting.isNew();

		if (!(cpDefinitionVirtualSetting instanceof
				CPDefinitionVirtualSettingModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpDefinitionVirtualSetting.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpDefinitionVirtualSetting);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpDefinitionVirtualSetting proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDefinitionVirtualSetting implementation " +
					cpDefinitionVirtualSetting.getClass());
		}

		CPDefinitionVirtualSettingModelImpl
			cpDefinitionVirtualSettingModelImpl =
				(CPDefinitionVirtualSettingModelImpl)cpDefinitionVirtualSetting;

		if (Validator.isNull(cpDefinitionVirtualSetting.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpDefinitionVirtualSetting.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpDefinitionVirtualSetting.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDefinitionVirtualSetting.setCreateDate(date);
			}
			else {
				cpDefinitionVirtualSetting.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpDefinitionVirtualSettingModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDefinitionVirtualSetting.setModifiedDate(date);
			}
			else {
				cpDefinitionVirtualSetting.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(cpDefinitionVirtualSetting);
			}
			else {
				cpDefinitionVirtualSetting =
					(CPDefinitionVirtualSetting)session.merge(
						cpDefinitionVirtualSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPDefinitionVirtualSettingImpl.class,
			cpDefinitionVirtualSettingModelImpl, false, true);

		cacheUniqueFindersCache(cpDefinitionVirtualSettingModelImpl);

		if (isNew) {
			cpDefinitionVirtualSetting.setNew(false);
		}

		cpDefinitionVirtualSetting.resetOriginalValues();

		return cpDefinitionVirtualSetting;
	}

	/**
	 * Returns the cp definition virtual setting with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition virtual setting
	 * @return the cp definition virtual setting
	 * @throws NoSuchCPDefinitionVirtualSettingException if a cp definition virtual setting with the primary key could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDefinitionVirtualSettingException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			fetchByPrimaryKey(primaryKey);

		if (cpDefinitionVirtualSetting == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionVirtualSettingException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpDefinitionVirtualSetting;
	}

	/**
	 * Returns the cp definition virtual setting with the primary key or throws a <code>NoSuchCPDefinitionVirtualSettingException</code> if it could not be found.
	 *
	 * @param CPDefinitionVirtualSettingId the primary key of the cp definition virtual setting
	 * @return the cp definition virtual setting
	 * @throws NoSuchCPDefinitionVirtualSettingException if a cp definition virtual setting with the primary key could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting findByPrimaryKey(
			long CPDefinitionVirtualSettingId)
		throws NoSuchCPDefinitionVirtualSettingException {

		return findByPrimaryKey((Serializable)CPDefinitionVirtualSettingId);
	}

	/**
	 * Returns the cp definition virtual setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionVirtualSettingId the primary key of the cp definition virtual setting
	 * @return the cp definition virtual setting, or <code>null</code> if a cp definition virtual setting with the primary key could not be found
	 */
	@Override
	public CPDefinitionVirtualSetting fetchByPrimaryKey(
		long CPDefinitionVirtualSettingId) {

		return fetchByPrimaryKey((Serializable)CPDefinitionVirtualSettingId);
	}

	/**
	 * Returns all the cp definition virtual settings.
	 *
	 * @return the cp definition virtual settings
	 */
	@Override
	public List<CPDefinitionVirtualSetting> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition virtual settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionVirtualSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition virtual settings
	 * @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	 * @return the range of cp definition virtual settings
	 */
	@Override
	public List<CPDefinitionVirtualSetting> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition virtual settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionVirtualSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition virtual settings
	 * @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition virtual settings
	 */
	@Override
	public List<CPDefinitionVirtualSetting> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition virtual settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionVirtualSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition virtual settings
	 * @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definition virtual settings
	 */
	@Override
	public List<CPDefinitionVirtualSetting> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator,
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

		List<CPDefinitionVirtualSetting> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionVirtualSetting>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CPDEFINITIONVIRTUALSETTING);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITIONVIRTUALSETTING;

				sql = sql.concat(
					CPDefinitionVirtualSettingModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CPDefinitionVirtualSetting>)QueryUtil.list(
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
	 * Removes all the cp definition virtual settings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionVirtualSetting cpDefinitionVirtualSetting :
				findAll()) {

			remove(cpDefinitionVirtualSetting);
		}
	}

	/**
	 * Returns the number of cp definition virtual settings.
	 *
	 * @return the number of cp definition virtual settings
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
					_SQL_COUNT_CPDEFINITIONVIRTUALSETTING);

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
		return "CPDefinitionVirtualSettingId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPDEFINITIONVIRTUALSETTING;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CPDefinitionVirtualSettingModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp definition virtual setting persistence.
	 */
	public void afterPropertiesSet() {
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

		_finderPathFetchByC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CPDefinitionVirtualSettingImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CPDEFINITIONVIRTUALSETTING =
		"SELECT cpDefinitionVirtualSetting FROM CPDefinitionVirtualSetting cpDefinitionVirtualSetting";

	private static final String _SQL_SELECT_CPDEFINITIONVIRTUALSETTING_WHERE =
		"SELECT cpDefinitionVirtualSetting FROM CPDefinitionVirtualSetting cpDefinitionVirtualSetting WHERE ";

	private static final String _SQL_COUNT_CPDEFINITIONVIRTUALSETTING =
		"SELECT COUNT(cpDefinitionVirtualSetting) FROM CPDefinitionVirtualSetting cpDefinitionVirtualSetting";

	private static final String _SQL_COUNT_CPDEFINITIONVIRTUALSETTING_WHERE =
		"SELECT COUNT(cpDefinitionVirtualSetting) FROM CPDefinitionVirtualSetting cpDefinitionVirtualSetting WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpDefinitionVirtualSetting.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPDefinitionVirtualSetting exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPDefinitionVirtualSetting exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionVirtualSettingPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "termsOfUseJournalArticleResourcePrimKey"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}