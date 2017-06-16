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

package com.liferay.portal.upgrade.v7_0_3;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alberto Chaparro
 */
public class UpgradeOracle extends UpgradeProcess {

	protected void alterVarchar2Columns() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select table_name, column_name, data_length from " +
					"user_tab_columns where data_type = 'VARCHAR2' and " +
						"char_used = 'B'");
			ResultSet rs = ps.executeQuery()) {

			int buildNumber = getBuildNumber();

			while (rs.next()) {
				String tableName = rs.getString(1);

				if (!_tableNames.contains(StringUtil.toLowerCase(tableName))) {
					continue;
				}

				String columnName = rs.getString(2);
				int dataLength = rs.getInt(3);

				if (isBetweenBuildNumbers(
						buildNumber, ReleaseInfo.RELEASE_5_2_9_BUILD_NUMBER,
						ReleaseInfo.RELEASE_6_0_0_BUILD_NUMBER) ||
					isBetweenBuildNumbers(
						buildNumber, ReleaseInfo.RELEASE_6_0_5_BUILD_NUMBER,
						ReleaseInfo.RELEASE_6_2_0_BUILD_NUMBER)) {

					// LPS-33903

					if (!ArrayUtil.contains(
							_ORIGINAL_DATA_LENGTH_VALUES, dataLength)) {

						dataLength = dataLength / 4;
					}
				}

				try {
					runSQL(
						"alter table " + tableName + " modify " + columnName +
							" varchar2(" + dataLength + " char)");
				}
				catch (SQLException sqle) {
					if (sqle.getErrorCode() == 1441) {
						if (_log.isWarnEnabled()) {
							StringBundler sb = new StringBundler(6);

							sb.append("Unable to alter length of column ");
							sb.append(columnName);
							sb.append(" for table ");
							sb.append(tableName);
							sb.append(" because it contains values that are ");
							sb.append("larger than the new column length");

							_log.warn(sb.toString());
						}
					}
					else {
						throw sqle;
					}
				}
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		DB db = DBManagerUtil.getDB();

		if (db.getDBType() != DBType.ORACLE) {
			return;
		}

		alterVarchar2Columns();
	}

	protected int getBuildNumber() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select buildNumber from Release_ where servletContextName = " +
					"?")) {

			ps.setString(1, ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

			try (ResultSet rs = ps.executeQuery()) {
				rs.next();

				return rs.getInt(1);
			}
		}
	}

	protected boolean isBetweenBuildNumbers(
		int buildNumber, int startBuildNumber, int endBuildNumber) {

		if ((buildNumber >= startBuildNumber) &&
			(buildNumber < endBuildNumber)) {

			return true;
		}

		return false;
	}

	private static final int[] _ORIGINAL_DATA_LENGTH_VALUES =
		{75, 100, 150, 200, 255, 500, 1000, 1024, 2000, 4000};

	private static final Log _log = LogFactoryUtil.getLog(UpgradeOracle.class);

	private static final Set<String> _tableNames = new HashSet<>(
		Arrays.asList(
			"account_", "address", "announcementsdelivery",
			"announcementsentry", "announcementsflag", "assetcategory",
			"assetcategoryproperty", "assetentries_assetcategories",
			"assetentries_assettags", "assetentry", "assetlink", "assettag",
			"assettagstats", "assetvocabulary", "backgroundtask", "blogsentry",
			"blogsstatsuser", "bookmarksentry", "bookmarksfolder",
			"browsertracker", "classname_", "clustergroup", "company",
			"contact_", "counter", "country", "ddlrecord", "ddlrecordset",
			"ddlrecordversion", "ddmcontent", "ddmstoragelink", "ddmstructure",
			"ddmstructurelink", "ddmtemplate", "dlcontent", "dlfileentry",
			"dlfileentrymetadata", "dlfileentrytype",
			"dlfileentrytypes_dlfolders", "dlfilerank", "dlfileshortcut",
			"dlfileversion", "dlfolder", "dlsyncevent", "emailaddress",
			"expandocolumn", "expandorow", "expandotable", "expandovalue",
			"exportimportconfiguration", "group_", "groups_orgs",
			"groups_roles", "groups_usergroups", "image", "journalarticle",
			"journalarticleimage", "journalarticleresource",
			"journalcontentsearch", "journalfeed", "journalfolder", "layout",
			"layoutbranch", "layoutfriendlyurl", "layoutprototype",
			"layoutrevision", "layoutset", "layoutsetbranch",
			"layoutsetprototype", "listtype", "lock_", "mbban", "mbcategory",
			"mbdiscussion", "mbmailinglist", "mbmessage", "mbstatsuser",
			"mbthread", "mbthreadflag", "mdraction", "mdrrule", "mdrrulegroup",
			"mdrrulegroupinstance", "membershiprequest", "organization_",
			"orggrouprole", "orglabor", "passwordpolicy", "passwordpolicyrel",
			"passwordtracker", "phone", "pluginsetting", "pollschoice",
			"pollsquestion", "pollsvote", "portalpreferences", "portlet",
			"portletitem", "portletpreferences", "ratingsentry", "ratingsstats",
			"recentlayoutbranch", "recentlayoutrevision",
			"recentlayoutsetbranch", "region", "release_", "repository",
			"repositoryentry", "resourceaction", "resourceblock",
			"resourceblockpermission", "resourcepermission",
			"resourcetypepermission", "role_", "servicecomponent",
			"shoppingcart", "shoppingcategory", "shoppingcoupon",
			"shoppingitem", "shoppingitemfield", "shoppingitemprice",
			"shoppingorder", "shoppingorderitem", "socialactivity",
			"socialactivityachievement", "socialactivitycounter",
			"socialactivitylimit", "socialactivityset", "socialactivitysetting",
			"socialrelation", "socialrequest", "subscription", "systemevent",
			"team", "ticket", "trashentry", "trashversion",
			"usernotificationdelivery", "user_", "usergroup",
			"usergroupgrouprole", "usergrouprole", "usergroups_teams",
			"useridmapper", "usernotificationevent", "users_groups",
			"users_orgs", "users_roles", "users_teams", "users_usergroups",
			"usertracker", "usertrackerpath", "virtualhost", "webdavprops",
			"website", "wikinode", "wikipage", "wikipageresource",
			"workflowdefinitionlink", "workflowinstancelink"));

}