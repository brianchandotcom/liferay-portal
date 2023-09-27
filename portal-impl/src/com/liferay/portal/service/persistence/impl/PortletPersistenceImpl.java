/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchPortletException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.PortletPersistence;
import com.liferay.portal.kernel.service.persistence.PortletUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.model.impl.PortletModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the portlet service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PortletPersistenceImpl
	extends BasePersistenceImpl<Portlet> implements PortletPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PortletUtil</code> to access the portlet persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PortletImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the portlets where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching portlets
	 */
	@Override
	public List<Portlet> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlets where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of portlets
	 * @param end the upper bound of the range of portlets (not inclusive)
	 * @return the range of matching portlets
	 */
	@Override
	public List<Portlet> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlets where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of portlets
	 * @param end the upper bound of the range of portlets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlets
	 */
	@Override
	public List<Portlet> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Portlet> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portlets where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of portlets
	 * @param end the upper bound of the range of portlets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlets
	 */
	@Override
	public List<Portlet> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Portlet> orderByComparator, boolean useFinderCache) {

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

		List<Portlet> list = null;

		if (useFinderCache) {
			list = (List<Portlet>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Portlet portlet : list) {
					if (companyId != portlet.getCompanyId()) {
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

			sb.append(_SQL_SELECT_PORTLET_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PortletModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<Portlet>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first portlet in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet
	 * @throws NoSuchPortletException if a matching portlet could not be found
	 */
	@Override
	public Portlet findByCompanyId_First(
			long companyId, OrderByComparator<Portlet> orderByComparator)
		throws NoSuchPortletException {

		Portlet portlet = fetchByCompanyId_First(companyId, orderByComparator);

		if (portlet != null) {
			return portlet;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPortletException(sb.toString());
	}

	/**
	 * Returns the first portlet in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet, or <code>null</code> if a matching portlet could not be found
	 */
	@Override
	public Portlet fetchByCompanyId_First(
		long companyId, OrderByComparator<Portlet> orderByComparator) {

		List<Portlet> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet
	 * @throws NoSuchPortletException if a matching portlet could not be found
	 */
	@Override
	public Portlet findByCompanyId_Last(
			long companyId, OrderByComparator<Portlet> orderByComparator)
		throws NoSuchPortletException {

		Portlet portlet = fetchByCompanyId_Last(companyId, orderByComparator);

		if (portlet != null) {
			return portlet;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPortletException(sb.toString());
	}

	/**
	 * Returns the last portlet in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet, or <code>null</code> if a matching portlet could not be found
	 */
	@Override
	public Portlet fetchByCompanyId_Last(
		long companyId, OrderByComparator<Portlet> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<Portlet> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlets before and after the current portlet in the ordered set where companyId = &#63;.
	 *
	 * @param id the primary key of the current portlet
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet
	 * @throws NoSuchPortletException if a portlet with the primary key could not be found
	 */
	@Override
	public Portlet[] findByCompanyId_PrevAndNext(
			long id, long companyId,
			OrderByComparator<Portlet> orderByComparator)
		throws NoSuchPortletException {

		Portlet portlet = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			Portlet[] array = new PortletImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, portlet, companyId, orderByComparator, true);

			array[1] = portlet;

			array[2] = getByCompanyId_PrevAndNext(
				session, portlet, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Portlet getByCompanyId_PrevAndNext(
		Session session, Portlet portlet, long companyId,
		OrderByComparator<Portlet> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_PORTLET_WHERE);

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
			sb.append(PortletModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(portlet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Portlet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portlets where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (Portlet portlet :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(portlet);
		}
	}

	/**
	 * Returns the number of portlets where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching portlets
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_PORTLET_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"portlet.companyId = ?";

	private FinderPath _finderPathFetchByC_P;
	private FinderPath _finderPathCountByC_P;

	/**
	 * Returns the portlet where companyId = &#63; and portletId = &#63; or throws a <code>NoSuchPortletException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @return the matching portlet
	 * @throws NoSuchPortletException if a matching portlet could not be found
	 */
	@Override
	public Portlet findByC_P(long companyId, String portletId)
		throws NoSuchPortletException {

		Portlet portlet = fetchByC_P(companyId, portletId);

		if (portlet == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", portletId=");
			sb.append(portletId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPortletException(sb.toString());
		}

		return portlet;
	}

	/**
	 * Returns the portlet where companyId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @return the matching portlet, or <code>null</code> if a matching portlet could not be found
	 */
	@Override
	public Portlet fetchByC_P(long companyId, String portletId) {
		return fetchByC_P(companyId, portletId, true);
	}

	/**
	 * Returns the portlet where companyId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching portlet, or <code>null</code> if a matching portlet could not be found
	 */
	@Override
	public Portlet fetchByC_P(
		long companyId, String portletId, boolean useFinderCache) {

		portletId = Objects.toString(portletId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, portletId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_P, finderArgs, this);
		}

		if (result instanceof Portlet) {
			Portlet portlet = (Portlet)result;

			if ((companyId != portlet.getCompanyId()) ||
				!Objects.equals(portletId, portlet.getPortletId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_PORTLET_WHERE);

			sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_C_P_PORTLETID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindPortletId) {
					queryPos.add(portletId);
				}

				List<Portlet> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_P, finderArgs, list);
					}
				}
				else {
					Portlet portlet = list.get(0);

					result = portlet;

					cacheResult(portlet);
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
			return (Portlet)result;
		}
	}

	/**
	 * Removes the portlet where companyId = &#63; and portletId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @return the portlet that was removed
	 */
	@Override
	public Portlet removeByC_P(long companyId, String portletId)
		throws NoSuchPortletException {

		Portlet portlet = findByC_P(companyId, portletId);

		return remove(portlet);
	}

	/**
	 * Returns the number of portlets where companyId = &#63; and portletId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @return the number of matching portlets
	 */
	@Override
	public int countByC_P(long companyId, String portletId) {
		portletId = Objects.toString(portletId, "");

		FinderPath finderPath = _finderPathCountByC_P;

		Object[] finderArgs = new Object[] {companyId, portletId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_PORTLET_WHERE);

			sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_C_P_PORTLETID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindPortletId) {
					queryPos.add(portletId);
				}

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_P_COMPANYID_2 =
		"portlet.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_P_PORTLETID_2 =
		"portlet.portletId = ?";

	private static final String _FINDER_COLUMN_C_P_PORTLETID_3 =
		"(portlet.portletId IS NULL OR portlet.portletId = '')";

	public PortletPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("id", "id_");
		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(Portlet.class);

		setModelImplClass(PortletImpl.class);
		setModelPKClass(long.class);

		setTable(PortletTable.INSTANCE);
	}

	/**
	 * Caches the portlet in the entity cache if it is enabled.
	 *
	 * @param portlet the portlet
	 */
	@Override
	public void cacheResult(Portlet portlet) {
		EntityCacheUtil.putResult(
			PortletImpl.class, portlet.getPrimaryKey(), portlet);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_P,
			new Object[] {portlet.getCompanyId(), portlet.getPortletId()},
			portlet);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the portlets in the entity cache if it is enabled.
	 *
	 * @param portlets the portlets
	 */
	@Override
	public void cacheResult(List<Portlet> portlets) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (portlets.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Portlet portlet : portlets) {
			if (EntityCacheUtil.getResult(
					PortletImpl.class, portlet.getPrimaryKey()) == null) {

				cacheResult(portlet);
			}
		}
	}

	/**
	 * Clears the cache for all portlets.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(PortletImpl.class);

		FinderCacheUtil.clearCache(PortletImpl.class);
	}

	/**
	 * Clears the cache for the portlet.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Portlet portlet) {
		EntityCacheUtil.removeResult(PortletImpl.class, portlet);
	}

	@Override
	public void clearCache(List<Portlet> portlets) {
		for (Portlet portlet : portlets) {
			EntityCacheUtil.removeResult(PortletImpl.class, portlet);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(PortletImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(PortletImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(PortletModelImpl portletModelImpl) {
		Object[] args = new Object[] {
			portletModelImpl.getCompanyId(), portletModelImpl.getPortletId()
		};

		FinderCacheUtil.putResult(_finderPathCountByC_P, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByC_P, args, portletModelImpl);
	}

	/**
	 * Creates a new portlet with the primary key. Does not add the portlet to the database.
	 *
	 * @param id the primary key for the new portlet
	 * @return the new portlet
	 */
	@Override
	public Portlet create(long id) {
		Portlet portlet = new PortletImpl();

		portlet.setNew(true);
		portlet.setPrimaryKey(id);

		portlet.setCompanyId(CompanyThreadLocal.getCompanyId());

		return portlet;
	}

	/**
	 * Removes the portlet with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the portlet
	 * @return the portlet that was removed
	 * @throws NoSuchPortletException if a portlet with the primary key could not be found
	 */
	@Override
	public Portlet remove(long id) throws NoSuchPortletException {
		return remove((Serializable)id);
	}

	/**
	 * Removes the portlet with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the portlet
	 * @return the portlet that was removed
	 * @throws NoSuchPortletException if a portlet with the primary key could not be found
	 */
	@Override
	public Portlet remove(Serializable primaryKey)
		throws NoSuchPortletException {

		Session session = null;

		try {
			session = openSession();

			Portlet portlet = (Portlet)session.get(
				PortletImpl.class, primaryKey);

			if (portlet == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPortletException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(portlet);
		}
		catch (NoSuchPortletException noSuchEntityException) {
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
	protected Portlet removeImpl(Portlet portlet) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(portlet)) {
				portlet = (Portlet)session.get(
					PortletImpl.class, portlet.getPrimaryKeyObj());
			}

			if (portlet != null) {
				session.delete(portlet);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (portlet != null) {
			clearCache(portlet);
		}

		return portlet;
	}

	@Override
	public Portlet updateImpl(Portlet portlet) {
		boolean isNew = portlet.isNew();

		if (!(portlet instanceof PortletModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(portlet.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(portlet);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in portlet proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Portlet implementation " +
					portlet.getClass());
		}

		PortletModelImpl portletModelImpl = (PortletModelImpl)portlet;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(portlet);
			}
			else {
				portlet = (Portlet)session.merge(portlet);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			PortletImpl.class, portletModelImpl, false, true);

		cacheUniqueFindersCache(portletModelImpl);

		if (isNew) {
			portlet.setNew(false);
		}

		portlet.resetOriginalValues();

		return portlet;
	}

	/**
	 * Returns the portlet with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the portlet
	 * @return the portlet
	 * @throws NoSuchPortletException if a portlet with the primary key could not be found
	 */
	@Override
	public Portlet findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPortletException {

		Portlet portlet = fetchByPrimaryKey(primaryKey);

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPortletException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return portlet;
	}

	/**
	 * Returns the portlet with the primary key or throws a <code>NoSuchPortletException</code> if it could not be found.
	 *
	 * @param id the primary key of the portlet
	 * @return the portlet
	 * @throws NoSuchPortletException if a portlet with the primary key could not be found
	 */
	@Override
	public Portlet findByPrimaryKey(long id) throws NoSuchPortletException {
		return findByPrimaryKey((Serializable)id);
	}

	/**
	 * Returns the portlet with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the portlet
	 * @return the portlet, or <code>null</code> if a portlet with the primary key could not be found
	 */
	@Override
	public Portlet fetchByPrimaryKey(long id) {
		return fetchByPrimaryKey((Serializable)id);
	}

	/**
	 * Returns all the portlets.
	 *
	 * @return the portlets
	 */
	@Override
	public List<Portlet> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlets
	 * @param end the upper bound of the range of portlets (not inclusive)
	 * @return the range of portlets
	 */
	@Override
	public List<Portlet> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlets
	 * @param end the upper bound of the range of portlets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of portlets
	 */
	@Override
	public List<Portlet> findAll(
		int start, int end, OrderByComparator<Portlet> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portlets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlets
	 * @param end the upper bound of the range of portlets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of portlets
	 */
	@Override
	public List<Portlet> findAll(
		int start, int end, OrderByComparator<Portlet> orderByComparator,
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

		List<Portlet> list = null;

		if (useFinderCache) {
			list = (List<Portlet>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_PORTLET);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_PORTLET;

				sql = sql.concat(PortletModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Portlet>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Removes all the portlets from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Portlet portlet : findAll()) {
			remove(portlet);
		}
	}

	/**
	 * Returns the number of portlets.
	 *
	 * @return the number of portlets
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_PORTLET);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "id_";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_PORTLET;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return PortletModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the portlet persistence.
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

		_finderPathFetchByC_P = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_P",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "portletId"}, true);

		_finderPathCountByC_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "portletId"}, false);

		PortletUtil.setPersistence(this);
	}

	public void destroy() {
		PortletUtil.setPersistence(null);

		EntityCacheUtil.removeCache(PortletImpl.class.getName());
	}

	private static final String _SQL_SELECT_PORTLET =
		"SELECT portlet FROM Portlet portlet";

	private static final String _SQL_SELECT_PORTLET_WHERE =
		"SELECT portlet FROM Portlet portlet WHERE ";

	private static final String _SQL_COUNT_PORTLET =
		"SELECT COUNT(portlet) FROM Portlet portlet";

	private static final String _SQL_COUNT_PORTLET_WHERE =
		"SELECT COUNT(portlet) FROM Portlet portlet WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "portlet.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Portlet exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Portlet exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PortletPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"id", "active"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}