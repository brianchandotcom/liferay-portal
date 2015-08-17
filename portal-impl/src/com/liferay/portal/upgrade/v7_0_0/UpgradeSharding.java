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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Manuel de la Peña
 */
public class UpgradeSharding extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateAnnouncementsFlags();
		_updateAssetEntriesAssetCategories();
		_updateAssetEntriesAssetTags();
		_updateAssetTagStats();
		_updateBrowserTracker();
		_updateDDMStorageLink();
		_updateDDMStructureLink();
		_updateDDMTemplateLink();
		_updateDLFileEntryMetadata();
		_updateDLFileEntryTypes_DLFolders();
		_updateDLSyncEvent();
		_updateGroupsOrgs();
		_updateGroupsRoles();
		_updateGroupsUserGroups();
		_updateImage();
		_updateJournalArticleImage();
		_updateJournalArticleResource();
		_updateMarketplaceModule();
		_updateMBStatsUser();
		_updateOrgGroupRole();
		_updateOrgLabor();
		_updatePasswordPolicyRel();
		_updatePasswordTracker();
		_updatePortletPreferences();
		_updateRatingsStats();
		_updateResourceBlockPermission();
		_updateSCFrameworkVersionSCProductVersion();
		_updateSCLicensesSCProductEntries();
		_updateShoppingItemField();
		_updateShoppingItemPrice();
		_updateShoppingOrderItem();
		_updateTrashVersion();
		_updateUserGroupGroupRole();
		_updateUserGroupRole();
		_updateUserGroupsTeams();
		_updateUserIdMapper();
		_updateUsersGroups();
		_updateUsersOrgs();
		_updateUsersRoles();
		_updateUsersTeams();
		_updateUsersUserGroups();
		_updateUserTrackerPath();
		_updateWikiPageResource();
	}

	private void _updateAnnouncementsFlags() throws Exception {
		String select =
			"select a.flagId, a.userId, u.companyId from AnnouncementsFlag a," +
				" User_ u where a.userId=u.userId";

		String update =
			"update AnnouncementsFlag set companyId = ? where flagId = ?";

		_updateCompanyColumnOnTable(
			"AnnouncementsFlag", select, update, "companyId", "flagId");
	}

	private void _updateAssetEntriesAssetCategories() throws Exception {
		String select =
			"select a.categoryId, c.companyId, a.entryId from " +
				"AssetEntries_AssetCategories a, AssetCategory c where " +
				"a.categoryId=c.categoryId";

		String update =
			"update AssetEntries_AssetCategories set companyId = ? " +
				"where categoryId = ? and entryId = ?";

		_updateCompanyColumnOnTable(
			"AssetEntries_AssetCategories", select, update, "companyId",
			"categoryId", "entryId");
	}

	private void _updateAssetEntriesAssetTags() throws Exception {
		String select =
			"select t.companyId, a.entryId, a.tagId from " +
				"AssetEntries_AssetTags a, AssetTag t where a.tagId=t.tagId";

		String update =
			"update AssetEntries_AssetTags set companyId = ? " +
				"where entryId = ? and tagId = ?";

		_updateCompanyColumnOnTable(
			"AssetEntries_AssetTags", select, update, "companyId", "entryId",
			"tagId");
	}

	private void _updateAssetTagStats() throws Exception {
		String select =
			"select t.companyId, a.tagStatsId from AssetTagStats a, " +
				"AssetTag t where a.tagId=t.tagId";

		String update =
			"update AssetTagStats set companyId = ? where tagStatsId = ?";

		_updateCompanyColumnOnTable(
			"AssetTagStats", select, update, "companyId", "tagStatsId");
	}

	private void _updateBrowserTracker() throws Exception {
		String select =
			"select b.browserTrackerId, u.companyId from BrowserTracker b, " +
				"User_ u where b.userId=u.userId";

		String update =
			"update BrowserTracker set companyId = ? " +
				"where browserTrackerId = ?";

		_updateCompanyColumnOnTable(
			"BrowserTracker", select, update, "companyId", "browserTrackerId");
	}

	private void _updateCompanyColumnOnTable(
			String tableName, String select, String update,
			String... columnNames)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			DatabaseMetaData databaseMetaData = con.getMetaData();

			boolean supportsBatchUpdates =
				databaseMetaData.supportsBatchUpdates();

			ps = con.prepareStatement(select);

			rs = ps.executeQuery();

			ps2 = con.prepareStatement(update);

			int count = 0;

			while (rs.next()) {
				int i = 1;

				for (String columnName : columnNames) {
					ps2.setLong(i++, rs.getLong(columnName));
				}

				if (supportsBatchUpdates) {
					ps.addBatch();

					if (count == PropsValues.HIBERNATE_JDBC_BATCH_SIZE) {
						ps2.executeBatch();

						count = 0;
					}
					else {
						count++;
					}
				}
				else {
					ps2.executeUpdate();
				}
			}

			if (supportsBatchUpdates && (count > 0)) {
				ps.executeBatch();
			}
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"The companyId was not updated in " + tableName + " table",
					sqle);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
			DataAccess.cleanUp(ps2);
		}
	}

	private void _updateDDMStorageLink() throws Exception {
		String select =
			"select ds.companyId, dsl.structureId from DDMStructure ds, " +
				"DDMStorageLink dsl where ds.structureId=dsl.structureId";

		String update =
			"update DDMStorageLink set companyId = ? where structureId = ?";

		_updateCompanyColumnOnTable(
			"DDMStorageLink", select, update, "companyId", "structureId");
	}

	private void _updateDDMStructureLink() throws Exception {
		String select =
			"select ds.companyId, dsl.structureId from DDMStructure ds, " +
				"DDMStructureLink dsl where ds.structureId=dsl.structureId";

		String update =
			"update DDMStructureLink set companyId = ? where structureId = ?";

		_updateCompanyColumnOnTable(
			"DDMStructureLink", select, update, "companyId", "structureId");
	}

	private void _updateDDMTemplateLink() throws Exception {
		String select =
			"select dt.companyId, dtl.templateId from DDMTemplate dt, " +
				"DDMTemplateLink dtl where dt.templateId=dtl.templateId";

		String update =
			"update DDMTemplateLink set companyId = ? where templateId = ?";

		_updateCompanyColumnOnTable(
			"DDMTemplateLink", select, update, "companyId", "templateId");
	}

	private void _updateDLFileEntryMetadata() throws Exception {
		String select =
			"select dlfe.companyId, dlfem.fileEntryMetadataId from " +
				"DLFileEntry dlfe, DLFileEntryMetadata dlfem " +
				"where dlfe.fileEntryId=dlfem.fileEntryId";

		String update =
			"update DLFileEntryMetadata set companyId = ? " +
				"where fileEntryMetadataId = ?";

		_updateCompanyColumnOnTable(
			"DLFileEntryMetadata", select, update, "companyId",
			"fileEntryMetadataId");
	}

	private void _updateDLFileEntryTypes_DLFolders() throws Exception {
		String select =
			"select dlfet.companyId, dlfetdlf.fileEntryTypeId, " +
				"dlfetdlf.folderId from DLFileEntryTypes_DLFolders dlfetdlf, " +
				"DLFileEntryType dlfet, DLFolder dlf " +
				"where dlf.companyId=dlfet.companyId and " +
				"dlfet.fileEntryTypeId=dlfetdlf.fileEntryTypeId and " +
				"dlf.folderId=dlfetdlf.folderId";

		String update =
			"update DLFileEntryTypes_DLFolders set companyId = ? " +
				"where fileEntryTypeId = ? and folderId = ?";

		_updateCompanyColumnOnTable(
			"DLFileEntryTypes_DLFolders", select, update, "companyId",
			"fileEntryTypeId", "folderId");
	}

	private void _updateDLSyncEvent() throws Exception {

		// DLFileEntries

		String select =
			"(select dlfe.companyId, dlse.syncEventId from DLFileEntry dlfe, " +
				"DLSyncEvent dlse where dlse.type_='file' and " +
				"dlfe.fileEntryId=dlse.typePK) UNION (select re.companyId, " +
				"dlse.syncEventId from DLSyncEvent dlse, RepositoryEntry re " +
				"where dlse.type_='file' and dlse.typePK=re.repositoryId)";

		String update =
			"update DLSyncEvent set companyId = ? where syncEventId = ?";

		String[] columnNames = {"companyId", "syncEventId"};

		_updateCompanyColumnOnTable("DLSyncEvent", select, update, columnNames);

		// DLFolders

		select =
			"(select dlf.companyId, dlse.syncEventId from DLFolder dlf, " +
				"DLSyncEvent dlse where dlse.type_='folder' and " +
				"dlf.folderId=dlse.typePK) UNION (select re.companyId, " +
				"dlse.syncEventId from DLSyncEvent dlse, RepositoryEntry re " +
				"where dlse.type_='folder' and dlse.typePK=re.repositoryId)";

		update = "update DLSyncEvent set companyId = ? where syncEventId = ?";

		_updateCompanyColumnOnTable(
			"DLSyncEvent", select, update, "companyId", "syncEventId");
	}

	private void _updateGroupsOrgs() throws Exception {
		String select =
			"select g.companyId, go.groupId, go.organizationId from " +
				"Group_ g, Groups_Orgs go, Organization_ o " +
				"where g.groupId=go.groupId and " +
				"go.organizationId=o.organizationId";

		String update =
			"update Groups_Orgs set companyId = ? " +
				"where groupId = ? and organizationId = ?";

		_updateCompanyColumnOnTable(
			"Groups_Orgs", select, update, "companyId", "groupId",
			"organizationId");
	}

	private void _updateGroupsRoles() throws Exception {
		String select =
			"select g.companyId, gr.groupId, gr.roleId from " +
				"Group_ g, Groups_Roles gr, Role_ r " +
				"where g.groupId=gr.groupId and gr.roleId=r.roleId";

		String update =
			"update Groups_Roles set companyId = ? " +
				"where groupId = ? and roleId = ?";

		_updateCompanyColumnOnTable(
			"Groups_Roles", select, update, "companyId", "groupId", "roleId");
	}

	private void _updateGroupsUserGroups() throws Exception {
		String select =
			"select g.companyId, gug.groupId, gug.userGroupId from " +
				"Group_ g, Groups_UserGroups gug, UserGroup ug " +
				"where g.groupId=gug.groupId " +
				"and gug.userGroupId=ug.userGroupId";

		String update =
			"update Groups_UserGroups set companyId = ? " +
				"where groupId = ? and userGroupId = ?";

		_updateCompanyColumnOnTable(
			"Groups_UserGroups", select, update, "companyId", "groupId",
			"userGroupId");
	}

	private void _updateImage() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select imageId from Image");

			rs = ps.executeQuery();

			while (rs.next()) {
				long imageId = rs.getLong("imageId");

				long companyId = _DEFAULT_COMPANY_ID;

				if (PropsValues.WEB_SERVER_SERVLET_CHECK_IMAGE_GALLERY) {
					PreparedStatement ps2 = con.prepareStatement(
						"select companyId from DLFileEntry " +
							"where largeImageId = " + imageId);

					ResultSet rs2 = ps2.executeQuery();

					companyId = rs2.getLong("companyId");
				}

				runSQL(
					"update Image set companyId = " + companyId +
						" where imageId = " + imageId);
			}
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn("The companyId was not updated in Image table", sqle);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void _updateJournalArticleImage() throws Exception {
		String select =
			"select g.companyId, jai.articleImageId from " +
				"Group_ g, JournalArticleImage jai where g.groupId=jai.groupId";

		String update =
			"update JournalArticleImage set companyId = ? " +
				"where articleImageId = ?";

		_updateCompanyColumnOnTable(
			"JournalArticleImage", select, update, "companyId",
			"articleImageId");
	}

	private void _updateJournalArticleResource() throws Exception {
		String select =
			"select g.companyId, jar.resourcePrimKey from " +
				"Group_ g, JournalArticleResource jar " +
				"where g.groupId=jar.groupId";

		String update =
			"update JournalArticleResource set companyId = ? " +
				"where resourcePrimKey = ?";

		_updateCompanyColumnOnTable(
			"JournalArticleResource", select, update, "companyId",
			"resourcePrimKey");
	}

	private void _updateMarketplaceModule() throws Exception {
		String select =
			"select ma.companyId, mm.moduleId from " +
				"Marketplace_App ma, Marketplace_Module mm " +
				"where ma.appId=mm.appId";

		String update =
			"update Marketplace_Module set companyId = ? where moduleId = ?";

		_updateCompanyColumnOnTable(
			"Marketplace_Module", select, update, "companyId", "moduleId");
	}

	private void _updateMBStatsUser() throws Exception {
		String select =
			"select g.companyId, mbsu.statsUserId from Group_ g, " +
				"MBStatsUser mbsu where g.groupId=mbsu.groupId";

		String update =
			"update MBStatsUser set companyId = ? where statsUserId = ?";

		_updateCompanyColumnOnTable(
			"MBStatsUser", select, update, "companyId", "statsUserId");
	}

	private void _updateOrgGroupRole() throws Exception {
		String select =
			"select g.companyId, ogr.organizationId, ogr.groupId, ogr.roleId " +
				"from OrgGroupRole ogr, Group_ g, Organization_ o, Role_ r " +
					"where ogr.organizationId=o.organizationId and " +
						"ogr.groupId=g.groupId and ogr.roleId=r.roleId;";

		String update =
			"update OrgGroupRole set companyId = ? " +
				"where organizationId = ? and groupId = ? and roleId = ?";

		_updateCompanyColumnOnTable(
			"OrgGroupRole", select, update, "companyId", "organizationId",
			"groupId", "roleId");
	}

	private void _updateOrgLabor() throws Exception {
		String select =
			"select o.companyId, ol.orgLaborId from Organization_ o, " +
				"OrgLabor ol where o.organizationId=ol.organizationId";

		String update =
			"update OrgLabor set companyId = ? where orgLaborId = ?";

		_updateCompanyColumnOnTable(
			"OrgLabor", select, update, "companyId", "orgLaborId");
	}

	private void _updatePasswordPolicyRel() throws Exception {
		String select =
			"select pp.companyId, ppr.passwordPolicyRelId " +
				"from PasswordPolicy pp, PasswordPolicyRel ppr " +
					"where pp.passwordPolicyId=ppr.passwordPolicyId";

		String update =
			"update PasswordPolicyRel set companyId = ? " +
				"where passwordPolicyRelId = ?";

		_updateCompanyColumnOnTable(
			"PasswordPolicyRel", select, update, "companyId",
			"passwordPolicyRelId");
	}

	private void _updatePasswordTracker() throws Exception {
		String select =
			"select u.companyId, pt.passwordTrackerId " +
				"from PasswordTracker pt, User_ u where pt.userId=u.userId";

		String update =
			"update PasswordTracker set companyId = ? " +
				"where passwordTrackerId = ?";

		_updateCompanyColumnOnTable(
			"PasswordTracker", select, update, "companyId",
			"passwordTrackerId");
	}

	private void _updatePortletPreferences() throws Exception {
		String select =
			"select p.companyId, pp.portletPreferencesId " +
				"from Portlet p, PortletPreferences pp where " +
				"p.portletId=pp.portletId";

		String update =
			"update PortletPreferences set companyId = ? " +
				"where portletPreferencesId = ?";

		_updateCompanyColumnOnTable(
			"PortletPreferences", select, update, "companyId",
			"portletPreferencesId");
	}

	private void _updateRatingsStats() throws Exception {
		StringBuilder select = new StringBuilder();

		// BookmarksEntry

		select.append(
			"(select be.companyId, r.statsId from BookmarksEntry be, " +
				"RatingsStats r where be.entryId=r.classPK)");

		select.append(" union ");

		// BookmarksFolder

		select.append(
			"(select bf.companyId, r.statsId from BookmarksFolder bf, " +
				"RatingsStats r where bf.folderId=r.classPK)");

		select.append(" union ");

		// BlogsEntry

		select.append(
			"(select be.companyId, r.statsId from BlogsEntry be, " +
				"RatingsStats r where be.entryId=r.classPK)");

		select.append(" union ");

		// CalendarBooking

		select.append(
			"(select cb.companyId, r.statsId from CalendarBooking cb, " +
				"RatingsStats r where cb.calendarBookingId=r.classPK)");

		select.append(" union ");

		// DDLRecord

		select.append(
			"(select ddlr.companyId, r.statsId from DDLRecord ddlr, " +
				"RatingsStats r where ddlr.recordId=r.classPK)");

		select.append(" union ");

		// DLFileEntry

		select.append(
			"(select dlfe.companyId, r.statsId from DLFileEntry dlfe, " +
				"RatingsStats r where dlfe.fileEntryId=r.classPK)");

		select.append(" union ");

		// DLFolder

		select.append(
			"(select dlf.companyId, r.statsId from DLFolder dlf, " +
				"RatingsStats r where dlf.folderId=r.classPK)");

		// JournalArticle

		select.append(
			"(select ja.companyId, r.statsId from JournalArticle ja, " +
				"RatingsStats r where ja.articleId=r.classPK)");

		select.append(" union ");

		// JournalFolder

		select.append(
			"(select jf.companyId, r.statsId from JournalFolder jf, " +
				"RatingsStats r where jf.folderId=r.classPK)");

		select.append(" union ");

		// MBDiscussion

		select.append(
			"(select mbd.companyId, r.statsId from MBDiscussion mbd, " +
				"RatingsStats r where mbd.discussionId=r.classPK)");

		select.append(" union ");

		// MBMessage

		select.append(
			"(select mbm.companyId, r.statsId from MBMessage mbm, " +
				"RatingsStats r where mbm.messageId=r.classPK)");

		select.append(" union ");

		// WikiPage

		select.append(
			"(select wp.companyId, r.statsId from WikiPage wp, " +
				"RatingsStats r where wp.pageId=r.classPK)");

		String update =
			"update RatingsStats set companyId = ? where statsId = ?";

		_updateCompanyColumnOnTable(
			"RatingsStats", select.toString(), update, "companyId", "statsId");
	}

	private void _updateResourceBlockPermission() throws Exception {
		String select =
			"select r.companyId, rbp.resourceBlockPermissionId " +
				"from ResourceBlockPermission rbp, Role_ r " +
					"where r.roleId=rbp.roleId";

		String update =
			"update ResourceBlockPermission set companyId = ? " +
				"where resourceBlockPermissionId = ?";

		_updateCompanyColumnOnTable(
			"ResourceBlockPermission", select, update, "companyId",
			"resourceBlockPermissionId");
	}

	private void _updateSCFrameworkVersionSCProductVersion() throws Exception {
		String select =
			"select fv.companyId, fvpv.frameworkVersionId, " +
				"fvpv.productVersionId from SCFrameworkVersion fv, " +
					"SCProductVersion pv, SCFrameworkVersi_SCProductVers fvpv" +
						" where fv.frameworkVersionId=fvpv.frameworkVersionId" +
							" and pv.productVersionId=fvpv.productVersionId";

		String update =
			"update SCFrameworkVersi_SCProductVers set companyId = ? " +
				"where frameworkVersionId = ? and productVersionId = ?";

		_updateCompanyColumnOnTable(
			"SCFrameworkVersi_SCProductVers", select, update, "companyId",
			"frameworkVersionId", "productVersionId");
	}

	private void _updateSCLicensesSCProductEntries() throws Exception {
		String select =
			"select pe.companyId, lpe.licenseId, " +
				"lpe.productVersionId from SCLicense l, " +
					"SCLicenses_SCProductEntries lpe, SCProductEntry pe " +
						"where l.licenseId=lpe.licenseId and " +
							"lpe.productVersionId=pe.productVersionId";

		String update =
			"update SCLicenses_SCProductEntries set companyId = ? " +
				"where licenseId = ? and productEntryId = ?";

		_updateCompanyColumnOnTable(
			"SCLicenses_SCProductEntries", select, update, "companyId",
			"licenseId", "productEntryId");

		// SCLicense

		update = "update SCLicense set companyId = ? where licenseId = ?";

		_updateCompanyColumnOnTable(
			"SCLicense", select, update, "companyId", "licenseId");
	}

	private void _updateShoppingItemField() throws Exception {
		String select =
			"select si.companyId, sif.itemFieldId from ShoppingItem si, " +
				"ShoppingItemField sif where si.itemId=sif.itemId";

		String update =
			"update ShoppingItemField set companyId = ? where itemFieldId = ?";

		_updateCompanyColumnOnTable(
			"ShoppingItemField", select, update, "companyId", "itemFieldId");
	}

	private void _updateShoppingItemPrice() throws Exception {
		String select =
			"select si.companyId, sip.itemPriceId from ShoppingItem si, " +
				"ShoppingItemPrice sip where si.itemId=sip.itemId";

		String update =
			"update ShoppingItemPrice set companyId = ? where itemPriceId = ?";

		_updateCompanyColumnOnTable(
			"ShoppingItemPrice", select, update, "companyId", "itemPriceId");
	}

	private void _updateShoppingOrderItem() throws Exception {
		String select =
			"select si.companyId, soi.orderItemId from ShoppingItem si, " +
				"ShoppingOrderItem soi where si.itemId=soi.itemId";

		String update =
			"update ShoppingOrderItem set companyId = ? where orderItemId = ?";

		_updateCompanyColumnOnTable(
			"ShoppingOrderItem", select, update, "companyId", "orderItemId");
	}

	private void _updateTrashVersion() throws Exception {
		String select =
			"select te.companyId, tv.versionId from TrashEntry te, " +
				"TrashVersion tv where te.entryId=tv.entryId";

		String update =
			"update TrashVersion set companyId = ? where versionId = ?";

		_updateCompanyColumnOnTable(
			"TrashVersion", select, update, "companyId", "versionId");
	}

	private void _updateUserGroupGroupRole() throws Exception {
		String select =
			"select g.companyId, uggr.userGroupId, uggr.groupId, uggr.roleId " +
				"from Group_ g, Role_ r, UserGroup ug, " +
					"UserGroupGroupRole uggr where g.groupId=uggr.groupId" +
						" and ug.userGroupId=uggr.userGroupId and " +
							"r.roleId=uggr.roleId";

		String update =
			"update UserGroupGroupRole set companyId = ? " +
				"where userGroupId = ? and groupId = ? and roleId = ?";

		_updateCompanyColumnOnTable(
			"UserGroupGroupRole", select, update, "companyId", "userGroupId",
			"groupId", "roleId");
	}

	private void _updateUserGroupRole() throws Exception {
		String select =
			"select g.companyId, ugr.userId, ugr.groupId, ugr.roleId from " +
				"Group_ g, Role_ r, User_ u, UserGroupRole ugr where " +
					"g.groupId=ugr.groupId and u.userId=ugr.userId and " +
						"r.roleId=ugr.roleId";

		String update =
			"update UserGroupRole set companyId = ? " +
				"where userId = ? and groupId = ? and roleId = ?";

		_updateCompanyColumnOnTable(
			"UserGroupRole", select, update, "companyId", "userId", "groupId",
			"roleId");
	}

	private void _updateUserGroupsTeams() throws Exception {
		String select =
			"select t.companyId, ugt.teamId, ugt.userGroupId from Team t, " +
				"UserGroup ug, UserGroups_Teams ugt where " +
					"t.teamId=ugt.teamId and ug.userGroupId=ugt.userGroupId";

		String update =
			"update UserGroups_Teams set companyId = ? " +
				"where teamId = ? and userGroupId = ?";

		_updateCompanyColumnOnTable(
			"UserGroups_Teams", select, update, "companyId", "teamId",
			"userGroupId");
	}

	private void _updateUserIdMapper() throws Exception {
		String select =
			"select u.companyId, uim.userIdMapperId from User_ u, " +
				"UserIdMapper uim where u.userId=ui.userId";

		String update =
			"update UserIdMapper set companyId = ? where userIdMapperId = ?";

		_updateCompanyColumnOnTable(
			"UserIdMapper", select, update, "companyId", "userIdMapperId");
	}

	private void _updateUsersGroups() throws Exception {
		String select =
			"select u.companyId, ug.userId, ug.groupId from Group_ g, " +
				"User_ u, Users_Groups ug where g.groupId=ug.groupId and " +
					"u.userId=ug.userId";

		String update =
			"update Users_Groups set companyId = ? where userId = ? " +
				"and groupId = ?";

		_updateCompanyColumnOnTable(
			"Users_Groups", select, update, "companyId", "userId", "groupId");
	}

	private void _updateUsersOrgs() throws Exception {
		String select =
			"select u.companyId, uo.userId, uo.organizationId from " +
				"Organization_ o, User_ u, Users_Orgs uo where " +
					"o.organizationId=uo.organizationId and u.userId=uo.userId";

		String update =
			"update Users_Orgs set companyId = ? where userId = ? and " +
				"organizationId = ?";

		_updateCompanyColumnOnTable(
			"Users_Orgs", select, update, "companyId", "userId",
			"organizationId");
	}

	private void _updateUsersRoles() throws Exception {
		String select =
			"select u.companyId, ur.userId, ur.roleId from Role_ r, User_ u, " +
				"Users_Roles ur where r.roleId=ur.roleId and " +
					"u.userId=ur.userId";

		String update =
			"update Users_Roles set companyId = ? where userId = ? and " +
				"roleId = ?";

		_updateCompanyColumnOnTable(
			"Users_Roles", select, update, "companyId", "userId", "roleId");
	}

	private void _updateUsersTeams() throws Exception {
		String select =
			"select u.companyId, ut.userId, ut.teamId from Team t, User_ u, " +
				"Users_Teams ut where t.teamId=ut.teamId and " +
					"u.userId=ut.userId";

		String update =
			"update Users_Teams set companyId = ? where userId = ? and " +
				"teamId = ?";

		_updateCompanyColumnOnTable(
			"Users_Teams", select, update, "companyId", "userId", "teamId");
	}

	private void _updateUsersUserGroups() throws Exception {
		String select =
			"select u.companyId, uug.userId, uug.userGroupId from " +
				"UserGroup ug, User_ u, Users_UserGroups uug where " +
					"ug.userGroupId=uug.userGroupId and u.userId=uug.userId";

		String update =
			"update Users_UserGroups set companyId = ? where userId = ? and " +
				"userGroupId = ?";

		_updateCompanyColumnOnTable(
			"Users_UserGroups", select, update, "companyId", "userId",
			"userGroupId");
	}

	private void _updateUserTrackerPath() throws Exception {
		String select =
			"select ut.companyId, utp.userTrackerPathId from UserTracker ut, " +
				"UserTrackerPath utp where ut.userTrackerId=utp.userTrackerId";

		String update =
			"update UserTrackerPath set companyId = ? where " +
				"userTrackerPathId = ?";

		_updateCompanyColumnOnTable(
			"UserTrackerPath", select, update, "companyId",
			"userTrackerPathId");
	}

	private void _updateWikiPageResource() throws Exception {
		String select =
			"select wn.companyId, wpr.resourcePrimKey from " +
				" WikiNode wn, WikiPageResource wpr where wn.nodeId=wpr.nodeId";

		String update =
			"update WikiPageResource set companyId = ? where " +
				"resourcePrimKey = ?";

		_updateCompanyColumnOnTable(
			"WikiPageResource", select, update, "companyId", "resourcePrimKey");
	}

	private static final long _DEFAULT_COMPANY_ID = 0;

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeSharding.class);

}