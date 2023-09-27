/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.storage.service.persistence.impl;

import com.liferay.analytics.message.storage.exception.NoSuchAssociationException;
import com.liferay.analytics.message.storage.model.AnalyticsAssociation;
import com.liferay.analytics.message.storage.model.AnalyticsAssociationTable;
import com.liferay.analytics.message.storage.model.impl.AnalyticsAssociationImpl;
import com.liferay.analytics.message.storage.model.impl.AnalyticsAssociationModelImpl;
import com.liferay.analytics.message.storage.service.persistence.AnalyticsAssociationPersistence;
import com.liferay.analytics.message.storage.service.persistence.AnalyticsAssociationUtil;
import com.liferay.analytics.message.storage.service.persistence.impl.constants.AnalyticsPersistenceConstants;
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
 * The persistence implementation for the analytics association service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AnalyticsAssociationPersistence.class)
public class AnalyticsAssociationPersistenceImpl
	extends BasePersistenceImpl<AnalyticsAssociation>
	implements AnalyticsAssociationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AnalyticsAssociationUtil</code> to access the analytics association persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AnalyticsAssociationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByC_A;
	private FinderPath _finderPathWithoutPaginationFindByC_A;
	private FinderPath _finderPathCountByC_A;

	/**
	 * Returns all the analytics associations where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @return the matching analytics associations
	 */
	@Override
	public List<AnalyticsAssociation> findByC_A(
		long companyId, String associationClassName) {

		return findByC_A(
			companyId, associationClassName, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the analytics associations where companyId = &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics associations
	 * @param end the upper bound of the range of analytics associations (not inclusive)
	 * @return the range of matching analytics associations
	 */
	@Override
	public List<AnalyticsAssociation> findByC_A(
		long companyId, String associationClassName, int start, int end) {

		return findByC_A(companyId, associationClassName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the analytics associations where companyId = &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics associations
	 * @param end the upper bound of the range of analytics associations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics associations
	 */
	@Override
	public List<AnalyticsAssociation> findByC_A(
		long companyId, String associationClassName, int start, int end,
		OrderByComparator<AnalyticsAssociation> orderByComparator) {

		return findByC_A(
			companyId, associationClassName, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the analytics associations where companyId = &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics associations
	 * @param end the upper bound of the range of analytics associations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics associations
	 */
	@Override
	public List<AnalyticsAssociation> findByC_A(
		long companyId, String associationClassName, int start, int end,
		OrderByComparator<AnalyticsAssociation> orderByComparator,
		boolean useFinderCache) {

		associationClassName = Objects.toString(associationClassName, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AnalyticsAssociation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_A;
				finderArgs = new Object[] {companyId, associationClassName};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_A;
			finderArgs = new Object[] {
				companyId, associationClassName, start, end, orderByComparator
			};
		}

		List<AnalyticsAssociation> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AnalyticsAssociation>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AnalyticsAssociation analyticsAssociation : list) {
					if ((companyId != analyticsAssociation.getCompanyId()) ||
						!associationClassName.equals(
							analyticsAssociation.getAssociationClassName())) {

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

			sb.append(_SQL_SELECT_ANALYTICSASSOCIATION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

			boolean bindAssociationClassName = false;

			if (associationClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_A_ASSOCIATIONCLASSNAME_3);
			}
			else {
				bindAssociationClassName = true;

				sb.append(_FINDER_COLUMN_C_A_ASSOCIATIONCLASSNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AnalyticsAssociationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindAssociationClassName) {
					queryPos.add(associationClassName);
				}

				list = (List<AnalyticsAssociation>)QueryUtil.list(
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
	 * Returns the first analytics association in the ordered set where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association
	 * @throws NoSuchAssociationException if a matching analytics association could not be found
	 */
	@Override
	public AnalyticsAssociation findByC_A_First(
			long companyId, String associationClassName,
			OrderByComparator<AnalyticsAssociation> orderByComparator)
		throws NoSuchAssociationException {

		AnalyticsAssociation analyticsAssociation = fetchByC_A_First(
			companyId, associationClassName, orderByComparator);

		if (analyticsAssociation != null) {
			return analyticsAssociation;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", associationClassName=");
		sb.append(associationClassName);

		sb.append("}");

		throw new NoSuchAssociationException(sb.toString());
	}

	/**
	 * Returns the first analytics association in the ordered set where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association, or <code>null</code> if a matching analytics association could not be found
	 */
	@Override
	public AnalyticsAssociation fetchByC_A_First(
		long companyId, String associationClassName,
		OrderByComparator<AnalyticsAssociation> orderByComparator) {

		List<AnalyticsAssociation> list = findByC_A(
			companyId, associationClassName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last analytics association in the ordered set where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association
	 * @throws NoSuchAssociationException if a matching analytics association could not be found
	 */
	@Override
	public AnalyticsAssociation findByC_A_Last(
			long companyId, String associationClassName,
			OrderByComparator<AnalyticsAssociation> orderByComparator)
		throws NoSuchAssociationException {

		AnalyticsAssociation analyticsAssociation = fetchByC_A_Last(
			companyId, associationClassName, orderByComparator);

		if (analyticsAssociation != null) {
			return analyticsAssociation;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", associationClassName=");
		sb.append(associationClassName);

		sb.append("}");

		throw new NoSuchAssociationException(sb.toString());
	}

	/**
	 * Returns the last analytics association in the ordered set where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association, or <code>null</code> if a matching analytics association could not be found
	 */
	@Override
	public AnalyticsAssociation fetchByC_A_Last(
		long companyId, String associationClassName,
		OrderByComparator<AnalyticsAssociation> orderByComparator) {

		int count = countByC_A(companyId, associationClassName);

		if (count == 0) {
			return null;
		}

		List<AnalyticsAssociation> list = findByC_A(
			companyId, associationClassName, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the analytics associations before and after the current analytics association in the ordered set where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param analyticsAssociationId the primary key of the current analytics association
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics association
	 * @throws NoSuchAssociationException if a analytics association with the primary key could not be found
	 */
	@Override
	public AnalyticsAssociation[] findByC_A_PrevAndNext(
			long analyticsAssociationId, long companyId,
			String associationClassName,
			OrderByComparator<AnalyticsAssociation> orderByComparator)
		throws NoSuchAssociationException {

		associationClassName = Objects.toString(associationClassName, "");

		AnalyticsAssociation analyticsAssociation = findByPrimaryKey(
			analyticsAssociationId);

		Session session = null;

		try {
			session = openSession();

			AnalyticsAssociation[] array = new AnalyticsAssociationImpl[3];

			array[0] = getByC_A_PrevAndNext(
				session, analyticsAssociation, companyId, associationClassName,
				orderByComparator, true);

			array[1] = analyticsAssociation;

			array[2] = getByC_A_PrevAndNext(
				session, analyticsAssociation, companyId, associationClassName,
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

	protected AnalyticsAssociation getByC_A_PrevAndNext(
		Session session, AnalyticsAssociation analyticsAssociation,
		long companyId, String associationClassName,
		OrderByComparator<AnalyticsAssociation> orderByComparator,
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

		sb.append(_SQL_SELECT_ANALYTICSASSOCIATION_WHERE);

		sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		boolean bindAssociationClassName = false;

		if (associationClassName.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_A_ASSOCIATIONCLASSNAME_3);
		}
		else {
			bindAssociationClassName = true;

			sb.append(_FINDER_COLUMN_C_A_ASSOCIATIONCLASSNAME_2);
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
			sb.append(AnalyticsAssociationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindAssociationClassName) {
			queryPos.add(associationClassName);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						analyticsAssociation)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AnalyticsAssociation> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the analytics associations where companyId = &#63; and associationClassName = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 */
	@Override
	public void removeByC_A(long companyId, String associationClassName) {
		for (AnalyticsAssociation analyticsAssociation :
				findByC_A(
					companyId, associationClassName, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(analyticsAssociation);
		}
	}

	/**
	 * Returns the number of analytics associations where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @return the number of matching analytics associations
	 */
	@Override
	public int countByC_A(long companyId, String associationClassName) {
		associationClassName = Objects.toString(associationClassName, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AnalyticsAssociation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_A;

			finderArgs = new Object[] {companyId, associationClassName};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ANALYTICSASSOCIATION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

			boolean bindAssociationClassName = false;

			if (associationClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_A_ASSOCIATIONCLASSNAME_3);
			}
			else {
				bindAssociationClassName = true;

				sb.append(_FINDER_COLUMN_C_A_ASSOCIATIONCLASSNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindAssociationClassName) {
					queryPos.add(associationClassName);
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

	private static final String _FINDER_COLUMN_C_A_COMPANYID_2 =
		"analyticsAssociation.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_ASSOCIATIONCLASSNAME_2 =
		"analyticsAssociation.associationClassName = ?";

	private static final String _FINDER_COLUMN_C_A_ASSOCIATIONCLASSNAME_3 =
		"(analyticsAssociation.associationClassName IS NULL OR analyticsAssociation.associationClassName = '')";

	private FinderPath _finderPathWithPaginationFindByC_GtM_A;
	private FinderPath _finderPathWithPaginationCountByC_GtM_A;

	/**
	 * Returns all the analytics associations where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @return the matching analytics associations
	 */
	@Override
	public List<AnalyticsAssociation> findByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName) {

		return findByC_GtM_A(
			companyId, modifiedDate, associationClassName, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the analytics associations where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics associations
	 * @param end the upper bound of the range of analytics associations (not inclusive)
	 * @return the range of matching analytics associations
	 */
	@Override
	public List<AnalyticsAssociation> findByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName,
		int start, int end) {

		return findByC_GtM_A(
			companyId, modifiedDate, associationClassName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the analytics associations where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics associations
	 * @param end the upper bound of the range of analytics associations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics associations
	 */
	@Override
	public List<AnalyticsAssociation> findByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName,
		int start, int end,
		OrderByComparator<AnalyticsAssociation> orderByComparator) {

		return findByC_GtM_A(
			companyId, modifiedDate, associationClassName, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the analytics associations where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics associations
	 * @param end the upper bound of the range of analytics associations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics associations
	 */
	@Override
	public List<AnalyticsAssociation> findByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName,
		int start, int end,
		OrderByComparator<AnalyticsAssociation> orderByComparator,
		boolean useFinderCache) {

		associationClassName = Objects.toString(associationClassName, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AnalyticsAssociation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_GtM_A;
		finderArgs = new Object[] {
			companyId, _getTime(modifiedDate), associationClassName, start, end,
			orderByComparator
		};

		List<AnalyticsAssociation> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AnalyticsAssociation>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AnalyticsAssociation analyticsAssociation : list) {
					if ((companyId != analyticsAssociation.getCompanyId()) ||
						(modifiedDate.getTime() >=
							analyticsAssociation.getModifiedDate(
							).getTime()) ||
						!associationClassName.equals(
							analyticsAssociation.getAssociationClassName())) {

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

			sb.append(_SQL_SELECT_ANALYTICSASSOCIATION_WHERE);

			sb.append(_FINDER_COLUMN_C_GTM_A_COMPANYID_2);

			boolean bindModifiedDate = false;

			if (modifiedDate == null) {
				sb.append(_FINDER_COLUMN_C_GTM_A_MODIFIEDDATE_1);
			}
			else {
				bindModifiedDate = true;

				sb.append(_FINDER_COLUMN_C_GTM_A_MODIFIEDDATE_2);
			}

			boolean bindAssociationClassName = false;

			if (associationClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_GTM_A_ASSOCIATIONCLASSNAME_3);
			}
			else {
				bindAssociationClassName = true;

				sb.append(_FINDER_COLUMN_C_GTM_A_ASSOCIATIONCLASSNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AnalyticsAssociationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindModifiedDate) {
					queryPos.add(new Timestamp(modifiedDate.getTime()));
				}

				if (bindAssociationClassName) {
					queryPos.add(associationClassName);
				}

				list = (List<AnalyticsAssociation>)QueryUtil.list(
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
	 * Returns the first analytics association in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association
	 * @throws NoSuchAssociationException if a matching analytics association could not be found
	 */
	@Override
	public AnalyticsAssociation findByC_GtM_A_First(
			long companyId, Date modifiedDate, String associationClassName,
			OrderByComparator<AnalyticsAssociation> orderByComparator)
		throws NoSuchAssociationException {

		AnalyticsAssociation analyticsAssociation = fetchByC_GtM_A_First(
			companyId, modifiedDate, associationClassName, orderByComparator);

		if (analyticsAssociation != null) {
			return analyticsAssociation;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", modifiedDate>");
		sb.append(modifiedDate);

		sb.append(", associationClassName=");
		sb.append(associationClassName);

		sb.append("}");

		throw new NoSuchAssociationException(sb.toString());
	}

	/**
	 * Returns the first analytics association in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association, or <code>null</code> if a matching analytics association could not be found
	 */
	@Override
	public AnalyticsAssociation fetchByC_GtM_A_First(
		long companyId, Date modifiedDate, String associationClassName,
		OrderByComparator<AnalyticsAssociation> orderByComparator) {

		List<AnalyticsAssociation> list = findByC_GtM_A(
			companyId, modifiedDate, associationClassName, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last analytics association in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association
	 * @throws NoSuchAssociationException if a matching analytics association could not be found
	 */
	@Override
	public AnalyticsAssociation findByC_GtM_A_Last(
			long companyId, Date modifiedDate, String associationClassName,
			OrderByComparator<AnalyticsAssociation> orderByComparator)
		throws NoSuchAssociationException {

		AnalyticsAssociation analyticsAssociation = fetchByC_GtM_A_Last(
			companyId, modifiedDate, associationClassName, orderByComparator);

		if (analyticsAssociation != null) {
			return analyticsAssociation;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", modifiedDate>");
		sb.append(modifiedDate);

		sb.append(", associationClassName=");
		sb.append(associationClassName);

		sb.append("}");

		throw new NoSuchAssociationException(sb.toString());
	}

	/**
	 * Returns the last analytics association in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association, or <code>null</code> if a matching analytics association could not be found
	 */
	@Override
	public AnalyticsAssociation fetchByC_GtM_A_Last(
		long companyId, Date modifiedDate, String associationClassName,
		OrderByComparator<AnalyticsAssociation> orderByComparator) {

		int count = countByC_GtM_A(
			companyId, modifiedDate, associationClassName);

		if (count == 0) {
			return null;
		}

		List<AnalyticsAssociation> list = findByC_GtM_A(
			companyId, modifiedDate, associationClassName, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the analytics associations before and after the current analytics association in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param analyticsAssociationId the primary key of the current analytics association
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics association
	 * @throws NoSuchAssociationException if a analytics association with the primary key could not be found
	 */
	@Override
	public AnalyticsAssociation[] findByC_GtM_A_PrevAndNext(
			long analyticsAssociationId, long companyId, Date modifiedDate,
			String associationClassName,
			OrderByComparator<AnalyticsAssociation> orderByComparator)
		throws NoSuchAssociationException {

		associationClassName = Objects.toString(associationClassName, "");

		AnalyticsAssociation analyticsAssociation = findByPrimaryKey(
			analyticsAssociationId);

		Session session = null;

		try {
			session = openSession();

			AnalyticsAssociation[] array = new AnalyticsAssociationImpl[3];

			array[0] = getByC_GtM_A_PrevAndNext(
				session, analyticsAssociation, companyId, modifiedDate,
				associationClassName, orderByComparator, true);

			array[1] = analyticsAssociation;

			array[2] = getByC_GtM_A_PrevAndNext(
				session, analyticsAssociation, companyId, modifiedDate,
				associationClassName, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnalyticsAssociation getByC_GtM_A_PrevAndNext(
		Session session, AnalyticsAssociation analyticsAssociation,
		long companyId, Date modifiedDate, String associationClassName,
		OrderByComparator<AnalyticsAssociation> orderByComparator,
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

		sb.append(_SQL_SELECT_ANALYTICSASSOCIATION_WHERE);

		sb.append(_FINDER_COLUMN_C_GTM_A_COMPANYID_2);

		boolean bindModifiedDate = false;

		if (modifiedDate == null) {
			sb.append(_FINDER_COLUMN_C_GTM_A_MODIFIEDDATE_1);
		}
		else {
			bindModifiedDate = true;

			sb.append(_FINDER_COLUMN_C_GTM_A_MODIFIEDDATE_2);
		}

		boolean bindAssociationClassName = false;

		if (associationClassName.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_GTM_A_ASSOCIATIONCLASSNAME_3);
		}
		else {
			bindAssociationClassName = true;

			sb.append(_FINDER_COLUMN_C_GTM_A_ASSOCIATIONCLASSNAME_2);
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
			sb.append(AnalyticsAssociationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindModifiedDate) {
			queryPos.add(new Timestamp(modifiedDate.getTime()));
		}

		if (bindAssociationClassName) {
			queryPos.add(associationClassName);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						analyticsAssociation)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AnalyticsAssociation> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the analytics associations where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 */
	@Override
	public void removeByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName) {

		for (AnalyticsAssociation analyticsAssociation :
				findByC_GtM_A(
					companyId, modifiedDate, associationClassName,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(analyticsAssociation);
		}
	}

	/**
	 * Returns the number of analytics associations where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @return the number of matching analytics associations
	 */
	@Override
	public int countByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName) {

		associationClassName = Objects.toString(associationClassName, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AnalyticsAssociation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByC_GtM_A;

			finderArgs = new Object[] {
				companyId, _getTime(modifiedDate), associationClassName
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ANALYTICSASSOCIATION_WHERE);

			sb.append(_FINDER_COLUMN_C_GTM_A_COMPANYID_2);

			boolean bindModifiedDate = false;

			if (modifiedDate == null) {
				sb.append(_FINDER_COLUMN_C_GTM_A_MODIFIEDDATE_1);
			}
			else {
				bindModifiedDate = true;

				sb.append(_FINDER_COLUMN_C_GTM_A_MODIFIEDDATE_2);
			}

			boolean bindAssociationClassName = false;

			if (associationClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_GTM_A_ASSOCIATIONCLASSNAME_3);
			}
			else {
				bindAssociationClassName = true;

				sb.append(_FINDER_COLUMN_C_GTM_A_ASSOCIATIONCLASSNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindModifiedDate) {
					queryPos.add(new Timestamp(modifiedDate.getTime()));
				}

				if (bindAssociationClassName) {
					queryPos.add(associationClassName);
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

	private static final String _FINDER_COLUMN_C_GTM_A_COMPANYID_2 =
		"analyticsAssociation.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_GTM_A_MODIFIEDDATE_1 =
		"analyticsAssociation.modifiedDate IS NULL AND ";

	private static final String _FINDER_COLUMN_C_GTM_A_MODIFIEDDATE_2 =
		"analyticsAssociation.modifiedDate > ? AND ";

	private static final String _FINDER_COLUMN_C_GTM_A_ASSOCIATIONCLASSNAME_2 =
		"analyticsAssociation.associationClassName = ?";

	private static final String _FINDER_COLUMN_C_GTM_A_ASSOCIATIONCLASSNAME_3 =
		"(analyticsAssociation.associationClassName IS NULL OR analyticsAssociation.associationClassName = '')";

	private FinderPath _finderPathWithPaginationFindByC_A_A;
	private FinderPath _finderPathWithoutPaginationFindByC_A_A;
	private FinderPath _finderPathCountByC_A_A;

	/**
	 * Returns all the analytics associations where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @return the matching analytics associations
	 */
	@Override
	public List<AnalyticsAssociation> findByC_A_A(
		long companyId, String associationClassName, long associationClassPK) {

		return findByC_A_A(
			companyId, associationClassName, associationClassPK,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the analytics associations where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param start the lower bound of the range of analytics associations
	 * @param end the upper bound of the range of analytics associations (not inclusive)
	 * @return the range of matching analytics associations
	 */
	@Override
	public List<AnalyticsAssociation> findByC_A_A(
		long companyId, String associationClassName, long associationClassPK,
		int start, int end) {

		return findByC_A_A(
			companyId, associationClassName, associationClassPK, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the analytics associations where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param start the lower bound of the range of analytics associations
	 * @param end the upper bound of the range of analytics associations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics associations
	 */
	@Override
	public List<AnalyticsAssociation> findByC_A_A(
		long companyId, String associationClassName, long associationClassPK,
		int start, int end,
		OrderByComparator<AnalyticsAssociation> orderByComparator) {

		return findByC_A_A(
			companyId, associationClassName, associationClassPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the analytics associations where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param start the lower bound of the range of analytics associations
	 * @param end the upper bound of the range of analytics associations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics associations
	 */
	@Override
	public List<AnalyticsAssociation> findByC_A_A(
		long companyId, String associationClassName, long associationClassPK,
		int start, int end,
		OrderByComparator<AnalyticsAssociation> orderByComparator,
		boolean useFinderCache) {

		associationClassName = Objects.toString(associationClassName, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AnalyticsAssociation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_A_A;
				finderArgs = new Object[] {
					companyId, associationClassName, associationClassPK
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_A_A;
			finderArgs = new Object[] {
				companyId, associationClassName, associationClassPK, start, end,
				orderByComparator
			};
		}

		List<AnalyticsAssociation> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AnalyticsAssociation>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AnalyticsAssociation analyticsAssociation : list) {
					if ((companyId != analyticsAssociation.getCompanyId()) ||
						!associationClassName.equals(
							analyticsAssociation.getAssociationClassName()) ||
						(associationClassPK !=
							analyticsAssociation.getAssociationClassPK())) {

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

			sb.append(_SQL_SELECT_ANALYTICSASSOCIATION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_A_COMPANYID_2);

			boolean bindAssociationClassName = false;

			if (associationClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_A_A_ASSOCIATIONCLASSNAME_3);
			}
			else {
				bindAssociationClassName = true;

				sb.append(_FINDER_COLUMN_C_A_A_ASSOCIATIONCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_C_A_A_ASSOCIATIONCLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AnalyticsAssociationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindAssociationClassName) {
					queryPos.add(associationClassName);
				}

				queryPos.add(associationClassPK);

				list = (List<AnalyticsAssociation>)QueryUtil.list(
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
	 * Returns the first analytics association in the ordered set where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association
	 * @throws NoSuchAssociationException if a matching analytics association could not be found
	 */
	@Override
	public AnalyticsAssociation findByC_A_A_First(
			long companyId, String associationClassName,
			long associationClassPK,
			OrderByComparator<AnalyticsAssociation> orderByComparator)
		throws NoSuchAssociationException {

		AnalyticsAssociation analyticsAssociation = fetchByC_A_A_First(
			companyId, associationClassName, associationClassPK,
			orderByComparator);

		if (analyticsAssociation != null) {
			return analyticsAssociation;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", associationClassName=");
		sb.append(associationClassName);

		sb.append(", associationClassPK=");
		sb.append(associationClassPK);

		sb.append("}");

		throw new NoSuchAssociationException(sb.toString());
	}

	/**
	 * Returns the first analytics association in the ordered set where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association, or <code>null</code> if a matching analytics association could not be found
	 */
	@Override
	public AnalyticsAssociation fetchByC_A_A_First(
		long companyId, String associationClassName, long associationClassPK,
		OrderByComparator<AnalyticsAssociation> orderByComparator) {

		List<AnalyticsAssociation> list = findByC_A_A(
			companyId, associationClassName, associationClassPK, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last analytics association in the ordered set where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association
	 * @throws NoSuchAssociationException if a matching analytics association could not be found
	 */
	@Override
	public AnalyticsAssociation findByC_A_A_Last(
			long companyId, String associationClassName,
			long associationClassPK,
			OrderByComparator<AnalyticsAssociation> orderByComparator)
		throws NoSuchAssociationException {

		AnalyticsAssociation analyticsAssociation = fetchByC_A_A_Last(
			companyId, associationClassName, associationClassPK,
			orderByComparator);

		if (analyticsAssociation != null) {
			return analyticsAssociation;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", associationClassName=");
		sb.append(associationClassName);

		sb.append(", associationClassPK=");
		sb.append(associationClassPK);

		sb.append("}");

		throw new NoSuchAssociationException(sb.toString());
	}

	/**
	 * Returns the last analytics association in the ordered set where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association, or <code>null</code> if a matching analytics association could not be found
	 */
	@Override
	public AnalyticsAssociation fetchByC_A_A_Last(
		long companyId, String associationClassName, long associationClassPK,
		OrderByComparator<AnalyticsAssociation> orderByComparator) {

		int count = countByC_A_A(
			companyId, associationClassName, associationClassPK);

		if (count == 0) {
			return null;
		}

		List<AnalyticsAssociation> list = findByC_A_A(
			companyId, associationClassName, associationClassPK, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the analytics associations before and after the current analytics association in the ordered set where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param analyticsAssociationId the primary key of the current analytics association
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics association
	 * @throws NoSuchAssociationException if a analytics association with the primary key could not be found
	 */
	@Override
	public AnalyticsAssociation[] findByC_A_A_PrevAndNext(
			long analyticsAssociationId, long companyId,
			String associationClassName, long associationClassPK,
			OrderByComparator<AnalyticsAssociation> orderByComparator)
		throws NoSuchAssociationException {

		associationClassName = Objects.toString(associationClassName, "");

		AnalyticsAssociation analyticsAssociation = findByPrimaryKey(
			analyticsAssociationId);

		Session session = null;

		try {
			session = openSession();

			AnalyticsAssociation[] array = new AnalyticsAssociationImpl[3];

			array[0] = getByC_A_A_PrevAndNext(
				session, analyticsAssociation, companyId, associationClassName,
				associationClassPK, orderByComparator, true);

			array[1] = analyticsAssociation;

			array[2] = getByC_A_A_PrevAndNext(
				session, analyticsAssociation, companyId, associationClassName,
				associationClassPK, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnalyticsAssociation getByC_A_A_PrevAndNext(
		Session session, AnalyticsAssociation analyticsAssociation,
		long companyId, String associationClassName, long associationClassPK,
		OrderByComparator<AnalyticsAssociation> orderByComparator,
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

		sb.append(_SQL_SELECT_ANALYTICSASSOCIATION_WHERE);

		sb.append(_FINDER_COLUMN_C_A_A_COMPANYID_2);

		boolean bindAssociationClassName = false;

		if (associationClassName.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_A_A_ASSOCIATIONCLASSNAME_3);
		}
		else {
			bindAssociationClassName = true;

			sb.append(_FINDER_COLUMN_C_A_A_ASSOCIATIONCLASSNAME_2);
		}

		sb.append(_FINDER_COLUMN_C_A_A_ASSOCIATIONCLASSPK_2);

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
			sb.append(AnalyticsAssociationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindAssociationClassName) {
			queryPos.add(associationClassName);
		}

		queryPos.add(associationClassPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						analyticsAssociation)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AnalyticsAssociation> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the analytics associations where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 */
	@Override
	public void removeByC_A_A(
		long companyId, String associationClassName, long associationClassPK) {

		for (AnalyticsAssociation analyticsAssociation :
				findByC_A_A(
					companyId, associationClassName, associationClassPK,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(analyticsAssociation);
		}
	}

	/**
	 * Returns the number of analytics associations where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @return the number of matching analytics associations
	 */
	@Override
	public int countByC_A_A(
		long companyId, String associationClassName, long associationClassPK) {

		associationClassName = Objects.toString(associationClassName, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AnalyticsAssociation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_A_A;

			finderArgs = new Object[] {
				companyId, associationClassName, associationClassPK
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ANALYTICSASSOCIATION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_A_COMPANYID_2);

			boolean bindAssociationClassName = false;

			if (associationClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_A_A_ASSOCIATIONCLASSNAME_3);
			}
			else {
				bindAssociationClassName = true;

				sb.append(_FINDER_COLUMN_C_A_A_ASSOCIATIONCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_C_A_A_ASSOCIATIONCLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindAssociationClassName) {
					queryPos.add(associationClassName);
				}

				queryPos.add(associationClassPK);

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

	private static final String _FINDER_COLUMN_C_A_A_COMPANYID_2 =
		"analyticsAssociation.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_A_ASSOCIATIONCLASSNAME_2 =
		"analyticsAssociation.associationClassName = ? AND ";

	private static final String _FINDER_COLUMN_C_A_A_ASSOCIATIONCLASSNAME_3 =
		"(analyticsAssociation.associationClassName IS NULL OR analyticsAssociation.associationClassName = '') AND ";

	private static final String _FINDER_COLUMN_C_A_A_ASSOCIATIONCLASSPK_2 =
		"analyticsAssociation.associationClassPK = ?";

	public AnalyticsAssociationPersistenceImpl() {
		setModelClass(AnalyticsAssociation.class);

		setModelImplClass(AnalyticsAssociationImpl.class);
		setModelPKClass(long.class);

		setTable(AnalyticsAssociationTable.INSTANCE);
	}

	/**
	 * Caches the analytics association in the entity cache if it is enabled.
	 *
	 * @param analyticsAssociation the analytics association
	 */
	@Override
	public void cacheResult(AnalyticsAssociation analyticsAssociation) {
		if (analyticsAssociation.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			AnalyticsAssociationImpl.class,
			analyticsAssociation.getPrimaryKey(), analyticsAssociation);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the analytics associations in the entity cache if it is enabled.
	 *
	 * @param analyticsAssociations the analytics associations
	 */
	@Override
	public void cacheResult(List<AnalyticsAssociation> analyticsAssociations) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (analyticsAssociations.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AnalyticsAssociation analyticsAssociation :
				analyticsAssociations) {

			if (analyticsAssociation.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					AnalyticsAssociationImpl.class,
					analyticsAssociation.getPrimaryKey()) == null) {

				cacheResult(analyticsAssociation);
			}
		}
	}

	/**
	 * Clears the cache for all analytics associations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AnalyticsAssociationImpl.class);

		finderCache.clearCache(AnalyticsAssociationImpl.class);
	}

	/**
	 * Clears the cache for the analytics association.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AnalyticsAssociation analyticsAssociation) {
		entityCache.removeResult(
			AnalyticsAssociationImpl.class, analyticsAssociation);
	}

	@Override
	public void clearCache(List<AnalyticsAssociation> analyticsAssociations) {
		for (AnalyticsAssociation analyticsAssociation :
				analyticsAssociations) {

			entityCache.removeResult(
				AnalyticsAssociationImpl.class, analyticsAssociation);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AnalyticsAssociationImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AnalyticsAssociationImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new analytics association with the primary key. Does not add the analytics association to the database.
	 *
	 * @param analyticsAssociationId the primary key for the new analytics association
	 * @return the new analytics association
	 */
	@Override
	public AnalyticsAssociation create(long analyticsAssociationId) {
		AnalyticsAssociation analyticsAssociation =
			new AnalyticsAssociationImpl();

		analyticsAssociation.setNew(true);
		analyticsAssociation.setPrimaryKey(analyticsAssociationId);

		analyticsAssociation.setCompanyId(CompanyThreadLocal.getCompanyId());

		return analyticsAssociation;
	}

	/**
	 * Removes the analytics association with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsAssociationId the primary key of the analytics association
	 * @return the analytics association that was removed
	 * @throws NoSuchAssociationException if a analytics association with the primary key could not be found
	 */
	@Override
	public AnalyticsAssociation remove(long analyticsAssociationId)
		throws NoSuchAssociationException {

		return remove((Serializable)analyticsAssociationId);
	}

	/**
	 * Removes the analytics association with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the analytics association
	 * @return the analytics association that was removed
	 * @throws NoSuchAssociationException if a analytics association with the primary key could not be found
	 */
	@Override
	public AnalyticsAssociation remove(Serializable primaryKey)
		throws NoSuchAssociationException {

		Session session = null;

		try {
			session = openSession();

			AnalyticsAssociation analyticsAssociation =
				(AnalyticsAssociation)session.get(
					AnalyticsAssociationImpl.class, primaryKey);

			if (analyticsAssociation == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAssociationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(analyticsAssociation);
		}
		catch (NoSuchAssociationException noSuchEntityException) {
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
	protected AnalyticsAssociation removeImpl(
		AnalyticsAssociation analyticsAssociation) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(analyticsAssociation)) {
				analyticsAssociation = (AnalyticsAssociation)session.get(
					AnalyticsAssociationImpl.class,
					analyticsAssociation.getPrimaryKeyObj());
			}

			if ((analyticsAssociation != null) &&
				ctPersistenceHelper.isRemove(analyticsAssociation)) {

				session.delete(analyticsAssociation);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (analyticsAssociation != null) {
			clearCache(analyticsAssociation);
		}

		return analyticsAssociation;
	}

	@Override
	public AnalyticsAssociation updateImpl(
		AnalyticsAssociation analyticsAssociation) {

		boolean isNew = analyticsAssociation.isNew();

		if (!(analyticsAssociation instanceof AnalyticsAssociationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(analyticsAssociation.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					analyticsAssociation);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in analyticsAssociation proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AnalyticsAssociation implementation " +
					analyticsAssociation.getClass());
		}

		AnalyticsAssociationModelImpl analyticsAssociationModelImpl =
			(AnalyticsAssociationModelImpl)analyticsAssociation;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (analyticsAssociation.getCreateDate() == null)) {
			if (serviceContext == null) {
				analyticsAssociation.setCreateDate(date);
			}
			else {
				analyticsAssociation.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!analyticsAssociationModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				analyticsAssociation.setModifiedDate(date);
			}
			else {
				analyticsAssociation.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(analyticsAssociation)) {
				if (!isNew) {
					session.evict(
						AnalyticsAssociationImpl.class,
						analyticsAssociation.getPrimaryKeyObj());
				}

				session.save(analyticsAssociation);
			}
			else {
				analyticsAssociation = (AnalyticsAssociation)session.merge(
					analyticsAssociation);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (analyticsAssociation.getCtCollectionId() != 0) {
			if (isNew) {
				analyticsAssociation.setNew(false);
			}

			analyticsAssociation.resetOriginalValues();

			return analyticsAssociation;
		}

		entityCache.putResult(
			AnalyticsAssociationImpl.class, analyticsAssociationModelImpl,
			false, true);

		if (isNew) {
			analyticsAssociation.setNew(false);
		}

		analyticsAssociation.resetOriginalValues();

		return analyticsAssociation;
	}

	/**
	 * Returns the analytics association with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the analytics association
	 * @return the analytics association
	 * @throws NoSuchAssociationException if a analytics association with the primary key could not be found
	 */
	@Override
	public AnalyticsAssociation findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAssociationException {

		AnalyticsAssociation analyticsAssociation = fetchByPrimaryKey(
			primaryKey);

		if (analyticsAssociation == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAssociationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return analyticsAssociation;
	}

	/**
	 * Returns the analytics association with the primary key or throws a <code>NoSuchAssociationException</code> if it could not be found.
	 *
	 * @param analyticsAssociationId the primary key of the analytics association
	 * @return the analytics association
	 * @throws NoSuchAssociationException if a analytics association with the primary key could not be found
	 */
	@Override
	public AnalyticsAssociation findByPrimaryKey(long analyticsAssociationId)
		throws NoSuchAssociationException {

		return findByPrimaryKey((Serializable)analyticsAssociationId);
	}

	/**
	 * Returns the analytics association with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the analytics association
	 * @return the analytics association, or <code>null</code> if a analytics association with the primary key could not be found
	 */
	@Override
	public AnalyticsAssociation fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				AnalyticsAssociation.class, primaryKey)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		AnalyticsAssociation analyticsAssociation = null;

		Session session = null;

		try {
			session = openSession();

			analyticsAssociation = (AnalyticsAssociation)session.get(
				AnalyticsAssociationImpl.class, primaryKey);

			if (analyticsAssociation != null) {
				cacheResult(analyticsAssociation);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return analyticsAssociation;
	}

	/**
	 * Returns the analytics association with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param analyticsAssociationId the primary key of the analytics association
	 * @return the analytics association, or <code>null</code> if a analytics association with the primary key could not be found
	 */
	@Override
	public AnalyticsAssociation fetchByPrimaryKey(long analyticsAssociationId) {
		return fetchByPrimaryKey((Serializable)analyticsAssociationId);
	}

	@Override
	public Map<Serializable, AnalyticsAssociation> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(AnalyticsAssociation.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AnalyticsAssociation> map =
			new HashMap<Serializable, AnalyticsAssociation>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AnalyticsAssociation analyticsAssociation = fetchByPrimaryKey(
				primaryKey);

			if (analyticsAssociation != null) {
				map.put(primaryKey, analyticsAssociation);
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

			for (AnalyticsAssociation analyticsAssociation :
					(List<AnalyticsAssociation>)query.list()) {

				map.put(
					analyticsAssociation.getPrimaryKeyObj(),
					analyticsAssociation);

				cacheResult(analyticsAssociation);
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
	 * Returns all the analytics associations.
	 *
	 * @return the analytics associations
	 */
	@Override
	public List<AnalyticsAssociation> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the analytics associations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics associations
	 * @param end the upper bound of the range of analytics associations (not inclusive)
	 * @return the range of analytics associations
	 */
	@Override
	public List<AnalyticsAssociation> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the analytics associations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics associations
	 * @param end the upper bound of the range of analytics associations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of analytics associations
	 */
	@Override
	public List<AnalyticsAssociation> findAll(
		int start, int end,
		OrderByComparator<AnalyticsAssociation> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the analytics associations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics associations
	 * @param end the upper bound of the range of analytics associations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of analytics associations
	 */
	@Override
	public List<AnalyticsAssociation> findAll(
		int start, int end,
		OrderByComparator<AnalyticsAssociation> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AnalyticsAssociation.class);

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

		List<AnalyticsAssociation> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AnalyticsAssociation>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ANALYTICSASSOCIATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ANALYTICSASSOCIATION;

				sql = sql.concat(AnalyticsAssociationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AnalyticsAssociation>)QueryUtil.list(
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
	 * Removes all the analytics associations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AnalyticsAssociation analyticsAssociation : findAll()) {
			remove(analyticsAssociation);
		}
	}

	/**
	 * Returns the number of analytics associations.
	 *
	 * @return the number of analytics associations
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AnalyticsAssociation.class);

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
					_SQL_COUNT_ANALYTICSASSOCIATION);

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
		return "analyticsAssociationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ANALYTICSASSOCIATION;
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
		return AnalyticsAssociationModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AnalyticsAssociation";
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
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("associationClassName");
		ctStrictColumnNames.add("associationClassPK");
		ctStrictColumnNames.add("className");
		ctStrictColumnNames.add("classPK");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("analyticsAssociationId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the analytics association persistence.
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

		_finderPathWithPaginationFindByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "associationClassName"}, true);

		_finderPathWithoutPaginationFindByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "associationClassName"}, true);

		_finderPathCountByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "associationClassName"}, false);

		_finderPathWithPaginationFindByC_GtM_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_GtM_A",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "modifiedDate", "associationClassName"},
			true);

		_finderPathWithPaginationCountByC_GtM_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_GtM_A",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "modifiedDate", "associationClassName"},
			false);

		_finderPathWithPaginationFindByC_A_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"companyId", "associationClassName", "associationClassPK"
			},
			true);

		_finderPathWithoutPaginationFindByC_A_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			},
			new String[] {
				"companyId", "associationClassName", "associationClassPK"
			},
			true);

		_finderPathCountByC_A_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			},
			new String[] {
				"companyId", "associationClassName", "associationClassPK"
			},
			false);

		AnalyticsAssociationUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		AnalyticsAssociationUtil.setPersistence(null);

		entityCache.removeCache(AnalyticsAssociationImpl.class.getName());
	}

	@Override
	@Reference(
		target = AnalyticsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AnalyticsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AnalyticsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_ANALYTICSASSOCIATION =
		"SELECT analyticsAssociation FROM AnalyticsAssociation analyticsAssociation";

	private static final String _SQL_SELECT_ANALYTICSASSOCIATION_WHERE =
		"SELECT analyticsAssociation FROM AnalyticsAssociation analyticsAssociation WHERE ";

	private static final String _SQL_COUNT_ANALYTICSASSOCIATION =
		"SELECT COUNT(analyticsAssociation) FROM AnalyticsAssociation analyticsAssociation";

	private static final String _SQL_COUNT_ANALYTICSASSOCIATION_WHERE =
		"SELECT COUNT(analyticsAssociation) FROM AnalyticsAssociation analyticsAssociation WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"analyticsAssociation.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AnalyticsAssociation exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AnalyticsAssociation exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsAssociationPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}