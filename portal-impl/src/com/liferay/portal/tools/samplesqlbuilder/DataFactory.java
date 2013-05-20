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

package com.liferay.portal.tools.samplesqlbuilder;

import com.liferay.counter.model.Counter;
import com.liferay.counter.model.impl.CounterImpl;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
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
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.SubscriptionConstants;
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
import com.liferay.portal.model.impl.SubscriptionImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.model.impl.VirtualHostImpl;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.model.impl.AssetCategoryImpl;
import com.liferay.portlet.asset.model.impl.AssetEntryImpl;
import com.liferay.portlet.asset.model.impl.AssetVocabularyImpl;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.blogs.model.impl.BlogsEntryImpl;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserImpl;
import com.liferay.portlet.blogs.social.BlogsActivityKeys;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordConstants;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSetConstants;
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
import com.liferay.portlet.dynamicdatamapping.util.DDMImpl;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleResourceImpl;
import com.liferay.portlet.journal.model.impl.JournalContentSearchImpl;
import com.liferay.portlet.journal.social.JournalActivityKeys;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.model.impl.MBDiscussionImpl;
import com.liferay.portlet.messageboards.model.impl.MBMailingListImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.portlet.messageboards.model.impl.MBStatsUserImpl;
import com.liferay.portlet.messageboards.model.impl.MBThreadFlagImpl;
import com.liferay.portlet.messageboards.model.impl.MBThreadImpl;
import com.liferay.portlet.messageboards.social.MBActivityKeys;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.model.impl.SocialActivityImpl;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.model.impl.WikiNodeImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageResourceImpl;
import com.liferay.portlet.wiki.social.WikiActivityKeys;
import com.liferay.util.PwdGenerator;
import com.liferay.util.SimpleCounter;

import java.io.File;
import java.io.FileInputStream;
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
			String baseDir, int maxAssetCategoryCount, int maxBlogsEntryCount,
			int maxDDLCustomFieldCount, int maxGroupsCount,
			int maxJournalArticleCount, int maxJournalArticleSize,
			int maxMBCategoryCount, int maxMBThreadCount, int maxMBMessageCount,
			int maxUserToGroupCount)
		throws Exception {

		_baseDir = baseDir;
		_maxAssetCategoryCount = maxAssetCategoryCount;
		_maxBlogsEntryCount = maxBlogsEntryCount;
		_maxDDLCustomFieldCount = maxDDLCustomFieldCount;
		_maxGroupsCount = maxGroupsCount;
		_maxJournalArticleCount = maxJournalArticleCount;
		_maxMBCategoryCount = maxMBCategoryCount;
		_maxMBThreadCount = maxMBThreadCount;
		_maxMBMessageCount = maxMBMessageCount;
		_maxUserToGroupCount = maxUserToGroupCount;

		_counter = new SimpleCounter(_maxGroupsCount + 1);
		_timeCounter = new SimpleCounter();
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
		_defaultUserId = _counter.get();
		_globalGroupId = _counter.get();
		_guestGroupId = _counter.get();
		_sampleUserId = _counter.get();

		_dlDDMStructureContent = StringUtil.read(
			new FileInputStream(
				new File(
					_baseDir,
					_DEPENDENCIES_DIR + "ddm_structure_basic_document.xml")));

		initAssetCateogries();
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

	public List<AssetCategory> getAssetCategories() {
		return _assetCategories;
	}

	public long getAssetCategoryId(long groupId, int currentIndex) {
		int index = (currentIndex - 1) % _maxAssetCategoryCount;

		index = _maxAssetCategoryCount * ((int)groupId - 1) + index;

		AssetCategory assetCategory = _assetCategories.get(index);

		return assetCategory.getCategoryId();
	}

	public List<AssetVocabulary> getAssetVocabularies() {
		return _assetVocabularies;
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

	public DDMStructure getDefaultDLDDMStructure() {
		return _defaultDLDDMStructure;
	}

	public long getDefaultDLDDMStructureId() {
		return _defaultDLDDMStructure.getStructureId();
	}

	public DLFileEntryType getDefaultDLFileEntryType() {
		return _defaultDLFileEntryType;
	}

	public User getDefaultUser() {
		return _defaultUser;
	}

	public long getDLFileEntryClassNameId() {
		return _classNamesMap.get(DLFileEntry.class.getName());
	}

	public Group getGlobalGroup() {
		return _globalGroup;
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

	public String getJournalArticleLayoutColumn(String portletPrefix) {
		StringBundler sb = new StringBundler(3 * _maxJournalArticleCount);

		for (int i = 1; i <= _maxJournalArticleCount; i++) {
			sb.append(portletPrefix);
			sb.append(i);
			sb.append(StringPool.COMMA);
		}

		return sb.toString();
	}

	public long getLayoutClassNameId() {
		return _classNamesMap.get(Layout.class.getName());
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

	public Role getUserRole() {
		return _userRole;
	}

	public VirtualHost getVirtualHost() {
		return _virtualHost;
	}

	public long getWikiPageClassNameId() {
		return _classNamesMap.get(WikiPage.class.getName());
	}

	public void initAssetCateogries() {
		_assetVocabularies = new ArrayList<AssetVocabulary>();
		_assetCategories = new ArrayList<AssetCategory>();

		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root ");
		sb.append("available-locales=\"en_US\" default-locale=\"en_US\">");
		sb.append("<Title language-id=\"en_US\">");
		sb.append(PropsValues.ASSET_VOCABULARY_DEFAULT);
		sb.append("</Title></root>");

		String defaultTitle = sb.toString();

		_assetVocabularies.add(
			newAssetVocabulary(
				_globalGroupId, _defaultUserId, null,
				PropsValues.ASSET_VOCABULARY_DEFAULT, defaultTitle));

		for (int i = 1; i <= _maxGroupsCount; i++) {
			long lastRightCategoryId = 2;

			for (int j = 0; j < _maxAssetCategoryCount; j++) {
				sb = new StringBundler(4);

				sb.append("Test_");
				sb.append(i);
				sb.append(StringPool.UNDERLINE);
				sb.append(j);

				String name = sb.toString();

				sb = new StringBundler(5);

				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root ");
				sb.append(
					"available-locales=\"en_US\" default-locale=\"en_US\">");
				sb.append("<Title language-id=\"en_US\">");
				sb.append(name);
				sb.append("</Title></root>");

				String title = sb.toString();

				AssetVocabulary assetVocabulary = newAssetVocabulary(
					i, _sampleUserId, _SAMPLE_USER_NAME, name, title);

				_assetVocabularies.add(assetVocabulary);

				AssetCategory assetCategory = new AssetCategoryImpl();

				assetCategory.setUuid(SequentialUUID.generate());
				assetCategory.setCategoryId(_counter.get());
				assetCategory.setGroupId(i);
				assetCategory.setCompanyId(_companyId);
				assetCategory.setUserId(_sampleUserId);
				assetCategory.setUserName(_SAMPLE_USER_NAME);
				assetCategory.setCreateDate(new Date());
				assetCategory.setModifiedDate(new Date());
				assetCategory.setParentCategoryId(
					AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
				assetCategory.setLeftCategoryId(lastRightCategoryId++);
				assetCategory.setRightCategoryId(lastRightCategoryId++);
				assetCategory.setName(name);
				assetCategory.setTitle(title);
				assetCategory.setVocabularyId(
					assetVocabulary.getVocabularyId());

				_assetCategories.add(assetCategory);
			}
		}
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

		_defaultDLDDMStructure = newDDMStructure(
			_guestGroupId, getDLFileEntryClassNameId(), "TIKARAWMETADATA",
			_dlDDMStructureContent);
	}

	public void initGroups() throws Exception {
		long groupClassNameId = getGroupClassNameId();

		_globalGroup = newGroup(
			_globalGroupId, _classNamesMap.get(Company.class.getName()),
			_companyId, GroupConstants.GLOBAL, false);

		_guestGroup = newGroup(
			_guestGroupId, groupClassNameId, _guestGroupId,
			GroupConstants.GUEST, true);

		_groups = new ArrayList<Group>(_maxGroupsCount);

		for (int i = 1; i <= _maxGroupsCount; i++) {
			Group group = newGroup(i, groupClassNameId, i, "Site " + i, true);

			_groups.add(group);
		}
	}

	public void initJournalArticle(int maxJournalArticleSize) {
		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\"?><root available-locales=\"en_US\" ");
		sb.append("default-locale=\"en_US\"><static-content language-id=");
		sb.append("\"en_US\"><![CDATA[<p>");

		if (maxJournalArticleSize <= 0) {
			maxJournalArticleSize = 1;
		}

		char[] chars = new char[maxJournalArticleSize];

		for (int i = 0; i < maxJournalArticleSize; i++) {
			chars[i] = (char)(CharPool.LOWER_CASE_A + (i % 26));
		}

		sb.append(new String(chars));

		sb.append("</p>]]></static-content></root>");

		_journalArticleContent = sb.toString();
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

		_siteMemberRole = newRole(
			RoleConstants.SITE_MEMBER, RoleConstants.TYPE_SITE);

		_roles.add(_siteMemberRole);

		// Site Owner

		Role siteOwnerRole = newRole(
			RoleConstants.SITE_OWNER, RoleConstants.TYPE_SITE);

		_roles.add(siteOwnerRole);

		// User

		_userRole = newRole(RoleConstants.USER, RoleConstants.TYPE_REGULAR);

		_roles.add(_userRole);
	}

	public void initUserNames() throws IOException {
		_firstNames = ListUtil.fromFile(
			new File(_baseDir, _DEPENDENCIES_DIR + "first_names.txt"));
		_lastNames = ListUtil.fromFile(
			new File(_baseDir, _DEPENDENCIES_DIR + "last_names.txt"));
	}

	public void initUsers() {
		_defaultUser = newUser(
			_defaultUserId, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, true);
		_guestUser = newUser(_counter.get(), "Test", "Test", "Test", false);
		_sampleUser = newUser(
			_sampleUserId, _SAMPLE_USER_NAME, _SAMPLE_USER_NAME,
			_SAMPLE_USER_NAME, false);
	}

	public void initVirtualHost() {
		_virtualHost = new VirtualHostImpl();

		_virtualHost.setVirtualHostId(_counter.get());
		_virtualHost.setCompanyId(_companyId);
		_virtualHost.setHostname("localhost");
	}

	public AssetEntry newAssetEntry(BlogsEntry blogsEntry) {
		return newAssetEntry(
			blogsEntry.getGroupId(), blogsEntry.getCreateDate(),
			blogsEntry.getModifiedDate(), getBlogsEntryClassNameId(),
			blogsEntry.getEntryId(), blogsEntry.getUuid(), 0, true,
			ContentTypes.TEXT_HTML, blogsEntry.getTitle());
	}

	public AssetEntry newAssetEntry(DLFileEntry dlFileEntry) {
		return newAssetEntry(
			dlFileEntry.getGroupId(), dlFileEntry.getCreateDate(),
			dlFileEntry.getModifiedDate(), getDLFileEntryClassNameId(),
			dlFileEntry.getFileEntryId(), dlFileEntry.getUuid(),
			dlFileEntry.getFileEntryTypeId(), true, dlFileEntry.getMimeType(),
			dlFileEntry.getTitle());
	}

	public AssetEntry newAssetEntry(DLFolder dlFolder) {
		return newAssetEntry(
			dlFolder.getGroupId(), dlFolder.getCreateDate(),
			dlFolder.getModifiedDate(),
			_classNamesMap.get(DLFolder.class.getName()),
			dlFolder.getFolderId(), dlFolder.getUuid(), 0, true, null,
			dlFolder.getName());
	}

	public AssetEntry newAssetEntry(JournalArticle journalArticle) {
		return newAssetEntry(
			journalArticle.getGroupId(), journalArticle.getCreateDate(),
			journalArticle.getModifiedDate(), getJournalArticleClassNameId(),
			journalArticle.getResourcePrimKey(), journalArticle.getUuid(), 0,
			true, ContentTypes.TEXT_HTML, journalArticle.getTitle());
	}

	public AssetEntry newAssetEntry(MBMessage mbMessage) {
		long classNameId = 0;
		boolean visible = false;

		if (mbMessage.isDiscussion()) {
			classNameId = _classNamesMap.get(MBDiscussion.class.getName());
		}
		else {
			classNameId = _classNamesMap.get(MBMessage.class.getName());
			visible = true;
		}

		return newAssetEntry(
			mbMessage.getGroupId(), mbMessage.getCreateDate(),
			mbMessage.getModifiedDate(), classNameId, mbMessage.getMessageId(),
			mbMessage.getUuid(), 0, visible, ContentTypes.TEXT_HTML,
			mbMessage.getSubject());
	}

	public AssetEntry newAssetEntry(MBThread mbThread) {
		return newAssetEntry(
			mbThread.getGroupId(), mbThread.getCreateDate(),
			mbThread.getModifiedDate(),
			_classNamesMap.get(MBThread.class.getName()),
			mbThread.getThreadId(), mbThread.getUuid(), 0, false,
			StringPool.BLANK, String.valueOf(mbThread.getRootMessageId()));
	}

	public AssetEntry newAssetEntry(WikiPage wikiPage) {
		return newAssetEntry(
			wikiPage.getGroupId(), wikiPage.getCreateDate(),
			wikiPage.getModifiedDate(), getWikiPageClassNameId(),
			wikiPage.getResourcePrimKey(), wikiPage.getUuid(), 0, true,
			ContentTypes.TEXT_HTML, wikiPage.getTitle());
	}

	public BlogsEntry newBlogsEntry(long groupId, int index) {
		BlogsEntry blogsEntry = new BlogsEntryImpl();

		blogsEntry.setUuid(SequentialUUID.generate());
		blogsEntry.setEntryId(_counter.get());
		blogsEntry.setGroupId(groupId);
		blogsEntry.setCompanyId(_companyId);
		blogsEntry.setUserId(_sampleUserId);
		blogsEntry.setUserName(_SAMPLE_USER_NAME);
		blogsEntry.setCreateDate(new Date());
		blogsEntry.setModifiedDate(new Date());
		blogsEntry.setTitle("Test Blog " + index);
		blogsEntry.setUrlTitle("testblog" + index);
		blogsEntry.setContent("This is test blog " + index + ".");
		blogsEntry.setDisplayDate(new Date());
		blogsEntry.setStatusDate(new Date());

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
		contact.setClassNameId(_classNamesMap.get(User.class.getName()));
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

	public DDMStructure newDDLDDMStructure(long groupId) {
		StringBundler sb = new StringBundler(3 + _maxDDLCustomFieldCount * 10);

		sb.append("<?xml version=\"1.0\"?>");
		sb.append(
			"<root available-locales=\"en_US\" default-locale=\"en_US\">");

		for (int i = 0; i < _maxDDLCustomFieldCount; i++) {
			sb.append(
				"<dynamic-element dataType=\"string\" indexType=\"keyword\"");
			sb.append(" name=\"");
			sb.append(nextDDLCustomFieldName(groupId, i));
			sb.append(
				"\" readOnly=\"false\" repeatable=\"false\" required=\"false");
			sb.append(
				"\" showLabel=\"true\" type=\"text\" width=\"25\"><meta-data");
			sb.append(" locale=\"en_US\"><entry name=\"label\"><![CDATA[Text");
			sb.append(i);
			sb.append(
				"]]></entry><entry name=\"predefinedValue\"><![CDATA[]]>");
			sb.append("</entry><entry name=\"tip\"><![CDATA[]]></entry>");
			sb.append("</meta-data></dynamic-element>");
		}

		sb.append("</root>");

		return newDDMStructure(
			groupId, _classNamesMap.get(DDLRecordSet.class.getName()),
			"Test DDM Structure", sb.toString());
	}

	public DDLRecord newDDLRecord(DDLRecordSet ddlRecordSet) {
		DDLRecord ddlRecord = new DDLRecordImpl();

		ddlRecord.setUuid(SequentialUUID.generate());
		ddlRecord.setRecordId(_counter.get());
		ddlRecord.setGroupId(ddlRecordSet.getGroupId());
		ddlRecord.setCompanyId(_companyId);
		ddlRecord.setUserId(_sampleUserId);
		ddlRecord.setUserName(_SAMPLE_USER_NAME);
		ddlRecord.setVersionUserId(_sampleUserId);
		ddlRecord.setVersionUserName(_SAMPLE_USER_NAME);
		ddlRecord.setCreateDate(new Date());
		ddlRecord.setModifiedDate(new Date());
		ddlRecord.setDDMStorageId(_counter.get());
		ddlRecord.setRecordSetId(ddlRecordSet.getRecordSetId());
		ddlRecord.setVersion(DDLRecordConstants.VERSION_DEFAULT);
		ddlRecord.setDisplayIndex(DDLRecordConstants.DISPLAY_INDEX_DEFAULT);

		return ddlRecord;
	}

	public DDLRecordSet newDDLRecordSet(
		DDMStructure ddmStructure, int currentIndex) {

		DDLRecordSet ddlRecordSet = new DDLRecordSetImpl();

		ddlRecordSet.setUuid(SequentialUUID.generate());
		ddlRecordSet.setRecordSetId(_counter.get());
		ddlRecordSet.setGroupId(ddmStructure.getGroupId());
		ddlRecordSet.setCompanyId(_companyId);
		ddlRecordSet.setUserId(_sampleUserId);
		ddlRecordSet.setUserName(_SAMPLE_USER_NAME);
		ddlRecordSet.setCreateDate(new Date());
		ddlRecordSet.setModifiedDate(new Date());
		ddlRecordSet.setDDMStructureId(ddmStructure.getStructureId());
		ddlRecordSet.setRecordSetKey(String.valueOf(_counter.get()));

		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root ");
		sb.append("available-locales=\"en_US\" default-locale=\"en_US\">");
		sb.append("<Name language-id=\"en_US\">Test DDL Record Set ");
		sb.append(currentIndex);
		sb.append("</Name></root>");

		ddlRecordSet.setName(sb.toString());

		ddlRecordSet.setMinDisplayRows(
			DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT);
		ddlRecordSet.setScope(DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS);

		return ddlRecordSet;
	}

	public DDLRecordVersion newDDLRecordVersion(DDLRecord ddlRecord) {
		DDLRecordVersion ddlRecordVersion = new DDLRecordVersionImpl();

		ddlRecordVersion.setRecordVersionId(_counter.get());
		ddlRecordVersion.setGroupId(ddlRecord.getGroupId());
		ddlRecordVersion.setCompanyId(_companyId);
		ddlRecordVersion.setUserId(_sampleUserId);
		ddlRecordVersion.setUserName(_SAMPLE_USER_NAME);
		ddlRecordVersion.setCreateDate(ddlRecord.getModifiedDate());
		ddlRecordVersion.setDDMStorageId(ddlRecord.getDDMStorageId());
		ddlRecordVersion.setRecordSetId(ddlRecord.getRecordSetId());
		ddlRecordVersion.setRecordId(ddlRecord.getRecordId());
		ddlRecordVersion.setVersion(ddlRecord.getVersion());
		ddlRecordVersion.setDisplayIndex(ddlRecord.getDisplayIndex());
		ddlRecordVersion.setStatus(WorkflowConstants.STATUS_APPROVED);
		ddlRecordVersion.setStatusDate(ddlRecord.getModifiedDate());

		return ddlRecordVersion;
	}

	public DDMContent newDDMContent(DDLRecord ddlRecord, int currentIndex) {
		StringBundler sb = new StringBundler(3 + _maxDDLCustomFieldCount * 10);

		sb.append("<?xml version=\"1.0\"?><root>");

		for (int i = 0; i < _maxDDLCustomFieldCount; i++) {
			sb.append("<dynamic-element default-language-id=\"en_US\" name=\"");
			sb.append(nextDDLCustomFieldName(ddlRecord.getGroupId(), i));
			sb.append("\"><dynamic-content language-id=\"en_US\">");
			sb.append("<![CDATA[Test Record ");
			sb.append(currentIndex);
			sb.append("]]></dynamic-content></dynamic-element>");
		}

		sb.append("<dynamic-element default-language-id=\"en_US\" name=\"_");
		sb.append(
			"fieldsDisplay\"><dynamic-content language-id=\"en_US\"><![CDATA[");

		for (int i = 0; i < _maxDDLCustomFieldCount; i++) {
			sb.append(nextDDLCustomFieldName(ddlRecord.getGroupId(), i));
			sb.append(DDMImpl.INSTANCE_SEPARATOR);
			sb.append(PwdGenerator.getPassword(4));
			sb.append(StringPool.COMMA);
		}

		sb.setStringAt(
			"]]></dynamic-content></dynamic-element></root>", sb.index() - 1);

		return newDDMContent(
			ddlRecord.getDDMStorageId(), ddlRecord.getGroupId(), sb.toString());
	}

	public DDMContent newDDMContent(DLFileEntry dlFileEntry) {
		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\"?><root><dynamic-element ");
		sb.append("name=\"CONTENT_TYPE\"><dynamic-content>");
		sb.append("<![CDATA[text/plain]]></dynamic-content></dynamic-element>");
		sb.append("<dynamic-element <![CDATA[ISO-8859-1]]></dynamic-content>");
		sb.append("</dynamic-element></root>");

		return newDDMContent(
			_counter.get(), dlFileEntry.getGroupId(), sb.toString());
	}

	public DDMStorageLink newDDMStorageLink(
		long ddmStorageLinkId, DDMContent ddmContent, long structureId) {

		DDMStorageLink ddmStorageLink = new DDMStorageLinkImpl();

		ddmStorageLink.setUuid(SequentialUUID.generate());
		ddmStorageLink.setStorageLinkId(ddmStorageLinkId);
		ddmStorageLink.setClassNameId(
			_classNamesMap.get(DDMContent.class.getName()));
		ddmStorageLink.setClassPK(ddmContent.getContentId());
		ddmStorageLink.setStructureId(structureId);

		return ddmStorageLink;
	}

	public DDMStructureLink newDDMStructureLink(DDLRecordSet ddlRecordSet) {
		return newDDMStructureLink(
			_classNamesMap.get(DDLRecordSet.class.getName()),
			ddlRecordSet.getRecordSetId(), ddlRecordSet.getDDMStructureId());
	}

	public DDMStructureLink newDDMStructureLink(
		DLFileEntryMetadata dLFileEntryMetadata) {

		return newDDMStructureLink(
			_classNamesMap.get(DLFileEntryMetadata.class.getName()),
			dLFileEntryMetadata.getFileEntryMetadataId(),
			dLFileEntryMetadata.getDDMStructureId());
	}

	public DLFileEntry newDlFileEntry(DLFolder dlFoler, int index) {
		DLFileEntry dlFileEntry = new DLFileEntryImpl();

		dlFileEntry.setUuid(SequentialUUID.generate());
		dlFileEntry.setFileEntryId(_counter.get());
		dlFileEntry.setGroupId(dlFoler.getGroupId());
		dlFileEntry.setCompanyId(_companyId);
		dlFileEntry.setUserId(_sampleUserId);
		dlFileEntry.setUserName(_SAMPLE_USER_NAME);
		dlFileEntry.setVersionUserId(_sampleUserId);
		dlFileEntry.setVersionUserName(_SAMPLE_USER_NAME);
		dlFileEntry.setCreateDate(nextFutureDate());
		dlFileEntry.setModifiedDate(nextFutureDate());
		dlFileEntry.setRepositoryId(dlFoler.getRepositoryId());
		dlFileEntry.setFolderId(dlFoler.getFolderId());
		dlFileEntry.setName("TestFile" + index);
		dlFileEntry.setExtension("txt");
		dlFileEntry.setMimeType(ContentTypes.TEXT_PLAIN);
		dlFileEntry.setTitle("TestFile" + index + ".txt");
		dlFileEntry.setFileEntryTypeId(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);
		dlFileEntry.setVersion(DLFileEntryConstants.VERSION_DEFAULT);
		dlFileEntry.setSize(_maxDLFileEntrySize);

		return dlFileEntry;
	}

	public DLFileEntryMetadata newDLFileEntryMetadata(
		long ddmStorageLinkId, long ddmStructureId,
		DLFileVersion dlFileVersion) {

		DLFileEntryMetadata dlFileEntryMetadata = new DLFileEntryMetadataImpl();

		dlFileEntryMetadata.setUuid(SequentialUUID.generate());
		dlFileEntryMetadata.setFileEntryMetadataId(_counter.get());
		dlFileEntryMetadata.setDDMStorageId(ddmStorageLinkId);
		dlFileEntryMetadata.setDDMStructureId(ddmStructureId);
		dlFileEntryMetadata.setFileEntryTypeId(
			dlFileVersion.getFileEntryTypeId());
		dlFileEntryMetadata.setFileEntryId(dlFileVersion.getFileEntryId());
		dlFileEntryMetadata.setFileVersionId(dlFileVersion.getFileVersionId());

		return dlFileEntryMetadata;
	}

	public DLFileVersion newDLFileVersion(DLFileEntry dlFileEntry) {
		DLFileVersion dlFileVersion = new DLFileVersionImpl();

		dlFileVersion.setUuid(SequentialUUID.generate());
		dlFileVersion.setFileVersionId(_counter.get());
		dlFileVersion.setGroupId(dlFileEntry.getGroupId());
		dlFileVersion.setCompanyId(_companyId);
		dlFileVersion.setUserId(_sampleUserId);
		dlFileVersion.setUserName(_SAMPLE_USER_NAME);
		dlFileVersion.setCreateDate(nextFutureDate());
		dlFileVersion.setModifiedDate(nextFutureDate());
		dlFileVersion.setRepositoryId(dlFileEntry.getRepositoryId());
		dlFileVersion.setFolderId(dlFileEntry.getFolderId());
		dlFileVersion.setFileEntryId(dlFileEntry.getFileEntryId());
		dlFileVersion.setExtension(dlFileEntry.getExtension());
		dlFileVersion.setMimeType(dlFileEntry.getMimeType());
		dlFileVersion.setTitle(dlFileEntry.getTitle());
		dlFileVersion.setFileEntryTypeId(dlFileEntry.getFileEntryTypeId());
		dlFileVersion.setVersion(dlFileEntry.getVersion());
		dlFileVersion.setSize(dlFileEntry.getSize());

		return dlFileVersion;
	}

	public DLFolder newDLFolder(long groupId, long parentFolderId, int index) {
		DLFolder dlFolder = new DLFolderImpl();

		dlFolder.setUuid(SequentialUUID.generate());
		dlFolder.setFolderId(_counter.get());
		dlFolder.setGroupId(groupId);
		dlFolder.setCompanyId(_companyId);
		dlFolder.setUserId(_sampleUserId);
		dlFolder.setUserName(_SAMPLE_USER_NAME);
		dlFolder.setCreateDate(nextFutureDate());
		dlFolder.setModifiedDate(nextFutureDate());
		dlFolder.setRepositoryId(groupId);
		dlFolder.setParentFolderId(parentFolderId);
		dlFolder.setName("Test Folder " + index);
		dlFolder.setLastPostDate(nextFutureDate());
		dlFolder.setDefaultFileEntryTypeId(
			_defaultDLFileEntryType.getFileEntryTypeId());
		dlFolder.setStatusDate(nextFutureDate());

		return dlFolder;
	}

	public Group newGroup(User user) throws Exception {
		return newGroup(
			_counter.get(), _classNamesMap.get(User.class.getName()),
			user.getUserId(), user.getScreenName(), false);
	}

	public IntegerWrapper newInteger() {
		return new IntegerWrapper();
	}

	public JournalArticle newJournalArticle(
		JournalArticleResource journalArticleResource, int articleIndex,
		int versionIndex) {

		JournalArticle journalArticle = new JournalArticleImpl();

		journalArticle.setUuid(SequentialUUID.generate());
		journalArticle.setId(_counter.get());
		journalArticle.setResourcePrimKey(
			journalArticleResource.getResourcePrimKey());
		journalArticle.setGroupId(journalArticleResource.getGroupId());
		journalArticle.setCompanyId(_companyId);
		journalArticle.setUserId(_sampleUserId);
		journalArticle.setUserName(_SAMPLE_USER_NAME);
		journalArticle.setCreateDate(new Date());
		journalArticle.setModifiedDate(new Date());
		journalArticle.setClassNameId(
			JournalArticleConstants.CLASSNAME_ID_DEFAULT);
		journalArticle.setArticleId(journalArticleResource.getArticleId());
		journalArticle.setVersion(versionIndex);

		StringBundler sb = new StringBundler(4);

		sb.append("TestJournalArticle_");
		sb.append(articleIndex);
		sb.append(StringPool.UNDERLINE);
		sb.append(versionIndex);

		String urlTitle = sb.toString();

		sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root ");
		sb.append("available-locales=\"en_US\" default-locale=\"en_US\">");
		sb.append("<Title language-id=\"en_US\">");
		sb.append(urlTitle);
		sb.append("</Title></root>");

		String title = sb.toString();

		journalArticle.setTitle(title);
		journalArticle.setUrlTitle(urlTitle);

		journalArticle.setContent(_journalArticleContent);
		journalArticle.setType("general");
		journalArticle.setDisplayDate(new Date());
		journalArticle.setExpirationDate(nextFutureDate());
		journalArticle.setReviewDate(new Date());
		journalArticle.setIndexable(true);
		journalArticle.setStatusDate(new Date());

		return journalArticle;
	}

	public JournalArticleResource newJournalArticleResource(long groupId) {
		JournalArticleResource journalArticleResource =
			new JournalArticleResourceImpl();

		journalArticleResource.setUuid(SequentialUUID.generate());
		journalArticleResource.setResourcePrimKey(_counter.get());
		journalArticleResource.setGroupId(groupId);
		journalArticleResource.setArticleId(String.valueOf(_counter.get()));

		return journalArticleResource;
	}

	public JournalContentSearch newJournalContentSearch(
		JournalArticle journalArticle, long layoutId) {

		JournalContentSearch journalContentSearch =
			new JournalContentSearchImpl();

		journalContentSearch.setContentSearchId(_counter.get());
		journalContentSearch.setGroupId(journalArticle.getGroupId());
		journalContentSearch.setCompanyId(_companyId);
		journalContentSearch.setLayoutId(layoutId);
		journalContentSearch.setPortletId(PortletKeys.JOURNAL_CONTENT);
		journalContentSearch.setArticleId(journalArticle.getArticleId());

		return journalContentSearch;
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
		layout.setUserId(_sampleUserId);
		layout.setUserName(_SAMPLE_USER_NAME);
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

	public MBCategory newMBCategory(long groupId, int index) {
		MBCategory mbCategory = new MBCategoryImpl();

		mbCategory.setUuid(SequentialUUID.generate());
		mbCategory.setCategoryId(_counter.get());
		mbCategory.setGroupId(groupId);
		mbCategory.setCompanyId(_companyId);
		mbCategory.setUserId(_sampleUserId);
		mbCategory.setUserName(_SAMPLE_USER_NAME);
		mbCategory.setCreateDate(new Date());
		mbCategory.setModifiedDate(new Date());
		mbCategory.setParentCategoryId(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
		mbCategory.setName("Test Category " + index);
		mbCategory.setDisplayStyle(MBCategoryConstants.DEFAULT_DISPLAY_STYLE);
		mbCategory.setThreadCount(_maxMBThreadCount);
		mbCategory.setMessageCount(_maxMBThreadCount * _maxMBMessageCount);
		mbCategory.setLastPostDate(new Date());
		mbCategory.setStatusDate(new Date());

		return mbCategory;
	}

	public MBDiscussion newMBDiscussion(
		long groupId, long classNameId, long classPK, long threadId) {

		MBDiscussion mbDiscussion = new MBDiscussionImpl();

		mbDiscussion.setUuid(SequentialUUID.generate());
		mbDiscussion.setDiscussionId(_counter.get());
		mbDiscussion.setGroupId(groupId);
		mbDiscussion.setCompanyId(_companyId);
		mbDiscussion.setUserId(_sampleUserId);
		mbDiscussion.setUserName(_SAMPLE_USER_NAME);
		mbDiscussion.setCreateDate(new Date());
		mbDiscussion.setModifiedDate(new Date());
		mbDiscussion.setClassNameId(classNameId);
		mbDiscussion.setClassPK(classPK);
		mbDiscussion.setThreadId(threadId);

		return mbDiscussion;
	}

	public MBMailingList newMBMailingList(MBCategory mbCategory) {
		MBMailingList mbMailingList = new MBMailingListImpl();

		mbMailingList.setUuid(SequentialUUID.generate());
		mbMailingList.setMailingListId(_counter.get());
		mbMailingList.setGroupId(mbCategory.getGroupId());
		mbMailingList.setCompanyId(_companyId);
		mbMailingList.setUserId(_sampleUserId);
		mbMailingList.setUserName(_SAMPLE_USER_NAME);
		mbMailingList.setCreateDate(new Date());
		mbMailingList.setModifiedDate(new Date());
		mbMailingList.setCategoryId(mbCategory.getCategoryId());
		mbMailingList.setInProtocol("pop3");
		mbMailingList.setInServerPort(110);
		mbMailingList.setInUserName(_sampleUser.getEmailAddress());
		mbMailingList.setInPassword(_sampleUser.getPassword());
		mbMailingList.setInReadInterval(5);
		mbMailingList.setOutServerPort(25);

		return mbMailingList;
	}

	public MBMessage newMBMessage(MBThread mbThread, int index) {
		long messageId = 0;
		long parentMessageId = 0;

		if (index == 1) {
			messageId = mbThread.getRootMessageId();
			parentMessageId = MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID;
		}
		else {
			messageId = _counter.get();
			parentMessageId = mbThread.getRootMessageId();
		}

		return newMBMessage(
			mbThread.getGroupId(), 0, 0, mbThread.getCategoryId(),
			mbThread.getThreadId(), messageId, mbThread.getRootMessageId(),
			parentMessageId, "Test Message " + index,
			"This is test message " + index + ".");
	}

	public MBMessage newMBMessage(
		MBThread mbThread, long classNameId, long classPK, int index) {

		long messageId = 0;
		long parentMessageId = 0;
		String subject = null;
		String body = null;

		if (index == 0) {
			messageId = mbThread.getRootMessageId();
			parentMessageId = MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID;
			subject = String.valueOf(classPK);
			body = String.valueOf(classPK);
		}
		else {
			messageId = _counter.get();
			parentMessageId = mbThread.getRootMessageId();
			subject = "N/A";
			body = "This is test comment " + index + ".";
		}

		return newMBMessage(
			mbThread.getGroupId(), classNameId, classPK,
			MBCategoryConstants.DISCUSSION_CATEGORY_ID, mbThread.getThreadId(),
			messageId, mbThread.getRootMessageId(), parentMessageId, subject,
			body);
	}

	public MBStatsUser newMBStatsUser(long groupId) {
		MBStatsUser mbStatsUser = new MBStatsUserImpl();

		mbStatsUser.setStatsUserId(_counter.get());
		mbStatsUser.setGroupId(groupId);
		mbStatsUser.setUserId(_sampleUserId);
		mbStatsUser.setMessageCount(
			_maxMBCategoryCount * _maxMBThreadCount * _maxMBMessageCount);
		mbStatsUser.setLastPostDate(new Date());

		return mbStatsUser;
	}

	public MBThread newMBThread(
		long threadId, long groupId, long rootMessageId, int messageCount) {

		if (messageCount == 0) {
			messageCount = 1;
		}

		return newMBThread(
			threadId, groupId, MBCategoryConstants.DISCUSSION_CATEGORY_ID,
			rootMessageId, messageCount);
	}

	public MBThread newMBThread(MBCategory mbCategory) {
		return newMBThread(
			_counter.get(), mbCategory.getGroupId(), mbCategory.getCategoryId(),
			_counter.get(), _maxMBMessageCount);
	}

	public MBThreadFlag newMBThreadFlag(MBThread mbThread) {
		MBThreadFlag mbThreadFlag = new MBThreadFlagImpl();

		mbThreadFlag.setUuid(SequentialUUID.generate());
		mbThreadFlag.setThreadFlagId(_counter.get());
		mbThreadFlag.setGroupId(mbThread.getGroupId());
		mbThreadFlag.setCompanyId(_companyId);
		mbThreadFlag.setUserId(_sampleUserId);
		mbThreadFlag.setUserName(_SAMPLE_USER_NAME);
		mbThreadFlag.setCreateDate(new Date());
		mbThreadFlag.setModifiedDate(new Date());
		mbThreadFlag.setThreadId(mbThread.getThreadId());

		return mbThreadFlag;
	}

	public List<PortletPreferences> newPortletPreferences(long plid) {
		List<PortletPreferences> portletPreferencesList =
			new ArrayList<PortletPreferences>(2);

		portletPreferencesList.add(
			newPortletPreferences(
				plid, PortletKeys.DOCKBAR,
				PortletConstants.DEFAULT_PREFERENCES));

		portletPreferencesList.add(
			newPortletPreferences(
				plid, PortletKeys.PORTLET_CONFIGURATION,
				PortletConstants.DEFAULT_PREFERENCES));

		return portletPreferencesList;
	}

	public PortletPreferences newPortletPreferences(
			long plid, String portletId, DDLRecordSet ddlRecordSet)
		throws Exception {

		javax.portlet.PortletPreferences jxPreferences =
			new com.liferay.portlet.PortletPreferencesImpl();

		jxPreferences.setValue("editable", "true");
		jxPreferences.setValue(
			"recordSetId", String.valueOf(ddlRecordSet.getRecordSetId()));
		jxPreferences.setValue("spreadsheet", "false");

		return newPortletPreferences(
			plid, portletId,
			PortletPreferencesFactoryUtil.toXML(jxPreferences));
	}

	public PortletPreferences newPortletPreferences(
			long plid, String portletId,
			JournalArticleResource journalArticleResource)
		throws Exception {

		javax.portlet.PortletPreferences jxPreferences =
			new com.liferay.portlet.PortletPreferencesImpl();

		jxPreferences.setValue(
			"articleId", journalArticleResource.getArticleId());
		jxPreferences.setValue("enableCommentRatings", "false");
		jxPreferences.setValue("enableComments", "false");
		jxPreferences.setValue("enablePrint", "false");
		jxPreferences.setValue("enableRatings", "false");
		jxPreferences.setValue("enableRelatedAssets", "true");
		jxPreferences.setValue("enableViewCountIncrement", "false");
		jxPreferences.setValue(
			"groupId", String.valueOf(journalArticleResource.getGroupId()));
		jxPreferences.setValue("showAvailableLocales", "false");

		return newPortletPreferences(
			plid, portletId,
			PortletPreferencesFactoryUtil.toXML(jxPreferences));
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

	public List<ResourcePermission> newResourcePermissions(
		AssetCategory assetCategory) {

		return newResourcePermissions(
			AssetCategory.class.getName(),
			String.valueOf(assetCategory.getCategoryId()), _sampleUserId);
	}

	public List<ResourcePermission> newResourcePermissions(
		AssetVocabulary assetVocabulary) {

		if (assetVocabulary.getUserId() == _defaultUserId) {
			List<ResourcePermission> resourcePermissions =
				new ArrayList<ResourcePermission>(1);

			resourcePermissions.add(
				newResourcePermission(
					AssetVocabulary.class.getName(),
					String.valueOf(assetVocabulary.getVocabularyId()),
					_ownerRole.getRoleId(), _defaultUserId));

			return resourcePermissions;
		}

		return newResourcePermissions(
			AssetVocabulary.class.getName(),
			String.valueOf(assetVocabulary.getVocabularyId()), _sampleUserId);
	}

	public List<ResourcePermission> newResourcePermissions(
		BlogsEntry blogsEntry) {

		return newResourcePermissions(
			BlogsEntry.class.getName(), String.valueOf(blogsEntry.getEntryId()),
			_sampleUserId);
	}

	public List<ResourcePermission> newResourcePermissions(
		DDLRecordSet ddlRecordSet) {

		List<ResourcePermission> resourcePermissions =
			new ArrayList<ResourcePermission>(1);

		resourcePermissions.add(
			newResourcePermission(
				DDLRecordSet.class.getName(),
				String.valueOf(ddlRecordSet.getRecordSetId()),
				_ownerRole.getRoleId(), _defaultUserId));

		return resourcePermissions;
	}

	public List<ResourcePermission> newResourcePermissions(
		DDMStructure ddmStructure) {

		List<ResourcePermission> resourcePermissions =
			new ArrayList<ResourcePermission>(1);

		resourcePermissions.add(
			newResourcePermission(
				DDMStructure.class.getName(),
				String.valueOf(ddmStructure.getStructureId()),
				_ownerRole.getRoleId(), _defaultUserId));

		return resourcePermissions;
	}

	public List<ResourcePermission> newResourcePermissions(
		DLFileEntry dlFileEntry) {

		return newResourcePermissions(
			DLFileEntry.class.getName(),
			String.valueOf(dlFileEntry.getFileEntryId()), _sampleUserId);
	}

	public List<ResourcePermission> newResourcePermissions(DLFolder dlFolder) {
		return newResourcePermissions(
			DLFolder.class.getName(), String.valueOf(dlFolder.getFolderId()),
			_sampleUserId);
	}

	public List<ResourcePermission> newResourcePermissions(
		JournalArticleResource journalArticleResource) {

		return newResourcePermissions(
			JournalArticle.class.getName(),
			String.valueOf(journalArticleResource.getResourcePrimKey()),
			_sampleUserId);
	}

	public List<ResourcePermission> newResourcePermissions(Layout layout) {
		return newResourcePermissions(
			Layout.class.getName(), String.valueOf(layout.getPlid()),
			_sampleUserId);
	}

	public List<ResourcePermission> newResourcePermissions(
		MBCategory mbCategory) {

		return newResourcePermissions(
			MBCategory.class.getName(),
			String.valueOf(mbCategory.getCategoryId()), _sampleUserId);
	}

	public List<ResourcePermission> newResourcePermissions(
		MBMessage mbMessage) {

		return newResourcePermissions(
			MBMessage.class.getName(), String.valueOf(mbMessage.getMessageId()),
			_sampleUserId);
	}

	public List<ResourcePermission> newResourcePermissions(
		PortletPreferences portletPreferences) {

		String portletId = portletPreferences.getPortletId();

		String name = portletId;

		int index = portletId.indexOf(StringPool.UNDERLINE);

		if (index > 0) {
			name = portletId.substring(0, index);
		}

		String primKey = PortletPermissionUtil.getPrimaryKey(
			portletPreferences.getPlid(), portletId);

		return newResourcePermissions(name, primKey, 0);
	}

	public List<ResourcePermission> newResourcePermissions(WikiNode wikiNode) {
		return newResourcePermissions(
			WikiNode.class.getName(), String.valueOf(wikiNode.getNodeId()),
			_sampleUserId);
	}

	public List<ResourcePermission> newResourcePermissions(WikiPage wikiPage) {
		return newResourcePermissions(
			WikiPage.class.getName(),
			String.valueOf(wikiPage.getResourcePrimKey()), _sampleUserId);
	}

	public SocialActivity newSocialActivity(BlogsEntry blogsEntry) {
		return newSocialActivity(
			blogsEntry.getGroupId(),
			_classNamesMap.get(BlogsEntry.class.getName()),
			blogsEntry.getEntryId(), BlogsActivityKeys.ADD_ENTRY,
			"{\"title\":\""+ blogsEntry.getTitle() +"\"}");
	}

	public SocialActivity newSocialActivity(DLFileEntry dlFileEntry) {
		return newSocialActivity(
			dlFileEntry.getGroupId(), getDLFileEntryClassNameId(),
			dlFileEntry.getFileEntryId(), DLActivityKeys.ADD_FILE_ENTRY,
			StringPool.BLANK);
	}

	public SocialActivity newSocialActivity(JournalArticle journalArticle) {
		int type = JournalActivityKeys.UPDATE_ARTICLE;

		if (journalArticle.getVersion() ==
				JournalArticleConstants.VERSION_DEFAULT) {

			type = JournalActivityKeys.ADD_ARTICLE;
		}

		return newSocialActivity(
			journalArticle.getGroupId(), getJournalArticleClassNameId(),
			journalArticle.getResourcePrimKey(), type,
			"{\"title\":\""+ journalArticle.getUrlTitle() +"\"}");
	}

	public SocialActivity newSocialActivity(MBMessage mbMessage) {
		long classNameId = mbMessage.getClassNameId();
		long classPk = mbMessage.getClassPK();

		int type = 0;
		String extraData = null;

		if (classNameId == _classNamesMap.get(WikiPage.class.getName())) {
			extraData = "{\"version\":1}";

			type = WikiActivityKeys.ADD_PAGE;
		}
		else if (classNameId == 0) {
			extraData = "{\"title\":\"" + mbMessage.getSubject() + "\"}";

			type = MBActivityKeys.ADD_MESSAGE;

			classNameId = _classNamesMap.get(MBMessage.class.getName());
			classPk = mbMessage.getMessageId();
		}
		else {
			StringBundler sb = new StringBundler(5);

			sb.append("{\"messageId\":\"");
			sb.append(mbMessage.getMessageId());
			sb.append("\", \"title\":");
			sb.append(mbMessage.getSubject());
			sb.append("}");

			extraData = sb.toString();

			type = SocialActivityConstants.TYPE_ADD_COMMENT;
		}

		return newSocialActivity(
			mbMessage.getGroupId(), classNameId, classPk, type, extraData);
	}

	public Subscription newSubscription(BlogsEntry blogsEntry) {
		return newSubscription(
			_classNamesMap.get(BlogsEntry.class.getName()),
			blogsEntry.getEntryId());
	}

	public Subscription newSubscription(MBThread mbThread) {
		return newSubscription(
			_classNamesMap.get(MBThread.class.getName()),
			mbThread.getThreadId());
	}

	public Subscription newSubscription(WikiPage wikiPage) {
		return newSubscription(
			_classNamesMap.get(WikiPage.class.getName()),
			wikiPage.getResourcePrimKey());
	}

	public User newUser(int index) {
		String[] userName = nextUserName(index - 1);

		return newUser(
			_counter.get(), userName[0], userName[1],
			"test" + _userScreenNameCounter.get(), false);
	}

	public WikiNode newWikiNode(long groupId, int index) {
		WikiNode wikiNode = new WikiNodeImpl();

		wikiNode.setUuid(SequentialUUID.generate());
		wikiNode.setNodeId(_counter.get());
		wikiNode.setGroupId(groupId);
		wikiNode.setCompanyId(_companyId);
		wikiNode.setUserId(_sampleUserId);
		wikiNode.setUserName(_SAMPLE_USER_NAME);
		wikiNode.setCreateDate(new Date());
		wikiNode.setModifiedDate(new Date());
		wikiNode.setName("Test Node " + index);
		wikiNode.setLastPostDate(new Date());
		wikiNode.setStatusDate(new Date());

		return wikiNode;
	}

	public WikiPage newWikiPage(WikiNode wikiNode, int index) {
		WikiPage wikiPage = new WikiPageImpl();

		wikiPage.setUuid(SequentialUUID.generate());
		wikiPage.setPageId(_counter.get());
		wikiPage.setResourcePrimKey(_counter.get());
		wikiPage.setGroupId(wikiNode.getGroupId());
		wikiPage.setCompanyId(_companyId);
		wikiPage.setUserId(_sampleUserId);
		wikiPage.setUserName(_SAMPLE_USER_NAME);
		wikiPage.setCreateDate(new Date());
		wikiPage.setModifiedDate(new Date());
		wikiPage.setNodeId(wikiNode.getNodeId());
		wikiPage.setTitle("Test Page " + index);
		wikiPage.setVersion(WikiPageConstants.VERSION_DEFAULT);
		wikiPage.setContent("This is test page " + index + ".");
		wikiPage.setFormat(WikiPageConstants.DEFAULT_FORMAT);
		wikiPage.setHead(true);

		return wikiPage;
	}

	public WikiPageResource newWikiPageResource(WikiPage wikiPage) {
		WikiPageResource wikiPageResource = new WikiPageResourceImpl();

		wikiPageResource.setUuid(SequentialUUID.generate());
		wikiPageResource.setResourcePrimKey(wikiPage.getResourcePrimKey());
		wikiPageResource.setNodeId(wikiPage.getNodeId());
		wikiPageResource.setTitle(wikiPage.getTitle());

		return wikiPageResource;
	}

	public String[] nextUserName(long index) {
		String[] userName = new String[2];

		userName[0] = _firstNames.get(
			(int)(index / _lastNames.size()) % _firstNames.size());
		userName[1] = _lastNames.get((int)(index % _lastNames.size()));

		return userName;
	}

	protected AssetEntry newAssetEntry(
		long groupId, Date createDate, Date modifiedDate, long classNameId,
		long classPK, String uuid, long classTypeId, boolean visible,
		String mimeType, String title) {

		AssetEntry assetEntry = new AssetEntryImpl();

		assetEntry.setEntryId(_counter.get());
		assetEntry.setGroupId(groupId);
		assetEntry.setCompanyId(_companyId);
		assetEntry.setUserId(_sampleUserId);
		assetEntry.setUserName(_SAMPLE_USER_NAME);
		assetEntry.setCreateDate(createDate);
		assetEntry.setModifiedDate(modifiedDate);
		assetEntry.setClassNameId(classNameId);
		assetEntry.setClassPK(classPK);
		assetEntry.setClassUuid(uuid);
		assetEntry.setClassTypeId(classTypeId);
		assetEntry.setVisible(visible);
		assetEntry.setStartDate(createDate);
		assetEntry.setEndDate(nextFutureDate());
		assetEntry.setPublishDate(createDate);
		assetEntry.setExpirationDate(nextFutureDate());
		assetEntry.setMimeType(mimeType);
		assetEntry.setTitle(title);

		return assetEntry;
	}

	protected AssetVocabulary newAssetVocabulary(
		long grouId, long userId, String userName, String name, String title) {

		AssetVocabulary assetVocabulary = new AssetVocabularyImpl();

		assetVocabulary.setUuid(SequentialUUID.generate());
		assetVocabulary.setVocabularyId(_counter.get());
		assetVocabulary.setGroupId(grouId);
		assetVocabulary.setCompanyId(_companyId);
		assetVocabulary.setUserId(userId);
		assetVocabulary.setUserName(userName);
		assetVocabulary.setCreateDate(new Date());
		assetVocabulary.setModifiedDate(new Date());
		assetVocabulary.setName(name);
		assetVocabulary.setTitle(title);
		assetVocabulary.setSettings(
			"multiValued=true\\nselectedClassNameIds=0");

		return assetVocabulary;
	}

	protected DDMContent newDDMContent(
		long contentId, long groupId, String xml) {

		DDMContent ddmContent = new DDMContentImpl();

		ddmContent.setUuid(SequentialUUID.generate());
		ddmContent.setContentId(contentId);
		ddmContent.setGroupId(groupId);
		ddmContent.setCompanyId(_companyId);
		ddmContent.setUserId(_sampleUserId);
		ddmContent.setUserName(_SAMPLE_USER_NAME);
		ddmContent.setCreateDate(nextFutureDate());
		ddmContent.setModifiedDate(nextFutureDate());
		ddmContent.setName(DDMStorageLink.class.getName());
		ddmContent.setXml(xml);

		return ddmContent;
	}

	protected DDMStructure newDDMStructure(
		long groupId, long classNameId, String structureKey, String xsd) {

		DDMStructure ddmStructure = new DDMStructureImpl();

		ddmStructure.setUuid(SequentialUUID.generate());
		ddmStructure.setStructureId(_counter.get());
		ddmStructure.setGroupId(groupId);
		ddmStructure.setCompanyId(_companyId);
		ddmStructure.setUserId(_sampleUserId);
		ddmStructure.setUserName(_SAMPLE_USER_NAME);
		ddmStructure.setCreateDate(nextFutureDate());
		ddmStructure.setModifiedDate(nextFutureDate());
		ddmStructure.setClassNameId(classNameId);
		ddmStructure.setStructureKey(structureKey);

		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root ");
		sb.append("available-locales=\"en_US\" default-locale=\"en_US\">");
		sb.append("<Name language-id=\"en_US\">");
		sb.append(structureKey);
		sb.append("</Name></root>");

		ddmStructure.setName(sb.toString());

		ddmStructure.setXsd(xsd);
		ddmStructure.setStorageType("xml");

		return ddmStructure;
	}

	protected DDMStructureLink newDDMStructureLink(
		long classNameId, long classPK, long structureId) {

		DDMStructureLink ddmStructureLink = new DDMStructureLinkImpl();

		ddmStructureLink.setStructureLinkId(_counter.get());
		ddmStructureLink.setClassNameId(classNameId);
		ddmStructureLink.setClassPK(classPK);
		ddmStructureLink.setStructureId(structureId);

		return ddmStructureLink;
	}

	protected Group newGroup(
			long groupId, long classNameId, long classPK, String name,
			boolean site)
		throws Exception {

		Group group = new GroupImpl();

		group.setUuid(SequentialUUID.generate());
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

	protected MBMessage newMBMessage(
		long groupId, long classNameId, long classPK, long categoryId,
		long threadId, long messageId, long rootMessageId, long parentMessageId,
		String subject, String body) {

		MBMessage mbMessage = new MBMessageImpl();

		mbMessage.setUuid(SequentialUUID.generate());
		mbMessage.setMessageId(messageId);
		mbMessage.setGroupId(groupId);
		mbMessage.setCompanyId(_companyId);
		mbMessage.setUserId(_sampleUserId);
		mbMessage.setUserName(_SAMPLE_USER_NAME);
		mbMessage.setCreateDate(new Date());
		mbMessage.setModifiedDate(new Date());
		mbMessage.setClassNameId(classNameId);
		mbMessage.setClassPK(classPK);
		mbMessage.setCategoryId(categoryId);
		mbMessage.setThreadId(threadId);
		mbMessage.setRootMessageId(rootMessageId);
		mbMessage.setParentMessageId(parentMessageId);
		mbMessage.setSubject(subject);
		mbMessage.setBody(body);
		mbMessage.setFormat(MBMessageConstants.DEFAULT_FORMAT);
		mbMessage.setStatusDate(new Date());

		return mbMessage;
	}

	protected MBThread newMBThread(
		long threadId, long groupId, long categoryId, long rootMessageId,
		int messageCount) {

		MBThread mbThread = new MBThreadImpl();

		mbThread.setUuid(SequentialUUID.generate());
		mbThread.setThreadId(threadId);
		mbThread.setGroupId(groupId);
		mbThread.setCompanyId(_companyId);
		mbThread.setUserId(_sampleUserId);
		mbThread.setUserName(_SAMPLE_USER_NAME);
		mbThread.setCreateDate(new Date());
		mbThread.setModifiedDate(new Date());
		mbThread.setCategoryId(categoryId);
		mbThread.setRootMessageId(rootMessageId);
		mbThread.setRootMessageUserId(_sampleUserId);
		mbThread.setMessageCount(messageCount);
		mbThread.setLastPostByUserId(_sampleUserId);
		mbThread.setLastPostDate(new Date());
		mbThread.setStatusDate(new Date());

		return mbThread;
	}

	protected PortletPreferences newPortletPreferences(
		long plid, String portletId, String preferences) {

		PortletPreferences portletPreferences = new PortletPreferencesImpl();

		portletPreferences.setPortletPreferencesId(_counter.get());
		portletPreferences.setOwnerId(PortletKeys.PREFS_OWNER_ID_DEFAULT);
		portletPreferences.setOwnerType(PortletKeys.PREFS_OWNER_TYPE_LAYOUT);
		portletPreferences.setPlid(plid);
		portletPreferences.setPortletId(portletId);
		portletPreferences.setPreferences(preferences);

		return portletPreferences;
	}

	protected ResourcePermission newResourcePermission(
		String name, String primKey, long roleId, long ownerId) {

		ResourcePermission resourcePermission = new ResourcePermissionImpl();

		resourcePermission.setResourcePermissionId(
			_resourcePermissionCounter.get());
		resourcePermission.setCompanyId(_companyId);
		resourcePermission.setName(name);
		resourcePermission.setScope(ResourceConstants.SCOPE_INDIVIDUAL);
		resourcePermission.setPrimKey(primKey);
		resourcePermission.setRoleId(roleId);
		resourcePermission.setOwnerId(ownerId);
		resourcePermission.setActionIds(1);

		return resourcePermission;
	}

	protected List<ResourcePermission> newResourcePermissions(
		String name, String primKey, long ownerId) {

		List<ResourcePermission> resourcePermissions =
			new ArrayList<ResourcePermission>(3);

		resourcePermissions.add(
			newResourcePermission(name, primKey, _guestRole.getRoleId(), 0));
		resourcePermissions.add(
			newResourcePermission(
				name, primKey, _ownerRole.getRoleId(), ownerId));
		resourcePermissions.add(
			newResourcePermission(
				name, primKey, _siteMemberRole.getRoleId(), 0));

		return resourcePermissions;
	}

	protected Role newRole(String name, int type) {
		Role role = new RoleImpl();

		role.setUuid(SequentialUUID.generate());
		role.setRoleId(_counter.get());
		role.setCompanyId(_companyId);
		role.setUserId(_sampleUserId);
		role.setUserName(_SAMPLE_USER_NAME);
		role.setCreateDate(new Date());
		role.setModifiedDate(new Date());
		role.setClassNameId(_classNamesMap.get(Role.class.getName()));
		role.setClassPK(role.getRoleId());
		role.setName(name);
		role.setType(type);

		return role;
	}

	protected SocialActivity newSocialActivity(
		long groupId, long classNameId, long classPK, int type,
		String extraData) {

		SocialActivity socialActivity = new SocialActivityImpl();

		socialActivity.setActivityId(_socialActivityCounter.get());
		socialActivity.setGroupId(groupId);
		socialActivity.setCompanyId(_companyId);
		socialActivity.setUserId(_sampleUserId);
		socialActivity.setCreateDate(_CURRENT_TIME + _timeCounter.get());
		socialActivity.setClassNameId(classNameId);
		socialActivity.setClassPK(classPK);
		socialActivity.setType(type);
		socialActivity.setExtraData(extraData);

		return socialActivity;
	}

	protected Subscription newSubscription(long classNameId, long classPK) {
		Subscription subscription = new SubscriptionImpl();

		subscription.setSubscriptionId(_counter.get());
		subscription.setCompanyId(_companyId);
		subscription.setUserId(_sampleUserId);
		subscription.setUserName(_SAMPLE_USER_NAME);
		subscription.setCreateDate(new Date());
		subscription.setModifiedDate(new Date());
		subscription.setClassNameId(classNameId);
		subscription.setClassPK(classPK);
		subscription.setFrequency(SubscriptionConstants.FREQUENCY_INSTANT);

		return subscription;
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

	protected String nextDDLCustomFieldName(
		long groupId, int customFieldIndex) {

		StringBundler sb = new StringBundler(4);

		sb.append("costom_field_text_");
		sb.append(groupId);
		sb.append("_");
		sb.append(customFieldIndex);

		return sb.toString();
	}

	protected Date nextFutureDate() {
		return new Date(
			_FUTURE_TIME + (_futureDateCounter.get() * Time.SECOND));
	}

	private static final long _CURRENT_TIME = System.currentTimeMillis();

	private static final String _DEPENDENCIES_DIR=
		"../portal-impl/src/com/liferay/portal/tools/samplesqlbuilder/" +
			"dependencies/";

	private static final long _FUTURE_TIME =
		System.currentTimeMillis() + Time.YEAR;

	private static final String _SAMPLE_USER_NAME = "Sample";

	private Account _account;
	private long _accountId;
	private Role _administratorRole;
	private List<AssetCategory> _assetCategories;
	private List<AssetVocabulary> _assetVocabularies;
	private String _baseDir;
	private List<ClassName> _classNames;
	private Map<String, Long> _classNamesMap = new HashMap<String, Long>();
	private Company _company;
	private long _companyId;
	private SimpleCounter _counter;
	private DDMStructure _defaultDLDDMStructure;
	private DLFileEntryType _defaultDLFileEntryType;
	private User _defaultUser;
	private long _defaultUserId;
	private String _dlDDMStructureContent;
	private List<String> _firstNames;
	private SimpleCounter _futureDateCounter;
	private Group _globalGroup;
	private long _globalGroupId;
	private List<Group> _groups;
	private Group _guestGroup;
	private long _guestGroupId;
	private Role _guestRole;
	private User _guestUser;
	private String _journalArticleContent;
	private List<String> _lastNames;
	private Map<Long, SimpleCounter> _layoutCounters =
		new HashMap<Long, SimpleCounter>();
	private int _maxAssetCategoryCount;
	private int _maxBlogsEntryCount;
	private int _maxDDLCustomFieldCount;
	private int _maxDLFileEntrySize;
	private int _maxGroupsCount;
	private int _maxJournalArticleCount;
	private int _maxMBCategoryCount;
	private int _maxMBMessageCount;
	private int _maxMBThreadCount;
	private int _maxUserToGroupCount;
	private Role _ownerRole;
	private Role _powerUserRole;
	private SimpleCounter _resourcePermissionCounter;
	private List<Role> _roles;
	private User _sampleUser;
	private long _sampleUserId;
	private Format _simpleDateFormat =
		FastDateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Role _siteMemberRole;
	private SimpleCounter _socialActivityCounter;
	private SimpleCounter _timeCounter;
	private Role _userRole;
	private SimpleCounter _userScreenNameCounter;
	private VirtualHost _virtualHost;

}