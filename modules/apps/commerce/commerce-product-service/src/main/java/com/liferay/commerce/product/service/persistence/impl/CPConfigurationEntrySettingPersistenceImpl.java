/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.exception.NoSuchCPConfigurationEntrySettingException;
import com.liferay.commerce.product.model.CPConfigurationEntrySetting;
import com.liferay.commerce.product.model.CPConfigurationEntrySettingTable;
import com.liferay.commerce.product.model.impl.CPConfigurationEntrySettingImpl;
import com.liferay.commerce.product.model.impl.CPConfigurationEntrySettingModelImpl;
import com.liferay.commerce.product.service.persistence.CPConfigurationEntrySettingPersistence;
import com.liferay.commerce.product.service.persistence.CPConfigurationEntrySettingUtil;
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
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
 * The persistence implementation for the cp configuration entry setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = CPConfigurationEntrySettingPersistence.class)
public class CPConfigurationEntrySettingPersistenceImpl
	extends BasePersistenceImpl<CPConfigurationEntrySetting>
	implements CPConfigurationEntrySettingPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPConfigurationEntrySettingUtil</code> to access the cp configuration entry setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPConfigurationEntrySettingImpl.class.getName();

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
	 * Returns all the cp configuration entry settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp configuration entry settings
	 */
	@Override
	public List<CPConfigurationEntrySetting> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration entry settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @return the range of matching cp configuration entry settings
	 */
	@Override
	public List<CPConfigurationEntrySetting> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration entry settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration entry settings
	 */
	@Override
	public List<CPConfigurationEntrySetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration entry settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration entry settings
	 */
	@Override
	public List<CPConfigurationEntrySetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationEntrySetting.class)) {

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

			List<CPConfigurationEntrySetting> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationEntrySetting>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationEntrySetting
							cpConfigurationEntrySetting : list) {

						if (!uuid.equals(
								cpConfigurationEntrySetting.getUuid())) {

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

				sb.append(_SQL_SELECT_CPCONFIGURATIONENTRYSETTING_WHERE);

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
					sb.append(
						CPConfigurationEntrySettingModelImpl.ORDER_BY_JPQL);
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

					list = (List<CPConfigurationEntrySetting>)QueryUtil.list(
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
	 * Returns the first cp configuration entry setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting findByUuid_First(
			String uuid,
			OrderByComparator<CPConfigurationEntrySetting> orderByComparator)
		throws NoSuchCPConfigurationEntrySettingException {

		CPConfigurationEntrySetting cpConfigurationEntrySetting =
			fetchByUuid_First(uuid, orderByComparator);

		if (cpConfigurationEntrySetting != null) {
			return cpConfigurationEntrySetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPConfigurationEntrySettingException(sb.toString());
	}

	/**
	 * Returns the first cp configuration entry setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting fetchByUuid_First(
		String uuid,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator) {

		List<CPConfigurationEntrySetting> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp configuration entry setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting findByUuid_Last(
			String uuid,
			OrderByComparator<CPConfigurationEntrySetting> orderByComparator)
		throws NoSuchCPConfigurationEntrySettingException {

		CPConfigurationEntrySetting cpConfigurationEntrySetting =
			fetchByUuid_Last(uuid, orderByComparator);

		if (cpConfigurationEntrySetting != null) {
			return cpConfigurationEntrySetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPConfigurationEntrySettingException(sb.toString());
	}

	/**
	 * Returns the last cp configuration entry setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting fetchByUuid_Last(
		String uuid,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPConfigurationEntrySetting> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp configuration entry settings before and after the current cp configuration entry setting in the ordered set where uuid = &#63;.
	 *
	 * @param CPConfigurationEntrySettingId the primary key of the current cp configuration entry setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a cp configuration entry setting with the primary key could not be found
	 */
	@Override
	public CPConfigurationEntrySetting[] findByUuid_PrevAndNext(
			long CPConfigurationEntrySettingId, String uuid,
			OrderByComparator<CPConfigurationEntrySetting> orderByComparator)
		throws NoSuchCPConfigurationEntrySettingException {

		uuid = Objects.toString(uuid, "");

		CPConfigurationEntrySetting cpConfigurationEntrySetting =
			findByPrimaryKey(CPConfigurationEntrySettingId);

		Session session = null;

		try {
			session = openSession();

			CPConfigurationEntrySetting[] array =
				new CPConfigurationEntrySettingImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, cpConfigurationEntrySetting, uuid, orderByComparator,
				true);

			array[1] = cpConfigurationEntrySetting;

			array[2] = getByUuid_PrevAndNext(
				session, cpConfigurationEntrySetting, uuid, orderByComparator,
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

	protected CPConfigurationEntrySetting getByUuid_PrevAndNext(
		Session session,
		CPConfigurationEntrySetting cpConfigurationEntrySetting, String uuid,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator,
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

		sb.append(_SQL_SELECT_CPCONFIGURATIONENTRYSETTING_WHERE);

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
			sb.append(CPConfigurationEntrySettingModelImpl.ORDER_BY_JPQL);
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
						cpConfigurationEntrySetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPConfigurationEntrySetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp configuration entry settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPConfigurationEntrySetting cpConfigurationEntrySetting :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpConfigurationEntrySetting);
		}
	}

	/**
	 * Returns the number of cp configuration entry settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp configuration entry settings
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationEntrySetting.class)) {

			uuid = Objects.toString(uuid, "");

			FinderPath finderPath = _finderPathCountByUuid;

			Object[] finderArgs = new Object[] {uuid};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_CPCONFIGURATIONENTRYSETTING_WHERE);

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
		"cpConfigurationEntrySetting.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(cpConfigurationEntrySetting.uuid IS NULL OR cpConfigurationEntrySetting.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;

	/**
	 * Returns the cp configuration entry setting where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPConfigurationEntrySettingException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting findByUUID_G(String uuid, long groupId)
		throws NoSuchCPConfigurationEntrySettingException {

		CPConfigurationEntrySetting cpConfigurationEntrySetting = fetchByUUID_G(
			uuid, groupId);

		if (cpConfigurationEntrySetting == null) {
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

			throw new NoSuchCPConfigurationEntrySettingException(sb.toString());
		}

		return cpConfigurationEntrySetting;
	}

	/**
	 * Returns the cp configuration entry setting where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting fetchByUUID_G(
		String uuid, long groupId) {

		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp configuration entry setting where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationEntrySetting.class)) {

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

			if (result instanceof CPConfigurationEntrySetting) {
				CPConfigurationEntrySetting cpConfigurationEntrySetting =
					(CPConfigurationEntrySetting)result;

				if (!Objects.equals(
						uuid, cpConfigurationEntrySetting.getUuid()) ||
					(groupId != cpConfigurationEntrySetting.getGroupId())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_CPCONFIGURATIONENTRYSETTING_WHERE);

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

					List<CPConfigurationEntrySetting> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							finderCache.putResult(
								_finderPathFetchByUUID_G, finderArgs, list);
						}
					}
					else {
						CPConfigurationEntrySetting
							cpConfigurationEntrySetting = list.get(0);

						result = cpConfigurationEntrySetting;

						cacheResult(cpConfigurationEntrySetting);
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
				return (CPConfigurationEntrySetting)result;
			}
		}
	}

	/**
	 * Removes the cp configuration entry setting where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp configuration entry setting that was removed
	 */
	@Override
	public CPConfigurationEntrySetting removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPConfigurationEntrySettingException {

		CPConfigurationEntrySetting cpConfigurationEntrySetting = findByUUID_G(
			uuid, groupId);

		return remove(cpConfigurationEntrySetting);
	}

	/**
	 * Returns the number of cp configuration entry settings where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp configuration entry settings
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		CPConfigurationEntrySetting cpConfigurationEntrySetting = fetchByUUID_G(
			uuid, groupId);

		if (cpConfigurationEntrySetting == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"cpConfigurationEntrySetting.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(cpConfigurationEntrySetting.uuid IS NULL OR cpConfigurationEntrySetting.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"cpConfigurationEntrySetting.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the cp configuration entry settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp configuration entry settings
	 */
	@Override
	public List<CPConfigurationEntrySetting> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration entry settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @return the range of matching cp configuration entry settings
	 */
	@Override
	public List<CPConfigurationEntrySetting> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration entry settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration entry settings
	 */
	@Override
	public List<CPConfigurationEntrySetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration entry settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration entry settings
	 */
	@Override
	public List<CPConfigurationEntrySetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationEntrySetting.class)) {

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

			List<CPConfigurationEntrySetting> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationEntrySetting>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationEntrySetting
							cpConfigurationEntrySetting : list) {

						if (!uuid.equals(
								cpConfigurationEntrySetting.getUuid()) ||
							(companyId !=
								cpConfigurationEntrySetting.getCompanyId())) {

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

				sb.append(_SQL_SELECT_CPCONFIGURATIONENTRYSETTING_WHERE);

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
					sb.append(
						CPConfigurationEntrySettingModelImpl.ORDER_BY_JPQL);
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

					list = (List<CPConfigurationEntrySetting>)QueryUtil.list(
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
	 * Returns the first cp configuration entry setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPConfigurationEntrySetting> orderByComparator)
		throws NoSuchCPConfigurationEntrySettingException {

		CPConfigurationEntrySetting cpConfigurationEntrySetting =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (cpConfigurationEntrySetting != null) {
			return cpConfigurationEntrySetting;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPConfigurationEntrySettingException(sb.toString());
	}

	/**
	 * Returns the first cp configuration entry setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator) {

		List<CPConfigurationEntrySetting> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp configuration entry setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPConfigurationEntrySetting> orderByComparator)
		throws NoSuchCPConfigurationEntrySettingException {

		CPConfigurationEntrySetting cpConfigurationEntrySetting =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (cpConfigurationEntrySetting != null) {
			return cpConfigurationEntrySetting;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPConfigurationEntrySettingException(sb.toString());
	}

	/**
	 * Returns the last cp configuration entry setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPConfigurationEntrySetting> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp configuration entry settings before and after the current cp configuration entry setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPConfigurationEntrySettingId the primary key of the current cp configuration entry setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a cp configuration entry setting with the primary key could not be found
	 */
	@Override
	public CPConfigurationEntrySetting[] findByUuid_C_PrevAndNext(
			long CPConfigurationEntrySettingId, String uuid, long companyId,
			OrderByComparator<CPConfigurationEntrySetting> orderByComparator)
		throws NoSuchCPConfigurationEntrySettingException {

		uuid = Objects.toString(uuid, "");

		CPConfigurationEntrySetting cpConfigurationEntrySetting =
			findByPrimaryKey(CPConfigurationEntrySettingId);

		Session session = null;

		try {
			session = openSession();

			CPConfigurationEntrySetting[] array =
				new CPConfigurationEntrySettingImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, cpConfigurationEntrySetting, uuid, companyId,
				orderByComparator, true);

			array[1] = cpConfigurationEntrySetting;

			array[2] = getByUuid_C_PrevAndNext(
				session, cpConfigurationEntrySetting, uuid, companyId,
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

	protected CPConfigurationEntrySetting getByUuid_C_PrevAndNext(
		Session session,
		CPConfigurationEntrySetting cpConfigurationEntrySetting, String uuid,
		long companyId,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator,
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

		sb.append(_SQL_SELECT_CPCONFIGURATIONENTRYSETTING_WHERE);

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
			sb.append(CPConfigurationEntrySettingModelImpl.ORDER_BY_JPQL);
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
						cpConfigurationEntrySetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPConfigurationEntrySetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp configuration entry settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPConfigurationEntrySetting cpConfigurationEntrySetting :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpConfigurationEntrySetting);
		}
	}

	/**
	 * Returns the number of cp configuration entry settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp configuration entry settings
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationEntrySetting.class)) {

			uuid = Objects.toString(uuid, "");

			FinderPath finderPath = _finderPathCountByUuid_C;

			Object[] finderArgs = new Object[] {uuid, companyId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_CPCONFIGURATIONENTRYSETTING_WHERE);

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
		"cpConfigurationEntrySetting.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(cpConfigurationEntrySetting.uuid IS NULL OR cpConfigurationEntrySetting.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"cpConfigurationEntrySetting.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the cp configuration entry settings where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp configuration entry settings
	 */
	@Override
	public List<CPConfigurationEntrySetting> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration entry settings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @return the range of matching cp configuration entry settings
	 */
	@Override
	public List<CPConfigurationEntrySetting> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration entry settings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration entry settings
	 */
	@Override
	public List<CPConfigurationEntrySetting> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration entry settings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration entry settings
	 */
	@Override
	public List<CPConfigurationEntrySetting> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationEntrySetting.class)) {

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

			List<CPConfigurationEntrySetting> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationEntrySetting>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationEntrySetting
							cpConfigurationEntrySetting : list) {

						if (companyId !=
								cpConfigurationEntrySetting.getCompanyId()) {

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

				sb.append(_SQL_SELECT_CPCONFIGURATIONENTRYSETTING_WHERE);

				sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(
						CPConfigurationEntrySettingModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					list = (List<CPConfigurationEntrySetting>)QueryUtil.list(
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
	 * Returns the first cp configuration entry setting in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting findByCompanyId_First(
			long companyId,
			OrderByComparator<CPConfigurationEntrySetting> orderByComparator)
		throws NoSuchCPConfigurationEntrySettingException {

		CPConfigurationEntrySetting cpConfigurationEntrySetting =
			fetchByCompanyId_First(companyId, orderByComparator);

		if (cpConfigurationEntrySetting != null) {
			return cpConfigurationEntrySetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPConfigurationEntrySettingException(sb.toString());
	}

	/**
	 * Returns the first cp configuration entry setting in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator) {

		List<CPConfigurationEntrySetting> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp configuration entry setting in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting findByCompanyId_Last(
			long companyId,
			OrderByComparator<CPConfigurationEntrySetting> orderByComparator)
		throws NoSuchCPConfigurationEntrySettingException {

		CPConfigurationEntrySetting cpConfigurationEntrySetting =
			fetchByCompanyId_Last(companyId, orderByComparator);

		if (cpConfigurationEntrySetting != null) {
			return cpConfigurationEntrySetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPConfigurationEntrySettingException(sb.toString());
	}

	/**
	 * Returns the last cp configuration entry setting in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CPConfigurationEntrySetting> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp configuration entry settings before and after the current cp configuration entry setting in the ordered set where companyId = &#63;.
	 *
	 * @param CPConfigurationEntrySettingId the primary key of the current cp configuration entry setting
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a cp configuration entry setting with the primary key could not be found
	 */
	@Override
	public CPConfigurationEntrySetting[] findByCompanyId_PrevAndNext(
			long CPConfigurationEntrySettingId, long companyId,
			OrderByComparator<CPConfigurationEntrySetting> orderByComparator)
		throws NoSuchCPConfigurationEntrySettingException {

		CPConfigurationEntrySetting cpConfigurationEntrySetting =
			findByPrimaryKey(CPConfigurationEntrySettingId);

		Session session = null;

		try {
			session = openSession();

			CPConfigurationEntrySetting[] array =
				new CPConfigurationEntrySettingImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, cpConfigurationEntrySetting, companyId,
				orderByComparator, true);

			array[1] = cpConfigurationEntrySetting;

			array[2] = getByCompanyId_PrevAndNext(
				session, cpConfigurationEntrySetting, companyId,
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

	protected CPConfigurationEntrySetting getByCompanyId_PrevAndNext(
		Session session,
		CPConfigurationEntrySetting cpConfigurationEntrySetting, long companyId,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator,
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

		sb.append(_SQL_SELECT_CPCONFIGURATIONENTRYSETTING_WHERE);

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
			sb.append(CPConfigurationEntrySettingModelImpl.ORDER_BY_JPQL);
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
						cpConfigurationEntrySetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPConfigurationEntrySetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp configuration entry settings where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CPConfigurationEntrySetting cpConfigurationEntrySetting :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpConfigurationEntrySetting);
		}
	}

	/**
	 * Returns the number of cp configuration entry settings where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp configuration entry settings
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationEntrySetting.class)) {

			FinderPath finderPath = _finderPathCountByCompanyId;

			Object[] finderArgs = new Object[] {companyId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_CPCONFIGURATIONENTRYSETTING_WHERE);

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
		"cpConfigurationEntrySetting.companyId = ?";

	private FinderPath _finderPathFetchByC_T;

	/**
	 * Returns the cp configuration entry setting where CPConfigurationEntryId = &#63; and type = &#63; or throws a <code>NoSuchCPConfigurationEntrySettingException</code> if it could not be found.
	 *
	 * @param CPConfigurationEntryId the cp configuration entry ID
	 * @param type the type
	 * @return the matching cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting findByC_T(
			long CPConfigurationEntryId, int type)
		throws NoSuchCPConfigurationEntrySettingException {

		CPConfigurationEntrySetting cpConfigurationEntrySetting = fetchByC_T(
			CPConfigurationEntryId, type);

		if (cpConfigurationEntrySetting == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CPConfigurationEntryId=");
			sb.append(CPConfigurationEntryId);

			sb.append(", type=");
			sb.append(type);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPConfigurationEntrySettingException(sb.toString());
		}

		return cpConfigurationEntrySetting;
	}

	/**
	 * Returns the cp configuration entry setting where CPConfigurationEntryId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPConfigurationEntryId the cp configuration entry ID
	 * @param type the type
	 * @return the matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting fetchByC_T(
		long CPConfigurationEntryId, int type) {

		return fetchByC_T(CPConfigurationEntryId, type, true);
	}

	/**
	 * Returns the cp configuration entry setting where CPConfigurationEntryId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPConfigurationEntryId the cp configuration entry ID
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting fetchByC_T(
		long CPConfigurationEntryId, int type, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationEntrySetting.class)) {

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {CPConfigurationEntryId, type};
			}

			Object result = null;

			if (useFinderCache) {
				result = finderCache.getResult(
					_finderPathFetchByC_T, finderArgs, this);
			}

			if (result instanceof CPConfigurationEntrySetting) {
				CPConfigurationEntrySetting cpConfigurationEntrySetting =
					(CPConfigurationEntrySetting)result;

				if ((CPConfigurationEntryId !=
						cpConfigurationEntrySetting.
							getCPConfigurationEntryId()) ||
					(type != cpConfigurationEntrySetting.getType())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_CPCONFIGURATIONENTRYSETTING_WHERE);

				sb.append(_FINDER_COLUMN_C_T_CPCONFIGURATIONENTRYID_2);

				sb.append(_FINDER_COLUMN_C_T_TYPE_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(CPConfigurationEntryId);

					queryPos.add(type);

					List<CPConfigurationEntrySetting> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							finderCache.putResult(
								_finderPathFetchByC_T, finderArgs, list);
						}
					}
					else {
						CPConfigurationEntrySetting
							cpConfigurationEntrySetting = list.get(0);

						result = cpConfigurationEntrySetting;

						cacheResult(cpConfigurationEntrySetting);
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
				return (CPConfigurationEntrySetting)result;
			}
		}
	}

	/**
	 * Removes the cp configuration entry setting where CPConfigurationEntryId = &#63; and type = &#63; from the database.
	 *
	 * @param CPConfigurationEntryId the cp configuration entry ID
	 * @param type the type
	 * @return the cp configuration entry setting that was removed
	 */
	@Override
	public CPConfigurationEntrySetting removeByC_T(
			long CPConfigurationEntryId, int type)
		throws NoSuchCPConfigurationEntrySettingException {

		CPConfigurationEntrySetting cpConfigurationEntrySetting = findByC_T(
			CPConfigurationEntryId, type);

		return remove(cpConfigurationEntrySetting);
	}

	/**
	 * Returns the number of cp configuration entry settings where CPConfigurationEntryId = &#63; and type = &#63;.
	 *
	 * @param CPConfigurationEntryId the cp configuration entry ID
	 * @param type the type
	 * @return the number of matching cp configuration entry settings
	 */
	@Override
	public int countByC_T(long CPConfigurationEntryId, int type) {
		CPConfigurationEntrySetting cpConfigurationEntrySetting = fetchByC_T(
			CPConfigurationEntryId, type);

		if (cpConfigurationEntrySetting == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_C_T_CPCONFIGURATIONENTRYID_2 =
		"cpConfigurationEntrySetting.CPConfigurationEntryId = ? AND ";

	private static final String _FINDER_COLUMN_C_T_TYPE_2 =
		"cpConfigurationEntrySetting.type = ?";

	public CPConfigurationEntrySettingPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPConfigurationEntrySetting.class);

		setModelImplClass(CPConfigurationEntrySettingImpl.class);
		setModelPKClass(long.class);

		setTable(CPConfigurationEntrySettingTable.INSTANCE);
	}

	/**
	 * Caches the cp configuration entry setting in the entity cache if it is enabled.
	 *
	 * @param cpConfigurationEntrySetting the cp configuration entry setting
	 */
	@Override
	public void cacheResult(
		CPConfigurationEntrySetting cpConfigurationEntrySetting) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					cpConfigurationEntrySetting.getCtCollectionId())) {

			entityCache.putResult(
				CPConfigurationEntrySettingImpl.class,
				cpConfigurationEntrySetting.getPrimaryKey(),
				cpConfigurationEntrySetting);

			finderCache.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {
					cpConfigurationEntrySetting.getUuid(),
					cpConfigurationEntrySetting.getGroupId()
				},
				cpConfigurationEntrySetting);

			finderCache.putResult(
				_finderPathFetchByC_T,
				new Object[] {
					cpConfigurationEntrySetting.getCPConfigurationEntryId(),
					cpConfigurationEntrySetting.getType()
				},
				cpConfigurationEntrySetting);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cp configuration entry settings in the entity cache if it is enabled.
	 *
	 * @param cpConfigurationEntrySettings the cp configuration entry settings
	 */
	@Override
	public void cacheResult(
		List<CPConfigurationEntrySetting> cpConfigurationEntrySettings) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpConfigurationEntrySettings.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPConfigurationEntrySetting cpConfigurationEntrySetting :
				cpConfigurationEntrySettings) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						cpConfigurationEntrySetting.getCtCollectionId())) {

				if (entityCache.getResult(
						CPConfigurationEntrySettingImpl.class,
						cpConfigurationEntrySetting.getPrimaryKey()) == null) {

					cacheResult(cpConfigurationEntrySetting);
				}
			}
		}
	}

	/**
	 * Clears the cache for all cp configuration entry settings.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPConfigurationEntrySettingImpl.class);

		finderCache.clearCache(CPConfigurationEntrySettingImpl.class);
	}

	/**
	 * Clears the cache for the cp configuration entry setting.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CPConfigurationEntrySetting cpConfigurationEntrySetting) {

		entityCache.removeResult(
			CPConfigurationEntrySettingImpl.class, cpConfigurationEntrySetting);
	}

	@Override
	public void clearCache(
		List<CPConfigurationEntrySetting> cpConfigurationEntrySettings) {

		for (CPConfigurationEntrySetting cpConfigurationEntrySetting :
				cpConfigurationEntrySettings) {

			entityCache.removeResult(
				CPConfigurationEntrySettingImpl.class,
				cpConfigurationEntrySetting);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPConfigurationEntrySettingImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPConfigurationEntrySettingImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPConfigurationEntrySettingModelImpl
			cpConfigurationEntrySettingModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					cpConfigurationEntrySettingModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				cpConfigurationEntrySettingModelImpl.getUuid(),
				cpConfigurationEntrySettingModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G, args,
				cpConfigurationEntrySettingModelImpl);

			args = new Object[] {
				cpConfigurationEntrySettingModelImpl.
					getCPConfigurationEntryId(),
				cpConfigurationEntrySettingModelImpl.getType()
			};

			finderCache.putResult(
				_finderPathFetchByC_T, args,
				cpConfigurationEntrySettingModelImpl);
		}
	}

	/**
	 * Creates a new cp configuration entry setting with the primary key. Does not add the cp configuration entry setting to the database.
	 *
	 * @param CPConfigurationEntrySettingId the primary key for the new cp configuration entry setting
	 * @return the new cp configuration entry setting
	 */
	@Override
	public CPConfigurationEntrySetting create(
		long CPConfigurationEntrySettingId) {

		CPConfigurationEntrySetting cpConfigurationEntrySetting =
			new CPConfigurationEntrySettingImpl();

		cpConfigurationEntrySetting.setNew(true);
		cpConfigurationEntrySetting.setPrimaryKey(
			CPConfigurationEntrySettingId);

		String uuid = PortalUUIDUtil.generate();

		cpConfigurationEntrySetting.setUuid(uuid);

		cpConfigurationEntrySetting.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return cpConfigurationEntrySetting;
	}

	/**
	 * Removes the cp configuration entry setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPConfigurationEntrySettingId the primary key of the cp configuration entry setting
	 * @return the cp configuration entry setting that was removed
	 * @throws NoSuchCPConfigurationEntrySettingException if a cp configuration entry setting with the primary key could not be found
	 */
	@Override
	public CPConfigurationEntrySetting remove(
			long CPConfigurationEntrySettingId)
		throws NoSuchCPConfigurationEntrySettingException {

		return remove((Serializable)CPConfigurationEntrySettingId);
	}

	/**
	 * Removes the cp configuration entry setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp configuration entry setting
	 * @return the cp configuration entry setting that was removed
	 * @throws NoSuchCPConfigurationEntrySettingException if a cp configuration entry setting with the primary key could not be found
	 */
	@Override
	public CPConfigurationEntrySetting remove(Serializable primaryKey)
		throws NoSuchCPConfigurationEntrySettingException {

		Session session = null;

		try {
			session = openSession();

			CPConfigurationEntrySetting cpConfigurationEntrySetting =
				(CPConfigurationEntrySetting)session.get(
					CPConfigurationEntrySettingImpl.class, primaryKey);

			if (cpConfigurationEntrySetting == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPConfigurationEntrySettingException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpConfigurationEntrySetting);
		}
		catch (NoSuchCPConfigurationEntrySettingException
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
	protected CPConfigurationEntrySetting removeImpl(
		CPConfigurationEntrySetting cpConfigurationEntrySetting) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpConfigurationEntrySetting)) {
				cpConfigurationEntrySetting =
					(CPConfigurationEntrySetting)session.get(
						CPConfigurationEntrySettingImpl.class,
						cpConfigurationEntrySetting.getPrimaryKeyObj());
			}

			if ((cpConfigurationEntrySetting != null) &&
				ctPersistenceHelper.isRemove(cpConfigurationEntrySetting)) {

				session.delete(cpConfigurationEntrySetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpConfigurationEntrySetting != null) {
			clearCache(cpConfigurationEntrySetting);
		}

		return cpConfigurationEntrySetting;
	}

	@Override
	public CPConfigurationEntrySetting updateImpl(
		CPConfigurationEntrySetting cpConfigurationEntrySetting) {

		boolean isNew = cpConfigurationEntrySetting.isNew();

		if (!(cpConfigurationEntrySetting instanceof
				CPConfigurationEntrySettingModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					cpConfigurationEntrySetting.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					cpConfigurationEntrySetting);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpConfigurationEntrySetting proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPConfigurationEntrySetting implementation " +
					cpConfigurationEntrySetting.getClass());
		}

		CPConfigurationEntrySettingModelImpl
			cpConfigurationEntrySettingModelImpl =
				(CPConfigurationEntrySettingModelImpl)
					cpConfigurationEntrySetting;

		if (Validator.isNull(cpConfigurationEntrySetting.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpConfigurationEntrySetting.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpConfigurationEntrySetting.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpConfigurationEntrySetting.setCreateDate(date);
			}
			else {
				cpConfigurationEntrySetting.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpConfigurationEntrySettingModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpConfigurationEntrySetting.setModifiedDate(date);
			}
			else {
				cpConfigurationEntrySetting.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(cpConfigurationEntrySetting)) {
				if (!isNew) {
					session.evict(
						CPConfigurationEntrySettingImpl.class,
						cpConfigurationEntrySetting.getPrimaryKeyObj());
				}

				session.save(cpConfigurationEntrySetting);
			}
			else {
				cpConfigurationEntrySetting =
					(CPConfigurationEntrySetting)session.merge(
						cpConfigurationEntrySetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPConfigurationEntrySettingImpl.class,
			cpConfigurationEntrySettingModelImpl, false, true);

		cacheUniqueFindersCache(cpConfigurationEntrySettingModelImpl);

		if (isNew) {
			cpConfigurationEntrySetting.setNew(false);
		}

		cpConfigurationEntrySetting.resetOriginalValues();

		return cpConfigurationEntrySetting;
	}

	/**
	 * Returns the cp configuration entry setting with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp configuration entry setting
	 * @return the cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a cp configuration entry setting with the primary key could not be found
	 */
	@Override
	public CPConfigurationEntrySetting findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPConfigurationEntrySettingException {

		CPConfigurationEntrySetting cpConfigurationEntrySetting =
			fetchByPrimaryKey(primaryKey);

		if (cpConfigurationEntrySetting == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPConfigurationEntrySettingException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpConfigurationEntrySetting;
	}

	/**
	 * Returns the cp configuration entry setting with the primary key or throws a <code>NoSuchCPConfigurationEntrySettingException</code> if it could not be found.
	 *
	 * @param CPConfigurationEntrySettingId the primary key of the cp configuration entry setting
	 * @return the cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a cp configuration entry setting with the primary key could not be found
	 */
	@Override
	public CPConfigurationEntrySetting findByPrimaryKey(
			long CPConfigurationEntrySettingId)
		throws NoSuchCPConfigurationEntrySettingException {

		return findByPrimaryKey((Serializable)CPConfigurationEntrySettingId);
	}

	/**
	 * Returns the cp configuration entry setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp configuration entry setting
	 * @return the cp configuration entry setting, or <code>null</code> if a cp configuration entry setting with the primary key could not be found
	 */
	@Override
	public CPConfigurationEntrySetting fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				CPConfigurationEntrySetting.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		CPConfigurationEntrySetting cpConfigurationEntrySetting =
			(CPConfigurationEntrySetting)entityCache.getResult(
				CPConfigurationEntrySettingImpl.class, primaryKey);

		if (cpConfigurationEntrySetting != null) {
			return cpConfigurationEntrySetting;
		}

		Session session = null;

		try {
			session = openSession();

			cpConfigurationEntrySetting =
				(CPConfigurationEntrySetting)session.get(
					CPConfigurationEntrySettingImpl.class, primaryKey);

			if (cpConfigurationEntrySetting != null) {
				cacheResult(cpConfigurationEntrySetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return cpConfigurationEntrySetting;
	}

	/**
	 * Returns the cp configuration entry setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPConfigurationEntrySettingId the primary key of the cp configuration entry setting
	 * @return the cp configuration entry setting, or <code>null</code> if a cp configuration entry setting with the primary key could not be found
	 */
	@Override
	public CPConfigurationEntrySetting fetchByPrimaryKey(
		long CPConfigurationEntrySettingId) {

		return fetchByPrimaryKey((Serializable)CPConfigurationEntrySettingId);
	}

	@Override
	public Map<Serializable, CPConfigurationEntrySetting> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				CPConfigurationEntrySetting.class)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPConfigurationEntrySetting> map =
			new HashMap<Serializable, CPConfigurationEntrySetting>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPConfigurationEntrySetting cpConfigurationEntrySetting =
				fetchByPrimaryKey(primaryKey);

			if (cpConfigurationEntrySetting != null) {
				map.put(primaryKey, cpConfigurationEntrySetting);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						CPConfigurationEntrySetting.class, primaryKey)) {

				CPConfigurationEntrySetting cpConfigurationEntrySetting =
					(CPConfigurationEntrySetting)entityCache.getResult(
						CPConfigurationEntrySettingImpl.class, primaryKey);

				if (cpConfigurationEntrySetting == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, cpConfigurationEntrySetting);
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

			for (CPConfigurationEntrySetting cpConfigurationEntrySetting :
					(List<CPConfigurationEntrySetting>)query.list()) {

				map.put(
					cpConfigurationEntrySetting.getPrimaryKeyObj(),
					cpConfigurationEntrySetting);

				cacheResult(cpConfigurationEntrySetting);
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
	 * Returns all the cp configuration entry settings.
	 *
	 * @return the cp configuration entry settings
	 */
	@Override
	public List<CPConfigurationEntrySetting> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration entry settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @return the range of cp configuration entry settings
	 */
	@Override
	public List<CPConfigurationEntrySetting> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration entry settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp configuration entry settings
	 */
	@Override
	public List<CPConfigurationEntrySetting> findAll(
		int start, int end,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration entry settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp configuration entry settings
	 */
	@Override
	public List<CPConfigurationEntrySetting> findAll(
		int start, int end,
		OrderByComparator<CPConfigurationEntrySetting> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationEntrySetting.class)) {

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

			List<CPConfigurationEntrySetting> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationEntrySetting>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_CPCONFIGURATIONENTRYSETTING);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_CPCONFIGURATIONENTRYSETTING;

					sql = sql.concat(
						CPConfigurationEntrySettingModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<CPConfigurationEntrySetting>)QueryUtil.list(
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
	 * Removes all the cp configuration entry settings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPConfigurationEntrySetting cpConfigurationEntrySetting :
				findAll()) {

			remove(cpConfigurationEntrySetting);
		}
	}

	/**
	 * Returns the number of cp configuration entry settings.
	 *
	 * @return the number of cp configuration entry settings
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationEntrySetting.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_CPCONFIGURATIONENTRYSETTING);

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
		return "CPConfigurationEntrySettingId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPCONFIGURATIONENTRYSETTING;
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
		return CPConfigurationEntrySettingModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CPConfigurationEntrySetting";
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
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctMergeColumnNames.add("CPConfigurationEntryId");
		ctMergeColumnNames.add("type_");
		ctMergeColumnNames.add("value");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CPConfigurationEntrySettingId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"CPConfigurationEntryId", "type_"});
	}

	/**
	 * Initializes the cp configuration entry setting persistence.
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

		_finderPathFetchByC_T = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"CPConfigurationEntryId", "type_"}, true);

		CPConfigurationEntrySettingUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CPConfigurationEntrySettingUtil.setPersistence(null);

		entityCache.removeCache(
			CPConfigurationEntrySettingImpl.class.getName());
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

	private static final String _SQL_SELECT_CPCONFIGURATIONENTRYSETTING =
		"SELECT cpConfigurationEntrySetting FROM CPConfigurationEntrySetting cpConfigurationEntrySetting";

	private static final String _SQL_SELECT_CPCONFIGURATIONENTRYSETTING_WHERE =
		"SELECT cpConfigurationEntrySetting FROM CPConfigurationEntrySetting cpConfigurationEntrySetting WHERE ";

	private static final String _SQL_COUNT_CPCONFIGURATIONENTRYSETTING =
		"SELECT COUNT(cpConfigurationEntrySetting) FROM CPConfigurationEntrySetting cpConfigurationEntrySetting";

	private static final String _SQL_COUNT_CPCONFIGURATIONENTRYSETTING_WHERE =
		"SELECT COUNT(cpConfigurationEntrySetting) FROM CPConfigurationEntrySetting cpConfigurationEntrySetting WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpConfigurationEntrySetting.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPConfigurationEntrySetting exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPConfigurationEntrySetting exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPConfigurationEntrySettingPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-530177371