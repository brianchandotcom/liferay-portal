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

package com.liferay.commerce.bom.service.persistence.impl;

import com.liferay.commerce.bom.exception.NoSuchBOMEntryException;
import com.liferay.commerce.bom.model.CommerceBOMEntry;
import com.liferay.commerce.bom.model.CommerceBOMEntryTable;
import com.liferay.commerce.bom.model.impl.CommerceBOMEntryImpl;
import com.liferay.commerce.bom.model.impl.CommerceBOMEntryModelImpl;
import com.liferay.commerce.bom.service.persistence.CommerceBOMEntryPersistence;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the commerce bom entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceBOMEntryPersistenceImpl
	extends BasePersistenceImpl<CommerceBOMEntry>
	implements CommerceBOMEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceBOMEntryUtil</code> to access the commerce bom entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceBOMEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommerceBOMDefinitionId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceBOMDefinitionId;
	private FinderPath _finderPathCountByCommerceBOMDefinitionId;

	/**
	 * Returns all the commerce bom entries where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @return the matching commerce bom entries
	 */
	@Override
	public List<CommerceBOMEntry> findByCommerceBOMDefinitionId(
		long commerceBOMDefinitionId) {

		return findByCommerceBOMDefinitionId(
			commerceBOMDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce bom entries where commerceBOMDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @return the range of matching commerce bom entries
	 */
	@Override
	public List<CommerceBOMEntry> findByCommerceBOMDefinitionId(
		long commerceBOMDefinitionId, int start, int end) {

		return findByCommerceBOMDefinitionId(
			commerceBOMDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce bom entries where commerceBOMDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce bom entries
	 */
	@Override
	public List<CommerceBOMEntry> findByCommerceBOMDefinitionId(
		long commerceBOMDefinitionId, int start, int end,
		OrderByComparator<CommerceBOMEntry> orderByComparator) {

		return findByCommerceBOMDefinitionId(
			commerceBOMDefinitionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce bom entries where commerceBOMDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce bom entries
	 */
	@Override
	public List<CommerceBOMEntry> findByCommerceBOMDefinitionId(
		long commerceBOMDefinitionId, int start, int end,
		OrderByComparator<CommerceBOMEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceBOMDefinitionId;
				finderArgs = new Object[] {commerceBOMDefinitionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceBOMDefinitionId;
			finderArgs = new Object[] {
				commerceBOMDefinitionId, start, end, orderByComparator
			};
		}

		List<CommerceBOMEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceBOMEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceBOMEntry commerceBOMEntry : list) {
					if (commerceBOMDefinitionId !=
							commerceBOMEntry.getCommerceBOMDefinitionId()) {

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

			sb.append(_SQL_SELECT_COMMERCEBOMENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEBOMDEFINITIONID_COMMERCEBOMDEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceBOMEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceBOMDefinitionId);

				list = (List<CommerceBOMEntry>)QueryUtil.list(
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
	 * Returns the first commerce bom entry in the ordered set where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom entry
	 * @throws NoSuchBOMEntryException if a matching commerce bom entry could not be found
	 */
	@Override
	public CommerceBOMEntry findByCommerceBOMDefinitionId_First(
			long commerceBOMDefinitionId,
			OrderByComparator<CommerceBOMEntry> orderByComparator)
		throws NoSuchBOMEntryException {

		CommerceBOMEntry commerceBOMEntry =
			fetchByCommerceBOMDefinitionId_First(
				commerceBOMDefinitionId, orderByComparator);

		if (commerceBOMEntry != null) {
			return commerceBOMEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceBOMDefinitionId=");
		sb.append(commerceBOMDefinitionId);

		sb.append("}");

		throw new NoSuchBOMEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce bom entry in the ordered set where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom entry, or <code>null</code> if a matching commerce bom entry could not be found
	 */
	@Override
	public CommerceBOMEntry fetchByCommerceBOMDefinitionId_First(
		long commerceBOMDefinitionId,
		OrderByComparator<CommerceBOMEntry> orderByComparator) {

		List<CommerceBOMEntry> list = findByCommerceBOMDefinitionId(
			commerceBOMDefinitionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce bom entry in the ordered set where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom entry
	 * @throws NoSuchBOMEntryException if a matching commerce bom entry could not be found
	 */
	@Override
	public CommerceBOMEntry findByCommerceBOMDefinitionId_Last(
			long commerceBOMDefinitionId,
			OrderByComparator<CommerceBOMEntry> orderByComparator)
		throws NoSuchBOMEntryException {

		CommerceBOMEntry commerceBOMEntry = fetchByCommerceBOMDefinitionId_Last(
			commerceBOMDefinitionId, orderByComparator);

		if (commerceBOMEntry != null) {
			return commerceBOMEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceBOMDefinitionId=");
		sb.append(commerceBOMDefinitionId);

		sb.append("}");

		throw new NoSuchBOMEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce bom entry in the ordered set where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom entry, or <code>null</code> if a matching commerce bom entry could not be found
	 */
	@Override
	public CommerceBOMEntry fetchByCommerceBOMDefinitionId_Last(
		long commerceBOMDefinitionId,
		OrderByComparator<CommerceBOMEntry> orderByComparator) {

		int count = countByCommerceBOMDefinitionId(commerceBOMDefinitionId);

		if (count == 0) {
			return null;
		}

		List<CommerceBOMEntry> list = findByCommerceBOMDefinitionId(
			commerceBOMDefinitionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce bom entries before and after the current commerce bom entry in the ordered set where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMEntryId the primary key of the current commerce bom entry
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce bom entry
	 * @throws NoSuchBOMEntryException if a commerce bom entry with the primary key could not be found
	 */
	@Override
	public CommerceBOMEntry[] findByCommerceBOMDefinitionId_PrevAndNext(
			long commerceBOMEntryId, long commerceBOMDefinitionId,
			OrderByComparator<CommerceBOMEntry> orderByComparator)
		throws NoSuchBOMEntryException {

		CommerceBOMEntry commerceBOMEntry = findByPrimaryKey(
			commerceBOMEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceBOMEntry[] array = new CommerceBOMEntryImpl[3];

			array[0] = getByCommerceBOMDefinitionId_PrevAndNext(
				session, commerceBOMEntry, commerceBOMDefinitionId,
				orderByComparator, true);

			array[1] = commerceBOMEntry;

			array[2] = getByCommerceBOMDefinitionId_PrevAndNext(
				session, commerceBOMEntry, commerceBOMDefinitionId,
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

	protected CommerceBOMEntry getByCommerceBOMDefinitionId_PrevAndNext(
		Session session, CommerceBOMEntry commerceBOMEntry,
		long commerceBOMDefinitionId,
		OrderByComparator<CommerceBOMEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEBOMENTRY_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCEBOMDEFINITIONID_COMMERCEBOMDEFINITIONID_2);

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
			sb.append(CommerceBOMEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceBOMDefinitionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceBOMEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceBOMEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce bom entries where commerceBOMDefinitionId = &#63; from the database.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 */
	@Override
	public void removeByCommerceBOMDefinitionId(long commerceBOMDefinitionId) {
		for (CommerceBOMEntry commerceBOMEntry :
				findByCommerceBOMDefinitionId(
					commerceBOMDefinitionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceBOMEntry);
		}
	}

	/**
	 * Returns the number of commerce bom entries where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @return the number of matching commerce bom entries
	 */
	@Override
	public int countByCommerceBOMDefinitionId(long commerceBOMDefinitionId) {
		FinderPath finderPath = _finderPathCountByCommerceBOMDefinitionId;

		Object[] finderArgs = new Object[] {commerceBOMDefinitionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEBOMENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEBOMDEFINITIONID_COMMERCEBOMDEFINITIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceBOMDefinitionId);

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

	private static final String
		_FINDER_COLUMN_COMMERCEBOMDEFINITIONID_COMMERCEBOMDEFINITIONID_2 =
			"commerceBOMEntry.commerceBOMDefinitionId = ?";

	public CommerceBOMEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("number", "number_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceBOMEntry.class);

		setModelImplClass(CommerceBOMEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceBOMEntryTable.INSTANCE);
	}

	/**
	 * Caches the commerce bom entry in the entity cache if it is enabled.
	 *
	 * @param commerceBOMEntry the commerce bom entry
	 */
	@Override
	public void cacheResult(CommerceBOMEntry commerceBOMEntry) {
		entityCache.putResult(
			CommerceBOMEntryImpl.class, commerceBOMEntry.getPrimaryKey(),
			commerceBOMEntry);
	}

	/**
	 * Caches the commerce bom entries in the entity cache if it is enabled.
	 *
	 * @param commerceBOMEntries the commerce bom entries
	 */
	@Override
	public void cacheResult(List<CommerceBOMEntry> commerceBOMEntries) {
		for (CommerceBOMEntry commerceBOMEntry : commerceBOMEntries) {
			if (entityCache.getResult(
					CommerceBOMEntryImpl.class,
					commerceBOMEntry.getPrimaryKey()) == null) {

				cacheResult(commerceBOMEntry);
			}
		}
	}

	/**
	 * Clears the cache for all commerce bom entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceBOMEntryImpl.class);

		finderCache.clearCache(CommerceBOMEntryImpl.class);
	}

	/**
	 * Clears the cache for the commerce bom entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceBOMEntry commerceBOMEntry) {
		entityCache.removeResult(CommerceBOMEntryImpl.class, commerceBOMEntry);
	}

	@Override
	public void clearCache(List<CommerceBOMEntry> commerceBOMEntries) {
		for (CommerceBOMEntry commerceBOMEntry : commerceBOMEntries) {
			entityCache.removeResult(
				CommerceBOMEntryImpl.class, commerceBOMEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceBOMEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CommerceBOMEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commerce bom entry with the primary key. Does not add the commerce bom entry to the database.
	 *
	 * @param commerceBOMEntryId the primary key for the new commerce bom entry
	 * @return the new commerce bom entry
	 */
	@Override
	public CommerceBOMEntry create(long commerceBOMEntryId) {
		CommerceBOMEntry commerceBOMEntry = new CommerceBOMEntryImpl();

		commerceBOMEntry.setNew(true);
		commerceBOMEntry.setPrimaryKey(commerceBOMEntryId);

		commerceBOMEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceBOMEntry;
	}

	/**
	 * Removes the commerce bom entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceBOMEntryId the primary key of the commerce bom entry
	 * @return the commerce bom entry that was removed
	 * @throws NoSuchBOMEntryException if a commerce bom entry with the primary key could not be found
	 */
	@Override
	public CommerceBOMEntry remove(long commerceBOMEntryId)
		throws NoSuchBOMEntryException {

		return remove((Serializable)commerceBOMEntryId);
	}

	/**
	 * Removes the commerce bom entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce bom entry
	 * @return the commerce bom entry that was removed
	 * @throws NoSuchBOMEntryException if a commerce bom entry with the primary key could not be found
	 */
	@Override
	public CommerceBOMEntry remove(Serializable primaryKey)
		throws NoSuchBOMEntryException {

		Session session = null;

		try {
			session = openSession();

			CommerceBOMEntry commerceBOMEntry = (CommerceBOMEntry)session.get(
				CommerceBOMEntryImpl.class, primaryKey);

			if (commerceBOMEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBOMEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceBOMEntry);
		}
		catch (NoSuchBOMEntryException noSuchEntityException) {
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
	protected CommerceBOMEntry removeImpl(CommerceBOMEntry commerceBOMEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceBOMEntry)) {
				commerceBOMEntry = (CommerceBOMEntry)session.get(
					CommerceBOMEntryImpl.class,
					commerceBOMEntry.getPrimaryKeyObj());
			}

			if (commerceBOMEntry != null) {
				session.delete(commerceBOMEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceBOMEntry != null) {
			clearCache(commerceBOMEntry);
		}

		return commerceBOMEntry;
	}

	@Override
	public CommerceBOMEntry updateImpl(CommerceBOMEntry commerceBOMEntry) {
		boolean isNew = commerceBOMEntry.isNew();

		if (!(commerceBOMEntry instanceof CommerceBOMEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceBOMEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceBOMEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceBOMEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceBOMEntry implementation " +
					commerceBOMEntry.getClass());
		}

		CommerceBOMEntryModelImpl commerceBOMEntryModelImpl =
			(CommerceBOMEntryModelImpl)commerceBOMEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceBOMEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceBOMEntry.setCreateDate(now);
			}
			else {
				commerceBOMEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceBOMEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceBOMEntry.setModifiedDate(now);
			}
			else {
				commerceBOMEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceBOMEntry);
			}
			else {
				commerceBOMEntry = (CommerceBOMEntry)session.merge(
					commerceBOMEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceBOMEntryImpl.class, commerceBOMEntryModelImpl, false, true);

		if (isNew) {
			commerceBOMEntry.setNew(false);
		}

		commerceBOMEntry.resetOriginalValues();

		return commerceBOMEntry;
	}

	/**
	 * Returns the commerce bom entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce bom entry
	 * @return the commerce bom entry
	 * @throws NoSuchBOMEntryException if a commerce bom entry with the primary key could not be found
	 */
	@Override
	public CommerceBOMEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchBOMEntryException {

		CommerceBOMEntry commerceBOMEntry = fetchByPrimaryKey(primaryKey);

		if (commerceBOMEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchBOMEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceBOMEntry;
	}

	/**
	 * Returns the commerce bom entry with the primary key or throws a <code>NoSuchBOMEntryException</code> if it could not be found.
	 *
	 * @param commerceBOMEntryId the primary key of the commerce bom entry
	 * @return the commerce bom entry
	 * @throws NoSuchBOMEntryException if a commerce bom entry with the primary key could not be found
	 */
	@Override
	public CommerceBOMEntry findByPrimaryKey(long commerceBOMEntryId)
		throws NoSuchBOMEntryException {

		return findByPrimaryKey((Serializable)commerceBOMEntryId);
	}

	/**
	 * Returns the commerce bom entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceBOMEntryId the primary key of the commerce bom entry
	 * @return the commerce bom entry, or <code>null</code> if a commerce bom entry with the primary key could not be found
	 */
	@Override
	public CommerceBOMEntry fetchByPrimaryKey(long commerceBOMEntryId) {
		return fetchByPrimaryKey((Serializable)commerceBOMEntryId);
	}

	/**
	 * Returns all the commerce bom entries.
	 *
	 * @return the commerce bom entries
	 */
	@Override
	public List<CommerceBOMEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce bom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @return the range of commerce bom entries
	 */
	@Override
	public List<CommerceBOMEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce bom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce bom entries
	 */
	@Override
	public List<CommerceBOMEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceBOMEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce bom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce bom entries
	 */
	@Override
	public List<CommerceBOMEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceBOMEntry> orderByComparator,
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

		List<CommerceBOMEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceBOMEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEBOMENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEBOMENTRY;

				sql = sql.concat(CommerceBOMEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceBOMEntry>)QueryUtil.list(
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
	 * Removes all the commerce bom entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceBOMEntry commerceBOMEntry : findAll()) {
			remove(commerceBOMEntry);
		}
	}

	/**
	 * Returns the number of commerce bom entries.
	 *
	 * @return the number of commerce bom entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_COMMERCEBOMENTRY);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "commerceBOMEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEBOMENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceBOMEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce bom entry persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceBOMEntryPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CommerceBOMEntryModelArgumentsResolver(),
			MapUtil.singletonDictionary(
				"model.class.name", CommerceBOMEntry.class.getName()));

		_finderPathWithPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByCommerceBOMDefinitionId =
			_createFinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceBOMDefinitionId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"commerceBOMDefinitionId"}, true);

		_finderPathWithoutPaginationFindByCommerceBOMDefinitionId =
			_createFinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceBOMDefinitionId",
				new String[] {Long.class.getName()},
				new String[] {"commerceBOMDefinitionId"}, true);

		_finderPathCountByCommerceBOMDefinitionId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceBOMDefinitionId",
			new String[] {Long.class.getName()},
			new String[] {"commerceBOMDefinitionId"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceBOMEntryImpl.class.getName());

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

	private static final String _SQL_SELECT_COMMERCEBOMENTRY =
		"SELECT commerceBOMEntry FROM CommerceBOMEntry commerceBOMEntry";

	private static final String _SQL_SELECT_COMMERCEBOMENTRY_WHERE =
		"SELECT commerceBOMEntry FROM CommerceBOMEntry commerceBOMEntry WHERE ";

	private static final String _SQL_COUNT_COMMERCEBOMENTRY =
		"SELECT COUNT(commerceBOMEntry) FROM CommerceBOMEntry commerceBOMEntry";

	private static final String _SQL_COUNT_COMMERCEBOMENTRY_WHERE =
		"SELECT COUNT(commerceBOMEntry) FROM CommerceBOMEntry commerceBOMEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceBOMEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceBOMEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceBOMEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceBOMEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"number"});

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

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;
	private Set<ServiceRegistration<FinderPath>> _serviceRegistrations =
		new HashSet<>();

	private static class CommerceBOMEntryModelArgumentsResolver
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

			CommerceBOMEntryModelImpl commerceBOMEntryModelImpl =
				(CommerceBOMEntryModelImpl)baseModel;

			long columnBitmask = commerceBOMEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					commerceBOMEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						commerceBOMEntryModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					commerceBOMEntryModelImpl, columnNames, original);
			}

			return null;
		}

		private Object[] _getValue(
			CommerceBOMEntryModelImpl commerceBOMEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						commerceBOMEntryModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = commerceBOMEntryModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}