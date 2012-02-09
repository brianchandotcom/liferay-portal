/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.NoSuchShardException;
import com.liferay.portal.kernel.dao.shard.ShardUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Shard;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ShardLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.RobotsUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyGroup extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyCompanyGroups();
		verifyNullFriendlyURLGroups();
		verifyRobots();
		verifyStagedGroups();
	}

	protected String getRobots(LayoutSet layoutSet) {
		if (layoutSet == null) {
			return RobotsUtil.getDefaultRobots(null);
		}

		String virtualHostname = StringPool.BLANK;

		try {
			virtualHostname = layoutSet.getVirtualHostname();
		}
		catch (Exception e) {
		}

		return GetterUtil.get(
			layoutSet.getSettingsProperty(
				layoutSet.isPrivateLayout() + "-robots.txt"),
			RobotsUtil.getDefaultRobots(virtualHostname));
	}

	protected void verifyCompanyGroups() throws Exception {
		List<Company> companies = CompanyLocalServiceUtil.getCompanies();

		String currentShardName = ShardUtil.getCurrentShardName();

		for (Company company : companies) {
			String shardName = null;

			try {
				shardName = company.getShardName();
			}
			catch (NoSuchShardException nsse) {
				Shard shard = ShardLocalServiceUtil.addShard(
					Company.class.getName(), company.getCompanyId(),
					PropsValues.SHARD_DEFAULT_NAME);

				shardName = shard.getName();
			}

			if (!ShardUtil.isEnabled() || shardName.equals(currentShardName)) {
				GroupLocalServiceUtil.checkCompanyGroup(company.getCompanyId());
			}
		}
	}

	protected void verifyNullFriendlyURLGroups() throws Exception {
		List<Group> groups = GroupLocalServiceUtil.getNullFriendlyURLGroups();

		for (Group group : groups) {
			String friendlyURL = StringPool.SLASH + group.getGroupId();

			User user = null;

			if (group.isUser()) {
				user = UserLocalServiceUtil.getUserById(group.getClassPK());

				friendlyURL = StringPool.SLASH + user.getScreenName();
			}
			else if (group.getClassPK() > 0) {
				friendlyURL = StringPool.SLASH + group.getClassPK();
			}

			try {
				GroupLocalServiceUtil.updateFriendlyURL(
					group.getGroupId(), friendlyURL);
			}
			catch (GroupFriendlyURLException gfurle) {
				if (user != null) {
					long userId = user.getUserId();
					String screenName = user.getScreenName();

					if (_log.isInfoEnabled()) {
						_log.info(
							"Updating user screen name " + screenName + " to " +
								userId + " because it is generating an " +
									"invalid friendly URL");
					}

					UserLocalServiceUtil.updateScreenName(
						userId, String.valueOf(userId));
				}
				else {
					_log.error("Invalid Friendly URL " + friendlyURL);

					throw gfurle;
				}
			}
		}
	}

	protected void verifyRobots() throws Exception {
		List<Group> groups = GroupLocalServiceUtil.getLiveGroups();

		for (Group group : groups) {
			LayoutSet privateLayoutSet = group.getPrivateLayoutSet();
			LayoutSet publicLayoutSet = group.getPublicLayoutSet();

			String privateLayoutSetRobots = getRobots(privateLayoutSet);
			String publicLayoutSetRobots = getRobots(publicLayoutSet);

			UnicodeProperties typeSettingsProperties =
				group.getTypeSettingsProperties();

			typeSettingsProperties.setProperty(
				"true-robots.txt", privateLayoutSetRobots);
			typeSettingsProperties.setProperty(
				"false-robots.txt", publicLayoutSetRobots);

			GroupLocalServiceUtil.updateGroup(
				group.getGroupId(), typeSettingsProperties.toString());
		}

	}
	protected void verifyStagedGroups() throws Exception {
		List<Group> groups = GroupLocalServiceUtil.getLiveGroups();

		for (Group group : groups) {
			if (!group.hasStagingGroup()) {
				continue;
			}

			UnicodeProperties typeSettingsProperties =
				group.getTypeSettingsProperties();

			typeSettingsProperties.setProperty(
				"staged", Boolean.TRUE.toString());
			typeSettingsProperties.setProperty(
				"stagedRemotely", Boolean.FALSE.toString());

			GroupLocalServiceUtil.updateGroup(
				group.getGroupId(), typeSettingsProperties.toString());

			Group stagingGroup = group.getStagingGroup();

			if (group.getClassNameId() != stagingGroup.getClassNameId()) {
				stagingGroup.setClassNameId(group.getClassNameId());

				GroupLocalServiceUtil.updateGroup(stagingGroup);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyGroup.class);

}