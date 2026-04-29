/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.persistence.impl;

import com.liferay.notification.exception.NoSuchNotificationRecipientException;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientTable;
import com.liferay.notification.model.impl.NotificationRecipientImpl;
import com.liferay.notification.model.impl.NotificationRecipientModelImpl;
import com.liferay.notification.service.persistence.NotificationRecipientPersistence;
import com.liferay.notification.service.persistence.NotificationRecipientUtil;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

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
 * The persistence implementation for the notification recipient service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @generated
 */
@Component(service = NotificationRecipientPersistence.class)
public class NotificationRecipientPersistenceImpl
	extends BasePersistenceImpl
		<NotificationRecipient, NoSuchNotificationRecipientException>
	implements NotificationRecipientPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>NotificationRecipientUtil</code> to access the notification recipient persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		NotificationRecipientImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<NotificationRecipient>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the notification recipients where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching notification recipients
	 */
	@Override
	public List<NotificationRecipient> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notification recipients where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @return the range of matching notification recipients
	 */
	@Override
	public List<NotificationRecipient> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notification recipients where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification recipients
	 */
	@Override
	public List<NotificationRecipient> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<NotificationRecipient> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notification recipients where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification recipients
	 */
	@Override
	public List<NotificationRecipient> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<NotificationRecipient> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first notification recipient in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching notification recipient could not be found
	 */
	@Override
	public NotificationRecipient findByUuid_First(
			String uuid,
			OrderByComparator<NotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException {

		NotificationRecipient notificationRecipient = fetchByUuid_First(
			uuid, orderByComparator);

		if (notificationRecipient != null) {
			return notificationRecipient;
		}

		throw new NoSuchNotificationRecipientException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first notification recipient in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient, or <code>null</code> if a matching notification recipient could not be found
	 */
	@Override
	public NotificationRecipient fetchByUuid_First(
		String uuid,
		OrderByComparator<NotificationRecipient> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the notification recipients where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of notification recipients where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching notification recipients
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<NotificationRecipient>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the notification recipients where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching notification recipients
	 */
	@Override
	public List<NotificationRecipient> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notification recipients where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @return the range of matching notification recipients
	 */
	@Override
	public List<NotificationRecipient> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notification recipients where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification recipients
	 */
	@Override
	public List<NotificationRecipient> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<NotificationRecipient> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notification recipients where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification recipients
	 */
	@Override
	public List<NotificationRecipient> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<NotificationRecipient> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notification recipient in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching notification recipient could not be found
	 */
	@Override
	public NotificationRecipient findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<NotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException {

		NotificationRecipient notificationRecipient = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (notificationRecipient != null) {
			return notificationRecipient;
		}

		throw new NoSuchNotificationRecipientException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first notification recipient in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient, or <code>null</code> if a matching notification recipient could not be found
	 */
	@Override
	public NotificationRecipient fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<NotificationRecipient> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the notification recipients where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		_collectionPersistenceFinderByUuid_C.remove(
			finderCache, new Object[] {uuid, companyId});
	}

	/**
	 * Returns the number of notification recipients where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching notification recipients
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	private FinderPath _finderPathFetchByClassPK;
	private UniquePersistenceFinder<NotificationRecipient>
		_uniquePersistenceFinderByClassPK;

	/**
	 * Returns the notification recipient where classPK = &#63; or throws a <code>NoSuchNotificationRecipientException</code> if it could not be found.
	 *
	 * @param classPK the class pk
	 * @return the matching notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching notification recipient could not be found
	 */
	@Override
	public NotificationRecipient findByClassPK(long classPK)
		throws NoSuchNotificationRecipientException {

		NotificationRecipient notificationRecipient = fetchByClassPK(classPK);

		if (notificationRecipient == null) {
			String message =
				_uniquePersistenceFinderByClassPK.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {classPK});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchNotificationRecipientException(message);
		}

		return notificationRecipient;
	}

	/**
	 * Returns the notification recipient where classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classPK the class pk
	 * @return the matching notification recipient, or <code>null</code> if a matching notification recipient could not be found
	 */
	@Override
	public NotificationRecipient fetchByClassPK(long classPK) {
		return fetchByClassPK(classPK, true);
	}

	/**
	 * Returns the notification recipient where classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notification recipient, or <code>null</code> if a matching notification recipient could not be found
	 */
	@Override
	public NotificationRecipient fetchByClassPK(
		long classPK, boolean useFinderCache) {

		return _uniquePersistenceFinderByClassPK.fetch(
			finderCache, new Object[] {classPK}, useFinderCache);
	}

	/**
	 * Removes the notification recipient where classPK = &#63; from the database.
	 *
	 * @param classPK the class pk
	 * @return the notification recipient that was removed
	 */
	@Override
	public NotificationRecipient removeByClassPK(long classPK)
		throws NoSuchNotificationRecipientException {

		NotificationRecipient notificationRecipient = findByClassPK(classPK);

		return remove(notificationRecipient);
	}

	/**
	 * Returns the number of notification recipients where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @return the number of matching notification recipients
	 */
	@Override
	public int countByClassPK(long classPK) {
		return _uniquePersistenceFinderByClassPK.count(
			finderCache, new Object[] {classPK});
	}

	public NotificationRecipientPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(NotificationRecipient.class);

		setModelImplClass(NotificationRecipientImpl.class);
		setModelPKClass(long.class);

		setTable(NotificationRecipientTable.INSTANCE);
	}

	/**
	 * Caches the notification recipient in the entity cache if it is enabled.
	 *
	 * @param notificationRecipient the notification recipient
	 */
	@Override
	public void cacheResult(NotificationRecipient notificationRecipient) {
		entityCache.putResult(
			NotificationRecipientImpl.class,
			notificationRecipient.getPrimaryKey(), notificationRecipient);

		finderCache.putResult(
			_finderPathFetchByClassPK,
			new Object[] {notificationRecipient.getClassPK()},
			notificationRecipient);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the notification recipients in the entity cache if it is enabled.
	 *
	 * @param notificationRecipients the notification recipients
	 */
	@Override
	public void cacheResult(
		List<NotificationRecipient> notificationRecipients) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (notificationRecipients.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (NotificationRecipient notificationRecipient :
				notificationRecipients) {

			if (entityCache.getResult(
					NotificationRecipientImpl.class,
					notificationRecipient.getPrimaryKey()) == null) {

				cacheResult(notificationRecipient);
			}
		}
	}

	protected void cacheUniqueFindersCache(
		NotificationRecipientModelImpl notificationRecipientModelImpl) {

		Object[] args = new Object[] {
			notificationRecipientModelImpl.getClassPK()
		};

		finderCache.putResult(
			_finderPathFetchByClassPK, args, notificationRecipientModelImpl);
	}

	/**
	 * Creates a new notification recipient with the primary key. Does not add the notification recipient to the database.
	 *
	 * @param notificationRecipientId the primary key for the new notification recipient
	 * @return the new notification recipient
	 */
	@Override
	public NotificationRecipient create(long notificationRecipientId) {
		NotificationRecipient notificationRecipient =
			new NotificationRecipientImpl();

		notificationRecipient.setNew(true);
		notificationRecipient.setPrimaryKey(notificationRecipientId);

		String uuid = PortalUUIDUtil.generate();

		notificationRecipient.setUuid(uuid);

		notificationRecipient.setCompanyId(CompanyThreadLocal.getCompanyId());

		return notificationRecipient;
	}

	/**
	 * Removes the notification recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationRecipientId the primary key of the notification recipient
	 * @return the notification recipient that was removed
	 * @throws NoSuchNotificationRecipientException if a notification recipient with the primary key could not be found
	 */
	@Override
	public NotificationRecipient remove(long notificationRecipientId)
		throws NoSuchNotificationRecipientException {

		return remove((Serializable)notificationRecipientId);
	}

	@Override
	protected NotificationRecipient removeImpl(
		NotificationRecipient notificationRecipient) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(notificationRecipient)) {
				notificationRecipient = (NotificationRecipient)session.get(
					NotificationRecipientImpl.class,
					notificationRecipient.getPrimaryKeyObj());
			}

			if (notificationRecipient != null) {
				session.delete(notificationRecipient);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (notificationRecipient != null) {
			clearCache(notificationRecipient);
		}

		return notificationRecipient;
	}

	@Override
	public NotificationRecipient updateImpl(
		NotificationRecipient notificationRecipient) {

		boolean isNew = notificationRecipient.isNew();

		if (!(notificationRecipient instanceof
				NotificationRecipientModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(notificationRecipient.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					notificationRecipient);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in notificationRecipient proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom NotificationRecipient implementation " +
					notificationRecipient.getClass());
		}

		NotificationRecipientModelImpl notificationRecipientModelImpl =
			(NotificationRecipientModelImpl)notificationRecipient;

		if (Validator.isNull(notificationRecipient.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			notificationRecipient.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (notificationRecipient.getCreateDate() == null)) {
			if (serviceContext == null) {
				notificationRecipient.setCreateDate(date);
			}
			else {
				notificationRecipient.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!notificationRecipientModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				notificationRecipient.setModifiedDate(date);
			}
			else {
				notificationRecipient.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(notificationRecipient);
			}
			else {
				notificationRecipient = (NotificationRecipient)session.merge(
					notificationRecipient);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			NotificationRecipientImpl.class, notificationRecipientModelImpl,
			false, true);

		cacheUniqueFindersCache(notificationRecipientModelImpl);

		if (isNew) {
			notificationRecipient.setNew(false);
		}

		notificationRecipient.resetOriginalValues();

		return notificationRecipient;
	}

	/**
	 * Returns the notification recipient with the primary key or throws a <code>NoSuchNotificationRecipientException</code> if it could not be found.
	 *
	 * @param notificationRecipientId the primary key of the notification recipient
	 * @return the notification recipient
	 * @throws NoSuchNotificationRecipientException if a notification recipient with the primary key could not be found
	 */
	@Override
	public NotificationRecipient findByPrimaryKey(long notificationRecipientId)
		throws NoSuchNotificationRecipientException {

		return findByPrimaryKey((Serializable)notificationRecipientId);
	}

	/**
	 * Returns the notification recipient with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationRecipientId the primary key of the notification recipient
	 * @return the notification recipient, or <code>null</code> if a notification recipient with the primary key could not be found
	 */
	@Override
	public NotificationRecipient fetchByPrimaryKey(
		long notificationRecipientId) {

		return fetchByPrimaryKey((Serializable)notificationRecipientId);
	}

	/**
	 * Returns all the notification recipients.
	 *
	 * @return the notification recipients
	 */
	@Override
	public List<NotificationRecipient> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notification recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @return the range of notification recipients
	 */
	@Override
	public List<NotificationRecipient> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the notification recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification recipients
	 */
	@Override
	public List<NotificationRecipient> findAll(
		int start, int end,
		OrderByComparator<NotificationRecipient> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notification recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification recipients
	 */
	@Override
	public List<NotificationRecipient> findAll(
		int start, int end,
		OrderByComparator<NotificationRecipient> orderByComparator,
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

		List<NotificationRecipient> list = null;

		if (useFinderCache) {
			list = (List<NotificationRecipient>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_NOTIFICATIONRECIPIENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_NOTIFICATIONRECIPIENT;

				sql = sql.concat(NotificationRecipientModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<NotificationRecipient>)QueryUtil.list(
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
	 * Removes all the notification recipients from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (NotificationRecipient notificationRecipient : findAll()) {
			remove(notificationRecipient);
		}
	}

	/**
	 * Returns the number of notification recipients.
	 *
	 * @return the number of notification recipients
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
					_SQL_COUNT_NOTIFICATIONRECIPIENT);

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
		return "notificationRecipientId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_NOTIFICATIONRECIPIENT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return NotificationRecipientModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the notification recipient persistence.
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

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_collectionPersistenceFinderByUuid = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByUuid,
			_finderPathWithoutPaginationFindByUuid, _finderPathCountByUuid,
			_SQL_SELECT_NOTIFICATIONRECIPIENT_WHERE,
			_SQL_COUNT_NOTIFICATIONRECIPIENT_WHERE,
			NotificationRecipientModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"notificationRecipient.", "uuid", FinderColumn.Type.STRING, "=",
				true, true, NotificationRecipient::getUuid));

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_collectionPersistenceFinderByUuid_C =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUuid_C,
				_finderPathWithoutPaginationFindByUuid_C,
				_finderPathCountByUuid_C,
				_SQL_SELECT_NOTIFICATIONRECIPIENT_WHERE,
				_SQL_COUNT_NOTIFICATIONRECIPIENT_WHERE,
				NotificationRecipientModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"notificationRecipient.", "uuid", FinderColumn.Type.STRING,
					"=", true, false, NotificationRecipient::getUuid),
				new FinderColumn<>(
					"notificationRecipient.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					NotificationRecipient::getCompanyId));

		_finderPathFetchByClassPK = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByClassPK",
			new String[] {Long.class.getName()}, new String[] {"classPK"},
			true);

		_uniquePersistenceFinderByClassPK = new UniquePersistenceFinder<>(
			this, _finderPathFetchByClassPK,
			_SQL_SELECT_NOTIFICATIONRECIPIENT_WHERE,
			new FinderColumn<>(
				"notificationRecipient.", "classPK", FinderColumn.Type.LONG,
				"=", true, true, NotificationRecipient::getClassPK));

		NotificationRecipientUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		NotificationRecipientUtil.setPersistence(null);

		entityCache.removeCache(NotificationRecipientImpl.class.getName());
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

	private static final String _SQL_SELECT_NOTIFICATIONRECIPIENT =
		"SELECT notificationRecipient FROM NotificationRecipient notificationRecipient";

	private static final String _SQL_SELECT_NOTIFICATIONRECIPIENT_WHERE =
		"SELECT notificationRecipient FROM NotificationRecipient notificationRecipient WHERE ";

	private static final String _SQL_COUNT_NOTIFICATIONRECIPIENT =
		"SELECT COUNT(notificationRecipient) FROM NotificationRecipient notificationRecipient";

	private static final String _SQL_COUNT_NOTIFICATIONRECIPIENT_WHERE =
		"SELECT COUNT(notificationRecipient) FROM NotificationRecipient notificationRecipient WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"notificationRecipient.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No NotificationRecipient exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationRecipientPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-926877720