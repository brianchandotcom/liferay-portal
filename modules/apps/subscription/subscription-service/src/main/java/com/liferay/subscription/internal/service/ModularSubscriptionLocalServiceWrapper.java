/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.subscription.internal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.SubscriptionLocalServiceWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.adapter.ModelAdapterUtil;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ServiceWrapper.class)
public class ModularSubscriptionLocalServiceWrapper
	extends SubscriptionLocalServiceWrapper {

	@Override
	public Subscription addSubscription(
			long userId, long groupId, String className, long classPK)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			Subscription.class,
			_subscriptionLocalService.addSubscription(
				userId, groupId, className, classPK));
	}

	@Override
	public Subscription addSubscription(
			long userId, long groupId, String className, long classPK,
			String frequency)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			Subscription.class,
			_subscriptionLocalService.addSubscription(
				userId, groupId, className, classPK, frequency));
	}

	@Override
	public Subscription deleteSubscription(long subscriptionId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			Subscription.class,
			_subscriptionLocalService.deleteSubscription(subscriptionId));
	}

	@Override
	public void deleteSubscription(long userId, String className, long classPK)
		throws PortalException {

		_subscriptionLocalService.deleteSubscription(
			userId, className, classPK);
	}

	@Override
	public Subscription deleteSubscription(Subscription subscription)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			Subscription.class,
			_subscriptionLocalService.deleteSubscription(
				subscription.getSubscriptionId()));
	}

	@Override
	public void deleteSubscriptions(long userId) throws PortalException {
		_subscriptionLocalService.deleteSubscriptions(userId);
	}

	@Override
	public void deleteSubscriptions(long userId, long groupId)
		throws PortalException {

		_subscriptionLocalService.deleteSubscriptions(userId, groupId);
	}

	@Override
	public void deleteSubscriptions(
			long companyId, String className, long classPK)
		throws PortalException {

		_subscriptionLocalService.deleteSubscriptions(
			companyId, className, classPK);
	}

	@Override
	public Subscription fetchSubscription(
		long companyId, long userId, String className, long classPK) {

		return ModelAdapterUtil.adapt(
			Subscription.class,
			_subscriptionLocalService.fetchSubscription(
				companyId, userId, className, classPK));
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _subscriptionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public Subscription getSubscription(
			long companyId, long userId, String className, long classPK)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			Subscription.class,
			_subscriptionLocalService.getSubscription(
				companyId, userId, className, classPK));
	}

	@Override
	public List<Subscription> getSubscriptions(
		long companyId, long userId, String className, long[] classPKs) {

		return ModelAdapterUtil.adapt(
			Subscription.class,
			_subscriptionLocalService.getSubscriptions(
				companyId, userId, className, classPKs));
	}

	@Override
	public List<Subscription> getSubscriptions(
		long companyId, String className, long classPK) {

		return ModelAdapterUtil.adapt(
			Subscription.class,
			_subscriptionLocalService.getSubscriptions(
				companyId, className, classPK));
	}

	@Override
	public List<Subscription> getUserSubscriptions(
		long userId, int start, int end,
		OrderByComparator<Subscription> orderByComparator) {

		return ModelAdapterUtil.adapt(
			Subscription.class,
			_subscriptionLocalService.getUserSubscriptions(
				userId, start, end,
				ModelAdapterUtil.adapt(Subscription.class, orderByComparator)));
	}

	@Override
	public List<Subscription> getUserSubscriptions(
		long userId, String className) {

		return ModelAdapterUtil.adapt(
			Subscription.class,
			_subscriptionLocalService.getUserSubscriptions(userId, className));
	}

	@Override
	public int getUserSubscriptionsCount(long userId) {
		return _subscriptionLocalService.getUserSubscriptionsCount(userId);
	}

	@Override
	public boolean isSubscribed(
		long companyId, long userId, String className, long classPK) {

		return _subscriptionLocalService.isSubscribed(
			companyId, userId, className, classPK);
	}

	@Override
	public boolean isSubscribed(
		long companyId, long userId, String className, long[] classPKs) {

		return _subscriptionLocalService.isSubscribed(
			companyId, userId, className, classPKs);
	}

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}