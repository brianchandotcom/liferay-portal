/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.notification.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceNotificationQueueEntry. This utility wraps
 * {@link com.liferay.commerce.notification.service.impl.CommerceNotificationQueueEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationQueueEntryLocalService
 * @see com.liferay.commerce.notification.service.base.CommerceNotificationQueueEntryLocalServiceBaseImpl
 * @see com.liferay.commerce.notification.service.impl.CommerceNotificationQueueEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceNotificationQueueEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.notification.service.impl.CommerceNotificationQueueEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce notification queue entry to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationQueueEntry the commerce notification queue entry
	* @return the commerce notification queue entry that was added
	*/
	public static com.liferay.commerce.notification.model.CommerceNotificationQueueEntry addCommerceNotificationQueueEntry(
		com.liferay.commerce.notification.model.CommerceNotificationQueueEntry commerceNotificationQueueEntry) {
		return getService()
				   .addCommerceNotificationQueueEntry(commerceNotificationQueueEntry);
	}

	public static com.liferay.commerce.notification.model.CommerceNotificationQueueEntry addCommerceNotificationQueueEntry(
		long userId, long groupId, long commerceNotificationTemplateId,
		String from, String fromName, String to, String toName, String cc,
		String bcc, String subject, String body, double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceNotificationQueueEntry(userId, groupId,
			commerceNotificationTemplateId, from, fromName, to, toName, cc,
			bcc, subject, body, priority);
	}

	/**
	* Creates a new commerce notification queue entry with the primary key. Does not add the commerce notification queue entry to the database.
	*
	* @param commerceNotificationQueueEntryId the primary key for the new commerce notification queue entry
	* @return the new commerce notification queue entry
	*/
	public static com.liferay.commerce.notification.model.CommerceNotificationQueueEntry createCommerceNotificationQueueEntry(
		long commerceNotificationQueueEntryId) {
		return getService()
				   .createCommerceNotificationQueueEntry(commerceNotificationQueueEntryId);
	}

	public static com.liferay.commerce.notification.model.CommerceNotificationQueueEntry deleteCommerceNotificationQueue(
		com.liferay.commerce.notification.model.CommerceNotificationQueueEntry commerceNotificationQueueEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceNotificationQueue(commerceNotificationQueueEntry);
	}

	public static void deleteCommerceNotificationQueueEntries(
		java.util.Date sentDate) {
		getService().deleteCommerceNotificationQueueEntries(sentDate);
	}

	public static void deleteCommerceNotificationQueueEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceNotificationQueueEntries(groupId);
	}

	/**
	* Deletes the commerce notification queue entry from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationQueueEntry the commerce notification queue entry
	* @return the commerce notification queue entry that was removed
	*/
	public static com.liferay.commerce.notification.model.CommerceNotificationQueueEntry deleteCommerceNotificationQueueEntry(
		com.liferay.commerce.notification.model.CommerceNotificationQueueEntry commerceNotificationQueueEntry) {
		return getService()
				   .deleteCommerceNotificationQueueEntry(commerceNotificationQueueEntry);
	}

	/**
	* Deletes the commerce notification queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	* @return the commerce notification queue entry that was removed
	* @throws PortalException if a commerce notification queue entry with the primary key could not be found
	*/
	public static com.liferay.commerce.notification.model.CommerceNotificationQueueEntry deleteCommerceNotificationQueueEntry(
		long commerceNotificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceNotificationQueueEntry(commerceNotificationQueueEntryId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.notification.model.impl.CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.notification.model.impl.CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.commerce.notification.model.CommerceNotificationQueueEntry fetchCommerceNotificationQueueEntry(
		long commerceNotificationQueueEntryId) {
		return getService()
				   .fetchCommerceNotificationQueueEntry(commerceNotificationQueueEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.commerce.notification.model.CommerceNotificationQueueEntry> getCommerceNotificationQueueEntries(
		boolean sent) {
		return getService().getCommerceNotificationQueueEntries(sent);
	}

	/**
	* Returns a range of all the commerce notification queue entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.notification.model.impl.CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @return the range of commerce notification queue entries
	*/
	public static java.util.List<com.liferay.commerce.notification.model.CommerceNotificationQueueEntry> getCommerceNotificationQueueEntries(
		int start, int end) {
		return getService().getCommerceNotificationQueueEntries(start, end);
	}

	public static java.util.List<com.liferay.commerce.notification.model.CommerceNotificationQueueEntry> getCommerceNotificationQueueEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.notification.model.CommerceNotificationQueueEntry> orderByComparator) {
		return getService()
				   .getCommerceNotificationQueueEntries(groupId, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of commerce notification queue entries.
	*
	* @return the number of commerce notification queue entries
	*/
	public static int getCommerceNotificationQueueEntriesCount() {
		return getService().getCommerceNotificationQueueEntriesCount();
	}

	public static int getCommerceNotificationQueueEntriesCount(long groupId) {
		return getService().getCommerceNotificationQueueEntriesCount(groupId);
	}

	/**
	* Returns the commerce notification queue entry with the primary key.
	*
	* @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	* @return the commerce notification queue entry
	* @throws PortalException if a commerce notification queue entry with the primary key could not be found
	*/
	public static com.liferay.commerce.notification.model.CommerceNotificationQueueEntry getCommerceNotificationQueueEntry(
		long commerceNotificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceNotificationQueueEntry(commerceNotificationQueueEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.commerce.notification.model.CommerceNotificationQueueEntry resendCommerceNotificationQueueEntry(
		long commerceNotificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .resendCommerceNotificationQueueEntry(commerceNotificationQueueEntryId);
	}

	public static void sendCommerceNotificationQueueEntries()
		throws Exception {
		getService().sendCommerceNotificationQueueEntries();
	}

	public static void updateCommerceNotificationQueueEntriesTemplateIds(
		long commerceNotificationTemplateId) {
		getService()
			.updateCommerceNotificationQueueEntriesTemplateIds(commerceNotificationTemplateId);
	}

	/**
	* Updates the commerce notification queue entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationQueueEntry the commerce notification queue entry
	* @return the commerce notification queue entry that was updated
	*/
	public static com.liferay.commerce.notification.model.CommerceNotificationQueueEntry updateCommerceNotificationQueueEntry(
		com.liferay.commerce.notification.model.CommerceNotificationQueueEntry commerceNotificationQueueEntry) {
		return getService()
				   .updateCommerceNotificationQueueEntry(commerceNotificationQueueEntry);
	}

	public static com.liferay.commerce.notification.model.CommerceNotificationQueueEntry updateSent(
		long commerceNotificationQueueEntryId, boolean sent)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateSent(commerceNotificationQueueEntryId, sent);
	}

	public static CommerceNotificationQueueEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceNotificationQueueEntryLocalService, CommerceNotificationQueueEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceNotificationQueueEntryLocalService.class);

		ServiceTracker<CommerceNotificationQueueEntryLocalService, CommerceNotificationQueueEntryLocalService> serviceTracker =
			new ServiceTracker<CommerceNotificationQueueEntryLocalService, CommerceNotificationQueueEntryLocalService>(bundle.getBundleContext(),
				CommerceNotificationQueueEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}