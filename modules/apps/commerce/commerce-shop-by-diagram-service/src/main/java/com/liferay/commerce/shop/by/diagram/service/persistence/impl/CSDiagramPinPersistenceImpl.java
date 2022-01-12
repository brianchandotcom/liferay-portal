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

import com.liferay.commerce.shop.by.diagram.exception.NoSuchCSDiagramPinException;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramPin;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramPinTable;
import com.liferay.commerce.shop.by.diagram.model.impl.CSDiagramPinImpl;
import com.liferay.commerce.shop.by.diagram.model.impl.CSDiagramPinModelImpl;
import com.liferay.commerce.shop.by.diagram.service.persistence.CSDiagramPinPersistence;
import com.liferay.commerce.shop.by.diagram.service.persistence.CSDiagramPinUtil;
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
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the cs diagram pin service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(service = {CSDiagramPinPersistence.class, BasePersistence.class})
public class CSDiagramPinPersistenceImpl
	extends BasePersistenceImpl<CSDiagramPin>
	implements CSDiagramPinPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CSDiagramPinUtil</code> to access the cs diagram pin persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CSDiagramPinImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCPDefinitionId;
	private FinderPath _finderPathWithoutPaginationFindByCPDefinitionId;
	private FinderPath _finderPathCountByCPDefinitionId;

	/**
	 * Returns all the cs diagram pins where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cs diagram pins
	 */
	@Override
	public List<CSDiagramPin> findByCPDefinitionId(long CPDefinitionId) {
		return findByCPDefinitionId(
			CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cs diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @return the range of matching cs diagram pins
	 */
	@Override
	public List<CSDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {

		return findByCPDefinitionId(CPDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cs diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cs diagram pins
	 */
	@Override
	public List<CSDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CSDiagramPin> orderByComparator) {

		return findByCPDefinitionId(
			CPDefinitionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cs diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cs diagram pins
	 */
	@Override
	public List<CSDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CSDiagramPin> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CSDiagramPin.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCPDefinitionId;
				finderArgs = new Object[] {CPDefinitionId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCPDefinitionId;
			finderArgs = new Object[] {
				CPDefinitionId, start, end, orderByComparator
			};
		}

		List<CSDiagramPin> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CSDiagramPin>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CSDiagramPin csDiagramPin : list) {
					if (CPDefinitionId != csDiagramPin.getCPDefinitionId()) {
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

			sb.append(_SQL_SELECT_CSDIAGRAMPIN_WHERE);

			sb.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CSDiagramPinModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				list = (List<CSDiagramPin>)QueryUtil.list(
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
	 * Returns the first cs diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram pin
	 * @throws NoSuchCSDiagramPinException if a matching cs diagram pin could not be found
	 */
	@Override
	public CSDiagramPin findByCPDefinitionId_First(
			long CPDefinitionId,
			OrderByComparator<CSDiagramPin> orderByComparator)
		throws NoSuchCSDiagramPinException {

		CSDiagramPin csDiagramPin = fetchByCPDefinitionId_First(
			CPDefinitionId, orderByComparator);

		if (csDiagramPin != null) {
			return csDiagramPin;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionId=");
		sb.append(CPDefinitionId);

		sb.append("}");

		throw new NoSuchCSDiagramPinException(sb.toString());
	}

	/**
	 * Returns the first cs diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram pin, or <code>null</code> if a matching cs diagram pin could not be found
	 */
	@Override
	public CSDiagramPin fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CSDiagramPin> orderByComparator) {

		List<CSDiagramPin> list = findByCPDefinitionId(
			CPDefinitionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cs diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram pin
	 * @throws NoSuchCSDiagramPinException if a matching cs diagram pin could not be found
	 */
	@Override
	public CSDiagramPin findByCPDefinitionId_Last(
			long CPDefinitionId,
			OrderByComparator<CSDiagramPin> orderByComparator)
		throws NoSuchCSDiagramPinException {

		CSDiagramPin csDiagramPin = fetchByCPDefinitionId_Last(
			CPDefinitionId, orderByComparator);

		if (csDiagramPin != null) {
			return csDiagramPin;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionId=");
		sb.append(CPDefinitionId);

		sb.append("}");

		throw new NoSuchCSDiagramPinException(sb.toString());
	}

	/**
	 * Returns the last cs diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram pin, or <code>null</code> if a matching cs diagram pin could not be found
	 */
	@Override
	public CSDiagramPin fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CSDiagramPin> orderByComparator) {

		int count = countByCPDefinitionId(CPDefinitionId);

		if (count == 0) {
			return null;
		}

		List<CSDiagramPin> list = findByCPDefinitionId(
			CPDefinitionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cs diagram pins before and after the current cs diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CSDiagramPinId the primary key of the current cs diagram pin
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cs diagram pin
	 * @throws NoSuchCSDiagramPinException if a cs diagram pin with the primary key could not be found
	 */
	@Override
	public CSDiagramPin[] findByCPDefinitionId_PrevAndNext(
			long CSDiagramPinId, long CPDefinitionId,
			OrderByComparator<CSDiagramPin> orderByComparator)
		throws NoSuchCSDiagramPinException {

		CSDiagramPin csDiagramPin = findByPrimaryKey(CSDiagramPinId);

		Session session = null;

		try {
			session = openSession();

			CSDiagramPin[] array = new CSDiagramPinImpl[3];

			array[0] = getByCPDefinitionId_PrevAndNext(
				session, csDiagramPin, CPDefinitionId, orderByComparator, true);

			array[1] = csDiagramPin;

			array[2] = getByCPDefinitionId_PrevAndNext(
				session, csDiagramPin, CPDefinitionId, orderByComparator,
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

	protected CSDiagramPin getByCPDefinitionId_PrevAndNext(
		Session session, CSDiagramPin csDiagramPin, long CPDefinitionId,
		OrderByComparator<CSDiagramPin> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CSDIAGRAMPIN_WHERE);

		sb.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

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
			sb.append(CSDiagramPinModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPDefinitionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(csDiagramPin)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CSDiagramPin> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cs diagram pins where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	@Override
	public void removeByCPDefinitionId(long CPDefinitionId) {
		for (CSDiagramPin csDiagramPin :
				findByCPDefinitionId(
					CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(csDiagramPin);
		}
	}

	/**
	 * Returns the number of cs diagram pins where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cs diagram pins
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CSDiagramPin.class);

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

			sb.append(_SQL_COUNT_CSDIAGRAMPIN_WHERE);

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
		"csDiagramPin.CPDefinitionId = ?";

	public CSDiagramPinPersistenceImpl() {
		setModelClass(CSDiagramPin.class);

		setModelImplClass(CSDiagramPinImpl.class);
		setModelPKClass(long.class);

		setTable(CSDiagramPinTable.INSTANCE);
	}

	/**
	 * Caches the cs diagram pin in the entity cache if it is enabled.
	 *
	 * @param csDiagramPin the cs diagram pin
	 */
	@Override
	public void cacheResult(CSDiagramPin csDiagramPin) {
		if (csDiagramPin.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			CSDiagramPinImpl.class, csDiagramPin.getPrimaryKey(), csDiagramPin);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cs diagram pins in the entity cache if it is enabled.
	 *
	 * @param csDiagramPins the cs diagram pins
	 */
	@Override
	public void cacheResult(List<CSDiagramPin> csDiagramPins) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (csDiagramPins.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CSDiagramPin csDiagramPin : csDiagramPins) {
			if (csDiagramPin.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					CSDiagramPinImpl.class, csDiagramPin.getPrimaryKey()) ==
						null) {

				cacheResult(csDiagramPin);
			}
		}
	}

	/**
	 * Clears the cache for all cs diagram pins.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CSDiagramPinImpl.class);

		finderCache.clearCache(CSDiagramPinImpl.class);
	}

	/**
	 * Clears the cache for the cs diagram pin.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CSDiagramPin csDiagramPin) {
		entityCache.removeResult(CSDiagramPinImpl.class, csDiagramPin);
	}

	@Override
	public void clearCache(List<CSDiagramPin> csDiagramPins) {
		for (CSDiagramPin csDiagramPin : csDiagramPins) {
			entityCache.removeResult(CSDiagramPinImpl.class, csDiagramPin);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CSDiagramPinImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CSDiagramPinImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new cs diagram pin with the primary key. Does not add the cs diagram pin to the database.
	 *
	 * @param CSDiagramPinId the primary key for the new cs diagram pin
	 * @return the new cs diagram pin
	 */
	@Override
	public CSDiagramPin create(long CSDiagramPinId) {
		CSDiagramPin csDiagramPin = new CSDiagramPinImpl();

		csDiagramPin.setNew(true);
		csDiagramPin.setPrimaryKey(CSDiagramPinId);

		csDiagramPin.setCompanyId(CompanyThreadLocal.getCompanyId());

		return csDiagramPin;
	}

	/**
	 * Removes the cs diagram pin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CSDiagramPinId the primary key of the cs diagram pin
	 * @return the cs diagram pin that was removed
	 * @throws NoSuchCSDiagramPinException if a cs diagram pin with the primary key could not be found
	 */
	@Override
	public CSDiagramPin remove(long CSDiagramPinId)
		throws NoSuchCSDiagramPinException {

		return remove((Serializable)CSDiagramPinId);
	}

	/**
	 * Removes the cs diagram pin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cs diagram pin
	 * @return the cs diagram pin that was removed
	 * @throws NoSuchCSDiagramPinException if a cs diagram pin with the primary key could not be found
	 */
	@Override
	public CSDiagramPin remove(Serializable primaryKey)
		throws NoSuchCSDiagramPinException {

		Session session = null;

		try {
			session = openSession();

			CSDiagramPin csDiagramPin = (CSDiagramPin)session.get(
				CSDiagramPinImpl.class, primaryKey);

			if (csDiagramPin == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCSDiagramPinException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(csDiagramPin);
		}
		catch (NoSuchCSDiagramPinException noSuchEntityException) {
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
	protected CSDiagramPin removeImpl(CSDiagramPin csDiagramPin) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(csDiagramPin)) {
				csDiagramPin = (CSDiagramPin)session.get(
					CSDiagramPinImpl.class, csDiagramPin.getPrimaryKeyObj());
			}

			if ((csDiagramPin != null) &&
				ctPersistenceHelper.isRemove(csDiagramPin)) {

				session.delete(csDiagramPin);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (csDiagramPin != null) {
			clearCache(csDiagramPin);
		}

		return csDiagramPin;
	}

	@Override
	public CSDiagramPin updateImpl(CSDiagramPin csDiagramPin) {
		boolean isNew = csDiagramPin.isNew();

		if (!(csDiagramPin instanceof CSDiagramPinModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(csDiagramPin.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					csDiagramPin);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in csDiagramPin proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CSDiagramPin implementation " +
					csDiagramPin.getClass());
		}

		CSDiagramPinModelImpl csDiagramPinModelImpl =
			(CSDiagramPinModelImpl)csDiagramPin;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (csDiagramPin.getCreateDate() == null)) {
			if (serviceContext == null) {
				csDiagramPin.setCreateDate(date);
			}
			else {
				csDiagramPin.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!csDiagramPinModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				csDiagramPin.setModifiedDate(date);
			}
			else {
				csDiagramPin.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(csDiagramPin)) {
				if (!isNew) {
					session.evict(
						CSDiagramPinImpl.class,
						csDiagramPin.getPrimaryKeyObj());
				}

				session.save(csDiagramPin);
			}
			else {
				csDiagramPin = (CSDiagramPin)session.merge(csDiagramPin);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (csDiagramPin.getCtCollectionId() != 0) {
			if (isNew) {
				csDiagramPin.setNew(false);
			}

			csDiagramPin.resetOriginalValues();

			return csDiagramPin;
		}

		entityCache.putResult(
			CSDiagramPinImpl.class, csDiagramPinModelImpl, false, true);

		if (isNew) {
			csDiagramPin.setNew(false);
		}

		csDiagramPin.resetOriginalValues();

		return csDiagramPin;
	}

	/**
	 * Returns the cs diagram pin with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cs diagram pin
	 * @return the cs diagram pin
	 * @throws NoSuchCSDiagramPinException if a cs diagram pin with the primary key could not be found
	 */
	@Override
	public CSDiagramPin findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCSDiagramPinException {

		CSDiagramPin csDiagramPin = fetchByPrimaryKey(primaryKey);

		if (csDiagramPin == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCSDiagramPinException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return csDiagramPin;
	}

	/**
	 * Returns the cs diagram pin with the primary key or throws a <code>NoSuchCSDiagramPinException</code> if it could not be found.
	 *
	 * @param CSDiagramPinId the primary key of the cs diagram pin
	 * @return the cs diagram pin
	 * @throws NoSuchCSDiagramPinException if a cs diagram pin with the primary key could not be found
	 */
	@Override
	public CSDiagramPin findByPrimaryKey(long CSDiagramPinId)
		throws NoSuchCSDiagramPinException {

		return findByPrimaryKey((Serializable)CSDiagramPinId);
	}

	/**
	 * Returns the cs diagram pin with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cs diagram pin
	 * @return the cs diagram pin, or <code>null</code> if a cs diagram pin with the primary key could not be found
	 */
	@Override
	public CSDiagramPin fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(CSDiagramPin.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		CSDiagramPin csDiagramPin = null;

		Session session = null;

		try {
			session = openSession();

			csDiagramPin = (CSDiagramPin)session.get(
				CSDiagramPinImpl.class, primaryKey);

			if (csDiagramPin != null) {
				cacheResult(csDiagramPin);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return csDiagramPin;
	}

	/**
	 * Returns the cs diagram pin with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CSDiagramPinId the primary key of the cs diagram pin
	 * @return the cs diagram pin, or <code>null</code> if a cs diagram pin with the primary key could not be found
	 */
	@Override
	public CSDiagramPin fetchByPrimaryKey(long CSDiagramPinId) {
		return fetchByPrimaryKey((Serializable)CSDiagramPinId);
	}

	@Override
	public Map<Serializable, CSDiagramPin> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(CSDiagramPin.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CSDiagramPin> map =
			new HashMap<Serializable, CSDiagramPin>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CSDiagramPin csDiagramPin = fetchByPrimaryKey(primaryKey);

			if (csDiagramPin != null) {
				map.put(primaryKey, csDiagramPin);
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

			for (CSDiagramPin csDiagramPin : (List<CSDiagramPin>)query.list()) {
				map.put(csDiagramPin.getPrimaryKeyObj(), csDiagramPin);

				cacheResult(csDiagramPin);
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
	 * Returns all the cs diagram pins.
	 *
	 * @return the cs diagram pins
	 */
	@Override
	public List<CSDiagramPin> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cs diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @return the range of cs diagram pins
	 */
	@Override
	public List<CSDiagramPin> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cs diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cs diagram pins
	 */
	@Override
	public List<CSDiagramPin> findAll(
		int start, int end, OrderByComparator<CSDiagramPin> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cs diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cs diagram pins
	 */
	@Override
	public List<CSDiagramPin> findAll(
		int start, int end, OrderByComparator<CSDiagramPin> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CSDiagramPin.class);

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

		List<CSDiagramPin> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CSDiagramPin>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CSDIAGRAMPIN);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CSDIAGRAMPIN;

				sql = sql.concat(CSDiagramPinModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CSDiagramPin>)QueryUtil.list(
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
	 * Removes all the cs diagram pins from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CSDiagramPin csDiagramPin : findAll()) {
			remove(csDiagramPin);
		}
	}

	/**
	 * Returns the number of cs diagram pins.
	 *
	 * @return the number of cs diagram pins
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CSDiagramPin.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CSDIAGRAMPIN);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "CSDiagramPinId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CSDIAGRAMPIN;
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
		return CSDiagramPinModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CSDiagramPin";
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
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("CPDefinitionId");
		ctStrictColumnNames.add("positionX");
		ctStrictColumnNames.add("positionY");
		ctStrictColumnNames.add("sequence");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("CSDiagramPinId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the cs diagram pin persistence.
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

		_finderPathWithPaginationFindByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPDefinitionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPDefinitionId"}, true);

		_finderPathWithoutPaginationFindByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPDefinitionId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionId"}, true);

		_finderPathCountByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPDefinitionId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionId"}, false);

		_setCSDiagramPinUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setCSDiagramPinUtilPersistence(null);

		entityCache.removeCache(CSDiagramPinImpl.class.getName());
	}

	private void _setCSDiagramPinUtilPersistence(
		CSDiagramPinPersistence csDiagramPinPersistence) {

		try {
			Field field = CSDiagramPinUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, csDiagramPinPersistence);
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

	private static final String _SQL_SELECT_CSDIAGRAMPIN =
		"SELECT csDiagramPin FROM CSDiagramPin csDiagramPin";

	private static final String _SQL_SELECT_CSDIAGRAMPIN_WHERE =
		"SELECT csDiagramPin FROM CSDiagramPin csDiagramPin WHERE ";

	private static final String _SQL_COUNT_CSDIAGRAMPIN =
		"SELECT COUNT(csDiagramPin) FROM CSDiagramPin csDiagramPin";

	private static final String _SQL_COUNT_CSDIAGRAMPIN_WHERE =
		"SELECT COUNT(csDiagramPin) FROM CSDiagramPin csDiagramPin WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "csDiagramPin.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CSDiagramPin exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CSDiagramPin exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CSDiagramPinPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private CSDiagramPinModelArgumentsResolver
		_csDiagramPinModelArgumentsResolver;

}