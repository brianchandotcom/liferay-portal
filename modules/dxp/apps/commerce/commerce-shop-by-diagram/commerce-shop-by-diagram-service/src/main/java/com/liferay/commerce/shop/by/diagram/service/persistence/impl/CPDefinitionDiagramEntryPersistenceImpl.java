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

import com.liferay.commerce.shop.by.diagram.exception.NoSuchCPDefinitionDiagramEntryException;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntryTable;
import com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramEntryImpl;
import com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramEntryModelImpl;
import com.liferay.commerce.shop.by.diagram.service.persistence.CPDefinitionDiagramEntryPersistence;
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
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the cp definition diagram entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Sbarra
 * @generated
 */
@Component(
	service = {CPDefinitionDiagramEntryPersistence.class, BasePersistence.class}
)
public class CPDefinitionDiagramEntryPersistenceImpl
	extends BasePersistenceImpl<CPDefinitionDiagramEntry>
	implements CPDefinitionDiagramEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPDefinitionDiagramEntryUtil</code> to access the cp definition diagram entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPDefinitionDiagramEntryImpl.class.getName();

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
	 * Returns all the cp definition diagram entries where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram entries
	 */
	@Override
	public List<CPDefinitionDiagramEntry> findByCPDefinitionId(
		long CPDefinitionId) {

		return findByCPDefinitionId(
			CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition diagram entries where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @return the range of matching cp definition diagram entries
	 */
	@Override
	public List<CPDefinitionDiagramEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {

		return findByCPDefinitionId(CPDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram entries where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition diagram entries
	 */
	@Override
	public List<CPDefinitionDiagramEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator) {

		return findByCPDefinitionId(
			CPDefinitionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram entries where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition diagram entries
	 */
	@Override
	public List<CPDefinitionDiagramEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator,
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

		List<CPDefinitionDiagramEntry> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionDiagramEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionDiagramEntry cpDefinitionDiagramEntry : list) {
					if (CPDefinitionId !=
							cpDefinitionDiagramEntry.getCPDefinitionId()) {

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

			sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDefinitionDiagramEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				list = (List<CPDefinitionDiagramEntry>)QueryUtil.list(
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
	 * Returns the first cp definition diagram entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a matching cp definition diagram entry could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry findByCPDefinitionId_First(
			long CPDefinitionId,
			OrderByComparator<CPDefinitionDiagramEntry> orderByComparator)
		throws NoSuchCPDefinitionDiagramEntryException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			fetchByCPDefinitionId_First(CPDefinitionId, orderByComparator);

		if (cpDefinitionDiagramEntry != null) {
			return cpDefinitionDiagramEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionId=");
		sb.append(CPDefinitionId);

		sb.append("}");

		throw new NoSuchCPDefinitionDiagramEntryException(sb.toString());
	}

	/**
	 * Returns the first cp definition diagram entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram entry, or <code>null</code> if a matching cp definition diagram entry could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator) {

		List<CPDefinitionDiagramEntry> list = findByCPDefinitionId(
			CPDefinitionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition diagram entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a matching cp definition diagram entry could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry findByCPDefinitionId_Last(
			long CPDefinitionId,
			OrderByComparator<CPDefinitionDiagramEntry> orderByComparator)
		throws NoSuchCPDefinitionDiagramEntryException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			fetchByCPDefinitionId_Last(CPDefinitionId, orderByComparator);

		if (cpDefinitionDiagramEntry != null) {
			return cpDefinitionDiagramEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionId=");
		sb.append(CPDefinitionId);

		sb.append("}");

		throw new NoSuchCPDefinitionDiagramEntryException(sb.toString());
	}

	/**
	 * Returns the last cp definition diagram entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram entry, or <code>null</code> if a matching cp definition diagram entry could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator) {

		int count = countByCPDefinitionId(CPDefinitionId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionDiagramEntry> list = findByCPDefinitionId(
			CPDefinitionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition diagram entries before and after the current cp definition diagram entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key of the current cp definition diagram entry
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a cp definition diagram entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry[] findByCPDefinitionId_PrevAndNext(
			long CPDefinitionDiagramEntryId, long CPDefinitionId,
			OrderByComparator<CPDefinitionDiagramEntry> orderByComparator)
		throws NoSuchCPDefinitionDiagramEntryException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry = findByPrimaryKey(
			CPDefinitionDiagramEntryId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionDiagramEntry[] array =
				new CPDefinitionDiagramEntryImpl[3];

			array[0] = getByCPDefinitionId_PrevAndNext(
				session, cpDefinitionDiagramEntry, CPDefinitionId,
				orderByComparator, true);

			array[1] = cpDefinitionDiagramEntry;

			array[2] = getByCPDefinitionId_PrevAndNext(
				session, cpDefinitionDiagramEntry, CPDefinitionId,
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

	protected CPDefinitionDiagramEntry getByCPDefinitionId_PrevAndNext(
		Session session, CPDefinitionDiagramEntry cpDefinitionDiagramEntry,
		long CPDefinitionId,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMENTRY_WHERE);

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
			sb.append(CPDefinitionDiagramEntryModelImpl.ORDER_BY_JPQL);
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
						cpDefinitionDiagramEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionDiagramEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition diagram entries where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	@Override
	public void removeByCPDefinitionId(long CPDefinitionId) {
		for (CPDefinitionDiagramEntry cpDefinitionDiagramEntry :
				findByCPDefinitionId(
					CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpDefinitionDiagramEntry);
		}
	}

	/**
	 * Returns the number of cp definition diagram entries where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition diagram entries
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		FinderPath finderPath = _finderPathCountByCPDefinitionId;

		Object[] finderArgs = new Object[] {CPDefinitionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITIONDIAGRAMENTRY_WHERE);

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
		"cpDefinitionDiagramEntry.CPDefinitionId = ?";

	private FinderPath _finderPathFetchByCPDI_S;
	private FinderPath _finderPathCountByCPDI_S;

	/**
	 * Returns the cp definition diagram entry where CPDefinitionId = &#63; and sequence = &#63; or throws a <code>NoSuchCPDefinitionDiagramEntryException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param sequence the sequence
	 * @return the matching cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a matching cp definition diagram entry could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry findByCPDI_S(
			long CPDefinitionId, String sequence)
		throws NoSuchCPDefinitionDiagramEntryException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry = fetchByCPDI_S(
			CPDefinitionId, sequence);

		if (cpDefinitionDiagramEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CPDefinitionId=");
			sb.append(CPDefinitionId);

			sb.append(", sequence=");
			sb.append(sequence);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPDefinitionDiagramEntryException(sb.toString());
		}

		return cpDefinitionDiagramEntry;
	}

	/**
	 * Returns the cp definition diagram entry where CPDefinitionId = &#63; and sequence = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param sequence the sequence
	 * @return the matching cp definition diagram entry, or <code>null</code> if a matching cp definition diagram entry could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry fetchByCPDI_S(
		long CPDefinitionId, String sequence) {

		return fetchByCPDI_S(CPDefinitionId, sequence, true);
	}

	/**
	 * Returns the cp definition diagram entry where CPDefinitionId = &#63; and sequence = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param sequence the sequence
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition diagram entry, or <code>null</code> if a matching cp definition diagram entry could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry fetchByCPDI_S(
		long CPDefinitionId, String sequence, boolean useFinderCache) {

		sequence = Objects.toString(sequence, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {CPDefinitionId, sequence};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCPDI_S, finderArgs);
		}

		if (result instanceof CPDefinitionDiagramEntry) {
			CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
				(CPDefinitionDiagramEntry)result;

			if ((CPDefinitionId !=
					cpDefinitionDiagramEntry.getCPDefinitionId()) ||
				!Objects.equals(
					sequence, cpDefinitionDiagramEntry.getSequence())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CPDI_S_CPDEFINITIONID_2);

			boolean bindSequence = false;

			if (sequence.isEmpty()) {
				sb.append(_FINDER_COLUMN_CPDI_S_SEQUENCE_3);
			}
			else {
				bindSequence = true;

				sb.append(_FINDER_COLUMN_CPDI_S_SEQUENCE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				if (bindSequence) {
					queryPos.add(sequence);
				}

				List<CPDefinitionDiagramEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCPDI_S, finderArgs, list);
					}
				}
				else {
					CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
						list.get(0);

					result = cpDefinitionDiagramEntry;

					cacheResult(cpDefinitionDiagramEntry);
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
			return (CPDefinitionDiagramEntry)result;
		}
	}

	/**
	 * Removes the cp definition diagram entry where CPDefinitionId = &#63; and sequence = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param sequence the sequence
	 * @return the cp definition diagram entry that was removed
	 */
	@Override
	public CPDefinitionDiagramEntry removeByCPDI_S(
			long CPDefinitionId, String sequence)
		throws NoSuchCPDefinitionDiagramEntryException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry = findByCPDI_S(
			CPDefinitionId, sequence);

		return remove(cpDefinitionDiagramEntry);
	}

	/**
	 * Returns the number of cp definition diagram entries where CPDefinitionId = &#63; and sequence = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param sequence the sequence
	 * @return the number of matching cp definition diagram entries
	 */
	@Override
	public int countByCPDI_S(long CPDefinitionId, String sequence) {
		sequence = Objects.toString(sequence, "");

		FinderPath finderPath = _finderPathCountByCPDI_S;

		Object[] finderArgs = new Object[] {CPDefinitionId, sequence};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITIONDIAGRAMENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CPDI_S_CPDEFINITIONID_2);

			boolean bindSequence = false;

			if (sequence.isEmpty()) {
				sb.append(_FINDER_COLUMN_CPDI_S_SEQUENCE_3);
			}
			else {
				bindSequence = true;

				sb.append(_FINDER_COLUMN_CPDI_S_SEQUENCE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				if (bindSequence) {
					queryPos.add(sequence);
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

	private static final String _FINDER_COLUMN_CPDI_S_CPDEFINITIONID_2 =
		"cpDefinitionDiagramEntry.CPDefinitionId = ? AND ";

	private static final String _FINDER_COLUMN_CPDI_S_SEQUENCE_2 =
		"cpDefinitionDiagramEntry.sequence = ?";

	private static final String _FINDER_COLUMN_CPDI_S_SEQUENCE_3 =
		"(cpDefinitionDiagramEntry.sequence IS NULL OR cpDefinitionDiagramEntry.sequence = '')";

	public CPDefinitionDiagramEntryPersistenceImpl() {
		setModelClass(CPDefinitionDiagramEntry.class);

		setModelImplClass(CPDefinitionDiagramEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CPDefinitionDiagramEntryTable.INSTANCE);
	}

	/**
	 * Caches the cp definition diagram entry in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramEntry the cp definition diagram entry
	 */
	@Override
	public void cacheResult(CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {
		entityCache.putResult(
			CPDefinitionDiagramEntryImpl.class,
			cpDefinitionDiagramEntry.getPrimaryKey(), cpDefinitionDiagramEntry);

		finderCache.putResult(
			_finderPathFetchByCPDI_S,
			new Object[] {
				cpDefinitionDiagramEntry.getCPDefinitionId(),
				cpDefinitionDiagramEntry.getSequence()
			},
			cpDefinitionDiagramEntry);
	}

	/**
	 * Caches the cp definition diagram entries in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramEntries the cp definition diagram entries
	 */
	@Override
	public void cacheResult(
		List<CPDefinitionDiagramEntry> cpDefinitionDiagramEntries) {

		for (CPDefinitionDiagramEntry cpDefinitionDiagramEntry :
				cpDefinitionDiagramEntries) {

			if (entityCache.getResult(
					CPDefinitionDiagramEntryImpl.class,
					cpDefinitionDiagramEntry.getPrimaryKey()) == null) {

				cacheResult(cpDefinitionDiagramEntry);
			}
		}
	}

	/**
	 * Clears the cache for all cp definition diagram entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionDiagramEntryImpl.class);

		finderCache.clearCache(CPDefinitionDiagramEntryImpl.class);
	}

	/**
	 * Clears the cache for the cp definition diagram entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {
		entityCache.removeResult(
			CPDefinitionDiagramEntryImpl.class, cpDefinitionDiagramEntry);
	}

	@Override
	public void clearCache(
		List<CPDefinitionDiagramEntry> cpDefinitionDiagramEntries) {

		for (CPDefinitionDiagramEntry cpDefinitionDiagramEntry :
				cpDefinitionDiagramEntries) {

			entityCache.removeResult(
				CPDefinitionDiagramEntryImpl.class, cpDefinitionDiagramEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPDefinitionDiagramEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPDefinitionDiagramEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionDiagramEntryModelImpl cpDefinitionDiagramEntryModelImpl) {

		Object[] args = new Object[] {
			cpDefinitionDiagramEntryModelImpl.getCPDefinitionId(),
			cpDefinitionDiagramEntryModelImpl.getSequence()
		};

		finderCache.putResult(_finderPathCountByCPDI_S, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCPDI_S, args, cpDefinitionDiagramEntryModelImpl);
	}

	/**
	 * Creates a new cp definition diagram entry with the primary key. Does not add the cp definition diagram entry to the database.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key for the new cp definition diagram entry
	 * @return the new cp definition diagram entry
	 */
	@Override
	public CPDefinitionDiagramEntry create(long CPDefinitionDiagramEntryId) {
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			new CPDefinitionDiagramEntryImpl();

		cpDefinitionDiagramEntry.setNew(true);
		cpDefinitionDiagramEntry.setPrimaryKey(CPDefinitionDiagramEntryId);

		cpDefinitionDiagramEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return cpDefinitionDiagramEntry;
	}

	/**
	 * Removes the cp definition diagram entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key of the cp definition diagram entry
	 * @return the cp definition diagram entry that was removed
	 * @throws NoSuchCPDefinitionDiagramEntryException if a cp definition diagram entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry remove(long CPDefinitionDiagramEntryId)
		throws NoSuchCPDefinitionDiagramEntryException {

		return remove((Serializable)CPDefinitionDiagramEntryId);
	}

	/**
	 * Removes the cp definition diagram entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition diagram entry
	 * @return the cp definition diagram entry that was removed
	 * @throws NoSuchCPDefinitionDiagramEntryException if a cp definition diagram entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry remove(Serializable primaryKey)
		throws NoSuchCPDefinitionDiagramEntryException {

		Session session = null;

		try {
			session = openSession();

			CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
				(CPDefinitionDiagramEntry)session.get(
					CPDefinitionDiagramEntryImpl.class, primaryKey);

			if (cpDefinitionDiagramEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionDiagramEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpDefinitionDiagramEntry);
		}
		catch (NoSuchCPDefinitionDiagramEntryException noSuchEntityException) {
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
	protected CPDefinitionDiagramEntry removeImpl(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionDiagramEntry)) {
				cpDefinitionDiagramEntry =
					(CPDefinitionDiagramEntry)session.get(
						CPDefinitionDiagramEntryImpl.class,
						cpDefinitionDiagramEntry.getPrimaryKeyObj());
			}

			if (cpDefinitionDiagramEntry != null) {
				session.delete(cpDefinitionDiagramEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionDiagramEntry != null) {
			clearCache(cpDefinitionDiagramEntry);
		}

		return cpDefinitionDiagramEntry;
	}

	@Override
	public CPDefinitionDiagramEntry updateImpl(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		boolean isNew = cpDefinitionDiagramEntry.isNew();

		if (!(cpDefinitionDiagramEntry instanceof
				CPDefinitionDiagramEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpDefinitionDiagramEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpDefinitionDiagramEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpDefinitionDiagramEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDefinitionDiagramEntry implementation " +
					cpDefinitionDiagramEntry.getClass());
		}

		CPDefinitionDiagramEntryModelImpl cpDefinitionDiagramEntryModelImpl =
			(CPDefinitionDiagramEntryModelImpl)cpDefinitionDiagramEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpDefinitionDiagramEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDefinitionDiagramEntry.setCreateDate(date);
			}
			else {
				cpDefinitionDiagramEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpDefinitionDiagramEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDefinitionDiagramEntry.setModifiedDate(date);
			}
			else {
				cpDefinitionDiagramEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(cpDefinitionDiagramEntry);
			}
			else {
				cpDefinitionDiagramEntry =
					(CPDefinitionDiagramEntry)session.merge(
						cpDefinitionDiagramEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPDefinitionDiagramEntryImpl.class,
			cpDefinitionDiagramEntryModelImpl, false, true);

		cacheUniqueFindersCache(cpDefinitionDiagramEntryModelImpl);

		if (isNew) {
			cpDefinitionDiagramEntry.setNew(false);
		}

		cpDefinitionDiagramEntry.resetOriginalValues();

		return cpDefinitionDiagramEntry;
	}

	/**
	 * Returns the cp definition diagram entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition diagram entry
	 * @return the cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a cp definition diagram entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDefinitionDiagramEntryException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry = fetchByPrimaryKey(
			primaryKey);

		if (cpDefinitionDiagramEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionDiagramEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpDefinitionDiagramEntry;
	}

	/**
	 * Returns the cp definition diagram entry with the primary key or throws a <code>NoSuchCPDefinitionDiagramEntryException</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key of the cp definition diagram entry
	 * @return the cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a cp definition diagram entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry findByPrimaryKey(
			long CPDefinitionDiagramEntryId)
		throws NoSuchCPDefinitionDiagramEntryException {

		return findByPrimaryKey((Serializable)CPDefinitionDiagramEntryId);
	}

	/**
	 * Returns the cp definition diagram entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key of the cp definition diagram entry
	 * @return the cp definition diagram entry, or <code>null</code> if a cp definition diagram entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry fetchByPrimaryKey(
		long CPDefinitionDiagramEntryId) {

		return fetchByPrimaryKey((Serializable)CPDefinitionDiagramEntryId);
	}

	/**
	 * Returns all the cp definition diagram entries.
	 *
	 * @return the cp definition diagram entries
	 */
	@Override
	public List<CPDefinitionDiagramEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition diagram entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @return the range of cp definition diagram entries
	 */
	@Override
	public List<CPDefinitionDiagramEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition diagram entries
	 */
	@Override
	public List<CPDefinitionDiagramEntry> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definition diagram entries
	 */
	@Override
	public List<CPDefinitionDiagramEntry> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator,
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

		List<CPDefinitionDiagramEntry> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionDiagramEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITIONDIAGRAMENTRY;

				sql = sql.concat(
					CPDefinitionDiagramEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CPDefinitionDiagramEntry>)QueryUtil.list(
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
	 * Removes all the cp definition diagram entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionDiagramEntry cpDefinitionDiagramEntry : findAll()) {
			remove(cpDefinitionDiagramEntry);
		}
	}

	/**
	 * Returns the number of cp definition diagram entries.
	 *
	 * @return the number of cp definition diagram entries
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
					_SQL_COUNT_CPDEFINITIONDIAGRAMENTRY);

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
		return "CPDefinitionDiagramEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPDEFINITIONDIAGRAMENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CPDefinitionDiagramEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp definition diagram entry persistence.
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

		_finderPathFetchByCPDI_S = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCPDI_S",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"CPDefinitionId", "sequence"}, true);

		_finderPathCountByCPDI_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPDI_S",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"CPDefinitionId", "sequence"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CPDefinitionDiagramEntryImpl.class.getName());
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

	private static final String _SQL_SELECT_CPDEFINITIONDIAGRAMENTRY =
		"SELECT cpDefinitionDiagramEntry FROM CPDefinitionDiagramEntry cpDefinitionDiagramEntry";

	private static final String _SQL_SELECT_CPDEFINITIONDIAGRAMENTRY_WHERE =
		"SELECT cpDefinitionDiagramEntry FROM CPDefinitionDiagramEntry cpDefinitionDiagramEntry WHERE ";

	private static final String _SQL_COUNT_CPDEFINITIONDIAGRAMENTRY =
		"SELECT COUNT(cpDefinitionDiagramEntry) FROM CPDefinitionDiagramEntry cpDefinitionDiagramEntry";

	private static final String _SQL_COUNT_CPDEFINITIONDIAGRAMENTRY_WHERE =
		"SELECT COUNT(cpDefinitionDiagramEntry) FROM CPDefinitionDiagramEntry cpDefinitionDiagramEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpDefinitionDiagramEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPDefinitionDiagramEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPDefinitionDiagramEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionDiagramEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private CPDefinitionDiagramEntryModelArgumentsResolver
		_cpDefinitionDiagramEntryModelArgumentsResolver;

}