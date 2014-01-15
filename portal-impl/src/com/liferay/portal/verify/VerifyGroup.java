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

package com.liferay.portal.verify;

import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.NoSuchShardException;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.shard.ShardUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Shard;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ShardLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.impl.GroupLocalServiceImpl;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.RobotsUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyGroup extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyCompanyGroups();
		verifyNullFriendlyURLGroups();
		verifyOrganizationNames();
		verifyRobots();
		verifySites();
		verifyStagedGroups();
		verifyTree();
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

	protected void updateName(long groupId, String name) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update Group_ set name = ? where groupId= " + groupId);

			ps.setString(1, name);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
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

				GroupLocalServiceUtil.checkSystemGroups(company.getCompanyId());
			}
		}
	}

	protected void verifyNullFriendlyURLGroups() throws Exception {
		List<Group> groups = GroupLocalServiceUtil.getNullFriendlyURLGroups();

		for (Group group : groups) {
			String friendlyURL = StringPool.SLASH + group.getGroupId();

			User user = null;

			if (group.isCompany() && !group.isCompanyStagingGroup()) {
				friendlyURL = GroupConstants.GLOBAL_FRIENDLY_URL;
			}
			else if (group.isUser()) {
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

	protected void verifyOrganizationNames() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("select groupId, name from Group_ where name like '%");
			sb.append(GroupLocalServiceImpl.ORGANIZATION_NAME_SUFFIX);
			sb.append("%' and name not like '%");
			sb.append(GroupLocalServiceImpl.ORGANIZATION_NAME_SUFFIX);
			sb.append("'");

			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				String name = rs.getString("name");

				if (name.endsWith(
						GroupLocalServiceImpl.ORGANIZATION_NAME_SUFFIX) ||
					name.endsWith(
						GroupLocalServiceImpl.ORGANIZATION_STAGING_SUFFIX)) {

					continue;
				}

				int pos = name.indexOf(
					GroupLocalServiceImpl.ORGANIZATION_NAME_SUFFIX);

				pos = name.indexOf(" ", pos + 1);

				String newName =
					name.substring(pos + 1) +
						GroupLocalServiceImpl.ORGANIZATION_NAME_SUFFIX;

				updateName(groupId, newName);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
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

	protected void verifySites() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Group.class);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"classNameId", PortalUtil.getClassNameId(Organization.class)));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("site", false));

		List<Group> groups = GroupLocalServiceUtil.dynamicQuery(dynamicQuery);

		for (Group group : groups) {
			if ((group.getPrivateLayoutsPageCount() > 0) ||
				(group.getPublicLayoutsPageCount() > 0)) {

				group.setSite(true);

				GroupLocalServiceUtil.updateGroup(group);
			}
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

	protected void verifyTree() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			GroupLocalServiceUtil.rebuildTree(companyId);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyGroup.class);

}