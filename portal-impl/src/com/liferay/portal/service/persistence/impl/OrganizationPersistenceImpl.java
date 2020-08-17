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

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchOrganizationException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;
import com.liferay.portal.kernel.service.persistence.OrganizationPersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.impl.OrganizationImpl;
import com.liferay.portal.model.impl.OrganizationModelImpl;

import java.io.Serializable;

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

/**
 * The persistence implementation for the organization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OrganizationPersistenceImpl
	extends BasePersistenceImpl<Organization>
	implements OrganizationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>OrganizationUtil</code> to access the organization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		OrganizationImpl.class.getName();

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
	 * Returns all the organizations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching organizations
	 */
	@Override
	public List<Organization> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations
	 */
	@Override
	public List<Organization> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Organization> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the organizations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Organization> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

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

		List<Organization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<Organization>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Organization organization : list) {
					if (!uuid.equals(organization.getUuid())) {
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

			sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
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

				list = (List<Organization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first organization in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByUuid_First(
			String uuid, OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByUuid_First(uuid, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the first organization in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByUuid_First(
		String uuid, OrderByComparator<Organization> orderByComparator) {

		List<Organization> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last organization in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByUuid_Last(
			String uuid, OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByUuid_Last(uuid, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the last organization in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByUuid_Last(
		String uuid, OrderByComparator<Organization> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<Organization> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the organizations before and after the current organization in the ordered set where uuid = &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization[] findByUuid_PrevAndNext(
			long organizationId, String uuid,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		uuid = Objects.toString(uuid, "");

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, organization, uuid, orderByComparator, true);

			array[1] = organization;

			array[2] = getByUuid_PrevAndNext(
				session, organization, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Organization getByUuid_PrevAndNext(
		Session session, Organization organization, String uuid,
		OrderByComparator<Organization> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

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
			sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(organization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Organization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the organizations that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByUuid(String uuid) {
		return filterFindByUuid(
			uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByUuid(
		String uuid, int start, int end) {

		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<Organization> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid(uuid, start, end, orderByComparator);
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OrganizationImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			return (List<Organization>)QueryUtil.list(
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
	 * Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where uuid = &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization[] filterFindByUuid_PrevAndNext(
			long organizationId, String uuid,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_PrevAndNext(
				organizationId, uuid, orderByComparator);
		}

		uuid = Objects.toString(uuid, "");

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = filterGetByUuid_PrevAndNext(
				session, organization, uuid, orderByComparator, true);

			array[1] = organization;

			array[2] = filterGetByUuid_PrevAndNext(
				session, organization, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Organization filterGetByUuid_PrevAndNext(
		Session session, Organization organization, String uuid,
		OrderByComparator<Organization> orderByComparator, boolean previous) {

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
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, OrganizationImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(organization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Organization> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the organizations where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (Organization organization :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(organization);
		}
	}

	/**
	 * Returns the number of organizations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching organizations
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ORGANIZATION_WHERE);

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
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	 * Returns the number of organizations that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching organizations that the user has permission to view
	 */
	@Override
	public int filterCountByUuid(String uuid) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_ORGANIZATION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"organization.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(organization.uuid IS NULL OR organization.uuid = '')";

	private static final String _FINDER_COLUMN_UUID_UUID_2_SQL =
		"organization.uuid_ = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3_SQL =
		"(organization.uuid_ IS NULL OR organization.uuid_ = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the organizations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching organizations
	 */
	@Override
	public List<Organization> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations
	 */
	@Override
	public List<Organization> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Organization> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the organizations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Organization> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

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

		List<Organization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<Organization>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Organization organization : list) {
					if (!uuid.equals(organization.getUuid()) ||
						(companyId != organization.getCompanyId())) {

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

			sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
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

				list = (List<Organization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first organization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the first organization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<Organization> orderByComparator) {

		List<Organization> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last organization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the last organization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<Organization> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<Organization> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the organizations before and after the current organization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization[] findByUuid_C_PrevAndNext(
			long organizationId, String uuid, long companyId,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		uuid = Objects.toString(uuid, "");

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, organization, uuid, companyId, orderByComparator,
				true);

			array[1] = organization;

			array[2] = getByUuid_C_PrevAndNext(
				session, organization, uuid, companyId, orderByComparator,
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

	protected Organization getByUuid_C_PrevAndNext(
		Session session, Organization organization, String uuid, long companyId,
		OrderByComparator<Organization> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

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
			sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(organization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Organization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the organizations that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByUuid_C(String uuid, long companyId) {
		return filterFindByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Organization> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByUuid_C(uuid, companyId, start, end, orderByComparator);
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OrganizationImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			queryPos.add(companyId);

			return (List<Organization>)QueryUtil.list(
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
	 * Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization[] filterFindByUuid_C_PrevAndNext(
			long organizationId, String uuid, long companyId,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByUuid_C_PrevAndNext(
				organizationId, uuid, companyId, orderByComparator);
		}

		uuid = Objects.toString(uuid, "");

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = filterGetByUuid_C_PrevAndNext(
				session, organization, uuid, companyId, orderByComparator,
				true);

			array[1] = organization;

			array[2] = filterGetByUuid_C_PrevAndNext(
				session, organization, uuid, companyId, orderByComparator,
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

	protected Organization filterGetByUuid_C_PrevAndNext(
		Session session, Organization organization, String uuid, long companyId,
		OrderByComparator<Organization> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, OrganizationImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(organization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Organization> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the organizations where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (Organization organization :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(organization);
		}
	}

	/**
	 * Returns the number of organizations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching organizations
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ORGANIZATION_WHERE);

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
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	 * Returns the number of organizations that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching organizations that the user has permission to view
	 */
	@Override
	public int filterCountByUuid_C(String uuid, long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByUuid_C(uuid, companyId);
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_ORGANIZATION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"organization.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(organization.uuid IS NULL OR organization.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_2_SQL =
		"organization.uuid_ = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3_SQL =
		"(organization.uuid_ IS NULL OR organization.uuid_ = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"organization.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the organizations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching organizations
	 */
	@Override
	public List<Organization> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations
	 */
	@Override
	public List<Organization> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Organization> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the organizations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Organization> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<Organization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<Organization>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Organization organization : list) {
					if (companyId != organization.getCompanyId()) {
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

			sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<Organization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first organization in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByCompanyId_First(
			long companyId, OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the first organization in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByCompanyId_First(
		long companyId, OrderByComparator<Organization> orderByComparator) {

		List<Organization> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last organization in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByCompanyId_Last(
			long companyId, OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the last organization in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByCompanyId_Last(
		long companyId, OrderByComparator<Organization> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<Organization> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the organizations before and after the current organization in the ordered set where companyId = &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization[] findByCompanyId_PrevAndNext(
			long organizationId, long companyId,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, organization, companyId, orderByComparator, true);

			array[1] = organization;

			array[2] = getByCompanyId_PrevAndNext(
				session, organization, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Organization getByCompanyId_PrevAndNext(
		Session session, Organization organization, long companyId,
		OrderByComparator<Organization> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(organization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Organization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the organizations that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Organization> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId(companyId, start, end, orderByComparator);
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
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OrganizationImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<Organization>)QueryUtil.list(
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
	 * Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization[] filterFindByCompanyId_PrevAndNext(
			long organizationId, long companyId,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId_PrevAndNext(
				organizationId, companyId, orderByComparator);
		}

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(
				session, organization, companyId, orderByComparator, true);

			array[1] = organization;

			array[2] = filterGetByCompanyId_PrevAndNext(
				session, organization, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Organization filterGetByCompanyId_PrevAndNext(
		Session session, Organization organization, long companyId,
		OrderByComparator<Organization> orderByComparator, boolean previous) {

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
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, OrganizationImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(organization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Organization> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the organizations where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (Organization organization :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(organization);
		}
	}

	/**
	 * Returns the number of organizations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching organizations
	 */
	@Override
	public int countByCompanyId(long companyId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCompanyId;

			finderArgs = new Object[] {companyId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	 * Returns the number of organizations that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching organizations that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_ORGANIZATION_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"organization.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByLocations;
	private FinderPath _finderPathWithoutPaginationFindByLocations;
	private FinderPath _finderPathCountByLocations;

	/**
	 * Returns all the organizations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching organizations
	 */
	@Override
	public List<Organization> findByLocations(long companyId) {
		return findByLocations(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations
	 */
	@Override
	public List<Organization> findByLocations(
		long companyId, int start, int end) {

		return findByLocations(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByLocations(
		long companyId, int start, int end,
		OrderByComparator<Organization> orderByComparator) {

		return findByLocations(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the organizations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByLocations(
		long companyId, int start, int end,
		OrderByComparator<Organization> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByLocations;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByLocations;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<Organization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<Organization>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Organization organization : list) {
					if (companyId != organization.getCompanyId()) {
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

			sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_LOCATIONS_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<Organization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first organization in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByLocations_First(
			long companyId, OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByLocations_First(
			companyId, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the first organization in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByLocations_First(
		long companyId, OrderByComparator<Organization> orderByComparator) {

		List<Organization> list = findByLocations(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last organization in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByLocations_Last(
			long companyId, OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByLocations_Last(
			companyId, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the last organization in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByLocations_Last(
		long companyId, OrderByComparator<Organization> orderByComparator) {

		int count = countByLocations(companyId);

		if (count == 0) {
			return null;
		}

		List<Organization> list = findByLocations(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the organizations before and after the current organization in the ordered set where companyId = &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization[] findByLocations_PrevAndNext(
			long organizationId, long companyId,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = getByLocations_PrevAndNext(
				session, organization, companyId, orderByComparator, true);

			array[1] = organization;

			array[2] = getByLocations_PrevAndNext(
				session, organization, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Organization getByLocations_PrevAndNext(
		Session session, Organization organization, long companyId,
		OrderByComparator<Organization> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

		sb.append(_FINDER_COLUMN_LOCATIONS_COMPANYID_2);

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
			sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(organization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Organization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the organizations that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByLocations(long companyId) {
		return filterFindByLocations(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByLocations(
		long companyId, int start, int end) {

		return filterFindByLocations(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByLocations(
		long companyId, int start, int end,
		OrderByComparator<Organization> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByLocations(companyId, start, end, orderByComparator);
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
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_LOCATIONS_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OrganizationImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<Organization>)QueryUtil.list(
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
	 * Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization[] filterFindByLocations_PrevAndNext(
			long organizationId, long companyId,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByLocations_PrevAndNext(
				organizationId, companyId, orderByComparator);
		}

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = filterGetByLocations_PrevAndNext(
				session, organization, companyId, orderByComparator, true);

			array[1] = organization;

			array[2] = filterGetByLocations_PrevAndNext(
				session, organization, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Organization filterGetByLocations_PrevAndNext(
		Session session, Organization organization, long companyId,
		OrderByComparator<Organization> orderByComparator, boolean previous) {

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
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_LOCATIONS_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, OrganizationImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(organization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Organization> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the organizations where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByLocations(long companyId) {
		for (Organization organization :
				findByLocations(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(organization);
		}
	}

	/**
	 * Returns the number of organizations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching organizations
	 */
	@Override
	public int countByLocations(long companyId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByLocations;

			finderArgs = new Object[] {companyId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_LOCATIONS_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	 * Returns the number of organizations that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching organizations that the user has permission to view
	 */
	@Override
	public int filterCountByLocations(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByLocations(companyId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_ORGANIZATION_WHERE);

		sb.append(_FINDER_COLUMN_LOCATIONS_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_LOCATIONS_COMPANYID_2 =
		"organization.companyId = ? AND organization.parentOrganizationId != 0";

	private FinderPath _finderPathWithPaginationFindByC_P;
	private FinderPath _finderPathWithoutPaginationFindByC_P;
	private FinderPath _finderPathCountByC_P;

	/**
	 * Returns all the organizations where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @return the matching organizations
	 */
	@Override
	public List<Organization> findByC_P(
		long companyId, long parentOrganizationId) {

		return findByC_P(
			companyId, parentOrganizationId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations
	 */
	@Override
	public List<Organization> findByC_P(
		long companyId, long parentOrganizationId, int start, int end) {

		return findByC_P(companyId, parentOrganizationId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByC_P(
		long companyId, long parentOrganizationId, int start, int end,
		OrderByComparator<Organization> orderByComparator) {

		return findByC_P(
			companyId, parentOrganizationId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the organizations where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByC_P(
		long companyId, long parentOrganizationId, int start, int end,
		OrderByComparator<Organization> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_P;
				finderArgs = new Object[] {companyId, parentOrganizationId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_P;
			finderArgs = new Object[] {
				companyId, parentOrganizationId, start, end, orderByComparator
			};
		}

		List<Organization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<Organization>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Organization organization : list) {
					if ((companyId != organization.getCompanyId()) ||
						(parentOrganizationId !=
							organization.getParentOrganizationId())) {

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

			sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_P_PARENTORGANIZATIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(parentOrganizationId);

				list = (List<Organization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByC_P_First(
			long companyId, long parentOrganizationId,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByC_P_First(
			companyId, parentOrganizationId, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", parentOrganizationId=");
		sb.append(parentOrganizationId);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the first organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByC_P_First(
		long companyId, long parentOrganizationId,
		OrderByComparator<Organization> orderByComparator) {

		List<Organization> list = findByC_P(
			companyId, parentOrganizationId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByC_P_Last(
			long companyId, long parentOrganizationId,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByC_P_Last(
			companyId, parentOrganizationId, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", parentOrganizationId=");
		sb.append(parentOrganizationId);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the last organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByC_P_Last(
		long companyId, long parentOrganizationId,
		OrderByComparator<Organization> orderByComparator) {

		int count = countByC_P(companyId, parentOrganizationId);

		if (count == 0) {
			return null;
		}

		List<Organization> list = findByC_P(
			companyId, parentOrganizationId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the organizations before and after the current organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization[] findByC_P_PrevAndNext(
			long organizationId, long companyId, long parentOrganizationId,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = getByC_P_PrevAndNext(
				session, organization, companyId, parentOrganizationId,
				orderByComparator, true);

			array[1] = organization;

			array[2] = getByC_P_PrevAndNext(
				session, organization, companyId, parentOrganizationId,
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

	protected Organization getByC_P_PrevAndNext(
		Session session, Organization organization, long companyId,
		long parentOrganizationId,
		OrderByComparator<Organization> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

		sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_P_PARENTORGANIZATIONID_2);

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
			sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(parentOrganizationId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(organization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Organization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @return the matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByC_P(
		long companyId, long parentOrganizationId) {

		return filterFindByC_P(
			companyId, parentOrganizationId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByC_P(
		long companyId, long parentOrganizationId, int start, int end) {

		return filterFindByC_P(
			companyId, parentOrganizationId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByC_P(
		long companyId, long parentOrganizationId, int start, int end,
		OrderByComparator<Organization> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_P(
				companyId, parentOrganizationId, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_P_PARENTORGANIZATIONID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OrganizationImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(parentOrganizationId);

			return (List<Organization>)QueryUtil.list(
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
	 * Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization[] filterFindByC_P_PrevAndNext(
			long organizationId, long companyId, long parentOrganizationId,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_P_PrevAndNext(
				organizationId, companyId, parentOrganizationId,
				orderByComparator);
		}

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = filterGetByC_P_PrevAndNext(
				session, organization, companyId, parentOrganizationId,
				orderByComparator, true);

			array[1] = organization;

			array[2] = filterGetByC_P_PrevAndNext(
				session, organization, companyId, parentOrganizationId,
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

	protected Organization filterGetByC_P_PrevAndNext(
		Session session, Organization organization, long companyId,
		long parentOrganizationId,
		OrderByComparator<Organization> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_P_PARENTORGANIZATIONID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, OrganizationImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		queryPos.add(parentOrganizationId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(organization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Organization> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the organizations where companyId = &#63; and parentOrganizationId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 */
	@Override
	public void removeByC_P(long companyId, long parentOrganizationId) {
		for (Organization organization :
				findByC_P(
					companyId, parentOrganizationId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(organization);
		}
	}

	/**
	 * Returns the number of organizations where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @return the number of matching organizations
	 */
	@Override
	public int countByC_P(long companyId, long parentOrganizationId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_P;

			finderArgs = new Object[] {companyId, parentOrganizationId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_P_PARENTORGANIZATIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(parentOrganizationId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	 * Returns the number of organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @return the number of matching organizations that the user has permission to view
	 */
	@Override
	public int filterCountByC_P(long companyId, long parentOrganizationId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_P(companyId, parentOrganizationId);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_ORGANIZATION_WHERE);

		sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_P_PARENTORGANIZATIONID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(parentOrganizationId);

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

	private static final String _FINDER_COLUMN_C_P_COMPANYID_2 =
		"organization.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_P_PARENTORGANIZATIONID_2 =
		"organization.parentOrganizationId = ?";

	private FinderPath _finderPathWithPaginationFindByC_T;
	private FinderPath _finderPathWithPaginationCountByC_T;

	/**
	 * Returns all the organizations where companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @return the matching organizations
	 */
	@Override
	public List<Organization> findByC_T(long companyId, String treePath) {
		return findByC_T(
			companyId, treePath, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations where companyId = &#63; and treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations
	 */
	@Override
	public List<Organization> findByC_T(
		long companyId, String treePath, int start, int end) {

		return findByC_T(companyId, treePath, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations where companyId = &#63; and treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByC_T(
		long companyId, String treePath, int start, int end,
		OrderByComparator<Organization> orderByComparator) {

		return findByC_T(
			companyId, treePath, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the organizations where companyId = &#63; and treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByC_T(
		long companyId, String treePath, int start, int end,
		OrderByComparator<Organization> orderByComparator,
		boolean useFinderCache) {

		treePath = Objects.toString(treePath, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_T;
		finderArgs = new Object[] {
			companyId, treePath, start, end, orderByComparator
		};

		List<Organization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<Organization>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Organization organization : list) {
					if ((companyId != organization.getCompanyId()) ||
						!StringUtil.wildcardMatches(
							organization.getTreePath(), treePath, '_', '%',
							'\\', true)) {

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

			sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			boolean bindTreePath = false;

			if (treePath.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_T_TREEPATH_3);
			}
			else {
				bindTreePath = true;

				sb.append(_FINDER_COLUMN_C_T_TREEPATH_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindTreePath) {
					queryPos.add(treePath);
				}

				list = (List<Organization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first organization in the ordered set where companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByC_T_First(
			long companyId, String treePath,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByC_T_First(
			companyId, treePath, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", treePathLIKE");
		sb.append(treePath);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the first organization in the ordered set where companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByC_T_First(
		long companyId, String treePath,
		OrderByComparator<Organization> orderByComparator) {

		List<Organization> list = findByC_T(
			companyId, treePath, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last organization in the ordered set where companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByC_T_Last(
			long companyId, String treePath,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByC_T_Last(
			companyId, treePath, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", treePathLIKE");
		sb.append(treePath);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the last organization in the ordered set where companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByC_T_Last(
		long companyId, String treePath,
		OrderByComparator<Organization> orderByComparator) {

		int count = countByC_T(companyId, treePath);

		if (count == 0) {
			return null;
		}

		List<Organization> list = findByC_T(
			companyId, treePath, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the organizations before and after the current organization in the ordered set where companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization[] findByC_T_PrevAndNext(
			long organizationId, long companyId, String treePath,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		treePath = Objects.toString(treePath, "");

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = getByC_T_PrevAndNext(
				session, organization, companyId, treePath, orderByComparator,
				true);

			array[1] = organization;

			array[2] = getByC_T_PrevAndNext(
				session, organization, companyId, treePath, orderByComparator,
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

	protected Organization getByC_T_PrevAndNext(
		Session session, Organization organization, long companyId,
		String treePath, OrderByComparator<Organization> orderByComparator,
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

		sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindTreePath = false;

		if (treePath.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_TREEPATH_3);
		}
		else {
			bindTreePath = true;

			sb.append(_FINDER_COLUMN_C_T_TREEPATH_2);
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
			sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindTreePath) {
			queryPos.add(treePath);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(organization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Organization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the organizations that the user has permission to view where companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @return the matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByC_T(long companyId, String treePath) {
		return filterFindByC_T(
			companyId, treePath, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations that the user has permission to view where companyId = &#63; and treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByC_T(
		long companyId, String treePath, int start, int end) {

		return filterFindByC_T(companyId, treePath, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63; and treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByC_T(
		long companyId, String treePath, int start, int end,
		OrderByComparator<Organization> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_T(
				companyId, treePath, start, end, orderByComparator);
		}

		treePath = Objects.toString(treePath, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindTreePath = false;

		if (treePath.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_TREEPATH_3);
		}
		else {
			bindTreePath = true;

			sb.append(_FINDER_COLUMN_C_T_TREEPATH_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OrganizationImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindTreePath) {
				queryPos.add(treePath);
			}

			return (List<Organization>)QueryUtil.list(
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
	 * Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization[] filterFindByC_T_PrevAndNext(
			long organizationId, long companyId, String treePath,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_T_PrevAndNext(
				organizationId, companyId, treePath, orderByComparator);
		}

		treePath = Objects.toString(treePath, "");

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = filterGetByC_T_PrevAndNext(
				session, organization, companyId, treePath, orderByComparator,
				true);

			array[1] = organization;

			array[2] = filterGetByC_T_PrevAndNext(
				session, organization, companyId, treePath, orderByComparator,
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

	protected Organization filterGetByC_T_PrevAndNext(
		Session session, Organization organization, long companyId,
		String treePath, OrderByComparator<Organization> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindTreePath = false;

		if (treePath.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_TREEPATH_3);
		}
		else {
			bindTreePath = true;

			sb.append(_FINDER_COLUMN_C_T_TREEPATH_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, OrganizationImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (bindTreePath) {
			queryPos.add(treePath);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(organization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Organization> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the organizations where companyId = &#63; and treePath LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 */
	@Override
	public void removeByC_T(long companyId, String treePath) {
		for (Organization organization :
				findByC_T(
					companyId, treePath, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(organization);
		}
	}

	/**
	 * Returns the number of organizations where companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @return the number of matching organizations
	 */
	@Override
	public int countByC_T(long companyId, String treePath) {
		treePath = Objects.toString(treePath, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByC_T;

			finderArgs = new Object[] {companyId, treePath};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			boolean bindTreePath = false;

			if (treePath.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_T_TREEPATH_3);
			}
			else {
				bindTreePath = true;

				sb.append(_FINDER_COLUMN_C_T_TREEPATH_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindTreePath) {
					queryPos.add(treePath);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	 * Returns the number of organizations that the user has permission to view where companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @return the number of matching organizations that the user has permission to view
	 */
	@Override
	public int filterCountByC_T(long companyId, String treePath) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_T(companyId, treePath);
		}

		treePath = Objects.toString(treePath, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_ORGANIZATION_WHERE);

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindTreePath = false;

		if (treePath.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_TREEPATH_3);
		}
		else {
			bindTreePath = true;

			sb.append(_FINDER_COLUMN_C_T_TREEPATH_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindTreePath) {
				queryPos.add(treePath);
			}

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

	private static final String _FINDER_COLUMN_C_T_COMPANYID_2 =
		"organization.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_T_TREEPATH_2 =
		"organization.treePath LIKE ?";

	private static final String _FINDER_COLUMN_C_T_TREEPATH_3 =
		"(organization.treePath IS NULL OR organization.treePath LIKE '')";

	private FinderPath _finderPathFetchByC_N;
	private FinderPath _finderPathCountByC_N;

	/**
	 * Returns the organization where companyId = &#63; and name = &#63; or throws a <code>NoSuchOrganizationException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByC_N(long companyId, String name)
		throws NoSuchOrganizationException {

		Organization organization = fetchByC_N(companyId, name);

		if (organization == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchOrganizationException(sb.toString());
		}

		return organization;
	}

	/**
	 * Returns the organization where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the organization where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {companyId, name};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_N, finderArgs, this);
		}

		if (result instanceof Organization) {
			Organization organization = (Organization)result;

			if ((companyId != organization.getCompanyId()) ||
				!Objects.equals(name, organization.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
				}

				List<Organization> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_N, finderArgs, list);
					}
				}
				else {
					Organization organization = list.get(0);

					result = organization;

					cacheResult(organization);
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
			return (Organization)result;
		}
	}

	/**
	 * Removes the organization where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the organization that was removed
	 */
	@Override
	public Organization removeByC_N(long companyId, String name)
		throws NoSuchOrganizationException {

		Organization organization = findByC_N(companyId, name);

		return remove(organization);
	}

	/**
	 * Returns the number of organizations where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching organizations
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_N;

			finderArgs = new Object[] {companyId, name};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 =
		"organization.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_NAME_2 =
		"organization.name = ?";

	private static final String _FINDER_COLUMN_C_N_NAME_3 =
		"(organization.name IS NULL OR organization.name = '')";

	private FinderPath _finderPathWithPaginationFindByC_LikeN;
	private FinderPath _finderPathWithPaginationCountByC_LikeN;

	/**
	 * Returns all the organizations where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching organizations
	 */
	@Override
	public List<Organization> findByC_LikeN(long companyId, String name) {
		return findByC_LikeN(
			companyId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations
	 */
	@Override
	public List<Organization> findByC_LikeN(
		long companyId, String name, int start, int end) {

		return findByC_LikeN(companyId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByC_LikeN(
		long companyId, String name, int start, int end,
		OrderByComparator<Organization> orderByComparator) {

		return findByC_LikeN(
			companyId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the organizations where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByC_LikeN(
		long companyId, String name, int start, int end,
		OrderByComparator<Organization> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_LikeN;
		finderArgs = new Object[] {
			companyId, name, start, end, orderByComparator
		};

		List<Organization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<Organization>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Organization organization : list) {
					if ((companyId != organization.getCompanyId()) ||
						!StringUtil.wildcardMatches(
							organization.getName(), name, '_', '%', '\\',
							false)) {

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

			sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_C_LIKEN_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
				}

				list = (List<Organization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first organization in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByC_LikeN_First(
			long companyId, String name,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByC_LikeN_First(
			companyId, name, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the first organization in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByC_LikeN_First(
		long companyId, String name,
		OrderByComparator<Organization> orderByComparator) {

		List<Organization> list = findByC_LikeN(
			companyId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last organization in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByC_LikeN_Last(
			long companyId, String name,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByC_LikeN_Last(
			companyId, name, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the last organization in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByC_LikeN_Last(
		long companyId, String name,
		OrderByComparator<Organization> orderByComparator) {

		int count = countByC_LikeN(companyId, name);

		if (count == 0) {
			return null;
		}

		List<Organization> list = findByC_LikeN(
			companyId, name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the organizations before and after the current organization in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization[] findByC_LikeN_PrevAndNext(
			long organizationId, long companyId, String name,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		name = Objects.toString(name, "");

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = getByC_LikeN_PrevAndNext(
				session, organization, companyId, name, orderByComparator,
				true);

			array[1] = organization;

			array[2] = getByC_LikeN_PrevAndNext(
				session, organization, companyId, name, orderByComparator,
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

	protected Organization getByC_LikeN_PrevAndNext(
		Session session, Organization organization, long companyId, String name,
		OrderByComparator<Organization> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

		sb.append(_FINDER_COLUMN_C_LIKEN_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_C_LIKEN_NAME_2);
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
			sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindName) {
			queryPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(organization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Organization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the organizations that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByC_LikeN(long companyId, String name) {
		return filterFindByC_LikeN(
			companyId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByC_LikeN(
		long companyId, String name, int start, int end) {

		return filterFindByC_LikeN(companyId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByC_LikeN(
		long companyId, String name, int start, int end,
		OrderByComparator<Organization> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_LikeN(
				companyId, name, start, end, orderByComparator);
		}

		name = Objects.toString(name, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_LIKEN_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_C_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OrganizationImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindName) {
				queryPos.add(StringUtil.toLowerCase(name));
			}

			return (List<Organization>)QueryUtil.list(
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
	 * Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization[] filterFindByC_LikeN_PrevAndNext(
			long organizationId, long companyId, String name,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_LikeN_PrevAndNext(
				organizationId, companyId, name, orderByComparator);
		}

		name = Objects.toString(name, "");

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = filterGetByC_LikeN_PrevAndNext(
				session, organization, companyId, name, orderByComparator,
				true);

			array[1] = organization;

			array[2] = filterGetByC_LikeN_PrevAndNext(
				session, organization, companyId, name, orderByComparator,
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

	protected Organization filterGetByC_LikeN_PrevAndNext(
		Session session, Organization organization, long companyId, String name,
		OrderByComparator<Organization> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_LIKEN_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_C_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, OrganizationImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (bindName) {
			queryPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(organization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Organization> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the organizations where companyId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	@Override
	public void removeByC_LikeN(long companyId, String name) {
		for (Organization organization :
				findByC_LikeN(
					companyId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(organization);
		}
	}

	/**
	 * Returns the number of organizations where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching organizations
	 */
	@Override
	public int countByC_LikeN(long companyId, String name) {
		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByC_LikeN;

			finderArgs = new Object[] {companyId, name};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_C_LIKEN_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_LIKEN_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	 * Returns the number of organizations that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching organizations that the user has permission to view
	 */
	@Override
	public int filterCountByC_LikeN(long companyId, String name) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_LikeN(companyId, name);
		}

		name = Objects.toString(name, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_ORGANIZATION_WHERE);

		sb.append(_FINDER_COLUMN_C_LIKEN_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_C_LIKEN_NAME_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindName) {
				queryPos.add(StringUtil.toLowerCase(name));
			}

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

	private static final String _FINDER_COLUMN_C_LIKEN_COMPANYID_2 =
		"organization.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_LIKEN_NAME_2 =
		"lower(organization.name) LIKE ?";

	private static final String _FINDER_COLUMN_C_LIKEN_NAME_3 =
		"(organization.name IS NULL OR organization.name LIKE '')";

	private FinderPath _finderPathWithPaginationFindByO_C_P;
	private FinderPath _finderPathWithPaginationCountByO_C_P;

	/**
	 * Returns all the organizations where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @return the matching organizations
	 */
	@Override
	public List<Organization> findByO_C_P(
		long organizationId, long companyId, long parentOrganizationId) {

		return findByO_C_P(
			organizationId, companyId, parentOrganizationId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations
	 */
	@Override
	public List<Organization> findByO_C_P(
		long organizationId, long companyId, long parentOrganizationId,
		int start, int end) {

		return findByO_C_P(
			organizationId, companyId, parentOrganizationId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByO_C_P(
		long organizationId, long companyId, long parentOrganizationId,
		int start, int end, OrderByComparator<Organization> orderByComparator) {

		return findByO_C_P(
			organizationId, companyId, parentOrganizationId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the organizations where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByO_C_P(
		long organizationId, long companyId, long parentOrganizationId,
		int start, int end, OrderByComparator<Organization> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByO_C_P;
		finderArgs = new Object[] {
			organizationId, companyId, parentOrganizationId, start, end,
			orderByComparator
		};

		List<Organization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<Organization>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Organization organization : list) {
					if ((organizationId >= organization.getOrganizationId()) ||
						(companyId != organization.getCompanyId()) ||
						(parentOrganizationId !=
							organization.getParentOrganizationId())) {

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

			sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_O_C_P_ORGANIZATIONID_2);

			sb.append(_FINDER_COLUMN_O_C_P_COMPANYID_2);

			sb.append(_FINDER_COLUMN_O_C_P_PARENTORGANIZATIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(organizationId);

				queryPos.add(companyId);

				queryPos.add(parentOrganizationId);

				list = (List<Organization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first organization in the ordered set where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByO_C_P_First(
			long organizationId, long companyId, long parentOrganizationId,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByO_C_P_First(
			organizationId, companyId, parentOrganizationId, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("organizationId>");
		sb.append(organizationId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", parentOrganizationId=");
		sb.append(parentOrganizationId);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the first organization in the ordered set where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByO_C_P_First(
		long organizationId, long companyId, long parentOrganizationId,
		OrderByComparator<Organization> orderByComparator) {

		List<Organization> list = findByO_C_P(
			organizationId, companyId, parentOrganizationId, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last organization in the ordered set where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByO_C_P_Last(
			long organizationId, long companyId, long parentOrganizationId,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByO_C_P_Last(
			organizationId, companyId, parentOrganizationId, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("organizationId>");
		sb.append(organizationId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", parentOrganizationId=");
		sb.append(parentOrganizationId);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the last organization in the ordered set where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByO_C_P_Last(
		long organizationId, long companyId, long parentOrganizationId,
		OrderByComparator<Organization> orderByComparator) {

		int count = countByO_C_P(
			organizationId, companyId, parentOrganizationId);

		if (count == 0) {
			return null;
		}

		List<Organization> list = findByO_C_P(
			organizationId, companyId, parentOrganizationId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the organizations that the user has permission to view where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @return the matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByO_C_P(
		long organizationId, long companyId, long parentOrganizationId) {

		return filterFindByO_C_P(
			organizationId, companyId, parentOrganizationId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations that the user has permission to view where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByO_C_P(
		long organizationId, long companyId, long parentOrganizationId,
		int start, int end) {

		return filterFindByO_C_P(
			organizationId, companyId, parentOrganizationId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations that the user has permissions to view where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByO_C_P(
		long organizationId, long companyId, long parentOrganizationId,
		int start, int end, OrderByComparator<Organization> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByO_C_P(
				organizationId, companyId, parentOrganizationId, start, end,
				orderByComparator);
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
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_O_C_P_ORGANIZATIONID_2);

		sb.append(_FINDER_COLUMN_O_C_P_COMPANYID_2);

		sb.append(_FINDER_COLUMN_O_C_P_PARENTORGANIZATIONID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OrganizationImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(organizationId);

			queryPos.add(companyId);

			queryPos.add(parentOrganizationId);

			return (List<Organization>)QueryUtil.list(
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
	 * Removes all the organizations where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63; from the database.
	 *
	 * @param organizationId the organization ID
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 */
	@Override
	public void removeByO_C_P(
		long organizationId, long companyId, long parentOrganizationId) {

		for (Organization organization :
				findByO_C_P(
					organizationId, companyId, parentOrganizationId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(organization);
		}
	}

	/**
	 * Returns the number of organizations where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @return the number of matching organizations
	 */
	@Override
	public int countByO_C_P(
		long organizationId, long companyId, long parentOrganizationId) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByO_C_P;

			finderArgs = new Object[] {
				organizationId, companyId, parentOrganizationId
			};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_O_C_P_ORGANIZATIONID_2);

			sb.append(_FINDER_COLUMN_O_C_P_COMPANYID_2);

			sb.append(_FINDER_COLUMN_O_C_P_PARENTORGANIZATIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(organizationId);

				queryPos.add(companyId);

				queryPos.add(parentOrganizationId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	 * Returns the number of organizations that the user has permission to view where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @return the number of matching organizations that the user has permission to view
	 */
	@Override
	public int filterCountByO_C_P(
		long organizationId, long companyId, long parentOrganizationId) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByO_C_P(
				organizationId, companyId, parentOrganizationId);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_ORGANIZATION_WHERE);

		sb.append(_FINDER_COLUMN_O_C_P_ORGANIZATIONID_2);

		sb.append(_FINDER_COLUMN_O_C_P_COMPANYID_2);

		sb.append(_FINDER_COLUMN_O_C_P_PARENTORGANIZATIONID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(organizationId);

			queryPos.add(companyId);

			queryPos.add(parentOrganizationId);

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

	private static final String _FINDER_COLUMN_O_C_P_ORGANIZATIONID_2 =
		"organization.organizationId > ? AND ";

	private static final String _FINDER_COLUMN_O_C_P_COMPANYID_2 =
		"organization.companyId = ? AND ";

	private static final String _FINDER_COLUMN_O_C_P_PARENTORGANIZATIONID_2 =
		"organization.parentOrganizationId = ?";

	private FinderPath _finderPathWithPaginationFindByC_P_LikeN;
	private FinderPath _finderPathWithPaginationCountByC_P_LikeN;

	/**
	 * Returns all the organizations where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param name the name
	 * @return the matching organizations
	 */
	@Override
	public List<Organization> findByC_P_LikeN(
		long companyId, long parentOrganizationId, String name) {

		return findByC_P_LikeN(
			companyId, parentOrganizationId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param name the name
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations
	 */
	@Override
	public List<Organization> findByC_P_LikeN(
		long companyId, long parentOrganizationId, String name, int start,
		int end) {

		return findByC_P_LikeN(
			companyId, parentOrganizationId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param name the name
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByC_P_LikeN(
		long companyId, long parentOrganizationId, String name, int start,
		int end, OrderByComparator<Organization> orderByComparator) {

		return findByC_P_LikeN(
			companyId, parentOrganizationId, name, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the organizations where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param name the name
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching organizations
	 */
	@Override
	public List<Organization> findByC_P_LikeN(
		long companyId, long parentOrganizationId, String name, int start,
		int end, OrderByComparator<Organization> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_P_LikeN;
		finderArgs = new Object[] {
			companyId, parentOrganizationId, name, start, end, orderByComparator
		};

		List<Organization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<Organization>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Organization organization : list) {
					if ((companyId != organization.getCompanyId()) ||
						(parentOrganizationId !=
							organization.getParentOrganizationId()) ||
						!StringUtil.wildcardMatches(
							organization.getName(), name, '_', '%', '\\',
							false)) {

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

			sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_C_P_LIKEN_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_P_LIKEN_PARENTORGANIZATIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_P_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_P_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(parentOrganizationId);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
				}

				list = (List<Organization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByC_P_LikeN_First(
			long companyId, long parentOrganizationId, String name,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByC_P_LikeN_First(
			companyId, parentOrganizationId, name, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", parentOrganizationId=");
		sb.append(parentOrganizationId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the first organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByC_P_LikeN_First(
		long companyId, long parentOrganizationId, String name,
		OrderByComparator<Organization> orderByComparator) {

		List<Organization> list = findByC_P_LikeN(
			companyId, parentOrganizationId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByC_P_LikeN_Last(
			long companyId, long parentOrganizationId, String name,
			OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		Organization organization = fetchByC_P_LikeN_Last(
			companyId, parentOrganizationId, name, orderByComparator);

		if (organization != null) {
			return organization;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", parentOrganizationId=");
		sb.append(parentOrganizationId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchOrganizationException(sb.toString());
	}

	/**
	 * Returns the last organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByC_P_LikeN_Last(
		long companyId, long parentOrganizationId, String name,
		OrderByComparator<Organization> orderByComparator) {

		int count = countByC_P_LikeN(companyId, parentOrganizationId, name);

		if (count == 0) {
			return null;
		}

		List<Organization> list = findByC_P_LikeN(
			companyId, parentOrganizationId, name, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the organizations before and after the current organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization[] findByC_P_LikeN_PrevAndNext(
			long organizationId, long companyId, long parentOrganizationId,
			String name, OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		name = Objects.toString(name, "");

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = getByC_P_LikeN_PrevAndNext(
				session, organization, companyId, parentOrganizationId, name,
				orderByComparator, true);

			array[1] = organization;

			array[2] = getByC_P_LikeN_PrevAndNext(
				session, organization, companyId, parentOrganizationId, name,
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

	protected Organization getByC_P_LikeN_PrevAndNext(
		Session session, Organization organization, long companyId,
		long parentOrganizationId, String name,
		OrderByComparator<Organization> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

		sb.append(_FINDER_COLUMN_C_P_LIKEN_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_P_LIKEN_PARENTORGANIZATIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_P_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_C_P_LIKEN_NAME_2);
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
			sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(parentOrganizationId);

		if (bindName) {
			queryPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(organization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Organization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param name the name
	 * @return the matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByC_P_LikeN(
		long companyId, long parentOrganizationId, String name) {

		return filterFindByC_P_LikeN(
			companyId, parentOrganizationId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param name the name
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByC_P_LikeN(
		long companyId, long parentOrganizationId, String name, int start,
		int end) {

		return filterFindByC_P_LikeN(
			companyId, parentOrganizationId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param name the name
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations that the user has permission to view
	 */
	@Override
	public List<Organization> filterFindByC_P_LikeN(
		long companyId, long parentOrganizationId, String name, int start,
		int end, OrderByComparator<Organization> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_P_LikeN(
				companyId, parentOrganizationId, name, start, end,
				orderByComparator);
		}

		name = Objects.toString(name, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_P_LIKEN_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_P_LIKEN_PARENTORGANIZATIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_P_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_C_P_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OrganizationImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(parentOrganizationId);

			if (bindName) {
				queryPos.add(StringUtil.toLowerCase(name));
			}

			return (List<Organization>)QueryUtil.list(
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
	 * Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization[] filterFindByC_P_LikeN_PrevAndNext(
			long organizationId, long companyId, long parentOrganizationId,
			String name, OrderByComparator<Organization> orderByComparator)
		throws NoSuchOrganizationException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_P_LikeN_PrevAndNext(
				organizationId, companyId, parentOrganizationId, name,
				orderByComparator);
		}

		name = Objects.toString(name, "");

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = filterGetByC_P_LikeN_PrevAndNext(
				session, organization, companyId, parentOrganizationId, name,
				orderByComparator, true);

			array[1] = organization;

			array[2] = filterGetByC_P_LikeN_PrevAndNext(
				session, organization, companyId, parentOrganizationId, name,
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

	protected Organization filterGetByC_P_LikeN_PrevAndNext(
		Session session, Organization organization, long companyId,
		long parentOrganizationId, String name,
		OrderByComparator<Organization> orderByComparator, boolean previous) {

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
			sb.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_P_LIKEN_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_P_LIKEN_PARENTORGANIZATIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_P_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_C_P_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, OrganizationImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		queryPos.add(parentOrganizationId);

		if (bindName) {
			queryPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(organization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Organization> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the organizations where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param name the name
	 */
	@Override
	public void removeByC_P_LikeN(
		long companyId, long parentOrganizationId, String name) {

		for (Organization organization :
				findByC_P_LikeN(
					companyId, parentOrganizationId, name, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(organization);
		}
	}

	/**
	 * Returns the number of organizations where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param name the name
	 * @return the number of matching organizations
	 */
	@Override
	public int countByC_P_LikeN(
		long companyId, long parentOrganizationId, String name) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByC_P_LikeN;

			finderArgs = new Object[] {companyId, parentOrganizationId, name};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_C_P_LIKEN_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_P_LIKEN_PARENTORGANIZATIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_P_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_P_LIKEN_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(parentOrganizationId);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	 * Returns the number of organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param name the name
	 * @return the number of matching organizations that the user has permission to view
	 */
	@Override
	public int filterCountByC_P_LikeN(
		long companyId, long parentOrganizationId, String name) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_P_LikeN(companyId, parentOrganizationId, name);
		}

		name = Objects.toString(name, "");

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_ORGANIZATION_WHERE);

		sb.append(_FINDER_COLUMN_C_P_LIKEN_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_P_LIKEN_PARENTORGANIZATIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_P_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_C_P_LIKEN_NAME_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Organization.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(parentOrganizationId);

			if (bindName) {
				queryPos.add(StringUtil.toLowerCase(name));
			}

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

	private static final String _FINDER_COLUMN_C_P_LIKEN_COMPANYID_2 =
		"organization.companyId = ? AND ";

	private static final String
		_FINDER_COLUMN_C_P_LIKEN_PARENTORGANIZATIONID_2 =
			"organization.parentOrganizationId = ? AND ";

	private static final String _FINDER_COLUMN_C_P_LIKEN_NAME_2 =
		"lower(organization.name) LIKE ?";

	private static final String _FINDER_COLUMN_C_P_LIKEN_NAME_3 =
		"(organization.name IS NULL OR organization.name LIKE '')";

	private FinderPath _finderPathFetchByC_ERC;
	private FinderPath _finderPathCountByC_ERC;

	/**
	 * Returns the organization where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchOrganizationException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching organization
	 * @throws NoSuchOrganizationException if a matching organization could not be found
	 */
	@Override
	public Organization findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchOrganizationException {

		Organization organization = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (organization == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchOrganizationException(sb.toString());
		}

		return organization;
	}

	/**
	 * Returns the organization where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the organization where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching organization, or <code>null</code> if a matching organization could not be found
	 */
	@Override
	public Organization fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {companyId, externalReferenceCode};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_ERC, finderArgs, this);
		}

		if (result instanceof Organization) {
			Organization organization = (Organization)result;

			if ((companyId != organization.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					organization.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				List<Organization> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_ERC, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!productionMode || !useFinderCache) {
								finderArgs = new Object[] {
									companyId, externalReferenceCode
								};
							}

							_log.warn(
								"OrganizationPersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					Organization organization = list.get(0);

					result = organization;

					cacheResult(organization);
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
			return (Organization)result;
		}
	}

	/**
	 * Removes the organization where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the organization that was removed
	 */
	@Override
	public Organization removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchOrganizationException {

		Organization organization = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(organization);
	}

	/**
	 * Returns the number of organizations where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching organizations
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_ERC;

			finderArgs = new Object[] {companyId, externalReferenceCode};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ORGANIZATION_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_ERC_COMPANYID_2 =
		"organization.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"organization.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(organization.externalReferenceCode IS NULL OR organization.externalReferenceCode = '')";

	public OrganizationPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");
		dbColumnNames.put("groups", "groups_");

		setDBColumnNames(dbColumnNames);

		setModelClass(Organization.class);

		setModelImplClass(OrganizationImpl.class);
		setModelPKClass(long.class);

		setTable(OrganizationTable.INSTANCE);
	}

	/**
	 * Caches the organization in the entity cache if it is enabled.
	 *
	 * @param organization the organization
	 */
	@Override
	public void cacheResult(Organization organization) {
		if (organization.getCtCollectionId() != 0) {
			organization.resetOriginalValues();

			return;
		}

		EntityCacheUtil.putResult(
			OrganizationImpl.class, organization.getPrimaryKey(), organization);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_N,
			new Object[] {organization.getCompanyId(), organization.getName()},
			organization);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				organization.getCompanyId(),
				organization.getExternalReferenceCode()
			},
			organization);

		organization.resetOriginalValues();
	}

	/**
	 * Caches the organizations in the entity cache if it is enabled.
	 *
	 * @param organizations the organizations
	 */
	@Override
	public void cacheResult(List<Organization> organizations) {
		for (Organization organization : organizations) {
			if (organization.getCtCollectionId() != 0) {
				organization.resetOriginalValues();

				continue;
			}

			if (EntityCacheUtil.getResult(
					OrganizationImpl.class, organization.getPrimaryKey()) ==
						null) {

				cacheResult(organization);
			}
			else {
				organization.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all organizations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(OrganizationImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the organization.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Organization organization) {
		EntityCacheUtil.removeResult(
			OrganizationImpl.class, organization.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((OrganizationModelImpl)organization, true);
	}

	@Override
	public void clearCache(List<Organization> organizations) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Organization organization : organizations) {
			EntityCacheUtil.removeResult(
				OrganizationImpl.class, organization.getPrimaryKey());

			clearUniqueFindersCache((OrganizationModelImpl)organization, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(OrganizationImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		OrganizationModelImpl organizationModelImpl) {

		Object[] args = new Object[] {
			organizationModelImpl.getCompanyId(),
			organizationModelImpl.getName()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByC_N, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByC_N, args, organizationModelImpl, false);

		args = new Object[] {
			organizationModelImpl.getCompanyId(),
			organizationModelImpl.getExternalReferenceCode()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByC_ERC, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByC_ERC, args, organizationModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		OrganizationModelImpl organizationModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				organizationModelImpl.getCompanyId(),
				organizationModelImpl.getName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_N, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_N, args);
		}

		if ((organizationModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				organizationModelImpl.getOriginalCompanyId(),
				organizationModelImpl.getOriginalName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_N, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_N, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				organizationModelImpl.getCompanyId(),
				organizationModelImpl.getExternalReferenceCode()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_ERC, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_ERC, args);
		}

		if ((organizationModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_ERC.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				organizationModelImpl.getOriginalCompanyId(),
				organizationModelImpl.getOriginalExternalReferenceCode()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_ERC, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_ERC, args);
		}
	}

	/**
	 * Creates a new organization with the primary key. Does not add the organization to the database.
	 *
	 * @param organizationId the primary key for the new organization
	 * @return the new organization
	 */
	@Override
	public Organization create(long organizationId) {
		Organization organization = new OrganizationImpl();

		organization.setNew(true);
		organization.setPrimaryKey(organizationId);

		String uuid = PortalUUIDUtil.generate();

		organization.setUuid(uuid);

		organization.setCompanyId(CompanyThreadLocal.getCompanyId());

		return organization;
	}

	/**
	 * Removes the organization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param organizationId the primary key of the organization
	 * @return the organization that was removed
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization remove(long organizationId)
		throws NoSuchOrganizationException {

		return remove((Serializable)organizationId);
	}

	/**
	 * Removes the organization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the organization
	 * @return the organization that was removed
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization remove(Serializable primaryKey)
		throws NoSuchOrganizationException {

		Session session = null;

		try {
			session = openSession();

			Organization organization = (Organization)session.get(
				OrganizationImpl.class, primaryKey);

			if (organization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOrganizationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(organization);
		}
		catch (NoSuchOrganizationException noSuchEntityException) {
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
	protected Organization removeImpl(Organization organization) {
		organizationToGroupTableMapper.deleteLeftPrimaryKeyTableMappings(
			organization.getPrimaryKey());

		organizationToUserTableMapper.deleteLeftPrimaryKeyTableMappings(
			organization.getPrimaryKey());

		if (!CTPersistenceHelperUtil.isRemove(organization)) {
			return organization;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(organization)) {
				organization = (Organization)session.get(
					OrganizationImpl.class, organization.getPrimaryKeyObj());
			}

			if (organization != null) {
				session.delete(organization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (organization != null) {
			clearCache(organization);
		}

		return organization;
	}

	@Override
	public Organization updateImpl(Organization organization) {
		boolean isNew = organization.isNew();

		if (!(organization instanceof OrganizationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(organization.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					organization);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in organization proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Organization implementation " +
					organization.getClass());
		}

		OrganizationModelImpl organizationModelImpl =
			(OrganizationModelImpl)organization;

		if (Validator.isNull(organization.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			organization.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (organization.getCreateDate() == null)) {
			if (serviceContext == null) {
				organization.setCreateDate(now);
			}
			else {
				organization.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!organizationModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				organization.setModifiedDate(now);
			}
			else {
				organization.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(organization)) {
				if (!isNew) {
					session.evict(
						OrganizationImpl.class,
						organization.getPrimaryKeyObj());
				}

				session.save(organization);

				organization.setNew(false);
			}
			else {
				organization = (Organization)session.merge(organization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (organization.getCtCollectionId() != 0) {
			organization.resetOriginalValues();

			return organization;
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {organizationModelImpl.getUuid()};

			FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				organizationModelImpl.getUuid(),
				organizationModelImpl.getCompanyId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {organizationModelImpl.getCompanyId()};

			FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {organizationModelImpl.getCompanyId()};

			FinderCacheUtil.removeResult(_finderPathCountByLocations, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByLocations, args);

			args = new Object[] {
				organizationModelImpl.getCompanyId(),
				organizationModelImpl.getParentOrganizationId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_P, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_P, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((organizationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					organizationModelImpl.getOriginalUuid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {organizationModelImpl.getUuid()};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((organizationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					organizationModelImpl.getOriginalUuid(),
					organizationModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					organizationModelImpl.getUuid(),
					organizationModelImpl.getCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((organizationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					organizationModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {organizationModelImpl.getCompanyId()};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((organizationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByLocations.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					organizationModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByLocations, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByLocations, args);

				args = new Object[] {organizationModelImpl.getCompanyId()};

				FinderCacheUtil.removeResult(_finderPathCountByLocations, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByLocations, args);
			}

			if ((organizationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					organizationModelImpl.getOriginalCompanyId(),
					organizationModelImpl.getOriginalParentOrganizationId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_P, args);

				args = new Object[] {
					organizationModelImpl.getCompanyId(),
					organizationModelImpl.getParentOrganizationId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_P, args);
			}
		}

		EntityCacheUtil.putResult(
			OrganizationImpl.class, organization.getPrimaryKey(), organization,
			false);

		clearUniqueFindersCache(organizationModelImpl, false);
		cacheUniqueFindersCache(organizationModelImpl);

		organization.resetOriginalValues();

		return organization;
	}

	/**
	 * Returns the organization with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the organization
	 * @return the organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOrganizationException {

		Organization organization = fetchByPrimaryKey(primaryKey);

		if (organization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOrganizationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return organization;
	}

	/**
	 * Returns the organization with the primary key or throws a <code>NoSuchOrganizationException</code> if it could not be found.
	 *
	 * @param organizationId the primary key of the organization
	 * @return the organization
	 * @throws NoSuchOrganizationException if a organization with the primary key could not be found
	 */
	@Override
	public Organization findByPrimaryKey(long organizationId)
		throws NoSuchOrganizationException {

		return findByPrimaryKey((Serializable)organizationId);
	}

	/**
	 * Returns the organization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the organization
	 * @return the organization, or <code>null</code> if a organization with the primary key could not be found
	 */
	@Override
	public Organization fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(Organization.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		Organization organization = null;

		Session session = null;

		try {
			session = openSession();

			organization = (Organization)session.get(
				OrganizationImpl.class, primaryKey);

			if (organization != null) {
				cacheResult(organization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return organization;
	}

	/**
	 * Returns the organization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param organizationId the primary key of the organization
	 * @return the organization, or <code>null</code> if a organization with the primary key could not be found
	 */
	@Override
	public Organization fetchByPrimaryKey(long organizationId) {
		return fetchByPrimaryKey((Serializable)organizationId);
	}

	@Override
	public Map<Serializable, Organization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(Organization.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Organization> map =
			new HashMap<Serializable, Organization>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Organization organization = fetchByPrimaryKey(primaryKey);

			if (organization != null) {
				map.put(primaryKey, organization);
			}

			return map;
		}

		StringBundler sb = new StringBundler(primaryKeys.size() * 2 + 1);

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

			for (Organization organization : (List<Organization>)query.list()) {
				map.put(organization.getPrimaryKeyObj(), organization);

				cacheResult(organization);
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
	 * Returns all the organizations.
	 *
	 * @return the organizations
	 */
	@Override
	public List<Organization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of organizations
	 */
	@Override
	public List<Organization> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of organizations
	 */
	@Override
	public List<Organization> findAll(
		int start, int end, OrderByComparator<Organization> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the organizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of organizations
	 */
	@Override
	public List<Organization> findAll(
		int start, int end, OrderByComparator<Organization> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

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

		List<Organization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<Organization>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ORGANIZATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ORGANIZATION;

				sql = sql.concat(OrganizationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Organization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the organizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Organization organization : findAll()) {
			remove(organization);
		}
	}

	/**
	 * Returns the number of organizations.
	 *
	 * @return the number of organizations
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			Organization.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ORGANIZATION);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
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

	/**
	 * Returns the primaryKeys of groups associated with the organization.
	 *
	 * @param pk the primary key of the organization
	 * @return long[] of the primaryKeys of groups associated with the organization
	 */
	@Override
	public long[] getGroupPrimaryKeys(long pk) {
		long[] pks = organizationToGroupTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the groups associated with the organization.
	 *
	 * @param pk the primary key of the organization
	 * @return the groups associated with the organization
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Group> getGroups(long pk) {
		return getGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the groups associated with the organization.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the organization
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of groups associated with the organization
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Group> getGroups(
		long pk, int start, int end) {

		return getGroups(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups associated with the organization.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the organization
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of groups associated with the organization
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Group> getGroups(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.Group>
			orderByComparator) {

		return organizationToGroupTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of groups associated with the organization.
	 *
	 * @param pk the primary key of the organization
	 * @return the number of groups associated with the organization
	 */
	@Override
	public int getGroupsSize(long pk) {
		long[] pks = organizationToGroupTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the group is associated with the organization.
	 *
	 * @param pk the primary key of the organization
	 * @param groupPK the primary key of the group
	 * @return <code>true</code> if the group is associated with the organization; <code>false</code> otherwise
	 */
	@Override
	public boolean containsGroup(long pk, long groupPK) {
		return organizationToGroupTableMapper.containsTableMapping(pk, groupPK);
	}

	/**
	 * Returns <code>true</code> if the organization has any groups associated with it.
	 *
	 * @param pk the primary key of the organization to check for associations with groups
	 * @return <code>true</code> if the organization has any groups associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsGroups(long pk) {
		if (getGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the organization and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param groupPK the primary key of the group
	 */
	@Override
	public void addGroup(long pk, long groupPK) {
		Organization organization = fetchByPrimaryKey(pk);

		if (organization == null) {
			organizationToGroupTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, groupPK);
		}
		else {
			organizationToGroupTableMapper.addTableMapping(
				organization.getCompanyId(), pk, groupPK);
		}
	}

	/**
	 * Adds an association between the organization and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param group the group
	 */
	@Override
	public void addGroup(long pk, com.liferay.portal.kernel.model.Group group) {
		Organization organization = fetchByPrimaryKey(pk);

		if (organization == null) {
			organizationToGroupTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, group.getPrimaryKey());
		}
		else {
			organizationToGroupTableMapper.addTableMapping(
				organization.getCompanyId(), pk, group.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the organization and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param groupPKs the primary keys of the groups
	 */
	@Override
	public void addGroups(long pk, long[] groupPKs) {
		long companyId = 0;

		Organization organization = fetchByPrimaryKey(pk);

		if (organization == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = organization.getCompanyId();
		}

		organizationToGroupTableMapper.addTableMappings(
			companyId, pk, groupPKs);
	}

	/**
	 * Adds an association between the organization and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param groups the groups
	 */
	@Override
	public void addGroups(
		long pk, List<com.liferay.portal.kernel.model.Group> groups) {

		addGroups(
			pk,
			ListUtil.toLongArray(
				groups,
				com.liferay.portal.kernel.model.Group.GROUP_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the organization and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization to clear the associated groups from
	 */
	@Override
	public void clearGroups(long pk) {
		organizationToGroupTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the organization and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param groupPK the primary key of the group
	 */
	@Override
	public void removeGroup(long pk, long groupPK) {
		organizationToGroupTableMapper.deleteTableMapping(pk, groupPK);
	}

	/**
	 * Removes the association between the organization and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param group the group
	 */
	@Override
	public void removeGroup(
		long pk, com.liferay.portal.kernel.model.Group group) {

		organizationToGroupTableMapper.deleteTableMapping(
			pk, group.getPrimaryKey());
	}

	/**
	 * Removes the association between the organization and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param groupPKs the primary keys of the groups
	 */
	@Override
	public void removeGroups(long pk, long[] groupPKs) {
		organizationToGroupTableMapper.deleteTableMappings(pk, groupPKs);
	}

	/**
	 * Removes the association between the organization and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param groups the groups
	 */
	@Override
	public void removeGroups(
		long pk, List<com.liferay.portal.kernel.model.Group> groups) {

		removeGroups(
			pk,
			ListUtil.toLongArray(
				groups,
				com.liferay.portal.kernel.model.Group.GROUP_ID_ACCESSOR));
	}

	/**
	 * Sets the groups associated with the organization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param groupPKs the primary keys of the groups to be associated with the organization
	 */
	@Override
	public void setGroups(long pk, long[] groupPKs) {
		Set<Long> newGroupPKsSet = SetUtil.fromArray(groupPKs);
		Set<Long> oldGroupPKsSet = SetUtil.fromArray(
			organizationToGroupTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeGroupPKsSet = new HashSet<Long>(oldGroupPKsSet);

		removeGroupPKsSet.removeAll(newGroupPKsSet);

		organizationToGroupTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeGroupPKsSet));

		newGroupPKsSet.removeAll(oldGroupPKsSet);

		long companyId = 0;

		Organization organization = fetchByPrimaryKey(pk);

		if (organization == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = organization.getCompanyId();
		}

		organizationToGroupTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newGroupPKsSet));
	}

	/**
	 * Sets the groups associated with the organization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param groups the groups to be associated with the organization
	 */
	@Override
	public void setGroups(
		long pk, List<com.liferay.portal.kernel.model.Group> groups) {

		try {
			long[] groupPKs = new long[groups.size()];

			for (int i = 0; i < groups.size(); i++) {
				com.liferay.portal.kernel.model.Group group = groups.get(i);

				groupPKs[i] = group.getPrimaryKey();
			}

			setGroups(pk, groupPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	/**
	 * Returns the primaryKeys of users associated with the organization.
	 *
	 * @param pk the primary key of the organization
	 * @return long[] of the primaryKeys of users associated with the organization
	 */
	@Override
	public long[] getUserPrimaryKeys(long pk) {
		long[] pks = organizationToUserTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the users associated with the organization.
	 *
	 * @param pk the primary key of the organization
	 * @return the users associated with the organization
	 */
	@Override
	public List<com.liferay.portal.kernel.model.User> getUsers(long pk) {
		return getUsers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the users associated with the organization.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the organization
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of users associated with the organization
	 */
	@Override
	public List<com.liferay.portal.kernel.model.User> getUsers(
		long pk, int start, int end) {

		return getUsers(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users associated with the organization.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the organization
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of users associated with the organization
	 */
	@Override
	public List<com.liferay.portal.kernel.model.User> getUsers(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.User>
			orderByComparator) {

		return organizationToUserTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of users associated with the organization.
	 *
	 * @param pk the primary key of the organization
	 * @return the number of users associated with the organization
	 */
	@Override
	public int getUsersSize(long pk) {
		long[] pks = organizationToUserTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the user is associated with the organization.
	 *
	 * @param pk the primary key of the organization
	 * @param userPK the primary key of the user
	 * @return <code>true</code> if the user is associated with the organization; <code>false</code> otherwise
	 */
	@Override
	public boolean containsUser(long pk, long userPK) {
		return organizationToUserTableMapper.containsTableMapping(pk, userPK);
	}

	/**
	 * Returns <code>true</code> if the organization has any users associated with it.
	 *
	 * @param pk the primary key of the organization to check for associations with users
	 * @return <code>true</code> if the organization has any users associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsUsers(long pk) {
		if (getUsersSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the organization and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param userPK the primary key of the user
	 */
	@Override
	public void addUser(long pk, long userPK) {
		Organization organization = fetchByPrimaryKey(pk);

		if (organization == null) {
			organizationToUserTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, userPK);
		}
		else {
			organizationToUserTableMapper.addTableMapping(
				organization.getCompanyId(), pk, userPK);
		}
	}

	/**
	 * Adds an association between the organization and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param user the user
	 */
	@Override
	public void addUser(long pk, com.liferay.portal.kernel.model.User user) {
		Organization organization = fetchByPrimaryKey(pk);

		if (organization == null) {
			organizationToUserTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, user.getPrimaryKey());
		}
		else {
			organizationToUserTableMapper.addTableMapping(
				organization.getCompanyId(), pk, user.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the organization and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param userPKs the primary keys of the users
	 */
	@Override
	public void addUsers(long pk, long[] userPKs) {
		long companyId = 0;

		Organization organization = fetchByPrimaryKey(pk);

		if (organization == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = organization.getCompanyId();
		}

		organizationToUserTableMapper.addTableMappings(companyId, pk, userPKs);
	}

	/**
	 * Adds an association between the organization and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param users the users
	 */
	@Override
	public void addUsers(
		long pk, List<com.liferay.portal.kernel.model.User> users) {

		addUsers(
			pk,
			ListUtil.toLongArray(
				users, com.liferay.portal.kernel.model.User.USER_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the organization and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization to clear the associated users from
	 */
	@Override
	public void clearUsers(long pk) {
		organizationToUserTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the organization and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param userPK the primary key of the user
	 */
	@Override
	public void removeUser(long pk, long userPK) {
		organizationToUserTableMapper.deleteTableMapping(pk, userPK);
	}

	/**
	 * Removes the association between the organization and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param user the user
	 */
	@Override
	public void removeUser(long pk, com.liferay.portal.kernel.model.User user) {
		organizationToUserTableMapper.deleteTableMapping(
			pk, user.getPrimaryKey());
	}

	/**
	 * Removes the association between the organization and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param userPKs the primary keys of the users
	 */
	@Override
	public void removeUsers(long pk, long[] userPKs) {
		organizationToUserTableMapper.deleteTableMappings(pk, userPKs);
	}

	/**
	 * Removes the association between the organization and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param users the users
	 */
	@Override
	public void removeUsers(
		long pk, List<com.liferay.portal.kernel.model.User> users) {

		removeUsers(
			pk,
			ListUtil.toLongArray(
				users, com.liferay.portal.kernel.model.User.USER_ID_ACCESSOR));
	}

	/**
	 * Sets the users associated with the organization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param userPKs the primary keys of the users to be associated with the organization
	 */
	@Override
	public void setUsers(long pk, long[] userPKs) {
		Set<Long> newUserPKsSet = SetUtil.fromArray(userPKs);
		Set<Long> oldUserPKsSet = SetUtil.fromArray(
			organizationToUserTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeUserPKsSet = new HashSet<Long>(oldUserPKsSet);

		removeUserPKsSet.removeAll(newUserPKsSet);

		organizationToUserTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeUserPKsSet));

		newUserPKsSet.removeAll(oldUserPKsSet);

		long companyId = 0;

		Organization organization = fetchByPrimaryKey(pk);

		if (organization == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = organization.getCompanyId();
		}

		organizationToUserTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newUserPKsSet));
	}

	/**
	 * Sets the users associated with the organization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param users the users to be associated with the organization
	 */
	@Override
	public void setUsers(
		long pk, List<com.liferay.portal.kernel.model.User> users) {

		try {
			long[] userPKs = new long[users.size()];

			for (int i = 0; i < users.size(); i++) {
				com.liferay.portal.kernel.model.User user = users.get(i);

				userPKs[i] = user.getPrimaryKey();
			}

			setUsers(pk, userPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "organizationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ORGANIZATION;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.get(ctColumnResolutionType);
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return OrganizationModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "Organization_";
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
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("externalReferenceCode");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("parentOrganizationId");
		ctStrictColumnNames.add("treePath");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("recursable");
		ctStrictColumnNames.add("regionId");
		ctStrictColumnNames.add("countryId");
		ctStrictColumnNames.add("statusId");
		ctStrictColumnNames.add("comments");
		ctStrictColumnNames.add("logoId");
		ctStrictColumnNames.add("groups_");
		ctStrictColumnNames.add("users");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("organizationId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_mappingTableNames.add("Groups_Orgs");
		_mappingTableNames.add("Users_Orgs");

		_uniqueIndexColumnNames.add(new String[] {"companyId", "name"});
	}

	/**
	 * Initializes the organization persistence.
	 */
	public void afterPropertiesSet() {
		organizationToGroupTableMapper = TableMapperFactory.getTableMapper(
			"Groups_Orgs", "companyId", "organizationId", "groupId", this,
			groupPersistence);

		organizationToUserTableMapper = TableMapperFactory.getTableMapper(
			"Users_Orgs", "companyId", "organizationId", "userId", this,
			userPersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			OrganizationModelImpl.UUID_COLUMN_BITMASK |
			OrganizationModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			OrganizationModelImpl.UUID_COLUMN_BITMASK |
			OrganizationModelImpl.COMPANYID_COLUMN_BITMASK |
			OrganizationModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] {Long.class.getName()},
			OrganizationModelImpl.COMPANYID_COLUMN_BITMASK |
			OrganizationModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByLocations = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLocations",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByLocations = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLocations", new String[] {Long.class.getName()},
			OrganizationModelImpl.COMPANYID_COLUMN_BITMASK |
			OrganizationModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByLocations = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLocations", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByC_P = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_P = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			OrganizationModelImpl.COMPANYID_COLUMN_BITMASK |
			OrganizationModelImpl.PARENTORGANIZATIONID_COLUMN_BITMASK |
			OrganizationModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByC_P = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_T = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByC_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_T",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByC_N = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			OrganizationModelImpl.COMPANYID_COLUMN_BITMASK |
			OrganizationModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByC_N = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByC_LikeN = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByC_LikeN = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByC_LikeN",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByO_C_P = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByO_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByO_C_P = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByO_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByC_P_LikeN = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_P_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByC_P_LikeN = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByC_P_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

		_finderPathFetchByC_ERC = new FinderPath(
			OrganizationImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			OrganizationModelImpl.COMPANYID_COLUMN_BITMASK |
			OrganizationModelImpl.EXTERNALREFERENCECODE_COLUMN_BITMASK);

		_finderPathCountByC_ERC = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(OrganizationImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("Groups_Orgs");
		TableMapperFactory.removeTableMapper("Users_Orgs");
	}

	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;

	protected TableMapper<Organization, com.liferay.portal.kernel.model.Group>
		organizationToGroupTableMapper;

	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;

	protected TableMapper<Organization, com.liferay.portal.kernel.model.User>
		organizationToUserTableMapper;

	private static final String _SQL_SELECT_ORGANIZATION =
		"SELECT organization FROM Organization organization";

	private static final String _SQL_SELECT_ORGANIZATION_WHERE =
		"SELECT organization FROM Organization organization WHERE ";

	private static final String _SQL_COUNT_ORGANIZATION =
		"SELECT COUNT(organization) FROM Organization organization";

	private static final String _SQL_COUNT_ORGANIZATION_WHERE =
		"SELECT COUNT(organization) FROM Organization organization WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"organization.organizationId";

	private static final String _FILTER_SQL_SELECT_ORGANIZATION_WHERE =
		"SELECT DISTINCT {organization.*} FROM Organization_ organization WHERE ";

	private static final String
		_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {Organization_.*} FROM (SELECT DISTINCT organization.organizationId FROM Organization_ organization WHERE ";

	private static final String
		_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN Organization_ ON TEMP_TABLE.organizationId = Organization_.organizationId";

	private static final String _FILTER_SQL_COUNT_ORGANIZATION_WHERE =
		"SELECT COUNT(DISTINCT organization.organizationId) AS COUNT_VALUE FROM Organization_ organization WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "organization";

	private static final String _FILTER_ENTITY_TABLE = "Organization_";

	private static final String _ORDER_BY_ENTITY_ALIAS = "organization.";

	private static final String _ORDER_BY_ENTITY_TABLE = "Organization_.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Organization exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Organization exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type", "groups"});

}