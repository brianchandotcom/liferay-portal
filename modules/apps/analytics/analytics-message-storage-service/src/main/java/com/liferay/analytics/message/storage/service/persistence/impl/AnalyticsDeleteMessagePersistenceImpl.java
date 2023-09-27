/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.storage.service.persistence.impl;

import com.liferay.analytics.message.storage.exception.NoSuchDeleteMessageException;
import com.liferay.analytics.message.storage.model.AnalyticsDeleteMessage;
import com.liferay.analytics.message.storage.model.AnalyticsDeleteMessageTable;
import com.liferay.analytics.message.storage.model.impl.AnalyticsDeleteMessageImpl;
import com.liferay.analytics.message.storage.model.impl.AnalyticsDeleteMessageModelImpl;
import com.liferay.analytics.message.storage.service.persistence.AnalyticsDeleteMessagePersistence;
import com.liferay.analytics.message.storage.service.persistence.AnalyticsDeleteMessageUtil;
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
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the analytics delete message service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AnalyticsDeleteMessagePersistence.class)
public class AnalyticsDeleteMessagePersistenceImpl
	extends BasePersistenceImpl<AnalyticsDeleteMessage>
	implements AnalyticsDeleteMessagePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AnalyticsDeleteMessageUtil</code> to access the analytics delete message persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AnalyticsDeleteMessageImpl.class.getName();

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
	 * Returns all the analytics delete messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching analytics delete messages
	 */
	@Override
	public List<AnalyticsDeleteMessage> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the analytics delete messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @return the range of matching analytics delete messages
	 */
	@Override
	public List<AnalyticsDeleteMessage> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the analytics delete messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics delete messages
	 */
	@Override
	public List<AnalyticsDeleteMessage> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the analytics delete messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics delete messages
	 */
	@Override
	public List<AnalyticsDeleteMessage> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AnalyticsDeleteMessage.class);

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

		List<AnalyticsDeleteMessage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AnalyticsDeleteMessage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AnalyticsDeleteMessage analyticsDeleteMessage : list) {
					if (companyId != analyticsDeleteMessage.getCompanyId()) {
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

			sb.append(_SQL_SELECT_ANALYTICSDELETEMESSAGE_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AnalyticsDeleteMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<AnalyticsDeleteMessage>)QueryUtil.list(
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
	 * Returns the first analytics delete message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics delete message
	 * @throws NoSuchDeleteMessageException if a matching analytics delete message could not be found
	 */
	@Override
	public AnalyticsDeleteMessage findByCompanyId_First(
			long companyId,
			OrderByComparator<AnalyticsDeleteMessage> orderByComparator)
		throws NoSuchDeleteMessageException {

		AnalyticsDeleteMessage analyticsDeleteMessage = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (analyticsDeleteMessage != null) {
			return analyticsDeleteMessage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchDeleteMessageException(sb.toString());
	}

	/**
	 * Returns the first analytics delete message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics delete message, or <code>null</code> if a matching analytics delete message could not be found
	 */
	@Override
	public AnalyticsDeleteMessage fetchByCompanyId_First(
		long companyId,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator) {

		List<AnalyticsDeleteMessage> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last analytics delete message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics delete message
	 * @throws NoSuchDeleteMessageException if a matching analytics delete message could not be found
	 */
	@Override
	public AnalyticsDeleteMessage findByCompanyId_Last(
			long companyId,
			OrderByComparator<AnalyticsDeleteMessage> orderByComparator)
		throws NoSuchDeleteMessageException {

		AnalyticsDeleteMessage analyticsDeleteMessage = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (analyticsDeleteMessage != null) {
			return analyticsDeleteMessage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchDeleteMessageException(sb.toString());
	}

	/**
	 * Returns the last analytics delete message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics delete message, or <code>null</code> if a matching analytics delete message could not be found
	 */
	@Override
	public AnalyticsDeleteMessage fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<AnalyticsDeleteMessage> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the analytics delete messages before and after the current analytics delete message in the ordered set where companyId = &#63;.
	 *
	 * @param analyticsDeleteMessageId the primary key of the current analytics delete message
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics delete message
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	@Override
	public AnalyticsDeleteMessage[] findByCompanyId_PrevAndNext(
			long analyticsDeleteMessageId, long companyId,
			OrderByComparator<AnalyticsDeleteMessage> orderByComparator)
		throws NoSuchDeleteMessageException {

		AnalyticsDeleteMessage analyticsDeleteMessage = findByPrimaryKey(
			analyticsDeleteMessageId);

		Session session = null;

		try {
			session = openSession();

			AnalyticsDeleteMessage[] array = new AnalyticsDeleteMessageImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, analyticsDeleteMessage, companyId, orderByComparator,
				true);

			array[1] = analyticsDeleteMessage;

			array[2] = getByCompanyId_PrevAndNext(
				session, analyticsDeleteMessage, companyId, orderByComparator,
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

	protected AnalyticsDeleteMessage getByCompanyId_PrevAndNext(
		Session session, AnalyticsDeleteMessage analyticsDeleteMessage,
		long companyId,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator,
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

		sb.append(_SQL_SELECT_ANALYTICSDELETEMESSAGE_WHERE);

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
			sb.append(AnalyticsDeleteMessageModelImpl.ORDER_BY_JPQL);
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
						analyticsDeleteMessage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AnalyticsDeleteMessage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the analytics delete messages where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (AnalyticsDeleteMessage analyticsDeleteMessage :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(analyticsDeleteMessage);
		}
	}

	/**
	 * Returns the number of analytics delete messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching analytics delete messages
	 */
	@Override
	public int countByCompanyId(long companyId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AnalyticsDeleteMessage.class);

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

			sb.append(_SQL_COUNT_ANALYTICSDELETEMESSAGE_WHERE);

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
		"analyticsDeleteMessage.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_GtM;
	private FinderPath _finderPathWithPaginationCountByC_GtM;

	/**
	 * Returns all the analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @return the matching analytics delete messages
	 */
	@Override
	public List<AnalyticsDeleteMessage> findByC_GtM(
		long companyId, Date modifiedDate) {

		return findByC_GtM(
			companyId, modifiedDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @return the range of matching analytics delete messages
	 */
	@Override
	public List<AnalyticsDeleteMessage> findByC_GtM(
		long companyId, Date modifiedDate, int start, int end) {

		return findByC_GtM(companyId, modifiedDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics delete messages
	 */
	@Override
	public List<AnalyticsDeleteMessage> findByC_GtM(
		long companyId, Date modifiedDate, int start, int end,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator) {

		return findByC_GtM(
			companyId, modifiedDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics delete messages
	 */
	@Override
	public List<AnalyticsDeleteMessage> findByC_GtM(
		long companyId, Date modifiedDate, int start, int end,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AnalyticsDeleteMessage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_GtM;
		finderArgs = new Object[] {
			companyId, _getTime(modifiedDate), start, end, orderByComparator
		};

		List<AnalyticsDeleteMessage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AnalyticsDeleteMessage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AnalyticsDeleteMessage analyticsDeleteMessage : list) {
					if ((companyId != analyticsDeleteMessage.getCompanyId()) ||
						(modifiedDate.getTime() >=
							analyticsDeleteMessage.getModifiedDate(
							).getTime())) {

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

			sb.append(_SQL_SELECT_ANALYTICSDELETEMESSAGE_WHERE);

			sb.append(_FINDER_COLUMN_C_GTM_COMPANYID_2);

			boolean bindModifiedDate = false;

			if (modifiedDate == null) {
				sb.append(_FINDER_COLUMN_C_GTM_MODIFIEDDATE_1);
			}
			else {
				bindModifiedDate = true;

				sb.append(_FINDER_COLUMN_C_GTM_MODIFIEDDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AnalyticsDeleteMessageModelImpl.ORDER_BY_JPQL);
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

				list = (List<AnalyticsDeleteMessage>)QueryUtil.list(
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
	 * Returns the first analytics delete message in the ordered set where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics delete message
	 * @throws NoSuchDeleteMessageException if a matching analytics delete message could not be found
	 */
	@Override
	public AnalyticsDeleteMessage findByC_GtM_First(
			long companyId, Date modifiedDate,
			OrderByComparator<AnalyticsDeleteMessage> orderByComparator)
		throws NoSuchDeleteMessageException {

		AnalyticsDeleteMessage analyticsDeleteMessage = fetchByC_GtM_First(
			companyId, modifiedDate, orderByComparator);

		if (analyticsDeleteMessage != null) {
			return analyticsDeleteMessage;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", modifiedDate>");
		sb.append(modifiedDate);

		sb.append("}");

		throw new NoSuchDeleteMessageException(sb.toString());
	}

	/**
	 * Returns the first analytics delete message in the ordered set where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics delete message, or <code>null</code> if a matching analytics delete message could not be found
	 */
	@Override
	public AnalyticsDeleteMessage fetchByC_GtM_First(
		long companyId, Date modifiedDate,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator) {

		List<AnalyticsDeleteMessage> list = findByC_GtM(
			companyId, modifiedDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last analytics delete message in the ordered set where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics delete message
	 * @throws NoSuchDeleteMessageException if a matching analytics delete message could not be found
	 */
	@Override
	public AnalyticsDeleteMessage findByC_GtM_Last(
			long companyId, Date modifiedDate,
			OrderByComparator<AnalyticsDeleteMessage> orderByComparator)
		throws NoSuchDeleteMessageException {

		AnalyticsDeleteMessage analyticsDeleteMessage = fetchByC_GtM_Last(
			companyId, modifiedDate, orderByComparator);

		if (analyticsDeleteMessage != null) {
			return analyticsDeleteMessage;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", modifiedDate>");
		sb.append(modifiedDate);

		sb.append("}");

		throw new NoSuchDeleteMessageException(sb.toString());
	}

	/**
	 * Returns the last analytics delete message in the ordered set where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics delete message, or <code>null</code> if a matching analytics delete message could not be found
	 */
	@Override
	public AnalyticsDeleteMessage fetchByC_GtM_Last(
		long companyId, Date modifiedDate,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator) {

		int count = countByC_GtM(companyId, modifiedDate);

		if (count == 0) {
			return null;
		}

		List<AnalyticsDeleteMessage> list = findByC_GtM(
			companyId, modifiedDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the analytics delete messages before and after the current analytics delete message in the ordered set where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param analyticsDeleteMessageId the primary key of the current analytics delete message
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics delete message
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	@Override
	public AnalyticsDeleteMessage[] findByC_GtM_PrevAndNext(
			long analyticsDeleteMessageId, long companyId, Date modifiedDate,
			OrderByComparator<AnalyticsDeleteMessage> orderByComparator)
		throws NoSuchDeleteMessageException {

		AnalyticsDeleteMessage analyticsDeleteMessage = findByPrimaryKey(
			analyticsDeleteMessageId);

		Session session = null;

		try {
			session = openSession();

			AnalyticsDeleteMessage[] array = new AnalyticsDeleteMessageImpl[3];

			array[0] = getByC_GtM_PrevAndNext(
				session, analyticsDeleteMessage, companyId, modifiedDate,
				orderByComparator, true);

			array[1] = analyticsDeleteMessage;

			array[2] = getByC_GtM_PrevAndNext(
				session, analyticsDeleteMessage, companyId, modifiedDate,
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

	protected AnalyticsDeleteMessage getByC_GtM_PrevAndNext(
		Session session, AnalyticsDeleteMessage analyticsDeleteMessage,
		long companyId, Date modifiedDate,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator,
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

		sb.append(_SQL_SELECT_ANALYTICSDELETEMESSAGE_WHERE);

		sb.append(_FINDER_COLUMN_C_GTM_COMPANYID_2);

		boolean bindModifiedDate = false;

		if (modifiedDate == null) {
			sb.append(_FINDER_COLUMN_C_GTM_MODIFIEDDATE_1);
		}
		else {
			bindModifiedDate = true;

			sb.append(_FINDER_COLUMN_C_GTM_MODIFIEDDATE_2);
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
			sb.append(AnalyticsDeleteMessageModelImpl.ORDER_BY_JPQL);
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

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						analyticsDeleteMessage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AnalyticsDeleteMessage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 */
	@Override
	public void removeByC_GtM(long companyId, Date modifiedDate) {
		for (AnalyticsDeleteMessage analyticsDeleteMessage :
				findByC_GtM(
					companyId, modifiedDate, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(analyticsDeleteMessage);
		}
	}

	/**
	 * Returns the number of analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @return the number of matching analytics delete messages
	 */
	@Override
	public int countByC_GtM(long companyId, Date modifiedDate) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AnalyticsDeleteMessage.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByC_GtM;

			finderArgs = new Object[] {companyId, _getTime(modifiedDate)};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ANALYTICSDELETEMESSAGE_WHERE);

			sb.append(_FINDER_COLUMN_C_GTM_COMPANYID_2);

			boolean bindModifiedDate = false;

			if (modifiedDate == null) {
				sb.append(_FINDER_COLUMN_C_GTM_MODIFIEDDATE_1);
			}
			else {
				bindModifiedDate = true;

				sb.append(_FINDER_COLUMN_C_GTM_MODIFIEDDATE_2);
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

	private static final String _FINDER_COLUMN_C_GTM_COMPANYID_2 =
		"analyticsDeleteMessage.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_GTM_MODIFIEDDATE_1 =
		"analyticsDeleteMessage.modifiedDate IS NULL";

	private static final String _FINDER_COLUMN_C_GTM_MODIFIEDDATE_2 =
		"analyticsDeleteMessage.modifiedDate > ?";

	public AnalyticsDeleteMessagePersistenceImpl() {
		setModelClass(AnalyticsDeleteMessage.class);

		setModelImplClass(AnalyticsDeleteMessageImpl.class);
		setModelPKClass(long.class);

		setTable(AnalyticsDeleteMessageTable.INSTANCE);
	}

	/**
	 * Caches the analytics delete message in the entity cache if it is enabled.
	 *
	 * @param analyticsDeleteMessage the analytics delete message
	 */
	@Override
	public void cacheResult(AnalyticsDeleteMessage analyticsDeleteMessage) {
		if (analyticsDeleteMessage.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			AnalyticsDeleteMessageImpl.class,
			analyticsDeleteMessage.getPrimaryKey(), analyticsDeleteMessage);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the analytics delete messages in the entity cache if it is enabled.
	 *
	 * @param analyticsDeleteMessages the analytics delete messages
	 */
	@Override
	public void cacheResult(
		List<AnalyticsDeleteMessage> analyticsDeleteMessages) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (analyticsDeleteMessages.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AnalyticsDeleteMessage analyticsDeleteMessage :
				analyticsDeleteMessages) {

			if (analyticsDeleteMessage.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					AnalyticsDeleteMessageImpl.class,
					analyticsDeleteMessage.getPrimaryKey()) == null) {

				cacheResult(analyticsDeleteMessage);
			}
		}
	}

	/**
	 * Clears the cache for all analytics delete messages.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AnalyticsDeleteMessageImpl.class);

		finderCache.clearCache(AnalyticsDeleteMessageImpl.class);
	}

	/**
	 * Clears the cache for the analytics delete message.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AnalyticsDeleteMessage analyticsDeleteMessage) {
		entityCache.removeResult(
			AnalyticsDeleteMessageImpl.class, analyticsDeleteMessage);
	}

	@Override
	public void clearCache(
		List<AnalyticsDeleteMessage> analyticsDeleteMessages) {

		for (AnalyticsDeleteMessage analyticsDeleteMessage :
				analyticsDeleteMessages) {

			entityCache.removeResult(
				AnalyticsDeleteMessageImpl.class, analyticsDeleteMessage);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AnalyticsDeleteMessageImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AnalyticsDeleteMessageImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new analytics delete message with the primary key. Does not add the analytics delete message to the database.
	 *
	 * @param analyticsDeleteMessageId the primary key for the new analytics delete message
	 * @return the new analytics delete message
	 */
	@Override
	public AnalyticsDeleteMessage create(long analyticsDeleteMessageId) {
		AnalyticsDeleteMessage analyticsDeleteMessage =
			new AnalyticsDeleteMessageImpl();

		analyticsDeleteMessage.setNew(true);
		analyticsDeleteMessage.setPrimaryKey(analyticsDeleteMessageId);

		analyticsDeleteMessage.setCompanyId(CompanyThreadLocal.getCompanyId());

		return analyticsDeleteMessage;
	}

	/**
	 * Removes the analytics delete message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsDeleteMessageId the primary key of the analytics delete message
	 * @return the analytics delete message that was removed
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	@Override
	public AnalyticsDeleteMessage remove(long analyticsDeleteMessageId)
		throws NoSuchDeleteMessageException {

		return remove((Serializable)analyticsDeleteMessageId);
	}

	/**
	 * Removes the analytics delete message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the analytics delete message
	 * @return the analytics delete message that was removed
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	@Override
	public AnalyticsDeleteMessage remove(Serializable primaryKey)
		throws NoSuchDeleteMessageException {

		Session session = null;

		try {
			session = openSession();

			AnalyticsDeleteMessage analyticsDeleteMessage =
				(AnalyticsDeleteMessage)session.get(
					AnalyticsDeleteMessageImpl.class, primaryKey);

			if (analyticsDeleteMessage == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDeleteMessageException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(analyticsDeleteMessage);
		}
		catch (NoSuchDeleteMessageException noSuchEntityException) {
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
	protected AnalyticsDeleteMessage removeImpl(
		AnalyticsDeleteMessage analyticsDeleteMessage) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(analyticsDeleteMessage)) {
				analyticsDeleteMessage = (AnalyticsDeleteMessage)session.get(
					AnalyticsDeleteMessageImpl.class,
					analyticsDeleteMessage.getPrimaryKeyObj());
			}

			if ((analyticsDeleteMessage != null) &&
				ctPersistenceHelper.isRemove(analyticsDeleteMessage)) {

				session.delete(analyticsDeleteMessage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (analyticsDeleteMessage != null) {
			clearCache(analyticsDeleteMessage);
		}

		return analyticsDeleteMessage;
	}

	@Override
	public AnalyticsDeleteMessage updateImpl(
		AnalyticsDeleteMessage analyticsDeleteMessage) {

		boolean isNew = analyticsDeleteMessage.isNew();

		if (!(analyticsDeleteMessage instanceof
				AnalyticsDeleteMessageModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(analyticsDeleteMessage.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					analyticsDeleteMessage);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in analyticsDeleteMessage proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AnalyticsDeleteMessage implementation " +
					analyticsDeleteMessage.getClass());
		}

		AnalyticsDeleteMessageModelImpl analyticsDeleteMessageModelImpl =
			(AnalyticsDeleteMessageModelImpl)analyticsDeleteMessage;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (analyticsDeleteMessage.getCreateDate() == null)) {
			if (serviceContext == null) {
				analyticsDeleteMessage.setCreateDate(date);
			}
			else {
				analyticsDeleteMessage.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!analyticsDeleteMessageModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				analyticsDeleteMessage.setModifiedDate(date);
			}
			else {
				analyticsDeleteMessage.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(analyticsDeleteMessage)) {
				if (!isNew) {
					session.evict(
						AnalyticsDeleteMessageImpl.class,
						analyticsDeleteMessage.getPrimaryKeyObj());
				}

				session.save(analyticsDeleteMessage);
			}
			else {
				analyticsDeleteMessage = (AnalyticsDeleteMessage)session.merge(
					analyticsDeleteMessage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (analyticsDeleteMessage.getCtCollectionId() != 0) {
			if (isNew) {
				analyticsDeleteMessage.setNew(false);
			}

			analyticsDeleteMessage.resetOriginalValues();

			return analyticsDeleteMessage;
		}

		entityCache.putResult(
			AnalyticsDeleteMessageImpl.class, analyticsDeleteMessageModelImpl,
			false, true);

		if (isNew) {
			analyticsDeleteMessage.setNew(false);
		}

		analyticsDeleteMessage.resetOriginalValues();

		return analyticsDeleteMessage;
	}

	/**
	 * Returns the analytics delete message with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the analytics delete message
	 * @return the analytics delete message
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	@Override
	public AnalyticsDeleteMessage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDeleteMessageException {

		AnalyticsDeleteMessage analyticsDeleteMessage = fetchByPrimaryKey(
			primaryKey);

		if (analyticsDeleteMessage == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDeleteMessageException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return analyticsDeleteMessage;
	}

	/**
	 * Returns the analytics delete message with the primary key or throws a <code>NoSuchDeleteMessageException</code> if it could not be found.
	 *
	 * @param analyticsDeleteMessageId the primary key of the analytics delete message
	 * @return the analytics delete message
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	@Override
	public AnalyticsDeleteMessage findByPrimaryKey(
			long analyticsDeleteMessageId)
		throws NoSuchDeleteMessageException {

		return findByPrimaryKey((Serializable)analyticsDeleteMessageId);
	}

	/**
	 * Returns the analytics delete message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the analytics delete message
	 * @return the analytics delete message, or <code>null</code> if a analytics delete message with the primary key could not be found
	 */
	@Override
	public AnalyticsDeleteMessage fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				AnalyticsDeleteMessage.class, primaryKey)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		AnalyticsDeleteMessage analyticsDeleteMessage = null;

		Session session = null;

		try {
			session = openSession();

			analyticsDeleteMessage = (AnalyticsDeleteMessage)session.get(
				AnalyticsDeleteMessageImpl.class, primaryKey);

			if (analyticsDeleteMessage != null) {
				cacheResult(analyticsDeleteMessage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return analyticsDeleteMessage;
	}

	/**
	 * Returns the analytics delete message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param analyticsDeleteMessageId the primary key of the analytics delete message
	 * @return the analytics delete message, or <code>null</code> if a analytics delete message with the primary key could not be found
	 */
	@Override
	public AnalyticsDeleteMessage fetchByPrimaryKey(
		long analyticsDeleteMessageId) {

		return fetchByPrimaryKey((Serializable)analyticsDeleteMessageId);
	}

	@Override
	public Map<Serializable, AnalyticsDeleteMessage> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				AnalyticsDeleteMessage.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AnalyticsDeleteMessage> map =
			new HashMap<Serializable, AnalyticsDeleteMessage>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AnalyticsDeleteMessage analyticsDeleteMessage = fetchByPrimaryKey(
				primaryKey);

			if (analyticsDeleteMessage != null) {
				map.put(primaryKey, analyticsDeleteMessage);
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

			for (AnalyticsDeleteMessage analyticsDeleteMessage :
					(List<AnalyticsDeleteMessage>)query.list()) {

				map.put(
					analyticsDeleteMessage.getPrimaryKeyObj(),
					analyticsDeleteMessage);

				cacheResult(analyticsDeleteMessage);
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
	 * Returns all the analytics delete messages.
	 *
	 * @return the analytics delete messages
	 */
	@Override
	public List<AnalyticsDeleteMessage> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the analytics delete messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @return the range of analytics delete messages
	 */
	@Override
	public List<AnalyticsDeleteMessage> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the analytics delete messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of analytics delete messages
	 */
	@Override
	public List<AnalyticsDeleteMessage> findAll(
		int start, int end,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the analytics delete messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of analytics delete messages
	 */
	@Override
	public List<AnalyticsDeleteMessage> findAll(
		int start, int end,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AnalyticsDeleteMessage.class);

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

		List<AnalyticsDeleteMessage> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AnalyticsDeleteMessage>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ANALYTICSDELETEMESSAGE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ANALYTICSDELETEMESSAGE;

				sql = sql.concat(AnalyticsDeleteMessageModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AnalyticsDeleteMessage>)QueryUtil.list(
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
	 * Removes all the analytics delete messages from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AnalyticsDeleteMessage analyticsDeleteMessage : findAll()) {
			remove(analyticsDeleteMessage);
		}
	}

	/**
	 * Returns the number of analytics delete messages.
	 *
	 * @return the number of analytics delete messages
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AnalyticsDeleteMessage.class);

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
					_SQL_COUNT_ANALYTICSDELETEMESSAGE);

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
		return "analyticsDeleteMessageId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ANALYTICSDELETEMESSAGE;
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
		return AnalyticsDeleteMessageModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AnalyticsDeleteMessage";
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
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("className");
		ctStrictColumnNames.add("classPK");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("analyticsDeleteMessageId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the analytics delete message persistence.
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

		_finderPathWithPaginationFindByC_GtM = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_GtM",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "modifiedDate"}, true);

		_finderPathWithPaginationCountByC_GtM = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_GtM",
			new String[] {Long.class.getName(), Date.class.getName()},
			new String[] {"companyId", "modifiedDate"}, false);

		AnalyticsDeleteMessageUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		AnalyticsDeleteMessageUtil.setPersistence(null);

		entityCache.removeCache(AnalyticsDeleteMessageImpl.class.getName());
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

	private static final String _SQL_SELECT_ANALYTICSDELETEMESSAGE =
		"SELECT analyticsDeleteMessage FROM AnalyticsDeleteMessage analyticsDeleteMessage";

	private static final String _SQL_SELECT_ANALYTICSDELETEMESSAGE_WHERE =
		"SELECT analyticsDeleteMessage FROM AnalyticsDeleteMessage analyticsDeleteMessage WHERE ";

	private static final String _SQL_COUNT_ANALYTICSDELETEMESSAGE =
		"SELECT COUNT(analyticsDeleteMessage) FROM AnalyticsDeleteMessage analyticsDeleteMessage";

	private static final String _SQL_COUNT_ANALYTICSDELETEMESSAGE_WHERE =
		"SELECT COUNT(analyticsDeleteMessage) FROM AnalyticsDeleteMessage analyticsDeleteMessage WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"analyticsDeleteMessage.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AnalyticsDeleteMessage exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AnalyticsDeleteMessage exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsDeleteMessagePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}