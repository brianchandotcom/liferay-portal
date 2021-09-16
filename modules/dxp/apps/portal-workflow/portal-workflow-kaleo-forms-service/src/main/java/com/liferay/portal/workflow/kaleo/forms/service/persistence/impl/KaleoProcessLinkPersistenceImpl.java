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

package com.liferay.portal.workflow.kaleo.forms.service.persistence.impl;

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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.workflow.kaleo.forms.exception.NoSuchKaleoProcessLinkException;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLinkTable;
import com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessLinkImpl;
import com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessLinkModelImpl;
import com.liferay.portal.workflow.kaleo.forms.service.persistence.KaleoProcessLinkPersistence;
import com.liferay.portal.workflow.kaleo.forms.service.persistence.impl.constants.KaleoFormsPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the kaleo process link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marcellus Tavares
 * @generated
 */
@Component(service = {KaleoProcessLinkPersistence.class, BasePersistence.class})
public class KaleoProcessLinkPersistenceImpl
	extends BasePersistenceImpl<KaleoProcessLink>
	implements KaleoProcessLinkPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>KaleoProcessLinkUtil</code> to access the kaleo process link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		KaleoProcessLinkImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByKaleoProcessId;
	private FinderPath _finderPathWithoutPaginationFindByKaleoProcessId;
	private FinderPath _finderPathCountByKaleoProcessId;

	/**
	 * Returns all the kaleo process links where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @return the matching kaleo process links
	 */
	@Override
	public List<KaleoProcessLink> findByKaleoProcessId(long kaleoProcessId) {
		return findByKaleoProcessId(
			kaleoProcessId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo process links where kaleoProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoProcessLinkModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @return the range of matching kaleo process links
	 */
	@Override
	public List<KaleoProcessLink> findByKaleoProcessId(
		long kaleoProcessId, int start, int end) {

		return findByKaleoProcessId(kaleoProcessId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo process links where kaleoProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoProcessLinkModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo process links
	 */
	@Override
	public List<KaleoProcessLink> findByKaleoProcessId(
		long kaleoProcessId, int start, int end,
		OrderByComparator<KaleoProcessLink> orderByComparator) {

		return findByKaleoProcessId(
			kaleoProcessId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo process links where kaleoProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoProcessLinkModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo process links
	 */
	@Override
	public List<KaleoProcessLink> findByKaleoProcessId(
		long kaleoProcessId, int start, int end,
		OrderByComparator<KaleoProcessLink> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByKaleoProcessId;
				finderArgs = new Object[] {kaleoProcessId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByKaleoProcessId;
			finderArgs = new Object[] {
				kaleoProcessId, start, end, orderByComparator
			};
		}

		List<KaleoProcessLink> list = null;

		if (useFinderCache) {
			list = (List<KaleoProcessLink>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoProcessLink kaleoProcessLink : list) {
					if (kaleoProcessId !=
							kaleoProcessLink.getKaleoProcessId()) {

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

			sb.append(_SQL_SELECT_KALEOPROCESSLINK_WHERE);

			sb.append(_FINDER_COLUMN_KALEOPROCESSID_KALEOPROCESSID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoProcessLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoProcessId);

				list = (List<KaleoProcessLink>)QueryUtil.list(
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
	 * Returns the first kaleo process link in the ordered set where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a matching kaleo process link could not be found
	 */
	@Override
	public KaleoProcessLink findByKaleoProcessId_First(
			long kaleoProcessId,
			OrderByComparator<KaleoProcessLink> orderByComparator)
		throws NoSuchKaleoProcessLinkException {

		KaleoProcessLink kaleoProcessLink = fetchByKaleoProcessId_First(
			kaleoProcessId, orderByComparator);

		if (kaleoProcessLink != null) {
			return kaleoProcessLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoProcessId=");
		sb.append(kaleoProcessId);

		sb.append("}");

		throw new NoSuchKaleoProcessLinkException(sb.toString());
	}

	/**
	 * Returns the first kaleo process link in the ordered set where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo process link, or <code>null</code> if a matching kaleo process link could not be found
	 */
	@Override
	public KaleoProcessLink fetchByKaleoProcessId_First(
		long kaleoProcessId,
		OrderByComparator<KaleoProcessLink> orderByComparator) {

		List<KaleoProcessLink> list = findByKaleoProcessId(
			kaleoProcessId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo process link in the ordered set where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a matching kaleo process link could not be found
	 */
	@Override
	public KaleoProcessLink findByKaleoProcessId_Last(
			long kaleoProcessId,
			OrderByComparator<KaleoProcessLink> orderByComparator)
		throws NoSuchKaleoProcessLinkException {

		KaleoProcessLink kaleoProcessLink = fetchByKaleoProcessId_Last(
			kaleoProcessId, orderByComparator);

		if (kaleoProcessLink != null) {
			return kaleoProcessLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoProcessId=");
		sb.append(kaleoProcessId);

		sb.append("}");

		throw new NoSuchKaleoProcessLinkException(sb.toString());
	}

	/**
	 * Returns the last kaleo process link in the ordered set where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo process link, or <code>null</code> if a matching kaleo process link could not be found
	 */
	@Override
	public KaleoProcessLink fetchByKaleoProcessId_Last(
		long kaleoProcessId,
		OrderByComparator<KaleoProcessLink> orderByComparator) {

		int count = countByKaleoProcessId(kaleoProcessId);

		if (count == 0) {
			return null;
		}

		List<KaleoProcessLink> list = findByKaleoProcessId(
			kaleoProcessId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo process links before and after the current kaleo process link in the ordered set where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessLinkId the primary key of the current kaleo process link
	 * @param kaleoProcessId the kaleo process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a kaleo process link with the primary key could not be found
	 */
	@Override
	public KaleoProcessLink[] findByKaleoProcessId_PrevAndNext(
			long kaleoProcessLinkId, long kaleoProcessId,
			OrderByComparator<KaleoProcessLink> orderByComparator)
		throws NoSuchKaleoProcessLinkException {

		KaleoProcessLink kaleoProcessLink = findByPrimaryKey(
			kaleoProcessLinkId);

		Session session = null;

		try {
			session = openSession();

			KaleoProcessLink[] array = new KaleoProcessLinkImpl[3];

			array[0] = getByKaleoProcessId_PrevAndNext(
				session, kaleoProcessLink, kaleoProcessId, orderByComparator,
				true);

			array[1] = kaleoProcessLink;

			array[2] = getByKaleoProcessId_PrevAndNext(
				session, kaleoProcessLink, kaleoProcessId, orderByComparator,
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

	protected KaleoProcessLink getByKaleoProcessId_PrevAndNext(
		Session session, KaleoProcessLink kaleoProcessLink, long kaleoProcessId,
		OrderByComparator<KaleoProcessLink> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOPROCESSLINK_WHERE);

		sb.append(_FINDER_COLUMN_KALEOPROCESSID_KALEOPROCESSID_2);

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
			sb.append(KaleoProcessLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoProcessId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoProcessLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoProcessLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo process links where kaleoProcessId = &#63; from the database.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 */
	@Override
	public void removeByKaleoProcessId(long kaleoProcessId) {
		for (KaleoProcessLink kaleoProcessLink :
				findByKaleoProcessId(
					kaleoProcessId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(kaleoProcessLink);
		}
	}

	/**
	 * Returns the number of kaleo process links where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @return the number of matching kaleo process links
	 */
	@Override
	public int countByKaleoProcessId(long kaleoProcessId) {
		FinderPath finderPath = _finderPathCountByKaleoProcessId;

		Object[] finderArgs = new Object[] {kaleoProcessId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOPROCESSLINK_WHERE);

			sb.append(_FINDER_COLUMN_KALEOPROCESSID_KALEOPROCESSID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoProcessId);

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

	private static final String _FINDER_COLUMN_KALEOPROCESSID_KALEOPROCESSID_2 =
		"kaleoProcessLink.kaleoProcessId = ?";

	private FinderPath _finderPathFetchByKPI_WTN;
	private FinderPath _finderPathCountByKPI_WTN;

	/**
	 * Returns the kaleo process link where kaleoProcessId = &#63; and workflowTaskName = &#63; or throws a <code>NoSuchKaleoProcessLinkException</code> if it could not be found.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param workflowTaskName the workflow task name
	 * @return the matching kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a matching kaleo process link could not be found
	 */
	@Override
	public KaleoProcessLink findByKPI_WTN(
			long kaleoProcessId, String workflowTaskName)
		throws NoSuchKaleoProcessLinkException {

		KaleoProcessLink kaleoProcessLink = fetchByKPI_WTN(
			kaleoProcessId, workflowTaskName);

		if (kaleoProcessLink == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("kaleoProcessId=");
			sb.append(kaleoProcessId);

			sb.append(", workflowTaskName=");
			sb.append(workflowTaskName);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchKaleoProcessLinkException(sb.toString());
		}

		return kaleoProcessLink;
	}

	/**
	 * Returns the kaleo process link where kaleoProcessId = &#63; and workflowTaskName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param workflowTaskName the workflow task name
	 * @return the matching kaleo process link, or <code>null</code> if a matching kaleo process link could not be found
	 */
	@Override
	public KaleoProcessLink fetchByKPI_WTN(
		long kaleoProcessId, String workflowTaskName) {

		return fetchByKPI_WTN(kaleoProcessId, workflowTaskName, true);
	}

	/**
	 * Returns the kaleo process link where kaleoProcessId = &#63; and workflowTaskName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param workflowTaskName the workflow task name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo process link, or <code>null</code> if a matching kaleo process link could not be found
	 */
	@Override
	public KaleoProcessLink fetchByKPI_WTN(
		long kaleoProcessId, String workflowTaskName, boolean useFinderCache) {

		workflowTaskName = Objects.toString(workflowTaskName, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {kaleoProcessId, workflowTaskName};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByKPI_WTN, finderArgs);
		}

		if (result instanceof KaleoProcessLink) {
			KaleoProcessLink kaleoProcessLink = (KaleoProcessLink)result;

			if ((kaleoProcessId != kaleoProcessLink.getKaleoProcessId()) ||
				!Objects.equals(
					workflowTaskName, kaleoProcessLink.getWorkflowTaskName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_KALEOPROCESSLINK_WHERE);

			sb.append(_FINDER_COLUMN_KPI_WTN_KALEOPROCESSID_2);

			boolean bindWorkflowTaskName = false;

			if (workflowTaskName.isEmpty()) {
				sb.append(_FINDER_COLUMN_KPI_WTN_WORKFLOWTASKNAME_3);
			}
			else {
				bindWorkflowTaskName = true;

				sb.append(_FINDER_COLUMN_KPI_WTN_WORKFLOWTASKNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoProcessId);

				if (bindWorkflowTaskName) {
					queryPos.add(workflowTaskName);
				}

				List<KaleoProcessLink> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByKPI_WTN, finderArgs, list);
					}
				}
				else {
					KaleoProcessLink kaleoProcessLink = list.get(0);

					result = kaleoProcessLink;

					cacheResult(kaleoProcessLink);
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
			return (KaleoProcessLink)result;
		}
	}

	/**
	 * Removes the kaleo process link where kaleoProcessId = &#63; and workflowTaskName = &#63; from the database.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param workflowTaskName the workflow task name
	 * @return the kaleo process link that was removed
	 */
	@Override
	public KaleoProcessLink removeByKPI_WTN(
			long kaleoProcessId, String workflowTaskName)
		throws NoSuchKaleoProcessLinkException {

		KaleoProcessLink kaleoProcessLink = findByKPI_WTN(
			kaleoProcessId, workflowTaskName);

		return remove(kaleoProcessLink);
	}

	/**
	 * Returns the number of kaleo process links where kaleoProcessId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param workflowTaskName the workflow task name
	 * @return the number of matching kaleo process links
	 */
	@Override
	public int countByKPI_WTN(long kaleoProcessId, String workflowTaskName) {
		workflowTaskName = Objects.toString(workflowTaskName, "");

		FinderPath finderPath = _finderPathCountByKPI_WTN;

		Object[] finderArgs = new Object[] {kaleoProcessId, workflowTaskName};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEOPROCESSLINK_WHERE);

			sb.append(_FINDER_COLUMN_KPI_WTN_KALEOPROCESSID_2);

			boolean bindWorkflowTaskName = false;

			if (workflowTaskName.isEmpty()) {
				sb.append(_FINDER_COLUMN_KPI_WTN_WORKFLOWTASKNAME_3);
			}
			else {
				bindWorkflowTaskName = true;

				sb.append(_FINDER_COLUMN_KPI_WTN_WORKFLOWTASKNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoProcessId);

				if (bindWorkflowTaskName) {
					queryPos.add(workflowTaskName);
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

	private static final String _FINDER_COLUMN_KPI_WTN_KALEOPROCESSID_2 =
		"kaleoProcessLink.kaleoProcessId = ? AND ";

	private static final String _FINDER_COLUMN_KPI_WTN_WORKFLOWTASKNAME_2 =
		"kaleoProcessLink.workflowTaskName = ?";

	private static final String _FINDER_COLUMN_KPI_WTN_WORKFLOWTASKNAME_3 =
		"(kaleoProcessLink.workflowTaskName IS NULL OR kaleoProcessLink.workflowTaskName = '')";

	public KaleoProcessLinkPersistenceImpl() {
		setModelClass(KaleoProcessLink.class);

		setModelImplClass(KaleoProcessLinkImpl.class);
		setModelPKClass(long.class);

		setTable(KaleoProcessLinkTable.INSTANCE);
	}

	/**
	 * Caches the kaleo process link in the entity cache if it is enabled.
	 *
	 * @param kaleoProcessLink the kaleo process link
	 */
	@Override
	public void cacheResult(KaleoProcessLink kaleoProcessLink) {
		entityCache.putResult(
			KaleoProcessLinkImpl.class, kaleoProcessLink.getPrimaryKey(),
			kaleoProcessLink);

		finderCache.putResult(
			_finderPathFetchByKPI_WTN,
			new Object[] {
				kaleoProcessLink.getKaleoProcessId(),
				kaleoProcessLink.getWorkflowTaskName()
			},
			kaleoProcessLink);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the kaleo process links in the entity cache if it is enabled.
	 *
	 * @param kaleoProcessLinks the kaleo process links
	 */
	@Override
	public void cacheResult(List<KaleoProcessLink> kaleoProcessLinks) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (kaleoProcessLinks.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (KaleoProcessLink kaleoProcessLink : kaleoProcessLinks) {
			if (entityCache.getResult(
					KaleoProcessLinkImpl.class,
					kaleoProcessLink.getPrimaryKey()) == null) {

				cacheResult(kaleoProcessLink);
			}
		}
	}

	/**
	 * Clears the cache for all kaleo process links.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KaleoProcessLinkImpl.class);

		finderCache.clearCache(KaleoProcessLinkImpl.class);
	}

	/**
	 * Clears the cache for the kaleo process link.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(KaleoProcessLink kaleoProcessLink) {
		entityCache.removeResult(KaleoProcessLinkImpl.class, kaleoProcessLink);
	}

	@Override
	public void clearCache(List<KaleoProcessLink> kaleoProcessLinks) {
		for (KaleoProcessLink kaleoProcessLink : kaleoProcessLinks) {
			entityCache.removeResult(
				KaleoProcessLinkImpl.class, kaleoProcessLink);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(KaleoProcessLinkImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(KaleoProcessLinkImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		KaleoProcessLinkModelImpl kaleoProcessLinkModelImpl) {

		Object[] args = new Object[] {
			kaleoProcessLinkModelImpl.getKaleoProcessId(),
			kaleoProcessLinkModelImpl.getWorkflowTaskName()
		};

		finderCache.putResult(_finderPathCountByKPI_WTN, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByKPI_WTN, args, kaleoProcessLinkModelImpl);
	}

	/**
	 * Creates a new kaleo process link with the primary key. Does not add the kaleo process link to the database.
	 *
	 * @param kaleoProcessLinkId the primary key for the new kaleo process link
	 * @return the new kaleo process link
	 */
	@Override
	public KaleoProcessLink create(long kaleoProcessLinkId) {
		KaleoProcessLink kaleoProcessLink = new KaleoProcessLinkImpl();

		kaleoProcessLink.setNew(true);
		kaleoProcessLink.setPrimaryKey(kaleoProcessLinkId);

		kaleoProcessLink.setCompanyId(CompanyThreadLocal.getCompanyId());

		return kaleoProcessLink;
	}

	/**
	 * Removes the kaleo process link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoProcessLinkId the primary key of the kaleo process link
	 * @return the kaleo process link that was removed
	 * @throws NoSuchKaleoProcessLinkException if a kaleo process link with the primary key could not be found
	 */
	@Override
	public KaleoProcessLink remove(long kaleoProcessLinkId)
		throws NoSuchKaleoProcessLinkException {

		return remove((Serializable)kaleoProcessLinkId);
	}

	/**
	 * Removes the kaleo process link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kaleo process link
	 * @return the kaleo process link that was removed
	 * @throws NoSuchKaleoProcessLinkException if a kaleo process link with the primary key could not be found
	 */
	@Override
	public KaleoProcessLink remove(Serializable primaryKey)
		throws NoSuchKaleoProcessLinkException {

		Session session = null;

		try {
			session = openSession();

			KaleoProcessLink kaleoProcessLink = (KaleoProcessLink)session.get(
				KaleoProcessLinkImpl.class, primaryKey);

			if (kaleoProcessLink == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchKaleoProcessLinkException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(kaleoProcessLink);
		}
		catch (NoSuchKaleoProcessLinkException noSuchEntityException) {
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
	protected KaleoProcessLink removeImpl(KaleoProcessLink kaleoProcessLink) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoProcessLink)) {
				kaleoProcessLink = (KaleoProcessLink)session.get(
					KaleoProcessLinkImpl.class,
					kaleoProcessLink.getPrimaryKeyObj());
			}

			if (kaleoProcessLink != null) {
				session.delete(kaleoProcessLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (kaleoProcessLink != null) {
			clearCache(kaleoProcessLink);
		}

		return kaleoProcessLink;
	}

	@Override
	public KaleoProcessLink updateImpl(KaleoProcessLink kaleoProcessLink) {
		boolean isNew = kaleoProcessLink.isNew();

		if (!(kaleoProcessLink instanceof KaleoProcessLinkModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(kaleoProcessLink.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					kaleoProcessLink);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in kaleoProcessLink proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom KaleoProcessLink implementation " +
					kaleoProcessLink.getClass());
		}

		KaleoProcessLinkModelImpl kaleoProcessLinkModelImpl =
			(KaleoProcessLinkModelImpl)kaleoProcessLink;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(kaleoProcessLink);
			}
			else {
				kaleoProcessLink = (KaleoProcessLink)session.merge(
					kaleoProcessLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			KaleoProcessLinkImpl.class, kaleoProcessLinkModelImpl, false, true);

		cacheUniqueFindersCache(kaleoProcessLinkModelImpl);

		if (isNew) {
			kaleoProcessLink.setNew(false);
		}

		kaleoProcessLink.resetOriginalValues();

		return kaleoProcessLink;
	}

	/**
	 * Returns the kaleo process link with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo process link
	 * @return the kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a kaleo process link with the primary key could not be found
	 */
	@Override
	public KaleoProcessLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchKaleoProcessLinkException {

		KaleoProcessLink kaleoProcessLink = fetchByPrimaryKey(primaryKey);

		if (kaleoProcessLink == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchKaleoProcessLinkException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return kaleoProcessLink;
	}

	/**
	 * Returns the kaleo process link with the primary key or throws a <code>NoSuchKaleoProcessLinkException</code> if it could not be found.
	 *
	 * @param kaleoProcessLinkId the primary key of the kaleo process link
	 * @return the kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a kaleo process link with the primary key could not be found
	 */
	@Override
	public KaleoProcessLink findByPrimaryKey(long kaleoProcessLinkId)
		throws NoSuchKaleoProcessLinkException {

		return findByPrimaryKey((Serializable)kaleoProcessLinkId);
	}

	/**
	 * Returns the kaleo process link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoProcessLinkId the primary key of the kaleo process link
	 * @return the kaleo process link, or <code>null</code> if a kaleo process link with the primary key could not be found
	 */
	@Override
	public KaleoProcessLink fetchByPrimaryKey(long kaleoProcessLinkId) {
		return fetchByPrimaryKey((Serializable)kaleoProcessLinkId);
	}

	/**
	 * Returns all the kaleo process links.
	 *
	 * @return the kaleo process links
	 */
	@Override
	public List<KaleoProcessLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo process links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoProcessLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @return the range of kaleo process links
	 */
	@Override
	public List<KaleoProcessLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo process links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoProcessLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo process links
	 */
	@Override
	public List<KaleoProcessLink> findAll(
		int start, int end,
		OrderByComparator<KaleoProcessLink> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo process links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoProcessLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo process links
	 */
	@Override
	public List<KaleoProcessLink> findAll(
		int start, int end,
		OrderByComparator<KaleoProcessLink> orderByComparator,
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

		List<KaleoProcessLink> list = null;

		if (useFinderCache) {
			list = (List<KaleoProcessLink>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_KALEOPROCESSLINK);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_KALEOPROCESSLINK;

				sql = sql.concat(KaleoProcessLinkModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<KaleoProcessLink>)QueryUtil.list(
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
	 * Removes all the kaleo process links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoProcessLink kaleoProcessLink : findAll()) {
			remove(kaleoProcessLink);
		}
	}

	/**
	 * Returns the number of kaleo process links.
	 *
	 * @return the number of kaleo process links
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_KALEOPROCESSLINK);

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
		return "kaleoProcessLinkId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_KALEOPROCESSLINK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return KaleoProcessLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the kaleo process link persistence.
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

		_finderPathWithPaginationFindByKaleoProcessId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKaleoProcessId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"kaleoProcessId"}, true);

		_finderPathWithoutPaginationFindByKaleoProcessId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByKaleoProcessId",
			new String[] {Long.class.getName()},
			new String[] {"kaleoProcessId"}, true);

		_finderPathCountByKaleoProcessId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKaleoProcessId",
			new String[] {Long.class.getName()},
			new String[] {"kaleoProcessId"}, false);

		_finderPathFetchByKPI_WTN = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByKPI_WTN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"kaleoProcessId", "workflowTaskName"}, true);

		_finderPathCountByKPI_WTN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKPI_WTN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"kaleoProcessId", "workflowTaskName"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(KaleoProcessLinkImpl.class.getName());
	}

	@Override
	@Reference(
		target = KaleoFormsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = KaleoFormsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = KaleoFormsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_KALEOPROCESSLINK =
		"SELECT kaleoProcessLink FROM KaleoProcessLink kaleoProcessLink";

	private static final String _SQL_SELECT_KALEOPROCESSLINK_WHERE =
		"SELECT kaleoProcessLink FROM KaleoProcessLink kaleoProcessLink WHERE ";

	private static final String _SQL_COUNT_KALEOPROCESSLINK =
		"SELECT COUNT(kaleoProcessLink) FROM KaleoProcessLink kaleoProcessLink";

	private static final String _SQL_COUNT_KALEOPROCESSLINK_WHERE =
		"SELECT COUNT(kaleoProcessLink) FROM KaleoProcessLink kaleoProcessLink WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "kaleoProcessLink.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No KaleoProcessLink exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No KaleoProcessLink exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoProcessLinkPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private KaleoProcessLinkModelArgumentsResolver
		_kaleoProcessLinkModelArgumentsResolver;

}