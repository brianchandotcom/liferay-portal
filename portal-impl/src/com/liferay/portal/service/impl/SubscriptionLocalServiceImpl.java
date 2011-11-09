/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.SubscriptionConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.base.SubscriptionLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.social.model.SocialActivityConstants;

import java.util.Date;
import java.util.List;

/**
 * @author Charles May
 * @author Zsolt Berentey
 */
public class SubscriptionLocalServiceImpl
	extends SubscriptionLocalServiceBaseImpl {

	public Subscription addSubscription(
			long userId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		return addSubscription(
			userId, groupId, className, classPK,
			SubscriptionConstants.FREQUENCY_INSTANT);
	}

	public Subscription addSubscription(
			long userId, long groupId, String className, long classPK,
			String frequency)
		throws PortalException, SystemException {

		// Subscription

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		long subscriptionId = counterLocalService.increment();

		Subscription subscription = subscriptionPersistence.fetchByC_U_C_C(
			user.getCompanyId(), userId, classNameId, classPK);

		if (subscription == null) {
			subscription = subscriptionPersistence.create(subscriptionId);

			subscription.setCompanyId(user.getCompanyId());
			subscription.setUserId(user.getUserId());
			subscription.setUserName(user.getFullName());
			subscription.setCreateDate(now);
			subscription.setModifiedDate(now);
			subscription.setClassNameId(classNameId);
			subscription.setClassPK(classPK);
			subscription.setFrequency(frequency);

			subscriptionPersistence.update(subscription, false);
		}

		if (groupId > 0) {

			// Asset

			try {
				assetEntryLocalService.getEntry(className, classPK);
			}
			catch (Exception e) {
				assetEntryLocalService.updateEntry(
					userId, groupId, className, classPK, null, 0,
					null, null, false, null, null, null, null, null,
					String.valueOf(groupId), null, null, null, null, 0, 0, null,
					false);
			}

			// Social

			if (className.equals(MBThread.class.getName())) {
				MBThread mbThread = mbThreadLocalService.getMBThread(classPK);

				JSONObject extraDataJSONObject =
					JSONFactoryUtil.createJSONObject();

				extraDataJSONObject.put("threadId", classPK);

				socialActivityLocalService.addActivity(
					userId, groupId, MBMessage.class.getName(),
					mbThread.getRootMessageId(),
					SocialActivityConstants.TYPE_SUBSCRIBE,
					extraDataJSONObject.toString(), 0);
			}
			else {
				socialActivityLocalService.addActivity(
					userId, groupId, className, classPK,
					SocialActivityConstants.TYPE_SUBSCRIBE, StringPool.BLANK,
					0);
			}
		}

		return subscription;
	}

	@Override
	public void deleteSubscription(long subscriptionId)
		throws PortalException, SystemException {

		Subscription subscription = subscriptionPersistence.fetchByPrimaryKey(
			subscriptionId);

		deleteSubscription(subscription);
	}

	public void deleteSubscription(
			long userId, String className, long classPK)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);

		Subscription subscription = subscriptionPersistence.findByC_U_C_C(
			user.getCompanyId(), userId, classNameId, classPK);

		deleteSubscription(subscription);
	}

	@Override
	public void deleteSubscription(Subscription subscription)
		throws PortalException, SystemException {

		// Subscription

		subscriptionPersistence.remove(subscription);

		// Social

		AssetEntry assetEntry = assetEntryPersistence.fetchByC_C(
			subscription.getClassNameId(), subscription.getClassPK());

		if (assetEntry != null) {
			String className = PortalUtil.getClassName(
				subscription.getClassNameId());

			socialActivityLocalService.addActivity(
				subscription.getUserId(), assetEntry.getGroupId(), className,
				subscription.getClassPK(),
				SocialActivityConstants.TYPE_UNSUBSCRIBE, StringPool.BLANK, 0);
		}
	}

	public void deleteSubscriptions(long userId)
		throws PortalException, SystemException {

		List<Subscription> subscriptions = subscriptionPersistence.findByUserId(
			userId);

		for (Subscription subscription : subscriptions) {
			deleteSubscription(subscription);
		}
	}

	public void deleteSubscriptions(
			long companyId, String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		List<Subscription> subscriptions = subscriptionPersistence.findByC_C_C(
			companyId, classNameId, classPK);

		for (Subscription subscription : subscriptions) {
			deleteSubscription(subscription);
		}
	}

	public Subscription getSubscription(
			long companyId, long userId, String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return subscriptionPersistence.findByC_U_C_C(
			companyId, userId, classNameId, classPK);
	}

	public List<Subscription> getSubscriptions(
			long companyId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return subscriptionPersistence.findByC_C_C(
			companyId, classNameId, classPK);
	}

	public List<Subscription> getUserSubscriptions(
			long userId, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return subscriptionPersistence.findByUserId(
			userId, start, end, orderByComparator);
	}

	public List<Subscription> getUserSubscriptions(
			long userId, String className)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return subscriptionPersistence.findByU_C(userId, classNameId);
	}

	public int getUserSubscriptionsCount(long userId) throws SystemException {
		return subscriptionPersistence.countByUserId(userId);
	}

	public boolean isSubscribed(
			long companyId, long userId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		Subscription subscription = subscriptionPersistence.fetchByC_U_C_C(
			companyId, userId, classNameId, classPK);

		if (subscription != null) {
			return true;
		}
		else {
			return false;
		}
	}

}