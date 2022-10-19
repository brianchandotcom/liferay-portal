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

package com.liferay.notification.service.persistence.impl;

import com.liferay.notification.exception.NoSuchNotificationQueueEntryRecipientException;
import com.liferay.notification.model.NotificationQueueEntryRecipient;
import com.liferay.notification.model.NotificationQueueEntryRecipientTable;
import com.liferay.notification.model.impl.NotificationQueueEntryRecipientImpl;
import com.liferay.notification.model.impl.NotificationQueueEntryRecipientModelImpl;
import com.liferay.notification.service.persistence.NotificationQueueEntryRecipientPersistence;
import com.liferay.notification.service.persistence.NotificationQueueEntryRecipientUtil;
import com.liferay.notification.service.persistence.impl.constants.NotificationPersistenceConstants;
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
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the notification queue entry recipient service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @generated
 */
@Component(
	service = {
		NotificationQueueEntryRecipientPersistence.class, BasePersistence.class
	}
)
public class NotificationQueueEntryRecipientPersistenceImpl
	extends BasePersistenceImpl<NotificationQueueEntryRecipient>
	implements NotificationQueueEntryRecipientPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>NotificationQueueEntryRecipientUtil</code> to access the notification queue entry recipient persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		NotificationQueueEntryRecipientImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public NotificationQueueEntryRecipientPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"notificationQueueEntryRecipientId", "NQueueEntryRecipientId");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(NotificationQueueEntryRecipient.class);

		setModelImplClass(NotificationQueueEntryRecipientImpl.class);
		setModelPKClass(long.class);

		setTable(NotificationQueueEntryRecipientTable.INSTANCE);
	}

	/**
	 * Caches the notification queue entry recipient in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryRecipient the notification queue entry recipient
	 */
	@Override
	public void cacheResult(
		NotificationQueueEntryRecipient notificationQueueEntryRecipient) {

		entityCache.putResult(
			NotificationQueueEntryRecipientImpl.class,
			notificationQueueEntryRecipient.getPrimaryKey(),
			notificationQueueEntryRecipient);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the notification queue entry recipients in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryRecipients the notification queue entry recipients
	 */
	@Override
	public void cacheResult(
		List<NotificationQueueEntryRecipient>
			notificationQueueEntryRecipients) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (notificationQueueEntryRecipients.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (NotificationQueueEntryRecipient notificationQueueEntryRecipient :
				notificationQueueEntryRecipients) {

			if (entityCache.getResult(
					NotificationQueueEntryRecipientImpl.class,
					notificationQueueEntryRecipient.getPrimaryKey()) == null) {

				cacheResult(notificationQueueEntryRecipient);
			}
		}
	}

	/**
	 * Clears the cache for all notification queue entry recipients.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(NotificationQueueEntryRecipientImpl.class);

		finderCache.clearCache(NotificationQueueEntryRecipientImpl.class);
	}

	/**
	 * Clears the cache for the notification queue entry recipient.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		NotificationQueueEntryRecipient notificationQueueEntryRecipient) {

		entityCache.removeResult(
			NotificationQueueEntryRecipientImpl.class,
			notificationQueueEntryRecipient);
	}

	@Override
	public void clearCache(
		List<NotificationQueueEntryRecipient>
			notificationQueueEntryRecipients) {

		for (NotificationQueueEntryRecipient notificationQueueEntryRecipient :
				notificationQueueEntryRecipients) {

			entityCache.removeResult(
				NotificationQueueEntryRecipientImpl.class,
				notificationQueueEntryRecipient);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(NotificationQueueEntryRecipientImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				NotificationQueueEntryRecipientImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new notification queue entry recipient with the primary key. Does not add the notification queue entry recipient to the database.
	 *
	 * @param notificationQueueEntryRecipientId the primary key for the new notification queue entry recipient
	 * @return the new notification queue entry recipient
	 */
	@Override
	public NotificationQueueEntryRecipient create(
		long notificationQueueEntryRecipientId) {

		NotificationQueueEntryRecipient notificationQueueEntryRecipient =
			new NotificationQueueEntryRecipientImpl();

		notificationQueueEntryRecipient.setNew(true);
		notificationQueueEntryRecipient.setPrimaryKey(
			notificationQueueEntryRecipientId);

		notificationQueueEntryRecipient.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return notificationQueueEntryRecipient;
	}

	/**
	 * Removes the notification queue entry recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationQueueEntryRecipientId the primary key of the notification queue entry recipient
	 * @return the notification queue entry recipient that was removed
	 * @throws NoSuchNotificationQueueEntryRecipientException if a notification queue entry recipient with the primary key could not be found
	 */
	@Override
	public NotificationQueueEntryRecipient remove(
			long notificationQueueEntryRecipientId)
		throws NoSuchNotificationQueueEntryRecipientException {

		return remove((Serializable)notificationQueueEntryRecipientId);
	}

	/**
	 * Removes the notification queue entry recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the notification queue entry recipient
	 * @return the notification queue entry recipient that was removed
	 * @throws NoSuchNotificationQueueEntryRecipientException if a notification queue entry recipient with the primary key could not be found
	 */
	@Override
	public NotificationQueueEntryRecipient remove(Serializable primaryKey)
		throws NoSuchNotificationQueueEntryRecipientException {

		Session session = null;

		try {
			session = openSession();

			NotificationQueueEntryRecipient notificationQueueEntryRecipient =
				(NotificationQueueEntryRecipient)session.get(
					NotificationQueueEntryRecipientImpl.class, primaryKey);

			if (notificationQueueEntryRecipient == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationQueueEntryRecipientException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(notificationQueueEntryRecipient);
		}
		catch (NoSuchNotificationQueueEntryRecipientException
					noSuchEntityException) {

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
	protected NotificationQueueEntryRecipient removeImpl(
		NotificationQueueEntryRecipient notificationQueueEntryRecipient) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(notificationQueueEntryRecipient)) {
				notificationQueueEntryRecipient =
					(NotificationQueueEntryRecipient)session.get(
						NotificationQueueEntryRecipientImpl.class,
						notificationQueueEntryRecipient.getPrimaryKeyObj());
			}

			if (notificationQueueEntryRecipient != null) {
				session.delete(notificationQueueEntryRecipient);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (notificationQueueEntryRecipient != null) {
			clearCache(notificationQueueEntryRecipient);
		}

		return notificationQueueEntryRecipient;
	}

	@Override
	public NotificationQueueEntryRecipient updateImpl(
		NotificationQueueEntryRecipient notificationQueueEntryRecipient) {

		boolean isNew = notificationQueueEntryRecipient.isNew();

		if (!(notificationQueueEntryRecipient instanceof
				NotificationQueueEntryRecipientModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					notificationQueueEntryRecipient.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					notificationQueueEntryRecipient);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in notificationQueueEntryRecipient proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom NotificationQueueEntryRecipient implementation " +
					notificationQueueEntryRecipient.getClass());
		}

		NotificationQueueEntryRecipientModelImpl
			notificationQueueEntryRecipientModelImpl =
				(NotificationQueueEntryRecipientModelImpl)
					notificationQueueEntryRecipient;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew &&
			(notificationQueueEntryRecipient.getCreateDate() == null)) {

			if (serviceContext == null) {
				notificationQueueEntryRecipient.setCreateDate(date);
			}
			else {
				notificationQueueEntryRecipient.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!notificationQueueEntryRecipientModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				notificationQueueEntryRecipient.setModifiedDate(date);
			}
			else {
				notificationQueueEntryRecipient.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(notificationQueueEntryRecipient);
			}
			else {
				notificationQueueEntryRecipient =
					(NotificationQueueEntryRecipient)session.merge(
						notificationQueueEntryRecipient);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			NotificationQueueEntryRecipientImpl.class,
			notificationQueueEntryRecipient, false, true);

		if (isNew) {
			notificationQueueEntryRecipient.setNew(false);
		}

		notificationQueueEntryRecipient.resetOriginalValues();

		return notificationQueueEntryRecipient;
	}

	/**
	 * Returns the notification queue entry recipient with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the notification queue entry recipient
	 * @return the notification queue entry recipient
	 * @throws NoSuchNotificationQueueEntryRecipientException if a notification queue entry recipient with the primary key could not be found
	 */
	@Override
	public NotificationQueueEntryRecipient findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchNotificationQueueEntryRecipientException {

		NotificationQueueEntryRecipient notificationQueueEntryRecipient =
			fetchByPrimaryKey(primaryKey);

		if (notificationQueueEntryRecipient == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationQueueEntryRecipientException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return notificationQueueEntryRecipient;
	}

	/**
	 * Returns the notification queue entry recipient with the primary key or throws a <code>NoSuchNotificationQueueEntryRecipientException</code> if it could not be found.
	 *
	 * @param notificationQueueEntryRecipientId the primary key of the notification queue entry recipient
	 * @return the notification queue entry recipient
	 * @throws NoSuchNotificationQueueEntryRecipientException if a notification queue entry recipient with the primary key could not be found
	 */
	@Override
	public NotificationQueueEntryRecipient findByPrimaryKey(
			long notificationQueueEntryRecipientId)
		throws NoSuchNotificationQueueEntryRecipientException {

		return findByPrimaryKey(
			(Serializable)notificationQueueEntryRecipientId);
	}

	/**
	 * Returns the notification queue entry recipient with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationQueueEntryRecipientId the primary key of the notification queue entry recipient
	 * @return the notification queue entry recipient, or <code>null</code> if a notification queue entry recipient with the primary key could not be found
	 */
	@Override
	public NotificationQueueEntryRecipient fetchByPrimaryKey(
		long notificationQueueEntryRecipientId) {

		return fetchByPrimaryKey(
			(Serializable)notificationQueueEntryRecipientId);
	}

	/**
	 * Returns all the notification queue entry recipients.
	 *
	 * @return the notification queue entry recipients
	 */
	@Override
	public List<NotificationQueueEntryRecipient> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notification queue entry recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry recipients
	 * @param end the upper bound of the range of notification queue entry recipients (not inclusive)
	 * @return the range of notification queue entry recipients
	 */
	@Override
	public List<NotificationQueueEntryRecipient> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the notification queue entry recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry recipients
	 * @param end the upper bound of the range of notification queue entry recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification queue entry recipients
	 */
	@Override
	public List<NotificationQueueEntryRecipient> findAll(
		int start, int end,
		OrderByComparator<NotificationQueueEntryRecipient> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notification queue entry recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry recipients
	 * @param end the upper bound of the range of notification queue entry recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification queue entry recipients
	 */
	@Override
	public List<NotificationQueueEntryRecipient> findAll(
		int start, int end,
		OrderByComparator<NotificationQueueEntryRecipient> orderByComparator,
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

		List<NotificationQueueEntryRecipient> list = null;

		if (useFinderCache) {
			list = (List<NotificationQueueEntryRecipient>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_NOTIFICATIONQUEUEENTRYRECIPIENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_NOTIFICATIONQUEUEENTRYRECIPIENT;

				sql = sql.concat(
					NotificationQueueEntryRecipientModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<NotificationQueueEntryRecipient>)QueryUtil.list(
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
	 * Removes all the notification queue entry recipients from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (NotificationQueueEntryRecipient notificationQueueEntryRecipient :
				findAll()) {

			remove(notificationQueueEntryRecipient);
		}
	}

	/**
	 * Returns the number of notification queue entry recipients.
	 *
	 * @return the number of notification queue entry recipients
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_NOTIFICATIONQUEUEENTRYRECIPIENT);

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
		return "NQueueEntryRecipientId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_NOTIFICATIONQUEUEENTRYRECIPIENT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return NotificationQueueEntryRecipientModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the notification queue entry recipient persistence.
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

		_setNotificationQueueEntryRecipientUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setNotificationQueueEntryRecipientUtilPersistence(null);

		entityCache.removeCache(
			NotificationQueueEntryRecipientImpl.class.getName());
	}

	private void _setNotificationQueueEntryRecipientUtilPersistence(
		NotificationQueueEntryRecipientPersistence
			notificationQueueEntryRecipientPersistence) {

		try {
			Field field =
				NotificationQueueEntryRecipientUtil.class.getDeclaredField(
					"_persistence");

			field.setAccessible(true);

			field.set(null, notificationQueueEntryRecipientPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = NotificationPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = NotificationPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = NotificationPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_NOTIFICATIONQUEUEENTRYRECIPIENT =
		"SELECT notificationQueueEntryRecipient FROM NotificationQueueEntryRecipient notificationQueueEntryRecipient";

	private static final String _SQL_COUNT_NOTIFICATIONQUEUEENTRYRECIPIENT =
		"SELECT COUNT(notificationQueueEntryRecipient) FROM NotificationQueueEntryRecipient notificationQueueEntryRecipient";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"notificationQueueEntryRecipient.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No NotificationQueueEntryRecipient exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationQueueEntryRecipientPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"notificationQueueEntryRecipientId", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}