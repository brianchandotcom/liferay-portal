/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.exception.DuplicateCPConfigurationListExternalReferenceCodeException;
import com.liferay.commerce.product.exception.NoSuchCPConfigurationListException;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CPConfigurationListTable;
import com.liferay.commerce.product.model.impl.CPConfigurationListImpl;
import com.liferay.commerce.product.model.impl.CPConfigurationListModelImpl;
import com.liferay.commerce.product.service.persistence.CPConfigurationListPersistence;
import com.liferay.commerce.product.service.persistence.CPConfigurationListUtil;
import com.liferay.commerce.product.service.persistence.impl.constants.CommercePersistenceConstants;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

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
 * The persistence implementation for the cp configuration list service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = CPConfigurationListPersistence.class)
public class CPConfigurationListPersistenceImpl
	extends BasePersistenceImpl<CPConfigurationList>
	implements CPConfigurationListPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPConfigurationListUtil</code> to access the cp configuration list persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPConfigurationListImpl.class.getName();

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
	 * Returns all the cp configuration lists where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

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

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationList cpConfigurationList : list) {
						if (!uuid.equals(cpConfigurationList.getUuid())) {
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

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

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
					sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
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

					list = (List<CPConfigurationList>)QueryUtil.list(
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
	}

	/**
	 * Returns the first cp configuration list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByUuid_First(
			String uuid,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByUuid_First(
			uuid, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the first cp configuration list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByUuid_First(
		String uuid, OrderByComparator<CPConfigurationList> orderByComparator) {

		List<CPConfigurationList> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp configuration list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByUuid_Last(
			String uuid,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByUuid_Last(
			uuid, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the last cp configuration list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByUuid_Last(
		String uuid, OrderByComparator<CPConfigurationList> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPConfigurationList> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp configuration lists before and after the current cp configuration list in the ordered set where uuid = &#63;.
	 *
	 * @param CPConfigurationListId the primary key of the current cp configuration list
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	@Override
	public CPConfigurationList[] findByUuid_PrevAndNext(
			long CPConfigurationListId, String uuid,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		uuid = Objects.toString(uuid, "");

		CPConfigurationList cpConfigurationList = findByPrimaryKey(
			CPConfigurationListId);

		Session session = null;

		try {
			session = openSession();

			CPConfigurationList[] array = new CPConfigurationListImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, cpConfigurationList, uuid, orderByComparator, true);

			array[1] = cpConfigurationList;

			array[2] = getByUuid_PrevAndNext(
				session, cpConfigurationList, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPConfigurationList getByUuid_PrevAndNext(
		Session session, CPConfigurationList cpConfigurationList, String uuid,
		OrderByComparator<CPConfigurationList> orderByComparator,
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

		sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

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
			sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
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
						cpConfigurationList)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPConfigurationList> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp configuration lists where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPConfigurationList cpConfigurationList :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpConfigurationList);
		}
	}

	/**
	 * Returns the number of cp configuration lists where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			uuid = Objects.toString(uuid, "");

			FinderPath finderPath = _finderPathCountByUuid;

			Object[] finderArgs = new Object[] {uuid};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_CPCONFIGURATIONLIST_WHERE);

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
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"cpConfigurationList.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(cpConfigurationList.uuid IS NULL OR cpConfigurationList.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;

	/**
	 * Returns the cp configuration list where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPConfigurationListException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByUUID_G(String uuid, long groupId)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByUUID_G(uuid, groupId);

		if (cpConfigurationList == null) {
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

			throw new NoSuchCPConfigurationListException(sb.toString());
		}

		return cpConfigurationList;
	}

	/**
	 * Returns the cp configuration list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp configuration list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			uuid = Objects.toString(uuid, "");

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {uuid, groupId};
			}

			Object result = null;

			if (useFinderCache) {
				result = finderCache.getResult(
					_finderPathFetchByUUID_G, finderArgs, this);
			}

			if (result instanceof CPConfigurationList) {
				CPConfigurationList cpConfigurationList =
					(CPConfigurationList)result;

				if (!Objects.equals(uuid, cpConfigurationList.getUuid()) ||
					(groupId != cpConfigurationList.getGroupId())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

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

					List<CPConfigurationList> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							finderCache.putResult(
								_finderPathFetchByUUID_G, finderArgs, list);
						}
					}
					else {
						CPConfigurationList cpConfigurationList = list.get(0);

						result = cpConfigurationList;

						cacheResult(cpConfigurationList);
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
				return (CPConfigurationList)result;
			}
		}
	}

	/**
	 * Removes the cp configuration list where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp configuration list that was removed
	 */
	@Override
	public CPConfigurationList removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = findByUUID_G(uuid, groupId);

		return remove(cpConfigurationList);
	}

	/**
	 * Returns the number of cp configuration lists where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		CPConfigurationList cpConfigurationList = fetchByUUID_G(uuid, groupId);

		if (cpConfigurationList == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"cpConfigurationList.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(cpConfigurationList.uuid IS NULL OR cpConfigurationList.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"cpConfigurationList.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the cp configuration lists where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

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

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationList cpConfigurationList : list) {
						if (!uuid.equals(cpConfigurationList.getUuid()) ||
							(companyId != cpConfigurationList.getCompanyId())) {

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

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

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
					sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
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

					list = (List<CPConfigurationList>)QueryUtil.list(
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
	}

	/**
	 * Returns the first cp configuration list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the first cp configuration list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		List<CPConfigurationList> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp configuration list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the last cp configuration list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPConfigurationList> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp configuration lists before and after the current cp configuration list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPConfigurationListId the primary key of the current cp configuration list
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	@Override
	public CPConfigurationList[] findByUuid_C_PrevAndNext(
			long CPConfigurationListId, String uuid, long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		uuid = Objects.toString(uuid, "");

		CPConfigurationList cpConfigurationList = findByPrimaryKey(
			CPConfigurationListId);

		Session session = null;

		try {
			session = openSession();

			CPConfigurationList[] array = new CPConfigurationListImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, cpConfigurationList, uuid, companyId,
				orderByComparator, true);

			array[1] = cpConfigurationList;

			array[2] = getByUuid_C_PrevAndNext(
				session, cpConfigurationList, uuid, companyId,
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

	protected CPConfigurationList getByUuid_C_PrevAndNext(
		Session session, CPConfigurationList cpConfigurationList, String uuid,
		long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator,
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

		sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

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
			sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
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
						cpConfigurationList)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPConfigurationList> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp configuration lists where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPConfigurationList cpConfigurationList :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpConfigurationList);
		}
	}

	/**
	 * Returns the number of cp configuration lists where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			uuid = Objects.toString(uuid, "");

			FinderPath finderPath = _finderPathCountByUuid_C;

			Object[] finderArgs = new Object[] {uuid, companyId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_CPCONFIGURATIONLIST_WHERE);

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
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"cpConfigurationList.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(cpConfigurationList.uuid IS NULL OR cpConfigurationList.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"cpConfigurationList.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the cp configuration lists where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration lists where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByCompanyId;
					finderArgs = new Object[] {companyId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByCompanyId;
				finderArgs = new Object[] {
					companyId, start, end, orderByComparator
				};
			}

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationList cpConfigurationList : list) {
						if (companyId != cpConfigurationList.getCompanyId()) {
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

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					list = (List<CPConfigurationList>)QueryUtil.list(
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
	}

	/**
	 * Returns the first cp configuration list in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByCompanyId_First(
			long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the first cp configuration list in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		List<CPConfigurationList> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp configuration list in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByCompanyId_Last(
			long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the last cp configuration list in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CPConfigurationList> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp configuration lists before and after the current cp configuration list in the ordered set where companyId = &#63;.
	 *
	 * @param CPConfigurationListId the primary key of the current cp configuration list
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	@Override
	public CPConfigurationList[] findByCompanyId_PrevAndNext(
			long CPConfigurationListId, long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = findByPrimaryKey(
			CPConfigurationListId);

		Session session = null;

		try {
			session = openSession();

			CPConfigurationList[] array = new CPConfigurationListImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, cpConfigurationList, companyId, orderByComparator,
				true);

			array[1] = cpConfigurationList;

			array[2] = getByCompanyId_PrevAndNext(
				session, cpConfigurationList, companyId, orderByComparator,
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

	protected CPConfigurationList getByCompanyId_PrevAndNext(
		Session session, CPConfigurationList cpConfigurationList,
		long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator,
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

		sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

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
			sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpConfigurationList)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPConfigurationList> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp configuration lists where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CPConfigurationList cpConfigurationList :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpConfigurationList);
		}
	}

	/**
	 * Returns the number of cp configuration lists where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			FinderPath finderPath = _finderPathCountByCompanyId;

			Object[] finderArgs = new Object[] {companyId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_CPCONFIGURATIONLIST_WHERE);

				sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

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
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"cpConfigurationList.companyId = ?";

	private FinderPath _finderPathFetchByParentCPConfigurationListId;

	/**
	 * Returns the cp configuration list where parentCPConfigurationListId = &#63; or throws a <code>NoSuchCPConfigurationListException</code> if it could not be found.
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @return the matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByParentCPConfigurationListId(
			long parentCPConfigurationListId)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList =
			fetchByParentCPConfigurationListId(parentCPConfigurationListId);

		if (cpConfigurationList == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("parentCPConfigurationListId=");
			sb.append(parentCPConfigurationListId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPConfigurationListException(sb.toString());
		}

		return cpConfigurationList;
	}

	/**
	 * Returns the cp configuration list where parentCPConfigurationListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByParentCPConfigurationListId(
		long parentCPConfigurationListId) {

		return fetchByParentCPConfigurationListId(
			parentCPConfigurationListId, true);
	}

	/**
	 * Returns the cp configuration list where parentCPConfigurationListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByParentCPConfigurationListId(
		long parentCPConfigurationListId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {parentCPConfigurationListId};
			}

			Object result = null;

			if (useFinderCache) {
				result = finderCache.getResult(
					_finderPathFetchByParentCPConfigurationListId, finderArgs,
					this);
			}

			if (result instanceof CPConfigurationList) {
				CPConfigurationList cpConfigurationList =
					(CPConfigurationList)result;

				if (parentCPConfigurationListId !=
						cpConfigurationList.getParentCPConfigurationListId()) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				sb.append(
					_FINDER_COLUMN_PARENTCPCONFIGURATIONLISTID_PARENTCPCONFIGURATIONLISTID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(parentCPConfigurationListId);

					List<CPConfigurationList> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							finderCache.putResult(
								_finderPathFetchByParentCPConfigurationListId,
								finderArgs, list);
						}
					}
					else {
						if (list.size() > 1) {
							Collections.sort(list, Collections.reverseOrder());

							if (_log.isWarnEnabled()) {
								if (!useFinderCache) {
									finderArgs = new Object[] {
										parentCPConfigurationListId
									};
								}

								_log.warn(
									"CPConfigurationListPersistenceImpl.fetchByParentCPConfigurationListId(long, boolean) with parameters (" +
										StringUtil.merge(finderArgs) +
											") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
							}
						}

						CPConfigurationList cpConfigurationList = list.get(0);

						result = cpConfigurationList;

						cacheResult(cpConfigurationList);
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
				return (CPConfigurationList)result;
			}
		}
	}

	/**
	 * Removes the cp configuration list where parentCPConfigurationListId = &#63; from the database.
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @return the cp configuration list that was removed
	 */
	@Override
	public CPConfigurationList removeByParentCPConfigurationListId(
			long parentCPConfigurationListId)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList =
			findByParentCPConfigurationListId(parentCPConfigurationListId);

		return remove(cpConfigurationList);
	}

	/**
	 * Returns the number of cp configuration lists where parentCPConfigurationListId = &#63;.
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByParentCPConfigurationListId(
		long parentCPConfigurationListId) {

		CPConfigurationList cpConfigurationList =
			fetchByParentCPConfigurationListId(parentCPConfigurationListId);

		if (cpConfigurationList == null) {
			return 0;
		}

		return 1;
	}

	private static final String
		_FINDER_COLUMN_PARENTCPCONFIGURATIONLISTID_PARENTCPCONFIGURATIONLISTID_2 =
			"cpConfigurationList.parentCPConfigurationListId = ?";

	private FinderPath _finderPathWithPaginationFindByG_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C;
	private FinderPath _finderPathCountByG_C;
	private FinderPath _finderPathWithPaginationCountByG_C;

	/**
	 * Returns all the cp configuration lists where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C(long groupId, long companyId) {
		return findByG_C(
			groupId, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C(
		long groupId, long companyId, int start, int end) {

		return findByG_C(groupId, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C(
		long groupId, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByG_C(
			groupId, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C(
		long groupId, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByG_C;
					finderArgs = new Object[] {groupId, companyId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByG_C;
				finderArgs = new Object[] {
					groupId, companyId, start, end, orderByComparator
				};
			}

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationList cpConfigurationList : list) {
						if ((groupId != cpConfigurationList.getGroupId()) ||
							(companyId != cpConfigurationList.getCompanyId())) {

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

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_C_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(companyId);

					list = (List<CPConfigurationList>)QueryUtil.list(
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
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByG_C_First(
			long groupId, long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByG_C_First(
			groupId, companyId, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByG_C_First(
		long groupId, long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		List<CPConfigurationList> list = findByG_C(
			groupId, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp configuration list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByG_C_Last(
			long groupId, long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByG_C_Last(
			groupId, companyId, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the last cp configuration list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByG_C_Last(
		long groupId, long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		int count = countByG_C(groupId, companyId);

		if (count == 0) {
			return null;
		}

		List<CPConfigurationList> list = findByG_C(
			groupId, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp configuration lists before and after the current cp configuration list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param CPConfigurationListId the primary key of the current cp configuration list
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	@Override
	public CPConfigurationList[] findByG_C_PrevAndNext(
			long CPConfigurationListId, long groupId, long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = findByPrimaryKey(
			CPConfigurationListId);

		Session session = null;

		try {
			session = openSession();

			CPConfigurationList[] array = new CPConfigurationListImpl[3];

			array[0] = getByG_C_PrevAndNext(
				session, cpConfigurationList, groupId, companyId,
				orderByComparator, true);

			array[1] = cpConfigurationList;

			array[2] = getByG_C_PrevAndNext(
				session, cpConfigurationList, groupId, companyId,
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

	protected CPConfigurationList getByG_C_PrevAndNext(
		Session session, CPConfigurationList cpConfigurationList, long groupId,
		long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator,
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

		sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

		sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_COMPANYID_2);

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
			sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpConfigurationList)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPConfigurationList> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the cp configuration lists where groupId = any &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C(
		long[] groupIds, long companyId) {

		return findByG_C(
			groupIds, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = any &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C(
		long[] groupIds, long companyId, int start, int end) {

		return findByG_C(groupIds, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = any &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C(
		long[] groupIds, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByG_C(
			groupIds, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C(
		long[] groupIds, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (groupIds.length == 1) {
			return findByG_C(
				groupIds[0], companyId, start, end, orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						StringUtil.merge(groupIds), companyId
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds), companyId, start, end,
					orderByComparator
				};
			}

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					_finderPathWithPaginationFindByG_C, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationList cpConfigurationList : list) {
						if (!ArrayUtil.contains(
								groupIds, cpConfigurationList.getGroupId()) ||
							(companyId != cpConfigurationList.getCompanyId())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				if (groupIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_C_GROUPID_7);

					sb.append(StringUtil.merge(groupIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_C_COMPANYID_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					list = (List<CPConfigurationList>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_C, finderArgs,
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
	}

	/**
	 * Removes all the cp configuration lists where groupId = &#63; and companyId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 */
	@Override
	public void removeByG_C(long groupId, long companyId) {
		for (CPConfigurationList cpConfigurationList :
				findByG_C(
					groupId, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpConfigurationList);
		}
	}

	/**
	 * Returns the number of cp configuration lists where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByG_C(long groupId, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			FinderPath finderPath = _finderPathCountByG_C;

			Object[] finderArgs = new Object[] {groupId, companyId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_CPCONFIGURATIONLIST_WHERE);

				sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_C_COMPANYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

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
	}

	/**
	 * Returns the number of cp configuration lists where groupId = any &#63; and companyId = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByG_C(long[] groupIds, long companyId) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			Object[] finderArgs = new Object[] {
				StringUtil.merge(groupIds), companyId
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_C, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_CPCONFIGURATIONLIST_WHERE);

				if (groupIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_C_GROUPID_7);

					sb.append(StringUtil.merge(groupIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_C_COMPANYID_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_C, finderArgs, count);
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
	}

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 =
		"cpConfigurationList.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_GROUPID_7 =
		"cpConfigurationList.groupId IN (";

	private static final String _FINDER_COLUMN_G_C_COMPANYID_2 =
		"cpConfigurationList.companyId = ?";

	private FinderPath _finderPathFetchByG_M;

	/**
	 * Returns the cp configuration list where groupId = &#63; and masterCPConfigurationList = &#63; or throws a <code>NoSuchCPConfigurationListException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param masterCPConfigurationList the master cp configuration list
	 * @return the matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByG_M(
			long groupId, boolean masterCPConfigurationList)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByG_M(
			groupId, masterCPConfigurationList);

		if (cpConfigurationList == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", masterCPConfigurationList=");
			sb.append(masterCPConfigurationList);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPConfigurationListException(sb.toString());
		}

		return cpConfigurationList;
	}

	/**
	 * Returns the cp configuration list where groupId = &#63; and masterCPConfigurationList = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param masterCPConfigurationList the master cp configuration list
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByG_M(
		long groupId, boolean masterCPConfigurationList) {

		return fetchByG_M(groupId, masterCPConfigurationList, true);
	}

	/**
	 * Returns the cp configuration list where groupId = &#63; and masterCPConfigurationList = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param masterCPConfigurationList the master cp configuration list
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByG_M(
		long groupId, boolean masterCPConfigurationList,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {groupId, masterCPConfigurationList};
			}

			Object result = null;

			if (useFinderCache) {
				result = finderCache.getResult(
					_finderPathFetchByG_M, finderArgs, this);
			}

			if (result instanceof CPConfigurationList) {
				CPConfigurationList cpConfigurationList =
					(CPConfigurationList)result;

				if ((groupId != cpConfigurationList.getGroupId()) ||
					(masterCPConfigurationList !=
						cpConfigurationList.isMasterCPConfigurationList())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				sb.append(_FINDER_COLUMN_G_M_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_M_MASTERCPCONFIGURATIONLIST_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(masterCPConfigurationList);

					List<CPConfigurationList> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							finderCache.putResult(
								_finderPathFetchByG_M, finderArgs, list);
						}
					}
					else {
						if (list.size() > 1) {
							Collections.sort(list, Collections.reverseOrder());

							if (_log.isWarnEnabled()) {
								if (!useFinderCache) {
									finderArgs = new Object[] {
										groupId, masterCPConfigurationList
									};
								}

								_log.warn(
									"CPConfigurationListPersistenceImpl.fetchByG_M(long, boolean, boolean) with parameters (" +
										StringUtil.merge(finderArgs) +
											") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
							}
						}

						CPConfigurationList cpConfigurationList = list.get(0);

						result = cpConfigurationList;

						cacheResult(cpConfigurationList);
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
				return (CPConfigurationList)result;
			}
		}
	}

	/**
	 * Removes the cp configuration list where groupId = &#63; and masterCPConfigurationList = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param masterCPConfigurationList the master cp configuration list
	 * @return the cp configuration list that was removed
	 */
	@Override
	public CPConfigurationList removeByG_M(
			long groupId, boolean masterCPConfigurationList)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = findByG_M(
			groupId, masterCPConfigurationList);

		return remove(cpConfigurationList);
	}

	/**
	 * Returns the number of cp configuration lists where groupId = &#63; and masterCPConfigurationList = &#63;.
	 *
	 * @param groupId the group ID
	 * @param masterCPConfigurationList the master cp configuration list
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByG_M(long groupId, boolean masterCPConfigurationList) {
		CPConfigurationList cpConfigurationList = fetchByG_M(
			groupId, masterCPConfigurationList);

		if (cpConfigurationList == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_G_M_GROUPID_2 =
		"cpConfigurationList.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_M_MASTERCPCONFIGURATIONLIST_2 =
		"cpConfigurationList.masterCPConfigurationList = ?";

	private FinderPath _finderPathWithPaginationFindByLtD_S;
	private FinderPath _finderPathWithPaginationCountByLtD_S;

	/**
	 * Returns all the cp configuration lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByLtD_S(Date displayDate, int status) {
		return findByLtD_S(
			displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return findByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByLtD_S(
			displayDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			finderPath = _finderPathWithPaginationFindByLtD_S;
			finderArgs = new Object[] {
				_getTime(displayDate), status, start, end, orderByComparator
			};

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationList cpConfigurationList : list) {
						if ((displayDate.getTime() <=
								cpConfigurationList.getDisplayDate(
								).getTime()) ||
							(status != cpConfigurationList.getStatus())) {

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

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				boolean bindDisplayDate = false;

				if (displayDate == null) {
					sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
				}
				else {
					bindDisplayDate = true;

					sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
				}

				sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					if (bindDisplayDate) {
						queryPos.add(new Timestamp(displayDate.getTime()));
					}

					queryPos.add(status);

					list = (List<CPConfigurationList>)QueryUtil.list(
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
	}

	/**
	 * Returns the first cp configuration list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByLtD_S_First(
			displayDate, status, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("displayDate<");
		sb.append(displayDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the first cp configuration list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		List<CPConfigurationList> list = findByLtD_S(
			displayDate, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp configuration list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByLtD_S_Last(
			Date displayDate, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByLtD_S_Last(
			displayDate, status, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("displayDate<");
		sb.append(displayDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the last cp configuration list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByLtD_S_Last(
		Date displayDate, int status,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		int count = countByLtD_S(displayDate, status);

		if (count == 0) {
			return null;
		}

		List<CPConfigurationList> list = findByLtD_S(
			displayDate, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp configuration lists before and after the current cp configuration list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param CPConfigurationListId the primary key of the current cp configuration list
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	@Override
	public CPConfigurationList[] findByLtD_S_PrevAndNext(
			long CPConfigurationListId, Date displayDate, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = findByPrimaryKey(
			CPConfigurationListId);

		Session session = null;

		try {
			session = openSession();

			CPConfigurationList[] array = new CPConfigurationListImpl[3];

			array[0] = getByLtD_S_PrevAndNext(
				session, cpConfigurationList, displayDate, status,
				orderByComparator, true);

			array[1] = cpConfigurationList;

			array[2] = getByLtD_S_PrevAndNext(
				session, cpConfigurationList, displayDate, status,
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

	protected CPConfigurationList getByLtD_S_PrevAndNext(
		Session session, CPConfigurationList cpConfigurationList,
		Date displayDate, int status,
		OrderByComparator<CPConfigurationList> orderByComparator,
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

		sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
		}

		sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

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
			sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindDisplayDate) {
			queryPos.add(new Timestamp(displayDate.getTime()));
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpConfigurationList)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPConfigurationList> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp configuration lists where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByLtD_S(Date displayDate, int status) {
		for (CPConfigurationList cpConfigurationList :
				findByLtD_S(
					displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpConfigurationList);
		}
	}

	/**
	 * Returns the number of cp configuration lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByLtD_S(Date displayDate, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			FinderPath finderPath = _finderPathWithPaginationCountByLtD_S;

			Object[] finderArgs = new Object[] {_getTime(displayDate), status};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_CPCONFIGURATIONLIST_WHERE);

				boolean bindDisplayDate = false;

				if (displayDate == null) {
					sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
				}
				else {
					bindDisplayDate = true;

					sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
				}

				sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					if (bindDisplayDate) {
						queryPos.add(new Timestamp(displayDate.getTime()));
					}

					queryPos.add(status);

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
	}

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_1 =
		"cpConfigurationList.displayDate IS NULL AND ";

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_2 =
		"cpConfigurationList.displayDate < ? AND ";

	private static final String _FINDER_COLUMN_LTD_S_STATUS_2 =
		"cpConfigurationList.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_S;
	private FinderPath _finderPathWithoutPaginationFindByG_C_S;
	private FinderPath _finderPathCountByG_C_S;
	private FinderPath _finderPathWithPaginationCountByG_C_S;

	/**
	 * Returns all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_S(
		long groupId, long companyId, int status) {

		return findByG_C_S(
			groupId, companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_S(
		long groupId, long companyId, int status, int start, int end) {

		return findByG_C_S(groupId, companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_S(
		long groupId, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByG_C_S(
			groupId, companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_S(
		long groupId, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByG_C_S;
					finderArgs = new Object[] {groupId, companyId, status};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByG_C_S;
				finderArgs = new Object[] {
					groupId, companyId, status, start, end, orderByComparator
				};
			}

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationList cpConfigurationList : list) {
						if ((groupId != cpConfigurationList.getGroupId()) ||
							(companyId != cpConfigurationList.getCompanyId()) ||
							(status != cpConfigurationList.getStatus())) {

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

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_C_S_COMPANYID_2);

				sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(companyId);

					queryPos.add(status);

					list = (List<CPConfigurationList>)QueryUtil.list(
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
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByG_C_S_First(
			long groupId, long companyId, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByG_C_S_First(
			groupId, companyId, status, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByG_C_S_First(
		long groupId, long companyId, int status,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		List<CPConfigurationList> list = findByG_C_S(
			groupId, companyId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByG_C_S_Last(
			long groupId, long companyId, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByG_C_S_Last(
			groupId, companyId, status, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the last cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByG_C_S_Last(
		long groupId, long companyId, int status,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		int count = countByG_C_S(groupId, companyId, status);

		if (count == 0) {
			return null;
		}

		List<CPConfigurationList> list = findByG_C_S(
			groupId, companyId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp configuration lists before and after the current cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param CPConfigurationListId the primary key of the current cp configuration list
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	@Override
	public CPConfigurationList[] findByG_C_S_PrevAndNext(
			long CPConfigurationListId, long groupId, long companyId,
			int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = findByPrimaryKey(
			CPConfigurationListId);

		Session session = null;

		try {
			session = openSession();

			CPConfigurationList[] array = new CPConfigurationListImpl[3];

			array[0] = getByG_C_S_PrevAndNext(
				session, cpConfigurationList, groupId, companyId, status,
				orderByComparator, true);

			array[1] = cpConfigurationList;

			array[2] = getByG_C_S_PrevAndNext(
				session, cpConfigurationList, groupId, companyId, status,
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

	protected CPConfigurationList getByG_C_S_PrevAndNext(
		Session session, CPConfigurationList cpConfigurationList, long groupId,
		long companyId, int status,
		OrderByComparator<CPConfigurationList> orderByComparator,
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

		sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

		sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

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
			sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(companyId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpConfigurationList)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPConfigurationList> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_S(
		long[] groupIds, long companyId, int status) {

		return findByG_C_S(
			groupIds, companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end) {

		return findByG_C_S(groupIds, companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByG_C_S(
			groupIds, companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (groupIds.length == 1) {
			return findByG_C_S(
				groupIds[0], companyId, status, start, end, orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						StringUtil.merge(groupIds), companyId, status
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds), companyId, status, start, end,
					orderByComparator
				};
			}

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					_finderPathWithPaginationFindByG_C_S, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationList cpConfigurationList : list) {
						if (!ArrayUtil.contains(
								groupIds, cpConfigurationList.getGroupId()) ||
							(companyId != cpConfigurationList.getCompanyId()) ||
							(status != cpConfigurationList.getStatus())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				if (groupIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_C_S_GROUPID_7);

					sb.append(StringUtil.merge(groupIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_C_S_COMPANYID_2);

				sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					queryPos.add(status);

					list = (List<CPConfigurationList>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_C_S, finderArgs,
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
	}

	/**
	 * Removes all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByG_C_S(long groupId, long companyId, int status) {
		for (CPConfigurationList cpConfigurationList :
				findByG_C_S(
					groupId, companyId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpConfigurationList);
		}
	}

	/**
	 * Returns the number of cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByG_C_S(long groupId, long companyId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			FinderPath finderPath = _finderPathCountByG_C_S;

			Object[] finderArgs = new Object[] {groupId, companyId, status};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_COUNT_CPCONFIGURATIONLIST_WHERE);

				sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_C_S_COMPANYID_2);

				sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(companyId);

					queryPos.add(status);

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
	}

	/**
	 * Returns the number of cp configuration lists where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByG_C_S(long[] groupIds, long companyId, int status) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			Object[] finderArgs = new Object[] {
				StringUtil.merge(groupIds), companyId, status
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_C_S, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_CPCONFIGURATIONLIST_WHERE);

				if (groupIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_C_S_GROUPID_7);

					sb.append(StringUtil.merge(groupIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_C_S_COMPANYID_2);

				sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					queryPos.add(status);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_C_S, finderArgs,
						count);
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
	}

	private static final String _FINDER_COLUMN_G_C_S_GROUPID_2 =
		"cpConfigurationList.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_S_GROUPID_7 =
		"cpConfigurationList.groupId IN (";

	private static final String _FINDER_COLUMN_G_C_S_COMPANYID_2 =
		"cpConfigurationList.companyId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_S_STATUS_2 =
		"cpConfigurationList.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_NotS;
	private FinderPath _finderPathWithPaginationCountByG_C_NotS;

	/**
	 * Returns all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_NotS(
		long groupId, long companyId, int status) {

		return findByG_C_NotS(
			groupId, companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_NotS(
		long groupId, long companyId, int status, int start, int end) {

		return findByG_C_NotS(groupId, companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_NotS(
		long groupId, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByG_C_NotS(
			groupId, companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_NotS(
		long groupId, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			finderPath = _finderPathWithPaginationFindByG_C_NotS;
			finderArgs = new Object[] {
				groupId, companyId, status, start, end, orderByComparator
			};

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationList cpConfigurationList : list) {
						if ((groupId != cpConfigurationList.getGroupId()) ||
							(companyId != cpConfigurationList.getCompanyId()) ||
							(status == cpConfigurationList.getStatus())) {

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

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_C_NOTS_COMPANYID_2);

				sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(companyId);

					queryPos.add(status);

					list = (List<CPConfigurationList>)QueryUtil.list(
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
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByG_C_NotS_First(
			long groupId, long companyId, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByG_C_NotS_First(
			groupId, companyId, status, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", status!=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByG_C_NotS_First(
		long groupId, long companyId, int status,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		List<CPConfigurationList> list = findByG_C_NotS(
			groupId, companyId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByG_C_NotS_Last(
			long groupId, long companyId, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByG_C_NotS_Last(
			groupId, companyId, status, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", status!=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the last cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByG_C_NotS_Last(
		long groupId, long companyId, int status,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		int count = countByG_C_NotS(groupId, companyId, status);

		if (count == 0) {
			return null;
		}

		List<CPConfigurationList> list = findByG_C_NotS(
			groupId, companyId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp configuration lists before and after the current cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param CPConfigurationListId the primary key of the current cp configuration list
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	@Override
	public CPConfigurationList[] findByG_C_NotS_PrevAndNext(
			long CPConfigurationListId, long groupId, long companyId,
			int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = findByPrimaryKey(
			CPConfigurationListId);

		Session session = null;

		try {
			session = openSession();

			CPConfigurationList[] array = new CPConfigurationListImpl[3];

			array[0] = getByG_C_NotS_PrevAndNext(
				session, cpConfigurationList, groupId, companyId, status,
				orderByComparator, true);

			array[1] = cpConfigurationList;

			array[2] = getByG_C_NotS_PrevAndNext(
				session, cpConfigurationList, groupId, companyId, status,
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

	protected CPConfigurationList getByG_C_NotS_PrevAndNext(
		Session session, CPConfigurationList cpConfigurationList, long groupId,
		long companyId, int status,
		OrderByComparator<CPConfigurationList> orderByComparator,
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

		sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

		sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_NOTS_COMPANYID_2);

		sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

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
			sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(companyId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpConfigurationList)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPConfigurationList> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_NotS(
		long[] groupIds, long companyId, int status) {

		return findByG_C_NotS(
			groupIds, companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end) {

		return findByG_C_NotS(groupIds, companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByG_C_NotS(
			groupIds, companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (groupIds.length == 1) {
			return findByG_C_NotS(
				groupIds[0], companyId, status, start, end, orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						StringUtil.merge(groupIds), companyId, status
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds), companyId, status, start, end,
					orderByComparator
				};
			}

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					_finderPathWithPaginationFindByG_C_NotS, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationList cpConfigurationList : list) {
						if (!ArrayUtil.contains(
								groupIds, cpConfigurationList.getGroupId()) ||
							(companyId != cpConfigurationList.getCompanyId()) ||
							(status == cpConfigurationList.getStatus())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				if (groupIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_7);

					sb.append(StringUtil.merge(groupIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_C_NOTS_COMPANYID_2);

				sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					queryPos.add(status);

					list = (List<CPConfigurationList>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_C_NotS, finderArgs,
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
	}

	/**
	 * Removes all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByG_C_NotS(long groupId, long companyId, int status) {
		for (CPConfigurationList cpConfigurationList :
				findByG_C_NotS(
					groupId, companyId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpConfigurationList);
		}
	}

	/**
	 * Returns the number of cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByG_C_NotS(long groupId, long companyId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			FinderPath finderPath = _finderPathWithPaginationCountByG_C_NotS;

			Object[] finderArgs = new Object[] {groupId, companyId, status};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_COUNT_CPCONFIGURATIONLIST_WHERE);

				sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_C_NOTS_COMPANYID_2);

				sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(companyId);

					queryPos.add(status);

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
	}

	/**
	 * Returns the number of cp configuration lists where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByG_C_NotS(long[] groupIds, long companyId, int status) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			Object[] finderArgs = new Object[] {
				StringUtil.merge(groupIds), companyId, status
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_C_NotS, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_CPCONFIGURATIONLIST_WHERE);

				if (groupIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_7);

					sb.append(StringUtil.merge(groupIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_C_NOTS_COMPANYID_2);

				sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					queryPos.add(status);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_C_NotS, finderArgs,
						count);
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
	}

	private static final String _FINDER_COLUMN_G_C_NOTS_GROUPID_2 =
		"cpConfigurationList.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_NOTS_GROUPID_7 =
		"cpConfigurationList.groupId IN (";

	private static final String _FINDER_COLUMN_G_C_NOTS_COMPANYID_2 =
		"cpConfigurationList.companyId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_NOTS_STATUS_2 =
		"cpConfigurationList.status != ?";

	private FinderPath _finderPathFetchByERC_C;

	/**
	 * Returns the cp configuration list where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchCPConfigurationListException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByERC_C(
			externalReferenceCode, companyId);

		if (cpConfigurationList == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append(", companyId=");
			sb.append(companyId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPConfigurationListException(sb.toString());
		}

		return cpConfigurationList;
	}

	/**
	 * Returns the cp configuration list where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the cp configuration list where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			externalReferenceCode = Objects.toString(externalReferenceCode, "");

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {externalReferenceCode, companyId};
			}

			Object result = null;

			if (useFinderCache) {
				result = finderCache.getResult(
					_finderPathFetchByERC_C, finderArgs, this);
			}

			if (result instanceof CPConfigurationList) {
				CPConfigurationList cpConfigurationList =
					(CPConfigurationList)result;

				if (!Objects.equals(
						externalReferenceCode,
						cpConfigurationList.getExternalReferenceCode()) ||
					(companyId != cpConfigurationList.getCompanyId())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				boolean bindExternalReferenceCode = false;

				if (externalReferenceCode.isEmpty()) {
					sb.append(_FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_3);
				}
				else {
					bindExternalReferenceCode = true;

					sb.append(_FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_2);
				}

				sb.append(_FINDER_COLUMN_ERC_C_COMPANYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					if (bindExternalReferenceCode) {
						queryPos.add(externalReferenceCode);
					}

					queryPos.add(companyId);

					List<CPConfigurationList> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							finderCache.putResult(
								_finderPathFetchByERC_C, finderArgs, list);
						}
					}
					else {
						CPConfigurationList cpConfigurationList = list.get(0);

						result = cpConfigurationList;

						cacheResult(cpConfigurationList);
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
				return (CPConfigurationList)result;
			}
		}
	}

	/**
	 * Removes the cp configuration list where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the cp configuration list that was removed
	 */
	@Override
	public CPConfigurationList removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = findByERC_C(
			externalReferenceCode, companyId);

		return remove(cpConfigurationList);
	}

	/**
	 * Returns the number of cp configuration lists where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		CPConfigurationList cpConfigurationList = fetchByERC_C(
			externalReferenceCode, companyId);

		if (cpConfigurationList == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_2 =
		"cpConfigurationList.externalReferenceCode = ? AND ";

	private static final String _FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_3 =
		"(cpConfigurationList.externalReferenceCode IS NULL OR cpConfigurationList.externalReferenceCode = '') AND ";

	private static final String _FINDER_COLUMN_ERC_C_COMPANYID_2 =
		"cpConfigurationList.companyId = ?";

	public CPConfigurationListPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPConfigurationList.class);

		setModelImplClass(CPConfigurationListImpl.class);
		setModelPKClass(long.class);

		setTable(CPConfigurationListTable.INSTANCE);
	}

	/**
	 * Caches the cp configuration list in the entity cache if it is enabled.
	 *
	 * @param cpConfigurationList the cp configuration list
	 */
	@Override
	public void cacheResult(CPConfigurationList cpConfigurationList) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					cpConfigurationList.getCtCollectionId())) {

			entityCache.putResult(
				CPConfigurationListImpl.class,
				cpConfigurationList.getPrimaryKey(), cpConfigurationList);

			finderCache.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {
					cpConfigurationList.getUuid(),
					cpConfigurationList.getGroupId()
				},
				cpConfigurationList);

			finderCache.putResult(
				_finderPathFetchByParentCPConfigurationListId,
				new Object[] {
					cpConfigurationList.getParentCPConfigurationListId()
				},
				cpConfigurationList);

			finderCache.putResult(
				_finderPathFetchByG_M,
				new Object[] {
					cpConfigurationList.getGroupId(),
					cpConfigurationList.isMasterCPConfigurationList()
				},
				cpConfigurationList);

			finderCache.putResult(
				_finderPathFetchByERC_C,
				new Object[] {
					cpConfigurationList.getExternalReferenceCode(),
					cpConfigurationList.getCompanyId()
				},
				cpConfigurationList);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cp configuration lists in the entity cache if it is enabled.
	 *
	 * @param cpConfigurationLists the cp configuration lists
	 */
	@Override
	public void cacheResult(List<CPConfigurationList> cpConfigurationLists) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpConfigurationLists.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPConfigurationList cpConfigurationList : cpConfigurationLists) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						cpConfigurationList.getCtCollectionId())) {

				if (entityCache.getResult(
						CPConfigurationListImpl.class,
						cpConfigurationList.getPrimaryKey()) == null) {

					cacheResult(cpConfigurationList);
				}
			}
		}
	}

	/**
	 * Clears the cache for all cp configuration lists.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPConfigurationListImpl.class);

		finderCache.clearCache(CPConfigurationListImpl.class);
	}

	/**
	 * Clears the cache for the cp configuration list.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPConfigurationList cpConfigurationList) {
		entityCache.removeResult(
			CPConfigurationListImpl.class, cpConfigurationList);
	}

	@Override
	public void clearCache(List<CPConfigurationList> cpConfigurationLists) {
		for (CPConfigurationList cpConfigurationList : cpConfigurationLists) {
			entityCache.removeResult(
				CPConfigurationListImpl.class, cpConfigurationList);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPConfigurationListImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CPConfigurationListImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPConfigurationListModelImpl cpConfigurationListModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					cpConfigurationListModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				cpConfigurationListModelImpl.getUuid(),
				cpConfigurationListModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G, args, cpConfigurationListModelImpl);

			args = new Object[] {
				cpConfigurationListModelImpl.getParentCPConfigurationListId()
			};

			finderCache.putResult(
				_finderPathFetchByParentCPConfigurationListId, args,
				cpConfigurationListModelImpl);

			args = new Object[] {
				cpConfigurationListModelImpl.getGroupId(),
				cpConfigurationListModelImpl.isMasterCPConfigurationList()
			};

			finderCache.putResult(
				_finderPathFetchByG_M, args, cpConfigurationListModelImpl);

			args = new Object[] {
				cpConfigurationListModelImpl.getExternalReferenceCode(),
				cpConfigurationListModelImpl.getCompanyId()
			};

			finderCache.putResult(
				_finderPathFetchByERC_C, args, cpConfigurationListModelImpl);
		}
	}

	/**
	 * Creates a new cp configuration list with the primary key. Does not add the cp configuration list to the database.
	 *
	 * @param CPConfigurationListId the primary key for the new cp configuration list
	 * @return the new cp configuration list
	 */
	@Override
	public CPConfigurationList create(long CPConfigurationListId) {
		CPConfigurationList cpConfigurationList = new CPConfigurationListImpl();

		cpConfigurationList.setNew(true);
		cpConfigurationList.setPrimaryKey(CPConfigurationListId);

		String uuid = PortalUUIDUtil.generate();

		cpConfigurationList.setUuid(uuid);

		cpConfigurationList.setCompanyId(CompanyThreadLocal.getCompanyId());

		return cpConfigurationList;
	}

	/**
	 * Removes the cp configuration list with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPConfigurationListId the primary key of the cp configuration list
	 * @return the cp configuration list that was removed
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	@Override
	public CPConfigurationList remove(long CPConfigurationListId)
		throws NoSuchCPConfigurationListException {

		return remove((Serializable)CPConfigurationListId);
	}

	/**
	 * Removes the cp configuration list with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp configuration list
	 * @return the cp configuration list that was removed
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	@Override
	public CPConfigurationList remove(Serializable primaryKey)
		throws NoSuchCPConfigurationListException {

		Session session = null;

		try {
			session = openSession();

			CPConfigurationList cpConfigurationList =
				(CPConfigurationList)session.get(
					CPConfigurationListImpl.class, primaryKey);

			if (cpConfigurationList == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPConfigurationListException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpConfigurationList);
		}
		catch (NoSuchCPConfigurationListException noSuchEntityException) {
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
	protected CPConfigurationList removeImpl(
		CPConfigurationList cpConfigurationList) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpConfigurationList)) {
				cpConfigurationList = (CPConfigurationList)session.get(
					CPConfigurationListImpl.class,
					cpConfigurationList.getPrimaryKeyObj());
			}

			if ((cpConfigurationList != null) &&
				ctPersistenceHelper.isRemove(cpConfigurationList)) {

				session.delete(cpConfigurationList);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpConfigurationList != null) {
			clearCache(cpConfigurationList);
		}

		return cpConfigurationList;
	}

	@Override
	public CPConfigurationList updateImpl(
		CPConfigurationList cpConfigurationList) {

		boolean isNew = cpConfigurationList.isNew();

		if (!(cpConfigurationList instanceof CPConfigurationListModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpConfigurationList.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpConfigurationList);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpConfigurationList proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPConfigurationList implementation " +
					cpConfigurationList.getClass());
		}

		CPConfigurationListModelImpl cpConfigurationListModelImpl =
			(CPConfigurationListModelImpl)cpConfigurationList;

		if (Validator.isNull(cpConfigurationList.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpConfigurationList.setUuid(uuid);
		}

		if (Validator.isNull(cpConfigurationList.getExternalReferenceCode())) {
			cpConfigurationList.setExternalReferenceCode(
				cpConfigurationList.getUuid());
		}
		else {
			if (!Objects.equals(
					cpConfigurationListModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					cpConfigurationList.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = cpConfigurationList.getCompanyId();

					long groupId = cpConfigurationList.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = cpConfigurationList.getPrimaryKey();
					}

					try {
						cpConfigurationList.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								CPConfigurationList.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								cpConfigurationList.getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			CPConfigurationList ercCPConfigurationList = fetchByERC_C(
				cpConfigurationList.getExternalReferenceCode(),
				cpConfigurationList.getCompanyId());

			if (isNew) {
				if (ercCPConfigurationList != null) {
					throw new DuplicateCPConfigurationListExternalReferenceCodeException(
						"Duplicate cp configuration list with external reference code " +
							cpConfigurationList.getExternalReferenceCode() +
								" and company " +
									cpConfigurationList.getCompanyId());
				}
			}
			else {
				if ((ercCPConfigurationList != null) &&
					(cpConfigurationList.getCPConfigurationListId() !=
						ercCPConfigurationList.getCPConfigurationListId())) {

					throw new DuplicateCPConfigurationListExternalReferenceCodeException(
						"Duplicate cp configuration list with external reference code " +
							cpConfigurationList.getExternalReferenceCode() +
								" and company " +
									cpConfigurationList.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpConfigurationList.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpConfigurationList.setCreateDate(date);
			}
			else {
				cpConfigurationList.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpConfigurationListModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpConfigurationList.setModifiedDate(date);
			}
			else {
				cpConfigurationList.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(cpConfigurationList)) {
				if (!isNew) {
					session.evict(
						CPConfigurationListImpl.class,
						cpConfigurationList.getPrimaryKeyObj());
				}

				session.save(cpConfigurationList);
			}
			else {
				cpConfigurationList = (CPConfigurationList)session.merge(
					cpConfigurationList);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPConfigurationListImpl.class, cpConfigurationListModelImpl, false,
			true);

		cacheUniqueFindersCache(cpConfigurationListModelImpl);

		if (isNew) {
			cpConfigurationList.setNew(false);
		}

		cpConfigurationList.resetOriginalValues();

		return cpConfigurationList;
	}

	/**
	 * Returns the cp configuration list with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp configuration list
	 * @return the cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	@Override
	public CPConfigurationList findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByPrimaryKey(primaryKey);

		if (cpConfigurationList == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPConfigurationListException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpConfigurationList;
	}

	/**
	 * Returns the cp configuration list with the primary key or throws a <code>NoSuchCPConfigurationListException</code> if it could not be found.
	 *
	 * @param CPConfigurationListId the primary key of the cp configuration list
	 * @return the cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	@Override
	public CPConfigurationList findByPrimaryKey(long CPConfigurationListId)
		throws NoSuchCPConfigurationListException {

		return findByPrimaryKey((Serializable)CPConfigurationListId);
	}

	/**
	 * Returns the cp configuration list with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp configuration list
	 * @return the cp configuration list, or <code>null</code> if a cp configuration list with the primary key could not be found
	 */
	@Override
	public CPConfigurationList fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				CPConfigurationList.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		CPConfigurationList cpConfigurationList =
			(CPConfigurationList)entityCache.getResult(
				CPConfigurationListImpl.class, primaryKey);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		Session session = null;

		try {
			session = openSession();

			cpConfigurationList = (CPConfigurationList)session.get(
				CPConfigurationListImpl.class, primaryKey);

			if (cpConfigurationList != null) {
				cacheResult(cpConfigurationList);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return cpConfigurationList;
	}

	/**
	 * Returns the cp configuration list with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPConfigurationListId the primary key of the cp configuration list
	 * @return the cp configuration list, or <code>null</code> if a cp configuration list with the primary key could not be found
	 */
	@Override
	public CPConfigurationList fetchByPrimaryKey(long CPConfigurationListId) {
		return fetchByPrimaryKey((Serializable)CPConfigurationListId);
	}

	@Override
	public Map<Serializable, CPConfigurationList> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(CPConfigurationList.class)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPConfigurationList> map =
			new HashMap<Serializable, CPConfigurationList>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPConfigurationList cpConfigurationList = fetchByPrimaryKey(
				primaryKey);

			if (cpConfigurationList != null) {
				map.put(primaryKey, cpConfigurationList);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						CPConfigurationList.class, primaryKey)) {

				CPConfigurationList cpConfigurationList =
					(CPConfigurationList)entityCache.getResult(
						CPConfigurationListImpl.class, primaryKey);

				if (cpConfigurationList == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, cpConfigurationList);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
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

			for (CPConfigurationList cpConfigurationList :
					(List<CPConfigurationList>)query.list()) {

				map.put(
					cpConfigurationList.getPrimaryKeyObj(),
					cpConfigurationList);

				cacheResult(cpConfigurationList);
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
	 * Returns all the cp configuration lists.
	 *
	 * @return the cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findAll(
		int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findAll(
		int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

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

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_CPCONFIGURATIONLIST);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_CPCONFIGURATIONLIST;

					sql = sql.concat(
						CPConfigurationListModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<CPConfigurationList>)QueryUtil.list(
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
	}

	/**
	 * Removes all the cp configuration lists from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPConfigurationList cpConfigurationList : findAll()) {
			remove(cpConfigurationList);
		}
	}

	/**
	 * Returns the number of cp configuration lists.
	 *
	 * @return the number of cp configuration lists
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_CPCONFIGURATIONLIST);

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
		return "CPConfigurationListId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPCONFIGURATIONLIST;
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
		return CPConfigurationListModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CPConfigurationList";
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
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctMergeColumnNames.add("parentCPConfigurationListId");
		ctMergeColumnNames.add("masterCPConfigurationList");
		ctMergeColumnNames.add("name");
		ctMergeColumnNames.add("priority");
		ctMergeColumnNames.add("displayDate");
		ctMergeColumnNames.add("expirationDate");
		ctMergeColumnNames.add("lastPublishDate");
		ctMergeColumnNames.add("status");
		ctMergeColumnNames.add("statusByUserId");
		ctMergeColumnNames.add("statusByUserName");
		ctMergeColumnNames.add("statusDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CPConfigurationListId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"externalReferenceCode", "companyId"});
	}

	/**
	 * Initializes the cp configuration list persistence.
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

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathFetchByParentCPConfigurationListId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByParentCPConfigurationListId",
			new String[] {Long.class.getName()},
			new String[] {"parentCPConfigurationListId"}, true);

		_finderPathWithPaginationFindByG_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "companyId"}, true);

		_finderPathWithoutPaginationFindByG_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "companyId"}, true);

		_finderPathCountByG_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "companyId"}, false);

		_finderPathWithPaginationCountByG_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "companyId"}, false);

		_finderPathFetchByG_M = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_M",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "masterCPConfigurationList"}, true);

		_finderPathWithPaginationFindByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtD_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"displayDate", "status"}, true);

		_finderPathWithPaginationCountByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtD_S",
			new String[] {Date.class.getName(), Integer.class.getName()},
			new String[] {"displayDate", "status"}, false);

		_finderPathWithPaginationFindByG_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "companyId", "status"}, true);

		_finderPathWithoutPaginationFindByG_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "companyId", "status"}, true);

		_finderPathCountByG_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "companyId", "status"}, false);

		_finderPathWithPaginationCountByG_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "companyId", "status"}, false);

		_finderPathWithPaginationFindByG_C_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "companyId", "status"}, true);

		_finderPathWithPaginationCountByG_C_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "companyId", "status"}, false);

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		CPConfigurationListUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CPConfigurationListUtil.setPersistence(null);

		entityCache.removeCache(CPConfigurationListImpl.class.getName());
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_CPCONFIGURATIONLIST =
		"SELECT cpConfigurationList FROM CPConfigurationList cpConfigurationList";

	private static final String _SQL_SELECT_CPCONFIGURATIONLIST_WHERE =
		"SELECT cpConfigurationList FROM CPConfigurationList cpConfigurationList WHERE ";

	private static final String _SQL_COUNT_CPCONFIGURATIONLIST =
		"SELECT COUNT(cpConfigurationList) FROM CPConfigurationList cpConfigurationList";

	private static final String _SQL_COUNT_CPCONFIGURATIONLIST_WHERE =
		"SELECT COUNT(cpConfigurationList) FROM CPConfigurationList cpConfigurationList WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "cpConfigurationList.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPConfigurationList exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPConfigurationList exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPConfigurationListPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}