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
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
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
import com.liferay.portal.model.impl.LayoutSetImpl;
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
import java.io.IOException;

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
			String baseDir, int maxBlogsEntryCount, int maxGroupsCount,
			int maxJournalArticleSize, int maxMBCategoryCount,
			int maxMBThreadCount, int maxMBMessageCount,
			int maxUserToGroupCount)
		throws Exception {

		_baseDir = baseDir;
		_maxBlogsEntryCount = maxBlogsEntryCount;
		_maxGroupsCount = maxGroupsCount;
		_maxMBMessageCount =
			maxMBCategoryCount * maxMBThreadCount * maxMBMessageCount;
		_maxUserToGroupCount = maxUserToGroupCount;

		_counter = new SimpleCounter(_maxGroupsCount + 1);
		_futureDateCounter = new SimpleCounter();
		_resourcePermissionCounter = new SimpleCounter();
		_socialActivityCounter = new SimpleCounter();
		_userScreenNameCounter = new SimpleCounter();

		_classNames = new ArrayList<ClassName>();

		List<String> models = ModelHintsUtil.getModels();

		for (String model : models) {
			ClassName className = new ClassNameImpl();

			long classNameId = _counter.get();

			className.setClassNameId(classNameId);

			className.setValue(model);

			_classNames.add(className);

			_classNamesMap.put(model, classNameId);
		}

		_accountId = _counter.get();
		_companyId = _counter.get();
		_sampleUserId = _counter.get();

		initCompany();
		initDLFileEntryType();
		initGroups();
		initJournalArticle(maxJournalArticleSize);
		initRoles();
		initUserNames();
		initUsers();
		initVirtualHost();
	}

	public Account getAccount() {
		return _account;
	}

	public Role getAdministratorRole() {
		return _administratorRole;
	}

	public long getBlogsEntryClassNameId() {
		return _classNamesMap.get(BlogsEntry.class.getName());
	}

	public List<ClassName> getClassNames() {
		return _classNames;
	}

	public Company getCompany() {
		return _company;
	}

	public long getCompanyId() {
		return _companyId;
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
		return _classNamesMap.get(DDLRecordSet.class.getName());
	}

	public long getDDMContentClassNameId() {
		return _classNamesMap.get(DDMContent.class.getName());
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
		return _classNamesMap.get(DLFileEntry.class.getName());
	}

	public long getGroupClassNameId() {
		return _classNamesMap.get(Group.class.getName());
	}

	public List<Group> getGroups() {
		return _groups;
	}

	public Group getGuestGroup() {
		return _guestGroup;
	}

	public User getGuestUser() {
		return _guestUser;
	}

	public long getJournalArticleClassNameId() {
		return _classNamesMap.get(JournalArticle.class.getName());
	}

	public long getMBMessageClassNameId() {
		return _classNamesMap.get(MBMessage.class.getName());
	}

	public List<Long> getNewUserGroupIds(long groupId) {
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
		return _classNamesMap.get(User.class.getName());
	}

	public Role getUserRole() {
		return _userRole;
	}

	public VirtualHost getVirtualHost() {
		return _virtualHost;
	}

	public long getWikiPageClassNameId() {
		return _classNamesMap.get(WikiPage.class.getName());
	}

	public void initCompany() {
		_company = new CompanyImpl();

		_company.setCompanyId(_companyId);
		_company.setAccountId(_accountId);
		_company.setWebId("liferay.com");
		_company.setMx("liferay.com");
		_company.setActive(true);

		_account = new AccountImpl();

		_account.setAccountId(_accountId);
		_account.setCompanyId(_companyId);
		_account.setCreateDate(new Date());
		_account.setModifiedDate(new Date());
		_account.setName("Liferay");
		_account.setLegalName("Liferay, Inc.");
	}

	public void initDLFileEntryType() {
		_defaultDLFileEntryType = new DLFileEntryTypeImpl();

		_defaultDLFileEntryType.setUuid(SequentialUUID.generate());
		_defaultDLFileEntryType.setFileEntryTypeId(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);
		_defaultDLFileEntryType.setCreateDate(nextFutureDate());
		_defaultDLFileEntryType.setModifiedDate(nextFutureDate());
		_defaultDLFileEntryType.setName(
			DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT);
	}

	public void initGroups() throws Exception {
		long groupClassNameId = getGroupClassNameId();

		long guestGroupId = _counter.get();

		_guestGroup = newGroup(
			guestGroupId, groupClassNameId, guestGroupId, GroupConstants.GUEST,
			true);

		_groups = new ArrayList<Group>(_maxGroupsCount);

		for (int i = 1; i <= _maxGroupsCount; i++) {
			Group group = newGroup(i, groupClassNameId, i, "Site " + i, true);

			_groups.add(group);
		}
	}

	public void initJournalArticle(int maxJournalArticleSize) {
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

		// Administrator

		_administratorRole = newRole(
			RoleConstants.ADMINISTRATOR, RoleConstants.TYPE_REGULAR);

		_roles.add(_administratorRole);

		// Guest

		_guestRole = newRole(RoleConstants.GUEST, RoleConstants.TYPE_REGULAR);

		_roles.add(_guestRole);

		// Organization Administrator

		Role organizationAdministratorRole = newRole(
			RoleConstants.ORGANIZATION_ADMINISTRATOR,
			RoleConstants.TYPE_ORGANIZATION);

		_roles.add(organizationAdministratorRole);

		// Organization Owner

		Role organizationOwnerRole = newRole(
			RoleConstants.ORGANIZATION_OWNER, RoleConstants.TYPE_ORGANIZATION);

		_roles.add(organizationOwnerRole);

		// Organization User

		Role organizationUserRole = newRole(
			RoleConstants.ORGANIZATION_USER, RoleConstants.TYPE_ORGANIZATION);

		_roles.add(organizationUserRole);

		// Owner

		_ownerRole = newRole(RoleConstants.OWNER, RoleConstants.TYPE_REGULAR);

		_roles.add(_ownerRole);

		// Power User

		_powerUserRole = newRole(
			RoleConstants.POWER_USER, RoleConstants.TYPE_REGULAR);

		_roles.add(_powerUserRole);

		// Site Administrator

		Role siteAdministratorRole = newRole(
			RoleConstants.SITE_ADMINISTRATOR, RoleConstants.TYPE_SITE);

		_roles.add(siteAdministratorRole);

		// Site Member

		Role siteMemberRole = newRole(
			RoleConstants.SITE_MEMBER, RoleConstants.TYPE_SITE);

		_roles.add(siteMemberRole);

		// Site Owner

		Role siteOwnerRole = newRole(
			RoleConstants.SITE_OWNER, RoleConstants.TYPE_SITE);

		_roles.add(siteOwnerRole);

		// User

		_userRole = newRole(RoleConstants.USER, RoleConstants.TYPE_REGULAR);

		_roles.add(_userRole);
	}

	public void initUserNames() throws IOException {
		String dependenciesDir =
			"../portal-impl/src/com/liferay/portal/tools/samplesqlbuilder/" +
				"dependencies/";

		_firstNames = ListUtil.fromFile(
			new File(_baseDir, dependenciesDir + "first_names.txt"));
		_lastNames = ListUtil.fromFile(
			new File(_baseDir, dependenciesDir + "last_names.txt"));
	}

	public void initUsers() {
		_defaultUser = newUser(
			_counter.get(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, true);
		_guestUser = newUser(_counter.get(), "Test", "Test", "Test", false);
		_sampleUser = newUser(
			_sampleUserId, "Sample", "Sample", "Sample", false);
	}

	public void initVirtualHost() {
		_virtualHost = new VirtualHostImpl();

		_virtualHost.setVirtualHostId(_counter.get());
		_virtualHost.setCompanyId(_companyId);
		_virtualHost.setHostname("localhost");
	}

	public AssetEntry newAssetEntry(
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

	public BlogsEntry newBlogsEntry(
		long groupId, long userId, String title, String urlTitle,
		String content) {

		BlogsEntry blogsEntry = new BlogsEntryImpl();

		blogsEntry.setEntryId(_counter.get());
		blogsEntry.setGroupId(groupId);
		blogsEntry.setUserId(userId);
		blogsEntry.setTitle(title);
		blogsEntry.setUrlTitle(urlTitle);
		blogsEntry.setContent(content);

		return blogsEntry;
	}

	public BlogsStatsUser newBlogsStatsUser(long groupId) {
		BlogsStatsUser blogsStatsUser = new BlogsStatsUserImpl();

		blogsStatsUser.setStatsUserId(_counter.get());
		blogsStatsUser.setGroupId(groupId);
		blogsStatsUser.setCompanyId(_companyId);
		blogsStatsUser.setUserId(_sampleUserId);
		blogsStatsUser.setEntryCount(_maxBlogsEntryCount);
		blogsStatsUser.setLastPostDate(new Date());

		return blogsStatsUser;
	}

	public Contact newContact(User user) {
		Contact contact = new ContactImpl();

		contact.setContactId(user.getContactId());
		contact.setCompanyId(user.getCompanyId());
		contact.setUserId(user.getUserId());
		contact.setUserName(user.getFullName());
		contact.setCreateDate(new Date());
		contact.setModifiedDate(new Date());
		contact.setClassNameId(getUserClassNameId());
		contact.setClassPK(user.getUserId());
		contact.setAccountId(_accountId);
		contact.setParentContactId(ContactConstants.DEFAULT_PARENT_CONTACT_ID);
		contact.setEmailAddress(user.getEmailAddress());
		contact.setFirstName(user.getFirstName());
		contact.setLastName(user.getLastName());
		contact.setMale(true);
		contact.setBirthday(new Date());

		return contact;
	}

	public List<Counter> newCounters() {
		List<Counter> counters = new ArrayList<Counter>();

		// Counter

		Counter counter = new CounterImpl();

		counter.setName(Counter.class.getName());
		counter.setCurrentId(_counter.get());

		counters.add(counter);

		// ResourcePermission

		counter = new CounterImpl();

		counter.setName(ResourcePermission.class.getName());
		counter.setCurrentId(_resourcePermissionCounter.get());

		counters.add(counter);

		// SocialActivity

		counter = new CounterImpl();

		counter.setName(SocialActivity.class.getName());
		counter.setCurrentId(_socialActivityCounter.get());

		counters.add(counter);

		return counters;
	}

	public DDLRecord newDDLRecord(
		long groupId, long companyId, long userId, long ddlRecordSetId) {

		DDLRecord ddlRecord = new DDLRecordImpl();

		ddlRecord.setRecordId(_counter.get());
		ddlRecord.setGroupId(groupId);
		ddlRecord.setCompanyId(companyId);
		ddlRecord.setUserId(userId);
		ddlRecord.setCreateDate(nextFutureDate());
		ddlRecord.setRecordSetId(ddlRecordSetId);

		return ddlRecord;
	}

	public DDLRecordSet newDDLRecordSet(
		long groupId, long companyId, long userId, long ddmStructureId) {

		DDLRecordSet ddlRecordSet = new DDLRecordSetImpl();

		ddlRecordSet.setRecordSetId(_counter.get());
		ddlRecordSet.setGroupId(groupId);
		ddlRecordSet.setCompanyId(companyId);
		ddlRecordSet.setUserId(userId);
		ddlRecordSet.setDDMStructureId(ddmStructureId);

		return ddlRecordSet;
	}

	public DDLRecordVersion newDDLRecordVersion(DDLRecord ddlRecord) {
		DDLRecordVersion ddlRecordVersion = new DDLRecordVersionImpl();

		ddlRecordVersion.setRecordVersionId(_counter.get());
		ddlRecordVersion.setGroupId(ddlRecord.getGroupId());
		ddlRecordVersion.setCompanyId(ddlRecord.getCompanyId());
		ddlRecordVersion.setUserId(ddlRecord.getUserId());
		ddlRecordVersion.setRecordSetId(ddlRecord.getRecordSetId());
		ddlRecordVersion.setRecordId(ddlRecord.getRecordId());

		return ddlRecordVersion;
	}

	public DDMContent newDDMContent(long groupId, long companyId, long userId) {
		DDMContent ddmContent = new DDMContentImpl();

		ddmContent.setContentId(_counter.get());
		ddmContent.setGroupId(groupId);
		ddmContent.setCompanyId(companyId);
		ddmContent.setUserId(userId);

		return ddmContent;
	}

	public DDMStorageLink newDDMStorageLink(
		long classNameId, long classPK, long structureId) {

		DDMStorageLink ddmStorageLink = new DDMStorageLinkImpl();

		ddmStorageLink.setStorageLinkId(_counter.get());
		ddmStorageLink.setClassNameId(classNameId);
		ddmStorageLink.setClassPK(classPK);
		ddmStorageLink.setStructureId(structureId);

		return ddmStorageLink;
	}

	public DDMStructure newDDMStructure(
		long groupId, long companyId, long userId, long classNameId) {

		DDMStructure ddmStructure = new DDMStructureImpl();

		ddmStructure.setStructureId(_counter.get());
		ddmStructure.setGroupId(groupId);
		ddmStructure.setCompanyId(companyId);
		ddmStructure.setUserId(userId);
		ddmStructure.setCreateDate(nextFutureDate());
		ddmStructure.setClassNameId(classNameId);

		return ddmStructure;
	}

	public DDMStructureLink newDDMStructureLink(
		long classPK, long structureId) {

		DDMStructureLink ddmStructureLink = new DDMStructureLinkImpl();

		ddmStructureLink.setStructureLinkId(_counter.get());
		ddmStructureLink.setClassNameId(getDLFileEntryClassNameId());
		ddmStructureLink.setClassPK(classPK);
		ddmStructureLink.setStructureId(structureId);

		return ddmStructureLink;
	}

	public DLFileEntry newDlFileEntry(
		long groupId, long companyId, long userId, long folderId,
		String extension, String mimeType, String name, String title,
		String description) {

		DLFileEntry dlFileEntry = new DLFileEntryImpl();

		dlFileEntry.setFileEntryId(_counter.get());
		dlFileEntry.setGroupId(groupId);
		dlFileEntry.setCompanyId(companyId);
		dlFileEntry.setUserId(userId);
		dlFileEntry.setCreateDate(nextFutureDate());
		dlFileEntry.setRepositoryId(groupId);
		dlFileEntry.setFolderId(folderId);
		dlFileEntry.setName(name);
		dlFileEntry.setExtension(extension);
		dlFileEntry.setMimeType(mimeType);
		dlFileEntry.setTitle(title);
		dlFileEntry.setDescription(description);
		dlFileEntry.setSmallImageId(_counter.get());
		dlFileEntry.setLargeImageId(_counter.get());

		return dlFileEntry;
	}

	public DLFileEntryMetadata newDLFileEntryMetadata(
		long ddmStorageId, long ddmStructureId, long fileEntryId,
		long fileVersionId) {

		DLFileEntryMetadata dlFileEntryMetadata = new DLFileEntryMetadataImpl();

		dlFileEntryMetadata.setFileEntryMetadataId(_counter.get());
		dlFileEntryMetadata.setDDMStorageId(ddmStorageId);
		dlFileEntryMetadata.setDDMStructureId(ddmStructureId);
		dlFileEntryMetadata.setFileEntryId(fileEntryId);
		dlFileEntryMetadata.setFileVersionId(fileVersionId);

		return dlFileEntryMetadata;
	}

	public DLFileVersion newDLFileVersion(DLFileEntry dlFileEntry) {
		DLFileVersion dlFileVersion = new DLFileVersionImpl();

		dlFileVersion.setFileVersionId(_counter.get());
		dlFileVersion.setGroupId(dlFileEntry.getGroupId());
		dlFileVersion.setCompanyId(dlFileEntry.getCompanyId());
		dlFileVersion.setUserId(dlFileEntry.getUserId());
		dlFileVersion.setRepositoryId(dlFileEntry.getRepositoryId());
		dlFileVersion.setFileEntryId(dlFileEntry.getFileEntryId());
		dlFileVersion.setExtension(dlFileEntry.getExtension());
		dlFileVersion.setMimeType(dlFileEntry.getMimeType());
		dlFileVersion.setTitle(dlFileEntry.getTitle());
		dlFileVersion.setDescription(dlFileEntry.getDescription());
		dlFileVersion.setSize(dlFileEntry.getSize());

		return dlFileVersion;
	}

	public DLFolder newDLFolder(
		long groupId, long companyId, long userId, long parentFolderId,
		String name, String description) {

		DLFolder dlFolder = new DLFolderImpl();

		dlFolder.setFolderId(_counter.get());
		dlFolder.setGroupId(groupId);
		dlFolder.setCompanyId(companyId);
		dlFolder.setUserId(userId);
		dlFolder.setCreateDate(nextFutureDate());
		dlFolder.setRepositoryId(groupId);
		dlFolder.setParentFolderId(parentFolderId);
		dlFolder.setName(name);
		dlFolder.setDescription(description);

		return dlFolder;
	}

	public DLSync newDLSync(
		long companyId, long fileId, long repositoryId, long parentFolderId,
		boolean typeFolder) {

		DLSync dlSync = new DLSyncImpl();

		dlSync.setSyncId(_counter.get());
		dlSync.setCompanyId(companyId);
		dlSync.setFileId(fileId);
		dlSync.setRepositoryId(repositoryId);
		dlSync.setParentFolderId(parentFolderId);
		dlSync.setEvent(DLSyncConstants.EVENT_ADD);

		if (typeFolder) {
			dlSync.setType(DLSyncConstants.TYPE_FOLDER);
		}
		else {
			dlSync.setType(DLSyncConstants.TYPE_FILE);
		}

		return dlSync;
	}

	public Group newGroup(User user) throws Exception {
		return newGroup(
			_counter.get(), getUserClassNameId(), user.getUserId(),
			user.getScreenName(), false);
	}

	public IntegerWrapper newInteger() {
		return new IntegerWrapper();
	}

	public JournalArticle newJournalArticle(
		long resourcePrimKey, long groupId, long companyId, String articleId) {

		JournalArticle journalArticle = new JournalArticleImpl();

		journalArticle.setId(_counter.get());
		journalArticle.setResourcePrimKey(resourcePrimKey);
		journalArticle.setGroupId(groupId);
		journalArticle.setCompanyId(companyId);
		journalArticle.setArticleId(articleId);
		journalArticle.setContent(_journalArticleContent);

		return journalArticle;
	}

	public JournalArticleResource newJournalArticleResource(long groupId) {
		JournalArticleResource journalArticleResource =
			new JournalArticleResourceImpl();

		journalArticleResource.setResourcePrimKey(_counter.get());
		journalArticleResource.setGroupId(groupId);
		journalArticleResource.setArticleId(String.valueOf(_counter.get()));

		return journalArticleResource;
	}

	public Layout newLayout(
		long groupId, String name, String column1, String column2) {

		SimpleCounter simpleCounter = _layoutCounters.get(groupId);

		if (simpleCounter == null) {
			simpleCounter = new SimpleCounter();

			_layoutCounters.put(groupId, simpleCounter);
		}

		Layout layout = new LayoutImpl();

		layout.setUuid(SequentialUUID.generate());
		layout.setPlid(_counter.get());
		layout.setGroupId(groupId);
		layout.setCompanyId(_companyId);
		layout.setCreateDate(new Date());
		layout.setModifiedDate(new Date());
		layout.setLayoutId(simpleCounter.get());
		layout.setName(
			"<?xml version=\"1.0\"?><root><name>" + name + "</name></root>");
		layout.setType(LayoutConstants.TYPE_PORTLET);
		layout.setFriendlyURL(StringPool.FORWARD_SLASH + name);

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.setProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "2_columns_ii");
		typeSettingsProperties.setProperty("column-1", column1);
		typeSettingsProperties.setProperty("column-2", column2);

		String typeSettings = StringUtil.replace(
			typeSettingsProperties.toString(), "\n", "\\n");

		layout.setTypeSettings(typeSettings);

		return layout;
	}

	public List<LayoutSet> newLayoutSets(
		long groupId, int publicLayoutSetPageCount) {

		List<LayoutSet> layoutSets = new ArrayList<LayoutSet>(2);

		layoutSets.add(newLayoutSet(groupId, true, 0));
		layoutSets.add(newLayoutSet(groupId, false, publicLayoutSetPageCount));

		return layoutSets;
	}

	public MBCategory newMBCategory(
		long categoryId, long groupId, long companyId, long userId, String name,
		String description, int threadCount, int messageCount) {

		MBCategory mbCategory = new MBCategoryImpl();

		mbCategory.setCategoryId(categoryId);
		mbCategory.setGroupId(groupId);
		mbCategory.setCompanyId(companyId);
		mbCategory.setUserId(userId);
		mbCategory.setName(name);
		mbCategory.setDescription(description);
		mbCategory.setDisplayStyle(MBCategoryConstants.DEFAULT_DISPLAY_STYLE);
		mbCategory.setThreadCount(threadCount);
		mbCategory.setMessageCount(messageCount);

		return mbCategory;
	}

	public MBDiscussion newMBDiscussion(
		long classNameId, long classPK, long threadId) {

		MBDiscussion mbDiscussion = new MBDiscussionImpl();

		mbDiscussion.setDiscussionId(_counter.get());
		mbDiscussion.setClassNameId(classNameId);
		mbDiscussion.setClassPK(classPK);
		mbDiscussion.setThreadId(threadId);

		return mbDiscussion;
	}

	public MBMessage newMBMessage(
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

	public MBStatsUser newMBStatsUser(long groupId) {
		MBStatsUser mbStatsUser = new MBStatsUserImpl();

		mbStatsUser.setStatsUserId(_counter.get());
		mbStatsUser.setGroupId(groupId);
		mbStatsUser.setUserId(_sampleUserId);
		mbStatsUser.setMessageCount(_maxMBMessageCount);
		mbStatsUser.setLastPostDate(new Date());

		return mbStatsUser;
	}

	public MBThread newMBThread(
		long threadId, long groupId, long companyId, long categoryId,
		long rootMessageId, int messageCount, long lastPostByUserId) {

		MBThread mbThread = new MBThreadImpl();

		mbThread.setUuid(SequentialUUID.generate());
		mbThread.setThreadId(threadId);
		mbThread.setGroupId(groupId);
		mbThread.setCompanyId(companyId);
		mbThread.setUserId(_sampleUserId);
		mbThread.setUserName(_sampleUser.getFullName());
		mbThread.setCreateDate(new Date());
		mbThread.setModifiedDate(new Date());
		mbThread.setCategoryId(categoryId);
		mbThread.setRootMessageId(rootMessageId);
		mbThread.setRootMessageUserId(lastPostByUserId);
		mbThread.setMessageCount(messageCount);
		mbThread.setLastPostByUserId(lastPostByUserId);

		return mbThread;
	}

	public PortletPreferences newPortletPreferences(
		long ownerId, long plid, String portletId, String preferences) {

		PortletPreferences portletPreferences = new PortletPreferencesImpl();

		portletPreferences.setPortletPreferencesId(_counter.get());
		portletPreferences.setOwnerId(ownerId);
		portletPreferences.setOwnerType(PortletKeys.PREFS_OWNER_TYPE_LAYOUT);
		portletPreferences.setPlid(plid);
		portletPreferences.setPortletId(portletId);
		portletPreferences.setPreferences(preferences);

		return portletPreferences;
	}

	public List<Layout> newPublicLayouts(long groupId) {
		List<Layout> layouts = new ArrayList<Layout>();

		layouts.add(newLayout(groupId, "welcome", "58,", "47,"));
		layouts.add(newLayout(groupId, "blogs", "", "33,"));
		layouts.add(newLayout(groupId, "document_library", "", "20,"));
		layouts.add(newLayout(groupId, "forums", "", "19,"));
		layouts.add(newLayout(groupId, "wiki", "", "36,"));

		return layouts;
	}

	public List<ResourcePermission> newResourcePermission(
		long companyId, String name, String primKey) {

		List<ResourcePermission> resourcePermissions =
			new ArrayList<ResourcePermission>(2);

		ResourcePermission resourcePermission = new ResourcePermissionImpl();

		resourcePermission.setResourcePermissionId(
			_resourcePermissionCounter.get());
		resourcePermission.setCompanyId(companyId);
		resourcePermission.setName(name);
		resourcePermission.setScope(ResourceConstants.SCOPE_INDIVIDUAL);
		resourcePermission.setPrimKey(primKey);
		resourcePermission.setRoleId(_ownerRole.getRoleId());
		resourcePermission.setOwnerId(_defaultUser.getUserId());
		resourcePermission.setActionIds(1);

		resourcePermissions.add(resourcePermission);

		resourcePermission = new ResourcePermissionImpl();

		resourcePermission.setResourcePermissionId(
			_resourcePermissionCounter.get());
		resourcePermission.setCompanyId(companyId);
		resourcePermission.setName(name);
		resourcePermission.setScope(ResourceConstants.SCOPE_INDIVIDUAL);
		resourcePermission.setPrimKey(primKey);
		resourcePermission.setRoleId(_guestRole.getRoleId());
		resourcePermission.setOwnerId(0);
		resourcePermission.setActionIds(1);

		resourcePermissions.add(resourcePermission);

		return resourcePermissions;
	}

	public SocialActivity newSocialActivity(
		long groupId, long companyId, long userId, long classNameId,
		long classPK) {

		SocialActivity socialActivity = new SocialActivityImpl();

		socialActivity.setActivityId(_socialActivityCounter.get());
		socialActivity.setGroupId(groupId);
		socialActivity.setCompanyId(companyId);
		socialActivity.setUserId(userId);
		socialActivity.setClassNameId(classNameId);
		socialActivity.setClassPK(classPK);

		return socialActivity;
	}

	public User newUser(int currentIndex) {
		String[] userName = nextUserName(currentIndex - 1);

		return newUser(
			_counter.get(), userName[0], userName[1],
			"test" + _userScreenNameCounter.get(), false);
	}

	public WikiNode newWikiNode(
		long groupId, long userId, String name, String description) {

		WikiNode wikiNode = new WikiNodeImpl();

		wikiNode.setNodeId(_counter.get());
		wikiNode.setGroupId(groupId);
		wikiNode.setUserId(userId);
		wikiNode.setName(name);
		wikiNode.setDescription(description);

		return wikiNode;
	}

	public WikiPage newWikiPage(
		long groupId, long userId, long nodeId, String title, double version,
		String content, boolean head) {

		WikiPage wikiPage = new WikiPageImpl();

		wikiPage.setPageId(_counter.get());
		wikiPage.setResourcePrimKey(_counter.get());
		wikiPage.setGroupId(groupId);
		wikiPage.setUserId(userId);
		wikiPage.setNodeId(nodeId);
		wikiPage.setTitle(title);
		wikiPage.setVersion(version);
		wikiPage.setContent(content);
		wikiPage.setHead(head);

		return wikiPage;
	}

	public String[] nextUserName(long index) {
		String[] userName = new String[2];

		userName[0] = _firstNames.get(
			(int)(index / _lastNames.size()) % _firstNames.size());
		userName[1] = _lastNames.get((int)(index % _lastNames.size()));

		return userName;
	}

	protected Group newGroup(
			long groupId, long classNameId, long classPK, String name,
			boolean site)
		throws Exception {

		Group group = new GroupImpl();

		group.setGroupId(groupId);
		group.setCompanyId(_companyId);
		group.setCreatorUserId(_sampleUserId);
		group.setClassNameId(classNameId);
		group.setClassPK(classPK);
		group.setTreePath(group.buildTreePath());
		group.setName(name);
		group.setFriendlyURL(
			StringPool.FORWARD_SLASH +
				FriendlyURLNormalizerUtil.normalize(name));
		group.setSite(site);
		group.setActive(true);

		return group;
	}

	protected LayoutSet newLayoutSet(
		long groupId, boolean privateLayout, int pageCount) {

		LayoutSet layoutSet = new LayoutSetImpl();

		layoutSet.setLayoutSetId(_counter.get());
		layoutSet.setGroupId(groupId);
		layoutSet.setCompanyId(_companyId);
		layoutSet.setCreateDate(new Date());
		layoutSet.setModifiedDate(new Date());
		layoutSet.setPrivateLayout(privateLayout);
		layoutSet.setThemeId("classic");
		layoutSet.setColorSchemeId("01");
		layoutSet.setWapThemeId("mobile");
		layoutSet.setWapColorSchemeId("01");
		layoutSet.setPageCount(pageCount);

		return layoutSet;
	}

	protected Role newRole(String name, int type) {
		Role role = new RoleImpl();

		role.setRoleId(_counter.get());
		role.setCompanyId(_companyId);
		role.setClassNameId(_classNamesMap.get(Role.class.getName()));
		role.setClassPK(role.getRoleId());
		role.setName(name);
		role.setType(type);

		return role;
	}

	protected User newUser(
		long userId, String firstName, String lastName, String screenName,
		boolean defaultUser) {

		if (Validator.isNull(screenName)) {
			screenName = String.valueOf(userId);
		}

		User user = new UserImpl();

		user.setUuid(SequentialUUID.generate());
		user.setUserId(userId);
		user.setCompanyId(_companyId);
		user.setCreateDate(new Date());
		user.setModifiedDate(new Date());
		user.setDefaultUser(defaultUser);
		user.setContactId(_counter.get());
		user.setPassword("test");
		user.setPasswordModifiedDate(new Date());
		user.setReminderQueryQuestion("What is your screen name?");
		user.setReminderQueryAnswer(screenName);
		user.setEmailAddress(screenName + "@liferay.com");
		user.setScreenName(screenName);
		user.setLanguageId("en_US");
		user.setGreeting("Welcome " + screenName + StringPool.EXCLAMATION);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setLoginDate(new Date());
		user.setLastLoginDate(new Date());
		user.setLastFailedLoginDate(new Date());
		user.setLockoutDate(new Date());
		user.setAgreedToTermsOfUse(true);
		user.setEmailAddressVerified(true);

		return user;
	}

	protected Date nextFutureDate() {
		return new Date(
			_FUTURE_TIME + (_futureDateCounter.get() * Time.SECOND));
	}

	private static final long _FUTURE_TIME =
		System.currentTimeMillis() + Time.YEAR;

	private Account _account;
	private long _accountId;
	private Role _administratorRole;
	private String _baseDir;
	private List<ClassName> _classNames;
	private Map<String, Long> _classNamesMap = new HashMap<String, Long>();
	private Company _company;
	private long _companyId;
	private SimpleCounter _counter;
	private DLFileEntryType _defaultDLFileEntryType;
	private User _defaultUser;
	private List<String> _firstNames;
	private SimpleCounter _futureDateCounter;
	private List<Group> _groups;
	private Group _guestGroup;
	private Role _guestRole;
	private User _guestUser;
	private String _journalArticleContent;
	private List<String> _lastNames;
	private Map<Long, SimpleCounter> _layoutCounters =
		new HashMap<Long, SimpleCounter>();
	private int _maxBlogsEntryCount;
	private int _maxGroupsCount;
	private int _maxMBMessageCount;
	private int _maxUserToGroupCount;
	private Role _ownerRole;
	private Role _powerUserRole;
	private SimpleCounter _resourcePermissionCounter;
	private List<Role> _roles;
	private User _sampleUser;
	private long _sampleUserId;
	private Format _simpleDateFormat =
		FastDateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleCounter _socialActivityCounter;
	private Role _userRole;
	private SimpleCounter _userScreenNameCounter;
	private VirtualHost _virtualHost;

}