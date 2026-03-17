/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.exception.NoSuchScoreException;
import com.liferay.change.tracking.model.CTScore;
import com.liferay.change.tracking.model.CTScoreTable;
import com.liferay.change.tracking.model.impl.CTScoreImpl;
import com.liferay.change.tracking.model.impl.CTScoreModelImpl;
import com.liferay.change.tracking.service.persistence.CTScorePersistence;
import com.liferay.change.tracking.service.persistence.CTScoreUtil;
import com.liferay.change.tracking.service.persistence.impl.constants.CTPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the ct score service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = CTScorePersistence.class)
public class CTScorePersistenceImpl
	extends BasePersistenceImpl<CTScore> implements CTScorePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTScoreUtil</code> to access the ct score persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTScoreImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByCtCollectionId;

	/**
	 * Returns the ct score where ctCollectionId = &#63; or throws a <code>NoSuchScoreException</code> if it could not be found.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct score
	 * @throws NoSuchScoreException if a matching ct score could not be found
	 */
	@Override
	public CTScore findByCtCollectionId(long ctCollectionId)
		throws NoSuchScoreException {

		CTScore ctScore = fetchByCtCollectionId(ctCollectionId);

		if (ctScore == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("ctCollectionId=");
			sb.append(ctCollectionId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchScoreException(sb.toString());
		}

		return ctScore;
	}

	/**
	 * Returns the ct score where ctCollectionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct score, or <code>null</code> if a matching ct score could not be found
	 */
	@Override
	public CTScore fetchByCtCollectionId(long ctCollectionId) {
		return fetchByCtCollectionId(ctCollectionId, true);
	}

	/**
	 * Returns the ct score where ctCollectionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ct score, or <code>null</code> if a matching ct score could not be found
	 */
	@Override
	public CTScore fetchByCtCollectionId(
		long ctCollectionId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {ctCollectionId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCtCollectionId, finderArgs, this);
		}

		if (result instanceof CTScore) {
			CTScore ctScore = (CTScore)result;

			if (ctCollectionId != ctScore.getCtCollectionId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_CTSCORE_WHERE);

			sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				List<CTScore> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCtCollectionId, finderArgs, list);
					}
				}
				else {
					CTScore ctScore = list.get(0);

					result = ctScore;

					cacheResult(ctScore);
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
			return (CTScore)result;
		}
	}

	/**
	 * Removes the ct score where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the ct score that was removed
	 */
	@Override
	public CTScore removeByCtCollectionId(long ctCollectionId)
		throws NoSuchScoreException {

		CTScore ctScore = findByCtCollectionId(ctCollectionId);

		return remove(ctScore);
	}

	/**
	 * Returns the number of ct scores where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct scores
	 */
	@Override
	public int countByCtCollectionId(long ctCollectionId) {
		CTScore ctScore = fetchByCtCollectionId(ctCollectionId);

		if (ctScore == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2 =
		"ctScore.ctCollectionId = ?";

	public CTScorePersistenceImpl() {
		setModelClass(CTScore.class);

		setModelImplClass(CTScoreImpl.class);
		setModelPKClass(long.class);

		setTable(CTScoreTable.INSTANCE);
	}

	/**
	 * Caches the ct score in the entity cache if it is enabled.
	 *
	 * @param ctScore the ct score
	 */
	@Override
	public void cacheResult(CTScore ctScore) {
		entityCache.putResult(
			CTScoreImpl.class, ctScore.getPrimaryKey(), ctScore);

		finderCache.putResult(
			_finderPathFetchByCtCollectionId,
			new Object[] {ctScore.getCtCollectionId()}, ctScore);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the ct scores in the entity cache if it is enabled.
	 *
	 * @param ctScores the ct scores
	 */
	@Override
	public void cacheResult(List<CTScore> ctScores) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ctScores.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CTScore ctScore : ctScores) {
			if (entityCache.getResult(
					CTScoreImpl.class, ctScore.getPrimaryKey()) == null) {

				cacheResult(ctScore);
			}
		}
	}

	/**
	 * Clears the cache for all ct scores.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTScoreImpl.class);

		finderCache.clearCache(CTScoreImpl.class);
	}

	/**
	 * Clears the cache for the ct score.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTScore ctScore) {
		entityCache.removeResult(CTScoreImpl.class, ctScore);
	}

	@Override
	public void clearCache(List<CTScore> ctScores) {
		for (CTScore ctScore : ctScores) {
			entityCache.removeResult(CTScoreImpl.class, ctScore);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CTScoreImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CTScoreImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(CTScoreModelImpl ctScoreModelImpl) {
		Object[] args = new Object[] {ctScoreModelImpl.getCtCollectionId()};

		finderCache.putResult(
			_finderPathFetchByCtCollectionId, args, ctScoreModelImpl);
	}

	/**
	 * Creates a new ct score with the primary key. Does not add the ct score to the database.
	 *
	 * @param ctScoreId the primary key for the new ct score
	 * @return the new ct score
	 */
	@Override
	public CTScore create(long ctScoreId) {
		CTScore ctScore = new CTScoreImpl();

		ctScore.setNew(true);
		ctScore.setPrimaryKey(ctScoreId);

		ctScore.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ctScore;
	}

	/**
	 * Removes the ct score with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctScoreId the primary key of the ct score
	 * @return the ct score that was removed
	 * @throws NoSuchScoreException if a ct score with the primary key could not be found
	 */
	@Override
	public CTScore remove(long ctScoreId) throws NoSuchScoreException {
		return remove((Serializable)ctScoreId);
	}

	/**
	 * Removes the ct score with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ct score
	 * @return the ct score that was removed
	 * @throws NoSuchScoreException if a ct score with the primary key could not be found
	 */
	@Override
	public CTScore remove(Serializable primaryKey) throws NoSuchScoreException {
		Session session = null;

		try {
			session = openSession();

			CTScore ctScore = (CTScore)session.get(
				CTScoreImpl.class, primaryKey);

			if (ctScore == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchScoreException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ctScore);
		}
		catch (NoSuchScoreException noSuchEntityException) {
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
	protected CTScore removeImpl(CTScore ctScore) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctScore)) {
				ctScore = (CTScore)session.get(
					CTScoreImpl.class, ctScore.getPrimaryKeyObj());
			}

			if (ctScore != null) {
				session.delete(ctScore);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ctScore != null) {
			clearCache(ctScore);
		}

		return ctScore;
	}

	@Override
	public CTScore updateImpl(CTScore ctScore) {
		boolean isNew = ctScore.isNew();

		if (!(ctScore instanceof CTScoreModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctScore.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(ctScore);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctScore proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTScore implementation " +
					ctScore.getClass());
		}

		CTScoreModelImpl ctScoreModelImpl = (CTScoreModelImpl)ctScore;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ctScore);
			}
			else {
				ctScore = (CTScore)session.merge(ctScore);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(CTScoreImpl.class, ctScoreModelImpl, false, true);

		cacheUniqueFindersCache(ctScoreModelImpl);

		if (isNew) {
			ctScore.setNew(false);
		}

		ctScore.resetOriginalValues();

		return ctScore;
	}

	/**
	 * Returns the ct score with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ct score
	 * @return the ct score
	 * @throws NoSuchScoreException if a ct score with the primary key could not be found
	 */
	@Override
	public CTScore findByPrimaryKey(Serializable primaryKey)
		throws NoSuchScoreException {

		CTScore ctScore = fetchByPrimaryKey(primaryKey);

		if (ctScore == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchScoreException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ctScore;
	}

	/**
	 * Returns the ct score with the primary key or throws a <code>NoSuchScoreException</code> if it could not be found.
	 *
	 * @param ctScoreId the primary key of the ct score
	 * @return the ct score
	 * @throws NoSuchScoreException if a ct score with the primary key could not be found
	 */
	@Override
	public CTScore findByPrimaryKey(long ctScoreId)
		throws NoSuchScoreException {

		return findByPrimaryKey((Serializable)ctScoreId);
	}

	/**
	 * Returns the ct score with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctScoreId the primary key of the ct score
	 * @return the ct score, or <code>null</code> if a ct score with the primary key could not be found
	 */
	@Override
	public CTScore fetchByPrimaryKey(long ctScoreId) {
		return fetchByPrimaryKey((Serializable)ctScoreId);
	}

	/**
	 * Returns all the ct scores.
	 *
	 * @return the ct scores
	 */
	@Override
	public List<CTScore> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct scores.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTScoreModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct scores
	 * @param end the upper bound of the range of ct scores (not inclusive)
	 * @return the range of ct scores
	 */
	@Override
	public List<CTScore> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct scores.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTScoreModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct scores
	 * @param end the upper bound of the range of ct scores (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct scores
	 */
	@Override
	public List<CTScore> findAll(
		int start, int end, OrderByComparator<CTScore> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct scores.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTScoreModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct scores
	 * @param end the upper bound of the range of ct scores (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct scores
	 */
	@Override
	public List<CTScore> findAll(
		int start, int end, OrderByComparator<CTScore> orderByComparator,
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

		List<CTScore> list = null;

		if (useFinderCache) {
			list = (List<CTScore>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CTSCORE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CTSCORE;

				sql = sql.concat(CTScoreModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CTScore>)QueryUtil.list(
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
	 * Removes all the ct scores from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTScore ctScore : findAll()) {
			remove(ctScore);
		}
	}

	/**
	 * Returns the number of ct scores.
	 *
	 * @return the number of ct scores
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CTSCORE);

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
		return "ctScoreId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTSCORE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTScoreModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ct score persistence.
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

		_finderPathFetchByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCtCollectionId",
			new String[] {Long.class.getName()},
			new String[] {"ctCollectionId"}, true);

		CTScoreUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CTScoreUtil.setPersistence(null);

		entityCache.removeCache(CTScoreImpl.class.getName());
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CTSCORE =
		"SELECT ctScore FROM CTScore ctScore";

	private static final String _SQL_SELECT_CTSCORE_WHERE =
		"SELECT ctScore FROM CTScore ctScore WHERE ";

	private static final String _SQL_COUNT_CTSCORE =
		"SELECT COUNT(ctScore) FROM CTScore ctScore";

	private static final String _SQL_COUNT_CTSCORE_WHERE =
		"SELECT COUNT(ctScore) FROM CTScore ctScore WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ctScore.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CTScore exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTScore exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTScorePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// SB-Hash:953465792