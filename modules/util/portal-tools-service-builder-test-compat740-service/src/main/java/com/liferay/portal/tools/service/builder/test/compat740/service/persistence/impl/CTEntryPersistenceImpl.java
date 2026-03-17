/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service.persistence.impl;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.tools.service.builder.test.compat740.exception.NoSuchCTEntryException;
import com.liferay.portal.tools.service.builder.test.compat740.model.CTEntry;
import com.liferay.portal.tools.service.builder.test.compat740.model.CTEntryTable;
import com.liferay.portal.tools.service.builder.test.compat740.model.impl.CTEntryImpl;
import com.liferay.portal.tools.service.builder.test.compat740.model.impl.CTEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.compat740.service.persistence.CTEntryPersistence;
import com.liferay.portal.tools.service.builder.test.compat740.service.persistence.CTEntryUtil;
import com.liferay.portal.tools.service.builder.test.compat740.service.persistence.impl.constants.SBCompat740PersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
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
 * The persistence implementation for the ct entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = CTEntryPersistence.class)
public class CTEntryPersistenceImpl
	extends BasePersistenceImpl<CTEntry> implements CTEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTEntryUtil</code> to access the ct entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTEntryImpl.class.getName();

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
	 * Returns all the ct entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct entries
	 */
	@Override
	public List<CTEntry> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CTEntry.class)) {

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

			List<CTEntry> list = null;

			if (useFinderCache) {
				list = (List<CTEntry>)dummyFinderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CTEntry ctEntry : list) {
						if (companyId != ctEntry.getCompanyId()) {
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

				sb.append(_SQL_SELECT_CTENTRY_WHERE);

				sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CTEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					list = (List<CTEntry>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						dummyFinderCache.putResult(
							finderPath, finderArgs, list);
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
	}

	/**
	 * Returns the first ct entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchCTEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByCompanyId_First(
			long companyId, OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchCTEntryException {

		CTEntry ctEntry = fetchByCompanyId_First(companyId, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCTEntryException(sb.toString());
	}

	/**
	 * Returns the first ct entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<CTEntry> orderByComparator) {

		List<CTEntry> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchCTEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByCompanyId_Last(
			long companyId, OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchCTEntryException {

		CTEntry ctEntry = fetchByCompanyId_Last(companyId, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCTEntryException(sb.toString());
	}

	/**
	 * Returns the last ct entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByCompanyId_Last(
		long companyId, OrderByComparator<CTEntry> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CTEntry> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where companyId = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchCTEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry[] findByCompanyId_PrevAndNext(
			long ctEntryId, long companyId,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchCTEntryException {

		CTEntry ctEntry = findByPrimaryKey(ctEntryId);

		Session session = null;

		try {
			session = openSession();

			CTEntry[] array = new CTEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, ctEntry, companyId, orderByComparator, true);

			array[1] = ctEntry;

			array[2] = getByCompanyId_PrevAndNext(
				session, ctEntry, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTEntry getByCompanyId_PrevAndNext(
		Session session, CTEntry ctEntry, long companyId,
		OrderByComparator<CTEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CTENTRY_WHERE);

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
			sb.append(CTEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CTEntry ctEntry :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CTEntry.class)) {

			FinderPath finderPath = _finderPathCountByCompanyId;

			Object[] finderArgs = new Object[] {companyId};

			Long count = (Long)dummyFinderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_CTENTRY_WHERE);

				sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					count = (Long)query.uniqueResult();

					dummyFinderCache.putResult(finderPath, finderArgs, count);
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
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"ctEntry.companyId = ?";

	private FinderPath _finderPathFetchByC_N;

	/**
	 * Returns the ct entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchCTEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching ct entry
	 * @throws NoSuchCTEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_N(long companyId, String name)
		throws NoSuchCTEntryException {

		CTEntry ctEntry = fetchByC_N(companyId, name);

		if (ctEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCTEntryException(sb.toString());
		}

		return ctEntry;
	}

	/**
	 * Returns the ct entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the ct entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CTEntry.class)) {

			name = Objects.toString(name, "");

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {companyId, name};
			}

			Object result = null;

			if (useFinderCache) {
				result = dummyFinderCache.getResult(
					_finderPathFetchByC_N, finderArgs, this);
			}

			if (result instanceof CTEntry) {
				CTEntry ctEntry = (CTEntry)result;

				if ((companyId != ctEntry.getCompanyId()) ||
					!Objects.equals(name, ctEntry.getName())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_CTENTRY_WHERE);

				sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

				boolean bindName = false;

				if (name.isEmpty()) {
					sb.append(_FINDER_COLUMN_C_N_NAME_3);
				}
				else {
					bindName = true;

					sb.append(_FINDER_COLUMN_C_N_NAME_2);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					if (bindName) {
						queryPos.add(name);
					}

					List<CTEntry> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							dummyFinderCache.putResult(
								_finderPathFetchByC_N, finderArgs, list);
						}
					}
					else {
						CTEntry ctEntry = list.get(0);

						result = ctEntry;

						cacheResult(ctEntry);
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
				return (CTEntry)result;
			}
		}
	}

	/**
	 * Removes the ct entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the ct entry that was removed
	 */
	@Override
	public CTEntry removeByC_N(long companyId, String name)
		throws NoSuchCTEntryException {

		CTEntry ctEntry = findByC_N(companyId, name);

		return remove(ctEntry);
	}

	/**
	 * Returns the number of ct entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		CTEntry ctEntry = fetchByC_N(companyId, name);

		if (ctEntry == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 =
		"ctEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_NAME_2 = "ctEntry.name = ?";

	private static final String _FINDER_COLUMN_C_N_NAME_3 =
		"(ctEntry.name IS NULL OR ctEntry.name = '')";

	public CTEntryPersistenceImpl() {
		setModelClass(CTEntry.class);

		setModelImplClass(CTEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CTEntryTable.INSTANCE);
	}

	/**
	 * Caches the ct entry in the entity cache if it is enabled.
	 *
	 * @param ctEntry the ct entry
	 */
	@Override
	public void cacheResult(CTEntry ctEntry) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctEntry.getCtCollectionId())) {

			dummyEntityCache.putResult(
				CTEntryImpl.class, ctEntry.getPrimaryKey(), ctEntry);

			dummyFinderCache.putResult(
				_finderPathFetchByC_N,
				new Object[] {ctEntry.getCompanyId(), ctEntry.getName()},
				ctEntry);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the ct entries in the entity cache if it is enabled.
	 *
	 * @param ctEntries the ct entries
	 */
	@Override
	public void cacheResult(List<CTEntry> ctEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ctEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CTEntry ctEntry : ctEntries) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						ctEntry.getCtCollectionId())) {

				if (dummyEntityCache.getResult(
						CTEntryImpl.class, ctEntry.getPrimaryKey()) == null) {

					cacheResult(ctEntry);
				}
			}
		}
	}

	/**
	 * Clears the cache for all ct entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		dummyEntityCache.clearCache(CTEntryImpl.class);

		dummyFinderCache.clearCache(CTEntryImpl.class);
	}

	/**
	 * Clears the cache for the ct entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTEntry ctEntry) {
		dummyEntityCache.removeResult(CTEntryImpl.class, ctEntry);
	}

	@Override
	public void clearCache(List<CTEntry> ctEntries) {
		for (CTEntry ctEntry : ctEntries) {
			dummyEntityCache.removeResult(CTEntryImpl.class, ctEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		dummyFinderCache.clearCache(CTEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			dummyEntityCache.removeResult(CTEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(CTEntryModelImpl ctEntryModelImpl) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctEntryModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				ctEntryModelImpl.getCompanyId(), ctEntryModelImpl.getName()
			};

			dummyFinderCache.putResult(
				_finderPathFetchByC_N, args, ctEntryModelImpl);
		}
	}

	/**
	 * Creates a new ct entry with the primary key. Does not add the ct entry to the database.
	 *
	 * @param ctEntryId the primary key for the new ct entry
	 * @return the new ct entry
	 */
	@Override
	public CTEntry create(long ctEntryId) {
		CTEntry ctEntry = new CTEntryImpl();

		ctEntry.setNew(true);
		ctEntry.setPrimaryKey(ctEntryId);

		ctEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ctEntry;
	}

	/**
	 * Removes the ct entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry that was removed
	 * @throws NoSuchCTEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry remove(long ctEntryId) throws NoSuchCTEntryException {
		return remove((Serializable)ctEntryId);
	}

	/**
	 * Removes the ct entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ct entry
	 * @return the ct entry that was removed
	 * @throws NoSuchCTEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry remove(Serializable primaryKey)
		throws NoSuchCTEntryException {

		Session session = null;

		try {
			session = openSession();

			CTEntry ctEntry = (CTEntry)session.get(
				CTEntryImpl.class, primaryKey);

			if (ctEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCTEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ctEntry);
		}
		catch (NoSuchCTEntryException noSuchEntityException) {
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
	protected CTEntry removeImpl(CTEntry ctEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctEntry)) {
				ctEntry = (CTEntry)session.get(
					CTEntryImpl.class, ctEntry.getPrimaryKeyObj());
			}

			if ((ctEntry != null) && ctPersistenceHelper.isRemove(ctEntry)) {
				session.delete(ctEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ctEntry != null) {
			clearCache(ctEntry);
		}

		return ctEntry;
	}

	@Override
	public CTEntry updateImpl(CTEntry ctEntry) {
		boolean isNew = ctEntry.isNew();

		if (!(ctEntry instanceof CTEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(ctEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTEntry implementation " +
					ctEntry.getClass());
		}

		CTEntryModelImpl ctEntryModelImpl = (CTEntryModelImpl)ctEntry;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(ctEntry)) {
				if (!isNew) {
					session.evict(
						CTEntryImpl.class, ctEntry.getPrimaryKeyObj());
				}

				session.save(ctEntry);
			}
			else {
				ctEntry = (CTEntry)session.merge(ctEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		dummyEntityCache.putResult(
			CTEntryImpl.class, ctEntryModelImpl, false, true);

		cacheUniqueFindersCache(ctEntryModelImpl);

		if (isNew) {
			ctEntry.setNew(false);
		}

		ctEntry.resetOriginalValues();

		return ctEntry;
	}

	/**
	 * Returns the ct entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ct entry
	 * @return the ct entry
	 * @throws NoSuchCTEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCTEntryException {

		CTEntry ctEntry = fetchByPrimaryKey(primaryKey);

		if (ctEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCTEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ctEntry;
	}

	/**
	 * Returns the ct entry with the primary key or throws a <code>NoSuchCTEntryException</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry
	 * @throws NoSuchCTEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry findByPrimaryKey(long ctEntryId)
		throws NoSuchCTEntryException {

		return findByPrimaryKey((Serializable)ctEntryId);
	}

	/**
	 * Returns the ct entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ct entry
	 * @return the ct entry, or <code>null</code> if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(CTEntry.class, primaryKey)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		CTEntry ctEntry = (CTEntry)dummyEntityCache.getResult(
			CTEntryImpl.class, primaryKey);

		if (ctEntry != null) {
			return ctEntry;
		}

		Session session = null;

		try {
			session = openSession();

			ctEntry = (CTEntry)session.get(CTEntryImpl.class, primaryKey);

			if (ctEntry != null) {
				cacheResult(ctEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return ctEntry;
	}

	/**
	 * Returns the ct entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry, or <code>null</code> if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry fetchByPrimaryKey(long ctEntryId) {
		return fetchByPrimaryKey((Serializable)ctEntryId);
	}

	@Override
	public Map<Serializable, CTEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(CTEntry.class)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CTEntry> map = new HashMap<Serializable, CTEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CTEntry ctEntry = fetchByPrimaryKey(primaryKey);

			if (ctEntry != null) {
				map.put(primaryKey, ctEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						CTEntry.class, primaryKey)) {

				CTEntry ctEntry = (CTEntry)dummyEntityCache.getResult(
					CTEntryImpl.class, primaryKey);

				if (ctEntry == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, ctEntry);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
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

			for (CTEntry ctEntry : (List<CTEntry>)query.list()) {
				map.put(ctEntry.getPrimaryKeyObj(), ctEntry);

				cacheResult(ctEntry);
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
	 * Returns all the ct entries.
	 *
	 * @return the ct entries
	 */
	@Override
	public List<CTEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of ct entries
	 */
	@Override
	public List<CTEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries
	 */
	@Override
	public List<CTEntry> findAll(
		int start, int end, OrderByComparator<CTEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct entries
	 */
	@Override
	public List<CTEntry> findAll(
		int start, int end, OrderByComparator<CTEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CTEntry.class)) {

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

			List<CTEntry> list = null;

			if (useFinderCache) {
				list = (List<CTEntry>)dummyFinderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_CTENTRY);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_CTENTRY;

					sql = sql.concat(CTEntryModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<CTEntry>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						dummyFinderCache.putResult(
							finderPath, finderArgs, list);
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
	}

	/**
	 * Removes all the ct entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTEntry ctEntry : findAll()) {
			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries.
	 *
	 * @return the number of ct entries
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CTEntry.class)) {

			Long count = (Long)dummyFinderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(_SQL_COUNT_CTENTRY);

					count = (Long)query.uniqueResult();

					dummyFinderCache.putResult(
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
	}

	@Override
	protected EntityCache getEntityCache() {
		return dummyEntityCache;
	}

	@Override
	protected String getPKDBName() {
		return "ctEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTENTRY;
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
		return CTEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CTEntry";
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
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("companyId");
		ctMergeColumnNames.add("name");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("ctEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"companyId", "name"});
	}

	/**
	 * Initializes the ct entry persistence.
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

		_finderPathFetchByC_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, true);

		CTEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CTEntryUtil.setPersistence(null);

		dummyEntityCache.removeCache(CTEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = SBCompat740PersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SBCompat740PersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SBCompat740PersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	private static final String _SQL_SELECT_CTENTRY =
		"SELECT ctEntry FROM CTEntry ctEntry";

	private static final String _SQL_SELECT_CTENTRY_WHERE =
		"SELECT ctEntry FROM CTEntry ctEntry WHERE ";

	private static final String _SQL_COUNT_CTENTRY =
		"SELECT COUNT(ctEntry) FROM CTEntry ctEntry";

	private static final String _SQL_COUNT_CTENTRY_WHERE =
		"SELECT COUNT(ctEntry) FROM CTEntry ctEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ctEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CTEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return dummyFinderCache;
	}

}
// SB-Hash:-1180395990