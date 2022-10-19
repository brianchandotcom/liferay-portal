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

import com.liferay.notification.exception.NoSuchNotificationTemplateRecipientException;
import com.liferay.notification.model.NotificationTemplateRecipient;
import com.liferay.notification.model.NotificationTemplateRecipientTable;
import com.liferay.notification.model.impl.NotificationTemplateRecipientImpl;
import com.liferay.notification.model.impl.NotificationTemplateRecipientModelImpl;
import com.liferay.notification.service.persistence.NotificationTemplateRecipientPersistence;
import com.liferay.notification.service.persistence.NotificationTemplateRecipientUtil;
import com.liferay.notification.service.persistence.impl.constants.NotificationPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
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
 * The persistence implementation for the notification template recipient service.
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
		NotificationTemplateRecipientPersistence.class, BasePersistence.class
	}
)
public class NotificationTemplateRecipientPersistenceImpl
	extends BasePersistenceImpl<NotificationTemplateRecipient>
	implements NotificationTemplateRecipientPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>NotificationTemplateRecipientUtil</code> to access the notification template recipient persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		NotificationTemplateRecipientImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByNotificationTemplateId;
	private FinderPath _finderPathCountByNotificationTemplateId;

	/**
	 * Returns the notification template recipient where notificationTemplateId = &#63; or throws a <code>NoSuchNotificationTemplateRecipientException</code> if it could not be found.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the matching notification template recipient
	 * @throws NoSuchNotificationTemplateRecipientException if a matching notification template recipient could not be found
	 */
	@Override
	public NotificationTemplateRecipient findByNotificationTemplateId(
			long notificationTemplateId)
		throws NoSuchNotificationTemplateRecipientException {

		NotificationTemplateRecipient notificationTemplateRecipient =
			fetchByNotificationTemplateId(notificationTemplateId);

		if (notificationTemplateRecipient == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("notificationTemplateId=");
			sb.append(notificationTemplateId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchNotificationTemplateRecipientException(
				sb.toString());
		}

		return notificationTemplateRecipient;
	}

	/**
	 * Returns the notification template recipient where notificationTemplateId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the matching notification template recipient, or <code>null</code> if a matching notification template recipient could not be found
	 */
	@Override
	public NotificationTemplateRecipient fetchByNotificationTemplateId(
		long notificationTemplateId) {

		return fetchByNotificationTemplateId(notificationTemplateId, true);
	}

	/**
	 * Returns the notification template recipient where notificationTemplateId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notification template recipient, or <code>null</code> if a matching notification template recipient could not be found
	 */
	@Override
	public NotificationTemplateRecipient fetchByNotificationTemplateId(
		long notificationTemplateId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {notificationTemplateId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByNotificationTemplateId, finderArgs, this);
		}

		if (result instanceof NotificationTemplateRecipient) {
			NotificationTemplateRecipient notificationTemplateRecipient =
				(NotificationTemplateRecipient)result;

			if (notificationTemplateId !=
					notificationTemplateRecipient.getNotificationTemplateId()) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_NOTIFICATIONTEMPLATERECIPIENT_WHERE);

			sb.append(
				_FINDER_COLUMN_NOTIFICATIONTEMPLATEID_NOTIFICATIONTEMPLATEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationTemplateId);

				List<NotificationTemplateRecipient> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByNotificationTemplateId,
							finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									notificationTemplateId
								};
							}

							_log.warn(
								"NotificationTemplateRecipientPersistenceImpl.fetchByNotificationTemplateId(long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					NotificationTemplateRecipient
						notificationTemplateRecipient = list.get(0);

					result = notificationTemplateRecipient;

					cacheResult(notificationTemplateRecipient);
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
			return (NotificationTemplateRecipient)result;
		}
	}

	/**
	 * Removes the notification template recipient where notificationTemplateId = &#63; from the database.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the notification template recipient that was removed
	 */
	@Override
	public NotificationTemplateRecipient removeByNotificationTemplateId(
			long notificationTemplateId)
		throws NoSuchNotificationTemplateRecipientException {

		NotificationTemplateRecipient notificationTemplateRecipient =
			findByNotificationTemplateId(notificationTemplateId);

		return remove(notificationTemplateRecipient);
	}

	/**
	 * Returns the number of notification template recipients where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the number of matching notification template recipients
	 */
	@Override
	public int countByNotificationTemplateId(long notificationTemplateId) {
		FinderPath finderPath = _finderPathCountByNotificationTemplateId;

		Object[] finderArgs = new Object[] {notificationTemplateId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_NOTIFICATIONTEMPLATERECIPIENT_WHERE);

			sb.append(
				_FINDER_COLUMN_NOTIFICATIONTEMPLATEID_NOTIFICATIONTEMPLATEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationTemplateId);

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
		_FINDER_COLUMN_NOTIFICATIONTEMPLATEID_NOTIFICATIONTEMPLATEID_2 =
			"notificationTemplateRecipient.notificationTemplateId = ?";

	public NotificationTemplateRecipientPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"notificationTemplateRecipientId", "NTemplateRecipientId");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(NotificationTemplateRecipient.class);

		setModelImplClass(NotificationTemplateRecipientImpl.class);
		setModelPKClass(long.class);

		setTable(NotificationTemplateRecipientTable.INSTANCE);
	}

	/**
	 * Caches the notification template recipient in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateRecipient the notification template recipient
	 */
	@Override
	public void cacheResult(
		NotificationTemplateRecipient notificationTemplateRecipient) {

		entityCache.putResult(
			NotificationTemplateRecipientImpl.class,
			notificationTemplateRecipient.getPrimaryKey(),
			notificationTemplateRecipient);

		finderCache.putResult(
			_finderPathFetchByNotificationTemplateId,
			new Object[] {
				notificationTemplateRecipient.getNotificationTemplateId()
			},
			notificationTemplateRecipient);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the notification template recipients in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateRecipients the notification template recipients
	 */
	@Override
	public void cacheResult(
		List<NotificationTemplateRecipient> notificationTemplateRecipients) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (notificationTemplateRecipients.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (NotificationTemplateRecipient notificationTemplateRecipient :
				notificationTemplateRecipients) {

			if (entityCache.getResult(
					NotificationTemplateRecipientImpl.class,
					notificationTemplateRecipient.getPrimaryKey()) == null) {

				cacheResult(notificationTemplateRecipient);
			}
		}
	}

	/**
	 * Clears the cache for all notification template recipients.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(NotificationTemplateRecipientImpl.class);

		finderCache.clearCache(NotificationTemplateRecipientImpl.class);
	}

	/**
	 * Clears the cache for the notification template recipient.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		NotificationTemplateRecipient notificationTemplateRecipient) {

		entityCache.removeResult(
			NotificationTemplateRecipientImpl.class,
			notificationTemplateRecipient);
	}

	@Override
	public void clearCache(
		List<NotificationTemplateRecipient> notificationTemplateRecipients) {

		for (NotificationTemplateRecipient notificationTemplateRecipient :
				notificationTemplateRecipients) {

			entityCache.removeResult(
				NotificationTemplateRecipientImpl.class,
				notificationTemplateRecipient);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(NotificationTemplateRecipientImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				NotificationTemplateRecipientImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		NotificationTemplateRecipientModelImpl
			notificationTemplateRecipientModelImpl) {

		Object[] args = new Object[] {
			notificationTemplateRecipientModelImpl.getNotificationTemplateId()
		};

		finderCache.putResult(
			_finderPathCountByNotificationTemplateId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByNotificationTemplateId, args,
			notificationTemplateRecipientModelImpl);
	}

	/**
	 * Creates a new notification template recipient with the primary key. Does not add the notification template recipient to the database.
	 *
	 * @param notificationTemplateRecipientId the primary key for the new notification template recipient
	 * @return the new notification template recipient
	 */
	@Override
	public NotificationTemplateRecipient create(
		long notificationTemplateRecipientId) {

		NotificationTemplateRecipient notificationTemplateRecipient =
			new NotificationTemplateRecipientImpl();

		notificationTemplateRecipient.setNew(true);
		notificationTemplateRecipient.setPrimaryKey(
			notificationTemplateRecipientId);

		notificationTemplateRecipient.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return notificationTemplateRecipient;
	}

	/**
	 * Removes the notification template recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationTemplateRecipientId the primary key of the notification template recipient
	 * @return the notification template recipient that was removed
	 * @throws NoSuchNotificationTemplateRecipientException if a notification template recipient with the primary key could not be found
	 */
	@Override
	public NotificationTemplateRecipient remove(
			long notificationTemplateRecipientId)
		throws NoSuchNotificationTemplateRecipientException {

		return remove((Serializable)notificationTemplateRecipientId);
	}

	/**
	 * Removes the notification template recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the notification template recipient
	 * @return the notification template recipient that was removed
	 * @throws NoSuchNotificationTemplateRecipientException if a notification template recipient with the primary key could not be found
	 */
	@Override
	public NotificationTemplateRecipient remove(Serializable primaryKey)
		throws NoSuchNotificationTemplateRecipientException {

		Session session = null;

		try {
			session = openSession();

			NotificationTemplateRecipient notificationTemplateRecipient =
				(NotificationTemplateRecipient)session.get(
					NotificationTemplateRecipientImpl.class, primaryKey);

			if (notificationTemplateRecipient == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationTemplateRecipientException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(notificationTemplateRecipient);
		}
		catch (NoSuchNotificationTemplateRecipientException
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
	protected NotificationTemplateRecipient removeImpl(
		NotificationTemplateRecipient notificationTemplateRecipient) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(notificationTemplateRecipient)) {
				notificationTemplateRecipient =
					(NotificationTemplateRecipient)session.get(
						NotificationTemplateRecipientImpl.class,
						notificationTemplateRecipient.getPrimaryKeyObj());
			}

			if (notificationTemplateRecipient != null) {
				session.delete(notificationTemplateRecipient);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (notificationTemplateRecipient != null) {
			clearCache(notificationTemplateRecipient);
		}

		return notificationTemplateRecipient;
	}

	@Override
	public NotificationTemplateRecipient updateImpl(
		NotificationTemplateRecipient notificationTemplateRecipient) {

		boolean isNew = notificationTemplateRecipient.isNew();

		if (!(notificationTemplateRecipient instanceof
				NotificationTemplateRecipientModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					notificationTemplateRecipient.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					notificationTemplateRecipient);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in notificationTemplateRecipient proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom NotificationTemplateRecipient implementation " +
					notificationTemplateRecipient.getClass());
		}

		NotificationTemplateRecipientModelImpl
			notificationTemplateRecipientModelImpl =
				(NotificationTemplateRecipientModelImpl)
					notificationTemplateRecipient;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (notificationTemplateRecipient.getCreateDate() == null)) {
			if (serviceContext == null) {
				notificationTemplateRecipient.setCreateDate(date);
			}
			else {
				notificationTemplateRecipient.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!notificationTemplateRecipientModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				notificationTemplateRecipient.setModifiedDate(date);
			}
			else {
				notificationTemplateRecipient.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(notificationTemplateRecipient);
			}
			else {
				notificationTemplateRecipient =
					(NotificationTemplateRecipient)session.merge(
						notificationTemplateRecipient);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			NotificationTemplateRecipientImpl.class,
			notificationTemplateRecipientModelImpl, false, true);

		cacheUniqueFindersCache(notificationTemplateRecipientModelImpl);

		if (isNew) {
			notificationTemplateRecipient.setNew(false);
		}

		notificationTemplateRecipient.resetOriginalValues();

		return notificationTemplateRecipient;
	}

	/**
	 * Returns the notification template recipient with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the notification template recipient
	 * @return the notification template recipient
	 * @throws NoSuchNotificationTemplateRecipientException if a notification template recipient with the primary key could not be found
	 */
	@Override
	public NotificationTemplateRecipient findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchNotificationTemplateRecipientException {

		NotificationTemplateRecipient notificationTemplateRecipient =
			fetchByPrimaryKey(primaryKey);

		if (notificationTemplateRecipient == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationTemplateRecipientException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return notificationTemplateRecipient;
	}

	/**
	 * Returns the notification template recipient with the primary key or throws a <code>NoSuchNotificationTemplateRecipientException</code> if it could not be found.
	 *
	 * @param notificationTemplateRecipientId the primary key of the notification template recipient
	 * @return the notification template recipient
	 * @throws NoSuchNotificationTemplateRecipientException if a notification template recipient with the primary key could not be found
	 */
	@Override
	public NotificationTemplateRecipient findByPrimaryKey(
			long notificationTemplateRecipientId)
		throws NoSuchNotificationTemplateRecipientException {

		return findByPrimaryKey((Serializable)notificationTemplateRecipientId);
	}

	/**
	 * Returns the notification template recipient with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationTemplateRecipientId the primary key of the notification template recipient
	 * @return the notification template recipient, or <code>null</code> if a notification template recipient with the primary key could not be found
	 */
	@Override
	public NotificationTemplateRecipient fetchByPrimaryKey(
		long notificationTemplateRecipientId) {

		return fetchByPrimaryKey((Serializable)notificationTemplateRecipientId);
	}

	/**
	 * Returns all the notification template recipients.
	 *
	 * @return the notification template recipients
	 */
	@Override
	public List<NotificationTemplateRecipient> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notification template recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template recipients
	 * @param end the upper bound of the range of notification template recipients (not inclusive)
	 * @return the range of notification template recipients
	 */
	@Override
	public List<NotificationTemplateRecipient> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the notification template recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template recipients
	 * @param end the upper bound of the range of notification template recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification template recipients
	 */
	@Override
	public List<NotificationTemplateRecipient> findAll(
		int start, int end,
		OrderByComparator<NotificationTemplateRecipient> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notification template recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template recipients
	 * @param end the upper bound of the range of notification template recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification template recipients
	 */
	@Override
	public List<NotificationTemplateRecipient> findAll(
		int start, int end,
		OrderByComparator<NotificationTemplateRecipient> orderByComparator,
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

		List<NotificationTemplateRecipient> list = null;

		if (useFinderCache) {
			list = (List<NotificationTemplateRecipient>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_NOTIFICATIONTEMPLATERECIPIENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_NOTIFICATIONTEMPLATERECIPIENT;

				sql = sql.concat(
					NotificationTemplateRecipientModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<NotificationTemplateRecipient>)QueryUtil.list(
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
	 * Removes all the notification template recipients from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (NotificationTemplateRecipient notificationTemplateRecipient :
				findAll()) {

			remove(notificationTemplateRecipient);
		}
	}

	/**
	 * Returns the number of notification template recipients.
	 *
	 * @return the number of notification template recipients
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
					_SQL_COUNT_NOTIFICATIONTEMPLATERECIPIENT);

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
		return "NTemplateRecipientId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_NOTIFICATIONTEMPLATERECIPIENT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return NotificationTemplateRecipientModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the notification template recipient persistence.
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

		_finderPathFetchByNotificationTemplateId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByNotificationTemplateId",
			new String[] {Long.class.getName()},
			new String[] {"notificationTemplateId"}, true);

		_finderPathCountByNotificationTemplateId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByNotificationTemplateId",
			new String[] {Long.class.getName()},
			new String[] {"notificationTemplateId"}, false);

		_setNotificationTemplateRecipientUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setNotificationTemplateRecipientUtilPersistence(null);

		entityCache.removeCache(
			NotificationTemplateRecipientImpl.class.getName());
	}

	private void _setNotificationTemplateRecipientUtilPersistence(
		NotificationTemplateRecipientPersistence
			notificationTemplateRecipientPersistence) {

		try {
			Field field =
				NotificationTemplateRecipientUtil.class.getDeclaredField(
					"_persistence");

			field.setAccessible(true);

			field.set(null, notificationTemplateRecipientPersistence);
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

	private static final String _SQL_SELECT_NOTIFICATIONTEMPLATERECIPIENT =
		"SELECT notificationTemplateRecipient FROM NotificationTemplateRecipient notificationTemplateRecipient";

	private static final String
		_SQL_SELECT_NOTIFICATIONTEMPLATERECIPIENT_WHERE =
			"SELECT notificationTemplateRecipient FROM NotificationTemplateRecipient notificationTemplateRecipient WHERE ";

	private static final String _SQL_COUNT_NOTIFICATIONTEMPLATERECIPIENT =
		"SELECT COUNT(notificationTemplateRecipient) FROM NotificationTemplateRecipient notificationTemplateRecipient";

	private static final String _SQL_COUNT_NOTIFICATIONTEMPLATERECIPIENT_WHERE =
		"SELECT COUNT(notificationTemplateRecipient) FROM NotificationTemplateRecipient notificationTemplateRecipient WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"notificationTemplateRecipient.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No NotificationTemplateRecipient exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No NotificationTemplateRecipient exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationTemplateRecipientPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"notificationTemplateRecipientId", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}