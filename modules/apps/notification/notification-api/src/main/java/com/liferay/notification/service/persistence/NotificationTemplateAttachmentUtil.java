/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.persistence;

import com.liferay.notification.model.NotificationTemplateAttachment;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the notification template attachment service. This utility wraps <code>com.liferay.notification.service.persistence.impl.NotificationTemplateAttachmentPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateAttachmentPersistence
 * @generated
 */
public class NotificationTemplateAttachmentUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		NotificationTemplateAttachment notificationTemplateAttachment) {

		getPersistence().clearCache(notificationTemplateAttachment);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, NotificationTemplateAttachment>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<NotificationTemplateAttachment> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<NotificationTemplateAttachment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<NotificationTemplateAttachment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<NotificationTemplateAttachment> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static NotificationTemplateAttachment update(
		NotificationTemplateAttachment notificationTemplateAttachment) {

		return getPersistence().update(notificationTemplateAttachment);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static NotificationTemplateAttachment update(
		NotificationTemplateAttachment notificationTemplateAttachment,
		ServiceContext serviceContext) {

		return getPersistence().update(
			notificationTemplateAttachment, serviceContext);
	}

	/**
	 * Returns all the notification template attachments where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the matching notification template attachments
	 */
	public static List<NotificationTemplateAttachment>
		findByNotificationTemplateId(long notificationTemplateId) {

		return getPersistence().findByNotificationTemplateId(
			notificationTemplateId);
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
	public static List<NotificationTemplateAttachment>
		findByNotificationTemplateId(
			long notificationTemplateId, int start, int end) {

		return getPersistence().findByNotificationTemplateId(
			notificationTemplateId, start, end);
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
	public static List<NotificationTemplateAttachment>
		findByNotificationTemplateId(
			long notificationTemplateId, int start, int end,
			OrderByComparator<NotificationTemplateAttachment>
				orderByComparator) {

		return getPersistence().findByNotificationTemplateId(
			notificationTemplateId, start, end, orderByComparator);
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
	public static List<NotificationTemplateAttachment>
		findByNotificationTemplateId(
			long notificationTemplateId, int start, int end,
			OrderByComparator<NotificationTemplateAttachment> orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByNotificationTemplateId(
			notificationTemplateId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first notification template attachment in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template attachment
	 * @throws NoSuchNotificationTemplateAttachmentException if a matching notification template attachment could not be found
	 */
	public static NotificationTemplateAttachment
			findByNotificationTemplateId_First(
				long notificationTemplateId,
				OrderByComparator<NotificationTemplateAttachment>
					orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateAttachmentException {

		return getPersistence().findByNotificationTemplateId_First(
			notificationTemplateId, orderByComparator);
	}

	/**
	 * Returns the first notification template attachment in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template attachment, or <code>null</code> if a matching notification template attachment could not be found
	 */
	public static NotificationTemplateAttachment
		fetchByNotificationTemplateId_First(
			long notificationTemplateId,
			OrderByComparator<NotificationTemplateAttachment>
				orderByComparator) {

		return getPersistence().fetchByNotificationTemplateId_First(
			notificationTemplateId, orderByComparator);
	}

	/**
	 * Returns the last notification template attachment in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template attachment
	 * @throws NoSuchNotificationTemplateAttachmentException if a matching notification template attachment could not be found
	 */
	public static NotificationTemplateAttachment
			findByNotificationTemplateId_Last(
				long notificationTemplateId,
				OrderByComparator<NotificationTemplateAttachment>
					orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateAttachmentException {

		return getPersistence().findByNotificationTemplateId_Last(
			notificationTemplateId, orderByComparator);
	}

	/**
	 * Returns the last notification template attachment in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template attachment, or <code>null</code> if a matching notification template attachment could not be found
	 */
	public static NotificationTemplateAttachment
		fetchByNotificationTemplateId_Last(
			long notificationTemplateId,
			OrderByComparator<NotificationTemplateAttachment>
				orderByComparator) {

		return getPersistence().fetchByNotificationTemplateId_Last(
			notificationTemplateId, orderByComparator);
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
	public static NotificationTemplateAttachment[]
			findByNotificationTemplateId_PrevAndNext(
				long notificationTemplateAttachmentId,
				long notificationTemplateId,
				OrderByComparator<NotificationTemplateAttachment>
					orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateAttachmentException {

		return getPersistence().findByNotificationTemplateId_PrevAndNext(
			notificationTemplateAttachmentId, notificationTemplateId,
			orderByComparator);
	}

	/**
	 * Removes all the notification template attachments where notificationTemplateId = &#63; from the database.
	 *
	 * @param notificationTemplateId the notification template ID
	 */
	public static void removeByNotificationTemplateId(
		long notificationTemplateId) {

		getPersistence().removeByNotificationTemplateId(notificationTemplateId);
	}

	/**
	 * Returns the number of notification template attachments where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the number of matching notification template attachments
	 */
	public static int countByNotificationTemplateId(
		long notificationTemplateId) {

		return getPersistence().countByNotificationTemplateId(
			notificationTemplateId);
	}

	/**
	 * Returns the notification template attachment where notificationTemplateId = &#63; and objectFieldId = &#63; or throws a <code>NoSuchNotificationTemplateAttachmentException</code> if it could not be found.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param objectFieldId the object field ID
	 * @return the matching notification template attachment
	 * @throws NoSuchNotificationTemplateAttachmentException if a matching notification template attachment could not be found
	 */
	public static NotificationTemplateAttachment findByNTI_OFI(
			long notificationTemplateId, long objectFieldId)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateAttachmentException {

		return getPersistence().findByNTI_OFI(
			notificationTemplateId, objectFieldId);
	}

	/**
	 * Returns the notification template attachment where notificationTemplateId = &#63; and objectFieldId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param objectFieldId the object field ID
	 * @return the matching notification template attachment, or <code>null</code> if a matching notification template attachment could not be found
	 */
	public static NotificationTemplateAttachment fetchByNTI_OFI(
		long notificationTemplateId, long objectFieldId) {

		return getPersistence().fetchByNTI_OFI(
			notificationTemplateId, objectFieldId);
	}

	/**
	 * Returns the notification template attachment where notificationTemplateId = &#63; and objectFieldId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param objectFieldId the object field ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notification template attachment, or <code>null</code> if a matching notification template attachment could not be found
	 */
	public static NotificationTemplateAttachment fetchByNTI_OFI(
		long notificationTemplateId, long objectFieldId,
		boolean useFinderCache) {

		return getPersistence().fetchByNTI_OFI(
			notificationTemplateId, objectFieldId, useFinderCache);
	}

	/**
	 * Removes the notification template attachment where notificationTemplateId = &#63; and objectFieldId = &#63; from the database.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param objectFieldId the object field ID
	 * @return the notification template attachment that was removed
	 */
	public static NotificationTemplateAttachment removeByNTI_OFI(
			long notificationTemplateId, long objectFieldId)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateAttachmentException {

		return getPersistence().removeByNTI_OFI(
			notificationTemplateId, objectFieldId);
	}

	/**
	 * Returns the number of notification template attachments where notificationTemplateId = &#63; and objectFieldId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param objectFieldId the object field ID
	 * @return the number of matching notification template attachments
	 */
	public static int countByNTI_OFI(
		long notificationTemplateId, long objectFieldId) {

		return getPersistence().countByNTI_OFI(
			notificationTemplateId, objectFieldId);
	}

	/**
	 * Caches the notification template attachment in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateAttachment the notification template attachment
	 */
	public static void cacheResult(
		NotificationTemplateAttachment notificationTemplateAttachment) {

		getPersistence().cacheResult(notificationTemplateAttachment);
	}

	/**
	 * Caches the notification template attachments in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateAttachments the notification template attachments
	 */
	public static void cacheResult(
		List<NotificationTemplateAttachment> notificationTemplateAttachments) {

		getPersistence().cacheResult(notificationTemplateAttachments);
	}

	/**
	 * Creates a new notification template attachment with the primary key. Does not add the notification template attachment to the database.
	 *
	 * @param notificationTemplateAttachmentId the primary key for the new notification template attachment
	 * @return the new notification template attachment
	 */
	public static NotificationTemplateAttachment create(
		long notificationTemplateAttachmentId) {

		return getPersistence().create(notificationTemplateAttachmentId);
	}

	/**
	 * Removes the notification template attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationTemplateAttachmentId the primary key of the notification template attachment
	 * @return the notification template attachment that was removed
	 * @throws NoSuchNotificationTemplateAttachmentException if a notification template attachment with the primary key could not be found
	 */
	public static NotificationTemplateAttachment remove(
			long notificationTemplateAttachmentId)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateAttachmentException {

		return getPersistence().remove(notificationTemplateAttachmentId);
	}

	public static NotificationTemplateAttachment updateImpl(
		NotificationTemplateAttachment notificationTemplateAttachment) {

		return getPersistence().updateImpl(notificationTemplateAttachment);
	}

	/**
	 * Returns the notification template attachment with the primary key or throws a <code>NoSuchNotificationTemplateAttachmentException</code> if it could not be found.
	 *
	 * @param notificationTemplateAttachmentId the primary key of the notification template attachment
	 * @return the notification template attachment
	 * @throws NoSuchNotificationTemplateAttachmentException if a notification template attachment with the primary key could not be found
	 */
	public static NotificationTemplateAttachment findByPrimaryKey(
			long notificationTemplateAttachmentId)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateAttachmentException {

		return getPersistence().findByPrimaryKey(
			notificationTemplateAttachmentId);
	}

	/**
	 * Returns the notification template attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationTemplateAttachmentId the primary key of the notification template attachment
	 * @return the notification template attachment, or <code>null</code> if a notification template attachment with the primary key could not be found
	 */
	public static NotificationTemplateAttachment fetchByPrimaryKey(
		long notificationTemplateAttachmentId) {

		return getPersistence().fetchByPrimaryKey(
			notificationTemplateAttachmentId);
	}

	/**
	 * Returns all the notification template attachments.
	 *
	 * @return the notification template attachments
	 */
	public static List<NotificationTemplateAttachment> findAll() {
		return getPersistence().findAll();
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
	public static List<NotificationTemplateAttachment> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
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
	public static List<NotificationTemplateAttachment> findAll(
		int start, int end,
		OrderByComparator<NotificationTemplateAttachment> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
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
	public static List<NotificationTemplateAttachment> findAll(
		int start, int end,
		OrderByComparator<NotificationTemplateAttachment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the notification template attachments from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of notification template attachments.
	 *
	 * @return the number of notification template attachments
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static NotificationTemplateAttachmentPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		NotificationTemplateAttachmentPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile NotificationTemplateAttachmentPersistence
		_persistence;

}