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

package com.liferay.chat.util;

import com.liferay.chat.configuration.ChatGroupServiceConfiguration;
import com.liferay.chat.jabber.JabberUtil;
import com.liferay.chat.service.StatusLocalServiceUtil;
import com.liferay.chat.util.comparator.BuddyComparator;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Ankit Srivastava
 * @author Tibor Lipusz
 * @author Peter Fellwock
 */
@Component(
	configurationPid = "com.liferay.chat.configuration.ChatConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = BuddyFinder.class
)
public class DefaultBuddyFinderImpl implements BuddyFinder {

	@Override
	public List<Object[]> getBuddies(long companyId, long userId) {
		long modifiedDate =
			System.currentTimeMillis() - ChatConstants.ONLINE_DELTA;

		List<Object[]> buddies = null;

		String buddyListStrategy =
			_chatGroupServiceConfiguration.buddyListStrategy();

		if (buddyListStrategy.equals("all")) {
			buddies = StatusLocalServiceUtil.getAllStatuses(
				companyId, userId, modifiedDate, 0,
				_chatGroupServiceConfiguration.buddyListMaxBuddies());
		}
		else if (buddyListStrategy.equals("communities") ||
				 buddyListStrategy.equals("sites")) {

			buddies = StatusLocalServiceUtil.getGroupStatuses(
				userId, modifiedDate,
				_chatGroupServiceConfiguration.buddyListSiteExcludes(), 0,
				_chatGroupServiceConfiguration.buddyListMaxBuddies());
		}
		else if (buddyListStrategy.equals("friends") ||
				 buddyListStrategy.equals("social")) {

			buddies = StatusLocalServiceUtil.getSocialStatuses(
				userId,
				_chatGroupServiceConfiguration.
					buddyListAllowedSocialRelationTypes(),
				modifiedDate, 0,
				_chatGroupServiceConfiguration.buddyListMaxBuddies());
		}
		else if (buddyListStrategy.equals("communities,friends") ||
				 buddyListStrategy.equals("sites,social") ||
				 buddyListStrategy.equals("friends,sites")) {

			List<Object[]> groupBuddies =
				StatusLocalServiceUtil.getGroupStatuses(
					userId, modifiedDate,
					_chatGroupServiceConfiguration.buddyListSiteExcludes(), 0,
					_chatGroupServiceConfiguration.buddyListMaxBuddies());
			List<Object[]> socialBuddies =
				StatusLocalServiceUtil.getSocialStatuses(
					userId,
					_chatGroupServiceConfiguration.
						buddyListAllowedSocialRelationTypes(),
					modifiedDate, 0,
					_chatGroupServiceConfiguration.buddyListMaxBuddies());

			buddies = new ArrayList<>(
				groupBuddies.size() + socialBuddies.size());

			buddies.addAll(groupBuddies);

			BuddyComparator buddyComparator = new BuddyComparator(true);

			for (Object[] socialBuddy : socialBuddies) {
				if (Collections.binarySearch(
						groupBuddies, socialBuddy, buddyComparator) < 0) {

					buddies.add(socialBuddy);
				}
			}

			Collections.sort(buddies, buddyComparator);
		}
		else {
			buddies = new ArrayList<>();
		}

		buddies = JabberUtil.getStatuses(companyId, userId, buddies);

		return buddies;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_chatGroupServiceConfiguration = ConfigurableUtil.createConfigurable(
			ChatGroupServiceConfiguration.class, properties);
	}

	private ChatGroupServiceConfiguration _chatGroupServiceConfiguration;

}