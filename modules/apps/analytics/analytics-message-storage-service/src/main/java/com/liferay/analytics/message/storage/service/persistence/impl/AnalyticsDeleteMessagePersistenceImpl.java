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

package com.liferay.analytics.message.storage.service.persistence.impl;

import com.liferay.analytics.message.storage.exception.NoSuchDeleteMessageException;
import com.liferay.analytics.message.storage.model.AnalyticsDeleteMessage;
import com.liferay.analytics.message.storage.model.AnalyticsDeleteMessageTable;
import com.liferay.analytics.message.storage.model.impl.AnalyticsDeleteMessageImpl;
import com.liferay.analytics.message.storage.model.impl.AnalyticsDeleteMessageModelImpl;
import com.liferay.analytics.message.storage.service.persistence.AnalyticsDeleteMessagePersistence;
import com.liferay.analytics.message.storage.service.persistence.AnalyticsDeleteMessageUtil;
import com.liferay.analytics.message.storage.service.persistence.impl.constants.AnalyticsPersistenceConstants;
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

import java.io.Serializable;

import java.lang.reflect.Field;
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
 * The persistence implementation for the analytics delete message service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {AnalyticsDeleteMessagePersistence.class, BasePersistence.class}
)
public class AnalyticsDeleteMessagePersistenceImpl
	extends BasePersistenceImpl<AnalyticsDeleteMessage>
	implements AnalyticsDeleteMessagePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AnalyticsDeleteMessageUtil</code> to access the analytics delete message persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AnalyticsDeleteMessageImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public AnalyticsDeleteMessagePersistenceImpl() {
		setModelClass(AnalyticsDeleteMessage.class);

		setModelImplClass(AnalyticsDeleteMessageImpl.class);
		setModelPKClass(long.class);

		setTable(AnalyticsDeleteMessageTable.INSTANCE);
	}

	/**
	 * Caches the analytics delete message in the entity cache if it is enabled.
	 *
	 * @param analyticsDeleteMessage the analytics delete message
	 */
	@Override
	public void cacheResult(AnalyticsDeleteMessage analyticsDeleteMessage) {
		entityCache.putResult(
			AnalyticsDeleteMessageImpl.class,
			analyticsDeleteMessage.getPrimaryKey(), analyticsDeleteMessage);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the analytics delete messages in the entity cache if it is enabled.
	 *
	 * @param analyticsDeleteMessages the analytics delete messages
	 */
	@Override
	public void cacheResult(
		List<AnalyticsDeleteMessage> analyticsDeleteMessages) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (analyticsDeleteMessages.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AnalyticsDeleteMessage analyticsDeleteMessage :
				analyticsDeleteMessages) {

			if (entityCache.getResult(
					AnalyticsDeleteMessageImpl.class,
					analyticsDeleteMessage.getPrimaryKey()) == null) {

				cacheResult(analyticsDeleteMessage);
			}
		}
	}

	/**
	 * Clears the cache for all analytics delete messages.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AnalyticsDeleteMessageImpl.class);

		finderCache.clearCache(AnalyticsDeleteMessageImpl.class);
	}

	/**
	 * Clears the cache for the analytics delete message.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AnalyticsDeleteMessage analyticsDeleteMessage) {
		entityCache.removeResult(
			AnalyticsDeleteMessageImpl.class, analyticsDeleteMessage);
	}

	@Override
	public void clearCache(
		List<AnalyticsDeleteMessage> analyticsDeleteMessages) {

		for (AnalyticsDeleteMessage analyticsDeleteMessage :
				analyticsDeleteMessages) {

			entityCache.removeResult(
				AnalyticsDeleteMessageImpl.class, analyticsDeleteMessage);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AnalyticsDeleteMessageImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AnalyticsDeleteMessageImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new analytics delete message with the primary key. Does not add the analytics delete message to the database.
	 *
	 * @param analyticsDeleteMessageId the primary key for the new analytics delete message
	 * @return the new analytics delete message
	 */
	@Override
	public AnalyticsDeleteMessage create(long analyticsDeleteMessageId) {
		AnalyticsDeleteMessage analyticsDeleteMessage =
			new AnalyticsDeleteMessageImpl();

		analyticsDeleteMessage.setNew(true);
		analyticsDeleteMessage.setPrimaryKey(analyticsDeleteMessageId);

		analyticsDeleteMessage.setCompanyId(CompanyThreadLocal.getCompanyId());

		return analyticsDeleteMessage;
	}

	/**
	 * Removes the analytics delete message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsDeleteMessageId the primary key of the analytics delete message
	 * @return the analytics delete message that was removed
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	@Override
	public AnalyticsDeleteMessage remove(long analyticsDeleteMessageId)
		throws NoSuchDeleteMessageException {

		return remove((Serializable)analyticsDeleteMessageId);
	}

	/**
	 * Removes the analytics delete message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the analytics delete message
	 * @return the analytics delete message that was removed
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	@Override
	public AnalyticsDeleteMessage remove(Serializable primaryKey)
		throws NoSuchDeleteMessageException {

		Session session = null;

		try {
			session = openSession();

			AnalyticsDeleteMessage analyticsDeleteMessage =
				(AnalyticsDeleteMessage)session.get(
					AnalyticsDeleteMessageImpl.class, primaryKey);

			if (analyticsDeleteMessage == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDeleteMessageException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(analyticsDeleteMessage);
		}
		catch (NoSuchDeleteMessageException noSuchEntityException) {
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
	protected AnalyticsDeleteMessage removeImpl(
		AnalyticsDeleteMessage analyticsDeleteMessage) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(analyticsDeleteMessage)) {
				analyticsDeleteMessage = (AnalyticsDeleteMessage)session.get(
					AnalyticsDeleteMessageImpl.class,
					analyticsDeleteMessage.getPrimaryKeyObj());
			}

			if (analyticsDeleteMessage != null) {
				session.delete(analyticsDeleteMessage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (analyticsDeleteMessage != null) {
			clearCache(analyticsDeleteMessage);
		}

		return analyticsDeleteMessage;
	}

	@Override
	public AnalyticsDeleteMessage updateImpl(
		AnalyticsDeleteMessage analyticsDeleteMessage) {

		boolean isNew = analyticsDeleteMessage.isNew();

		if (!(analyticsDeleteMessage instanceof
				AnalyticsDeleteMessageModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(analyticsDeleteMessage.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					analyticsDeleteMessage);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in analyticsDeleteMessage proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AnalyticsDeleteMessage implementation " +
					analyticsDeleteMessage.getClass());
		}

		AnalyticsDeleteMessageModelImpl analyticsDeleteMessageModelImpl =
			(AnalyticsDeleteMessageModelImpl)analyticsDeleteMessage;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (analyticsDeleteMessage.getCreateDate() == null)) {
			if (serviceContext == null) {
				analyticsDeleteMessage.setCreateDate(date);
			}
			else {
				analyticsDeleteMessage.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!analyticsDeleteMessageModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				analyticsDeleteMessage.setModifiedDate(date);
			}
			else {
				analyticsDeleteMessage.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(analyticsDeleteMessage);
			}
			else {
				analyticsDeleteMessage = (AnalyticsDeleteMessage)session.merge(
					analyticsDeleteMessage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			AnalyticsDeleteMessageImpl.class, analyticsDeleteMessage, false,
			true);

		if (isNew) {
			analyticsDeleteMessage.setNew(false);
		}

		analyticsDeleteMessage.resetOriginalValues();

		return analyticsDeleteMessage;
	}

	/**
	 * Returns the analytics delete message with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the analytics delete message
	 * @return the analytics delete message
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	@Override
	public AnalyticsDeleteMessage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDeleteMessageException {

		AnalyticsDeleteMessage analyticsDeleteMessage = fetchByPrimaryKey(
			primaryKey);

		if (analyticsDeleteMessage == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDeleteMessageException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return analyticsDeleteMessage;
	}

	/**
	 * Returns the analytics delete message with the primary key or throws a <code>NoSuchDeleteMessageException</code> if it could not be found.
	 *
	 * @param analyticsDeleteMessageId the primary key of the analytics delete message
	 * @return the analytics delete message
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	@Override
	public AnalyticsDeleteMessage findByPrimaryKey(
			long analyticsDeleteMessageId)
		throws NoSuchDeleteMessageException {

		return findByPrimaryKey((Serializable)analyticsDeleteMessageId);
	}

	/**
	 * Returns the analytics delete message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param analyticsDeleteMessageId the primary key of the analytics delete message
	 * @return the analytics delete message, or <code>null</code> if a analytics delete message with the primary key could not be found
	 */
	@Override
	public AnalyticsDeleteMessage fetchByPrimaryKey(
		long analyticsDeleteMessageId) {

		return fetchByPrimaryKey((Serializable)analyticsDeleteMessageId);
	}

	/**
	 * Returns all the analytics delete messages.
	 *
	 * @return the analytics delete messages
	 */
	@Override
	public List<AnalyticsDeleteMessage> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the analytics delete messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @return the range of analytics delete messages
	 */
	@Override
	public List<AnalyticsDeleteMessage> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the analytics delete messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of analytics delete messages
	 */
	@Override
	public List<AnalyticsDeleteMessage> findAll(
		int start, int end,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the analytics delete messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of analytics delete messages
	 */
	@Override
	public List<AnalyticsDeleteMessage> findAll(
		int start, int end,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator,
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

		List<AnalyticsDeleteMessage> list = null;

		if (useFinderCache) {
			list = (List<AnalyticsDeleteMessage>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ANALYTICSDELETEMESSAGE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ANALYTICSDELETEMESSAGE;

				sql = sql.concat(AnalyticsDeleteMessageModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AnalyticsDeleteMessage>)QueryUtil.list(
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
	 * Removes all the analytics delete messages from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AnalyticsDeleteMessage analyticsDeleteMessage : findAll()) {
			remove(analyticsDeleteMessage);
		}
	}

	/**
	 * Returns the number of analytics delete messages.
	 *
	 * @return the number of analytics delete messages
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
					_SQL_COUNT_ANALYTICSDELETEMESSAGE);

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
		return "analyticsDeleteMessageId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ANALYTICSDELETEMESSAGE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AnalyticsDeleteMessageModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the analytics delete message persistence.
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

		_setAnalyticsDeleteMessageUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setAnalyticsDeleteMessageUtilPersistence(null);

		entityCache.removeCache(AnalyticsDeleteMessageImpl.class.getName());
	}

	private void _setAnalyticsDeleteMessageUtilPersistence(
		AnalyticsDeleteMessagePersistence analyticsDeleteMessagePersistence) {

		try {
			Field field = AnalyticsDeleteMessageUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, analyticsDeleteMessagePersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = AnalyticsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AnalyticsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AnalyticsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ANALYTICSDELETEMESSAGE =
		"SELECT analyticsDeleteMessage FROM AnalyticsDeleteMessage analyticsDeleteMessage";

	private static final String _SQL_COUNT_ANALYTICSDELETEMESSAGE =
		"SELECT COUNT(analyticsDeleteMessage) FROM AnalyticsDeleteMessage analyticsDeleteMessage";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"analyticsDeleteMessage.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AnalyticsDeleteMessage exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsDeleteMessagePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private AnalyticsDeleteMessageModelArgumentsResolver
		_analyticsDeleteMessageModelArgumentsResolver;

}