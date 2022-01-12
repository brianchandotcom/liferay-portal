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

package com.liferay.commerce.shop.by.diagram.service.persistence.impl;

import com.liferay.commerce.shop.by.diagram.exception.NoSuchCSDiagramSettingException;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSettingTable;
import com.liferay.commerce.shop.by.diagram.model.impl.CSDiagramSettingImpl;
import com.liferay.commerce.shop.by.diagram.model.impl.CSDiagramSettingModelImpl;
import com.liferay.commerce.shop.by.diagram.service.persistence.CSDiagramSettingPersistence;
import com.liferay.commerce.shop.by.diagram.service.persistence.CSDiagramSettingUtil;
import com.liferay.commerce.shop.by.diagram.service.persistence.impl.constants.CommercePersistenceConstants;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

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
 * The persistence implementation for the cs diagram setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(service = {CSDiagramSettingPersistence.class, BasePersistence.class})
public class CSDiagramSettingPersistenceImpl
	extends BasePersistenceImpl<CSDiagramSetting>
	implements CSDiagramSettingPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CSDiagramSettingUtil</code> to access the cs diagram setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CSDiagramSettingImpl.class.getName();

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
	 * Returns all the cs diagram settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cs diagram settings
	 */
	@Override
	public List<CSDiagramSetting> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cs diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @return the range of matching cs diagram settings
	 */
	@Override
	public List<CSDiagramSetting> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cs diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cs diagram settings
	 */
	@Override
	public List<CSDiagramSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CSDiagramSetting> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cs diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cs diagram settings
	 */
	@Override
	public List<CSDiagramSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CSDiagramSetting> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CSDiagramSetting.class);

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

		List<CSDiagramSetting> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CSDiagramSetting>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CSDiagramSetting csDiagramSetting : list) {
					if (!uuid.equals(csDiagramSetting.getUuid())) {
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

			sb.append(_SQL_SELECT_CSDIAGRAMSETTING_WHERE);

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
				sb.append(CSDiagramSettingModelImpl.ORDER_BY_JPQL);
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

				list = (List<CSDiagramSetting>)QueryUtil.list(
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
	 * Returns the first cs diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a matching cs diagram setting could not be found
	 */
	@Override
	public CSDiagramSetting findByUuid_First(
			String uuid, OrderByComparator<CSDiagramSetting> orderByComparator)
		throws NoSuchCSDiagramSettingException {

		CSDiagramSetting csDiagramSetting = fetchByUuid_First(
			uuid, orderByComparator);

		if (csDiagramSetting != null) {
			return csDiagramSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCSDiagramSettingException(sb.toString());
	}

	/**
	 * Returns the first cs diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	@Override
	public CSDiagramSetting fetchByUuid_First(
		String uuid, OrderByComparator<CSDiagramSetting> orderByComparator) {

		List<CSDiagramSetting> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cs diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a matching cs diagram setting could not be found
	 */
	@Override
	public CSDiagramSetting findByUuid_Last(
			String uuid, OrderByComparator<CSDiagramSetting> orderByComparator)
		throws NoSuchCSDiagramSettingException {

		CSDiagramSetting csDiagramSetting = fetchByUuid_Last(
			uuid, orderByComparator);

		if (csDiagramSetting != null) {
			return csDiagramSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCSDiagramSettingException(sb.toString());
	}

	/**
	 * Returns the last cs diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	@Override
	public CSDiagramSetting fetchByUuid_Last(
		String uuid, OrderByComparator<CSDiagramSetting> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CSDiagramSetting> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cs diagram settings before and after the current cs diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param CSDiagramSettingId the primary key of the current cs diagram setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a cs diagram setting with the primary key could not be found
	 */
	@Override
	public CSDiagramSetting[] findByUuid_PrevAndNext(
			long CSDiagramSettingId, String uuid,
			OrderByComparator<CSDiagramSetting> orderByComparator)
		throws NoSuchCSDiagramSettingException {

		uuid = Objects.toString(uuid, "");

		CSDiagramSetting csDiagramSetting = findByPrimaryKey(
			CSDiagramSettingId);

		Session session = null;

		try {
			session = openSession();

			CSDiagramSetting[] array = new CSDiagramSettingImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, csDiagramSetting, uuid, orderByComparator, true);

			array[1] = csDiagramSetting;

			array[2] = getByUuid_PrevAndNext(
				session, csDiagramSetting, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CSDiagramSetting getByUuid_PrevAndNext(
		Session session, CSDiagramSetting csDiagramSetting, String uuid,
		OrderByComparator<CSDiagramSetting> orderByComparator,
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

		sb.append(_SQL_SELECT_CSDIAGRAMSETTING_WHERE);

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
			sb.append(CSDiagramSettingModelImpl.ORDER_BY_JPQL);
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
						csDiagramSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CSDiagramSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cs diagram settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CSDiagramSetting csDiagramSetting :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(csDiagramSetting);
		}
	}

	/**
	 * Returns the number of cs diagram settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cs diagram settings
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CSDiagramSetting.class);

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

			sb.append(_SQL_COUNT_CSDIAGRAMSETTING_WHERE);

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
		"csDiagramSetting.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(csDiagramSetting.uuid IS NULL OR csDiagramSetting.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the cs diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cs diagram settings
	 */
	@Override
	public List<CSDiagramSetting> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cs diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @return the range of matching cs diagram settings
	 */
	@Override
	public List<CSDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cs diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cs diagram settings
	 */
	@Override
	public List<CSDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CSDiagramSetting> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cs diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cs diagram settings
	 */
	@Override
	public List<CSDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CSDiagramSetting> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CSDiagramSetting.class);

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

		List<CSDiagramSetting> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CSDiagramSetting>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CSDiagramSetting csDiagramSetting : list) {
					if (!uuid.equals(csDiagramSetting.getUuid()) ||
						(companyId != csDiagramSetting.getCompanyId())) {

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

			sb.append(_SQL_SELECT_CSDIAGRAMSETTING_WHERE);

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
				sb.append(CSDiagramSettingModelImpl.ORDER_BY_JPQL);
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

				list = (List<CSDiagramSetting>)QueryUtil.list(
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
	 * Returns the first cs diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a matching cs diagram setting could not be found
	 */
	@Override
	public CSDiagramSetting findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CSDiagramSetting> orderByComparator)
		throws NoSuchCSDiagramSettingException {

		CSDiagramSetting csDiagramSetting = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (csDiagramSetting != null) {
			return csDiagramSetting;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCSDiagramSettingException(sb.toString());
	}

	/**
	 * Returns the first cs diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	@Override
	public CSDiagramSetting fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CSDiagramSetting> orderByComparator) {

		List<CSDiagramSetting> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cs diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a matching cs diagram setting could not be found
	 */
	@Override
	public CSDiagramSetting findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CSDiagramSetting> orderByComparator)
		throws NoSuchCSDiagramSettingException {

		CSDiagramSetting csDiagramSetting = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (csDiagramSetting != null) {
			return csDiagramSetting;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCSDiagramSettingException(sb.toString());
	}

	/**
	 * Returns the last cs diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	@Override
	public CSDiagramSetting fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CSDiagramSetting> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CSDiagramSetting> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cs diagram settings before and after the current cs diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CSDiagramSettingId the primary key of the current cs diagram setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a cs diagram setting with the primary key could not be found
	 */
	@Override
	public CSDiagramSetting[] findByUuid_C_PrevAndNext(
			long CSDiagramSettingId, String uuid, long companyId,
			OrderByComparator<CSDiagramSetting> orderByComparator)
		throws NoSuchCSDiagramSettingException {

		uuid = Objects.toString(uuid, "");

		CSDiagramSetting csDiagramSetting = findByPrimaryKey(
			CSDiagramSettingId);

		Session session = null;

		try {
			session = openSession();

			CSDiagramSetting[] array = new CSDiagramSettingImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, csDiagramSetting, uuid, companyId, orderByComparator,
				true);

			array[1] = csDiagramSetting;

			array[2] = getByUuid_C_PrevAndNext(
				session, csDiagramSetting, uuid, companyId, orderByComparator,
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

	protected CSDiagramSetting getByUuid_C_PrevAndNext(
		Session session, CSDiagramSetting csDiagramSetting, String uuid,
		long companyId, OrderByComparator<CSDiagramSetting> orderByComparator,
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

		sb.append(_SQL_SELECT_CSDIAGRAMSETTING_WHERE);

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
			sb.append(CSDiagramSettingModelImpl.ORDER_BY_JPQL);
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
						csDiagramSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CSDiagramSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cs diagram settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CSDiagramSetting csDiagramSetting :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(csDiagramSetting);
		}
	}

	/**
	 * Returns the number of cs diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cs diagram settings
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CSDiagramSetting.class);

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

			sb.append(_SQL_COUNT_CSDIAGRAMSETTING_WHERE);

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
		"csDiagramSetting.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(csDiagramSetting.uuid IS NULL OR csDiagramSetting.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"csDiagramSetting.companyId = ?";

	private FinderPath _finderPathFetchByCPDefinitionId;
	private FinderPath _finderPathCountByCPDefinitionId;

	/**
	 * Returns the cs diagram setting where CPDefinitionId = &#63; or throws a <code>NoSuchCSDiagramSettingException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a matching cs diagram setting could not be found
	 */
	@Override
	public CSDiagramSetting findByCPDefinitionId(long CPDefinitionId)
		throws NoSuchCSDiagramSettingException {

		CSDiagramSetting csDiagramSetting = fetchByCPDefinitionId(
			CPDefinitionId);

		if (csDiagramSetting == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CPDefinitionId=");
			sb.append(CPDefinitionId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCSDiagramSettingException(sb.toString());
		}

		return csDiagramSetting;
	}

	/**
	 * Returns the cs diagram setting where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	@Override
	public CSDiagramSetting fetchByCPDefinitionId(long CPDefinitionId) {
		return fetchByCPDefinitionId(CPDefinitionId, true);
	}

	/**
	 * Returns the cs diagram setting where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	@Override
	public CSDiagramSetting fetchByCPDefinitionId(
		long CPDefinitionId, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CSDiagramSetting.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {CPDefinitionId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByCPDefinitionId, finderArgs);
		}

		if (result instanceof CSDiagramSetting) {
			CSDiagramSetting csDiagramSetting = (CSDiagramSetting)result;

			if (CPDefinitionId != csDiagramSetting.getCPDefinitionId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_CSDIAGRAMSETTING_WHERE);

			sb.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				List<CSDiagramSetting> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByCPDefinitionId, finderArgs, list);
					}
				}
				else {
					CSDiagramSetting csDiagramSetting = list.get(0);

					result = csDiagramSetting;

					cacheResult(csDiagramSetting);
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
			return (CSDiagramSetting)result;
		}
	}

	/**
	 * Removes the cs diagram setting where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the cs diagram setting that was removed
	 */
	@Override
	public CSDiagramSetting removeByCPDefinitionId(long CPDefinitionId)
		throws NoSuchCSDiagramSettingException {

		CSDiagramSetting csDiagramSetting = findByCPDefinitionId(
			CPDefinitionId);

		return remove(csDiagramSetting);
	}

	/**
	 * Returns the number of cs diagram settings where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cs diagram settings
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CSDiagramSetting.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCPDefinitionId;

			finderArgs = new Object[] {CPDefinitionId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CSDIAGRAMSETTING_WHERE);

			sb.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

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

	private static final String _FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2 =
		"csDiagramSetting.CPDefinitionId = ?";

	public CSDiagramSettingPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CSDiagramSetting.class);

		setModelImplClass(CSDiagramSettingImpl.class);
		setModelPKClass(long.class);

		setTable(CSDiagramSettingTable.INSTANCE);
	}

	/**
	 * Caches the cs diagram setting in the entity cache if it is enabled.
	 *
	 * @param csDiagramSetting the cs diagram setting
	 */
	@Override
	public void cacheResult(CSDiagramSetting csDiagramSetting) {
		if (csDiagramSetting.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			CSDiagramSettingImpl.class, csDiagramSetting.getPrimaryKey(),
			csDiagramSetting);

		finderCache.putResult(
			_finderPathFetchByCPDefinitionId,
			new Object[] {csDiagramSetting.getCPDefinitionId()},
			csDiagramSetting);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cs diagram settings in the entity cache if it is enabled.
	 *
	 * @param csDiagramSettings the cs diagram settings
	 */
	@Override
	public void cacheResult(List<CSDiagramSetting> csDiagramSettings) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (csDiagramSettings.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CSDiagramSetting csDiagramSetting : csDiagramSettings) {
			if (csDiagramSetting.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					CSDiagramSettingImpl.class,
					csDiagramSetting.getPrimaryKey()) == null) {

				cacheResult(csDiagramSetting);
			}
		}
	}

	/**
	 * Clears the cache for all cs diagram settings.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CSDiagramSettingImpl.class);

		finderCache.clearCache(CSDiagramSettingImpl.class);
	}

	/**
	 * Clears the cache for the cs diagram setting.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CSDiagramSetting csDiagramSetting) {
		entityCache.removeResult(CSDiagramSettingImpl.class, csDiagramSetting);
	}

	@Override
	public void clearCache(List<CSDiagramSetting> csDiagramSettings) {
		for (CSDiagramSetting csDiagramSetting : csDiagramSettings) {
			entityCache.removeResult(
				CSDiagramSettingImpl.class, csDiagramSetting);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CSDiagramSettingImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CSDiagramSettingImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CSDiagramSettingModelImpl csDiagramSettingModelImpl) {

		Object[] args = new Object[] {
			csDiagramSettingModelImpl.getCPDefinitionId()
		};

		finderCache.putResult(
			_finderPathCountByCPDefinitionId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCPDefinitionId, args, csDiagramSettingModelImpl);
	}

	/**
	 * Creates a new cs diagram setting with the primary key. Does not add the cs diagram setting to the database.
	 *
	 * @param CSDiagramSettingId the primary key for the new cs diagram setting
	 * @return the new cs diagram setting
	 */
	@Override
	public CSDiagramSetting create(long CSDiagramSettingId) {
		CSDiagramSetting csDiagramSetting = new CSDiagramSettingImpl();

		csDiagramSetting.setNew(true);
		csDiagramSetting.setPrimaryKey(CSDiagramSettingId);

		String uuid = PortalUUIDUtil.generate();

		csDiagramSetting.setUuid(uuid);

		csDiagramSetting.setCompanyId(CompanyThreadLocal.getCompanyId());

		return csDiagramSetting;
	}

	/**
	 * Removes the cs diagram setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CSDiagramSettingId the primary key of the cs diagram setting
	 * @return the cs diagram setting that was removed
	 * @throws NoSuchCSDiagramSettingException if a cs diagram setting with the primary key could not be found
	 */
	@Override
	public CSDiagramSetting remove(long CSDiagramSettingId)
		throws NoSuchCSDiagramSettingException {

		return remove((Serializable)CSDiagramSettingId);
	}

	/**
	 * Removes the cs diagram setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cs diagram setting
	 * @return the cs diagram setting that was removed
	 * @throws NoSuchCSDiagramSettingException if a cs diagram setting with the primary key could not be found
	 */
	@Override
	public CSDiagramSetting remove(Serializable primaryKey)
		throws NoSuchCSDiagramSettingException {

		Session session = null;

		try {
			session = openSession();

			CSDiagramSetting csDiagramSetting = (CSDiagramSetting)session.get(
				CSDiagramSettingImpl.class, primaryKey);

			if (csDiagramSetting == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCSDiagramSettingException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(csDiagramSetting);
		}
		catch (NoSuchCSDiagramSettingException noSuchEntityException) {
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
	protected CSDiagramSetting removeImpl(CSDiagramSetting csDiagramSetting) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(csDiagramSetting)) {
				csDiagramSetting = (CSDiagramSetting)session.get(
					CSDiagramSettingImpl.class,
					csDiagramSetting.getPrimaryKeyObj());
			}

			if ((csDiagramSetting != null) &&
				ctPersistenceHelper.isRemove(csDiagramSetting)) {

				session.delete(csDiagramSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (csDiagramSetting != null) {
			clearCache(csDiagramSetting);
		}

		return csDiagramSetting;
	}

	@Override
	public CSDiagramSetting updateImpl(CSDiagramSetting csDiagramSetting) {
		boolean isNew = csDiagramSetting.isNew();

		if (!(csDiagramSetting instanceof CSDiagramSettingModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(csDiagramSetting.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					csDiagramSetting);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in csDiagramSetting proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CSDiagramSetting implementation " +
					csDiagramSetting.getClass());
		}

		CSDiagramSettingModelImpl csDiagramSettingModelImpl =
			(CSDiagramSettingModelImpl)csDiagramSetting;

		if (Validator.isNull(csDiagramSetting.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			csDiagramSetting.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (csDiagramSetting.getCreateDate() == null)) {
			if (serviceContext == null) {
				csDiagramSetting.setCreateDate(date);
			}
			else {
				csDiagramSetting.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!csDiagramSettingModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				csDiagramSetting.setModifiedDate(date);
			}
			else {
				csDiagramSetting.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(csDiagramSetting)) {
				if (!isNew) {
					session.evict(
						CSDiagramSettingImpl.class,
						csDiagramSetting.getPrimaryKeyObj());
				}

				session.save(csDiagramSetting);
			}
			else {
				csDiagramSetting = (CSDiagramSetting)session.merge(
					csDiagramSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (csDiagramSetting.getCtCollectionId() != 0) {
			if (isNew) {
				csDiagramSetting.setNew(false);
			}

			csDiagramSetting.resetOriginalValues();

			return csDiagramSetting;
		}

		entityCache.putResult(
			CSDiagramSettingImpl.class, csDiagramSettingModelImpl, false, true);

		cacheUniqueFindersCache(csDiagramSettingModelImpl);

		if (isNew) {
			csDiagramSetting.setNew(false);
		}

		csDiagramSetting.resetOriginalValues();

		return csDiagramSetting;
	}

	/**
	 * Returns the cs diagram setting with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cs diagram setting
	 * @return the cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a cs diagram setting with the primary key could not be found
	 */
	@Override
	public CSDiagramSetting findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCSDiagramSettingException {

		CSDiagramSetting csDiagramSetting = fetchByPrimaryKey(primaryKey);

		if (csDiagramSetting == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCSDiagramSettingException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return csDiagramSetting;
	}

	/**
	 * Returns the cs diagram setting with the primary key or throws a <code>NoSuchCSDiagramSettingException</code> if it could not be found.
	 *
	 * @param CSDiagramSettingId the primary key of the cs diagram setting
	 * @return the cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a cs diagram setting with the primary key could not be found
	 */
	@Override
	public CSDiagramSetting findByPrimaryKey(long CSDiagramSettingId)
		throws NoSuchCSDiagramSettingException {

		return findByPrimaryKey((Serializable)CSDiagramSettingId);
	}

	/**
	 * Returns the cs diagram setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cs diagram setting
	 * @return the cs diagram setting, or <code>null</code> if a cs diagram setting with the primary key could not be found
	 */
	@Override
	public CSDiagramSetting fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(CSDiagramSetting.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		CSDiagramSetting csDiagramSetting = null;

		Session session = null;

		try {
			session = openSession();

			csDiagramSetting = (CSDiagramSetting)session.get(
				CSDiagramSettingImpl.class, primaryKey);

			if (csDiagramSetting != null) {
				cacheResult(csDiagramSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return csDiagramSetting;
	}

	/**
	 * Returns the cs diagram setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CSDiagramSettingId the primary key of the cs diagram setting
	 * @return the cs diagram setting, or <code>null</code> if a cs diagram setting with the primary key could not be found
	 */
	@Override
	public CSDiagramSetting fetchByPrimaryKey(long CSDiagramSettingId) {
		return fetchByPrimaryKey((Serializable)CSDiagramSettingId);
	}

	@Override
	public Map<Serializable, CSDiagramSetting> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(CSDiagramSetting.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CSDiagramSetting> map =
			new HashMap<Serializable, CSDiagramSetting>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CSDiagramSetting csDiagramSetting = fetchByPrimaryKey(primaryKey);

			if (csDiagramSetting != null) {
				map.put(primaryKey, csDiagramSetting);
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

			for (CSDiagramSetting csDiagramSetting :
					(List<CSDiagramSetting>)query.list()) {

				map.put(csDiagramSetting.getPrimaryKeyObj(), csDiagramSetting);

				cacheResult(csDiagramSetting);
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
	 * Returns all the cs diagram settings.
	 *
	 * @return the cs diagram settings
	 */
	@Override
	public List<CSDiagramSetting> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cs diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @return the range of cs diagram settings
	 */
	@Override
	public List<CSDiagramSetting> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cs diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cs diagram settings
	 */
	@Override
	public List<CSDiagramSetting> findAll(
		int start, int end,
		OrderByComparator<CSDiagramSetting> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cs diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cs diagram settings
	 */
	@Override
	public List<CSDiagramSetting> findAll(
		int start, int end,
		OrderByComparator<CSDiagramSetting> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CSDiagramSetting.class);

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

		List<CSDiagramSetting> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CSDiagramSetting>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CSDIAGRAMSETTING);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CSDIAGRAMSETTING;

				sql = sql.concat(CSDiagramSettingModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CSDiagramSetting>)QueryUtil.list(
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
	 * Removes all the cs diagram settings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CSDiagramSetting csDiagramSetting : findAll()) {
			remove(csDiagramSetting);
		}
	}

	/**
	 * Returns the number of cs diagram settings.
	 *
	 * @return the number of cs diagram settings
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CSDiagramSetting.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CSDIAGRAMSETTING);

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
		return "CSDiagramSettingId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CSDIAGRAMSETTING;
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
		return CSDiagramSettingModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CSDiagramSetting";
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
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("CPAttachmentFileEntryId");
		ctStrictColumnNames.add("CPDefinitionId");
		ctStrictColumnNames.add("color");
		ctStrictColumnNames.add("radius");
		ctStrictColumnNames.add("type_");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CSDiagramSettingId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"CPDefinitionId"});
	}

	/**
	 * Initializes the cs diagram setting persistence.
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

		_finderPathFetchByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCPDefinitionId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionId"}, true);

		_finderPathCountByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPDefinitionId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionId"}, false);

		_setCSDiagramSettingUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setCSDiagramSettingUtilPersistence(null);

		entityCache.removeCache(CSDiagramSettingImpl.class.getName());
	}

	private void _setCSDiagramSettingUtilPersistence(
		CSDiagramSettingPersistence csDiagramSettingPersistence) {

		try {
			Field field = CSDiagramSettingUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, csDiagramSettingPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
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

	private static final String _SQL_SELECT_CSDIAGRAMSETTING =
		"SELECT csDiagramSetting FROM CSDiagramSetting csDiagramSetting";

	private static final String _SQL_SELECT_CSDIAGRAMSETTING_WHERE =
		"SELECT csDiagramSetting FROM CSDiagramSetting csDiagramSetting WHERE ";

	private static final String _SQL_COUNT_CSDIAGRAMSETTING =
		"SELECT COUNT(csDiagramSetting) FROM CSDiagramSetting csDiagramSetting";

	private static final String _SQL_COUNT_CSDIAGRAMSETTING_WHERE =
		"SELECT COUNT(csDiagramSetting) FROM CSDiagramSetting csDiagramSetting WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "csDiagramSetting.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CSDiagramSetting exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CSDiagramSetting exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CSDiagramSettingPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private CSDiagramSettingModelArgumentsResolver
		_csDiagramSettingModelArgumentsResolver;

}