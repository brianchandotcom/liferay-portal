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
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the cp definition diagram entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
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
	private FinderPath
		_finderPathWithPaginationFindByCPDefinitionDiagramSettingsId;
	private FinderPath
		_finderPathWithoutPaginationFindByCPDefinitionDiagramSettingsId;
	private FinderPath _finderPathCountByCPDefinitionDiagramSettingsId;

	/**
	 * Returns all the cp definition diagram entries where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @return the matching cp definition diagram entries
	 */
	@Override
	public List<CPDefinitionDiagramEntry> findByCPDefinitionDiagramSettingsId(
		long CPDefinitionDiagramSettingsId) {

		return findByCPDefinitionDiagramSettingsId(
			CPDefinitionDiagramSettingsId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp definition diagram entries where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @return the range of matching cp definition diagram entries
	 */
	@Override
	public List<CPDefinitionDiagramEntry> findByCPDefinitionDiagramSettingsId(
		long CPDefinitionDiagramSettingsId, int start, int end) {

		return findByCPDefinitionDiagramSettingsId(
			CPDefinitionDiagramSettingsId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram entries where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition diagram entries
	 */
	@Override
	public List<CPDefinitionDiagramEntry> findByCPDefinitionDiagramSettingsId(
		long CPDefinitionDiagramSettingsId, int start, int end,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator) {

		return findByCPDefinitionDiagramSettingsId(
			CPDefinitionDiagramSettingsId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram entries where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition diagram entries
	 */
	@Override
	public List<CPDefinitionDiagramEntry> findByCPDefinitionDiagramSettingsId(
		long CPDefinitionDiagramSettingsId, int start, int end,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCPDefinitionDiagramSettingsId;
				finderArgs = new Object[] {CPDefinitionDiagramSettingsId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCPDefinitionDiagramSettingsId;
			finderArgs = new Object[] {
				CPDefinitionDiagramSettingsId, start, end, orderByComparator
			};
		}

		List<CPDefinitionDiagramEntry> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionDiagramEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionDiagramEntry cpDefinitionDiagramEntry : list) {
					if (CPDefinitionDiagramSettingsId !=
							cpDefinitionDiagramEntry.
								getCPDefinitionDiagramSettingsId()) {

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

			sb.append(
				_FINDER_COLUMN_CPDEFINITIONDIAGRAMSETTINGSID_CPDEFINITIONDIAGRAMSETTINGSID_2);

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

				queryPos.add(CPDefinitionDiagramSettingsId);

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
	 * Returns the first cp definition diagram entry in the ordered set where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a matching cp definition diagram entry could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry findByCPDefinitionDiagramSettingsId_First(
			long CPDefinitionDiagramSettingsId,
			OrderByComparator<CPDefinitionDiagramEntry> orderByComparator)
		throws NoSuchCPDefinitionDiagramEntryException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			fetchByCPDefinitionDiagramSettingsId_First(
				CPDefinitionDiagramSettingsId, orderByComparator);

		if (cpDefinitionDiagramEntry != null) {
			return cpDefinitionDiagramEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionDiagramSettingsId=");
		sb.append(CPDefinitionDiagramSettingsId);

		sb.append("}");

		throw new NoSuchCPDefinitionDiagramEntryException(sb.toString());
	}

	/**
	 * Returns the first cp definition diagram entry in the ordered set where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram entry, or <code>null</code> if a matching cp definition diagram entry could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry fetchByCPDefinitionDiagramSettingsId_First(
		long CPDefinitionDiagramSettingsId,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator) {

		List<CPDefinitionDiagramEntry> list =
			findByCPDefinitionDiagramSettingsId(
				CPDefinitionDiagramSettingsId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition diagram entry in the ordered set where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a matching cp definition diagram entry could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry findByCPDefinitionDiagramSettingsId_Last(
			long CPDefinitionDiagramSettingsId,
			OrderByComparator<CPDefinitionDiagramEntry> orderByComparator)
		throws NoSuchCPDefinitionDiagramEntryException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			fetchByCPDefinitionDiagramSettingsId_Last(
				CPDefinitionDiagramSettingsId, orderByComparator);

		if (cpDefinitionDiagramEntry != null) {
			return cpDefinitionDiagramEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionDiagramSettingsId=");
		sb.append(CPDefinitionDiagramSettingsId);

		sb.append("}");

		throw new NoSuchCPDefinitionDiagramEntryException(sb.toString());
	}

	/**
	 * Returns the last cp definition diagram entry in the ordered set where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram entry, or <code>null</code> if a matching cp definition diagram entry could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry fetchByCPDefinitionDiagramSettingsId_Last(
		long CPDefinitionDiagramSettingsId,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator) {

		int count = countByCPDefinitionDiagramSettingsId(
			CPDefinitionDiagramSettingsId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionDiagramEntry> list =
			findByCPDefinitionDiagramSettingsId(
				CPDefinitionDiagramSettingsId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition diagram entries before and after the current cp definition diagram entry in the ordered set where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key of the current cp definition diagram entry
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a cp definition diagram entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramEntry[]
			findByCPDefinitionDiagramSettingsId_PrevAndNext(
				long CPDefinitionDiagramEntryId,
				long CPDefinitionDiagramSettingsId,
				OrderByComparator<CPDefinitionDiagramEntry> orderByComparator)
		throws NoSuchCPDefinitionDiagramEntryException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry = findByPrimaryKey(
			CPDefinitionDiagramEntryId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionDiagramEntry[] array =
				new CPDefinitionDiagramEntryImpl[3];

			array[0] = getByCPDefinitionDiagramSettingsId_PrevAndNext(
				session, cpDefinitionDiagramEntry,
				CPDefinitionDiagramSettingsId, orderByComparator, true);

			array[1] = cpDefinitionDiagramEntry;

			array[2] = getByCPDefinitionDiagramSettingsId_PrevAndNext(
				session, cpDefinitionDiagramEntry,
				CPDefinitionDiagramSettingsId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionDiagramEntry
		getByCPDefinitionDiagramSettingsId_PrevAndNext(
			Session session, CPDefinitionDiagramEntry cpDefinitionDiagramEntry,
			long CPDefinitionDiagramSettingsId,
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

		sb.append(
			_FINDER_COLUMN_CPDEFINITIONDIAGRAMSETTINGSID_CPDEFINITIONDIAGRAMSETTINGSID_2);

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

		queryPos.add(CPDefinitionDiagramSettingsId);

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
	 * Removes all the cp definition diagram entries where CPDefinitionDiagramSettingsId = &#63; from the database.
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 */
	@Override
	public void removeByCPDefinitionDiagramSettingsId(
		long CPDefinitionDiagramSettingsId) {

		for (CPDefinitionDiagramEntry cpDefinitionDiagramEntry :
				findByCPDefinitionDiagramSettingsId(
					CPDefinitionDiagramSettingsId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpDefinitionDiagramEntry);
		}
	}

	/**
	 * Returns the number of cp definition diagram entries where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @return the number of matching cp definition diagram entries
	 */
	@Override
	public int countByCPDefinitionDiagramSettingsId(
		long CPDefinitionDiagramSettingsId) {

		FinderPath finderPath = _finderPathCountByCPDefinitionDiagramSettingsId;

		Object[] finderArgs = new Object[] {CPDefinitionDiagramSettingsId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITIONDIAGRAMENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_CPDEFINITIONDIAGRAMSETTINGSID_CPDEFINITIONDIAGRAMSETTINGSID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionDiagramSettingsId);

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
		_FINDER_COLUMN_CPDEFINITIONDIAGRAMSETTINGSID_CPDEFINITIONDIAGRAMSETTINGSID_2 =
			"cpDefinitionDiagramEntry.CPDefinitionDiagramSettingsId = ?";

	public CPDefinitionDiagramEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("number", "number_");

		setDBColumnNames(dbColumnNames);

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

		Date now = new Date();

		if (isNew && (cpDefinitionDiagramEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDefinitionDiagramEntry.setCreateDate(now);
			}
			else {
				cpDefinitionDiagramEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!cpDefinitionDiagramEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDefinitionDiagramEntry.setModifiedDate(now);
			}
			else {
				cpDefinitionDiagramEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
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
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CPDefinitionDiagramEntryPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CPDefinitionDiagramEntryModelArgumentsResolver(),
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

		_finderPathWithPaginationFindByCPDefinitionDiagramSettingsId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCPDefinitionDiagramSettingsId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"CPDefinitionDiagramSettingsId"}, true);

		_finderPathWithoutPaginationFindByCPDefinitionDiagramSettingsId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCPDefinitionDiagramSettingsId",
				new String[] {Long.class.getName()},
				new String[] {"CPDefinitionDiagramSettingsId"}, true);

		_finderPathCountByCPDefinitionDiagramSettingsId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCPDefinitionDiagramSettingsId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionDiagramSettingsId"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CPDefinitionDiagramEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
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

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"number"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CPDefinitionDiagramEntryModelArgumentsResolver
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

			CPDefinitionDiagramEntryModelImpl
				cpDefinitionDiagramEntryModelImpl =
					(CPDefinitionDiagramEntryModelImpl)baseModel;

			long columnBitmask =
				cpDefinitionDiagramEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					cpDefinitionDiagramEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						cpDefinitionDiagramEntryModelImpl.getColumnBitmask(
							columnName);
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
					cpDefinitionDiagramEntryModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CPDefinitionDiagramEntryImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CPDefinitionDiagramEntryTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			CPDefinitionDiagramEntryModelImpl cpDefinitionDiagramEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						cpDefinitionDiagramEntryModelImpl.
							getColumnOriginalValue(columnName);
				}
				else {
					arguments[i] =
						cpDefinitionDiagramEntryModelImpl.getColumnValue(
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

			orderByColumnsBitmask |=
				CPDefinitionDiagramEntryModelImpl.getColumnBitmask("number_");

			_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
		}

	}

}