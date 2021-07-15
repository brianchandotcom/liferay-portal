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

package com.liferay.search.tuning.rankings.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
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
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.search.tuning.rankings.exception.NoSuchSTRankingsEntryException;
import com.liferay.search.tuning.rankings.model.STRankingsEntry;
import com.liferay.search.tuning.rankings.model.STRankingsEntryTable;
import com.liferay.search.tuning.rankings.model.impl.STRankingsEntryImpl;
import com.liferay.search.tuning.rankings.model.impl.STRankingsEntryModelImpl;
import com.liferay.search.tuning.rankings.service.persistence.STRankingsEntryPersistence;
import com.liferay.search.tuning.rankings.service.persistence.impl.constants.SearchTuningPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the st rankings entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Bryan Engler
 * @generated
 */
@Component(service = {STRankingsEntryPersistence.class, BasePersistence.class})
public class STRankingsEntryPersistenceImpl
	extends BasePersistenceImpl<STRankingsEntry>
	implements STRankingsEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>STRankingsEntryUtil</code> to access the st rankings entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		STRankingsEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindBycompanyId;
	private FinderPath _finderPathWithoutPaginationFindBycompanyId;
	private FinderPath _finderPathCountBycompanyId;

	/**
	 * Returns all the st rankings entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching st rankings entries
	 */
	@Override
	public List<STRankingsEntry> findBycompanyId(long companyId) {
		return findBycompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the st rankings entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @return the range of matching st rankings entries
	 */
	@Override
	public List<STRankingsEntry> findBycompanyId(
		long companyId, int start, int end) {

		return findBycompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the st rankings entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching st rankings entries
	 */
	@Override
	public List<STRankingsEntry> findBycompanyId(
		long companyId, int start, int end,
		OrderByComparator<STRankingsEntry> orderByComparator) {

		return findBycompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the st rankings entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching st rankings entries
	 */
	@Override
	public List<STRankingsEntry> findBycompanyId(
		long companyId, int start, int end,
		OrderByComparator<STRankingsEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindBycompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindBycompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<STRankingsEntry> list = null;

		if (useFinderCache) {
			list = (List<STRankingsEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (STRankingsEntry stRankingsEntry : list) {
					if (companyId != stRankingsEntry.getCompanyId()) {
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

			sb.append(_SQL_SELECT_STRANKINGSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(STRankingsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<STRankingsEntry>)QueryUtil.list(
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
	 * Returns the first st rankings entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching st rankings entry
	 * @throws NoSuchSTRankingsEntryException if a matching st rankings entry could not be found
	 */
	@Override
	public STRankingsEntry findBycompanyId_First(
			long companyId,
			OrderByComparator<STRankingsEntry> orderByComparator)
		throws NoSuchSTRankingsEntryException {

		STRankingsEntry stRankingsEntry = fetchBycompanyId_First(
			companyId, orderByComparator);

		if (stRankingsEntry != null) {
			return stRankingsEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSTRankingsEntryException(sb.toString());
	}

	/**
	 * Returns the first st rankings entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching st rankings entry, or <code>null</code> if a matching st rankings entry could not be found
	 */
	@Override
	public STRankingsEntry fetchBycompanyId_First(
		long companyId, OrderByComparator<STRankingsEntry> orderByComparator) {

		List<STRankingsEntry> list = findBycompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last st rankings entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching st rankings entry
	 * @throws NoSuchSTRankingsEntryException if a matching st rankings entry could not be found
	 */
	@Override
	public STRankingsEntry findBycompanyId_Last(
			long companyId,
			OrderByComparator<STRankingsEntry> orderByComparator)
		throws NoSuchSTRankingsEntryException {

		STRankingsEntry stRankingsEntry = fetchBycompanyId_Last(
			companyId, orderByComparator);

		if (stRankingsEntry != null) {
			return stRankingsEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSTRankingsEntryException(sb.toString());
	}

	/**
	 * Returns the last st rankings entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching st rankings entry, or <code>null</code> if a matching st rankings entry could not be found
	 */
	@Override
	public STRankingsEntry fetchBycompanyId_Last(
		long companyId, OrderByComparator<STRankingsEntry> orderByComparator) {

		int count = countBycompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<STRankingsEntry> list = findBycompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the st rankings entries before and after the current st rankings entry in the ordered set where companyId = &#63;.
	 *
	 * @param STRankingsEntryId the primary key of the current st rankings entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next st rankings entry
	 * @throws NoSuchSTRankingsEntryException if a st rankings entry with the primary key could not be found
	 */
	@Override
	public STRankingsEntry[] findBycompanyId_PrevAndNext(
			long STRankingsEntryId, long companyId,
			OrderByComparator<STRankingsEntry> orderByComparator)
		throws NoSuchSTRankingsEntryException {

		STRankingsEntry stRankingsEntry = findByPrimaryKey(STRankingsEntryId);

		Session session = null;

		try {
			session = openSession();

			STRankingsEntry[] array = new STRankingsEntryImpl[3];

			array[0] = getBycompanyId_PrevAndNext(
				session, stRankingsEntry, companyId, orderByComparator, true);

			array[1] = stRankingsEntry;

			array[2] = getBycompanyId_PrevAndNext(
				session, stRankingsEntry, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected STRankingsEntry getBycompanyId_PrevAndNext(
		Session session, STRankingsEntry stRankingsEntry, long companyId,
		OrderByComparator<STRankingsEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_STRANKINGSENTRY_WHERE);

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
			sb.append(STRankingsEntryModelImpl.ORDER_BY_JPQL);
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
						stRankingsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<STRankingsEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the st rankings entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeBycompanyId(long companyId) {
		for (STRankingsEntry stRankingsEntry :
				findBycompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(stRankingsEntry);
		}
	}

	/**
	 * Returns the number of st rankings entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching st rankings entries
	 */
	@Override
	public int countBycompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountBycompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_STRANKINGSENTRY_WHERE);

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
		"stRankingsEntry.companyId = ?";

	public STRankingsEntryPersistenceImpl() {
		setModelClass(STRankingsEntry.class);

		setModelImplClass(STRankingsEntryImpl.class);
		setModelPKClass(long.class);

		setTable(STRankingsEntryTable.INSTANCE);
	}

	/**
	 * Caches the st rankings entry in the entity cache if it is enabled.
	 *
	 * @param stRankingsEntry the st rankings entry
	 */
	@Override
	public void cacheResult(STRankingsEntry stRankingsEntry) {
		entityCache.putResult(
			STRankingsEntryImpl.class, stRankingsEntry.getPrimaryKey(),
			stRankingsEntry);
	}

	/**
	 * Caches the st rankings entries in the entity cache if it is enabled.
	 *
	 * @param stRankingsEntries the st rankings entries
	 */
	@Override
	public void cacheResult(List<STRankingsEntry> stRankingsEntries) {
		for (STRankingsEntry stRankingsEntry : stRankingsEntries) {
			if (entityCache.getResult(
					STRankingsEntryImpl.class,
					stRankingsEntry.getPrimaryKey()) == null) {

				cacheResult(stRankingsEntry);
			}
		}
	}

	/**
	 * Clears the cache for all st rankings entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(STRankingsEntryImpl.class);

		finderCache.clearCache(STRankingsEntryImpl.class);
	}

	/**
	 * Clears the cache for the st rankings entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(STRankingsEntry stRankingsEntry) {
		entityCache.removeResult(STRankingsEntryImpl.class, stRankingsEntry);
	}

	@Override
	public void clearCache(List<STRankingsEntry> stRankingsEntries) {
		for (STRankingsEntry stRankingsEntry : stRankingsEntries) {
			entityCache.removeResult(
				STRankingsEntryImpl.class, stRankingsEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(STRankingsEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(STRankingsEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new st rankings entry with the primary key. Does not add the st rankings entry to the database.
	 *
	 * @param STRankingsEntryId the primary key for the new st rankings entry
	 * @return the new st rankings entry
	 */
	@Override
	public STRankingsEntry create(long STRankingsEntryId) {
		STRankingsEntry stRankingsEntry = new STRankingsEntryImpl();

		stRankingsEntry.setNew(true);
		stRankingsEntry.setPrimaryKey(STRankingsEntryId);

		stRankingsEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return stRankingsEntry;
	}

	/**
	 * Removes the st rankings entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param STRankingsEntryId the primary key of the st rankings entry
	 * @return the st rankings entry that was removed
	 * @throws NoSuchSTRankingsEntryException if a st rankings entry with the primary key could not be found
	 */
	@Override
	public STRankingsEntry remove(long STRankingsEntryId)
		throws NoSuchSTRankingsEntryException {

		return remove((Serializable)STRankingsEntryId);
	}

	/**
	 * Removes the st rankings entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the st rankings entry
	 * @return the st rankings entry that was removed
	 * @throws NoSuchSTRankingsEntryException if a st rankings entry with the primary key could not be found
	 */
	@Override
	public STRankingsEntry remove(Serializable primaryKey)
		throws NoSuchSTRankingsEntryException {

		Session session = null;

		try {
			session = openSession();

			STRankingsEntry stRankingsEntry = (STRankingsEntry)session.get(
				STRankingsEntryImpl.class, primaryKey);

			if (stRankingsEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSTRankingsEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(stRankingsEntry);
		}
		catch (NoSuchSTRankingsEntryException noSuchEntityException) {
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
	protected STRankingsEntry removeImpl(STRankingsEntry stRankingsEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(stRankingsEntry)) {
				stRankingsEntry = (STRankingsEntry)session.get(
					STRankingsEntryImpl.class,
					stRankingsEntry.getPrimaryKeyObj());
			}

			if (stRankingsEntry != null) {
				session.delete(stRankingsEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (stRankingsEntry != null) {
			clearCache(stRankingsEntry);
		}

		return stRankingsEntry;
	}

	@Override
	public STRankingsEntry updateImpl(STRankingsEntry stRankingsEntry) {
		boolean isNew = stRankingsEntry.isNew();

		if (!(stRankingsEntry instanceof STRankingsEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(stRankingsEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					stRankingsEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in stRankingsEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom STRankingsEntry implementation " +
					stRankingsEntry.getClass());
		}

		STRankingsEntryModelImpl stRankingsEntryModelImpl =
			(STRankingsEntryModelImpl)stRankingsEntry;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(stRankingsEntry);
			}
			else {
				stRankingsEntry = (STRankingsEntry)session.merge(
					stRankingsEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			STRankingsEntryImpl.class, stRankingsEntryModelImpl, false, true);

		if (isNew) {
			stRankingsEntry.setNew(false);
		}

		stRankingsEntry.resetOriginalValues();

		return stRankingsEntry;
	}

	/**
	 * Returns the st rankings entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the st rankings entry
	 * @return the st rankings entry
	 * @throws NoSuchSTRankingsEntryException if a st rankings entry with the primary key could not be found
	 */
	@Override
	public STRankingsEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSTRankingsEntryException {

		STRankingsEntry stRankingsEntry = fetchByPrimaryKey(primaryKey);

		if (stRankingsEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSTRankingsEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return stRankingsEntry;
	}

	/**
	 * Returns the st rankings entry with the primary key or throws a <code>NoSuchSTRankingsEntryException</code> if it could not be found.
	 *
	 * @param STRankingsEntryId the primary key of the st rankings entry
	 * @return the st rankings entry
	 * @throws NoSuchSTRankingsEntryException if a st rankings entry with the primary key could not be found
	 */
	@Override
	public STRankingsEntry findByPrimaryKey(long STRankingsEntryId)
		throws NoSuchSTRankingsEntryException {

		return findByPrimaryKey((Serializable)STRankingsEntryId);
	}

	/**
	 * Returns the st rankings entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param STRankingsEntryId the primary key of the st rankings entry
	 * @return the st rankings entry, or <code>null</code> if a st rankings entry with the primary key could not be found
	 */
	@Override
	public STRankingsEntry fetchByPrimaryKey(long STRankingsEntryId) {
		return fetchByPrimaryKey((Serializable)STRankingsEntryId);
	}

	/**
	 * Returns all the st rankings entries.
	 *
	 * @return the st rankings entries
	 */
	@Override
	public List<STRankingsEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the st rankings entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @return the range of st rankings entries
	 */
	@Override
	public List<STRankingsEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the st rankings entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of st rankings entries
	 */
	@Override
	public List<STRankingsEntry> findAll(
		int start, int end,
		OrderByComparator<STRankingsEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the st rankings entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of st rankings entries
	 */
	@Override
	public List<STRankingsEntry> findAll(
		int start, int end,
		OrderByComparator<STRankingsEntry> orderByComparator,
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

		List<STRankingsEntry> list = null;

		if (useFinderCache) {
			list = (List<STRankingsEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_STRANKINGSENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_STRANKINGSENTRY;

				sql = sql.concat(STRankingsEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<STRankingsEntry>)QueryUtil.list(
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
	 * Removes all the st rankings entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (STRankingsEntry stRankingsEntry : findAll()) {
			remove(stRankingsEntry);
		}
	}

	/**
	 * Returns the number of st rankings entries.
	 *
	 * @return the number of st rankings entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_STRANKINGSENTRY);

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
		return "STRankingsEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_STRANKINGSENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return STRankingsEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the st rankings entry persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new STRankingsEntryModelArgumentsResolver(),
			new HashMapDictionary<>());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindBycompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBycompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindBycompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBycompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountBycompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBycompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(STRankingsEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	@Override
	@Reference(
		target = SearchTuningPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SearchTuningPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SearchTuningPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private BundleContext _bundleContext;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_STRANKINGSENTRY =
		"SELECT stRankingsEntry FROM STRankingsEntry stRankingsEntry";

	private static final String _SQL_SELECT_STRANKINGSENTRY_WHERE =
		"SELECT stRankingsEntry FROM STRankingsEntry stRankingsEntry WHERE ";

	private static final String _SQL_COUNT_STRANKINGSENTRY =
		"SELECT COUNT(stRankingsEntry) FROM STRankingsEntry stRankingsEntry";

	private static final String _SQL_COUNT_STRANKINGSENTRY_WHERE =
		"SELECT COUNT(stRankingsEntry) FROM STRankingsEntry stRankingsEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "stRankingsEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No STRankingsEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No STRankingsEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		STRankingsEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class STRankingsEntryModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			STRankingsEntryModelImpl stRankingsEntryModelImpl =
				(STRankingsEntryModelImpl)baseModel;

			long columnBitmask = stRankingsEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					stRankingsEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						stRankingsEntryModelImpl.getColumnBitmask(columnName);
				}

				if (finderPath.isBaseModelResult() &&
					(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION ==
						finderPath.getCacheName())) {

					finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					stRankingsEntryModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return STRankingsEntryImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return STRankingsEntryTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			STRankingsEntryModelImpl stRankingsEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						stRankingsEntryModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = stRankingsEntryModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

		private static final long _ORDER_BY_COLUMNS_BITMASK;

		static {
			long orderByColumnsBitmask = 0;

			_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
		}

	}

}