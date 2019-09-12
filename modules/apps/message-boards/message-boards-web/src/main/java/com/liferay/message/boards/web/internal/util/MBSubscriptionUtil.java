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

package com.liferay.message.boards.web.internal.util;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBThread;
import com.liferay.subscription.model.Subscription;
import com.liferay.subscription.service.SubscriptionLocalServiceUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public class MBSubscriptionUtil {

	public static Set<Long> getCategorySubscriptionClassPKs(long userId) {
		List<Subscription> subscriptions =
			SubscriptionLocalServiceUtil.getUserSubscriptions(
				userId, MBCategory.class.getName());

		Set<Long> classPKs = new HashSet<>();

		for (Subscription subscription : subscriptions) {
			classPKs.add(subscription.getClassPK());
		}

		return classPKs;
	}

	public static Set<Long> getThreadSubscriptionClassPKs(long userId) {
		List<Subscription> subscriptions =
			SubscriptionLocalServiceUtil.getUserSubscriptions(
				userId, MBThread.class.getName());

		Set<Long> classPKs = new HashSet<>();

		for (Subscription subscription : subscriptions) {
			classPKs.add(subscription.getClassPK());
		}

		return classPKs;
	}

}