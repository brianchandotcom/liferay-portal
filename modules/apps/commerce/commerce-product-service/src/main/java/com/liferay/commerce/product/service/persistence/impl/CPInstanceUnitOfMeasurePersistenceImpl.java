/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.exception.NoSuchCPInstanceUnitOfMeasureException;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasureTable;
import com.liferay.commerce.product.model.impl.CPInstanceUnitOfMeasureImpl;
import com.liferay.commerce.product.model.impl.CPInstanceUnitOfMeasureModelImpl;
import com.liferay.commerce.product.service.persistence.CPInstanceUnitOfMeasurePersistence;
import com.liferay.commerce.product.service.persistence.CPInstanceUnitOfMeasureUtil;
import com.liferay.commerce.product.service.persistence.impl.constants.CommercePersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;

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
 * The persistence implementation for the cp instance unit of measure service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = CPInstanceUnitOfMeasurePersistence.class)
public class CPInstanceUnitOfMeasurePersistenceImpl
	extends BasePersistenceImpl<CPInstanceUnitOfMeasure>
	implements CPInstanceUnitOfMeasurePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPInstanceUnitOfMeasureUtil</code> to access the cp instance unit of measure persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPInstanceUnitOfMeasureImpl.class.getName();

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
	 * Returns all the cp instance unit of measures where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp instance unit of measures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp instance unit of measures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp instance unit of measures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

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

		List<CPInstanceUnitOfMeasure> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPInstanceUnitOfMeasure>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure : list) {
					if (!uuid.equals(cpInstanceUnitOfMeasure.getUuid())) {
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

			sb.append(_SQL_SELECT_CPINSTANCEUNITOFMEASURE_WHERE);

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
				sb.append(CPInstanceUnitOfMeasureModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPInstanceUnitOfMeasure>)QueryUtil.list(
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
	 * Returns the first cp instance unit of measure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByUuid_First(
			String uuid,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = fetchByUuid_First(
			uuid, orderByComparator);

		if (cpInstanceUnitOfMeasure != null) {
			return cpInstanceUnitOfMeasure;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPInstanceUnitOfMeasureException(sb.toString());
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByUuid_First(
		String uuid,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		List<CPInstanceUnitOfMeasure> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByUuid_Last(
			String uuid,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = fetchByUuid_Last(
			uuid, orderByComparator);

		if (cpInstanceUnitOfMeasure != null) {
			return cpInstanceUnitOfMeasure;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPInstanceUnitOfMeasureException(sb.toString());
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByUuid_Last(
		String uuid,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPInstanceUnitOfMeasure> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where uuid = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure[] findByUuid_PrevAndNext(
			long CPInstanceUnitOfMeasureId, String uuid,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		uuid = Objects.toString(uuid, "");

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = findByPrimaryKey(
			CPInstanceUnitOfMeasureId);

		Session session = null;

		try {
			session = openSession();

			CPInstanceUnitOfMeasure[] array =
				new CPInstanceUnitOfMeasureImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, cpInstanceUnitOfMeasure, uuid, orderByComparator,
				true);

			array[1] = cpInstanceUnitOfMeasure;

			array[2] = getByUuid_PrevAndNext(
				session, cpInstanceUnitOfMeasure, uuid, orderByComparator,
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

	protected CPInstanceUnitOfMeasure getByUuid_PrevAndNext(
		Session session, CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure,
		String uuid,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
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

		sb.append(_SQL_SELECT_CPINSTANCEUNITOFMEASURE_WHERE);

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
			sb.append(CPInstanceUnitOfMeasureModelImpl.ORDER_BY_JPQL);
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
						cpInstanceUnitOfMeasure)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPInstanceUnitOfMeasure> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp instance unit of measures where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpInstanceUnitOfMeasure);
		}
	}

	/**
	 * Returns the number of cp instance unit of measures where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp instance unit of measures
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPINSTANCEUNITOFMEASURE_WHERE);

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
		"cpInstanceUnitOfMeasure.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(cpInstanceUnitOfMeasure.uuid IS NULL OR cpInstanceUnitOfMeasure.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the cp instance unit of measures where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp instance unit of measures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp instance unit of measures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp instance unit of measures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

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

		List<CPInstanceUnitOfMeasure> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPInstanceUnitOfMeasure>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure : list) {
					if (!uuid.equals(cpInstanceUnitOfMeasure.getUuid()) ||
						(companyId != cpInstanceUnitOfMeasure.getCompanyId())) {

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

			sb.append(_SQL_SELECT_CPINSTANCEUNITOFMEASURE_WHERE);

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
				sb.append(CPInstanceUnitOfMeasureModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPInstanceUnitOfMeasure>)QueryUtil.list(
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
	 * Returns the first cp instance unit of measure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (cpInstanceUnitOfMeasure != null) {
			return cpInstanceUnitOfMeasure;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPInstanceUnitOfMeasureException(sb.toString());
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		List<CPInstanceUnitOfMeasure> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (cpInstanceUnitOfMeasure != null) {
			return cpInstanceUnitOfMeasure;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPInstanceUnitOfMeasureException(sb.toString());
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPInstanceUnitOfMeasure> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure[] findByUuid_C_PrevAndNext(
			long CPInstanceUnitOfMeasureId, String uuid, long companyId,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		uuid = Objects.toString(uuid, "");

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = findByPrimaryKey(
			CPInstanceUnitOfMeasureId);

		Session session = null;

		try {
			session = openSession();

			CPInstanceUnitOfMeasure[] array =
				new CPInstanceUnitOfMeasureImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, cpInstanceUnitOfMeasure, uuid, companyId,
				orderByComparator, true);

			array[1] = cpInstanceUnitOfMeasure;

			array[2] = getByUuid_C_PrevAndNext(
				session, cpInstanceUnitOfMeasure, uuid, companyId,
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

	protected CPInstanceUnitOfMeasure getByUuid_C_PrevAndNext(
		Session session, CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure,
		String uuid, long companyId,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
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

		sb.append(_SQL_SELECT_CPINSTANCEUNITOFMEASURE_WHERE);

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
			sb.append(CPInstanceUnitOfMeasureModelImpl.ORDER_BY_JPQL);
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
						cpInstanceUnitOfMeasure)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPInstanceUnitOfMeasure> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp instance unit of measures where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpInstanceUnitOfMeasure);
		}
	}

	/**
	 * Returns the number of cp instance unit of measures where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp instance unit of measures
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPINSTANCEUNITOFMEASURE_WHERE);

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
		"cpInstanceUnitOfMeasure.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(cpInstanceUnitOfMeasure.uuid IS NULL OR cpInstanceUnitOfMeasure.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"cpInstanceUnitOfMeasure.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCPInstanceId;
	private FinderPath _finderPathWithoutPaginationFindByCPInstanceId;
	private FinderPath _finderPathCountByCPInstanceId;

	/**
	 * Returns all the cp instance unit of measures where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByCPInstanceId(long CPInstanceId) {
		return findByCPInstanceId(
			CPInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp instance unit of measures where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByCPInstanceId(
		long CPInstanceId, int start, int end) {

		return findByCPInstanceId(CPInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp instance unit of measures where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return findByCPInstanceId(
			CPInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp instance unit of measures where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCPInstanceId;
				finderArgs = new Object[] {CPInstanceId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCPInstanceId;
			finderArgs = new Object[] {
				CPInstanceId, start, end, orderByComparator
			};
		}

		List<CPInstanceUnitOfMeasure> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPInstanceUnitOfMeasure>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure : list) {
					if (CPInstanceId !=
							cpInstanceUnitOfMeasure.getCPInstanceId()) {

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

			sb.append(_SQL_SELECT_CPINSTANCEUNITOFMEASURE_WHERE);

			sb.append(_FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPInstanceUnitOfMeasureModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPInstanceId);

				list = (List<CPInstanceUnitOfMeasure>)QueryUtil.list(
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
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByCPInstanceId_First(
			long CPInstanceId,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			fetchByCPInstanceId_First(CPInstanceId, orderByComparator);

		if (cpInstanceUnitOfMeasure != null) {
			return cpInstanceUnitOfMeasure;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPInstanceId=");
		sb.append(CPInstanceId);

		sb.append("}");

		throw new NoSuchCPInstanceUnitOfMeasureException(sb.toString());
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByCPInstanceId_First(
		long CPInstanceId,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		List<CPInstanceUnitOfMeasure> list = findByCPInstanceId(
			CPInstanceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByCPInstanceId_Last(
			long CPInstanceId,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			fetchByCPInstanceId_Last(CPInstanceId, orderByComparator);

		if (cpInstanceUnitOfMeasure != null) {
			return cpInstanceUnitOfMeasure;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPInstanceId=");
		sb.append(CPInstanceId);

		sb.append("}");

		throw new NoSuchCPInstanceUnitOfMeasureException(sb.toString());
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByCPInstanceId_Last(
		long CPInstanceId,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		int count = countByCPInstanceId(CPInstanceId);

		if (count == 0) {
			return null;
		}

		List<CPInstanceUnitOfMeasure> list = findByCPInstanceId(
			CPInstanceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure[] findByCPInstanceId_PrevAndNext(
			long CPInstanceUnitOfMeasureId, long CPInstanceId,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = findByPrimaryKey(
			CPInstanceUnitOfMeasureId);

		Session session = null;

		try {
			session = openSession();

			CPInstanceUnitOfMeasure[] array =
				new CPInstanceUnitOfMeasureImpl[3];

			array[0] = getByCPInstanceId_PrevAndNext(
				session, cpInstanceUnitOfMeasure, CPInstanceId,
				orderByComparator, true);

			array[1] = cpInstanceUnitOfMeasure;

			array[2] = getByCPInstanceId_PrevAndNext(
				session, cpInstanceUnitOfMeasure, CPInstanceId,
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

	protected CPInstanceUnitOfMeasure getByCPInstanceId_PrevAndNext(
		Session session, CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure,
		long CPInstanceId,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
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

		sb.append(_SQL_SELECT_CPINSTANCEUNITOFMEASURE_WHERE);

		sb.append(_FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2);

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
			sb.append(CPInstanceUnitOfMeasureModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPInstanceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpInstanceUnitOfMeasure)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPInstanceUnitOfMeasure> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp instance unit of measures where CPInstanceId = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 */
	@Override
	public void removeByCPInstanceId(long CPInstanceId) {
		for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure :
				findByCPInstanceId(
					CPInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpInstanceUnitOfMeasure);
		}
	}

	/**
	 * Returns the number of cp instance unit of measures where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching cp instance unit of measures
	 */
	@Override
	public int countByCPInstanceId(long CPInstanceId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCPInstanceId;

			finderArgs = new Object[] {CPInstanceId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPINSTANCEUNITOFMEASURE_WHERE);

			sb.append(_FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPInstanceId);

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

	private static final String _FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2 =
		"cpInstanceUnitOfMeasure.CPInstanceId = ?";

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;

	/**
	 * Returns all the cp instance unit of measures where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByC_S(long companyId, String sku) {
		return findByC_S(
			companyId, sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp instance unit of measures where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByC_S(
		long companyId, String sku, int start, int end) {

		return findByC_S(companyId, sku, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp instance unit of measures where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByC_S(
		long companyId, String sku, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return findByC_S(companyId, sku, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp instance unit of measures where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByC_S(
		long companyId, String sku, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache) {

		sku = Objects.toString(sku, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_S;
				finderArgs = new Object[] {companyId, sku};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_S;
			finderArgs = new Object[] {
				companyId, sku, start, end, orderByComparator
			};
		}

		List<CPInstanceUnitOfMeasure> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPInstanceUnitOfMeasure>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure : list) {
					if ((companyId != cpInstanceUnitOfMeasure.getCompanyId()) ||
						!sku.equals(cpInstanceUnitOfMeasure.getSku())) {

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

			sb.append(_SQL_SELECT_CPINSTANCEUNITOFMEASURE_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			boolean bindSku = false;

			if (sku.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_S_SKU_3);
			}
			else {
				bindSku = true;

				sb.append(_FINDER_COLUMN_C_S_SKU_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPInstanceUnitOfMeasureModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindSku) {
					queryPos.add(sku);
				}

				list = (List<CPInstanceUnitOfMeasure>)QueryUtil.list(
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
	 * Returns the first cp instance unit of measure in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByC_S_First(
			long companyId, String sku,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = fetchByC_S_First(
			companyId, sku, orderByComparator);

		if (cpInstanceUnitOfMeasure != null) {
			return cpInstanceUnitOfMeasure;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", sku=");
		sb.append(sku);

		sb.append("}");

		throw new NoSuchCPInstanceUnitOfMeasureException(sb.toString());
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByC_S_First(
		long companyId, String sku,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		List<CPInstanceUnitOfMeasure> list = findByC_S(
			companyId, sku, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByC_S_Last(
			long companyId, String sku,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = fetchByC_S_Last(
			companyId, sku, orderByComparator);

		if (cpInstanceUnitOfMeasure != null) {
			return cpInstanceUnitOfMeasure;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", sku=");
		sb.append(sku);

		sb.append("}");

		throw new NoSuchCPInstanceUnitOfMeasureException(sb.toString());
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByC_S_Last(
		long companyId, String sku,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		int count = countByC_S(companyId, sku);

		if (count == 0) {
			return null;
		}

		List<CPInstanceUnitOfMeasure> list = findByC_S(
			companyId, sku, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure[] findByC_S_PrevAndNext(
			long CPInstanceUnitOfMeasureId, long companyId, String sku,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		sku = Objects.toString(sku, "");

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = findByPrimaryKey(
			CPInstanceUnitOfMeasureId);

		Session session = null;

		try {
			session = openSession();

			CPInstanceUnitOfMeasure[] array =
				new CPInstanceUnitOfMeasureImpl[3];

			array[0] = getByC_S_PrevAndNext(
				session, cpInstanceUnitOfMeasure, companyId, sku,
				orderByComparator, true);

			array[1] = cpInstanceUnitOfMeasure;

			array[2] = getByC_S_PrevAndNext(
				session, cpInstanceUnitOfMeasure, companyId, sku,
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

	protected CPInstanceUnitOfMeasure getByC_S_PrevAndNext(
		Session session, CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure,
		long companyId, String sku,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
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

		sb.append(_SQL_SELECT_CPINSTANCEUNITOFMEASURE_WHERE);

		sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		boolean bindSku = false;

		if (sku.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_S_SKU_3);
		}
		else {
			bindSku = true;

			sb.append(_FINDER_COLUMN_C_S_SKU_2);
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
			sb.append(CPInstanceUnitOfMeasureModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindSku) {
			queryPos.add(sku);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpInstanceUnitOfMeasure)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPInstanceUnitOfMeasure> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp instance unit of measures where companyId = &#63; and sku = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 */
	@Override
	public void removeByC_S(long companyId, String sku) {
		for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure :
				findByC_S(
					companyId, sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpInstanceUnitOfMeasure);
		}
	}

	/**
	 * Returns the number of cp instance unit of measures where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the number of matching cp instance unit of measures
	 */
	@Override
	public int countByC_S(long companyId, String sku) {
		sku = Objects.toString(sku, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_S;

			finderArgs = new Object[] {companyId, sku};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPINSTANCEUNITOFMEASURE_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			boolean bindSku = false;

			if (sku.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_S_SKU_3);
			}
			else {
				bindSku = true;

				sb.append(_FINDER_COLUMN_C_S_SKU_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindSku) {
					queryPos.add(sku);
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

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 =
		"cpInstanceUnitOfMeasure.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_SKU_2 =
		"cpInstanceUnitOfMeasure.sku = ?";

	private static final String _FINDER_COLUMN_C_S_SKU_3 =
		"(cpInstanceUnitOfMeasure.sku IS NULL OR cpInstanceUnitOfMeasure.sku = '')";

	private FinderPath _finderPathWithPaginationFindByC_A;
	private FinderPath _finderPathWithoutPaginationFindByC_A;
	private FinderPath _finderPathCountByC_A;

	/**
	 * Returns all the cp instance unit of measures where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @return the matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByC_A(
		long CPInstanceId, boolean active) {

		return findByC_A(
			CPInstanceId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp instance unit of measures where CPInstanceId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByC_A(
		long CPInstanceId, boolean active, int start, int end) {

		return findByC_A(CPInstanceId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp instance unit of measures where CPInstanceId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByC_A(
		long CPInstanceId, boolean active, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return findByC_A(
			CPInstanceId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp instance unit of measures where CPInstanceId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByC_A(
		long CPInstanceId, boolean active, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_A;
				finderArgs = new Object[] {CPInstanceId, active};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_A;
			finderArgs = new Object[] {
				CPInstanceId, active, start, end, orderByComparator
			};
		}

		List<CPInstanceUnitOfMeasure> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPInstanceUnitOfMeasure>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure : list) {
					if ((CPInstanceId !=
							cpInstanceUnitOfMeasure.getCPInstanceId()) ||
						(active != cpInstanceUnitOfMeasure.isActive())) {

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

			sb.append(_SQL_SELECT_CPINSTANCEUNITOFMEASURE_WHERE);

			sb.append(_FINDER_COLUMN_C_A_CPINSTANCEID_2);

			sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPInstanceUnitOfMeasureModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPInstanceId);

				queryPos.add(active);

				list = (List<CPInstanceUnitOfMeasure>)QueryUtil.list(
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
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByC_A_First(
			long CPInstanceId, boolean active,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = fetchByC_A_First(
			CPInstanceId, active, orderByComparator);

		if (cpInstanceUnitOfMeasure != null) {
			return cpInstanceUnitOfMeasure;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPInstanceId=");
		sb.append(CPInstanceId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchCPInstanceUnitOfMeasureException(sb.toString());
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByC_A_First(
		long CPInstanceId, boolean active,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		List<CPInstanceUnitOfMeasure> list = findByC_A(
			CPInstanceId, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByC_A_Last(
			long CPInstanceId, boolean active,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = fetchByC_A_Last(
			CPInstanceId, active, orderByComparator);

		if (cpInstanceUnitOfMeasure != null) {
			return cpInstanceUnitOfMeasure;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPInstanceId=");
		sb.append(CPInstanceId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchCPInstanceUnitOfMeasureException(sb.toString());
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByC_A_Last(
		long CPInstanceId, boolean active,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		int count = countByC_A(CPInstanceId, active);

		if (count == 0) {
			return null;
		}

		List<CPInstanceUnitOfMeasure> list = findByC_A(
			CPInstanceId, active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure[] findByC_A_PrevAndNext(
			long CPInstanceUnitOfMeasureId, long CPInstanceId, boolean active,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = findByPrimaryKey(
			CPInstanceUnitOfMeasureId);

		Session session = null;

		try {
			session = openSession();

			CPInstanceUnitOfMeasure[] array =
				new CPInstanceUnitOfMeasureImpl[3];

			array[0] = getByC_A_PrevAndNext(
				session, cpInstanceUnitOfMeasure, CPInstanceId, active,
				orderByComparator, true);

			array[1] = cpInstanceUnitOfMeasure;

			array[2] = getByC_A_PrevAndNext(
				session, cpInstanceUnitOfMeasure, CPInstanceId, active,
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

	protected CPInstanceUnitOfMeasure getByC_A_PrevAndNext(
		Session session, CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure,
		long CPInstanceId, boolean active,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
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

		sb.append(_SQL_SELECT_CPINSTANCEUNITOFMEASURE_WHERE);

		sb.append(_FINDER_COLUMN_C_A_CPINSTANCEID_2);

		sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

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
			sb.append(CPInstanceUnitOfMeasureModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPInstanceId);

		queryPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpInstanceUnitOfMeasure)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPInstanceUnitOfMeasure> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp instance unit of measures where CPInstanceId = &#63; and active = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 */
	@Override
	public void removeByC_A(long CPInstanceId, boolean active) {
		for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure :
				findByC_A(
					CPInstanceId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpInstanceUnitOfMeasure);
		}
	}

	/**
	 * Returns the number of cp instance unit of measures where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @return the number of matching cp instance unit of measures
	 */
	@Override
	public int countByC_A(long CPInstanceId, boolean active) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_A;

			finderArgs = new Object[] {CPInstanceId, active};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPINSTANCEUNITOFMEASURE_WHERE);

			sb.append(_FINDER_COLUMN_C_A_CPINSTANCEID_2);

			sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPInstanceId);

				queryPos.add(active);

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

	private static final String _FINDER_COLUMN_C_A_CPINSTANCEID_2 =
		"cpInstanceUnitOfMeasure.CPInstanceId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_ACTIVE_2 =
		"cpInstanceUnitOfMeasure.active = ?";

	private FinderPath _finderPathFetchByC_K;
	private FinderPath _finderPathCountByC_K;

	/**
	 * Returns the cp instance unit of measure where CPInstanceId = &#63; and key = &#63; or throws a <code>NoSuchCPInstanceUnitOfMeasureException</code> if it could not be found.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param key the key
	 * @return the matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByC_K(long CPInstanceId, String key)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = fetchByC_K(
			CPInstanceId, key);

		if (cpInstanceUnitOfMeasure == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CPInstanceId=");
			sb.append(CPInstanceId);

			sb.append(", key=");
			sb.append(key);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPInstanceUnitOfMeasureException(sb.toString());
		}

		return cpInstanceUnitOfMeasure;
	}

	/**
	 * Returns the cp instance unit of measure where CPInstanceId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param key the key
	 * @return the matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByC_K(long CPInstanceId, String key) {
		return fetchByC_K(CPInstanceId, key, true);
	}

	/**
	 * Returns the cp instance unit of measure where CPInstanceId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByC_K(
		long CPInstanceId, String key, boolean useFinderCache) {

		key = Objects.toString(key, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {CPInstanceId, key};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_K, finderArgs, this);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

		if (result instanceof CPInstanceUnitOfMeasure) {
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
				(CPInstanceUnitOfMeasure)result;

			if ((CPInstanceId != cpInstanceUnitOfMeasure.getCPInstanceId()) ||
				!Objects.equals(key, cpInstanceUnitOfMeasure.getKey())) {

				result = null;
			}
			else if (!ctPersistenceHelper.isProductionMode(
						CPInstanceUnitOfMeasure.class,
						cpInstanceUnitOfMeasure.getPrimaryKey())) {

				result = null;
			}
		}
		else if (!productionMode && (result instanceof List<?>)) {
			result = null;
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPINSTANCEUNITOFMEASURE_WHERE);

			sb.append(_FINDER_COLUMN_C_K_CPINSTANCEID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_C_K_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPInstanceId);

				if (bindKey) {
					queryPos.add(key);
				}

				List<CPInstanceUnitOfMeasure> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByC_K, finderArgs, list);
					}
				}
				else {
					CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = list.get(
						0);

					result = cpInstanceUnitOfMeasure;

					cacheResult(cpInstanceUnitOfMeasure);
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
			return (CPInstanceUnitOfMeasure)result;
		}
	}

	/**
	 * Removes the cp instance unit of measure where CPInstanceId = &#63; and key = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param key the key
	 * @return the cp instance unit of measure that was removed
	 */
	@Override
	public CPInstanceUnitOfMeasure removeByC_K(long CPInstanceId, String key)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = findByC_K(
			CPInstanceId, key);

		return remove(cpInstanceUnitOfMeasure);
	}

	/**
	 * Returns the number of cp instance unit of measures where CPInstanceId = &#63; and key = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param key the key
	 * @return the number of matching cp instance unit of measures
	 */
	@Override
	public int countByC_K(long CPInstanceId, String key) {
		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_K;

			finderArgs = new Object[] {CPInstanceId, key};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPINSTANCEUNITOFMEASURE_WHERE);

			sb.append(_FINDER_COLUMN_C_K_CPINSTANCEID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_C_K_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPInstanceId);

				if (bindKey) {
					queryPos.add(key);
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

	private static final String _FINDER_COLUMN_C_K_CPINSTANCEID_2 =
		"cpInstanceUnitOfMeasure.CPInstanceId = ? AND ";

	private static final String _FINDER_COLUMN_C_K_KEY_2 =
		"cpInstanceUnitOfMeasure.key = ?";

	private static final String _FINDER_COLUMN_C_K_KEY_3 =
		"(cpInstanceUnitOfMeasure.key IS NULL OR cpInstanceUnitOfMeasure.key = '')";

	private FinderPath _finderPathWithPaginationFindByC_P;
	private FinderPath _finderPathWithoutPaginationFindByC_P;
	private FinderPath _finderPathCountByC_P;

	/**
	 * Returns all the cp instance unit of measures where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @return the matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByC_P(
		long CPInstanceId, boolean primary) {

		return findByC_P(
			CPInstanceId, primary, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp instance unit of measures where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByC_P(
		long CPInstanceId, boolean primary, int start, int end) {

		return findByC_P(CPInstanceId, primary, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp instance unit of measures where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByC_P(
		long CPInstanceId, boolean primary, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return findByC_P(
			CPInstanceId, primary, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp instance unit of measures where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByC_P(
		long CPInstanceId, boolean primary, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_P;
				finderArgs = new Object[] {CPInstanceId, primary};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_P;
			finderArgs = new Object[] {
				CPInstanceId, primary, start, end, orderByComparator
			};
		}

		List<CPInstanceUnitOfMeasure> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPInstanceUnitOfMeasure>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure : list) {
					if ((CPInstanceId !=
							cpInstanceUnitOfMeasure.getCPInstanceId()) ||
						(primary != cpInstanceUnitOfMeasure.isPrimary())) {

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

			sb.append(_SQL_SELECT_CPINSTANCEUNITOFMEASURE_WHERE);

			sb.append(_FINDER_COLUMN_C_P_CPINSTANCEID_2);

			sb.append(_FINDER_COLUMN_C_P_PRIMARY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPInstanceUnitOfMeasureModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPInstanceId);

				queryPos.add(primary);

				list = (List<CPInstanceUnitOfMeasure>)QueryUtil.list(
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
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByC_P_First(
			long CPInstanceId, boolean primary,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = fetchByC_P_First(
			CPInstanceId, primary, orderByComparator);

		if (cpInstanceUnitOfMeasure != null) {
			return cpInstanceUnitOfMeasure;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPInstanceId=");
		sb.append(CPInstanceId);

		sb.append(", primary=");
		sb.append(primary);

		sb.append("}");

		throw new NoSuchCPInstanceUnitOfMeasureException(sb.toString());
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByC_P_First(
		long CPInstanceId, boolean primary,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		List<CPInstanceUnitOfMeasure> list = findByC_P(
			CPInstanceId, primary, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByC_P_Last(
			long CPInstanceId, boolean primary,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = fetchByC_P_Last(
			CPInstanceId, primary, orderByComparator);

		if (cpInstanceUnitOfMeasure != null) {
			return cpInstanceUnitOfMeasure;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPInstanceId=");
		sb.append(CPInstanceId);

		sb.append(", primary=");
		sb.append(primary);

		sb.append("}");

		throw new NoSuchCPInstanceUnitOfMeasureException(sb.toString());
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByC_P_Last(
		long CPInstanceId, boolean primary,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		int count = countByC_P(CPInstanceId, primary);

		if (count == 0) {
			return null;
		}

		List<CPInstanceUnitOfMeasure> list = findByC_P(
			CPInstanceId, primary, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure[] findByC_P_PrevAndNext(
			long CPInstanceUnitOfMeasureId, long CPInstanceId, boolean primary,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = findByPrimaryKey(
			CPInstanceUnitOfMeasureId);

		Session session = null;

		try {
			session = openSession();

			CPInstanceUnitOfMeasure[] array =
				new CPInstanceUnitOfMeasureImpl[3];

			array[0] = getByC_P_PrevAndNext(
				session, cpInstanceUnitOfMeasure, CPInstanceId, primary,
				orderByComparator, true);

			array[1] = cpInstanceUnitOfMeasure;

			array[2] = getByC_P_PrevAndNext(
				session, cpInstanceUnitOfMeasure, CPInstanceId, primary,
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

	protected CPInstanceUnitOfMeasure getByC_P_PrevAndNext(
		Session session, CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure,
		long CPInstanceId, boolean primary,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
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

		sb.append(_SQL_SELECT_CPINSTANCEUNITOFMEASURE_WHERE);

		sb.append(_FINDER_COLUMN_C_P_CPINSTANCEID_2);

		sb.append(_FINDER_COLUMN_C_P_PRIMARY_2);

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
			sb.append(CPInstanceUnitOfMeasureModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPInstanceId);

		queryPos.add(primary);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpInstanceUnitOfMeasure)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPInstanceUnitOfMeasure> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp instance unit of measures where CPInstanceId = &#63; and primary = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 */
	@Override
	public void removeByC_P(long CPInstanceId, boolean primary) {
		for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure :
				findByC_P(
					CPInstanceId, primary, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpInstanceUnitOfMeasure);
		}
	}

	/**
	 * Returns the number of cp instance unit of measures where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @return the number of matching cp instance unit of measures
	 */
	@Override
	public int countByC_P(long CPInstanceId, boolean primary) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_P;

			finderArgs = new Object[] {CPInstanceId, primary};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPINSTANCEUNITOFMEASURE_WHERE);

			sb.append(_FINDER_COLUMN_C_P_CPINSTANCEID_2);

			sb.append(_FINDER_COLUMN_C_P_PRIMARY_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPInstanceId);

				queryPos.add(primary);

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

	private static final String _FINDER_COLUMN_C_P_CPINSTANCEID_2 =
		"cpInstanceUnitOfMeasure.CPInstanceId = ? AND ";

	private static final String _FINDER_COLUMN_C_P_PRIMARY_2 =
		"cpInstanceUnitOfMeasure.primary = ?";

	private FinderPath _finderPathWithPaginationFindByC_K_S;
	private FinderPath _finderPathWithoutPaginationFindByC_K_S;
	private FinderPath _finderPathCountByC_K_S;

	/**
	 * Returns all the cp instance unit of measures where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @return the matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByC_K_S(
		long companyId, String key, String sku) {

		return findByC_K_S(
			companyId, key, sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp instance unit of measures where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByC_K_S(
		long companyId, String key, String sku, int start, int end) {

		return findByC_K_S(companyId, key, sku, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp instance unit of measures where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByC_K_S(
		long companyId, String key, String sku, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return findByC_K_S(
			companyId, key, sku, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp instance unit of measures where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findByC_K_S(
		long companyId, String key, String sku, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache) {

		key = Objects.toString(key, "");
		sku = Objects.toString(sku, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_K_S;
				finderArgs = new Object[] {companyId, key, sku};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_K_S;
			finderArgs = new Object[] {
				companyId, key, sku, start, end, orderByComparator
			};
		}

		List<CPInstanceUnitOfMeasure> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPInstanceUnitOfMeasure>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure : list) {
					if ((companyId != cpInstanceUnitOfMeasure.getCompanyId()) ||
						!key.equals(cpInstanceUnitOfMeasure.getKey()) ||
						!sku.equals(cpInstanceUnitOfMeasure.getSku())) {

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

			sb.append(_SQL_SELECT_CPINSTANCEUNITOFMEASURE_WHERE);

			sb.append(_FINDER_COLUMN_C_K_S_COMPANYID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_S_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_C_K_S_KEY_2);
			}

			boolean bindSku = false;

			if (sku.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_S_SKU_3);
			}
			else {
				bindSku = true;

				sb.append(_FINDER_COLUMN_C_K_S_SKU_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPInstanceUnitOfMeasureModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindKey) {
					queryPos.add(key);
				}

				if (bindSku) {
					queryPos.add(sku);
				}

				list = (List<CPInstanceUnitOfMeasure>)QueryUtil.list(
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
	 * Returns the first cp instance unit of measure in the ordered set where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByC_K_S_First(
			long companyId, String key, String sku,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = fetchByC_K_S_First(
			companyId, key, sku, orderByComparator);

		if (cpInstanceUnitOfMeasure != null) {
			return cpInstanceUnitOfMeasure;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", key=");
		sb.append(key);

		sb.append(", sku=");
		sb.append(sku);

		sb.append("}");

		throw new NoSuchCPInstanceUnitOfMeasureException(sb.toString());
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByC_K_S_First(
		long companyId, String key, String sku,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		List<CPInstanceUnitOfMeasure> list = findByC_K_S(
			companyId, key, sku, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByC_K_S_Last(
			long companyId, String key, String sku,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = fetchByC_K_S_Last(
			companyId, key, sku, orderByComparator);

		if (cpInstanceUnitOfMeasure != null) {
			return cpInstanceUnitOfMeasure;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", key=");
		sb.append(key);

		sb.append(", sku=");
		sb.append(sku);

		sb.append("}");

		throw new NoSuchCPInstanceUnitOfMeasureException(sb.toString());
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByC_K_S_Last(
		long companyId, String key, String sku,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		int count = countByC_K_S(companyId, key, sku);

		if (count == 0) {
			return null;
		}

		List<CPInstanceUnitOfMeasure> list = findByC_K_S(
			companyId, key, sku, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure[] findByC_K_S_PrevAndNext(
			long CPInstanceUnitOfMeasureId, long companyId, String key,
			String sku,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException {

		key = Objects.toString(key, "");
		sku = Objects.toString(sku, "");

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = findByPrimaryKey(
			CPInstanceUnitOfMeasureId);

		Session session = null;

		try {
			session = openSession();

			CPInstanceUnitOfMeasure[] array =
				new CPInstanceUnitOfMeasureImpl[3];

			array[0] = getByC_K_S_PrevAndNext(
				session, cpInstanceUnitOfMeasure, companyId, key, sku,
				orderByComparator, true);

			array[1] = cpInstanceUnitOfMeasure;

			array[2] = getByC_K_S_PrevAndNext(
				session, cpInstanceUnitOfMeasure, companyId, key, sku,
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

	protected CPInstanceUnitOfMeasure getByC_K_S_PrevAndNext(
		Session session, CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure,
		long companyId, String key, String sku,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
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

		sb.append(_SQL_SELECT_CPINSTANCEUNITOFMEASURE_WHERE);

		sb.append(_FINDER_COLUMN_C_K_S_COMPANYID_2);

		boolean bindKey = false;

		if (key.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_K_S_KEY_3);
		}
		else {
			bindKey = true;

			sb.append(_FINDER_COLUMN_C_K_S_KEY_2);
		}

		boolean bindSku = false;

		if (sku.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_K_S_SKU_3);
		}
		else {
			bindSku = true;

			sb.append(_FINDER_COLUMN_C_K_S_SKU_2);
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
			sb.append(CPInstanceUnitOfMeasureModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindKey) {
			queryPos.add(key);
		}

		if (bindSku) {
			queryPos.add(sku);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpInstanceUnitOfMeasure)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPInstanceUnitOfMeasure> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp instance unit of measures where companyId = &#63; and key = &#63; and sku = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 */
	@Override
	public void removeByC_K_S(long companyId, String key, String sku) {
		for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure :
				findByC_K_S(
					companyId, key, sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpInstanceUnitOfMeasure);
		}
	}

	/**
	 * Returns the number of cp instance unit of measures where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @return the number of matching cp instance unit of measures
	 */
	@Override
	public int countByC_K_S(long companyId, String key, String sku) {
		key = Objects.toString(key, "");
		sku = Objects.toString(sku, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_K_S;

			finderArgs = new Object[] {companyId, key, sku};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_CPINSTANCEUNITOFMEASURE_WHERE);

			sb.append(_FINDER_COLUMN_C_K_S_COMPANYID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_S_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_C_K_S_KEY_2);
			}

			boolean bindSku = false;

			if (sku.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_S_SKU_3);
			}
			else {
				bindSku = true;

				sb.append(_FINDER_COLUMN_C_K_S_SKU_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindKey) {
					queryPos.add(key);
				}

				if (bindSku) {
					queryPos.add(sku);
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

	private static final String _FINDER_COLUMN_C_K_S_COMPANYID_2 =
		"cpInstanceUnitOfMeasure.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_K_S_KEY_2 =
		"cpInstanceUnitOfMeasure.key = ? AND ";

	private static final String _FINDER_COLUMN_C_K_S_KEY_3 =
		"(cpInstanceUnitOfMeasure.key IS NULL OR cpInstanceUnitOfMeasure.key = '') AND ";

	private static final String _FINDER_COLUMN_C_K_S_SKU_2 =
		"cpInstanceUnitOfMeasure.sku = ?";

	private static final String _FINDER_COLUMN_C_K_S_SKU_3 =
		"(cpInstanceUnitOfMeasure.sku IS NULL OR cpInstanceUnitOfMeasure.sku = '')";

	public CPInstanceUnitOfMeasurePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("CPInstanceUnitOfMeasureId", "CPInstanceUOMId");
		dbColumnNames.put("active", "active_");
		dbColumnNames.put("key", "key_");
		dbColumnNames.put("precision", "precision_");
		dbColumnNames.put("primary", "primary_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPInstanceUnitOfMeasure.class);

		setModelImplClass(CPInstanceUnitOfMeasureImpl.class);
		setModelPKClass(long.class);

		setTable(CPInstanceUnitOfMeasureTable.INSTANCE);
	}

	/**
	 * Caches the cp instance unit of measure in the entity cache if it is enabled.
	 *
	 * @param cpInstanceUnitOfMeasure the cp instance unit of measure
	 */
	@Override
	public void cacheResult(CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {
		if (cpInstanceUnitOfMeasure.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			CPInstanceUnitOfMeasureImpl.class,
			cpInstanceUnitOfMeasure.getPrimaryKey(), cpInstanceUnitOfMeasure);

		finderCache.putResult(
			_finderPathFetchByC_K,
			new Object[] {
				cpInstanceUnitOfMeasure.getCPInstanceId(),
				cpInstanceUnitOfMeasure.getKey()
			},
			cpInstanceUnitOfMeasure);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cp instance unit of measures in the entity cache if it is enabled.
	 *
	 * @param cpInstanceUnitOfMeasures the cp instance unit of measures
	 */
	@Override
	public void cacheResult(
		List<CPInstanceUnitOfMeasure> cpInstanceUnitOfMeasures) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpInstanceUnitOfMeasures.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure :
				cpInstanceUnitOfMeasures) {

			if (cpInstanceUnitOfMeasure.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					CPInstanceUnitOfMeasureImpl.class,
					cpInstanceUnitOfMeasure.getPrimaryKey()) == null) {

				cacheResult(cpInstanceUnitOfMeasure);
			}
		}
	}

	/**
	 * Clears the cache for all cp instance unit of measures.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPInstanceUnitOfMeasureImpl.class);

		finderCache.clearCache(CPInstanceUnitOfMeasureImpl.class);
	}

	/**
	 * Clears the cache for the cp instance unit of measure.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {
		entityCache.removeResult(
			CPInstanceUnitOfMeasureImpl.class, cpInstanceUnitOfMeasure);
	}

	@Override
	public void clearCache(
		List<CPInstanceUnitOfMeasure> cpInstanceUnitOfMeasures) {

		for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure :
				cpInstanceUnitOfMeasures) {

			entityCache.removeResult(
				CPInstanceUnitOfMeasureImpl.class, cpInstanceUnitOfMeasure);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPInstanceUnitOfMeasureImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPInstanceUnitOfMeasureImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPInstanceUnitOfMeasureModelImpl cpInstanceUnitOfMeasureModelImpl) {

		Object[] args = new Object[] {
			cpInstanceUnitOfMeasureModelImpl.getCPInstanceId(),
			cpInstanceUnitOfMeasureModelImpl.getKey()
		};

		finderCache.putResult(_finderPathCountByC_K, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_K, args, cpInstanceUnitOfMeasureModelImpl);
	}

	/**
	 * Creates a new cp instance unit of measure with the primary key. Does not add the cp instance unit of measure to the database.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key for the new cp instance unit of measure
	 * @return the new cp instance unit of measure
	 */
	@Override
	public CPInstanceUnitOfMeasure create(long CPInstanceUnitOfMeasureId) {
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			new CPInstanceUnitOfMeasureImpl();

		cpInstanceUnitOfMeasure.setNew(true);
		cpInstanceUnitOfMeasure.setPrimaryKey(CPInstanceUnitOfMeasureId);

		String uuid = _portalUUID.generate();

		cpInstanceUnitOfMeasure.setUuid(uuid);

		cpInstanceUnitOfMeasure.setCompanyId(CompanyThreadLocal.getCompanyId());

		return cpInstanceUnitOfMeasure;
	}

	/**
	 * Removes the cp instance unit of measure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure that was removed
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure remove(long CPInstanceUnitOfMeasureId)
		throws NoSuchCPInstanceUnitOfMeasureException {

		return remove((Serializable)CPInstanceUnitOfMeasureId);
	}

	/**
	 * Removes the cp instance unit of measure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure that was removed
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure remove(Serializable primaryKey)
		throws NoSuchCPInstanceUnitOfMeasureException {

		Session session = null;

		try {
			session = openSession();

			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
				(CPInstanceUnitOfMeasure)session.get(
					CPInstanceUnitOfMeasureImpl.class, primaryKey);

			if (cpInstanceUnitOfMeasure == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPInstanceUnitOfMeasureException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpInstanceUnitOfMeasure);
		}
		catch (NoSuchCPInstanceUnitOfMeasureException noSuchEntityException) {
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
	protected CPInstanceUnitOfMeasure removeImpl(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpInstanceUnitOfMeasure)) {
				cpInstanceUnitOfMeasure = (CPInstanceUnitOfMeasure)session.get(
					CPInstanceUnitOfMeasureImpl.class,
					cpInstanceUnitOfMeasure.getPrimaryKeyObj());
			}

			if ((cpInstanceUnitOfMeasure != null) &&
				ctPersistenceHelper.isRemove(cpInstanceUnitOfMeasure)) {

				session.delete(cpInstanceUnitOfMeasure);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpInstanceUnitOfMeasure != null) {
			clearCache(cpInstanceUnitOfMeasure);
		}

		return cpInstanceUnitOfMeasure;
	}

	@Override
	public CPInstanceUnitOfMeasure updateImpl(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {

		boolean isNew = cpInstanceUnitOfMeasure.isNew();

		if (!(cpInstanceUnitOfMeasure instanceof
				CPInstanceUnitOfMeasureModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpInstanceUnitOfMeasure.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpInstanceUnitOfMeasure);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpInstanceUnitOfMeasure proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPInstanceUnitOfMeasure implementation " +
					cpInstanceUnitOfMeasure.getClass());
		}

		CPInstanceUnitOfMeasureModelImpl cpInstanceUnitOfMeasureModelImpl =
			(CPInstanceUnitOfMeasureModelImpl)cpInstanceUnitOfMeasure;

		if (Validator.isNull(cpInstanceUnitOfMeasure.getUuid())) {
			String uuid = _portalUUID.generate();

			cpInstanceUnitOfMeasure.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpInstanceUnitOfMeasure.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpInstanceUnitOfMeasure.setCreateDate(date);
			}
			else {
				cpInstanceUnitOfMeasure.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpInstanceUnitOfMeasureModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpInstanceUnitOfMeasure.setModifiedDate(date);
			}
			else {
				cpInstanceUnitOfMeasure.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(cpInstanceUnitOfMeasure)) {
				if (!isNew) {
					session.evict(
						CPInstanceUnitOfMeasureImpl.class,
						cpInstanceUnitOfMeasure.getPrimaryKeyObj());
				}

				session.save(cpInstanceUnitOfMeasure);
			}
			else {
				cpInstanceUnitOfMeasure =
					(CPInstanceUnitOfMeasure)session.merge(
						cpInstanceUnitOfMeasure);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpInstanceUnitOfMeasure.getCtCollectionId() != 0) {
			if (isNew) {
				cpInstanceUnitOfMeasure.setNew(false);
			}

			cpInstanceUnitOfMeasure.resetOriginalValues();

			return cpInstanceUnitOfMeasure;
		}

		entityCache.putResult(
			CPInstanceUnitOfMeasureImpl.class, cpInstanceUnitOfMeasureModelImpl,
			false, true);

		cacheUniqueFindersCache(cpInstanceUnitOfMeasureModelImpl);

		if (isNew) {
			cpInstanceUnitOfMeasure.setNew(false);
		}

		cpInstanceUnitOfMeasure.resetOriginalValues();

		return cpInstanceUnitOfMeasure;
	}

	/**
	 * Returns the cp instance unit of measure with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPInstanceUnitOfMeasureException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = fetchByPrimaryKey(
			primaryKey);

		if (cpInstanceUnitOfMeasure == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPInstanceUnitOfMeasureException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpInstanceUnitOfMeasure;
	}

	/**
	 * Returns the cp instance unit of measure with the primary key or throws a <code>NoSuchCPInstanceUnitOfMeasureException</code> if it could not be found.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure findByPrimaryKey(
			long CPInstanceUnitOfMeasureId)
		throws NoSuchCPInstanceUnitOfMeasureException {

		return findByPrimaryKey((Serializable)CPInstanceUnitOfMeasureId);
	}

	/**
	 * Returns the cp instance unit of measure with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure, or <code>null</code> if a cp instance unit of measure with the primary key could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				CPInstanceUnitOfMeasure.class, primaryKey)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = null;

		Session session = null;

		try {
			session = openSession();

			cpInstanceUnitOfMeasure = (CPInstanceUnitOfMeasure)session.get(
				CPInstanceUnitOfMeasureImpl.class, primaryKey);

			if (cpInstanceUnitOfMeasure != null) {
				cacheResult(cpInstanceUnitOfMeasure);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return cpInstanceUnitOfMeasure;
	}

	/**
	 * Returns the cp instance unit of measure with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure, or <code>null</code> if a cp instance unit of measure with the primary key could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure fetchByPrimaryKey(
		long CPInstanceUnitOfMeasureId) {

		return fetchByPrimaryKey((Serializable)CPInstanceUnitOfMeasureId);
	}

	@Override
	public Map<Serializable, CPInstanceUnitOfMeasure> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				CPInstanceUnitOfMeasure.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPInstanceUnitOfMeasure> map =
			new HashMap<Serializable, CPInstanceUnitOfMeasure>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = fetchByPrimaryKey(
				primaryKey);

			if (cpInstanceUnitOfMeasure != null) {
				map.put(primaryKey, cpInstanceUnitOfMeasure);
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

			for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure :
					(List<CPInstanceUnitOfMeasure>)query.list()) {

				map.put(
					cpInstanceUnitOfMeasure.getPrimaryKeyObj(),
					cpInstanceUnitOfMeasure);

				cacheResult(cpInstanceUnitOfMeasure);
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
	 * Returns all the cp instance unit of measures.
	 *
	 * @return the cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp instance unit of measures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp instance unit of measures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findAll(
		int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp instance unit of measures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp instance unit of measures
	 */
	@Override
	public List<CPInstanceUnitOfMeasure> findAll(
		int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

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

		List<CPInstanceUnitOfMeasure> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPInstanceUnitOfMeasure>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CPINSTANCEUNITOFMEASURE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CPINSTANCEUNITOFMEASURE;

				sql = sql.concat(
					CPInstanceUnitOfMeasureModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CPInstanceUnitOfMeasure>)QueryUtil.list(
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
	 * Removes all the cp instance unit of measures from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure : findAll()) {
			remove(cpInstanceUnitOfMeasure);
		}
	}

	/**
	 * Returns the number of cp instance unit of measures.
	 *
	 * @return the number of cp instance unit of measures
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceUnitOfMeasure.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_CPINSTANCEUNITOFMEASURE);

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
		return "CPInstanceUOMId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPINSTANCEUNITOFMEASURE;
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
		return CPInstanceUnitOfMeasureModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CPInstanceUOM";
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
		ctStrictColumnNames.add("CPInstanceId");
		ctStrictColumnNames.add("active_");
		ctStrictColumnNames.add("incrementalOrderQuantity");
		ctStrictColumnNames.add("key_");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("precision_");
		ctStrictColumnNames.add("primary_");
		ctStrictColumnNames.add("priority");
		ctStrictColumnNames.add("rate");
		ctStrictColumnNames.add("sku");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CPInstanceUOMId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"CPInstanceId", "key_"});
	}

	/**
	 * Initializes the cp instance unit of measure persistence.
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

		_finderPathWithPaginationFindByCPInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPInstanceId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPInstanceId"}, true);

		_finderPathWithoutPaginationFindByCPInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPInstanceId",
			new String[] {Long.class.getName()}, new String[] {"CPInstanceId"},
			true);

		_finderPathCountByCPInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPInstanceId",
			new String[] {Long.class.getName()}, new String[] {"CPInstanceId"},
			false);

		_finderPathWithPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "sku"}, true);

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "sku"}, true);

		_finderPathCountByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "sku"}, false);

		_finderPathWithPaginationFindByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"CPInstanceId", "active_"}, true);

		_finderPathWithoutPaginationFindByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"CPInstanceId", "active_"}, true);

		_finderPathCountByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"CPInstanceId", "active_"}, false);

		_finderPathFetchByC_K = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_K",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"CPInstanceId", "key_"}, true);

		_finderPathCountByC_K = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_K",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"CPInstanceId", "key_"}, false);

		_finderPathWithPaginationFindByC_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"CPInstanceId", "primary_"}, true);

		_finderPathWithoutPaginationFindByC_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"CPInstanceId", "primary_"}, true);

		_finderPathCountByC_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"CPInstanceId", "primary_"}, false);

		_finderPathWithPaginationFindByC_K_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_K_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "key_", "sku"}, true);

		_finderPathWithoutPaginationFindByC_K_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_K_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "key_", "sku"}, true);

		_finderPathCountByC_K_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_K_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "key_", "sku"}, false);

		CPInstanceUnitOfMeasureUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CPInstanceUnitOfMeasureUtil.setPersistence(null);

		entityCache.removeCache(CPInstanceUnitOfMeasureImpl.class.getName());
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

	private static final String _SQL_SELECT_CPINSTANCEUNITOFMEASURE =
		"SELECT cpInstanceUnitOfMeasure FROM CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure";

	private static final String _SQL_SELECT_CPINSTANCEUNITOFMEASURE_WHERE =
		"SELECT cpInstanceUnitOfMeasure FROM CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure WHERE ";

	private static final String _SQL_COUNT_CPINSTANCEUNITOFMEASURE =
		"SELECT COUNT(cpInstanceUnitOfMeasure) FROM CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure";

	private static final String _SQL_COUNT_CPINSTANCEUNITOFMEASURE_WHERE =
		"SELECT COUNT(cpInstanceUnitOfMeasure) FROM CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpInstanceUnitOfMeasure.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPInstanceUnitOfMeasure exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPInstanceUnitOfMeasure exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPInstanceUnitOfMeasurePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {
			"uuid", "CPInstanceUnitOfMeasureId", "active", "key", "precision",
			"primary"
		});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

}