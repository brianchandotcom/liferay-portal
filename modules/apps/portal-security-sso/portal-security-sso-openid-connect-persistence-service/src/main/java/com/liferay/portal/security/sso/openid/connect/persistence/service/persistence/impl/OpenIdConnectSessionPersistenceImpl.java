/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.security.sso.openid.connect.persistence.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.security.sso.openid.connect.persistence.exception.NoSuchSessionException;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSessionTable;
import com.liferay.portal.security.sso.openid.connect.persistence.model.impl.OpenIdConnectSessionImpl;
import com.liferay.portal.security.sso.openid.connect.persistence.model.impl.OpenIdConnectSessionModelImpl;
import com.liferay.portal.security.sso.openid.connect.persistence.service.persistence.OpenIdConnectSessionPersistence;
import com.liferay.portal.security.sso.openid.connect.persistence.service.persistence.impl.constants.OpenIdConnectPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the open ID connect session service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Arthur Chan
 * @generated
 */
@Component(
	service = {OpenIdConnectSessionPersistence.class, BasePersistence.class}
)
public class OpenIdConnectSessionPersistenceImpl
	extends BasePersistenceImpl<OpenIdConnectSession>
	implements OpenIdConnectSessionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>OpenIdConnectSessionUtil</code> to access the open ID connect session persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		OpenIdConnectSessionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public OpenIdConnectSessionPersistenceImpl() {
		setModelClass(OpenIdConnectSession.class);

		setModelImplClass(OpenIdConnectSessionImpl.class);
		setModelPKClass(long.class);

		setTable(OpenIdConnectSessionTable.INSTANCE);
	}

	/**
	 * Caches the open ID connect session in the entity cache if it is enabled.
	 *
	 * @param openIdConnectSession the open ID connect session
	 */
	@Override
	public void cacheResult(OpenIdConnectSession openIdConnectSession) {
		entityCache.putResult(
			OpenIdConnectSessionImpl.class,
			openIdConnectSession.getPrimaryKey(), openIdConnectSession);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the open ID connect sessions in the entity cache if it is enabled.
	 *
	 * @param openIdConnectSessions the open ID connect sessions
	 */
	@Override
	public void cacheResult(List<OpenIdConnectSession> openIdConnectSessions) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (openIdConnectSessions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (OpenIdConnectSession openIdConnectSession :
				openIdConnectSessions) {

			if (entityCache.getResult(
					OpenIdConnectSessionImpl.class,
					openIdConnectSession.getPrimaryKey()) == null) {

				cacheResult(openIdConnectSession);
			}
		}
	}

	/**
	 * Clears the cache for all open ID connect sessions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(OpenIdConnectSessionImpl.class);

		finderCache.clearCache(OpenIdConnectSessionImpl.class);
	}

	/**
	 * Clears the cache for the open ID connect session.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OpenIdConnectSession openIdConnectSession) {
		entityCache.removeResult(
			OpenIdConnectSessionImpl.class, openIdConnectSession);
	}

	@Override
	public void clearCache(List<OpenIdConnectSession> openIdConnectSessions) {
		for (OpenIdConnectSession openIdConnectSession :
				openIdConnectSessions) {

			entityCache.removeResult(
				OpenIdConnectSessionImpl.class, openIdConnectSession);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(OpenIdConnectSessionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				OpenIdConnectSessionImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new open ID connect session with the primary key. Does not add the open ID connect session to the database.
	 *
	 * @param openIdConnectSessionId the primary key for the new open ID connect session
	 * @return the new open ID connect session
	 */
	@Override
	public OpenIdConnectSession create(long openIdConnectSessionId) {
		OpenIdConnectSession openIdConnectSession =
			new OpenIdConnectSessionImpl();

		openIdConnectSession.setNew(true);
		openIdConnectSession.setPrimaryKey(openIdConnectSessionId);

		openIdConnectSession.setCompanyId(CompanyThreadLocal.getCompanyId());

		return openIdConnectSession;
	}

	/**
	 * Removes the open ID connect session with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param openIdConnectSessionId the primary key of the open ID connect session
	 * @return the open ID connect session that was removed
	 * @throws NoSuchSessionException if a open ID connect session with the primary key could not be found
	 */
	@Override
	public OpenIdConnectSession remove(long openIdConnectSessionId)
		throws NoSuchSessionException {

		return remove((Serializable)openIdConnectSessionId);
	}

	/**
	 * Removes the open ID connect session with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the open ID connect session
	 * @return the open ID connect session that was removed
	 * @throws NoSuchSessionException if a open ID connect session with the primary key could not be found
	 */
	@Override
	public OpenIdConnectSession remove(Serializable primaryKey)
		throws NoSuchSessionException {

		Session session = null;

		try {
			session = openSession();

			OpenIdConnectSession openIdConnectSession =
				(OpenIdConnectSession)session.get(
					OpenIdConnectSessionImpl.class, primaryKey);

			if (openIdConnectSession == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSessionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(openIdConnectSession);
		}
		catch (NoSuchSessionException noSuchEntityException) {
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
	protected OpenIdConnectSession removeImpl(
		OpenIdConnectSession openIdConnectSession) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(openIdConnectSession)) {
				openIdConnectSession = (OpenIdConnectSession)session.get(
					OpenIdConnectSessionImpl.class,
					openIdConnectSession.getPrimaryKeyObj());
			}

			if (openIdConnectSession != null) {
				session.delete(openIdConnectSession);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (openIdConnectSession != null) {
			clearCache(openIdConnectSession);
		}

		return openIdConnectSession;
	}

	@Override
	public OpenIdConnectSession updateImpl(
		OpenIdConnectSession openIdConnectSession) {

		boolean isNew = openIdConnectSession.isNew();

		if (!(openIdConnectSession instanceof OpenIdConnectSessionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(openIdConnectSession.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					openIdConnectSession);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in openIdConnectSession proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom OpenIdConnectSession implementation " +
					openIdConnectSession.getClass());
		}

		OpenIdConnectSessionModelImpl openIdConnectSessionModelImpl =
			(OpenIdConnectSessionModelImpl)openIdConnectSession;

		if (!openIdConnectSessionModelImpl.hasSetModifiedDate()) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				openIdConnectSession.setModifiedDate(date);
			}
			else {
				openIdConnectSession.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(openIdConnectSession);
			}
			else {
				openIdConnectSession = (OpenIdConnectSession)session.merge(
					openIdConnectSession);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			OpenIdConnectSessionImpl.class, openIdConnectSession, false, true);

		if (isNew) {
			openIdConnectSession.setNew(false);
		}

		openIdConnectSession.resetOriginalValues();

		return openIdConnectSession;
	}

	/**
	 * Returns the open ID connect session with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the open ID connect session
	 * @return the open ID connect session
	 * @throws NoSuchSessionException if a open ID connect session with the primary key could not be found
	 */
	@Override
	public OpenIdConnectSession findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSessionException {

		OpenIdConnectSession openIdConnectSession = fetchByPrimaryKey(
			primaryKey);

		if (openIdConnectSession == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSessionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return openIdConnectSession;
	}

	/**
	 * Returns the open ID connect session with the primary key or throws a <code>NoSuchSessionException</code> if it could not be found.
	 *
	 * @param openIdConnectSessionId the primary key of the open ID connect session
	 * @return the open ID connect session
	 * @throws NoSuchSessionException if a open ID connect session with the primary key could not be found
	 */
	@Override
	public OpenIdConnectSession findByPrimaryKey(long openIdConnectSessionId)
		throws NoSuchSessionException {

		return findByPrimaryKey((Serializable)openIdConnectSessionId);
	}

	/**
	 * Returns the open ID connect session with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param openIdConnectSessionId the primary key of the open ID connect session
	 * @return the open ID connect session, or <code>null</code> if a open ID connect session with the primary key could not be found
	 */
	@Override
	public OpenIdConnectSession fetchByPrimaryKey(long openIdConnectSessionId) {
		return fetchByPrimaryKey((Serializable)openIdConnectSessionId);
	}

	/**
	 * Returns all the open ID connect sessions.
	 *
	 * @return the open ID connect sessions
	 */
	@Override
	public List<OpenIdConnectSession> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the open ID connect sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @return the range of open ID connect sessions
	 */
	@Override
	public List<OpenIdConnectSession> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the open ID connect sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of open ID connect sessions
	 */
	@Override
	public List<OpenIdConnectSession> findAll(
		int start, int end,
		OrderByComparator<OpenIdConnectSession> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the open ID connect sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of open ID connect sessions
	 */
	@Override
	public List<OpenIdConnectSession> findAll(
		int start, int end,
		OrderByComparator<OpenIdConnectSession> orderByComparator,
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

		List<OpenIdConnectSession> list = null;

		if (useFinderCache) {
			list = (List<OpenIdConnectSession>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OPENIDCONNECTSESSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OPENIDCONNECTSESSION;

				sql = sql.concat(OpenIdConnectSessionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<OpenIdConnectSession>)QueryUtil.list(
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
	 * Removes all the open ID connect sessions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OpenIdConnectSession openIdConnectSession : findAll()) {
			remove(openIdConnectSession);
		}
	}

	/**
	 * Returns the number of open ID connect sessions.
	 *
	 * @return the number of open ID connect sessions
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
					_SQL_COUNT_OPENIDCONNECTSESSION);

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
		return "openIdConnectSessionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OPENIDCONNECTSESSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return OpenIdConnectSessionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the open ID connect session persistence.
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
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(OpenIdConnectSessionImpl.class.getName());
	}

	@Override
	@Reference(
		target = OpenIdConnectPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = OpenIdConnectPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OpenIdConnectPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_OPENIDCONNECTSESSION =
		"SELECT openIdConnectSession FROM OpenIdConnectSession openIdConnectSession";

	private static final String _SQL_COUNT_OPENIDCONNECTSESSION =
		"SELECT COUNT(openIdConnectSession) FROM OpenIdConnectSession openIdConnectSession";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"openIdConnectSession.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No OpenIdConnectSession exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectSessionPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private OpenIdConnectSessionModelArgumentsResolver
		_openIdConnectSessionModelArgumentsResolver;

}