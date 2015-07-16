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
		_updateGroups_Orgs();
		_updateGroups_Roles();
		_updateGroups_UserGroups();
		_updateJournalArticleImage();
		_updateJournalArticleResource();
		_updateMarketplace_Module();
		_updateMBStatsUser();
		_updateOrgGroupRole();
		_updateOrgLabor();
		_updatePasswordPolicyRel();
		_updatePasswordTracker();
		_updatePortletPreferences();
		_updateRatingsStats();
		_updateResourceBlockPermission();
		_updateSCFrameworkVersi_SCProductVers();
		_updateSCLicenses_SCProductEntries();
		_updateShoppingItemField();
		_updateShoppingItemPrice();
		_updateShoppingOrderItem();
		_updateTrashVersion();
		_updateUserGroupGroupRole();
		_updateUserGroupRole();
		_updateUserGroups_Teams();
		_updateUserIdMapper();
		_updateUsers_Groups();
		_updateUsers_Orgs();
		_updateUsers_Roles();
		_updateUsers_Teams();
		_updateUsers_UserGroups();
		_updateUserTrackerPath();
		_updateWikiPageResource();
	}

	private void _batchUpdateCompanyIdOnTable(
			String tableName, String select, String update,
			String[] columnNames)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			DatabaseMetaData databaseMetaData = con.getMetaData();

			boolean supportsBatchUpdates =
				databaseMetaData.supportsBatchUpdates();

			ps = con.prepareStatement(select);

			rs = ps.executeQuery();

			ps = con.prepareStatement(update);

			int count = 0;

			while (rs.next()) {
				int i = 1;

				for (String columnName : columnNames) {
					ps.setLong(i++, rs.getLong(columnName));
				}

				if (supportsBatchUpdates) {
					ps.addBatch();

					if (count == PropsValues.HIBERNATE_JDBC_BATCH_SIZE) {
						ps.executeBatch();

						count = 0;
					}
					else {
						count++;
					}
				}
				else {
					ps.executeUpdate();
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
		}
	}

	private void _updateAnnouncementsFlags() throws Exception {
		String select =
			"select a.flagId, a.userId, u.companyId from AnnouncementsFlag a," +
				" User_ u where a.userId=u.userId";

		String update =
			"update AnnouncementsFlag set companyId = ? where flagId = ?";

		String[] columnNames = {"companyId", "flagId"};

		_batchUpdateCompanyIdOnTable(
			"AnnouncementsFlag", select, update, columnNames);
	}

	private void _updateAssetEntriesAssetCategories() throws Exception {
		String select =
			"select a.categoryId, c.companyId, a.entryId from " +
				"AssetEntries_AssetCategories a, AssetCategory c where " +
				"a.categoryId=c.categoryId";

		String update =
			"update AssetEntries_AssetCategories set companyId = ? " +
				"where categoryId = ? and entryId = ?";

		String[] columnNames = {"companyId", "categoryId", "entryId"};

		_batchUpdateCompanyIdOnTable(
			"AssetEntries_AssetCategories", select, update, columnNames);
	}

	private void _updateAssetEntriesAssetTags() throws Exception {
		String select =
			"select t.companyId, a.entryId, a.tagId from " +
				"AssetEntries_AssetTags a, AssetTag t where a.tagId=t.tagId";

		String update =
			"update AssetEntries_AssetTags set companyId = ? " +
				"where entryId = ? and tagId = ?";

		String[] columnNames = {"companyId", "entryId", "tagId"};

		_batchUpdateCompanyIdOnTable(
			"AssetEntries_AssetTags", select, update, columnNames);
	}

	private void _updateAssetTagStats() throws Exception {
		String select =
			"select t.companyId, a.tagStatsId from AssetTagStats a, " +
				"AssetTag t where a.tagId=t.tagId";

		String update =
			"update AssetTagStats set companyId = ? where tagStatsId = ?";

		String[] columnNames = {"companyId", "tagStatsId"};

		_batchUpdateCompanyIdOnTable(
			"AssetTagStats", select, update, columnNames);
	}

	private void _updateBrowserTracker() throws Exception {
		String select =
			"select b.browserTrackerId, u.companyId from BrowserTracker b, " +
				"User_ u where b.userId=u.userId";

		String update =
			"update BrowserTracker set companyId = ? " +
				"where browserTrackerId = ?";

		String[] columnNames = {"companyId", "browserTrackerId"};

		_batchUpdateCompanyIdOnTable(
			"BrowserTracker", select, update, columnNames);
	}

	private void _updateDDMStorageLink() throws Exception {
		String select =
			"select ds.companyId, dsl.structureId from DDMStructure ds, " +
				"DDMStorageLink dsl where ds.structureId=dsl.structureId";

		String update =
			"update DDMStorageLink set companyId = ? where structureId = ?";

		String[] columnNames = {"companyId", "structureId"};

		_batchUpdateCompanyIdOnTable(
			"DDMStorageLink", select, update, columnNames);
	}

	private void _updateDDMStructureLink() throws Exception {
		String select =
			"select ds.companyId, dsl.structureId from DDMStructure ds, " +
				"DDMStructureLink dsl where ds.structureId=dsl.structureId";

		String update =
			"update DDMStructureLink set companyId = ? where structureId = ?";

		String[] columnNames = {"companyId", "structureId"};

		_batchUpdateCompanyIdOnTable(
			"DDMStructureLink", select, update, columnNames);
	}

	private void _updateDDMTemplateLink() throws Exception {
		String select =
			"select dt.companyId, dtl.templateId from DDMTemplate dt, " +
				"DDMTemplateLink dtl where dt.templateId=dtl.templateId";

		String update =
			"update DDMTemplateLink set companyId = ? where templateId = ?";

		String[] columnNames = {"companyId", "templateId"};

		_batchUpdateCompanyIdOnTable(
			"DDMTemplateLink", select, update, columnNames);
	}

	private void _updateDLFileEntryMetadata() throws Exception {
		String select =
			"select dlfe.companyId, dlfem.fileEntryMetadataId from " +
				"DLFileEntry dlfe, DLFileEntryMetadata dlfem " +
				"where dlfe.fileEntryId=dlfem.fileEntryId";

		String update =
			"update DLFileEntryMetadata set companyId = ? " +
				"where fileEntryMetadataId = ?";

		String[] columnNames = {"companyId", "fileEntryMetadataId"};

		_batchUpdateCompanyIdOnTable(
			"DLFileEntryMetadata", select, update, columnNames);
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

		String[] columnNames = {"companyId", "fileEntryTypeId", "folderId"};

		_batchUpdateCompanyIdOnTable(
			"DLFileEntryTypes_DLFolders", select, update, columnNames);
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

		_batchUpdateCompanyIdOnTable(
			"DLSyncEvent", select, update, columnNames);

		// DLFolders

		select =
			"(select dlf.companyId, dlse.syncEventId from DLFolder dlf, " +
				"DLSyncEvent dlse where dlse.type_='folder' and " +
				"dlf.folderId=dlse.typePK) UNION (select re.companyId, " +
				"dlse.syncEventId from DLSyncEvent dlse, RepositoryEntry re " +
				"where dlse.type_='folder' and dlse.typePK=re.repositoryId)";

		update =
			"update DLSyncEvent set companyId = ? where syncEventId = ?";

		columnNames = new String[] {"companyId", "syncEventId"};

		_batchUpdateCompanyIdOnTable(
			"DLSyncEvent", select, update, columnNames);
	}

	private void _updateGroups_Orgs() throws Exception {
		String select =
			"select g.companyId, go.groupId, go.organizationId from " +
				"Group_ g, Groups_Orgs go, Organization_ o " +
				"where g.groupId=go.groupId and " +
				"go.organizationId=o.organizationId";

		String update =
			"update Groups_Orgs set companyId = ? " +
				"where groupId = ? and organizationId = ?";

		String[] columnNames = {"companyId", "groupId", "organizationId"};

		_batchUpdateCompanyIdOnTable(
			"Groups_Orgs", select, update, columnNames);
	}

	private void _updateGroups_Roles() throws Exception {
		String select =
			"select g.companyId, gr.groupId, gr.roleId from " +
				"Group_ g, Groups_Roles gr, Role_ r " +
				"where g.groupId=gr.groupId and gr.roleId=r.roleId";

		String update =
			"update Groups_Roles set companyId = ? " +
				"where groupId = ? and roleId = ?";

		String[] columnNames = {"companyId", "groupId", "roleId"};

		_batchUpdateCompanyIdOnTable(
			"Groups_Roles", select, update, columnNames);
	}

	private void _updateGroups_UserGroups() throws Exception {
		String select =
			"select g.companyId, gug.groupId, gug.userGroupId from " +
				"Group_ g, Groups_UserGroups gug, UserGroup ug " +
				"where g.groupId=gug.groupId and gug.userGroupId=ug.userGroupId";

		String update =
			"update Groups_UserGroups set companyId = ? " +
				"where groupId = ? and userGroupId = ?";

		String[] columnNames = {"companyId", "groupId", "userGroupId"};

		_batchUpdateCompanyIdOnTable(
			"Groups_UserGroups", select, update, columnNames);
	}

	private void _updateJournalArticleImage() throws Exception {
		String select =
			"select g.companyId, jai.articleImageId from " +
				"Group_ g, JournalArticleImage jai where g.groupId=jai.groupId";

		String update =
			"update JournalArticleImage set companyId = ? " +
				"where articleImageId = ?";

		String[] columnNames = {"companyId", "articleImageId"};

		_batchUpdateCompanyIdOnTable(
			"JournalArticleImage", select, update, columnNames);
	}

	private void _updateJournalArticleResource() throws Exception {
		String select =
			"select g.companyId, jar.resourcePrimKey from " +
				"Group_ g, JournalArticleResource jar " +
				"where g.groupId=jar.groupId";

		String update =
			"update JournalArticleResource set companyId = ? " +
				"where resourcePrimKey = ?";

		String[] columnNames = {"companyId", "resourcePrimKey"};

		_batchUpdateCompanyIdOnTable(
			"JournalArticleResource", select, update, columnNames);
	}

	private void _updateMarketplace_Module() throws Exception {
		String select =
			"select ma.companyId, mm.moduleId from " +
				"Marketplace_App ma, Marketplace_Module mm " +
				"where ma.appId=mm.appId";

		String update =
			"update Marketplace_Module set companyId = ? where moduleId = ?";

		String[] columnNames = {"companyId", "moduleId"};

		_batchUpdateCompanyIdOnTable(
			"Marketplace_Module", select, update, columnNames);
	}

	private void _updateMBStatsUser() throws Exception {
		String select =
			"select g.companyId, mbsu.statsUserId from Group_ g, " +
				"MBStatsUser mbsu where g.groupId=mbsu.groupId";

		String update =
			"update MBStatsUser set companyId = ? where statsUserId = ?";

		String[] columnNames = {"companyId", "statsUserId"};

		_batchUpdateCompanyIdOnTable(
			"MBStatsUser", select, update, columnNames);
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

		String[] columnNames = {
			"companyId", "organizationId", "groupId", "roleId"};

		_batchUpdateCompanyIdOnTable(
			"OrgGroupRole", select, update, columnNames);
	}

	private void _updateOrgLabor() throws Exception {
		String select =
			"select o.companyId, ol.orgLaborId from Organization_ o, " +
				"OrgLabor ol where o.organizationId=ol.organizationId";

		String update =
			"update OrgLabor set companyId = ? where orgLaborId = ?";

		String[] columnNames = {"companyId", "orgLaborId"};

		_batchUpdateCompanyIdOnTable(
			"OrgLabor", select, update, columnNames);
	}

	private void _updatePasswordPolicyRel() throws Exception {
		String select =
			"select pp.companyId, ppr.passwordPolicyRelId " +
				"from PasswordPolicy pp, PasswordPolicyRel ppr " +
				"where pp.passwordPolicyId=ppr.passwordPolicyId";

		String update =
			"update PasswordPolicyRel set companyId = ? " +
				"where passwordPolicyRelId = ?";

		String[] columnNames = {"companyId", "passwordPolicyRelId"};

		_batchUpdateCompanyIdOnTable(
			"PasswordPolicyRel", select, update, columnNames);
	}

	private void _updatePasswordTracker() throws Exception {
		String select =
			"select u.companyId, pt.passwordTrackerId " +
				"from PasswordTracker pt, User_ u where pt.userId=u.userId";

		String update =
			"update PasswordTracker set companyId = ? " +
				"where passwordTrackerId = ?";

		String[] columnNames = {"companyId", "passwordTrackerId"};

		_batchUpdateCompanyIdOnTable(
			"PasswordTracker", select, update, columnNames);
	}

	private void _updatePortletPreferences() throws Exception {
		String select =
			"select p.companyId, pp.portletPreferencesId " +
				"from Portlet p, PortletPreferences pp where " +
				"p.portletId=pp.portletId";

		String update =
			"update PortletPreferences set companyId = ? " +
				"where portletPreferencesId = ?";

		String[] columnNames = {"companyId", "portletPreferencesId"};

		_batchUpdateCompanyIdOnTable(
			"PortletPreferences", select, update, columnNames);
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

		String[] columnNames = {"companyId", "statsId"};

		_batchUpdateCompanyIdOnTable(
			"RatingsStats", select.toString(), update, columnNames);
	}

	private void _updateResourceBlockPermission() throws Exception {
		String select =
			"select r.companyId, rbp.resourceBlockPermissionId " +
				"from ResourceBlockPermission rbp, Role_ r " +
				"where r.roleId=rbp.roleId";

		String update =
			"update ResourceBlockPermission set companyId = ? " +
				"where resourceBlockPermissionId = ?";

		String[] columnNames = {"companyId", "resourceBlockPermissionId"};

		_batchUpdateCompanyIdOnTable(
			"ResourceBlockPermission", select, update, columnNames);
	}

	private void _updateSCFrameworkVersi_SCProductVers() throws Exception {
		String select =
			"select fv.companyId, fvpv.frameworkVersionId, " +
				"fvpv.productVersionId from SCFrameworkVersion fv, " +
				"SCProductVersion pv, SCFrameworkVersi_SCProductVers fvpv " +
				"where fv.frameworkVersionId=fvpv.frameworkVersionId and " +
				"pv.productVersionId=fvpv.productVersionId";

		String update =
			"update SCFrameworkVersi_SCProductVers set companyId = ? " +
				"where frameworkVersionId = ? and productVersionId = ?";

		String[] columnNames = {
			"companyId", "frameworkVersionId", "productVersionId"};

		_batchUpdateCompanyIdOnTable(
			"SCFrameworkVersi_SCProductVers", select, update, columnNames);
	}

	private void _updateSCLicenses_SCProductEntries() throws Exception {
		String select =
			"select pe.companyId, lpe.licenseId, " +
				"lpe.productVersionId from SCLicense l, " +
				"SCLicenses_SCProductEntries lpe, SCProductEntry pe " +
				"where l.licenseId=lpe.licenseId and " +
				"lpe.productVersionId=pe.productVersionId";

		String update =
			"update SCLicenses_SCProductEntries set companyId = ? " +
				"where licenseId = ? and productEntryId = ?";

		String[] columnNames = {"companyId", "licenseId", "productEntryId"};

		_batchUpdateCompanyIdOnTable(
			"SCLicenses_SCProductEntries", select, update, columnNames);

		// SCLicense

		update =
			"update SCLicense set companyId = ? where licenseId = ?";

		columnNames = new String[] {"companyId", "licenseId"};

		_batchUpdateCompanyIdOnTable(
			"SCLicense", select, update, columnNames);
	}

	private void _updateShoppingItemField() throws Exception {
		String select =
			"select si.companyId, sif.itemFieldId from ShoppingItem si, " +
				"ShoppingItemField sif where si.itemId=sif.itemId";

		String update =
			"update ShoppingItemField set companyId = ? where itemFieldId = ?";

		String[] columnNames = {"companyId", "itemFieldId"};

		_batchUpdateCompanyIdOnTable(
			"ShoppingItemField", select, update, columnNames);
	}

	private void _updateShoppingItemPrice() throws Exception {
		String select =
			"select si.companyId, sip.itemPriceId from ShoppingItem si, " +
				"ShoppingItemPrice sip where si.itemId=sip.itemId";

		String update =
			"update ShoppingItemPrice set companyId = ? where itemPriceId = ?";

		String[] columnNames = {"companyId", "itemPriceId"};

		_batchUpdateCompanyIdOnTable(
			"ShoppingItemPrice", select, update, columnNames);
	}

	private void _updateShoppingOrderItem() throws Exception {
		String select =
			"select si.companyId, soi.orderItemId from ShoppingItem si, " +
				"ShoppingOrderItem soi where si.itemId=soi.itemId";

		String update =
			"update ShoppingOrderItem set companyId = ? where orderItemId = ?";

		String[] columnNames = {"companyId", "orderItemId"};

		_batchUpdateCompanyIdOnTable(
			"ShoppingOrderItem", select, update, columnNames);
	}

	private void _updateTrashVersion() throws Exception {
		String select =
			"select te.companyId, tv.versionId from TrashEntry te, " +
				"TrashVersion tv where te.entryId=tv.entryId";

		String update =
			"update TrashVersion set companyId = ? where versionId = ?";

		String[] columnNames = {"companyId", "versionId"};

		_batchUpdateCompanyIdOnTable(
			"TrashVersion", select, update, columnNames);
	}

	private void _updateUserGroupGroupRole() throws Exception {
		String select =
			"select g.companyId, uggr.userGroupId, uggr.groupId, uggr.roleId " +
				"from Group_ g, Role_ r, UserGroup ug, " +
				"UserGroupGroupRole uggr where g.groupId=uggr.groupId and " +
				"ug.userGroupId=uggr.userGroupId and r.roleId=uggr.roleId";

		String update =
			"update UserGroupGroupRole set companyId = ? " +
				"where userGroupId = ? and groupId = ? and roleId = ?";

		String[] columnNames = {
			"companyId", "userGroupId", "groupId", "roleId"};

		_batchUpdateCompanyIdOnTable(
			"UserGroupGroupRole", select, update, columnNames);
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

		String[] columnNames = {"companyId", "userId", "groupId", "roleId"};

		_batchUpdateCompanyIdOnTable(
			"UserGroupRole", select, update, columnNames);
	}

	private void _updateUserGroups_Teams() throws Exception {
		String select =
			"select t.companyId, ugt.teamId, ugt.userGroupId from Team t, " +
				"UserGroup ug, UserGroups_Teams ugt where " +
				"t.teamId=ugt.teamId and ug.userGroupId=ugt.userGroupId";

		String update =
			"update UserGroups_Teams set companyId = ? " +
				"where teamId = ? and userGroupId = ?";

		String[] columnNames = {"companyId", "teamId", "userGroupId"};

		_batchUpdateCompanyIdOnTable(
			"UserGroups_Teams", select, update, columnNames);
	}

	private void _updateUserIdMapper() throws Exception {
		String select =
			"select u.companyId, uim.userIdMapperId from User_ u, " +
				"UserIdMapper uim where u.userId=ui.userId";

		String update =
			"update UserIdMapper set companyId = ? where userIdMapperId = ?";

		String[] columnNames = {"companyId", "userIdMapperId"};

		_batchUpdateCompanyIdOnTable(
			"UserIdMapper", select, update, columnNames);
	}

	private void _updateUsers_Groups() throws Exception {
		String select =
			"select u.companyId, ug.userId, ug.groupId from Group_ g, " +
				"User_ u, Users_Groups ug where g.groupId=ug.groupId and " +
				"u.userId=ug.userId";

		String update =
			"update Users_Groups set companyId = ? where userId = ? " +
				"and groupId = ?";

		String[] columnNames = {"companyId", "userId", "groupId"};

		_batchUpdateCompanyIdOnTable(
			"Users_Groups", select, update, columnNames);
	}

	private void _updateUsers_Orgs() throws Exception {
		String select =
			"select u.companyId, uo.userId, uo.organizationId from " +
				"Organization_ o, User_ u, Users_Orgs uo where " +
				"o.organizationId=uo.organizationId and u.userId=uo.userId";

		String update =
			"update Users_Orgs set companyId = ? where userId = ? and " +
				"organizationId = ?";

		String[] columnNames = {"companyId", "userId", "organizationId"};

		_batchUpdateCompanyIdOnTable(
			"Users_Orgs", select, update, columnNames);
	}

	private void _updateUsers_Roles() throws Exception {
		String select =
			"select u.companyId, ur.userId, ur.roleId from Role_ r, User_ u, " +
				"Users_Roles ur where r.roleId=ur.roleId and " +
				"u.userId=ur.userId";

		String update =
			"update Users_Roles set companyId = ? where userId = ? and " +
				"roleId = ?";

		String[] columnNames = {"companyId", "userId", "roleId"};

		_batchUpdateCompanyIdOnTable(
			"Users_Roles", select, update, columnNames);
	}

	private void _updateUsers_Teams() throws Exception {
		String select =
			"select u.companyId, ut.userId, ut.teamId from Team t, User_ u, " +
				"Users_Teams ut where t.teamId=ut.teamId and " +
				"u.userId=ut.userId";

		String update =
			"update Users_Teams set companyId = ? where userId = ? and " +
				"teamId = ?";

		String[] columnNames = {"companyId", "userId", "teamId"};

		_batchUpdateCompanyIdOnTable(
			"Users_Teams", select, update, columnNames);
	}

	private void _updateUsers_UserGroups() throws Exception {
		String select =
			"select u.companyId, uug.userId, uug.userGroupId from " +
				"UserGroup ug, User_ u, Users_UserGroups uug where " +
				"ug.userGroupId=uug.userGroupId and u.userId=uug.userId";

		String update =
			"update Users_UserGroups set companyId = ? where userId = ? and " +
				"userGroupId = ?";

		String[] columnNames = {"companyId", "userId", "userGroupId"};

		_batchUpdateCompanyIdOnTable(
			"Users_UserGroups", select, update, columnNames);
	}

	private void _updateUserTrackerPath() throws Exception {
		String select =
			"select ut.companyId, utp.userTrackerPathId from UserTracker ut, " +
				"UserTrackerPath utp where ut.userTrackerId=utp.userTrackerId";

		String update =
			"update UserTrackerPath set companyId = ? where " +
				"userTrackerPathId = ?";

		String[] columnNames = {"companyId", "userTrackerPathId"};

		_batchUpdateCompanyIdOnTable(
			"UserTrackerPath", select, update, columnNames);
	}

	private void _updateWikiPageResource() throws Exception {
		String select =
			"select wn.companyId, wpr.resourcePrimKey from " +
				" WikiNode wn, WikiPageResource wpr where wn.nodeId=wpr.nodeId";

		String update =
			"update WikiPageResource set companyId = ? where " +
				"resourcePrimKey = ?";

		String[] columnNames = {"companyId", "resourcePrimKey"};

		_batchUpdateCompanyIdOnTable(
			"WikiPageResource", select, update, columnNames);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeSharding.class);

}