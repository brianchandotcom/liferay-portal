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

package com.liferay.search.tuning.synonyms.service.persistence.impl;

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
import com.liferay.search.tuning.synonyms.exception.NoSuchSTSynonymsEntryException;
import com.liferay.search.tuning.synonyms.model.STSynonymsEntry;
import com.liferay.search.tuning.synonyms.model.STSynonymsEntryTable;
import com.liferay.search.tuning.synonyms.model.impl.STSynonymsEntryImpl;
import com.liferay.search.tuning.synonyms.model.impl.STSynonymsEntryModelImpl;
import com.liferay.search.tuning.synonyms.service.persistence.STSynonymsEntryPersistence;
import com.liferay.search.tuning.synonyms.service.persistence.impl.constants.SearchTuningPersistenceConstants;

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
 * The persistence implementation for the st synonyms entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Bryan Engler
 * @generated
 */
@Component(service = {STSynonymsEntryPersistence.class, BasePersistence.class})
public class STSynonymsEntryPersistenceImpl
	extends BasePersistenceImpl<STSynonymsEntry>
	implements STSynonymsEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>STSynonymsEntryUtil</code> to access the st synonyms entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		STSynonymsEntryImpl.class.getName();

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
	 * Returns all the st synonyms entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching st synonyms entries
	 */
	@Override
	public List<STSynonymsEntry> findBycompanyId(long companyId) {
		return findBycompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the st synonyms entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @return the range of matching st synonyms entries
	 */
	@Override
	public List<STSynonymsEntry> findBycompanyId(
		long companyId, int start, int end) {

		return findBycompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the st synonyms entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching st synonyms entries
	 */
	@Override
	public List<STSynonymsEntry> findBycompanyId(
		long companyId, int start, int end,
		OrderByComparator<STSynonymsEntry> orderByComparator) {

		return findBycompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the st synonyms entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching st synonyms entries
	 */
	@Override
	public List<STSynonymsEntry> findBycompanyId(
		long companyId, int start, int end,
		OrderByComparator<STSynonymsEntry> orderByComparator,
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

		List<STSynonymsEntry> list = null;

		if (useFinderCache) {
			list = (List<STSynonymsEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (STSynonymsEntry stSynonymsEntry : list) {
					if (companyId != stSynonymsEntry.getCompanyId()) {
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

			sb.append(_SQL_SELECT_STSYNONYMSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(STSynonymsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<STSynonymsEntry>)QueryUtil.list(
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
	 * Returns the first st synonyms entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching st synonyms entry
	 * @throws NoSuchSTSynonymsEntryException if a matching st synonyms entry could not be found
	 */
	@Override
	public STSynonymsEntry findBycompanyId_First(
			long companyId,
			OrderByComparator<STSynonymsEntry> orderByComparator)
		throws NoSuchSTSynonymsEntryException {

		STSynonymsEntry stSynonymsEntry = fetchBycompanyId_First(
			companyId, orderByComparator);

		if (stSynonymsEntry != null) {
			return stSynonymsEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSTSynonymsEntryException(sb.toString());
	}

	/**
	 * Returns the first st synonyms entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching st synonyms entry, or <code>null</code> if a matching st synonyms entry could not be found
	 */
	@Override
	public STSynonymsEntry fetchBycompanyId_First(
		long companyId, OrderByComparator<STSynonymsEntry> orderByComparator) {

		List<STSynonymsEntry> list = findBycompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last st synonyms entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching st synonyms entry
	 * @throws NoSuchSTSynonymsEntryException if a matching st synonyms entry could not be found
	 */
	@Override
	public STSynonymsEntry findBycompanyId_Last(
			long companyId,
			OrderByComparator<STSynonymsEntry> orderByComparator)
		throws NoSuchSTSynonymsEntryException {

		STSynonymsEntry stSynonymsEntry = fetchBycompanyId_Last(
			companyId, orderByComparator);

		if (stSynonymsEntry != null) {
			return stSynonymsEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSTSynonymsEntryException(sb.toString());
	}

	/**
	 * Returns the last st synonyms entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching st synonyms entry, or <code>null</code> if a matching st synonyms entry could not be found
	 */
	@Override
	public STSynonymsEntry fetchBycompanyId_Last(
		long companyId, OrderByComparator<STSynonymsEntry> orderByComparator) {

		int count = countBycompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<STSynonymsEntry> list = findBycompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the st synonyms entries before and after the current st synonyms entry in the ordered set where companyId = &#63;.
	 *
	 * @param STSynonymsEntryId the primary key of the current st synonyms entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next st synonyms entry
	 * @throws NoSuchSTSynonymsEntryException if a st synonyms entry with the primary key could not be found
	 */
	@Override
	public STSynonymsEntry[] findBycompanyId_PrevAndNext(
			long STSynonymsEntryId, long companyId,
			OrderByComparator<STSynonymsEntry> orderByComparator)
		throws NoSuchSTSynonymsEntryException {

		STSynonymsEntry stSynonymsEntry = findByPrimaryKey(STSynonymsEntryId);

		Session session = null;

		try {
			session = openSession();

			STSynonymsEntry[] array = new STSynonymsEntryImpl[3];

			array[0] = getBycompanyId_PrevAndNext(
				session, stSynonymsEntry, companyId, orderByComparator, true);

			array[1] = stSynonymsEntry;

			array[2] = getBycompanyId_PrevAndNext(
				session, stSynonymsEntry, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected STSynonymsEntry getBycompanyId_PrevAndNext(
		Session session, STSynonymsEntry stSynonymsEntry, long companyId,
		OrderByComparator<STSynonymsEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_STSYNONYMSENTRY_WHERE);

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
			sb.append(STSynonymsEntryModelImpl.ORDER_BY_JPQL);
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
						stSynonymsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<STSynonymsEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the st synonyms entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeBycompanyId(long companyId) {
		for (STSynonymsEntry stSynonymsEntry :
				findBycompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(stSynonymsEntry);
		}
	}

	/**
	 * Returns the number of st synonyms entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching st synonyms entries
	 */
	@Override
	public int countBycompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountBycompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_STSYNONYMSENTRY_WHERE);

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
		"stSynonymsEntry.companyId = ?";

	public STSynonymsEntryPersistenceImpl() {
		setModelClass(STSynonymsEntry.class);

		setModelImplClass(STSynonymsEntryImpl.class);
		setModelPKClass(long.class);

		setTable(STSynonymsEntryTable.INSTANCE);
	}

	/**
	 * Caches the st synonyms entry in the entity cache if it is enabled.
	 *
	 * @param stSynonymsEntry the st synonyms entry
	 */
	@Override
	public void cacheResult(STSynonymsEntry stSynonymsEntry) {
		entityCache.putResult(
			STSynonymsEntryImpl.class, stSynonymsEntry.getPrimaryKey(),
			stSynonymsEntry);
	}

	/**
	 * Caches the st synonyms entries in the entity cache if it is enabled.
	 *
	 * @param stSynonymsEntries the st synonyms entries
	 */
	@Override
	public void cacheResult(List<STSynonymsEntry> stSynonymsEntries) {
		for (STSynonymsEntry stSynonymsEntry : stSynonymsEntries) {
			if (entityCache.getResult(
					STSynonymsEntryImpl.class,
					stSynonymsEntry.getPrimaryKey()) == null) {

				cacheResult(stSynonymsEntry);
			}
		}
	}

	/**
	 * Clears the cache for all st synonyms entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(STSynonymsEntryImpl.class);

		finderCache.clearCache(STSynonymsEntryImpl.class);
	}

	/**
	 * Clears the cache for the st synonyms entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(STSynonymsEntry stSynonymsEntry) {
		entityCache.removeResult(STSynonymsEntryImpl.class, stSynonymsEntry);
	}

	@Override
	public void clearCache(List<STSynonymsEntry> stSynonymsEntries) {
		for (STSynonymsEntry stSynonymsEntry : stSynonymsEntries) {
			entityCache.removeResult(
				STSynonymsEntryImpl.class, stSynonymsEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(STSynonymsEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(STSynonymsEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new st synonyms entry with the primary key. Does not add the st synonyms entry to the database.
	 *
	 * @param STSynonymsEntryId the primary key for the new st synonyms entry
	 * @return the new st synonyms entry
	 */
	@Override
	public STSynonymsEntry create(long STSynonymsEntryId) {
		STSynonymsEntry stSynonymsEntry = new STSynonymsEntryImpl();

		stSynonymsEntry.setNew(true);
		stSynonymsEntry.setPrimaryKey(STSynonymsEntryId);

		stSynonymsEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return stSynonymsEntry;
	}

	/**
	 * Removes the st synonyms entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param STSynonymsEntryId the primary key of the st synonyms entry
	 * @return the st synonyms entry that was removed
	 * @throws NoSuchSTSynonymsEntryException if a st synonyms entry with the primary key could not be found
	 */
	@Override
	public STSynonymsEntry remove(long STSynonymsEntryId)
		throws NoSuchSTSynonymsEntryException {

		return remove((Serializable)STSynonymsEntryId);
	}

	/**
	 * Removes the st synonyms entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the st synonyms entry
	 * @return the st synonyms entry that was removed
	 * @throws NoSuchSTSynonymsEntryException if a st synonyms entry with the primary key could not be found
	 */
	@Override
	public STSynonymsEntry remove(Serializable primaryKey)
		throws NoSuchSTSynonymsEntryException {

		Session session = null;

		try {
			session = openSession();

			STSynonymsEntry stSynonymsEntry = (STSynonymsEntry)session.get(
				STSynonymsEntryImpl.class, primaryKey);

			if (stSynonymsEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSTSynonymsEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(stSynonymsEntry);
		}
		catch (NoSuchSTSynonymsEntryException noSuchEntityException) {
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
	protected STSynonymsEntry removeImpl(STSynonymsEntry stSynonymsEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(stSynonymsEntry)) {
				stSynonymsEntry = (STSynonymsEntry)session.get(
					STSynonymsEntryImpl.class,
					stSynonymsEntry.getPrimaryKeyObj());
			}

			if (stSynonymsEntry != null) {
				session.delete(stSynonymsEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (stSynonymsEntry != null) {
			clearCache(stSynonymsEntry);
		}

		return stSynonymsEntry;
	}

	@Override
	public STSynonymsEntry updateImpl(STSynonymsEntry stSynonymsEntry) {
		boolean isNew = stSynonymsEntry.isNew();

		if (!(stSynonymsEntry instanceof STSynonymsEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(stSynonymsEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					stSynonymsEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in stSynonymsEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom STSynonymsEntry implementation " +
					stSynonymsEntry.getClass());
		}

		STSynonymsEntryModelImpl stSynonymsEntryModelImpl =
			(STSynonymsEntryModelImpl)stSynonymsEntry;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(stSynonymsEntry);
			}
			else {
				stSynonymsEntry = (STSynonymsEntry)session.merge(
					stSynonymsEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			STSynonymsEntryImpl.class, stSynonymsEntryModelImpl, false, true);

		if (isNew) {
			stSynonymsEntry.setNew(false);
		}

		stSynonymsEntry.resetOriginalValues();

		return stSynonymsEntry;
	}

	/**
	 * Returns the st synonyms entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the st synonyms entry
	 * @return the st synonyms entry
	 * @throws NoSuchSTSynonymsEntryException if a st synonyms entry with the primary key could not be found
	 */
	@Override
	public STSynonymsEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSTSynonymsEntryException {

		STSynonymsEntry stSynonymsEntry = fetchByPrimaryKey(primaryKey);

		if (stSynonymsEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSTSynonymsEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return stSynonymsEntry;
	}

	/**
	 * Returns the st synonyms entry with the primary key or throws a <code>NoSuchSTSynonymsEntryException</code> if it could not be found.
	 *
	 * @param STSynonymsEntryId the primary key of the st synonyms entry
	 * @return the st synonyms entry
	 * @throws NoSuchSTSynonymsEntryException if a st synonyms entry with the primary key could not be found
	 */
	@Override
	public STSynonymsEntry findByPrimaryKey(long STSynonymsEntryId)
		throws NoSuchSTSynonymsEntryException {

		return findByPrimaryKey((Serializable)STSynonymsEntryId);
	}

	/**
	 * Returns the st synonyms entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param STSynonymsEntryId the primary key of the st synonyms entry
	 * @return the st synonyms entry, or <code>null</code> if a st synonyms entry with the primary key could not be found
	 */
	@Override
	public STSynonymsEntry fetchByPrimaryKey(long STSynonymsEntryId) {
		return fetchByPrimaryKey((Serializable)STSynonymsEntryId);
	}

	/**
	 * Returns all the st synonyms entries.
	 *
	 * @return the st synonyms entries
	 */
	@Override
	public List<STSynonymsEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the st synonyms entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @return the range of st synonyms entries
	 */
	@Override
	public List<STSynonymsEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the st synonyms entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of st synonyms entries
	 */
	@Override
	public List<STSynonymsEntry> findAll(
		int start, int end,
		OrderByComparator<STSynonymsEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the st synonyms entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of st synonyms entries
	 */
	@Override
	public List<STSynonymsEntry> findAll(
		int start, int end,
		OrderByComparator<STSynonymsEntry> orderByComparator,
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

		List<STSynonymsEntry> list = null;

		if (useFinderCache) {
			list = (List<STSynonymsEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_STSYNONYMSENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_STSYNONYMSENTRY;

				sql = sql.concat(STSynonymsEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<STSynonymsEntry>)QueryUtil.list(
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
	 * Removes all the st synonyms entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (STSynonymsEntry stSynonymsEntry : findAll()) {
			remove(stSynonymsEntry);
		}
	}

	/**
	 * Returns the number of st synonyms entries.
	 *
	 * @return the number of st synonyms entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_STSYNONYMSENTRY);

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
		return "STSynonymsEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_STSYNONYMSENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return STSynonymsEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the st synonyms entry persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new STSynonymsEntryModelArgumentsResolver(),
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
		entityCache.removeCache(STSynonymsEntryImpl.class.getName());

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

	private static final String _SQL_SELECT_STSYNONYMSENTRY =
		"SELECT stSynonymsEntry FROM STSynonymsEntry stSynonymsEntry";

	private static final String _SQL_SELECT_STSYNONYMSENTRY_WHERE =
		"SELECT stSynonymsEntry FROM STSynonymsEntry stSynonymsEntry WHERE ";

	private static final String _SQL_COUNT_STSYNONYMSENTRY =
		"SELECT COUNT(stSynonymsEntry) FROM STSynonymsEntry stSynonymsEntry";

	private static final String _SQL_COUNT_STSYNONYMSENTRY_WHERE =
		"SELECT COUNT(stSynonymsEntry) FROM STSynonymsEntry stSynonymsEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "stSynonymsEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No STSynonymsEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No STSynonymsEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		STSynonymsEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class STSynonymsEntryModelArgumentsResolver
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

			STSynonymsEntryModelImpl stSynonymsEntryModelImpl =
				(STSynonymsEntryModelImpl)baseModel;

			long columnBitmask = stSynonymsEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					stSynonymsEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						stSynonymsEntryModelImpl.getColumnBitmask(columnName);
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
					stSynonymsEntryModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return STSynonymsEntryImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return STSynonymsEntryTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			STSynonymsEntryModelImpl stSynonymsEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						stSynonymsEntryModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = stSynonymsEntryModelImpl.getColumnValue(
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