/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchCompanyInfoException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyInfo;
import com.liferay.portal.kernel.model.CompanyInfoTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyInfoPersistence;
import com.liferay.portal.kernel.service.persistence.CompanyInfoUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.impl.CompanyInfoImpl;
import com.liferay.portal.model.impl.CompanyInfoModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the company info service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CompanyInfoPersistenceImpl
	extends BasePersistenceImpl<CompanyInfo> implements CompanyInfoPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CompanyInfoUtil</code> to access the company info persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CompanyInfoImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns the company info where companyId = &#63; or throws a <code>NoSuchCompanyInfoException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @return the matching company info
	 * @throws NoSuchCompanyInfoException if a matching company info could not be found
	 */
	@Override
	public CompanyInfo findByCompanyId(long companyId)
		throws NoSuchCompanyInfoException {

		CompanyInfo companyInfo = fetchByCompanyId(companyId);

		if (companyInfo == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCompanyInfoException(sb.toString());
		}

		return companyInfo;
	}

	/**
	 * Returns the company info where companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @return the matching company info, or <code>null</code> if a matching company info could not be found
	 */
	@Override
	public CompanyInfo fetchByCompanyId(long companyId) {
		return fetchByCompanyId(companyId, true);
	}

	/**
	 * Returns the company info where companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching company info, or <code>null</code> if a matching company info could not be found
	 */
	@Override
	public CompanyInfo fetchByCompanyId(
		long companyId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByCompanyId, finderArgs, this);
		}

		if (result instanceof CompanyInfo) {
			CompanyInfo companyInfo = (CompanyInfo)result;

			if (companyId != companyInfo.getCompanyId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_COMPANYINFO_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				List<CompanyInfo> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByCompanyId, finderArgs, list);
					}
				}
				else {
					CompanyInfo companyInfo = list.get(0);

					result = companyInfo;

					cacheResult(companyInfo);
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
			return (CompanyInfo)result;
		}
	}

	/**
	 * Removes the company info where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @return the company info that was removed
	 */
	@Override
	public CompanyInfo removeByCompanyId(long companyId)
		throws NoSuchCompanyInfoException {

		CompanyInfo companyInfo = findByCompanyId(companyId);

		return remove(companyInfo);
	}

	/**
	 * Returns the number of company infos where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching company infos
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMPANYINFO_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		"companyInfo.companyId = ?";

	public CompanyInfoPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("key", "key_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CompanyInfo.class);

		setModelImplClass(CompanyInfoImpl.class);
		setModelPKClass(long.class);

		setTable(CompanyInfoTable.INSTANCE);
	}

	/**
	 * Caches the company info in the entity cache if it is enabled.
	 *
	 * @param companyInfo the company info
	 */
	@Override
	public void cacheResult(CompanyInfo companyInfo) {
		EntityCacheUtil.putResult(
			CompanyInfoImpl.class, companyInfo.getPrimaryKey(), companyInfo);

		FinderCacheUtil.putResult(
			_finderPathFetchByCompanyId,
			new Object[] {companyInfo.getCompanyId()}, companyInfo);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the company infos in the entity cache if it is enabled.
	 *
	 * @param companyInfos the company infos
	 */
	@Override
	public void cacheResult(List<CompanyInfo> companyInfos) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (companyInfos.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CompanyInfo companyInfo : companyInfos) {
			if (EntityCacheUtil.getResult(
					CompanyInfoImpl.class, companyInfo.getPrimaryKey()) ==
						null) {

				cacheResult(companyInfo);
			}
		}
	}

	/**
	 * Clears the cache for all company infos.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(CompanyInfoImpl.class);

		FinderCacheUtil.clearCache(CompanyInfoImpl.class);
	}

	/**
	 * Clears the cache for the company info.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CompanyInfo companyInfo) {
		EntityCacheUtil.removeResult(CompanyInfoImpl.class, companyInfo);
	}

	@Override
	public void clearCache(List<CompanyInfo> companyInfos) {
		for (CompanyInfo companyInfo : companyInfos) {
			EntityCacheUtil.removeResult(CompanyInfoImpl.class, companyInfo);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(CompanyInfoImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(CompanyInfoImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CompanyInfoModelImpl companyInfoModelImpl) {

		Object[] args = new Object[] {companyInfoModelImpl.getCompanyId()};

		FinderCacheUtil.putResult(
			_finderPathCountByCompanyId, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByCompanyId, args, companyInfoModelImpl);
	}

	/**
	 * Creates a new company info with the primary key. Does not add the company info to the database.
	 *
	 * @param companyInfoId the primary key for the new company info
	 * @return the new company info
	 */
	@Override
	public CompanyInfo create(long companyInfoId) {
		CompanyInfo companyInfo = new CompanyInfoImpl();

		companyInfo.setNew(true);
		companyInfo.setPrimaryKey(companyInfoId);

		companyInfo.setCompanyId(CompanyThreadLocal.getCompanyId());

		return companyInfo;
	}

	/**
	 * Removes the company info with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param companyInfoId the primary key of the company info
	 * @return the company info that was removed
	 * @throws NoSuchCompanyInfoException if a company info with the primary key could not be found
	 */
	@Override
	public CompanyInfo remove(long companyInfoId)
		throws NoSuchCompanyInfoException {

		return remove((Serializable)companyInfoId);
	}

	/**
	 * Removes the company info with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the company info
	 * @return the company info that was removed
	 * @throws NoSuchCompanyInfoException if a company info with the primary key could not be found
	 */
	@Override
	public CompanyInfo remove(Serializable primaryKey)
		throws NoSuchCompanyInfoException {

		Session session = null;

		try {
			session = openSession();

			CompanyInfo companyInfo = (CompanyInfo)session.get(
				CompanyInfoImpl.class, primaryKey);

			if (companyInfo == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCompanyInfoException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(companyInfo);
		}
		catch (NoSuchCompanyInfoException noSuchEntityException) {
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
	protected CompanyInfo removeImpl(CompanyInfo companyInfo) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(companyInfo)) {
				companyInfo = (CompanyInfo)session.get(
					CompanyInfoImpl.class, companyInfo.getPrimaryKeyObj());
			}

			if (companyInfo != null) {
				session.delete(companyInfo);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (companyInfo != null) {
			clearCache(companyInfo);
		}

		return companyInfo;
	}

	@Override
	public CompanyInfo updateImpl(CompanyInfo companyInfo) {
		boolean isNew = companyInfo.isNew();

		if (!(companyInfo instanceof CompanyInfoModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(companyInfo.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(companyInfo);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in companyInfo proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CompanyInfo implementation " +
					companyInfo.getClass());
		}

		CompanyInfoModelImpl companyInfoModelImpl =
			(CompanyInfoModelImpl)companyInfo;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(companyInfo);
			}
			else {
				companyInfo = (CompanyInfo)session.merge(companyInfo);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			CompanyInfoImpl.class, companyInfoModelImpl, false, true);

		cacheUniqueFindersCache(companyInfoModelImpl);

		if (isNew) {
			companyInfo.setNew(false);
		}

		companyInfo.resetOriginalValues();

		return companyInfo;
	}

	/**
	 * Returns the company info with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the company info
	 * @return the company info
	 * @throws NoSuchCompanyInfoException if a company info with the primary key could not be found
	 */
	@Override
	public CompanyInfo findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCompanyInfoException {

		CompanyInfo companyInfo = fetchByPrimaryKey(primaryKey);

		if (companyInfo == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCompanyInfoException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return companyInfo;
	}

	/**
	 * Returns the company info with the primary key or throws a <code>NoSuchCompanyInfoException</code> if it could not be found.
	 *
	 * @param companyInfoId the primary key of the company info
	 * @return the company info
	 * @throws NoSuchCompanyInfoException if a company info with the primary key could not be found
	 */
	@Override
	public CompanyInfo findByPrimaryKey(long companyInfoId)
		throws NoSuchCompanyInfoException {

		return findByPrimaryKey((Serializable)companyInfoId);
	}

	/**
	 * Returns the company info with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param companyInfoId the primary key of the company info
	 * @return the company info, or <code>null</code> if a company info with the primary key could not be found
	 */
	@Override
	public CompanyInfo fetchByPrimaryKey(long companyInfoId) {
		return fetchByPrimaryKey((Serializable)companyInfoId);
	}

	/**
	 * Returns all the company infos.
	 *
	 * @return the company infos
	 */
	@Override
	public List<CompanyInfo> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the company infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CompanyInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of company infos
	 * @param end the upper bound of the range of company infos (not inclusive)
	 * @return the range of company infos
	 */
	@Override
	public List<CompanyInfo> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the company infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CompanyInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of company infos
	 * @param end the upper bound of the range of company infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of company infos
	 */
	@Override
	public List<CompanyInfo> findAll(
		int start, int end, OrderByComparator<CompanyInfo> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the company infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CompanyInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of company infos
	 * @param end the upper bound of the range of company infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of company infos
	 */
	@Override
	public List<CompanyInfo> findAll(
		int start, int end, OrderByComparator<CompanyInfo> orderByComparator,
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

		List<CompanyInfo> list = null;

		if (useFinderCache) {
			list = (List<CompanyInfo>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMPANYINFO);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMPANYINFO;

				sql = sql.concat(CompanyInfoModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CompanyInfo>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the company infos from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CompanyInfo companyInfo : findAll()) {
			remove(companyInfo);
		}
	}

	/**
	 * Returns the number of company infos.
	 *
	 * @return the number of company infos
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_COMPANYINFO);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "companyInfoId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMPANYINFO;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CompanyInfoModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the company info persistence.
	 */
	public void afterPropertiesSet() {
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

		_finderPathFetchByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		CompanyInfoUtil.setPersistence(this);
	}

	public void destroy() {
		CompanyInfoUtil.setPersistence(null);

		EntityCacheUtil.removeCache(CompanyInfoImpl.class.getName());
	}

	private static final String _SQL_SELECT_COMPANYINFO =
		"SELECT companyInfo FROM CompanyInfo companyInfo";

	private static final String _SQL_SELECT_COMPANYINFO_WHERE =
		"SELECT companyInfo FROM CompanyInfo companyInfo WHERE ";

	private static final String _SQL_COUNT_COMPANYINFO =
		"SELECT COUNT(companyInfo) FROM CompanyInfo companyInfo";

	private static final String _SQL_COUNT_COMPANYINFO_WHERE =
		"SELECT COUNT(companyInfo) FROM CompanyInfo companyInfo WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "companyInfo.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CompanyInfo exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CompanyInfo exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyInfoPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"key"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}