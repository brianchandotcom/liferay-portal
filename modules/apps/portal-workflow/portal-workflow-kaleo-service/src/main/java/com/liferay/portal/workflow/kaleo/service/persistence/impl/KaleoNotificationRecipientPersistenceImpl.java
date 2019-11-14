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

package com.liferay.portal.workflow.kaleo.service.persistence.impl;

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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.workflow.kaleo.exception.NoSuchNotificationRecipientException;
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationRecipientImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationRecipientModelImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoNotificationRecipientPersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.impl.constants.KaleoPersistenceConstants;

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

	/**
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

		List<KaleoNotificationRecipient> list = null;

		if (useFinderCache) {
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
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_KALEONOTIFICATIONRECIPIENT_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(KaleoNotificationRecipientModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<KaleoNotificationRecipient>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
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

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchNotificationRecipientException(msg.toString());
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

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchNotificationRecipientException(msg.toString());
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
		catch (Exception e) {
			throw processException(e);
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

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_KALEONOTIFICATIONRECIPIENT_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(KaleoNotificationRecipientModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoNotificationRecipient)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<KaleoNotificationRecipient> list = q.list();

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
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_KALEONOTIFICATIONRECIPIENT_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
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

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByKaleoDefinitionVersionId;
				finderArgs = new Object[] {kaleoDefinitionVersionId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByKaleoDefinitionVersionId;
			finderArgs = new Object[] {
				kaleoDefinitionVersionId, start, end, orderByComparator
			};
		}

		List<KaleoNotificationRecipient> list = null;

		if (useFinderCache) {
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
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_KALEONOTIFICATIONRECIPIENT_WHERE);

			query.append(
				_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(KaleoNotificationRecipientModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoDefinitionVersionId);

				list = (List<KaleoNotificationRecipient>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
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

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("kaleoDefinitionVersionId=");
		msg.append(kaleoDefinitionVersionId);

		msg.append("}");

		throw new NoSuchNotificationRecipientException(msg.toString());
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

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("kaleoDefinitionVersionId=");
		msg.append(kaleoDefinitionVersionId);

		msg.append("}");

		throw new NoSuchNotificationRecipientException(msg.toString());
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
		catch (Exception e) {
			throw processException(e);
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

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_KALEONOTIFICATIONRECIPIENT_WHERE);

		query.append(
			_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(KaleoNotificationRecipientModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(kaleoDefinitionVersionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoNotificationRecipient)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<KaleoNotificationRecipient> list = q.list();

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
		FinderPath finderPath = _finderPathCountByKaleoDefinitionVersionId;

		Object[] finderArgs = new Object[] {kaleoDefinitionVersionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_KALEONOTIFICATIONRECIPIENT_WHERE);

			query.append(
				_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoDefinitionVersionId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
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

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByKaleoNotificationId;
				finderArgs = new Object[] {kaleoNotificationId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByKaleoNotificationId;
			finderArgs = new Object[] {
				kaleoNotificationId, start, end, orderByComparator
			};
		}

		List<KaleoNotificationRecipient> list = null;

		if (useFinderCache) {
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
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_KALEONOTIFICATIONRECIPIENT_WHERE);

			query.append(
				_FINDER_COLUMN_KALEONOTIFICATIONID_KALEONOTIFICATIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(KaleoNotificationRecipientModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoNotificationId);

				list = (List<KaleoNotificationRecipient>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
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

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("kaleoNotificationId=");
		msg.append(kaleoNotificationId);

		msg.append("}");

		throw new NoSuchNotificationRecipientException(msg.toString());
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

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("kaleoNotificationId=");
		msg.append(kaleoNotificationId);

		msg.append("}");

		throw new NoSuchNotificationRecipientException(msg.toString());
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
		catch (Exception e) {
			throw processException(e);
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

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_KALEONOTIFICATIONRECIPIENT_WHERE);

		query.append(_FINDER_COLUMN_KALEONOTIFICATIONID_KALEONOTIFICATIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(KaleoNotificationRecipientModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(kaleoNotificationId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoNotificationRecipient)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<KaleoNotificationRecipient> list = q.list();

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
		FinderPath finderPath = _finderPathCountByKaleoNotificationId;

		Object[] finderArgs = new Object[] {kaleoNotificationId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_KALEONOTIFICATIONRECIPIENT_WHERE);

			query.append(
				_FINDER_COLUMN_KALEONOTIFICATIONID_KALEONOTIFICATIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoNotificationId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
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
	}

	/**
	 * Caches the kaleo notification recipient in the entity cache if it is enabled.
	 *
	 * @param kaleoNotificationRecipient the kaleo notification recipient
	 */
	@Override
	public void cacheResult(
		KaleoNotificationRecipient kaleoNotificationRecipient) {

		entityCache.putResult(
			entityCacheEnabled, KaleoNotificationRecipientImpl.class,
			kaleoNotificationRecipient.getPrimaryKey(),
			kaleoNotificationRecipient);

		kaleoNotificationRecipient.resetOriginalValues();
	}

	/**
	 * Caches the kaleo notification recipients in the entity cache if it is enabled.
	 *
	 * @param kaleoNotificationRecipients the kaleo notification recipients
	 */
	@Override
	public void cacheResult(
		List<KaleoNotificationRecipient> kaleoNotificationRecipients) {

		for (KaleoNotificationRecipient kaleoNotificationRecipient :
				kaleoNotificationRecipients) {

			if (entityCache.getResult(
					entityCacheEnabled, KaleoNotificationRecipientImpl.class,
					kaleoNotificationRecipient.getPrimaryKey()) == null) {

				cacheResult(kaleoNotificationRecipient);
			}
			else {
				kaleoNotificationRecipient.resetOriginalValues();
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

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
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
			entityCacheEnabled, KaleoNotificationRecipientImpl.class,
			kaleoNotificationRecipient.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<KaleoNotificationRecipient> kaleoNotificationRecipients) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (KaleoNotificationRecipient kaleoNotificationRecipient :
				kaleoNotificationRecipients) {

			entityCache.removeResult(
				entityCacheEnabled, KaleoNotificationRecipientImpl.class,
				kaleoNotificationRecipient.getPrimaryKey());
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, KaleoNotificationRecipientImpl.class,
				primaryKey);
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
		catch (NoSuchNotificationRecipientException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
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

			if (kaleoNotificationRecipient != null) {
				session.delete(kaleoNotificationRecipient);
			}
		}
		catch (Exception e) {
			throw processException(e);
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

		Date now = new Date();

		if (isNew && (kaleoNotificationRecipient.getCreateDate() == null)) {
			if (serviceContext == null) {
				kaleoNotificationRecipient.setCreateDate(now);
			}
			else {
				kaleoNotificationRecipient.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!kaleoNotificationRecipientModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kaleoNotificationRecipient.setModifiedDate(now);
			}
			else {
				kaleoNotificationRecipient.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (kaleoNotificationRecipient.isNew()) {
				session.save(kaleoNotificationRecipient);

				kaleoNotificationRecipient.setNew(false);
			}
			else {
				kaleoNotificationRecipient =
					(KaleoNotificationRecipient)session.merge(
						kaleoNotificationRecipient);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				kaleoNotificationRecipientModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {
				kaleoNotificationRecipientModelImpl.
					getKaleoDefinitionVersionId()
			};

			finderCache.removeResult(
				_finderPathCountByKaleoDefinitionVersionId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByKaleoDefinitionVersionId,
				args);

			args = new Object[] {
				kaleoNotificationRecipientModelImpl.getKaleoNotificationId()
			};

			finderCache.removeResult(
				_finderPathCountByKaleoNotificationId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByKaleoNotificationId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((kaleoNotificationRecipientModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					kaleoNotificationRecipientModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {
					kaleoNotificationRecipientModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((kaleoNotificationRecipientModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByKaleoDefinitionVersionId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					kaleoNotificationRecipientModelImpl.
						getOriginalKaleoDefinitionVersionId()
				};

				finderCache.removeResult(
					_finderPathCountByKaleoDefinitionVersionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByKaleoDefinitionVersionId,
					args);

				args = new Object[] {
					kaleoNotificationRecipientModelImpl.
						getKaleoDefinitionVersionId()
				};

				finderCache.removeResult(
					_finderPathCountByKaleoDefinitionVersionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByKaleoDefinitionVersionId,
					args);
			}

			if ((kaleoNotificationRecipientModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByKaleoNotificationId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					kaleoNotificationRecipientModelImpl.
						getOriginalKaleoNotificationId()
				};

				finderCache.removeResult(
					_finderPathCountByKaleoNotificationId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByKaleoNotificationId,
					args);

				args = new Object[] {
					kaleoNotificationRecipientModelImpl.getKaleoNotificationId()
				};

				finderCache.removeResult(
					_finderPathCountByKaleoNotificationId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByKaleoNotificationId,
					args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, KaleoNotificationRecipientImpl.class,
			kaleoNotificationRecipient.getPrimaryKey(),
			kaleoNotificationRecipient, false);

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
	 * @param kaleoNotificationRecipientId the primary key of the kaleo notification recipient
	 * @return the kaleo notification recipient, or <code>null</code> if a kaleo notification recipient with the primary key could not be found
	 */
	@Override
	public KaleoNotificationRecipient fetchByPrimaryKey(
		long kaleoNotificationRecipientId) {

		return fetchByPrimaryKey((Serializable)kaleoNotificationRecipientId);
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

		List<KaleoNotificationRecipient> list = null;

		if (useFinderCache) {
			list = (List<KaleoNotificationRecipient>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_KALEONOTIFICATIONRECIPIENT);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_KALEONOTIFICATIONRECIPIENT;

				sql = sql.concat(
					KaleoNotificationRecipientModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<KaleoNotificationRecipient>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
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
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
					_SQL_COUNT_KALEONOTIFICATIONRECIPIENT);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
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
	protected Map<String, Integer> getTableColumnsMap() {
		return KaleoNotificationRecipientModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the kaleo notification recipient persistence.
	 */
	@Activate
	public void activate() {
		KaleoNotificationRecipientModelImpl.setEntityCacheEnabled(
			entityCacheEnabled);
		KaleoNotificationRecipientModelImpl.setFinderCacheEnabled(
			finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			KaleoNotificationRecipientImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			KaleoNotificationRecipientImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			KaleoNotificationRecipientImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			KaleoNotificationRecipientImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()},
			KaleoNotificationRecipientModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByKaleoDefinitionVersionId =
			new FinderPath(
				entityCacheEnabled, finderCacheEnabled,
				KaleoNotificationRecipientImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByKaleoDefinitionVersionId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByKaleoDefinitionVersionId =
			new FinderPath(
				entityCacheEnabled, finderCacheEnabled,
				KaleoNotificationRecipientImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByKaleoDefinitionVersionId",
				new String[] {Long.class.getName()},
				KaleoNotificationRecipientModelImpl.
					KALEODEFINITIONVERSIONID_COLUMN_BITMASK);

		_finderPathCountByKaleoDefinitionVersionId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKaleoDefinitionVersionId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByKaleoNotificationId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			KaleoNotificationRecipientImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKaleoNotificationId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByKaleoNotificationId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			KaleoNotificationRecipientImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByKaleoNotificationId", new String[] {Long.class.getName()},
			KaleoNotificationRecipientModelImpl.
				KALEONOTIFICATIONID_COLUMN_BITMASK);

		_finderPathCountByKaleoNotificationId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKaleoNotificationId", new String[] {Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(KaleoNotificationRecipientImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient"),
			true);
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

	private boolean _columnBitmaskEnabled;

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

	static {
		try {
			Class.forName(KaleoPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}