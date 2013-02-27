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

package com.liferay.portal.tools.samplesqlbuilder;

import com.liferay.counter.model.Counter;
import com.liferay.counter.model.impl.CounterImpl;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.model.impl.AccountImpl;
import com.liferay.portal.model.impl.ClassNameImpl;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.model.impl.ContactImpl;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.PortletPreferencesImpl;
import com.liferay.portal.model.impl.ResourcePermissionImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.model.impl.VirtualHostImpl;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.impl.AssetEntryImpl;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.blogs.model.impl.BlogsEntryImpl;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserImpl;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLSync;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLSyncImpl;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordImpl;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordSetImpl;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMContent;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMContentImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStorageLinkImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkImpl;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleResourceImpl;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.model.impl.MBDiscussionImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.portlet.messageboards.model.impl.MBStatsUserImpl;
import com.liferay.portlet.messageboards.model.impl.MBThreadImpl;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.impl.SocialActivityImpl;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.impl.WikiNodeImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;
import com.liferay.util.SimpleCounter;

import java.io.File;

import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class DataFactory {

	public DataFactory(
		String baseDir, int maxGroupsCount, int maxJournalArticleSize,
		int maxUserToGroupCount) {

		try {
			_baseDir = baseDir;
			_maxGroupsCount = maxGroupsCount;
			_maxUserToGroupCount = maxUserToGroupCount;

			initSimpleCounters();

			initClassNames();
			initCompany();
			initDLFileEntryType();
			initGuestGroup();
			initJournalArticle(maxJournalArticleSize);
			initRoles();
			initUserNames();
			initUsers();
			initVirtualHost();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AssetEntry addAssetEntry(
		long groupId, long userId, long classNameId, long classPK,
		boolean visible, String mimeType, String title) {

		return _newAssetEntry(
			groupId, userId, classNameId, classPK, visible, mimeType, title);
	}

	public BlogsEntry addBlogsEntry(
		long groupId, long userId, String title, String urlTitle,
		String content) {

		return _newBlogsEntry(
			_counter.get(), groupId, userId, title, urlTitle, content);
	}

	public BlogsStatsUser addBlogsStatsUser(long groupId, long userId) {
		return _newBlogsStatsUser(groupId, userId);
	}

	public Contact addContact(User user) {
		return _newContact(
			user.getContactId(), user.getCompanyId(), user.getUserId(),
			user.getFullName(), new Date(), new Date(), getUserClassNameId(),
			user.getUserId(), _company.getAccountId(),
			ContactConstants.DEFAULT_PARENT_CONTACT_ID, user.getEmailAddress(),
			user.getFirstName(), user.getLastName(), true, new Date());
	}

	public List<Counter> addCounters() {
		List<Counter> counters = new ArrayList<Counter>();

		// Counter

		Counter counter = _newCounter(Counter.class.getName(), _counter.get());

		counters.add(counter);

		// ResourcePermission

		counter = _newCounter(
			ResourcePermission.class.getName(),
			_resourcePermissionCounter.get());

		counters.add(counter);

		// SocialActivity

		counter = _newCounter(
			SocialActivity.class.getName(), _socialActivityCounter.get());

		counters.add(counter);

		return counters;
	}

	public DDLRecord addDDLRecord(
		long groupId, long companyId, long userId, long ddlRecordSetId) {

		return _newDDLRecord(
			_counter.get(), groupId, companyId, userId, nextFutureDate(),
			ddlRecordSetId);
	}

	public DDLRecordSet addDDLRecordSet(
		long groupId, long companyId, long userId, long ddmStructureId) {

		return _newDDLRecordSet(
			_counter.get(), groupId, companyId, userId, ddmStructureId);
	}

	public DDLRecordVersion addDDLRecordVersion(DDLRecord ddlRecord) {
		return _newDDLRecordVersion(
			_counter.get(), ddlRecord.getGroupId(), ddlRecord.getCompanyId(),
			ddlRecord.getUserId(), ddlRecord.getRecordSetId(),
			ddlRecord.getRecordId());
	}

	public DDMContent addDDMContent(long groupId, long companyId, long userId) {
		return _newDDMContent(_counter.get(), groupId, companyId, userId);
	}

	public DDMStorageLink addDDMStorageLink(
		long classNameId, long classPK, long structureId) {

		return _newDDMStorageLink(
			_counter.get(), classNameId, classPK, structureId);
	}

	public DDMStructure addDDMStructure(
		long groupId, long companyId, long userId, long classNameId) {

		return _newDDMStructure(
			_counter.get(), groupId, companyId, userId, nextFutureDate(),
			classNameId);
	}

	public DDMStructureLink addDDMStructureLink(
		long classPK, long structureId) {

		return _newDDMStructureLink(
			_counter.get(), getDLFileEntryClassNameId(), classPK, structureId);
	}

	public DLFileEntry addDlFileEntry(
		long groupId, long companyId, long userId, long folderId,
		String extension, String mimeType, String name, String title,
		String description) {

		return _newDlFileEntry(
			_counter.get(), groupId, companyId, userId, nextFutureDate(),
			groupId, folderId, name, extension, mimeType, title, description,
			_counter.get(), _counter.get());
	}

	public DLFileEntryMetadata addDLFileEntryMetadata(
		long ddmStorageId, long ddmStructureId, long fileEntryId,
		long fileVersionId) {

		return _newDLFileEntryMetadata(
			_counter.get(), ddmStorageId, ddmStructureId, fileEntryId,
			fileVersionId);
	}

	public DLFileVersion addDLFileVersion(DLFileEntry dlFileEntry) {
		return _newDLFileVersion(
			_counter.get(), dlFileEntry.getGroupId(),
			dlFileEntry.getCompanyId(), dlFileEntry.getUserId(),
			dlFileEntry.getRepositoryId(), dlFileEntry.getFileEntryId(),
			dlFileEntry.getExtension(), dlFileEntry.getMimeType(),
			dlFileEntry.getTitle(), dlFileEntry.getDescription(),
			dlFileEntry.getSize());
	}

	public DLFolder addDLFolder(
		long groupId, long companyId, long userId, long parentFolderId,
		String name, String description) {

		return _newDLFolder(
			_counter.get(), groupId, companyId, userId, nextFutureDate(),
			groupId, parentFolderId, name, description);
	}

	public DLSync addDLSync(
		long companyId, long fileId, long repositoryId, long parentFolderId,
		boolean typeFolder) {

		String type = null;

		if (typeFolder) {
			type = DLSyncConstants.TYPE_FOLDER;
		}
		else {
			type = DLSyncConstants.TYPE_FILE;
		}

		return _newDLSync(
			_counter.get(), companyId, fileId, repositoryId, parentFolderId,
			DLSyncConstants.EVENT_ADD, type);
	}

	public Group addGroup(
		long groupId, long classNameId, long classPK, String name,
		String friendlyURL, boolean site) {

		return _newGroup(
			groupId, classNameId, classPK, name, friendlyURL, site);
	}

	public JournalArticle addJournalArticle(
		long resourcePrimKey, long groupId, long companyId, String articleId) {

		return _newJournalArticle(
			_counter.get(), resourcePrimKey, groupId, companyId, articleId,
			_journalArticleContent);
	}

	public JournalArticleResource addJournalArticleResource(long groupId) {
		return _newJournalArticleResource(
			_counter.get(), groupId, String.valueOf(_counter.get()));
	}

	public Layout addLayout(
		long layoutId, String name, String friendlyURL, String column1,
		String column2) {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.setProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "2_columns_ii");
		typeSettingsProperties.setProperty("column-1", column1);
		typeSettingsProperties.setProperty("column-2", column2);

		String typeSettings = StringUtil.replace(
			typeSettingsProperties.toString(), "\n", "\\n");

		return _newLayout(
			_counter.get(), false, layoutId, name, friendlyURL, typeSettings);
	}

	public MBCategory addMBCategory(
		long categoryId, long groupId, long companyId, long userId, String name,
		String description, int threadCount, int messageCount) {

		return _newMBCategory(
			categoryId, groupId, companyId, userId, name, description,
			MBCategoryConstants.DEFAULT_DISPLAY_STYLE, threadCount,
			messageCount);
	}

	public MBDiscussion addMBDiscussion(
		long classNameId, long classPK, long threadId) {

		return _newMBDiscussion(_counter.get(), classNameId, classPK, threadId);
	}

	public MBMessage addMBMessage(
		long messageId, long groupId, long userId, long classNameId,
		long classPK, long categoryId, long threadId, long rootMessageId,
		long parentMessageId, String subject, String body) {

		return _newMBMessage(
			messageId, groupId, userId, classNameId, classPK, categoryId,
			threadId, rootMessageId, parentMessageId, subject, body);
	}

	public MBStatsUser addMBStatsUser(long groupId, long userId) {
		return _newMBStatsUser(groupId, userId);
	}

	public MBThread addMBThread(
		long threadId, long groupId, long companyId, long categoryId,
		long rootMessageId, int messageCount, long lastPostByUserId) {

		return _newMBThread(
			threadId, groupId, companyId, categoryId, rootMessageId,
			lastPostByUserId, messageCount, lastPostByUserId);
	}

	public PortletPreferences addPortletPreferences(
		long ownerId, long plid, String portletId, String preferences) {

		return _newPortletPreferences(
			_counter.get(), ownerId, PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid,
			portletId, preferences);
	}

	public List<ResourcePermission> addResourcePermission(
		long companyId, String name, String primKey) {

		List<ResourcePermission> resourcePermissions =
			new ArrayList<ResourcePermission>(2);

		ResourcePermission resourcePermission = _newResourcePermission(
			_resourcePermissionCounter.get(), companyId, name,
			ResourceConstants.SCOPE_INDIVIDUAL, primKey, _ownerRole.getRoleId(),
			_defaultUser.getUserId(), 1);

		resourcePermissions.add(resourcePermission);

		resourcePermission = _newResourcePermission(
			_resourcePermissionCounter.get(), companyId, name,
			ResourceConstants.SCOPE_INDIVIDUAL, primKey, _guestRole.getRoleId(),
			0, 1);

		resourcePermissions.add(resourcePermission);

		return resourcePermissions;
	}

	public SocialActivity addSocialActivity(
		long groupId, long companyId, long userId, long classNameId,
		long classPK) {

		return _newSocialActivity(
			_socialActivityCounter.get(), groupId, companyId, userId,
			classNameId, classPK);
	}

	public User addUser(int currentIndex) {
		String[] userName = nextUserName(currentIndex - 1);
		String screenName ="test" + _userScreenNameCounter.get();

		return _newUser(
			SequentialUUID.generate(), _counter.get(), _company.getCompanyId(),
			new Date(), new Date(), false, _counter.get(), "test", new Date(),
			"What is your screen name?", screenName,
			screenName + "@liferay.com", screenName, "en_US",
			"Welcome " + screenName + StringPool.EXCLAMATION, userName[0],
			userName[1], new Date(), new Date(), new Date(), new Date(), true,
			true);
	}

	public List<Long> addUserToGroupIds(long groupId) {
		List<Long> groupIds = new ArrayList<Long>(_maxUserToGroupCount + 1);

		groupIds.add(_guestGroup.getGroupId());

		if ((groupId + _maxUserToGroupCount) > _maxGroupsCount) {
			groupId = groupId - _maxUserToGroupCount + 1;
		}

		for (int i = 0; i < _maxUserToGroupCount; i++) {
			groupIds.add(groupId + i);
		}

		return groupIds;
	}

	public WikiNode addWikiNode(
		long groupId, long userId, String name, String description) {

		return _newWikiNode(_counter.get(), groupId, userId, name, description);
	}

	public WikiPage addWikiPage(
		long groupId, long userId, long nodeId, String title, double version,
		String content, boolean head) {

		return _newWikiPage(
			_counter.get(), _counter.get(), groupId, userId, nodeId, title,
			version, content, head);
	}

	public Account getAccount() {
		return _account;
	}

	public Role getAdministratorRole() {
		return _administratorRole;
	}

	public long getBlogsEntryClassNameId() {
		return _classNameMap.get(BlogsEntry.class.getName());
	}

	public List<ClassName> getClassNames() {
		return _classNames;
	}

	public Company getCompany() {
		return _company;
	}

	public long getCompanyId() {
		return _company.getCompanyId();
	}

	public SimpleCounter getCounter() {
		return _counter;
	}

	public String getDateLong(Date date) {
		return String.valueOf(date.getTime());
	}

	public String getDateString(Date date) {
		if (date == null) {
			return null;
		}

		return _simpleDateFormat.format(date);
	}

	public long getDDLRecordSetClassNameId() {
		return _classNameMap.get(DDLRecordSet.class.getName());
	}

	public long getDDMContentClassNameId() {
		return _classNameMap.get(DDMContent.class.getName());
	}

	public DLFileEntryType getDefaultDLFileEntryType() {
		return _defaultDLFileEntryType;
	}

	public User getDefaultUser() {
		return _defaultUser;
	}

	public long getDefaultUserId() {
		return _defaultUser.getUserId();
	}

	public long getDLFileEntryClassNameId() {
		return _classNameMap.get(DLFileEntry.class.getName());
	}

	public long getGroupClassNameId() {
		return _classNameMap.get(Group.class.getName());
	}

	public Group getGuestGroup() {
		return _guestGroup;
	}

	public User getGuestUser() {
		return _guestUser;
	}

	public long getJournalArticleClassNameId() {
		return _classNameMap.get(JournalArticle.class.getName());
	}

	public long getMBMessageClassNameId() {
		return _classNameMap.get(MBMessage.class.getName());
	}

	public Role getPowerUserRole() {
		return _powerUserRole;
	}

	public List<Role> getRoles() {
		return _roles;
	}

	public User getSampleUser() {
		return _sampleUser;
	}

	public long getUserClassNameId() {
		return _classNameMap.get(User.class.getName());
	}

	public Role getUserRole() {
		return _userRole;
	}

	public VirtualHost getVirtualHost() {
		return _virtualHost;
	}

	public long getWikiPageClassNameId() {
		return _classNameMap.get(WikiPage.class.getName());
	}

	public void initClassNames() {
		_classNames = new ArrayList<ClassName>();

		List<String> models = ModelHintsUtil.getModels();

		for (String model : models) {
			ClassName className = _newClassName(_counter.get(), model);

			_classNames.add(className);

			_classNameMap.put(model, className.getClassNameId());
		}
	}

	public void initCompany() {
		_company = _newCompany(
			_counter.get(), _counter.get(), "liferay.com", "liferay.com", true);

		_account = _newAccount(
			_company.getAccountId(), _company.getCompanyId(), new Date(),
			new Date(), "Liferay", "Liferay, Inc.");
	}

	public void initDLFileEntryType() {
		_defaultDLFileEntryType = _newDLFileEntryType(
			SequentialUUID.generate(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			nextFutureDate(), nextFutureDate(),
			DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT);
	}

	public void initGuestGroup() {
		long groupId = _counter.get();

		_guestGroup = _newGroup(
			groupId, getGroupClassNameId(), groupId, GroupConstants.GUEST,
			"/guest", true);
	}

	public void initJournalArticle(int maxJournalArticleSize) throws Exception {
		if (maxJournalArticleSize <= 0) {
			maxJournalArticleSize = 1;
		}

		char[] chars = new char[maxJournalArticleSize];

		for (int i = 0; i < maxJournalArticleSize; i++) {
			chars[i] = (char)(CharPool.LOWER_CASE_A + (i % 26));
		}

		_journalArticleContent = new String(chars);
	}

	public void initRoles() {
		_roles = new ArrayList<Role>();

		long roleClassNameId = _classNameMap.get(Role.class.getName());

		// Administrator

		long roleId = _counter.get();

		_administratorRole = _newRole(
			roleId, _company.getCompanyId(), roleClassNameId, roleId,
			RoleConstants.ADMINISTRATOR, RoleConstants.TYPE_REGULAR);

		_roles.add(_administratorRole);

		// Guest

		roleId = _counter.get();

		_guestRole = _newRole(
			roleId, _company.getCompanyId(), roleClassNameId, roleId,
			RoleConstants.GUEST, RoleConstants.TYPE_REGULAR);

		_roles.add(_guestRole);

		// Organization Administrator

		roleId = _counter.get();

		Role organizationAdministratorRole = _newRole(
			roleId, _company.getCompanyId(), roleClassNameId, roleId,
			RoleConstants.ORGANIZATION_ADMINISTRATOR,
			RoleConstants.TYPE_ORGANIZATION);

		_roles.add(organizationAdministratorRole);

		// Organization Owner

		roleId = _counter.get();

		Role organizationOwnerRole = _newRole(
			roleId, _company.getCompanyId(), roleClassNameId, roleId,
			RoleConstants.ORGANIZATION_OWNER, RoleConstants.TYPE_ORGANIZATION);

		_roles.add(organizationOwnerRole);

		// Organization User

		roleId = _counter.get();

		Role organizationUserRole = _newRole(
			roleId, _company.getCompanyId(), roleClassNameId, roleId,
			RoleConstants.ORGANIZATION_USER, RoleConstants.TYPE_ORGANIZATION);

		_roles.add(organizationUserRole);

		// Owner

		roleId = _counter.get();

		_ownerRole = _newRole(
			roleId, _company.getCompanyId(), roleClassNameId, roleId,
			RoleConstants.OWNER, RoleConstants.TYPE_REGULAR);

		_roles.add(_ownerRole);

		// Power User

		roleId = _counter.get();

		_powerUserRole = _newRole(
			roleId, _company.getCompanyId(), roleClassNameId, roleId,
			RoleConstants.POWER_USER, RoleConstants.TYPE_REGULAR);

		_roles.add(_powerUserRole);

		// Site Administrator

		roleId = _counter.get();

		Role siteAdministratorRole = _newRole(
			roleId, _company.getCompanyId(), roleClassNameId, roleId,
			RoleConstants.SITE_ADMINISTRATOR, RoleConstants.TYPE_SITE);

		_roles.add(siteAdministratorRole);

		// Site Member

		roleId = _counter.get();

		Role siteMemberRole = _newRole(
			roleId, _company.getCompanyId(), roleClassNameId, roleId,
			RoleConstants.SITE_MEMBER, RoleConstants.TYPE_SITE);

		_roles.add(siteMemberRole);

		// Site Owner

		roleId = _counter.get();

		Role siteOwnerRole = _newRole(
			roleId, _company.getCompanyId(), roleClassNameId, roleId,
			RoleConstants.SITE_OWNER, RoleConstants.TYPE_SITE);

		_roles.add(siteOwnerRole);

		// User

		roleId = _counter.get();

		_userRole = _newRole(
			roleId, _company.getCompanyId(), roleClassNameId, roleId,
			RoleConstants.USER, RoleConstants.TYPE_REGULAR);

		_roles.add(_userRole);
	}

	public void initSimpleCounters() {
		_counter = new SimpleCounter(_maxGroupsCount + 1);
		_futureDateCounter = new SimpleCounter();
		_resourcePermissionCounter = new SimpleCounter();
		_socialActivityCounter = new SimpleCounter();
		_userScreenNameCounter = new SimpleCounter();
	}

	public void initUserNames() throws Exception {
		String dependenciesDir =
			"../portal-impl/src/com/liferay/portal/tools/samplesqlbuilder/" +
				"dependencies/";

		_firstNames = ListUtil.fromFile(
			new File(_baseDir, dependenciesDir + "first_names.txt"));
		_lastNames = ListUtil.fromFile(
			new File(_baseDir, dependenciesDir + "last_names.txt"));
	}

	public void initUsers() {
		long userId = _counter.get();
		String screenName = String.valueOf(userId);

		_defaultUser = _newUser(
			SequentialUUID.generate(), userId, _company.getCompanyId(),
			new Date(), new Date(), true, _counter.get(), "test", new Date(),
			"What is your screen name?", screenName,
			screenName + "@liferay.com", screenName, "en_US",
			"Welcome " + screenName + StringPool.EXCLAMATION, StringPool.BLANK,
			StringPool.BLANK, new Date(), new Date(), new Date(), new Date(),
			true, true);

		_guestUser = _newUser(
			SequentialUUID.generate(), _counter.get(), _company.getCompanyId(),
			new Date(), new Date(), false, _counter.get(), "test", new Date(),
			"What is your screen name?", "Test", "Test@liferay.com", "Test",
			"en_US", "Welcome Test!", "Test", "Test", new Date(), new Date(),
			new Date(), new Date(), true, true);

		_sampleUser = _newUser(
			SequentialUUID.generate(), _counter.get(), _company.getCompanyId(),
			new Date(), new Date(), false, _counter.get(), "test", new Date(),
			"What is your screen name?", "Sample", "Sample@liferay.com",
			"Sample", "en_US", "Welcome Sample!", "Sample", "Sample",
			new Date(), new Date(), new Date(), new Date(), true, true);
	}

	public void initVirtualHost() throws Exception {
		_virtualHost = _newVirtualHost(
			_counter.get(), _company.getCompanyId(), "localhost");
	}

	public IntegerWrapper newInteger() {
		return new IntegerWrapper();
	}

	public String[] nextUserName(long index) {
		String[] userName = new String[2];

		userName[0] = _firstNames.get(
			(int)(index / _lastNames.size()) % _firstNames.size());
		userName[1] = _lastNames.get((int)(index % _lastNames.size()));

		return userName;
	}

	protected Date nextFutureDate() {
		return new Date(
			_FUTURE_TIME + (_futureDateCounter.get() * Time.SECOND));
	}

	private Account _newAccount(
		long accountId, long companyId, Date createDate, Date modifiedDate,
		String name, String legalName) {

		Account account = new AccountImpl();

		account.setAccountId(accountId);
		account.setCompanyId(companyId);
		account.setCreateDate(createDate);
		account.setModifiedDate(modifiedDate);
		account.setName(name);
		account.setLegalName(legalName);

		return account;
	}

	private AssetEntry _newAssetEntry(
		long groupId, long userId, long classNameId, long classPK,
		boolean visible, String mimeType, String title) {

		AssetEntry assetEntry = new AssetEntryImpl();

		assetEntry.setGroupId(groupId);
		assetEntry.setUserId(userId);
		assetEntry.setClassNameId(classNameId);
		assetEntry.setClassPK(classPK);
		assetEntry.setVisible(visible);
		assetEntry.setMimeType(mimeType);
		assetEntry.setTitle(title);

		return assetEntry;
	}

	private BlogsEntry _newBlogsEntry(
		long entryId, long groupId, long userId, String title, String urlTitle,
		String content) {

		BlogsEntry blogsEntry = new BlogsEntryImpl();

		blogsEntry.setEntryId(entryId);
		blogsEntry.setGroupId(groupId);
		blogsEntry.setUserId(userId);
		blogsEntry.setTitle(title);
		blogsEntry.setUrlTitle(urlTitle);
		blogsEntry.setContent(content);

		return blogsEntry;
	}

	private BlogsStatsUser _newBlogsStatsUser(long groupId, long userId) {
		BlogsStatsUser blogsStatsUser = new BlogsStatsUserImpl();

		blogsStatsUser.setGroupId(groupId);
		blogsStatsUser.setUserId(userId);

		return blogsStatsUser;
	}

	private ClassName _newClassName(long classNameId, String value) {
		ClassName className = new ClassNameImpl();

		className.setClassNameId(classNameId);
		className.setValue(value);

		return className;
	}

	private Company _newCompany(
		long companyId, long accountId, String webId, String mx,
		boolean active) {

		Company company = new CompanyImpl();

		company.setCompanyId(companyId);
		company.setAccountId(accountId);
		company.setWebId(webId);
		company.setMx(mx);
		company.setActive(active);

		return company;
	}

	private Contact _newContact(
		long contactId, long companyId, long userId, String userName,
		Date createDate, Date modifiedDate, long classNameId, long classPK,
		long accountId, long parentContactId, String emailAddress,
		String firstName, String lastName, boolean male, Date birthday) {

		Contact contact = new ContactImpl();

		contact.setContactId(contactId);
		contact.setCompanyId(companyId);
		contact.setUserId(userId);
		contact.setUserName(userName);
		contact.setCreateDate(createDate);
		contact.setModifiedDate(modifiedDate);
		contact.setClassNameId(classNameId);
		contact.setClassPK(classPK);
		contact.setAccountId(accountId);
		contact.setParentContactId(parentContactId);
		contact.setEmailAddress(emailAddress);
		contact.setFirstName(firstName);
		contact.setLastName(lastName);
		contact.setMale(male);
		contact.setBirthday(birthday);

		return contact;
	}

	private Counter _newCounter(String name, long currentId) {
		Counter counter = new CounterImpl();

		counter.setName(name);
		counter.setCurrentId(currentId);

		return counter;
	}

	private DDLRecord _newDDLRecord(
		long recordId, long groupId, long companyId, long userId,
		Date createDate, long recordSetId) {

		DDLRecord ddlRecord = new DDLRecordImpl();

		ddlRecord.setRecordId(recordId);
		ddlRecord.setGroupId(groupId);
		ddlRecord.setCompanyId(companyId);
		ddlRecord.setUserId(userId);
		ddlRecord.setCreateDate(createDate);
		ddlRecord.setRecordSetId(recordSetId);

		return ddlRecord;
	}

	private DDLRecordSet _newDDLRecordSet(
		long recordSetId, long groupId, long companyId, long userId,
		long ddmStructureId) {

		DDLRecordSet ddlRecordSet = new DDLRecordSetImpl();

		ddlRecordSet.setRecordSetId(recordSetId);
		ddlRecordSet.setGroupId(groupId);
		ddlRecordSet.setCompanyId(companyId);
		ddlRecordSet.setUserId(userId);
		ddlRecordSet.setDDMStructureId(ddmStructureId);

		return ddlRecordSet;
	}

	private DDLRecordVersion _newDDLRecordVersion(
		long recordVersionId, long groupId, long companyId, long userId,
		long recordSetId, long recordId) {

		DDLRecordVersion ddlRecordVersion = new DDLRecordVersionImpl();

		ddlRecordVersion.setRecordVersionId(recordVersionId);
		ddlRecordVersion.setGroupId(groupId);
		ddlRecordVersion.setCompanyId(companyId);
		ddlRecordVersion.setUserId(userId);
		ddlRecordVersion.setRecordSetId(recordSetId);
		ddlRecordVersion.setRecordId(recordId);

		return ddlRecordVersion;
	}

	private DDMContent _newDDMContent(
		long contentId, long groupId, long companyId, long userId) {

		DDMContent ddmContent = new DDMContentImpl();

		ddmContent.setContentId(contentId);
		ddmContent.setGroupId(groupId);
		ddmContent.setCompanyId(companyId);
		ddmContent.setUserId(userId);

		return ddmContent;
	}

	private DDMStorageLink _newDDMStorageLink(
		long storageLinkId, long classNameId, long classPK, long structureId) {

		DDMStorageLink ddmStorageLink = new DDMStorageLinkImpl();

		ddmStorageLink.setStorageLinkId(storageLinkId);
		ddmStorageLink.setClassNameId(classNameId);
		ddmStorageLink.setClassPK(classPK);
		ddmStorageLink.setStructureId(structureId);

		return ddmStorageLink;
	}

	private DDMStructure _newDDMStructure(
		long structureId, long groupId, long companyId, long userId,
		Date createDate, long classNameId) {

		DDMStructure ddmStructure = new DDMStructureImpl();

		ddmStructure.setStructureId(structureId);
		ddmStructure.setGroupId(groupId);
		ddmStructure.setCompanyId(companyId);
		ddmStructure.setUserId(userId);
		ddmStructure.setCreateDate(createDate);
		ddmStructure.setClassNameId(classNameId);

		return ddmStructure;
	}

	private DDMStructureLink _newDDMStructureLink(
		long structureLinkId, long classNameId, long classPK,
		long structureId) {

		DDMStructureLink ddmStructureLink = new DDMStructureLinkImpl();

		ddmStructureLink.setStructureLinkId(structureLinkId);
		ddmStructureLink.setClassNameId(classNameId);
		ddmStructureLink.setClassPK(classPK);
		ddmStructureLink.setStructureId(structureId);

		return ddmStructureLink;
	}

	private DLFileEntry _newDlFileEntry(
		long fileEntryId, long groupId, long companyId, long userId,
		Date createDate, long repositoryId, long folderId, String name,
		String extension, String mimeType, String title, String description,
		long smallImageId, long largeImageId) {

		DLFileEntry dlFileEntry = new DLFileEntryImpl();

		dlFileEntry.setFileEntryId(fileEntryId);
		dlFileEntry.setGroupId(groupId);
		dlFileEntry.setCompanyId(companyId);
		dlFileEntry.setUserId(userId);
		dlFileEntry.setCreateDate(createDate);
		dlFileEntry.setRepositoryId(repositoryId);
		dlFileEntry.setFolderId(folderId);
		dlFileEntry.setName(name);
		dlFileEntry.setExtension(extension);
		dlFileEntry.setMimeType(mimeType);
		dlFileEntry.setTitle(title);
		dlFileEntry.setDescription(description);
		dlFileEntry.setSmallImageId(smallImageId);
		dlFileEntry.setLargeImageId(largeImageId);

		return dlFileEntry;
	}

	private DLFileEntryMetadata _newDLFileEntryMetadata(
		long fileEntryMetadataId, long ddmStorageId, long ddmStructureId,
		long fileEntryId, long fileVersionId) {

		DLFileEntryMetadata dlFileEntryMetadata = new DLFileEntryMetadataImpl();

		dlFileEntryMetadata.setFileEntryMetadataId(fileEntryMetadataId);
		dlFileEntryMetadata.setDDMStorageId(ddmStorageId);
		dlFileEntryMetadata.setDDMStructureId(ddmStructureId);
		dlFileEntryMetadata.setFileEntryId(fileEntryId);
		dlFileEntryMetadata.setFileVersionId(fileVersionId);

		return dlFileEntryMetadata;
	}

	private DLFileEntryType _newDLFileEntryType(
		String uuid, long fileEntryTypeId, Date createDate, Date modifiedDate,
		String name) {

		DLFileEntryType dLFileEntryType = new DLFileEntryTypeImpl();

		dLFileEntryType.setUuid(uuid);
		dLFileEntryType.setFileEntryTypeId(fileEntryTypeId);
		dLFileEntryType.setCreateDate(createDate);
		dLFileEntryType.setModifiedDate(modifiedDate);
		dLFileEntryType.setName(name);

		return dLFileEntryType;
	}

	private DLFileVersion _newDLFileVersion(
		long fileVersionId, long groupId, long companyId, long userId,
		long repositoryId, long fileEntryId, String extension, String mimeType,
		String title, String description, long size) {

		DLFileVersion dlFileVersion = new DLFileVersionImpl();

		dlFileVersion.setFileVersionId(fileVersionId);
		dlFileVersion.setGroupId(groupId);
		dlFileVersion.setCompanyId(companyId);
		dlFileVersion.setUserId(userId);
		dlFileVersion.setRepositoryId(repositoryId);
		dlFileVersion.setFileEntryId(fileEntryId);
		dlFileVersion.setExtension(extension);
		dlFileVersion.setMimeType(mimeType);
		dlFileVersion.setTitle(title);
		dlFileVersion.setDescription(description);
		dlFileVersion.setSize(size);

		return dlFileVersion;
	}

	private DLFolder _newDLFolder(
		long folderId, long groupId, long companyId, long userId,
		Date createDate, long repositoryId, long parentFolderId, String name,
		String description) {

		DLFolder dlFolder = new DLFolderImpl();

		dlFolder.setFolderId(folderId);
		dlFolder.setGroupId(groupId);
		dlFolder.setCompanyId(companyId);
		dlFolder.setUserId(userId);
		dlFolder.setCreateDate(createDate);
		dlFolder.setRepositoryId(repositoryId);
		dlFolder.setParentFolderId(parentFolderId);
		dlFolder.setName(name);
		dlFolder.setDescription(description);

		return dlFolder;
	}

	private DLSync _newDLSync(
		long syncId, long companyId, long fileId, long repositoryId,
		long parentFolderId, String event, String type) {

		DLSync dlSync = new DLSyncImpl();

		dlSync.setSyncId(syncId);
		dlSync.setCompanyId(companyId);
		dlSync.setFileId(fileId);
		dlSync.setRepositoryId(repositoryId);
		dlSync.setParentFolderId(parentFolderId);
		dlSync.setEvent(event);
		dlSync.setType(type);

		return dlSync;
	}

	private Group _newGroup(
		long groupId, long classNameId, long classPK, String name,
		String friendlyURL, boolean site) {

		Group group = new GroupImpl();

		group.setGroupId(groupId);
		group.setClassNameId(classNameId);
		group.setClassPK(classPK);
		group.setName(name);
		group.setFriendlyURL(friendlyURL);
		group.setSite(site);

		return group;
	}

	private JournalArticle _newJournalArticle(
		long id, long resourcePrimKey, long groupId, long companyId,
		String articleId, String content) {

		JournalArticle journalArticle = new JournalArticleImpl();

		journalArticle.setId(id);
		journalArticle.setResourcePrimKey(resourcePrimKey);
		journalArticle.setGroupId(groupId);
		journalArticle.setCompanyId(companyId);
		journalArticle.setArticleId(articleId);
		journalArticle.setContent(content);

		return journalArticle;
	}

	private JournalArticleResource _newJournalArticleResource(
		long resourcePrimKey, long groupId, String articleId) {

		JournalArticleResource journalArticleResource =
			new JournalArticleResourceImpl();

		journalArticleResource.setResourcePrimKey(resourcePrimKey);
		journalArticleResource.setGroupId(groupId);
		journalArticleResource.setArticleId(articleId);

		return journalArticleResource;
	}

	private Layout _newLayout(
		long plid, boolean privateLayout, long layoutId, String name,
		String friendlyURL, String typeSettings) {

		Layout layout = new LayoutImpl();

		layout.setPlid(plid);
		layout.setPrivateLayout(privateLayout);
		layout.setLayoutId(layoutId);
		layout.setName(name);
		layout.setFriendlyURL(friendlyURL);
		layout.setTypeSettings(typeSettings);

		return layout;
	}

	private MBCategory _newMBCategory(
		long categoryId, long groupId, long companyId, long userId, String name,
		String description, String displayStyle, int threadCount,
		int messageCount) {

		MBCategory mbCategory = new MBCategoryImpl();

		mbCategory.setCategoryId(categoryId);
		mbCategory.setGroupId(groupId);
		mbCategory.setCompanyId(companyId);
		mbCategory.setUserId(userId);
		mbCategory.setName(name);
		mbCategory.setDescription(description);
		mbCategory.setDisplayStyle(displayStyle);
		mbCategory.setThreadCount(threadCount);
		mbCategory.setMessageCount(messageCount);

		return mbCategory;
	}

	private MBDiscussion _newMBDiscussion(
		long discussionId, long classNameId, long classPK, long threadId) {

		MBDiscussion mbDiscussion = new MBDiscussionImpl();

		mbDiscussion.setDiscussionId(discussionId);
		mbDiscussion.setClassNameId(classNameId);
		mbDiscussion.setClassPK(classPK);
		mbDiscussion.setThreadId(threadId);

		return mbDiscussion;
	}

	private MBMessage _newMBMessage(
		long messageId, long groupId, long userId, long classNameId,
		long classPK, long categoryId, long threadId, long rootMessageId,
		long parentMessageId, String subject, String body) {

		MBMessage mbMessage = new MBMessageImpl();

		mbMessage.setMessageId(messageId);
		mbMessage.setGroupId(groupId);
		mbMessage.setUserId(userId);
		mbMessage.setClassNameId(classNameId);
		mbMessage.setClassPK(classPK);
		mbMessage.setCategoryId(categoryId);
		mbMessage.setThreadId(threadId);
		mbMessage.setRootMessageId(rootMessageId);
		mbMessage.setParentMessageId(parentMessageId);
		mbMessage.setSubject(subject);
		mbMessage.setBody(body);

		return mbMessage;
	}

	private MBStatsUser _newMBStatsUser(long groupId, long userId) {
		MBStatsUser mbStatsUser = new MBStatsUserImpl();

		mbStatsUser.setGroupId(groupId);
		mbStatsUser.setUserId(userId);

		return mbStatsUser;
	}

	private MBThread _newMBThread(
		long threadId, long groupId, long companyId, long categoryId,
		long rootMessageId, long rootMessageuserId, int messageCount,
		long lastPostByUserId) {

		MBThread mbThread = new MBThreadImpl();

		mbThread.setThreadId(threadId);
		mbThread.setGroupId(groupId);
		mbThread.setCompanyId(companyId);
		mbThread.setCategoryId(categoryId);
		mbThread.setRootMessageId(rootMessageId);
		mbThread.setRootMessageUserId(rootMessageuserId);
		mbThread.setMessageCount(messageCount);
		mbThread.setLastPostByUserId(lastPostByUserId);

		return mbThread;
	}

	private PortletPreferences _newPortletPreferences(
		long portletPreferencesId, long ownerId, int ownerType, long plid,
		String portletId, String preferences) {

		PortletPreferences portletPreferences = new PortletPreferencesImpl();

		portletPreferences.setPortletPreferencesId(portletPreferencesId);
		portletPreferences.setOwnerId(ownerId);
		portletPreferences.setOwnerType(ownerType);
		portletPreferences.setPlid(plid);
		portletPreferences.setPortletId(portletId);
		portletPreferences.setPreferences(preferences);

		return portletPreferences;
	}

	private ResourcePermission _newResourcePermission(
		long resourcePermissionId, long companyId, String name, int scope,
		String primKey, long roleId, long ownerId, long actionIds) {

		ResourcePermission resourcePermission = new ResourcePermissionImpl();

		resourcePermission.setResourcePermissionId(resourcePermissionId);
		resourcePermission.setCompanyId(companyId);
		resourcePermission.setName(name);
		resourcePermission.setScope(scope);
		resourcePermission.setPrimKey(primKey);
		resourcePermission.setRoleId(roleId);
		resourcePermission.setOwnerId(ownerId);
		resourcePermission.setActionIds(actionIds);

		return resourcePermission;
	}

	private Role _newRole(
		long roleId, long companyId, long classNameId, long classPK,
		String name, int type) {

		Role role = new RoleImpl();

		role.setRoleId(roleId);
		role.setCompanyId(companyId);
		role.setClassNameId(classNameId);
		role.setClassPK(classPK);
		role.setName(name);
		role.setType(type);

		return role;
	}

	private SocialActivity _newSocialActivity(
		long activityId, long groupId, long companyId, long userId,
		long classNameId, long classPK) {

		SocialActivity socialActivity = new SocialActivityImpl();

		socialActivity.setActivityId(activityId);
		socialActivity.setGroupId(groupId);
		socialActivity.setCompanyId(companyId);
		socialActivity.setUserId(userId);
		socialActivity.setClassNameId(classNameId);
		socialActivity.setClassPK(classPK);

		return socialActivity;
	}

	private User _newUser(
		String uuid, long userId, long companyId, Date createDate,
		Date modifiedDate, boolean defaultUser, long contactId, String password,
		Date passwordModifiedDate, String reminderQueryQuestion,
		String reminderQueryAnswer, String emailAddress, String screenName,
		String languageId, String greeting, String firstName, String lastName,
		Date loginDate, Date lastLoginDate, Date lastFailedLoginDate,
		Date lockoutDate, boolean agreedToTermsOfUse,
		boolean emailAddressVerified) {

		User user = new UserImpl();

		user.setUuid(uuid);
		user.setUserId(userId);
		user.setCompanyId(companyId);
		user.setCreateDate(createDate);
		user.setModifiedDate(modifiedDate);
		user.setDefaultUser(defaultUser);
		user.setContactId(contactId);
		user.setPassword(password);
		user.setPasswordModifiedDate(passwordModifiedDate);
		user.setReminderQueryQuestion(reminderQueryQuestion);
		user.setReminderQueryAnswer(reminderQueryAnswer);
		user.setEmailAddress(emailAddress);
		user.setScreenName(screenName);
		user.setLanguageId(languageId);
		user.setGreeting(greeting);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setLoginDate(loginDate);
		user.setLastLoginDate(lastLoginDate);
		user.setLastFailedLoginDate(lastFailedLoginDate);
		user.setLockoutDate(lockoutDate);
		user.setAgreedToTermsOfUse(agreedToTermsOfUse);
		user.setEmailAddressVerified(emailAddressVerified);

		return user;
	}

	private VirtualHost _newVirtualHost(
		long virtualHostId, long companyId, String hostname) {

		VirtualHost virtualHost = new VirtualHostImpl();

		virtualHost.setVirtualHostId(virtualHostId);
		virtualHost.setCompanyId(companyId);
		virtualHost.setHostname(hostname);

		return virtualHost;
	}

	private WikiNode _newWikiNode(
		long nodeId, long groupId, long userId, String name,
		String description) {

		WikiNode wikiNode = new WikiNodeImpl();

		wikiNode.setNodeId(nodeId);
		wikiNode.setGroupId(groupId);
		wikiNode.setUserId(userId);
		wikiNode.setName(name);
		wikiNode.setDescription(description);

		return wikiNode;
	}

	private WikiPage _newWikiPage(
		long pageId, long resourcePrimKey, long groupId, long userId,
		long nodeId, String title, double version, String content,
		boolean head) {

		WikiPage wikiPage = new WikiPageImpl();

		wikiPage.setPageId(pageId);
		wikiPage.setResourcePrimKey(resourcePrimKey);
		wikiPage.setGroupId(groupId);
		wikiPage.setUserId(userId);
		wikiPage.setNodeId(nodeId);
		wikiPage.setTitle(title);
		wikiPage.setVersion(version);
		wikiPage.setContent(content);
		wikiPage.setHead(head);

		return wikiPage;
	}

	private static final long _FUTURE_TIME =
		System.currentTimeMillis() + Time.YEAR;

	private Account _account;
	private Role _administratorRole;
	private String _baseDir;
	private Map<String, Long> _classNameMap = new HashMap<String, Long>();
	private List<ClassName> _classNames;
	private Company _company;
	private SimpleCounter _counter;
	private DLFileEntryType _defaultDLFileEntryType;
	private User _defaultUser;
	private List<String> _firstNames;
	private SimpleCounter _futureDateCounter;
	private Group _guestGroup;
	private Role _guestRole;
	private User _guestUser;
	private String _journalArticleContent;
	private List<String> _lastNames;
	private int _maxGroupsCount;
	private int _maxUserToGroupCount;
	private Role _ownerRole;
	private Role _powerUserRole;
	private SimpleCounter _resourcePermissionCounter;
	private List<Role> _roles;
	private User _sampleUser;
	private Format _simpleDateFormat =
		FastDateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleCounter _socialActivityCounter;
	private Role _userRole;
	private SimpleCounter _userScreenNameCounter;
	private VirtualHost _virtualHost;

}