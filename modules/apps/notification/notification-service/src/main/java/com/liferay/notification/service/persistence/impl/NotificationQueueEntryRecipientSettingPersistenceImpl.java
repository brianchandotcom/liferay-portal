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

import com.liferay.notification.exception.NoSuchNotificationQueueEntryRecipientSettingException;
import com.liferay.notification.model.NotificationQueueEntryRecipientSetting;
import com.liferay.notification.model.NotificationQueueEntryRecipientSettingTable;
import com.liferay.notification.model.impl.NotificationQueueEntryRecipientSettingImpl;
import com.liferay.notification.model.impl.NotificationQueueEntryRecipientSettingModelImpl;
import com.liferay.notification.service.persistence.NotificationQueueEntryRecipientSettingPersistence;
import com.liferay.notification.service.persistence.NotificationQueueEntryRecipientSettingUtil;
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
 * The persistence implementation for the notification queue entry recipient setting service.
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
		NotificationQueueEntryRecipientSettingPersistence.class,
		BasePersistence.class
	}
)
public class NotificationQueueEntryRecipientSettingPersistenceImpl
	extends BasePersistenceImpl<NotificationQueueEntryRecipientSetting>
	implements NotificationQueueEntryRecipientSettingPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>NotificationQueueEntryRecipientSettingUtil</code> to access the notification queue entry recipient setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		NotificationQueueEntryRecipientSettingImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public NotificationQueueEntryRecipientSettingPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"notificationQueueEntryRecipientSettingId",
			"NQueueEntryRecipientSettingId");
		dbColumnNames.put(
			"notificationQueueEntryRecipientId", "NQueueEntryRecipientId");

		setDBColumnNames(dbColumnNames);

		setModelClass(NotificationQueueEntryRecipientSetting.class);

		setModelImplClass(NotificationQueueEntryRecipientSettingImpl.class);
		setModelPKClass(long.class);

		setTable(NotificationQueueEntryRecipientSettingTable.INSTANCE);
	}

	/**
	 * Caches the notification queue entry recipient setting in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryRecipientSetting the notification queue entry recipient setting
	 */
	@Override
	public void cacheResult(
		NotificationQueueEntryRecipientSetting
			notificationQueueEntryRecipientSetting) {

		entityCache.putResult(
			NotificationQueueEntryRecipientSettingImpl.class,
			notificationQueueEntryRecipientSetting.getPrimaryKey(),
			notificationQueueEntryRecipientSetting);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the notification queue entry recipient settings in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryRecipientSettings the notification queue entry recipient settings
	 */
	@Override
	public void cacheResult(
		List<NotificationQueueEntryRecipientSetting>
			notificationQueueEntryRecipientSettings) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (notificationQueueEntryRecipientSettings.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (NotificationQueueEntryRecipientSetting
				notificationQueueEntryRecipientSetting :
					notificationQueueEntryRecipientSettings) {

			if (entityCache.getResult(
					NotificationQueueEntryRecipientSettingImpl.class,
					notificationQueueEntryRecipientSetting.getPrimaryKey()) ==
						null) {

				cacheResult(notificationQueueEntryRecipientSetting);
			}
		}
	}

	/**
	 * Clears the cache for all notification queue entry recipient settings.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(
			NotificationQueueEntryRecipientSettingImpl.class);

		finderCache.clearCache(
			NotificationQueueEntryRecipientSettingImpl.class);
	}

	/**
	 * Clears the cache for the notification queue entry recipient setting.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		NotificationQueueEntryRecipientSetting
			notificationQueueEntryRecipientSetting) {

		entityCache.removeResult(
			NotificationQueueEntryRecipientSettingImpl.class,
			notificationQueueEntryRecipientSetting);
	}

	@Override
	public void clearCache(
		List<NotificationQueueEntryRecipientSetting>
			notificationQueueEntryRecipientSettings) {

		for (NotificationQueueEntryRecipientSetting
				notificationQueueEntryRecipientSetting :
					notificationQueueEntryRecipientSettings) {

			entityCache.removeResult(
				NotificationQueueEntryRecipientSettingImpl.class,
				notificationQueueEntryRecipientSetting);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(
			NotificationQueueEntryRecipientSettingImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				NotificationQueueEntryRecipientSettingImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new notification queue entry recipient setting with the primary key. Does not add the notification queue entry recipient setting to the database.
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key for the new notification queue entry recipient setting
	 * @return the new notification queue entry recipient setting
	 */
	@Override
	public NotificationQueueEntryRecipientSetting create(
		long notificationQueueEntryRecipientSettingId) {

		NotificationQueueEntryRecipientSetting
			notificationQueueEntryRecipientSetting =
				new NotificationQueueEntryRecipientSettingImpl();

		notificationQueueEntryRecipientSetting.setNew(true);
		notificationQueueEntryRecipientSetting.setPrimaryKey(
			notificationQueueEntryRecipientSettingId);

		notificationQueueEntryRecipientSetting.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return notificationQueueEntryRecipientSetting;
	}

	/**
	 * Removes the notification queue entry recipient setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key of the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting that was removed
	 * @throws NoSuchNotificationQueueEntryRecipientSettingException if a notification queue entry recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationQueueEntryRecipientSetting remove(
			long notificationQueueEntryRecipientSettingId)
		throws NoSuchNotificationQueueEntryRecipientSettingException {

		return remove((Serializable)notificationQueueEntryRecipientSettingId);
	}

	/**
	 * Removes the notification queue entry recipient setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting that was removed
	 * @throws NoSuchNotificationQueueEntryRecipientSettingException if a notification queue entry recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationQueueEntryRecipientSetting remove(
			Serializable primaryKey)
		throws NoSuchNotificationQueueEntryRecipientSettingException {

		Session session = null;

		try {
			session = openSession();

			NotificationQueueEntryRecipientSetting
				notificationQueueEntryRecipientSetting =
					(NotificationQueueEntryRecipientSetting)session.get(
						NotificationQueueEntryRecipientSettingImpl.class,
						primaryKey);

			if (notificationQueueEntryRecipientSetting == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationQueueEntryRecipientSettingException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(notificationQueueEntryRecipientSetting);
		}
		catch (NoSuchNotificationQueueEntryRecipientSettingException
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
	protected NotificationQueueEntryRecipientSetting removeImpl(
		NotificationQueueEntryRecipientSetting
			notificationQueueEntryRecipientSetting) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(notificationQueueEntryRecipientSetting)) {
				notificationQueueEntryRecipientSetting =
					(NotificationQueueEntryRecipientSetting)session.get(
						NotificationQueueEntryRecipientSettingImpl.class,
						notificationQueueEntryRecipientSetting.
							getPrimaryKeyObj());
			}

			if (notificationQueueEntryRecipientSetting != null) {
				session.delete(notificationQueueEntryRecipientSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (notificationQueueEntryRecipientSetting != null) {
			clearCache(notificationQueueEntryRecipientSetting);
		}

		return notificationQueueEntryRecipientSetting;
	}

	@Override
	public NotificationQueueEntryRecipientSetting updateImpl(
		NotificationQueueEntryRecipientSetting
			notificationQueueEntryRecipientSetting) {

		boolean isNew = notificationQueueEntryRecipientSetting.isNew();

		if (!(notificationQueueEntryRecipientSetting instanceof
				NotificationQueueEntryRecipientSettingModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					notificationQueueEntryRecipientSetting.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					notificationQueueEntryRecipientSetting);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in notificationQueueEntryRecipientSetting proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom NotificationQueueEntryRecipientSetting implementation " +
					notificationQueueEntryRecipientSetting.getClass());
		}

		NotificationQueueEntryRecipientSettingModelImpl
			notificationQueueEntryRecipientSettingModelImpl =
				(NotificationQueueEntryRecipientSettingModelImpl)
					notificationQueueEntryRecipientSetting;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew &&
			(notificationQueueEntryRecipientSetting.getCreateDate() == null)) {

			if (serviceContext == null) {
				notificationQueueEntryRecipientSetting.setCreateDate(date);
			}
			else {
				notificationQueueEntryRecipientSetting.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!notificationQueueEntryRecipientSettingModelImpl.
				hasSetModifiedDate()) {

			if (serviceContext == null) {
				notificationQueueEntryRecipientSetting.setModifiedDate(date);
			}
			else {
				notificationQueueEntryRecipientSetting.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(notificationQueueEntryRecipientSetting);
			}
			else {
				notificationQueueEntryRecipientSetting =
					(NotificationQueueEntryRecipientSetting)session.merge(
						notificationQueueEntryRecipientSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			NotificationQueueEntryRecipientSettingImpl.class,
			notificationQueueEntryRecipientSetting, false, true);

		if (isNew) {
			notificationQueueEntryRecipientSetting.setNew(false);
		}

		notificationQueueEntryRecipientSetting.resetOriginalValues();

		return notificationQueueEntryRecipientSetting;
	}

	/**
	 * Returns the notification queue entry recipient setting with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting
	 * @throws NoSuchNotificationQueueEntryRecipientSettingException if a notification queue entry recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationQueueEntryRecipientSetting findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchNotificationQueueEntryRecipientSettingException {

		NotificationQueueEntryRecipientSetting
			notificationQueueEntryRecipientSetting = fetchByPrimaryKey(
				primaryKey);

		if (notificationQueueEntryRecipientSetting == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationQueueEntryRecipientSettingException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return notificationQueueEntryRecipientSetting;
	}

	/**
	 * Returns the notification queue entry recipient setting with the primary key or throws a <code>NoSuchNotificationQueueEntryRecipientSettingException</code> if it could not be found.
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key of the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting
	 * @throws NoSuchNotificationQueueEntryRecipientSettingException if a notification queue entry recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationQueueEntryRecipientSetting findByPrimaryKey(
			long notificationQueueEntryRecipientSettingId)
		throws NoSuchNotificationQueueEntryRecipientSettingException {

		return findByPrimaryKey(
			(Serializable)notificationQueueEntryRecipientSettingId);
	}

	/**
	 * Returns the notification queue entry recipient setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key of the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting, or <code>null</code> if a notification queue entry recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationQueueEntryRecipientSetting fetchByPrimaryKey(
		long notificationQueueEntryRecipientSettingId) {

		return fetchByPrimaryKey(
			(Serializable)notificationQueueEntryRecipientSettingId);
	}

	/**
	 * Returns all the notification queue entry recipient settings.
	 *
	 * @return the notification queue entry recipient settings
	 */
	@Override
	public List<NotificationQueueEntryRecipientSetting> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notification queue entry recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry recipient settings
	 * @param end the upper bound of the range of notification queue entry recipient settings (not inclusive)
	 * @return the range of notification queue entry recipient settings
	 */
	@Override
	public List<NotificationQueueEntryRecipientSetting> findAll(
		int start, int end) {

		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the notification queue entry recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry recipient settings
	 * @param end the upper bound of the range of notification queue entry recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification queue entry recipient settings
	 */
	@Override
	public List<NotificationQueueEntryRecipientSetting> findAll(
		int start, int end,
		OrderByComparator<NotificationQueueEntryRecipientSetting>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notification queue entry recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry recipient settings
	 * @param end the upper bound of the range of notification queue entry recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification queue entry recipient settings
	 */
	@Override
	public List<NotificationQueueEntryRecipientSetting> findAll(
		int start, int end,
		OrderByComparator<NotificationQueueEntryRecipientSetting>
			orderByComparator,
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

		List<NotificationQueueEntryRecipientSetting> list = null;

		if (useFinderCache) {
			list =
				(List<NotificationQueueEntryRecipientSetting>)
					finderCache.getResult(finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_NOTIFICATIONQUEUEENTRYRECIPIENTSETTING);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_NOTIFICATIONQUEUEENTRYRECIPIENTSETTING;

				sql = sql.concat(
					NotificationQueueEntryRecipientSettingModelImpl.
						ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list =
					(List<NotificationQueueEntryRecipientSetting>)
						QueryUtil.list(query, getDialect(), start, end);

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
	 * Removes all the notification queue entry recipient settings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (NotificationQueueEntryRecipientSetting
				notificationQueueEntryRecipientSetting : findAll()) {

			remove(notificationQueueEntryRecipientSetting);
		}
	}

	/**
	 * Returns the number of notification queue entry recipient settings.
	 *
	 * @return the number of notification queue entry recipient settings
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
					_SQL_COUNT_NOTIFICATIONQUEUEENTRYRECIPIENTSETTING);

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
		return "NQueueEntryRecipientSettingId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_NOTIFICATIONQUEUEENTRYRECIPIENTSETTING;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return NotificationQueueEntryRecipientSettingModelImpl.
			TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the notification queue entry recipient setting persistence.
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

		_setNotificationQueueEntryRecipientSettingUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setNotificationQueueEntryRecipientSettingUtilPersistence(null);

		entityCache.removeCache(
			NotificationQueueEntryRecipientSettingImpl.class.getName());
	}

	private void _setNotificationQueueEntryRecipientSettingUtilPersistence(
		NotificationQueueEntryRecipientSettingPersistence
			notificationQueueEntryRecipientSettingPersistence) {

		try {
			Field field =
				NotificationQueueEntryRecipientSettingUtil.class.
					getDeclaredField("_persistence");

			field.setAccessible(true);

			field.set(null, notificationQueueEntryRecipientSettingPersistence);
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

	private static final String
		_SQL_SELECT_NOTIFICATIONQUEUEENTRYRECIPIENTSETTING =
			"SELECT notificationQueueEntryRecipientSetting FROM NotificationQueueEntryRecipientSetting notificationQueueEntryRecipientSetting";

	private static final String
		_SQL_COUNT_NOTIFICATIONQUEUEENTRYRECIPIENTSETTING =
			"SELECT COUNT(notificationQueueEntryRecipientSetting) FROM NotificationQueueEntryRecipientSetting notificationQueueEntryRecipientSetting";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"notificationQueueEntryRecipientSetting.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No NotificationQueueEntryRecipientSetting exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationQueueEntryRecipientSettingPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {
			"notificationQueueEntryRecipientSettingId",
			"notificationQueueEntryRecipientId"
		});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}