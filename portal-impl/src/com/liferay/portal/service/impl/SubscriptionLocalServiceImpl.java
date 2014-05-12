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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.SubscriptionConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.base.SubscriptionLocalServiceBaseImpl;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.social.model.SocialActivityConstants;

import java.util.Date;
import java.util.List;

/**
 * Provides the local service for accessing, adding, and deleting notification
 * subscriptions to entities. It handles subscriptions to entities found in many
 * different places in the portal, including message boards, blogs, and
 * documents and media.
 *
 * @author Charles May
 * @author Zsolt Berentey
 */
public class SubscriptionLocalServiceImpl
	extends SubscriptionLocalServiceBaseImpl {

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
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the entity's group
	 * @param  className the entity's class name
	 * @param  classPK the primary key of the entity's instance
	 * @return the subscription
	 * @throws PortalException if a matching user or group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Subscription addSubscription(
			long userId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		return addSubscription(
			userId, groupId, className, classPK,
			SubscriptionConstants.FREQUENCY_INSTANT);
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
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the entity's group
	 * @param  className the entity's class name
	 * @param  classPK the primary key of the entity's instance
	 * @param  frequency the frequency for notifications
	 * @return the subscription
	 * @throws PortalException if a matching user or group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Subscription addSubscription(
			long userId, long groupId, String className, long classPK,
			String frequency)
		throws PortalException, SystemException {

		// Subscription

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = classNameLocalService.getClassNameId(className);
		Date now = new Date();

		Subscription subscription = subscriptionPersistence.fetchByC_U_C_C(
			user.getCompanyId(), userId, classNameId, classPK);

		if (subscription == null) {
			long subscriptionId = counterLocalService.increment();

			subscription = subscriptionPersistence.create(subscriptionId);

			subscription.setCompanyId(user.getCompanyId());
			subscription.setUserId(user.getUserId());
			subscription.setUserName(user.getFullName());
			subscription.setCreateDate(now);
			subscription.setModifiedDate(now);
			subscription.setClassNameId(classNameId);
			subscription.setClassPK(classPK);
			subscription.setFrequency(frequency);

			subscriptionPersistence.update(subscription);
		}

		if (groupId > 0) {

			// Asset

			AssetEntry assetEntry = null;

			try {
				assetEntry = assetEntryLocalService.getEntry(
					className, classPK);
			}
			catch (Exception e) {
				assetEntry = assetEntryLocalService.updateEntry(
					userId, groupId, subscription.getCreateDate(),
					subscription.getModifiedDate(), className, classPK, null, 0,
					null, null, false, null, null, null, null,
					String.valueOf(groupId), null, null, null, null, 0, 0, null,
					false);
			}

			// Social

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put("title", assetEntry.getTitle());

			if (className.equals(MBThread.class.getName())) {
				MBThread mbThread = mbThreadLocalService.getMBThread(classPK);

				extraDataJSONObject.put("threadId", classPK);

				socialActivityLocalService.addActivity(
					userId, groupId, MBMessage.class.getName(),
					mbThread.getRootMessageId(),
					SocialActivityConstants.TYPE_SUBSCRIBE,
					extraDataJSONObject.toString(), 0);
			}
			else {
				if (classPK != groupId) {
					socialActivityLocalService.addActivity(
						userId, groupId, className, classPK,
						SocialActivityConstants.TYPE_SUBSCRIBE,
						extraDataJSONObject.toString(), 0);
				}
			}
		}

		return subscription;
	}

	/**
	 * Deletes the subscription with the primary key. A social activity with the
	 * unsubscribe action is created.
	 *
	 * @param  subscriptionId the primary key of the subscription
	 * @return the subscription that was removed
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Subscription deleteSubscription(long subscriptionId)
		throws PortalException, SystemException {

		Subscription subscription = subscriptionPersistence.fetchByPrimaryKey(
			subscriptionId);

		return deleteSubscription(subscription);
	}

	/**
	 * Deletes the user's subscription to the entity. A social activity with the
	 * unsubscribe action is created.
	 *
	 * @param  userId the primary key of the user
	 * @param  className the entity's class name
	 * @param  classPK the primary key of the entity's instance
	 * @throws PortalException if a matching user or subscription could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteSubscription(long userId, String className, long classPK)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = classNameLocalService.getClassNameId(className);

		Subscription subscription = subscriptionPersistence.fetchByC_U_C_C(
			user.getCompanyId(), userId, classNameId, classPK);

		if (subscription != null) {
			deleteSubscription(subscription);
		}
	}

	/**
	 * Deletes the subscription. A social activity with the unsubscribe action
	 * is created.
	 *
	 * @param  subscription the subscription
	 * @return the subscription that was removed
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Subscription deleteSubscription(Subscription subscription)
		throws PortalException, SystemException {

		// Subscription

		subscriptionPersistence.remove(subscription);

		// Social

		AssetEntry assetEntry = assetEntryPersistence.fetchByC_C(
			subscription.getClassNameId(), subscription.getClassPK());

		if (assetEntry != null) {
			ClassName className = classNameLocalService.getClassName(
				subscription.getClassNameId());

			String subscriptionClassName = className.getValue();

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put("title", assetEntry.getTitle());

			socialActivityLocalService.addActivity(
				subscription.getUserId(), assetEntry.getGroupId(),
				subscriptionClassName, subscription.getClassPK(),
				SocialActivityConstants.TYPE_UNSUBSCRIBE,
				extraDataJSONObject.toString(), 0);
		}

		return subscription;
	}

	/**
	 * Deletes all the subscriptions of the user.
	 *
	 * @param  userId the primary key of the user
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteSubscriptions(long userId)
		throws PortalException, SystemException {

		List<Subscription> subscriptions = subscriptionPersistence.findByUserId(
			userId);

		for (Subscription subscription : subscriptions) {
			deleteSubscription(subscription);
		}
	}

	/**
	 * Deletes all the subscriptions to the entity.
	 *
	 * @param  companyId the primary key of the company
	 * @param  className the entity's class name
	 * @param  classPK the primary key of the entity's instance
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteSubscriptions(
			long companyId, String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = classNameLocalService.getClassNameId(className);

		List<Subscription> subscriptions = subscriptionPersistence.findByC_C_C(
			companyId, classNameId, classPK);

		for (Subscription subscription : subscriptions) {
			deleteSubscription(subscription);
		}
	}

	/**
	 * Returns the subscription of the user to the entity.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user
	 * @param  className the entity's class name
	 * @param  classPK the primary key of the entity's instance
	 * @return the subscription of the user to the entity
	 * @throws PortalException if a matching subscription could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Subscription getSubscription(
			long companyId, long userId, String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = classNameLocalService.getClassNameId(className);

		return subscriptionPersistence.findByC_U_C_C(
			companyId, userId, classNameId, classPK);
	}

	/**
	 * Returns all the subscriptions of the user to the entities.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user
	 * @param  className the entity's class name
	 * @param  classPKs the primary key of the entities
	 * @return the subscriptions of the user to the entities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Subscription> getSubscriptions(
			long companyId, long userId, String className, long[] classPKs)
		throws SystemException {

		long classNameId = classNameLocalService.getClassNameId(className);

		return subscriptionPersistence.findByC_U_C_C(
			companyId, userId, classNameId, classPKs);
	}

	/**
	 * Returns all the subscriptions to the entity.
	 *
	 * @param  companyId the primary key of the company
	 * @param  className the entity's class name
	 * @param  classPK the primary key of the entity's instance
	 * @return the subscriptions to the entity
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Subscription> getSubscriptions(
			long companyId, String className, long classPK)
		throws SystemException {

		long classNameId = classNameLocalService.getClassNameId(className);

		return subscriptionPersistence.findByC_C_C(
			companyId, classNameId, classPK);
	}

	/**
	 * Returns an ordered range of all the subscriptions of the user.
	 *
	 * @param  userId the primary key of the user
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  orderByComparator the comparator to order the subscriptions
	 * @return the range of subscriptions of the user
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Subscription> getUserSubscriptions(
			long userId, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return subscriptionPersistence.findByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns all the subscriptions of the user to the entities with the class
	 * name.
	 *
	 * @param  userId the primary key of the user
	 * @param  className the entity's class name
	 * @return the subscriptions of the user to the entities with the class name
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Subscription> getUserSubscriptions(
			long userId, String className)
		throws SystemException {

		long classNameId = classNameLocalService.getClassNameId(className);

		return subscriptionPersistence.findByU_C(userId, classNameId);
	}

	/**
	 * Returns the number of subscriptions of the user.
	 *
	 * @param  userId the primary key of the user
	 * @return the number of subscriptions of the user
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getUserSubscriptionsCount(long userId) throws SystemException {
		return subscriptionPersistence.countByUserId(userId);
	}

	/**
	 * Returns <code>true</code> if the user is subscribed to the entity.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user
	 * @param  className the entity's class name
	 * @param  classPK the primary key of the entity's instance
	 * @return <code>true</code> if the user is subscribed to the entity;
	 *         <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean isSubscribed(
			long companyId, long userId, String className, long classPK)
		throws SystemException {

		long classNameId = classNameLocalService.getClassNameId(className);

		Subscription subscription = subscriptionPersistence.fetchByC_U_C_C(
			companyId, userId, classNameId, classPK);

		if (subscription != null) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the user is subscribed to any of the
	 * entities.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user
	 * @param  className the entity's class name
	 * @param  classPKs the primary key of the entities
	 * @return <code>true</code> if the user is subscribed to any of the
	 *         entities; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean isSubscribed(
			long companyId, long userId, String className, long[] classPKs)
		throws SystemException {

		long classNameId = classNameLocalService.getClassNameId(className);

		int count = subscriptionPersistence.countByC_U_C_C(
			companyId, userId, classNameId, classPKs);

		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}

}