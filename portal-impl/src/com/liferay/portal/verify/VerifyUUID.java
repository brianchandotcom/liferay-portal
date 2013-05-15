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

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyUUID extends VerifyProcess {

	public static void verifyModel(String modelName, String pkColumnName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select " + pkColumnName + " from " + modelName +
					" where uuid_ is null or uuid_ = ''");

			rs = ps.executeQuery();

			while (rs.next()) {
				long pk = rs.getLong(pkColumnName);

				verifyModel(modelName, pkColumnName, pk);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	public static void verifyModel(
			String modelName, String pkColumnName, long pk)
		throws Exception {

		String uuid = PortalUUIDUtil.generate();

		DB db = DBFactoryUtil.getDB();

		db.runSQL(
			"update " + modelName + " set uuid_ = '" + uuid +
				"' where " + pkColumnName + " = " + pk);
	}

	@Override
	protected void doVerify() throws Exception {
		for (String[] model : _MODELS) {
			verifyModel(model[0], model[1]);
		}
	}

	private static final String[][] _MODELS = new String[][] {
		new String[] {
			"Address", "addressId"
		},
		new String[] {
			"DLFileRank", "fileRankId"
		},
		new String[] {
			"DLFileVersion", "fileVersionId"
		},
		new String[] {
			"EmailAddress", "emailAddressId"
		},
		new String[] {
			"Group_", "groupId"
		},
		new String[] {
			"JournalArticleResource", "resourcePrimKey"
		},
		new String[] {
			"JournalFeed", "id_"
		},
		new String[] {
			"Layout", "plid"
		},
		new String[] {
			"LayoutPrototype", "layoutPrototypeId"
		},
		new String[] {
			"LayoutSetPrototype", "layoutSetPrototypeId"
		},
		new String[] {
			"MBBan", "banId"
		},
		new String[] {
			"MBDiscussion", "discussionId"
		},
		new String[] {
			"MBThread", "threadId"
		},
		new String[] {
			"MBThreadFlag", "threadFlagId"
		},
		new String[] {
			"Organization_", "organizationId"
		},
		new String[] {
			"PasswordPolicy", "passwordPolicyId"
		},
		new String[] {
			"Phone", "phoneId"
		},
		new String[] {
			"PollsVote", "voteId"
		},
		new String[] {
			"Role_", "roleId"
		},
		new String[] {
			"UserGroup", "userGroupId"
		},
		new String[] {
			"Website", "websiteId"
		},
		new String[] {
			"WikiPageResource", "resourcePrimKey"
		}
	};

}