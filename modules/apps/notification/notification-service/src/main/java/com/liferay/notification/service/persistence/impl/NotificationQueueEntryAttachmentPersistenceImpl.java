/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.persistence.impl;

import com.liferay.notification.exception.NoSuchNotificationQueueEntryAttachmentException;
import com.liferay.notification.model.NotificationQueueEntryAttachment;
import com.liferay.notification.model.NotificationQueueEntryAttachmentTable;
import com.liferay.notification.model.impl.NotificationQueueEntryAttachmentImpl;
import com.liferay.notification.model.impl.NotificationQueueEntryAttachmentModelImpl;
import com.liferay.notification.service.persistence.NotificationQueueEntryAttachmentPersistence;
import com.liferay.notification.service.persistence.NotificationQueueEntryAttachmentUtil;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the notification queue entry attachment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @generated
 */
@Component(service = NotificationQueueEntryAttachmentPersistence.class)
public class NotificationQueueEntryAttachmentPersistenceImpl
	extends BasePersistenceImpl<NotificationQueueEntryAttachment>
	implements NotificationQueueEntryAttachmentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>NotificationQueueEntryAttachmentUtil</code> to access the notification queue entry attachment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		NotificationQueueEntryAttachmentImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByNotificationQueueEntryId;
	private FinderPath
		_finderPathWithoutPaginationFindByNotificationQueueEntryId;
	private FinderPath _finderPathCountByNotificationQueueEntryId;

	/**
	 * Returns all the notification queue entry attachments where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @return the matching notification queue entry attachments
	 */
	@Override
	public List<NotificationQueueEntryAttachment>
		findByNotificationQueueEntryId(long notificationQueueEntryId) {

		return findByNotificationQueueEntryId(
			notificationQueueEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the notification queue entry attachments where notificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @return the range of matching notification queue entry attachments
	 */
	@Override
	public List<NotificationQueueEntryAttachment>
		findByNotificationQueueEntryId(
			long notificationQueueEntryId, int start, int end) {

		return findByNotificationQueueEntryId(
			notificationQueueEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notification queue entry attachments where notificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification queue entry attachments
	 */
	@Override
	public List<NotificationQueueEntryAttachment>
		findByNotificationQueueEntryId(
			long notificationQueueEntryId, int start, int end,
			OrderByComparator<NotificationQueueEntryAttachment>
				orderByComparator) {

		return findByNotificationQueueEntryId(
			notificationQueueEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notification queue entry attachments where notificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification queue entry attachments
	 */
	@Override
	public List<NotificationQueueEntryAttachment>
		findByNotificationQueueEntryId(
			long notificationQueueEntryId, int start, int end,
			OrderByComparator<NotificationQueueEntryAttachment>
				orderByComparator,
			boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByNotificationQueueEntryId;
				finderArgs = new Object[] {notificationQueueEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByNotificationQueueEntryId;
			finderArgs = new Object[] {
				notificationQueueEntryId, start, end, orderByComparator
			};
		}

		List<NotificationQueueEntryAttachment> list = null;

		if (useFinderCache) {
			list =
				(List<NotificationQueueEntryAttachment>)finderCache.getResult(
					finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (NotificationQueueEntryAttachment
						notificationQueueEntryAttachment : list) {

					if (notificationQueueEntryId !=
							notificationQueueEntryAttachment.
								getNotificationQueueEntryId()) {

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

			sb.append(_SQL_SELECT_NOTIFICATIONQUEUEENTRYATTACHMENT_WHERE);

			sb.append(
				_FINDER_COLUMN_NOTIFICATIONQUEUEENTRYID_NOTIFICATIONQUEUEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					NotificationQueueEntryAttachmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationQueueEntryId);

				list = (List<NotificationQueueEntryAttachment>)QueryUtil.list(
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
	 * Returns the first notification queue entry attachment in the ordered set where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry attachment
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a matching notification queue entry attachment could not be found
	 */
	@Override
	public NotificationQueueEntryAttachment
			findByNotificationQueueEntryId_First(
				long notificationQueueEntryId,
				OrderByComparator<NotificationQueueEntryAttachment>
					orderByComparator)
		throws NoSuchNotificationQueueEntryAttachmentException {

		NotificationQueueEntryAttachment notificationQueueEntryAttachment =
			fetchByNotificationQueueEntryId_First(
				notificationQueueEntryId, orderByComparator);

		if (notificationQueueEntryAttachment != null) {
			return notificationQueueEntryAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("notificationQueueEntryId=");
		sb.append(notificationQueueEntryId);

		sb.append("}");

		throw new NoSuchNotificationQueueEntryAttachmentException(
			sb.toString());
	}

	/**
	 * Returns the first notification queue entry attachment in the ordered set where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry attachment, or <code>null</code> if a matching notification queue entry attachment could not be found
	 */
	@Override
	public NotificationQueueEntryAttachment
		fetchByNotificationQueueEntryId_First(
			long notificationQueueEntryId,
			OrderByComparator<NotificationQueueEntryAttachment>
				orderByComparator) {

		List<NotificationQueueEntryAttachment> list =
			findByNotificationQueueEntryId(
				notificationQueueEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last notification queue entry attachment in the ordered set where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry attachment
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a matching notification queue entry attachment could not be found
	 */
	@Override
	public NotificationQueueEntryAttachment findByNotificationQueueEntryId_Last(
			long notificationQueueEntryId,
			OrderByComparator<NotificationQueueEntryAttachment>
				orderByComparator)
		throws NoSuchNotificationQueueEntryAttachmentException {

		NotificationQueueEntryAttachment notificationQueueEntryAttachment =
			fetchByNotificationQueueEntryId_Last(
				notificationQueueEntryId, orderByComparator);

		if (notificationQueueEntryAttachment != null) {
			return notificationQueueEntryAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("notificationQueueEntryId=");
		sb.append(notificationQueueEntryId);

		sb.append("}");

		throw new NoSuchNotificationQueueEntryAttachmentException(
			sb.toString());
	}

	/**
	 * Returns the last notification queue entry attachment in the ordered set where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry attachment, or <code>null</code> if a matching notification queue entry attachment could not be found
	 */
	@Override
	public NotificationQueueEntryAttachment
		fetchByNotificationQueueEntryId_Last(
			long notificationQueueEntryId,
			OrderByComparator<NotificationQueueEntryAttachment>
				orderByComparator) {

		int count = countByNotificationQueueEntryId(notificationQueueEntryId);

		if (count == 0) {
			return null;
		}

		List<NotificationQueueEntryAttachment> list =
			findByNotificationQueueEntryId(
				notificationQueueEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the notification queue entry attachments before and after the current notification queue entry attachment in the ordered set where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key of the current notification queue entry attachment
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification queue entry attachment
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a notification queue entry attachment with the primary key could not be found
	 */
	@Override
	public NotificationQueueEntryAttachment[]
			findByNotificationQueueEntryId_PrevAndNext(
				long notificationQueueEntryAttachmentId,
				long notificationQueueEntryId,
				OrderByComparator<NotificationQueueEntryAttachment>
					orderByComparator)
		throws NoSuchNotificationQueueEntryAttachmentException {

		NotificationQueueEntryAttachment notificationQueueEntryAttachment =
			findByPrimaryKey(notificationQueueEntryAttachmentId);

		Session session = null;

		try {
			session = openSession();

			NotificationQueueEntryAttachment[] array =
				new NotificationQueueEntryAttachmentImpl[3];

			array[0] = getByNotificationQueueEntryId_PrevAndNext(
				session, notificationQueueEntryAttachment,
				notificationQueueEntryId, orderByComparator, true);

			array[1] = notificationQueueEntryAttachment;

			array[2] = getByNotificationQueueEntryId_PrevAndNext(
				session, notificationQueueEntryAttachment,
				notificationQueueEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected NotificationQueueEntryAttachment
		getByNotificationQueueEntryId_PrevAndNext(
			Session session,
			NotificationQueueEntryAttachment notificationQueueEntryAttachment,
			long notificationQueueEntryId,
			OrderByComparator<NotificationQueueEntryAttachment>
				orderByComparator,
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

		sb.append(_SQL_SELECT_NOTIFICATIONQUEUEENTRYATTACHMENT_WHERE);

		sb.append(
			_FINDER_COLUMN_NOTIFICATIONQUEUEENTRYID_NOTIFICATIONQUEUEENTRYID_2);

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
			sb.append(NotificationQueueEntryAttachmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(notificationQueueEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						notificationQueueEntryAttachment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<NotificationQueueEntryAttachment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the notification queue entry attachments where notificationQueueEntryId = &#63; from the database.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 */
	@Override
	public void removeByNotificationQueueEntryId(
		long notificationQueueEntryId) {

		for (NotificationQueueEntryAttachment notificationQueueEntryAttachment :
				findByNotificationQueueEntryId(
					notificationQueueEntryId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(notificationQueueEntryAttachment);
		}
	}

	/**
	 * Returns the number of notification queue entry attachments where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @return the number of matching notification queue entry attachments
	 */
	@Override
	public int countByNotificationQueueEntryId(long notificationQueueEntryId) {
		FinderPath finderPath = _finderPathCountByNotificationQueueEntryId;

		Object[] finderArgs = new Object[] {notificationQueueEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_NOTIFICATIONQUEUEENTRYATTACHMENT_WHERE);

			sb.append(
				_FINDER_COLUMN_NOTIFICATIONQUEUEENTRYID_NOTIFICATIONQUEUEENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationQueueEntryId);

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
		_FINDER_COLUMN_NOTIFICATIONQUEUEENTRYID_NOTIFICATIONQUEUEENTRYID_2 =
			"notificationQueueEntryAttachment.notificationQueueEntryId = ?";

	public NotificationQueueEntryAttachmentPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"notificationQueueEntryAttachmentId", "NQueueEntryAttachmentId");

		setDBColumnNames(dbColumnNames);

		setModelClass(NotificationQueueEntryAttachment.class);

		setModelImplClass(NotificationQueueEntryAttachmentImpl.class);
		setModelPKClass(long.class);

		setTable(NotificationQueueEntryAttachmentTable.INSTANCE);
	}

	/**
	 * Caches the notification queue entry attachment in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryAttachment the notification queue entry attachment
	 */
	@Override
	public void cacheResult(
		NotificationQueueEntryAttachment notificationQueueEntryAttachment) {

		entityCache.putResult(
			NotificationQueueEntryAttachmentImpl.class,
			notificationQueueEntryAttachment.getPrimaryKey(),
			notificationQueueEntryAttachment);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the notification queue entry attachments in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryAttachments the notification queue entry attachments
	 */
	@Override
	public void cacheResult(
		List<NotificationQueueEntryAttachment>
			notificationQueueEntryAttachments) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (notificationQueueEntryAttachments.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (NotificationQueueEntryAttachment notificationQueueEntryAttachment :
				notificationQueueEntryAttachments) {

			if (entityCache.getResult(
					NotificationQueueEntryAttachmentImpl.class,
					notificationQueueEntryAttachment.getPrimaryKey()) == null) {

				cacheResult(notificationQueueEntryAttachment);
			}
		}
	}

	/**
	 * Clears the cache for all notification queue entry attachments.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(NotificationQueueEntryAttachmentImpl.class);

		finderCache.clearCache(NotificationQueueEntryAttachmentImpl.class);
	}

	/**
	 * Clears the cache for the notification queue entry attachment.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		NotificationQueueEntryAttachment notificationQueueEntryAttachment) {

		entityCache.removeResult(
			NotificationQueueEntryAttachmentImpl.class,
			notificationQueueEntryAttachment);
	}

	@Override
	public void clearCache(
		List<NotificationQueueEntryAttachment>
			notificationQueueEntryAttachments) {

		for (NotificationQueueEntryAttachment notificationQueueEntryAttachment :
				notificationQueueEntryAttachments) {

			entityCache.removeResult(
				NotificationQueueEntryAttachmentImpl.class,
				notificationQueueEntryAttachment);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(NotificationQueueEntryAttachmentImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				NotificationQueueEntryAttachmentImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new notification queue entry attachment with the primary key. Does not add the notification queue entry attachment to the database.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key for the new notification queue entry attachment
	 * @return the new notification queue entry attachment
	 */
	@Override
	public NotificationQueueEntryAttachment create(
		long notificationQueueEntryAttachmentId) {

		NotificationQueueEntryAttachment notificationQueueEntryAttachment =
			new NotificationQueueEntryAttachmentImpl();

		notificationQueueEntryAttachment.setNew(true);
		notificationQueueEntryAttachment.setPrimaryKey(
			notificationQueueEntryAttachmentId);

		notificationQueueEntryAttachment.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return notificationQueueEntryAttachment;
	}

	/**
	 * Removes the notification queue entry attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key of the notification queue entry attachment
	 * @return the notification queue entry attachment that was removed
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a notification queue entry attachment with the primary key could not be found
	 */
	@Override
	public NotificationQueueEntryAttachment remove(
			long notificationQueueEntryAttachmentId)
		throws NoSuchNotificationQueueEntryAttachmentException {

		return remove((Serializable)notificationQueueEntryAttachmentId);
	}

	/**
	 * Removes the notification queue entry attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the notification queue entry attachment
	 * @return the notification queue entry attachment that was removed
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a notification queue entry attachment with the primary key could not be found
	 */
	@Override
	public NotificationQueueEntryAttachment remove(Serializable primaryKey)
		throws NoSuchNotificationQueueEntryAttachmentException {

		Session session = null;

		try {
			session = openSession();

			NotificationQueueEntryAttachment notificationQueueEntryAttachment =
				(NotificationQueueEntryAttachment)session.get(
					NotificationQueueEntryAttachmentImpl.class, primaryKey);

			if (notificationQueueEntryAttachment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationQueueEntryAttachmentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(notificationQueueEntryAttachment);
		}
		catch (NoSuchNotificationQueueEntryAttachmentException
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
	protected NotificationQueueEntryAttachment removeImpl(
		NotificationQueueEntryAttachment notificationQueueEntryAttachment) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(notificationQueueEntryAttachment)) {
				notificationQueueEntryAttachment =
					(NotificationQueueEntryAttachment)session.get(
						NotificationQueueEntryAttachmentImpl.class,
						notificationQueueEntryAttachment.getPrimaryKeyObj());
			}

			if (notificationQueueEntryAttachment != null) {
				session.delete(notificationQueueEntryAttachment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (notificationQueueEntryAttachment != null) {
			clearCache(notificationQueueEntryAttachment);
		}

		return notificationQueueEntryAttachment;
	}

	@Override
	public NotificationQueueEntryAttachment updateImpl(
		NotificationQueueEntryAttachment notificationQueueEntryAttachment) {

		boolean isNew = notificationQueueEntryAttachment.isNew();

		if (!(notificationQueueEntryAttachment instanceof
				NotificationQueueEntryAttachmentModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					notificationQueueEntryAttachment.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					notificationQueueEntryAttachment);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in notificationQueueEntryAttachment proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom NotificationQueueEntryAttachment implementation " +
					notificationQueueEntryAttachment.getClass());
		}

		NotificationQueueEntryAttachmentModelImpl
			notificationQueueEntryAttachmentModelImpl =
				(NotificationQueueEntryAttachmentModelImpl)
					notificationQueueEntryAttachment;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(notificationQueueEntryAttachment);
			}
			else {
				notificationQueueEntryAttachment =
					(NotificationQueueEntryAttachment)session.merge(
						notificationQueueEntryAttachment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			NotificationQueueEntryAttachmentImpl.class,
			notificationQueueEntryAttachmentModelImpl, false, true);

		if (isNew) {
			notificationQueueEntryAttachment.setNew(false);
		}

		notificationQueueEntryAttachment.resetOriginalValues();

		return notificationQueueEntryAttachment;
	}

	/**
	 * Returns the notification queue entry attachment with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the notification queue entry attachment
	 * @return the notification queue entry attachment
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a notification queue entry attachment with the primary key could not be found
	 */
	@Override
	public NotificationQueueEntryAttachment findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchNotificationQueueEntryAttachmentException {

		NotificationQueueEntryAttachment notificationQueueEntryAttachment =
			fetchByPrimaryKey(primaryKey);

		if (notificationQueueEntryAttachment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationQueueEntryAttachmentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return notificationQueueEntryAttachment;
	}

	/**
	 * Returns the notification queue entry attachment with the primary key or throws a <code>NoSuchNotificationQueueEntryAttachmentException</code> if it could not be found.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key of the notification queue entry attachment
	 * @return the notification queue entry attachment
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a notification queue entry attachment with the primary key could not be found
	 */
	@Override
	public NotificationQueueEntryAttachment findByPrimaryKey(
			long notificationQueueEntryAttachmentId)
		throws NoSuchNotificationQueueEntryAttachmentException {

		return findByPrimaryKey(
			(Serializable)notificationQueueEntryAttachmentId);
	}

	/**
	 * Returns the notification queue entry attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key of the notification queue entry attachment
	 * @return the notification queue entry attachment, or <code>null</code> if a notification queue entry attachment with the primary key could not be found
	 */
	@Override
	public NotificationQueueEntryAttachment fetchByPrimaryKey(
		long notificationQueueEntryAttachmentId) {

		return fetchByPrimaryKey(
			(Serializable)notificationQueueEntryAttachmentId);
	}

	/**
	 * Returns all the notification queue entry attachments.
	 *
	 * @return the notification queue entry attachments
	 */
	@Override
	public List<NotificationQueueEntryAttachment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notification queue entry attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @return the range of notification queue entry attachments
	 */
	@Override
	public List<NotificationQueueEntryAttachment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the notification queue entry attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification queue entry attachments
	 */
	@Override
	public List<NotificationQueueEntryAttachment> findAll(
		int start, int end,
		OrderByComparator<NotificationQueueEntryAttachment> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notification queue entry attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification queue entry attachments
	 */
	@Override
	public List<NotificationQueueEntryAttachment> findAll(
		int start, int end,
		OrderByComparator<NotificationQueueEntryAttachment> orderByComparator,
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

		List<NotificationQueueEntryAttachment> list = null;

		if (useFinderCache) {
			list =
				(List<NotificationQueueEntryAttachment>)finderCache.getResult(
					finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_NOTIFICATIONQUEUEENTRYATTACHMENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_NOTIFICATIONQUEUEENTRYATTACHMENT;

				sql = sql.concat(
					NotificationQueueEntryAttachmentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<NotificationQueueEntryAttachment>)QueryUtil.list(
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
	 * Removes all the notification queue entry attachments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (NotificationQueueEntryAttachment notificationQueueEntryAttachment :
				findAll()) {

			remove(notificationQueueEntryAttachment);
		}
	}

	/**
	 * Returns the number of notification queue entry attachments.
	 *
	 * @return the number of notification queue entry attachments
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
					_SQL_COUNT_NOTIFICATIONQUEUEENTRYATTACHMENT);

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
		return "NQueueEntryAttachmentId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_NOTIFICATIONQUEUEENTRYATTACHMENT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return NotificationQueueEntryAttachmentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the notification queue entry attachment persistence.
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

		_finderPathWithPaginationFindByNotificationQueueEntryId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByNotificationQueueEntryId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"notificationQueueEntryId"}, true);

		_finderPathWithoutPaginationFindByNotificationQueueEntryId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByNotificationQueueEntryId",
				new String[] {Long.class.getName()},
				new String[] {"notificationQueueEntryId"}, true);

		_finderPathCountByNotificationQueueEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByNotificationQueueEntryId",
			new String[] {Long.class.getName()},
			new String[] {"notificationQueueEntryId"}, false);

		NotificationQueueEntryAttachmentUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		NotificationQueueEntryAttachmentUtil.setPersistence(null);

		entityCache.removeCache(
			NotificationQueueEntryAttachmentImpl.class.getName());
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

	private static final String _SQL_SELECT_NOTIFICATIONQUEUEENTRYATTACHMENT =
		"SELECT notificationQueueEntryAttachment FROM NotificationQueueEntryAttachment notificationQueueEntryAttachment";

	private static final String
		_SQL_SELECT_NOTIFICATIONQUEUEENTRYATTACHMENT_WHERE =
			"SELECT notificationQueueEntryAttachment FROM NotificationQueueEntryAttachment notificationQueueEntryAttachment WHERE ";

	private static final String _SQL_COUNT_NOTIFICATIONQUEUEENTRYATTACHMENT =
		"SELECT COUNT(notificationQueueEntryAttachment) FROM NotificationQueueEntryAttachment notificationQueueEntryAttachment";

	private static final String
		_SQL_COUNT_NOTIFICATIONQUEUEENTRYATTACHMENT_WHERE =
			"SELECT COUNT(notificationQueueEntryAttachment) FROM NotificationQueueEntryAttachment notificationQueueEntryAttachment WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"notificationQueueEntryAttachment.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No NotificationQueueEntryAttachment exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No NotificationQueueEntryAttachment exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationQueueEntryAttachmentPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"notificationQueueEntryAttachmentId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}