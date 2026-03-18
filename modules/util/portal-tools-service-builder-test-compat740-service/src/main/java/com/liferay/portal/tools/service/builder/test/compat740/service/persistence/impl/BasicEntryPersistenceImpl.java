/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service.persistence.impl;

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
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.tools.service.builder.test.compat740.exception.NoSuchBasicEntryException;
import com.liferay.portal.tools.service.builder.test.compat740.model.BasicEntry;
import com.liferay.portal.tools.service.builder.test.compat740.model.BasicEntryTable;
import com.liferay.portal.tools.service.builder.test.compat740.model.MappingEntry;
import com.liferay.portal.tools.service.builder.test.compat740.model.impl.BasicEntryImpl;
import com.liferay.portal.tools.service.builder.test.compat740.model.impl.BasicEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.compat740.service.persistence.BasicEntryPersistence;
import com.liferay.portal.tools.service.builder.test.compat740.service.persistence.BasicEntryUtil;
import com.liferay.portal.tools.service.builder.test.compat740.service.persistence.impl.constants.SBCompat740PersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashSet;
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
 * The persistence implementation for the basic entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = BasicEntryPersistence.class)
public class BasicEntryPersistenceImpl
	extends BasePersistenceImpl<BasicEntry> implements BasicEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BasicEntryUtil</code> to access the basic entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BasicEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the basic entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching basic entries
	 */
	@Override
	public List<BasicEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the basic entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @return the range of matching basic entries
	 */
	@Override
	public List<BasicEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the basic entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching basic entries
	 */
	@Override
	public List<BasicEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<BasicEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the basic entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching basic entries
	 */
	@Override
	public List<BasicEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<BasicEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<BasicEntry> list = null;

		if (useFinderCache) {
			list = (List<BasicEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BasicEntry basicEntry : list) {
					if (groupId != basicEntry.getGroupId()) {
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

			sb.append(_SQL_SELECT_BASICENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BasicEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<BasicEntry>)QueryUtil.list(
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
	 * Returns the first basic entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching basic entry
	 * @throws NoSuchBasicEntryException if a matching basic entry could not be found
	 */
	@Override
	public BasicEntry findByGroupId_First(
			long groupId, OrderByComparator<BasicEntry> orderByComparator)
		throws NoSuchBasicEntryException {

		BasicEntry basicEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (basicEntry != null) {
			return basicEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchBasicEntryException(sb.toString());
	}

	/**
	 * Returns the first basic entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching basic entry, or <code>null</code> if a matching basic entry could not be found
	 */
	@Override
	public BasicEntry fetchByGroupId_First(
		long groupId, OrderByComparator<BasicEntry> orderByComparator) {

		List<BasicEntry> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last basic entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching basic entry
	 * @throws NoSuchBasicEntryException if a matching basic entry could not be found
	 */
	@Override
	public BasicEntry findByGroupId_Last(
			long groupId, OrderByComparator<BasicEntry> orderByComparator)
		throws NoSuchBasicEntryException {

		BasicEntry basicEntry = fetchByGroupId_Last(groupId, orderByComparator);

		if (basicEntry != null) {
			return basicEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchBasicEntryException(sb.toString());
	}

	/**
	 * Returns the last basic entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching basic entry, or <code>null</code> if a matching basic entry could not be found
	 */
	@Override
	public BasicEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<BasicEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<BasicEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the basic entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (BasicEntry basicEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(basicEntry);
		}
	}

	/**
	 * Returns the number of basic entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching basic entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BASICENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"basicEntry.groupId = ?";

	private FinderPath _finderPathFetchByC_N;

	/**
	 * Returns the basic entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchBasicEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching basic entry
	 * @throws NoSuchBasicEntryException if a matching basic entry could not be found
	 */
	@Override
	public BasicEntry findByC_N(long companyId, String name)
		throws NoSuchBasicEntryException {

		BasicEntry basicEntry = fetchByC_N(companyId, name);

		if (basicEntry == null) {
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

			throw new NoSuchBasicEntryException(sb.toString());
		}

		return basicEntry;
	}

	/**
	 * Returns the basic entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching basic entry, or <code>null</code> if a matching basic entry could not be found
	 */
	@Override
	public BasicEntry fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the basic entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching basic entry, or <code>null</code> if a matching basic entry could not be found
	 */
	@Override
	public BasicEntry fetchByC_N(
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

		if (result instanceof BasicEntry) {
			BasicEntry basicEntry = (BasicEntry)result;

			if ((companyId != basicEntry.getCompanyId()) ||
				!Objects.equals(name, basicEntry.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_BASICENTRY_WHERE);

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

				List<BasicEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_N, finderArgs, list);
					}
				}
				else {
					BasicEntry basicEntry = list.get(0);

					result = basicEntry;

					cacheResult(basicEntry);
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
			return (BasicEntry)result;
		}
	}

	/**
	 * Removes the basic entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the basic entry that was removed
	 */
	@Override
	public BasicEntry removeByC_N(long companyId, String name)
		throws NoSuchBasicEntryException {

		BasicEntry basicEntry = findByC_N(companyId, name);

		return remove(basicEntry);
	}

	/**
	 * Returns the number of basic entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching basic entries
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		BasicEntry basicEntry = fetchByC_N(companyId, name);

		if (basicEntry == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 =
		"basicEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_NAME_2 =
		"basicEntry.name = ?";

	private static final String _FINDER_COLUMN_C_N_NAME_3 =
		"(basicEntry.name IS NULL OR basicEntry.name = '')";

	public BasicEntryPersistenceImpl() {
		setModelClass(BasicEntry.class);

		setModelImplClass(BasicEntryImpl.class);
		setModelPKClass(long.class);

		setTable(BasicEntryTable.INSTANCE);
	}

	/**
	 * Caches the basic entry in the entity cache if it is enabled.
	 *
	 * @param basicEntry the basic entry
	 */
	@Override
	public void cacheResult(BasicEntry basicEntry) {
		entityCache.putResult(
			BasicEntryImpl.class, basicEntry.getPrimaryKey(), basicEntry);

		finderCache.putResult(
			_finderPathFetchByC_N,
			new Object[] {basicEntry.getCompanyId(), basicEntry.getName()},
			basicEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the basic entries in the entity cache if it is enabled.
	 *
	 * @param basicEntries the basic entries
	 */
	@Override
	public void cacheResult(List<BasicEntry> basicEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (basicEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (BasicEntry basicEntry : basicEntries) {
			if (entityCache.getResult(
					BasicEntryImpl.class, basicEntry.getPrimaryKey()) == null) {

				cacheResult(basicEntry);
			}
		}
	}

	/**
	 * Clears the cache for all basic entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BasicEntryImpl.class);

		finderCache.clearCache(BasicEntryImpl.class);
	}

	/**
	 * Clears the cache for the basic entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BasicEntry basicEntry) {
		entityCache.removeResult(BasicEntryImpl.class, basicEntry);
	}

	@Override
	public void clearCache(List<BasicEntry> basicEntries) {
		for (BasicEntry basicEntry : basicEntries) {
			entityCache.removeResult(BasicEntryImpl.class, basicEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(BasicEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(BasicEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		BasicEntryModelImpl basicEntryModelImpl) {

		Object[] args = new Object[] {
			basicEntryModelImpl.getCompanyId(), basicEntryModelImpl.getName()
		};

		finderCache.putResult(_finderPathFetchByC_N, args, basicEntryModelImpl);
	}

	/**
	 * Creates a new basic entry with the primary key. Does not add the basic entry to the database.
	 *
	 * @param basicEntryId the primary key for the new basic entry
	 * @return the new basic entry
	 */
	@Override
	public BasicEntry create(long basicEntryId) {
		BasicEntry basicEntry = new BasicEntryImpl();

		basicEntry.setNew(true);
		basicEntry.setPrimaryKey(basicEntryId);

		basicEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return basicEntry;
	}

	/**
	 * Removes the basic entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param basicEntryId the primary key of the basic entry
	 * @return the basic entry that was removed
	 * @throws NoSuchBasicEntryException if a basic entry with the primary key could not be found
	 */
	@Override
	public BasicEntry remove(long basicEntryId)
		throws NoSuchBasicEntryException {

		return remove((Serializable)basicEntryId);
	}

	/**
	 * Removes the basic entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the basic entry
	 * @return the basic entry that was removed
	 * @throws NoSuchBasicEntryException if a basic entry with the primary key could not be found
	 */
	@Override
	public BasicEntry remove(Serializable primaryKey)
		throws NoSuchBasicEntryException {

		Session session = null;

		try {
			session = openSession();

			BasicEntry basicEntry = (BasicEntry)session.get(
				BasicEntryImpl.class, primaryKey);

			if (basicEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBasicEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(basicEntry);
		}
		catch (NoSuchBasicEntryException noSuchEntityException) {
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
	protected BasicEntry removeImpl(BasicEntry basicEntry) {
		basicEntryToMappingEntryTableMapper.deleteLeftPrimaryKeyTableMappings(
			basicEntry.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(basicEntry)) {
				basicEntry = (BasicEntry)session.get(
					BasicEntryImpl.class, basicEntry.getPrimaryKeyObj());
			}

			if (basicEntry != null) {
				session.delete(basicEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (basicEntry != null) {
			clearCache(basicEntry);
		}

		return basicEntry;
	}

	@Override
	public BasicEntry updateImpl(BasicEntry basicEntry) {
		boolean isNew = basicEntry.isNew();

		if (!(basicEntry instanceof BasicEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(basicEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(basicEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in basicEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BasicEntry implementation " +
					basicEntry.getClass());
		}

		BasicEntryModelImpl basicEntryModelImpl =
			(BasicEntryModelImpl)basicEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (basicEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				basicEntry.setCreateDate(date);
			}
			else {
				basicEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!basicEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				basicEntry.setModifiedDate(date);
			}
			else {
				basicEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(basicEntry);
			}
			else {
				basicEntry = (BasicEntry)session.merge(basicEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			BasicEntryImpl.class, basicEntryModelImpl, false, true);

		cacheUniqueFindersCache(basicEntryModelImpl);

		if (isNew) {
			basicEntry.setNew(false);
		}

		basicEntry.resetOriginalValues();

		return basicEntry;
	}

	/**
	 * Returns the basic entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the basic entry
	 * @return the basic entry
	 * @throws NoSuchBasicEntryException if a basic entry with the primary key could not be found
	 */
	@Override
	public BasicEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchBasicEntryException {

		BasicEntry basicEntry = fetchByPrimaryKey(primaryKey);

		if (basicEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchBasicEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return basicEntry;
	}

	/**
	 * Returns the basic entry with the primary key or throws a <code>NoSuchBasicEntryException</code> if it could not be found.
	 *
	 * @param basicEntryId the primary key of the basic entry
	 * @return the basic entry
	 * @throws NoSuchBasicEntryException if a basic entry with the primary key could not be found
	 */
	@Override
	public BasicEntry findByPrimaryKey(long basicEntryId)
		throws NoSuchBasicEntryException {

		return findByPrimaryKey((Serializable)basicEntryId);
	}

	/**
	 * Returns the basic entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param basicEntryId the primary key of the basic entry
	 * @return the basic entry, or <code>null</code> if a basic entry with the primary key could not be found
	 */
	@Override
	public BasicEntry fetchByPrimaryKey(long basicEntryId) {
		return fetchByPrimaryKey((Serializable)basicEntryId);
	}

	/**
	 * Returns all the basic entries.
	 *
	 * @return the basic entries
	 */
	@Override
	public List<BasicEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the basic entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @return the range of basic entries
	 */
	@Override
	public List<BasicEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the basic entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of basic entries
	 */
	@Override
	public List<BasicEntry> findAll(
		int start, int end, OrderByComparator<BasicEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the basic entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of basic entries
	 */
	@Override
	public List<BasicEntry> findAll(
		int start, int end, OrderByComparator<BasicEntry> orderByComparator,
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

		List<BasicEntry> list = null;

		if (useFinderCache) {
			list = (List<BasicEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_BASICENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_BASICENTRY;

				sql = sql.concat(BasicEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<BasicEntry>)QueryUtil.list(
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
	 * Removes all the basic entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BasicEntry basicEntry : findAll()) {
			remove(basicEntry);
		}
	}

	/**
	 * Returns the number of basic entries.
	 *
	 * @return the number of basic entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_BASICENTRY);

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

	/**
	 * Returns the primaryKeys of mapping entries associated with the basic entry.
	 *
	 * @param pk the primary key of the basic entry
	 * @return long[] of the primaryKeys of mapping entries associated with the basic entry
	 */
	@Override
	public long[] getMappingEntryPrimaryKeys(long pk) {
		long[] pks = basicEntryToMappingEntryTableMapper.getRightPrimaryKeys(
			pk);

		return pks.clone();
	}

	/**
	 * Returns all the basic entry associated with the mapping entry.
	 *
	 * @param pk the primary key of the mapping entry
	 * @return the basic entries associated with the mapping entry
	 */
	@Override
	public List<BasicEntry> getMappingEntryBasicEntries(long pk) {
		return getMappingEntryBasicEntries(
			pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns all the basic entry associated with the mapping entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the mapping entry
	 * @param start the lower bound of the range of mapping entries
	 * @param end the upper bound of the range of mapping entries (not inclusive)
	 * @return the range of basic entries associated with the mapping entry
	 */
	@Override
	public List<BasicEntry> getMappingEntryBasicEntries(
		long pk, int start, int end) {

		return getMappingEntryBasicEntries(pk, start, end, null);
	}

	/**
	 * Returns all the basic entry associated with the mapping entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the mapping entry
	 * @param start the lower bound of the range of mapping entries
	 * @param end the upper bound of the range of mapping entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of basic entries associated with the mapping entry
	 */
	@Override
	public List<BasicEntry> getMappingEntryBasicEntries(
		long pk, int start, int end,
		OrderByComparator<BasicEntry> orderByComparator) {

		return basicEntryToMappingEntryTableMapper.getLeftBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of mapping entries associated with the basic entry.
	 *
	 * @param pk the primary key of the basic entry
	 * @return the number of mapping entries associated with the basic entry
	 */
	@Override
	public int getMappingEntriesSize(long pk) {
		long[] pks = basicEntryToMappingEntryTableMapper.getRightPrimaryKeys(
			pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the mapping entry is associated with the basic entry.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPK the primary key of the mapping entry
	 * @return <code>true</code> if the mapping entry is associated with the basic entry; <code>false</code> otherwise
	 */
	@Override
	public boolean containsMappingEntry(long pk, long mappingEntryPK) {
		return basicEntryToMappingEntryTableMapper.containsTableMapping(
			pk, mappingEntryPK);
	}

	/**
	 * Returns <code>true</code> if the basic entry has any mapping entries associated with it.
	 *
	 * @param pk the primary key of the basic entry to check for associations with mapping entries
	 * @return <code>true</code> if the basic entry has any mapping entries associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsMappingEntries(long pk) {
		if (getMappingEntriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the basic entry and the mapping entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPK the primary key of the mapping entry
	 * @return <code>true</code> if an association between the basic entry and the mapping entry was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addMappingEntry(long pk, long mappingEntryPK) {
		BasicEntry basicEntry = fetchByPrimaryKey(pk);

		if (basicEntry == null) {
			return basicEntryToMappingEntryTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, mappingEntryPK);
		}
		else {
			return basicEntryToMappingEntryTableMapper.addTableMapping(
				basicEntry.getCompanyId(), pk, mappingEntryPK);
		}
	}

	/**
	 * Adds an association between the basic entry and the mapping entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntry the mapping entry
	 * @return <code>true</code> if an association between the basic entry and the mapping entry was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addMappingEntry(long pk, MappingEntry mappingEntry) {
		BasicEntry basicEntry = fetchByPrimaryKey(pk);

		if (basicEntry == null) {
			return basicEntryToMappingEntryTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				mappingEntry.getPrimaryKey());
		}
		else {
			return basicEntryToMappingEntryTableMapper.addTableMapping(
				basicEntry.getCompanyId(), pk, mappingEntry.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the basic entry and the mapping entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPKs the primary keys of the mapping entries
	 * @return <code>true</code> if at least one association between the basic entry and the mapping entries was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addMappingEntries(long pk, long[] mappingEntryPKs) {
		long companyId = 0;

		BasicEntry basicEntry = fetchByPrimaryKey(pk);

		if (basicEntry == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = basicEntry.getCompanyId();
		}

		long[] addedKeys = basicEntryToMappingEntryTableMapper.addTableMappings(
			companyId, pk, mappingEntryPKs);

		if (addedKeys.length > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Adds an association between the basic entry and the mapping entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntries the mapping entries
	 * @return <code>true</code> if at least one association between the basic entry and the mapping entries was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addMappingEntries(
		long pk, List<MappingEntry> mappingEntries) {

		return addMappingEntries(
			pk,
			ListUtil.toLongArray(
				mappingEntries, MappingEntry.MAPPING_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the basic entry and its mapping entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry to clear the associated mapping entries from
	 */
	@Override
	public void clearMappingEntries(long pk) {
		basicEntryToMappingEntryTableMapper.deleteLeftPrimaryKeyTableMappings(
			pk);
	}

	/**
	 * Removes the association between the basic entry and the mapping entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPK the primary key of the mapping entry
	 */
	@Override
	public void removeMappingEntry(long pk, long mappingEntryPK) {
		basicEntryToMappingEntryTableMapper.deleteTableMapping(
			pk, mappingEntryPK);
	}

	/**
	 * Removes the association between the basic entry and the mapping entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntry the mapping entry
	 */
	@Override
	public void removeMappingEntry(long pk, MappingEntry mappingEntry) {
		basicEntryToMappingEntryTableMapper.deleteTableMapping(
			pk, mappingEntry.getPrimaryKey());
	}

	/**
	 * Removes the association between the basic entry and the mapping entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPKs the primary keys of the mapping entries
	 */
	@Override
	public void removeMappingEntries(long pk, long[] mappingEntryPKs) {
		basicEntryToMappingEntryTableMapper.deleteTableMappings(
			pk, mappingEntryPKs);
	}

	/**
	 * Removes the association between the basic entry and the mapping entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntries the mapping entries
	 */
	@Override
	public void removeMappingEntries(
		long pk, List<MappingEntry> mappingEntries) {

		removeMappingEntries(
			pk,
			ListUtil.toLongArray(
				mappingEntries, MappingEntry.MAPPING_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Sets the mapping entries associated with the basic entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPKs the primary keys of the mapping entries to be associated with the basic entry
	 */
	@Override
	public void setMappingEntries(long pk, long[] mappingEntryPKs) {
		Set<Long> newMappingEntryPKsSet = SetUtil.fromArray(mappingEntryPKs);
		Set<Long> oldMappingEntryPKsSet = SetUtil.fromArray(
			basicEntryToMappingEntryTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeMappingEntryPKsSet = new HashSet<Long>(
			oldMappingEntryPKsSet);

		removeMappingEntryPKsSet.removeAll(newMappingEntryPKsSet);

		basicEntryToMappingEntryTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeMappingEntryPKsSet));

		newMappingEntryPKsSet.removeAll(oldMappingEntryPKsSet);

		long companyId = 0;

		BasicEntry basicEntry = fetchByPrimaryKey(pk);

		if (basicEntry == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = basicEntry.getCompanyId();
		}

		basicEntryToMappingEntryTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newMappingEntryPKsSet));
	}

	/**
	 * Sets the mapping entries associated with the basic entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntries the mapping entries to be associated with the basic entry
	 */
	@Override
	public void setMappingEntries(long pk, List<MappingEntry> mappingEntries) {
		try {
			long[] mappingEntryPKs = new long[mappingEntries.size()];

			for (int i = 0; i < mappingEntries.size(); i++) {
				MappingEntry mappingEntry = mappingEntries.get(i);

				mappingEntryPKs[i] = mappingEntry.getPrimaryKey();
			}

			setMappingEntries(pk, mappingEntryPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "basicEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BASICENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BasicEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the basic entry persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		basicEntryToMappingEntryTableMapper = TableMapperFactory.getTableMapper(
			"MappingEntries_BasicEntries#basicEntryId",
			"MappingEntries_BasicEntries", "companyId", "basicEntryId",
			"mappingEntryId", this, MappingEntry.class);

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathFetchByC_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, true);

		BasicEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		BasicEntryUtil.setPersistence(null);

		entityCache.removeCache(BasicEntryImpl.class.getName());

		TableMapperFactory.removeTableMapper(
			"MappingEntries_BasicEntries#basicEntryId");
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
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	protected TableMapper<BasicEntry, MappingEntry>
		basicEntryToMappingEntryTableMapper;

	private static final String _SQL_SELECT_BASICENTRY =
		"SELECT basicEntry FROM BasicEntry basicEntry";

	private static final String _SQL_SELECT_BASICENTRY_WHERE =
		"SELECT basicEntry FROM BasicEntry basicEntry WHERE ";

	private static final String _SQL_COUNT_BASICENTRY =
		"SELECT COUNT(basicEntry) FROM BasicEntry basicEntry";

	private static final String _SQL_COUNT_BASICENTRY_WHERE =
		"SELECT COUNT(basicEntry) FROM BasicEntry basicEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "basicEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BasicEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BasicEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BasicEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:53313616