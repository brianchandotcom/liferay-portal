/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat730.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.compat730.exception.NoSuchMvccEntryException;
import com.liferay.portal.tools.service.builder.test.compat730.model.MvccEntry;
import com.liferay.portal.tools.service.builder.test.compat730.model.impl.MvccEntryImpl;
import com.liferay.portal.tools.service.builder.test.compat730.model.impl.MvccEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.compat730.service.persistence.MvccEntryPersistence;
import com.liferay.portal.tools.service.builder.test.compat730.service.persistence.MvccEntryUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the mvcc entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MvccEntryPersistenceImpl
	extends BasePersistenceImpl<MvccEntry> implements MvccEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MvccEntryUtil</code> to access the mvcc entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MvccEntryImpl.class.getName();

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
	 * Returns all the mvcc entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching mvcc entries
	 */
	@Override
	public List<MvccEntry> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mvcc entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MvccEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mvcc entries
	 * @param end the upper bound of the range of mvcc entries (not inclusive)
	 * @return the range of matching mvcc entries
	 */
	@Override
	public List<MvccEntry> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the mvcc entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MvccEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mvcc entries
	 * @param end the upper bound of the range of mvcc entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mvcc entries
	 */
	@Override
	public List<MvccEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<MvccEntry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mvcc entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MvccEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mvcc entries
	 * @param end the upper bound of the range of mvcc entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mvcc entries
	 */
	@Override
	public List<MvccEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<MvccEntry> orderByComparator,
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

		List<MvccEntry> list = null;

		if (useFinderCache) {
			list = (List<MvccEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MvccEntry mvccEntry : list) {
					if (companyId != mvccEntry.getCompanyId()) {
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

			sb.append(_SQL_SELECT_MVCCENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MvccEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<MvccEntry>)QueryUtil.list(
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
	 * Returns the first mvcc entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mvcc entry
	 * @throws NoSuchMvccEntryException if a matching mvcc entry could not be found
	 */
	@Override
	public MvccEntry findByCompanyId_First(
			long companyId, OrderByComparator<MvccEntry> orderByComparator)
		throws NoSuchMvccEntryException {

		MvccEntry mvccEntry = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (mvccEntry != null) {
			return mvccEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchMvccEntryException(sb.toString());
	}

	/**
	 * Returns the first mvcc entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mvcc entry, or <code>null</code> if a matching mvcc entry could not be found
	 */
	@Override
	public MvccEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<MvccEntry> orderByComparator) {

		List<MvccEntry> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last mvcc entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mvcc entry
	 * @throws NoSuchMvccEntryException if a matching mvcc entry could not be found
	 */
	@Override
	public MvccEntry findByCompanyId_Last(
			long companyId, OrderByComparator<MvccEntry> orderByComparator)
		throws NoSuchMvccEntryException {

		MvccEntry mvccEntry = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (mvccEntry != null) {
			return mvccEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchMvccEntryException(sb.toString());
	}

	/**
	 * Returns the last mvcc entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mvcc entry, or <code>null</code> if a matching mvcc entry could not be found
	 */
	@Override
	public MvccEntry fetchByCompanyId_Last(
		long companyId, OrderByComparator<MvccEntry> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<MvccEntry> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the mvcc entries before and after the current mvcc entry in the ordered set where companyId = &#63;.
	 *
	 * @param mvccEntryId the primary key of the current mvcc entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mvcc entry
	 * @throws NoSuchMvccEntryException if a mvcc entry with the primary key could not be found
	 */
	@Override
	public MvccEntry[] findByCompanyId_PrevAndNext(
			long mvccEntryId, long companyId,
			OrderByComparator<MvccEntry> orderByComparator)
		throws NoSuchMvccEntryException {

		MvccEntry mvccEntry = findByPrimaryKey(mvccEntryId);

		Session session = null;

		try {
			session = openSession();

			MvccEntry[] array = new MvccEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, mvccEntry, companyId, orderByComparator, true);

			array[1] = mvccEntry;

			array[2] = getByCompanyId_PrevAndNext(
				session, mvccEntry, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MvccEntry getByCompanyId_PrevAndNext(
		Session session, MvccEntry mvccEntry, long companyId,
		OrderByComparator<MvccEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MVCCENTRY_WHERE);

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
			sb.append(MvccEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mvccEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MvccEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the mvcc entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (MvccEntry mvccEntry :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mvccEntry);
		}
	}

	/**
	 * Returns the number of mvcc entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching mvcc entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MVCCENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"mvccEntry.companyId = ?";

	private FinderPath _finderPathFetchByC_N;
	private FinderPath _finderPathCountByC_N;

	/**
	 * Returns the mvcc entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchMvccEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching mvcc entry
	 * @throws NoSuchMvccEntryException if a matching mvcc entry could not be found
	 */
	@Override
	public MvccEntry findByC_N(long companyId, String name)
		throws NoSuchMvccEntryException {

		MvccEntry mvccEntry = fetchByC_N(companyId, name);

		if (mvccEntry == null) {
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

			throw new NoSuchMvccEntryException(sb.toString());
		}

		return mvccEntry;
	}

	/**
	 * Returns the mvcc entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching mvcc entry, or <code>null</code> if a matching mvcc entry could not be found
	 */
	@Override
	public MvccEntry fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the mvcc entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mvcc entry, or <code>null</code> if a matching mvcc entry could not be found
	 */
	@Override
	public MvccEntry fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_N, finderArgs, this);
		}

		if (result instanceof MvccEntry) {
			MvccEntry mvccEntry = (MvccEntry)result;

			if ((companyId != mvccEntry.getCompanyId()) ||
				!Objects.equals(name, mvccEntry.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_MVCCENTRY_WHERE);

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

				List<MvccEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_N, finderArgs, list);
					}
				}
				else {
					MvccEntry mvccEntry = list.get(0);

					result = mvccEntry;

					cacheResult(mvccEntry);
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
			return (MvccEntry)result;
		}
	}

	/**
	 * Removes the mvcc entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the mvcc entry that was removed
	 */
	@Override
	public MvccEntry removeByC_N(long companyId, String name)
		throws NoSuchMvccEntryException {

		MvccEntry mvccEntry = findByC_N(companyId, name);

		return remove(mvccEntry);
	}

	/**
	 * Returns the number of mvcc entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching mvcc entries
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_N;

		Object[] finderArgs = new Object[] {companyId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MVCCENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 =
		"mvccEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_NAME_2 =
		"mvccEntry.name = ?";

	private static final String _FINDER_COLUMN_C_N_NAME_3 =
		"(mvccEntry.name IS NULL OR mvccEntry.name = '')";

	public MvccEntryPersistenceImpl() {
		setModelClass(MvccEntry.class);

		setModelImplClass(MvccEntryImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the mvcc entry in the entity cache if it is enabled.
	 *
	 * @param mvccEntry the mvcc entry
	 */
	@Override
	public void cacheResult(MvccEntry mvccEntry) {
		entityCache.putResult(
			MvccEntryImpl.class, mvccEntry.getPrimaryKey(), mvccEntry);

		finderCache.putResult(
			_finderPathFetchByC_N,
			new Object[] {mvccEntry.getCompanyId(), mvccEntry.getName()},
			mvccEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the mvcc entries in the entity cache if it is enabled.
	 *
	 * @param mvccEntries the mvcc entries
	 */
	@Override
	public void cacheResult(List<MvccEntry> mvccEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (mvccEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (MvccEntry mvccEntry : mvccEntries) {
			if (entityCache.getResult(
					MvccEntryImpl.class, mvccEntry.getPrimaryKey()) == null) {

				cacheResult(mvccEntry);
			}
		}
	}

	/**
	 * Clears the cache for all mvcc entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MvccEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the mvcc entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MvccEntry mvccEntry) {
		entityCache.removeResult(MvccEntryImpl.class, mvccEntry);
	}

	@Override
	public void clearCache(List<MvccEntry> mvccEntries) {
		for (MvccEntry mvccEntry : mvccEntries) {
			entityCache.removeResult(MvccEntryImpl.class, mvccEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(MvccEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		MvccEntryModelImpl mvccEntryModelImpl) {

		Object[] args = new Object[] {
			mvccEntryModelImpl.getCompanyId(), mvccEntryModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByC_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_N, args, mvccEntryModelImpl, false);
	}

	/**
	 * Creates a new mvcc entry with the primary key. Does not add the mvcc entry to the database.
	 *
	 * @param mvccEntryId the primary key for the new mvcc entry
	 * @return the new mvcc entry
	 */
	@Override
	public MvccEntry create(long mvccEntryId) {
		MvccEntry mvccEntry = new MvccEntryImpl();

		mvccEntry.setNew(true);
		mvccEntry.setPrimaryKey(mvccEntryId);

		mvccEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return mvccEntry;
	}

	/**
	 * Removes the mvcc entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mvccEntryId the primary key of the mvcc entry
	 * @return the mvcc entry that was removed
	 * @throws NoSuchMvccEntryException if a mvcc entry with the primary key could not be found
	 */
	@Override
	public MvccEntry remove(long mvccEntryId) throws NoSuchMvccEntryException {
		return remove((Serializable)mvccEntryId);
	}

	/**
	 * Removes the mvcc entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the mvcc entry
	 * @return the mvcc entry that was removed
	 * @throws NoSuchMvccEntryException if a mvcc entry with the primary key could not be found
	 */
	@Override
	public MvccEntry remove(Serializable primaryKey)
		throws NoSuchMvccEntryException {

		Session session = null;

		try {
			session = openSession();

			MvccEntry mvccEntry = (MvccEntry)session.get(
				MvccEntryImpl.class, primaryKey);

			if (mvccEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMvccEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(mvccEntry);
		}
		catch (NoSuchMvccEntryException noSuchEntityException) {
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
	protected MvccEntry removeImpl(MvccEntry mvccEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mvccEntry)) {
				mvccEntry = (MvccEntry)session.get(
					MvccEntryImpl.class, mvccEntry.getPrimaryKeyObj());
			}

			if (mvccEntry != null) {
				session.delete(mvccEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mvccEntry != null) {
			clearCache(mvccEntry);
		}

		return mvccEntry;
	}

	@Override
	public MvccEntry updateImpl(MvccEntry mvccEntry) {
		boolean isNew = mvccEntry.isNew();

		if (!(mvccEntry instanceof MvccEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(mvccEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(mvccEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in mvccEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MvccEntry implementation " +
					mvccEntry.getClass());
		}

		MvccEntryModelImpl mvccEntryModelImpl = (MvccEntryModelImpl)mvccEntry;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(mvccEntry);
			}
			else {
				mvccEntry = (MvccEntry)session.merge(mvccEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			MvccEntryImpl.class, mvccEntryModelImpl, false, true);

		cacheUniqueFindersCache(mvccEntryModelImpl);

		if (isNew) {
			mvccEntry.setNew(false);
		}

		mvccEntry.resetOriginalValues();

		return mvccEntry;
	}

	/**
	 * Returns the mvcc entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the mvcc entry
	 * @return the mvcc entry
	 * @throws NoSuchMvccEntryException if a mvcc entry with the primary key could not be found
	 */
	@Override
	public MvccEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMvccEntryException {

		MvccEntry mvccEntry = fetchByPrimaryKey(primaryKey);

		if (mvccEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMvccEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return mvccEntry;
	}

	/**
	 * Returns the mvcc entry with the primary key or throws a <code>NoSuchMvccEntryException</code> if it could not be found.
	 *
	 * @param mvccEntryId the primary key of the mvcc entry
	 * @return the mvcc entry
	 * @throws NoSuchMvccEntryException if a mvcc entry with the primary key could not be found
	 */
	@Override
	public MvccEntry findByPrimaryKey(long mvccEntryId)
		throws NoSuchMvccEntryException {

		return findByPrimaryKey((Serializable)mvccEntryId);
	}

	/**
	 * Returns the mvcc entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mvccEntryId the primary key of the mvcc entry
	 * @return the mvcc entry, or <code>null</code> if a mvcc entry with the primary key could not be found
	 */
	@Override
	public MvccEntry fetchByPrimaryKey(long mvccEntryId) {
		return fetchByPrimaryKey((Serializable)mvccEntryId);
	}

	/**
	 * Returns all the mvcc entries.
	 *
	 * @return the mvcc entries
	 */
	@Override
	public List<MvccEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mvcc entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MvccEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mvcc entries
	 * @param end the upper bound of the range of mvcc entries (not inclusive)
	 * @return the range of mvcc entries
	 */
	@Override
	public List<MvccEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the mvcc entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MvccEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mvcc entries
	 * @param end the upper bound of the range of mvcc entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mvcc entries
	 */
	@Override
	public List<MvccEntry> findAll(
		int start, int end, OrderByComparator<MvccEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mvcc entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MvccEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mvcc entries
	 * @param end the upper bound of the range of mvcc entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mvcc entries
	 */
	@Override
	public List<MvccEntry> findAll(
		int start, int end, OrderByComparator<MvccEntry> orderByComparator,
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

		List<MvccEntry> list = null;

		if (useFinderCache) {
			list = (List<MvccEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MVCCENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MVCCENTRY;

				sql = sql.concat(MvccEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<MvccEntry>)QueryUtil.list(
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
	 * Removes all the mvcc entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MvccEntry mvccEntry : findAll()) {
			remove(mvccEntry);
		}
	}

	/**
	 * Returns the number of mvcc entries.
	 *
	 * @return the number of mvcc entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_MVCCENTRY);

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
		return "mvccEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MVCCENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return MvccEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the mvcc entry persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(MvccEntryPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class, new MvccEntryModelArgumentsResolver(),
			MapUtil.singletonDictionary(
				"model.class.name", MvccEntry.class.getName()));

		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByCompanyId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathFetchByC_N = _createFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, true);

		_finderPathCountByC_N = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, false);

		MvccEntryUtil.setPersistence(this);
	}

	public void destroy() {
		MvccEntryUtil.setPersistence(null);

		entityCache.removeCache(MvccEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();

		for (ServiceRegistration<FinderPath> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_MVCCENTRY =
		"SELECT mvccEntry FROM MvccEntry mvccEntry";

	private static final String _SQL_SELECT_MVCCENTRY_WHERE =
		"SELECT mvccEntry FROM MvccEntry mvccEntry WHERE ";

	private static final String _SQL_COUNT_MVCCENTRY =
		"SELECT COUNT(mvccEntry) FROM MvccEntry mvccEntry";

	private static final String _SQL_COUNT_MVCCENTRY_WHERE =
		"SELECT COUNT(mvccEntry) FROM MvccEntry mvccEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "mvccEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No MvccEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No MvccEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MvccEntryPersistenceImpl.class);

	private FinderPath _createFinderPath(
		String cacheName, String methodName, String[] params,
		String[] columnNames, boolean baseModelResult) {

		FinderPath finderPath = new FinderPath(
			cacheName, methodName, params, columnNames, baseModelResult);

		if (!cacheName.equals(FINDER_CLASS_NAME_LIST_WITH_PAGINATION)) {
			_serviceRegistrations.add(
				_bundleContext.registerService(
					FinderPath.class, finderPath,
					MapUtil.singletonDictionary("cache.name", cacheName)));
		}

		return finderPath;
	}

	private Set<ServiceRegistration<FinderPath>> _serviceRegistrations =
		new HashSet<>();
	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class MvccEntryModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return new Object[0];
				}

				return null;
			}

			MvccEntryModelImpl mvccEntryModelImpl =
				(MvccEntryModelImpl)baseModel;

			long columnBitmask = mvccEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(mvccEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						mvccEntryModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(mvccEntryModelImpl, columnNames, original);
			}

			return null;
		}

		private static Object[] _getValue(
			MvccEntryModelImpl mvccEntryModelImpl, String[] columnNames,
			boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] = mvccEntryModelImpl.getColumnOriginalValue(
						columnName);
				}
				else {
					arguments[i] = mvccEntryModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

	}

}