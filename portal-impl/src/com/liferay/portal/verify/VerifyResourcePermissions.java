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

import com.liferay.portal.NoSuchResourcePermissionException;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ContactLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Raymond Augé
 * @author James Lefeu
 */
public class VerifyResourcePermissions extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			Role role = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.OWNER);

			for (String[] model : _MODELS) {
				verifyModel(
					role, model[_INDEX_NAME], model[_INDEX_MODEL_NAME],
					model[_INDEX_PK_COL_NAME]);
			}

			verifyLayout(role);
		}
	}

	protected void verifyLayout(Role role) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String[] MODEL = _LAYOUT_MODELS[0];
			long companyId = role.getCompanyId();
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select * from " + MODEL[_INDEX_MODEL_NAME] +
						" where companyId = " +
						companyId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long primKey = rs.getLong(MODEL[_INDEX_PK_COL_NAME]);

				verifyModel(companyId, MODEL[_INDEX_NAME], primKey, role, 0);

			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void verifyModel(
			long companyId, String name, long primKey, Role role, long ownerId)
		throws Exception {

		ResourcePermission resourcePermission = null;

		try {
			resourcePermission =
				ResourcePermissionLocalServiceUtil.getResourcePermission(
					companyId, name, ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(primKey), role.getRoleId());
		}
		catch (NoSuchResourcePermissionException nsrpe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No resource found for {" + companyId + ", " + name + ", " +
						ResourceConstants.SCOPE_INDIVIDUAL + ", " + primKey +
							", " + role.getRoleId() + "}");
			}

			ResourceLocalServiceUtil.addResources(
				companyId, 0, ownerId, name, String.valueOf(primKey), false,
				false, false);
		}

		if (resourcePermission == null) {
			try {
				resourcePermission =
					ResourcePermissionLocalServiceUtil.getResourcePermission(
						companyId, name, ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(primKey), role.getRoleId());
			}
			catch (NoSuchResourcePermissionException nsrpe) {
				return;
			}
		}

		if (name.equals(User.class.getName())) {
			User user = UserLocalServiceUtil.getUserById(ownerId);

			Contact contact = ContactLocalServiceUtil.getContact(
				user.getContactId());

			ownerId = contact.getUserId();
		}

		if (ownerId != resourcePermission.getOwnerId()) {
			resourcePermission.setOwnerId(ownerId);

			ResourcePermissionLocalServiceUtil.updateResourcePermission(
				resourcePermission);
		}

		if (_log.isInfoEnabled() &&
			((resourcePermission.getResourcePermissionId() % 100) == 0)) {

			_log.info("Processed 100 resource permissions for " + name);
		}
	}

	protected void verifyModel(
			Role role, String name, String modelName, String pkColumnName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select " + pkColumnName + ", userId AS ownerId " +
					"from " + modelName + " where companyId = " +
						role.getCompanyId());

			rs = ps.executeQuery();

			while (rs.next()) {
				long primKey = rs.getLong(pkColumnName);
				long ownerId = rs.getLong("ownerId");

				verifyModel(role.getCompanyId(), name, primKey, role, ownerId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final int _INDEX_MODEL_NAME = 1;
	private static final int _INDEX_NAME = 0;
	private static final int _INDEX_PK_COL_NAME = 2;

	private static final String[][] _LAYOUT_MODELS = new String[][] {
		new String[] {
				Layout.class.getName(), "Layout", "plid"
			}
	};

	private static final String[][] _MODELS = new String[][] {
		new String[] {
			AnnouncementsEntry.class.getName(), "AnnouncementsEntry", "entryId"
		},
		new String[] {
			AssetCategory.class.getName(), "AssetCategory", "categoryId"
		},
		new String[] {
			AssetTag.class.getName(), "AssetTag", "tagId"
		},
		new String[] {
			AssetVocabulary.class.getName(), "AssetVocabulary", "vocabularyId"
		},
		new String[] {
			BlogsEntry.class.getName(), "BlogsEntry", "entryId"
		},
		new String[] {
			CalEvent.class.getName(), "CalEvent", "eventId"
		},
		new String[] {
			DDMStructure.class.getName(), "DDMStructure", "structureId"
		},
		new String[] {
			DDMTemplate.class.getName(), "DDMTemplate", "templateId"
		},
		new String[] {
			DLFileEntry.class.getName(), "DLFileEntry", "fileEntryId"
		},
		new String[] {
			DLFileShortcut.class.getName(), "DLFileShortcut", "fileShortcutId"
		},
		new String[] {
			DLFolder.class.getName(), "DLFolder", "folderId"
		},
		new String[] {
			JournalArticle.class.getName(), "JournalArticle", "resourcePrimKey"
		},
		new String[] {
			JournalFeed.class.getName(), "JournalFeed", "id_"
		},
		new String[] {
			JournalStructure.class.getName(), "JournalStructure", "id_"
		},
		new String[] {
			JournalTemplate.class.getName(), "JournalTemplate", "id_"
		},
		new String[] {
			LayoutSetBranch.class.getName(), "LayoutSetBranch",
			"layoutSetBranchId"
		},
		new String[] {
			MBCategory.class.getName(), "MBCategory", "categoryId"
		},
		new String[] {
			MBMessage.class.getName(), "MBMessage", "messageId"
		},
		new String[] {
			PasswordPolicy.class.getName(), "PasswordPolicy", "passwordPolicyId"
		},
		new String[] {
			PollsQuestion.class.getName(), "PollsQuestion", "questionId"
		},
		new String[] {
			SCFrameworkVersion.class.getName(), "SCFrameworkVersion",
			"frameworkVersionId"
		},
		new String[] {
			SCProductEntry.class.getName(), "SCProductEntry", "productEntryId"
		},
		new String[] {
			ShoppingCategory.class.getName(), "ShoppingCategory", "categoryId"
		},
		new String[] {
			ShoppingItem.class.getName(), "ShoppingItem", "itemId"
		},
		new String[] {
			Team.class.getName(), "Team", "teamId"
		},
		new String[] {
			User.class.getName(), "User_", "userId"
		},
		new String[] {
			WikiNode.class.getName(), "WikiNode", "nodeId"
		},
		new String[] {
			WikiPage.class.getName(), "WikiPage", "resourcePrimKey"
		}
	};

	private static Log _log = LogFactoryUtil.getLog(
		VerifyResourcePermissions.class);

}