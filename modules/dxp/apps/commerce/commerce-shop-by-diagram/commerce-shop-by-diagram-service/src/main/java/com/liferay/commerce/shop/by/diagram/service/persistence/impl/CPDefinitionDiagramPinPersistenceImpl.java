/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.service.persistence.impl;

import com.liferay.commerce.shop.by.diagram.exception.NoSuchCPDefinitionDiagramPinException;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPinTable;
import com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramPinImpl;
import com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramPinModelImpl;
import com.liferay.commerce.shop.by.diagram.service.persistence.CPDefinitionDiagramPinPersistence;
import com.liferay.commerce.shop.by.diagram.service.persistence.impl.constants.CommercePersistenceConstants;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the cp definition diagram pin service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Sbarra
 * @generated
 */
@Component(
	service = {CPDefinitionDiagramPinPersistence.class, BasePersistence.class}
)
public class CPDefinitionDiagramPinPersistenceImpl
	extends BasePersistenceImpl<CPDefinitionDiagramPin>
	implements CPDefinitionDiagramPinPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPDefinitionDiagramPinUtil</code> to access the cp definition diagram pin persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPDefinitionDiagramPinImpl.class.getName();

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
	 * Returns all the cp definition diagram pins where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram pins
	 */
	@Override
	public List<CPDefinitionDiagramPin> findByCPDefinitionId(
		long CPDefinitionId) {

		return findByCPDefinitionId(
			CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @return the range of matching cp definition diagram pins
	 */
	@Override
	public List<CPDefinitionDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {

		return findByCPDefinitionId(CPDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition diagram pins
	 */
	@Override
	public List<CPDefinitionDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionDiagramPin> orderByComparator) {

		return findByCPDefinitionId(
			CPDefinitionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition diagram pins
	 */
	@Override
	public List<CPDefinitionDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionDiagramPin> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCPDefinitionId;
				finderArgs = new Object[] {CPDefinitionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCPDefinitionId;
			finderArgs = new Object[] {
				CPDefinitionId, start, end, orderByComparator
			};
		}

		List<CPDefinitionDiagramPin> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionDiagramPin>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionDiagramPin cpDefinitionDiagramPin : list) {
					if (CPDefinitionId !=
							cpDefinitionDiagramPin.getCPDefinitionId()) {

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

			sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMPIN_WHERE);

			sb.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDefinitionDiagramPinModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				list = (List<CPDefinitionDiagramPin>)QueryUtil.list(
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
	 * Returns the first cp definition diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram pin
	 * @throws NoSuchCPDefinitionDiagramPinException if a matching cp definition diagram pin could not be found
	 */
	@Override
	public CPDefinitionDiagramPin findByCPDefinitionId_First(
			long CPDefinitionId,
			OrderByComparator<CPDefinitionDiagramPin> orderByComparator)
		throws NoSuchCPDefinitionDiagramPinException {

		CPDefinitionDiagramPin cpDefinitionDiagramPin =
			fetchByCPDefinitionId_First(CPDefinitionId, orderByComparator);

		if (cpDefinitionDiagramPin != null) {
			return cpDefinitionDiagramPin;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionId=");
		sb.append(CPDefinitionId);

		sb.append("}");

		throw new NoSuchCPDefinitionDiagramPinException(sb.toString());
	}

	/**
	 * Returns the first cp definition diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram pin, or <code>null</code> if a matching cp definition diagram pin could not be found
	 */
	@Override
	public CPDefinitionDiagramPin fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionDiagramPin> orderByComparator) {

		List<CPDefinitionDiagramPin> list = findByCPDefinitionId(
			CPDefinitionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram pin
	 * @throws NoSuchCPDefinitionDiagramPinException if a matching cp definition diagram pin could not be found
	 */
	@Override
	public CPDefinitionDiagramPin findByCPDefinitionId_Last(
			long CPDefinitionId,
			OrderByComparator<CPDefinitionDiagramPin> orderByComparator)
		throws NoSuchCPDefinitionDiagramPinException {

		CPDefinitionDiagramPin cpDefinitionDiagramPin =
			fetchByCPDefinitionId_Last(CPDefinitionId, orderByComparator);

		if (cpDefinitionDiagramPin != null) {
			return cpDefinitionDiagramPin;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionId=");
		sb.append(CPDefinitionId);

		sb.append("}");

		throw new NoSuchCPDefinitionDiagramPinException(sb.toString());
	}

	/**
	 * Returns the last cp definition diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram pin, or <code>null</code> if a matching cp definition diagram pin could not be found
	 */
	@Override
	public CPDefinitionDiagramPin fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionDiagramPin> orderByComparator) {

		int count = countByCPDefinitionId(CPDefinitionId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionDiagramPin> list = findByCPDefinitionId(
			CPDefinitionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition diagram pins before and after the current cp definition diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionDiagramPinId the primary key of the current cp definition diagram pin
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition diagram pin
	 * @throws NoSuchCPDefinitionDiagramPinException if a cp definition diagram pin with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramPin[] findByCPDefinitionId_PrevAndNext(
			long CPDefinitionDiagramPinId, long CPDefinitionId,
			OrderByComparator<CPDefinitionDiagramPin> orderByComparator)
		throws NoSuchCPDefinitionDiagramPinException {

		CPDefinitionDiagramPin cpDefinitionDiagramPin = findByPrimaryKey(
			CPDefinitionDiagramPinId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionDiagramPin[] array = new CPDefinitionDiagramPinImpl[3];

			array[0] = getByCPDefinitionId_PrevAndNext(
				session, cpDefinitionDiagramPin, CPDefinitionId,
				orderByComparator, true);

			array[1] = cpDefinitionDiagramPin;

			array[2] = getByCPDefinitionId_PrevAndNext(
				session, cpDefinitionDiagramPin, CPDefinitionId,
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

	protected CPDefinitionDiagramPin getByCPDefinitionId_PrevAndNext(
		Session session, CPDefinitionDiagramPin cpDefinitionDiagramPin,
		long CPDefinitionId,
		OrderByComparator<CPDefinitionDiagramPin> orderByComparator,
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

		sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMPIN_WHERE);

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
			sb.append(CPDefinitionDiagramPinModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPDefinitionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpDefinitionDiagramPin)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionDiagramPin> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition diagram pins where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	@Override
	public void removeByCPDefinitionId(long CPDefinitionId) {
		for (CPDefinitionDiagramPin cpDefinitionDiagramPin :
				findByCPDefinitionId(
					CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpDefinitionDiagramPin);
		}
	}

	/**
	 * Returns the number of cp definition diagram pins where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition diagram pins
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		FinderPath finderPath = _finderPathCountByCPDefinitionId;

		Object[] finderArgs = new Object[] {CPDefinitionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITIONDIAGRAMPIN_WHERE);

			sb.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

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

	private static final String _FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2 =
		"cpDefinitionDiagramPin.CPDefinitionId = ?";

	public CPDefinitionDiagramPinPersistenceImpl() {
		setModelClass(CPDefinitionDiagramPin.class);

		setModelImplClass(CPDefinitionDiagramPinImpl.class);
		setModelPKClass(long.class);

		setTable(CPDefinitionDiagramPinTable.INSTANCE);
	}

	/**
	 * Caches the cp definition diagram pin in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramPin the cp definition diagram pin
	 */
	@Override
	public void cacheResult(CPDefinitionDiagramPin cpDefinitionDiagramPin) {
		entityCache.putResult(
			CPDefinitionDiagramPinImpl.class,
			cpDefinitionDiagramPin.getPrimaryKey(), cpDefinitionDiagramPin);
	}

	/**
	 * Caches the cp definition diagram pins in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramPins the cp definition diagram pins
	 */
	@Override
	public void cacheResult(
		List<CPDefinitionDiagramPin> cpDefinitionDiagramPins) {

		for (CPDefinitionDiagramPin cpDefinitionDiagramPin :
				cpDefinitionDiagramPins) {

			if (entityCache.getResult(
					CPDefinitionDiagramPinImpl.class,
					cpDefinitionDiagramPin.getPrimaryKey()) == null) {

				cacheResult(cpDefinitionDiagramPin);
			}
		}
	}

	/**
	 * Clears the cache for all cp definition diagram pins.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionDiagramPinImpl.class);

		finderCache.clearCache(CPDefinitionDiagramPinImpl.class);
	}

	/**
	 * Clears the cache for the cp definition diagram pin.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPDefinitionDiagramPin cpDefinitionDiagramPin) {
		entityCache.removeResult(
			CPDefinitionDiagramPinImpl.class, cpDefinitionDiagramPin);
	}

	@Override
	public void clearCache(
		List<CPDefinitionDiagramPin> cpDefinitionDiagramPins) {

		for (CPDefinitionDiagramPin cpDefinitionDiagramPin :
				cpDefinitionDiagramPins) {

			entityCache.removeResult(
				CPDefinitionDiagramPinImpl.class, cpDefinitionDiagramPin);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPDefinitionDiagramPinImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPDefinitionDiagramPinImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new cp definition diagram pin with the primary key. Does not add the cp definition diagram pin to the database.
	 *
	 * @param CPDefinitionDiagramPinId the primary key for the new cp definition diagram pin
	 * @return the new cp definition diagram pin
	 */
	@Override
	public CPDefinitionDiagramPin create(long CPDefinitionDiagramPinId) {
		CPDefinitionDiagramPin cpDefinitionDiagramPin =
			new CPDefinitionDiagramPinImpl();

		cpDefinitionDiagramPin.setNew(true);
		cpDefinitionDiagramPin.setPrimaryKey(CPDefinitionDiagramPinId);

		cpDefinitionDiagramPin.setCompanyId(CompanyThreadLocal.getCompanyId());

		return cpDefinitionDiagramPin;
	}

	/**
	 * Removes the cp definition diagram pin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionDiagramPinId the primary key of the cp definition diagram pin
	 * @return the cp definition diagram pin that was removed
	 * @throws NoSuchCPDefinitionDiagramPinException if a cp definition diagram pin with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramPin remove(long CPDefinitionDiagramPinId)
		throws NoSuchCPDefinitionDiagramPinException {

		return remove((Serializable)CPDefinitionDiagramPinId);
	}

	/**
	 * Removes the cp definition diagram pin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition diagram pin
	 * @return the cp definition diagram pin that was removed
	 * @throws NoSuchCPDefinitionDiagramPinException if a cp definition diagram pin with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramPin remove(Serializable primaryKey)
		throws NoSuchCPDefinitionDiagramPinException {

		Session session = null;

		try {
			session = openSession();

			CPDefinitionDiagramPin cpDefinitionDiagramPin =
				(CPDefinitionDiagramPin)session.get(
					CPDefinitionDiagramPinImpl.class, primaryKey);

			if (cpDefinitionDiagramPin == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionDiagramPinException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpDefinitionDiagramPin);
		}
		catch (NoSuchCPDefinitionDiagramPinException noSuchEntityException) {
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
	protected CPDefinitionDiagramPin removeImpl(
		CPDefinitionDiagramPin cpDefinitionDiagramPin) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionDiagramPin)) {
				cpDefinitionDiagramPin = (CPDefinitionDiagramPin)session.get(
					CPDefinitionDiagramPinImpl.class,
					cpDefinitionDiagramPin.getPrimaryKeyObj());
			}

			if (cpDefinitionDiagramPin != null) {
				session.delete(cpDefinitionDiagramPin);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionDiagramPin != null) {
			clearCache(cpDefinitionDiagramPin);
		}

		return cpDefinitionDiagramPin;
	}

	@Override
	public CPDefinitionDiagramPin updateImpl(
		CPDefinitionDiagramPin cpDefinitionDiagramPin) {

		boolean isNew = cpDefinitionDiagramPin.isNew();

		if (!(cpDefinitionDiagramPin instanceof
				CPDefinitionDiagramPinModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpDefinitionDiagramPin.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpDefinitionDiagramPin);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpDefinitionDiagramPin proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDefinitionDiagramPin implementation " +
					cpDefinitionDiagramPin.getClass());
		}

		CPDefinitionDiagramPinModelImpl cpDefinitionDiagramPinModelImpl =
			(CPDefinitionDiagramPinModelImpl)cpDefinitionDiagramPin;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpDefinitionDiagramPin.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDefinitionDiagramPin.setCreateDate(date);
			}
			else {
				cpDefinitionDiagramPin.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpDefinitionDiagramPinModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDefinitionDiagramPin.setModifiedDate(date);
			}
			else {
				cpDefinitionDiagramPin.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(cpDefinitionDiagramPin);
			}
			else {
				cpDefinitionDiagramPin = (CPDefinitionDiagramPin)session.merge(
					cpDefinitionDiagramPin);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPDefinitionDiagramPinImpl.class, cpDefinitionDiagramPinModelImpl,
			false, true);

		if (isNew) {
			cpDefinitionDiagramPin.setNew(false);
		}

		cpDefinitionDiagramPin.resetOriginalValues();

		return cpDefinitionDiagramPin;
	}

	/**
	 * Returns the cp definition diagram pin with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition diagram pin
	 * @return the cp definition diagram pin
	 * @throws NoSuchCPDefinitionDiagramPinException if a cp definition diagram pin with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramPin findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDefinitionDiagramPinException {

		CPDefinitionDiagramPin cpDefinitionDiagramPin = fetchByPrimaryKey(
			primaryKey);

		if (cpDefinitionDiagramPin == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionDiagramPinException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpDefinitionDiagramPin;
	}

	/**
	 * Returns the cp definition diagram pin with the primary key or throws a <code>NoSuchCPDefinitionDiagramPinException</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramPinId the primary key of the cp definition diagram pin
	 * @return the cp definition diagram pin
	 * @throws NoSuchCPDefinitionDiagramPinException if a cp definition diagram pin with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramPin findByPrimaryKey(
			long CPDefinitionDiagramPinId)
		throws NoSuchCPDefinitionDiagramPinException {

		return findByPrimaryKey((Serializable)CPDefinitionDiagramPinId);
	}

	/**
	 * Returns the cp definition diagram pin with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramPinId the primary key of the cp definition diagram pin
	 * @return the cp definition diagram pin, or <code>null</code> if a cp definition diagram pin with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramPin fetchByPrimaryKey(
		long CPDefinitionDiagramPinId) {

		return fetchByPrimaryKey((Serializable)CPDefinitionDiagramPinId);
	}

	/**
	 * Returns all the cp definition diagram pins.
	 *
	 * @return the cp definition diagram pins
	 */
	@Override
	public List<CPDefinitionDiagramPin> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @return the range of cp definition diagram pins
	 */
	@Override
	public List<CPDefinitionDiagramPin> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition diagram pins
	 */
	@Override
	public List<CPDefinitionDiagramPin> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionDiagramPin> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definition diagram pins
	 */
	@Override
	public List<CPDefinitionDiagramPin> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionDiagramPin> orderByComparator,
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

		List<CPDefinitionDiagramPin> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionDiagramPin>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMPIN);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITIONDIAGRAMPIN;

				sql = sql.concat(CPDefinitionDiagramPinModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CPDefinitionDiagramPin>)QueryUtil.list(
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
	 * Removes all the cp definition diagram pins from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionDiagramPin cpDefinitionDiagramPin : findAll()) {
			remove(cpDefinitionDiagramPin);
		}
	}

	/**
	 * Returns the number of cp definition diagram pins.
	 *
	 * @return the number of cp definition diagram pins
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
					_SQL_COUNT_CPDEFINITIONDIAGRAMPIN);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "CPDefinitionDiagramPinId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPDEFINITIONDIAGRAMPIN;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CPDefinitionDiagramPinModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp definition diagram pin persistence.
	 */
	@Activate
	public void activate() {
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
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CPDefinitionDiagramPinImpl.class.getName());
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
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CPDEFINITIONDIAGRAMPIN =
		"SELECT cpDefinitionDiagramPin FROM CPDefinitionDiagramPin cpDefinitionDiagramPin";

	private static final String _SQL_SELECT_CPDEFINITIONDIAGRAMPIN_WHERE =
		"SELECT cpDefinitionDiagramPin FROM CPDefinitionDiagramPin cpDefinitionDiagramPin WHERE ";

	private static final String _SQL_COUNT_CPDEFINITIONDIAGRAMPIN =
		"SELECT COUNT(cpDefinitionDiagramPin) FROM CPDefinitionDiagramPin cpDefinitionDiagramPin";

	private static final String _SQL_COUNT_CPDEFINITIONDIAGRAMPIN_WHERE =
		"SELECT COUNT(cpDefinitionDiagramPin) FROM CPDefinitionDiagramPin cpDefinitionDiagramPin WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpDefinitionDiagramPin.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPDefinitionDiagramPin exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPDefinitionDiagramPin exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionDiagramPinPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private CPDefinitionDiagramPinModelArgumentsResolver
		_cpDefinitionDiagramPinModelArgumentsResolver;

}