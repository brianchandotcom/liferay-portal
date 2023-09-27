/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.persistence.impl;

import com.liferay.notification.exception.NoSuchNotificationTemplateAttachmentException;
import com.liferay.notification.model.NotificationTemplateAttachment;
import com.liferay.notification.model.NotificationTemplateAttachmentTable;
import com.liferay.notification.model.impl.NotificationTemplateAttachmentImpl;
import com.liferay.notification.model.impl.NotificationTemplateAttachmentModelImpl;
import com.liferay.notification.service.persistence.NotificationTemplateAttachmentPersistence;
import com.liferay.notification.service.persistence.NotificationTemplateAttachmentUtil;
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
 * The persistence implementation for the notification template attachment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @generated
 */
@Component(service = NotificationTemplateAttachmentPersistence.class)
public class NotificationTemplateAttachmentPersistenceImpl
	extends BasePersistenceImpl<NotificationTemplateAttachment>
	implements NotificationTemplateAttachmentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>NotificationTemplateAttachmentUtil</code> to access the notification template attachment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		NotificationTemplateAttachmentImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByNotificationTemplateId;
	private FinderPath _finderPathWithoutPaginationFindByNotificationTemplateId;
	private FinderPath _finderPathCountByNotificationTemplateId;

	/**
	 * Returns all the notification template attachments where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the matching notification template attachments
	 */
	@Override
	public List<NotificationTemplateAttachment> findByNotificationTemplateId(
		long notificationTemplateId) {

		return findByNotificationTemplateId(
			notificationTemplateId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notification template attachments where notificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param start the lower bound of the range of notification template attachments
	 * @param end the upper bound of the range of notification template attachments (not inclusive)
	 * @return the range of matching notification template attachments
	 */
	@Override
	public List<NotificationTemplateAttachment> findByNotificationTemplateId(
		long notificationTemplateId, int start, int end) {

		return findByNotificationTemplateId(
			notificationTemplateId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notification template attachments where notificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param start the lower bound of the range of notification template attachments
	 * @param end the upper bound of the range of notification template attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification template attachments
	 */
	@Override
	public List<NotificationTemplateAttachment> findByNotificationTemplateId(
		long notificationTemplateId, int start, int end,
		OrderByComparator<NotificationTemplateAttachment> orderByComparator) {

		return findByNotificationTemplateId(
			notificationTemplateId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notification template attachments where notificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param start the lower bound of the range of notification template attachments
	 * @param end the upper bound of the range of notification template attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification template attachments
	 */
	@Override
	public List<NotificationTemplateAttachment> findByNotificationTemplateId(
		long notificationTemplateId, int start, int end,
		OrderByComparator<NotificationTemplateAttachment> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByNotificationTemplateId;
				finderArgs = new Object[] {notificationTemplateId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByNotificationTemplateId;
			finderArgs = new Object[] {
				notificationTemplateId, start, end, orderByComparator
			};
		}

		List<NotificationTemplateAttachment> list = null;

		if (useFinderCache) {
			list = (List<NotificationTemplateAttachment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (NotificationTemplateAttachment
						notificationTemplateAttachment : list) {

					if (notificationTemplateId !=
							notificationTemplateAttachment.
								getNotificationTemplateId()) {

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

			sb.append(_SQL_SELECT_NOTIFICATIONTEMPLATEATTACHMENT_WHERE);

			sb.append(
				_FINDER_COLUMN_NOTIFICATIONTEMPLATEID_NOTIFICATIONTEMPLATEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					NotificationTemplateAttachmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationTemplateId);

				list = (List<NotificationTemplateAttachment>)QueryUtil.list(
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
	 * Returns the first notification template attachment in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template attachment
	 * @throws NoSuchNotificationTemplateAttachmentException if a matching notification template attachment could not be found
	 */
	@Override
	public NotificationTemplateAttachment findByNotificationTemplateId_First(
			long notificationTemplateId,
			OrderByComparator<NotificationTemplateAttachment> orderByComparator)
		throws NoSuchNotificationTemplateAttachmentException {

		NotificationTemplateAttachment notificationTemplateAttachment =
			fetchByNotificationTemplateId_First(
				notificationTemplateId, orderByComparator);

		if (notificationTemplateAttachment != null) {
			return notificationTemplateAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("notificationTemplateId=");
		sb.append(notificationTemplateId);

		sb.append("}");

		throw new NoSuchNotificationTemplateAttachmentException(sb.toString());
	}

	/**
	 * Returns the first notification template attachment in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template attachment, or <code>null</code> if a matching notification template attachment could not be found
	 */
	@Override
	public NotificationTemplateAttachment fetchByNotificationTemplateId_First(
		long notificationTemplateId,
		OrderByComparator<NotificationTemplateAttachment> orderByComparator) {

		List<NotificationTemplateAttachment> list =
			findByNotificationTemplateId(
				notificationTemplateId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last notification template attachment in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template attachment
	 * @throws NoSuchNotificationTemplateAttachmentException if a matching notification template attachment could not be found
	 */
	@Override
	public NotificationTemplateAttachment findByNotificationTemplateId_Last(
			long notificationTemplateId,
			OrderByComparator<NotificationTemplateAttachment> orderByComparator)
		throws NoSuchNotificationTemplateAttachmentException {

		NotificationTemplateAttachment notificationTemplateAttachment =
			fetchByNotificationTemplateId_Last(
				notificationTemplateId, orderByComparator);

		if (notificationTemplateAttachment != null) {
			return notificationTemplateAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("notificationTemplateId=");
		sb.append(notificationTemplateId);

		sb.append("}");

		throw new NoSuchNotificationTemplateAttachmentException(sb.toString());
	}

	/**
	 * Returns the last notification template attachment in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template attachment, or <code>null</code> if a matching notification template attachment could not be found
	 */
	@Override
	public NotificationTemplateAttachment fetchByNotificationTemplateId_Last(
		long notificationTemplateId,
		OrderByComparator<NotificationTemplateAttachment> orderByComparator) {

		int count = countByNotificationTemplateId(notificationTemplateId);

		if (count == 0) {
			return null;
		}

		List<NotificationTemplateAttachment> list =
			findByNotificationTemplateId(
				notificationTemplateId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the notification template attachments before and after the current notification template attachment in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateAttachmentId the primary key of the current notification template attachment
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification template attachment
	 * @throws NoSuchNotificationTemplateAttachmentException if a notification template attachment with the primary key could not be found
	 */
	@Override
	public NotificationTemplateAttachment[]
			findByNotificationTemplateId_PrevAndNext(
				long notificationTemplateAttachmentId,
				long notificationTemplateId,
				OrderByComparator<NotificationTemplateAttachment>
					orderByComparator)
		throws NoSuchNotificationTemplateAttachmentException {

		NotificationTemplateAttachment notificationTemplateAttachment =
			findByPrimaryKey(notificationTemplateAttachmentId);

		Session session = null;

		try {
			session = openSession();

			NotificationTemplateAttachment[] array =
				new NotificationTemplateAttachmentImpl[3];

			array[0] = getByNotificationTemplateId_PrevAndNext(
				session, notificationTemplateAttachment, notificationTemplateId,
				orderByComparator, true);

			array[1] = notificationTemplateAttachment;

			array[2] = getByNotificationTemplateId_PrevAndNext(
				session, notificationTemplateAttachment, notificationTemplateId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected NotificationTemplateAttachment
		getByNotificationTemplateId_PrevAndNext(
			Session session,
			NotificationTemplateAttachment notificationTemplateAttachment,
			long notificationTemplateId,
			OrderByComparator<NotificationTemplateAttachment> orderByComparator,
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

		sb.append(_SQL_SELECT_NOTIFICATIONTEMPLATEATTACHMENT_WHERE);

		sb.append(
			_FINDER_COLUMN_NOTIFICATIONTEMPLATEID_NOTIFICATIONTEMPLATEID_2);

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
			sb.append(NotificationTemplateAttachmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(notificationTemplateId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						notificationTemplateAttachment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<NotificationTemplateAttachment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the notification template attachments where notificationTemplateId = &#63; from the database.
	 *
	 * @param notificationTemplateId the notification template ID
	 */
	@Override
	public void removeByNotificationTemplateId(long notificationTemplateId) {
		for (NotificationTemplateAttachment notificationTemplateAttachment :
				findByNotificationTemplateId(
					notificationTemplateId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(notificationTemplateAttachment);
		}
	}

	/**
	 * Returns the number of notification template attachments where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the number of matching notification template attachments
	 */
	@Override
	public int countByNotificationTemplateId(long notificationTemplateId) {
		FinderPath finderPath = _finderPathCountByNotificationTemplateId;

		Object[] finderArgs = new Object[] {notificationTemplateId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_NOTIFICATIONTEMPLATEATTACHMENT_WHERE);

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
			"notificationTemplateAttachment.notificationTemplateId = ?";

	private FinderPath _finderPathFetchByNTI_OFI;
	private FinderPath _finderPathCountByNTI_OFI;

	/**
	 * Returns the notification template attachment where notificationTemplateId = &#63; and objectFieldId = &#63; or throws a <code>NoSuchNotificationTemplateAttachmentException</code> if it could not be found.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param objectFieldId the object field ID
	 * @return the matching notification template attachment
	 * @throws NoSuchNotificationTemplateAttachmentException if a matching notification template attachment could not be found
	 */
	@Override
	public NotificationTemplateAttachment findByNTI_OFI(
			long notificationTemplateId, long objectFieldId)
		throws NoSuchNotificationTemplateAttachmentException {

		NotificationTemplateAttachment notificationTemplateAttachment =
			fetchByNTI_OFI(notificationTemplateId, objectFieldId);

		if (notificationTemplateAttachment == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("notificationTemplateId=");
			sb.append(notificationTemplateId);

			sb.append(", objectFieldId=");
			sb.append(objectFieldId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchNotificationTemplateAttachmentException(
				sb.toString());
		}

		return notificationTemplateAttachment;
	}

	/**
	 * Returns the notification template attachment where notificationTemplateId = &#63; and objectFieldId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param objectFieldId the object field ID
	 * @return the matching notification template attachment, or <code>null</code> if a matching notification template attachment could not be found
	 */
	@Override
	public NotificationTemplateAttachment fetchByNTI_OFI(
		long notificationTemplateId, long objectFieldId) {

		return fetchByNTI_OFI(notificationTemplateId, objectFieldId, true);
	}

	/**
	 * Returns the notification template attachment where notificationTemplateId = &#63; and objectFieldId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param objectFieldId the object field ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notification template attachment, or <code>null</code> if a matching notification template attachment could not be found
	 */
	@Override
	public NotificationTemplateAttachment fetchByNTI_OFI(
		long notificationTemplateId, long objectFieldId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {notificationTemplateId, objectFieldId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByNTI_OFI, finderArgs, this);
		}

		if (result instanceof NotificationTemplateAttachment) {
			NotificationTemplateAttachment notificationTemplateAttachment =
				(NotificationTemplateAttachment)result;

			if ((notificationTemplateId !=
					notificationTemplateAttachment.
						getNotificationTemplateId()) ||
				(objectFieldId !=
					notificationTemplateAttachment.getObjectFieldId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_NOTIFICATIONTEMPLATEATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_NTI_OFI_NOTIFICATIONTEMPLATEID_2);

			sb.append(_FINDER_COLUMN_NTI_OFI_OBJECTFIELDID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationTemplateId);

				queryPos.add(objectFieldId);

				List<NotificationTemplateAttachment> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByNTI_OFI, finderArgs, list);
					}
				}
				else {
					NotificationTemplateAttachment
						notificationTemplateAttachment = list.get(0);

					result = notificationTemplateAttachment;

					cacheResult(notificationTemplateAttachment);
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
			return (NotificationTemplateAttachment)result;
		}
	}

	/**
	 * Removes the notification template attachment where notificationTemplateId = &#63; and objectFieldId = &#63; from the database.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param objectFieldId the object field ID
	 * @return the notification template attachment that was removed
	 */
	@Override
	public NotificationTemplateAttachment removeByNTI_OFI(
			long notificationTemplateId, long objectFieldId)
		throws NoSuchNotificationTemplateAttachmentException {

		NotificationTemplateAttachment notificationTemplateAttachment =
			findByNTI_OFI(notificationTemplateId, objectFieldId);

		return remove(notificationTemplateAttachment);
	}

	/**
	 * Returns the number of notification template attachments where notificationTemplateId = &#63; and objectFieldId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param objectFieldId the object field ID
	 * @return the number of matching notification template attachments
	 */
	@Override
	public int countByNTI_OFI(long notificationTemplateId, long objectFieldId) {
		FinderPath finderPath = _finderPathCountByNTI_OFI;

		Object[] finderArgs = new Object[] {
			notificationTemplateId, objectFieldId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_NOTIFICATIONTEMPLATEATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_NTI_OFI_NOTIFICATIONTEMPLATEID_2);

			sb.append(_FINDER_COLUMN_NTI_OFI_OBJECTFIELDID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationTemplateId);

				queryPos.add(objectFieldId);

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
		_FINDER_COLUMN_NTI_OFI_NOTIFICATIONTEMPLATEID_2 =
			"notificationTemplateAttachment.notificationTemplateId = ? AND ";

	private static final String _FINDER_COLUMN_NTI_OFI_OBJECTFIELDID_2 =
		"notificationTemplateAttachment.objectFieldId = ?";

	public NotificationTemplateAttachmentPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"notificationTemplateAttachmentId", "NTemplateAttachmentId");

		setDBColumnNames(dbColumnNames);

		setModelClass(NotificationTemplateAttachment.class);

		setModelImplClass(NotificationTemplateAttachmentImpl.class);
		setModelPKClass(long.class);

		setTable(NotificationTemplateAttachmentTable.INSTANCE);
	}

	/**
	 * Caches the notification template attachment in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateAttachment the notification template attachment
	 */
	@Override
	public void cacheResult(
		NotificationTemplateAttachment notificationTemplateAttachment) {

		entityCache.putResult(
			NotificationTemplateAttachmentImpl.class,
			notificationTemplateAttachment.getPrimaryKey(),
			notificationTemplateAttachment);

		finderCache.putResult(
			_finderPathFetchByNTI_OFI,
			new Object[] {
				notificationTemplateAttachment.getNotificationTemplateId(),
				notificationTemplateAttachment.getObjectFieldId()
			},
			notificationTemplateAttachment);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the notification template attachments in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateAttachments the notification template attachments
	 */
	@Override
	public void cacheResult(
		List<NotificationTemplateAttachment> notificationTemplateAttachments) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (notificationTemplateAttachments.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (NotificationTemplateAttachment notificationTemplateAttachment :
				notificationTemplateAttachments) {

			if (entityCache.getResult(
					NotificationTemplateAttachmentImpl.class,
					notificationTemplateAttachment.getPrimaryKey()) == null) {

				cacheResult(notificationTemplateAttachment);
			}
		}
	}

	/**
	 * Clears the cache for all notification template attachments.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(NotificationTemplateAttachmentImpl.class);

		finderCache.clearCache(NotificationTemplateAttachmentImpl.class);
	}

	/**
	 * Clears the cache for the notification template attachment.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		NotificationTemplateAttachment notificationTemplateAttachment) {

		entityCache.removeResult(
			NotificationTemplateAttachmentImpl.class,
			notificationTemplateAttachment);
	}

	@Override
	public void clearCache(
		List<NotificationTemplateAttachment> notificationTemplateAttachments) {

		for (NotificationTemplateAttachment notificationTemplateAttachment :
				notificationTemplateAttachments) {

			entityCache.removeResult(
				NotificationTemplateAttachmentImpl.class,
				notificationTemplateAttachment);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(NotificationTemplateAttachmentImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				NotificationTemplateAttachmentImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		NotificationTemplateAttachmentModelImpl
			notificationTemplateAttachmentModelImpl) {

		Object[] args = new Object[] {
			notificationTemplateAttachmentModelImpl.getNotificationTemplateId(),
			notificationTemplateAttachmentModelImpl.getObjectFieldId()
		};

		finderCache.putResult(_finderPathCountByNTI_OFI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByNTI_OFI, args,
			notificationTemplateAttachmentModelImpl);
	}

	/**
	 * Creates a new notification template attachment with the primary key. Does not add the notification template attachment to the database.
	 *
	 * @param notificationTemplateAttachmentId the primary key for the new notification template attachment
	 * @return the new notification template attachment
	 */
	@Override
	public NotificationTemplateAttachment create(
		long notificationTemplateAttachmentId) {

		NotificationTemplateAttachment notificationTemplateAttachment =
			new NotificationTemplateAttachmentImpl();

		notificationTemplateAttachment.setNew(true);
		notificationTemplateAttachment.setPrimaryKey(
			notificationTemplateAttachmentId);

		notificationTemplateAttachment.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return notificationTemplateAttachment;
	}

	/**
	 * Removes the notification template attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationTemplateAttachmentId the primary key of the notification template attachment
	 * @return the notification template attachment that was removed
	 * @throws NoSuchNotificationTemplateAttachmentException if a notification template attachment with the primary key could not be found
	 */
	@Override
	public NotificationTemplateAttachment remove(
			long notificationTemplateAttachmentId)
		throws NoSuchNotificationTemplateAttachmentException {

		return remove((Serializable)notificationTemplateAttachmentId);
	}

	/**
	 * Removes the notification template attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the notification template attachment
	 * @return the notification template attachment that was removed
	 * @throws NoSuchNotificationTemplateAttachmentException if a notification template attachment with the primary key could not be found
	 */
	@Override
	public NotificationTemplateAttachment remove(Serializable primaryKey)
		throws NoSuchNotificationTemplateAttachmentException {

		Session session = null;

		try {
			session = openSession();

			NotificationTemplateAttachment notificationTemplateAttachment =
				(NotificationTemplateAttachment)session.get(
					NotificationTemplateAttachmentImpl.class, primaryKey);

			if (notificationTemplateAttachment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationTemplateAttachmentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(notificationTemplateAttachment);
		}
		catch (NoSuchNotificationTemplateAttachmentException
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
	protected NotificationTemplateAttachment removeImpl(
		NotificationTemplateAttachment notificationTemplateAttachment) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(notificationTemplateAttachment)) {
				notificationTemplateAttachment =
					(NotificationTemplateAttachment)session.get(
						NotificationTemplateAttachmentImpl.class,
						notificationTemplateAttachment.getPrimaryKeyObj());
			}

			if (notificationTemplateAttachment != null) {
				session.delete(notificationTemplateAttachment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (notificationTemplateAttachment != null) {
			clearCache(notificationTemplateAttachment);
		}

		return notificationTemplateAttachment;
	}

	@Override
	public NotificationTemplateAttachment updateImpl(
		NotificationTemplateAttachment notificationTemplateAttachment) {

		boolean isNew = notificationTemplateAttachment.isNew();

		if (!(notificationTemplateAttachment instanceof
				NotificationTemplateAttachmentModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					notificationTemplateAttachment.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					notificationTemplateAttachment);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in notificationTemplateAttachment proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom NotificationTemplateAttachment implementation " +
					notificationTemplateAttachment.getClass());
		}

		NotificationTemplateAttachmentModelImpl
			notificationTemplateAttachmentModelImpl =
				(NotificationTemplateAttachmentModelImpl)
					notificationTemplateAttachment;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(notificationTemplateAttachment);
			}
			else {
				notificationTemplateAttachment =
					(NotificationTemplateAttachment)session.merge(
						notificationTemplateAttachment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			NotificationTemplateAttachmentImpl.class,
			notificationTemplateAttachmentModelImpl, false, true);

		cacheUniqueFindersCache(notificationTemplateAttachmentModelImpl);

		if (isNew) {
			notificationTemplateAttachment.setNew(false);
		}

		notificationTemplateAttachment.resetOriginalValues();

		return notificationTemplateAttachment;
	}

	/**
	 * Returns the notification template attachment with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the notification template attachment
	 * @return the notification template attachment
	 * @throws NoSuchNotificationTemplateAttachmentException if a notification template attachment with the primary key could not be found
	 */
	@Override
	public NotificationTemplateAttachment findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchNotificationTemplateAttachmentException {

		NotificationTemplateAttachment notificationTemplateAttachment =
			fetchByPrimaryKey(primaryKey);

		if (notificationTemplateAttachment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationTemplateAttachmentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return notificationTemplateAttachment;
	}

	/**
	 * Returns the notification template attachment with the primary key or throws a <code>NoSuchNotificationTemplateAttachmentException</code> if it could not be found.
	 *
	 * @param notificationTemplateAttachmentId the primary key of the notification template attachment
	 * @return the notification template attachment
	 * @throws NoSuchNotificationTemplateAttachmentException if a notification template attachment with the primary key could not be found
	 */
	@Override
	public NotificationTemplateAttachment findByPrimaryKey(
			long notificationTemplateAttachmentId)
		throws NoSuchNotificationTemplateAttachmentException {

		return findByPrimaryKey((Serializable)notificationTemplateAttachmentId);
	}

	/**
	 * Returns the notification template attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationTemplateAttachmentId the primary key of the notification template attachment
	 * @return the notification template attachment, or <code>null</code> if a notification template attachment with the primary key could not be found
	 */
	@Override
	public NotificationTemplateAttachment fetchByPrimaryKey(
		long notificationTemplateAttachmentId) {

		return fetchByPrimaryKey(
			(Serializable)notificationTemplateAttachmentId);
	}

	/**
	 * Returns all the notification template attachments.
	 *
	 * @return the notification template attachments
	 */
	@Override
	public List<NotificationTemplateAttachment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notification template attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template attachments
	 * @param end the upper bound of the range of notification template attachments (not inclusive)
	 * @return the range of notification template attachments
	 */
	@Override
	public List<NotificationTemplateAttachment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the notification template attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template attachments
	 * @param end the upper bound of the range of notification template attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification template attachments
	 */
	@Override
	public List<NotificationTemplateAttachment> findAll(
		int start, int end,
		OrderByComparator<NotificationTemplateAttachment> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notification template attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template attachments
	 * @param end the upper bound of the range of notification template attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification template attachments
	 */
	@Override
	public List<NotificationTemplateAttachment> findAll(
		int start, int end,
		OrderByComparator<NotificationTemplateAttachment> orderByComparator,
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

		List<NotificationTemplateAttachment> list = null;

		if (useFinderCache) {
			list = (List<NotificationTemplateAttachment>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_NOTIFICATIONTEMPLATEATTACHMENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_NOTIFICATIONTEMPLATEATTACHMENT;

				sql = sql.concat(
					NotificationTemplateAttachmentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<NotificationTemplateAttachment>)QueryUtil.list(
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
	 * Removes all the notification template attachments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (NotificationTemplateAttachment notificationTemplateAttachment :
				findAll()) {

			remove(notificationTemplateAttachment);
		}
	}

	/**
	 * Returns the number of notification template attachments.
	 *
	 * @return the number of notification template attachments
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
					_SQL_COUNT_NOTIFICATIONTEMPLATEATTACHMENT);

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
		return "NTemplateAttachmentId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_NOTIFICATIONTEMPLATEATTACHMENT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return NotificationTemplateAttachmentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the notification template attachment persistence.
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

		_finderPathWithPaginationFindByNotificationTemplateId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByNotificationTemplateId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"notificationTemplateId"}, true);

		_finderPathWithoutPaginationFindByNotificationTemplateId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByNotificationTemplateId",
				new String[] {Long.class.getName()},
				new String[] {"notificationTemplateId"}, true);

		_finderPathCountByNotificationTemplateId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByNotificationTemplateId",
			new String[] {Long.class.getName()},
			new String[] {"notificationTemplateId"}, false);

		_finderPathFetchByNTI_OFI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByNTI_OFI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"notificationTemplateId", "objectFieldId"}, true);

		_finderPathCountByNTI_OFI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByNTI_OFI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"notificationTemplateId", "objectFieldId"}, false);

		NotificationTemplateAttachmentUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		NotificationTemplateAttachmentUtil.setPersistence(null);

		entityCache.removeCache(
			NotificationTemplateAttachmentImpl.class.getName());
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

	private static final String _SQL_SELECT_NOTIFICATIONTEMPLATEATTACHMENT =
		"SELECT notificationTemplateAttachment FROM NotificationTemplateAttachment notificationTemplateAttachment";

	private static final String
		_SQL_SELECT_NOTIFICATIONTEMPLATEATTACHMENT_WHERE =
			"SELECT notificationTemplateAttachment FROM NotificationTemplateAttachment notificationTemplateAttachment WHERE ";

	private static final String _SQL_COUNT_NOTIFICATIONTEMPLATEATTACHMENT =
		"SELECT COUNT(notificationTemplateAttachment) FROM NotificationTemplateAttachment notificationTemplateAttachment";

	private static final String
		_SQL_COUNT_NOTIFICATIONTEMPLATEATTACHMENT_WHERE =
			"SELECT COUNT(notificationTemplateAttachment) FROM NotificationTemplateAttachment notificationTemplateAttachment WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"notificationTemplateAttachment.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No NotificationTemplateAttachment exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No NotificationTemplateAttachment exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationTemplateAttachmentPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"notificationTemplateAttachmentId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}