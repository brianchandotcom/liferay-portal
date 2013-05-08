/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for Subscription. This utility wraps
 * {@link com.liferay.portal.service.impl.SubscriptionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SubscriptionLocalService
 * @see com.liferay.portal.service.base.SubscriptionLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.SubscriptionLocalServiceImpl
 * @generated
 */
public class SubscriptionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.SubscriptionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the subscription to the database. Also notifies the appropriate model listeners.
	*
	* @param subscription the subscription
	* @return the subscription that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription addSubscription(
		com.liferay.portal.model.Subscription subscription)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSubscription(subscription);
	}

	/**
	* Creates a new subscription with the primary key. Does not add the subscription to the database.
	*
	* @param subscriptionId the primary key for the new subscription
	* @return the new subscription
	*/
	public static com.liferay.portal.model.Subscription createSubscription(
		long subscriptionId) {
		return getService().createSubscription(subscriptionId);
	}

	/**
	* Deletes the subscription with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param subscriptionId the primary key of the subscription
	* @return the subscription that was removed
	* @throws PortalException if a subscription with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription deleteSubscription(
		long subscriptionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteSubscription(subscriptionId);
	}

	/**
	* Deletes the subscription from the database. Also notifies the appropriate model listeners.
	*
	* @param subscription the subscription
	* @return the subscription that was removed
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription deleteSubscription(
		com.liferay.portal.model.Subscription subscription)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteSubscription(subscription);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.SubscriptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.SubscriptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.Subscription fetchSubscription(
		long subscriptionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchSubscription(subscriptionId);
	}

	/**
	* Returns the subscription with the primary key.
	*
	* @param subscriptionId the primary key of the subscription
	* @return the subscription
	* @throws PortalException if a subscription with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription getSubscription(
		long subscriptionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSubscription(subscriptionId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the subscriptions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.SubscriptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of subscriptions
	* @param end the upper bound of the range of subscriptions (not inclusive)
	* @return the range of subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> getSubscriptions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSubscriptions(start, end);
	}

	/**
	* Returns the number of subscriptions.
	*
	* @return the number of subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static int getSubscriptionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSubscriptionsCount();
	}

	/**
	* Updates the subscription in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param subscription the subscription
	* @return the subscription that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription updateSubscription(
		com.liferay.portal.model.Subscription subscription)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSubscription(subscription);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* Subscribes the user to the entity, notifying him the instant the entity
	* is created, deleted, or modified.
	*
	* <p>
	* If there is no asset entry with the class name and class PK a new asset
	* entry is created.
	* </p>
	*
	* <p>
	* A social activity for the subscription is created using the asset entry
	* associated with the class name and class PK, or the newly created asset
	* entry.
	* </p>
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the entity's group
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	* @return the subscription
	* @throws PortalException if a matching user or group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription addSubscription(
		long userId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addSubscription(userId, groupId, className, classPK);
	}

	/**
	* Subscribes the user to the entity, notifying him at the given frequency.
	*
	* <p>
	* If there is no asset entry with the class name and class PK a new asset
	* entry is created.
	* </p>
	*
	* <p>
	* A social activity for the subscription is created using the asset entry
	* associated with the class name and class PK, or the newly created asset
	* entry.
	* </p>
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the entity's group
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	* @param frequency the frequency for notifications
	* @return the subscription
	* @throws PortalException if a matching user or group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription addSubscription(
		long userId, long groupId, java.lang.String className, long classPK,
		java.lang.String frequency)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addSubscription(userId, groupId, className, classPK,
			frequency);
	}

	/**
	* Deletes the user's subscription to the entity. A social activity with the
	* unsubscribe action is created.
	*
	* @param userId the primary key of the user
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	* @throws PortalException if a matching user or subscription could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSubscription(long userId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSubscription(userId, className, classPK);
	}

	/**
	* Deletes all the subscriptions of the user.
	*
	* @param userId the primary key of the user
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSubscriptions(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSubscriptions(userId);
	}

	/**
	* Deletes all the subscriptions to the entity.
	*
	* @param companyId the primary key of the company
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSubscriptions(long companyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSubscriptions(companyId, className, classPK);
	}

	/**
	* Returns the subscription of the user to the entity.
	*
	* @param companyId the primary key of the company
	* @param userId the primary key of the user
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	* @return the subscription of the user to the entity
	* @throws PortalException if a matching subscription could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription getSubscription(
		long companyId, long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getSubscription(companyId, userId, className, classPK);
	}

	/**
	* Returns all the subscriptions of the user to the entities.
	*
	* @param companyId the primary key of the company
	* @param userId the primary key of the user
	* @param className the entity's class name
	* @param classPKs the primary key of the entities
	* @return the subscriptions of the user to the entities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> getSubscriptions(
		long companyId, long userId, java.lang.String className, long[] classPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getSubscriptions(companyId, userId, className, classPKs);
	}

	/**
	* Returns all the subscriptions to the entity.
	*
	* @param companyId the primary key of the company
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	* @return the subscriptions to the entity
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> getSubscriptions(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSubscriptions(companyId, className, classPK);
	}

	/**
	* Returns an ordered range of all the subscriptions of the user.
	*
	* @param userId the primary key of the user
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of subscriptions of the user
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> getUserSubscriptions(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getUserSubscriptions(userId, start, end, orderByComparator);
	}

	/**
	* Returns all the subscriptions of the user to the entities with the class
	* name.
	*
	* @param userId the primary key of the user
	* @param className the entity's class name
	* @return the subscriptions of the user to the entities with the class name
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> getUserSubscriptions(
		long userId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserSubscriptions(userId, className);
	}

	/**
	* Returns the number of subscriptions of the user.
	*
	* @param userId the primary key of the user
	* @return the number of subscriptions of the user
	* @throws SystemException if a system exception occurred
	*/
	public static int getUserSubscriptionsCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserSubscriptionsCount(userId);
	}

	/**
	* Returns <code>true</code> if the user is subscribed to the entity.
	*
	* @param companyId the primary key of the company
	* @param userId the primary key of the user
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	* @return <code>true</code> if the user is subscribed to the entity;
	<code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean isSubscribed(long companyId, long userId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().isSubscribed(companyId, userId, className, classPK);
	}

	/**
	* Returns <code>true</code> if the user is subscribed to any of the
	* entities.
	*
	* @param companyId the primary key of the company
	* @param userId the primary key of the user
	* @param className the entity's class name
	* @param classPKs the primary key of the entities
	* @return <code>true</code> if the user is subscribed to any of the
	entities; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean isSubscribed(long companyId, long userId,
		java.lang.String className, long[] classPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().isSubscribed(companyId, userId, className, classPKs);
	}

	public static SubscriptionLocalService getService() {
		if (_service == null) {
			_service = (SubscriptionLocalService)PortalBeanLocatorUtil.locate(SubscriptionLocalService.class.getName());

			ReferenceRegistry.registerReference(SubscriptionLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(SubscriptionLocalService service) {
	}

	private static SubscriptionLocalService _service;
}