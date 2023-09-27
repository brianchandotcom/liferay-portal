/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.persistence.impl;

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
import com.liferay.portal.workflow.kaleo.exception.NoSuchNotificationRecipientException;
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient;
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipientTable;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationRecipientImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationRecipientModelImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoNotificationRecipientPersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoNotificationRecipientUtil;
import com.liferay.portal.workflow.kaleo.service.persistence.impl.constants.KaleoPersistenceConstants;

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
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the kaleo notification recipient service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = KaleoNotificationRecipientPersistence.class)
public class KaleoNotificationRecipientPersistenceImpl
	extends BasePersistenceImpl<KaleoNotificationRecipient>
	implements KaleoNotificationRecipientPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>KaleoNotificationRecipientUtil</code> to access the kaleo notification recipient persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		KaleoNotificationRecipientImpl.class.getName();

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
	 * Returns all the kaleo notification recipients where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo notification recipients
	 */
	@Override
	public List<KaleoNotificationRecipient> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo notification recipients where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo notification recipients
	 * @param end the upper bound of the range of kaleo notification recipients (not inclusive)
	 * @return the range of matching kaleo notification recipients
	 */
	@Override
	public List<KaleoNotificationRecipient> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo notification recipients where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo notification recipients
	 * @param end the upper bound of the range of kaleo notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo notification recipients
	 */
	@Override
	public List<KaleoNotificationRecipient> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoNotificationRecipient> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo notification recipients where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo notification recipients
	 * @param end the upper bound of the range of kaleo notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo notification recipients
	 */
	@Override
	public List<KaleoNotificationRecipient> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoNotificationRecipient> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			KaleoNotificationRecipient.class);

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

		List<KaleoNotificationRecipient> list = null;

		if (useFinderCache && productionMode) {
			list = (List<KaleoNotificationRecipient>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoNotificationRecipient kaleoNotificationRecipient :
						list) {

					if (companyId !=
							kaleoNotificationRecipient.getCompanyId()) {

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

			sb.append(_SQL_SELECT_KALEONOTIFICATIONRECIPIENT_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoNotificationRecipientModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<KaleoNotificationRecipient>)QueryUtil.list(
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
	 * Returns the first kaleo notification recipient in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching kaleo notification recipient could not be found
	 */
	@Override
	public KaleoNotificationRecipient findByCompanyId_First(
			long companyId,
			OrderByComparator<KaleoNotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException {

		KaleoNotificationRecipient kaleoNotificationRecipient =
			fetchByCompanyId_First(companyId, orderByComparator);

		if (kaleoNotificationRecipient != null) {
			return kaleoNotificationRecipient;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchNotificationRecipientException(sb.toString());
	}

	/**
	 * Returns the first kaleo notification recipient in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo notification recipient, or <code>null</code> if a matching kaleo notification recipient could not be found
	 */
	@Override
	public KaleoNotificationRecipient fetchByCompanyId_First(
		long companyId,
		OrderByComparator<KaleoNotificationRecipient> orderByComparator) {

		List<KaleoNotificationRecipient> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo notification recipient in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching kaleo notification recipient could not be found
	 */
	@Override
	public KaleoNotificationRecipient findByCompanyId_Last(
			long companyId,
			OrderByComparator<KaleoNotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException {

		KaleoNotificationRecipient kaleoNotificationRecipient =
			fetchByCompanyId_Last(companyId, orderByComparator);

		if (kaleoNotificationRecipient != null) {
			return kaleoNotificationRecipient;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchNotificationRecipientException(sb.toString());
	}

	/**
	 * Returns the last kaleo notification recipient in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo notification recipient, or <code>null</code> if a matching kaleo notification recipient could not be found
	 */
	@Override
	public KaleoNotificationRecipient fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<KaleoNotificationRecipient> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<KaleoNotificationRecipient> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo notification recipients before and after the current kaleo notification recipient in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoNotificationRecipientId the primary key of the current kaleo notification recipient
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo notification recipient
	 * @throws NoSuchNotificationRecipientException if a kaleo notification recipient with the primary key could not be found
	 */
	@Override
	public KaleoNotificationRecipient[] findByCompanyId_PrevAndNext(
			long kaleoNotificationRecipientId, long companyId,
			OrderByComparator<KaleoNotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException {

		KaleoNotificationRecipient kaleoNotificationRecipient =
			findByPrimaryKey(kaleoNotificationRecipientId);

		Session session = null;

		try {
			session = openSession();

			KaleoNotificationRecipient[] array =
				new KaleoNotificationRecipientImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, kaleoNotificationRecipient, companyId,
				orderByComparator, true);

			array[1] = kaleoNotificationRecipient;

			array[2] = getByCompanyId_PrevAndNext(
				session, kaleoNotificationRecipient, companyId,
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

	protected KaleoNotificationRecipient getByCompanyId_PrevAndNext(
		Session session, KaleoNotificationRecipient kaleoNotificationRecipient,
		long companyId,
		OrderByComparator<KaleoNotificationRecipient> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEONOTIFICATIONRECIPIENT_WHERE);

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
			sb.append(KaleoNotificationRecipientModelImpl.ORDER_BY_JPQL);
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
						kaleoNotificationRecipient)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoNotificationRecipient> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo notification recipients where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (KaleoNotificationRecipient kaleoNotificationRecipient :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoNotificationRecipient);
		}
	}

	/**
	 * Returns the number of kaleo notification recipients where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo notification recipients
	 */
	@Override
	public int countByCompanyId(long companyId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			KaleoNotificationRecipient.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCompanyId;

			finderArgs = new Object[] {companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEONOTIFICATIONRECIPIENT_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"kaleoNotificationRecipient.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoDefinitionVersionId;
	private FinderPath
		_finderPathWithoutPaginationFindByKaleoDefinitionVersionId;
	private FinderPath _finderPathCountByKaleoDefinitionVersionId;

	/**
	 * Returns all the kaleo notification recipients where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo notification recipients
	 */
	@Override
	public List<KaleoNotificationRecipient> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo notification recipients where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo notification recipients
	 * @param end the upper bound of the range of kaleo notification recipients (not inclusive)
	 * @return the range of matching kaleo notification recipients
	 */
	@Override
	public List<KaleoNotificationRecipient> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo notification recipients where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo notification recipients
	 * @param end the upper bound of the range of kaleo notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo notification recipients
	 */
	@Override
	public List<KaleoNotificationRecipient> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoNotificationRecipient> orderByComparator) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo notification recipients where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo notification recipients
	 * @param end the upper bound of the range of kaleo notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo notification recipients
	 */
	@Override
	public List<KaleoNotificationRecipient> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoNotificationRecipient> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			KaleoNotificationRecipient.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByKaleoDefinitionVersionId;
				finderArgs = new Object[] {kaleoDefinitionVersionId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath =
				_finderPathWithPaginationFindByKaleoDefinitionVersionId;
			finderArgs = new Object[] {
				kaleoDefinitionVersionId, start, end, orderByComparator
			};
		}

		List<KaleoNotificationRecipient> list = null;

		if (useFinderCache && productionMode) {
			list = (List<KaleoNotificationRecipient>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoNotificationRecipient kaleoNotificationRecipient :
						list) {

					if (kaleoDefinitionVersionId !=
							kaleoNotificationRecipient.
								getKaleoDefinitionVersionId()) {

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

			sb.append(_SQL_SELECT_KALEONOTIFICATIONRECIPIENT_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoNotificationRecipientModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionVersionId);

				list = (List<KaleoNotificationRecipient>)QueryUtil.list(
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
	 * Returns the first kaleo notification recipient in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching kaleo notification recipient could not be found
	 */
	@Override
	public KaleoNotificationRecipient findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoNotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException {

		KaleoNotificationRecipient kaleoNotificationRecipient =
			fetchByKaleoDefinitionVersionId_First(
				kaleoDefinitionVersionId, orderByComparator);

		if (kaleoNotificationRecipient != null) {
			return kaleoNotificationRecipient;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchNotificationRecipientException(sb.toString());
	}

	/**
	 * Returns the first kaleo notification recipient in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo notification recipient, or <code>null</code> if a matching kaleo notification recipient could not be found
	 */
	@Override
	public KaleoNotificationRecipient fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNotificationRecipient> orderByComparator) {

		List<KaleoNotificationRecipient> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo notification recipient in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching kaleo notification recipient could not be found
	 */
	@Override
	public KaleoNotificationRecipient findByKaleoDefinitionVersionId_Last(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoNotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException {

		KaleoNotificationRecipient kaleoNotificationRecipient =
			fetchByKaleoDefinitionVersionId_Last(
				kaleoDefinitionVersionId, orderByComparator);

		if (kaleoNotificationRecipient != null) {
			return kaleoNotificationRecipient;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchNotificationRecipientException(sb.toString());
	}

	/**
	 * Returns the last kaleo notification recipient in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo notification recipient, or <code>null</code> if a matching kaleo notification recipient could not be found
	 */
	@Override
	public KaleoNotificationRecipient fetchByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNotificationRecipient> orderByComparator) {

		int count = countByKaleoDefinitionVersionId(kaleoDefinitionVersionId);

		if (count == 0) {
			return null;
		}

		List<KaleoNotificationRecipient> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo notification recipients before and after the current kaleo notification recipient in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoNotificationRecipientId the primary key of the current kaleo notification recipient
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo notification recipient
	 * @throws NoSuchNotificationRecipientException if a kaleo notification recipient with the primary key could not be found
	 */
	@Override
	public KaleoNotificationRecipient[]
			findByKaleoDefinitionVersionId_PrevAndNext(
				long kaleoNotificationRecipientId,
				long kaleoDefinitionVersionId,
				OrderByComparator<KaleoNotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException {

		KaleoNotificationRecipient kaleoNotificationRecipient =
			findByPrimaryKey(kaleoNotificationRecipientId);

		Session session = null;

		try {
			session = openSession();

			KaleoNotificationRecipient[] array =
				new KaleoNotificationRecipientImpl[3];

			array[0] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoNotificationRecipient, kaleoDefinitionVersionId,
				orderByComparator, true);

			array[1] = kaleoNotificationRecipient;

			array[2] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoNotificationRecipient, kaleoDefinitionVersionId,
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

	protected KaleoNotificationRecipient
		getByKaleoDefinitionVersionId_PrevAndNext(
			Session session,
			KaleoNotificationRecipient kaleoNotificationRecipient,
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoNotificationRecipient> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEONOTIFICATIONRECIPIENT_WHERE);

		sb.append(
			_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

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
			sb.append(KaleoNotificationRecipientModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoDefinitionVersionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoNotificationRecipient)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoNotificationRecipient> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo notification recipients where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	@Override
	public void removeByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		for (KaleoNotificationRecipient kaleoNotificationRecipient :
				findByKaleoDefinitionVersionId(
					kaleoDefinitionVersionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoNotificationRecipient);
		}
	}

	/**
	 * Returns the number of kaleo notification recipients where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo notification recipients
	 */
	@Override
	public int countByKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			KaleoNotificationRecipient.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByKaleoDefinitionVersionId;

			finderArgs = new Object[] {kaleoDefinitionVersionId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEONOTIFICATIONRECIPIENT_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionVersionId);

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

	private static final String
		_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2 =
			"kaleoNotificationRecipient.kaleoDefinitionVersionId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoNotificationId;
	private FinderPath _finderPathWithoutPaginationFindByKaleoNotificationId;
	private FinderPath _finderPathCountByKaleoNotificationId;

	/**
	 * Returns all the kaleo notification recipients where kaleoNotificationId = &#63;.
	 *
	 * @param kaleoNotificationId the kaleo notification ID
	 * @return the matching kaleo notification recipients
	 */
	@Override
	public List<KaleoNotificationRecipient> findByKaleoNotificationId(
		long kaleoNotificationId) {

		return findByKaleoNotificationId(
			kaleoNotificationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo notification recipients where kaleoNotificationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoNotificationId the kaleo notification ID
	 * @param start the lower bound of the range of kaleo notification recipients
	 * @param end the upper bound of the range of kaleo notification recipients (not inclusive)
	 * @return the range of matching kaleo notification recipients
	 */
	@Override
	public List<KaleoNotificationRecipient> findByKaleoNotificationId(
		long kaleoNotificationId, int start, int end) {

		return findByKaleoNotificationId(kaleoNotificationId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo notification recipients where kaleoNotificationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoNotificationId the kaleo notification ID
	 * @param start the lower bound of the range of kaleo notification recipients
	 * @param end the upper bound of the range of kaleo notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo notification recipients
	 */
	@Override
	public List<KaleoNotificationRecipient> findByKaleoNotificationId(
		long kaleoNotificationId, int start, int end,
		OrderByComparator<KaleoNotificationRecipient> orderByComparator) {

		return findByKaleoNotificationId(
			kaleoNotificationId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo notification recipients where kaleoNotificationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoNotificationId the kaleo notification ID
	 * @param start the lower bound of the range of kaleo notification recipients
	 * @param end the upper bound of the range of kaleo notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo notification recipients
	 */
	@Override
	public List<KaleoNotificationRecipient> findByKaleoNotificationId(
		long kaleoNotificationId, int start, int end,
		OrderByComparator<KaleoNotificationRecipient> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			KaleoNotificationRecipient.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByKaleoNotificationId;
				finderArgs = new Object[] {kaleoNotificationId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByKaleoNotificationId;
			finderArgs = new Object[] {
				kaleoNotificationId, start, end, orderByComparator
			};
		}

		List<KaleoNotificationRecipient> list = null;

		if (useFinderCache && productionMode) {
			list = (List<KaleoNotificationRecipient>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoNotificationRecipient kaleoNotificationRecipient :
						list) {

					if (kaleoNotificationId !=
							kaleoNotificationRecipient.
								getKaleoNotificationId()) {

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

			sb.append(_SQL_SELECT_KALEONOTIFICATIONRECIPIENT_WHERE);

			sb.append(_FINDER_COLUMN_KALEONOTIFICATIONID_KALEONOTIFICATIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoNotificationRecipientModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoNotificationId);

				list = (List<KaleoNotificationRecipient>)QueryUtil.list(
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
	 * Returns the first kaleo notification recipient in the ordered set where kaleoNotificationId = &#63;.
	 *
	 * @param kaleoNotificationId the kaleo notification ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching kaleo notification recipient could not be found
	 */
	@Override
	public KaleoNotificationRecipient findByKaleoNotificationId_First(
			long kaleoNotificationId,
			OrderByComparator<KaleoNotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException {

		KaleoNotificationRecipient kaleoNotificationRecipient =
			fetchByKaleoNotificationId_First(
				kaleoNotificationId, orderByComparator);

		if (kaleoNotificationRecipient != null) {
			return kaleoNotificationRecipient;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoNotificationId=");
		sb.append(kaleoNotificationId);

		sb.append("}");

		throw new NoSuchNotificationRecipientException(sb.toString());
	}

	/**
	 * Returns the first kaleo notification recipient in the ordered set where kaleoNotificationId = &#63;.
	 *
	 * @param kaleoNotificationId the kaleo notification ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo notification recipient, or <code>null</code> if a matching kaleo notification recipient could not be found
	 */
	@Override
	public KaleoNotificationRecipient fetchByKaleoNotificationId_First(
		long kaleoNotificationId,
		OrderByComparator<KaleoNotificationRecipient> orderByComparator) {

		List<KaleoNotificationRecipient> list = findByKaleoNotificationId(
			kaleoNotificationId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo notification recipient in the ordered set where kaleoNotificationId = &#63;.
	 *
	 * @param kaleoNotificationId the kaleo notification ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching kaleo notification recipient could not be found
	 */
	@Override
	public KaleoNotificationRecipient findByKaleoNotificationId_Last(
			long kaleoNotificationId,
			OrderByComparator<KaleoNotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException {

		KaleoNotificationRecipient kaleoNotificationRecipient =
			fetchByKaleoNotificationId_Last(
				kaleoNotificationId, orderByComparator);

		if (kaleoNotificationRecipient != null) {
			return kaleoNotificationRecipient;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoNotificationId=");
		sb.append(kaleoNotificationId);

		sb.append("}");

		throw new NoSuchNotificationRecipientException(sb.toString());
	}

	/**
	 * Returns the last kaleo notification recipient in the ordered set where kaleoNotificationId = &#63;.
	 *
	 * @param kaleoNotificationId the kaleo notification ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo notification recipient, or <code>null</code> if a matching kaleo notification recipient could not be found
	 */
	@Override
	public KaleoNotificationRecipient fetchByKaleoNotificationId_Last(
		long kaleoNotificationId,
		OrderByComparator<KaleoNotificationRecipient> orderByComparator) {

		int count = countByKaleoNotificationId(kaleoNotificationId);

		if (count == 0) {
			return null;
		}

		List<KaleoNotificationRecipient> list = findByKaleoNotificationId(
			kaleoNotificationId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo notification recipients before and after the current kaleo notification recipient in the ordered set where kaleoNotificationId = &#63;.
	 *
	 * @param kaleoNotificationRecipientId the primary key of the current kaleo notification recipient
	 * @param kaleoNotificationId the kaleo notification ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo notification recipient
	 * @throws NoSuchNotificationRecipientException if a kaleo notification recipient with the primary key could not be found
	 */
	@Override
	public KaleoNotificationRecipient[] findByKaleoNotificationId_PrevAndNext(
			long kaleoNotificationRecipientId, long kaleoNotificationId,
			OrderByComparator<KaleoNotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException {

		KaleoNotificationRecipient kaleoNotificationRecipient =
			findByPrimaryKey(kaleoNotificationRecipientId);

		Session session = null;

		try {
			session = openSession();

			KaleoNotificationRecipient[] array =
				new KaleoNotificationRecipientImpl[3];

			array[0] = getByKaleoNotificationId_PrevAndNext(
				session, kaleoNotificationRecipient, kaleoNotificationId,
				orderByComparator, true);

			array[1] = kaleoNotificationRecipient;

			array[2] = getByKaleoNotificationId_PrevAndNext(
				session, kaleoNotificationRecipient, kaleoNotificationId,
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

	protected KaleoNotificationRecipient getByKaleoNotificationId_PrevAndNext(
		Session session, KaleoNotificationRecipient kaleoNotificationRecipient,
		long kaleoNotificationId,
		OrderByComparator<KaleoNotificationRecipient> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEONOTIFICATIONRECIPIENT_WHERE);

		sb.append(_FINDER_COLUMN_KALEONOTIFICATIONID_KALEONOTIFICATIONID_2);

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
			sb.append(KaleoNotificationRecipientModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoNotificationId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoNotificationRecipient)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoNotificationRecipient> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo notification recipients where kaleoNotificationId = &#63; from the database.
	 *
	 * @param kaleoNotificationId the kaleo notification ID
	 */
	@Override
	public void removeByKaleoNotificationId(long kaleoNotificationId) {
		for (KaleoNotificationRecipient kaleoNotificationRecipient :
				findByKaleoNotificationId(
					kaleoNotificationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(kaleoNotificationRecipient);
		}
	}

	/**
	 * Returns the number of kaleo notification recipients where kaleoNotificationId = &#63;.
	 *
	 * @param kaleoNotificationId the kaleo notification ID
	 * @return the number of matching kaleo notification recipients
	 */
	@Override
	public int countByKaleoNotificationId(long kaleoNotificationId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			KaleoNotificationRecipient.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByKaleoNotificationId;

			finderArgs = new Object[] {kaleoNotificationId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEONOTIFICATIONRECIPIENT_WHERE);

			sb.append(_FINDER_COLUMN_KALEONOTIFICATIONID_KALEONOTIFICATIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoNotificationId);

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

	private static final String
		_FINDER_COLUMN_KALEONOTIFICATIONID_KALEONOTIFICATIONID_2 =
			"kaleoNotificationRecipient.kaleoNotificationId = ?";

	public KaleoNotificationRecipientPersistenceImpl() {
		setModelClass(KaleoNotificationRecipient.class);

		setModelImplClass(KaleoNotificationRecipientImpl.class);
		setModelPKClass(long.class);

		setTable(KaleoNotificationRecipientTable.INSTANCE);
	}

	/**
	 * Caches the kaleo notification recipient in the entity cache if it is enabled.
	 *
	 * @param kaleoNotificationRecipient the kaleo notification recipient
	 */
	@Override
	public void cacheResult(
		KaleoNotificationRecipient kaleoNotificationRecipient) {

		if (kaleoNotificationRecipient.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			KaleoNotificationRecipientImpl.class,
			kaleoNotificationRecipient.getPrimaryKey(),
			kaleoNotificationRecipient);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the kaleo notification recipients in the entity cache if it is enabled.
	 *
	 * @param kaleoNotificationRecipients the kaleo notification recipients
	 */
	@Override
	public void cacheResult(
		List<KaleoNotificationRecipient> kaleoNotificationRecipients) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (kaleoNotificationRecipients.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (KaleoNotificationRecipient kaleoNotificationRecipient :
				kaleoNotificationRecipients) {

			if (kaleoNotificationRecipient.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					KaleoNotificationRecipientImpl.class,
					kaleoNotificationRecipient.getPrimaryKey()) == null) {

				cacheResult(kaleoNotificationRecipient);
			}
		}
	}

	/**
	 * Clears the cache for all kaleo notification recipients.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KaleoNotificationRecipientImpl.class);

		finderCache.clearCache(KaleoNotificationRecipientImpl.class);
	}

	/**
	 * Clears the cache for the kaleo notification recipient.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		KaleoNotificationRecipient kaleoNotificationRecipient) {

		entityCache.removeResult(
			KaleoNotificationRecipientImpl.class, kaleoNotificationRecipient);
	}

	@Override
	public void clearCache(
		List<KaleoNotificationRecipient> kaleoNotificationRecipients) {

		for (KaleoNotificationRecipient kaleoNotificationRecipient :
				kaleoNotificationRecipients) {

			entityCache.removeResult(
				KaleoNotificationRecipientImpl.class,
				kaleoNotificationRecipient);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(KaleoNotificationRecipientImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				KaleoNotificationRecipientImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new kaleo notification recipient with the primary key. Does not add the kaleo notification recipient to the database.
	 *
	 * @param kaleoNotificationRecipientId the primary key for the new kaleo notification recipient
	 * @return the new kaleo notification recipient
	 */
	@Override
	public KaleoNotificationRecipient create(
		long kaleoNotificationRecipientId) {

		KaleoNotificationRecipient kaleoNotificationRecipient =
			new KaleoNotificationRecipientImpl();

		kaleoNotificationRecipient.setNew(true);
		kaleoNotificationRecipient.setPrimaryKey(kaleoNotificationRecipientId);

		kaleoNotificationRecipient.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return kaleoNotificationRecipient;
	}

	/**
	 * Removes the kaleo notification recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoNotificationRecipientId the primary key of the kaleo notification recipient
	 * @return the kaleo notification recipient that was removed
	 * @throws NoSuchNotificationRecipientException if a kaleo notification recipient with the primary key could not be found
	 */
	@Override
	public KaleoNotificationRecipient remove(long kaleoNotificationRecipientId)
		throws NoSuchNotificationRecipientException {

		return remove((Serializable)kaleoNotificationRecipientId);
	}

	/**
	 * Removes the kaleo notification recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kaleo notification recipient
	 * @return the kaleo notification recipient that was removed
	 * @throws NoSuchNotificationRecipientException if a kaleo notification recipient with the primary key could not be found
	 */
	@Override
	public KaleoNotificationRecipient remove(Serializable primaryKey)
		throws NoSuchNotificationRecipientException {

		Session session = null;

		try {
			session = openSession();

			KaleoNotificationRecipient kaleoNotificationRecipient =
				(KaleoNotificationRecipient)session.get(
					KaleoNotificationRecipientImpl.class, primaryKey);

			if (kaleoNotificationRecipient == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationRecipientException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(kaleoNotificationRecipient);
		}
		catch (NoSuchNotificationRecipientException noSuchEntityException) {
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
	protected KaleoNotificationRecipient removeImpl(
		KaleoNotificationRecipient kaleoNotificationRecipient) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoNotificationRecipient)) {
				kaleoNotificationRecipient =
					(KaleoNotificationRecipient)session.get(
						KaleoNotificationRecipientImpl.class,
						kaleoNotificationRecipient.getPrimaryKeyObj());
			}

			if ((kaleoNotificationRecipient != null) &&
				ctPersistenceHelper.isRemove(kaleoNotificationRecipient)) {

				session.delete(kaleoNotificationRecipient);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (kaleoNotificationRecipient != null) {
			clearCache(kaleoNotificationRecipient);
		}

		return kaleoNotificationRecipient;
	}

	@Override
	public KaleoNotificationRecipient updateImpl(
		KaleoNotificationRecipient kaleoNotificationRecipient) {

		boolean isNew = kaleoNotificationRecipient.isNew();

		if (!(kaleoNotificationRecipient instanceof
				KaleoNotificationRecipientModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(kaleoNotificationRecipient.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					kaleoNotificationRecipient);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in kaleoNotificationRecipient proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom KaleoNotificationRecipient implementation " +
					kaleoNotificationRecipient.getClass());
		}

		KaleoNotificationRecipientModelImpl
			kaleoNotificationRecipientModelImpl =
				(KaleoNotificationRecipientModelImpl)kaleoNotificationRecipient;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (kaleoNotificationRecipient.getCreateDate() == null)) {
			if (serviceContext == null) {
				kaleoNotificationRecipient.setCreateDate(date);
			}
			else {
				kaleoNotificationRecipient.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!kaleoNotificationRecipientModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kaleoNotificationRecipient.setModifiedDate(date);
			}
			else {
				kaleoNotificationRecipient.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(kaleoNotificationRecipient)) {
				if (!isNew) {
					session.evict(
						KaleoNotificationRecipientImpl.class,
						kaleoNotificationRecipient.getPrimaryKeyObj());
				}

				session.save(kaleoNotificationRecipient);
			}
			else {
				kaleoNotificationRecipient =
					(KaleoNotificationRecipient)session.merge(
						kaleoNotificationRecipient);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (kaleoNotificationRecipient.getCtCollectionId() != 0) {
			if (isNew) {
				kaleoNotificationRecipient.setNew(false);
			}

			kaleoNotificationRecipient.resetOriginalValues();

			return kaleoNotificationRecipient;
		}

		entityCache.putResult(
			KaleoNotificationRecipientImpl.class,
			kaleoNotificationRecipientModelImpl, false, true);

		if (isNew) {
			kaleoNotificationRecipient.setNew(false);
		}

		kaleoNotificationRecipient.resetOriginalValues();

		return kaleoNotificationRecipient;
	}

	/**
	 * Returns the kaleo notification recipient with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo notification recipient
	 * @return the kaleo notification recipient
	 * @throws NoSuchNotificationRecipientException if a kaleo notification recipient with the primary key could not be found
	 */
	@Override
	public KaleoNotificationRecipient findByPrimaryKey(Serializable primaryKey)
		throws NoSuchNotificationRecipientException {

		KaleoNotificationRecipient kaleoNotificationRecipient =
			fetchByPrimaryKey(primaryKey);

		if (kaleoNotificationRecipient == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationRecipientException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return kaleoNotificationRecipient;
	}

	/**
	 * Returns the kaleo notification recipient with the primary key or throws a <code>NoSuchNotificationRecipientException</code> if it could not be found.
	 *
	 * @param kaleoNotificationRecipientId the primary key of the kaleo notification recipient
	 * @return the kaleo notification recipient
	 * @throws NoSuchNotificationRecipientException if a kaleo notification recipient with the primary key could not be found
	 */
	@Override
	public KaleoNotificationRecipient findByPrimaryKey(
			long kaleoNotificationRecipientId)
		throws NoSuchNotificationRecipientException {

		return findByPrimaryKey((Serializable)kaleoNotificationRecipientId);
	}

	/**
	 * Returns the kaleo notification recipient with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo notification recipient
	 * @return the kaleo notification recipient, or <code>null</code> if a kaleo notification recipient with the primary key could not be found
	 */
	@Override
	public KaleoNotificationRecipient fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				KaleoNotificationRecipient.class, primaryKey)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		KaleoNotificationRecipient kaleoNotificationRecipient = null;

		Session session = null;

		try {
			session = openSession();

			kaleoNotificationRecipient =
				(KaleoNotificationRecipient)session.get(
					KaleoNotificationRecipientImpl.class, primaryKey);

			if (kaleoNotificationRecipient != null) {
				cacheResult(kaleoNotificationRecipient);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return kaleoNotificationRecipient;
	}

	/**
	 * Returns the kaleo notification recipient with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoNotificationRecipientId the primary key of the kaleo notification recipient
	 * @return the kaleo notification recipient, or <code>null</code> if a kaleo notification recipient with the primary key could not be found
	 */
	@Override
	public KaleoNotificationRecipient fetchByPrimaryKey(
		long kaleoNotificationRecipientId) {

		return fetchByPrimaryKey((Serializable)kaleoNotificationRecipientId);
	}

	@Override
	public Map<Serializable, KaleoNotificationRecipient> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				KaleoNotificationRecipient.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, KaleoNotificationRecipient> map =
			new HashMap<Serializable, KaleoNotificationRecipient>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			KaleoNotificationRecipient kaleoNotificationRecipient =
				fetchByPrimaryKey(primaryKey);

			if (kaleoNotificationRecipient != null) {
				map.put(primaryKey, kaleoNotificationRecipient);
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

			for (KaleoNotificationRecipient kaleoNotificationRecipient :
					(List<KaleoNotificationRecipient>)query.list()) {

				map.put(
					kaleoNotificationRecipient.getPrimaryKeyObj(),
					kaleoNotificationRecipient);

				cacheResult(kaleoNotificationRecipient);
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
	 * Returns all the kaleo notification recipients.
	 *
	 * @return the kaleo notification recipients
	 */
	@Override
	public List<KaleoNotificationRecipient> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo notification recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo notification recipients
	 * @param end the upper bound of the range of kaleo notification recipients (not inclusive)
	 * @return the range of kaleo notification recipients
	 */
	@Override
	public List<KaleoNotificationRecipient> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo notification recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo notification recipients
	 * @param end the upper bound of the range of kaleo notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo notification recipients
	 */
	@Override
	public List<KaleoNotificationRecipient> findAll(
		int start, int end,
		OrderByComparator<KaleoNotificationRecipient> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo notification recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo notification recipients
	 * @param end the upper bound of the range of kaleo notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo notification recipients
	 */
	@Override
	public List<KaleoNotificationRecipient> findAll(
		int start, int end,
		OrderByComparator<KaleoNotificationRecipient> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			KaleoNotificationRecipient.class);

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

		List<KaleoNotificationRecipient> list = null;

		if (useFinderCache && productionMode) {
			list = (List<KaleoNotificationRecipient>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_KALEONOTIFICATIONRECIPIENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_KALEONOTIFICATIONRECIPIENT;

				sql = sql.concat(
					KaleoNotificationRecipientModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<KaleoNotificationRecipient>)QueryUtil.list(
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
	 * Removes all the kaleo notification recipients from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoNotificationRecipient kaleoNotificationRecipient :
				findAll()) {

			remove(kaleoNotificationRecipient);
		}
	}

	/**
	 * Returns the number of kaleo notification recipients.
	 *
	 * @return the number of kaleo notification recipients
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			KaleoNotificationRecipient.class);

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
					_SQL_COUNT_KALEONOTIFICATIONRECIPIENT);

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
		return "kaleoNotificationRecipientId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_KALEONOTIFICATIONRECIPIENT;
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
		return KaleoNotificationRecipientModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "KaleoNotificationRecipient";
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
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("kaleoDefinitionId");
		ctStrictColumnNames.add("kaleoDefinitionVersionId");
		ctStrictColumnNames.add("kaleoNotificationId");
		ctStrictColumnNames.add("recipientClassName");
		ctStrictColumnNames.add("recipientClassPK");
		ctStrictColumnNames.add("recipientRoleType");
		ctStrictColumnNames.add("recipientScript");
		ctStrictColumnNames.add("recipientScriptLanguage");
		ctStrictColumnNames.add("recipientScriptContexts");
		ctStrictColumnNames.add("address");
		ctStrictColumnNames.add("notificationReceptionType");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("kaleoNotificationRecipientId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the kaleo notification recipient persistence.
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

		_finderPathWithPaginationFindByKaleoDefinitionVersionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByKaleoDefinitionVersionId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"kaleoDefinitionVersionId"}, true);

		_finderPathWithoutPaginationFindByKaleoDefinitionVersionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByKaleoDefinitionVersionId",
				new String[] {Long.class.getName()},
				new String[] {"kaleoDefinitionVersionId"}, true);

		_finderPathCountByKaleoDefinitionVersionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKaleoDefinitionVersionId",
			new String[] {Long.class.getName()},
			new String[] {"kaleoDefinitionVersionId"}, false);

		_finderPathWithPaginationFindByKaleoNotificationId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKaleoNotificationId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"kaleoNotificationId"}, true);

		_finderPathWithoutPaginationFindByKaleoNotificationId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByKaleoNotificationId", new String[] {Long.class.getName()},
			new String[] {"kaleoNotificationId"}, true);

		_finderPathCountByKaleoNotificationId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKaleoNotificationId", new String[] {Long.class.getName()},
			new String[] {"kaleoNotificationId"}, false);

		KaleoNotificationRecipientUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		KaleoNotificationRecipientUtil.setPersistence(null);

		entityCache.removeCache(KaleoNotificationRecipientImpl.class.getName());
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_KALEONOTIFICATIONRECIPIENT =
		"SELECT kaleoNotificationRecipient FROM KaleoNotificationRecipient kaleoNotificationRecipient";

	private static final String _SQL_SELECT_KALEONOTIFICATIONRECIPIENT_WHERE =
		"SELECT kaleoNotificationRecipient FROM KaleoNotificationRecipient kaleoNotificationRecipient WHERE ";

	private static final String _SQL_COUNT_KALEONOTIFICATIONRECIPIENT =
		"SELECT COUNT(kaleoNotificationRecipient) FROM KaleoNotificationRecipient kaleoNotificationRecipient";

	private static final String _SQL_COUNT_KALEONOTIFICATIONRECIPIENT_WHERE =
		"SELECT COUNT(kaleoNotificationRecipient) FROM KaleoNotificationRecipient kaleoNotificationRecipient WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"kaleoNotificationRecipient.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No KaleoNotificationRecipient exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No KaleoNotificationRecipient exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoNotificationRecipientPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}